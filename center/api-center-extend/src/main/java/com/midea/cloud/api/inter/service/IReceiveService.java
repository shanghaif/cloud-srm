package com.midea.cloud.api.inter.service;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;

/**
*  <pre>
 *  接口参数 服务类
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 * </pre>
*/
public interface IReceiveService {
	BaseResult receive(InterfaceConfig inter,String content);//接收
	
}
