package com.midea.cloud.srm.inq.quote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.quote.domain.EffectiveQuoteItemsResult;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;

import java.util.List;

/**
 * <p>
 * 报价-报价信息行表 Mapper 接口
 * </p>
 *
 * @author linxc6@meiCloud.com
 * @since 2020-03-23
 */
public interface QuoteItemMapper extends BaseMapper<QuoteItem> {

    /**
     * 查询有效报价行
     */
    List<EffectiveQuoteItemsResult> effrcticeQuoteItems(Long inquiryId);
}
