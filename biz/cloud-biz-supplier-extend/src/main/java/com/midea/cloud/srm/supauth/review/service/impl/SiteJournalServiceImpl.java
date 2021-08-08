package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.review.FormType;
import com.midea.cloud.common.enums.sup.SiteJournalStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteJournal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.supauth.review.mapper.SiteJournalMapper;
import com.midea.cloud.srm.supauth.review.service.ISiteJournalService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  地点信息日志表 服务实现类
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
@Service
public class SiteJournalServiceImpl extends ServiceImpl<SiteJournalMapper, SiteJournal> implements ISiteJournalService {

    @Autowired
    private SiteJournalMapper siteJournalMapper;

    @Autowired
    private SupplierClient supplierClient;

    /**
     * 根据reviewFormId, vendorId获取供应商地点信息
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    @Override
    public List<SiteJournal> listSiteJournal(Long reviewFormId, Long vendorId) {
        List<SiteJournal> results = new ArrayList<>();
        if (reviewFormId != null) {
            QueryWrapper<SiteJournal> queryWrapper = new QueryWrapper<>(
                    new SiteJournal().setFormId(reviewFormId).setFormType(FormType.REVIEW_FORM.toString())
            );
            results = siteJournalMapper.selectList(queryWrapper);
        }
        else {
            //没生成资质审查单，则查询注册表信息
            if (vendorId != null) {
                List<SiteInfo> siteInfos = supplierClient.getBySiteInfoCompanyId(vendorId);
                if (CollectionUtils.isNotEmpty(siteInfos)) {
                    for (SiteInfo siteInfo : siteInfos) {
                        if (siteInfo == null) continue;
                        SiteJournal siteJournal = new SiteJournal();
                        BeanUtils.copyProperties(siteInfo, siteJournal);
                        siteJournal.setVendorId(vendorId)
                                .setCeeaSiteInfoId(siteInfo.getSiteInfoId())
                                .setCeeaAllowDelete(SiteJournalStatus.N.name());
                        if(StringUtils.isEmpty(siteInfo.getBelongOprId())) {
                            throw new BaseException(LocaleHandler.getLocaleMsg("所选供应商存在erp业务实体Id为空的地点！业务实体名称："+siteInfo.getOrgName()));
                        }
                        siteJournal.setErpOrgId(Long.valueOf(siteInfo.getBelongOprId()));
                        results.add(siteJournal);
                    }
                }
            }
        }
        return results;
    }
}
