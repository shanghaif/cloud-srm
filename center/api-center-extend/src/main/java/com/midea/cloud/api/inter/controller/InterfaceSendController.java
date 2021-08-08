package com.midea.cloud.api.inter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.common.exception.BaseException;

@Controller
@RequestMapping("/interface")
public class InterfaceSendController {
	@Autowired
	private IInterService iInterService;


	/**
	 * 接口发送
	 * @throws Exception 
	 */
	@RequestMapping("/send")
	public void send(@RequestParam String interfaceCode,@RequestParam Map<String, Object> params,HttpServletResponse httpServletResponse) {
		this.httpWrite(iInterService.send(interfaceCode, params,httpServletResponse), httpServletResponse);
	}

	/**
	 * 接口发送
	 * @throws Exception 
	 */
	@RequestMapping("/apiSend/{interfaceCode}")
	public void apiSend(@PathVariable String interfaceCode,@RequestBody Object params,HttpServletResponse httpServletResponse) throws Exception {
		if (null != interfaceCode && !interfaceCode.isEmpty()) {
			String result = iInterService.send(interfaceCode, params,httpServletResponse);
			this.httpWrite(result, httpServletResponse);
		}else {
			throw new BaseException("缺少interfaceCode参数");
		}
	}
	
	/**
	 * 接口发送
	 * @throws Exception 
	 */
	@RequestMapping("/apiSend/form/{interfaceCode}")
	public void apiFileUpload(@PathVariable String interfaceCode,HttpServletResponse httpServletResponse) throws Exception {
		if (null != interfaceCode && !interfaceCode.isEmpty()) {
			String result = iInterService.sendForm(interfaceCode, httpServletResponse);
			this.httpWrite(result, httpServletResponse);
		}else {
			throw new BaseException("缺少interfaceCode参数");
		}
	}
	
	/**
	 * 接口发送
	 * @throws Exception 
	 */
	@RequestMapping("/apiSend/file/{interfaceCode}")
	public void apiFileDown(@PathVariable String interfaceCode,@RequestBody Object params,HttpServletResponse httpServletResponse) throws Exception {
		if (null != interfaceCode && !interfaceCode.isEmpty()) {
			iInterService.send(interfaceCode, params,httpServletResponse);
		}else {
			throw new BaseException("缺少interfaceCode参数");
		}
	}
	
	
	private void httpWrite(String result,HttpServletResponse httpServletResponse) {
		try {
			httpServletResponse.setContentType("text/html;charset=UTF-8");
			httpServletResponse.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
