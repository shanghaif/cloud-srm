package com.midea.cloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.constants.VendorAssesFormConstant;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;
@Slf4j
public class AppUserUtil {

	/**
	 * 获取当前登陆的用户信息
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static LoginAppUser getLoginAppUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
			authentication = oAuth2Auth.getUserAuthentication();

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
				Object details = authenticationToken.getDetails();
				if (details instanceof Map) {
					Map map = (Map) details;
					Object principal = map.get("principal");
					if (principal instanceof LoginAppUser) {
						return (LoginAppUser) principal;
					}
					try {
						return JSONObject.parseObject(JSONObject.toJSONString(principal), LoginAppUser.class);
					} catch (Exception ex) {
						return null;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Description 获取用户名
	 * @Param
	 * @return
	 * @Author wuwl18@meicloud.com
	 * @Date 2020.03.26
	 **/
	public static String getUserName(){
		LoginAppUser loginAppUser = getLoginAppUser();
		if(null != loginAppUser){
			return loginAppUser.getUsername();
		}
		return "";
	}

	/**
	 * 判断用户是否供应商
	 * @return
	 */
	public static boolean userIsVendor(){
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		String userType = loginAppUser.getUserType();
		if(VendorAssesFormConstant.USER_TYPE_VENDOR.equals(userType)){
			return true;
		}
		return false;
	}

	/**
	 * 获取供应商编码
	 * @return
	 */
	public static String getVendorCode(){
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		return loginAppUser.getCompanyCode();
	}

}
