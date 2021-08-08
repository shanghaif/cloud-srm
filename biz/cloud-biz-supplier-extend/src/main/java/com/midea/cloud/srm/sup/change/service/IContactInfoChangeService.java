package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.ContactInfoChange;

import java.util.List;

/**
 *  <pre>
 *  联系人信息变更表 服务类
 * </pre>
 *
 * @author zhuwl7@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 13:57:35
 *  修改内容:
 * </pre>
 */
public interface IContactInfoChangeService extends IService<ContactInfoChange> {

    ContactInfoChange saveOrUpdateContact(ContactInfoChange contactInfoChange, Long companyId,Long changeId);

    List<ContactInfoChange> getByChangeId(Long chanageId);

    void removeByChangeId(Long changeId);

    void removeByContactChangeId(Long contactChangeId);
}
