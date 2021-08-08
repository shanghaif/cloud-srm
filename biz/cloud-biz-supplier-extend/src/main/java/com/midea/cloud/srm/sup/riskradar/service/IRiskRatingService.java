package com.midea.cloud.srm.sup.riskradar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.riskradar.dto.RiskRadarDto;
import com.midea.cloud.srm.model.supplier.riskradar.entity.RiskRating;

/**
*  <pre>
 *  风险评级表 服务类
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
public interface IRiskRatingService extends IService<RiskRating> {
    /**
     * 获取风险雷达信息
     * @param vendorId
     * @return
     */
    RiskRadarDto getRiskRadarDto(Long vendorId);
}
