package com.midea.cloud.srm.sup.demotion.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
*  <pre>
 *  供应商升降级品类行表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:08
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/demotion/company-demotion-category")
public class CompanyDemotionCategoryController extends BaseController {

    @Autowired
    private ICompanyDemotionCategoryService iCompanyDemotionCategoryService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CompanyDemotionCategory get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCompanyDemotionCategoryService.getById(id);
    }

    /**
    * 新增
    * @param companyDemotionCategory
    */
    @PostMapping("/add")
    public void add(@RequestBody CompanyDemotionCategory companyDemotionCategory) {
        Long id = IdGenrator.generate();
        companyDemotionCategory.setCompanyDemotionId(id);
        iCompanyDemotionCategoryService.save(companyDemotionCategory);
    }

    /**
    * 修改
    * @param companyDemotionCategory
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CompanyDemotionCategory companyDemotionCategory) {
        iCompanyDemotionCategoryService.updateById(companyDemotionCategory);
    }

    /**
    * 分页查询
    * @param companyDemotionCategory
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CompanyDemotionCategory> listPage(@RequestBody CompanyDemotionCategory companyDemotionCategory) {
        PageUtil.startPage(companyDemotionCategory.getPageNum(), companyDemotionCategory.getPageSize());
        QueryWrapper<CompanyDemotionCategory> wrapper = new QueryWrapper<CompanyDemotionCategory>(companyDemotionCategory);
        return new PageInfo<CompanyDemotionCategory>(iCompanyDemotionCategoryService.list(wrapper));
    }

    /**
     * 根据供应商id和升降级类型查询品类
     * @param queryDTO
     * @return
     */
    @PostMapping("/queryCategorysByParam")
    public List<CompanyDemotionCategory> queryCategorysByParam(@RequestBody CompanyDemotion queryDTO) {
        Assert.notNull(queryDTO.getCompanyId(), "供应商id不能为空，请选择后重试。");
        Assert.isTrue(StringUtils.isNotEmpty(queryDTO.getDemotionType()), "供应商升降级类型不能为空，请选择后重试。");
        return iCompanyDemotionCategoryService.queryCategorysByParam(queryDTO);
    }

    /**
     * 根据行id删除升降级品类行
     * @param companyDemotionCategoryId
     */
    @GetMapping("/deleteByCompanyDemotionCategoryId")
    public void deleteByCompanyDemotionCategoryId(@NotNull Long companyDemotionCategoryId) {
        iCompanyDemotionCategoryService.removeById(companyDemotionCategoryId);
    }

}
