package com.midea.cloud.srm.ps.statement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyHead;

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
 *  修改日期: Jun 10, 20205:49:47 PM
 *  修改内容:
 *          </pre>
 */
public interface IPaymentApplyHeadService extends IService<PaymentApplyHead> {

    /**
     * 根据参数获取付款申请头行
     * @param paymentApplyHeadQueryDTO
     * @return
     */
    PaymentApplySaveDTO getPaymentApplyByParm(PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO);

	PageInfo<PaymentApplyHead> listPage(PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO);

	void syncERP(Long paymentApplyHeadId);

    /**
     * 保存付款申请
     * @param paymentApplySaveDTO
     */
    void savePaymentApply(PaymentApplySaveDTO paymentApplySaveDTO);
}
