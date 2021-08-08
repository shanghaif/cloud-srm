package com.midea.cloud.api.inter.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceAllDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;

public interface IInterService {
	
	public String sendForm(String interfaceCode,HttpServletResponse response);
	
	public String send(@RequestParam String interfaceCode,@RequestParam Object params,HttpServletResponse response);
	
	public Object getContent(InterfaceConfig inter, Map<String, Object> params);
	
	/**
	 * 接收
	 * @param method
	 * @return
	 */
	public String apiInfo(String method);
	
	public BaseResult apiInfoWebservice(String method,Object obj) ;
	
	public Object getContentWebservice(InterfaceConfig inter, Map<String, Object> params);
	
	/**
	 * 刷新http字段
	 * @param http
	 * @param allDTO
	 */
	public List<InterfaceColumn> getHttpColumns(InterfaceAllDTO allDTO) throws Exception;
	
	/**
	 * 刷新SQL返回
	 * @param http
	 * @param allDTO
	 */
	public List<InterfaceColumn> getSqlColumns(InterfaceAllDTO allDTO);
	
	public String showDoc(InterfaceAllDTO allDTO) throws Exception;
	
	public String testInterface(InterfaceAllDTO allDTO) throws Exception;
	
	/**
	 * HTTP SEND  通过报文刷新入参
	 */
	public List<InterfaceColumn> getHttpParam(InterfaceAllDTO allDTO) throws Exception;
	
	public List<InterfaceColumn> listSql(String sql,String dataConfig) throws SQLException;
}
