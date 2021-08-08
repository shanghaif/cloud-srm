package com.midea.cloud.srm.report.costreduction.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionParamDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionResultDTO;
import com.midea.cloud.srm.report.costreduction.service.ICostReductionService;

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
@RequestMapping("/costReduction")
public class CostReductionController extends BaseController {

	 @Autowired
	 private ICostReductionService iCostReductionService;
	 
    /**
     *	 采购分析接口
     * @return
     */
    @PostMapping("/getCostReductionAnalysis")
    public CostReductionResultDTO getCostReductionAnalysis(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
        return iCostReductionService.getCostReductionAnalysis(paramDto);
    }
    
    
    /**
     *	月降本趋势
     * @return
     */
    @PostMapping("/queryMonthsDetail")
    public PageInfo<CostReductionDTO> queryMonthsDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
        return iCostReductionService.queryMonthsDetail(paramDto);
    }
    /**
     *	年度累计降本率
     * @return
     */
    @PostMapping("/queryYearCumulativeDetail")
    public PageInfo<CostReductionDTO> queryYearCumulativeDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
    	return iCostReductionService.queryYearCumulativeDetail(paramDto);
    }
    /**
     *	品类降本达成率排名
     * @return
     */
    @PostMapping("/queryCategorRateDetail")
    public PageInfo<CostReductionDTO> queryCategorRateDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
    	return iCostReductionService.queryCategorRateDetail(paramDto);
    }
    
    /**
     *	品类降本金额区间占比
     * @return
     */
    @PostMapping("/queryCategorAmountDetail")
    public PageInfo<CostReductionDTO> queryCategorAmountDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
    	return iCostReductionService.queryCategorAmountDetail(paramDto);
    }
    
    /**
     *	品类上涨金额排名
     * @return
     */
    @PostMapping("/queryCategorUpAmountDetail")
    public PageInfo<CostReductionDTO> queryCategorUpAmountDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
    	return iCostReductionService.queryCategorUpAmountDetail(paramDto);
    }
    /**
     *	品类上涨金额排名
     * @return
     */
    @PostMapping("/queryCostReductionDetail")
    public PageInfo<CostReductionDTO> queryCostReductionDetail(@RequestBody CostReductionParamDTO paramDto) throws ParseException {
    	return iCostReductionService.queryCostReductionDetail(paramDto);
    }
}
