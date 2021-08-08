package com.midea.cloud.file.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.file.config.MideaOssConfig;
import com.midea.cloud.file.utils.FileUtil;
import com.midea.cloud.file.utils.HttpClientInvokerUtil;
import com.midea.cloud.file.utils.MideaOssResponse;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 美的云OSS存储文件
 * </pre>
 *
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 15:26
 *  修改内容:
 *          </pre>
 */
@Service("mideaOssFileServiceImpl")
@Slf4j
public class MideaOssFileServiceImpl extends AbstractFileService {

	@Autowired
	private MideaOssConfig mideaOssConfig;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected FileUploadType fileUploadType() {
		return FileUploadType.MIDEAOSS;
	}

	/**
	 * token，半小时有效期
	 */
	private static String currentCertificationValue;

	/**
	 * 最后token获取时间，用于超时重新获取
	 */
	private static Date lastCertificationDate;

	/**
	 * 获取token
	 * 
	 * @param authHost
	 * @param appId
	 * @param appKey
	 * @return
	 * @throws Exception
	 */
	protected String getCertification(String authHost, String appId, String appKey) {
		// 半个小时过期
		Date dateNow = new Date();
		Date datePre = new Date(dateNow.getTime() - 30 * 60 * 1000);
		if (currentCertificationValue != null && lastCertificationDate != null
				&& lastCertificationDate.after(datePre)) {
			return currentCertificationValue;
		}
		String ts = String.valueOf(System.currentTimeMillis());
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appid", appId);
		paramMap.put("ts", ts);
		paramMap.put("signature", getSignature(appId, appKey, ts));

		String requestUrl = authHost + "/v1/appmanager/certification/create";
		String response = HttpClientInvokerUtil.invokePostMethodByHttp(requestUrl, JsonUtil.entityToJsonStr(paramMap));
		MideaOssResponse mideaOssResponse = JSON.parseObject(response, MideaOssResponse.class);
		if (mideaOssResponse.getCode() == 200) {
			Map<String, Object> map = (Map<String, Object>) mideaOssResponse.getData();
			currentCertificationValue = (String) map.get("certification");
			return currentCertificationValue;
		}
		return null;
	}

	/**
	 * 签名
	 * 
	 * @param appId
	 * @param appKey
	 * @param ts
	 * @return
	 */
	private static String getSignature(String appId, String appKey, String ts) {
		String value = appId + ts + appKey;
		String signature = md5String(value.getBytes());
		return signature;
	}

	/**
	 * md5加密
	 * 
	 * @param buf
	 * @return
	 */
	private static String md5String(byte[] buf) {
		byte[] tmp = md5(buf);
		return Hex.encodeHexString(tmp);
	}

	/**
	 * md5加密
	 * 
	 * @param buf
	 * @return
	 */
	private static byte[] md5(byte[] buf) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(buf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 上传
	 */
	@Override
	protected void uploadFile(MultipartFile file, Fileupload fileupload) throws Exception {
		Map<String, String> headMap = new HashMap<String, String>();

		// 样例application/octet-stream
		if (file != null && file.getContentType() != null) {
			headMap.put("Content-Type", file.getContentType());
		}

		// 请求地址
		Date dateNow = new Date();

		// 文件格式用日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileKey = simpleDateFormat.format(dateNow) + UUID.randomUUID().toString();
		String requestUrl = mideaOssConfig.getAppHost() + "/v1/oss/object/upload/" + mideaOssConfig.getAppId() + "/"
				+ mideaOssConfig.getBucket() + "/" + fileKey;
		headMap.put("x-amz-acl", "public-read");
		headMap.put("certification",
				getCertification(mideaOssConfig.getAuthHost(), mideaOssConfig.getAppId(), mideaOssConfig.getAppKey()));
		SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		headMap.put("x-amz-date", sdf.format(new Date()));

		int status = HttpClientInvokerUtil.httpClientUploadFile(file.getInputStream(), requestUrl, headMap);
		if (status == 200) {
			String fileStorageeName = FileUtil.getPureName(fileupload);
			fileupload.setFilePath(fileKey);
			fileupload.setFilePureName(fileStorageeName);
			fileupload.setFileFullname(fileKey); // 文件全路径名
		} else {
			throw new BaseException("文件上传失败");
		}
	}

	/**
	 * 获取文件访问地址
	 * 
	 * @param authHost
	 * @param appId
	 * @param appKey
	 * @param bucket
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String getFileUrl(String authHost, String appId, String appKey, String bucket, String object)
			throws Exception {
		String requestUrl = authHost + "/v1/appmanager/object/get_presignedurl";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appid", appId);
		paramMap.put("bucket", bucket);
		paramMap.put("certification", getCertification(authHost, appId, appKey));
		paramMap.put("object", object);
		// public-read, public-read-write, private
		paramMap.put("acl", "public-read");

		// 样例http://10.17.155.107:17480/userDownload/DD68BA88D73E4B3EABC30F965A0DE522/mro_mall/test_1587871949549
		String response = HttpClientInvokerUtil.invokePostMethodByHttp(requestUrl, JsonUtil.entityToJsonStr(paramMap));
		MideaOssResponse mideaOssResponse = JSON.parseObject(response, MideaOssResponse.class);
		if (mideaOssResponse.getCode() == 200) {
			Map<String, Object> map = (Map<String, Object>) mideaOssResponse.getData();
			return (String) map.get("url");
		}
		return null;
	}

	/**
	 * 下载
	 */
	@Override
	public InputStream download(Fileupload fileupload) throws Exception {

		String fileKey = fileupload.getFileFullname();
		String requestUrl = mideaOssConfig.getAppHost() + "/v1/oss/object/download/" + mideaOssConfig.getAppId() + "/"
				+ mideaOssConfig.getBucket() + "/" + fileKey;

		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("certification",
				getCertification(mideaOssConfig.getAuthHost(), mideaOssConfig.getAppId(), mideaOssConfig.getAppKey()));
		SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		headMap.put("x-amz-date", sdf.format(new Date()));

		InputStream inputStream = HttpClientInvokerUtil.invokeGetMethodByInputStream(requestUrl, headMap);
		return inputStream;
		
		// 显示测试
//		InputStreamReader isr = new InputStreamReader(inputStream);
//		BufferedReader read = new BufferedReader(isr);
//		StringBuffer res = new StringBuffer();
//		try {
//			String line;
//			line = read.readLine();
//			while (line != null) {
//				res.append(line + "");
//				line = read.readLine();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(res);
		

	}

	/**
	 * 删除
	 */
	@Override
	protected boolean deleteFile(Fileupload fileupload) {

		String fileKey = fileupload.getFileFullname();
		String requestUrl = mideaOssConfig.getAuthHost() + "/v1/appmanager/object/delete?issync=true";

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appid", mideaOssConfig.getAppId());
		paramMap.put("bucket", mideaOssConfig.getBucket());
		paramMap.put("certification",
				getCertification(mideaOssConfig.getAuthHost(), mideaOssConfig.getAppId(), mideaOssConfig.getAppKey()));
		paramMap.put("object", fileKey);

		Map<String, String> headMap = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		headMap.put("x-amz-date", sdf.format(new Date()));

		String response = HttpClientInvokerUtil.invokePostMethodByHttp(requestUrl, JsonUtil.entityToJsonStr(paramMap),
				headMap);

		MideaOssResponse mideaOssResponse = JSON.parseObject(response, MideaOssResponse.class);
		if (mideaOssResponse.getCode() == 200) {
			return true;
		}else {
			throw new BaseException("文件删除失败");
		}

	}

	public static void main(String[] args) {
		MideaOssFileServiceImpl mideaOssFileServiceImpl = new MideaOssFileServiceImpl();
		try {

//            File fileLocal=new File("D:\\ObjectUtil.txt");
//            byte[] bytesArray = new byte[(int) fileLocal.length()];
//            FileInputStream fis = new FileInputStream(fileLocal);
//            fis.read(bytesArray); //read file into bytes[]
//            fis.close();

//        	String certification =mideaOssFileServiceImpl.getCertification("http://10.17.155.107:80","DD68BA88D73E4B3EABC30F965A0DE522","Of6Ym83XM4W3y1CA1p93TrfVuBGAzi377cYp0e00");
//        	String certification =mideaOssFileServiceImpl.getCertification("http://oss-cn-foshan.midea.com:8000","899C7C659D024EE79E1E0C445DC1D137","uGh4OxgS7mkOKJ9QUG0M6DkB6FhktvoCrmjsgg5K");

//        	mideaOssFileServiceImpl.uploadFile(null, null);
//            mideaOssFileServiceImpl.download(null);
//            mideaOssFileServiceImpl.delete(null);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
