package com.midea.cloud.srm.model.inq.price.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/10/13 9:44
 * 创建合同方式
 */
@Getter
@AllArgsConstructor
public enum ContractAgreement {
    PACKAGINGSIGNING("PACKAGINGSIGNING", "统谈统签"),
    SEPERATELYSIGNING("SEPERATELYSIGNING", "统谈分签");
    private String code;
    private String mean;
}
