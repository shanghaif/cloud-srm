package com.midea.cloud.srm.model.inq.quota.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/21 13:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotaCalculateParam {
    //需求行id
    private Long requirementId;
    //品类id
    private Long categoryId;
    //品类名称,提示信息用
    private String categoryName;
    //计算类型
    private String calculateType;
    //对应的事业部
    private String buCode;
    //中标的供应商
    List<WinVendorInfoDto> winVendorInfoDtoList;
    //剩余配额
    private BigDecimal leftQuota;
    //最低折息价供应商
    WinVendorInfoDto minDiscountVendorInfo;
}
