package com.midea.cloud.srm.rbac.security.common.facepass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FaceUtil {
	
	/**
	 * 检测人脸，参数是base64的数据流
	 * @param faceData
	 * @param faceDataSource
	 * @return
	 */
	public static Boolean detectFace(String faceData){
		String s =GetAccessTokenUtil.getAccessToken();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("X-IDaaS-Token", s);

		Map<String, String> mapBody =new HashMap<>();
		mapBody.put("imgBase64", faceData);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonUtil.entityToJsonStr(mapBody), header);
	
		String url ="https://apiuat.midea.com/aif/ai-face-service/faceapp/v1/detect"+"?appId=srm";
		
		//{"resultCode":"200","msg":"ok","data":{"faceInPicCode":3,"confidence":0.828843373},"ip":"10.17.156.198","logId":161932153919420,"ok":true}
		//{"resultCode":"3014","msg":"人脸检测失败","data":null,"ip":"10.17.156.198","logId":161934510804414,"ok":false}
		//Map<String, Object> mapData = (Map)restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
		
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		log.info(result.getBody());
		Map<String, Object> mapResult =JsonUtil.parseJsonStrToMap(result.getBody());
		if( "200".equals(mapResult.get("resultCode"))) {
			return true;
		}else {
			if( mapResult.get("msg")!=null && !"".equals(mapResult.get("msg")) ) {
				throw new BaseException(mapResult.get("msg").toString());
			}else {
				throw new BaseException("人脸检测失败");
			}
			
		}
	}
	
	/**
	 * 校验人脸相似度，参数是base64的数据流
	 * @param faceData
	 * @param faceDataSource
	 * @return
	 */
	public static Boolean verifyFace(String faceData, String faceDataSource){
		String s =GetAccessTokenUtil.getAccessToken();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("X-IDaaS-Token", s);

		Map<String, String> mapBody =new HashMap<>();
		mapBody.put("img1Base64", faceData);
		mapBody.put("img2Base64", faceDataSource);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonUtil.entityToJsonStr(mapBody), header);
	
		String url ="https://apiuat.midea.com/aif/ai-face-service/faceapp/v1/compare"+"?appId=srm";
		
		//{"resultCode":"200","msg":"ok","data":{"faceInPicCode":3,"confidence":0.828843373},"ip":"10.17.156.198","logId":161932153919420,"ok":true}
		//{"resultCode":"3014","msg":"人脸检测失败","data":null,"ip":"10.17.156.198","logId":161934510804414,"ok":false}
		//Map<String, Object> mapData = (Map)restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
		
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		log.info(result.getBody());
		Map<String, Object> mapResult =JsonUtil.parseJsonStrToMap(result.getBody());
		if( "200".equals(mapResult.get("resultCode"))) {
			Double confidence =Double.parseDouble(((Map)mapResult.get("data")).get("confidence").toString());
			//80%相似度表示通过
			if( confidence<0.8d ) {
				throw new BaseException("人脸信息不匹配");
			}else {
				return true;
			}
		}else {
			throw new BaseException(mapResult.get("msg").toString());
		}
	}
	
	
	/**
	 * 图片转base64字符串
	 * @param imgFile 图片路径
	 * @return
	 */
	public static String imageToBase64Str(String imgFile) {
	 InputStream inputStream = null;
	 byte[] data = null;
	 try {
	  inputStream = new FileInputStream(imgFile);
	  data = new byte[inputStream.available()];
	  inputStream.read(data);
	  inputStream.close();
	 } catch (IOException e) {
	  e.printStackTrace();
	 }
	 // 加密
	 return Base64.getEncoder().encodeToString(data);
	}
	
	public static void main(String[] args) {
		
		String s =GetAccessTokenUtil.getAccessToken();
		System.out.println("accessToken访问令牌："+s);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("X-IDaaS-Token", s);
		
		Map<String, String> mapBody =new HashMap<>();
		mapBody.put("imgBase64", imageToBase64Str("D:\\DevProgram\\PythonWorkspace\\FaceDiscern\\data\\lizl\\2.jpg"));
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonUtil.entityToJsonStr(mapBody), header);
	
		String url ="https://apiuat.midea.com/aif/ai-face-service/faceapp/v1/detect"+"?appId=srm";
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> result = new RestTemplate().exchange(url, HttpMethod.POST, httpEntity, String.class);
		
		System.out.println(result.getBody());

		
		mapBody =new HashMap<>();
		mapBody.put("img1Base64", imageToBase64Str("D:\\DevProgram\\PythonWorkspace\\FaceDiscern\\data\\lizl\\2.jpg"));
		mapBody.put("img2Base64", imageToBase64Str("D:\\DevProgram\\PythonWorkspace\\FaceDiscern\\data\\lizl\\90.jpg"));
		
		httpEntity = new HttpEntity<String>(JsonUtil.entityToJsonStr(mapBody), header);
	
		url ="https://apiuat.midea.com/aif/ai-face-service/faceapp/v1/compare"+"?appId=srm";
		
		restTemplate =new RestTemplate();
		result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		System.out.println(result.getBody());
		Map<String, Object> mapResult =JsonUtil.parseJsonStrToMap(result.getBody());
		System.out.println(((Map)mapResult.get("data")).get("confidence"));
	
		
	}
}
