package com.midea.cloud.api.inter.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.srm.model.common.BaseController;

/**
 * <pre>
 *  接口字段 前端控制器
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
@Controller
@RequestMapping("/interface")
public class InterfaceController extends BaseController {

	@Autowired
	private IInterService iInterService;

	/**
	 *  单点统一接口接收
	 */
	@RequestMapping(value = "/api", method = {RequestMethod.GET,RequestMethod.POST})
	public void api(String method,HttpServletResponse response) {
		this.httpWrite(iInterService.apiInfo(method), response); ;
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
