package com.midea.cloud.common.enums.pm.po;

/**
 * <pre>
 * 采购需求申请状态 字典码:APPLICATION_STATUS
 * </pre>
 *
 * @author chenwj92@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 19:15:20
 *  修改内容:
 * </pre>
 */
public enum CeeaOrderAttachTypeEnum {
    APPROVAL("审批附件信息","APPROVAL"),
    ORDER("订单附件信息","ORDER");

    private String name;
    private String value;

    private CeeaOrderAttachTypeEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    private static CeeaOrderAttachTypeEnum get(String value){
        for(CeeaOrderAttachTypeEnum orderAttachType: CeeaOrderAttachTypeEnum.values()){
            if(orderAttachType.value.equals(value)){
                return orderAttachType;
            }
        }
        return null;
    }

}
