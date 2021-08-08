package com.midea.cloud.srm.model.base.dynamicsql.dto;

import java.util.List;

import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlAttr;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlConfig;

import lombok.Data;
@Data
public class DynamicSqlDTO extends DynamicSqlConfig {
	
	List<DynamicSqlAttr> attrs = null;
}
