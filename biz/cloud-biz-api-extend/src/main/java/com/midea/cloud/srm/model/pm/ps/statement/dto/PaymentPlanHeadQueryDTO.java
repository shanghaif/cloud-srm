package com.midea.cloud.srm.model.pm.ps.statement.dto;

import com.midea.cloud.srm.model.common.BasePage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 * 付款计划查询条件
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 15, 20201:56:49 PM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentPlanHeadQueryDTO extends BasePage {

	private static final long serialVersionUID = -1988710276804404631L;

	private String paymentPlanNumber;// 付款计划单号
	private Long organizationId;// 采购组织ID
	private String fullPathId; //组织全路径虚拟ID
	private String planPaymentDateStart;// 计划付款日期开始
	private String planPaymentDateEnd;// 计划付款日期结束
	private String creationDate;// 单据日期
	private String paymentPlanStatus;// 付款计划单据状态

}
