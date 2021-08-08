package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 寻源结果供应商投标行报表
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
public class SourcingResultVendorBiddingLineReport implements Serializable, Cloneable {

    private Long        demandLineId;       // 寻源需求行ID

    private Long        bidVendorId;        // 投标供应商ID
    private Long        vendorId;           // 供应商ID
    private Long        biddingId;          // 招标单ID

    private boolean     isWinning;          // 是否中标

    private String      vendorCode;         // 供应商编码
    private String      vendorName;         // 供应商名称
    private String      vendorStatus;       // 供应商状态

    private BigDecimal  priceScore;         // 价格得分
    private BigDecimal  technologyScore;    // 技术得分
    private BigDecimal  performanceScore;   // 绩效得分
    private BigDecimal  compositeScore;     // 绩效得分
    private Integer     rank;               // 排名

    private BigDecimal	quotaQuantity;		// 配额数量
    private BigDecimal	quotaRatio;			// 配额比例
    private String      leadTime;           // 供货周期
    private String		taxKey;             // 税率KEY
    private BigDecimal	taxRate;            // 税率
    private Integer 	warrantyPeriod;		// 保修期
    private Date        deliverDate;	    // 承诺交货期

    private Collection<VendorBiddingLinePaymentTerm>    paymentTerms; //付款条款

    private BigDecimal  quotePrice;         // 供应商含税报价
    private BigDecimal  discountPrice;      // 折息价


    @Override
    public SourcingResultVendorBiddingLineReport clone() {
        try {
            SourcingResultVendorBiddingLineReport clone = (SourcingResultVendorBiddingLineReport) super.clone();
            BeanUtils.copyProperties(this, clone);
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     * 创建 寻源结果供应商投标行报表
     *
     * @param biddingLine 供应商投标行
     * @param demandLine  寻源需求行
     * @return 寻源结果供应商投标行报表
     */
    public static SourcingResultVendorBiddingLineReport create(VendorBiddingLine biddingLine,
                                                               SourcingDemandLine demandLine) {
        SourcingResultVendorBiddingLineReport report = new SourcingResultVendorBiddingLineReport();
        BeanUtils.copyProperties(biddingLine, report);
        List<VendorBiddingLinePaymentTerm> list = biddingLine.getPaymentTerms().stream().map(e -> {
            VendorBiddingLinePaymentTerm term = VendorBiddingLinePaymentTerm.builder()
                    .paymentDay(e.getPaymentDay())
                    .paymentDayCode(e.getPaymentDayCode())
                    .paymentTerm(e.getPaymentTerm())
                    .paymentWay(e.getPaymentWay())
                    .build();
            return term;
        }).collect(Collectors.toList());
        //中标金额
        if(Objects.nonNull(report.getQuotaQuantity())&&Objects.nonNull(report.getQuotePrice())&biddingLine.isWinning()){
            report.setDiscountPrice(report.getQuotePrice().multiply(report.getQuotaQuantity()));
        }else{
            report.setDiscountPrice(null);
        }
        //状态
        report.setVendorStatus(CategoryStatus.getMeanByValue(report.getVendorStatus()));
        report.setPaymentTerms(list);
        return report;
    }
    @Getter
    @AllArgsConstructor
    public enum CategoryStatus {

        APPLICATION("APPLICATION","申请中"),        //申请中
        QUALIFIED("QUALIFIED","合格"),          //合格
        SUSPEND("SUSPEND","暂停"),            //暂停
        TERMINATION("TERMINATION","终止"),        //终止

        //ceea
        VERIFY("VERIFY","认证中"),//认证中
        YELLOW("YELLOW","黄牌"),//黄牌
        RED("RED","红牌"),//红牌
        ONE_TIME("ONE_TIME","一次性"),//一次性
        REGISTERED("REGISTERED","注册"),//注册
        BLACK("BLACK","黑牌"),//黑牌
        GREEN("GREEN","绿牌");//绿牌
        private final String value;
        private final String mean;

        public static String getMeanByValue(String value){
            for (CategoryStatus categoryStatus : values()) {
                if(Objects.equals(categoryStatus.getValue(),value)){
                    return categoryStatus.getMean();
                }
            }
            return "状态未知";
        }
    }
}
