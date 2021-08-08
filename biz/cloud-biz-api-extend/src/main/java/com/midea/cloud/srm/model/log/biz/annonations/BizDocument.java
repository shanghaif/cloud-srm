package com.midea.cloud.srm.model.log.biz.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/6 11:38
 *  修改内容:
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BizDocument {
    //业务单据名
    String bizDocumentName();
    //所属菜单
    String tab() default "";


}
