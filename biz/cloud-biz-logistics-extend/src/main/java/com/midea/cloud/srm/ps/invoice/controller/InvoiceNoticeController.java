package com.midea.cloud.srm.ps.invoice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.supcooperate.InvoiceNoticeStatus;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;
import com.midea.cloud.srm.ps.invoice.service.IInvoiceNoticeService;
import com.midea.cloud.srm.ps.invoice.service.IOnlineInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private SupcooperateClient supcooperateClient;

    @Autowired
    private IInvoiceNoticeService iInvoiceNoticeService;

    /**
     * 采购员分页条件查询
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/buyerListPageByParm")
    public PageInfo<InvoiceNotice> buyerListPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
    	// 未选择单据状态时默认查询所有单据状态的数据
        return supcooperateClient.invoiceNoticeListPageByParm(invoiceNoticeQueryDTO);
    }

    /**
     * 财务分页条件查询
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/financeListPageByParm")
    public PageInfo<InvoiceNotice> financeListPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        List<String> invoiceNoticeStatuses = new ArrayList<>();
        invoiceNoticeStatuses.add(InvoiceNoticeStatus.SUBMITTED.name());
        invoiceNoticeStatuses.add(InvoiceNoticeStatus.REJECTED.name());
        invoiceNoticeStatuses.add(InvoiceNoticeStatus.FIRST_REVIEW_APPROVED.name());
        invoiceNoticeStatuses.add(InvoiceNoticeStatus.FINAL_REVIEW_APPROVED.name());
        return supcooperateClient.invoiceNoticeListPageByParm(invoiceNoticeQueryDTO.setInvoiceNoticeStatuses(invoiceNoticeStatuses));
    }

    /**
     * 采购员驳回
     * @param invoiceNotice
     */
    @PostMapping("/buyerReject")
    public void buyerReject(@RequestBody InvoiceNotice invoiceNotice) {
        supcooperateClient.updateInvoiceNotice(invoiceNotice.setInvoiceNoticeStatus(InvoiceNoticeStatus.REJECTED.name()));
    }

    /**
     * 财务驳回
     * @param invoiceNotice
     */
    @PostMapping("/financeReject")
    public void financeReject(@RequestBody InvoiceNotice invoiceNotice) {
        supcooperateClient.updateInvoiceNotice(invoiceNotice.setInvoiceNoticeStatus(InvoiceNoticeStatus.REJECTED.name()));
    }

    /**
     * 采购员初审
     * @param invoiceNotice
     */
    @PostMapping("/buyerFirstReview")
    public void buyerFirstReview(@RequestBody InvoiceNotice invoiceNotice) {
        supcooperateClient.updateInvoiceNotice(invoiceNotice.setInvoiceNoticeStatus(InvoiceNoticeStatus.FIRST_REVIEW_APPROVED.name()));
    }

    /**
     * 财务终审
     * @param invoiceNotice
     */
    @PostMapping("/financeFinalReview")
    public void approve(@RequestBody InvoiceNotice invoiceNotice) {
        supcooperateClient.updateInvoiceNotice(invoiceNotice.setInvoiceNoticeStatus(InvoiceNoticeStatus.FINAL_REVIEW_APPROVED.name()));
    }

    /**
     * 获取InvoiceNoticeSaveDTO
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/getInvoiceNoticeSaveDTO")
    public InvoiceNoticeSaveDTO getInvoiceNoticeSaveDTO(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        return supcooperateClient.getInvoiceNoticeSaveDTO(invoiceNoticeId);
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
        supcooperateClient.deleteByInvoiceNoticeId(invoiceNoticeId);
    }

    /**
     * 分页条件查询(ceea) modify by chensl26
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/listPageByParm")
    PageInfo<InvoiceNotice> listPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        return supcooperateClient.listPageByParm(invoiceNoticeQueryDTO);
    }

    /**
     * 废弃
     * @param invoiceNotice
     */
    @PostMapping("/abandon")
    public void abandon(@RequestBody InvoiceNotice invoiceNotice) {
        supcooperateClient.abandon(invoiceNotice);
    }

    /**
     * 开票通知撤回(采购商)
     * @param invoiceNoticeId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("invoiceNoticeId") Long invoiceNoticeId) {
        supcooperateClient.invoiceNoticeWithdraw(invoiceNoticeId);
    }
}
