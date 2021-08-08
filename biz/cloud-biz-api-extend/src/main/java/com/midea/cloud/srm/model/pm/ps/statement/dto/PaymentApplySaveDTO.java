package com.midea.cloud.srm.model.pm.ps.statement.dto;

import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称描述:付款申请保存DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-17 14:51
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PaymentApplySaveDTO {

    private PaymentApplyHead paymentApplyHead;//付款申请头
    private List<PaymentApplyLine> paymentApplyLines;//付款申请行

}
