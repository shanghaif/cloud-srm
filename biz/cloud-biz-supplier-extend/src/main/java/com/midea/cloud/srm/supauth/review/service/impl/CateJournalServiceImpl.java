package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.review.FormType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.review.mapper.CateJournalMapper;
import com.midea.cloud.srm.supauth.review.service.ICateJournalService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
*  <pre>
 *  品类日志表 服务实现类
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
@Service
public class CateJournalServiceImpl extends ServiceImpl<CateJournalMapper, CateJournal> implements ICateJournalService {

	@Override
	public void batchSaveOrUpdateCateJournal(List<CateJournal> cateJournals,Long vendorId,Long formId, String formType) {
        if (!CollectionUtils.isEmpty(cateJournals)) {
        	//先删除
			QueryWrapper<CateJournal> qw = new QueryWrapper<CateJournal>();
			qw.eq("FORM_ID", formId);
			qw.eq("VENDOR_ID", vendorId);
			qw.eq("FORM_TYPE", formType);
			this.remove(qw);
        	
            for (CateJournal cateJournal : cateJournals) {
                if (cateJournal == null) continue;
                cateJournal.setCategoryJournalId(IdGenrator.generate())
	                .setVendorId(vendorId)
	                .setFormId(formId)
	                .setFormType(formType);
                this.save(cateJournal);
            }
        }
    }
}
