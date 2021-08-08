package com.midea.cloud.log.bind;

import com.midea.cloud.log.autoconfigure.LogAutoRegistar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11
 *  修改日期: 2020/12/18
 *  修改内容:
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogAutoRegistar.class)
public @interface EnableOpLog {
    /**
     * 采用es收集
     * @return
     */
    boolean enableEs() default true;

    /**
     * 采用mysql收集
     * @return
     */
    boolean enableMysql() default false;

    /**
     * 是否启用操作记录文档记录(mysql)
     * @return
     */
    boolean enableBizLog() default false;

    /**
     * 是否开启定时任务日志记录
     * @return
     */
    boolean enableJobRecord() default true;


}
