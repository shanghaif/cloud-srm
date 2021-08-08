package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.pm.ps.InvoiceStatus;
import com.midea.cloud.common.enums.pm.ps.OnlineInvoiceType;
import com.midea.cloud.common.enums.supcooperate.OnlineOrNotice;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.BeanMapUtils;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService;
import com.midea.cloud.srm.supcooperate.invoice.utils.OnlineInvoiceExportUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
*  <pre>
 *  网上开票表 前端控制器
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
@RestController
@RequestMapping("/invoice/onlineInvoice")
public class OnlineInvoiceController extends BaseController {

    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;
    @Autowired
    private IInvoiceNoticeService iInvoiceNoticeService;

    /**
     * 分页查询发票通知明细
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/listPageInvoiceNoticeDetail")
    public PageInfo<InvoiceNoticeDTO> listPageInvoiceNoticeDetail(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO) {
        return iInvoiceNoticeService.listPageByParm(invoiceNoticeQueryDTO.setOnlineOrNotice(OnlineOrNotice.ONLINE_INVOICE.name()));
    }

    /**
     * 分页查询网上发票(供应商端开具,供应商查询)
     */
    @PostMapping("/listPage")
    public PageInfo<OnlineInvoice> listPage(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO){
        if (!OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoiceQueryDTO.getOnlineInvoiceType())) {
            onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name());
        }
        //2020-12-24 隆基产品回迁 重写方法(解决单据创建时间降序排序无效)
//        return iOnlineInvoiceService.listPage(onlineInvoiceQueryDTO);
        return iOnlineInvoiceService.listPageNew(onlineInvoiceQueryDTO);
    }

    /**
     * 分页查询网上发票(付款申请专用) add by chensl26
     */
    @PostMapping("/buyerListPageFromPayment")
    public PageInfo<OnlineInvoice> buyerListPageFromPayment(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO){
        return iOnlineInvoiceService.listPage(onlineInvoiceQueryDTO);
    }

    /**
     * 查询网上发票
     * @param onlineInvoice
     * @return
     */
    @PostMapping("/list")
    public List<OnlineInvoice> listOnlineInvoice(@RequestBody OnlineInvoice onlineInvoice){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ONLINE_INVOICE_NUM" , onlineInvoice.getOnlineInvoiceNum());
        return iOnlineInvoiceService.list(wrapper);
    }

    /**
     * 创建网上发票
     * @param invoiceNoticeDTO
     * @return
     */
    @PostMapping("/createOnlineInvoice")
    public OnlineInvoiceSaveDTO createOnlineInvoice(@RequestBody List<InvoiceNoticeDTO> invoiceNoticeDTO) {
        return iOnlineInvoiceService.createOnlineInvoice(invoiceNoticeDTO);
    }

    /**
     * 暂存(供应商端开具)
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/saveTemporary")
    public BaseResult saveTemporary(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        if (onlineInvoice != null && !OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())) {
            onlineInvoice.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name());
        }
        return iOnlineInvoiceService.saveTemporary(onlineInvoiceSaveDTO, InvoiceStatus.DRAFT.name());
    }

    /**
     * 暂存(采购商端开具)
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/saveTemporaryForBuyer")
    public Map<String, Object> saveTemporaryForBuyer(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        if (onlineInvoice != null && !OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())) {
            onlineInvoice.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name());
        }
        BaseResult baseResult = iOnlineInvoiceService.saveTemporary(onlineInvoiceSaveDTO, InvoiceStatus.DRAFT.name());
        Map<String, Object> toMap = BeanMapUtils.beanToMap(baseResult);
        return toMap;
    }

    /**
     * 提交(供应商端)
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/submit")
    public OnlineInvoiceSaveDTO submit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        String invoiceStatus = InvoiceStatus.APPROVAL.name();
        if (onlineInvoice != null && !OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())) {
            onlineInvoice.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name());
            invoiceStatus = InvoiceStatus.UNDER_APPROVAL.name();
        }
        OnlineInvoiceSaveDTO invoiceSaveDTO = iOnlineInvoiceService.submit(onlineInvoiceSaveDTO, invoiceStatus);
        return invoiceSaveDTO;
    }

    /**
     * 作废(采购商作废)
     * @param onlineInvoiceId
     */
    @GetMapping("/buyerAbandon")
    public void buyerAbandon(@RequestParam Long onlineInvoiceId) {
        iOnlineInvoiceService.buyerAbandon(onlineInvoiceId);
    }

    /**
     * 作废(供应商作废)
     * @param onlineInvoiceId
     */
    @GetMapping("/vendorAbandon")
    public BaseResult vendorAbandon(@RequestParam Long onlineInvoiceId) {
        FSSCResult fsscResult = iOnlineInvoiceService.vendorAbandon(onlineInvoiceId);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (StringUtils.isNotBlank(fsscResult.getCode()) && StringUtils.isNotBlank(fsscResult.getMsg())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
        }
        return baseResult;
    }

    /**
     * 获取
     * @param onlineInvoiceId
     * @return
     */
    @GetMapping("/get")
    public OnlineInvoiceSaveDTO get(@RequestParam("onlineInvoiceId") Long onlineInvoiceId) {
        if(Objects.isNull(onlineInvoiceId)){
            return new OnlineInvoiceSaveDTO();
        }
        return iOnlineInvoiceService.get(onlineInvoiceId);
    }

    /**
     * 审核(采购商)
     * @param OnlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/audit")
    public OnlineInvoiceSaveDTO audit(@RequestBody OnlineInvoiceSaveDTO OnlineInvoiceSaveDTO) {
        OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = iOnlineInvoiceService.audit(OnlineInvoiceSaveDTO, InvoiceStatus.APPROVAL.name());
        return onlineInvoiceSaveDTO;
    }

    /**
     * 审核通过前需保存
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/saveTemporaryBeforeAudit")
    public Map<String, Object> saveTemporaryBeforeAudit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        BaseResult baseResult = iOnlineInvoiceService.saveTemporaryBeforeAudit(onlineInvoiceSaveDTO);
        Map<String, Object> toMap = BeanMapUtils.beanToMap(baseResult);
        return toMap;
    }

    /**
     * 网上开票费控状态返回
     * @param boeNo
     * @param invoiceStatus
     */
    @PostMapping("/statusReturn")
    public void statusReturn(@RequestParam("boeNo") String boeNo, @RequestParam("invoiceStatus") String invoiceStatus) {
        iOnlineInvoiceService.statusReturn(boeNo, invoiceStatus);
    }

    /**
     * 更新网上开票信息
     * @param onlineInvoice
     * @return
     */
    @PostMapping("/updateOnlineInvoice")
    public void updateOnlineInvoice(@RequestBody OnlineInvoice onlineInvoice){
        UpdateWrapper<OnlineInvoice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("PAID_AMOUNT" , onlineInvoice.getPaidAmount());
        updateWrapper.set("UN_PAID_AMOUNT" , onlineInvoice.getUnPaidAmount());
        updateWrapper.eq("ONLINE_INVOICE_NUM" , onlineInvoice.getOnlineInvoiceNum());
        iOnlineInvoiceService.update(updateWrapper);
    }

    /**
     * 网上开票采购商撤回
     * @param onlineInvoiceId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("onlineInvoiceId") Long onlineInvoiceId) {
        iOnlineInvoiceService.withdraw(onlineInvoiceId);
    }

//    /**
//     * erp锁定接收接口(采购商端开具)  (备用)
//     * @param onlineInvoiceSaveDTO
//     * @return
//     */
//    @PostMapping("/acceptPoLockForBuyer")
//    public OnlineInvoiceOutputParameters acceptPoLockForBuyer(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
//        return iOnlineInvoiceService.acceptPoLockForBuyer(onlineInvoiceSaveDTO);
//    }

    /**
     * @Description 导出代理网上开票excel表头标题
     * @modified xiexh12 2020-11-15 14:02
     */
    @PostMapping("/exportOnlineInvoiceTitle")
    public Map<String, String> exportMaterialItemTitle() {
        return OnlineInvoiceExportUtils.getOnlineInvoiceTitles();
    }


    /**
     * @Description 导出代理网上开票excel
     * @modified xiexh12 2020-11-15 14:53
     */
    @PostMapping("/exportOnlineInvoiceExcel")
    public BaseResult exportOnlineInvoiceExcel(@RequestBody ExportExcelParam<OnlineInvoice> onlineInvoiceExportParam, HttpServletResponse response) throws IOException {
        try {
            iOnlineInvoiceService.exportOnlineInvoiceExcel(onlineInvoiceExportParam, response);
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.build(ResultCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 导出网上发票
     */
    @PostMapping("/export")
    public void export(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO, HttpServletResponse response) throws Exception {
        if (!OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoiceQueryDTO.getOnlineInvoiceType())) {
            onlineInvoiceQueryDTO.setOnlineInvoiceType(OnlineInvoiceType.VENDOR_INVOICE.name());
        }
        iOnlineInvoiceService.export(onlineInvoiceQueryDTO,response);
    }

    /**
     * 获取发票
     * @param invoiceNumbers
     * @return
     */
    @PostMapping("/listByNumbers")
    public List<OnlineInvoice> listOnlineInvoiceByNumbers(@RequestBody List<String> invoiceNumbers){
        if(invoiceNumbers.isEmpty()){
            return new ArrayList<>();
        }
        return iOnlineInvoiceService.list(Wrappers.lambdaQuery(OnlineInvoice.class)
            .in(OnlineInvoice::getOnlineInvoiceNum , invoiceNumbers));
    }

    /**
     * 重置调整金额
     * @param
     */
    @PostMapping("/restart")
    public void restart(@RequestBody List<Long> onlineInvoiceIds) {
        iOnlineInvoiceService.restart(onlineInvoiceIds);
    }

    /**
     * 批量重置调整金额(临时应急功能)
     * @param
     */
    @PostMapping("/batchRestart")
    public void batchRestart(@RequestBody List<Long> onlineInvoiceIds) {
        iOnlineInvoiceService.batchRestart(onlineInvoiceIds);
    }

    /**
     * 批量调整金额(临时应急功能)
     * @param
     */
    @PostMapping("/batchAdjust")
    public BaseResult batchAdjust(@RequestBody List<Long> onlineInvoiceIds) {
        BaseResult baseResult = iOnlineInvoiceService.batchAdjust(onlineInvoiceIds);
        if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())) {
            throw new BaseException(baseResult.getMessage());
        }
        return baseResult;
    }

    @PostMapping("/queryBatchOnlineInvoiceSaveDTO")
    public OnlineInvoiceQueryDTO queryBatchOnlineInvoiceSaveDTO(@RequestBody List<Long> onlineInvoiceIds) {
        return iOnlineInvoiceService.queryBatchOnlineInvoiceSaveDTO(onlineInvoiceIds);
    }
}
