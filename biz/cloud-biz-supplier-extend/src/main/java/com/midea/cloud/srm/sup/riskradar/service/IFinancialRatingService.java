package com.midea.cloud.srm.sup.riskradar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.riskradar.entity.FinancialRating;

import java.util.List;

/**
*  <pre>
 *  财务评级表 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 14:14:17
 *  修改内容:
 * </pre>
*/
public interface IFinancialRatingService extends IService<FinancialRating> {
    /**
     * 获取供应商近两年财务数据
     * @return
     */
    List<FinancialRating> getLastTwoYearData(Long vendorId);
}
