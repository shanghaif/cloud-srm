package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class BidOrderLineTemplateTempReportVO {

    private String vendorName;

    private String headerName;

    private String lineName;

    private BigDecimal taxPrice;

}
