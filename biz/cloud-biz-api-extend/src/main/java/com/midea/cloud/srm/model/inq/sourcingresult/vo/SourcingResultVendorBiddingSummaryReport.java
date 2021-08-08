package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 寻源结果供应商投标总结报表
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourcingResultVendorBiddingSummaryReport implements Serializable {

    private Long bidVendorId;            // 投标供应商ID
    private Long vendorId;               // 供应商ID
    private Long biddingId;              // 招标单ID

    private BigDecimal biddingAmount;          // 投标总金额
    private BigDecimal winningBiddingAmount;   // 中标总金额

    // 寻源结果供应商投标行报表
    private Collection<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports;


    /**
     * 创建 寻源结果供应商投标总结报表
     *
     * @param vendorBiddingLineReports 寻源结果供应商投标行报表
     * @return 寻源结果需求行报表
     */
    public static SourcingResultVendorBiddingSummaryReport create(Collection<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports, Map<Long, SourcingDemandLine> winningDemandLines) {

        Long bidVendorId = vendorBiddingLineReports.stream()
                .findAny()
                .map(SourcingResultVendorBiddingLineReport::getBidVendorId)
                .orElseThrow(() -> new ApiException("获取投标供应商ID失败。"));
        Long vendorId = vendorBiddingLineReports.stream()
                .findAny()
                .map(SourcingResultVendorBiddingLineReport::getVendorId)
                .orElseThrow(() -> new ApiException("获取供应商ID失败。"));
        Long biddingId = vendorBiddingLineReports.stream()
                .findAny()
                .map(SourcingResultVendorBiddingLineReport::getBiddingId)
                .orElseThrow(() -> new ApiException("获取供应商ID失败。"));

        vendorBiddingLineReports.forEach(report -> {
            report.setBidVendorId(bidVendorId);
            report.setVendorId(vendorId);
            report.setBiddingId(biddingId);
        });

        // 投标总金额
        BigDecimal biddingAmount=null;
        // 中标总金额
        BigDecimal winningAmount=null;
        for (SourcingResultVendorBiddingLineReport report : vendorBiddingLineReports) {
            //含税报价
            if(Objects.nonNull(report.getQuotePrice())){
                SourcingDemandLine temp = winningDemandLines.get(report.getDemandLineId());
                if(Objects.nonNull(temp)&&Objects.nonNull(temp.getDemandQuantity())){
                    BigDecimal multiply = temp.getDemandQuantity().multiply(report.getQuotePrice());
                    if(biddingAmount==null){
                        biddingAmount=multiply;
                    }else{
                        biddingAmount=biddingAmount.add(multiply);
                    }
                }
            }
            //改为配额数量*报价
            if(Objects.nonNull(report.getQuotePrice())){
                if(Objects.nonNull(report.getQuotaQuantity())){
                    BigDecimal winAmount = report.getQuotaQuantity().multiply(report.getQuotePrice());
                    if(winningAmount==null){
                        winningAmount=winAmount;
                    }else{
                        winningAmount=winningAmount.add(winAmount);
                    }
                }
            }
        }
        return SourcingResultVendorBiddingSummaryReport.builder()
                .bidVendorId(bidVendorId)
                .vendorId(vendorId)
                .biddingId(biddingId)
                .biddingAmount(biddingAmount)
                .winningBiddingAmount(winningAmount)
                .vendorBiddingLineReports(vendorBiddingLineReports)
                .build();
    }
}
