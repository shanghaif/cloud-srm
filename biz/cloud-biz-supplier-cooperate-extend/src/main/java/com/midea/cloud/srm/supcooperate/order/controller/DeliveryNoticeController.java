package com.midea.cloud.srm.supcooperate.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   送货通知单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/deliveryNotice")
public class DeliveryNoticeController extends BaseController {

    @Autowired
    private IDeliveryNoticeService iDeliveryNoticeService;

    /**
     * 采购商分页查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/deliveryNoticelListPage")
    public PageInfo<DeliveryNoticeDTO> deliveryNoticelListPage(@RequestBody DeliveryNoticeRequestDTO requestDTO) {
        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<DeliveryNoticeDTO> deliveryNoticeServiceList = iDeliveryNoticeService.findList(requestDTO);
        if(!CollectionUtils.isEmpty(deliveryNoticeServiceList)){
            deliveryNoticeServiceList.forEach(deliveryNoticeDTO -> {
                // 送货通知引用数量
                BigDecimal deliveryNoticeQuantity = deliveryNoticeDTO.getDeliveryNoticeQuantity();
                // 订单数量
                BigDecimal orderNum = deliveryNoticeDTO.getOrderNum();
                BigDecimal surplusDeliveryQuantity = orderNum.subtract(deliveryNoticeQuantity);
                surplusDeliveryQuantity = surplusDeliveryQuantity.compareTo(BigDecimal.ZERO) >= 0 ? surplusDeliveryQuantity : BigDecimal.ZERO;
                BigDecimal noticeSum = deliveryNoticeDTO.getNoticeSum();
                surplusDeliveryQuantity = null != noticeSum ? surplusDeliveryQuantity.add(noticeSum):surplusDeliveryQuantity;
                // 本次可通知送货数量
                deliveryNoticeDTO.setSurplusDeliveryQuantity(surplusDeliveryQuantity);
            });
        }
        return new PageInfo(deliveryNoticeServiceList);
    }

    /**
     * 采购商更新送货通知单
     * @param deliveryNotice
     * @return
     */
    @PostMapping("/updateById")
    public void updateById(@RequestBody DeliveryNotice deliveryNotice) {
        iDeliveryNoticeService.updateById(deliveryNotice);
    }

    /**
     * 采购商通过通知单id获取通知单
     * @param deliveryNoticeId
     * @return
     */
    @GetMapping("/getById")
    public DeliveryNotice getById(@RequestParam("deliveryNoticeId")Long deliveryNoticeId) {
        return iDeliveryNoticeService.getById(deliveryNoticeId);
    }

    /**
     * 通过送货通知单id获取要创建的送货单明细
     * @param deliveryNoticeIds 送货通知单id列
     * @return
     */
    @PostMapping("/listCreateDeliveryNoteDetail")
    public PageInfo<DeliveryNoticeDTO> listCreateDeliveryNoteDetail(@RequestBody List<Long> deliveryNoticeIds) {
        Assert.notNull(deliveryNoticeIds,"送货单ID不能为空");
        Assert.isTrue(deliveryNoticeIds.size()>0,"送货单ID不能为空");
        return new PageInfo<DeliveryNoticeDTO>(iDeliveryNoticeService.listCreateDeliveryNoteDetail(deliveryNoticeIds));
    }

    /**
     * 获取送货通知单的入库数量
     * @param deliveryNoticeId
     * @return
     */
    @GetMapping("/getWarehouseReceiptQuantityByDeliveryNoticeId")
    public BigDecimal getWarehouseReceiptQuantity(@RequestParam("deliveryNoticeId") Long deliveryNoticeId){
        return iDeliveryNoticeService.getWarehouseReceiptQuantityByDeliveryNoticeId(deliveryNoticeId);
    }

    /**
     * 供应商分页查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoticeDTO> listPage(@RequestBody DeliveryNoticeRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }

        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        return new PageInfo(iDeliveryNoticeService.findList(requestDTO));
    }

    /**
     * 修改送货通知单
     * @param deliveryNotice
     */
    /*@PostMapping("/update")
    public void update(@RequestBody DeliveryNotice deliveryNotice) {
        Assert.notNull(deliveryNotice,"数据格式错误");
        Assert.notNull(deliveryNotice.getDeliveryNoticeId(),"送货通知单ID不能为空");

        DeliveryNotice checkNotice = iDeliveryNoticeService.getById(deliveryNotice.getDeliveryNoticeId());
        Assert.notNull(checkNotice,"送货通知单不存在");
        Assert.isTrue(StringUtils.equals(NoteStatus.EDIT.name(),checkNotice.getDeliveryNoticeStatus()),"只能修改拟态送货通知单");

        DeliveryNotice updateNotice = new DeliveryNotice();
        updateNotice.setDeliveryNoticeId(deliveryNotice.getDeliveryNoticeId());
        updateNotice.setNoticeSum(deliveryNotice.getNoticeSum());
        updateNotice.setDeliveryTime(deliveryNotice.getDeliveryTime());
        iDeliveryNoticeService.updateById(updateNotice);
    }*/

    /**
     * 采购商批量保存送货通知单
     * @param deliveryNotices
     * @return
     */
    @PostMapping("/saveBatchDeliveryNotice")
    void saveBatchDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNotices){
        iDeliveryNoticeService.saveBatch(deliveryNotices);
    }

    /**
     * 采购商批量发布
     *
     * @param ids 送货通知单ids
     */
    /*@PostMapping("/releasedBatch")
    public void releasedBatch(@RequestBody List<Long> ids) {
        iDeliveryNoticeService.releasedBatch(ids);
    }*/

    /**
     * 采购商批量更新送货通知单
     * @param list
     * @return
     */
    @PostMapping("/updateBatchById")
    public void updateBatchById(@RequestBody List<DeliveryNotice> list){
        iDeliveryNoticeService.updateBatchById(list);
    }

    /**
     *
     * @param ids
     * @return
     */
    @PostMapping("/queryDeliveryNoticeByIds")
    public List<DeliveryNotice> queryDeliveryNoticeByIds(@RequestBody List<Long> ids){
    	return ids == null || ids.size() == 0 ? new ArrayList<DeliveryNotice>() : iDeliveryNoticeService.listByIds(ids);
    }

    /**
     * 批量添加送货通知单
     * @param deliveryNoticeList
     */
    @PostMapping("/batchAddDeliveryNotice")
    public void batchAddDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNoticeList){
        iDeliveryNoticeService.batchAdd(deliveryNoticeList);
    }

    /**
     * 批量更新送货通知单
     * @param deliveryNoticeList
     */
    @PostMapping("/batchUpdateDeliveryNotice")
    public void batchUpdateDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNoticeList){
        iDeliveryNoticeService.batchUpdate(deliveryNoticeList);
    }

    /**
     * 批量删除送货通知单
     * @param ids
     */
    @PostMapping("/batchDeleteDeliveryNotice")
    public void batchDeleteDeliveryNotice(@RequestBody List<Long> ids){
        iDeliveryNoticeService.batchDeleteDeliveryNotice(ids);
    }

    /**
     * 供应商确认
     * @param ids
     */
    @PostMapping("/supplierConfirm")
    public void supplierConfirm(@RequestBody List<Long> ids){
        iDeliveryNoticeService.supplierConfirm(ids);
    }

    /**
     * 供应商拒绝
     * @param deliveryNoticeRequestDTO
     */
    @PostMapping("/supplierReject")
    public void supplierReject(@RequestBody DeliveryNoticeRequestDTO deliveryNoticeRequestDTO){
        iDeliveryNoticeService.supplierReject(deliveryNoticeRequestDTO);
    }

    /**
     * 批量发布
     * @param deliveryNotices
     */
    @PostMapping("/releasedBatch")
    public void releasedBatch(@RequestBody List<DeliveryNotice> deliveryNotices){
        iDeliveryNoticeService.releasedBatch(deliveryNotices);
    }

    /**
     * 创建送货单-查询送货通知
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/listInDeliveryNote")
    public PageInfo<OrderDetailDTO> listInDeliveryNote(@RequestBody OrderRequestDTO orderRequestDTO){
        return iDeliveryNoticeService.listInDeliveryNote(orderRequestDTO);
    }

}

