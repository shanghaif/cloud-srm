package com.midea.cloud.api.config;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.api.inter.service.impl.WebserviceGenerator;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceParamService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;

@Configuration
public class WebserviceConfig {

	@Autowired
	private IInterfaceConfigService iInterfaceConfigService;

	@Autowired
	private IInterfaceColumnService iInterfaceColumnService;

	@Autowired
	private IInterfaceParamService iInterfaceParamService;

	@Autowired
	private ISystemConfigService iSystemConfigService;

	@Bean
	public BaseResult initWebservice(@Qualifier(Bus.DEFAULT_BUS_ID) SpringBus springBus) throws Exception {
		

			WebserviceGenerator generator = new WebserviceGenerator();
			QueryWrapper qw = new QueryWrapper();
			qw.eq("PROTOCOL", "WEBSERVICE");
			qw.eq("TYPE", "RECEIVE");
			List<SystemConfig> systemConfigs = iSystemConfigService.list(qw);
			if (null != systemConfigs && !systemConfigs.isEmpty()) {
				qw = new QueryWrapper();
				qw.in("SYSTEM_ID", systemConfigs.stream().map(SystemConfig::getSystemId).collect(Collectors.toList()));
				List<InterfaceConfig> configs = iInterfaceConfigService.list(qw);
				for (InterfaceConfig config : configs) {
					this.initWebservice(config, generator);
				}
			}
			return BaseResult.buildSuccess();
		
	}

	private void initWebservice(InterfaceConfig config, WebserviceGenerator generator) throws Exception {
		try {
			QueryWrapper qw = new QueryWrapper();
			qw.eq("INTERFACE_ID", config.getInterfaceId());
			qw.eq("TYPE", "OUT");
			List<InterfaceColumn> columns = iInterfaceColumnService.list(qw);
			qw = new QueryWrapper();
			qw.eq("INTERFACE_ID", config.getInterfaceId());
			qw.eq("TYPE", "IN");
			List<InterfaceColumn> params = iInterfaceColumnService.list(qw);
		generator.initWebservice(config, columns, params);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("webservice接口发布一次："+e.getMessage());
			throw e;
		}
	}
}
