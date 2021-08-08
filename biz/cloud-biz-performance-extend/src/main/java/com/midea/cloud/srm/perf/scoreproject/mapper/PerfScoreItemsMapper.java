package com.midea.cloud.srm.perf.scoreproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemsDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;

/**
 * <p>
 * 绩效评分项目主信息表 Mapper 接口
 * </p>Site
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-05
 */
public interface PerfScoreItemsMapper extends BaseMapper<PerfScoreItems> {
    /**
     * Description 根据条件获取绩效评分项目和子表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     * @throws
     **/
    PerfScoreItemsDTO findScoreItemsAndSonList(PerfScoreItems perfScoreItems);
}
