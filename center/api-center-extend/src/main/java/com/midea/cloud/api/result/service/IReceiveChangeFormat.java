package com.midea.cloud.api.result.service;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;

public interface IReceiveChangeFormat {
	public BaseResult changeFormatResult(InterfaceConfig inter,String json);
}
