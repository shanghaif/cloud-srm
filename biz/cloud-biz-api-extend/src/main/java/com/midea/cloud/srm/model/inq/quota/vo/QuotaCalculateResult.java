package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 出参 - 配额计算结果
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuotaCalculateResult implements Serializable {

    private Long                            categoryId;             // 品类ID
    private Collection<VendorQuotaInfo>     vendorQuotaInfo;        // 供应商配额信息
}
