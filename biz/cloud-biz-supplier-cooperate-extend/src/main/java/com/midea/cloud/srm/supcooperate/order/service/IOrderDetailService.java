package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptNumVO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.OrderDetailBaseMaterialPriceDTO;
import com.midea.cloud.srm.model.supplier.info.dto.PurchaseAmountDto;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  采购订单明细表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:12
 *  修改内容:
 * </pre>
 */
public interface IOrderDetailService extends IService<OrderDetail> {

    PageInfo<OrderDetailDTO> listPage(OrderRequestDTO orderRequestDTO);

    void deleteBatch(Long orderId, List<Long> ids);

    OrderDetail getJitOrderDetail(OrderRequestDTO orderRequestDTO);

    List<OrderDetailDTO> listUnDeliveryPage(OrderRequestDTO orderRequestDTO);

    PageInfo<OrderDetailDTO> listMaterialPage(OrderRequestDTO orderRequestDTO);

    /**
     * 获取近三年的采购金额汇总, 维度 供应商+品类
     *
     * @param vendorId
     * @param categoryId
     * @return
     */
    List<PurchaseAmountDto> aggregateAmount(Long vendorId, Long categoryId) throws ParseException;

    List<OrderDetailDTO> OrderDetailListPage(OrderRequestDTO orderRequestDTO);

    List<OrderDetailDTO> OrderDetailListPage1(OrderRequestDTO orderRequestDTO);

    List<OrderDetailDTO> OrderDetailListPageCopy(OrderRequestDTO orderRequestDTO);

    void acceptBuyerSubmit(List<AcceptNumVO> acceptNumVOList);

    List<OrderDetail> getOrderDetailForCheck(Long requirementLineId);

    List<OrderDetail> getOrderDetailByOrderStatus();

    /**
     * 检查采购订单是否引用指定的合同
     *
     * @param contractHead
     * @return
     */
    boolean checkOrderDetailIfQuoteContract(ContractHead contractHead);

    /**
     * 计算订单基价并填写到数据库中
     *
     * @param orderDetailBaseMaterialPriceDTO
     * @return
     */
    BaseResult calcBaseMaterialPrice(OrderDetailBaseMaterialPriceDTO orderDetailBaseMaterialPriceDTO);

    /**
     * 获取有效的订单详情信息
     * @param orderDetails
     * @return
     */
    List<OrderDetail> listValidOrderDetail(List<OrderDetail> orderDetails);

    /**
     * 供应商接受
     * @param ids
     */
    void supplierConfirm(List<Long> ids);

    /**
     * 供应商拒绝
     * @param orderDetail
     */
    void supplierReject(OrderDetail orderDetail);

    /**
     * 送货通知单 - 选择订单明细
     * @param orderDetailDTO
     * @return
     */
    PageInfo<OrderDetailDTO> listInDeliveryNotice(OrderDetailDTO orderDetailDTO);

    /**
     * 订单明细列表查询
     * @param orderRequestDTO
     * @return
     */
    PageInfo<OrderDetailDTO> listPageNew(OrderRequestDTO orderRequestDTO);

    /**
     * 查询订单物料明细
     * @param orderRequestDTO
     * @return
     */
    PageInfo<OrderDetailDTO> listInDeliveryNote(OrderRequestDTO orderRequestDTO);
}
