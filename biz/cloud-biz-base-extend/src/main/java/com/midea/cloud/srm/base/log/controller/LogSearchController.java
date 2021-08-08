package com.midea.cloud.srm.base.log.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.common.utils.support.EsQuerySupport;
import com.midea.cloud.common.utils.support.FieldParser;
import com.midea.cloud.log.aop.es.EsUtil;
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
import org.elasticsearch.client.RequestOptions;
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
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.ParsedPercentiles;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
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
 * 日志界面查询
 * 
 * @author lizl7
 *
 */
@RestController
@Slf4j
@RequestMapping("/logsearch")
public class LogSearchController {

	@Autowired
	EsUtil esUtil;

	public final LinkedBlockingQueue<IndexRequest> data = new LinkedBlockingQueue<>();
	private static volatile String todayKey;
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 聚合统计
	 * @param logDocument
	 * @return
	 * @throws IOException
	 */
    @PostMapping("/agg")
    public List<Map<String, Object>> aggResult(@RequestBody LogDocument logDocument) throws Exception {
    	Date dateNow =new Date();
        SearchRequest request = new SearchRequest(esUtil.getIndexByDate(esUtil.Index_Pre_Logdocument, dateNow));
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        buildBaseQuery(logDocument, builder);
        if (StringUtils.isNotBlank(logDocument.getLogInfo())) {
            builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getLogInfo, logDocument.getLogInfo()));
        }
        
        String responseDatePercentiles = "datePercentiles";
        String userCount = "userCount";
        String responseDateStats = "dateStats";
        String resultStatus = "resultStatus";
        String groupDate = "groupDate";
        
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

        SearchResponse result = esUtil.client.search(request, RequestOptions.DEFAULT);
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

	@PostMapping("/search")
	public ResultVO search(@RequestBody LogDocument document) throws Exception {
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// 时间过滤
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		buildBaseQuery(document, builder);
		// 日志信息
		String logInfo = FieldParser.resolveFieldName(LogDocument::getLogInfo);
		HighlightBuilder highlightBuilder = null;
		if (StringUtils.isNotBlank(document.getLogInfo())) {
			highlightBuilder = new HighlightBuilder().field(logInfo).preTags("<span style=\"color:red\">")
					.numOfFragments(0).postTags("</span>");
			builder.must(QueryBuilders.matchPhraseQuery(logInfo, document.getLogInfo()));
		}
		//排序
		//searchSourceBuilder.query(builder).sort(SortBuilders.fieldSort("operationTime").order(SortOrder.DESC));
		searchSourceBuilder.query(builder);
		Integer size = Optional.ofNullable(document.getPageSize()).orElse(15);
		if (Objects.nonNull(highlightBuilder)) {
			searchSourceBuilder.highlighter(highlightBuilder);
		}
		searchSourceBuilder
				.from(Optional.ofNullable(document.getPageNum()).map(e -> e == 1 ? 0 : (e - 1) * size).orElse(0))
				.size(size).query(builder);

		Date dateNow = new Date();
		String index = esUtil.getIndexByDate(esUtil.Index_Pre_Logdocument, dateNow);
		List<LogDocument> listData = esUtil.search(index, searchSourceBuilder, LogDocument.class);

		ResultVO r = new ResultVO();
		r.list = listData;
		r.size = listData.size();
		r.total = 100;
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
		// 方法名
		if (StringUtils.isNotBlank(document.getMethodName())) {
			builder.must(EsQuerySupport.termQuery(LogDocument::getMethodName, document.getMethodName()));
		}
		// 请求路径
		if (StringUtils.isNotBlank(document.getRequestUrl())) {
			builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getRequestUrl, document.getRequestUrl()));
		}
		// 错误信息
		if (StringUtils.isNotBlank(document.getErrorInfo())) {
			builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getErrorInfo, document.getErrorInfo()));
		}
		// 请求参数
		if (StringUtils.isNotBlank(document.getRequestParam())) {
			builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getRequestParam, document.getRequestParam()));
		}
		// 返回结果
		if (StringUtils.isNotBlank(document.getResponseResult())) {
			builder.must(EsQuerySupport.matchPhraseQuery(LogDocument::getResponseResult, document.getResponseResult()));
		}
		// 用户名
		if (StringUtils.isNotBlank(document.getUsername())) {
			builder.must(EsQuerySupport.termQuery(LogDocument::getUsername, document.getUsername()));
		}
		// 昵称
		if (StringUtils.isNotBlank(document.getNickname())) {
			builder.must(EsQuerySupport.termQuery(LogDocument::getNickname, document.getNickname()));
		}
		// 模块
		if (StringUtils.isNotBlank(document.getModel())) {
			builder.must(EsQuerySupport.termQuery(LogDocument::getModel, document.getModel()));
		}
		if (StringUtils.isNotBlank(document.getResultStatus())) {
			builder.must(EsQuerySupport.termQuery(LogDocument::getResultStatus, document.getResultStatus()));
		}
	}

	@Data
	static class ResultVO {
		List<LogDocument> list;
		private int size;
		private long total;
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
