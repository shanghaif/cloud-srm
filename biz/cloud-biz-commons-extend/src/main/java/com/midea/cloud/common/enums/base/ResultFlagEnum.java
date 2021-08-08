package com.midea.cloud.common.enums.base;

import lombok.Getter;

/**
 * <pre>
 *  反馈结果标识
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/19 19:17
 *  修改内容:
 * </pre>
 */
@Getter
public enum ResultFlagEnum {
    Y("已反馈","Y"),
    N("未反馈","N"),
    EXPIRED("已过期","EXPIRED");

    private String flag;
    private String value;


    ResultFlagEnum(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }
}
