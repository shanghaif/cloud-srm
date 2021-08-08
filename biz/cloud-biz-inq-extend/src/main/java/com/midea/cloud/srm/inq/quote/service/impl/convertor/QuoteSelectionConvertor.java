package com.midea.cloud.srm.inq.quote.service.impl.convertor;

import com.google.common.collect.Lists;
import com.midea.cloud.srm.model.inq.quote.domain.EffectiveQuoteItemsResult;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteItemCalculation;
import com.midea.cloud.common.utils.inq.QuoteLadderPriceUtil;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuoteAuth;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import com.midea.cloud.srm.model.inq.quote.dto.*;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteLadderPriceDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  报价评选数据处理
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-25 10:28
 *  修改内容:
 * </pre>
 */
public class QuoteSelectionConvertor {

    /**
     *
     */
    public static List<QuoteSelectionQueryResponseDTO> convert(List<EffectiveQuoteItemsResult> effectiveQuoteItems,
                                                               List<QuoteItemCalculation> calculations,
                                                               List<QuoteLadderPrice> quoteLadderPrices,
                                                               List<QuoteSelection> selectionList) {
        ArrayList<QuoteSelectionQueryResponseDTO> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(effectiveQuoteItems)) {

            HashMap<Long, List<QuoteLadderPriceDTO>> ladderPriceMap = QuoteLadderPriceUtil.builtQuoteLadderPriceMap(quoteLadderPrices);

            effectiveQuoteItems.forEach(quoteItem -> {
                QuoteSelectionQueryResponseDTO responseDTO = new QuoteSelectionQueryResponseDTO();
                BeanUtils.copyProperties(quoteItem, responseDTO);
                /*把字符串类型的未税目标价转成数字型，供后面评选计算*/
                responseDTO.setNotaxTargrtPrice(new BigDecimal(quoteItem.getNotaxTargrtPrice()));
                /*设置计算的价格*/
                if (CollectionUtils.isNotEmpty(calculations)) {
                    /*没有评分的时候计算的价格*/
                    calculations.forEach(calculation -> {
                        if (quoteItem.getQuoteItemId().equals(calculation.getQuoteItemId())) {
                            BeanUtils.copyProperties(calculation, responseDTO);
                        }
                    });
                }else if (CollectionUtils.isNotEmpty(selectionList)) {
                    /*有评分的时候直接在评分表拿的价格*/
                    selectionList.forEach(quoteSelection -> {
                        if (quoteItem.getQuoteItemId().equals(quoteSelection.getQuoteItemId())) {
                            BeanUtils.copyProperties(quoteSelection, responseDTO);
                        }
                    });
                }
                /*设置目标总价和总价*/
                if (responseDTO.getNotaxTargrtPrice() != null) {
                    responseDTO.setNotaxTargrtPriceTotal(responseDTO.getNotaxTargrtPrice().multiply(responseDTO.getDemandQuantity()));
                }
                if (responseDTO.getNotaxPrice() != null) {
                    responseDTO.setNotaxPriceTotal(responseDTO.getNotaxPrice().multiply(responseDTO.getDemandQuantity()));
                }
                /*设置阶梯价*/
                if (ladderPriceMap.containsKey(quoteItem.getQuoteItemId())) {
                    responseDTO.setQuoteLadderPrices(ladderPriceMap.get(quoteItem.getQuoteItemId()));
                }
                responses.add(responseDTO);
            });
        }
        return responses;
    }

    public static QuoteBargainingQueryResponseDTO convertBargaining(List<Item> items, List<LadderPrice> ladderPrice,
                                                          List<Vendor> vendorIds, List<QuoteAuth> quoteAuths) {

        QuoteBargainingQueryResponseDTO responseDTO = new QuoteBargainingQueryResponseDTO();
        HashMap<Long, List<InquiryLadderPriceDTO>> ladderPriceMap = builtInquiryLadderPriceMap(ladderPrice);

        if (CollectionUtils.isNotEmpty(items)) {
            List<InquiryItemDTO> inquiryItemDTOS = new ArrayList<>();
            items.forEach(item -> {
                InquiryItemDTO inquiryItemDTO = new InquiryItemDTO();
                BeanUtils.copyProperties(item, inquiryItemDTO);
                if (ladderPriceMap.containsKey(item.getInquiryItemId())) {
                    inquiryItemDTO.setLadderPrices(ladderPriceMap.get(item.getInquiryItemId()));
                }
                inquiryItemDTOS.add(inquiryItemDTO);
            });
            responseDTO.setItems(inquiryItemDTOS);
        }

        if (CollectionUtils.isNotEmpty(vendorIds)) {
            List<InquiryVendorDTO> inquiryVendorDTOS = new ArrayList<>();
            vendorIds.forEach(vendor -> {
                InquiryVendorDTO inquiryVendorDTO = new InquiryVendorDTO();
                BeanUtils.copyProperties(vendor, inquiryVendorDTO);
                inquiryVendorDTO.setQuoteAuths(buildQuoteAuth(vendor, quoteAuths));
                inquiryVendorDTOS.add(inquiryVendorDTO);
            });
            responseDTO.setVendors(inquiryVendorDTOS);
        }

        return responseDTO;
    }

    /**
     * 构建报价权限
     */
    private static List<QuoteAuthDTO> buildQuoteAuth(Vendor vendor, List<QuoteAuth> quoteAuths) {
        List<QuoteAuthDTO> quoteAuthDTOS = new ArrayList<>();
        List<QuoteAuth> auths = quoteAuths.stream().filter(f ->
                f.getVendorId().equals(vendor.getVendorId())).collect(Collectors.toList());
        auths.forEach(quoteAuth -> {
            QuoteAuthDTO quoteAuthDTO = new QuoteAuthDTO();
            BeanUtils.copyProperties(quoteAuth, quoteAuthDTO);
            quoteAuthDTOS.add(quoteAuthDTO);
        });
        return quoteAuthDTOS;
    }

    public static HashMap<Long, List<InquiryLadderPriceDTO>> builtInquiryLadderPriceMap(List<LadderPrice> inquiryLadderPrices) {
        HashMap<Long, List<InquiryLadderPriceDTO>> ladderPriceMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(inquiryLadderPrices)) {
            inquiryLadderPrices.forEach(inquiryLadderPrice -> {
                if (ladderPriceMap.containsKey(inquiryLadderPrice.getInquiryItemId())) {
                    InquiryLadderPriceDTO priceDTO = new InquiryLadderPriceDTO();
                    BeanUtils.copyProperties(inquiryLadderPrice, priceDTO);
                    List<InquiryLadderPriceDTO> inquiryLadderPriceDTOS = ladderPriceMap.get(inquiryLadderPrice.getInquiryItemId());
                    inquiryLadderPriceDTOS.add(priceDTO);
                    ladderPriceMap.put(inquiryLadderPrice.getInquiryItemId(), inquiryLadderPriceDTOS);
                }else {
                    InquiryLadderPriceDTO priceDTO = new InquiryLadderPriceDTO();
                    BeanUtils.copyProperties(inquiryLadderPrice, priceDTO);
                    ladderPriceMap.put(inquiryLadderPrice.getInquiryItemId(), Lists.newArrayList(priceDTO));
                }
            });
        }
        return ladderPriceMap;
    }
}