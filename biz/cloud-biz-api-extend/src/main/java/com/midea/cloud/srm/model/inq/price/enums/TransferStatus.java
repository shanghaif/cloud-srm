package com.midea.cloud.srm.model.inq.price.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tanjl11
 * @date 2020/10/13 19:23
 */
@Getter
@AllArgsConstructor
public enum TransferStatus {
    CHANGE("CHANGE", "转换合同中"),
    FAIL("FAIL","转换合同失败"),
    FINISH("FINISH", "无可生成合同明细的行"),
    CONTINUE("CONTINUE","仍有可用审批行");
    private String code;
    private String mean;

    public static String getMeanByCode(String code) {
        for (TransferStatus value : values()) {
            if(Objects.equals(value.getCode(),code)){
                return value.getMean();
            }
        }
        return "未知状态";
    }
}
