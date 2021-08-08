package com.midea.cloud.srm.model.pm.ps.payment.dto;

import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyLine;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyPlan;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 * 付款申请DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/26 16:42
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CeeaPaymentApplyDTO {

    private CeeaPaymentApplyHead ceeaPaymentApplyHead; //付款申请头

    private List<CeeaPaymentApplyLine> ceeaPaymentApplyLines; //付款申请行

    private List<CeeaPaymentApplyPlan> ceeaPaymentApplyPlans; //付款计划行
}
