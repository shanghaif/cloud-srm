package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商价格信息
 * //todo 按品类金额分配时，按品类分组，供应商 折息价多行总价
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorPriceInfo implements Serializable {

    private VendorInfo  vendorInfo;     // 供应商信息
    private String        buCode;           // 事业部编码
    private BigDecimal  discountPrice;  // 折息价
    private Integer     rank;           // 排名

}
