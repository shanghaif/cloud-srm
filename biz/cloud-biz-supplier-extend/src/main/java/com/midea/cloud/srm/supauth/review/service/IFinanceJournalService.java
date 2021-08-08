package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.review.entity.FinanceJournal;

import java.util.List;

/**
*  <pre>
 *  资质审查财务信息日志表 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:03
 *  修改内容:
 * </pre>
*/
public interface IFinanceJournalService extends IService<FinanceJournal> {

    /**
     * 根据reviewFormId,vendorId获取财务信息
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    List<FinanceJournal> listFinanceJournal(Long reviewFormId, Long vendorId);
}
