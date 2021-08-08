package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.EncryptUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.configguide.service.IConfigGuideService;
import com.midea.cloud.srm.base.dept.service.IVirtualDepartService;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationRelationMapper;
import com.midea.cloud.srm.base.organization.service.*;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.organization.dto.OrgTypeDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationDTO;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationMapDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationParamDto;
import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  组织信息 服务实现类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:42:29
 *  修改内容:
 * </pre>
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    @Resource
    OrganizationMapper organizationMapper;

    @Resource
    OrganizationRelationMapper organizationRelationMapper;

    @Autowired
    IOrganizationRelationService organizationRelationService;

    @Autowired
    private IConfigGuideService iConfigGuideService;

    /**
     * ERP库存组织Service
     */
    @Resource
    private IInvOrganizationsService iInvOrganizationsService;
    /**
     * ERP业务实体Service
     */
    @Resource
    private IBusinessUnitsService iBusinessUnitsService;
    /**
     * ERP地点信息Service
     */
    @Resource
    private ILocationService iLocationService;
    @Autowired
    private IVirtualDepartService iVirtualDepartService;

    /**
     * update date 20-09-26 by Wally
     */
    @Override
    @Transactional
    public void saveOrUpdateOrganization(Organization organization) {
        Assert.notNull(organization, "organization不能为空");

        //根据组织名称获取对应的erp库存组织ID、库存组织Code、业务实体ID和业务实体Code、地点ID、地点名称等相关
        String oldOrganizationCode = organization.getOrganizationCode();    //页面传类
        String orgName = organization.getOrganizationName();
        if (StringUtils.isNotBlank(orgName) && BaseConst.ORG_INV.equals(organization.getOrganizationTypeCode())) { //库存组织
            List<InvOrganization> invOrganizationList = iInvOrganizationsService.list(new QueryWrapper<>(
                    new InvOrganization().setInvOrganizationName(orgName)));
            if (CollectionUtils.isNotEmpty(invOrganizationList)) {
                invOrganizationList.forEach(invOrganization -> {
                    if (null != organization) {
                        organization.setErpOrgId(invOrganization.getEsInvOrganizationId());
                        organization.setOrganizationCode(invOrganization.getEsInvOrganizationCode());
                        organization.setCeeaErpUnitId(invOrganization.getEsBusinessUnitId());
                        organization.setCeeaErpUnitCode(invOrganization.getEsBusinessUnitCode());
                        /**页面上已经传 locationId 和locationName，所以不需要重新设计了*/
//                        String locationId = invOrganization.getEsLocationId();
//                        String locationName = "";
//                        if(StringUtils.isNotBlank(locationId)){
//                            List<Location> locationList = iLocationService.list(new QueryWrapper<>(new Location().setLocationId(Long.valueOf(locationId))));
//                            if(CollectionUtils.isNotEmpty(locationList) && null != locationList.get(0)){
//                                locationName = locationList.get(0).getAddrDetail();
//                            }
//                        }
//                        organization.setCeeaReceivingLocationId(locationId);
//                        organization.setCeeaReceivingLocation(locationName);
//                        organization.setCeeaOrganizationSiteId((StringUtils.isNotBlank(locationId) ? Long.valueOf(locationId) : null));
//                        organization.setOrganizationSite(locationName);
                        return;
                    }
                });
            }
        } else if (StringUtils.isNotBlank(orgName) && BaseConst.ORG_OU.equals(organization.getOrganizationTypeCode())) { //业务实体
            List<BusinessUnits> businessUnitsList = iBusinessUnitsService.list(new QueryWrapper<>(new BusinessUnits().setBusinessFullName(orgName)));
            if (CollectionUtils.isNotEmpty(businessUnitsList)) {
                businessUnitsList.forEach(businessUnits -> {
                    if (null != businessUnits) {
                        organization.setErpOrgId(businessUnits.getEsBusinessUnitId());
                        organization.setOrganizationCode(businessUnits.getEsBusinessUnitCode());
                        /**页面上已经传 locationId 和locationName，所以不需要重新设计了*/
//                        String locationId = businessUnits.getEsLocationId();
//                        String locationName = "";
//                        if(StringUtils.isNotBlank(locationId)){
//                            List<Location> locationList = iLocationService.list(new QueryWrapper<>(new Location().setLocationId(Long.valueOf(locationId))));
//                            if(CollectionUtils.isNotEmpty(locationList) && null != locationList.get(0)){
//                                locationName = locationList.get(0).getAddrDetail();
//                            }
//                        }
//                        organization.setCeeaReceivingLocationId(locationId);
//                        organization.setCeeaReceivingLocation(locationName);
//                        organization.setCeeaOrganizationSiteId((StringUtils.isNotBlank(locationId) ? Long.valueOf(locationId) : null));
//                        organization.setOrganizationSite(locationName);
                        return;
                    }
                });
            }
        }
        if (StringUtils.isBlank(organization.getOrganizationCode())) {
            organization.setOrganizationCode(oldOrganizationCode);
        }


        if (organization.getOrganizationId() == null) {
            //新增组织
            Long id = IdGenrator.generate();
            organization.setOrganizationId(id);
            //添加组织
            addOrganization(organization);
            //添加组织关系
            addOrganizationRelations(organization);
        } else {
            List<Long> updateIds = StringUtil.stringConvertNumList(organization.getParentOrganizationIds(), ",");
            List<Long> existIds = StringUtil.stringConvertNumList(organizationMapper.selectById(organization.getOrganizationId()).getParentOrganizationIds(), ",");
            //编辑时,将编辑的父组织ID与数据库中的父组织ID对比
            if (!CollectionUtils.isEmpty(existIds)) {
                for (Long existId : existIds) {
                    if (!updateIds.contains(existId)) {
                        //编辑的父组织ID中,不存在数据库中的父组织ID,则需要删除
                        deleteOrganizationRelation(organization, existId);
                    }
                }
            } else {
                //删除根节点的关系
                deleteOrganizationRelation(organization, SysConstant.TREE_PARENT_ID);
            }
            if (!CollectionUtils.isEmpty(updateIds)) {
                for (Long updateId : updateIds) {
                    //数据库中的父组织ID不存在编辑的父组织ID,则需重新添加组织关系
                    if (!existIds.contains(updateId)) {
                        saveOrganizationRelation(organization, updateId);
                    }
                }
            } else {
                OrganizationRelation organizationRelation = new OrganizationRelation();
                assembleOrganizationRelation(organization, organizationRelation);
                //添加顶级组织
                organizationRelationMapper.insert(organizationRelation);
            }
            organization.setLastUpdateDate(new Date());
            try {
                organizationMapper.updateById(organization);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                    if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("ORGANIZATION_NAME") != -1) {
                        throw new BaseException("组织名称已存在,无需再添加,请编辑");
                    }
                }
            }
        }

    }


    @Override
    @Transactional
    public void saveOrUpdateOrganizationForErp(Organization organization) {
        Assert.notNull(organization, "organization不能为空");

        if (organization.getOrganizationId() == null) {
            //新增组织
            Long id = IdGenrator.generate();
            organization.setOrganizationId(id);
            //添加组织
            addOrganization(organization);
            //添加组织关系
            addOrganizationRelations(organization);
        } else {
            List<Long> updateIds = StringUtil.stringConvertNumList(organization.getParentOrganizationIds(), ",");
            List<Long> existIds = StringUtil.stringConvertNumList(organizationMapper.selectById(organization.getOrganizationId()).getParentOrganizationIds(), ",");
            //编辑时,将编辑的父组织ID与数据库中的父组织ID对比
            if (!CollectionUtils.isEmpty(existIds)) {
                for (Long existId : existIds) {
                    if (!updateIds.contains(existId)) {
                        //编辑的父组织ID中,不存在数据库中的父组织ID,则需要删除
                        deleteOrganizationRelation(organization, existId);
                    }
                }
            } else {
                //删除根节点的关系
                deleteOrganizationRelation(organization, SysConstant.TREE_PARENT_ID);
            }
            if (!CollectionUtils.isEmpty(updateIds)) {
                for (Long updateId : updateIds) {
                    //数据库中的父组织ID不存在编辑的父组织ID,则需重新添加组织关系
                    if (!existIds.contains(updateId)) {
                        saveOrganizationRelation(organization, updateId);
                    }
                }
            } else {
                OrganizationRelation organizationRelation = new OrganizationRelation();
                assembleOrganizationRelation(organization, organizationRelation);
                //添加顶级组织
                organizationRelationMapper.insert(organizationRelation);
            }
            organization.setLastUpdateDate(new Date());
            try {
                organizationMapper.updateById(organization);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                    if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("ORGANIZATION_NAME") != -1) {
                        throw new BaseException("组织名称已存在,无需再添加,请编辑");
                    }
                }
            }
        }

    }

    private void deleteOrganizationRelation(Organization organization, Long existId) {
        QueryWrapper<OrganizationRelation> queryWrapper = new QueryWrapper<>(new OrganizationRelation()
                .setOrganizationId(organization.getOrganizationId()).setParentOrganizationId(existId));
        //TODO
        /*queryWrapper.eq("ORGANIZATION_ID", organization.getOrganizationId());
        queryWrapper.eq("PARENT_ORGANIZATION_ID", existId);*/
        organizationRelationMapper.delete(queryWrapper);
    }

    /**
     * 根据条件查询组织
     *
     * @param organization
     * @return
     */
    @Override
    public Organization getOrganization(Organization organization) {
        Assert.notNull(organization, "查询条件organization为空！");
        List<Organization> organizations = this.list(new QueryWrapper<>(organization));
        Organization resultOrganization = new Organization();
        resultOrganization = CollectionUtils.isNotEmpty(organizations) ? organizations.get(0) : null;
        return resultOrganization;
    }

    @Override
    public OrganizationDTO getOrganizationDTOById(Long id) {
        //获取组织
        Organization organization = organizationMapper.selectById(id);
        //获取上层组织
        QueryWrapper<OrganizationRelation> queryWrapper = new QueryWrapper<>(new OrganizationRelation().setOrganizationId(id));
        List<OrganizationRelation> organizationRelations = organizationRelationMapper.selectList(queryWrapper);
        //组合DTO
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setOrganization(organization);
        organizationDTO.setOrganizationRelations(organizationRelations);
        return organizationDTO;
    }

    @Override
    public PageInfo<Organization> listAllOrganization(Organization organization) {
        PageUtil.startPage(organization.getPageNum(), organization.getPageSize());
        Organization organizationEntity = new Organization();
        if (StringUtils.isNotBlank(organization.getOrganizationTypeCode())) {
            organizationEntity.setOrganizationTypeCode(organization.getOrganizationTypeCode());
        }
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>(organizationEntity);
        if (organization.getOrganizationId() != null) {
            queryWrapper.ne("ORGANIZATION_ID", organization.getOrganizationId());
        }
        if (!StringUtil.isEmpty(organization.getOrganizationName())) {
            queryWrapper.like("ORGANIZATION_NAME", organization.getOrganizationName());
        }
        if (!StringUtil.isEmpty(organization.getParentOrganizationNames())) {
            queryWrapper.like("PARENT_ORGANIZATION_NAMES", organization.getParentOrganizationNames());
        }
        if (!StringUtil.isEmpty(organization.getEnabled())) {
            queryWrapper.like("ENABLED", organization.getEnabled());
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        List<Organization> organizations = organizationMapper.selectList(queryWrapper);
        for (Organization entityOrganization : organizations) {
            setParentOrganizationNames(entityOrganization);
        }
        return new PageInfo<>(organizations);
    }

    //给每个组织设置父组织名称
    private void setParentOrganizationNames(Organization organization) {
        if (organization == null) return;
        String parentOrganizationIds = organization.getParentOrganizationIds();
        if (!StringUtils.isBlank(parentOrganizationIds)) {
            List<Long> longs = StringUtil.stringConvertNumList(parentOrganizationIds, ",");
            //从组织中取出父ID集,根据ID集查出对应父组织的名字再组合成字符串返回给前端.
            QueryWrapper<Organization> wrapper = new QueryWrapper<>();
            wrapper.in("ORGANIZATION_ID", longs);
            List<Organization> parentOrganizations = organizationMapper.selectList(wrapper);
            List<String> parentOrganizationNames = new ArrayList<>();
            for (Organization parentOrganization : parentOrganizations) {
                if (parentOrganization == null) continue;
                parentOrganizationNames.add(parentOrganization.getOrganizationName());
            }
            organization.setParentOrganizationNames(String.join(",", parentOrganizationNames));
        }
    }

    @Override
    public Organization get(Long id) {
        Organization organization = organizationMapper.selectById(id);
        setParentOrganizationNames(organization);
        return organization;
    }

    @Override
    public List<Organization> getOrganizationByNameList(List<String> orgNameList) {
        if (CollectionUtils.isNotEmpty(orgNameList)) {
            orgNameList = orgNameList.stream().distinct().collect(Collectors.toList());
            QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("ORGANIZATION_NAME", orgNameList);
            return this.list(queryWrapper);
        } else {
            return null;
        }
    }

    @Override
    public List<Organization> listOrganization(List<Long> organizationIdList, String organizationTypeCode) {
        if (CollectionUtils.isNotEmpty(organizationIdList)) {
            organizationIdList = organizationIdList.stream().distinct().collect(Collectors.toList());
            QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("ORGANIZATION_ID", organizationIdList);
            queryWrapper.eq("ORGANIZATION_TYPE_CODE", organizationTypeCode);
            return this.list(queryWrapper);
        } else {
            return Collections.emptyList();
        }
    }

    /*private void returnParentOrganizationIds(Long id, Organization organization) {
        QueryWrapper<OrganizationRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORGANIZATION_ID", id);
        List<OrganizationRelation> organizationRelations = organizationRelationMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(organizationRelations)) {
            List<Long> parentOrganizationIds = new ArrayList<>();
            Set<Long> set = new HashSet<>();
            for (OrganizationRelation organizationRelation : organizationRelations) {
                if (organizationRelation == null) continue;
                set.add(organizationRelation.getParentOrganizationId());
            }
            Iterator<Long> iterator = set.iterator();
            while (iterator.hasNext()) {
                parentOrganizationIds.add(iterator.next());
            }
            organization.setParentOrganizationIds(parentOrganizationIds);
        }
    }*/

    private void addOrganization(Organization organization) {
        Assert.hasText(organization.getOrganizationCode(), "组织编码不能为空");
        Assert.hasText(organization.getOrganizationName(), "组织名称不能为空");
        Assert.notNull(organization.getOrganizationTypeId(), "组织类型id不能为空");
        Assert.hasText(organization.getOrganizationTypeCode(), "组织类型编码不能为空");
        Assert.hasText(organization.getOrganizationTypeName(), "组织类型名称不能为空");
        Assert.notNull(organization.getStartDate(), "生效日期不能为空");
        //设置组织常用的属性
        organization.setEnabled(Enable.Y.toString());
        try {
            organizationMapper.insert(organization);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("ORGANIZATION_NAME") != -1) {
                    throw new BaseException("组织名称已存在,无需再添加,请编辑");
                } else if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("ORGANIZATION_CODE") != -1) {
                    throw new BaseException("组织编码已存在,无需再添加,请编辑");
                }
            }
        }
    }

    private void addOrganizationRelations(Organization organization) {
        //过滤organizationid为null的情况
        String orgIds = Arrays.stream(organization.getParentOrganizationIds().split(",")).filter(
                orgid -> StringUtils.isNotBlank(orgid) && !"null".equals(orgid)
        ).collect(Collectors.joining(","));
        List<Long> parentOrganizationIds = StringUtil.stringConvertNumList(orgIds, ",");
        if (CollectionUtils.isEmpty(parentOrganizationIds)) {
            OrganizationRelation organizationRelation = new OrganizationRelation();
            assembleOrganizationRelation(organization, organizationRelation);
            //添加顶级组织
            organizationRelationMapper.insert(organizationRelation);
        } else {
            for (Long parentOrganizationId : parentOrganizationIds) {
                saveOrganizationRelation(organization, parentOrganizationId);
            }
        }
    }

    private void saveOrganizationRelation(Organization organization, Long parentOrganizationId) {
        OrganizationRelation organizationRelation = new OrganizationRelation();
        assembleOrganizationRelation(organization, organizationRelation);
        organizationRelation.setParentOrganizationId(parentOrganizationId);
        Long parentId = organizationRelation.getOrganizationId();
        if (organizationRelation.getParentOrganizationId() != null && organizationRelation.getParentOrganizationId() != 0) {
            setFullPathId(organizationRelation, String.valueOf(organizationRelation.getParentOrganizationId()), parentId);
        }
        organizationRelationMapper.insert(organizationRelation);
       /* for (OrganizationRelation parentOrganizationRelation : organizationRelations) {
            if (parentOrganizationRelation == null) continue;
            OrganizationRelation organizationRelation = new OrganizationRelation();
            assembleOrganizationRelation(organization, organizationRelation);
            organizationRelation.setParentOrganizationCode(parentOrganizationRelation.getOrganizationCode());
            organizationRelation.setParentOrganizationId(parentOrganizationRelation.getOrganizationId());
            organizationRelation.setParentOrganizationName(parentOrganizationRelation.getOrganizationName());
            organizationRelation.setParentRelId(parentOrganizationRelation.getRelId());
            organizationRelationMapper.insert(organizationRelation);
        }*/
    }

    private String setFullPathId(OrganizationRelation organizationRelation, String parentSignId, Long currentId) {
        String md5ParentOrganizationId = EncryptUtil.getMD5(parentSignId + currentId);
        organizationRelation.setFullPathId(md5ParentOrganizationId);
        return md5ParentOrganizationId;
    }
    //更新子节点的全路径ID TODO
    /*private void updateChildrenFullPathId(OrganizationRelation organizationRelation) {
        QueryWrapper<OrganizationRelation> wrapper = new QueryWrapper<>();
        wrapper.like("ORG_FULL_PATH_ID",  + organizationRelation.getOrganizationId() + "_");
        List<OrganizationRelation> childrenOrganizationRelations = organizationRelationMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(childrenOrganizationRelations)) {
            for (OrganizationRelation childrenOrganizationRelation : childrenOrganizationRelations) {
                if (childrenOrganizationRelation == null) continue;
                String orgFullPathId = childrenOrganizationRelation.getOrgFullPathId();
                String[] strings = orgFullPathId.split( organizationRelation.getOrganizationId() + "_");
                String oldIds = strings[0] + organizationRelation.getOrganizationId();
                String newIds = organizationRelation.getOrgFullPathId();
                organizationRelationMapper.replaceFullPathId(oldIds, newIds, childrenOrganizationRelation.getRelId());
            }
        }
    }*/

    /**
     * 聚合组织关系与组织共同属性
     *
     * @param organization
     * @param organizationRelation
     */
    private void assembleOrganizationRelation(Organization organization, OrganizationRelation organizationRelation) {
        organizationRelation.setOrganizationId(organization.getOrganizationId());
        organizationRelation.setCreationDate(new Date());
        organizationRelation.setLastUpdateDate(new Date());
    }

    @Override
    public List<Organization> getOrganizationByOrgCode(OrganizationParamDto organizationParamDto) {
        Assert.notNull(organizationParamDto.getOrganizationTypeCode(), "组织类型不能为空");
        Assert.notNull(organizationParamDto.getParentOrganizationId(), "组织父id不能为空");
        String parentOrganizationId = organizationParamDto.getParentOrganizationId();
        List<Organization> organizationList = null;
        if (StringUtil.notEmpty(organizationParamDto.getUserId())) {
            // 查询用户用权限的指定类型的的组织
            organizationList = this.baseMapper.getOrganizationByOrgCode(organizationParamDto);
        } else {
            // 查找全部
            QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORGANIZATION_TYPE_CODE", organizationParamDto.getOrganizationTypeCode());
            organizationList = this.list(queryWrapper);
        }
        List<Organization> result = new ArrayList<>();
        // 检查是否指定组织下的组织
        String parentOrgId = organizationParamDto.getParentOrganizationId();
        if (CollectionUtils.isNotEmpty(organizationList)) {
            String parentIds = organizationList.get(0).getParentOrganizationIds();
            if (StringUtil.notEmpty(parentIds)) {
                for (Organization organization : organizationList) {
                    // 父id集
                    String parentOrganizationIds = organization.getParentOrganizationIds();
                    setResultOrg(result, parentOrgId, organization, parentOrganizationIds);
                }
            } else {
                // 为顶层节点没有父id. 在判断父id是否为-1
                if ("-1".equals(parentOrganizationId) && CollectionUtils.isNotEmpty(organizationList)) {
                    result = organizationList;
                }
            }
        }
        ArrayList<Organization> collect = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            // 去重
            collect = result.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Organization::getOrganizationId))), ArrayList::new
                    )
            );
        }
        return collect;
    }

    public void setResultOrg(List<Organization> result, String parentOrgId, Organization organization, String parentOrganizationIds) {
        if (StringUtil.notEmpty(parentOrganizationIds)) {
            List<String> parentOrgIds = null;
            if (StringUtils.contains(parentOrganizationIds, ",")) {
                parentOrgIds = Arrays.asList(parentOrganizationIds.split(","));
            } else {
                parentOrgIds = Arrays.asList(parentOrganizationIds);
            }
            if (CollectionUtils.isNotEmpty(parentOrgIds)) {
                if (parentOrgIds.contains(parentOrgId)) {
                    result.add(organization);
                } else {
                    for (String parOrgId : parentOrgIds) {
                        Organization organization1 = this.getById(parOrgId);
                        if (null != organization1) {
                            String parentOrganizationIds1 = organization1.getParentOrganizationIds();
                            // 递归
                            setResultOrg(result, parentOrgId, organization, parentOrganizationIds1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<OrgTypeDto> getOrgTypeDtoList() {
        return this.baseMapper.getOrgTypeDtoList();
    }

    @Override
    @Transactional
    public void saveOrUpdateOrganization(List<Organization> organizationList) {
        if (CollectionUtils.isNotEmpty(organizationList)) {
            for (Organization org : organizationList) {
                if (null != org) {
                    this.saveOrUpdateOrganization(org);
                }
            }
        }
    }

    @Override
    @Transactional
    public void saveOrUpdateOrganizationForErp(List<Organization> organizationList) {
        if (CollectionUtils.isNotEmpty(organizationList)) {
            for (Organization org : organizationList) {
                if (null != org) {
                    this.saveOrUpdateOrganizationForErp(org);
                }
            }
        }
    }

    @Override
    public OrganizationMapDto getFatherByChild(List<Long> orgIds) {
        OrganizationMapDto organizationMapDto = new OrganizationMapDto();
        // 查找父-子
        if (CollectionUtils.isNotEmpty(orgIds)) {
            // 组织id - 父组织id
            HashMap<String, Long> pcMap = new HashMap<>();
            // 父组织集合
            List<Organization> buList = new ArrayList<>();
            for (Long orgId : orgIds) {
                // 查找父组织
                List<Organization> organizations = this.baseMapper.getFatherByChild(orgId);
                if (CollectionUtils.isNotEmpty(organizations)) {
                    // 父组织
                    Organization organization = organizations.get(0);
                    pcMap.put(String.valueOf(orgId), organization.getOrganizationId());
                    // 把事业部下所有的ouID找出来
                    List<Long> ouIds = this.baseMapper.queryOuIdByBuId(organization.getOrganizationId());
                    if (null != ouIds) {
                        organization.setOuIds(ouIds);
                        buList.add(organization);
                    }
                }
            }
            organizationMapDto.setPcMap(pcMap);
            if (CollectionUtils.isNotEmpty(buList)) {
                // buList去重
                ArrayList<Organization> collect = buList.stream().collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Organization::getOrganizationId))), ArrayList::new
                        )
                );
                organizationMapDto.setBuList(collect);
            }
        }
        return organizationMapDto;
    }

    @Override
    public List<Organization> ceeaListAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ORGANIZATION_TYPE_CODE", "OU");
        return this.list(wrapper);
    }

    @Override
    public List<DeptDto> getAllDeptByOrganization(Organization organization) {
        List<DeptDto> deptDtos = new ArrayList<>();
        if (organization.getOrganizationId() != null) {
            Organization byId = this.getById(organization.getOrganizationId());
            if (StringUtils.isNotBlank(byId.getCompany())) {
                DeptDto deptDto = new DeptDto();
                deptDto.setCompany(byId.getCompany());
                deptDto.setOrgId(organization.getOrganizationId());
                deptDtos = iVirtualDepartService.getAll(deptDto);
            }
        }
        return deptDtos;
    }

    @Override
    public List<Organization> listAllOrganizationForImport() {
        return organizationMapper.listAllOrganizationForImport();
    }

    @Override
    public List<Organization> listOrganizationByOrgCode(OrganizationParamDto organizationParamDto) {
        Assert.notNull(organizationParamDto.getOrganizationTypeCode(), "组织类型不能为空");
        Assert.notNull(organizationParamDto.getParentOrganizationId(), "组织父id不能为空");
        String parentOrganizationId = organizationParamDto.getParentOrganizationId();
        List<Organization> organizationList = null;
        if (StringUtil.notEmpty(organizationParamDto.getUserId())) {
            // 查询用户用权限的指定类型的的组织
            organizationList = this.baseMapper.listOrganizationByOrgCode(organizationParamDto);
        } else {
            // 查找全部
            QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORGANIZATION_TYPE_CODE", organizationParamDto.getOrganizationTypeCode());
            queryWrapper.ne("DIVISION_ID", "LONGI_NEW-ENERGY");//新能源
            queryWrapper.ne("DIVISION_ID", "CLEAN_ENERGY");//清洁能源
            queryWrapper.isNotNull("DIVISION_ID");
            organizationList = this.list(queryWrapper);
        }
        List<Organization> result = new ArrayList<>();
        // 检查是否指定组织下的组织
        String parentOrgId = organizationParamDto.getParentOrganizationId();
        if (CollectionUtils.isNotEmpty(organizationList)) {
            String parentIds = organizationList.get(0).getParentOrganizationIds();
            if (StringUtil.notEmpty(parentIds)) {
                for (Organization organization : organizationList) {
                    // 父id集
                    String parentOrganizationIds = organization.getParentOrganizationIds();
                    setResultOrg(result, parentOrgId, organization, parentOrganizationIds);
                }
            } else {
                // 为顶层节点没有父id. 在判断父id是否为-1
                if ("-1".equals(parentOrganizationId) && CollectionUtils.isNotEmpty(organizationList)) {
                    result = organizationList;
                }
            }
        }
        ArrayList<Organization> collect = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            // 去重
            collect = result.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Organization::getOrganizationId))), ArrayList::new
                    )
            );
        }
        return collect;
    }

    @Override
    public Map<Long, List<DeptDto>> getAllDeptByOrganizations(List<Long> organizationIds) {
        Map<Long, List<DeptDto>> hashMap = new HashMap<>();
        organizationIds.forEach(o -> {
            List<DeptDto> allDeptByOrganization = getAllDeptByOrganization(new Organization().setOrganizationId(o));
            hashMap.put(o, allDeptByOrganization);
        });
        return hashMap;
    }
}
