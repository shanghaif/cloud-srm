package com.midea.cloud.srm.report.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailInfoDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseParamDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;

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
public interface OrderReportMapper {

    Integer getPurApplyCount(PurchaseParamDTO dto);

    List<OrderDetail> getPurchaseTotalPrice(PurchaseParamDTO dto);
    
    List<OrderDetailDTO> getDeliveryTotalPrice(PurchaseParamDTO dto);
    
    List<OrderDetailDTO> getExecution(PurchaseParamDTO dto);
    
    List<OrderDetailDTO> punctualityYear(PurchaseParamDTO dto);
    
    
    PurchaseDetailDTO getOrderRate(PurchaseParamDTO dto);
    
    
    
    BigDecimal punctuality(PurchaseParamDTO dto);
    
    List<OrderDetailDTO> queryPurchaseCategory(PurchaseParamDTO dto);
    
    List<PurchaseDetailInfoDTO> queryPurchaseDetailList(PurchaseParamDTO dto);

    List<ReturnDetailDTO> queryReturnDetailByOrderDetailId(ReturnDetailDTO dto);
    
    List<PurchaseDetailDTO> getOrderRateMap(PurchaseParamDTO dto);
    
    
    List<OrderDetailDTO> getExecutionList(PurchaseParamDTO dto);
    
    List<OrderDetailDTO> punctualityYearList(PurchaseParamDTO dto);
}


