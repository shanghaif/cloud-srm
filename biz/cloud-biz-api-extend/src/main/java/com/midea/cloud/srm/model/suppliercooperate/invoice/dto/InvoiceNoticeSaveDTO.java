package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称描述:
 * </pre>
 *
 * @author @meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-27 16:12
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class InvoiceNoticeSaveDTO extends BaseDTO{

    private InvoiceNotice invoiceNotice;

    private List<InvoiceDetail>  invoiceDetails;

    private List<InvoicePunish> invoicePunishes;

    private List<Fileupload> fileuploads;
//    private List<InvoiceNoticeReceipt> invoiceNoticeReceipts;
//
//    private List<InvoiceNoticeReturn> invoiceNoticeReturns;
//
//    private List<InvoiceTaxControl> invoiceTaxControls;
}
