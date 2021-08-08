package com.midea.cloud.srm.ps.statement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.pm.ps.PaymentPlanSourceTypeEnum;
import com.midea.cloud.common.enums.pm.ps.PaymentSchedulesStatusEnum;
import com.midea.cloud.common.enums.pm.ps.StatementStatusEnum;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.model.cm.contract.dto.ContractPayPlanDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanLineQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanSaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.*;
import com.midea.cloud.srm.ps.statement.mapper.PaymentPlanHeadMapper;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyHeadService;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyLineService;
import com.midea.cloud.srm.ps.statement.service.IPaymentPlanHeadService;
import com.midea.cloud.srm.ps.statement.service.IPaymentPlanLineService;
import com.midea.cloud.srm.supcooperate.statement.service.IStatementHeadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * <pre>
 * 付款计划
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 10, 20205:42:26 PM
 *  修改内容:
 *          </pre>
 */
@Service
public class PaymentPlanHeadServiceImpl extends ServiceImpl<PaymentPlanHeadMapper, PaymentPlanHead> implements IPaymentPlanHeadService {

	@Autowired
	private IPaymentPlanLineService iPaymentPlanLineService;

	@Autowired
	private IPaymentApplyHeadService iPaymentApplyHeadService;

	@Autowired
	private IPaymentApplyLineService iPaymentApplyLineService;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private ContractClient contractClient;

	@Resource
	private IStatementHeadService scIStatementHeadService;

	@Override
	public PageInfo<PaymentPlanHead> listPage(PaymentPlanHeadQueryDTO paymentPlanHeadQueryDTO) {
		PageUtil.startPage(paymentPlanHeadQueryDTO.getPageNum(), paymentPlanHeadQueryDTO.getPageSize());
		PaymentPlanHead paymentPlanHead = new PaymentPlanHead();
		BeanUtils.copyProperties(paymentPlanHeadQueryDTO, paymentPlanHead);
		QueryWrapper<PaymentPlanHead> queryWrapper = new QueryWrapper<PaymentPlanHead>(paymentPlanHead);
		queryWrapper.like(StringUtils.isNotBlank(paymentPlanHeadQueryDTO.getPaymentPlanNumber()), "PAYMENT_PLAN_NUMBER", paymentPlanHeadQueryDTO.getPaymentPlanNumber());
		queryWrapper.apply(paymentPlanHeadQueryDTO.getPlanPaymentDateStart() != null, "DATE_FORMAT(PLAN_PAYMENT_DATE,'%Y-%m-%d') >= {0} ", paymentPlanHeadQueryDTO.getPlanPaymentDateStart());
		queryWrapper.apply(paymentPlanHeadQueryDTO.getPlanPaymentDateEnd() != null, "DATE_FORMAT(PLAN_PAYMENT_DATE,'%Y-%m-%d') <= {0}  ", paymentPlanHeadQueryDTO.getPlanPaymentDateEnd());
		queryWrapper.apply(paymentPlanHeadQueryDTO.getCreationDate() != null, "DATE_FORMAT(CREATION_DATE,'%Y-%m-%d') = {0}", paymentPlanHeadQueryDTO.getCreationDate());
		queryWrapper.orderByDesc("PAYMENT_PLAN_HEAD_ID");
		return new PageInfo<PaymentPlanHead>(this.list(queryWrapper));
	}

	@Override
	@Transactional
	public void saveOrUpdatePaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO) {
		if (paymentPlanSaveDTO.getPaymentPlanHead().getPaymentPlanHeadId() == null) {
			paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanHeadId(IdGenrator.generate());
			paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_PAYMENT_PLAN_NUMBER));
		} else {
			iPaymentPlanLineService.remove(new QueryWrapper<PaymentPlanLine>(new PaymentPlanLine().setPaymentPlanHeadId(paymentPlanSaveDTO.getPaymentPlanHead().getPaymentPlanHeadId())));
		}
		BigDecimal planPaymentAmountNoTax = new BigDecimal(0);
		if (!CollectionUtils.isEmpty(paymentPlanSaveDTO.getPaymentPlanLines())) {
			// 行表设置头表ID
			paymentPlanSaveDTO.getPaymentPlanLines().forEach(l -> {
				l.setPaymentPlanLineId(IdGenrator.generate());
				l.setPaymentPlanHeadId(paymentPlanSaveDTO.getPaymentPlanHead().getPaymentPlanHeadId());
			});
			for (PaymentPlanLine l : paymentPlanSaveDTO.getPaymentPlanLines()) {
				if (l.getPlanPaymentAmountNoTax() != null) {
					planPaymentAmountNoTax = planPaymentAmountNoTax.add(l.getPlanPaymentAmountNoTax());
				}
			}
			iPaymentPlanLineService.saveOrUpdateBatch(paymentPlanSaveDTO.getPaymentPlanLines());
		}
		paymentPlanSaveDTO.getPaymentPlanHead().setPlanPaymentAmountNoTax(planPaymentAmountNoTax);
		this.saveOrUpdate(paymentPlanSaveDTO.getPaymentPlanHead());
	}

	@Override
	public void rejectPaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO) {
		PaymentPlanHead paymentPlanHead = this.getById(paymentPlanSaveDTO.getPaymentPlanHead().getPaymentPlanHeadId());
		paymentPlanHead.setPaymentPlanStatus(PaymentSchedulesStatusEnum.REJECTED.getValue());
		this.updateById(paymentPlanHead);
		List<Long> paymentPlanLineIds = paymentPlanSaveDTO.getPaymentPlanLines().stream().map(PaymentPlanLine::getPaymentPlanLineId).collect(Collectors.toList());
		List<PaymentPlanLine> existedPaymentPlanLines = iPaymentPlanLineService.listByIds(paymentPlanLineIds);
		// 更新字段：本次计划付款金额（未税），备注
		Map<Long, PaymentPlanLine> paymentPlanLineMap = paymentPlanSaveDTO.getPaymentPlanLines().stream().collect(Collectors.toMap(PaymentPlanLine::getPaymentPlanLineId, Function.identity()));
		existedPaymentPlanLines.forEach(o -> {
			o.setPlanPaymentAmountNoTax(paymentPlanLineMap.get(o.getPaymentPlanLineId()).getPlanPaymentAmountNoTax());
			o.setRemark(paymentPlanLineMap.get(o.getPaymentPlanLineId()).getRemark());
		});
		iPaymentPlanLineService.updateBatchById(existedPaymentPlanLines);
	}

	@Override
	@Transactional
	public void passPaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO) {
		PaymentPlanHead paymentPlanHead = this.getById(paymentPlanSaveDTO.getPaymentPlanHead().getPaymentPlanHeadId());
		paymentPlanHead.setPaymentPlanStatus(PaymentSchedulesStatusEnum.APPROVED.getValue());
		this.updateById(paymentPlanHead);
		List<PaymentPlanLine> existedPaymentPlanLines = iPaymentPlanLineService.list(new QueryWrapper<PaymentPlanLine>(new PaymentPlanLine().setPaymentPlanHeadId(paymentPlanHead.getPaymentPlanHeadId())));
		Map<Long, PaymentPlanLine> paymentPlanLineMap = paymentPlanSaveDTO.getPaymentPlanLines().stream().collect(Collectors.toMap(PaymentPlanLine::getPaymentPlanLineId, Function.identity(), (k1, k2) -> k1));
		// 更新字段：本次计划付款金额（未税），备注
		existedPaymentPlanLines.forEach(o -> {
			if (paymentPlanLineMap.get(o.getPaymentPlanLineId()) != null) {
				o.setPlanPaymentAmountNoTax(paymentPlanLineMap.get(o.getPaymentPlanLineId()).getPlanPaymentAmountNoTax());
				o.setRemark(paymentPlanLineMap.get(o.getPaymentPlanLineId()).getRemark());
			}
		});
		iPaymentPlanLineService.updateBatchById(existedPaymentPlanLines);
		// 初始化采购申请单据，按照供应商拆分
		Map<Long, PaymentApplyHead> paymentApplyHeadMap = new HashMap<Long, PaymentApplyHead>();
		Map<PaymentApplyHead, List<PaymentApplyLine>> paymentApplyLineMap = new HashMap<PaymentApplyHead, List<PaymentApplyLine>>();
		for (PaymentPlanLine paymentPlanLine : existedPaymentPlanLines) {
			PaymentApplyHead paymentApplyHead = paymentApplyHeadMap.get(paymentPlanLine.getVendorId());
			if (paymentApplyHead == null) {
				paymentApplyHead = new PaymentApplyHead();
				paymentApplyHead.setPaymentPlanHeadId(paymentPlanHead.getPaymentPlanHeadId());
				paymentApplyHead.setPaymentPlanNumber(paymentPlanHead.getPaymentPlanNumber());
				paymentApplyHead.setVendorCode(paymentPlanLine.getVendorCode());
				paymentApplyHead.setVendorId(paymentPlanLine.getVendorId());
				paymentApplyHead.setVendorName(paymentPlanLine.getVendorName());
				paymentApplyHead.setFullPathId(paymentPlanHead.getFullPathId());
				paymentApplyHead.setOrganizationId(paymentPlanHead.getOrganizationId());
				paymentApplyHead.setOrganizationName(paymentPlanHead.getOrganizationName());
				paymentApplyHead.setOrganizationCode(paymentPlanHead.getOrganizationCode());
				paymentApplyHead.setCurrency(paymentPlanHead.getCurrency());
				paymentApplyHead.setTaxRate(paymentPlanLine.getTaxRate());
				paymentApplyHead.setPaymentApplyHeadId(IdGenrator.generate());
				paymentApplyHead.setPaymentApplyNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_PAYMENT_APPLY_NUMBER));
			}
			paymentApplyHeadMap.put(paymentApplyHead.getVendorId(), paymentApplyHead);
			List<PaymentApplyLine> paymentApplyLineList = paymentApplyLineMap.get(paymentApplyHead);
			if (paymentApplyLineList == null) {
				paymentApplyLineList = new ArrayList<PaymentApplyLine>();
			}
			PaymentApplyLine paymentApplyLine = new PaymentApplyLine();
			BeanUtils.copyProperties(paymentPlanLine, paymentApplyLine);
			paymentApplyLine.setPaymentApplyLineId(IdGenrator.generate());
			paymentApplyLine.setPaymentApplyHeadId(paymentApplyHead.getPaymentApplyHeadId());
			paymentApplyLine.setPaymentPlanHeadId(paymentPlanLine.getPaymentPlanHeadId());
			paymentApplyLine.setPaymentPlanLineId(paymentPlanLine.getPaymentPlanLineId());
			paymentApplyLineList.add(paymentApplyLine);
			paymentApplyLineMap.put(paymentApplyHead, paymentApplyLineList);
		}
		for (PaymentApplyHead paymentApplyHead : paymentApplyLineMap.keySet()) {
			List<PaymentApplyLine> paymentApplyLineList = paymentApplyLineMap.get(paymentApplyHead);
			BigDecimal sum = new BigDecimal(0);
			for (PaymentApplyLine line : paymentApplyLineList) {
				if (line.getTotalAmountNoTax() != null) {
					sum = sum.add(line.getTotalAmountNoTax());
				}
			}
			paymentApplyHead.setTotalAmountNoTax(sum);
		}
		iPaymentApplyHeadService.saveOrUpdateBatch(Arrays.asList(paymentApplyLineMap.keySet().toArray(new PaymentApplyHead[] {})));
		List<PaymentApplyLine> allPaymentApplyLineList = new ArrayList<PaymentApplyLine>();
		paymentApplyLineMap.values().forEach(l -> {
			allPaymentApplyLineList.addAll(l);
		});
		iPaymentApplyLineService.saveOrUpdateBatch(allPaymentApplyLineList);
	}

	@Override
	public PageInfo<PaymentPlanLine> listPaymentPlanLinePage(PaymentPlanLineQueryDTO paymentPlanLineQueryDTO) {
		PaymentPlanSourceTypeEnum paymentPlanSourceTypeEnum = PaymentPlanSourceTypeEnum.get(paymentPlanLineQueryDTO.getSourceType());
		List<PaymentPlanLine> paymentPlanLineList = new ArrayList<PaymentPlanLine>();
		if (paymentPlanSourceTypeEnum == PaymentPlanSourceTypeEnum.ACCOUNT_STATEMENT) {
//			PageInfo<StatementHead> page = supcooperateClient.getStatementHeadByPaymentPlanLineQueryDTO(paymentPlanLineQueryDTO);
			PageUtil.startPage(paymentPlanLineQueryDTO.getPageNum(), paymentPlanLineQueryDTO.getPageSize());
			QueryWrapper<StatementHead> queryWrapper = new QueryWrapper<StatementHead>(new StatementHead().setStatementStatus(StatementStatusEnum.PASS.getValue()).setVendorId(paymentPlanLineQueryDTO.getVendorId()));
			queryWrapper.like(org.apache.commons.lang3.StringUtils.isNotBlank(paymentPlanLineQueryDTO.getSourceNumber()), "STATEMENT_NUMBER", paymentPlanLineQueryDTO.getSourceNumber());
			queryWrapper.ne("PAID_FINISHED", "Y").or().isNull("PAID_FINISHED");
			PageInfo<StatementHead> page = new PageInfo<StatementHead>(scIStatementHeadService.list(queryWrapper));
			page.getList().forEach(o -> {
				PaymentPlanLine paymentPlanLine = new PaymentPlanLine();
				paymentPlanLine.setSourceType(paymentPlanLineQueryDTO.getSourceType());
				paymentPlanLine.setSourceNumber(o.getStatementNumber());
				paymentPlanLine.setPayMethod(o.getPaymentMethod());
				paymentPlanLine.setTermOfPayment(o.getTermOfPayment());
				paymentPlanLine.setVendorId(o.getVendorId());
				paymentPlanLine.setVendorName(o.getVendorName());
				paymentPlanLine.setVendorCode(o.getVendorCode());
				paymentPlanLine.setTaxRate(o.getTaxRate());
				paymentPlanLine.setTotalAmountNoTax(o.getStatementTotalAmount());
				paymentPlanLine.setUnpaidAmountNoTax(o.getStatementTotalAmount().subtract(o.getPaidTotalAmount()));
				paymentPlanLineList.add(paymentPlanLine);
			});
			PageInfo<PaymentPlanLine> pageInfo = new PageInfo<PaymentPlanLine>();
			BeanUtils.copyProperties(page, pageInfo);
			pageInfo.setList(paymentPlanLineList);
			return pageInfo;
		} else if (paymentPlanSourceTypeEnum == PaymentPlanSourceTypeEnum.CONTRACT) {
			ContractPayPlanDTO contractPayPlanDTO = new ContractPayPlanDTO();
			contractPayPlanDTO.setPageNum(paymentPlanLineQueryDTO.getPageNum());
			contractPayPlanDTO.setPageSize(paymentPlanLineQueryDTO.getPageSize());
			contractPayPlanDTO.setContractNo(paymentPlanLineQueryDTO.getSourceNumber());
			contractPayPlanDTO.setVendorId(paymentPlanLineQueryDTO.getVendorId());
			contractPayPlanDTO.setVendorName(paymentPlanLineQueryDTO.getVendorName());
			PageInfo<ContractPayPlanDTO> listContractPayPlanDTO = contractClient.listPageContractPayPlanDTO(contractPayPlanDTO);
			listContractPayPlanDTO.getList().forEach(o -> {
				PaymentPlanLine paymentPlanLine = new PaymentPlanLine();
				paymentPlanLine.setSourceType(paymentPlanLineQueryDTO.getSourceType());
				paymentPlanLine.setSourceNumber(o.getContractNo());
				paymentPlanLine.setPayStage(o.getPayStage());
				paymentPlanLine.setPayMethod(o.getPayMethod());
				paymentPlanLine.setPayType(o.getPayType());
				paymentPlanLine.setVendorId(o.getVendorId());
				paymentPlanLine.setVendorName(o.getVendorName());
				paymentPlanLine.setVendorCode(o.getVendorCode());
				paymentPlanLine.setTaxRate(o.getTaxRate());
				paymentPlanLine.setTotalAmountNoTax(o.getTotalAmountNoTax());
				paymentPlanLine.setUnpaidAmountNoTax(o.getUnpaidAmountNoTax());
				paymentPlanLine.setSourceLineId(o.getPayPlanId());
				paymentPlanLineList.add(paymentPlanLine);
			});
			PageInfo<PaymentPlanLine> pageInfo = new PageInfo<PaymentPlanLine>();
			BeanUtils.copyProperties(listContractPayPlanDTO, pageInfo);
			pageInfo.setList(paymentPlanLineList);
			return pageInfo;
		}
		return null;
	}

	@Override
	public void deletePaymentPlan(List<Long> paymentPlanIds) {
		List<PaymentPlanHead> paymentPlanHeadList = this.listByIds(paymentPlanIds);
		paymentPlanHeadList.forEach(o -> {
			Assert.isTrue(PaymentSchedulesStatusEnum.get(o.getPaymentPlanStatus()) == PaymentSchedulesStatusEnum.DRAFT, "只能删除拟定的付款计划");
		});
		this.removeByIds(paymentPlanIds);
		iPaymentPlanLineService.remove(new QueryWrapper<PaymentPlanLine>().in("PAYMENT_PLAN_HEAD_ID", paymentPlanIds));
	}

	@Override
	public PaymentPlanSaveDTO getPaymentPlanById(Long paymentPlanHeadId) {
		PaymentPlanHead byId = this.getById(paymentPlanHeadId);
		List<PaymentPlanLine> list = iPaymentPlanLineService.list(new QueryWrapper<PaymentPlanLine>(new PaymentPlanLine().setPaymentPlanHeadId(paymentPlanHeadId)));
		return new PaymentPlanSaveDTO().setPaymentPlanHead(byId).setPaymentPlanLines(list);
	}
}
