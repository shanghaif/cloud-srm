package com.midea.cloud.srm.base.dynamicsql.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlConfig;

/**
 * <p>
 * 动态sql Mapper 接口
 * </p>
 *
 * @author kuangzm
 * @since Jan 18, 2021 8:24:06 PM
 */
public interface DynamicSqlConfigMapper extends BaseMapper<DynamicSqlConfig> {
	List<Map<String, Object>> getSqlData(String language);

	Integer getSqlDataCount(String countStr);
}