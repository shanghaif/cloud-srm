package com.midea.cloud.srm.sup.riskraider.r8.service;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8Discredit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  企业财务信息表 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
public interface IR8DiscreditService extends IService<R8Discredit> {

    void saveOrUpdateR8FromRaider(CompanyInfo companyInfo);

    R8DiscreditDto getR8DiscreditDtoByCompanyId(Long companyId);

}
