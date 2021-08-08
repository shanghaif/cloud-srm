package com.midea.cloud.srm.model.base.ou.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


/**
 * @author tanjl11
 * @date 2020/10/05 11:06
 */
@Builder
@Getter
public class BaseOuBuInfoVO {
    private Long organizationId;
    private String divisionId;
    private String division;
    private String organizationName ;
}
