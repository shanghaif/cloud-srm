package com.midea.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *  缓存注解
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
public @interface CacheData {
    String keyName(); // 缓存唯一KEY
    long cacheTime() default 3600; // 缓存时间,默认1个小时
    String interfaceName() default ""; // 接口名称
    String remark() default ""; // 备注

    /**
     * 自定义参数序列化, 默认全部参数序列化, 数组填写从0开始
     * 例如: 方法有3个参数, Function(String param1,String param2,String param3)
     *       现在只想把第一和第三个参数作为KEY, 那么 paramIndex = {0,2}
     * @return
     */
    int[] paramIndex() default {};

    /**
     * 是否开启多语言
     * @return
     */
    boolean i18n() default false;
}
