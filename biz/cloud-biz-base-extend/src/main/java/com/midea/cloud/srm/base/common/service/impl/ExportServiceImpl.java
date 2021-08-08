package com.midea.cloud.srm.base.common.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.constants.OAuthConstant;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.srm.base.categorydv.utils.ExportUtils;
import com.midea.cloud.srm.base.common.service.IExportService;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.common.ExportExcelParamCommon;

@Service
public class ExportServiceImpl implements IExportService {

	@Resource
	private RibbonTemplateWrapper ribbonTemplate;

	@Resource
	private RestTemplate restTemplate;

	@Autowired
	private IDictItemService iDictItemService;

	private static int pageSize = 5000;

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
		// 获取输出流
		ServletOutputStream outputStream = null;
		ExcelWriter excelWriter = null;
		WriteSheet sheet = null;
		try {
			outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
			List<List<String>> list = new ArrayList<>();
			if (head != null) {
				head.forEach(h -> list.add(Collections.singletonList(h)));
				excelWriter = EasyExcel.write(outputStream).build();
				sheet = EasyExcel.writerSheet(1).build();
				sheet.setHead(list);
				sheet.setSheetName(fileName);
			}

			// kuangzm 字典转换
			String dicts = (String) param.getDictCodes();
			Map<String, String> dictMap = JsonUtil.parseJsonStrToMap(dicts);
			Map<String, Map<String, String>> dict = this.getDict(dicts);
			
			JSONArray jsonArray = null;

			JSONObject queryParam = (JSONObject) param.getQueryParam();
			if (null == queryParam) {
				queryParam = new JSONObject();
			}
			boolean flag = StringUtil.notEmpty(queryParam.get("pageSize"));
			
			List<List<Object>> dataList = null;

			if (!flag) { // 不分页
				Integer total = getTotal(param.getUrl(), queryParam);
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
				jsonArray = this.getDatas(param.getUrl(), queryParam);
				dataList = getList(jsonArray, param.getTitleList(), dictMap, dict);
				excelWriter.write(dataList, sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}

	}
	
	
	private Map<String, Map<String, String>> getDict(String dicts) {
		Map<String, String> dictMap = JsonUtil.parseJsonStrToMap(dicts);
		List<String> dictList = this.getDictCodes(dictMap);
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		if (null != dictList && dictList.size() > 0) {
			List<DictItemDTO> dictItemDTOList = iDictItemService.listByDictCode(dictList);
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
				}

			}
			dataList.add(objects);
		}
		return dataList;
	}

	private JSONArray getDatas(String url, JSONObject queryParam) {
		long a = System.currentTimeMillis();
		String str = this.postHttp(url, queryParam);
		long b = System.currentTimeMillis();
		System.out.println("单次查询使用时长：" + (b - a) / 1000 + "秒");
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

	@Override
	public List<String> getMultilingualHeader(ExportExcelParamCommon param) {
		LinkedHashMap<String, String> titles = ExportUtils.getCategoryDvTitles();
		return param.getMultilingualHeader(param, titles);
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
