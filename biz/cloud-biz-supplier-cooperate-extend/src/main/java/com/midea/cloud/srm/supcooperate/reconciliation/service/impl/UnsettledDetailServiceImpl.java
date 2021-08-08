package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.UnsettledDetailMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单表 服务实现类
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
public class UnsettledDetailServiceImpl extends ServiceImpl<UnsettledDetailMapper, UnsettledDetail> implements IUnsettledDetailService {

    @Override
    public List<UnsettledDetail> findList(UnsettledOrderRequestDTO requestDTO) {
        return this.getBaseMapper().findList(requestDTO);
    }

    @Override
    public List<UnsettledDetail> materialCountList(UnsettledOrderRequestDTO requestDTO) {
        return this.getBaseMapper().materialCountList(requestDTO);
    }
}
