package com.midea.cloud.srm.report.supplier.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDetailDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierCategoryDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierMapDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierMonthsDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierParamDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierPerformanceDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;

/**
 * 
 * 
 * <pre>
 * 。供应商分析
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 上午8:29:16
 *	修改内容:
 * </pre>
 */
public interface SupplierMapper {

    BigDecimal getSupplierCount(SupplierParamDTO param);
    
    List<OrderDetailDTO> getActiveCount(SupplierParamDTO param);
    
    BigDecimal getAddCount(SupplierParamDTO param);
    
    BigDecimal getOutCount(SupplierParamDTO param);
    
    List<SupplierMonthsDTO> getCompanyMonths(SupplierParamDTO param);
    
    List<SupplierMapDTO> getMap(SupplierParamDTO param);
    
    List<SupplierPerformanceDTO> getPeformance(SupplierParamDTO param);
    
    List<SupplierCategoryDTO> getCategory(SupplierParamDTO param);
    
    List<OrderDetailDTO> getWareHouse(SupplierParamDTO param);
    
    List<SupplierAnalysisDetailDTO> queryCooperationDetail(SupplierParamDTO param);
    
    List<SupplierAnalysisDetailDTO> queryPeformanceDetail(SupplierParamDTO param);
    
    List<SupplierAnalysisDetailDTO> queryCategoryDetail(SupplierParamDTO param);
    
    List<SupplierAnalysisDetailDTO> querySupplierDetail(SupplierParamDTO param);

    List<SupplierAnalysisDetailDTO> querySupplierDetailExclude(SupplierParamDTO param);
}


