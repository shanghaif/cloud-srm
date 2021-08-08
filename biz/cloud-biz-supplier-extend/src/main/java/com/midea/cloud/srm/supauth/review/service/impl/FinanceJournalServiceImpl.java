package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.FormType;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;
import com.midea.cloud.srm.model.supplierauth.review.entity.FinanceJournal;
import com.midea.cloud.srm.supauth.review.mapper.FinanceJournalMapper;
import com.midea.cloud.srm.supauth.review.service.IFinanceJournalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  资质审查财务信息日志表 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:03
 *  修改内容:
 * </pre>
*/
@Service
public class FinanceJournalServiceImpl extends ServiceImpl<FinanceJournalMapper, FinanceJournal> implements IFinanceJournalService {

    @Autowired
    FinanceJournalMapper financeJournalMapper;

    @Autowired
    SupplierClient supplierClient;

    @Override
    public List<FinanceJournal> listFinanceJournal(Long reviewFormId, Long vendorId) {
        List<FinanceJournal> results = new ArrayList<>();
        if (reviewFormId != null) {
            QueryWrapper<FinanceJournal> queryWrapper = new QueryWrapper<>(
                    new FinanceJournal().setFormId(reviewFormId).setFormType(FormType.REVIEW_FORM.toString()));
            results = financeJournalMapper.selectList(queryWrapper);
        } else {
            //没生成资质审查单,则查注册表信息
            if (vendorId != null) {
                List<FinanceInfo> financeInfos = supplierClient.getByFinanceInfoCompanyId(vendorId);
                for (FinanceInfo financeInfo : financeInfos) {
                    if (financeInfo == null) continue;
                    FinanceJournal financeJournal = new FinanceJournal();
                    BeanUtils.copyProperties(financeInfo, financeJournal);
                    financeJournal.setVendorId(vendorId);
                    results.add(financeJournal);
                }
            }
        }
        return results;
    }
}
