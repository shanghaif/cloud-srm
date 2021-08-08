package com.midea.cloud.srm.ps.invoice.service.impl;

import com.midea.cloud.common.enums.supcooperate.InvoiceNoticeStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import com.midea.cloud.srm.ps.invoice.service.IInvoiceNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  开票通知表 服务实现类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/12 10:25
 *  修改内容:
 * </pre>
 */
@Service
public class InvoiceNoticeServiceImpl implements IInvoiceNoticeService {

    @Autowired
    private SupcooperateClient supcooperateClient;

    @Override
    public void submit(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        checkBeforeSubmit(invoiceNoticeSaveDTO);
        supcooperateClient.submit(invoiceNoticeSaveDTO);
    }

    @Override
    public Long saveTemporary(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
//        checkBeforeSaveTemporary(invoiceNoticeSaveDTO);
        return supcooperateClient.saveTemporary(invoiceNoticeSaveDTO);
    }

//    private void checkBeforeSaveTemporary(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
//        InvoiceNotice invoiceNotice = invoiceNoticeSaveDTO.getInvoiceNotice();
//        List<InvoicePunish> invoicePunishes = invoiceNoticeSaveDTO.getInvoicePunishes();
//        if (!CollectionUtils.isEmpty(invoicePunishes)) {
//            BigDecimal actualAssessmentTotalAmountY = BigDecimal.ZERO;
//            for (InvoicePunish invoicePunish : invoicePunishes) {
//                if (invoicePunish == null) continue;
//                actualAssessmentTotalAmountY = actualAssessmentTotalAmountY.add(invoicePunish.getActualAssessmentAmountY());
//            }
//        }
//    }


    private void checkBeforeSubmit(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        InvoiceNotice invoiceNotice = invoiceNoticeSaveDTO.getInvoiceNotice();
        List<InvoiceDetail> invoiceDetails = invoiceNoticeSaveDTO.getInvoiceDetails();
        Assert.notNull(invoiceNotice, LocaleHandler.getLocaleMsg("invoiceNotice must not null"));
        Assert.notEmpty(invoiceDetails, LocaleHandler.getLocaleMsg("开票通知明细不能为空,请检查!"));
        if (!InvoiceNoticeStatus.DRAFT.name().equals(invoiceNotice.getInvoiceNoticeStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("单据状态不为新建,请先操作保存,再提交!"));
        }
    }
}
