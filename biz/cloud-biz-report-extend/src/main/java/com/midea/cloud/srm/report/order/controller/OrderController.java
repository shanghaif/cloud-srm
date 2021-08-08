package com.midea.cloud.srm.report.order.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseCategoryDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailInfoDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseMonthRatio;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseParamDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseResultDTO;
import com.midea.cloud.srm.report.order.service.IOrderReportService;

/**
 * <pre>
 *   采购订单明细表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

	 @Autowired
	 private IOrderReportService iOrderReportService;
	 
    /**
     * 采购分析接口
     * @param vendorId
     * @param categoryId
     * @return
     */
    @PostMapping("/getPurchaseAnalysis")
    public PurchaseResultDTO getPurchaseAnalysis(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
        return iOrderReportService.getPurchaseAnalysis(paramDto);
    }
    
    
    /**
     * 采购分析品类占比接口
     * @param vendorId
     * @param categoryId
     * @return
     */
    @PostMapping("/getPurchaseAnalysisCategory")
    public List<PurchaseCategoryDTO> getPurchaseAnalysisCategory(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
        return iOrderReportService.getPurchaseCategory(paramDto);
    }
    
   /**
    * 订单确认排名
    * @param paramDto
    * @return
    * @throws ParseException
    */
    @PostMapping("/queryOrderConfirm")
    public PageInfo<PurchaseDetailDTO> queryOrderConfirm(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
        return iOrderReportService.queryOrderConfirm(paramDto);
    }
    
   /**
    * 收货达成率排名
    * @param paramDto
    * @return
    * @throws ParseException
    */
    @PostMapping("/queryOrderWarehousing")
    public PageInfo<PurchaseDetailDTO> queryOrderWarehousing(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
        return iOrderReportService.queryOrderWarehousing(paramDto);
    }
    /**
     * 采购准时率排名
     * @param paramDto
     * @return
     * @throws ParseException
     */
    @PostMapping("/queryOrderPunctuality")
    public PageInfo<PurchaseDetailDTO> queryOrderPunctuality(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
    	return iOrderReportService.queryOrderPunctuality(paramDto);
    }
    /**
     * 采购准时率趋势
     * @param paramDto
     * @return
     * @throws ParseException
     */
    @PostMapping("/queryOrderPunctualityYear")
    public PageInfo<PurchaseMonthRatio> queryOrderPunctualityYear(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
    	return iOrderReportService.queryOrderPunctualityYear(paramDto);
    }
    
    
    /**
     * 品类采购金额占比排名
     * @param paramDto
     * @return
     * @throws ParseException
     */
    @PostMapping("/getPurchaseCategoryDetail")
    public PageInfo<PurchaseCategoryDTO> getPurchaseCategoryDetail(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
    	return iOrderReportService.getPurchaseCategoryDetail(paramDto);
    }
    
    
    /**
     * 采购订单明细
     * @param paramDto
     * @return
     * @throws ParseException
     */
    @PostMapping("/queryPurchaseDetailList")
    public PageInfo<PurchaseDetailInfoDTO> queryPurchaseDetailList(@RequestBody PurchaseParamDTO paramDto) throws ParseException {
    	return iOrderReportService.queryPurchaseDetailList(paramDto);
    }
}
