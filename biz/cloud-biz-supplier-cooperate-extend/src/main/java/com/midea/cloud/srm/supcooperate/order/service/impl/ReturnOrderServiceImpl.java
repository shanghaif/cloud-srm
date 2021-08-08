package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.neworder.ReturnOrderStatus;
import com.midea.cloud.common.enums.neworder.WarehouseReceiptStatus;
import com.midea.cloud.common.enums.neworder.WarehousingReturnDetailStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.*;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.ReturnOrderVO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptDetailVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import com.midea.cloud.srm.supcooperate.order.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateReturnOrder;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.ConfirmReturnOrders;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderPageDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.supcooperate.order.mapper.ReturnDetailMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.ReturnOrderMapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  退货单表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 11:32
 *  修改内容:
 *          </pre>
 */
@Service
public class ReturnOrderServiceImpl extends ServiceImpl<ReturnOrderMapper, ReturnOrder> implements IReturnOrderService {

	@Autowired
	private ReturnOrderMapper returnOrderMapper;

	@Autowired
	private ReturnDetailMapper returnDetailMapper;

	@Autowired
	private IReturnDetailService iReturnDetailService;

	@Autowired
	private IOrderDetailService iOrderDetailService;

	@Autowired
	private IDeliveryNoticeService iDeliveryNoticeService;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private IDeliveryNoteDetailService deliveryNoteDetailService;

	@Autowired
	private IWarehousingReturnDetailService warehousingReturnDetailService;

	@Autowired
	private IDeliveryNoteService deliveryNoteService;

	@Autowired
	private IOrderService iOrderService;

	@Override
	public PageInfo<ReturnOrder> listPage(ReturnOrderRequestDTO requestDTO) {
		PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
		return new PageInfo<ReturnOrder>(returnOrderMapper.listPage(requestDTO));
	}

	@Override
	@Transactional
	public void saveOrUpdateReturnOrder(SaveOrUpdateReturnOrder saveOrUpdateReturnOrder) {
		this.saveOrUpdate(saveOrUpdateReturnOrder.getReturnOrder());
		// 删除原行记录
		returnDetailMapper.delete(new QueryWrapper<ReturnDetail>(new ReturnDetail().setReturnOrderId(saveOrUpdateReturnOrder.getReturnOrder().getReturnOrderId())));
		iReturnDetailService.saveBatch(saveOrUpdateReturnOrder.getReturnDetails());
		if (!CollectionUtils.isEmpty(saveOrUpdateReturnOrder.getDeliveryNotices())) {
			iDeliveryNoticeService.saveOrUpdateBatch(saveOrUpdateReturnOrder.getDeliveryNotices());
		}
		if (!CollectionUtils.isEmpty(saveOrUpdateReturnOrder.getOrderDetails())) {
			iOrderDetailService.saveOrUpdateBatch(saveOrUpdateReturnOrder.getOrderDetails());
		}
	}

	@Override
	@Transactional
	public void confirmReturnOrders(ConfirmReturnOrders confirmReturnOrders) {
		this.updateBatchById(confirmReturnOrders.getReturnOrders());
		if (!CollectionUtils.isEmpty(confirmReturnOrders.getOrderDetails())) {
			iOrderDetailService.updateBatchById(confirmReturnOrders.getOrderDetails());
		}
		if (!CollectionUtils.isEmpty(confirmReturnOrders.getDeliveryNotices())) {
			iDeliveryNoticeService.saveOrUpdateBatch(confirmReturnOrders.getDeliveryNotices());
		}
	}

	/**
	 * 批量删除退货单
	 * @param ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchDelete(List<Long> ids) {
		checkIfDelete(ids);
		//删除退货单头
		this.removeByIds(ids);
		//删除退货单行
		QueryWrapper<ReturnDetail> returnDetailWrapper = new QueryWrapper<>();
		returnDetailWrapper.in("RETURN_ORDER_ID", ids);
		iReturnDetailService.remove(returnDetailWrapper);
	}

	/**
	 * 校验是否可删除
	 * @param ids
	 */
	private void checkIfDelete(List<Long> ids){
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<ReturnOrder> returnOrderList = this.listByIds(ids);
		if(CollectionUtils.isEmpty(returnOrderList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("查询不到退货单,请检查"));
		}
		for(ReturnOrder returnOrder : returnOrderList){
			if(!ReturnOrderStatus.DRAFT.getValue().equals(returnOrder.getReturnStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("退货单[%s]不为拟定状态,请检查",returnOrder.getReturnOrderNumber())));
			}
		}
	}

	/**
	 * 暂存
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long temporarySave(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();

		Long id = this.saveOrSubmit(returnOrderSaveRequestDTO, ReturnOrderStatus.DRAFT, false);

		return id;
	}

	/**
	 * 提交退货单
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long submit(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();

		Long id = this.saveOrSubmit(returnOrderSaveRequestDTO, ReturnOrderStatus.WAITING_CONFIRM, true);

		return id;
	}

	@Transactional(rollbackFor = Exception.class)
	private Long saveOrSubmit(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO, ReturnOrderStatus returnOrderStatus, boolean checkQuantity) {
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();

		Set<Long> deliveryNoteDetailIdSet = new HashSet<>();

		List<ReturnDetail> returnDetailList = returnOrderSaveRequestDTO.getReturnDetailList();
		Set<Long> tempIdSet = returnDetailList.stream().map(ReturnDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
		deliveryNoteDetailIdSet.addAll(tempIdSet);

		if(Objects.nonNull(returnOrder.getReturnOrderId())){
			//修改退货单行信息
			QueryWrapper<ReturnDetail> returnDetailWrapper = new QueryWrapper<>();
			returnDetailWrapper.eq("RETURN_ORDER_ID",returnOrder.getReturnOrderId());
			List<ReturnDetail> dbList = iReturnDetailService.list(returnDetailWrapper);
			Set<Long> dbIdSet = dbList.stream().map(ReturnDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
			deliveryNoteDetailIdSet.addAll(dbIdSet);
		}
		List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailService.listByIds(deliveryNoteDetailIdSet);
		Map<Long, DeliveryNoteDetail> detailMap = deliveryNoteDetailList.stream().collect(Collectors.toMap(DeliveryNoteDetail::getDeliveryNoteDetailId, item -> item));
		for (ReturnDetail returnDetail: returnDetailList) {
			DeliveryNoteDetail deliveryNoteDetail = detailMap.get(returnDetail.getDeliveryNoteDetailId());
			returnDetail.setOrderDetailId(deliveryNoteDetail.getOrderDetailId());
		}



		Long id = null;
		if(Objects.isNull(returnOrder.getReturnOrderId())){
			//新增
			returnOrder.setReturnStatus(returnOrderStatus.getValue());
			id = add(returnOrderSaveRequestDTO);
		}else{
			ReturnOrder dbReturnOrder = this.getById(returnOrder.getReturnOrderId());
			if(Objects.isNull(dbReturnOrder) || !ReturnOrderStatus.DRAFT.getValue().equals(dbReturnOrder.getReturnStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("退货单[%s]不为拟定状态,请检查", dbReturnOrder.getReturnOrderNumber())));
			}

			//修改
			returnOrder.setReturnStatus(returnOrderStatus.getValue());
			id = update(returnOrderSaveRequestDTO);
		}

		// 校验退货数量是否高于入库数量，如果超过，则提交失败。同时更新送货单的退货数量
		if (checkQuantity) {
			this.updateDeliveryNoteDetail(deliveryNoteDetailList, deliveryNoteDetailIdSet);
		}
		return id;
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateDeliveryNoteDetail(List<DeliveryNoteDetail> deliveryNoteDetailList, Set<Long> deliveryNoteDetailIdList){
		// 获取退货单与退货数量的对应关系
		Map<Long, BigDecimal> detailQuantityMap = this.getReturnQuantity(deliveryNoteDetailIdList);

		for(DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList){
			BigDecimal totalQuantity = detailQuantityMap.getOrDefault(deliveryNoteDetail.getDeliveryNoteDetailId(), BigDecimal.ZERO);

			// 校验数量是否满足: 退货数量小于入库数量，因为入库数量肯定小于送货单送货数量
			if (deliveryNoteDetail.getWarehouseQuantity().compareTo(totalQuantity) < 0) {
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("送货明细[id=%s]数量不足,请检查", deliveryNoteDetail.getDeliveryNoteDetailId())));
			}
		}

		for(DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList){
			BigDecimal totalQuantity = detailQuantityMap.getOrDefault(deliveryNoteDetail.getDeliveryNoteDetailId(), BigDecimal.ZERO);
			deliveryNoteDetail.setActualReturnedNum(totalQuantity);

			//更新数量
			LambdaQueryWrapper<DeliveryNoteDetail> deliveryNoteDetailWrapper = Wrappers.lambdaQuery(DeliveryNoteDetail.class)
					.eq(DeliveryNoteDetail::getDeliveryNoteDetailId, deliveryNoteDetail.getDeliveryNoteDetailId())
					.eq(DeliveryNoteDetail::getVersion, deliveryNoteDetail.getVersion())
					.ge(DeliveryNoteDetail::getWarehouseQuantity, totalQuantity); // 入库数量大于退货数量

			if(Objects.isNull(deliveryNoteDetail.getVersion())){
				deliveryNoteDetail.setVersion(0L);
			}
			deliveryNoteDetail.setVersion(deliveryNoteDetail.getVersion() + 1);
			boolean ifUpdate = deliveryNoteDetailService.update(deliveryNoteDetail, deliveryNoteDetailWrapper);
			if(!ifUpdate){
				throw new BaseException(LocaleHandler.getLocaleMsg("当前送货单单据被占用,请刷新重试"));
			}

		}
	}

	private Map<Long, BigDecimal> getReturnQuantity(Set<Long> deliveryNoteDetailIdList) {
		List<ReturnDetail> returnDetailList = iReturnDetailService.listByDeliveryNoteDetailId(deliveryNoteDetailIdList);

		if (CollectionUtils.isEmpty(returnDetailList)) {
			return Collections.emptyMap();
		}

		// 获取未拒绝的退货单。
		List<Long> returnOrderIdList = returnDetailList.stream().map(ReturnDetail::getReturnOrderId).distinct().collect(Collectors.toList());
		List<ReturnOrder> receiptList = this.listUnReject(returnOrderIdList);
		List<Long> activeReturnOrderIdList = receiptList.stream().map(ReturnOrder::getReturnOrderId).collect(Collectors.toList());

		// 保留未拒绝的退货单. 送货单对应的退货明细
		Map<Long, List<ReturnDetail>> detailMap = returnDetailList.stream()
				.filter(item -> item.getReturnNum() != null && activeReturnOrderIdList.contains(item.getReturnOrderId()))
				.collect(Collectors.groupingBy(ReturnDetail::getDeliveryNoteDetailId));
		Map<Long, BigDecimal> detailQuantityMap = new HashMap<>();
		detailMap.forEach((deliveryNoteDetailId, list) -> {
			BigDecimal totalQuantity = list.stream().map(ReturnDetail::getReturnNum).reduce(BigDecimal::add).get();

			detailQuantityMap.put(deliveryNoteDetailId, totalQuantity);
		});

		return detailQuantityMap;
	}

	private List<ReturnOrder> listUnReject(List<Long> ids) {
		return this.list(Wrappers.lambdaQuery(ReturnOrder.class)
				.in(ReturnOrder::getReturnOrderId, ids)
				.ne(ReturnOrder::getReturnStatus, ReturnOrderStatus.REJECT.getValue())
		);
	}

	/**
	 * 批量确认
	 * @param ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchConfirm(List<Long> ids) {
		//校验
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<ReturnOrder> returnOrderList = this.listByIds(ids);
		if(CollectionUtils.isEmpty(returnOrderList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("查询不到退货单,请刷新页面"));
		}
		for(ReturnOrder returnOrder : returnOrderList){
			if(!ReturnOrderStatus.WAITING_CONFIRM.getValue().equals(returnOrder.getReturnStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("退货单[%s]不为待确认状态,请检查",returnOrder.getReturnOrderNumber())));
			}
		}

		//更新状态
		for(ReturnOrder returnOrder : returnOrderList){
			returnOrder.setReturnStatus(ReturnOrderStatus.ACCEPT.getValue());
		}
		this.updateBatchById(returnOrderList);

		// 添加入入库退货明细
		QueryWrapper<ReturnDetail> returnDetailWrapper = new QueryWrapper<>();
		returnDetailWrapper.in("RETURN_ORDER_ID", ids);
		List<ReturnDetail> returnDetailList = iReturnDetailService.list(returnDetailWrapper);
		List<WarehousingReturnDetail> warehousingReturnDetailList = build(returnOrderList, returnDetailList);

		warehousingReturnDetailService.saveBatch(warehousingReturnDetailList);
	}

	/**
	 * 构建入库退货明细
	 * @param returnOrderList
	 * @return
	 */
	private List<WarehousingReturnDetail> build(List<ReturnOrder> returnOrderList, List<ReturnDetail> returnDetailList){
		//获取数据
		List<WarehousingReturnDetail> result = new LinkedList<>();

		// 获取采购单明细
		List<Long> orderDetailIdList = returnDetailList.stream().map(ReturnDetail::getOrderDetailId).collect(Collectors.toList());
		List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIdList);
		Map<Long, OrderDetail> orderDetailMap = orderDetailList.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, item -> item));

		// 获取采购单
		List<Long> orderIdList = orderDetailList.stream().map(OrderDetail::getOrderId).collect(Collectors.toList());
		List<Order> orderList = iOrderService.listByIds(orderIdList);
		Map<Long, Order> orderMap = orderList.stream().collect(Collectors.toMap(Order::getOrderId, item -> item));

		// 获取送货单明细
		Set<Long> tempIdSet = returnDetailList.stream().map(ReturnDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
		List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailService.listByIds(tempIdSet);
		Map<Long, DeliveryNoteDetail> detailMap = deliveryNoteDetailList.stream().collect(Collectors.toMap(DeliveryNoteDetail::getDeliveryNoteDetailId, item -> item));

		// 获取送货单信息
		Set<Long> noteIdSet = deliveryNoteDetailList.stream().map(DeliveryNoteDetail::getDeliveryNoteId).collect(Collectors.toSet());
		List<DeliveryNote> deliveryNoteList = deliveryNoteService.listByIds(noteIdSet);
		Map<Long, DeliveryNote> deliveryNoteMap = deliveryNoteList.stream().collect(Collectors.toMap(DeliveryNote::getDeliveryNoteId, item -> item));


		Map<Long, ReturnOrder> returnOrderMap = returnOrderList.stream().collect(Collectors.toMap(ReturnOrder::getReturnOrderId, item -> item));
		for(ReturnDetail returnDetail : returnDetailList){
			ReturnOrder returnOrder = returnOrderMap.get(returnDetail.getReturnOrderId());
			OrderDetail orderDetail = orderDetailMap.get(returnDetail.getOrderDetailId());
			Order order = orderMap.get(orderDetail.getOrderId());
			DeliveryNoteDetail deliveryNoteDetail = detailMap.get(returnDetail.getDeliveryNoteDetailId());
			DeliveryNote deliveryNote = deliveryNoteMap.get(deliveryNoteDetail.getDeliveryNoteId());

			Date date = new Date();
			Long txnId = IdGenrator.generate();
			WarehousingReturnDetail warehousingReturnDetail = new WarehousingReturnDetail()
					.setWarehousingReturnDetailId(IdGenrator.generate())
//					.setWarehouseReceiptDetailId(warehouseReceiptDetail.getWarehouseReceiptDetailId())  入库单才有
					.setType(WarehousingReturnDetailStatus.RETURN_TO_VENDOR.getValue())

//					.setReceiveOrderNo(baseClient.seqGen(SequenceCodeConstant.SEQ_WAREHOUSE_RETURN_NUM))
//					.setReceiveOrderLineNo(1)
					.setReceiveOrderNo(returnOrder.getReturnOrderNumber())
					.setReceiveOrderLineNo(returnDetail.getLineNum())
					.setDeliveryLineNum(deliveryNoteDetail.getLineNum())
					.setDeliveryNumber(deliveryNote.getDeliveryNumber())

					.setOrgId(order.getCeeaOrgId())
					.setOrgCode(order.getCeeaOrgCode())
					.setOrgName(order.getCeeaOrgName())
					.setOrganizationId(orderDetail.getCeeaOrganizationId())
					.setOrganizationCode(orderDetail.getCeeaOrganizationCode())
					.setOrganizationName(orderDetail.getCeeaOrganizationName())
					.setVendorId(order.getVendorId())
					.setVendorCode(order.getVendorCode())
					.setVendorName(order.getVendorName())

					.setCategoryId(orderDetail.getCategoryId())
					.setCategoryCode(orderDetail.getCategoryCode())
					.setCategoryName(orderDetail.getCategoryName())

					.setItemId(orderDetail.getMaterialId())
					.setItemCode(orderDetail.getMaterialCode())
					.setItemName(orderDetail.getMaterialName())
					.setUnit(orderDetail.getUnit())
					.setUnitCode(orderDetail.getUnitCode())

//					.setReceiveNum(returnDetail.getReturnNum())
					.setReturnToSupplierNum(returnDetail.getReturnNum())

//					.setRequirementHeadNum()
//					.setRowNum()
					.setOrderNumber(order.getOrderNumber())
					.setSrmOrderNumber(order.getOrderNumber())
					.setSettlementOrderNumber(order.getOrderNumber())
					.setLineNum(orderDetail.getLineNum())
					.setContractNo(orderDetail.getCeeaContractNo())
					.setContractCode(orderDetail.getCategoryCode())
//					.setContractHeadId()
					.setTaxKey(orderDetail.getCeeaTaxKey())
					.setTaxRate(orderDetail.getCeeaTaxRate())
//					.setProjectName()
//					.setProjectNum()
//					.setTaskName()
//					.setTaskNum()
					.setNotInvoiceQuantity(returnDetail.getReturnNum())
//					.setStorageQuantity(warehouseReceiptDetail.getWarehouseQuantity()) // 用来比较未开票数量与入库数量是否一致，如果不一致，则说明，已经存在开票数据。   退货数量

					.setUnitPriceContainingTax(orderDetail.getUnitPriceContainingTax())
					.setUnitPriceExcludingTax(orderDetail.getUnitPriceExcludingTax())
					.setCurrencyId(orderDetail.getCurrencyId())
					.setCurrencyCode(orderDetail.getCurrencyCode())
					.setCurrencyName(orderDetail.getCurrencyName())
					.setReceiveDate(date)
					.setWarehousingDate(date)
					.setPoLineId(orderDetail.getOrderDetailId())
					.setTxnId(txnId)
					.setParentTxnId(txnId);
			result.add(warehousingReturnDetail);
		}
		return result;
	}

	/**
	 * 批量拒绝
	 * @param ro
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchReject(ReturnOrder ro) {
		List<Long> ids = ro.getIds();
		String rejectReason = ro.getRejectReason();

		// 校验
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<ReturnOrder> returnOrderList = this.listByIds(ids);
		if(CollectionUtils.isEmpty(returnOrderList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("查询不到退货单,请刷新页面"));
		}
		for(ReturnOrder returnOrder : returnOrderList){
			if(!ReturnOrderStatus.WAITING_CONFIRM.getValue().equals(returnOrder.getReturnStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("退货单[%s]不为待确认状态,请检查",returnOrder.getReturnOrderNumber())));
			}
		}

		// 更新状态
		for(ReturnOrder returnOrder : returnOrderList){
			returnOrder.setRejectReason(rejectReason);
			returnOrder.setReturnStatus(ReturnOrderStatus.REJECT.getValue());
		}
		this.updateBatchById(returnOrderList);

		// 释放退货单退货数量 todo
		this.freeReturnNum(ids);
	}

	private void freeReturnNum(List<Long> returnOrderIdList) {

	}

	/**
	 * 校验是否可以提交
	 * @param returnOrderSaveRequestDTO
	 */
	private void checkIfSubmit(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();

		ReturnOrder ro = this.getById(returnOrder.getReturnOrderId());
		if(Objects.nonNull(ro) && !ReturnOrderStatus.DRAFT.getValue().equals(ro.getReturnStatus())){
			throw new BaseException(LocaleHandler.getLocaleMsg(String.format("退货单[%s]不为拟定状态,请检查",ro.getReturnOrderNumber())));
		}

	}

	/**
	 * 保存
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long add(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();
		List<ReturnDetail> returnDetailList = returnOrderSaveRequestDTO.getReturnDetailList();

		//保存退货单头信息
		Long id = IdGenrator.generate();
		returnOrder.setReturnOrderId(id);
		returnOrder.setReturnOrderNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_RETURN_ORDER_NUM));
		this.save(returnOrder);
		//保存退货单行信息
		int index = 1;
		for(ReturnDetail returnDetail : returnDetailList){
			returnDetail.setReturnOrderId(id)
					.setReturnDetailId(IdGenrator.generate())
					.setLineNum(index ++);
		}
		iReturnDetailService.saveBatch(returnDetailList);

		return id;
	}

	/**
	 * 更新
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long update(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		ReturnOrder returnOrder = returnOrderSaveRequestDTO.getReturnOrder();
		List<ReturnDetail> returnDetailList = returnOrderSaveRequestDTO.getReturnDetailList();

		//修改退货单头信息
		Long id = returnOrder.getReturnOrderId();
		this.updateById(returnOrder);

		//修改退货单行信息
		QueryWrapper<ReturnDetail> returnDetailWrapper = new QueryWrapper<>();
		returnDetailWrapper.eq("RETURN_ORDER_ID",id);
		iReturnDetailService.remove(returnDetailWrapper);
		int index = 1;
		for(ReturnDetail returnDetail : returnDetailList){
			returnDetail.setReturnOrderId(id)
					.setReturnDetailId(IdGenrator.generate())
					.setLineNum(index ++);
		}
		iReturnDetailService.saveBatch(returnDetailList);
		return id;
	}

	/**
	 * 详情
	 * @param returnOrderId
	 * @return
	 */
	@Override
	public ReturnOrderVO detail(Long returnOrderId) {
		if(Objects.isNull(returnOrderId)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		ReturnOrder returnOrder = this.getById(returnOrderId);
		List<ReturnDetail> returnDetailList = iReturnDetailService.list(returnOrderId);

		return new ReturnOrderVO().setReturnOrder(returnOrder).setReturnDetailList(returnDetailList);
	}
}
