package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptNumVO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.OrderDetailBaseMaterialPriceDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.PurchaseAmountDto;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDeleteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.QuantityAmountDto;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <pre>
 *   订单协同 订单明细表 前端控制器
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
@RequestMapping("/order/orderDetail")
public class OrderDetailController extends BaseController {
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    /**
     * 校验数量不为0的采购订单行上，有没有该合同的合同编号、合同序号：
     * @param contractHead
     * @return
     */
    @PostMapping("/checkOrderDetailIfQuoteContract")
    public boolean checkOrderDetailIfQuoteContract(@RequestBody ContractHead contractHead){
        return iOrderDetailService.checkOrderDetailIfQuoteContract(contractHead);
    }

    @PostMapping("/getOrderDetail")
    public OrderDetail getOrderDetail(@RequestBody OrderDetail orderDetail) {
        return iOrderDetailService.getOne(new QueryWrapper<OrderDetail>(orderDetail));
    }

    @PostMapping("/getJitOrderDetail")
    public OrderDetail getJitOrderDetail(@RequestBody OrderRequestDTO orderRequestDTO) {
        return iOrderDetailService.getJitOrderDetail(orderRequestDTO);
    }


    @GetMapping("/deleteOrderDetailByOrderId")
    public void deleteOrderDetailByOrderId(Long orderId) {
    	iOrderDetailService.remove(new QueryWrapper<OrderDetail>(new OrderDetail().setOrderId(orderId)));
    }

    @GetMapping("/getOrderDetailByOrderId")
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        List<OrderDetail> orderDetailList = iOrderDetailService.list(new QueryWrapper<OrderDetail>(new OrderDetail().setOrderId(orderId)));
        for(OrderDetail orderDetail:orderDetailList){
            List<PurchaseTax> purchaseTaxList = baseClient.queryTaxByItemForOrder(orderDetail.getMaterialId());
            orderDetail.setPurchaseTaxList(purchaseTaxList);
        }
        return orderDetailList;
    }

    @GetMapping("/getQuantityAmountDto")
    public QuantityAmountDto getQuantityAmountDto(@RequestParam("externalRowId") Long externalRowId) {
        QuantityAmountDto quantityAmountDto = null;
        List<OrderDetail> orderDetails = iOrderDetailService.list(new QueryWrapper<>(new OrderDetail().setExternalRowId(externalRowId)));
        if(!CollectionUtils.isEmpty(orderDetails)){
            quantityAmountDto = new QuantityAmountDto();
            AtomicReference<BigDecimal> sum = new AtomicReference(BigDecimal.ZERO);
            orderDetails.sort(Comparator.comparing(OrderDetail::getUnitPriceContainingTax));
            quantityAmountDto.setUnitPriceContainingTax(orderDetails.get(0).getUnitPriceContainingTax());
            orderDetails.forEach(orderDetail -> {
                BigDecimal add = sum.get().add(orderDetail.getOrderNum());
                sum.set(add);
            });
            if(!sum.get().equals(BigDecimal.ZERO)){
                quantityAmountDto.setOrderNum(sum.get());
            }
        }
        return quantityAmountDto;
    }

    @PostMapping("/getOrderDetailByOrderIds")
    public List<OrderDetail> getOrderDetailByOrderIds(@RequestBody List<Long> orderIds) {
    	return orderIds == null || orderIds.size() == 0 ? new ArrayList<OrderDetail>() : iOrderDetailService.list(new QueryWrapper<OrderDetail>().in("ORDER_ID", orderIds));
    }

    @PostMapping("/getOrderDetailListByIds")
    public List<OrderDetail> getOrderDetailListByIds(@RequestBody List<Long> orderDetailIds) {
    	return iOrderDetailService.listByIds(orderDetailIds);
    }

    /**
     * 批量更新订单行
     *
     * @param orderDetailList
     */
    @PostMapping("/batchUpdateOrderDetail")
    void batchUpdateOrderDetail(@RequestBody List<OrderDetail> orderDetailList){
        iOrderDetailService.updateBatchById(orderDetailList);
    }

    /**
     * 分页查询订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<OrderDetailDTO> listPage(@RequestBody OrderRequestDTO orderRequestDTO) {
//        return iOrderDetailService.listPage(orderRequestDTO);
        return iOrderDetailService.listPageNew(orderRequestDTO);
    }

    /**
     * 分页查询订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/OrderDetailListPage")
    public PageInfo<OrderDetailDTO> OrderDetailListPage(@RequestBody OrderRequestDTO orderRequestDTO) {
        PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
        return new PageInfo<>(iOrderDetailService.OrderDetailListPage(orderRequestDTO));
    }
    /**
     * 分页查询订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/OrderDetailList")
    public List<OrderDetailDTO> OrderDetailList(@RequestBody OrderRequestDTO orderRequestDTO) {
        if ("SERVICE".equals(orderRequestDTO.getAssetType())){
            return iOrderDetailService.OrderDetailListPageCopy(orderRequestDTO);
        }
        return iOrderDetailService.OrderDetailListPage(orderRequestDTO);
    }

    /**
     * 查询订物料小类对应的大类为服务类的订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/OrderDetailListPageCopy")
    public PageInfo<OrderDetailDTO> OrderDetailListPageCopy(@RequestBody OrderRequestDTO orderRequestDTO) {
        PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
        return new PageInfo<>(iOrderDetailService.OrderDetailListPageCopy(orderRequestDTO));
    }

    /**
     * 分页查询订单物料明细 用于查询出符合某些条件的采购订单中采购订单详情的物料明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/listMaterialPage")
    public PageInfo<OrderDetailDTO> listMaterialPage(@RequestBody OrderRequestDTO orderRequestDTO) {
        Assert.isTrue(StringUtils.isNotEmpty(orderRequestDTO.getCeeaReceiveAddress()),"交货地点为空，找不到对应的订单明细。");
        return iOrderDetailService.listMaterialPage(orderRequestDTO);
    }

    /**
     * 分页查询未送货完成订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/listUnDeliveryPage")
    public PageInfo<OrderDetailDTO> listUnDeliveryPage(@RequestBody OrderRequestDTO orderRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
        return new PageInfo<OrderDetailDTO>(iOrderDetailService.listUnDeliveryPage(orderRequestDTO));
    }

    /**
     * 采购商批量删除订单明细
     * @param param
     */
    @PostMapping("/deleteBatch")
    public void deleteBatch(@RequestBody OrderDetailDeleteRequestDTO param) {
        Long orderId = param.getOrderId();
        List<Long> detailIds = param.getDetailIds();

        Assert.notNull(orderId,"订单ID不能为空");
        iOrderDetailService.deleteBatch(orderId,detailIds);
    }

    /**
     * 根据requirementLineId查询订单行
     */
    @PostMapping("/getOrderDetailByRequirementLineId")
    public List<OrderDetail> getOrderDetailByRequirementLineId(@RequestParam("requirementLineId") Long requirementLineId){
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("CEEA_REQUIREMENT_LINE_ID",requirementLineId);
        List<OrderDetail> orderDetailList = iOrderDetailService.list(wrapper);
        return orderDetailList;
    }

    /**
     * 根据requirementLineId查询订单行
     */
    @PostMapping("/getOrderDetailByRequirementLineIds")
    public List<OrderDetail> getOrderDetailByRequirementLineIds(@RequestBody Collection<Long> requirementLineIds){
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.in("CEEA_REQUIREMENT_LINE_ID",requirementLineIds);
        List<OrderDetail> orderDetailList = iOrderDetailService.list(wrapper);
        return orderDetailList;
    }


    /**
     * 查询出 状态为审批中，持有这些采购申请的订单
     * @param requirementLineId
     * @return
     */
    @PostMapping("/getOrderDetailForCheck")
    public List<OrderDetail> getOrderDetailForCheck(@RequestParam("requirementLineId") Long requirementLineId){
        return iOrderDetailService.getOrderDetailForCheck(requirementLineId);
    }


    @PostMapping("/judgeEnoughCount")
    public boolean judgeEnoughCount(@RequestBody List<OrderDetailDTO> orderDetailDTOList){
        List<OrderDetail> list = iOrderDetailService.getOrderDetailByOrderStatus();

        for(OrderDetailDTO item:orderDetailDTOList){
            if("Y".equals(item.getCeeaIfRequirement())){
                List<OrderDetail> result = new ArrayList<>();
                for(OrderDetail orderDetail:list){
                    if(Objects.equals(item.getCeeaRequirementLineId(),orderDetail.getCeeaRequirementLineId())){
                        result.add(orderDetail);
                    }
                }
                BigDecimal allSum = BigDecimal.ZERO;
                for(int i=0;i<result.size();i++){
                    allSum = allSum.add(result.get(i).getOrderNum());
                }
                if(item.getRequirementLineNum().compareTo(allSum.add(item.getOrderNum())) == -1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取近三年采购金额汇总
     * @param vendorId
     * @param categoryId
     * @return
     */
    @GetMapping("/aggregateAmount")
    public List<PurchaseAmountDto> aggregateAmount(@RequestParam("vendorId") Long vendorId, @RequestParam("categoryId") Long categoryId) throws ParseException {
        return iOrderDetailService.aggregateAmount(vendorId, categoryId);
    }

    /**
     * 回写已验收数量
     * @param acceptNumVOList
     */
    @PostMapping("acceptBuyerSubmit")
    public void acceptBuyerSubmit(@RequestBody List<AcceptNumVO> acceptNumVOList){
        Assert.notEmpty(acceptNumVOList,"需要回写的对象为空");
        iOrderDetailService.acceptBuyerSubmit(acceptNumVOList);
    }

    /**
     * 根据id更新物料明细
     * @param orderDetail
     */
    @PostMapping("/updateById")
    public void updateById(@RequestBody OrderDetail orderDetail){
        iOrderDetailService.updateById(orderDetail);
    }

    /**
     * 根据id获取物料明细
     * @param orderDetailId
     * @return
     */
    @GetMapping("/getById")
    public OrderDetail getById(@RequestParam("orderDetailId") Long orderDetailId){
        return iOrderDetailService.getById(orderDetailId);
    }


    /**
     * 计算基价，并更新到数据库中
     * @param requirementLineBaseMaterialPriceDTO
     * @return
     */
    @PostMapping("/calcBaseMaterialPrice")
    public BaseResult calcBaseMaterialPrice(@RequestBody OrderDetailBaseMaterialPriceDTO requirementLineBaseMaterialPriceDTO){
        return iOrderDetailService.calcBaseMaterialPrice(requirementLineBaseMaterialPriceDTO);
    }


    /**
     * 供应商接受
     * @param ids
     */
    @PostMapping("/supplierConfirm")
    public void supplierConfirm(@RequestBody List<Long> ids){
        iOrderDetailService.supplierConfirm(ids);
    }

    /**
     * 供应商拒绝
     * @param orderDetail
     */
    @PostMapping("/supplierReject")
    public void supplierReject(@RequestBody OrderDetail orderDetail){
        iOrderDetailService.supplierReject(orderDetail);
    }

    /**
     * 送货通知单 - 选择订单明细
     * @param orderDetailDTO
     * @return
     */
    @PostMapping("/listInDeliveryNotice")
    public PageInfo<OrderDetailDTO> listInDeliveryNotice(@RequestBody OrderDetailDTO orderDetailDTO){
        return iOrderDetailService.listInDeliveryNotice(orderDetailDTO);
    }

    /**
     * 送货单 - 选择订单明细
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/listInDeliveryNote")
    public PageInfo<OrderDetailDTO> listInDeliveryNote(@RequestBody OrderRequestDTO orderRequestDTO){
        Assert.isTrue(StringUtils.isNotEmpty(orderRequestDTO.getCeeaReceiveAddress()),"交货地点为空，找不到对应的订单明细。");
        return iOrderDetailService.listInDeliveryNote(orderRequestDTO);
    }
}
