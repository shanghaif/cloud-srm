package com.midea.cloud.srm.sup.info.service.impl;

import com.midea.cloud.repush.service.RepushCallbackService;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank.VendorBankOutputParameters;

import java.util.Objects;

/**
 * <pre>
 *  推送银行信息失败重推回调
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/19 16:46
 *  修改内容:
 * </pre>
 */
public class PushBankInfoCallBack implements RepushCallbackService<VendorBankOutputParameters> {

    @Override
    public Boolean callback(VendorBankOutputParameters result, Object... otherService) {
        String returnStatus = "";
        if (Objects.nonNull(result)) {
            returnStatus = null != result.getXesbresultinforec() ? result.getXesbresultinforec().getReturnstatus() : "E";
            if ("S".equals(returnStatus)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
