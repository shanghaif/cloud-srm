package com.midea.cloud.srm.supcooperate.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;

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
public interface IOnlineInvoiceService extends IService<OnlineInvoice> {

    /**
     * 创建网上发票
     * @param invoiceNoticeDTO
     * @return
     */
    OnlineInvoiceSaveDTO createOnlineInvoice(List<InvoiceNoticeDTO> invoiceNoticeDTO);

    PageInfo<OnlineInvoice> listPage(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);

    BaseResult saveTemporary(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO, String invoiceStatus);

    OnlineInvoiceSaveDTO submit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO, String invoiceStatus);

    FSSCResult vendorAbandon(Long onlineInvoiceId);

    OnlineInvoiceSaveDTO get(Long onlineInvoiceId);

    OnlineInvoiceSaveDTO audit(OnlineInvoiceSaveDTO OnlineInvoiceSaveDTO, String invoiceStatus);

    void buyerAbandon(Long onlineInvoiceId);

    void statusReturn(String boeNo, String invoiceStatus);

    void importStatusReturn(String boeNo, String importStatus);

    BaseResult saveTemporaryBeforeAudit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    void withdraw(Long onlineInvoiceId);

    /**
     * @Description 导出代理网上开票excel
     * @Author: xiexh12 2020-11-15 14:57
     */
    void exportOnlineInvoiceExcel(ExportExcelParam<OnlineInvoice> onlineInvoiceExportParam, HttpServletResponse response) throws IOException;

    void export(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO, HttpServletResponse response) throws Exception;

    void restart(List<Long> onlineInvoiceIds);

    BaseResult batchAdjust(List<Long> onlineInvoiceIds);

    OnlineInvoiceQueryDTO queryBatchOnlineInvoiceSaveDTO(List<Long> onlineInvoiceIds);

    void batchRestart(List<Long> onlineInvoiceIds);

    //2020-12-24 隆基产品回迁 重写方法(解决单据创建时间降序排序无效)
    PageInfo<OnlineInvoice> listPageNew(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);
}
