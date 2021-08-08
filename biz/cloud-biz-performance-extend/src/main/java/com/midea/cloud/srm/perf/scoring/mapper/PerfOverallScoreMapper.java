package com.midea.cloud.srm.perf.scoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;

import java.util.List;

/**
 * <p>
 * 综合绩效得分主表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-16
 */
public interface PerfOverallScoreMapper extends BaseMapper<PerfOverallScore> {

    /**
     * Description 根据条件获绩效指标绩效得分主表和子表集合(用于连表查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     **/
    List<PerfOverallScore> findOverallScorelList(PerfOverallScore overallScore);
}
