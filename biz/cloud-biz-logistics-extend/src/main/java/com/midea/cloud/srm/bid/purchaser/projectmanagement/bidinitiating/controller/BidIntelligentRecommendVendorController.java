package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidIntelligentRecommendVendorService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.FindVendorPricingPermissionParameter;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 招标供应商智能推荐
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/inviteVendor/intelligentRecommendVendor")
public class BidIntelligentRecommendVendorController extends BaseController {

    @Resource
    private IBidIntelligentRecommendVendorService   bidIntelligentRecommendVendorService;


    @PostMapping("/findVendors")
    public List<CompanyInfo> findVendors(@RequestBody FindVendorPricingPermissionParameter parameter) {
        return bidIntelligentRecommendVendorService.findVendors(parameter);
    }

    @PostMapping("/findInitialVendorPricingPermissions")
    public List<VendorPricingPermission> findInitialVendorPricingPermissions(@RequestBody FindVendorPricingPermissionParameter parameter) {
        return bidIntelligentRecommendVendorService.findInitialVendorPricingPermissions(parameter);
    }
}
