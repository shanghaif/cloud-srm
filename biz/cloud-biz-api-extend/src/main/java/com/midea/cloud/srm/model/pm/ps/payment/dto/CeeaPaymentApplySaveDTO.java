package com.midea.cloud.srm.model.pm.ps.payment.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyLine;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyPlan;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/1 13:48
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CeeaPaymentApplySaveDTO implements Serializable {
    private CeeaPaymentApplyHead paymentApplyHead;

    private List<CeeaPaymentApplyLine> paymentApplyLineList;

    private List<CeeaPaymentApplyPlan> paymentApplyPlanList;

    private List<Fileupload> fileuploadList;
}
