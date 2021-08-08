package com.midea.cloud.log.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.WorkHandler;
import com.midea.cloud.common.utils.support.EsQuerySupport;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;



import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author tanjl11
 * @date 2020/12/18 13:55
 */
@Slf4j
public class LogDocumentEventHandler implements WorkHandler<IndexRequest> {

    private final RestHighLevelClient restHighLevelClient;
    //或数据满了同步一次
    public final LinkedBlockingQueue<IndexRequest> data;
    //每x秒同步一次
    private final int threshsoldMs;

    private final int threshsoldSize;

    private final AtomicLong lastTime;

    public LogDocumentEventHandler(RestHighLevelClient client, Integer threshsoldSecond, Integer threshsoldSize) {
        this.restHighLevelClient = client;
        data = new LinkedBlockingQueue<>();
        lastTime = new AtomicLong();
        this.threshsoldMs = threshsoldSecond == null ? 1 * 1000 : threshsoldSecond * 1000;
        this.threshsoldSize = threshsoldSize == null ? 20 : threshsoldSize;
    }


    @Override
    public void onEvent(IndexRequest request) throws Exception {
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
}
