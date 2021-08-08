package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.price.mapper.PriceLadderPriceMapper;
import com.midea.cloud.srm.inq.price.service.IPriceLadderPriceService;
import com.midea.cloud.srm.model.inq.price.entity.PriceLadderPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*  <pre>
 *  价格目录-阶梯价 服务实现类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 14:53:38
 *  修改内容:
 * </pre>
*/
@Service
public class PriceLadderPriceServiceImpl extends ServiceImpl<PriceLadderPriceMapper, PriceLadderPrice> implements IPriceLadderPriceService {

    @Override
    public List<PriceLadderPrice> getLadderPrice(Long priceLibraryId) {

        QueryWrapper<PriceLadderPrice> wrapper = new QueryWrapper<>();
        wrapper.eq("PRICE_LIBRARY_ID", priceLibraryId)
                .orderByAsc("BEGIN_QUANTITY").orderByAsc("END_QUANTITY");
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByPriceLibraryId(Long priceLibraryId) {
        QueryWrapper<PriceLadderPrice> wrapper = new QueryWrapper<>();
        wrapper.eq("PRICE_LIBRARY_ID", priceLibraryId);
        remove(wrapper);
    }
}
