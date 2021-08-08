package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 入参 - 配额计算
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuotaCalculateParameter implements Serializable {

    private Long                            categoryId;             // 品类ID
    private List<VendorPriceInfo> vendorPriceInfo;        // 供应商价格信息

    private BigDecimal              minDiscountPrice;               // 最低折息价
    private Collection<VendorInfo>  minDiscountPriceVendorInfo;     // 最低折息价的供应商信息集
    //private Integer groupId;
}
