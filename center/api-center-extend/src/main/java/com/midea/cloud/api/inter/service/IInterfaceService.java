package com.midea.cloud.api.inter.service;

import org.springframework.http.HttpMethod;

import com.midea.cloud.common.result.BaseResult;
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
public interface IInterfaceService {

	/**
	 * 发送请求方法
	 * @param system 系统配置
	 * @param url    发送URL
	 * @param content 发送的内容(JSON)
	 * @return
	 */
	Object send(String systemUrl, String url, Object content,HttpMethod method,String type);//发送

	/**
	 *
	 * @param interfaceCode
	 * @param result
	 * @param dto
	 * @return
	 */
	Object sendResult(String interfaceCode,Object result,InterfaceLogDTO dto);
	
}
