package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.*;

import java.io.Serializable;

/**
 * 供应商付款条件
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class VendorBiddingLinePaymentTerm implements Serializable {

    private Long        id;                 // 行ID
    private Long        biddingLineId;      // 供应商报价行ID

    private Long        bidVendorId;        // 投标供应商ID
    private Long        vendorId;           // 供应商ID
    private Long        biddingId;          // 招标单ID

    private String      paymentTerm;    // 付款条件名称
    private String      paymentWay;         // 付款方式
    private Integer     paymentDay;         // 账期值
    private String      paymentDayCode;     // 账期编码
}
