package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.organization.mapper.InvOrganizationsMapper;
import com.midea.cloud.srm.base.organization.service.IInvOrganizationsService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  库存组织接口表 服务实现类
 * </pre>
*OrganizationServiceImplOrganizationServiceImpl
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
@Service
public class InvOrganizationsServiceImpl extends ServiceImpl<InvOrganizationsMapper, InvOrganization> implements IInvOrganizationsService {
    @Resource
    private IOrganizationService iOrganizationService;

    @Override
    public List<Organization> queryIvnByParent(Long parentId) {
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("PARENT_ORGANIZATION_IDS",parentId);
        queryWrapper.groupBy("ORGANIZATION_ID");
        List<Organization> organizationList = iOrganizationService.list(queryWrapper);
        return organizationList;
    }

    @Override
    public InvOrganization queryInvByInvCode(String invCode){
        InvOrganization invOrganization = new InvOrganization();
        invOrganization.setEsInvOrganizationCode(invCode);
        QueryWrapper<InvOrganization> invOrganizationQueryWrapper = new QueryWrapper<>(invOrganization);
        List<InvOrganization> resultInvOrganizationList = this.list(invOrganizationQueryWrapper);
        InvOrganization returnInvOrganization = new InvOrganization();
        if (!resultInvOrganizationList.isEmpty() && null != resultInvOrganizationList.get(0)) {
            returnInvOrganization = resultInvOrganizationList.get(0);
        }
        return returnInvOrganization;
    }
}
