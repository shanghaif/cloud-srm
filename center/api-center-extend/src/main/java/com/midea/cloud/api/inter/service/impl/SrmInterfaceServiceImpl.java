package com.midea.cloud.api.inter.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.api.inter.service.IInterfaceService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.api.result.service.IInterfaceResult;
import com.midea.cloud.common.enums.api.ResultStatus;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *  接口参数 服务实现类
 * </pre>
 *
 * @author kuangzm@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 *          </pre>
 */
@Service
@Slf4j
public class SrmInterfaceServiceImpl implements IInterfaceService {

	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private IInterfaceConfigService iInterfaceConfigService;
	
	@Autowired
    private IInterfaceLogService iInterfaceLogService;
	
	private boolean flag ;
	
	private static String host = "http://10.16.87.99:8805";

	@Override
	public Object send(String systemUrl, String url, Object content,HttpMethod method,String type) {
		/**
		 * 1. 编写发送请求的方法
		 * 2. 设置访问权限信息
		 */
		String path = systemUrl + url;
		HttpHeaders header = new HttpHeaders();
		
//		header.set("appId", "paas");
//		header.set("timestamp", "123456987");
//		header.set("sign", "99d04e92b87719d06a6d2cb997cca0f3");
		//FIXME 获取redis token 信息
		header.set("Authorization", "Bearer 9d788071-911f-41d3-bd63-3cbd982ac4c1");
		log.error("接口调用参数："+(null == content ?"":content.toString()));
		log.error("接口调用地址："+path);
		ResponseEntity<String> result = null;
		if (content instanceof MultiValueMap) {
			header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>((MultiValueMap)content, header);
			result = restTemplate.exchange(path,method, httpEntity, String.class);
		} else {
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<String>(content==null?"":content.toString(), header);
			result = restTemplate.exchange(path,method, httpEntity, String.class);
		}
		log.error("接口调用返回："+result.getBody());
		return result;
	}
	
	@Override
	public Object sendResult(String interfaceCode, Object result,InterfaceLogDTO dto) {
		// ----------------------------------执行必要的业务操作-------------------------------------------
		// 1.通过接口获取接口信息
		QueryWrapper<InterfaceConfig> wrapper = new QueryWrapper<>();
		wrapper.eq("INTERFACE_CODE", interfaceCode);
		// 获取接口配置信息
		InterfaceConfig inter = this.iInterfaceConfigService.getOne(wrapper);
		if (StringUtils.isNotEmpty(inter.getReturnClass())) {
			// 返回结果处理类
			IInterfaceResult interResult = (IInterfaceResult) SpringContextHolder.getBean(inter.getReturnClass());
			interResult.interfaceResult(result,dto.getServiceInfo());
		}
		//----------------------------------------END----------------------------------------

		//-----------------------------------------执行日志记录-----------------------------------------
		/**
		 * 下面步骤必写, 日志记录是否成功
		 * 成功则记录状态和完成时间
		 * 失败则记录状态和错误信息
		 */
		// 解析返回内容
		BaseResult<String> baseResult = JSON.parseObject(result.toString(), BaseResult.class);
		if (baseResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
			dto.setStatus(ResultStatus.SUCCESS.name()); // 状态
			dto.setFinishDate(new Date()); // 完成时间
		} else {
			dto.setStatus(ResultStatus.FAIL.name()); // 状态
			dto.setErrorInfo(baseResult.getErrorMsgTrace()); // 错误信息
		}
		//记录日志
   	 	iInterfaceLogService.createInterfaceLog(dto);
		//-----------------------------------------END-----------------------------------------

   	 	return result;
	}
	
	
	/**
	 * 获取对方TOKEN信息
	 * @param userName
	 */
	private void getTokenHtpp(String userName) {
		String url = host+"/oauth/token?grant_type=password&client_id=cloud&client_secret=cloud_20200213&scope=app&username="+userName+"|JWT&password="+userName;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>("", header);
		ResponseEntity<String> result = null;
		result = restTemplate.exchange(url,HttpMethod.POST, httpEntity, String.class);
		this.setToken(result.toString());
	}
	
	private String setToken(String result) {
		BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
		if (baseResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
			Map<String,Object> map = (Map<String, Object>) baseResult.getData();
			return (String) map.get("value");
		}
		return null;
	}

	
	
	public static void main(String[] args) {
		
		String path ="http://10.16.87.99/cloud-srm/api-base/notice/notice/get?noticeId=8515307723358208";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
//		header.set("appId", "paas");
//		header.set("timestamp", "123456987");
//		header.set("sign", "99d04e92b87719d06a6d2cb997cca0f3");
		header.set("Authorization", "Bearer 02ca71ba-4041-46c3-ba9f-76bb266b65fc");
		HttpEntity<String> httpEntity = new HttpEntity<String>("", header);
//		String result = new RestTemplate().postForObject(path, httpEntity, String.class);
		ResponseEntity<String> result = new RestTemplate().exchange(path, HttpMethod.GET, httpEntity, String.class);
		System.out.println(result);
	}

}
