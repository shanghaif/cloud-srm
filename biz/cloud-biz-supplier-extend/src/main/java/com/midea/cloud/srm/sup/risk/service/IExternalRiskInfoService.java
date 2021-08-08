package com.midea.cloud.srm.sup.risk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.risk.dto.ExternalRiskInfoDto;
import com.midea.cloud.srm.model.supplier.risk.entity.ExternalRiskInfo;

import java.util.List;

/**
*  <pre>
 *  外部风险信息 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-03 19:41:36
 *  修改内容:
 * </pre>
*/
public interface IExternalRiskInfoService extends IService<ExternalRiskInfo> {
    /**
     * 获取供应商风险信息列表
     * @param vendorId
     * @return
     */
    List<ExternalRiskInfoDto> getExternalRiskInfoDto(Long vendorId);
}
