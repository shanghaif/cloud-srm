package com.midea.cloud.component.interceptor;

import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Properties;

@Configuration
@ConditionalOnClass({RedisUtil.class, SqlSessionFactory.class, RbacClient.class, BaseClient.class})
public class MybatisInterceptorConfig {

    @Bean
    public String myInterceptor() {
        SqlSessionFactory sqlSessionFactory = null;
        RedisTemplate redisTemplate;
        try {
            sqlSessionFactory = SpringContextHolder.getApplicationContext().getBean(SqlSessionFactory.class);
            SpringContextHolder.getApplicationContext().getBean(RedisUtil.class);
            SpringContextHolder.getApplicationContext().getBean(BaseClient.class);
            SpringContextHolder.getApplicationContext().getBean(RbacClient.class);
            SpringContextHolder.getApplicationContext().getBean(PmClient.class);
            SpringContextHolder.getApplicationContext().getBean(SqlSessionFactory.class);
            redisTemplate = (RedisTemplate) SpringContextHolder.getApplicationContext().getBean("stringRedisTemplate");
        } catch (Exception e) {
            return null;
        }
        DataPermissionInterceptor executorInterceptor = new DataPermissionInterceptor();
        UserInfoConvertInterceptor userInfoConvertInterceptor = new UserInfoConvertInterceptor();
        userInfoConvertInterceptor.setHashOperations(redisTemplate.opsForHash());
        Properties properties = new Properties();
        properties.setProperty("prop1", "value1");
        executorInterceptor.setProperties(properties);
        SchemaReplaceInterceptor schemaReplaceInterceptor = new SchemaReplaceInterceptor();
        sqlSessionFactory.getConfiguration().addInterceptor(schemaReplaceInterceptor);
        sqlSessionFactory.getConfiguration().addInterceptor(executorInterceptor);
        sqlSessionFactory.getConfiguration().addInterceptor(userInfoConvertInterceptor);
        return "interceptor";
    }

    @Bean
    public String userInfoConvertInterceptor() {
        SqlSessionFactory sqlSessionFactory;
        RedisTemplate redisTemplate;
        try {
            sqlSessionFactory = SpringContextHolder.getApplicationContext().getBean(SqlSessionFactory.class);
            redisTemplate = (RedisTemplate) SpringContextHolder.getApplicationContext().getBean("stringRedisTemplate");
        } catch (Exception e) {
            return null;
        }
        UserInfoConvertInterceptor userInfoConvertInterceptor = new UserInfoConvertInterceptor();
        userInfoConvertInterceptor.setHashOperations(redisTemplate.opsForHash());
        sqlSessionFactory.getConfiguration().addInterceptor(userInfoConvertInterceptor);
        return "userInfoConvertInterceptor";
    }

}
