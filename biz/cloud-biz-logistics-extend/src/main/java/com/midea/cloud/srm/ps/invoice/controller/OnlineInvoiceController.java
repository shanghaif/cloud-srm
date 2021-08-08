package com.midea.cloud.srm.ps.invoice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.enums.pm.ps.OnlineInvoiceType;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
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
@RequestMapping("/invoice/onlineInvoice")
public class OnlineInvoiceController extends BaseController {

    @Autowired
    private SupcooperateClient supcooperateClient;

    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;

    @Autowired
    private IAdvanceApplyHeadService iAdvanceApplyHeadService;

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
        return supcooperateClient.listPageOnlineInvoice(onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name()));
    }

    /**
     * 分页查询网上发票(采购商端开具,采购商查询)
     */
    @PostMapping("/buyerListPage")
    public PageInfo<OnlineInvoice> buyerListPage(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return supcooperateClient.listPageOnlineInvoice(onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.BUYER_INVOICE.name()));
    }

    /**
     * 作废(采购商端开具)
     *
     * @param onlineInvoiceId
     */
    @GetMapping("/abandon")
    public BaseResult abandon(@RequestParam Long onlineInvoiceId) {
        BaseResult baseResult = iOnlineInvoiceService.abandon(onlineInvoiceId);
        return baseResult;
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
        return supcooperateClient.getOnlineInvoice(onlineInvoiceId);
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
        supcooperateClient.onlineInvoiceWithdraw(onlineInvoiceId);
    }

}
