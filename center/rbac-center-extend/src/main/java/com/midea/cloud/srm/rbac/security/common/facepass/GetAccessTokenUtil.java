package com.midea.cloud.srm.rbac.security.common.facepass;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * 	//调用GetAccessToken接口获取访问令牌说明文档：http://confluence.midea.com/pages/viewpage.action?pageId=77632894
 *	//GetAccessToken-生产地址：https://apiprod.midea.com/iam/v1/security/getAccessToken
 *	//GetAccessToken-UAT测试地址：https://apiuat.midea.com/iam/v1/security/getAccessToken
 * @author AIFace
 *
 */
public class GetAccessTokenUtil {

	//根据响应环境选择对应的域名
	static String getAccessTokenUrl = "https://apiuat.midea.com/iam/v1/security/getAccessToken"; 
	
	//登录paas平台，在个人信息--访问控制--查看个人秘钥对
	static String accessKeyId = "s4qlZLHLgxBVIufDnKaJfexu";
	static String accessKeySecret = "cFpFNBhyaxx4hpwxMAEx1m3Shd6PTDEy";
	
	static String grantType = "client_credential";

	/**
	 * paas:获取API服务的访问令牌
	 * @param accessKeyId 秘钥id
	 * @param accessKeySecret 秘钥
	 * @param getAccessTokenUrl
	 * @return
	 */
	public static String getAccessToken() {
		Pbkdf2Encoder encoder = new Pbkdf2Encoder(accessKeySecret);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = encoder.encode(org.apache.commons.lang3.StringUtils.join(grantType, accessKeyId, timestamp));
		String url = getAccessTokenUrl+"?"+"accessKeyId="+accessKeyId+"&grantType="
		+grantType+"&signature="+signature+"&timestamp="+timestamp;
		
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> result = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		String string =result.getBody();
		//String string = HttpClientUtil.get(url);
		
		JSONObject parseObject = JSONObject.parseObject(string);
		return parseObject.getJSONObject("result").getString("accessToken");
	}
	
	public static void main(String[] args) {
		System.out.println("accessToken访问令牌："+getAccessToken());
	}
	
}
