package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.inq.inquiry.entity.PriceStandard;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaPreinstall;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaRebate;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaRebateMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaRebateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  配额-预估返利 服务实现类
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
public class QuotaRebateServiceImpl extends ServiceImpl<QuotaRebateMapper, QuotaRebate> implements IQuotaRebateService {

    @Override
    public List<QuotaRebate> quotaRebateList(QuotaRebate quotaRebate) {
        QueryWrapper<QuotaRebate> wrapper = new QueryWrapper<>();
        wrapper.eq(quotaRebate.getQuotaId()!=null,"QUOTA_ID",quotaRebate.getQuotaId());
        return this.list(wrapper);
    }

    @Override
    public void quotaRebateAdd(List<QuotaRebate> quotaRebateList) {
        for (QuotaRebate quotaRebate : quotaRebateList) {
            if (quotaRebate.getQuotaRebateId() == null) {
                Long id = IdGenrator.generate();
                quotaRebate.setQuotaRebateId(id);
                this.save(quotaRebate);
            } else {
                this.updateById(quotaRebate);
            }
        }
    }
}
