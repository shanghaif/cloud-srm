package com.midea.cloud.srm.base.organization.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.CacheClear;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.base.organization.service.IOrganizationRelationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationUserService;
import com.midea.cloud.srm.base.ou.service.IBaseOuDetailService;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.organization.dto.OrgTypeDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationMapDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationParamDto;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 *  <pre>
 *  组织信息 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:42:29
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/organization/organization")
@Slf4j
public class OrganizationController extends BaseController {

    @Autowired
    private IOrganizationService iOrganizationService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private IOrganizationRelationService iOrganizationRelationService;
    @Resource
    private  IBaseOuDetailService groupDetailService;
    @Resource
    private IOrganizationUserService iOrganizationUserService;

    /**
     * 根据用户id获取组织权限信息
     * @param userIds
     * @return 用户id+组织id -> 组织
     */
    @PostMapping("/queryOrganizationUserBuUserIds")
    public Map<String, OrganizationUser> queryOrganizationUserBuUserIds(@RequestBody List<Long> userIds){
        Map<String,OrganizationUser> organizationUserMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(userIds)){
            userIds = userIds.stream().distinct().collect(Collectors.toList());
            List<OrganizationUser> organizationUserList = iOrganizationUserService.list(new QueryWrapper<OrganizationUser>().in("USER_ID", userIds));
            if(CollectionUtils.isNotEmpty(organizationUserList)){
                organizationUserMap = organizationUserList.stream().collect(
                        Collectors.toMap(k->String.valueOf(k.getUserId()+k.getOrganizationId()),v->v,(k1,k2)->k1));
            }
        }
        return organizationUserMap;
    }

    /**
     * 保存用户组织权限数据
     * @param organizationUsers
     */
    @PostMapping("/saveOrganizationUserImport")
    public void saveOrganizationUserImport(@RequestBody List<OrganizationUser> organizationUsers){
        if(CollectionUtils.isNotEmpty(organizationUsers)){
            CompletableFuture.runAsync(() -> {
                Date startTime = new Date();
                log.info("----------------------用户组织权限导入开始保存--------------------------");
                log.info("----------------------当前时间:"+ DateUtil.format(startTime,DateUtil.DATE_FORMAT_19) +"--------------------------");
                organizationUsers.forEach(organizationUser -> {
                    log.info("保存数据:"+ JSON.toJSONString(organizationUser));
                    if (StringUtil.notEmpty(organizationUser.getUserId()) && StringUtil.notEmpty(organizationUser.getOrganizationId())) {
                        QueryWrapper<OrganizationUser> queryWrapper = new QueryWrapper<>(new OrganizationUser().setUserId(organizationUser.getUserId()).
                                setOrganizationId(organizationUser.getOrganizationId()));
                        List<OrganizationUser> users = iOrganizationUserService.list(queryWrapper);
                        if(CollectionUtils.isEmpty(users)){
                            organizationUser.setOrganizationUserRelId(IdGenrator.generate());
                            iOrganizationUserService.save(organizationUser);
                        }
                    }
                });
                Date endTime = new Date();
                log.info("----------------------用户组织权限导入结束--------------------------------");
                log.info("----------------------当前时间:"+ DateUtil.format(endTime,DateUtil.DATE_FORMAT_19) +"--------------------------");
                long s = (endTime.getTime()-startTime.getTime())/ 1000 ;
                log.info("----------------------用时"+s +"秒-------------------------------");
            });
        }
    }


    /**
     * 根据组织ID获取组织和组织关系
     * @param organizationId
     */
    @GetMapping("/get")
    public Organization get(@RequestParam("organizationId") Long organizationId) {
        Assert.notNull(organizationId, "organizationId不能为空");
        return iOrganizationService.get(organizationId);
    }

    /**
     * 新增组织和组织关系
     * @param organization
     */
    @PostMapping("/saveOrUpdateOrganization")
    @CacheClear(keyName = {RedisKey.TREE_BY_PARENT,RedisKey.TREE_NEW_LOCK})
    public void saveOrUpdateOrganization(@RequestBody Organization organization) {
        iOrganizationService.saveOrUpdateOrganization(organization);
//        // 异步
//        CompletableFuture.runAsync(() -> {
//            try {
//                // 刷新组织树缓存
//                List<OrganizationRelation> organizationRelations = iOrganizationRelationService.assembleTreeByParentNew(new OrganizationRelation());
//                redisUtil.set(RedisKey.TREE_NEW_LOCK,organizationRelations,3600*12);
//            } catch (Exception e) {
//                log.error("刷新组织树缓存报错:" , e);
//            }
//        });
    }

    /**
     * 删除
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrganizationService.removeById(id);
    }

    /**
     * 修改
     * @param organization
     */
    @PostMapping("/modify")
    public void modify(Organization organization) {
        iOrganizationService.updateById(organization);
    }

    /**
     * 分页查询
     * @param organization
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Organization> listPage(Organization organization) {
        PageUtil.startPage(organization.getPageNum(), organization.getPageSize());
        QueryWrapper<Organization> wrapper = new QueryWrapper<Organization>(organization);
        return new PageInfo<Organization>(iOrganizationService.list(wrapper));
    }

    @PostMapping({"/listOrganization"})
    public List<Organization> listOrganization(@RequestBody Organization organization) {

        List<Long> organizationIdList = organization.getOrganizationIdList();
        return iOrganizationService.listOrganization(organizationIdList, organization.getOrganizationTypeCode());
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<Organization> listAll() {
        return iOrganizationService.list();
    }

    /**
     * 分页查询全部组织
     * @param organization
     * @return
     */
    @PostMapping("/listAllOrganization")
    public PageInfo<Organization> listAllOrganization(@RequestBody Organization organization) {
        return iOrganizationService.listAllOrganization(organization);
    }

    @GetMapping("/ceeaListAllOrganization")
    public List<Organization> ceeaListAll(){
        return iOrganizationService.ceeaListAll();
    }

    /**
     * 分页查询上层组织(上层组织不包括本级)
     * @param organization
     * @return
     */
    @PostMapping("/listParentOrganization")
    public PageInfo<Organization> listParentOrganization(@RequestBody Organization organization) {
        return iOrganizationService.listAllOrganization(organization);
    }

    /**
     * 根据条件获取
     *
     * @param organization
     */
    @PostMapping("/getByParam")
    public Organization getByParam(@RequestBody Organization organization) {
        Assert.notNull(organization, "查询条件不能为空");
        List<Organization> result = iOrganizationService.list(new QueryWrapper<>(organization));
        if(result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }

    /**
     * 根据条件获取
     *
     * @param organization
     */
    @PostMapping("/getOrganization")
    public Organization getOrganization(@RequestBody Organization organization) {
        Assert.notNull(organization, "查询条件不能为空");
        return iOrganizationService.getOrganization(organization);
    }

    /**
     * 根据组织名称列表查询组织信息列表
     *
     * @return
     */
    @PostMapping("/getOrganizationByNameList")
    List<Organization> getOrganizationByNameList(@RequestBody List<String> orgNameList){
        return iOrganizationService.getOrganizationByNameList(orgNameList);
    }

    /**
     * 查找指定组织下用户有权限的或全部指定组织类型的组织
     * 传用户ID时就过滤权限, 不传用户ID查全部
     *
     * @return
     */
    @PostMapping("/getOrganizationByOrgCode")
    List<Organization> getOrganizationByOrgCode(@RequestBody OrganizationParamDto organizationParamDto){
        return iOrganizationService.getOrganizationByOrgCode(organizationParamDto);
    }

    /**
     * 查询所有组织类型
     * @return
     */
    @RequestMapping("/getOrgTypeDtoList")
    List<OrgTypeDto> getOrgTypeDtoList(){
        return iOrganizationService.getOrgTypeDtoList();
    }

    /**
     * 根据子id获取父列表
     * @return
     */
    @PostMapping("/getFatherByChild")
    public OrganizationMapDto getFatherByChild(@RequestBody List<Long> orgIds){
        OrganizationMapDto fatherByChild = iOrganizationService.getFatherByChild(orgIds);
        return fatherByChild;
    }

    @GetMapping("/getOrgParentCodeByOrgId")
    public String getOrgParentCodeByOrgId(@RequestParam Long orgId){
        return iOrganizationService.getOne(Wrappers.lambdaQuery(Organization.class)
                .select(Organization::getDivisionId)
                .eq(Organization::getOrganizationId,orgId)).getDivisionId();
    }

    /**
     * 通过组织获取全部部门(包括虚拟部门,结算用)
     * @param organization
     * @return
     */
    @PostMapping("/getAllDeptByOrganization")
    public List<DeptDto> getAllDeptByOrganization(@RequestBody Organization organization) {
        return iOrganizationService.getAllDeptByOrganization(organization);
    }

    /**
     * 通过批量组织ID获取对应业务实体全部部门(包括虚拟部门,结算用)
     * @param organizationIds
     * @return
     */
    @PostMapping("/getAllDeptByOrganizations")
    public Map<Long, List<DeptDto>> getAllDeptByOrganizations(@RequestBody List<Long> organizationIds) {
        return iOrganizationService.getAllDeptByOrganizations(organizationIds);
    }

    @GetMapping("/listAllOrganizationForImport")
    public List<Organization> listAllOrganizationForImport(){
        return iOrganizationService.listAllOrganizationForImport();
    }


    @PostMapping("/getBuCodeByOuGroupId")
    List<String> getBuCodeByOuGroupId(@RequestBody Collection<Long> ouGroupId){
        return groupDetailService.list(Wrappers.query(new BaseOuDetail())
                .select("distinct BU_ID")
                .in("OU_GROUP_ID",ouGroupId)
        ).stream().map(BaseOuDetail::getBuId).collect(Collectors.toList());
    }

    @PostMapping("/getBuCodeByOrgIds")
    List<String> getBuCodeByOrgIds(@RequestBody Collection<Long> orgIds){
        List<String> list = new ArrayList<>();
        List<Organization> organizations = iOrganizationService.list(Wrappers.query(new Organization())
                .select("distinct DIVISION_ID")
                .in("ORGANIZATION_ID", orgIds));
        if(CollectionUtils.isNotEmpty(organizations)){
            organizations.forEach(organization -> {
                if(null != organization && !ObjectUtils.isEmpty(organization.getDivisionId())) list.add(organization.getDivisionId());
            });
        }
        return list;
    }

    /**
     * 查找指定组织下用户有权限的或全部指定组织类型的组织  add by chensl26(供应商注册界面使用)
     * 传用户ID时就过滤权限, 不传用户ID查全部
     *
     * @return
     */
    @PostMapping("/listOrganizationByOrgCode")
    List<Organization> listOrganizationByOrgCode(@RequestBody OrganizationParamDto organizationParamDto){
        return iOrganizationService.listOrganizationByOrgCode(organizationParamDto);
    }
    
    @GetMapping("/getFullPathIdByTypeCode")
    List<Organization> getFullPathIdByTypeCode(@RequestParam(name = "userId", required = true)Long userId) {
        return iOrganizationUserService.getFullPathIdByTypeCode(userId);
    }
}
