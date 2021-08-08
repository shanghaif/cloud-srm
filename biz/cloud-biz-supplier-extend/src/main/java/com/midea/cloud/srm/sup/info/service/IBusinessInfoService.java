package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.BusinessInfo;

import java.util.List;

/**
*  <pre>
 *  业务信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-07 10:30:12
 *  修改内容:
 * </pre>
*/
public interface IBusinessInfoService extends IService<BusinessInfo> {

    void saveOrUpdateBusinessInfo(BusinessInfo businessInfo, Long companyId);

    BusinessInfo getByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    List<BusinessInfo> listByCompanyId(Long companyId);
}
