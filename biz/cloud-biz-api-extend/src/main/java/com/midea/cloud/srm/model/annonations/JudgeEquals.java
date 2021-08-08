package com.midea.cloud.srm.model.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tanjl11
 * @date 2020/10/28 16:39
 * 标记在需要对比的字段上面
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JudgeEquals {
}
