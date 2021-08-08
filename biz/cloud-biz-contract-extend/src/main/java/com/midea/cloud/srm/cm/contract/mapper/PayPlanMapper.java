package com.midea.cloud.srm.cm.contract.mapper;

import com.midea.cloud.srm.model.cm.contract.dto.ContractPayPlanDTO;
import com.midea.cloud.srm.model.cm.contract.dto.PayPlanRequestDTO;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.cm.contract.vo.PayPlanVo;

import java.util.List;

/**
 * <p>
 * 合同付款计划 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-05-27
 */
public interface PayPlanMapper extends BaseMapper<PayPlan> {

    /**
     * 分页条件查询合同付款计划
     * @param contractPayPlanDTO
     * @return
     */
    List<ContractPayPlanDTO> listPageContractPayPlanDTO(ContractPayPlanDTO contractPayPlanDTO);

    List<PayPlanVo> findList(PayPlanRequestDTO requestDTO);
}
