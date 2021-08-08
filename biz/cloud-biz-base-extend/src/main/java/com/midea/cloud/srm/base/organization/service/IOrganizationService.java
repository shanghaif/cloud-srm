package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.organization.dto.OrgTypeDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationDTO;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationMapDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationParamDto;
import com.midea.cloud.srm.model.base.organization.entity.Organization;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织信息 服务类
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
public interface IOrganizationService extends IService<Organization> {

    /**
     * 新增组织和组织关系
     * @param organization
     */
    void saveOrUpdateOrganization(Organization organization);

    /**
     * 新增组织和组织关系(erp推送库存组织/业务实体用)
     * @param organization
     */
    void saveOrUpdateOrganizationForErp(Organization organization);

    /**
     * 根据组织ID获取组织和组织关系
     * @param id
     * @return
     */
    OrganizationDTO getOrganizationDTOById(Long id);

    /**
     * 根据条件查询组织
     * @param organization
     * @return
     */
    Organization getOrganization(Organization organization);

    /**
     * 分页查询全部组织信息
     *
     * @return
     */
    PageInfo<Organization> listAllOrganization(Organization organization);

    /**
     * 获取组织信息
     * @param id
     * @return
     */
    Organization get(Long id);

    /**
     * 根据组织名称列表查询组织信息列表
     * @param orgNameList
     * @return
     */
    List<Organization> getOrganizationByNameList(List<String> orgNameList);

    /**
     * 根据ID查询全部记录
     * @param organizationIdList
     * @return
     */
    List<Organization> listOrganization(List<Long> organizationIdList, String organizationTypeCode);

    /**
     * 查找指定组织下用户有权限的指定组织类型的组织
     * @param organizationParamDto
     * @return
     */
    List<Organization> getOrganizationByOrgCode(OrganizationParamDto organizationParamDto);

    /**
     * 查找组织类型
     * @return
     */
    List<OrgTypeDto> getOrgTypeDtoList();

    /**
     * 新增多条组织和组织关系
     * @param organizationList
     */
    void saveOrUpdateOrganization(List<Organization> organizationList);

    /**
     * 新增多条组织和组织关系(erp推送库存组织/业务实体用)
     * @param organizationList
     */
    void saveOrUpdateOrganizationForErp(List<Organization> organizationList);
    /**
     * 根据子id获取父列表
     * @param orgIds
     * @return
     */
    OrganizationMapDto getFatherByChild(List<Long> orgIds);

    /**
     * 通过组织获取全部部门(包括虚拟部门,结算用)
     * @param organization
     * @return
     */
    List<DeptDto> getAllDeptByOrganization(Organization organization);
    /**
     * 查询所有的库存组织
     * @return
     */
    List<Organization> ceeaListAll();

    /**
     * 查询所有业务实体，库存组织(导入价格库)
     * @return
     */
    List<Organization> listAllOrganizationForImport();

    List<Organization> listOrganizationByOrgCode(OrganizationParamDto organizationParamDto);

    Map<Long, List<DeptDto>> getAllDeptByOrganizations(List<Long> organizationIds);
}
