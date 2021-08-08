package com.midea.cloud.srm.model.annonations;

import java.lang.annotation.*;

/**
 * <pre>
 * 绑定导入参数信息
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
@Target({ElementType.FIELD})  //作用的位置
@Retention(RetentionPolicy.RUNTIME) //作用域
@Documented
public @interface ExcelParamCheck {

    /**
     * 是否必填,默认为否
     * @return
     */
    boolean ifRequired() default false;
    /**
     * 必填为空时报错提示
     * @return
     */
    String errorMsg() default "参数不能为空";

    /**
     * 字典值
     * @return
     */
    String dicCode() default "";

    /**
     * 字典编码报错提示
     * @return
     */
    String dicErrorMsg() default "字典编码不存在";

    /**
     * 填写格式错误提示
     * @return
     */
    String formatErrorMsg() default "[字段]填写格式错误";

    /**
     * 是否为币种, 默认否
     * @return
     */
    boolean isCurrency() default false;

    /**
     * 币种是否填名字, 默认为是
     * @return
     */
    boolean isCurrencyName() default true;

    /**
     * 币种填写错误提示
     * @return
     */
    String currencyErrorMsg() default "[币种]填写错误";
}
