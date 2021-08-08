package com.midea.cloud.srm.feign.supcooperate;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptNumVO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanLineQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReceipt;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReturn;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.model.supplier.info.dto.PurchaseAmountDto;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.*;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.*;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.ReturnOrderVO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  订单协同模块 内部调用Feign接口
 * </pre>
 *
 * @author huangbf3meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-14 17:R19
 *  修改内容:
 *          </pre>
 */
@FeignClient("cloud-biz-supplier-cooperate")
public interface SupcooperateClient extends FlowBusinessCallbackClient {

    /**
     * 检查采购订单是否引用指定的合同
     *
     * @param contractHead
     * @return
     */
    @PostMapping("/order/orderDetail/checkOrderDetailIfQuoteContract")
    boolean checkOrderDetailIfQuoteContract(@RequestBody ContractHead contractHead);

    @GetMapping("/order/order/getOrderByOrderNumber")
    Order getOrderByOrderNumber(@RequestParam("orderNumber") String orderNumber);

    /**
     * 待确认订单统计
     */
    @GetMapping("/order/order/countSubmit")
    WorkCount orderCountSubmit();

    /**
     * 待创建送货单统计
     */
    @GetMapping("/order/deliveryNote/countCreate")
    WorkCount deliveryNoteCountCreate();

    /**
     * 采购商分页查询
     *
     * @param deliveryNoteRequestDTO 送货单数据请求传输对象
     * @return
     */
    @PostMapping("/order/deliveryNote/deliveryNotePage")
    PageInfo<DeliveryNoteDTO> deliveryNotePage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /**
     * 采购商分页查询送货单明细
     *
     * @param deliveryNoteRequestDTO 送货数据请求传输对象
     * @return
     */
    @PostMapping("/order/deliveryNoteDetail/deliveryNoteDetailPage")
    PageInfo<DeliveryNoteDetailDTO> deliveryNoteDetailPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /**
     * 通过订单ID与物料ID查询送货通知单
     */
    @PostMapping("/order/deliveryNotice/getOne")
    DeliveryNotice getOneDeliveryNotice(DeliveryNotice deliveryNotice);

    /**
     * 根据ID获取订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/order/order/getOrderById")
    Order getOrderById(@RequestParam("orderId") Long orderId);

    /**
     * 根据ID集合获取订单
     *
     * @param orderIdList
     * @return
     */
    @PostMapping("/order/order/getOrderByIds")
    List<Order> getOrderByIds(@RequestBody List<Long> orderIdList);

    /**
     * 根据订单头ID删除订单明细
     *
     * @param orderId
     */
    @GetMapping("/order/orderDetail/deleteOrderDetailByOrderId")
    void deleteOrderDetailByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 采购订单查询
     *
     * @param order 条件
     */
    @PostMapping("/order/order/listParamOrder")
    List<Order> listParamOrder(@RequestBody Order order);

    /**
     * 根据外部ID集合获取订单列表
     *
     * @param externalIds
     * @return
     */
    @PostMapping("/order/order/getOrderByExternalIds")
    List<Order> getOrderByExternalIds(@RequestBody List<Long> externalIds);

    /**
     * 获取近三年采购金额汇总 维度: 供应商+采购分类
     *
     * @param vendorId
     * @param categoryId
     * @return
     */
    @GetMapping("/order/orderDetail/aggregateAmount")
    List<PurchaseAmountDto> aggregateAmount(@RequestParam("vendorId") Long vendorId, @RequestParam("categoryId") Long categoryId) throws ParseException;

    /**
     * 根据外办行id查找最低单价和总下单数
     *
     * @param externalRowId
     * @return
     */
    @GetMapping("/order/orderDetail/getQuantityAmountDto")
    QuantityAmountDto getQuantityAmountDto(@RequestParam("externalRowId") Long externalRowId);

    /**
     * 根据订单ID集合获取订单明细
     *
     * @param orderIds
     * @return
     */
    @PostMapping("/order/orderDetail/getOrderDetailByOrderIds")
    List<OrderDetail> getOrderDetailByOrderIds(@RequestBody List<Long> orderIds);

    /**
     * 获取明细
     *
     * @param orderDetail
     * @return
     */
    @PostMapping("/order/orderDetail/getOrderDetail")
    OrderDetail getOrderDetail(@RequestBody OrderDetail orderDetail);

    /**
     * 获取明细
     *
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/order/orderDetail/getJitOrderDetail")
    OrderDetail getJitOrderDetail(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 批量更新订单
     *
     * @param orderList
     */
    @PostMapping("/order/order/batchUpdateOrder")
    void batchUpdateOrder(@RequestBody List<Order> orderList);

    /**
     * 批量更新订单行
     *
     * @param orderDetailList
     */
    @PostMapping("/order/orderDetail/batchUpdateOrderDetail")
    void batchUpdateOrderDetail(@RequestBody List<OrderDetail> orderDetailList);

    /**
     * 报错订单
     *
     * @param order
     */
    @PostMapping("/order/order/saveOrder")
    void saveOrder(@RequestBody Order order);

    /**
     * 根据id集合
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/orderDetail/getOrderDetailListByIds")
    List<OrderDetail> getOrderDetailListByIds(@RequestBody List<Long> ids);

    /**
     * 订单分页
     *
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/order/order/listPage")
    PageInfo<OrderDTO> orderListPage(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 订单条件查询
     *
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/order/order/orderListByParam")
    List<OrderDTO> orderListByParam(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 订单条件查询头id
     *
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/order/order/listOrderIdsByParam")
    List<Long> listOrderIdsByParam(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 根据头ids查询行ids
     *
     * @param orderIds
     * @return
     */
    @PostMapping("/order/order/listOrderDetailIdsByOrderIds")
    int listOrderDetailIdsByOrderIds(@RequestBody List<Long> orderIds);

    /**
     * 订单变更分页
     */
    @PostMapping("/order/order/listPageUpdates")
    PageInfo<OrderDTO> orderListPageUpdates(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 订单行分页
     *
     * @param orderRequestDTO
     * @return
     */
    @PostMapping("/order/orderDetail/listPage")
    PageInfo<OrderDetailDTO> orderDetailListPage(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 创建订单
     *
     * @param param
     */
    @PostMapping("/order/order/saveOrderForm")
    void saveOrderForm(@RequestBody OrderSaveRequestDTO param);

    /**
     * 采购商分页查询
     *
     * @param requestDTO
     * @return
     */
    @PostMapping("/order/deliveryNotice/deliveryNoticelListPage")
    PageInfo<DeliveryNoticeDTO> deliveryNoticelListPage(@RequestBody DeliveryNoticeRequestDTO requestDTO);

    /**
     * 采购商通过通知单id获取通知单
     *
     * @param deliveryNoticeId
     * @return
     */
    @GetMapping("/order/deliveryNotice/getById")
    DeliveryNotice getDeliveryNoticeById(@RequestParam("deliveryNoticeId") Long deliveryNoticeId);

    /**
     * 根据主键查询
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/deliveryNotice/queryDeliveryNoticeByIds")
    List<DeliveryNotice> queryDeliveryNoticeByIds(@RequestBody List<Long> ids);

    /**
     * 采购商更新送货通知单
     *
     * @param deliveryNotice
     * @return
     */
    @PostMapping("/order/deliveryNotice/updateDeliveryNoticeById")
    void updateDeliveryNoticeById(@RequestBody DeliveryNotice deliveryNotice);

    /**
     * 采购商批量更新送货通知单
     *
     * @param list
     * @return
     */
    @PostMapping("/order/deliveryNotice/updateBatchById")
    void updateBatchById(@RequestBody List<DeliveryNotice> list);

    /**
     * 采购商批量保存送货通知单
     *
     * @param deliveryNotices
     * @return
     */
    @PostMapping("/order/deliveryNotice/saveBatchDeliveryNotice")
    void saveBatchDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNotices);

    /**
     * 根据送货明细ID查询入库记录
     *
     * @param deliveryNoteDetailIds
     * @return
     */
    @PostMapping("/order/warehouseReceipt/queryWarehouseReceiptByDeliveryNoteDetailIds")
    List<WarehouseReceipt> queryWarehouseReceiptByDeliveryNoteDetailIds(@RequestBody List<Long> deliveryNoteDetailIds);

    /**
     * 根据主键查询入库记录
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/warehouseReceipt/queryWarehouseReceiptByIds")
    List<WarehouseReceipt> queryWarehouseReceiptByIds(@RequestBody List<Long> ids);

    /**
     * 保存or更新入库记录
     *
     * @param warehouseReceiptList
     */
    @PostMapping("/order/warehouseReceipt/saveOrUpdateWarehouseReceipt")
    void saveOrUpdateWarehouseReceipt(@RequestBody List<WarehouseReceipt> warehouseReceiptList);

    /**
     * 订单审批
     * @param orderId
     */
    @GetMapping("/order/order/approval")
    void approval(@RequestParam("orderId") Long orderId);

    /**
     * 批量删除订单
     * @param ids
     */
    @PostMapping("/order/order/batchDelete")
    void batchDelete(@RequestBody List<Long> ids);

    /**
     * 批量确认送货预约单
     * @param ids
     */
    @PostMapping("/order/deliveryAppoint/confirmBatch")
    void confirmBatch(@RequestBody List<Long> ids);

    /**
     * 批量拒绝送货单
     * @param requestDTO
     */
    @PostMapping("/order/deliveryAppoint/refuseBatch")
    void refuseBatch(@RequestBody DeliveryAppointRequestDTO requestDTO);

    /**
     * 分页查询列表
     * @param deliveryAppointRequestDTO
     * @return
     */
    @PostMapping("/order/deliveryAppoint/listPage")
    PageInfo<DeliveryAppoint> listPageDeliveryAppoint(@RequestBody DeliveryAppointRequestDTO deliveryAppointRequestDTO);


    @Data
    @Accessors(chain = true)
    class SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail {
        private List<WarehouseReceipt> warehouseReceiptList;
        private List<OrderDetail> orderDetailList;
    }

    /**
     * 保存入库&更新订单行
     *
     * @param param
     */
    @PostMapping("/order/warehouseReceipt/saveOrUpdateWarehouseReceiptAndUpdateOrderDetail")
    void saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(@RequestBody SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail param);

    /**
     * 根据主键查询送货单
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/deliveryNoteDetail/queryDeliveryNoteDetailByIds")
    List<DeliveryNoteDetail> queryDeliveryNoteDetailByIds(@RequestBody List<Long> ids);

    /**
     * 批量查询送货单信息
     *
     * @param deliveryNoteDetails
     * @return
     */
    @PostMapping("/order/deliveryNoteDetail/listDeliveryNoteDetailMore")
    List<DeliveryNoteDetail> listDeliveryNoteDetailMore(@RequestBody List<DeliveryNoteDetail> deliveryNoteDetails);

    /**
     * 退货列表
     *
     * @param returnOrderRequestDTO
     * @return
     */
    @PostMapping("/order/returnOrder/listPage")
    PageInfo<ReturnOrder> returnOrderPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO);

    /**
     * 根据ID获取退货单
     *
     * @param returnOrderId
     * @return
     */
    @GetMapping("/order/returnOrder/getReturnOrderById")
    ReturnOrder getReturnOrderById(@RequestParam("returnOrderId") Long returnOrderId);

    /**
     * 根据退货订单头集合查询退货明细行
     *
     * @param returnOrderIds
     * @return
     */
    @PostMapping("/order/returnDetail/queryReturnDetailByReturnOrderIds")
    List<ReturnDetail> queryReturnDetailByReturnOrderIds(@RequestBody List<Long> returnOrderIds);

    /**
     * 根据主键获取
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/returnDetail/queryReturnDetailByIds")
    List<ReturnDetail> queryReturnDetailByIds(@RequestBody List<Long> ids);

    /**
     * 获取退货单详情
     *
     * @param returnOrderId
     * @return
     */
    @GetMapping("/order/returnDetail/getReturnDetailListByReturnOrderId")
    List<ReturnDetailDTO> getReturnDetailListByReturnOrderId(@RequestParam("returnOrderId") Long returnOrderId);

    @Data
    @Accessors(chain = true)
    class SaveOrUpdateReturnOrder {
        @NotNull(message = "退货订单头不能为空")
        private ReturnOrder returnOrder;// 退货订单头
        @NotEmpty(message = "退货明细行不能为空")
        private List<ReturnDetail> returnDetails;// 退货明细行
        private List<OrderDetail> orderDetails;// 订单明细
        private List<DeliveryNotice> deliveryNotices;// 送货通知单
    }

    /**
     * 创建退货单
     *
     * @param saveOrUpdateReturnOrder
     */
    @PostMapping("/order/returnOrder/saveOrUpdateReturnOrder")
    void saveOrUpdateReturnOrder(@RequestBody SaveOrUpdateReturnOrder saveOrUpdateReturnOrder);

    /**
     * 更新退货订单行
     *
     * @param returnDetails
     */
    @PostMapping("/order/returnDetail/saveOrUpdateReturnDetails")
    void saveOrUpdateReturnDetails(@RequestBody List<ReturnDetail> returnDetails);

    @Data
    @Accessors(chain = true)
    class ConfirmReturnOrders {
        @NotEmpty(message = "退货订单头不能为空")
        private List<ReturnOrder> returnOrders;// 退货单头表
        private List<OrderDetail> orderDetails;// 订单明细
        private List<DeliveryNotice> deliveryNotices;// 送货通知单
    }

    /**
     * 确认退货订单
     *
     * @param confirmReturnOrders
     */
    @PostMapping("/order/returnOrder/confirmReturnOrders")
    void confirmReturnOrders(@RequestBody ConfirmReturnOrders confirmReturnOrders);

    /**
     * 查询退货单
     *
     * @param ids
     */
    @PostMapping("/order/returnOrder/getReturnOrderByIds")
    List<ReturnOrder> getReturnOrderByIds(@RequestBody List<Long> ids);

    /**
     * 对账单选择入库单明细列表
     *
     * @param warehouseReceiptPageQueryDTO
     * @return
     */
    @PostMapping("/order/warehouseReceipt/listStatementReceiptDTOPage")
    PageInfo<StatementReceiptDTO> listStatementReceiptDTOPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

    /**
     * 对账单选择退货单
     *
     * @param returnOrderRequestDTO
     * @return
     */
    @PostMapping("/order/returnDetail/listStatementReturnDTOPage")
    PageInfo<StatementReturnDTO> listStatementReturnDTOPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO);

    /**
     * 对账单分页
     *
     * @param statementHead
     * @return
     */
    @PostMapping("/ps/statementHead/listStatementHeadPage")
    PageInfo<StatementHead> listStatementHeadPage(@RequestBody StatementHead statementHead);

    /**
     * 采购商新增对账单
     *
     * @param statementDTO
     */
    @PostMapping("/ps/statementHead/saveStatement")
    void saveStatement(@RequestBody StatementDTO statementDTO);

    /**
     * 采购商修改对账单
     *
     * @param statementDTO
     */
    @PostMapping("/ps/statementHead/updateStatement")
    void updateStatement(@RequestBody StatementDTO statementDTO);

    /**
     * 采购商提交对账单
     *
     * @param statementDTO
     */
    @PostMapping("/ps/statementHead/submitStatement")
    void submitStatement(@RequestBody StatementDTO statementDTO);

    /**
     * 采购商删除对账单
     *
     * @param statementHeadIds
     */
    @PostMapping("/ps/statementHead/deleteStatement")
    void deleteStatement(@RequestBody List<Long> statementHeadIds);

    /**
     * 批量更新对账单头表
     *
     * @param statementHeadList
     */
    @PostMapping("/ps/statementHead/saveOrUpdateBatchStatementHead")
    void saveOrUpdateBatchStatementHead(@RequestBody List<StatementHead> statementHeadList);

    /**
     * 根据ID获取对账单
     *
     * @param statementHeadId
     * @return
     */
    @GetMapping("/ps/statementHead/getStatementHeadById")
    StatementHead getStatementHeadById(@RequestParam("statementHeadId") Long statementHeadId);

    /**
     * 根据ID集合获取对账单
     *
     * @param statementHeadIds
     * @return
     */
    @PostMapping("/ps/statementHead/getStatementHeadByIds")
    List<StatementHead> getStatementHeadByIds(@RequestBody List<Long> statementHeadIds);

    /**
     * 获取未付款完成的对账单
     *
     * @param paymentPlanLineQueryDTO
     * @return
     */
    @PostMapping("/ps/statementHead/getStatementHeadByPaymentPlanLineQueryDTO")
    PageInfo<StatementHead> getStatementHeadByPaymentPlanLineQueryDTO(@RequestBody PaymentPlanLineQueryDTO paymentPlanLineQueryDTO);

    /**
     * 根据对账单头ID获取对账单入库明细
     *
     * @param statementHeadId
     * @return
     */
    @GetMapping("/ps/statementHead/getStatementReceiptByStatementHeadId")
    List<StatementReceipt> getStatementReceiptByStatementHeadId(@RequestParam("statementHeadId") Long statementHeadId);

    /**
     * 根据对账单头ID获取对账单退货明细
     *
     * @param statementHeadId
     * @return
     */
    @GetMapping("/ps/statementHead/getStatementReturnByStatementHeadId")
    List<StatementReturn> getStatementReturnByStatementHeadId(@RequestParam("statementHeadId") Long statementHeadId);

    /**
     * 根据对账单ID获取详情
     *
     * @param statementHeadId
     * @return
     */
    @GetMapping("/ps/statementHead/getStatementById")
    StatementDTO getStatementById(@RequestParam(value = "statementHeadId", required = true) Long statementHeadId);

    /**
     * 分页条件查询(采购商端)
     *
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/invoice/invoiceNotice/listPageByParm")
    PageInfo<InvoiceNotice> invoiceNoticeListPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 更新发票通知
     *
     * @param invoiceNotice
     */
    @PostMapping("/invoice/invoiceNotice/updateInvoiceNotice")
    void updateInvoiceNotice(@RequestBody InvoiceNotice invoiceNotice);

    /**
     * 获取InvoiceNoticeSaveDTO
     *
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/invoice/invoiceNotice/getInvoiceNoticeSaveDTO")
    InvoiceNoticeSaveDTO getInvoiceNoticeSaveDTO(@RequestParam("invoiceNoticeId") Long invoiceNoticeId);

    @GetMapping("/sc-anon/internal/getOrderById")
    Order getOrderByIdFromIner(@RequestParam("orderId") Long orderId);

    @PostMapping("/sc-anon/internal/updateById")
    void updateOrderByIdFromIner(@RequestBody Order order);

    /**
     * 获取供应商首次送货单
     *
     * @param vendorId 订单ids
     */
    @GetMapping("/order/deliveryNote/getFirstDeliveryNo")
    DeliveryNote getFirstDeliveryNo(@RequestParam("vendorId") Long vendorId);

    /**
     * 根据订单ID获取订单明细
     *
     * @param orderId
     * @return
     */
    @GetMapping("/order/orderDetail/getOrderDetailByOrderId")
    List<OrderDetail> getOrderDetailByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据orderId查询订单附件
     */
    @GetMapping("/order/orderAttach/getOrderAttachByOrderId")
    List<OrderAttach> getOrderAttachByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据orderId查询付款条款
     */
    @GetMapping("/order/orderPaymentProvision/getOrderPaymentProvisionByOrderId")
    List<OrderPaymentProvision> getOrderPaymentProvisionByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据orderId删除订单(包括订单头，订单行，订单附件，订单付款条款)
     */
    @PostMapping("/order/order/delete")
    void deleteOrderByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据orderId删除订单附件
     */
    @PostMapping("order/orderAttach/deleteOrderAttachByOrderId")
    void deleteOrderAttachByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据orderId删除订单付款条款
     */
    @PostMapping("order/orderPaymentProvision/deleteOrderPaymentProvisionByOrderId")
    void deleteOrderPaymentProvisionByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据requirementLineId查询OrderDetail
     */
    @PostMapping("order/orderDetail/getOrderDetailByRequirementLineId")
    List<OrderDetail> getOrderDetailByRequirementLineId(@RequestParam("requirementLineId") Long requirementLineId);

    /**
     * 根据requirementLineId查询OrderDetail
     */
    @PostMapping("order/orderDetail/getOrderDetailByRequirementLineIds")
    List<OrderDetail> getOrderDetailByRequirementLineIds(@RequestBody Collection<Long> requirementLineIds);

    /**
     * 保存订单文件
     */
    @PostMapping("order/orderAttach/saveOrderAttach")
    void saveOrderAttach(@RequestBody OrderAttach orderAttach);

    /**
     * 分页查询入库退货明细
     */
    @PostMapping("order/warehousingReturnDetail/listPage")
    PageInfo<WarehousingReturnDetail> warehousingReturnDetailListPage(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    /**
     * 查询入库退货明细
     */
    @PostMapping("/order/warehousingReturnDetail/list")
    List<WarehousingReturnDetail> warehousingReturnDetailList(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    /**
     * 保存付款条款
     */
    @PostMapping("order/orderPaymentProvision/save")
    void saveOrderPaymentProvision(@RequestBody OrderSaveRequestDTO param);

    /**
     * 供应商暂存（ceea:采购商暂存）
     *
     * @param invoiceNoticeSaveDTO
     */
    @PostMapping("/invoice/invoiceNotice/saveTemporary")
    Long saveTemporary(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    /**
     * 供应商提交(ceea:采购商提交)
     *
     * @param invoiceNoticeSaveDTO
     */
    @PostMapping("/invoice/invoiceNotice/submit")
    void submit(@RequestBody InvoiceNoticeSaveDTO invoiceNoticeSaveDTO);

    /**
     * 通过发票通知单ID删除
     *
     * @param invoiceNoticeId
     */
    @GetMapping("/invoice/invoiceNotice/deleteByInvoiceNoticeId")
    void deleteByInvoiceNoticeId(@RequestParam("invoiceNoticeId") Long invoiceNoticeId);

    /**
     * 批量删除
     *
     * @param invoiceDetailIds
     */
    @PostMapping("/invoice/invoiceDetail/batchDelete")
    void batchDeleteInvoiceDetails(@RequestBody List<Long> invoiceDetailIds);

    /**
     * 批量删除
     *
     * @param invoicePunishIds
     */
    @PostMapping("/invoice/invoicePunish/batchDelete")
    void bachDeleteInvoicePunishes(@RequestBody List<Long> invoicePunishIds);

    /**
     * 分页条件查询(ceea) modify by chensl26
     *
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/invoice/invoiceNotice/listPageByParm")
    PageInfo<InvoiceNotice> listPageByParm(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 分页查询发票通知明细
     *
     * @param invoiceNoticeQueryDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/listPageInvoiceNoticeDetail")
    PageInfo<InvoiceNoticeDTO> listPageInvoiceNoticeDetail(@RequestBody InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 分页查询网上发票(供应商端开具)
     */
    @PostMapping("/invoice/onlineInvoice/listPage")
    PageInfo<OnlineInvoice> listPageOnlineInvoice(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);

    @PostMapping("/invoice/onlineInvoice/list")
    List<OnlineInvoice> listOnlineInvoice(@RequestBody OnlineInvoice onlineInvoice);

    @PostMapping("/invoice/onlineInvoice/listByNumbers")
    List<OnlineInvoice> listOnlineInvoiceByNumbers(@RequestBody List<String> invoiceNumbers);

    /**
     * 创建网上发票
     *
     * @param invoiceNoticeDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/createOnlineInvoice")
    OnlineInvoiceSaveDTO createOnlineInvoice(@RequestBody List<InvoiceNoticeDTO> invoiceNoticeDTO);

    /**
     * 暂存(采购商端开具)
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/saveTemporaryForBuyer")
    Map<String, Object> saveTemporaryForBuyer(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    /**
     * 提交(供应商端)
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/submit")
    OnlineInvoiceSaveDTO submitOnlineInvoice(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    /**
     * 审核(采购商)
     *
     * @param OnlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/audit")
    OnlineInvoiceSaveDTO audit(@RequestBody OnlineInvoiceSaveDTO OnlineInvoiceSaveDTO);

    /**
     * 作废(采购商作废)
     *
     * @param onlineInvoiceId
     */
    @GetMapping("/invoice/onlineInvoice/buyerAbandon")
    Map<String, Object> abandonOnlineInvoice(@RequestParam("onlineInvoiceId") Long onlineInvoiceId);

    /**
     * 获取
     *
     * @param onlineInvoiceId
     * @return
     */
    @GetMapping("/invoice/onlineInvoice/get")
    OnlineInvoiceSaveDTO getOnlineInvoice(@RequestParam("onlineInvoiceId") Long onlineInvoiceId);

    /**
     * 网上开票费控状态返回
     *
     * @param boeNo
     * @param invoiceStatus
     */
    @PostMapping("/invoice/onlineInvoice/statusReturn")
    void statusReturn(@RequestParam("boeNo") String boeNo, @RequestParam("invoiceStatus") String invoiceStatus);

    /**
     * 网上开票费控状态返回
     *
     * @param boeNo
     * @param invoiceStatus
     */
    @PostMapping("/sc-anon/external/statusReturn")
    void statusReturnForAnon(@RequestParam("boeNo") String boeNo, @RequestParam("invoiceStatus") String invoiceStatus);

    /**
     * 网上开票费控导入状态返回
     *
     * @param boeNo
     * @param importStatus
     */
    @PostMapping("/sc-anon/external/importStatusReturn")
    void importStatusReturnForAnon(@RequestParam("boeNo") String boeNo, @RequestParam("importStatus") String importStatus);

    /**
     * 根据费控单号查询引用的预付款
     *
     * @param fsscNo
     * @return
     */
    @GetMapping("/invoice/onlineInvoiceAdvance/listOnlineInvoiceAdvanceByFsscNo")
    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNo(@RequestParam("fsscNo") String fsscNo);

    /**
     * 根据费控单号查询引用的预付款
     *
     * @param fsscNo
     * @return
     */
    @GetMapping("/sc-anon/external/listOnlineInvoiceAdvanceByFsscNo")
    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNoForAnon(@RequestParam("fsscNo") String fsscNo);

    /**
     * 分页查询订单明细
     *
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/order/orderDetail/OrderDetailList")
    List<OrderDetailDTO> OrderDetailList(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 批量删除网上开票预付款明细
     *
     * @param onlineInvoiceAdvanceIds
     */
    @PostMapping("/invoice/onlineInvoiceAdvance/batchDelete")
    void batchDeleteOnlineInvoiceAdvance(@RequestBody List<Long> onlineInvoiceAdvanceIds);

    /**
     * 回写订单明细的已验收数量
     *
     * @param acceptNumVOList
     */
    @PostMapping("/order/orderDetail/acceptBuyerSubmit")
    void acceptBuyerSubmit(@RequestBody List<AcceptNumVO> acceptNumVOList);

    /**
     * 根据id更新物料明细
     *
     * @param orderDetail
     */
    @PostMapping("/order/orderDetail/updateById")
    void updateOrderDetailById(@RequestBody OrderDetail orderDetail);

    /**
     * 根据id查询订单明细
     *
     * @param orderDetailId
     */
    @GetMapping("/order/orderDetail/getById")
    OrderDetail getOrderDetailById(@RequestParam("orderDetailId") Long orderDetailId);


    /**
     * 根据采购申请行id查询数据
     *
     * @param requirementLineId
     * @return
     */
    @PostMapping("/order/orderDetail/getOrderDetailForCheck")
    List<OrderDetail> getOrderDetailForCheck(@RequestParam("requirementLineId") Long requirementLineId);

    /**
     * @param orderDetailDTOList
     * @return
     */
    @PostMapping("/order/orderDetail/judgeEnoughCount")
    boolean judgeEnoughCount(@RequestBody List<OrderDetailDTO> orderDetailDTOList);

    @GetMapping("/invoice/invoiceDetail/getByInvoiceDetailId")
    InvoiceDetail getByInvoiceDetailId(@RequestParam("invoiceDetailId") Long invoiceDetailId);

    /**
     * 更新网上开票信息
     *
     * @param onlineInvoice 网上开票信息
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/updateOnlineInvoice")
    void updateOnlineInvoice(@RequestBody OnlineInvoice onlineInvoice);

    /**
     * 废弃
     *
     * @param invoiceNotice
     */
    @PostMapping("/invoice/invoiceNotice/abandon")
    void abandon(@RequestBody InvoiceNotice invoiceNotice);

    /**
     * 审核通过前需保存
     *
     * @param onlineInvoiceSaveDTO
     * @return
     */
    @PostMapping("/invoice/onlineInvoice/saveTemporaryBeforeAudit")
    Map<String, Object> saveTemporaryBeforeAudit(@RequestBody OnlineInvoiceSaveDTO onlineInvoiceSaveDTO);

    /**
     * 根据开票预付id集获取开票预付
     *
     * @param onlineInvoiceAdvanceIds
     * @return
     */
    @PostMapping("/invoice/onlineInvoiceAdvance/listOnlineInvoiceAdvanceByIds")
    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByIds(@RequestBody List<Long> onlineInvoiceAdvanceIds);

    /**
     * 开票通知撤回(采购商)
     *
     * @param invoiceNoticeId
     */
    @GetMapping("/invoice/invoiceNotice/withdraw")
    void invoiceNoticeWithdraw(@RequestParam("invoiceNoticeId") Long invoiceNoticeId);

    /**
     * 网上开票撤回(采购商)
     *
     * @param onlineInvoiceId
     */
    @GetMapping("/invoice/onlineInvoice/withdraw")
    void onlineInvoiceWithdraw(@RequestParam("onlineInvoiceId") Long onlineInvoiceId);

    /**
     * 批量获取订单id
     *
     * @param orderNumbers
     * @return
     */
    @PostMapping("/sc-anon/internal/order/batchGetOrderIdsByOrderNumbers")
    List<Order> batchGetOrderIdsByOrderNumbers(@RequestBody List<String> orderNumbers);

    /**
     * 根据id或者number 获取网上发票明细
     *
     * @param onlineInvoiceDetail
     * @return
     */
    @PostMapping("/invoice/onlineInvoiceDetail/list")
    List<OnlineInvoiceDetail> listOnlineInvoiceDetail(@RequestBody OnlineInvoiceDetail onlineInvoiceDetail);

    /**
     * 批量修改网上开票中预付款可用金额
     *
     * @param onlineInvoiceAdvances
     */
    @PostMapping("/sc-anon/external/updateOnlineInvoiceAdvanceByParam")
    void updateOnlineInvoiceAdvanceByParamForAnon(@RequestBody List<OnlineInvoiceAdvance> onlineInvoiceAdvances);

    @PostMapping("/invoice/onlineInvoice/queryBatchOnlineInvoiceSaveDTO")
    OnlineInvoiceQueryDTO queryBatchOnlineInvoiceSaveDTO(@RequestBody List<Long> onlineInvoiceIds);

    @GetMapping("/invoice/onlineInvoiceAdvance/listOnlineInvoiceAdvanceByOnlineInvoiceId")
    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByOnlineInvoiceId(@RequestParam("onlineInvoiceId") Long onlineInvoiceId);

    /**
     * 分页查询订单明细
     *
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/sc-anon/internal/OrderDetailList")
    List<OrderDetailDTO> OrderDetailListCopy(@RequestBody OrderRequestDTO orderRequestDTO);

    /**
     * 分页查询网上发票(供应商端开具,供应商查询) add by chensl26
     */
    @PostMapping("/invoice/onlineInvoice/buyerListPageFromPayment")
    PageInfo<OnlineInvoice> buyerListPageFromPayment(@RequestBody OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);

    /**
     * 获取开票通知头
     *
     * @param invoiceNoticeId
     * @return
     */
    @GetMapping("/invoice/invoiceNotice/getInvoiceNoticeById")
    InvoiceNotice getInvoiceNoticeById(@RequestParam("invoiceNoticeId") Long invoiceNoticeId);


    /**
     * 重置调整金额
     *
     * @param
     */
    @PostMapping("/invoice/onlineInvoice/restart")
    void restart(@RequestBody List<Long> onlineInvoiceIds);

    @PostMapping("/order/order/listOrderByOrderNumberOrErpNumber")
    List<Order> listOrderByOrderNumberOrErpNumber(@RequestBody List<String> numbers);

    /**
     * 根据订单头和订单行条件，找到指定订单行信息
     */
    @PostMapping("/sc-anon/internal/orderDetail/listValidOrderDetail")
    List<OrderDetail> listValidOrderDetail(@RequestBody List<OrderDetail> orderDetails);

    @PostMapping("/supco/po-line-settlement-erp/listPoLineSettlementErp")
    List<PoLineSettlementErp> listPoLineSettlementErp(@RequestBody PoLineSettlementErp poLineSettlementErp);

    @PostMapping("/order/order/queryDeliveryPlanExportData")
    List<List<Object>> queryDeliveryPlanExportData(@RequestBody ExportExcelParam<OrderRequestDTO> orderRequestDTOExportExcelParam);

    @PostMapping("/sc-anon/internal/getOrderDetailBySourcingIds")
    List<OrderDetail> getOrderDetailBySourcingIds(Collection<Long> sourcingIds);

    /**
     * 根据结算订单号查询ERP结算订单
     * @param settlementPoNumbers
     * @return
     */
    @PostMapping("/supco/po-head-settlement-erp/listPoSettlementsBySettlementPoNumber")
    List<PoHeadSettlementErp> listPoSettlementsBySettlementPoNumber(@RequestBody List<String> settlementPoNumbers);

    /**
     * 根据订单号查询所有的采购订单
     * @param orderNumbers
     * @return
     */
    @PostMapping("/order/order/listByOrderNumbers")
    List<Order> listByOrderNumbers(@RequestBody List<String> orderNumbers);

    /**
     * 送货通知查询订单明细
     * @param orderDetailDTO
     * @return
     */
    @PostMapping("/order/orderDetail/listInDeliveryNotice")
    PageInfo<OrderDetailDTO> listInDeliveryNotice(OrderDetailDTO orderDetailDTO);

    /**
     * 批量添加送货通知
     * @param deliveryNoticeList
     */
    @PostMapping("/order/deliveryNotice/batchAddDeliveryNotice")
    void batchAddDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNoticeList);

    /**
     * 批量更新送货通知
     * @param deliveryNoticeList
     */
    @PostMapping("/order/deliveryNotice/batchUpdateDeliveryNotice")
    void batchUpdateDeliveryNotice(@RequestBody List<DeliveryNotice> deliveryNoticeList);

    /**
     * 批量删除送货通知单
     * @param ids
     */
    @PostMapping("/order/deliveryNotice/batchDeleteDeliveryNotice")
    void batchDeleteDeliveryNotice(@RequestBody List<Long> ids);

    /**
     * 批量发布送货通知
     * @param deliveryNotices
     */
    @PostMapping("/order/deliveryNotice/releasedBatch")
    void releasedBatch(@RequestBody List<DeliveryNotice> deliveryNotices);

    /*入库单-start*/
    /**
     * 入库单分页
     *
     * @param warehouseReceiptPageQueryDTO
     * @return
     */
    @PostMapping("/order/warehouseReceipt/wareHouseReceiptListPage")
    PageInfo<WarehouseReceiptVO> wareHouseReceiptListPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

    /**
     * 暂存
     * @param warehouseReceiptDTO
     * @return
     */
    @PostMapping("/order/warehouseReceipt/temporarySave")
    Long temporarySave(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO);

    /**
     * 入库单提交
     * @param warehouseReceiptDTO
     * @return
     */
    @PostMapping("/order/warehouseReceipt/submit")
    Long warehouseReceiptSubmit(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO);

    /**
     * 批量删除入库单
     * @param ids
     */
    @PostMapping("/order/warehouseReceipt/batchDelete")
    void batchDeleteWarehouseReceipt(@RequestBody List<Long> ids);

    /**
     * 查看入库单详情
     * @param id
     * @return
     */
    @GetMapping("/order/warehouseReceipt/detail")
    WarehouseReceiptDTO detailWarehouseReceipt(@RequestParam("id") Long id);

    /**
     * 批量确认入库单
     * @param ids
     */
    @PostMapping("/order/warehouseReceipt/batchConfirm")
    void batchConfirmWarehouseReceipts(@RequestBody List<Long> ids);

    /**
     * 批量冲销
     * @param ids
     */
    @PostMapping("/order/warehouseReceipt/batchWriteOff")
    void batchWriteOff(@RequestBody List<Long> ids);

    /*入库单-end*/

    /*送货单-start*/

    /**
     * 创建入库单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @PostMapping("/order/deliveryNoteDetail/listInWarehouseReceipt")
    PageInfo<DeliveryNoteDetailDTO> listInWarehouseReceipt(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /**
     * 创建退货单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @PostMapping("/order/deliveryNoteDetail/listInReturnOrder")
    PageInfo<DeliveryNoteDetailDTO> listInReturnOrder(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /*送货单end*/

    /*退货单-start*/

    /**
     * 暂存
     * @param returnOrderSaveRequestDTO
     * @return
     */
    @PostMapping("/order/returnOrder/temporarySave")
    Long temporarySaveReturnOrder(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO);

    /**
     * 批量删除退货单
     * @param ids
     */
    @PostMapping("/order/returnOrder/batchDelete")
    void batchDeleteReturnOrder(@RequestBody List<Long> ids);

    /**
     * 提交退货单
     * @param returnOrderSaveRequestDTO
     * @return
     */
    @PostMapping("/order/returnOrder/submit")
    Long submitReturnOrder(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO);


    /**
     * 退货单明细
     * @param returnOrderId
     * @return
     */
    @PostMapping("/order/returnOrder/detail")
    ReturnOrderVO detailReturnOrder(@RequestParam("returnOrderId") Long returnOrderId);


    /*退货单-end*/

    /**
     * 工作流回调入口，需要各个模块实现本模块下的各个功能的动态调用service
     */
    @PostMapping("/pr-anon/callbackFlow")
    @Override
    String callbackFlow(@RequestParam("serviceBean") String serviceBean, @RequestParam("callbackMethod") String callbackMethod, @RequestParam("businessId") Long businessId , @RequestParam("param") String param);


    /**
     * 根据品类查询采购策略主负责人
     * @param companyDemotionOrgs
     * @return
     */
    @PostMapping("/pr-anon/listDivisionPurchaseStrategyMainPerson")
    List<DivisionCategory> listDivisionPurchaseStrategyMainPerson(@RequestBody List<CompanyDemotionOrg> companyDemotionOrgs);
}
