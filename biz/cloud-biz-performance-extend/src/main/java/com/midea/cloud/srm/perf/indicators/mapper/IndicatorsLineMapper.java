package com.midea.cloud.srm.perf.indicators.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 指标库行表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-26
 */
public interface IndicatorsLineMapper extends BaseMapper<IndicatorsLine> {
    List<IndicatorsLine> queruIndicatorsLine(@Param("scoreManScoringId") Long scoreManScoringId);
}
