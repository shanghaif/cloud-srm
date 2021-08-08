package com.midea.cloud.srm.supcooperate.anon.controller;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.model.api.aps.PiNotifyForSrmDTO;
import com.midea.cloud.srm.model.api.aps.PiNotifyLine;
import com.midea.cloud.srm.model.api.deliveryNote.dto.DeliveryNoteAllDto;
import com.midea.cloud.srm.model.api.deliveryNote.dto.DeliveryNoteDetailDto;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceAdvanceService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务外部调用接口
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/16 15:43
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/sc-anon/external")
public class ScAnonExternalController {
    @Autowired
    private IOnlineInvoiceAdvanceService iOnlineInvoiceAdvanceService;
    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;
    @Autowired
    private IDeliverPlanService iDeliverPlanService;

    /**
     * 根据费控单号查询引用的预付款
     * @param fsscNo
     * @return
     */
    @GetMapping("/listOnlineInvoiceAdvanceByFsscNo")
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNo(@RequestParam("fsscNo") String fsscNo) {
        return iOnlineInvoiceAdvanceService.listOnlineInvoiceAdvanceByFsscNo(fsscNo);
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
     * 网上开票费控导入状态返回
     * @param boeNo
     * @param importStatus
     */
    @PostMapping("/importStatusReturn")
    public void importStatusReturn(@RequestParam("boeNo") String boeNo, @RequestParam("importStatus") String importStatus) {
        iOnlineInvoiceService.importStatusReturn(boeNo, importStatus);
    }
    /**
     *送货预示计划
     * @param piNotifyForSrmDTO
     */
    @PostMapping("/getDeliverPlanMRP")
    public PiNotifyForSrmDTO getDeliverPlanMRP(@RequestBody PiNotifyForSrmDTO piNotifyForSrmDTO) throws Exception{
        //数据转换
        DeliverPlanDTO deliverPlanDTO = getObjectSwitch(piNotifyForSrmDTO);
        String notifyType = deliverPlanDTO.getNotifyType();
        DeliverPlanDTO deliverPlanMRPList=null;
        //送货通知类型(DN送货通知，DP送货预示)
        if ("DN".equals(notifyType)){
            deliverPlanMRPList = iDeliverPlanService.getDeliverPlanMessageMRP(deliverPlanDTO);
        }else if ("DP".equals(notifyType)){
             deliverPlanMRPList = iDeliverPlanService.getDeliverPlanMRPList(deliverPlanDTO);
        }else {
            Assert.isTrue(false,"送货通知类型:"+notifyType+"。不存在");
        }
        piNotifyForSrmDTO.setSrmDnNumber(deliverPlanMRPList.getDeliverPlanNum());
        return piNotifyForSrmDTO;
    }
    /**
     *送货预示计划（送货通知）
     * @param deliveryNotice
     */
    @PostMapping("/getDeliverPlanMessageMRP")
    public void getDeliverPlanMessageMRP(@RequestBody DeliveryNotice deliveryNotice){
        //iDeliverPlanService.getDeliverPlanMessageMRP(deliverPlanDTO);
    }

    /**
     * 批量修改网上开票中预付款可用金额
     * @param onlineInvoiceAdvances
     */
    @PostMapping("/updateOnlineInvoiceAdvanceByParam")
    public void updateOnlineInvoiceAdvanceByParam(@RequestBody List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        iOnlineInvoiceAdvanceService.updateOnlineInvoiceAdvanceByParam(onlineInvoiceAdvances);
    }


    public DeliverPlanDTO getObjectSwitch(PiNotifyForSrmDTO piNotifyForSrmDTO){
        List<PiNotifyLine> notifyLines = piNotifyForSrmDTO.getNotifyLines();
        DeliverPlanDTO deliverPlanDTO = new DeliverPlanDTO();
        //库存组织
        deliverPlanDTO.setOrganizationCode(piNotifyForSrmDTO.getPlantCode());
        //地点
        deliverPlanDTO.setDeliveryAddress(piNotifyForSrmDTO.getLocationCode());
        //供应商
        deliverPlanDTO.setVendorCode(piNotifyForSrmDTO.getVendorCode());
        //物料
        deliverPlanDTO.setMaterialCode(piNotifyForSrmDTO.getItemCode());
        deliverPlanDTO.setNotifyType(piNotifyForSrmDTO.getNotifyType());
        //srm到货计划单号
        deliverPlanDTO.setDeliverPlanNum(piNotifyForSrmDTO.getSrmDnNumber());
        //获取到货计划明细行
        List<DeliverPlanDetail> deliverPlanDetailList = new ArrayList<>();

        for (PiNotifyLine piNotifyLine:notifyLines){
            DeliverPlanDetail deliverPlanDetail = new DeliverPlanDetail();
            //送货通知单号
            deliverPlanDetail.setNotifyNumber(piNotifyLine.getNotifyNumber());
            //计划到货日期
//            deliverPlanDetail.setSchMonthlyDate(DateUtil.dateToLocalDate(piNotifyLine.getNeedByDate()));
            deliverPlanDetail.setRequirementQuantity(piNotifyLine.getQuantity());
            deliverPlanDetailList.add(deliverPlanDetail);
        }
        deliverPlanDTO.setDeliverPlanDetailList(deliverPlanDetailList);
    return deliverPlanDTO;
    }

}
