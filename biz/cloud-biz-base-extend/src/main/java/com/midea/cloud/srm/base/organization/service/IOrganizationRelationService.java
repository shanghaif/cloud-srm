package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;

import java.util.List;

/**
 * <pre>
 *  组织关系 服务类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:48:06
 *  修改内容:
 * </pre>
 */
public interface IOrganizationRelationService extends IService<OrganizationRelation> {



    /**
     * 根据关系ID与组织ID查找下级组织
     *
     * @param organizationId
     * @return
     */
    List<OrganizationRelation> listChildrenOrganization(Long organizationId);

    /**
     * 通过父节点组装组织架构树
     *
     * @param organizationRelation
     * @return
     */
    List<OrganizationRelation> assembleTreeByParent(OrganizationRelation organizationRelation);

    /**
     * 通过子节点组装组织架构树
     *
     * @param organizationRelation
     * @return
     */
    List<OrganizationRelation> assembleTreeByChild(OrganizationRelation organizationRelation);

    List<OrganizationRelation> selectTreeByType(String organizationTypeCode);

    List<OrganizationRelation> assembleTreeByParentNew(OrganizationRelation organizationRelation);
}
