package com.midea.cloud.srm.sup.change.service;

import com.midea.cloud.srm.model.supplier.change.entity.BankInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.SiteInfoChange;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  地点信息变更 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-25 10:44:41
 *  修改内容:
 * </pre>
*/
public interface ISiteInfoChangeService extends IService<SiteInfoChange> {

    List<SiteInfoChange> getByChangeId(Long changeId);

    void saveOrUpdateSite(SiteInfoChange siteInfoChange, Long companyId, Long changeId);

    void removeByChangeId(Long changeId);

    void removeBySiteChangeId(Long siteChangeId);
}
