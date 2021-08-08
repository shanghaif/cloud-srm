package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaSourceDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaSourceMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  配额调整-关联寻源列表 服务实现类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 16:57:09
 *  修改内容:
 * </pre>
*/
@Service
public class QuotaSourceServiceImpl extends ServiceImpl<QuotaSourceMapper, QuotaSource> implements IQuotaSourceService {
    @Autowired
    private QuotaSourceMapper quotaSourceMapper;

    /**
     * 配额调整对应寻源列表
     * @param quotaSource
     * @return
     */
    @Override
    public List<QuotaSourceDTO> quotaSourceList(QuotaSource quotaSource) {
        QueryWrapper<QuotaSource> wrapper = new QueryWrapper<>();
        wrapper.eq(quotaSource.getQuotaAdjustId()!=null,"QUOTA_ADJUST_ID",quotaSource.getQuotaAdjustId());
        return quotaSourceMapper.quotaSourceList(wrapper);
    }
}
