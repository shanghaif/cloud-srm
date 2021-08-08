package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledPenaltyDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.UnsettledPenaltyMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledPenaltyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单罚扣款表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/27 19:33
 *  修改内容:
 * </pre>
 */
@Service
public class UnsettledPenaltyServiceImpl extends ServiceImpl<UnsettledPenaltyMapper, UnsettledPenalty> implements IUnsettledPenaltyService {

    @Override
    public List<UnsettledPenaltyDTO> findUnsettledList(UnsettledOrderRequestDTO requestDTO) {
        return this.getBaseMapper().findUnsettledList(requestDTO);
    }
}
