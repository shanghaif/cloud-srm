package com.midea.cloud.srm.supauth.entry.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.CatalogStatusType;
import com.midea.cloud.common.enums.CategoryStatus;
import com.midea.cloud.common.enums.OrgCateBillType;
import com.midea.cloud.common.enums.review.FormType;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfigRecord;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryRelationRecord;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;
import com.midea.cloud.srm.supauth.entry.mapper.EntryConfigRecordMapper;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigRecordService;
import com.midea.cloud.srm.supauth.entry.service.IEntryRelationRecordService;
import com.midea.cloud.srm.supauth.review.service.ICateJournalService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;
import com.midea.cloud.srm.supauth.review.service.IOrgJournalService;

/**
*  <pre>
 *  供应商准入记录 服务实现类
 * </pre>
*
* @author kuangzm
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
@Service
public class EntryConfigRecordServiceImpl extends ServiceImpl<EntryConfigRecordMapper, EntryConfigRecord> implements IEntryConfigRecordService {
	
	@Autowired
    private IEntryRelationRecordService iEntryRelationRecordService;

	@Autowired
    private IOrgJournalService iOrgJournalService;
	
	@Autowired
    private ICateJournalService iCateJournalService;
	
	@Autowired
    private IOrgCateJournalService iOrgCateJournalService;

	/**
	 * 判断是否结束
	 * @return
	 */
	@Override
	public boolean judgeIsEnd(Long reviewFormId,Long formId,String type) {
		EntryConfigRecord record = this.getRecord(reviewFormId);
		List<EntryRelationRecord> relations = null;
		List<EntryRelationRecord> template = null;
		QueryWrapper qw = new QueryWrapper();
		qw.eq("RECORD_ID", record.getRecordId());
		relations = iEntryRelationRecordService.list(qw);
		qw = new QueryWrapper();
		qw.eq("FORM_ID", formId);
		List<EntryRelationRecord> checkList = new ArrayList<EntryRelationRecord>();
		
		//更新记录
		if (OrgCateBillType.REVIEW_FORM.getValue().equals(type)) {
			return this.judgeIsEnd(record,relations);
		} else {
			if (OrgCateBillType.SITE_FORM.getValue().equals(type)) {
				qw.eq("FORM_TYPE", FormType.AUTH_FORM.name());
				List<OrgJournal> orgs = iOrgJournalService.list(qw);
				List<CateJournal> cates = iCateJournalService.list(qw);
				if (null != orgs && null != cates) {
					for (OrgJournal org : orgs) {
						for (CateJournal cate : cates) {
							for (EntryRelationRecord relation : relations) {
								if (org.getOrgId().equals(relation.getOrganizationId()) && cate.getCategoryId().equals(relation.getCategoryId())) {
									checkList.add(relation);
									break;
								}
							}
						}
					}
				}
				
			} else if (OrgCateBillType.SAMPLE_FORM.getValue().equals(type)) {
				List<OrgCateJournal> ocList = iOrgCateJournalService.getOrgCateList(formId,OrgCateBillType.SAMPLE_FORM.getValue());
				if (null != ocList && ocList.size() > 0) {
					for (OrgCateJournal oc : ocList) {
						for (EntryRelationRecord relation : relations) {
							if (oc.getOrgId().equals(relation.getOrganizationId()) && oc.getCategoryId().equals(relation.getCategoryId())) {
								checkList.add(relation);
								break;
							}
						}
					}
				}
			} else if (OrgCateBillType.MATERIAL_FORM.getValue().equals(type)) {
				List<OrgCateJournal> ocList = iOrgCateJournalService.getOrgCateList(formId,OrgCateBillType.MATERIAL_FORM.getValue());
				if (null != ocList && ocList.size() > 0) {
					for (OrgCateJournal oc : ocList) {
						for (EntryRelationRecord relation : relations) {
							if (oc.getOrgId().equals(relation.getOrganizationId()) && oc.getCategoryId().equals(relation.getCategoryId())) {
								checkList.add(relation);
								break;
							}
						}
					}
				}
			}
			//更新对应的状态为Y
			for (EntryRelationRecord relation : checkList) {
				if (OrgCateBillType.SITE_FORM.getValue().equals(type)) {
					relation.setIfAuth("Y");
				} else if (OrgCateBillType.SAMPLE_FORM.getValue().equals(type)) {
					relation.setIfAuthSample("Y");
				} else if (OrgCateBillType.MATERIAL_FORM.getValue().equals(type)) {
					relation.setIfMaterial("Y");
				}
			}
			this.iEntryRelationRecordService.updateBatchById(checkList);
			
			return this.judgeIsEnd(record,checkList);
		}
	}
	
	private boolean judgeIsEnd(EntryConfigRecord record,List<EntryRelationRecord> relations) {
		if (null != relations && relations.size() > 0) {
			for (EntryRelationRecord relation : relations) {
				//供应商评审
				if ("Y".equals(record.getIfAuth())) {
					if (null == relation.getIfAuth() || !"Y".equals(relation.getIfAuth())) {
						return false;
					}
				}
				//样品确认
				if ("Y".equals(record.getIfAuthSample())) {
					if (null == relation.getIfAuthSample() || !"Y".equals(relation.getIfAuthSample())) {
						return false;
					}
				}
				//物料试用
				if ("Y".equals(record.getIfMaterial())) {
					if (null == relation.getIfMaterial() || !"Y".equals(relation.getIfMaterial())) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public EntryConfigRecord getRecord(Long businessId) {
		QueryWrapper qw = new QueryWrapper();
		qw.eq("REVIEW_FORM_ID", businessId);
    	EntryConfigRecord record = this.getOne(qw);
    	return record;
	}
	
	@Override
	public EntryConfigRecord getRecord(Long businessId,String type) {
		QueryWrapper qw = new QueryWrapper();
    	if (type.equals("REVIEW")) {
    		qw.eq("REVIEW_FORM_ID", businessId);
    	} else if (type.equals("AUTH")) {
    		qw.eq("SITE_FORM_ID", businessId);
    	} else if (type.equals("SAMPLE")) {
    		qw.eq("SAMPLE_ID", businessId);
    	} else if (type.equals("MATERIAL")) {
    		qw.eq("MATERIAL_TRIAL_ID", businessId);
    	}
    	EntryConfigRecord record = this.getOne(qw);
    	return record;
	}
}
