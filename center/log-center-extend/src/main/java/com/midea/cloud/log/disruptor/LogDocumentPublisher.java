package com.midea.cloud.log.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.log.controller.LogSearchController;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;


import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author tanjl11
 * @date 2020/12/18 13:50
 */
public class LogDocumentPublisher {

    private final RingBuffer<IndexRequest> ringBuffer;

    public LogDocumentPublisher(RingBuffer<IndexRequest> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<IndexRequest, LogDocument> TRANSLATOR =
            (indexRequest, l, document) -> {
                String s = JSON.toJSONString(document);
                if(Objects.nonNull(indexRequest)){
                    indexRequest
                            .index(LogSearchController.getTodayKey())
                            .type("_doc")
                            .source(s, XContentType.JSON);
                }
                document = null;
            };

    public void sendData(LogDocument document) {
        ringBuffer.publishEvent(TRANSLATOR, document);
    }
}
