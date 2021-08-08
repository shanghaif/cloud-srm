package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.BankInfoChange;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.sup.change.mapper.BankInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IBankInfoChangeService;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  银行信息变更 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:37
 *  修改内容:
 * </pre>
 */
@Service
public class BankInfoChangeServiceImpl extends ServiceImpl<BankInfoChangeMapper, BankInfoChange> implements IBankInfoChangeService {

    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public List<BankInfoChange> getByChangeId(Long chanageId) {
        BankInfoChange querybankInfoChange = new BankInfoChange();
        querybankInfoChange.setChangeId(chanageId);
        List<BankInfoChange> bankInfoChanges = chanageId != null ? this.list(new QueryWrapper<>(querybankInfoChange)) : null;
//        for (BankInfoChange bankInfoChange : bankInfoChanges) {
//            if (bankInfoChange != null) {
//                Map<String, Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(bankInfoChange.getBankChangeId());
//                bankInfoChange.setDimFieldContexts(dimFieldContexts);
//            }
//        }
        return bankInfoChanges;
    }

    @Override
    public void saveOrUpdateBank(BankInfoChange bankInfoChange, Long companyId, Long changeId) {
        bankInfoChange.setChangeId(changeId);
        bankInfoChange.setCompanyId(companyId);
        if (bankInfoChange.getBankChangeId() != null) {
            this.updateById(bankInfoChange);
        } else {
            Long id = IdGenrator.generate();
            bankInfoChange.setBankChangeId(id);
            if (bankInfoChange.getBankInfoId() == null) {
                bankInfoChange.setBankInfoId(id);
            }
            this.save(bankInfoChange);
        }
//        if (!CollectionUtils.isEmpty(bankInfoChange.getDimFieldContexts())) {
//            iDimFieldContextChangeService.saveOrUpdateList(bankInfoChange.getDimFieldContexts(),
//                    bankInfoChange.getBankChangeId(),
//                    bankInfoChange.getBankInfoId(),
//                    bankInfoChange.getCompanyId(),
//                    bankInfoChange.getChangeId()
//            );
//        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        BankInfoChange deleteBank = new BankInfoChange();
        deleteBank.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteBank));
    }

    /**
     * 删除供应商银行变更信息
     * modifiedBy xiexh12@meicloud.com
     * @param bankChangeId
     */
    @Override
    public void removeByBankChangeId(Long bankChangeId) {
        QueryWrapper<BankInfoChange> queryWrapper = new QueryWrapper<>(
                new BankInfoChange().setBankChangeId(bankChangeId)
        );
        this.remove(queryWrapper);
    }
}
