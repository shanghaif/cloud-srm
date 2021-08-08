package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.BusinessInfoChange;

/**
*  <pre>
 *  业务信息变更 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:38
 *  修改内容:
 * </pre>
*/
public interface IBusinessInfoChangeService extends IService<BusinessInfoChange> {

    BusinessInfoChange getByChangeId(Long chanageId);

    void saveOrUpdateBusiness(BusinessInfoChange businessInfoChange, Long companyId, Long changeId);

    void removeByChangeId(Long changeId);
}
