package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
*  <pre>
 *  联系人信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:55:06
 *  修改内容:
 * </pre>
*/
public interface IContactInfoService extends IService<ContactInfo> {
    void saveOrUpdateContact(ContactInfo contactInfo, Long companyId);

    List<ContactInfo> getByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    List<ContactInfo> listContactInfoByParam(List<Long> vendorIdList);

    ContactInfo getContactInfoByCompanyId(Long companyId);

    void saveOrUpdateContact(ContactInfo contactInfo);

    List<ContactInfo> getContactInfosByParam(ContactInfo contactInfo);
}
