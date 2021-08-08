package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.inq.inquiry.mapper.PriceStandardMapper;
import com.midea.cloud.srm.inq.inquiry.service.IPriceStandardService;
import com.midea.cloud.srm.model.inq.inquiry.entity.PriceStandard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 *  配额-差价标准 服务实现类
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
public class PriceStandardServiceImpl extends ServiceImpl<PriceStandardMapper, PriceStandard> implements IPriceStandardService {

    @Override
    public List<PriceStandard> priceStandardList(PriceStandard priceStandard) {
        QueryWrapper<PriceStandard> wrapper = new QueryWrapper<>();
        //配额条件查询
        wrapper.eq(priceStandard.getQuotaId() != null, "QUOTA_ID", priceStandard.getQuotaId());
        return this.list(wrapper);
    }

    /**
     * 新增或修改
     * @param priceStandardList
     */
    @Transactional
    @Override
    public void priceStandardAdd(List<PriceStandard> priceStandardList) {
        for (PriceStandard priceStandard : priceStandardList) {
            if (priceStandard.getPriceStandardId() == null) {
                Long id = IdGenrator.generate();
                priceStandard.setPriceStandardId(id);
                this.save(priceStandard);
            } else {
                this.updateById(priceStandard);
            }
        }
    }
}
