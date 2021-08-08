package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 寻源结果需求行报表
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourcingResultDemandLineReport implements Serializable {

    private Long demandLineId;   // 寻源需求行ID
    private Long biddingId;      // 招标单ID

    private Long orgId;          // 组织ID
    private String orgCode;        // 组织编码
    private String orgName;        // 组织名称

    private Long ouGroupId;      // OU组ID
    private String ouGroupCode;    // OU组编码
    private String ouGroupName;    // OU组名称

    private Long itemId;         // 物料ID
    private String itemCode;       // 物料编码
    private String itemName;       // 物料名称

    private Long categoryId;     // 品类ID
    private String categoryCode;   // 品类编码
    private String categoryName;   // 品类名称

    private String groupKey;           // 组合Key
    private BigDecimal groupQuantityRatio; // 组合配比

    private BigDecimal demandQuantity;     // 需求数量
    private BigDecimal historyTaxPrice;    //历史含税价

    // 寻源结果供应商投标行报表
    private Collection<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports;

    // 寻源结果[中标]供应商投标行报表（有序 & 排名）
    List<SourcingResultVendorBiddingLineReport> winningVendorBiddingLineReports;

    // 需求行价格趋势（有序）
    List<SourcingDemandLinePriceTrend> sourcingDemandLinePriceTrends;


    /**
     * 创建 寻源结果需求行报表
     *
     * @param demandLine                    寻源需求行
     * @param vendorBiddingLineReports      寻源结果供应商投标行报表
     * @param sourcingDemandLinePriceTrends 需求行价格趋势
     * @return 寻源结果需求行报表
     */
    public static SourcingResultDemandLineReport create(SourcingDemandLine demandLine,
                                                        Collection<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports,
                                                        List<SourcingDemandLinePriceTrend> sourcingDemandLinePriceTrends) {
        SourcingResultDemandLineReport report = new SourcingResultDemandLineReport();
        BeanUtils.copyProperties(demandLine, report);
        report.setDemandLineId(demandLine.getId());
        report.setVendorBiddingLineReports(vendorBiddingLineReports);
        report.setWinningVendorBiddingLineReports(
                vendorBiddingLineReports.stream()
//                        .filter(SourcingResultVendorBiddingLineReport::isWinning)
                        .map(SourcingResultVendorBiddingLineReport::clone)
                        .sorted((o1, o2) -> {
                            if (Objects.nonNull(o1.getRank()) && Objects.nonNull(o2.getRank())) {
                               return o1.getRank()>o2.getRank()?1:o1.getRank().equals(o2.getRank())?0:-1;
                            }
                            ;
                            if (Objects.isNull(o1.getRank())) {
                                return -1;
                            }
                            if (Objects.isNull(o2.getRank())) {
                                return 1;
                            }
                            return 0;
                        })
                        .collect(Collectors.toList())
        );
        report.setSourcingDemandLinePriceTrends(sourcingDemandLinePriceTrends);
        return report;
    }
}
