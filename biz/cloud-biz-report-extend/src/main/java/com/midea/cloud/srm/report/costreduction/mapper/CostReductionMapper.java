package com.midea.cloud.srm.report.costreduction.mapper;

import java.util.List;

import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionParamDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetCategoryInfoDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetMaterialInfoDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.OrderWarehouseDTO;

/**
 * <pre>
 *  采购订单表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface CostReductionMapper {

    List<OrderWarehouseDTO> queryWarehouse(CostReductionParamDTO dto);
    
    List<CrSetMaterialInfoDTO> queryMaterialInfo(CostReductionParamDTO dto);
    
    List<CrSetCategoryInfoDTO> queryCategoryInfo(CostReductionParamDTO dto);
}


