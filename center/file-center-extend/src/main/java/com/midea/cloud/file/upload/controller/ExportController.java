package com.midea.cloud.file.upload.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.file.upload.service.IExportService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParamCommon;

@RestController
@RequestMapping("/common-export")
public class ExportController extends BaseController {

	@Autowired
	private IExportService iExportService;
	/**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,@RequestBody ExportExcelParamCommon excelParam) throws Exception {
		iExportService.exportStart(request,response,excelParam);
	}
}
