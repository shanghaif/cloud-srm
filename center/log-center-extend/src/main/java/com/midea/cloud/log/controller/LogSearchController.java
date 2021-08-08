package com.midea.cloud.log.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.common.utils.support.EsQuerySupport;
import com.midea.cloud.common.utils.support.FieldParser;
import com.midea.cloud.log.disruptor.LogDocumentDisruptor;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import io.netty.util.HashedWheelTimer;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.percentiles.ParsedPercentiles;
import org.elasticsearch.search.aggregations.metrics.stats.ParsedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author tanjl11
 * @date 2020/12/18 9:45
 */
@RestController
@Slf4j
@RequestMapping("/es")
public class LogSearchController implements InitializingBean {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String responseDatePercentiles = "datePercentiles";
    private static final String userCount = "userCount";
    private static final String responseDateStats = "dateStats";
    private static final String resultStatus = "resultStatus";
    private static final String groupDate = "groupDate";
   /* @Autowired
    private LogDocumentDisruptor disruptor;*/
    @Autowired
    private RedisUtil redisUtil;
    @Value("${elasticsearch.log-index.shards}")
    private String shards;
    @Value("${elasticsearch.log-index.replicas}")
    private String replicas;
    @Autowired
    private RestTemplate restTemplate;
    private String[] restUrisArray;
    @Value("${elasticsearch.rest.uris}")
    private String restUris;
    private final int threshsoldMs=1000;

    private final int threshsoldSize = 20;
    private final AtomicLong lastTime=new AtomicLong(System.currentTimeMillis());
    public final LinkedBlockingQueue<IndexRequest> data=new LinkedBlockingQueue<>();
    private static final AtomicBoolean isCreateIndexToday = new AtomicBoolean(false);
    private static final HashedWheelTimer timer = new HashedWheelTimer(new NamedThreadFactory("resetSignOneDay", true));
    private static volatile String todayKey;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @SneakyThrows
    @PostMapping("/saveLog")
    public void saveLog(@RequestBody LogDocument document) throws IOException {
        checkWhetherCreateIndex();
        String s = JSON.toJSONString(document);
        IndexRequest request=new IndexRequest();
            request
                    .index(LogSearchController.getTodayKey())
                    .type("_doc")
                    .source(s, XContentType.JSON);
        if(Objects.nonNull(request)){
            data.add(request);
            long now = System.currentTimeMillis();
            if (now - lastTime.get() > threshsoldMs || data.size() >= threshsoldSize) {
                if (!data.isEmpty()) {
                    BulkRequest bulkRequest = new BulkRequest();
                    while (!data.isEmpty()) {
                        bulkRequest.add(data.poll().id(UUID.randomUUID().toString()));
                    }
                    restHighLevelClient.bulkAsync(bulkRequest, new ActionListener<BulkResponse>() {
                        @Override
                        public void onResponse(BulkResponse bulkItemResponses) {
                            if (bulkItemResponses.hasFailures()) {
                                log.info("日志异步回传信息--" + JSON.toJSONString(bulkItemResponses));
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            log.info("日志异步回传信息--" + JSON.toJSONString(e));
                        }
                    });
                    bulkRequest.requests().forEach(e->e=null);
                }
            }
            lastTime.set(now);
        }
    }


    @PostMapping("/agg")
    public List<Map<String, Object>> aggResult(@RequestBody LogDocument logDocument) throws IOException {
        SearchRequest request = new SearchRequest("logDocument");
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        buildBaseQuery(logDocument, builder);
        if (StringUtils.isNotBlank(logDocument.getLogInfo())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getLogInfo, logDocument.getLogInfo()));
        }
        DateHistogramAggregationBuilder time = AggregationBuilders.dateHistogram(groupDate)
                .interval(logDocument.getInterval())
                .field(FieldParser.resolveFieldName(LogDocument::getOperationTime))
                .format("yyyy-MM-dd:HH:mm:ss");
        String responseDate = FieldParser.resolveFieldName(LogDocument::getResponseDate);
        time.subAggregation(AggregationBuilders.cardinality(userCount).field(FieldParser.resolveFieldName(LogDocument::getUsername)))
                .subAggregation(AggregationBuilders.percentiles(responseDatePercentiles).field(responseDate))
                .subAggregation(AggregationBuilders.stats(responseDateStats).field(responseDate))
                .subAggregation(AggregationBuilders.terms(resultStatus).field(FieldParser.resolveFieldName(LogDocument::getResultStatus)));
        searchSourceBuilder.aggregation(time);
        searchSourceBuilder.query(builder);
        request.source(searchSourceBuilder);
        SearchResponse result = restHighLevelClient.search(request, EsQuerySupport.defalutHeaders());
        ParsedDateHistogram aggregator = result.getAggregations().get(groupDate);
        List<Map<String, Object>> resultList = new LinkedList<>();
        for (Histogram.Bucket bucket : aggregator.getBuckets()) {
            Aggregations aggregations = bucket.getAggregations();
            ParsedPercentiles percentiles = aggregations.get(responseDatePercentiles);
            ParsedStats stats = aggregations.get(responseDateStats);
            ParsedCardinality cardinality = aggregations.get(userCount);
            ParsedStringTerms terms = aggregations.get(resultStatus);
            Map<String, Object> map = new HashMap<>(8);
            Map resultRate = new HashMap(2);
            long bucketDocCount = bucket.getDocCount();
            for (Terms.Bucket rateBucket : terms.getBuckets()) {
                resultRate.put(rateBucket.getKey(), BigDecimal.valueOf(rateBucket.getDocCount() * 100).divide(BigDecimal.valueOf(bucketDocCount), 3, RoundingMode.HALF_DOWN));
            }
            Map statusMap = new HashMap();
            statusMap.put("min", stats.getMin());
            statusMap.put("max", stats.getMax());
            statusMap.put("avg", stats.getAvg());
            Map percentilesResult = new HashMap(8);
            percentiles.iterator().forEachRemaining(e -> percentilesResult.put(e.getPercent(), e.getValue()));
            map.put(terms.getName(), resultRate);
            map.put(cardinality.getName(), cardinality.getValue());
            map.put(stats.getName(), statusMap);
            map.put(percentiles.getName(), percentilesResult);
            map.put("name", bucket.getKeyAsString());
            map.put("total", bucketDocCount);
            resultList.add(map);
        }
        return resultList;
    }

    @SneakyThrows
    @PostMapping("/search")
    public ResultVO search(@RequestBody LogDocument document) {
        if (StringUtils.isNotBlank(document.getSql())) {
            StringBuilder url = new StringBuilder();
            int index = ThreadLocalRandom.current().nextInt(restUrisArray.length);
            StringBuilder urlStr = url.append("http://")
                    .append(restUrisArray[index])
                    .append("/_sql?sql=")
                    .append("SELECT * FROM logDocument where ")
                    .append(document.getSql())
                    .append("limit ")
                    .append(document.getPageNum() == 1 ? 0 : (document.getPageNum() - 1) * document.getPageSize())
                    .append(",")
                    .append(document.getPageSize());
            String finalUrl = urlStr.toString();
            log.info(finalUrl);
            JSONObject forObject = restTemplate.getForObject(finalUrl, JSONObject.class);
            JSONObject searchHits = forObject.getJSONObject("hits");
            Integer total = (Integer) searchHits.get("total");
            JSONArray hits = searchHits.getJSONArray("hits");
            ResultVO r = new ResultVO();
            List<Map> res = new LinkedList<>();
            for (int i = 0; i < hits.size(); i++) {
                JSONObject jsonObject = hits.getJSONObject(i);
                Map source = jsonObject.getJSONObject("_source");
                res.add(source);
            }
            r.list = res;
            r.size = res.size();
            r.total = Long.valueOf(total);
            return r;
        }
        SearchRequest request = new SearchRequest("logDocument");
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //时间过滤
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = null;
        buildBaseQuery(document, builder);
        //日志信息
        String logInfo = FieldParser.resolveFieldName(LogDocument::getLogInfo);
        if (StringUtils.isNotBlank(document.getLogInfo())) {
            highlightBuilder = new HighlightBuilder()
                    .field(logInfo)
                    .preTags("<span style=\"color:red\">")
                    .numOfFragments(0)
                    .postTags("</span>");
            builder.must(QueryBuilders.matchPhraseQuery(logInfo, document.getLogInfo()));
        }
        searchSourceBuilder.query(builder).sort(SortBuilders.fieldSort("operationTime").order(SortOrder.DESC));
        Integer size = Optional.ofNullable(document.getPageSize()).orElse(15);
        if (Objects.nonNull(highlightBuilder)) {
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        searchSourceBuilder.from(Optional.ofNullable(document.getPageNum()).map(e -> e == 1 ? 0 : (e - 1) * size).orElse(0)).size(size).query(builder);
        String s = searchSourceBuilder.toString();
        NStringEntity entity = new NStringEntity(s, ContentType.APPLICATION_JSON);
        Response post = restHighLevelClient.getLowLevelClient().performRequest("POST", "/_search", Collections.emptyMap(), entity, new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(1024 * 1024 * 1024), EsQuerySupport.defalutHeaders());
        String lines = EntityUtils.toString(post.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(lines);
        lines = null;
        Integer total = jsonObject.getJSONObject("hits").getInteger("total");
        JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
        List<Map> result = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONObject source = obj.getJSONObject("_source");
            JSONObject highlight = obj.getJSONObject("highlight");
            if(Objects.nonNull(highlight)){
                String logI = highlight.getJSONArray("logInfo").getString(0);
                for (Map.Entry<String, Object> entry : source.entrySet()) {
                    if(entry.getKey().equals("logInfo")){
                        entry.setValue(logI);
                    }
                }
            }
            result.add(source);
            obj = null;
        }
        jsonObject = null;
        ResultVO r = new ResultVO();
        r.list = result;
        r.size = result.size();
        r.total = total;
        return r;
    }

    private void buildBaseQuery(LogDocument document, BoolQueryBuilder builder) {
        String operationTimeStart = document.getOperationTimeStart();
        String operationTimeEnd = document.getOperationTimeEnd();
        if (StringUtils.isNotBlank(operationTimeStart) || StringUtils.isNotBlank(operationTimeEnd)) {
            RangeQueryBuilder rangeQuery = EsQuerySupport.rangeQuery(LogDocument::getOperationTime);
            if (StringUtils.isNotBlank(operationTimeEnd)) {
                rangeQuery.lte(operationTimeEnd);
            }
            if (StringUtils.isNotBlank(operationTimeStart)) {
                rangeQuery.gte(operationTimeStart);
            }
            builder.must(rangeQuery);
        }
        //方法名
        if (StringUtils.isNotBlank(document.getMethodName())) {
            builder.must(EsQuerySupport.termQuery(LogDocument::getMethodName, document.getMethodName()));
        }
        //请求路径
        if (StringUtils.isNotBlank(document.getRequestUrl())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getRequestUrl, document.getRequestUrl()));
        }
        //错误信息
        if (StringUtils.isNotBlank(document.getErrorInfo())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getErrorInfo, document.getErrorInfo()));
        }
        //请求参数
        if (StringUtils.isNotBlank(document.getRequestParam())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getRequestParam, document.getRequestParam()));
        }
        //返回结果
        if (StringUtils.isNotBlank(document.getResponseResult())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getResponseResult, document.getResponseResult()));
        }
        //用户名
        if (StringUtils.isNotBlank(document.getUsername())) {
            builder.must(EsQuerySupport.termQuery(LogDocument::getUsername, document.getUsername()));
        }
        //昵称
        if (StringUtils.isNotBlank(document.getNickname())) {
            builder.must(EsQuerySupport.termQuery(LogDocument::getNickname, document.getNickname()));
        }
        //模块
        if (StringUtils.isNotBlank(document.getModel())) {
            builder.must(EsQuerySupport.termQuery(LogDocument::getModel, document.getModel()));
        }
        if (StringUtils.isNotBlank(document.getResultStatus())) {
            builder.must(EsQuerySupport.termQuery(LogDocument::getResultStatus, document.getResultStatus()));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.restUrisArray = restUris.split(",");
    }

    @Data
    static class ResultVO {
        List<Map> list;
        private int size;
        private long total;
    }

    private void checkWhetherCreateIndex() throws IOException, InterruptedException {
        //内存判断
        while (!isCreateIndexToday.get()) {
            String key = getTodayKey();
            //redis判断——如果这里手动删除了索引，请把redis的key也删除了
            boolean exists = redisUtil.exists(key);
            if (exists) {
                if (!isCreateIndexToday.get()) {
                    synchronized (timer) {
                        if (!isCreateIndexToday.get()) {
                            isCreateIndexToday.compareAndSet(false, true);
                            long ttl = redisUtil.ttl(key);
                            timer.newTimeout(timeout -> {
                                isCreateIndexToday.compareAndSet(true, false);
                                setTodayKey();
                            }, ttl + 1, TimeUnit.SECONDS);
                        }
                    }
                }
                break;
            }
            Boolean lock = redisUtil.tryLock(key);
            if (lock) {
                try {
                    //es判断
                    CreateIndexRequest request = new CreateIndexRequest(key);
                    Response response = restHighLevelClient.getLowLevelClient().performRequest("HEAD", "/" + key);
                    int statusCode = response.getStatusLine().getStatusCode();
                    //如果不存在index,新建
                    if (Objects.equals(statusCode, 404)) {
                        request.source(String.format("{\"settings\":{\"number_of_shards\":%s,\"number_of_replicas\":%s},\"aliases\":{\"logDocument\":{}},\"mappings\":{\"_doc\":{\"properties\":{\"operationLogId\":{\"type\":\"long\",\"index\":true},\"nickname\":{\"type\":\"text\"},\"username\":{\"type\":\"keyword\"},\"userType\":{\"type\":\"keyword\"},\"methodName\":{\"type\":\"keyword\"},\"operationTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestIp\":{\"type\":\"ip\"},\"requestUrl\":{\"type\":\"text\"},\"requestParam\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"logInfo\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"responseResult\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"resultStatus\":{\"type\":\"keyword\"},\"errorInfo\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"model\":{\"type\":\"keyword\"},\"requestStartTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestEndTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"responseDate\":{\"type\":\"long\"}}}}}", shards, replicas), XContentType.JSON);
                        restHighLevelClient.indices().create(request);
                    }
                    resetStatusForNextIndex(key);
                } finally {
                    redisUtil.unLock(key);
                }
                break;
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    private void resetStatusForNextIndex(String key) {
        Instant instant = Instant.now();
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        //获取第第二天零点时刻的实例
        LocalDateTime tomorrow = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        //ChronoUnit日期枚举类,between方法计算两个时间对象之间的时间量
        long seconds = ChronoUnit.SECONDS.between(now, tomorrow) + 1;
        redisUtil.set(key, "created", seconds);
        long ttl = redisUtil.ttl(key);
        timer.newTimeout(timeout -> {
            isCreateIndexToday.compareAndSet(true, false);
            setTodayKey();
        }, ttl + 1, TimeUnit.SECONDS);
        isCreateIndexToday.compareAndSet(false, true);
    }

    public static synchronized void setTodayKey() {
        String format = dateTimeFormatter.format(LocalDate.now());
        todayKey = "log_document_" + format;
    }

    public static String getTodayKey() {
        if (todayKey == null) {
            setTodayKey();
        }
        return todayKey;
    }

}
