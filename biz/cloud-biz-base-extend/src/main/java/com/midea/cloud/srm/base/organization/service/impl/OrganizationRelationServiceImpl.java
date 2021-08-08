package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.EncryptUtil;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationRelationMapper;
import com.midea.cloud.srm.base.organization.service.IOrganizationRelationService;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <pre>
 *  组织关系 服务实现类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:48:06
 *  修改内容:
 * </pre>
 */
@Service
public class OrganizationRelationServiceImpl extends ServiceImpl<OrganizationRelationMapper, OrganizationRelation> implements IOrganizationRelationService {

    @Resource
    OrganizationRelationMapper organizationRelationMapper;

    @Resource
    OrganizationMapper organizationMapper;

    @Override
    public List<OrganizationRelation> listChildrenOrganization(Long organizationId) {
        QueryWrapper<OrganizationRelation> queryWrapper = new QueryWrapper<>(new OrganizationRelation()
                .setParentOrganizationId(organizationId));
        List<OrganizationRelation> childernOrganizationRelations = organizationRelationMapper.selectList(queryWrapper);
        for (OrganizationRelation childrenOrganizationRelation : childernOrganizationRelations) {
            if (childrenOrganizationRelation == null) continue;
            Organization parentOrganization = organizationMapper.selectById(childrenOrganizationRelation.getOrganizationId());
            Organization childrenOrganization = organizationMapper.selectById(childrenOrganizationRelation.getOrganizationId());
            if (parentOrganization != null) {
                childrenOrganizationRelation.setParentOrganizationName(parentOrganization.getOrganizationName())
                        .setParentOrganizationCode(parentOrganization.getOrganizationCode())
                        .setOrganizationName(childrenOrganization.getOrganizationName())
                        .setOrganizationCode(childrenOrganization.getOrganizationCode());
            }
        }
        return childernOrganizationRelations;
    }

    @Override
    public List<OrganizationRelation> assembleTreeByParent(OrganizationRelation organizationRelation) {
        OrganizationRelation queryOrganizationRelation = new OrganizationRelation();
        List<OrganizationRelation> organizationRelations = this.baseMapper.selectByParam(queryOrganizationRelation);
        if (organizationRelation.getParentOrganizationId() == null) {
            organizationRelation.setParentOrganizationId(SysConstant.TREE_PARENT_ID);
            Long parentId = organizationRelation.getOrganizationId();
            setFullPathId(organizationRelation, String.valueOf(organizationRelation.getParentOrganizationId()), parentId);
        }
        return assemble(organizationRelations, organizationRelation.getParentOrganizationId());
    }

    /**
     * 组装所有的节点
     *
     * @param organizationRelations
     * @param parentOrganizationId
     * @return
     */
    public List<OrganizationRelation> assemble(List<OrganizationRelation> organizationRelations, Long parentOrganizationId) {
        List<OrganizationRelation> endPointOrganizationRelationTree = new ArrayList<OrganizationRelation>();
        // 保存当前节点的组织信息
        organizationRelations.forEach(organizationRelation -> {
            if (organizationRelation.getParentOrganizationId().compareTo(parentOrganizationId) == 0) {
                Long parentId = organizationRelation.getOrganizationId();
                // 添加子节点组织信息
                List<OrganizationRelation> childOrganizationRelation = assemble(organizationRelations, parentId);
                childOrganizationRelation = (List<OrganizationRelation>) ObjectUtil.deepCopy(childOrganizationRelation);
                organizationRelation.setChildOrganRelation(childOrganizationRelation);
                organizationRelation = (OrganizationRelation) ObjectUtil.deepCopy(organizationRelation);
                endPointOrganizationRelationTree.add(organizationRelation);
            }
        });
        return endPointOrganizationRelationTree;
    }

    @Override
    public List<OrganizationRelation> selectTreeByType(String organizationTypeCode) {
        List<OrganizationRelation> orgs = new ArrayList<>();
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        List<OrganizationUser> OrgUsers = appUser.getOrganizationUsers();
        Assert.hasLength(organizationTypeCode, LocaleHandler.getLocaleMsg("组织类型不能为空"));
        OrganizationRelation queryOrganizationRelation = new OrganizationRelation();
        // 查找所有的组织架构关系集
        List<OrganizationRelation> organizationAllRelations = this.baseMapper.selectByParam(queryOrganizationRelation);
        List<String> fullPathIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(OrgUsers)) {
            for (OrganizationUser organizationUser : OrgUsers) {
                fullPathIds.add(organizationUser.getFullPathId());
            }
            List<OrganizationRelation> orgHasRights = this.commonAssembleTree(organizationAllRelations,
                    new OrganizationRelation()
//                            .setOrganizationId(organizationUser.getOrganizationId())
                            .setUserId(OrgUsers.get(0).getUserId()), fullPathIds);
            if (!CollectionUtils.isEmpty(orgHasRights)) {
                List<OrganizationRelation> collects = orgHasRights.stream().filter(distinctByKey(OrganizationRelation::getFullPathId))
                        .filter(orghasRight -> organizationTypeCode.equals(orghasRight.getOrganizationTypeCode()))
                        .collect(Collectors.toList());
                orgs.addAll(collects);
            }
        }
        return orgs;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<OrganizationRelation> assembleTreeByParentNew(OrganizationRelation organizationRelation) {
        OrganizationRelation queryOrganizationRelation = new OrganizationRelation();
        List<OrganizationRelation> organizationRelations = this.baseMapper.selectByParam(queryOrganizationRelation);
        if (organizationRelation.getParentOrganizationId() == null) {
            organizationRelation.setParentOrganizationId(SysConstant.TREE_PARENT_ID);
            Long parentId = organizationRelation.getOrganizationId();
            setFullPathId(organizationRelation, String.valueOf(organizationRelation.getParentOrganizationId()), parentId);
        }
        return assembleNew(organizationRelations, organizationRelation.getParentOrganizationId(), String.valueOf(organizationRelation.getParentOrganizationId()));
    }

    private List<OrganizationRelation> assembleNew(List<OrganizationRelation> organizationRelations, Long parentOrganizationId, String parentSignId) {
        List<OrganizationRelation> endPointOrganizationRelationTree = new ArrayList<OrganizationRelation>();
        // 保存当前节点的组织信息
        organizationRelations.forEach(organizationRelation -> {
            if (organizationRelation.getParentOrganizationId().compareTo(parentOrganizationId) == 0) {
                Long parentId = organizationRelation.getOrganizationId();
                //添加子节点全路径唯一ID
                String currentSignId = setFullPathId(organizationRelation, parentSignId, parentId);
                // 添加子节点组织信息
                List<OrganizationRelation> childOrganizationRelation = assembleNew(organizationRelations, parentId, currentSignId);
                childOrganizationRelation = (List<OrganizationRelation>) ObjectUtil.deepCopy(childOrganizationRelation);
                organizationRelation.setChildOrganRelation(childOrganizationRelation);
                organizationRelation = (OrganizationRelation) ObjectUtil.deepCopy(organizationRelation);
                endPointOrganizationRelationTree.add(organizationRelation);
            }
        });
        return endPointOrganizationRelationTree;
    }

    private String setFullPathId(OrganizationRelation organizationRelation, String parentSignId, Long currentId) {
        String md5ParentOrganizationId = EncryptUtil.getMD5(parentSignId + currentId);
        organizationRelation.setFullPathId(md5ParentOrganizationId);
        return md5ParentOrganizationId;
//        if (SysConstant.TREE_PARENT_ID.compareTo(organizationRelation.getParentOrganizationId()) == 0) {
//            //使用MD5加密压缩长度,并且保持唯一性
//            String md5ParentOrganizationId = EncryptUtil.getMD5("" + SysConstant.TREE_PARENT_ID);
//            String fullPathId = EncryptUtil.getMD5(md5ParentOrganizationId + organizationRelation.getOrganizationId());
//            organizationRelation.setFullPathId(fullPathId);
//        }else {
//            organizationRelations.forEach(relation -> {
//                if (relation.getOrganizationId().compareTo(organizationRelation.getParentOrganizationId()) == 0) {
//                    //使用MD5加密压缩长度,并且保持唯一性
//                    String fullPathId = EncryptUtil.getMD5(relation.getFullPathId() + organizationRelation.getOrganizationId());
//                    organizationRelation.setFullPathId(fullPathId);
//                }
//            });
//        }
    }

    public List<OrganizationRelation> assembleTreeByChild(OrganizationRelation organizationRelation) {
        OrganizationRelation queryOrganizationRelation = new OrganizationRelation();
        // 查找所有的组织架构关系集
        List<OrganizationRelation> organizationAllRelations = this.baseMapper.selectByParam(queryOrganizationRelation);
        // 通过查询条件查询相应的组织架构关系集
        queryOrganizationRelation = organizationRelation;
        List<OrganizationRelation> organizationConditionRelations = this.baseMapper.selectByParam(queryOrganizationRelation);
        // 由下往上查找所有的父节点并组装成集合
        List<OrganizationRelation> multipleLinkOutermostTree = new ArrayList<OrganizationRelation>(); // 多链路所有元素集合
        Set<String> checkoutDuplicat = new HashSet<String>(); // 检查重复
        if (CollectionUtils.isNotEmpty(organizationConditionRelations)) {
            Map<Long, OrganizationRelation> organizationRelationMap = organizationAllRelations.stream().collect(
                    Collectors.toMap(OrganizationRelation::getOrganizationId, Function.identity(), (v1, v2) -> v1));
            organizationConditionRelations.forEach(lastOne -> {
                Long lastOneParentOrganizationId = null; // 父组织关系ID
                while ((lastOneParentOrganizationId = lastOne.getParentOrganizationId()) != -1) { // 父组织关系ID不是根节点才迭代
                    putIfNotDuplicat(checkoutDuplicat, lastOne, multipleLinkOutermostTree); // 所有链路的子节点
                    lastOne = organizationRelationMap.get(lastOneParentOrganizationId); // 查找上一节点
                }
                putIfNotDuplicat(checkoutDuplicat, lastOne, multipleLinkOutermostTree); // 根节点
            });
        }
        if (organizationConditionRelations.size() > 0) {
            // 组装组织树
            return assemble(multipleLinkOutermostTree, SysConstant.TREE_PARENT_ID);
        }
        return Collections.EMPTY_LIST;
    }

    private List<OrganizationRelation> commonAssembleTree(List<OrganizationRelation> organizationAllRelations, OrganizationRelation organizationRelation, List<String> fullPathIds) {
        // 通过查询条件查询相应的组织架构关系集
        List<OrganizationRelation> organizationConditionRelations = this.baseMapper.selectByParamNew(organizationRelation, fullPathIds);
        // 由下往上查找所有的父节点并组装成集合
        List<OrganizationRelation> multipleLinkOutermostTree = new ArrayList<OrganizationRelation>(); // 多链路所有元素集合
        Set<String> checkoutDuplicat = new HashSet<String>(); // 检查重复
        if (CollectionUtils.isNotEmpty(organizationConditionRelations)) {
            Map<Long, OrganizationRelation> organizationRelationMap = organizationAllRelations.stream().collect(
                    Collectors.toMap(OrganizationRelation::getOrganizationId, Function.identity(), (v1, v2) -> v1));
            organizationConditionRelations.forEach(lastOne -> {
                Long lastOneParentOrganizationId = null; // 父组织关系ID
                while ((lastOneParentOrganizationId = lastOne.getParentOrganizationId()) != -1) { // 父组织关系ID不是根节点才迭代
                    putIfNotDuplicat(checkoutDuplicat, lastOne, multipleLinkOutermostTree); // 所有链路的子节点
                    lastOne = organizationRelationMap.get(lastOneParentOrganizationId); // 查找上一节点
                }
                putIfNotDuplicat(checkoutDuplicat, lastOne, multipleLinkOutermostTree); // 根节点
            });
        }
        // 设置虚拟ID
        assembleNew(multipleLinkOutermostTree, SysConstant.TREE_PARENT_ID, SysConstant.TREE_PARENT_ID.toString());
        return multipleLinkOutermostTree;
    }

    /**
     * 如果没有重复放到多链路所有元素集合
     *
     * @param checkoutDuplicat
     * @param lastOne
     * @param multipleLinkOutermostTree
     */
    private void putIfNotDuplicat(Set<String> checkoutDuplicat, OrganizationRelation lastOne, List<OrganizationRelation> multipleLinkOutermostTree) {
        if (!checkoutDuplicat.contains(lastOne.getOrganizationId() + "" + lastOne.getRelId())) {
            checkoutDuplicat.add(lastOne.getOrganizationId() + "" + lastOne.getRelId());
            multipleLinkOutermostTree.add(lastOne);
        }
    }

}
