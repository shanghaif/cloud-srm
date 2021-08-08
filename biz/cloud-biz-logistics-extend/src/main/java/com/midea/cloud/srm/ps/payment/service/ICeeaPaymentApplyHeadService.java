package com.midea.cloud.srm.ps.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyDTO;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.payment.vo.CeeaPaymentApplyVo;

/**
*  <pre>
 *  付款申请-头表（隆基新增） 服务类
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 21:04:34
 *  修改内容:
 * </pre>
*/
public interface ICeeaPaymentApplyHeadService extends IService<CeeaPaymentApplyHead> {

    /**
     * Description 根据付款计划ID查询对应的
     * @Param payPlanId
     * @return ceeaPaymentApplyDTO
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.26
     **/
    CeeaPaymentApplyDTO getPaymentApplyByPayPlanId(Long payPlanId);

    /**
     * 查询
     * @param queryDTO
     * @return
     */
    PageInfo<CeeaPaymentApplyHead> listPage(CeeaPaymentApplyHeadQueryDTO queryDTO);

    /**
     * 保存付款申请
     * @param ceeaPaymentApplySaveDTO
     * @return
     */
    Long savePaymentApply(CeeaPaymentApplySaveDTO ceeaPaymentApplySaveDTO);

    /**
     * 提交付款申请
     * @param ceeaPaymentApplySaveDTO
     */
    FSSCResult submitPaymentApply(CeeaPaymentApplySaveDTO ceeaPaymentApplySaveDTO);

    /**
     * 作废单据
     * @param paymentApplyHeadId
     */
    void delete(Long paymentApplyHeadId);

    /**
     * 查询详情
     * @param paymentApplyHeadId
     * @return
     */
    CeeaPaymentApplyVo detail(Long paymentApplyHeadId);

    /**
     * 作废
     * @param paymentApplyHeadId
     * @return
     */
    FSSCResult abandon(Long paymentApplyHeadId);
}
