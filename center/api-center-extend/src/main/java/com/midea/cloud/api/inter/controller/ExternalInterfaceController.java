package com.midea.cloud.api.inter.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.common.BaseController;

@Controller
@RequestMapping("/api-anon")
public class ExternalInterfaceController extends BaseController {
	
	
	@Autowired
	private IInterService iInterService;
	
	/**
	 * 统一对外接收接口
	 */
	@RequestMapping("/external/interface/api")
	public void api(String method,HttpServletResponse response) {
		this.httpWrite(iInterService.apiInfo(method), response);
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
