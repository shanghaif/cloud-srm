package com.midea.cloud.srm.feign.supplier;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * FeignClient - 供应商组织品类关系APi
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(value = "cloud-biz-supplier", contextId = "cloud-biz-supplier-vendorOrgCateRel")
public interface VendorOrgCateRelClient {

    @PostMapping("/vendorOrgCateRel/findValidVendors")
    List<CompanyInfo> findValidVendors(@RequestBody FindVendorOrgCateRelParameter parameter);

    @PostMapping("/vendorOrgCateRel/findValidVendorOrgCateRels")
    List<VendorOrgCateRelsVO> findValidVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter);

    @PostMapping("/vendorOrgCateRel/findVendorOrgCateRels")
    List<VendorOrgCateRelsVO> findVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter);

    @PostMapping("/vendorOrgCateRel/findVendorForLogistic")
    List<CompanyInfo> findVendorForLogistic(@RequestBody FindVendorOrgCateRelParameter parameter);
}
