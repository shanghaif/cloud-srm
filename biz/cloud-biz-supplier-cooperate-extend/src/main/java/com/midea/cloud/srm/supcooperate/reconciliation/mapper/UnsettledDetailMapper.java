package com.midea.cloud.srm.supcooperate.reconciliation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;

import java.util.List;

/**
 * <pre>
 *  未结算数量对账单明细表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface UnsettledDetailMapper extends BaseMapper<UnsettledDetail> {

    List<UnsettledDetail> findList(UnsettledOrderRequestDTO requestDTO);

    List<UnsettledDetail> materialCountList(UnsettledOrderRequestDTO requestDTO);
}