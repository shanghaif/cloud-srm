package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IOrganizationUserService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  组织与用户关系 前端控制器
 * </pre>
 *
 * @author huanghb14@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:04:52
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/organization/organization-user")
public class OrganizationUserController extends BaseController {

    @Autowired
    private IOrganizationUserService iOrganizationUserService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public OrganizationUser get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrganizationUserService.getById(id);
    }

    /**
     * 新增
     *
     * @param organizationUser
     */
    @PostMapping("/add")
    public void add(@RequestBody OrganizationUser organizationUser) {
        Long id = IdGenrator.generate();
        organizationUser.setOrganizationUserRelId(id);
        iOrganizationUserService.save(organizationUser);
    }

    /**
     * 批量新增
     *
     * @param organizationUsers
     */
    @PostMapping("/addBatch")
    public void add(@RequestBody List<OrganizationUser> organizationUsers) {
        if (CollectionUtils.isNotEmpty(organizationUsers)) {
            organizationUsers.forEach(organizationUser -> {
                Long id = IdGenrator.generate();
                organizationUser.setOrganizationUserRelId(id);
            });
            iOrganizationUserService.saveBatch(organizationUsers);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrganizationUserService.removeById(id);
    }

    /**
     * 通过用户ID删除
     *
     * @param userId
     */
    @GetMapping("/deleteByUserId")
    public void deleteByUserId(Long userId) {
        Assert.notNull(userId, "id不能为空");
        QueryWrapper<OrganizationUser> queryOrganizationUserWrapper
                = new QueryWrapper<OrganizationUser>(new OrganizationUser().setUserId(userId));
        iOrganizationUserService.remove(queryOrganizationUserWrapper);
    }

    /**
     * 修改
     *
     * @param organizationUser
     */
    @PostMapping("/modify")
    public void modify(@RequestBody OrganizationUser organizationUser) {
        iOrganizationUserService.updateById(organizationUser);
    }

    /**
     * 分页查询
     *
     * @param organizationUser
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<OrganizationUser> listPage(@RequestBody OrganizationUser organizationUser) {
        PageUtil.startPage(organizationUser.getPageNum(), organizationUser.getPageSize());
        QueryWrapper<OrganizationUser> wrapper = new QueryWrapper<OrganizationUser>(organizationUser);
        return new PageInfo<OrganizationUser>(iOrganizationUserService.list(wrapper));
    }

    /**
     * 通过条件查询
     *
     * @return
     */
    @PostMapping("/listByParam")
    public List<OrganizationUser> listByParam(@RequestBody OrganizationUser organizationUser) {
        return iOrganizationUserService.listByParam(organizationUser);
    }

    /**
     * 通过FullPathId查找
     *
     * @return
     */
    @GetMapping("/queryByFullPathId")
    public List<OrganizationUser> queryByFullPathId(@RequestParam(value = "fullPathId" ) String fullPathId) {
        QueryWrapper<OrganizationUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("FULL_PATH_ID",fullPathId);
        return iOrganizationUserService.list(queryWrapper);
    }

}
