package com.midea.cloud.srm.perf.scoreproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;

import java.util.List;

/**
 * <p>
 * 绩效评分项目评分人-供应商指标表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-07-02
 */
public interface PerfScoreItemManSupIndMapper extends BaseMapper<PerfScoreItemManSupInd> {

    /**
     * Description 根据条件获取绩效评分项目评分人-供应商指标表集合(误删，在绩效评分项目评分人Mapper.xml文件中有引用)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.02
     **/
    List<PerfScoreItemManSupInd> listScoreItemManSupInd(PerfScoreItemManSupInd scoreItemManSupInd);
}
