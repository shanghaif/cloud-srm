package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author tanjl11
 * @date 2020/10/21 13:27
 * 对应了一个评选行信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinVendorInfoDto {
    //公司id,对应了寻源模块的bidVendor表里面的vendorId
    private Long companyId;
    //提示信息用
    private String companyName;
    //配额
    private BigDecimal quota;
    //折息价格
    private BigDecimal discountPrice;
    //预估返利
    private Integer proportion;
    //下限配额
    private BigDecimal minQuota;
    //需求数量
    private Double needCount;
    //折息价格*（1-预估返利）||总金额
    private BigDecimal tempPrice;
    //配额上限
    private BigDecimal maxQuota;
    //回传用于赋值
    private Long orderLineId;
    //按品类时赋值,orderLine,needCount
    private Map<Long,Double> lineCountMap;
}
