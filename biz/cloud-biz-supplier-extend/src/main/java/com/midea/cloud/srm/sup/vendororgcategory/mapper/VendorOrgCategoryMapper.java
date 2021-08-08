package com.midea.cloud.srm.sup.vendororgcategory.mapper;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOCRequestDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOrgCategoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商组织与品类关系  Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-13
 */
public interface VendorOrgCategoryMapper{

    List<VendorOrgCategoryDTO> list(VendorOCRequestDTO vendorOCRequestDTO);

    List<IntelligentRecommendVO> listIntelligentRecommendInfo(@Param("recommendParam") IntelligentRecommendParam recommendParam);
}
