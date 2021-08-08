package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult;

/**
 * 寻源单服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ISourceFormService {

    /**
     * 生成 寻源单
     *
     * @param parameter 入参
     */
    GenerateSourceFormResult generateForm(GenerateSourceFormParameter parameter);
}
