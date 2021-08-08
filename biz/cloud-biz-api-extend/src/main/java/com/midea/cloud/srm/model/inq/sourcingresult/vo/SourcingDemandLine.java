package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 寻源需求行
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SourcingDemandLine implements Serializable {

    private Long        id;             // 行ID
    private Long        biddingId;      // 寻源招标单ID

    private Long        orgId;          // 组织ID
    private String      orgCode;        // 组织编码
    private String      orgName;        // 组织名称

    private Long        ouGroupId;      // OU组ID
    private String      ouGroupCode;    // OU组编码
    private String      ouGroupName;    // OU组名称

    private Long        itemId;         // 物料ID
    private String      itemCode;       // 物料编码
    private String      itemName;       // 物料名称

    private Long        categoryId;     // 品类ID
    private String      categoryCode;   // 品类编码
    private String      categoryName;   // 品类名称

    private String      groupKey;           // 组合Key
    private BigDecimal  groupQuantityRatio; // 组合配比

    private BigDecimal  demandQuantity;     // 需求数量

    private BigDecimal  historyTaxPrice;    // 历史含税价格

}
