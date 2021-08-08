package com.midea.cloud.srm.model.base.dynamicsql.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
@Data
public class DynamicSqlParamDTO implements Serializable {
	private String sqlKey;
	private Integer pageNum;
	private Integer pageSize;
	private Map<String,Object> queryParam;
}
