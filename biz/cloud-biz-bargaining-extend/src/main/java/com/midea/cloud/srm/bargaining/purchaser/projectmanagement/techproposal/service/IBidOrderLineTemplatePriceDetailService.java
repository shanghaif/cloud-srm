package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplatePriceDetailVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 供应商投标行[模型报价]明细服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IBidOrderLineTemplatePriceDetailService {

    /**
     * 根据[投标行ID]获取 供应商投标行[模型报价]明细
     *
     * @param lineId    投标行ID
     * @return  供应商投标行[模型报价]明细
     */
    List<BidOrderLineTemplatePriceDetailVO> findDetailsByLineId(Long lineId);

    /**
     * 存储 供应商投标行[模型报价]明细集
     *
     * @param details   供应商投标行[模型报价]明细集
     */
    void saveDetails(List<BidOrderLineTemplatePriceDetailVO> details);

    /**
     * 计算 指定投标行含税总价
     *
     * @param lineId    投标行ID
     * @return  投标行含税总价
     */
    BigDecimal calculateOrderLineTaxTotalPrice(Long lineId);
}
