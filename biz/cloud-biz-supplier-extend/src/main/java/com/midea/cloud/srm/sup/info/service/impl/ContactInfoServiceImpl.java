package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.ContactInfoMapper;
import com.midea.cloud.srm.sup.info.service.IContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  联系人信息 服务实现类
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:55:06
 *  修改内容:
 * </pre>
 */
@Service
public class ContactInfoServiceImpl extends ServiceImpl<ContactInfoMapper, ContactInfo> implements IContactInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    @Transactional
    public void saveOrUpdateContact(ContactInfo contactInfo, Long companyId) {
        contactInfo.setCompanyId(companyId);
        if (contactInfo.getContactInfoId() != null) {
            contactInfo.setLastUpdateDate(new Date());
        } else {
            contactInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            contactInfo.setContactInfoId(id);
        }
        this.saveOrUpdate(contactInfo);
        if (null != contactInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(contactInfo.getDimFieldContexts())) {
            dimFieldContextService.saveOrUpdateList(contactInfo.getDimFieldContexts(), contactInfo.getContactInfoId(), companyId);
        }
    }

    @Override
    public List<ContactInfo> getByCompanyId(Long companyId) {
        QueryWrapper<ContactInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID", companyId);
        List<ContactInfo> contactInfos = companyId != null ? this.list(queryWrapper) : null;
//        ceea,隆基不需要
//        if(!CollectionUtils.isEmpty(contactInfos)) {
//            for(ContactInfo contactInfo:contactInfos){
//                Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(contactInfo.getContactInfoId());
//                contactInfo.setDimFieldContexts(dimFieldContexts);
//            }
//
//        }
        return contactInfos;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<ContactInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID", companyId);
        this.remove(wrapper);
    }

    @Override
    public List<ContactInfo> listContactInfoByParam(List<Long> vendorIdList) {
        QueryWrapper<ContactInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("COMPANY_ID", vendorIdList);
        return list(queryWrapper);
    }

    @Override
    public ContactInfo getContactInfoByCompanyId(Long companyId) {
        QueryWrapper queryWrapper = new QueryWrapper<ContactInfo>();
        queryWrapper.eq("COMPANY_ID", companyId);
        //查询默认联系人
        queryWrapper.eq("CEEA_DEFAULT_CONTACT", "Y");
        queryWrapper.eq("IF_PUSH_ERP", "N");
        List<ContactInfo> contactInfoList = this.list(queryWrapper);
        ContactInfo contactInfo = new ContactInfo();
        if (!contactInfoList.isEmpty()){
            contactInfo = contactInfoList.get(0);
        }
        return contactInfo;
    }

    @Override
    public void saveOrUpdateContact(ContactInfo contactInfo) {
        if (Objects.nonNull(contactInfo.getContactInfoId()))
            this.updateById(contactInfo);
    }

    @Override
    public List<ContactInfo> getContactInfosByParam(ContactInfo contactInfo) {
        QueryWrapper<ContactInfo> queryWrapper = new QueryWrapper<>(contactInfo);
        List<ContactInfo> contactInfos = this.list(queryWrapper);
        return org.apache.commons.collections4.CollectionUtils.isNotEmpty(contactInfos)?contactInfos:null;
    }
}
