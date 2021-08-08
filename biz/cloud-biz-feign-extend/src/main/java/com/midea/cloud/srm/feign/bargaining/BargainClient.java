package com.midea.cloud.srm.feign.bargaining;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author tanjl11
 * @date 2020/10/20 10:17
 */
@FeignClient(value = "cloud-biz-bargaining", contextId = "cloud-biz-bargaining-info")
public interface BargainClient {
    @PostMapping("/inviteVendor/intelligentRecommendVendor/findVendorsByTemplate")
    List<CompanyInfo> findVendorsByTemplate(@RequestBody SourceForm sourceForm);

    @PostMapping("/inviteVendor/intelligentRecommendVendor/getVendorPricingPermissionsByTemplateId")
    List<VendorPricingPermission> getVendorPricingPermissionsByTemplateId(Map<String, Object> paramMap);

    @GetMapping("/bar-anon/releaseDocument")
    void releaseDocument(@RequestParam("number") String number);

    /**
     * 根据招标编码查询单据
     * @param bidingNums
     * @return
     */
    @PostMapping("/bidInitiating/biding/listByNumbers")
    List<Biding> listByNumbers(@RequestBody List<String> bidingNums);
}
