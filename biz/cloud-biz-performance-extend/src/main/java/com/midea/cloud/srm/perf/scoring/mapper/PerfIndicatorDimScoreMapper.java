package com.midea.cloud.srm.perf.scoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoring.PerfIndicatorDimScore;

import java.util.List;

/**
 * <p>
 * 指标维度绩效得分表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-16
 */
public interface PerfIndicatorDimScoreMapper extends BaseMapper<PerfIndicatorDimScore> {

    /**
     * Description 根据条件获绩效指标绩效得分表集合(用于连表查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     **/
    List<PerfIndicatorDimScore> findIndicatorDimScoreList(PerfIndicatorDimScore indicatorDimScore);
}
