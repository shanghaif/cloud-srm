package com.midea.cloud.log.aop.db;

import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.feign.log.LogClient;
import com.midea.cloud.srm.model.log.biz.annonations.BizDocument;
import com.midea.cloud.srm.model.log.biz.annonations.BizDocumentId;
import com.midea.cloud.srm.model.log.biz.annonations.BizDocumentNo;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/4 15:34
 *  修改内容:
 * </pre>
 */

//@Intercepts({
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
//                Object.class})})
//@Component
//@ConditionalOnExpression("${cloud.biz-log.enable:true}")
public class BizSqlInterceptor implements Interceptor, DisposableBean {
    private final ThreadPoolExecutor executor;
    private final Map<String, MutablePair<Field, Field>> clazzMap;
    private final LogClient logClient;

    public BizSqlInterceptor(LogClient logClient) {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor(cpuCount, cpuCount,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new NamedThreadFactory("sendBusinessLog", true), new ThreadPoolExecutor.CallerRunsPolicy());
        clazzMap = new ConcurrentHashMap<>();
        this.logClient = logClient;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        MappedStatement statement = (MappedStatement) objects[0];
        //判断是不是新增操作
        if (!statement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            return invocation.proceed();
        } else {
            Object target = objects[1];
            Class<?> bizClass = target.getClass();
            boolean isBizDocument = bizClass.isAnnotationPresent(BizDocument.class);
            //非单据实体，直接跳过
            if (!isBizDocument) {
                return invocation.proceed();
            }
            BizOperateLogInfo info = BizDocumentInfoHolder.get();
            //有的话就从里面拿值，并做缓存
            String clazzName = bizClass.getCanonicalName();
            MutablePair<Field, Field> fieldPair = clazzMap.get(clazzName);
            if (Objects.isNull(fieldPair)) {
                fieldPair = new MutablePair();
                Field tableId = null;
                for (Field field : bizClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(BizDocumentId.class)) {
                        fieldPair.setLeft(field);
                        continue;
                    }
                    if (field.isAnnotationPresent(BizDocumentNo.class)) {
                        fieldPair.setRight(field);
                        continue;
                    }
                    if (field.isAnnotationPresent(TableId.class)) {
                        tableId = field;
                        continue;
                    }
                    if (fieldPair.getLeft() != null && fieldPair.getRight() != null) {
                        break;
                    }
                }
                if (fieldPair.getLeft() == null) {
                    fieldPair.setLeft(tableId);
                }
                clazzMap.put(clazzName, fieldPair);
            }
            //对这两个属性进行获取值的操作，设置进对象里面
            Field bizDocumentIdField = fieldPair.getLeft();
            Field bizDocumentNoField = fieldPair.getRight();

            String bizDocumentNo = null;
            String bizDocumentId = null;
            try {
                if (bizDocumentNoField != null) {
                    bizDocumentNo = bizDocumentNoField.get(target).toString();
                    info.setBusinessNo(bizDocumentNo);
                }
                bizDocumentId = bizDocumentIdField.get(target).toString();
                info.setBusinessId(bizDocumentId);
            } catch (Exception e) {
                throw new BaseException(String.format("反射获取业务单据,class:%s,信息出错,请检查单据号注解或单据id注解是否存在", clazzName));
            }
            BizDocument annotation = bizClass.getAnnotation(BizDocument.class);
            info.setBusinessTab(annotation.tab());
            info.setOperateInfo(String.format("已创建%s,单据号为%s", annotation.bizDocumentName(), bizDocumentNo));
            info.setOperateType("已创建");
//            executor.submit(() -> logClient.saveBusinessLogFromInnerSystem(info));
        }
        return invocation.proceed();
    }

    @Override
    public void destroy() throws Exception {
        BizDocumentInfoHolder.clear();
        clazzMap.forEach((k, v) -> v = null);
        executor.shutdown();
    }
}
