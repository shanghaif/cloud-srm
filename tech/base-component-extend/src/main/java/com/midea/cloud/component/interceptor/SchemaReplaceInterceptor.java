package com.midea.cloud.component.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;


/**
 * <pre>
 * 将cloud_前缀的schema替换成nsrm_cloud_前缀
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/14 16:12
 *  修改内容:
 * </pre>
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
@Slf4j
public class SchemaReplaceInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String processSql = ExecutorPluginUtils.getSqlByInvocation(invocation);
        log.debug("schema替换前：{}", processSql);
        processSql = processSql.replaceAll("cloud_(?=.*\\.)", "nsrm_cloud_");
        log.debug("schema替换后：{}", processSql);
        ExecutorPluginUtils.resetSql2Invocation(invocation, processSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
