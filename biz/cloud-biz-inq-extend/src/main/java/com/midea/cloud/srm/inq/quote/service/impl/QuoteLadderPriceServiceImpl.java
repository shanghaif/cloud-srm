package com.midea.cloud.srm.inq.quote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.inq.quote.mapper.QuoteLadderPriceMapper;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.inq.quote.service.impl.convertor.QuoteLadderPriceConvertor;
import com.midea.cloud.srm.model.inq.quote.dto.LadderPriceCompareResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteSelectionQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
*  <pre>
 *  报价--物料阶梯价表 服务实现类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 15:22:46
 *  修改内容:
 * </pre>
*/
@Service
public class QuoteLadderPriceServiceImpl extends ServiceImpl<QuoteLadderPriceMapper, QuoteLadderPrice> implements IQuoteLadderPriceService {

    @Override
    public List<QuoteLadderPrice> getLadderPrice(List<Long> quoteItemIds) {

        QueryWrapper<QuoteLadderPrice> itemWrapper = new QueryWrapper<>();
        itemWrapper.in("QUOTE_ITEM_ID", quoteItemIds)
                .orderByAsc("BEGIN_QUANTITY").orderByAsc("END_QUANTITY");
        return list(itemWrapper);
    }

    @Override
    public List<LadderPriceCompareResponseDTO> compareLadderPrice(Long quoteItemId,
                                                                  List<QuoteSelectionQueryResponseDTO> selectionQueryResult) {
        /*匹配报价行*/
        QuoteSelectionQueryResponseDTO quoteItem =
                selectionQueryResult.stream().filter(value -> quoteItemId.equals(value.getQuoteItemId())).findFirst().get();

        /*根据匹配的报价行物料过滤出不同供应商*/
        List<QuoteSelectionQueryResponseDTO> themSameItems =
                selectionQueryResult.stream().filter(value -> quoteItem.getItemId().equals(value.getItemId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(themSameItems)) {
            throw new BaseException("未找到相同物料的供应商信息");
        }

        return QuoteLadderPriceConvertor.convert(themSameItems);
    }

}
