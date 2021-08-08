package com.midea.cloud.srm.ps.statement.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanLineQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanSaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanLine;

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
 *  修改日期: Jun 10, 20205:49:47 PM
 *  修改内容:
 *          </pre>
 */
public interface IPaymentPlanHeadService extends IService<PaymentPlanHead> {

	PageInfo<PaymentPlanHead> listPage(PaymentPlanHeadQueryDTO paymentPlanHeadQueryDTO);

	void saveOrUpdatePaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO);

	void rejectPaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO);

	void passPaymentPlan(PaymentPlanSaveDTO paymentPlanSaveDTO);

	void deletePaymentPlan(List<Long> paymentPlanIds);

	PaymentPlanSaveDTO getPaymentPlanById(Long paymentPlanHeadId);

	PageInfo<PaymentPlanLine> listPaymentPlanLinePage(PaymentPlanLineQueryDTO paymentPlanLineQueryDTO);
}
