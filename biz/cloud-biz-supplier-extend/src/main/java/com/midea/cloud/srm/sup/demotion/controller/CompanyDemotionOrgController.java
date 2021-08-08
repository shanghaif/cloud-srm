package com.midea.cloud.srm.sup.demotion.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.demotion.dto.DemotionOrgQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商升降级业务实体行表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:44
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/demotion/company-demotion-org")
public class CompanyDemotionOrgController extends BaseController {

    @Autowired
    private ICompanyDemotionOrgService iCompanyDemotionOrgService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CompanyDemotionOrg get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCompanyDemotionOrgService.getById(id);
    }

    /**
    * 新增
    * @param companyDemotionOrg
    */
    @PostMapping("/add")
    public void add(@RequestBody CompanyDemotionOrg companyDemotionOrg) {
        Long id = IdGenrator.generate();
        companyDemotionOrg.setCompanyDemotionOrgId(id);
        iCompanyDemotionOrgService.save(companyDemotionOrg);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCompanyDemotionOrgService.removeById(id);
    }

    /**
    * 修改
    * @param companyDemotionOrg
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CompanyDemotionOrg companyDemotionOrg) {
        iCompanyDemotionOrgService.updateById(companyDemotionOrg);
    }

    /**
    * 分页查询
    * @param companyDemotionOrg
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CompanyDemotionOrg> listPage(@RequestBody CompanyDemotionOrg companyDemotionOrg) {
        PageUtil.startPage(companyDemotionOrg.getPageNum(), companyDemotionOrg.getPageSize());
        QueryWrapper<CompanyDemotionOrg> wrapper = new QueryWrapper<CompanyDemotionOrg>(companyDemotionOrg);
        return new PageInfo<CompanyDemotionOrg>(iCompanyDemotionOrgService.list(wrapper));
    }

    /**
     * 根据供应商id和品类和降级类型 查询升降级OU行
     * @param queryDTO
     * @return
     */
    @PostMapping("/queryOrgsByParam")
    public List<CompanyDemotionOrg> queryOrgsByParam(@RequestBody DemotionOrgQueryDTO queryDTO) {
        Assert.notNull(queryDTO.getCompanyId(), "供应商id不能为空，请选择后重试。");
        Assert.isTrue(StringUtils.isNotEmpty(queryDTO.getDemotionType()), "供应商升降级类型不能为空，请选择后重试。");
        return iCompanyDemotionOrgService.queryOrgsByParam(queryDTO);
    }
 
}
