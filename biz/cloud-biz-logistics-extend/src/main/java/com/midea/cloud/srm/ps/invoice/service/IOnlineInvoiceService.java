package com.midea.cloud.srm.ps.invoice.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplyQueryDTO;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  网上开票表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:47:48
 *  修改内容:
 * </pre>
*/
public interface IOnlineInvoiceService {

    FSSCResult submitOnlineInvoice(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    FSSCResult audit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    BaseResult abandon(Long onlineInvoiceId);

    BaseResult saveTemporaryOnlineInvoice(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    OnlineInvoiceSaveDTO createOnlineInvoice(List<InvoiceNoticeDTO> invoiceNoticeDTOS);

    BaseResult saveTemporaryBeforeAudit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    void batchDelete(List<Long> onlineInvoiceAdvanceIds);

}
