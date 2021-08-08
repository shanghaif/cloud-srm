package com.midea.cloud.common.enums.pm.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *    bpm回调接口
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-20
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum StatusForBpmEnum {
    //Y已审批、N驳回、C撤回
    Y("Y","APPROVED"),
    N("N","REJECTED"),
    C("C","WITHDRAW");

    private String code;
    private String value;

    public static String getValueByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (StatusForBpmEnum bpmEnum:StatusForBpmEnum.values()){
            if(code.equals(bpmEnum.getCode())){
                return bpmEnum.getValue();
            }
        }
        return null;
    }

}
