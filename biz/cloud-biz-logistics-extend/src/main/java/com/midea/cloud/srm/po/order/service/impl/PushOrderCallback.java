package com.midea.cloud.srm.po.order.service.impl;

import com.midea.cloud.repush.service.RepushCallbackService;
import com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz.PurchaseOutputParameters;

import java.util.Objects;

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
 *  修改日期: 2020/11/12 20:11
 *  修改内容:
 * </pre>
 */
public class PushOrderCallback implements RepushCallbackService<PurchaseOutputParameters> {
    @Override
    public Boolean callback(PurchaseOutputParameters result, Object... otherService) {
        String returnStatus = "";
        if(Objects.nonNull(result)){
            returnStatus = null != result.getXesbresultinforec() ? result.getXesbresultinforec().getReturnstatus() : "E";
            if("S".equals(returnStatus)){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }
}
