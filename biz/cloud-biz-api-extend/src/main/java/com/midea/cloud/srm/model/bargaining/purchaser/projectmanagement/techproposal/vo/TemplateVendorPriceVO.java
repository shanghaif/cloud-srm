package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TemplateVendorPriceVO {
    private String vendorName;
    private BigDecimal taxPrice;
}
