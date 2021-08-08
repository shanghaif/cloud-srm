package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 供应商信息
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorInfo implements Serializable {

    private Long        vendorId;       // 供应商ID
    private String      vendorCode;     // 供应商编码
    private String      vendorName;     // 供应商名称

}
