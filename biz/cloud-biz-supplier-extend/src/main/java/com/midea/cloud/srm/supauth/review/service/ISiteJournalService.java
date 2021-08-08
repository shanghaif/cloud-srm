package com.midea.cloud.srm.supauth.review.service;

import com.midea.cloud.srm.model.supplierauth.review.entity.BankJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteJournal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  地点信息日志表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-23 16:58:56
 *  修改内容:
 * </pre>
*/
public interface ISiteJournalService extends IService<SiteJournal> {

    /**
     * 根据reviewFormId, vendorId获取供应商地点信息
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    List<SiteJournal> listSiteJournal(Long reviewFormId, Long vendorId);

}
