package com.midea.cloud.srm.model.pm.ps.statement.dto;

import java.util.List;

import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanLine;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
 *  修改日期: Jun 15, 20203:03:53 PM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentPlanSaveDTO {

	private PaymentPlanHead paymentPlanHead;// 付款计划头
	private List<PaymentPlanLine> paymentPlanLines;// 付款计划行

}
