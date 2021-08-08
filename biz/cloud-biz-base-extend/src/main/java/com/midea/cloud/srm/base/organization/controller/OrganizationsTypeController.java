package com.midea.cloud.srm.base.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IOrganizationsTypeService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;

/**
*  <pre>
 *  组织类型 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-13 23:53:35
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/organizationsType")
public class OrganizationsTypeController extends BaseController {

    @Autowired
    private IOrganizationsTypeService iOrganizationsTypeService;

    @Autowired
    private IOrganizationsTypeService organizationsTypeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrganizationsType get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrganizationsTypeService.getById(id);
    }

    /**
    * 新增
    * @param organizationsType
    */
    @PostMapping("/add")
    public void add(OrganizationsType organizationsType) {
        Long id = IdGenrator.generate();
        organizationsType.setTypeId(id);
        iOrganizationsTypeService.save(organizationsType);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrganizationsTypeService.removeById(id);
    }

    /**
    * 修改
    * @param organizationsType
    */
    @PostMapping("/modify")
    public void modify(OrganizationsType organizationsType) {
        iOrganizationsTypeService.updateById(organizationsType);
    }

    /**
    * 分页查询
    * @param organizationsType
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrganizationsType> listPage(OrganizationsType organizationsType) {
        PageUtil.startPage(organizationsType.getPageNum(), organizationsType.getPageSize());
        QueryWrapper<OrganizationsType> wrapper = new QueryWrapper<OrganizationsType>(organizationsType);
        return new PageInfo<OrganizationsType>(iOrganizationsTypeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrganizationsType> listAll() { 
        return iOrganizationsTypeService.list();
    }


    /**
     * 编辑或新增组织类型
     * @param organizationsTypes
     */
    @PostMapping("/saveOrUpdateBatch")
    public void saveOrUpdateBatch(@RequestBody List<OrganizationsType> organizationsTypes) {
        organizationsTypeService.batchSaveOrUpdateOrganizationsType(organizationsTypes);

    }
    
    /**
     * 根据用户信息获取组织类型(多个默认取最后一个)
     * @param
     */
    @GetMapping("/getOrgTypeByUser")
    public List<OrganizationsType> getOrgTypeByUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser, "用户信息不能为空");
        return iOrganizationsTypeService.getOrgTypeByUser(loginAppUser.getUserId());
    }
}
