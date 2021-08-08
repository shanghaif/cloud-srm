package com.midea.cloud.srm.sup.vendororgcategory.service.impl;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOCRequestDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOrgCategoryDTO;
import com.midea.cloud.srm.sup.vendororgcategory.mapper.VendorOrgCategoryMapper;
import com.midea.cloud.srm.sup.vendororgcategory.service.IVendorOrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  供应商组织与品类关系 服务实现类
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
@Service
public class VendorOrgCategoryServiceImpl implements IVendorOrgCategoryService {

    @Autowired
    private VendorOrgCategoryMapper vendorOrgCategoryMapper;

    @Override
    public PageInfo<VendorOrgCategoryDTO> listAllByParam(VendorOCRequestDTO vendorOCRequestDTO) {
        PageUtil.startPage(vendorOCRequestDTO.getPageNum(), vendorOCRequestDTO.getPageSize());
        return new PageInfo<VendorOrgCategoryDTO>(vendorOrgCategoryMapper.list(vendorOCRequestDTO));
    }

    @Override
    public List<VendorOrgCategoryDTO> list(VendorOCRequestDTO vendorOCRequestDTO) {

        return vendorOrgCategoryMapper.list(vendorOCRequestDTO);
    }

    @Override
    public List<IntelligentRecommendVO> listIntelligentRecommendInfo(IntelligentRecommendParam recommendParam) {
        return vendorOrgCategoryMapper.listIntelligentRecommendInfo(recommendParam);
    }
}
