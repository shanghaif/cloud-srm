package com.midea.cloud.srm.ps.statement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyLine;
import com.midea.cloud.srm.ps.statement.mapper.PaymentApplyHeadMapper;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyHeadService;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyLineService;

/**
 *
 * <pre>
 * 付款申请
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
public class PaymentApplyHeadServiceImpl extends ServiceImpl<PaymentApplyHeadMapper, PaymentApplyHead> implements IPaymentApplyHeadService {

	@Autowired
	private IPaymentApplyLineService iPaymentApplyLineService;

	@Autowired
	private BaseClient baseClient;

	@Override
	public PaymentApplySaveDTO getPaymentApplyByParm(PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO) {
		PaymentApplyHead paymentApplyHeadQuery = new PaymentApplyHead();
		BeanUtils.copyProperties(paymentApplyHeadQueryDTO, paymentApplyHeadQuery);
		PaymentApplyHead paymentApplyHead = this.getOne(new QueryWrapper<>(paymentApplyHeadQuery));
		List<PaymentApplyLine> paymentApplyLines = new ArrayList<PaymentApplyLine>();
		PaymentApplyLine paymentApplyLine = new PaymentApplyLine();
		BeanUtils.copyProperties(paymentApplyHeadQueryDTO, paymentApplyLine);
		if (paymentApplyHead != null) {
			paymentApplyLine.setPaymentApplyHeadId(paymentApplyHead.getPaymentApplyHeadId());
			paymentApplyLines = iPaymentApplyLineService.list(new QueryWrapper<PaymentApplyLine>(paymentApplyLine));
		}
		PaymentApplySaveDTO paymentApplySaveDTO = new PaymentApplySaveDTO().setPaymentApplyHead(paymentApplyHead).setPaymentApplyLines(paymentApplyLines);
		return paymentApplySaveDTO;
	}

	@Override
	public PageInfo<PaymentApplyHead> listPage(PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO) {
		PageUtil.startPage(paymentApplyHeadQueryDTO.getPageNum(), paymentApplyHeadQueryDTO.getPageSize());
		PaymentApplyHead paymentApplyHead = new PaymentApplyHead();
		BeanUtils.copyProperties(paymentApplyHeadQueryDTO, paymentApplyHead);
		QueryWrapper<PaymentApplyHead> queryWrapper = new QueryWrapper<PaymentApplyHead>(paymentApplyHead);
		queryWrapper.like(StringUtils.isNotBlank(paymentApplyHeadQueryDTO.getPaymentApplyNumber()), "PAYMENT_APPLY_NUMBER", paymentApplyHeadQueryDTO.getPaymentApplyNumber());
		queryWrapper.apply(paymentApplyHeadQueryDTO.getCreationDateStart() != null, "DATE_FORMAT(CREATION_DATE,'%Y-%m-%d') <= {0}", paymentApplyHeadQueryDTO.getCreationDateStart());
		queryWrapper.apply(paymentApplyHeadQueryDTO.getCreationDateEnd() != null, "DATE_FORMAT(CREATION_DATE,'%Y-%m-%d') >= {0}", paymentApplyHeadQueryDTO.getCreationDateEnd());
		return new PageInfo<PaymentApplyHead>(this.list(queryWrapper));
	}

	@Override
	public void syncERP(Long paymentApplyHeadId) {
		// TODO
	}

	@Override
	@Transactional
	public void savePaymentApply(PaymentApplySaveDTO paymentApplySaveDTO) {
		PaymentApplyHead paymentApplyHead = paymentApplySaveDTO.getPaymentApplyHead();
		List<PaymentApplyLine> paymentApplyLines = paymentApplySaveDTO.getPaymentApplyLines();
		Assert.notNull(paymentApplyHead, LocaleHandler.getLocaleMsg("paymentApplyHead为空"));
		Assert.notEmpty(paymentApplyLines, LocaleHandler.getLocaleMsg("paymentApplyLines为空"));
		// 保存付款申请头
		savePaymentApplyHead(paymentApplyHead);
		// 保存付款申请行
		savePaymentApplyLines(paymentApplyHead, paymentApplyLines);
	}

	private void savePaymentApplyLines(PaymentApplyHead paymentApplyHead, List<PaymentApplyLine> paymentApplyLines) {
		paymentApplyLines.forEach(paymentApplyLine -> {
			if (paymentApplyLine != null && paymentApplyLine.getPaymentApplyLineId() == null) {
				paymentApplyLine.setPaymentApplyLineId(IdGenrator.generate()).setPaymentApplyHeadId(paymentApplyHead.getPaymentApplyHeadId());
				iPaymentApplyLineService.save(paymentApplyLine);
			}
		});
	}

	private void savePaymentApplyHead(PaymentApplyHead paymentApplyHead) {
		if (paymentApplyHead.getPaymentApplyHeadId() == null) {
			long id = IdGenrator.generate();
			paymentApplyHead.setPaymentApplyHeadId(id).setPaymentApplyNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_PAYMENT_APPLY_NUMBER));
			this.save(paymentApplyHead);
		}
	}
}
