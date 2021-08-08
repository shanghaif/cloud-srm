package com.midea.cloud.common.annotation;

import com.midea.cloud.common.utils.LogWriteBack;

import java.lang.annotation.*;

/**
 * <pre>
 * 接口日志记录注解
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
    /**
     * 接口名称
     */
    String serviceName();
    /**
     * 接口类型(HTTP,WEBSERVICE)
     * 默认HTTP
     */
    String serviceType() default "HTTP";
    /**
     * 传输类型(RECEIVE:接收，SEND:发送)
     */
    String type() default "SEND";

    /**
     * 单据类型
     */
    String billType() default "";

    /**
     * 目标系统
     */
    String targetSys() default "";

    /**
     * 访问地址
     */
    String url() default "";

    /**
     * 回调检查结果方法
     * @return
     */
    Class<? extends LogWriteBack> aClass();
}
