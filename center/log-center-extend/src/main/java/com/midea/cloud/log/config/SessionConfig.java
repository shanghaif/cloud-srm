package com.midea.cloud.log.config;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookiePath("/");
        return cookieSerializer;
    }

    /**
     * 用于spring session，防止每次创建一个线程
     * @return
     */
    @Bean(name = "springSessionRedisTaskExecutor")
    public ThreadPoolTaskExecutor springSessionRedisTaskExecutor(){
        ThreadPoolTaskExecutor springSessionRedisTaskExecutor = new ThreadPoolTaskExecutor();
        springSessionRedisTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        springSessionRedisTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        springSessionRedisTaskExecutor.setKeepAliveSeconds(10);
        springSessionRedisTaskExecutor.setQueueCapacity(1024);
        springSessionRedisTaskExecutor.setThreadNamePrefix("Spring session redis executor thread: ");
        return springSessionRedisTaskExecutor;
    }

}
