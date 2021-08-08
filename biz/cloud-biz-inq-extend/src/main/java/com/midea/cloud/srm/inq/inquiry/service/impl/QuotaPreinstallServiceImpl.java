package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaPreinstallMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaPreinstallService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaPreinstall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 *  配额-预设比例表 服务实现类
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
 */
@Service
public class QuotaPreinstallServiceImpl extends ServiceImpl<QuotaPreinstallMapper, QuotaPreinstall> implements IQuotaPreinstallService {
    /**
     * 条件查询
     * @param quotaPreinstall
     * @return
     */
    @Override
    public List<QuotaPreinstall> quotaPreinstallList(QuotaPreinstall quotaPreinstall) {
        QueryWrapper<QuotaPreinstall> wrapper = new QueryWrapper<>();
        wrapper.eq(quotaPreinstall.getQuotaId()!=null,"QUOTA_ID",quotaPreinstall.getQuotaId());
        return this.list(wrapper);
    }

    /**
     * 新增修改
     * @param quotaPreinstallList
     */
    @Transactional
    @Override
    public void quotaPreinstallAdd(List<QuotaPreinstall> quotaPreinstallList) {
        for (QuotaPreinstall quotaPreinstall :quotaPreinstallList){
            if (quotaPreinstall.getQuotaPreinstallId()==null){
                Long id = IdGenrator.generate();
                quotaPreinstall.setQuotaPreinstallId(id);
                this.save(quotaPreinstall);
            }else {
                this.updateById(quotaPreinstall);
            }
        }
    }
}
