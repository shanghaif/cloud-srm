package com.midea.cloud.srm.ps.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;

import java.util.List;

/**
 * <p>
 * 付款申请-头表（隆基新增） Mapper 接口
 * </p>
 *
 * @author xiexh12@midea.com
 * @since 2020-08-25
 */
public interface CeeaPaymentApplyHeadMapper extends BaseMapper<CeeaPaymentApplyHead> {
    List<CeeaPaymentApplyHead> findList(CeeaPaymentApplyHeadQueryDTO queryDTO);
}
