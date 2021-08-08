package com.midea.cloud.srm.base.common.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

import com.midea.cloud.srm.model.common.ExportExcelParamCommon;

public interface IExportService {
	
//	/**
//     * 查询
//     *
//     * @param param
//     * @return
//     */
//    List<List<Object>> queryExportData(HttpServletRequest request,ExportExcelParamCommon param);
	
	/**
     * 开始导出
     * @param param
     * @param response
     */
    void exportStart(HttpServletRequest request,HttpServletResponse response,@RequestBody ExportExcelParamCommon excelParam)throws IOException;
    
    /**
     * 获取多语言列名
     *
     * @param param
     * @return
     */
    List<String> getMultilingualHeader(ExportExcelParamCommon param);

	void exportData(HttpServletRequest request, HttpServletResponse response, ExportExcelParamCommon param,
			List<String> head, String fileName);
    
}
