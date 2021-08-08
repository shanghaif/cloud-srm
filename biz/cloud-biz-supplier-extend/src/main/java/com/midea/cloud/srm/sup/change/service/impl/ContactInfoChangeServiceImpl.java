package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.BankInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.BusinessInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.ContactInfoChange;
import com.midea.cloud.srm.sup.change.mapper.ContactInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IContactInfoChangeService;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  联系人信息变更表 服务实现类
 * </pre>
 *
 * @author zhuwl7@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 13:57:35
 *  修改内容:
 * </pre>
 */
@Service
public class ContactInfoChangeServiceImpl extends ServiceImpl<ContactInfoChangeMapper, ContactInfoChange> implements IContactInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public ContactInfoChange saveOrUpdateContact(ContactInfoChange contactInfoChange, Long companyId, Long changeId) {
        contactInfoChange.setCompanyId(companyId);
        contactInfoChange.setChangeId(changeId);
        if(contactInfoChange.getContactChangeId() != null){
            contactInfoChange.setLastUpdateDate(new Date());
        }else{
            contactInfoChange.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            contactInfoChange.setContactChangeId(id);
        }
        if(contactInfoChange.getContactInfoId() == null){
            Long id = IdGenrator.generate();
            contactInfoChange.setContactInfoId(id);
        }
        this.saveOrUpdate(contactInfoChange);
//        if(!CollectionUtils.isEmpty(contactInfoChange.getDimFieldContexts())){
//            iDimFieldContextChangeService.saveOrUpdateList(contactInfoChange.getDimFieldContexts(),
//                    contactInfoChange.getContactChangeId(),
//                    contactInfoChange.getContactInfoId(),
//                    companyId,
//                    changeId);
//        }
        return contactInfoChange;
    }

    @Override
    public List<ContactInfoChange> getByChangeId(Long chanageId) {
        ContactInfoChange queryContact = new ContactInfoChange();
        queryContact.setChangeId(chanageId);
        List<ContactInfoChange> contactInfoChanges = chanageId != null?this.list(new QueryWrapper<>(queryContact)):null;
//        for(ContactInfoChange contactInfoChange:contactInfoChanges){
//            if(contactInfoChange != null){
//                Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(contactInfoChange.getChangeId());
//                contactInfoChange.setDimFieldContexts(dimFieldContexts);
//            }
//        }
        return contactInfoChanges;
    }

    @Override
    public void removeByChangeId(Long changeId) {
        ContactInfoChange deleteContact = new ContactInfoChange();
        deleteContact.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteContact));
    }

    /**
     * 删除供应商联系人信息
     * modifiedBy xiexh12@meicloud.com
     * @param contactChangeId
     */
    @Override
    public void removeByContactChangeId(Long contactChangeId) {
        QueryWrapper<ContactInfoChange> queryWrapper = new QueryWrapper<>(
                new ContactInfoChange().setContactChangeId(contactChangeId)
        );
        this.remove(queryWrapper);
    }
}
