package com.midea.cloud.srm.model.log.biz.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  业务单据号
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BizDocumentNo {
}
