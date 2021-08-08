package com.midea.cloud.file.upload.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.constants.OAuthConstant;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.config.InternalAnonPasswordProperties;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.file.config.FileServiceFactory;
import com.midea.cloud.file.upload.mapper.FileuploadMapper;
import com.midea.cloud.file.upload.service.FileService;
import com.midea.cloud.file.upload.service.IExportService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.common.ExportExcelParamCommon;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExportServiceImpl implements IExportService {

	@Resource
	private RibbonTemplateWrapper ribbonTemplate;

	@Resource
	private RestTemplate restTemplate;

	@Autowired
	private BaseClient baseClient;

	private static int pageSize = 5000;
	
	
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
	
	
	private final ThreadPoolExecutor executor;

	@Autowired
	private FileServiceFactory fileServiceFactory;
	
	@Autowired
	private FileuploadMapper fileuploadMapper;

	@Value("${eureka.instance.instance-id}")
	private String instanceId;

	@Value("${spring.application.name}")
	private String applicationName;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Autowired(required = false)
	private InternalAnonPasswordProperties internalAnonPassword;
	
	public ExportServiceImpl() {
		int cpuCount = Runtime.getRuntime().availableProcessors(); //获取cpu可执行数量
		System.out.println("cpu数量:"+cpuCount);
        TimeUnit unit;
        BlockingQueue workQueue;
        executor = new ThreadPoolExecutor(corePoolSize , maximumPoolSize,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(taxPoolSize));
	}

	@Override
	public void exportStart(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ExportExcelParamCommon param) throws IOException {
		// 标题
		List<String> head = param.getLanguageList();
		// 文件名
		String fileName = param.getFileName();
		// 获取导出的数据
		exportData(request, response, param, head, fileName);
	}

	@Override
	public void exportData(HttpServletRequest request, HttpServletResponse response, ExportExcelParamCommon param,
			List<String> head, String fileName) {

		Boolean exportFlag = true; // 直接导出

		JSONObject queryParam = (JSONObject) param.getQueryParam();
		if (null == queryParam) {
			queryParam = new JSONObject();
		}
		Long startTime = System.currentTimeMillis();
		Long pSize = null;
		Long pNum = null;
		boolean flag = StringUtil.notEmpty(queryParam.get("pageSize"));
		if (flag) {
			pSize = Long.valueOf(queryParam.get("pageSize").toString());
			pNum = Long.valueOf(queryParam.get("pageNum").toString());
		}
		Integer total = getTotal(param.getUrl(), queryParam);
		Long endTime = System.currentTimeMillis();
		if ((endTime - startTime) / 1000 > maxTotalTime) { // 当导出查询总数量超过3秒时 直接放到导出中心
			exportFlag = false;
		} else if (!flag && total.longValue() > maxTotalNum) { // 导出全部 全部数量大于配置数量时 直接放到导出中心
			exportFlag = false;
		} else if (flag && pSize.longValue() > maxTotalNum) { // 分页导出 导出数量大于配置数量时 直接放到导出中心
			exportFlag = false;
		} 
		if (flag) {
			queryParam.put("pageSize", pSize);
			queryParam.put("pageNum", pNum);
		} else {
			queryParam.put("pageSize", null);
			queryParam.put("pageNum", null);
		}
		FileService fileService = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!exportFlag) {
			//先保存数据
			Fileupload fileupload = null;
			try {
				fileupload = this.getUploadFile(fileName, param.getPermissionName()==null?"":param.getPermissionName());
				if (fileupload.getUploadType() == null) {
					fileupload.setUploadType(FileUploadType.FASTDFS.name());
				}
				fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
				fileupload.setComment("执行中");
				fileupload.setFingerprint("1");
				fileupload.setFileFullname("1");
				fileupload.setFileExtendType("1");
				fileService.saveFile(fileupload);
				Long fileUploadId = fileupload.getFileuploadId();
				String uploadType = fileupload.getUploadType();
				
				
				log.info("活跃线程数量："+executor.getActiveCount());
				log.info("任务数量数量："+executor.getTaskCount());
				// 异步导出,线程管理线程池处理
				executor.submit(new Runnable() {
                  @Override
                  public void run() {
                	  createAsyncFile(request, response, param, head, fileName, total,fileUploadId,uploadType,authentication);
                  }
              });
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			throw new BaseException("该附件下载时间过长，请到下载中心下载！");
		} else {
			this.createFile(request, response, param, head, fileName, total,authentication);
		}

	}

	/**
	 *导出及保存
	 * @param request
	 * @param response
	 * @param param
	 * @param head
	 * @param fileName
	 * @param total
	 */
	private void createFile(HttpServletRequest request, HttpServletResponse response, ExportExcelParamCommon param,
			List<String> head, String fileName, Integer total, Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream is = null;
		this.getFile(request, response, param, head, fileName, total, bos);
		this.saveFile(fileName,param.getPermissionName()==null?"":param.getPermissionName(), bos, is,response);
	}

	/**
	 * 异步保存到附件中心
	 * @param request
	 * @param response
	 * @param param
	 * @param head
	 * @param fileName
	 * @param total
	 */
	private void createAsyncFile(HttpServletRequest request, HttpServletResponse response, ExportExcelParamCommon param,
			List<String> head, String fileName, Integer total,Long fileUploadId,String uploadType,Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream is = null;
		FileService fileService = null;
		try {
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(uploadType));
			Fileupload fileupload = fileuploadMapper.selectById(fileUploadId);
			this.getFile(request, response, param, head, fileName, total, bos);
			this.saveAsynFile(fileupload, bos, is, response,fileService);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("文件上传方式有误");
		}
		
	}
	
	private void getFile(HttpServletRequest request, HttpServletResponse response, ExportExcelParamCommon param,
			List<String> head, String fileName, Integer total,ByteArrayOutputStream bos) {
		JSONObject queryParam = (JSONObject) param.getQueryParam();
		if (null == queryParam) {
			queryParam = new JSONObject();
		}

		
		ExcelWriter excelWriter = null;
		WriteSheet sheet = null;
		boolean flag = StringUtil.notEmpty(queryParam.get("pageSize"));
		try {
			List<List<String>> list = new ArrayList<>();
			if (head != null) {
				head.forEach(h -> list.add(Collections.singletonList(h)));
				excelWriter = EasyExcel.write(bos).build();
				sheet = EasyExcel.writerSheet(1).build();
				sheet.setHead(list);
				sheet.setSheetName(fileName);
			}
			// kuangzm 字典转换
			String dicts = (String) param.getDictCodes();
			Map<String, String> dictMap = JsonUtil.parseJsonStrToMap(dicts);
			Map<String, Map<String, String>> dict = this.getDict(dicts);

			JSONArray jsonArray = null;

			List<List<Object>> dataList = null;

			if (!flag) { // 不分页
				if (null == total) { // 总数不存在
					jsonArray = this.getDatas(param.getUrl(), queryParam);
					dataList = getList(jsonArray, param.getTitleList(), dictMap, dict);
					excelWriter.write(dataList, sheet);
				} else { // 总数存在则分页
					int count = 0;
					if (total % pageSize == 0) {// 能整除
						count = total / pageSize;
					} else {// 不能整数
						count = total / pageSize + 1;
					}
					queryParam.put("pageSize", pageSize);
					for (int i = 1; i <= count; i++) {
						queryParam.put("pageNum", i);
						jsonArray = this.getDatas(param.getUrl(), queryParam);
						if (null != jsonArray) {
							dataList = getList(jsonArray, param.getTitleList(), dictMap, dict);
							excelWriter.write(dataList, sheet);
						}
					}
				}
			} else { // 分页
				int pSize = Integer.valueOf(queryParam.get("pageSize").toString());
				int pNum = Integer.valueOf(queryParam.get("pageNum").toString());
				if (pSize > pageSize) {
					int count = 0 ;
					boolean ff = true;
					int other = 0;
					if (pSize % pageSize == 0) {
						count = pSize / pageSize;
					} else {
						count = pSize / pageSize+1;
						ff = false;
						other = pSize - pageSize*(count-1);
					}
					int start = count*(pNum-1)+1;
					int end = count * pNum;
					for (int i = start;i<=end ;i++) {
						if (!ff) {
							if (i == end ) {
								queryParam.put("pageNum", i);
								queryParam.put("pageSize", other);
							} else {
								queryParam.put("pageNum", i);
								queryParam.put("pageSize", pageSize);
							}
						} else {
							queryParam.put("pageNum", i);
							queryParam.put("pageSize", pageSize);
						}
						
						jsonArray = this.getDatas(param.getUrl(), queryParam);
						if (null != jsonArray) {
							dataList = getList(jsonArray, param.getTitleList(), dictMap, dict);
							excelWriter.write(dataList, sheet);
						}
					}
				} else {
					jsonArray = this.getDatas(param.getUrl(), queryParam);
					dataList = getList(jsonArray, param.getTitleList(), dictMap, dict);
					excelWriter.write(dataList, sheet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}

	
	private void saveFile(String fileName,String fileFunction,ByteArrayOutputStream bos,InputStream is,HttpServletResponse response) {
		String contentType = "";
		MultipartFile multipartFile = null;
		FileService fileService = null;
		InputStream isTemp = null;
		try {
			byte[] barray = bos.toByteArray();
			is = new ByteArrayInputStream(barray);
			isTemp = new ByteArrayInputStream(barray);
			this.write(response, fileName, is);
			
			multipartFile = new MockMultipartFile("file", fileName+".xlsx", contentType, isTemp);
			
			Fileupload fileupload = this.getUploadFile(fileName,fileFunction==null?"":fileFunction);
			fileupload.setComment("完成");
			if (fileupload.getUploadType() == null) {
				fileupload.setUploadType(FileUploadType.FASTDFS.name());
			}
			
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
			
			try {
				fileService.upload(multipartFile, fileupload);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BaseException("文件上传失败"); // 支持国际化输出
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != isTemp) {
				try {
					isTemp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	private void saveAsynFile(Fileupload fileupload,ByteArrayOutputStream bos,InputStream is,HttpServletResponse response,FileService fileService) {
		String originalFilename = fileupload.getFileSourceName();
		String contentType = "";
		MultipartFile multipartFile = null;
		try {
			byte[] barray = bos.toByteArray();
			is = new ByteArrayInputStream(barray);
			
			multipartFile = new MockMultipartFile("file", originalFilename, contentType, is);
			
			try {
				fileupload.setComment("完成");
				fileService.updateFile(multipartFile, fileupload);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BaseException("文件上传失败"); // 支持国际化输出
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is ) {
				try {
					is.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private Fileupload getUploadFile(String fileName,String fileFunction) {
		Fileupload fileupload = new Fileupload();
		String uploadType = FileUploadType.FASTDFS.name();
		String fileType = "xlsx";
		fileupload.setFileType(fileType);
		fileupload.setUploadType(uploadType);
		fileupload.setFileType("EXPORT_FILE");
		fileupload.setFileModular("api-file");
		fileupload.setSourceType("WEB_APP");
		fileupload.setFileSourceName(fileName + ".xlsx");
		fileupload.setFileFunction(fileFunction);
		return fileupload;
	}
	

	private void write(HttpServletResponse response, String fileName, InputStream is) {
		// 获取输出流
		ServletOutputStream outputStream = null;
		BufferedOutputStream bos = null;
		try {
			outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
			bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("写出异常1", e);
		} finally {
			try {
				if (null != bos) {
					bos.close();
				}
				if (null !=response &&  null != response.getOutputStream()) {
					response.getOutputStream().close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				log.error("写出异常2", e2);
			}

		}
	}

	private Map<String, Map<String, String>> getDict(String dicts) {
		Map<String, String> dictMap = JsonUtil.parseJsonStrToMap(dicts);
		List<String> dictList = this.getDictCodes(dictMap);
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		if (null != dictList && dictList.size() > 0) {
			List<DictItemDTO> dictItemDTOList = baseClient.listByDictCode(dictList);
			dict = this.getDictMap(dictItemDTOList);
		}
		return dict;
	}

	private List<List<Object>> getList(JSONArray jsonArray, ArrayList<String> titleList, Map<String, String> dictMap,
			Map<String, Map<String, String>> dict) {
		List<List<Object>> dataList = new ArrayList<>();
		JSONObject jsonObject = null;
		ArrayList<Object> objects = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			objects = new ArrayList<>();
			for (String key : titleList) {
				if (jsonObject.containsKey(key) && null != jsonObject.get(key)) {
					if (dictMap.containsKey(key)) {
						objects.add(this.getDictValue(dictMap.get(key), String.valueOf(jsonObject.get(key)), dict));
					} else {
						objects.add(jsonObject.get(key));
					}
				} else {
					objects.add("");
				}

			}
			dataList.add(objects);
		}
		return dataList;
	}

	private JSONArray getDatas(String url, JSONObject queryParam) {
		long startTime = System.currentTimeMillis();
		String str = this.postHttp(url, queryParam);
		long endTime = System.currentTimeMillis();
		log.info("单次查询使用时长：" + (endTime - startTime) / 1000 + "秒");
		Map<String, Object> map = JsonUtil.parseJsonStrToMap(str);
		JSONArray jsonArray = new JSONArray();
		List<List<Object>> dataList = new ArrayList<>();
		if (map.get("code").equals(ResultCode.SUCCESS.getCode())) {
			JSONObject obj = (JSONObject) map.get("data");
			return (JSONArray) obj.get("list");
		}
		return null;
	}

	private Integer getTotal(String url, JSONObject queryParam) {
		queryParam.put("pageSize", 1);
		queryParam.put("pageNum", 1);
		String str = this.postHttp(url, queryParam);
		Map<String, Object> map = JsonUtil.parseJsonStrToMap(str);
		JSONArray jsonArray = new JSONArray();
		List<List<Object>> dataList = new ArrayList<>();
		if (map.get("code").equals(ResultCode.SUCCESS.getCode())) {
			JSONObject obj = (JSONObject) map.get("data");
			if (obj.containsKey("total")) {
				return (Integer) obj.get("total");
			}
		}
		return null;
	}

	private String postHttp(String url, JSONObject queryParam) {
		HttpHeaders requestHeaders = new HttpHeaders();
		// body
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication instanceof OAuth2Authentication) {
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
				String access_token = details.getTokenValue();
				requestHeaders.add(OAuthConstant.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE + " " + access_token);
			}
		}
		HttpEntity<String> requestEntity = new HttpEntity(queryParam, requestHeaders);
		return restTemplate.postForObject(url, requestEntity, String.class);
	}

	private void setDate(Map<String, Object> map, ArrayList<Object> list, String title) {
		Object date = map.get(title);
		if (null != date) {
			LocalDate assessmentDate = (LocalDate) date;
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			list.add(assessmentDate.format(dateTimeFormatter));
		} else {
			list.add("");
		}
	}

	/**
	 * 获取map字典
	 * 
	 * @param dictMap
	 * @return
	 */
	private List<String> getDictCodes(Map<String, String> dictMap) {
		List<String> list = new ArrayList<String>();
		if (null != dictMap && dictMap.size() > 0) {
			Iterator it = dictMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				list.add(dictMap.get(key));
			}
		}
		return list;
	}

	/**
	 * 字典转换Map集合
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, Map<String, String>> getDictMap(List<DictItemDTO> list) {
		Map<String, Map<String, String>> dictMap = null;
		Map<String, String> map = null;
		if (null != list && list.size() > 0) {
			dictMap = new HashMap<String, Map<String, String>>();
			for (DictItemDTO dto : list) {
				if (dictMap.containsKey(dto.getDictCode())) {
					map = dictMap.get(dto.getDictCode());
				} else {
					map = new HashMap<String, String>();
				}
				map.put(dto.getDictItemCode(), dto.getDictItemName());
				dictMap.put(dto.getDictCode(), map);
			}
		}
		return dictMap;
	}

	/**
	 * 获取字典值
	 * 
	 * @param dictCode
	 * @param value
	 * @param dictMap
	 * @return
	 */
	private String getDictValue(String dictCode, String value, Map<String, Map<String, String>> dictMap) {
		if (dictMap.containsKey(dictCode)) {
			Map<String, String> dict = dictMap.get(dictCode);
			if (dict.containsKey(value)) {
				return dict.get(value);
			}
		}
		return value;
	}

}
