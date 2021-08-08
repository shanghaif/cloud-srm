package com.midea.cloud.srm.supauth.review.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;

/**
*  <pre>
 *  供应商可供货组织品类关系日志 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:01:27
 *  修改内容:
 * </pre>
*/
public interface IOrgCateJournalService extends IService<OrgCateJournal> {

	/**
	 * 批量保存修改信息
	 * @param list
	 * @param obj
	 * @param formType
	 */
	public void batchSaveOrUpdate(List<OrgCateJournal> list,Object obj,String formType);
	
	/**
	 * 根据单据类型及ID获取组织品类记录信息
	 * @param formId
	 * @param formType
	 * @return
	 */
	public List<OrgCateJournal> getOrgCateList(Long formId,String formType);
	
	/**
	 * 更新组织信息
	 * @param orgCateJournals
	 * @param reviewFormId
	 * @param formId
	 * @param formType
	 */
	public void generateOrgCategorys(List<OrgJournal> orgJournals,List<CateJournal> cateJournals,Long reviewFormId,Long formId,String formType);
	
	/**
	 * 更新组织信息
	 * @param orgCateJournals
	 * @param reviewFormId
	 * @param formId
	 * @param formType
	 */
	public void generateOrgCategorys(List<OrgCateJournal> orgCateJournals,Long reviewFormId,Long formId,String formType);
}
