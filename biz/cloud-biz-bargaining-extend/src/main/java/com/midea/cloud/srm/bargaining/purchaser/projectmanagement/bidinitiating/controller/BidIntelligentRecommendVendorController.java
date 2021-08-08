package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidIntelligentRecommendVendorService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.FindVendorPricingPermissionParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/findVendorsByTemplate")
    public List<CompanyInfo> findVendorsByTemplate(@RequestBody SourceForm sourceForm){
        return bidIntelligentRecommendVendorService.findVendors(sourceForm);
    }
    @PostMapping("/getVendorPricingPermissionsByTemplateId")
    public List<VendorPricingPermission> getVendorPricingPermissionsByTemplateId(@RequestBody Map<String,Object> map) {
        SourceForm sourceForm = JSON.toJavaObject((JSONObject)map.get("sourceForm"), SourceForm.class);
        Long vendorId=(Long)map.get("vendorId");
        return bidIntelligentRecommendVendorService.getVendorPricingPermissions(sourceForm.getDemandLines(),new Long[]{vendorId},sourceForm.getBidding().getBidingAwardWay());
    }

}
