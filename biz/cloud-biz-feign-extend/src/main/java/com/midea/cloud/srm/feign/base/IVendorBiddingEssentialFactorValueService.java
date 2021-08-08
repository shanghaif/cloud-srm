package com.midea.cloud.srm.feign.base;

import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValue;

import java.util.List;

/**
 * 接口定义 - 获取 供应商报价明细中的要素值集
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IVendorBiddingEssentialFactorValueService {

    /**
     * 获取 供应商报价明细中的要素值集
     * 一行报价对应对个要素明细
     *
     * @param bizId 业务ID`
     * @return  供应商报价明细中的要素值集
     */
    List<EssentialFactorValue> findEssentialFactorValues(long bizId);
}
