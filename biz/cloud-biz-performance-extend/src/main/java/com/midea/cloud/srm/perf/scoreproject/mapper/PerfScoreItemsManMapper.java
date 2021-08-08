package com.midea.cloud.srm.perf.scoreproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;

import java.util.List;

/**
 * <p>
 * 绩效评分项目评分人表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-06
 */
public interface PerfScoreItemsManMapper extends BaseMapper<PerfScoreItemsMan> {

    /**
     * Description 根据添加查询绩效评分项目-评分人集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     **/
    List<PerfScoreItemsMan> listScoreItemsMan(PerfScoreItemsMan scoreItemsMan);

    /**
     * Description 根据绩效评分项目供应商Id、指标维度ID获取有效对应的评分人信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.11
     **/
    List<PerfScoreItemsMan> listScoreItemsManByItemsSupIdAndDimWeightId(PerfScoreItemsMan scoreItemsMan);
}
