package com.midea.cloud.srm.base.formula.service;

import com.midea.cloud.srm.model.base.formula.enums.EssentialFactorFromType;

/**
 * 要素值计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IEssentialFactorCalculateService {

    /**
     * 选择要素值获取服务
     *
     * @param valueSourceType   要素值来源类型
     * @return One kind of {@link IEssentialFactorValueService}.
     */
    IEssentialFactorValueService select(EssentialFactorFromType valueSourceType);
}
