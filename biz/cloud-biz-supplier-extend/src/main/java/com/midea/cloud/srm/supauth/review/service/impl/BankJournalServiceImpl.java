package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.FormType;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplierauth.review.entity.BankJournal;
import com.midea.cloud.srm.supauth.review.mapper.BankJournalMapper;
import com.midea.cloud.srm.supauth.review.service.IBankJournalService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  银行信息日志表 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:37
 *  修改内容:
 * </pre>
*/
@Service
public class BankJournalServiceImpl extends ServiceImpl<BankJournalMapper, BankJournal> implements IBankJournalService {

    @Autowired
    BankJournalMapper bankJournalMapper;

    @Autowired
    SupplierClient supplierClient;


    @Override
    public List<BankJournal> listBankJournal(Long reviewFormId, Long vendorId) {
        List<BankJournal> results = new ArrayList<>();
        if (reviewFormId != null) {
            QueryWrapper<BankJournal> queryWrapper = new QueryWrapper<>(
                    new BankJournal().setFormId(reviewFormId).setFormType(FormType.REVIEW_FORM.toString()));
            results = bankJournalMapper.selectList(queryWrapper);
        } else {
            //没生成资质审查单,则查注册表信息
            if (vendorId != null) {
                List<BankInfo> bankInfos = supplierClient.getByBankInfoCompanyId(vendorId);
                if (CollectionUtils.isNotEmpty(bankInfos)) {
                    for (BankInfo bankInfo : bankInfos) {
                        if (bankInfo == null) continue;
                        BankJournal bankJournal = new BankJournal();
                        BeanUtils.copyProperties(bankInfo, bankJournal);
                        bankJournal.setVendorId(vendorId)
                                .setCeeaBankInfoId(bankInfo.getBankInfoId());
                        results.add(bankJournal);
                    }
                }
            }
        }
        return results;
    }
}
