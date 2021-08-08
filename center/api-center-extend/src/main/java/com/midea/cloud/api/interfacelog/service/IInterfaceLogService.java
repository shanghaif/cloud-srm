package com.midea.cloud.api.interfacelog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLog;

/**
*  <pre>
 *  接口日志表 服务类
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 10:58:43
 *  修改内容:
 * </pre>
*/
public interface IInterfaceLogService extends IService<InterfaceLog> {
	InterfaceLogDTO createInterfaceLog(InterfaceLogDTO interfaceLog);

	void asyncAddLog(InterfaceLog interfaceLog);
	
	void updateInterfaceLog(InterfaceLogDTO interfaceLog);
}
