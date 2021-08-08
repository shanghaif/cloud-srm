package com.midea.cloud.srm.perf.scoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoring.PerfIndicatorDimScore;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;

import java.util.List;

/**
 * <p>
 * 评分人绩效评分表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-11
 */
public interface PerfScoreManScoringMapper extends BaseMapper<PerfScoreManScoring> {

    /**
     * Description 分组获取指标维度绩效得分保存信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.17
     **/
    List<PerfIndicatorDimScore> getSaveIndicatorDimScoreList(PerfScoreManScoring scoreManScoring);

    /**
     * Description 分组获取指标维度绩效得分主表保存信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.17
     **/
    List<PerfOverallScore> getSaveOverallScoreList(PerfScoreManScoring scoreManScoring);

}
