package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfoDetail;

/**
*  <pre>
 *  基本信息从表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:47:45
 *  修改内容:
 * </pre>
*/
public interface ICompanyInfoDetailService extends IService<CompanyInfoDetail> {

    CompanyInfoDetail getByCompanyId(Long companyId);
}
