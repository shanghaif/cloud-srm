package com.midea.cloud.srm.ps.invoice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.enums.pm.ps.OnlineInvoiceType;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplyQueryDTO;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.ps.advance.service.IAdvanceApplyHeadService;
import com.midea.cloud.srm.ps.invoice.service.IOnlineInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  网上开票表 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:47:48
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/ps/invoice/onlineInvoice")
public class OnlineInvoiceController extends BaseController {

    @Autowired
    private com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService scIOnlineInvoiceService;

    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;

    @Autowired
    private IAdvanceApplyHeadService iAdvanceApplyHeadService;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService onlineInvoiceService;

    @Autowired
    private com.midea.cloud.srm.supcooperate.invoice.controller.OnlineInvoiceController onlineInvoiceController;

//    /**
//     * 分页查询发票通知明细(备用)
//     * @param invoiceNoticeQueryDTO
//     * @return
//     */
//    @PostMapping("/listPageInvoiceNoticeDetail")
//    public PageInfo<InvoiceNoticeDTO> listPageInvoiceNoticeDetail(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
//        return supcooperateClient.listPageInvoiceNoticeDetail(invoiceNoticeQueryDTO.setOnlineOrNotice(OnlineOrNotice.ONLINE_INVOICE.name()));
//    }

    /**
     * 创建网上发票(采购商开具)
     *
     * @param invoiceNoticeDTOS
     * @return
     */
    @PostMapping("/createOnlineInvoice")
    public OnlineInvoiceSaveDTO createOnlineInvoice(@RequestBody List<InvoiceNoticeDTO> invoiceNoticeDTOS) {
        return iOnlineInvoiceService.createOnlineInvoice(invoiceNoticeDTOS);
    }

    /**
     * 提交(采购商端开具)
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/submit")
    public BaseResult submit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        if (onlineInvoice != null) {
            onlineInvoice.setOnlineInvoiceType(OnlineInvoiceType.BUYER_INVOICE.name());
        }
        FSSCResult fsscResult = iOnlineInvoiceService.submitOnlineInvoice(onlineInvoiceSaveDTO);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
            baseResult.setData(fsscResult.getType());
        }
        return baseResult;
    }

    /**
     * 暂存(采购商端开具)
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/saveTemporary")
    public BaseResult saveTemporary(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        if (onlineInvoice != null) {
            onlineInvoice.setOnlineInvoiceType(OnlineInvoiceType.BUYER_INVOICE.name());
        }
        return iOnlineInvoiceService.saveTemporaryOnlineInvoice(onlineInvoiceSaveDTO);
    }

    /**
     * 分页查询网上发票(供应商端开具,采购商查询)
     */
    @PostMapping("/vendorListPage")
    public PageInfo<OnlineInvoice> vendorListPage(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return scIOnlineInvoiceService.listPageNew(onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name()));
    }

    /**
     * 分页查询网上发票(采购商端开具,采购商查询)
     */
    @PostMapping("/buyerListPage")
    public PageInfo<OnlineInvoice> buyerListPage(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return scIOnlineInvoiceService.listPageNew(onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.BUYER_INVOICE.name()));
    }

    /**
     * 分页查询网上发票(付款申请专用)
     */
    @PostMapping("/buyerListPageFromPayment")
    public PageInfo<OnlineInvoice> buyerListPageFromPayment(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return scIOnlineInvoiceService.listPageNew(onlineInvoiceQueryDTO.setOnlineInvoiceType(null));
    }

    /**
     * 作废(采购商端开具)
     *
     * @param onlineInvoiceId
     */
    @GetMapping("/abandon")
    public void abandon(@RequestParam Long onlineInvoiceId) {
        iOnlineInvoiceService.abandon(onlineInvoiceId);
    }

    /**
     * 获取(采购商端开具)
     *
     * @param onlineInvoiceId
     * @return
     */
    @GetMapping("/get")
    public OnlineInvoiceSaveDTO get(@RequestParam Long onlineInvoiceId) {
        if(Objects.isNull(onlineInvoiceId)){
            return new OnlineInvoiceSaveDTO();
        }
        return scIOnlineInvoiceService.get(onlineInvoiceId);
    }

    /**
     * 审核(供应商开具,采购商审核)
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/audit")
    public BaseResult audit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        FSSCResult fsscResult = iOnlineInvoiceService.audit(onlineInvoiceSaveDTO);
        BaseResult baseResult = BaseResult.buildSuccess();
        baseResult.setData(onlineInvoiceSaveDTO.getOnlineInvoice().getOnlineInvoiceId());
        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
            baseResult.setData(fsscResult.getType());
        }
        return baseResult;
    }

    /**
     * 审核通过前需保存
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/saveTemporaryBeforeAudit")
    public Long saveTemporaryBeforeAudit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        BaseResult baseResult = iOnlineInvoiceService.saveTemporaryBeforeAudit(onlineInvoiceSaveDTO);
        Long onlineInvoiceId = (Long)baseResult.getData();
        return onlineInvoiceId;
    }

    /**
     * 分页条件查询预付款
     *
     * @param advanceApplyQueryDTO
     * @return
     */
    @PostMapping("/listPageAdvanceByParam")
    public PageInfo<AdvanceApplyHead> listPageAdvanceByParam(@RequestBody AdvanceApplyQueryDTO advanceApplyQueryDTO) {
        return iAdvanceApplyHeadService.listPageByParam(advanceApplyQueryDTO.setIfOnlineInvoice(YesOrNo.YES.getValue()));
    }

    /**
     * 采购商撤回
     *
     * @param onlineInvoiceId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("onlineInvoiceId") Long onlineInvoiceId) {
        iOnlineInvoiceService.onlineInvoiceWithdraw(onlineInvoiceId);
    }

//    @GetMapping("/testBankCount")
//    public BankInfo testBankCount(@RequestParam Long id) {
//        List<BankInfo> bankInfosByParam = supplierClient.getBankInfosByParam(new BankInfo()
//                .setCompanyId(id)
//                .setCeeaMainAccount(YesOrNo.YES.getValue())
//                .setCeeaEnabled(YesOrNo.YES.getValue()));
//        return bankInfosByParam.get(0);
//    }

    /**
     * 重置调整金额
     * @param onlineInvoiceId
     */
    @GetMapping("/restart")
    public Long restart(@RequestParam("onlineInvoiceId") Long onlineInvoiceId) {
        Assert.notNull(onlineInvoiceId, LocaleHandler.getLocaleMsg("onlineInvoiceId不能为空"));
        return iOnlineInvoiceService.restart(onlineInvoiceId);
    }

    @PostMapping("/batchSubmit")
    public FSSCResult batchSubmit(@RequestBody List<Long> onlineInvoicIds) {
        FSSCResult fsscResult = iOnlineInvoiceService.batchSubmit(onlineInvoicIds);
        return fsscResult;
    }


}
