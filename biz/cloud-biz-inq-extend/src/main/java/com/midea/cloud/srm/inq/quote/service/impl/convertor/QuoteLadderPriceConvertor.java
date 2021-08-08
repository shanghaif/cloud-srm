package com.midea.cloud.srm.inq.quote.service.impl.convertor;

import com.midea.cloud.srm.model.inq.quote.dto.LadderPriceCompareResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.LadderPriceVendorDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteSelectionQueryResponseDTO;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteLadderPriceDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

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
 *  修改日期: 2020-4-1 16:42
 *  修改内容:
 * </pre>
 */
public class QuoteLadderPriceConvertor {

    public static List<LadderPriceCompareResponseDTO> convert(List<QuoteSelectionQueryResponseDTO> themSameItems) {

        List<LadderPriceCompareResponseDTO> response = new ArrayList<>();

        List<QuoteLadderPriceDTO> ladderPrices = themSameItems.get(0).getQuoteLadderPrices();
        if (CollectionUtils.isEmpty(ladderPrices)) {
            return response;
        }

        ladderPrices.forEach(quoteLadderPrice -> {
            /*设置价格区间*/
            LadderPriceCompareResponseDTO dto = new LadderPriceCompareResponseDTO();
            BeanUtils.copyProperties(quoteLadderPrice, dto);

            List<LadderPriceVendorDTO> vendorDTOs = new ArrayList<>();
            themSameItems.forEach(quoteItem -> {
                /*设置供应商信息*/
                LadderPriceVendorDTO vendorDTO = new LadderPriceVendorDTO();
                BeanUtils.copyProperties(quoteItem, vendorDTO);
                for (QuoteLadderPriceDTO ladderPrice : quoteItem.getQuoteLadderPrices()) {
                    /*设置区间价格*/
                    if (quoteLadderPrice.getBeginQuantity().equals(ladderPrice.getBeginQuantity()) &&
                            quoteLadderPrice.getEndQuantity().equals(ladderPrice.getEndQuantity())) {
                        vendorDTO.setNotaxPrice(ladderPrice.getPrice());
                        break;
                    }
                }
                vendorDTOs.add(vendorDTO);
            });
            dto.setLadderPriceVendors(vendorDTOs);
            response.add(dto);
        });

        return response;
    }
}