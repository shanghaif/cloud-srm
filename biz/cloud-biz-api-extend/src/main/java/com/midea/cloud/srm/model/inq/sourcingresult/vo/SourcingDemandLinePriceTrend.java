package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import com.midea.cloud.srm.model.inq.quota.vo.VendorInfo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * 需求行价格趋势
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourcingDemandLinePriceTrend implements Serializable {

    private Long        itemId;             // 物料ID
    private String      itemCode;           // 物料编码
    private String      itemName;           // 物料名称
    private BigDecimal  taxPrice;           // 含税单价
    private Date        creationDate;       // 创建日期

    private Collection<VendorInfo>  vendorInfos;    // 供应商信息
}
