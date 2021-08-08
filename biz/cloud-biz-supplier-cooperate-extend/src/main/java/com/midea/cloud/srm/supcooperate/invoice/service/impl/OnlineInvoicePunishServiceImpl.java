package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoicePunish;
import com.midea.cloud.srm.supcooperate.invoice.mapper.OnlineInvoicePunishMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoicePunishService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoicePunishService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*  <pre>
 *  网上开票-扣罚&派利明细 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:49:25
 *  修改内容:
 * </pre>
*/
@Service
public class OnlineInvoicePunishServiceImpl extends ServiceImpl<OnlineInvoicePunishMapper, OnlineInvoicePunish> implements IOnlineInvoicePunishService {

    @Autowired
    private PerformanceClient performanceClient;

    @Autowired
    private IInvoicePunishService iInvoicePunishService;

    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;

    @Override
    @Transactional
    public void batchDelete(List<Long> onlineInvoicePunishIds) {
        this.removeByIds(onlineInvoicePunishIds);
        if (CollectionUtils.isNotEmpty(onlineInvoicePunishIds)) {
            Long id = onlineInvoicePunishIds.get(0);
            OnlineInvoicePunish onlineInvoicePunish = this.getById(id);
            String isService = "";
            if (onlineInvoicePunish != null) {
                OnlineInvoice onlineInvoice = iOnlineInvoiceService.getById(onlineInvoicePunish.getOnlineInvoiceId());
                isService = onlineInvoice.getIsService();
            }
            for (Long onlineInvoicePunishId : onlineInvoicePunishIds) {
                if (onlineInvoicePunishId == null) continue;
                OnlineInvoicePunish byId = this.getById(onlineInvoicePunishId);
                if (byId == null) continue;
                //非服务类
                if (YesOrNo.NO.getValue().equals(isService)) {
                    iInvoicePunishService.updateById(new InvoicePunish()
                            .setInvoicePunishId(byId.getInvoicePunishId()).setIfQuote(YesOrNo.NO.getValue()));
                }
                //服务类
                if (YesOrNo.YES.getValue().equals(isService)) {
                    performanceClient.modify(new VendorAssesForm()
                            .setVendorAssesId(byId.getVendorAssesId())
                            .setIfQuote(YesOrNo.NO.getValue())
                            .setStatus(VendorAssesFormStatus.ASSESSED.getKey()));
                }
            }
        }
    }
}
