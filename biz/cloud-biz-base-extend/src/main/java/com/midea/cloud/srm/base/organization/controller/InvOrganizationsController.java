package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IInvOrganizationsService;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  库存组织接口表 前端控制器
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:39:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/invOrganizations")
public class InvOrganizationsController extends BaseController {

    @Autowired
    private IInvOrganizationsService iInvOrganizationsService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/getInvOrganizationsById")
    public InvOrganization get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iInvOrganizationsService.getById(id);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/deleteInvOrganizations")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iInvOrganizationsService.removeById(id);
    }

    /**
    * 修改
    * @param invOrganization
    */
    @PostMapping("/modifyInvOrganizations")
    public void modify(@RequestBody InvOrganization invOrganization) {
        iInvOrganizationsService.updateById(invOrganization);
    }

    /**
    * 分页查询
    * @param invOrganization
    * @return
    */
    @PostMapping("/listInvOrganizationsPage")
    public PageInfo<InvOrganization> listPage(@RequestBody InvOrganization invOrganization) {
        PageUtil.startPage(invOrganization.getPageNum(), invOrganization.getPageSize());
        QueryWrapper<InvOrganization> wrapper = new QueryWrapper<InvOrganization>(invOrganization);
        return new PageInfo<InvOrganization>(iInvOrganizationsService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listInvOrganizationsAll")
    public List<InvOrganization> listAll() {
        return iInvOrganizationsService.list();
    }

    /**
     * 根据业务实体查找库存组织
     * @param parentId
     * @return
     */
    @GetMapping("/queryIvnByParent")
    public List<Organization> queryIvnByParent(@RequestParam("parentId") Long parentId){
        return iInvOrganizationsService.queryIvnByParent(parentId);
    }

}
