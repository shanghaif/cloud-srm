package com.midea.cloud.srm.report.order.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
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
public interface IOrderReportService {
    
    /**
     * 获取采购分析数据
     * @param dto
     * @return
     */
    public PurchaseResultDTO getPurchaseAnalysis(PurchaseParamDTO dto);
    
    /**
     * 获取组装品类占比
     * @param dto
     * @return
     */
    public List<PurchaseCategoryDTO> getPurchaseCategory(PurchaseParamDTO dto);
    
    /**
     * 订单确认排名
     * @param dto
     * @return
     */
    public PageInfo<PurchaseDetailDTO> queryOrderConfirm(PurchaseParamDTO dto);
    /**
     * 订单确认排名
     * @param dto
     * @return
     */
    public PageInfo<PurchaseDetailDTO> queryOrderWarehousing(PurchaseParamDTO dto);
    
    /**
     * 采购准时率排名
     * @param dto
     * @return
     */
    public PageInfo<PurchaseDetailDTO> queryOrderPunctuality(PurchaseParamDTO dto);
    
    /**
     * 采购准时率趋势
     * @param dto
     * @return
     */
    public PageInfo<PurchaseMonthRatio> queryOrderPunctualityYear(PurchaseParamDTO dto);
    
    /**
     * 品类采购金额占比排名
     * @param dto
     * @return
     */
    public PageInfo<PurchaseCategoryDTO> getPurchaseCategoryDetail(PurchaseParamDTO dto);
    
    /**
     * 采购订单明细
     * @param dto
     * @return
     */
    public PageInfo<PurchaseDetailInfoDTO> queryPurchaseDetailList(PurchaseParamDTO dto);

}
