package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.neworder.WarehouseReceiptStatus;
import com.midea.cloud.common.enums.neworder.WarehousingReturnDetailStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.service.impl.BaseServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehouseReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptDetailVO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import com.midea.cloud.srm.supcooperate.order.service.*;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.WarehouseReceiptMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * <pre>
 *  入库单表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 11:33
 *  修改内容:
 *          </pre>
 */
@Service
public class WarehouseReceiptServiceImpl extends BaseServiceImpl<WarehouseReceiptMapper, WarehouseReceipt> implements IWarehouseReceiptService {

	@Autowired
	private IOrderDetailService iOrderDetailService;

	@Autowired
	private IWarehouseReceiptDetailService warehouseReceiptDetailService;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private IDeliveryNoteDetailService deliveryNoteDetailService;

	@Autowired
	private IDeliveryNoteService deliveryNoteService;

	@Autowired
	private IWarehousingReturnDetailService warehousingReturnDetailService;

	@Override
	public PageInfo<WarehouseReceiptVO> listPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		PageUtil.startPage(warehouseReceiptPageQueryDTO.getPageNum(), warehouseReceiptPageQueryDTO.getPageSize());
		List<WarehouseReceiptVO> list = getBaseMapper().listPage(warehouseReceiptPageQueryDTO);
		return new PageInfo<WarehouseReceiptVO>(list);
	}

	@Override
	public void saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail param) {
		this.saveOrUpdateBatch(param.getWarehouseReceiptList());
		iOrderDetailService.saveOrUpdateBatch(param.getOrderDetailList());
	}

	@Override
	public PageInfo<StatementReceiptDTO> listStatementReceiptDTOPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		PageUtil.startPage(warehouseReceiptPageQueryDTO.getPageNum(), warehouseReceiptPageQueryDTO.getPageSize());
		return new PageInfo<StatementReceiptDTO>(getBaseMapper().listStatementReceiptDTOPage(warehouseReceiptPageQueryDTO));
	}

	/**
	 * 暂存
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long temporarySave(WarehouseReceiptDTO warehouseReceiptDTO) {
		return this.temporarySaveOrUpdate(warehouseReceiptDTO, WarehouseReceiptStatus.DRAFT);
	}

	/**
	 * 提交
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long submit(WarehouseReceiptDTO warehouseReceiptDTO) {
		return this.temporarySaveOrUpdate(warehouseReceiptDTO, WarehouseReceiptStatus.WAITING_CONFIRM);
	}

	private Long temporarySaveOrUpdate(WarehouseReceiptDTO warehouseReceiptDTO, WarehouseReceiptStatus warehouseReceiptStatus) {
		//校验
		checkIfAdd(warehouseReceiptDTO);
		WarehouseReceipt warehouseReceipt = warehouseReceiptDTO.getWarehouseReceipt();
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDTO.getWarehouseReceiptDetailList();

		Set<Long> deliveryNoteDetailIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
		List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailService.listByIds(deliveryNoteDetailIdList);

		//新增/修改入库单
		warehouseReceipt.setWarehouseReceiptStatus(warehouseReceiptStatus.getValue());
		Long id = null;
		if(Objects.isNull(warehouseReceipt.getWarehouseReceiptId())){
			//新增
			id = add(warehouseReceiptDTO);
		}else{
			//修改
			id = update(warehouseReceiptDTO);
		}

		//回写送货单明细，重新从数据库中查询全部的入库明细，计算入库数量，更新送货单的入库数量。且更新语句中添加条件：入库数量不能大于送货数量。,且入库数量必须大于退货数量。
		this.updateDeliveryNoteDetail(deliveryNoteDetailList, deliveryNoteDetailIdList);

		return id;

	}

	/**
	 * 回写送货单明细入库数量
	 * @param deliveryNoteDetailIdList
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateDeliveryNoteDetail(List<DeliveryNoteDetail> deliveryNoteDetailList, Set<Long> deliveryNoteDetailIdList){
		Map<Long, BigDecimal> detailQuantityMap = this.getWarehouseQuantity(deliveryNoteDetailIdList);
		for(DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList){
			BigDecimal totalQuantity = detailQuantityMap.getOrDefault(deliveryNoteDetail.getDeliveryNoteDetailId(), BigDecimal.ZERO);

			// 校验数量是否满足
			if (deliveryNoteDetail.getDeliveryQuantity().compareTo(totalQuantity) < 0) {
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("送货明细[id=%s]数量不足,请检查", deliveryNoteDetail.getDeliveryNoteDetailId())));
			}
		}

		for(DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList){
			BigDecimal totalQuantity = detailQuantityMap.getOrDefault(deliveryNoteDetail.getDeliveryNoteDetailId(), BigDecimal.ZERO);
			deliveryNoteDetail.setWarehouseQuantity(totalQuantity);

			//更新数量
			LambdaQueryWrapper<DeliveryNoteDetail> deliveryNoteDetailWrapper = Wrappers.lambdaQuery(DeliveryNoteDetail.class)
					.eq(DeliveryNoteDetail::getDeliveryNoteDetailId, deliveryNoteDetail.getDeliveryNoteDetailId())
					.eq(DeliveryNoteDetail::getVersion, deliveryNoteDetail.getVersion())
					.ge(DeliveryNoteDetail::getDeliveryQuantity, totalQuantity); // 送货数量大于入库数量

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



	private Map<Long, BigDecimal> getWarehouseQuantity(Set<Long> deliveryNoteDetailIdList) {
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDetailService.listByDeliveryNoteDetailId(deliveryNoteDetailIdList);

		if (CollectionUtils.isEmpty(warehouseReceiptDetailList)) {
			return Collections.emptyMap();
		}

		// 获取未冲销的入库单。
		List<Long> warehouseReceiptIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getWarehouseReceiptId).distinct().collect(Collectors.toList());
		List<WarehouseReceipt> receiptList = this.listUnWriteOff(warehouseReceiptIdList);
		List<Long> activeReceiptIdList = receiptList.stream().map(WarehouseReceipt::getWarehouseReceiptId).collect(Collectors.toList());

		// 保留未冲销的入库单
		Map<Long, List<WarehouseReceiptDetail>> detailMap = warehouseReceiptDetailList.stream()
				.filter(item -> item.getWarehouseQuantity() != null && activeReceiptIdList.contains(item.getWarehouseReceiptId()))
				.collect(Collectors.groupingBy(WarehouseReceiptDetail::getDeliveryNoteDetailId));
		Map<Long, BigDecimal> detailQuantityMap = new HashMap<>();
		detailMap.forEach((deliveryNoteDetailId, list) -> {
			BigDecimal totalQuantity = list.stream().map(WarehouseReceiptDetail::getWarehouseQuantity).reduce(BigDecimal::add).get();

			detailQuantityMap.put(deliveryNoteDetailId, totalQuantity);
		});

		return detailQuantityMap;
	}

	/**
	 * 批量删除入库单
	 * @param ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchDelete(List<Long> ids) {
		//校验
		checkIfDelete(ids);

		// 获取待更新的送货单数据
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDetailService.listByWarehouseReceiptId(ids);

		//删除入库单头
		this.removeByIds(ids);
		//删除入库单行
		warehouseReceiptDetailService.deleteByWarehouseReceiptId(ids);

		// 更新送货单入库数量
		Set<Long> deliveryNoteDetailIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
		List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailService.listByIds(deliveryNoteDetailIdList);
		this.updateDeliveryNoteDetail(deliveryNoteDetailList, deliveryNoteDetailIdList);

	}

	/**
	 * 查看入库单详情
	 * @param id
	 * @return
	 */
	@Override
	public WarehouseReceiptDTO detailWarehouseReceipt(Long id) {
		if(Objects.isNull(id)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必填参数"));
		}

		WarehouseReceipt warehouseReceipt = this.getById(id);

		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDetailService.list(id);
		return new WarehouseReceiptDTO().setWarehouseReceipt(warehouseReceipt).setWarehouseReceiptDetailList(warehouseReceiptDetailList);
	}

	private void checkIfDelete(List<Long> ids){
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<WarehouseReceipt> warehouseReceiptList = this.listByIds(ids);
		if(CollectionUtils.isNotEmpty(warehouseReceiptList)){
			for(WarehouseReceipt warehouseReceipt : warehouseReceiptList){
				if(!WarehouseReceiptStatus.DRAFT.getValue().equals(warehouseReceipt.getWarehouseReceiptStatus()) && !WarehouseReceiptStatus.WAITING_CONFIRM.getValue().equals(warehouseReceipt.getWarehouseReceiptStatus())){
					throw new BaseException(LocaleHandler.getLocaleMsg(String.format("入库单[单号=%s]不为%s状态,请检查", warehouseReceipt.getWarehouseReceiptNumber(), warehouseReceipt.getWarehouseReceiptStatus())));
				}
			}
		}
	}

	private void checkIfAdd(WarehouseReceiptDTO warehouseReceiptDTO){
		WarehouseReceipt warehouseReceipt = warehouseReceiptDTO.getWarehouseReceipt();
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDTO.getWarehouseReceiptDetailList();
		if(StringUtils.isBlank(warehouseReceipt.getOrgCode())){
			throw new BaseException(LocaleHandler.getLocaleMsg("业务实体不可为空"));
		}
		if(StringUtils.isBlank(warehouseReceipt.getVendorCode())){
			throw new BaseException(LocaleHandler.getLocaleMsg("供应商不可为空"));
		}
		if(CollectionUtils.isEmpty(warehouseReceiptDetailList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("入库明细不可为空"));
		}
		for(WarehouseReceiptDetail warehouseReceiptDetail : warehouseReceiptDetailList){
			if(Objects.nonNull(warehouseReceiptDetail.getWarehouseQuantity()) && warehouseReceiptDetail.getWarehouseQuantity().compareTo(BigDecimal.ZERO) <= 0){
				throw new BaseException(LocaleHandler.getLocaleMsg("入库数量不可为空或者是负数"));
			}
		}

	}

	/**
	 * 新增入库单
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long add(WarehouseReceiptDTO warehouseReceiptDTO){
		WarehouseReceipt warehouseReceipt = warehouseReceiptDTO.getWarehouseReceipt();
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDTO.getWarehouseReceiptDetailList();
		//保存入库单头表
		Long id = IdGenrator.generate();
		warehouseReceipt.setWarehouseReceiptId(id);
		warehouseReceipt.setWarehouseReceiptNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_WAREHOUSE_RECEIPT_NUM));
		this.save(warehouseReceipt);
		//保存入库单行表
		long lineNum = 1;
		for(WarehouseReceiptDetail warehouseReceiptDetail : warehouseReceiptDetailList){
			warehouseReceiptDetail.setWarehouseReceiptDetailId(IdGenrator.generate());
			warehouseReceiptDetail.setWarehouseReceiptId(id);
			warehouseReceiptDetail.setWarehouseReceiptRowNum(lineNum ++);
		}
		warehouseReceiptDetailService.saveBatch(warehouseReceiptDetailList);
		return id;
	}

	/**
	 * 修改入库单
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long update(WarehouseReceiptDTO warehouseReceiptDTO){
		WarehouseReceipt warehouseReceipt = warehouseReceiptDTO.getWarehouseReceipt();
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDTO.getWarehouseReceiptDetailList();
		// 更新入库单头表
		Long id = warehouseReceipt.getWarehouseReceiptId();
		this.updateById(warehouseReceipt);
		// 删除入库单行表
		warehouseReceiptDetailService.deleteByWarehouseReceiptId(id);

		long lineNum = 1;
		for(WarehouseReceiptDetail warehouseReceiptDetail : warehouseReceiptDetailList){
			warehouseReceiptDetail.setWarehouseReceiptDetailId(IdGenrator.generate());
			warehouseReceiptDetail.setWarehouseReceiptId(id);
			warehouseReceiptDetail.setWarehouseReceiptRowNum(lineNum ++);
		}
		warehouseReceiptDetailService.saveBatch(warehouseReceiptDetailList);
		return id;
	}

	/**
	 * 批量确认
	 * @param ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchConfirm(List<Long> ids) {
		// 校验
		checkIfConfirm(ids);
		// 获取数据
		List<WarehouseReceipt> warehouseReceiptList = this.listByIds(ids);
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDetailService.listByWarehouseReceiptId(ids);

		// 更新入库单状态
		this.updateWarehouseReceiptStatus(ids, WarehouseReceiptStatus.CONFIRM);

		//生成入库退货明细数据
		List<WarehousingReturnDetail> warehousingReturnDetailList = build(warehouseReceiptList,warehouseReceiptDetailList);
		warehousingReturnDetailService.saveBatch(warehousingReturnDetailList);

	}

	/**
	 * 校验是否可以确认
	 * @param ids
	 */
	private void checkIfConfirm(List<Long> ids){
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<WarehouseReceipt> warehouseReceiptList = this.listByIds(ids);
		if(CollectionUtils.isEmpty(warehouseReceiptList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("查询不到入库单,请刷新页面"));
		}

		for(WarehouseReceipt warehouseReceipt : warehouseReceiptList){
			if(!WarehouseReceiptStatus.WAITING_CONFIRM.getValue().equals(warehouseReceipt.getWarehouseReceiptStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("入库单[%s]不是待确认状态",warehouseReceipt.getWarehouseReceiptNumber())));
			}
		}
	}

	/**
	 * 构建入库退货明细
	 * @param warehouseReceiptList
	 * @param warehouseReceiptDetailList
	 * @return
	 */
	private List<WarehousingReturnDetail> build(List<WarehouseReceipt> warehouseReceiptList, List<WarehouseReceiptDetail> warehouseReceiptDetailList){
		//获取数据
		List<Long> warehouseReceiptDetailIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getWarehouseReceiptDetailId).collect(Collectors.toList());

		List<WarehouseReceiptDetailVO> warehouseReceiptDetailVOList = warehouseReceiptDetailService.list(warehouseReceiptDetailIdList);
		Map<Long, WarehouseReceiptDetailVO> warehouseReceiptDetailVOMap = warehouseReceiptDetailVOList.stream().collect(Collectors.toMap(WarehouseReceiptDetail::getWarehouseReceiptDetailId, item -> item));

		Map<Long, WarehouseReceipt> receiptMap = warehouseReceiptList.stream().collect(Collectors.toMap(WarehouseReceipt::getWarehouseReceiptId, a -> a));

		List<WarehousingReturnDetail> result = new LinkedList<>();

		for(WarehouseReceiptDetail warehouseReceiptDetail : warehouseReceiptDetailList){
			WarehouseReceiptDetailVO warehouseReceiptDetailVO = warehouseReceiptDetailVOMap.get(warehouseReceiptDetail.getWarehouseReceiptDetailId());

			WarehouseReceipt warehouseReceipt = receiptMap.get(warehouseReceiptDetail.getWarehouseReceiptId());

			Date date = new Date();
			Long txnId = IdGenrator.generate();
			WarehousingReturnDetail warehousingReturnDetail = new WarehousingReturnDetail()
					.setWarehousingReturnDetailId(IdGenrator.generate())
					.setWarehouseReceiptDetailId(warehouseReceiptDetail.getWarehouseReceiptDetailId())
					.setType(WarehousingReturnDetailStatus.RECEIVE.getValue())

//					.setReceiveOrderNo(baseClient.seqGen(SequenceCodeConstant.SEQ_WAREHOUSE_RETURN_NUM))
//					.setReceiveOrderLineNo(1)
					.setReceiveOrderNo(warehouseReceipt.getWarehouseReceiptNumber())
					.setReceiveOrderLineNo(warehouseReceiptDetail.getWarehouseReceiptRowNum().intValue())
					.setDeliveryLineNum(warehouseReceiptDetailVO.getDeliveryLineNum())
					.setDeliveryNumber(warehouseReceiptDetailVO.getDeliveryNumber())

					.setOrgId(warehouseReceiptDetailVO.getOrgId())
					.setOrgCode(warehouseReceiptDetailVO.getOrgCode())
					.setOrgName(warehouseReceiptDetailVO.getOrgName())
					.setOrganizationId(warehouseReceiptDetailVO.getOrganizationId())
					.setOrganizationCode(warehouseReceiptDetailVO.getOrganizationCode())
					.setOrganizationName(warehouseReceiptDetailVO.getOrganizationName())
					.setVendorId(warehouseReceiptDetailVO.getVendorId())
					.setVendorCode(warehouseReceiptDetailVO.getVendorCode())
					.setVendorName(warehouseReceiptDetailVO.getVendorName())
					.setCategoryId(warehouseReceiptDetailVO.getCategoryId())
					.setCategoryCode(warehouseReceiptDetailVO.getCategoryCode())
					.setCategoryName(warehouseReceiptDetailVO.getCategoryName())
					.setItemId(warehouseReceiptDetailVO.getMaterialId())
					.setItemCode(warehouseReceiptDetailVO.getMaterialCode())
					.setItemName(warehouseReceiptDetailVO.getMaterialName())
					.setUnit(warehouseReceiptDetailVO.getUnit())
					.setUnitCode(warehouseReceiptDetailVO.getUnitCode())
					.setReceiveNum(warehouseReceiptDetail.getWarehouseQuantity())
//					.setRequirementHeadNum()
//					.setRowNum()
					.setOrderNumber(warehouseReceiptDetailVO.getOrderNumber())
					.setSrmOrderNumber(warehouseReceiptDetailVO.getOrderNumber())
					.setSettlementOrderNumber(warehouseReceiptDetailVO.getOrderNumber())
					.setLineNum(warehouseReceiptDetailVO.getLineNum())
					.setContractNo(warehouseReceiptDetailVO.getContractNo())
					.setContractCode(warehouseReceiptDetailVO.getContractCode())
//					.setContractHeadId()
					.setTaxKey(warehouseReceiptDetailVO.getTaxKey())
					.setTaxRate(warehouseReceiptDetailVO.getTaxRate())
//					.setProjectName()
//					.setProjectNum()
//					.setTaskName()
//					.setTaskNum()
					.setNotInvoiceQuantity(warehouseReceiptDetail.getWarehouseQuantity())
					.setStorageQuantity(warehouseReceiptDetail.getWarehouseQuantity()) // 用来比较未开票数量与入库数量是否一致，如果不一致，则说明，已经存在开票数据。
					.setUnitPriceContainingTax(warehouseReceiptDetailVO.getUnitPriceContainingTax())
					.setUnitPriceExcludingTax(warehouseReceiptDetailVO.getUnitPriceExcludingTax())
					.setCurrencyId(warehouseReceiptDetailVO.getCurrencyId())
					.setCurrencyCode(warehouseReceiptDetailVO.getCurrencyCode())
					.setCurrencyName(warehouseReceiptDetailVO.getCurrencyName())
					.setReceiveDate(date)
					.setWarehousingDate(date)
					.setPoLineId(warehouseReceiptDetailVO.getPoLineId())
					.setTxnId(txnId)
					.setParentTxnId(txnId);
			result.add(warehousingReturnDetail);
		}
		return result;
	}

	/**
	 * 批量冲销
	 * @param ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchWriteOff(List<Long> ids) {
		//校验
		checkIfWriteOff(ids);
		//获取数据
		List<WarehouseReceipt> warehouseReceiptList = this.listByIds(ids);
		List<WarehouseReceiptDetail> warehouseReceiptDetailList = warehouseReceiptDetailService.listByWarehouseReceiptId(ids);

		List<Long> warehouseReceiptDetailIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getWarehouseReceiptDetailId).collect(Collectors.toList());

		// 判断是否存在开票的入库退货明细数据
		QueryWrapper<WarehousingReturnDetail> warehousingReturnDetailWrapper = new QueryWrapper<>();
		warehousingReturnDetailWrapper.in("WAREHOUSE_RECEIPT_DETAIL_ID", warehouseReceiptDetailIdList);
		warehousingReturnDetailWrapper.apply(" NOT_INVOICE_QUANTITY != STORAGE_QUANTITY");
		long existAmount = warehousingReturnDetailService.count(warehousingReturnDetailWrapper);

		if (existAmount > 0) {
			throw new BaseException(LocaleHandler.getLocaleMsg("存在已开票入库退货明细，不能冲销。"));
		}

		//更新入库单
		for(WarehouseReceipt warehouseReceipt : warehouseReceiptList){
			warehouseReceipt.setWarehouseReceiptStatus(WarehouseReceiptStatus.WRITEOFF.getValue());
		}
		this.updateBatchById(warehouseReceiptList);

		//删除入库退货明细数据
		QueryWrapper<WarehousingReturnDetail> delete = new QueryWrapper<>();
		delete.in("WAREHOUSE_RECEIPT_DETAIL_ID", warehouseReceiptDetailIdList);
		warehousingReturnDetailService.remove(delete);

		// 更新送货单入库数量
		Set<Long> deliveryNoteDetailIdList = warehouseReceiptDetailList.stream().map(WarehouseReceiptDetail::getDeliveryNoteDetailId).collect(Collectors.toSet());
		List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailService.listByIds(deliveryNoteDetailIdList);
		this.updateDeliveryNoteDetail(deliveryNoteDetailList, deliveryNoteDetailIdList);
	}

	@Override
	public int updateWarehouseReceiptStatus(List<Long> ids, WarehouseReceiptStatus warehouseReceiptStatus) {
		return super.updateByIds(ids, new WarehouseReceipt().setWarehouseReceiptStatus(warehouseReceiptStatus.getValue()));
	}

	@Override
	public List<WarehouseReceipt> listUnWriteOff(List<Long> ids) {
		return this.list(Wrappers.lambdaQuery(WarehouseReceipt.class)
				.in(WarehouseReceipt::getWarehouseReceiptId, ids)
				.ne(WarehouseReceipt::getWarehouseReceiptStatus, WarehouseReceiptStatus.WRITEOFF.getValue())
		);
	}

	/**
	 * 校验是否可以冲销
	 * @param ids
	 */
	private void checkIfWriteOff(List<Long> ids){
		if(CollectionUtils.isEmpty(ids)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
		}
		List<WarehouseReceipt> warehouseReceiptList = this.listByIds(ids);
		if(CollectionUtils.isEmpty(warehouseReceiptList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("查询不到入库单,请刷新页面"));
		}

		for(WarehouseReceipt warehouseReceipt : warehouseReceiptList){
			if(!WarehouseReceiptStatus.CONFIRM.getValue().equals(warehouseReceipt.getWarehouseReceiptStatus())){
				throw new BaseException(LocaleHandler.getLocaleMsg(String.format("入库单[%s]不是确认状态",warehouseReceipt.getWarehouseReceiptNumber())));
			}
		}
	}

	@Override
	public SFunction<WarehouseReceipt, Long> getKeyWrapper() {
		return WarehouseReceipt::getWarehouseReceiptId;
	}
}
