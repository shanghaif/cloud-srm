package com.midea.cloud.api.inter.service.impl;

import org.springframework.stereotype.Service;

import com.midea.cloud.api.inter.service.IReceiveService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;

@Service
public class UserBindServiceImpl implements IReceiveService {

	@Override
	public BaseResult receive(InterfaceConfig inter, String content) {
		//FIXME  插入微信用户记录
		System.out.println("插入微信用户信息");
		BaseResult baseResult = BaseResult.buildSuccess();
		baseResult.setData(content);
		return baseResult;
	}

}
