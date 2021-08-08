package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteFormRecord;

/**
*  <pre>
 *  现场评审记录 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-15 17:14:30
 *  修改内容:
 * </pre>
*/
public interface ISiteFormRecordService extends IService<SiteFormRecord> {

    void deleteBySiteFormRecordId(Long siteFormRecordId);
}
