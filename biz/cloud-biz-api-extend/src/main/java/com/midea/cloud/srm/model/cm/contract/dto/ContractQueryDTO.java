package com.midea.cloud.srm.model.cm.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tanjl11
 * @date 2020/11/28 17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractQueryDTO {

    private String materialCode;
    private String invCode;
    private String orgCode;

}
