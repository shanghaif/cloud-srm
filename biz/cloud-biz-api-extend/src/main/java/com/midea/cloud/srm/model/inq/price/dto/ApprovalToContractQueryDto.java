package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

/**
 * @author tanjl11
 * @date 2020/10/13 10:42
 */
@Data
public class ApprovalToContractQueryDto {
    private Long approvalHeaderId;
    private String vendorId;
    private String itemId;
    private Long orgId;
    private String type;
}
