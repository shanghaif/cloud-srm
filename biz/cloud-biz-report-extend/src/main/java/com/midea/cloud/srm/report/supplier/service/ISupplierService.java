package com.midea.cloud.srm.report.supplier.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDetailDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierParamDTO;

/**
 * 
 * 
 * <pre>
 * 供应商分析service
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 上午8:27:12
 *	修改内容:
 * </pre>
 */
public interface ISupplierService {
    
	/*
     * @Description:供应商分析
     * @param param
     * @return
     */
    public SupplierAnalysisDTO getSupplierAnalysis(SupplierParamDTO param);
    
    /**
     * 供应商合作年限
     * @param param
     * @return
     */
    public List<SupplierAnalysisDetailDTO> queryCooperationDetail(SupplierParamDTO param);
    
    /**
     * 采购金额供方排名
     * @param param
     * @return
     */
    public PageInfo<SupplierAnalysisDetailDTO> queryPurchaseAmount(SupplierParamDTO param);
    
    /**
     * 品类供方数
     * @param param
     * @return
     */
    public PageInfo<SupplierAnalysisDetailDTO> queryPeformanceDetail(SupplierParamDTO param);
    
    /**
     * 供应商等级
     * @param param
     * @return
     */
    public PageInfo<SupplierAnalysisDetailDTO> queryCategoryDetail(SupplierParamDTO param);

    public PageInfo<SupplierAnalysisDetailDTO> queryCategoryDetailNew(SupplierParamDTO param);
    
    /**
     * 总的明细表
     * @param param
     * @return
     */
    public PageInfo<SupplierAnalysisDetailDTO> querySupplierDetail(SupplierParamDTO param);

}
