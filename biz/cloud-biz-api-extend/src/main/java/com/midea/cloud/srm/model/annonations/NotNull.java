package com.midea.cloud.srm.model.annonations;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})  //作用的位置
@Retention(RetentionPolicy.RUNTIME) //作用域
@Documented
public @interface NotNull {
    String value() default "{报错信息}";
}
