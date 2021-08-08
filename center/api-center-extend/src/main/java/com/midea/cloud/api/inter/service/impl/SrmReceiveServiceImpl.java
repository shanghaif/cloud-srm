package com.midea.cloud.api.inter.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.api.inter.service.IReceiveService;
import com.midea.cloud.api.result.service.IReceiveChangeFormat;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;

@Service
public class SrmReceiveServiceImpl implements IReceiveService {

	@Override
	public BaseResult receive(InterfaceConfig inter, String content) { 
		BaseResult result = null;
		if (StringUtil.isNotEmpty(inter.getReturnClass())) {
			IReceiveChangeFormat changeFormat = (IReceiveChangeFormat) SpringContextHolder.getBean(inter.getReturnClass());
			result = changeFormat.changeFormatResult(inter,content);
		} else {
			if (inter.getDataType().equals("MAP")) {
				Map resultMap = JSON.parseObject(content, Map.class);
				result = BaseResult.build(ResultCode.SUCCESS);
				result.setData(resultMap);
			} else if (inter.getDataType().equals("LIST")) {
				List<Map> resultMap = JSON.parseArray(content, Map.class);
				result = BaseResult.build(ResultCode.SUCCESS);
				result.setData(resultMap);
			}
			
//			if (resultMap.containsKey("flag") && resultMap.get("flag").equals("Y")) {
				
//			} else {
//				result = BaseResult.build(ResultCode.UNKNOWN_ERROR,"条件不符合");
//			}
		}
		return result;
	}
}
