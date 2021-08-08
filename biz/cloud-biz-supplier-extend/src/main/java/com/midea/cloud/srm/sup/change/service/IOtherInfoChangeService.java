package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.OtherInfoChange;

/**
*  <pre>
 *  其他信息变更表 服务类
 * </pre>
*
* @author zhuwl7@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 14:15:34
 *  修改内容:
 * </pre>
*/
public interface IOtherInfoChangeService extends IService<OtherInfoChange> {
    OtherInfoChange saveOrUpdateOther(OtherInfoChange otherInfoChange, Long companyId, Long changeId);

    OtherInfoChange getByChangeId(Long chanageId);

    void removeByChangeId(Long changeId);
}
