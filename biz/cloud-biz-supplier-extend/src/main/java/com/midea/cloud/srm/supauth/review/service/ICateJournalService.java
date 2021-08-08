package com.midea.cloud.srm.supauth.review.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;

/**
*  <pre>
 *  品类日志表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-08 11:11:26
 *  修改内容:
 * </pre>
*/
public interface ICateJournalService extends IService<CateJournal> {

	public void batchSaveOrUpdateCateJournal(List<CateJournal> cateJournals,Long vendorId,Long formId, String formType);
}
