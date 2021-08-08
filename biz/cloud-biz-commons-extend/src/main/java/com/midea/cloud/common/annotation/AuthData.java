package com.midea.cloud.common.annotation;

import com.midea.cloud.common.enums.rbac.MenuEnum;

import java.lang.annotation.*;


/**
 * <pre>
 *  数据权限控制标识符，仅显示自身创建的数据
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
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthData {

    MenuEnum[] module() ;

}