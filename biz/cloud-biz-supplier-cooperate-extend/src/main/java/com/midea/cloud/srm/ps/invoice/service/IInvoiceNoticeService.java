package com.midea.cloud.srm.ps.invoice.service;

import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;

/**
 *  <pre>
 *  开票通知表 服务类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-12 10:05:03
 *  修改内容:
 * </pre>
 */
public interface IInvoiceNoticeService {

    void submit(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    Long saveTemporary(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    void abandon(InvoiceNotice invoiceNotice);
}
