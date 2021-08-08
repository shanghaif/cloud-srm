package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaBuMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaBuService;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaBuDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  配额事业部中间表 服务实现类
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
public class QuotaBuServiceImpl extends ServiceImpl<QuotaBuMapper, QuotaBu> implements IQuotaBuService {
    @Autowired
    private QuotaBuMapper quotaBuMapper;

    /**
     * 自定义sql
     * @param quotaBuDTO
     * @return
     */
    @Override
    public List<QuotaBu> quotaBuList(QuotaBuDTO quotaBuDTO) {
        QueryWrapper<QuotaBu> wrapper = new QueryWrapper<>();
        //配额条件查询
        wrapper.eq(quotaBuDTO.getQuotaId() != null, "QUOTA_ID", quotaBuDTO.getQuotaId());
        //事业部id多条件查询
        wrapper.in(CollectionUtils.isNotEmpty(quotaBuDTO.getBuIdList()),"BU_CODE",quotaBuDTO.getBuIdList());
        wrapper.groupBy(CollectionUtils.isNotEmpty(quotaBuDTO.getBuIdList()),"QUOTA_ID");
        return this.list(wrapper);
    }
}
