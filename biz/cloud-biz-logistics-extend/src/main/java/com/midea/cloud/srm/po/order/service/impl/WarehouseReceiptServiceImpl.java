package com.midea.cloud.srm.po.order.service.impl;

import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.po.ReceivedStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptConfirmDTO;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageDTO;
import com.midea.cloud.srm.model.pm.po.dto.WriteOffDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.po.order.service.IWarehouseReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * <pre>
 * 订单入库
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 25, 20203:23:15 PM
 *  修改内容:
 *          </pre>
 */
@Service
public class WarehouseReceiptServiceImpl implements IWarehouseReceiptService {

//	@Autowired
//	private SupcooperateClient supcooperateClient;
//
//	@Autowired
//	private BaseClient baseClient;
//
//	@Override
//	public void confirm(List<WarehouseReceiptConfirmDTO> warehouseReceiptConfirmDTOList) {
//		List<Long> deliveryNoteDetailIds = warehouseReceiptConfirmDTOList.stream().map(WarehouseReceiptConfirmDTO::getDeliveryNoteDetailId).collect(Collectors.toList());
//		List<WarehouseReceipt> warehouseReceiptByDeliveryNoteDetailIds = supcooperateClient.queryWarehouseReceiptByDeliveryNoteDetailIds(deliveryNoteDetailIds);
//		Map<Long, WarehouseReceipt> warehouseReceiptMap = warehouseReceiptByDeliveryNoteDetailIds.stream().collect(Collectors.toMap(WarehouseReceipt::getDeliveryNoteDetailId, Function.identity(), (k1, k2) -> k1));
//		warehouseReceiptConfirmDTOList.forEach(o -> {
//			if (warehouseReceiptMap.containsKey(o.getDeliveryNoteDetailId()) && ReceivedStatusEnum.get(warehouseReceiptMap.get(o.getDeliveryNoteDetailId()).getWarehouseReceiptStatus()) != ReceivedStatusEnum.UNCONFIRMED) {
//				throw new BaseException("只能勾选待确认记录");
//			}
//		});
//		List<WarehouseReceipt> saveList = new ArrayList<WarehouseReceipt>();
//		List<DeliveryNoteDetail> deliveryNoteDetailByIds = supcooperateClient.queryDeliveryNoteDetailByIds(deliveryNoteDetailIds);
//		List<Long> orderDetailIds = deliveryNoteDetailByIds.stream().map(DeliveryNoteDetail::getOrderDetailId).collect(Collectors.toList());
//		List<OrderDetail> orderDetailListByIds = supcooperateClient.getOrderDetailListByIds(orderDetailIds);
//		Map<Long, DeliveryNoteDetail> deliveryNoteDetailMap = deliveryNoteDetailByIds.stream().collect(Collectors.toMap(DeliveryNoteDetail::getDeliveryNoteDetailId, Function.identity(), (k1, k2) -> k1));
//		Map<Long, OrderDetail> orderDetailMap = orderDetailListByIds.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, Function.identity(), (k1, k2) -> k1));
//		Long rowNum = 1L;
//		String warehouseReceiptNumber = baseClient.seqGen(SequenceCodeConstant.SEQ_WAREHOUSE_RECEIPT_NUM);
//		for (WarehouseReceiptConfirmDTO o : warehouseReceiptConfirmDTOList) {
//			WarehouseReceipt wr = new WarehouseReceipt();
//			wr.setDeliveryNoteDetailId(o.getDeliveryNoteDetailId());
//			wr.setDeliveryNoteId(o.getDeliveryNoteId());
//			wr.setWarehouseReceiptQuantity(o.getWarehouseReceiptQuantity());
//			wr.setWarehouseReceiptId(IdGenrator.generate());
//			wr.setConfirmTime(new Date());
//			wr.setWarehouseReceiptStatus(ReceivedStatusEnum.CONFIRMED.getValue());
//			wr.setWriteOff(YesOrNo.NO.getValue());
//			wr.setWarehouseReceiptNumber(warehouseReceiptNumber);
//			wr.setWarehouseReceiptRowNum(rowNum++);
//			wr.setOrderDetailId(deliveryNoteDetailMap.get(o.getDeliveryNoteDetailId()).getOrderDetailId());
//			saveList.add(wr);
//			OrderDetail orderDetail = orderDetailMap.get(deliveryNoteDetailMap.get(o.getDeliveryNoteDetailId()).getOrderDetailId());
//			if (orderDetail != null) {
//				if (orderDetail.getReceiveSum() == null) {
//					orderDetail.setReceiveSum(new BigDecimal(0));
//				}
//				orderDetail.setReceiveSum(orderDetail.getReceiveSum().add(o.getWarehouseReceiptQuantity()));
//			}
//		}
//		supcooperateClient.saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(new SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail().setOrderDetailList(orderDetailListByIds).setWarehouseReceiptList(saveList));
//	}
//
//	@Override
//	public void writeOff(WriteOffDTO writeOffDTO) {
//		List<Long> warehouseReceiptIds = writeOffDTO.getOriginalList().stream().map(WarehouseReceiptPageDTO::getWarehouseReceiptId).collect(Collectors.toList());
//		List<WarehouseReceipt> queryWarehouseReceiptByIds = supcooperateClient.queryWarehouseReceiptByIds(warehouseReceiptIds);
//		queryWarehouseReceiptByIds.forEach(o -> {
//			if (ReceivedStatusEnum.get(o.getWarehouseReceiptStatus()) != ReceivedStatusEnum.CONFIRMED) {
//				throw new BaseException("只能勾选已确认记录");
//			}
//		});
//		List<Long> deliveryNoteDetailIds = queryWarehouseReceiptByIds.stream().map(WarehouseReceipt::getDeliveryNoteDetailId).collect(Collectors.toList());
//		List<DeliveryNoteDetail> deliveryNoteDetailByIds = supcooperateClient.queryDeliveryNoteDetailByIds(deliveryNoteDetailIds);
//		List<Long> orderDetailIds = deliveryNoteDetailByIds.stream().map(DeliveryNoteDetail::getOrderDetailId).collect(Collectors.toList());
//		List<OrderDetail> orderDetailListByIds = supcooperateClient.getOrderDetailListByIds(orderDetailIds);
//		Map<Long, DeliveryNoteDetail> deliveryNoteDetailMap = deliveryNoteDetailByIds.stream().collect(Collectors.toMap(DeliveryNoteDetail::getDeliveryNoteDetailId, Function.identity(), (k1, k2) -> k1));
//		Map<Long, OrderDetail> orderDetailMap = orderDetailListByIds.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, Function.identity(), (k1, k2) -> k1));
//		Map<Long, WarehouseReceiptPageDTO> newWarehouseReceiptPageMap = writeOffDTO.getNewList().stream().collect(Collectors.toMap(WarehouseReceiptPageDTO::getWarehouseReceiptId, Function.identity(), (k1, k2) -> k1));
//		List<WarehouseReceipt> saveOrUpdateList = new ArrayList<WarehouseReceipt>();
//		Long rowNum = 1L;
//		for (WarehouseReceipt o : queryWarehouseReceiptByIds) {
//			o.setWarehouseReceiptStatus(ReceivedStatusEnum.WRITEOFF.getValue());
//			saveOrUpdateList.add(o);
//			WarehouseReceipt writeOffRecord = new WarehouseReceipt();
//			writeOffRecord.setDeliveryNoteDetailId(o.getDeliveryNoteDetailId());
//			writeOffRecord.setDeliveryNoteId(o.getDeliveryNoteId());
//			writeOffRecord.setWarehouseReceiptQuantity(o.getWarehouseReceiptQuantity());
//			writeOffRecord.setWarehouseReceiptId(IdGenrator.generate());
//			writeOffRecord.setConfirmTime(new Date());
//			writeOffRecord.setWarehouseReceiptStatus(ReceivedStatusEnum.CONFIRMED.getValue());
//			writeOffRecord.setWriteOff(YesOrNo.YES.getValue());
//			writeOffRecord.setWarehouseReceiptNumber(o.getWarehouseReceiptNumber());
//			writeOffRecord.setWarehouseReceiptQuantity(writeOffRecord.getWarehouseReceiptQuantity().multiply(new BigDecimal(-1)));
//			writeOffRecord.setOriginalId(o.getWarehouseReceiptId());
//			writeOffRecord.setWarehouseReceiptRowNum(o.getWarehouseReceiptRowNum());
//			writeOffRecord.setOrderDetailId(o.getOrderDetailId());
//			saveOrUpdateList.add(writeOffRecord);
//			WarehouseReceipt newRecord = new WarehouseReceipt();
//			newRecord.setDeliveryNoteDetailId(o.getDeliveryNoteDetailId());
//			newRecord.setDeliveryNoteId(o.getDeliveryNoteId());
//			newRecord.setWarehouseReceiptQuantity(o.getWarehouseReceiptQuantity());
//			newRecord.setWarehouseReceiptId(IdGenrator.generate());
//			newRecord.setConfirmTime(new Date());
//			newRecord.setWarehouseReceiptStatus(ReceivedStatusEnum.CONFIRMED.getValue());
//			newRecord.setWriteOff(YesOrNo.NO.getValue());
//			newRecord.setWarehouseReceiptNumber(o.getWarehouseReceiptNumber());
//			newRecord.setWarehouseReceiptQuantity(newWarehouseReceiptPageMap.get(o.getWarehouseReceiptId()).getWarehouseReceiptQuantity());
//			newRecord.setOriginalId(o.getWarehouseReceiptId());
//			newRecord.setOrderDetailId(o.getOrderDetailId());
//			newRecord.setWarehouseReceiptRowNum(rowNum++);
//			saveOrUpdateList.add(newRecord);
//			OrderDetail orderDetail = orderDetailMap.get(deliveryNoteDetailMap.get(o.getDeliveryNoteDetailId()).getOrderDetailId());
//			if (orderDetail != null) {
//				if (orderDetail.getReceiveSum() == null) {
//					orderDetail.setReceiveSum(new BigDecimal(0));
//				}
//				orderDetail.setReceiveSum(orderDetail.getReceiveSum().add(newRecord.getWarehouseReceiptQuantity().subtract(o.getWarehouseReceiptQuantity())));
//			}
//		}
//		supcooperateClient.saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(new SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail().setOrderDetailList(orderDetailListByIds).setWarehouseReceiptList(saveOrUpdateList));
//	}

}
