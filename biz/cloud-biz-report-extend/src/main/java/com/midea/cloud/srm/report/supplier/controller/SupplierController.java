package com.midea.cloud.srm.report.supplier.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDetailDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierParamDTO;
import com.midea.cloud.srm.report.supplier.service.ISupplierService;

/**
 * 
 * 
 * <pre>
 * 供应商分析
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 上午8:25:32
 *	修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController {
	
	@Autowired
	private ISupplierService iSupplierService;
	
	/*
	 * @Description:供应商分析
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/getPurchaseAnalysis")
    public SupplierAnalysisDTO getPurchaseAnalysis(@RequestBody SupplierParamDTO param) throws ParseException {
        return iSupplierService.getSupplierAnalysis(param);
    }
    
    /*
	 * @Description:供应商合作年限
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/queryCooperationDetail")
    public PageInfo<SupplierAnalysisDetailDTO> queryCooperationDetail(@RequestBody SupplierParamDTO param) throws ParseException {
        return new PageInfo(iSupplierService.queryCooperationDetail(param));
    }
    
    /*
	 * @Description:采购金额供方排名
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/queryPurchaseAmount")
    public PageInfo<SupplierAnalysisDetailDTO> queryPurchaseAmount(@RequestBody SupplierParamDTO param) throws ParseException {
        return iSupplierService.queryPurchaseAmount(param);
    }
    
    /*
	 * @Description:供应商合作年限
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/queryPeformanceDetail")
    public PageInfo<SupplierAnalysisDetailDTO> queryPeformanceDetail(@RequestBody SupplierParamDTO param) throws ParseException {
        return iSupplierService.queryPeformanceDetail(param);
    }
    
    /*
	 * @Description:总的明细表
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/querySupplierDetail")
    public PageInfo<SupplierAnalysisDetailDTO> querySupplierDetail(@RequestBody SupplierParamDTO param) throws ParseException {
        return iSupplierService.querySupplierDetail(param);
    }
    
    /*
	 * @Description:总的明细表
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
    @PostMapping("/queryCategoryDetail")
    public PageInfo<SupplierAnalysisDetailDTO> queryCategoryDetail(@RequestBody SupplierParamDTO param) throws ParseException {
        return iSupplierService.queryCategoryDetailNew(param);
    }
}
