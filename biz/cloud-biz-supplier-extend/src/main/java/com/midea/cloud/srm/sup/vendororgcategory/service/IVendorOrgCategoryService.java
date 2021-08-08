package com.midea.cloud.srm.sup.vendororgcategory.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOCRequestDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOrgCategoryDTO;

import java.util.List;

/**
*  <pre>
 *  供应商组织与品类关系  服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 17:43:49
 *  修改内容:
 * </pre>
*/
public interface IVendorOrgCategoryService{


    PageInfo<VendorOrgCategoryDTO> listAllByParam(VendorOCRequestDTO vendorOCRequestDTO);

    List<VendorOrgCategoryDTO> list(VendorOCRequestDTO vendorOCRequestDTO);

    List<IntelligentRecommendVO> listIntelligentRecommendInfo(IntelligentRecommendParam recommendParam);
}
