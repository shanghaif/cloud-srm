package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanjl11
 */
@Data
public class BidOrderLineTemplateReportLineVO {
    /**
     * 费用项名
     */
    private String headerName;
    /**
     * 明细名
     */
    private String lineName;
    /**
     * 含税总价
     */
    private List<TemplateVendorPriceVO> price;

}
