package com.midea.cloud.srm.model.api.interfaceconfig.dto;

import java.util.List;

import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceParam;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceResult;

import lombok.Data;

@Data
public class InterfaceAllDTO {
	
	private InterfaceConfigDTO form; //配置信息
	
	private List<InterfaceColumn> columns; //字段信息
	
	private List<InterfaceColumn> sqlParams;//SQL参数信息
	
	private List<InterfaceColumn> params;//参数信息
	
	private List<InterfaceColumn> childColumns; //子层字段信息
	
	private List<InterfaceColumn> childParams; //子层返回信息
	
}
