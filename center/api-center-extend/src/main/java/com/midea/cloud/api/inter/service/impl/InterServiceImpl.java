package com.midea.cloud.api.inter.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.api.inter.common.CommonUtil;
import com.midea.cloud.api.inter.common.WebServiceUtil;
import com.midea.cloud.api.inter.controller.ThreadHttpSend;
import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.api.inter.service.IInterfaceService;
import com.midea.cloud.api.inter.service.IReceiveService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.enums.api.ResultStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceAllDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InterServiceImpl implements IInterService {
	
	@Autowired
	private IInterfaceConfigService iInterfaceConfigService;
	@Autowired
	private IInterfaceColumnService iInterfaceColumnService;
	@Autowired
	private ISystemConfigService iSystemConfigService;
	@Autowired
	private IInterfaceLogService iInterfaceLogService;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	protected SqlSessionTemplate stJrsm;
	
	@Autowired
	HttpServletRequest request;
	
	/**
	 * 查询总数量时长 超过 maxTotalTime 秒  进去 导出中心 
	 */
	private static int maxTotalTime = 3;

	/**
	 * 导出数量超过 maxTotalNum 进入导出中心 
	 */
	private static int maxTotalNum = 20000;
	
	/**
	 * 最大任务数
	 */
	private static int taxPoolSize = 100;
	/**
	 * 最少线程数量
	 */
	private static int corePoolSize = 3;
	/**
	 * 最大线程数量
	 */
	private static int maximumPoolSize = 5;
	
	
	private  ThreadPoolExecutor executor;

	public InterServiceImpl() {
		super();
		executor = new ThreadPoolExecutor(corePoolSize , maximumPoolSize,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(taxPoolSize));
	}
	
	
	@Override
	public String sendForm(String interfaceCode,HttpServletResponse response) {
		
		Assert.notNull(interfaceCode, "interfaceCode不能为空");
		// 1.通过接口获取接口信息
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_CODE", interfaceCode);
		InterfaceConfig inter = this.iInterfaceConfigService.getOne(wrapper);
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		
    	wrapper = new QueryWrapper();
    	wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
    	wrapper.eq("TYPE", "IN");
    	List<InterfaceColumn> params = iInterfaceColumnService.list(wrapper);
    	
    	
		Object paramsMap = this.getHttpForm(params);
		return this.send(interfaceCode, paramsMap, response);
	}
	

	@Override
	public String send(String interfaceCode, Object paramsMap,HttpServletResponse response) {
		
		Assert.notNull(interfaceCode, "interfaceCode不能为空");
		// 1.通过接口获取接口信息
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_CODE", interfaceCode);
		wrapper.inSql("SYSTEM_ID", "select SYSTEM_ID from scc_api_system_config where PROTOCOL='HTTP' and type='SEND' ");
		InterfaceConfig inter = this.iInterfaceConfigService.getOne(wrapper);
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		// 获取发送内容
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "OUT");
    	List<InterfaceColumn> columns = iInterfaceColumnService.list(wrapper);
    	wrapper = new QueryWrapper();
    	wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
    	wrapper.eq("TYPE", "IN");
    	List<InterfaceColumn> params = iInterfaceColumnService.list(wrapper);
    	
    	wrapper = new QueryWrapper();
    	wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
    	wrapper.eq("TYPE", "SQL");
    	List<InterfaceColumn> sqlParam = iInterfaceColumnService.list(wrapper);

		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		
		if ("Y".equals(inter.getIfSyn())) {
			log.info("活跃线程数量："+executor.getActiveCount());
			log.info("任务数量数量："+executor.getTaskCount());
			executor.submit(new Runnable() {
                @Override
                public void run() {
                	sendInfo(inter, system, sqlParam, params, columns, paramsMap,response);
                }
            });
			return this.getResults(columns, inter.getOutType());
		} else {
			return sendInfo(inter, system, sqlParam, params, columns, paramsMap,response);
		}
	}
	
	
	private String sendInfo (InterfaceConfig inter,SystemConfig system,List<InterfaceColumn> sqlParam,List<InterfaceColumn> params,List<InterfaceColumn> columns,Object paramsMap,HttpServletResponse response) {
		// 日志
		InterfaceLogDTO dto = new InterfaceLogDTO();
		dto.setServiceName(inter.getInterfaceName());
		dto.setServiceType(system.getProtocol());
		dto.setType(system.getType());
		dto.setUrl(system.getSystemUrl() + inter.getInterfaceUrl());
		dto.setTargetSys(system.getSystemName());

		// 声明发送结果
		Object result = null;
		// 获取发送处理时限类
		IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
		try {
			if (!inter.getSource().equals("JDBC_EXCUTE"))  {
				dto.setServiceInfo(paramsMap.toString());
				if (inter.getSource().equals("JDBC")) {
					
					Object obj = this.getParam(system, inter, sqlParam, new HashMap<String,Object>());
					Object content = getSendContent(inter, this.getParamList(sqlParam, obj.toString(), inter.getDataType()), params);
					if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) { //参数组装
						result = this.getUrl(this.getParamList(params, content.toString(), inter.getDataType()));
					} else if (null != inter.getParamStruct() && inter.getParamStruct().equals("FORM")) {
						result = this.getForm(this.getParamList(params, content.toString(), inter.getDataType()));
					}
					result = this.getHttpResult(result, system, inter, interfaceService);			
					
				} else if (inter.getSource().equals("HTTP")) {
					// 获取发送的内容
					Object param = this.getParam(system, inter, params, paramsMap);
					if (null != param) {
						dto.setServiceInfo(param.toString());
					}
					result = this.getHttpResult(param, system, inter, interfaceService);
				}
				
				dto.setStatus(ResultStatus.SUCCESS.name()); // 成功
				try {
					if (inter.getOutType().equals("MAP")) {
						this.checkResults(result, columns, inter.getOutType());
						dto.setReturnInfo(JSON.toJSONString(result)); // 返回结果
						if (columns.size() > 0) { //有字段才转换 
							if (null != result && !result.toString().isEmpty()) {
								result = this.getReceiveContent(JsonUtil.parseJsonStrToMap(result.toString()), inter, columns,inter.getOutType());
								result = JSON.toJSONString(result);
							}
						}
					} else if (inter.getOutType().equals("LIST")) {
						if (columns.size() > 0) { //有字段才转换 
							if (null != result && !result.toString().isEmpty()) {
								result = this.getReceiveContent(JsonUtil.parseJsonStrToList(result.toString()), inter, columns,inter.getOutType());
								result = JSONArray.toJSONString(result);
							}
						}
					} else if (inter.getOutType().equals("FILE")) {
						ResponseEntity<byte []> resEntity = ((ResponseEntity)result);
						InputStream is = new ByteArrayInputStream(resEntity.getBody(),0,resEntity.getBody().length);
						String contentType = "";
						if (resEntity.getHeaders().containsKey("Content-Type")) {
							contentType = resEntity.getHeaders().get("Content-Type").get(0);
						}
						String fileName = "";
						if (resEntity.getHeaders().containsKey("FileName")) {
							fileName = resEntity.getHeaders().get("FileName").get(0);
						}
						this.wirte(contentType, response, is, fileName);
						result = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					dto.setStatus(ResultStatus.FAIL.name()); //失败
					dto.setErrorInfo(JSON.toJSONString(result));
				}
				
				if (null != inter.getReturnClass() && !inter.getReturnClass().isEmpty()) {
					IReceiveService iReceiveService = (IReceiveService) SpringContextHolder.getBean(inter.getReturnClass());
					return iReceiveService.receive(inter, result.toString()).getData().toString();
				}
				
			} else { //定时执行
				getSendContent(inter, params, columns);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// 发送失败处理
			result = e.getMessage();
			dto.setStatus(ResultStatus.FAIL.name());
			dto.setErrorInfo(e.getMessage());
		}finally {
			// 记录日志信息
			if("ASYNC".equals(inter.getLogModel())) { // 异步
				iInterfaceLogService.asyncAddLog(dto);
			} else {
				iInterfaceLogService.createInterfaceLog(dto);
			}
		}
		return result.toString();
	}

	
	
	/**
	 * 获取接口返回
	 * @param param
	 * @param system
	 * @param inter
	 * @param interfaceService
	 * @return
	 */
	private Object getHttpResult(Object param,SystemConfig system,InterfaceConfig inter,IInterfaceService interfaceService) {
		Object result = null;
		if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) {
			if ((system.getSystemUrl()+inter.getInterfaceUrl()).indexOf("?")!= -1) {
				inter.setInterfaceUrl(inter.getInterfaceUrl()+"&"+param);
			} else {
				inter.setInterfaceUrl(inter.getInterfaceUrl()+"?"+param);
			}
			param = null;
		}
		if ("FILE".equals(inter.getOutType())) {
			result = interfaceService.send(system.getSystemUrl(), inter.getInterfaceUrl(), param,HttpMethod.GET,"FILE");
		} else {
			if (null != inter.getMethod() && inter.getMethod().equals("GET")) { //GET请求
				result = interfaceService.send(system.getSystemUrl(), inter.getInterfaceUrl(), param,HttpMethod.GET,"String");
				result = ((ResponseEntity)result).getBody();
			} else {
				result = interfaceService.send(system.getSystemUrl(), inter.getInterfaceUrl(), param,HttpMethod.POST,"String");
				result = ((ResponseEntity)result).getBody();
			}
		}
		
		return result;
	}
	
	@Override
	public Object getContent(InterfaceConfig inter, Map<String, Object> paramsMap) {
		// 1.通过接口获取接口信息
		QueryWrapper wrapper = new QueryWrapper();
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		// 获取发送内容
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "OUT");
    	List<InterfaceColumn> columns = iInterfaceColumnService.list(wrapper);
    	wrapper = new QueryWrapper();
    	wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
    	wrapper.eq("TYPE", "IN");
    	List<InterfaceColumn> params = iInterfaceColumnService.list(wrapper);
		
		if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) {
			HttpServletRequest request = HttpServletHolder.getRequest();
			for (InterfaceColumn dto : params) {
				paramsMap.put(dto.getColumnName(), request.getParameter(dto.getColumnName()));
			}
		}

		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		// 日志
		InterfaceLogDTO dto = new InterfaceLogDTO();
		dto.setServiceName(inter.getInterfaceName());
		dto.setServiceType(system.getProtocol());
		dto.setType(system.getType());
		dto.setUrl(system.getSystemUrl() + inter.getInterfaceUrl());
		dto.setTargetSys(system.getSystemName());
		Object result = null;

		return  getSendContent(inter, params, columns); 
	}
	
	@Override
	public Object getContentWebservice(InterfaceConfig inter, Map<String, Object> paramsMap) {
		// 1.通过接口获取接口信息
		QueryWrapper wrapper = new QueryWrapper();
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		// 获取发送内容
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "OUT");
    	List<InterfaceColumn> columns = iInterfaceColumnService.list(wrapper);
    	wrapper = new QueryWrapper();
    	wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
    	wrapper.eq("TYPE", "IN");
    	List<InterfaceColumn> params = iInterfaceColumnService.list(wrapper);
		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		// 日志
		InterfaceLogDTO dto = new InterfaceLogDTO();
		dto.setServiceName(inter.getInterfaceName());
		dto.setServiceType(system.getProtocol());
		dto.setType(system.getType());
		dto.setUrl(system.getSystemUrl() + inter.getInterfaceUrl());
		dto.setTargetSys(system.getSystemName());
		Object result = null;
		return  getSendContent(inter, params, columns); 
	}

	
	@Override
	public String apiInfo(String method) {
		// 接受JSON
		String content = "";
		Assert.notNull(method, "interfaceCode接口参数不存在");
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_CODE", method);
		wrapper.inSql("SYSTEM_ID", "select SYSTEM_ID from scc_api_system_config where PROTOCOL='HTTP' and type='RECEIVE' ");
		InterfaceConfig inter = this.iInterfaceConfigService.getOne(wrapper);
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		
		//入参
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "IN");
		wrapper.orderByAsc("PARENT_ID,LINE_NUM");
		List<InterfaceColumn> params = this.iInterfaceColumnService.list(wrapper);
		
		// 获取发送内容
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "OUT");
		wrapper.orderByAsc("PARENT_ID,LINE_NUM");
		List<InterfaceColumn> columns = this.iInterfaceColumnService.list(wrapper);
		
		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		
		//获取参数
		try {
			final String json = getParam(system, inter, params, null).toString();
			if ("Y".equals(inter.getIfSyn())) { //异步
				log.info("活跃线程数量："+executor.getActiveCount());
				log.info("任务数量数量："+executor.getTaskCount());
				executor.submit(new Runnable() {
	                @Override
	                public void run() {
	                	receiveInfo(inter,params,columns,system,json);
	                }
	            });
				return this.getResults(columns, inter.getOutType());
			} else { //同步
				return receiveInfo(inter,params,columns,system,json).toString();
			}
		} catch (Exception e) {
			throw new BaseException("获取参数异常："+e.getMessage());
		}
	}
	
	private String receiveInfo(InterfaceConfig inter,List<InterfaceColumn> params,List<InterfaceColumn> columns,SystemConfig system,String param) {
		
		// 日志
		InterfaceLogDTO dto = new InterfaceLogDTO();
		//日志信息
		dto.setServiceName(inter.getInterfaceName());
		dto.setServiceType(system.getProtocol());
		dto.setType(system.getType());
		dto.setUrl(system.getSystemUrl() + inter.getInterfaceCode());
		dto.setTargetSys(system.getSystemName());
		String result = null;
		try {
			
			dto.setServiceInfo(param);
			//当存在额外日志接口时，异步调用
			if (StringUtils.isNotBlank(inter.getLogHttp())) {
				this.extLog(param, inter.getLogHttp());
			}
			//增加判断  实现方式 
			if (inter.getSource().equals("HTTP_RE")) {
				dto.setServiceInfo(param);
				//调用SRM http
				try {
					result = this.send(inter.getDataSource(), param);
				} catch (Exception e) {
					throw new BaseException(inter.getDataSource()+" 调用异常:"+e.getMessage());
				}
				dto.setStatus("SUCCESS");
				dto.setFinishDate(new Date());
				dto.setReturnInfo(result);
			} else if (inter.getSource().equals("JDBC_RE")) {
				result = (String) getSendContent(inter, getParamList(params, param, inter.getDataType()), columns); 
				dto.setReturnInfo(result);
				dto.setStatus("SUCCESS");
				dto.setFinishDate(new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatus("FAIL");
			dto.setErrorInfo(e.getMessage());
		}
		//记录日志
		if("ASYNC".equals(inter.getLogModel())) { // 异步
			iInterfaceLogService.asyncAddLog(dto);
		} else {
			iInterfaceLogService.createInterfaceLog(dto);
		}
	 	return result;
	}
	
	@Override
	public BaseResult apiInfoWebservice(String method,Object obj) {
		// 接受JSON
		String content = "";
		// 日志
		InterfaceLogDTO dto = new InterfaceLogDTO();
		
		Assert.notNull(method, "interfaceCode接口参数不存在");
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_CODE", method);
		wrapper.inSql("SYSTEM_ID", "select SYSTEM_ID from scc_api_system_config where PROTOCOL='WEBSERVICE' and type='RECEIVE' ");
		InterfaceConfig inter = this.iInterfaceConfigService.getOne(wrapper);
		
		Assert.notNull(inter, "interfaceCode接口配置不存在");
		
		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		//日志信息
		dto.setServiceName(inter.getInterfaceName());
		dto.setServiceType(system.getProtocol());
		dto.setType(system.getType());
		dto.setUrl(system.getSystemUrl() + inter.getInterfaceCode());
		dto.setTargetSys(system.getSystemName());
		BaseResult baseResult = BaseResult.build(ResultCode.SUCCESS);
		
		//入参
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "IN");
		wrapper.orderByAsc("PARENT_ID,LINE_NUM");
		List<InterfaceColumn> params = this.iInterfaceColumnService.list(wrapper);
		
		// 获取发送内容
		wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_ID", inter.getInterfaceId());
		wrapper.eq("TYPE", "OUT");
		wrapper.orderByAsc("PARENT_ID,LINE_NUM");
		List<InterfaceColumn> columns = this.iInterfaceColumnService.list(wrapper);
		try {
			//获取参数
			Object paramObj = getParam(system, inter, params, obj);
			//当存在额外日志接口时，异步调用
			if (StringUtils.isNotBlank(inter.getLogHttp())) {
				this.extLog(obj.toString(), inter.getLogHttp());
			}
			//增加判断  实现方式 
			if (inter.getSource().equals("HTTP_RE")) {
				if (null != paramObj) {
					dto.setServiceInfo(paramObj.toString());
					//调用SRM http
					String result = this.send(inter.getDataSource(), paramObj.toString());
					baseResult = com.alibaba.fastjson.JSONObject.parseObject(result, BaseResult.class);
					if (null != baseResult.getData()) {
						if (baseResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
							dto.setStatus("SUCCESS");
							dto.setFinishDate(new Date());
							dto.setReturnInfo(result);
						} else {
							dto.setStatus("FAIL");
							dto.setErrorInfo(baseResult.getMessage());
						}
					}else {
						dto.setStatus("SUCCESS");
						dto.setFinishDate(new Date());
						dto.setReturnInfo(JSON.toJSONString(baseResult));
					}
				}else {
					dto.setStatus("FAIL");
					dto.setErrorInfo("没有接收到json数据");
					baseResult =  BaseResult.build(ResultCode.EXPORT_EXCEPTIONS,"没有接收到json数据");
				}
			} else if (inter.getSource().equals("JDBC_RE")) {
				String param = null;
				if (inter.getDataType().equals("LIST")) {
					param = JSONArray.toJSONString(paramObj);
				} else if (inter.getDataType().equals("MAP")) {
					param = JSON.toJSONString(paramObj);
				} else {
					throw new BaseException("请选择入参类型");
				}
				Object jsonStr = getSendContent(inter, getParamList(params, param, inter.getDataType()), columns); 
				dto.setReturnInfo(jsonStr.toString());
				if (jsonStr.toString().length() > 0) {
					if (inter.getOutType().equals("LIST")) {
						baseResult.setData(JsonUtil.parseJsonStrToList(jsonStr.toString()));
					} else if (inter.getOutType().equals("MAP")) {
						baseResult.setData(JsonUtil.parseJsonStrToMap(jsonStr.toString()));
					}
				}
				dto.setStatus("SUCCESS");
				dto.setFinishDate(new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatus("FAIL");
			dto.setErrorInfo(e.getMessage());
			baseResult =  BaseResult.build(ResultCode.PARAM_VALID_ERROR,e.getMessage());
		}
		//记录日志
		if("ASYNC".equals(inter.getLogModel())) { // 异步
			iInterfaceLogService.asyncAddLog(dto);
		} else {
			iInterfaceLogService.createInterfaceLog(dto);
		}
		return baseResult;
	}
	
	public Map<String,Object> getParamMap(Object obj) throws IllegalArgumentException, IllegalAccessException  {
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		}
		return map;
	}
	
	private String send(String url,String content) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("appId", "paas");
		header.set("timestamp", "123456");
		header.set("sign", "c0281e14f6fca4bb6bca64bd2d2694c7");
		HttpEntity<String> httpEntity = new HttpEntity<String>(content, header);
		log.error("SRM内部调用传入："+content);
		log.error("SRM内部调用地址："+url);
		String result = restTemplate.postForObject(url, httpEntity, String.class);
		log.error("SRM内部调用返回："+result);
		return result;
	}
	
	/**
	 * 异步调用额外记录日志接口
	 * 
	 * @param json
	 * @param url
	 * @return
	 */
	private void extLog(String json,String url) {
		try {
			Thread t = new Thread(new ThreadHttpSend(url,json,restTemplate));
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//--------------------------------检查必填 start--------------------------------------------//
	public void checkParams(Object data, List<InterfaceColumn> params,String type) {
		String json = null;
		if (null != params && params.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = this.getResultsByParentId(params, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = (Map)data;
				temp = this.checkParams(map, tempColumnList,params,null);
				json = JSON.toJSONString(temp);
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = (List)data;
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				if (null == list || list.isEmpty()) {
					temp = this.checkParams(new HashMap(), tempColumnList,params,null);
				} else {
					for (Map<String,Object> map : list) {
						temp = this.checkParams(map, tempColumnList,params,null);
						tempList.add(temp);
					}
				}
			}
		}
	}
	
	
	private Map<String,Object> checkParams(Map<String,Object> data,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,Map<String,Object> parentMap) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			Object value = data.get(column.getColumnName());
			if (column.getColumnType().equals("LIST")) { //list
				List list = (List) data.get(column.getColumnName());
				if (null != list && list.size() > 0) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = null;
					if (list.get(0) instanceof Map) {
						List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listMap = (List<Map<String,Object>>) data.get(column.getColumnName());
						for (Map<String,Object> map : listMap) {
							tempMap = this.checkParams(map, tempColumnList, allColumns,result);
							tempList.add(tempMap);
						}
						result.put(column.getColumnName(), tempList);
					}
					
				}
			} else if (column.getColumnType().equals("Map")) { //Map
				Map<String,Object> map = (Map<String, Object>) data.get(column.getColumnName());
				if (null != map) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = this.checkParams(map, tempColumnList, allColumns,result);
					result.put(column.getColumnName(), tempMap);
				}
				
			} else {
				if (null != column.getIfRequirye() && "Y".equals(column.getIfRequirye())) {
					if (data.containsKey(column.getColumnName()) && null != data.get(column.getColumnName())) {
						
					}else {
						throw new BaseException("缺省入参值："+column.getColumnDesc());
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据父ID获取字段
	 */
	private List<InterfaceColumn> getResultsByParentId(List<InterfaceColumn> columns, Long parentId) {
		List<InterfaceColumn> temp = new ArrayList<InterfaceColumn>();
		if (null != columns && columns.size() > 0) {
			for (InterfaceColumn column : columns) {
				if (null != parentId) {
					if (null != column.getParentId()) {
						if (parentId.longValue() == column.getParentId().longValue()) {
							temp.add(column);
						}
					}
				} else {
					if (null == column.getParentId()) {
						temp.add(column);
					}
				}
			}
		}
		return temp;
	}
	//--------------------------------检查必填      end--------------------------------------------//
	
	
	
	//--------------------------------判断是否正确     start--------------------------------------------//
	public void checkResults(Object data, List<InterfaceColumn> results,String type) {
		String json = null;
		if (null != results && results.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = this.getResultsByParentId(results, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = JsonUtil.parseJsonStrToMap(data.toString());
				temp = this.checkResults(map, tempColumnList,results,null);
				json = JSON.toJSONString(temp);
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = JsonUtil.parseJsonStrToList(data.toString());
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : list) {
					temp = this.checkResults(map, tempColumnList,results,null);
					tempList.add(temp);
				}
			}
		}
	}
	
	
	private Map<String,Object> checkResults(Map<String,Object> data,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,Map<String,Object> parentMap) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			Object value = data.get(column.getColumnName());
			if (column.getColumnType().equals("LIST")) { //list
				List list = (List) data.get(column.getColumnName());
				if (null != list && list.size() > 0) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = null;
					if (list.get(0) instanceof Map) {
						List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listMap = (List<Map<String,Object>>) data.get(column.getColumnName());
						for (Map<String,Object> map : listMap) {
							tempMap = this.checkResults(map, tempColumnList, allColumns,result);
							tempList.add(tempMap);
						}
						result.put(column.getColumnName(), tempList);
					}
					
				}
			} else if (column.getColumnType().equals("Map")) { //Map
				Map<String,Object> map = (Map<String, Object>) data.get(column.getColumnName());
				if (null != map) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = this.checkResults(map, tempColumnList, allColumns,result);
					result.put(column.getColumnName(), tempMap);
				}
				
			} else {
				if (null != column.getCorrectValue() && !column.getCorrectValue().isEmpty()) {
					if (data.containsKey(column.getColumnName()) && null != data.get(column.getColumnName())) {
						if (!column.getCorrectValue().equals(data.get(column.getColumnName()))) {
							throw new BaseException("返回异常");
						}
					}else {
						throw new BaseException("返回异常");
					}
				}
			}
		}
		return result;
	}
	//--------------------------------判断是否正确     end--------------------------------------------//
	
	//--------------------------------参数获取  start--------------------------------------------//
	private Object getParam(SystemConfig system,InterfaceConfig inter,List<InterfaceColumn> params,Object obj) throws UnsupportedEncodingException, IOException {
		// 1.获取参数data
		Object result = null;
		Object checkData = null;
		String temp = null;
		if (inter.getSource().equals("JDBC")) {// JDBC 发生
			result = this.getReceiveContent(obj, inter, params,inter.getDataType());
			checkData = result;
			result = JSON.toJSONString(result);
		} else if (inter.getSource().equals("JDBC_RE")) { // JDBC_RE 
			if ("WEBSERVICE".equals(system.getProtocol())) {
				if("MAP".equals(inter.getDataType())) {
					try {
						obj =WebServiceUtil.getObjectToMap(obj,new HashMap<String,Object>());
					} catch (Exception e) {
						e.printStackTrace();
					}
					result = this.getReceiveContent(obj, inter, params,inter.getDataType());
					checkData = result;
				} else if ("LIST".equals(inter.getDataType())) {
					List<Map<String,Object>> l = new ArrayList();
					for (int len=0 ;len<Array.getLength(obj);len++) {
						try {
							l.add(WebServiceUtil.getObjectToMap(Array.get(obj, len), new HashMap<String,Object>()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					result = this.getReceiveContent(l, inter, params,inter.getDataType());
					checkData = result;
				}
			} else {
				return this.getParams(inter, params);
			}
		} else if (inter.getSource().equals("HTTP")) { // http 发生  组装入参
			if(null != params && params.size() > 0) {
				result = this.getReceiveContent(obj, inter, params,inter.getDataType());
				if ("URL".equals(inter.getParamStruct())) {
					checkData = result;
					result = this.getUrl(this.getParamList(params, result, inter.getDataType()));
				} else if ("FORM".equals(inter.getParamStruct())) {
					checkData = result;
					result = this.getForm(this.getParamList(params, result, inter.getDataType()));
				} else {
					if("MAP".equals(inter.getDataType())) {
						checkData = result;
						result = JSON.toJSONString(result);
					} else if ("LIST".equals(inter.getDataType())) {
						checkData = result;
						result = JSONArray.toJSONString(result);
					}
				}
			}
		} else if (inter.getSource().equals("HTTP_RE")) { // HTTP_RE http接收
			if ("WEBSERVICE".equals(system.getProtocol())) {
				if("MAP".equals(inter.getDataType())) {
					try {
						obj =WebServiceUtil.getObjectToMap(obj,new HashMap<String,Object>());
					} catch (Exception e) {
						e.printStackTrace();
					}
					result = this.getReceiveContent(obj, inter, params,inter.getDataType());
					checkData = result;
					result = JSON.toJSONString(result);
				} else if ("LIST".equals(inter.getDataType())) {
					List<Map<String,Object>> l = new ArrayList();
					for (int len=0 ;len<Array.getLength(obj);len++) {
						try {
							l.add(WebServiceUtil.getObjectToMap(Array.get(obj, len), new HashMap<String,Object>()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					result = this.getReceiveContent(l, inter, params,inter.getDataType());
					checkData = result;
					result = JSONArray.toJSONString(result);
				}
			} else {
				return this.getParams(inter, params);
			}
			
		} else if (inter.getSource().equals("JDBC_EXCUTE")) {
			
		}
		
		//验证对象data
		this.checkParams(checkData, params, inter.getDataType());
		
		return result;
	}
		
	private String getUrl(List<InterfaceColumn> params) {
		StringBuffer sb = new StringBuffer();
		if (null != params && params.size() > 0) {
			for (InterfaceColumn param : params) {
				if (null != param.getValue()) {
					if (sb.length()>0) {
						sb.append("&");
					}
					if (null != param.getConverName() && !param.getConverName().isEmpty()) {
						sb.append(param.getConverName()).append("=").append(param.getValue());
					} else {
						sb.append(param.getColumnName()).append("=").append(param.getValue());
					}
				}
			}
		}
		return sb.toString();
	}
	
	private MultiValueMap getForm(List<InterfaceColumn> params) {
		MultiValueMap map = new LinkedMultiValueMap();
		if (null != params && params.size() > 0) {
			for (InterfaceColumn param : params) {
				if (null != param.getValue()) {
					if (null != param.getConverName() && !param.getConverName().isEmpty()) {
						map.add(param.getConverName(), param.getValue());
					} else {
						map.add(param.getColumnName(), param.getValue());
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取HTTP JSON
	 * @return
	 */
	public String getHttpJson() {
		StringBuffer json = new StringBuffer();
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(request.getInputStream(), "UTF-8");
			char[] buff = new char[1024];
			int length = 0;
			while ((length = reader.read(buff)) != -1) {
				String x = new String(buff, 0, length);
				json.append(x);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public Map<String,Object> getHttpForm(List<InterfaceColumn> params) {
		Map<String,Object> paramsMap = new HashMap();
    	if (null != params && params.size() > 0) {
    		HttpServletRequest request = HttpServletHolder.getRequest();
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			for (InterfaceColumn dto : params) {
				if (dto.getColumnType().equals("MultipartFile")) {
					try {
						MultipartFile file= multipartRequest.getFile(dto.getColumnName());
					
						ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
				            @Override
				            public String getFilename() {
				                return file.getOriginalFilename();
				            }
				            @Override
				            public long contentLength() {
				                return file.getSize();
				            }
				        };
						paramsMap.put(dto.getColumnName(),fileAsResource);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					
				} else {
					paramsMap.put(dto.getColumnName(), request.getParameter(dto.getColumnName()));
				}
				
			}
    	}
    	return paramsMap;
	}
	
	public Map<String,Object> getHttpGet(List<InterfaceColumn> params) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		HttpServletRequest request = HttpServletHolder.getRequest();
		for (InterfaceColumn dto : params) {
			Object obj = request.getParameter(dto.getColumnName());
			if (null == obj || obj.toString().isEmpty()) {
				if (null != dto.getDefaultValue() && !dto.getDefaultValue().isEmpty()) {
					obj = dto.getDefaultValue();
				}
			}
			if (null != dto.getIfRequirye() && "Y".equals(dto.getIfRequirye()) && ( null == obj || obj.toString().isEmpty() )) {
				throw new BaseException("缺省入参值："+dto.getColumnDesc());
			}
			paramsMap.put(dto.getColumnName(), obj);
		}
		return paramsMap;
	}
	
	public String getParams(InterfaceConfig inter,List<InterfaceColumn> params) throws UnsupportedEncodingException, IOException  {
		String paramStr = null;
		Object paramsMap = new Object();
		if ("URL".equals(inter.getParamStruct())) {
			paramsMap = this.getHttpGet(params);
		} else if ("FORM".equals(inter.getParamStruct())) {
			paramsMap = this.getHttpForm(params);
		} else {
			paramStr = this.getHttpJson();
			if ("Y".equals(inter.getIfNeedParam())) {
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(paramStr);
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(paramStr);
				}
			}
		}
		if ("Y".equals(inter.getIfNeedParam())) {
			Object result = this.getReceiveContent(paramsMap, inter, params,inter.getDataType());
			this.checkParams(result, params, inter.getDataType());
			if("MAP".equals(inter.getDataType())) {
				paramStr = JSON.toJSONString(result);
			} else if ("LIST".equals(inter.getDataType())) {
				paramStr = JSONArray.toJSONString(result);
			}
		}
		return paramStr;
	}
	
	public List<InterfaceColumn> getParamList(List<InterfaceColumn> params,String json,String type) {
		if (null != params && params.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = this.getColumnsByParentId(params, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = JsonUtil.parseJsonStrToMap(json);
				
				temp = this.convertColumn(map, tempColumnList,params,null);
				json = JSON.toJSONString(temp);
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = JsonUtil.parseJsonStrToList(json);
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : list) {
					temp = this.convertColumn(map, tempColumnList,params,null);
					tempList.add(temp);
				}
				json = JSONArray.toJSONString(tempList);
			}
		}
		
		return params;
	}
	
	public List<InterfaceColumn> getParamList(List<InterfaceColumn> params,Object json,String type) {
		if (null != params && params.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = this.getColumnsByParentId(params, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = (Map<String, Object>) json;
				
				temp = this.convertColumn(map, tempColumnList,params,null);
				json = JSON.toJSONString(temp);
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = (List<Map<String, Object>>) json;
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : list) {
					temp = this.convertColumn(map, tempColumnList,params,null);
					tempList.add(temp);
				}
				json = JSONArray.toJSONString(tempList);
			}
		}
		
		return params;
	}
	
	private Map<String,Object> convertColumn(Map<String,Object> data,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,Map<String,Object> parentMap) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			
			Object value = null;
			if (null != column.getConverName() && !column.getConverName().isEmpty()) {
				value = data.get(column.getConverName());
			} else {
				value = data.get(column.getColumnName());
			}
			if (column.getColumnType().equals("LIST")) { //list
				List list = (List) data.get(column.getColumnName());
				if (null != list && list.size() > 0) {
					List<InterfaceColumn> tempColumnList = this.getColumnsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = null;
					if (list.get(0) instanceof Map) {
						List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listMap = (List<Map<String,Object>>) data.get(column.getColumnName());
						for (Map<String,Object> map : listMap) {
							tempMap = this.convertColumn(map, tempColumnList, allColumns,result);
							tempList.add(tempMap);
						}
						if (null != column.getConverName() && !column.getConverName().isEmpty()) {
							result.put(column.getConverName(), tempList);
						} else {
							result.put(column.getColumnName(), tempList);
						}
					}
					
				}
			} else if (column.getColumnType().equals("Map")) { //Map
				Map<String,Object> map = (Map<String, Object>) data.get(column.getColumnName());
				if (null != map) {
					List<InterfaceColumn> tempColumnList = this.getColumnsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = null;
					if (null == tempColumnList || tempColumnList.isEmpty()) {
						tempMap = map;
					} else {
						tempMap = this.convertColumn(map, tempColumnList, allColumns,result);
					}
					if (null != column.getConverName() && !column.getConverName().isEmpty()) {
						result.put(column.getConverName(), tempMap);
					} else {
						result.put(column.getColumnName(), tempMap);
					}
				}
				
			} else {
				CommonUtil.setValue(result, value, column);
			}
		}
		return result;
	}
	
	/**
	 * 根据父ID获取字段
	 */
	private List<InterfaceColumn> getColumnsByParentId(List<InterfaceColumn> columns, Long parentId) {
		List<InterfaceColumn> temp = new ArrayList<InterfaceColumn>();
		if (null != columns && columns.size() > 0) {
			for (InterfaceColumn column : columns) {
				if (null != parentId) {
					if (null != column.getParentId()) {
						if (parentId.longValue() == column.getParentId().longValue()) {
							temp.add(column);
						}
					}
				} else {
					if (null == column.getParentId()) {
						temp.add(column);
					}
				}
			}
		}
		return temp;
	}
	//--------------------------------参数获取        end--------------------------------------------//
	
	//--------------------------------刷新HTTP        start--------------------------------------------//
	/**
	 * 刷新http字段
	 * @param allDTO
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 */
	public List<InterfaceColumn> getHttpColumns(InterfaceAllDTO allDTO) throws Exception {
		InterfaceConfig inter = allDTO.getForm();
		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());
		
		BeanUtils.copyProperties(allDTO.getForm(), inter);
		List<InterfaceColumn> params = allDTO.getParams();
		if (null != allDTO.getChildParams()) {
			params.addAll(allDTO.getChildParams());
		}
		
		Object result = null;
		String type = null;
		if ("HTTP".equals(inter.getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			// 获取发送的内容
			Object param = this.getParam(system, inter, params, paramsMap);
			result = this.getHttpResult(param, system, inter, interfaceService);			
			type = allDTO.getForm().getOutType();
		} else if ("JDBC".equals(inter.getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			Object obj = this.getParam(system, inter, allDTO.getSqlParams(), new HashMap<String,Object>());
			Object content = getSendContent(inter, this.getParamList(allDTO.getSqlParams(), obj.toString(), inter.getDataType()), allDTO.getParams());
			if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) { //参数组装
				result = this.getUrl(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			} else if (null != inter.getParamStruct() && inter.getParamStruct().equals("FORM")) {
				result = this.getForm(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			}
			result = this.getHttpResult(result, system, inter, interfaceService);			
			type = allDTO.getForm().getOutType();
		} else if ("HTTP_RE".equals(inter.getSource())) {
			Object paramObj = null;
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			//获取参数
			Object json = this.getReceiveContent(paramsMap, inter, allDTO.getParams(),inter.getDataType());
			String param = null;
			//参数必填校验
			if ("MAP".equals(inter.getDataType())) {
				paramObj = json;
				param = JSON.toJSONString(json);
			} else if ("LIST".equals(inter.getDataType())) {
				paramObj = json;
				param = JSONArray.toJSONString(json);
			}
			this.checkParams(paramObj, params, inter.getDataType());
			//发送
			result = this.send(inter.getDataSource(), param);
			type = allDTO.getForm().getOutType();
		} else if ("JDBC_RE".equals(inter.getSource())) {
			//获取参数
			Object obj = this.getParam(system, inter, params, new Object());
			String sql = CommonUtil.getSql(inter.getDataSource(), this.getParamList(params, obj.toString(), inter.getDataType()));
			return listSql(sql, inter.getDataConfig());
		}
		List<InterfaceColumn> columns = new ArrayList<InterfaceColumn>();
		try {
			if (type.equals("MAP")) {
				Map<String,Object> map = JsonUtil.parseJsonStrToMap(result.toString());
				CommonUtil.mapToColumn(map, columns,null);
			} else if (type.equals("LIST")) {
				List<Map<String,Object>> list = JsonUtil.parseJsonStrToList(result.toString());
				CommonUtil.mapToColumn(list.get(0), columns,null);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(ResultCode.UNKNOWN_ERROR, "出参类型与返回结果不对应，请修改出参类型", e);
		}
		
		return columns;
	}
	
	/**
	 * 刷新SQL返回
	 * @param allDTO
	 */
	@Override
	public List<InterfaceColumn> getSqlColumns(InterfaceAllDTO allDTO) {
		//获取接口配置
		InterfaceConfig inter = allDTO.getForm();
		// 获取系统配置
		SystemConfig system = iSystemConfigService.getById(inter.getSystemId());

		List<InterfaceColumn> columns = allDTO.getColumns();
		columns.addAll(allDTO.getChildColumns());
		
		List<InterfaceColumn> params = allDTO.getParams();
		params.addAll(allDTO.getChildParams());
		
		Object result = null;
		
		// 获取发送的内容
		Object content = getSendContent(inter, params, columns);
		// 执行发送方法
		IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
		
		result = interfaceService.send(system.getSystemUrl(), inter.getInterfaceUrl(), content.toString(),HttpMethod.POST,"String");
		result = ((ResponseEntity)result).getBody();
		System.out.println(result);
		columns = new ArrayList<InterfaceColumn>();
		if (result.toString().startsWith("[")) {
			List<Map<String,Object>> list = JsonUtil.parseJsonStrToList(result.toString());
			CommonUtil.mapToColumn(list.get(0), columns,null);
		} else {
			Map<String,Object> map = JsonUtil.parseJsonStrToMap(result.toString());
			CommonUtil.mapToColumn(map, columns,null);
		}
		
		return columns;
	}
	
	public static Map<String,Object> JsonToMap(JSONObject j){
	    Map<String,Object> map = new HashMap<>();
	    Iterator<String> iterator = j.keySet().iterator();
	    while(iterator.hasNext())
	    {
	        String key = (String)iterator.next();
	        Object value = j.get(key);
	        map.put(key, value);
	    }
	    return map;
	}
	
	//--------------------------------判断是否正确     start--------------------------------------------//
	public String getResults(List<InterfaceColumn> results,String type) {
		String json = null;
		if (null != results && results.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = this.getResultsByParentId(results, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = new HashMap();
				temp = this.getResults(map, tempColumnList,results,null);
				json = JSON.toJSONString(temp);
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : list) {
					temp = this.getResults(map, tempColumnList,results,null);
					tempList.add(temp);
				}
				json = JSON.toJSONString(tempList);
			}
		}
		return json;
	}
	
	
	private Map<String,Object> getResults(Map<String,Object> data,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,Map<String,Object> parentMap) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			if (column.getColumnType().equals("LIST")) { //list
				List list = (List) data.get(column.getColumnName());
				if (null != list && list.size() > 0) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = null;
					if (list.get(0) instanceof Map) {
						List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listMap = (List<Map<String,Object>>) data.get(column.getColumnName());
						for (Map<String,Object> map : listMap) {
							tempMap = this.getResults(map, tempColumnList, allColumns,result);
							tempList.add(tempMap);
						}
						data.put(column.getColumnName(), tempList);
					}
					
				}
			} else if (column.getColumnType().equals("Map")) { //Map
				Map<String,Object> map = (Map<String, Object>) data.get(column.getColumnName());
				if (null != map) {
					List<InterfaceColumn> tempColumnList = this.getResultsByParentId(allColumns, column.getColumnId());
					Map<String,Object> tempMap = this.getResults(map, tempColumnList, allColumns,result);
					data.put(column.getColumnName(), tempMap);
				}
				
			} else {
				if (null != column.getCorrectValue() && !column.getCorrectValue().isEmpty()) {
					data.put(column.getColumnName(), column.getCorrectValue());
					CommonUtil.setValue(data, column.getCorrectValue(), column);
				}
			}
		}
		return data;
	}
	//--------------------------------刷新HTTP        end--------------------------------------------//
	
	@Override
	public String showDoc(InterfaceAllDTO allDTO) throws Exception {
		List<InterfaceColumn> columns = allDTO.getColumns();
		if (null != allDTO.getChildColumns()) {
			columns.addAll(allDTO.getChildColumns());
		}
		
		List<InterfaceColumn> params = allDTO.getParams();
		if (null != allDTO.getChildParams()) {
			params.addAll(allDTO.getChildParams());
		}
		
		
		InterfaceConfig inter = allDTO.getForm();
		
		String inText = null;
		Object outText = null;
		SystemConfig system = iSystemConfigService.getById(allDTO.getForm().getSystemId());
		Object result = null;
		if ("HTTP".equals(allDTO.getForm().getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			// 获取发送的内容
			Object param = this.getParam(system, inter, params, paramsMap);
			inText = param.toString();
			result = this.getHttpResult(param, system, inter, interfaceService);
			outText = result.toString();
		} else if ("JDBC".equals(allDTO.getForm().getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			Object obj = this.getParam(system, inter, allDTO.getSqlParams(), new HashMap<String,Object>());
			Object content = getSendContent(inter, this.getParamList(allDTO.getSqlParams(), obj.toString(), inter.getDataType()), allDTO.getParams());
			if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) { //参数组装
				result = this.getUrl(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			} else if (null != inter.getParamStruct() && inter.getParamStruct().equals("FORM")) {
				result = this.getForm(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			}
			result = this.getHttpResult(result, system, inter, interfaceService);			
			inText = obj.toString();
			outText = result.toString();
		} else if ("HTTP_RE".equals(allDTO.getForm().getSource())) {
			Object paramObj = null;
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			//获取参数
			Object json = this.getReceiveContent(paramsMap, inter, allDTO.getParams(),inter.getDataType());
			inText = json.toString(); 
			String param = null;
			//参数必填校验
			if ("MAP".equals(inter.getDataType())) {
				paramObj = json;
				param = JSON.toJSONString(json);
			} else if ("LIST".equals(inter.getDataType())) {
				paramObj = json;
				param = JSONArray.toJSONString(json);
			}
			this.checkParams(paramObj, params, inter.getDataType());
			//发送
			result = this.send(inter.getDataSource(), param);
			outText = result.toString();
		} else if ("JDBC_RE".equals(allDTO.getForm().getSource())) {
			//获取参数
			Object obj = this.getParam(system, inter, params, new Object());
			inText = obj.toString();
			//获取返回
			Object jsonStr = getSendContent(inter, getParamList(params, obj.toString(), inter.getDataType()), columns); 
			outText = jsonStr;
		}
		return this.showDoc(params, columns, inText, outText, allDTO.getForm(), system);
	}
	
	
	
	@Override
	public String testInterface(InterfaceAllDTO allDTO) throws Exception {
		List<InterfaceColumn> columns = allDTO.getColumns();
		if (null != allDTO.getChildColumns()) {
			columns.addAll(allDTO.getChildColumns());
		}
		
		List<InterfaceColumn> params = allDTO.getParams();
		if (null != allDTO.getChildParams()) {
			params.addAll(allDTO.getChildParams());
		}
		
		InterfaceConfig inter = allDTO.getForm();
		
		Object outText = null;
		SystemConfig system = iSystemConfigService.getById(allDTO.getForm().getSystemId());
		Object result = null;
		if ("HTTP".equals(allDTO.getForm().getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			// 获取发送的内容
			Object param = this.getParam(system, inter, params, paramsMap);
			result = this.getHttpResult(param, system, inter, interfaceService);
			outText = result.toString();
		} else if ("JDBC".equals(allDTO.getForm().getSource())) {
			IInterfaceService interfaceService = (IInterfaceService) SpringContextHolder.getBean(system.getSystemClass());
			Object obj = this.getParam(system, inter, allDTO.getSqlParams(), new HashMap<String,Object>());
			Object content = getSendContent(inter, this.getParamList(allDTO.getSqlParams(), obj.toString(), inter.getDataType()), allDTO.getParams());
			if (null != inter.getParamStruct() && inter.getParamStruct().equals("URL")) { //参数组装
				result = this.getUrl(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			} else if (null != inter.getParamStruct() && inter.getParamStruct().equals("FORM")) {
				result = this.getForm(this.getParamList(allDTO.getParams(), content.toString(), inter.getDataType()));
			}
			result = this.getHttpResult(result, system, inter, interfaceService);			
			outText = result.toString();
		} else if ("HTTP_RE".equals(allDTO.getForm().getSource())) {
			Object paramObj = null;
			Object paramsMap = new Object();
			if ("URL".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else if ("FORM".equals(inter.getParamStruct())) {
				paramsMap = new HashMap<String,Object>();
			} else {
				if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
					throw new BaseException("请先填写参考报文");
				}
				if("MAP".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				} else if ("LIST".equals(inter.getDataType())) {
					paramsMap = JsonUtil.parseJsonStrToList(inter.getDemoText());
				}
			}
			//获取参数
			Object json = this.getReceiveContent(paramsMap, inter, allDTO.getParams(),inter.getDataType());
			String param = null;
			//参数必填校验
			if ("MAP".equals(inter.getDataType())) {
				paramObj = json;
				param = JSON.toJSONString(json);
			} else if ("LIST".equals(inter.getDataType())) {
				paramObj = json;
				param = JSONArray.toJSONString(json);
			}
			this.checkParams(paramObj, params, inter.getDataType());
			//发送
			result = this.send(inter.getDataSource(), param);
			outText = result.toString();
		} else if ("JDBC_RE".equals(allDTO.getForm().getSource())) {
			//获取参数
			Object obj = this.getParam(system, inter, params, new Object());
			Object jsonStr = getSendContent(inter, getParamList(params, obj.toString(), inter.getDataType()), columns); 
			outText = jsonStr;
		}
		return outText.toString();
	}
	
	private String showDoc(List<InterfaceColumn> params,List<InterfaceColumn> columns,String inText,Object outText,InterfaceConfig inter,SystemConfig system) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("> 作者：XXX").append("\n");
		sb.append("> 邮箱：XXX@meicloud.com").append("\n");
		sb.append("> 时间："+new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append("\n");
		sb.append("").append("\n");
		sb.append("**简要描述：**").append("\n");
		sb.append("").append("\n");
		sb.append("- "+inter.getInterfaceName()).append("\n");
		sb.append("").append("\n");
		sb.append("**请求URL：** ").append("\n");
		sb.append("").append("\n");
		if (system.getType().equals("RECEIVE")) {
			sb.append("- `"+system.getSystemUrl()+"/api-anon/external/interface/api?method="+inter.getInterfaceCode()+" ` ").append("\n");
		} else {
			sb.append("- `"+system.getSystemUrl()+inter.getInterfaceUrl()+" ` ").append("\n");
		}
		sb.append("").append("\n");
		sb.append("**请求方式：**").append("\n");
		sb.append("").append("\n");
		sb.append("- "+inter.getMethod()).append("\n");
		sb.append("").append("\n");
		
		Map<Long,List<InterfaceColumn>> columnMap = CommonUtil.getColumnMap(columns);
		Map<Long,List<InterfaceColumn>> paramMap = CommonUtil.getColumnMap(params);
		this.showDocParam(paramMap.get(null),0,paramMap,sb);
		this.showDocParamDemo(inText,sb);
		this.showDocColumn( columnMap.get(null), 0, columnMap,sb);
		if (outText instanceof String) {
			this.showDocResult(outText.toString(),sb);
		} else {
			this.showDocResult(outText,sb);
		}
		return sb.toString();
	}
	
	private void showDocParam(List<InterfaceColumn> columns,int level,Map<Long,List<InterfaceColumn>> map,StringBuffer sb) {
		sb.append("**参数说明**").append("\n").append("\n");
		sb.append("|参数名|必选|类型|说明|").append("\n");
		sb.append("|:----    |:---|:----- |-----   |").append("\n");
		if (null == columns) {
			sb.append("|  | | | | |").append("\n");
		}
		this.showDocParamPrint(columns, level, map,sb);
	}
	
	private void showDocParamPrint(List<InterfaceColumn> columns,int level,Map<Long,List<InterfaceColumn>> map,StringBuffer sb) {
		if (null != columns && columns.size() > 0) {
			for (InterfaceColumn column:columns) {
				sb.append("|");
				for (int i=0;i<level;i++) {
					sb.append("<span style=\"padding-left: 20px\">");
				}
				if (level > 0) {
					sb.append("<span style=\\\"color: #8c8a8a\\\">├─</span>");
				}
				sb.append(column.getColumnName()+" |"+(column.getIfRequirye().equals("Y")?"是":"否" ) +" |"+column.getColumnType()+"   |"+column.getColumnDesc()+"  |").append("\n");
				if (map.containsKey(column.getColumnId())) {
					this.showDocParamPrint(map.get(column.getColumnId()), level+1, map,sb);
				}
			}
		}
	}
	
	private void showDocParamDemo(Object map,StringBuffer sb) {
		sb.append("**参数示例**").append("\n");
		sb.append("``` ").append("\n");
		if (null == map ) {
			sb.append("无 ").append("\n");
		} else {
			JSONObject object = null;
			if (map instanceof String) {
				try {
					if (map.toString().trim().startsWith("[") ) {
						String pretty = JSON.toJSONString(JSONArray.parse(map.toString()), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
					            SerializerFeature.WriteDateUseDateFormat);
						sb.append(pretty).append("\n");
					} else {
						object = JSONObject.parseObject(map.toString());
						 String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
						            SerializerFeature.WriteDateUseDateFormat);
						 sb.append(pretty).append("\n");
					}
				} catch (Exception e) {
					sb.append(map.toString()).append("\n");
				}
			} else if (map instanceof MultiValueMap) {
				object = JSONObject.parseObject(map.toString());
				 String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
				            SerializerFeature.WriteDateUseDateFormat);
				 sb.append(pretty).append("\n");
			} else if (map instanceof JSONArray){
				sb.append(map.toString()).append("\n");
			} else if (map instanceof JSONObject){
				String pretty = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
			            SerializerFeature.WriteDateUseDateFormat);
				sb.append(pretty).append("\n");
			}
		}
		
		sb.append("``` ").append("\n");
	}
	
	private void showDocResult(String result,StringBuffer sb) {
		sb.append("**返回示例**").append("\n");
		sb.append("``` ").append("\n");
		try {
			JSONObject object = JSONObject.parseObject(result);
		    String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
		            SerializerFeature.WriteDateUseDateFormat);
		    sb.append(pretty).append("\n");
		} catch (Exception e) {
			sb.append(result.toString()).append("\n");
		}
		
	    
	    sb.append("``` ").append("\n");
	}
	
	private void showDocResult(Object object,StringBuffer sb) {
		sb.append("**返回示例**").append("\n");
		sb.append("``` ").append("\n");
	    String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, 
	            SerializerFeature.WriteDateUseDateFormat);
	    sb.append(pretty).append("\n");
	    sb.append("``` ").append("\n");
	}
	
	private void showDocColumn(List<InterfaceColumn> columns,int level,Map<Long,List<InterfaceColumn>> map,StringBuffer sb) {
		sb.append("**返回参数说明**").append("\n").append("\n");
		sb.append("|参数名|类型|说明|").append("\n");
		sb.append("|:-----  |:-----|-----|").append("\n");
		this.showDocPrint(columns, level, map,sb);
	}
	
	private void showDocPrint(List<InterfaceColumn> columns,int level,Map<Long,List<InterfaceColumn>> map,StringBuffer sb) {
		if (null != columns && columns.size() > 0) {
			for (InterfaceColumn column:columns) {
				sb.append("|");
				for (int i=0;i<level;i++) {
					sb.append("<span style=\"padding-left: 20px\">");
				}
				if (level > 0) {
					sb.append("<span style=\\\"color: #8c8a8a\\\">├─</span>");
				}
				sb.append(column.getColumnName()+" |"+column.getColumnType()+"   |"+column.getColumnDesc()+"  |").append("\n");
				if (map.containsKey(column.getColumnId())) {
					this.showDocPrint(map.get(column.getColumnId()), level+1, map,sb);
				}
			}
		}
	}
	
	
	/**
	 * 输出文件流到前端
	 *
	 * @param response
	 * @param is
	 * @param fileName
	 * @throws IOException
	 */
	public void wirte(String contentType, HttpServletResponse response, InputStream is, String fileName)
			throws IOException {
		fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
				.replaceAll("%29", "\\)").replaceAll("%25", "\\%");// 替换空格 不然会变为加号
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setHeader("Content-type", contentType);
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
			response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception ex) {
			log.error("文件下载失败", ex);
		} finally {
			if (bis != null) {
				try {
					is.close();
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bos != null)
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
	/**
	 * HTTP SEND  通过报文刷新入参
	 */
	@Override
	public List<InterfaceColumn> getHttpParam(InterfaceAllDTO allDTO) throws Exception {
		InterfaceConfig inter = allDTO.getForm();
		if (null == inter.getDemoText() || inter.getDemoText().isEmpty()) {
			throw new BaseException("请先输入参考报文");
		}
		List<InterfaceColumn> columns = new ArrayList<InterfaceColumn>();
		try {
			if ("MAP".equals(inter.getDataType())) {
				Map<String,Object> map = JsonUtil.parseJsonStrToMap(inter.getDemoText());
				CommonUtil.mapToColumn(map, columns,null);
			} else if ("LIST".equals(inter.getDataType())) {
				List<Map<String,Object>> list = JsonUtil.parseJsonStrToList(inter.getDemoText());
				CommonUtil.mapToColumn(list.get(0), columns,null);
			} else {
				throw new BaseException("请先选择入参类型");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(ResultCode.UNKNOWN_ERROR, "出参类型与返回结果不对应，请修改出参类型", e);
		}
		return columns;
	}
	
	
	
	/**
	 * 查询表信息
	 * @param sql
	 * @param dataConfig
	 * @return
	 */
	@Override
	public List<InterfaceColumn> listSql(String sql, String dataConfig) throws SQLException {

		List<InterfaceColumn> list = new ArrayList<InterfaceColumn>();
		Statement pStemt = null;
		ResultSet rs = null;
		Connection connection = null;
		SqlSession sqlSession = null;
		ResultSetMetaData metaData = null;
		try {
			if (StringUtil.isNotEmpty(dataConfig)) {
				// 切换数据源
				CheckModuleHolder.checkout(Module.get(dataConfig));
			}
			sqlSession = stJrsm.getSqlSessionFactory().openSession();
			connection = sqlSession.getConnection();
			pStemt = (Statement) connection.createStatement();
			try {
				rs = pStemt.executeQuery(sql);
				metaData = rs.getMetaData();
			} catch (Exception e) {
				throw new BaseException(sql + "--> sql无法解析, 刷新时请填写可直接解析的sql, 等刷新后再在sql语句的where后加带参数的条件");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != rs ) {
				rs.close();
			}
			if (null != connection) {
				connection.close();
			}
			if (null != pStemt) {
				pStemt.close();
			}
			if (StringUtil.isNotEmpty(dataConfig)) {
				CheckModuleHolder.release();
			}
			sqlSession.close();
		}

		// 获取字段名集合
		this.getSqlAttrList(list, metaData);

		if (null != list && list.size() > 0) {
//			for (Map<String, Object> map : list) {
//				String tableName = (String) map.get("table");
//				String column = (String) map.get("columnName");
//				String type = scanTableEntity(tableName, column);
//				if (null != type && type.indexOf("java.lang.") != -1) {
//					map.put("javaType", type.replace("java.lang.", ""));
//				} else if (null != type && type.indexOf("java.util.") != -1) {
//					map.put("javaType", type.replace("java.util.", ""));
//				} else if (null != type && type.indexOf("java.time.") != -1) {
//					map.put("javaType", type.replace("java.time.", ""));
//				}
//
//			}
		}

		return list;
	}
	
	private void getSqlAttrList(List<InterfaceColumn> list, ResultSetMetaData metaData) throws SQLException {
		if (null != metaData) {
			int count = metaData.getColumnCount();
			Map<String, List<InterfaceColumn>> columnListMap = new HashMap<String, List<InterfaceColumn>>();
			List<InterfaceColumn> columnList = null;
			InterfaceColumn column = null;
			for (int i = 1; i <= count; i++) {
				String name = metaData.getColumnName(i);// 获取字段名称
				String tableName = metaData.getTableName(i);
				if (columnListMap.containsKey(tableName)) {
					columnList = columnListMap.get(tableName);
				} else {
					columnList = new ArrayList<InterfaceColumn>();
				}
				column = new InterfaceColumn();
				column.setColumnName(name);
				column.setColumnType(metaData.getColumnTypeName(i));
				column.setSource("MANUAL");
				columnList.add(column);
				columnListMap.put(tableName, columnList);
			}
			// 获取数据库备注
			Iterator it = columnListMap.keySet().iterator();
			Map<String, String> remarkMap = null;
			while (it.hasNext()) {
				String tableName = (String) it.next();
				remarkMap = this.listTables(tableName);
				columnList = columnListMap.get(tableName);
				for (InterfaceColumn c : columnList) {
					String columnName = c.getColumnName();
					if (remarkMap.containsKey(columnName)) {
						c.setColumnDesc(remarkMap.get(columnName));
					}
					list.add(c);
				}
			}
		}
	}
	
	private Map<String, String> listTables(String tableName) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SqlSession sqlSession = stJrsm.getSqlSessionFactory().openSession();
		Connection connection = sqlSession.getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dm = connection.getMetaData();
			rs = dm.getColumns(null, null, tableName, "%");
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");// 列名
				String label = rs.getString("REMARKS");
				resultMap.put(columnName, label);
			}
		} catch (Exception e) {
			try {
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e1) {
			}
		} finally {
			sqlSession.close();
		}
		return resultMap;
	}
	
	
	private String scanTableEntity(String tableName, String column) {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(TableName.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("com.midea.cloud.srm.model.*")) {
			try {
				Class<?> clazz = Class.forName(bd.getBeanClassName());
				TableName myClassAnnotation = clazz.getAnnotation(TableName.class);
				if (tableName.toUpperCase().equals(myClassAnnotation.value().toUpperCase())) {
					Field fields[] = clazz.getDeclaredFields();
					for (Field field : fields) {
						TableId myFieldAnnotationId = field.getAnnotation(TableId.class);
						if (null != myFieldAnnotationId && null != myFieldAnnotationId.value()) {
							if (column.toUpperCase().equals(myFieldAnnotationId.value().toUpperCase())) {
								return field.getType().getTypeName();
							}
						}
						TableField myFieldAnnotation = field.getAnnotation(TableField.class);
						if (null != myFieldAnnotation && null != myFieldAnnotation.value()) {
							if (column.toUpperCase().equals(myFieldAnnotation.value().toUpperCase())) {
								return field.getType().getTypeName();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 获取发送内容
	 */
	public Object getSendContent(InterfaceConfig config, List<InterfaceColumn> params, List<InterfaceColumn> columns) {
		String result = null;
		List<InterfaceColumn> tempColumns = null;
		// 2.获取数据
		if (config.getSource().equals("JDBC") || config.getSource().equals("JDBC_RE")) {// JDBC
			// 获取最外层数据
			tempColumns = CommonUtil.getColumnByParentId(columns, null);
			// 参数组装
			String sql = CommonUtil.getSql(config.getDataSource(), params);
			if (config.getDataType().equals("MAP")) { // 集合
				Map<String,Object> map = this.getDataMap(config.getDataConfig(), sql, tempColumns,columns,params);
				result = JSON.toJSONString(map);
			} else if (config.getDataType().equals("LIST")) { // 数组
				List<Map<String,Object>> list = this.getDataList(config.getDataConfig(), sql, tempColumns, columns, params);
				result = com.alibaba.fastjson.JSONArray.toJSONString(list);
			}
		}
		return result;
	}

	/**
	 * 根据数据源 获取数据
	 */
	private Map<String, Object> getDataMap(String dataConfig, String sql, List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,List<InterfaceColumn> params) {
		Map<String,Object> result = null;
		Map<String,Object> converMap = null;
		List<Map<String, Object>> list = this.getData(sql, dataConfig,  columns);
		if (null != list && list.size() > 0) {
			result = list.get(0);
			converMap = this.getDataByColumn(result, columns, allColumns, params);
		}else {
			converMap = new HashMap<String,Object>();
		}
		return converMap;
	}

	/**
	 * 根据数据源 获取数据
	 */
	private List<Map<String, Object>> getDataList(String dataConfig, String sql, List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,List<InterfaceColumn> params) {
		List<Map<String,Object>> list = this.getData(sql, dataConfig,  columns);
		if (null != list && list.size() > 0) {
			Map<String,Object> temp = null;
			for (Map<String,Object> map :list) {
				temp =this.getDataByColumn(map, columns, allColumns, params);
				if (null != temp) {
					map.clear();
					map.putAll(temp);
				}
			}
		}
		return list;
	}
	
	private Map<String,Object> getDataByColumn(Map<String,Object> result ,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,List<InterfaceColumn> params) {
		Map<String,Object> converMap = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			if (column.getColumnType().equals("MAP")) {
				List tempColumns = CommonUtil.getColumnByParentId(allColumns, column.getColumnId());
				List<InterfaceColumn> paramList = CommonUtil.getParamByMap(result, params);
				String childSql = CommonUtil.getSql(column.getChildSource(), paramList);
				Map<String,Object> childMap = this.getDataMap(column.getDataConfig(), childSql, tempColumns, allColumns, paramList);
				if (StringUtil.isNotEmpty(column.getConverName())) {
					converMap.put(column.getConverName(), childMap);
				} else {
					converMap.put(column.getColumnName(), childMap);
				}
				
			} else if (column.getColumnType().equals("LIST")) {
				List tempColumns = CommonUtil.getColumnByParentId(allColumns, column.getColumnId());
				List<InterfaceColumn> paramList = CommonUtil.getParamByMap(result, params);
				String childSql = CommonUtil.getSql(column.getSqlText(), paramList);
				List<Map<String,Object>> childList = this.getDataList(column.getDataConfig(), childSql, tempColumns, allColumns, paramList);
				result.put(column.getColumnName(), childList);
				if (StringUtil.isNotEmpty(column.getConverName())) {
					converMap.put(column.getConverName(), childList);
				} else {
					converMap.put(column.getColumnName(), childList);
				}
			} else {
				if (column.getSource().equals("SQL")) {
					this.setSqlColumn(result, converMap, column, new HashMap());
				} else {
					if (result.containsKey(column.getColumnName())) {
						//设置值
						CommonUtil.setValueToMap(converMap, result.get(column.getColumnName()), column);
					} else { 
						//设置值
						CommonUtil.setValueToMap(converMap, null, column);
					}
					
				}
			}
		}
		return converMap;
	}
	
	private List<Map<String,Object>> executeSql(String sql, String dataConfig) {
		// 获取id=2的customers数据表的记录，并打印
		ResultSet rs = null;
		Connection connection = null;
		SqlSession sqlSession = null;
		Statement pStemt = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			if (StringUtil.isNotEmpty(dataConfig)) {
				// 切换数据源
				CheckModuleHolder.checkout(Module.get(dataConfig));
			}
			sqlSession = stJrsm.getSqlSessionFactory().openSession();
			connection = sqlSession.getConnection();
			pStemt = (Statement) connection.createStatement();
			System.out.println(sql);
			pStemt.execute(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (StringUtil.isNotEmpty(dataConfig)) {
				CheckModuleHolder.release();
			}
			try {
				if(null != rs) {
					rs.close();
				}
				if (null != pStemt) {
					pStemt.close();
				}
				if (null != connection) {
					connection.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
		return list;
	}

	private List<Map<String,Object>> getData(String sql, String dataConfig, List<InterfaceColumn> columns) {
		// 获取id=2的customers数据表的记录，并打印
		ResultSet rs = null;
		Connection connection = null;
		SqlSession sqlSession = null;
		Statement pStemt = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		try {
			if (StringUtil.isNotEmpty(dataConfig)) {
				// 切换数据源
				CheckModuleHolder.checkout(Module.get(dataConfig));
			}
			sqlSession = stJrsm.getSqlSessionFactory().openSession();
			connection = sqlSession.getConnection();
			pStemt = (Statement) connection.createStatement();
			System.out.println(sql);
			rs = pStemt.executeQuery(sql);
			// 5.处理ResultSet
			while (rs.next()) {
				map = new HashMap<String,Object>();
				for (InterfaceColumn column : columns) {
					Object value = null;
					if (null != column.getJavaType()) {
						value = CommonUtil.getTypeValue(column.getJavaType(),rs,column.getColumnName());
					} else {
						value = CommonUtil.getTypeValue(column.getColumnType(),rs,column.getColumnName());
					}
					if (null == value) {
						map.put(column.getColumnName(), "");
					} else {
						map.put(column.getColumnName(), value);
					}
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (StringUtil.isNotEmpty(dataConfig)) {
				CheckModuleHolder.release();
			}
			try {
				if(null != rs) {
					rs.close();
				}
				if (null != pStemt) {
					pStemt.close();
				}
				if (null != connection) {
					connection.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
		return list;
	}
	

	//-----------------------------------接收转换 start-----------------------------------------------------------//
	public Object getReceiveContent(Object data,InterfaceConfig config, List<InterfaceColumn> columns,String type) {
		Object obj = null;
		if (null != columns && columns.size() > 0) {
			Map<String,Object> temp = null;
			List<InterfaceColumn> tempColumnList = CommonUtil.getColumnByParentId(columns, null);
			if (type.equals("MAP")) {
				Map<String,Object> map = (Map)data;
				
				temp = this.convertColumnToMap(map, tempColumnList,columns,null);
				obj = temp;
			} else if (type.equals("LIST")){
				List<Map<String,Object>> list = (List)data;
				List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : list) {
					temp = this.convertColumnToMap(map, tempColumnList,columns,null);
					tempList.add(temp);
				}
				obj = tempList;
			}
		}
		return obj;
	}
	
	private Map<String,Object> convertColumnToMap(Map<String,Object> data,List<InterfaceColumn> columns,List<InterfaceColumn> allColumns,Map<String,Object> parentMap) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (InterfaceColumn column : columns) {
			if (column.getSource().equals("MANUAL")) { // 手工
				Object value = data.get(column.getColumnName());
				if (column.getColumnType().equals("LIST")) { //list
					List list = (List) data.get(column.getColumnName());
					if (null != list && list.size() > 0) {
						List<InterfaceColumn> tempColumnList = CommonUtil.getColumnByParentId(allColumns, column.getColumnId());
						Map<String,Object> tempMap = null;
						if (list.get(0) instanceof Map) {
							List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
							List<Map<String,Object>> listMap = (List<Map<String,Object>>) data.get(column.getColumnName());
							if (null == tempColumnList || tempColumnList.isEmpty()) {
								tempList = listMap;
							} else {
								for (Map<String,Object> map : listMap) {
									tempMap = this.convertColumnToMap(map, tempColumnList, allColumns,result);
									tempList.add(tempMap);
								}
							}
							if (null != column.getConverName() && !column.getConverName().isEmpty()) {
								result.put(column.getConverName(), tempList);
							} else {
								result.put(column.getColumnName(), tempList);
							}
						}
						
					} else {
						if (null != column.getConverName() && !column.getConverName().isEmpty()) {
							result.put(column.getConverName(), list);
						} else {
							result.put(column.getColumnName(), list);
						}
					}
				} else if (column.getColumnType().equals("Map")) { //Map
					Map<String,Object> map = (Map<String, Object>) data.get(column.getColumnName());
					if (null != map) {
						List<InterfaceColumn> tempColumnList = CommonUtil.getColumnByParentId(allColumns, column.getColumnId());
						Map<String,Object> tempMap = null;
						if (null == tempColumnList || tempColumnList.isEmpty()) {
							tempMap = map;
						} else {
							tempMap = this.convertColumnToMap(map, tempColumnList, allColumns,result);
						}
						if (null != column.getConverName() && !column.getConverName().isEmpty()) {
							result.put(column.getConverName(), tempMap);
						} else {
							result.put(column.getColumnName(), tempMap);
						}
					}
					
				} else {
					CommonUtil.setValueToMap(result, value, column);
				}
			} else if (column.getSource().equals("SQL")) { // 系统取值
				this.setSqlColumn(data, result, column, parentMap);
			}
		}
		return result;
	}
	
	/**
	 * 获取sql语句字段转换值
	 * @param data
	 * @param result
	 * @param column
	 * @param parentMap
	 */
	private void setSqlColumn(Map<String,Object> data,Map<String,Object> result,InterfaceColumn column,Map<String,Object> parentMap) {
		List<InterfaceColumn> params = CommonUtil.getParamByMap(data, null);
		List<InterfaceColumn> value = CommonUtil.getParamByMap(result, null); //行字段参数
		//判断 如果行已经存在参数名称 则不使用头的
		List<InterfaceColumn> parent = CommonUtil.getParam(value, CommonUtil.getParamByMap(parentMap, null)); //父级字段参数
		
		params.addAll(parent);
		params.addAll(value);
		
		String sql = CommonUtil.getSql(column.getSqlText(), params);
		List<InterfaceColumn> convertList = new ArrayList<InterfaceColumn>();
		convertList.add(column);
		List<Map<String,Object>> list = this.getData(sql, column.getDataConfig(), convertList);
		if (null != list && list.size() > 0) {
			Map<String,Object> map = list.get(0);
			if (map.containsKey(column.getColumnName())) {
				CommonUtil.setValueToMap(result, map.get(column.getColumnName()), column);
			} else {
				CommonUtil.setValueToMap(result, null, column);
			}
		} else {
			CommonUtil.setValueToMap(result, null, column);
		}
	}
}
