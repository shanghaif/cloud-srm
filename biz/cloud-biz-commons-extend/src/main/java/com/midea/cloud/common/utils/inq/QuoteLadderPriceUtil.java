package com.midea.cloud.common.utils.inq;

import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteLadderPriceDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 阶梯价工具类
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-25 10:35
 *  修改内容:
 * </pre>
 */
public class QuoteLadderPriceUtil {

    public static HashMap<Long, List<QuoteLadderPriceDTO>> builtQuoteLadderPriceMap(List<QuoteLadderPrice> quoteLadderPrices) {
        HashMap<Long, List<QuoteLadderPriceDTO>> ladderPriceMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(quoteLadderPrices)) {
            quoteLadderPrices.forEach(quoteLadderPrice -> {
                if (ladderPriceMap.containsKey(quoteLadderPrice.getQuoteItemId())) {
                    QuoteLadderPriceDTO priceDTO = new QuoteLadderPriceDTO();
                    BeanUtils.copyProperties(quoteLadderPrice, priceDTO);
                    List<QuoteLadderPriceDTO> quoteLadderPriceDTOS = ladderPriceMap.get(quoteLadderPrice.getQuoteItemId());
                    quoteLadderPriceDTOS.add(priceDTO);
                    ladderPriceMap.put(quoteLadderPrice.getQuoteItemId(), quoteLadderPriceDTOS);
                }else {
                    QuoteLadderPriceDTO priceDTO = new QuoteLadderPriceDTO();
                    BeanUtils.copyProperties(quoteLadderPrice, priceDTO);
                    List<QuoteLadderPriceDTO> list = new ArrayList<>();
                    list.add(priceDTO);
                    ladderPriceMap.put(quoteLadderPrice.getQuoteItemId(), list);
                }
            });
        }
        return ladderPriceMap;
    }
}