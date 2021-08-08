package com.midea.cloud.srm.sup.vendororgcategory.controller;

import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import com.midea.cloud.srm.sup.vendororgcategory.service.IVendorOrgCateRelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Http接口 - 供应商组织品类关系
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/vendorOrgCateRel")
public class VendorOrgCateRelController extends BaseController {

    @Resource
    private IVendorOrgCateRelService vendorOrgCateRelService;


    @PostMapping("/findValidVendors")
    public List<CompanyInfo> findValidVendors(@RequestBody FindVendorOrgCateRelParameter parameter) {
        return vendorOrgCateRelService.findValidVendors(parameter);
    }

    @PostMapping("/findValidVendorOrgCateRels")
    public List<VendorOrgCateRelsVO> findValidVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter) {
        return vendorOrgCateRelService.findValidVendorOrgCateRels(parameter);
    }

    @PostMapping("/findVendorOrgCateRels")
    public List<VendorOrgCateRelsVO> findVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter) {
        return vendorOrgCateRelService.findVendorOrgCateRels(parameter);
    }

   /* @PostMapping("/findVendorForLogistic")
    public List<CompanyInfo> findVendorForLogistic(@RequestBody Collection<String> midCategoryName) {
        return Collections.EMPTY_LIST;
    }*/
}
