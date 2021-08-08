package com.midea.cloud.srm.po.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.order.NoteStatus;
import com.midea.cloud.common.enums.order.ReturnOrderStatusEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.ConfirmReturnOrders;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.po.order.service.IReturnOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IReturnDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * <pre>
 *
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 29, 20203:03:17 PM
 *  修改内容:
 *          </pre>
 */
@Service
public class ReturnOrderServiceImpl implements IReturnOrderService {

	@Autowired
	private com.midea.cloud.srm.supcooperate.order.service.IReturnOrderService scIReturnOrderService;

	@Autowired
	private IOrderDetailService scIOrderDetailService;

	@Autowired
	private IDeliveryNoteDetailService scIDeliveryNoteDetailService;

	@Autowired
	private IDeliveryNoticeService scIDeliveryNoticeService;

	@Autowired
	private IReturnDetailService scIReturnDetailService;

	@Autowired
	private BaseClient baseClient;

	@Override
	public void saveOrUpdateReturnOrder(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO, ReturnOrderStatusEnum returnOrderStatusEnum) {
//		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();
//		if (returnOrder.getReturnOrderId() == null) {
//			returnOrder.setReturnOrderId(IdGenrator.generate());
//		}
//		returnOrder.setReturnOrderNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_RETURN_ORDER_NUM));
//		returnOrder.setReturnDate(new Date());
//		returnOrder.setReturnStatus(returnOrderStatusEnum.getValue());
//		List<ReturnDetail> returnDetails = new ArrayList<ReturnDetail>();
//		int lineNum = 1;
//		for (ReturnDetailDTO o : returnOrderSaveRequestDTO.getReturnDetailDTOs()) {
//			ReturnDetail d = new ReturnDetail();
//			d.setReturnDetailId(IdGenrator.generate());
//			d.setWarehouseReceiptId(o.getWarehouseReceiptId());
//			d.setDeliveryNoteDetailId(o.getDeliveryNoteDetailId());
//			d.setReturnOrderId(returnOrder.getReturnOrderId());
//			d.setReturnNum(o.getReturnNum());
//			d.setOrderDetailId(o.getOrderDetailId());
//			d.setLineNum(lineNum++);
//			returnDetails.add(d);
//		}
//		SaveOrUpdateReturnOrder saveOrUpdateReturnOrder = new SaveOrUpdateReturnOrder().setReturnOrder(returnOrder).setReturnDetails(returnDetails);
//		// 确认
//		if (returnOrderStatusEnum == ReturnOrderStatusEnum.CONFIRM) {
//			for(ReturnDetailDTO dto : returnOrderSaveRequestDTO.getReturnDetailDTOs()) {
//				Assert.notNull(dto.getReturnNum(), "退货数量不能为空");
//			}
//			returnOrder.setConfirmTime(new Date());
//			returnOrder.setConfirmId(AppUserUtil.getLoginAppUser().getUserId());
//			returnOrder.setConfirmBy(AppUserUtil.getLoginAppUser().getUsername());
//			Map<Long, BigDecimal> decutionMap = new HashMap<Long, BigDecimal>();
//			// 按订单明细ID分组汇总退货数量
//			for (ReturnDetailDTO returnDetailDTO : returnOrderSaveRequestDTO.getReturnDetailDTOs()) {
//				if (decutionMap.containsKey(returnDetailDTO.getOrderDetailId())) {
//					decutionMap.put(returnDetailDTO.getOrderDetailId(), decutionMap.get(returnDetailDTO.getOrderDetailId()).add(returnDetailDTO.getReturnNum()));
//				} else {
//					decutionMap.put(returnDetailDTO.getOrderDetailId(), returnDetailDTO.getReturnNum());
//				}
//			}
//			saveOrUpdateReturnOrder.setOrderDetails(deductionReceiveSum(decutionMap));
//			List<Long> deliveryNoteDetailIds = returnOrderSaveRequestDTO.getReturnDetailDTOs().stream().map(ReturnDetailDTO::getDeliveryNoteDetailId).collect(Collectors.toList());
//			saveOrUpdateReturnOrder.setDeliveryNotices(openDeliveryNotice(deliveryNoteDetailIds));
//		}
//		supcooperateClient.saveOrUpdateReturnOrder(saveOrUpdateReturnOrder);
	}

	/**
	 * 订单明细行上，“订单累计收货量” - “退货数量”
	 *
	 * @param decutionMap
	 * @return
	 */
	private List<OrderDetail> deductionReceiveSum(Map<Long, BigDecimal> decutionMap) {
		List<OrderDetail> orderDetails = scIOrderDetailService.listByIds(Arrays.asList(decutionMap.keySet().toArray(new Long[] {})));
		for (OrderDetail od : orderDetails) {
			od.setReceiveSum(od.getReceiveSum().subtract(decutionMap.get(od.getOrderDetailId())));
		}
		return orderDetails;
	}

	/**
	 * 打开送货通知
	 *
	 * @param deliveryNoteDetailIds
	 * @return
	 */
	private List<DeliveryNotice> openDeliveryNotice(List<Long> deliveryNoteDetailIds) {
		List<DeliveryNoteDetail> deliveryNoteDetailByIds = scIDeliveryNoteDetailService.listByIds(deliveryNoteDetailIds);
		List<Long> deliveryNoticeIds = deliveryNoteDetailByIds.stream().map(DeliveryNoteDetail::getDeliveryNoticeId).collect(Collectors.toList());
		List<DeliveryNotice> deliveryNotices = deliveryNoticeIds == null || deliveryNoticeIds.size() == 0 ? new ArrayList<DeliveryNotice>() : scIDeliveryNoticeService.listByIds(deliveryNoticeIds);
		deliveryNotices.forEach(o -> {
			if (NoteStatus.CLOSED.name().equals(o.getDeliveryNoticeStatus())) {
				o.setDeliveryNoticeStatus(NoteStatus.RELEASED.name());
			}
		});
		return deliveryNotices;
	}

	@Override
	public void batchConfirm(List<Long> returnOrderIds) {
		List<ReturnDetail> returnDetails = CollectionUtils.isEmpty(returnOrderIds) ? new ArrayList<ReturnDetail>() : scIReturnDetailService.list(new QueryWrapper<ReturnDetail>().in("RETURN_ORDER_ID", returnOrderIds));
		List<Long> deliveryNoteDetailIds = returnDetails.stream().map(ReturnDetail::getDeliveryNoteDetailId).collect(Collectors.toList());
		List<DeliveryNoteDetail> deliveryNoteDetails = scIDeliveryNoteDetailService.listByIds(deliveryNoteDetailIds);
		List<Long> orderDetailIds = deliveryNoteDetails.stream().map(DeliveryNoteDetail::getOrderDetailId).collect(Collectors.toList());
		List<OrderDetail> orderDetails = scIOrderDetailService.listByIds(orderDetailIds);
		Map<Long, DeliveryNoteDetail> deliveryNoteDetailMap = deliveryNoteDetails.stream().collect(Collectors.toMap(DeliveryNoteDetail::getDeliveryNoteDetailId, Function.identity(), (k1, k2) -> k1));
		Map<Long, OrderDetail> orderDetailMap = orderDetails.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, Function.identity(), (k1, k2) -> k1));
		Map<Long, BigDecimal> decutionMap = new HashMap<Long, BigDecimal>();
		for (ReturnDetail returnDetail : returnDetails) {
			DeliveryNoteDetail dnd = deliveryNoteDetailMap.get(returnDetail.getDeliveryNoteDetailId());
			if (dnd != null && orderDetailMap.containsKey(dnd.getOrderDetailId())) {
				if (decutionMap.containsKey(dnd.getOrderDetailId())) {
					decutionMap.put(dnd.getOrderDetailId(), decutionMap.get(dnd.getOrderDetailId()).add(returnDetail.getReturnNum()));
				} else {
					decutionMap.put(dnd.getOrderDetailId(), returnDetail.getReturnNum());
				}
			}
		}
		List<ReturnOrder> returnOrderByIds = CollectionUtils.isEmpty(returnOrderIds) ? new ArrayList<ReturnOrder>() : scIReturnOrderService.listByIds(returnOrderIds);
		returnOrderByIds.forEach(o -> {
			o.setReturnStatus(ReturnOrderStatusEnum.CONFIRM.getValue());
			o.setConfirmTime(new Date());
			o.setConfirmId(AppUserUtil.getLoginAppUser().getUserId());
			o.setConfirmBy(AppUserUtil.getLoginAppUser().getUsername());
		});
		ConfirmReturnOrders confirmReturnOrders = new ConfirmReturnOrders();
		confirmReturnOrders.setReturnOrders(returnOrderByIds);
		confirmReturnOrders.setOrderDetails(deductionReceiveSum(decutionMap));
		confirmReturnOrders.setDeliveryNotices(openDeliveryNotice(deliveryNoteDetailIds));
		scIReturnOrderService.confirmReturnOrders(confirmReturnOrders);
	}
}
