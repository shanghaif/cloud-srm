package com.midea.cloud.srm.model.pm.ps.statement.dto;

import com.midea.cloud.srm.model.common.BasePage;

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
 *  修改日期: Jun 17, 20209:29:44 AM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentApplyHeadQueryDTO extends BasePage {

	private static final long serialVersionUID = -807095571510764555L;
	private Long vendorId;// 供应商ID
	private Long organizationId;// 采购组织ID
	private String fullPathId; //组织全路径虚拟ID
	private String creationDateStart;// 单据日期开始
	private String creationDateEnd;// 单据日期结束
	private String syncStatus;// 同步状态
	private String paymentApplyNumber;// 付款申请单号
	private Long paymentApplyHeadId;//付款申请头ID
	private String sourceType;//来源类型
}
