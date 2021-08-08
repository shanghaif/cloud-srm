package com.midea.cloud.srm.sup.riskraider.r7.service;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7Financial;
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
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
public interface IR7FinancialService extends IService<R7Financial> {
    void saveOrUpdateR7FromRaider(CompanyInfo companyInfo);


    R7FinancialDto getR7FinancialDtoByCompanyId(Long companyId);

    void saveR7ToEs(CompanyInfo companyInfo) throws Exception;
}
