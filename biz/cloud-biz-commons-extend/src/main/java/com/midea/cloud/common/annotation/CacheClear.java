package com.midea.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *  清楚缓存注解
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 16:09:21
 *  修改内容:
 * </pre>
 */
@Target({ElementType.METHOD})  //作用的位置
@Retention(RetentionPolicy.RUNTIME) //作用域
@Documented
public @interface CacheClear {
    String[] keyName(); // 缓存唯一KEY
}
