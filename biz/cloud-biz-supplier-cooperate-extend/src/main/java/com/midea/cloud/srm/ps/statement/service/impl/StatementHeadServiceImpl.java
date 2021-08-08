package com.midea.cloud.srm.ps.statement.service.impl;

import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.pm.ps.StatementStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReceipt;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReturn;
import com.midea.cloud.srm.ps.statement.service.IStatementHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * <pre>
 * 对账单
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
public class StatementHeadServiceImpl implements IStatementHeadService {

	@Autowired
	private BaseClient baseClient;

	@Resource
	private com.midea.cloud.srm.supcooperate.statement.service.IStatementHeadService scIStatementHeadService;

	@Override
	@Transactional
	public void saveStatement(StatementDTO statementDTO) {
		StatementHead statmentHead = statementDTO.getStatementHead();
		statmentHead.setPaidTotalAmount(new BigDecimal(0));
		statmentHead.setStatementHeadId(IdGenrator.generate());
		statmentHead.setStatementStatus(StatementStatusEnum.CREATE.getValue());
		statmentHead.setStatementNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_RECONCILIATION_NUMBER));
		updateAmountItem(statementDTO);
		scIStatementHeadService.saveOrUpdateStatement(statementDTO);
	}

	@Override
	@Transactional
	public void updateStatement(StatementDTO statementDTO) {
		StatementHead checkHead = scIStatementHeadService.getById(statementDTO.getStatementHead().getStatementHeadId());
		Assert.notNull(checkHead, "对账单不存在");
		Assert.isTrue(StatementStatusEnum.get(checkHead.getStatementStatus()) == StatementStatusEnum.CREATE, "只能修改拟定对账单");
		updateAmountItem(statementDTO);
		scIStatementHeadService.saveOrUpdateStatement(statementDTO);
	}

	@Override
	public void submitStatement(StatementDTO statementDTO) {
		if (statementDTO.getStatementHead().getStatementHeadId() == null) {
			statementDTO.getStatementHead().setPaidTotalAmount(new BigDecimal(0));
			statementDTO.getStatementHead().setStatementHeadId(IdGenrator.generate());
			statementDTO.getStatementHead().setStatementNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_RECONCILIATION_NUMBER));
		} else {
			StatementHead checkHead = scIStatementHeadService.getById(statementDTO.getStatementHead().getStatementHeadId());
			Assert.notNull(checkHead, "对账单不存在");
			Assert.isTrue(StatementStatusEnum.get(checkHead.getStatementStatus()) == StatementStatusEnum.CREATE, "只能提交拟定对账单");
		}
		statementDTO.getStatementHead().setSubmitTime(LocalDate.now()).setStatementStatus(StatementStatusEnum.SUBMITTED.getValue());
		updateAmountItem(statementDTO);
		scIStatementHeadService.saveOrUpdateStatement(statementDTO);
	}

	@Override
	public void deleteStatement(List<Long> statementHeadIds) {
		if (statementHeadIds != null && statementHeadIds.size() > 0) {
			List<StatementHead> listByIds = scIStatementHeadService.listByIds(statementHeadIds);
			listByIds.forEach(o -> {
				if (StatementStatusEnum.get(o.getStatementStatus()) != StatementStatusEnum.CREATE) {
					throw new BaseException("只能删除拟定状态单据！");
				}
			});
			scIStatementHeadService.deleteStatement(statementHeadIds);
		}
	}

	@Override
	public void recallStatement(List<Long> statementHeadIds) {
		if (statementHeadIds != null && statementHeadIds.size() > 0) {
			List<StatementHead> listByIds = scIStatementHeadService.listByIds(statementHeadIds);
			listByIds.forEach(o -> {
				if (StatementStatusEnum.get(o.getStatementStatus()) != StatementStatusEnum.SUBMITTED) {
					throw new BaseException("只能撤回已发布单据！");
				}
				o.setStatementStatus(StatementStatusEnum.CREATE.getValue());
			});
			scIStatementHeadService.saveOrUpdateBatch(listByIds);
		}
	}

	/**
	 * 更新金额子项数据
	 *
	 * @param statementDTO
	 */
	private void updateAmountItem(StatementDTO statementDTO) {
		StatementHead statementHead = statementDTO.getStatementHead();
		// 设置头表ID
		if (!CollectionUtils.isEmpty(statementDTO.getReceiptList())) {
			statementDTO.getReceiptList().forEach(o -> o.setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId()));
		}
		if (!CollectionUtils.isEmpty(statementDTO.getReturnList())) {
			statementDTO.getReturnList().forEach(o -> o.setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId()));
		}
		// 计算金额
		BigDecimal receiptAmount = new BigDecimal(0);
		BigDecimal returnAmount = new BigDecimal(0);
		if (!CollectionUtils.isEmpty(statementDTO.getReceiptList())) {
			for (StatementReceipt line : statementDTO.getReceiptList()) {
				receiptAmount = receiptAmount.add(line.getTotalAmountNoTax());
			}
			for (StatementReturn line : statementDTO.getReturnList()) {
				returnAmount = returnAmount.add(line.getTotalAmountNoTax());
			}
		}
		statementHead.setReceiptAmount(receiptAmount);// 入库总金额（未税）
		statementHead.setReturnAmount(returnAmount);// 退货总金额（未税）
		statementHead.setStatementTotalAmount(receiptAmount.subtract(returnAmount));
	}

}
