package com.midea.cloud.srm.base.formula.service;

import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValueParameter;

/**
 * 要素值获取服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IEssentialFactorValueService {

    /**
     * 要素值获取
     *
     * @param parameter 获取参数
     * @return  要素值
     */
    String value(EssentialFactorValueParameter parameter);
}
