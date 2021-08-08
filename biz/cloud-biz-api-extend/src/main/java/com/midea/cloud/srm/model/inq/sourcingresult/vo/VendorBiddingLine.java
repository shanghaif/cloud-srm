package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * 供应商投标行
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class VendorBiddingLine implements Serializable {

    private Long        id;                 // 行ID
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

    private BigDecimal  quotePrice;         // 供应商含税报价
    private BigDecimal  discountPrice;      // 折息价

    private Collection<VendorBiddingLinePaymentTerm>    paymentTerms;   // 供应商付款条件
}
