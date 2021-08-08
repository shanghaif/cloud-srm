package com.midea.cloud.srm.ps.http.fssc.service;

import com.midea.cloud.srm.model.pm.ps.http.*;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.PaymentApplyDto;

/**
 * <pre>
 *  财务共享接口Service
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 15:59
 *  修改内容:
 * </pre>
 */
public interface IFSSCReqService {

    String getToken();

    /**
     * 预算释放
     * @param bgtCheckReqParamDto
     * @return
     */
    FSSCResult applyRelease(BgtCheckReqParamDto bgtCheckReqParamDto);

    /**
     * 预算冻结
     * @param bgtCheckReqParamDto
     * @return
     */
    FSSCResult applyFreeze(BgtCheckReqParamDto bgtCheckReqParamDto);

    /**
     * 提交付款申请审批
     */
    FSSCResult submitPaymentApply(PaymentApplyDto paymentApplyDto);

    /**
     * 提交预付款审批
     */
    FSSCResult submitAdvanceApply(AdvanceApplyDto advanceApplyDto);


    /**
     * 提交网上开票(对接费控)
     * @param paymentApplyDto
     * @return
     */
    FSSCResult submitOnlineInvoice(PaymentApplyDto paymentApplyDto);

    /**
     * 作废
     * @param fsscStatus
     * @return
     */
    FSSCResult abandon(FsscStatus fsscStatus);
}
