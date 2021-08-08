package com.midea.cloud.srm.perf.indicators.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsHeaderDTO;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 指标库头表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-26
 */
public interface IndicatorsHeaderMapper extends BaseMapper<IndicatorsHeader> {

    /**
     * Description 根据条件获取指标头和行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     * @throws
     **/
    IndicatorsHeaderDTO findIndicationHeadAndLineList(@Param("indicatorsHeader") IndicatorsHeaderDTO indicatorsHeader);

    /**
     * Description 获取指标类型-指标维度(启动、不删除和不重复的指标维度和指标类型)
     * @Param
     * @return Map的Key为 indicatorType和indicatorDimension
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     **/
    List<Map<String, Object>> findIndicatorsHeaderDimensionList();

    /**
     根据指标类型和指标维度获取有效的指标头信息
     * @Param indicatorsHeader 指标头实体类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     * @throws
     **/
    List<IndicatorsHeaderDTO> findIndicatorsHeaderByDimension(IndicatorsHeader indicatorsHeader);
}
