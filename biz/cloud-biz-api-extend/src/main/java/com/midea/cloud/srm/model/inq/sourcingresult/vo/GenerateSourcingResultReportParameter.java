package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 入参 - 寻源报表生成
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateSourcingResultReportParameter implements Serializable {

    // 寻源单ID
    private Long sourcingFormId;

    //寻源单号
    private String sourcingFormNo;
    //价格审批单id
    private Long inquiryId;

    // 寻源需求行集
    private Collection<SourcingDemandLine> demandLines;

    // 最后一轮的供应商报价行集
    private Collection<VendorBiddingLine> lastRoundVendorBiddingLines;
    //报表生成错误信息
    private String failMsg;
}
