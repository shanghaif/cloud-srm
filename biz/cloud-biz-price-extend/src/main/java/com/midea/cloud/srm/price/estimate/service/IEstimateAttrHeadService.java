package com.midea.cloud.srm.price.estimate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateAttrHead;

import java.util.List;

/**
*  <pre>
 *  价格估算属性头表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
public interface IEstimateAttrHeadService extends IService<EstimateAttrHead> {
    /**
     * 通过核价模型头id查找要素属性及维护内容
     * @param priceModelHeadId
     * @return
     */
    List<EstimateAttrHead> get(Long priceModelHeadId);
}
