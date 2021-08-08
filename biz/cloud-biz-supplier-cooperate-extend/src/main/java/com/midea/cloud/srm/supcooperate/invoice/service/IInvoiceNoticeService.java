package com.midea.cloud.srm.supcooperate.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
 *  修改日期: 2020-06-27 11:46:26
 *  修改内容:
 * </pre>
*/
public interface IInvoiceNoticeService extends IService<InvoiceNotice> {

    /**
     * 供应商暂存
     * @param invoiceNoticeSaveDTO
     */
    Long saveTemporary(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    /**
     * 分页条件查询
     * @param invoiceNoticeQueryDTO
     * @return
     */
    PageInfo<InvoiceNoticeDTO> listPageByParm(InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 查找符合条件的对账单
     * @param invoiceNoticeQueryDTO
     * @return
     */
    List<StatementHead> listStatementHeadByParm(InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 供应商提交
     * @param invoiceNoticeSaveDTO
     */
    void submit(InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    /**
     * 获取InvoiceNoticeSaveDTO
     * @param invoiceNoticeId
     */
    InvoiceNoticeSaveDTO getInvoiceNoticeSaveDTO(Long invoiceNoticeId);

    void exportExcel(Long invoiceNoticeId);

    /**
     * 根据发票通知单ID删除
     * @param invoiceNoticeId
     */
    void deleteByInvoiceNoticeId(Long invoiceNoticeId);

    /**
     * 通过对账单ID批量删除发票通知明细
     * @param invoiceNoticeQueryDTOs
     */
    void batchDeleteByStatementHeadId(List<InvoiceNoticeQueryDTO> invoiceNoticeQueryDTOs);

    /**
     * 供应商确认
     * @param invoiceNoticeId
     */
    void confirm(Long invoiceNoticeId);

    /**
     * 供应商驳回
     * @param invoiceNoticeId
     */
    void reject(Long invoiceNoticeId);

    PageInfo<WarehousingReturnDetail> warehousingReturnlistPageByParam(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    void abandon(InvoiceNotice invoiceNotice);

    void withdraw(Long invoiceNoticeId);

    /**
     * 导出开票明细
     * @param invoiceNoticeId
     */
    void exportInvoiceDetails(Long invoiceNoticeId, HttpServletResponse response) throws IOException;
}
