package com.midea.cloud.srm.base.dynamicsql.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlDTO;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlParamDTO;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlAttr;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlConfig;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;

/**
*  <pre>
 *  动态sql 服务类
 * </pre>
*
* kuangzm
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 18, 2021 8:24:06 PM
 *  修改内容:
 * </pre>
*/
public interface DynamicSqlConfigService extends IService<DynamicSqlConfig> {

	public DynamicSqlDTO saveOrUpdate(DynamicSqlDTO dynamicSqlDTO);
	
	public List<Map<String, Object>> listSql(String sql, String dataConfig) throws SQLException;
	
	public Map<String, Object> listByFormCondition(DynamicSqlParamDTO param);
	
	public Integer countPopupWindowInfo(DynamicSqlConfig config,List<DynamicSqlAttr> queryParam);
}
