package com.midea.cloud.srm.logistics.config;

import com.midea.cloud.common.utils.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tanjl11
 * @date 2020/10/12 10:23
 */
@Configuration
public class ThreadPoolConfig {
    /**
     * 用于发送请求的线程池
     * @return
     */
    @Bean("requestExecutors")
    public ThreadPoolExecutor executor() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor httpClientExecutor = new ThreadPoolExecutor(cpuCount * 2, cpuCount * 2,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new NamedThreadFactory("TMS-message-sender", true), new ThreadPoolExecutor.CallerRunsPolicy());
        return httpClientExecutor;
    }

}
