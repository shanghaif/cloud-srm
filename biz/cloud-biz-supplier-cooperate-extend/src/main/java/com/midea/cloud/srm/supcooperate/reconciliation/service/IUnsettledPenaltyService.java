package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledPenaltyDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单罚扣款表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:20
 *  修改内容:
 * </pre>
 */
public interface IUnsettledPenaltyService extends IService<UnsettledPenalty> {
    List<UnsettledPenaltyDTO> findUnsettledList(UnsettledOrderRequestDTO requestDTO);
}
