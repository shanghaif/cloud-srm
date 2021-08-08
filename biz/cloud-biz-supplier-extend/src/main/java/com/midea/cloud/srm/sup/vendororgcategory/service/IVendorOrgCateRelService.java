package com.midea.cloud.srm.sup.vendororgcategory.service;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;

import java.util.Collection;
import java.util.List;

/**
 * 供应商组织品类关系服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IVendorOrgCateRelService {

    /**
     * 查询 合格供应商集
     *
     * @param parameter 查询参数
     * @return 合格供应商集
     */
    List<CompanyInfo> findValidVendors(FindVendorOrgCateRelParameter parameter);

    /**
     * 查询 合格的供应商组织品类关系集
     *
     * @param parameter 查询参数
     * @return 供应商组织品类关系集
     */
    List<VendorOrgCateRelsVO> findValidVendorOrgCateRels(FindVendorOrgCateRelParameter parameter);

    /**
     * 查询 供应商组织品类关系集
     *
     * @param parameter 查询参数
     * @return 供应商组织品类关系集
     */
    List<VendorOrgCateRelsVO> findVendorOrgCateRels(FindVendorOrgCateRelParameter parameter);

   /* List<CompanyInfo> findVendorForLogistic(Collection<String> midCategoryNames);*/
}
