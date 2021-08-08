package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryPaymentTermMapper;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryPaymentTermService;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
*  <pre>
 *  价格库付款条款 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-24 22:14:25
 *  修改内容:
 * </pre>
*/
@Service
public class PriceLibraryPaymentTermServiceImpl extends ServiceImpl<PriceLibraryPaymentTermMapper, PriceLibraryPaymentTerm> implements IPriceLibraryPaymentTermService {

    @Override
    public List<PriceLibraryPaymentTerm> listByPriceLibraryIdCollection(Collection<Long> priceLibraryIdCollection) {
        if (CollectionUtils.isEmpty(priceLibraryIdCollection)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<PriceLibraryPaymentTerm> queryWrapper = Wrappers.lambdaQuery(PriceLibraryPaymentTerm.class).in(PriceLibraryPaymentTerm::getPriceLibraryId, priceLibraryIdCollection);
        return this.list(queryWrapper);
    }
}
