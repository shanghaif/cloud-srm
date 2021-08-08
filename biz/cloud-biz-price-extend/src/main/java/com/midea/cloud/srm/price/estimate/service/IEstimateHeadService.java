package com.midea.cloud.srm.price.estimate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateHead;
import org.springframework.web.bind.annotation.RequestBody;

/**
*  <pre>
 *  价格估算头表 服务类
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
public interface IEstimateHeadService extends IService<EstimateHead> {

    /**
     * 分页查询
     * @param estimateHead
     * @return
     */
    PageInfo<EstimateHead> listPage( EstimateHead estimateHead);

    /**
     * 新增
     * @param estimateHead
     * @return
     */
    Long add(EstimateHead estimateHead);

    /**
     * 获取详情
     * @param estimateHeadId
     * @return
     */
    EstimateHead get(Long estimateHeadId);

    /**
     * 修改
     * @param estimateHead
     */
    Long modify(EstimateHead estimateHead);


}
