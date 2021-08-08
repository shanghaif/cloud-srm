package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.ContactInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.FinanceInfoChange;
import com.midea.cloud.srm.sup.change.mapper.FinanceInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IFinanceInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  财务信息变更 服务实现类
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
public class FinanceInfoChangeServiceImpl extends ServiceImpl<FinanceInfoChangeMapper, FinanceInfoChange> implements IFinanceInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public List<FinanceInfoChange> getByChangeId(Long chanageId) {
        FinanceInfoChange queryFinanceInfoChange = new FinanceInfoChange();
        queryFinanceInfoChange.setChangeId(chanageId);
        List<FinanceInfoChange> financeInfoChanges = chanageId != null?this.list(new QueryWrapper<>(queryFinanceInfoChange)):null;
        for(FinanceInfoChange financeInfoChange:financeInfoChanges){
            if(financeInfoChange != null){
                Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(financeInfoChange.getFinanceChangeId());
                financeInfoChange.setDimFieldContexts(dimFieldContexts);
            }
        }
        return financeInfoChanges;
    }

    @Override
    public void saveOrUpdateFinance(FinanceInfoChange financeInfoChange, Long companyId, Long changeId) {
        financeInfoChange.setChangeId(changeId);
        financeInfoChange.setCompanyId(companyId);
        if(financeInfoChange.getFinanceChangeId() != null){
            this.updateById(financeInfoChange);
        }else {
            Long id = IdGenrator.generate();
            financeInfoChange.setFinanceChangeId(id);
            if(financeInfoChange.getFinanceInfoId() ==null){
                financeInfoChange.setFinanceInfoId(id);
            }
            this.save(financeInfoChange);
        }
        if(!CollectionUtils.isEmpty(financeInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(financeInfoChange.getDimFieldContexts(),
                    financeInfoChange.getFinanceChangeId(),
                    financeInfoChange.getFinanceInfoId(),
                    financeInfoChange.getCompanyId(),
                    financeInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        FinanceInfoChange deleteFinance = new FinanceInfoChange();
        deleteFinance.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteFinance));
    }
}
