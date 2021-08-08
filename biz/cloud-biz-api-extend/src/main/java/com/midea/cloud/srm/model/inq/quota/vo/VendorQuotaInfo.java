package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商配额信息
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorQuotaInfo implements Serializable {

    private VendorInfo  vendorInfo;     // 供应商信息
    private String        buCode;            // 事业部ID
    private Integer  quotaRatio;     // 配额比例

}
