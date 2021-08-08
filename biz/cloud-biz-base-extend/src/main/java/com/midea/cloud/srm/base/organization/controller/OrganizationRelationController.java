package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.base.organization.service.IOrganizationRelationService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */

@RestController
@RequestMapping({"/organization/relation"})
public class OrganizationRelationController {
    @Autowired
    private IOrganizationRelationService iOrganizationRelationService;
    @Resource
    private RedisUtil redisUtil;

    public OrganizationRelationController() {
    }

    @GetMapping({"/get"})
    public OrganizationRelation get(Long id) {
        Assert.notNull(id, "id不能为空");
        return (OrganizationRelation)this.iOrganizationRelationService.getById(id);
    }

    @GetMapping({"/getByFullPathId"})
    public OrganizationRelation getByFullPathId(String fullPathId) {
        Assert.notNull(fullPathId, "fullPathId不能为空");
        List<OrganizationRelation> organizationRelations = this.iOrganizationRelationService.assembleTreeByParentNew(new OrganizationRelation());
        return this.getParentOrgByFullPathId((OrganizationRelation)null, organizationRelations, fullPathId);
    }

    private OrganizationRelation getParentOrgByFullPathId(OrganizationRelation parentOrgan, List<OrganizationRelation> organizationRelations, String fullPathId) {
        Iterator var4 = organizationRelations.iterator();

        while(var4.hasNext()) {
            OrganizationRelation organizationRelation = (OrganizationRelation)var4.next();
            if (organizationRelation.getFullPathId().equals(fullPathId)) {
                return parentOrgan;
            }

            List<OrganizationRelation> childOrganizationRelations = organizationRelation.getChildOrganRelation();
            if (childOrganizationRelations != null) {
                OrganizationRelation downParentOrgan = this.getParentOrgByFullPathId(organizationRelation, childOrganizationRelations, fullPathId);
                if (downParentOrgan != null) {
                    return downParentOrgan;
                }
            }
        }

        return null;
    }

    @PostMapping({"/add"})
    public void add(OrganizationRelation organizationRelation) {
        Long id = IdGenrator.generate();
        organizationRelation.setRelId(id);
        this.iOrganizationRelationService.save(organizationRelation);
    }

    @PostMapping({"/delete"})
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        this.iOrganizationRelationService.removeById(id);
    }

    @PostMapping({"/modify"})
    public void modify(OrganizationRelation organizationRelation) {
        this.iOrganizationRelationService.updateById(organizationRelation);
    }

    @PostMapping({"/listPage"})
    public PageInfo<OrganizationRelation> listPage(OrganizationRelation organizationRelation) {
        PageUtil.startPage(organizationRelation.getPageNum(), organizationRelation.getPageSize());
        QueryWrapper<OrganizationRelation> wrapper = new QueryWrapper(organizationRelation);
        return new PageInfo(this.iOrganizationRelationService.list(wrapper));
    }

    @PostMapping({"/listAll"})
    public List<OrganizationRelation> listAll() {
        return this.iOrganizationRelationService.list();
    }

    @PostMapping({"/listChildrenOrganization"})
    public List<OrganizationRelation> listChildrenOrganization(Long organizationId) {
        Assert.notNull(organizationId, "organizationId不能为空");
        return this.iOrganizationRelationService.listChildrenOrganization(organizationId);
    }

    @PostMapping({"/tree"})
    @CacheData(keyName = RedisKey.TREE_BY_PARENT,cacheTime = 3600*6,interfaceName = "组织树接口")
    public List<OrganizationRelation> treeByParent(@RequestBody OrganizationRelation organizationRelation) {
        return this.iOrganizationRelationService.assembleTreeByParent(organizationRelation);
    }

    @PostMapping({"/treeNew"})
    @CacheData(keyName = RedisKey.TREE_NEW_LOCK,cacheTime = 3600*6,interfaceName = "组织树接口")
    public List<OrganizationRelation> allTree() {
        return iOrganizationRelationService.assembleTreeByParentNew(new OrganizationRelation());
    }

    @PostMapping({"/treeByNoCache"})
    public List<OrganizationRelation> treeByNoCache(@RequestBody OrganizationRelation organizationRelation) {
        return this.iOrganizationRelationService.assembleTreeByParentNew(organizationRelation);
    }

    @PostMapping({"/treeByChild"})
    public List<OrganizationRelation> treeByChild(@RequestBody OrganizationRelation organizationRelation) {
        return this.iOrganizationRelationService.assembleTreeByChild(organizationRelation);
    }

    @GetMapping({"/selectTreeByType"})
    public List<OrganizationRelation> selectTreeByType(String organizationTypeCode) {
        return this.iOrganizationRelationService.selectTreeByType(organizationTypeCode);
    }

    @GetMapping({"/queryByOrganizationId"})
    public List<OrganizationRelation> queryByOrganizationId(@RequestParam("organizationId") Long organizationId) {
        Assert.notNull(organizationId, "organizationId不能为空");
        QueryWrapper<OrganizationRelation> queryWrapper = new QueryWrapper();
        queryWrapper.eq("ORGANIZATION_ID", "organizationId");
        return this.iOrganizationRelationService.list(queryWrapper);
    }
}
