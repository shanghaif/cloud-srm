package com.midea.cloud.srm.base.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  汇率表（最新的汇率，持续更新） 服务类
 * </pre>
*
* @author xiexh12@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 16:29:44
 *  修改内容:
 * </pre>
*/
public interface ILatestGidailyRateService extends IService<LatestGidailyRate> {

    List<LatestGidailyRate> getRMBRate(String toCurrencyCode);
    
    Map<String,List<LatestGidailyRate>> getCurrency(LatestGidailyRate gidailyRate);

    /**
     * 根据目标币种和汇率类型获取汇率
     * @param toCurrencyCode 目标币种编码
     * @param conversionType 汇率类型
     * @return
     */
    Map<String,LatestGidailyRate> getLatestGidailyRate(String toCurrencyCode,String conversionType);
}
