package com.midea.cloud.srm.inq.quote.service.impl.convertor;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.QuoteStatus;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.inq.QuoteLadderPriceUtil;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteLadderPriceDTO;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteTrackingItemsResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-23 15:30
 *  修改内容:
 * </pre>
 */
public class QuoteTrackingDetailConvertor {

    /**
     *
     */
    public static PageInfo<QuoteTrackingItemsResponseDTO> convert(List<QuoteItem> items,
                                                                  List<QuoteLadderPrice> quoteLadderPrices,
                                                                  List<QuoteSelection> quoteItemSelections) {
        ArrayList<QuoteTrackingItemsResponseDTO> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(items)) {

            HashMap<Long, List<QuoteLadderPriceDTO>> ladderPriceMap = QuoteLadderPriceUtil.builtQuoteLadderPriceMap(quoteLadderPrices);

            items.forEach(quoteItem -> {
                QuoteTrackingItemsResponseDTO responseDTO = new QuoteTrackingItemsResponseDTO();
                BeanUtils.copyProperties(quoteItem, responseDTO);
                responseDTO.setTotal(quoteItem.getNotaxPrice().multiply(quoteItem.getDemandQuantity()));
                if (ladderPriceMap.containsKey(quoteItem.getQuoteItemId())) {
                    responseDTO.setQuoteLadderPrices(ladderPriceMap.get(quoteItem.getQuoteItemId()));
                }
                QuoteSelection quoteSelection = quoteItemSelections.stream().filter(f ->
                        f.getQuoteItemId().equals(quoteItem.getQuoteItemId())).findAny().orElse(null);
                if (quoteSelection != null && QuoteStatus.SELECTED.getKey().equals(quoteSelection.getIsSelected())) {
                    responseDTO.setQuoteResult(QuoteStatus.SELECTED.getKey());
                }
                responses.add(responseDTO);
            });
        }
        return PageUtil.buildPageInfoAfertConvert(items, responses);
    }
}