package com.midea.cloud.srm.sup.vendororgcategory.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOCRequestDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.dto.VendorOrgCategoryDTO;
import com.midea.cloud.srm.sup.vendororgcategory.service.IVendorOrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  供应商组织与品类关系 前端控制器
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
@RestController
@RequestMapping("/vendorOrgCategory")
public class VendorOrgCategoryController extends BaseController {

    @Autowired
    private IVendorOrgCategoryService iVendorOrgCategoryService;


    /**
     * 查询根据传参查询所有并且根据操作时间排序
     * @return
     */
    @PostMapping("/listAllByParam")
    public  PageInfo<VendorOrgCategoryDTO> listAllByParam(@RequestBody VendorOCRequestDTO vendorOCRequestDTO) {
        return iVendorOrgCategoryService.listAllByParam(vendorOCRequestDTO);
    }

    /**
     * 招标查询智能推荐列表
     */
    @PostMapping("/listIntelligentRecommendInfo")
    List<IntelligentRecommendVO> listIntelligentRecommendInfo(@RequestBody IntelligentRecommendParam recommendParam) {
        return iVendorOrgCategoryService.listIntelligentRecommendInfo(recommendParam);
    }
}
