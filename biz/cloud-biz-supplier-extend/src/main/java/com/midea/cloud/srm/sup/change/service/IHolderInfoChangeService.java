package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.HolderInfoChange;

/**
*  <pre>
 *  股东信息变更 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 14:13:08
 *  修改内容:
 * </pre>
*/
public interface IHolderInfoChangeService extends IService<HolderInfoChange> {

    HolderInfoChange getByChangeId(Long chanageId);

    void saveOrUpdateHolder(HolderInfoChange holderInfoChange, Long companyId, Long changeId);

    void removeByChangeId(Long changeId);
}
