package com.midea.cloud.srm.inq.quote.service.impl.convertor;

import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteTrackingQueryResult;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteTrackingItemDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteTrackingQueryResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-23 10:50
 *  修改内容:
 * </pre>
 */
public class QuoteHeaderConvertor {

    /**
     * domain转DTO
     */
    public static QuoteTrackingQueryResponseDTO convert(Header header, List<QuoteTrackingQueryResult> result,
                                                        boolean hasPerformanceScore, boolean hasVendorN) {

        QuoteTrackingQueryResponseDTO response = new QuoteTrackingQueryResponseDTO();
        BeanUtils.copyProperties(header, response);

        List<QuoteTrackingItemDTO> trackingItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(quoteTrackingQueryResult -> {
                QuoteTrackingItemDTO dto = new QuoteTrackingItemDTO();
                BeanUtils.copyProperties(quoteTrackingQueryResult, dto);
                /*有报价则计算总价*/
                if (quoteTrackingQueryResult.getQuoteId() != null) {

                }
                trackingItems.add(dto);
            });
        }

        if (YesOrNo.YES.getValue().equals(header.getIsTargetPriceEncry())) {
            response.setTargetPriceEncrypt(Boolean.TRUE);
        }else if (YesOrNo.NO.getValue().equals(header.getIsTargetPriceEncry())) {
            response.setTargetPriceEncrypt(Boolean.FALSE);
        }
        response.setTrackingItems(trackingItems);
        response.setHasPerformanceScore(hasPerformanceScore);
        response.setHasVendorN(hasVendorN);
        return response;
    }
}