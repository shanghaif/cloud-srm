package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.BankInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.BusinessInfoChange;
import com.midea.cloud.srm.sup.change.mapper.BusinessInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IBusinessInfoChangeService;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
*  <pre>
 *  业务信息变更 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:38
 *  修改内容:
 * </pre>
*/
@Service
public class BusinessInfoChangeServiceImpl extends ServiceImpl<BusinessInfoChangeMapper, BusinessInfoChange> implements IBusinessInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public BusinessInfoChange getByChangeId(Long chanageId) {
        BusinessInfoChange queryBusinessInfoChange = new BusinessInfoChange();
        queryBusinessInfoChange.setChangeId(chanageId);
        BusinessInfoChange businessInfoChange =  chanageId != null?this.getOne(new QueryWrapper<>(queryBusinessInfoChange)):null;
        if(businessInfoChange != null){
            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(businessInfoChange.getBusinessChangeId());
            businessInfoChange.setDimFieldContexts(dimFieldContexts);
        }
        return businessInfoChange;
    }

    @Override
    public void saveOrUpdateBusiness(BusinessInfoChange businessInfoChange, Long companyId, Long changeId) {
        businessInfoChange.setChangeId(changeId);
        businessInfoChange.setCompanyId(companyId);
        if (businessInfoChange.getBusinessChangeId() != null) {
            this.updateById(businessInfoChange);
        } else {
            Long id = IdGenrator.generate();
            businessInfoChange.setBusinessChangeId(id);
            if (businessInfoChange.getBusinessInfoId() == null) {
                businessInfoChange.setBusinessInfoId(id);
            }
            this.save(businessInfoChange);
        }
        if(!CollectionUtils.isEmpty(businessInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(businessInfoChange.getDimFieldContexts(),
                    businessInfoChange.getBusinessChangeId(),
                    businessInfoChange.getBusinessInfoId(),
                    businessInfoChange.getCompanyId(),
                    businessInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        BusinessInfoChange deleteBusiness = new BusinessInfoChange();
        deleteBusiness.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteBusiness));
    }
}
