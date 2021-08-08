package com.midea.cloud.srm.base.organization.service.impl;

import java.sql.BatchUpdateException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.organization.mapper.OrganizationsTypeMapper;
import com.midea.cloud.srm.base.organization.service.IOrganizationsTypeService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;

/**
*  <pre>
 *  组织类型 服务实现类
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
@Service
public class OrganizationsTypeServiceImpl extends ServiceImpl<OrganizationsTypeMapper, OrganizationsType> implements IOrganizationsTypeService {

	@Autowired
	private OrganizationsTypeMapper organizationsTypeMapper;
	 
    @Override
    @Transactional
    public void batchSaveOrUpdateOrganizationsType(List<OrganizationsType> organizationsTypes) {
        if (!CollectionUtils.isEmpty(organizationsTypes)) {
            for (OrganizationsType organizationsType : organizationsTypes) {
                if (organizationsType == null) continue;
                Assert.hasText(organizationsType.getOrganizationTypeName(), "组织类型名称不能为空");
                Assert.hasText(organizationsType.getOrganizationTypeCode(), "组织类型编码不能为空");
                if (organizationsType.getTypeId() == null) {
                    Long id = IdGenrator.generate();
                    organizationsType.setTypeId(id);
                    organizationsType.setCreationDate(new Date());
                    organizationsType.setLastUpdateDate(new Date());
                } else {
                    organizationsType.setLastUpdateDate(new Date());
                }
            }
        }
        try {
            this.saveOrUpdateBatch(organizationsTypes);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause instanceof BatchUpdateException) {
                String errMsg = ((BatchUpdateException) cause).getMessage();
                if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("INDEX_ORGANIZATION_TYPE_NAME") != -1) {
                    throw new BaseException("组织类型名称已存在,不允许重新维护");
                } else if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("INDEX_ORGANIZATION_TYPE_CODE") != -1) {
                    throw new BaseException("组织类型编码已存在,不允许重新维护");
                }
            }
        }
    }
    
    @Override
    public List<OrganizationsType> getOrgTypeByUser(Long userId) {
        List<OrganizationsType> orgTypeList = organizationsTypeMapper.getOrgTypeByUser(userId);
        if (CollectionUtils.isEmpty(orgTypeList)) {
            return null;
        }
        if (orgTypeList.size()>1) {
        	orgTypeList.get(1).setIsDefault(true);
        } else {
        	orgTypeList.get(0).setIsDefault(true);
        }
        
        return orgTypeList;
    }
}
