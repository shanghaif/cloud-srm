package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoicePunish;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * <pre>
 *  网上开票保存DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 13:50
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class OnlineInvoiceSaveDTO extends BaseDTO{

    private OnlineInvoice onlineInvoice;
    private List<OnlineInvoiceDetail> onlineInvoiceDetails;
    private List<OnlineInvoicePunish> onlineInvoicePunishes;
    private List<OnlineInvoiceAdvance> onlineInvoiceAdvances;
    private List<Fileupload> fileuploads;
}
