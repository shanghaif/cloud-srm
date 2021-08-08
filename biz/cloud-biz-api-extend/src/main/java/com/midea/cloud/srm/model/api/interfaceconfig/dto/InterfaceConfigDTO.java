package com.midea.cloud.srm.model.api.interfaceconfig.dto;

import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;

import lombok.Data;

@Data
public class InterfaceConfigDTO extends InterfaceConfig {
	
	private String systemName;//系统名称
	
	private String protocol;//协议
	
	private String type;//传输类型
	
}
