package com.midea.cloud.srm.supcooperate.reconciliation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  对账单跟踪表 Mapper 接口
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
public interface ReconciliationTrackMapper extends BaseMapper<ReconciliationTrack> {

    public Map<String, BigDecimal> sum(ReconciliationTrackRequestDTO requestDTO);

    public List<ReconciliationTrackDTO> findList(ReconciliationTrackRequestDTO requestDTO);
}