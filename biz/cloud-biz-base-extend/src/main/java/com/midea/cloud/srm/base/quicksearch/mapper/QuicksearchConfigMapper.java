package com.midea.cloud.srm.base.quicksearch.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchConfig;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  快速查询配置 Mapper
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
public interface QuicksearchConfigMapper extends BaseMapper<QuicksearchConfig> {

    List<Map<String,Object>> getQuickData(String language);

    Integer getQuickDatacount(String countStr);
}

