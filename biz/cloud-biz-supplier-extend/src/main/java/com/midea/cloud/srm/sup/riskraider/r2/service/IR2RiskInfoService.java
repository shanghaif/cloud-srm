package com.midea.cloud.srm.sup.riskraider.r2.service;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r2.dto.R2RiskInfoDto;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  风险扫描结果表 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
public interface IR2RiskInfoService extends IService<R2RiskInfo> {
    /**
     * 从风险雷达获取数据保存或更新
    */
    void saveOrUpdateRiskFromRaider(CompanyInfo companyInfo);

    /**
     * 从风险雷达获取数据
     */
    R2RiskInfoDto getRiskInfoDtoByCompanyId(Long companyId);
}
