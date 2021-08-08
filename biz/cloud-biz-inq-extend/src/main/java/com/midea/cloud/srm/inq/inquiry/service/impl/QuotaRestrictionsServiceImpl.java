package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaPreinstall;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaRestrictions;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaRestrictionsMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaRestrictionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*  <pre>
 *  配额-配额上下限表 服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:41:44
 *  修改内容:
 * </pre>
*/
@Service
public class QuotaRestrictionsServiceImpl extends ServiceImpl<QuotaRestrictionsMapper, QuotaRestrictions> implements IQuotaRestrictionsService {

    @Override
    public List<QuotaRestrictions> quotaRestrictionsList(QuotaRestrictions quotaRestrictions) {
        QueryWrapper<QuotaRestrictions> wrapper = new QueryWrapper<>();
        //配额条件查询
        wrapper.eq(quotaRestrictions.getQuotaId()!=null,"QUOTA_ID",quotaRestrictions.getQuotaId());
        return this.list(wrapper);
    }

    /**
     * 新增或修改
     * @param quotaRestrictionsList
     */
    @Transactional
    @Override
    public void quotaRestrictionsAdd(List<QuotaRestrictions> quotaRestrictionsList) {
        for (QuotaRestrictions quotaRestrictions :quotaRestrictionsList){
            if (quotaRestrictions.getQuotaRestrictionsId()==null){
                Long id = IdGenrator.generate();
                quotaRestrictions.setQuotaRestrictionsId(id);
                this.save(quotaRestrictions);
            }else {
                this.updateById(quotaRestrictions);
            }
        }
    }
}
