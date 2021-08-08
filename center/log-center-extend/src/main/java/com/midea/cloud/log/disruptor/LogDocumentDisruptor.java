package com.midea.cloud.log.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tanjl11
 * @date 2020/12/18 14:26
 */
//@Configuration
@AutoConfigureAfter(RestHighLevelClient.class)
@Slf4j
public class LogDocumentDisruptor implements DisposableBean, InitializingBean {
    @Value("${elasticsearch.sync.threshsold.second}")
    private Integer threshsoldSecond;
    @Value("${elasticsearch.sync.threshsold.size}")
    private Integer threshsoldSize;
    @Value("${elasticsearch.sync.producer.size}")
    private Integer producerSize;
    @Autowired
    private RestHighLevelClient client;

    private RingBuffer ringBuffer;

    private ThreadPoolExecutor asyncPool;

    private WorkerPool workerPool;

    private LogDocumentPublisher[] publisher;

    public void sendData(LogDocument document) {
        LogDocumentPublisher logDocumentPublisher = publisher[document.hashCode() & (producerSize - 1)];
        logDocumentPublisher.sendData(document);
    }

    @Override
    public void destroy() throws Exception {
        workerPool.drainAndHalt();
        asyncPool.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        asyncPool = new ThreadPoolExecutor(cpuCount*2, cpuCount*2,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("es-log-sender", true), new ThreadPoolExecutor.DiscardOldestPolicy());
        ringBuffer = RingBuffer.create(ProducerType.MULTI, new LogDocumentIndexFactory(), cpuCount*4 , new SleepingWaitStrategy(15));
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        LogDocumentEventHandler[] handlers = new LogDocumentEventHandler[cpuCount];
        for (Integer i = 0; i < cpuCount; i++) {
            handlers[i] = new LogDocumentEventHandler(client, threshsoldSecond, threshsoldSize);
        }

        workerPool = new WorkerPool(
                ringBuffer, sequenceBarrier, new LogEventExceptionHandler(), handlers
        );
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        producerSize = getProducerSize((cpuCount>>1));
        publisher = new LogDocumentPublisher[producerSize];
        for (Integer i = 0; i < producerSize; i++) {
            publisher[i] = new LogDocumentPublisher(ringBuffer);
        }
        workerPool.start(asyncPool);
    }

    static class LogEventExceptionHandler implements ExceptionHandler<IndexRequest> {

        @Override
        public void handleEventException(Throwable ex, long sequence, IndexRequest event) {
            log.error("处理异常",ex);
        }

        @Override
        public void handleOnStartException(Throwable ex) {

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {

        }
    }

    public static int getProducerSize(int size) {
        int n = size - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 32) ? 32 : n + 1;
    }
}
