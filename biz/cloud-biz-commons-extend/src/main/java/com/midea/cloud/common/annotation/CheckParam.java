package com.midea.cloud.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})  //作用的位置
@Retention(RetentionPolicy.RUNTIME) //作用域
@Documented
public @interface CheckParam {
}
