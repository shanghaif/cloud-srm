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
 *  修改日期: Jun 16, 20209:23:10 AM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentPlanLineQueryDTO extends BasePage {

	private static final long serialVersionUID = 4105524342104294591L;

	private String sourceType;// 来源类型
	private String sourceNumber;// 来源单号
	private Long vendorId;// 供应商ID
	private String vendorName;// 供应商名称

}
