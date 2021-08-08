package com.midea.cloud.srm.perf.level.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.level.entity.PerfLevel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 绩效等级表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-03
 */
public interface PerfLevelMapper extends BaseMapper<PerfLevel> {

    /**
     * Description 获取有效的去重等级名称集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     **/
    List<Map<String, Object>> findDistinctLevelNameList();

}
