package com.midea.cloud.srm.cm.contract.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.contract.dto.ContractPayPlanDTO;
import com.midea.cloud.srm.model.cm.contract.dto.PayPlanRequestDTO;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.cm.contract.vo.PayPlanVo;

import java.util.List;

/**
*  <pre>
 *  合同付款计划 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:18:17
 *  修改内容:
 * </pre>
*/
public interface IPayPlanService extends IService<PayPlan> {

    /**
     * 分页条件查询合同付款计划
     * @param contractPayPlanDTO
     * @return
     */
    PageInfo<ContractPayPlanDTO> listPageContractPayPlanDTO(ContractPayPlanDTO contractPayPlanDTO);

    /**
     * 分页查询合同付款计划
     */
    PageInfo<PayPlanVo> ceeaListPageForPaymentApply(PayPlanRequestDTO requestDTO);

    /**
     * 发起付款申请
     * @param payPlanId
     */
    void startPayApplication(Long payPlanId);

    /**
     * 批量删除2.0
     * @param payPlanIds
     */
    void batchDeleteSecond(List<Long> payPlanIds);

}
