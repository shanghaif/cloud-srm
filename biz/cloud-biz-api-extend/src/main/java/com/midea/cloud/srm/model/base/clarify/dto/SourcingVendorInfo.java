package com.midea.cloud.srm.model.base.clarify.dto;

import lombok.Data;

/**
 * @author tanjl11
 * @date 2020/10/08 16:48
 * 返回的参数
 */
@Data
public class SourcingVendorInfo {
    private Long vendorId;
    private String vendorName;
    private String companyName;
    private Long companyId;
    private String companyCode;
}
