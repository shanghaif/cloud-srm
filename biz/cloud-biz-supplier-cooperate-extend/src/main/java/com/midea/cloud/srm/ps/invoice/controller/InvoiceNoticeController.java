package com.midea.cloud.srm.ps.invoice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.supcooperate.OnlineOrNotice;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.ps.invoice.service.IInvoiceNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/ps/invoice/invoiceNotice")
public class InvoiceNoticeController extends BaseController {

    @Autowired
    private IInvoiceNoticeService iInvoiceNoticeService;

    @Autowired
    private com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeService scIInvoiceNoticeService;

    /**
     * 获取InvoiceNoticeSaveDTO
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/getInvoiceNoticeSaveDTO")
    public InvoiceNoticeSaveDTO getInvoiceNoticeSaveDTO(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        return scIInvoiceNoticeService.getInvoiceNoticeSaveDTO(invoiceNoticeId);
    }

    /**
     * 采购商暂存
     * @param invoiceNoticeSaveDTO
     * @return
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        return iInvoiceNoticeService.saveTemporary(invoiceNoticeSaveDTO);
    }

    /**
     * 采购商提交
     * @param invoiceNoticeSaveDTO
     */
    @PostMapping("/submit")
    public void submit(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO) {
        iInvoiceNoticeService.submit(invoiceNoticeSaveDTO);
    }

    /**
     * 通过发票通知单ID删除
     * @param invoiceNoticeId
     */
    @GetMapping("/deleteByInvoiceNoticeId")
    public void deleteByInvoiceNoticeId(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        scIInvoiceNoticeService.deleteByInvoiceNoticeId(invoiceNoticeId);
    }

    /**
     * 分页条件查询(ceea) modify by chensl26
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/listPageByParm")
    PageInfo<InvoiceNoticeDTO> listPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        return scIInvoiceNoticeService.listPageByParm(invoiceNoticeQueryDTO.setOnlineOrNotice(OnlineOrNotice.INVOICE_NOTICE.name()));
    }

    /**
     * 废弃
     * @param invoiceNotice
     */
    @PostMapping("/abandon")
    public void abandon(@RequestBody InvoiceNotice invoiceNotice) {
        iInvoiceNoticeService.abandon(invoiceNotice);
    }

    /**
     * 开票通知撤回(采购商)
     * @param invoiceNoticeId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        scIInvoiceNoticeService.withdraw(invoiceNoticeId);
    }
}
