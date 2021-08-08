package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * 寻源结果报表
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourcingResultReport implements Serializable {

    public final static SourcingResultReport EMPTY_REPORT = SourcingResultReport.builder()
            .demandLineReports(Collections.emptyList())
            .build();


    // 寻源结果需求行报表
    private Collection<SourcingResultDemandLineReport>  demandLineReports;

    // 寻源结果供应商投标总结报表
    private Collection<SourcingResultVendorBiddingSummaryReport> vendorBiddingSummaryReports;
}
