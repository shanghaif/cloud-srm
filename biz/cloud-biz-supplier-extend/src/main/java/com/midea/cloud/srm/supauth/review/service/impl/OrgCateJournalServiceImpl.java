package com.midea.cloud.srm.supauth.review.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.OrgCateBillType;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.review.QuaReviewType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategorySaveDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigRecordService;
import com.midea.cloud.srm.supauth.review.mapper.OrgCateJournalMapper;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;

import lombok.extern.slf4j.Slf4j;

/**
*  <pre>
 *  供应商可供货组织品类关系日志 服务实现类
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
@Service
@Slf4j
public class OrgCateJournalServiceImpl extends ServiceImpl<OrgCateJournalMapper, OrgCateJournal> implements IOrgCateJournalService {
	
	@Autowired
	private IEntryConfigRecordService iEntryConfigRecordService;
	@Autowired
    private SupplierClient supplierClient;
	
	@Autowired
    private IReviewFormService iReviewFormService;

	/**
	 * 批量保存修改信息
	 * @param list
	 * @param obj
	 * @param formType
	 */
	@Override
	public void batchSaveOrUpdate(List<OrgCateJournal> list,Object obj,String formType) {
		//先删除
		QueryWrapper<OrgCateJournal> qw = new QueryWrapper<OrgCateJournal>();
		qw.eq("ORG_CATE_BILL_TYPE", formType);
		Long orgCateBillId = null;
		Long vendorId = null;
		if (OrgCateBillType.SAMPLE_FORM.getValue().equals(formType)) {
			QuaSample quaSample = (QuaSample)obj;
			orgCateBillId = quaSample.getSampleId();
			vendorId = quaSample.getVendorId();
		} else if (OrgCateBillType.MATERIAL_FORM.getValue().equals(formType)) {
			MaterialTrial materialTrial = (MaterialTrial)obj;
			orgCateBillId =materialTrial.getMaterialTrialId();
			vendorId = materialTrial.getVendorId();
		}
		Assert.notNull(orgCateBillId, "单据ID不存在");
		qw.eq("ORG_CATE_BILL_ID", orgCateBillId);
		this.remove(qw);

		//再添加
		if (null != list) {
			for (OrgCateJournal journal : list) {
				if (null == journal.getOrgCateJournalId()) {
					journal.setOrgCateJournalId(IdGenrator.generate());
				}
				journal.setOrgCateBillType(formType);
				journal.setOrgCateBillId(orgCateBillId);
				journal.setVendorId(vendorId);
			}
			this.saveBatch(list);
		}
	}
	/**
	 * 根据单据类型及ID获取组织品类记录信息
	 * @param formId
	 * @param formType
	 * @return
	 */
	@Override
	public List<OrgCateJournal> getOrgCateList(Long formId,String formType) {
		QueryWrapper<OrgCateJournal> qw = new QueryWrapper<OrgCateJournal>();
		qw.eq("ORG_CATE_BILL_TYPE", formType);
		qw.eq("ORG_CATE_BILL_ID", formId);
		return this.list(qw);
	}

	/**
	 * 更新组织信息
	 * @param cateJournals
	 * @param reviewFormId
	 * @param formId
	 * @param formType
	 */
	@Override
	public void generateOrgCategorys(List<OrgJournal> orgJournals,List<CateJournal> cateJournals,Long reviewFormId,Long formId,String formType) {
        if (null != orgJournals && null != cateJournals) {
        	List<OrgCateJournal> list = new ArrayList<OrgCateJournal>();
        	OrgCateJournal orgCate = null;
        	for (OrgJournal org : orgJournals) {
        		for (CateJournal cate : cateJournals) {
        			orgCate = new OrgCateJournal();
        			orgCate.setVendorId(org.getVendorId());
        			orgCate.setOrgId(org.getOrgId());
        			orgCate.setOrgName(org.getOrgName());
        			orgCate.setOrgCode(org.getOrgCode());
//        			orgCate.setFullPathId(org.ge)
        			orgCate.setCategoryId(cate.getCategoryId());
        			orgCate.setCategoryCode(cate.getCategoryCode());
        			orgCate.setCategoryName(cate.getCategoryName());
        			orgCate.setCategoryFullName(cate.getCategoryFullName());
        			list.add(orgCate);
            	}
        	}
        	this.generateOrgCategorys(list, reviewFormId, formId, formType);
        }
    }
	
	/**
	 * 更新组织信息
	 * @param orgCateJournals
	 * @param reviewFormId
	 * @param formId
	 * @param formType
	 */
	@Override
	public void generateOrgCategorys(List<OrgCateJournal> orgCateJournals,Long reviewFormId,Long formId,String formType) {
		if (null == reviewFormId) {
			return;
		}
        //生成供应商合作品类
        //根据资质审查类型做对应的逻辑校验
        Boolean isEnd = iEntryConfigRecordService.judgeIsEnd(reviewFormId,formId, formType);
        ReviewForm reviewForm =iReviewFormService.getById(reviewFormId);
        String serviceStatus = "";
        
        if (QuaReviewType.ONETIME_VENDOR.name().equals(reviewForm.getQuaReviewType())) {
            serviceStatus = CategoryStatus.ONE_TIME.name();
        } else if (isEnd) {
            serviceStatus = CategoryStatus.GREEN.name();
        } else {
            serviceStatus = CategoryStatus.VERIFY.name();
        }
        if (OrgCateBillType.REVIEW_FORM.getValue().equals(formType)) {
        	updateOrgCateInfo(serviceStatus, orgCateJournals, reviewForm, formId, formType);
        } else {
        	if (isEnd) {
        		updateOrgCateInfo(serviceStatus, orgCateJournals, reviewForm, formId, formType);
        	}
        }
    }

	private void updateOrgCateInfo(String serviceStatus,List<OrgCateJournal> orgCateJournals,ReviewForm reviewForm,Long formId,String formType ) {
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                OrgCategorySaveDTO orgCategorySaveDTO = new OrgCategorySaveDTO();
                orgCategorySaveDTO.setOrgCategory(new OrgCategory()
                        .setCompanyId(orgCateJournal.getVendorId())
                        .setOrgId(orgCateJournal.getOrgId())
                        .setOrgName(orgCateJournal.getOrgName())
                        .setOrgCode(orgCateJournal.getOrgCode())
                        .setCategoryId(orgCateJournal.getCategoryId())
                        .setCategoryCode(orgCateJournal.getCategoryCode())
                        .setCategoryName(orgCateJournal.getCategoryName())
                        .setCategoryFullName(orgCateJournal.getCategoryFullName())
                        .setFullPathId(orgCateJournal.getFullPathId())
                        .setServiceStatus(serviceStatus));
                List<OrgCategoryChange> orgCategoryChanges = new ArrayList<>();
                orgCategoryChanges.add(new OrgCategoryChange().setFormType(formType).setFormNum(reviewForm.getReviewFormNumber()));
                orgCategorySaveDTO.setOrgCategoryChanges(orgCategoryChanges);
                orgCategorySaveDTO.setReviewForm(reviewForm);
                log.info("-------------------资质审查,审批通过,生成品类关系-------入参:" + JsonUtil.entityToJsonStr(orgCategorySaveDTO));
                supplierClient.collectOrgCategoryForAnon(orgCategorySaveDTO);
            }
        }
	}
}
