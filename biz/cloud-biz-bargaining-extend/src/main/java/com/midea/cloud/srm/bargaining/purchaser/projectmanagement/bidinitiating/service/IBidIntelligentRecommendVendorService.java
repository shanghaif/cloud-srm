package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.FindVendorPricingPermissionParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;

import java.util.Collection;
import java.util.List;

/**
 * 供应商智能推荐服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IBidIntelligentRecommendVendorService {

    /**
     * 获取 智能推荐供应商集
     *
     * @param parameter 查询参数
     * @return 智能推荐供应商集
     */
    List<CompanyInfo> findVendors(FindVendorPricingPermissionParameter parameter);

    /**
     * 获取 供应商的初始报价权限集
     *
     * @param parameter 查询参数
     * @return 智能推荐供应商集
     */
    List<VendorPricingPermission> findInitialVendorPricingPermissions(FindVendorPricingPermissionParameter parameter);

    /**
     * 根据模板data返回供应商集合
     * @param form
     * @return
     */
    List<CompanyInfo> findVendors(SourceForm form);

    List<VendorPricingPermission> getVendorPricingPermissions(Collection<BidRequirementLine> demandLines, Long[] vendorIds, String bidingAwardWay);

}
