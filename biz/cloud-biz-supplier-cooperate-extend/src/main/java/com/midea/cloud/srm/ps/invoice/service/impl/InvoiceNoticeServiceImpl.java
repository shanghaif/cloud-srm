package com.midea.cloud.srm.ps.invoice.service.impl;

import com.midea.cloud.common.enums.supcooperate.InvoiceNoticeStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.ps.invoice.service.IInvoiceNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    private com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeService scIInvoiceNoticeService;

    @Override
    public void submit(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        checkBeforeSubmit(invoiceNoticeSaveDTO);
        scIInvoiceNoticeService.submit(invoiceNoticeSaveDTO);
    }

    @Override
    public Long saveTemporary(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
//        checkBeforeSaveTemporary(invoiceNoticeSaveDTO);
        return scIInvoiceNoticeService.saveTemporary(invoiceNoticeSaveDTO);
    }

    @Override
    public void abandon(InvoiceNotice invoiceNotice) {
        if (invoiceNotice.getInvoiceNoticeId() != null) {
            checkBeforeAbandon(invoiceNotice);
            scIInvoiceNoticeService.abandon(invoiceNotice);
        }
    }

    private void checkBeforeAbandon(InvoiceNotice invoiceNotice) {
        InvoiceNotice byId = scIInvoiceNoticeService.getById(invoiceNotice.getInvoiceNoticeId());
        if (null != byId
                && (!InvoiceNoticeStatus.DRAFT.name().equals(byId.getInvoiceNoticeStatus())
                && !InvoiceNoticeStatus.REJECTED.name().equals(byId.getInvoiceNoticeStatus()))) {
            throw new BaseException(LocaleHandler.getLocaleMsg("单据状态为新建或驳回,才可操作作废"));
        }
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
