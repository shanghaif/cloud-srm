package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.supcooperate.InvoiceNoticeStatus;
import com.midea.cloud.common.enums.supcooperate.OnlineOrNotice;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  开票通知表 前端控制器
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
@RestController
@RequestMapping("/invoice/invoiceNotice")
public class InvoiceNoticeController extends BaseController {

    @Autowired
    private IInvoiceNoticeService iInvoiceNoticeService;

    /**
     * 供应商暂存（ceea:采购商暂存）modify by chensl26
     * @param invoiceNoticeSaveDTO
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        return iInvoiceNoticeService.saveTemporary(invoiceNoticeSaveDTO);
    }

    /**
     * 供应商提交(ceea:采购商提交) modify by chensl26
     * @param invoiceNoticeSaveDTO
     */
    @PostMapping("/submit")
    public void submit(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        iInvoiceNoticeService.submit(invoiceNoticeSaveDTO);
    }

    /**
     * 废弃
     * @param invoiceNotice
     */
    @PostMapping("/abandon")
    public void abandon(@RequestBody InvoiceNotice invoiceNotice) {
        iInvoiceNoticeService.abandon(invoiceNotice);
    }

//    /**
//     * 供应商分页条件查询
//     * @param invoiceNoticeQueryDTO
//     * @return
//     */
//    @PostMapping("/vendorListPageByParm")
//    public PageInfo<InvoiceNotice> vendorListPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
//        List<String> invoiceNoticeStatuses = new ArrayList<>();
//        return iInvoiceNoticeService.listPageByParm(invoiceNoticeQueryDTO.setInvoiceNoticeStatuses(invoiceNoticeStatuses));
//    }

    /**
     * 通过发票通知单ID删除
     * @param invoiceNoticeId
     */
    @GetMapping("/deleteByInvoiceNoticeId")
    public void deleteByInvoiceNoticeId(@RequestParam Long invoiceNoticeId) {
        iInvoiceNoticeService.deleteByInvoiceNoticeId(invoiceNoticeId);
    }

    /**
     * 通过对账单ID批量删除发票通知明细
     * @param
     */
    @PostMapping("/batchDeleteByStatementHeadId")
    public void batchDeleteByStatementHeadId(@RequestBody List<InvoiceNoticeQueryDTO> invoiceNoticeQueryDTOs) {
        iInvoiceNoticeService.batchDeleteByStatementHeadId(invoiceNoticeQueryDTOs);
    }

    /**
     * 分页条件查询(ceea) modify by chensl26
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<InvoiceNoticeDTO> listPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        return iInvoiceNoticeService.listPageByParm(invoiceNoticeQueryDTO.setOnlineOrNotice(OnlineOrNotice.INVOICE_NOTICE.name()));
    }

    /**
     * 查找符合条件的对账单(不需要)
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/listStatementHeadByParm")
    public List<StatementHead> listStatementHeadByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        return iInvoiceNoticeService.listStatementHeadByParm(invoiceNoticeQueryDTO);
    }

    /**
     * 更新发票通知
     * @param invoiceNotice
     */
    @PostMapping("/updateInvoiceNotice")
    public void updateInvoiceNotice(@RequestBody InvoiceNotice invoiceNotice) {
        iInvoiceNoticeService.updateById(invoiceNotice);
    }

    /**
     * 获取InvoiceNoticeSaveDTO
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/getInvoiceNoticeSaveDTO")
    public InvoiceNoticeSaveDTO getInvoiceNoticeSaveDTO(@RequestParam Long invoiceNoticeId) {
        return iInvoiceNoticeService.getInvoiceNoticeSaveDTO(invoiceNoticeId);
    }

    /**
     * 导出开票明细
     */
    @GetMapping("/exportInvoiceDetails")
    public void exportInvoiceDetails(@RequestParam("invoiceNoticeId") Long invoiceNoticeId, HttpServletResponse response) throws IOException {
        iInvoiceNoticeService.exportInvoiceDetails(invoiceNoticeId,response);
    }

    /**
     * 供应商确认
     * @param invoiceNoticeId
     */
    @GetMapping("/confirm")
    public void confirm(@RequestParam Long invoiceNoticeId) {
        iInvoiceNoticeService.confirm(invoiceNoticeId);
    }

    /**
     * 供应商驳回
     * @param invoiceNoticeId
     */
    @GetMapping("/reject")
    public void reject(@RequestParam Long invoiceNoticeId) {
        iInvoiceNoticeService.reject(invoiceNoticeId);
    }

    /**
     * 分页条件查询入退库明细
     * @param warehousingReturnDetailRequestDTO
     * @return
     */
    @PostMapping("/warehousingReturnlistPageByParam")
    public PageInfo<WarehousingReturnDetail> warehousingReturnlistPageByParam(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO) {
        return iInvoiceNoticeService.warehousingReturnlistPageByParam(warehousingReturnDetailRequestDTO);
    }

    /**
     * 撤回(采购商)
     * @param invoiceNoticeId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        iInvoiceNoticeService.withdraw(invoiceNoticeId);
    }

    /**
     * 获取开票通知头
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/getInvoiceNoticeById")
    public InvoiceNotice getInvoiceNoticeById(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        Assert.notNull(invoiceNoticeId, "invoiceNoticeId不能为空");
        return iInvoiceNoticeService.getById(invoiceNoticeId);
    }
}
