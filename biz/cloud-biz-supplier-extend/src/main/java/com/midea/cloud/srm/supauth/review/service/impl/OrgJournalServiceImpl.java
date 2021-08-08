package com.midea.cloud.srm.supauth.review.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;
import com.midea.cloud.srm.supauth.review.mapper.OrgJournalMapper;
import com.midea.cloud.srm.supauth.review.service.IOrgJournalService;

/**
*  <pre>
 *  合作ou日志表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-08 11:08:06
 *  修改内容:
 * </pre>
*/
@Service
public class OrgJournalServiceImpl extends ServiceImpl<OrgJournalMapper, OrgJournal> implements IOrgJournalService {
	
	@Override
	public void batchSaveOrUpdateOrgJournal(List<OrgJournal> orgJournals,Long vendorId,Long formId, String formType) {
       
		if (!CollectionUtils.isEmpty(orgJournals)) {
			//先删除
			QueryWrapper<OrgJournal> qw = new QueryWrapper<OrgJournal>();
			qw.eq("FORM_ID", formId);
			qw.eq("VENDOR_ID", vendorId);
			qw.eq("FORM_TYPE", formType);
			this.remove(qw);
			//新增
            for (OrgJournal orgJournal : orgJournals) {
                if (orgJournal == null) continue;
                orgJournal.setOrgJournalId(IdGenrator.generate())
                .setVendorId(vendorId)
                .setFormId(formId)
                .setFormType(formType);
                this.save(orgJournal);
            }
        }
    }
}
