package com.midea.cloud.srm.report.costreduction.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionParamDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionResultDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseCategoryDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailInfoDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseMonthRatio;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseParamDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseResultDTO;

/**
 * <pre>
 *  采购订单报表
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/23 19:12
 *  修改内容:
 * </pre>
 */
public interface ICostReductionService {
    
	public CostReductionResultDTO getCostReductionAnalysis(CostReductionParamDTO param);
	
	public PageInfo<CostReductionDTO> queryMonthsDetail(CostReductionParamDTO param);
	
	public PageInfo<CostReductionDTO> queryYearCumulativeDetail(CostReductionParamDTO param);
	
	/**
	 * 品类降本达成率
	 */
	public PageInfo<CostReductionDTO> queryCategorRateDetail(CostReductionParamDTO param);
	/**
	 * 品类降本金额区间占比
	 */
	public PageInfo<CostReductionDTO> queryCategorAmountDetail(CostReductionParamDTO param);
	
	/**
	 * 品类上涨金额排名
	 */
	public PageInfo<CostReductionDTO> queryCategorUpAmountDetail(CostReductionParamDTO param);
	
	/**
	 * 总的明细表
	 */
	public PageInfo<CostReductionDTO> queryCostReductionDetail(CostReductionParamDTO param);
}
