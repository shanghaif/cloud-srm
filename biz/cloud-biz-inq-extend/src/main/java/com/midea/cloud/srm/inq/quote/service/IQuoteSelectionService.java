package com.midea.cloud.srm.inq.quote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteBargainingQueryRequestDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteBargainingQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteBargainingSaveRequestDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteSelectionQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;

import java.util.List;
import java.util.Map;

public interface IQuoteSelectionService extends IService<QuoteSelection> {

    /**
     * 根据询价单查询是否已经评选
     */
    List<QuoteSelection> getQuoteSelectionByInquiryId(Long inquiryId);

    /**
     * 报价评选计算价格查询
     */
    Map<Long, List<QuoteSelectionQueryResponseDTO>> quoteSelect(Long inquiryId);

    /**
     * 计算得分并进行存储
     */
    Map<Long, List<QuoteSelectionQueryResponseDTO>> calculateScore(Long inquiryId, List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems);

    /**
     * 保存报价评选
     */
    void saveQuoteSelection(Long inquiryId);

    /**
     * 查询报价行选定信息
     */
    List<QuoteSelection> getQuoteSelectionByQuoteItemId(List<Long> quoteItemIds);

    /**
     * 继续议价查询
     */
    QuoteBargainingQueryResponseDTO getQuoteBargaining(QuoteBargainingQueryRequestDTO quoteItemIds, List<QuoteSelectionQueryResponseDTO> stroeInRedis);

    /**
     * 继续议价保存
     */
    void saveBargaining(QuoteBargainingSaveRequestDTO request);
}
