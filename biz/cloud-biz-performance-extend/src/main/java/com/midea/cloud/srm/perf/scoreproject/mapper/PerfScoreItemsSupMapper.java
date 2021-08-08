package com.midea.cloud.srm.perf.scoreproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;

import java.util.List;

/**
 * <p>
 * 绩效评分项目供应商表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-06
 */
public interface PerfScoreItemsSupMapper extends BaseMapper<PerfScoreItemsSup> {

    /**
     * Description 根据条件获取绩效评分项目-供应商信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     **/
    List<PerfScoreItemsSup> findScoreItemsSupList(PerfScoreItemsSup perfScoreItemsSup);

}
