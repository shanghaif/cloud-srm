package com.midea.cloud.srm.supauth.entry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfigRecord;

/**
*  <pre>
 *  供应商准入记录 服务类
 * </pre>
*
* kuangzm
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 21, 2021 5:44:19 PM
 *  修改内容:
 * </pre>
*/
public interface IEntryConfigRecordService extends IService<EntryConfigRecord> {
	/**
	 * 判断是否结束
	 * @param businessId
	 * @param type
	 * @return
	 */
	public boolean judgeIsEnd(Long reviewFormId,Long formId,String type);
	
	/**
	 * 获取供应商记录
	 * @param businessId
	 * @param type
	 * @return
	 */
	public EntryConfigRecord getRecord(Long businessId);
	
	/**
	 * 获取历史流程信息
	 * @param businessId
	 * @param type
	 * @return
	 */
	public EntryConfigRecord getRecord(Long businessId,String type);
}
