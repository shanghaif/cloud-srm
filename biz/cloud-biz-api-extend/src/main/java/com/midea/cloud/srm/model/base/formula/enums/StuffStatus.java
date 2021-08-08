package com.midea.cloud.srm.model.base.formula.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/26 22:48
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum StuffStatus {
    INVAILD("INVAILD", "失效"),
    DRAFT("DRAFT", "新建"),
    ACTIVE("ACTIVE", "生效"),
    ABANDON("ABANDON","废弃");
    private String status;
    private String value;

    public static String getValueByStatus(String status){
        for (StuffStatus value : StuffStatus.values()) {
            if(value.getStatus().equals(status)){
                return value.getValue();
            }
        }
        return "非法状态";
    }
}
