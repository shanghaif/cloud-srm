package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * VO for {@link CompanyInfo} and {@link BidRequirementLine}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorPricingPermission implements Serializable {

    private CompanyInfo                 companyInfo;        // 供应商信息
    private List<BidRequirementLine>    requirementLines;   // 有报价权限的需求行
}
