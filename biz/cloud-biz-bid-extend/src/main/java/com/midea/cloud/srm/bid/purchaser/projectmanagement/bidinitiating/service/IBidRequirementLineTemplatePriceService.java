package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.BidRequirementLineTemplatePriceVO;

import java.util.List;

/**
 * 寻源需求行[模型报价]明细服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IBidRequirementLineTemplatePriceService {

    /**
     * 根据[寻源需求行ID]获取 寻源需求行[模型报价]明细
     *
     * @param lineId    寻源需求行ID
     * @return  寻源需求行[模型报价]明细
     */
    List<BidRequirementLineTemplatePriceVO> findDetailsByLineId(Long lineId);

    /**
     * 存储 寻源需求行[模型报价]明细集
     *
     * @param details   寻源需求行[模型报价]明细集
     */
    void saveDetails(List<BidRequirementLineTemplatePriceVO> details);

    /**
     * 删除 寻源需求行[模型报价]明细头集
     *
     * @param detailIds  寻源需求行[模型报价]明细头ID集
     */
    void deleteDetails(Long[] detailIds);

    /**
     * 删除 寻源需求行[模型报价]明细行集
     *
     * @param detailLineIds  寻源需求行[模型报价]明细行ID集
     */
    void deleteDetailLines(Long[] detailLineIds);
}
