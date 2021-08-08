package com.midea.cloud.srm.inq.quote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.NumberUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.common.enums.inq.InquiryPublishStatusEnum;
import com.midea.cloud.common.enums.inq.QuoteStatusEnum;
import com.midea.cloud.common.enums.inq.ScoreRuleEnum;
import com.midea.cloud.srm.inq.inquiry.service.IHeaderService;
import com.midea.cloud.srm.inq.inquiry.service.VendorNService;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteTrackingQueryResult;
import com.midea.cloud.srm.inq.quote.mapper.QuoteHeaderMapper;
import com.midea.cloud.srm.inq.quote.service.IQuoteHeaderService;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemPerformanceService;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemService;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.inq.quote.service.impl.convertor.QuoteHeaderConvertor;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteItemDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.inquiry.entity.VendorN;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteCancelDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteRollbackDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteTrackingQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteHeader;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItemPerformance;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
*  <pre>
 *  报价-报价信息头表 服务实现类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 09:49:07
 *  修改内容:
 * </pre>
*/
@Service
public class QuoteHeaderServiceImpl extends ServiceImpl<QuoteHeaderMapper, QuoteHeader> implements IQuoteHeaderService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IQuoteHeaderService iQuoteHeaderService;
    @Autowired
    private IQuoteItemService iQuoteItemService;
    @Autowired
    private IQuoteLadderPriceService iQuoteLadderPriceService;
    @Autowired
    private IHeaderService iHeaderService;
    @Autowired
    private IQuoteItemPerformanceService iQuoteItemPerformanceService;
    @Autowired
    private VendorNService vendorNService;

    @Override
    public Long saveQuote(QuoteHeaderDto header) {
        QuoteHeader dto = new QuoteHeader();
        Long id = header.getQuoteId();
        if (null == id) {
            id = IdGenrator.generate();
            header.setQuoteId(id);
            header.setQuoteNo(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_QUOTENO_CODE));
        }
        BeanUtils.copyProperties(header,dto);
        saveHeader(dto);
        saveItems(header, id);
        updateInquiryHeader(header.getInquiryId());
        return id;
    }

    /**
     * 更新询价单信息
     */
    private void updateInquiryHeader(Long inquiryId) {
        Header header = iHeaderService.getById(inquiryId);
        header.setQuoteCnt(header.getQuoteCnt().add(new BigDecimal("1")));
        iHeaderService.updateById(header);
    }

    private void saveHeader(QuoteHeader head) {
        //需要初始化的字段写这里
        if("SAVE".equals(head.getStatus())){
            head.setStatus(QuoteStatusEnum.SAVE.getKey());
        }else if("SUBMIT".equals(head.getStatus())){
            head.setStatus(QuoteStatusEnum.SUBMIT.getKey());
        }
        iQuoteHeaderService.saveOrUpdate(head);
    }

    private void saveItems(QuoteHeaderDto header, Long quoteId) {
        List<QuoteItemDto> items = header.getItems();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("QUOTE_ID", quoteId);
        iQuoteItemService.remove(wrapper);
        iQuoteLadderPriceService.remove(wrapper);
        for (QuoteItemDto item:items){
            QuoteItem newItem = new QuoteItem();
            BeanUtils.copyProperties(item,newItem);
            newItem.setQuoteItemId(IdGenrator.generate());
            newItem.setQuoteId(quoteId);
            newItem.setInquiryItemId(item.getInquiryItemId());
            iQuoteItemService.save(newItem);
            List<QuoteLadderPrice> ladderPrices = item.getLadderPrices();
            for(QuoteLadderPrice ladder:ladderPrices){
                ladder.setQuoteId(quoteId);
                ladder.setQuoteItemId(newItem.getQuoteItemId());
                ladder.setQuoteLadderPriceId(IdGenrator.generate());
            }
            iQuoteLadderPriceService.saveBatch(ladderPrices);
        }
    }

    @Override
    public QuoteTrackingQueryResponseDTO tracking(Long inquiryId) {

        Header header = iHeaderService.getById(inquiryId);
        /*状态为已发布并且当前时间超过询价截止时间状态变为“已截止报价”*/
        if (InquiryPublishStatusEnum.PUBLISHED.getKey().equals(header.getStatus())
                && header.getDeadline().before(new Date())) {
            Header inquiryHeader = new Header();
            inquiryHeader.setInquiryId(inquiryId);
            inquiryHeader.setStatus(InquiryPublishStatusEnum.CLOSE_QUOTATION.getKey());
            iHeaderService.updateById(inquiryHeader);
        }
        List<QuoteTrackingQueryResult> quoteTrackingQueryResults = this.baseMapper.queryByInquiryId(inquiryId);
        if (CollectionUtils.isNotEmpty(quoteTrackingQueryResults)) {
            /*计算总价*/
            calculationTotalAmount(quoteTrackingQueryResults);
        }

        //是否维护绩效评分
        boolean hasPerformanceScore = false;
        //是否维护供应商N值
        boolean hasVendorN = false;
        if (ScoreRuleEnum.COMPREHENSIVE_SCORING_METHOD.toString().equals(header.getInquiryRule())) {
            List<QuoteItemPerformance> performances = iQuoteItemPerformanceService.getDimensionByInquiryNo(header.getInquiryNo());
            if (CollectionUtils.isNotEmpty(performances)) {
                hasPerformanceScore = true;
            }
        }
        List<VendorN> vendorNs = vendorNService.queryByInquiryId(header.getInquiryId());
        if (CollectionUtils.isNotEmpty(vendorNs)) {
            hasVendorN = true;
        }
        return QuoteHeaderConvertor.convert(header, quoteTrackingQueryResults, hasPerformanceScore, hasVendorN);
    }

    /**
     * 计算总价
     */
    private void calculationTotalAmount(List<QuoteTrackingQueryResult> quoteTrackingQueryResults) {

        List<Long> quoteIds = new ArrayList<>();
        quoteTrackingQueryResults.forEach(quoteTrackingQueryResult -> {
            /*有报价单才计算*/
            if (quoteTrackingQueryResult.getQuoteId() != null) {
                quoteIds.add(quoteTrackingQueryResult.getQuoteId());
            }
        });

        if (CollectionUtils.isEmpty(quoteIds)) {
            return;
        }

        List<QuoteItem> quoteItems = iQuoteItemService.getQuoteItemByQuteIds(quoteIds);
        quoteTrackingQueryResults.forEach(quoteTrackingQueryResult -> {
            /*有报价单才计算*/
            if (quoteTrackingQueryResult.getQuoteId() != null) {
                double totalAmount = 0.0;
                List<QuoteItem> theSameQuoteItems = quoteItems.stream().filter(f ->
                        f.getQuoteId().equals(quoteTrackingQueryResult.getQuoteId())).collect(Collectors.toList());
                for (QuoteItem quoteItem : theSameQuoteItems) {
                    totalAmount = NumberUtil.add(
                            NumberUtil.mul(quoteItem.getDemandQuantity().doubleValue(), quoteItem.getNotaxPrice().doubleValue()), totalAmount);
                }
                quoteTrackingQueryResult.setTotalAmount(NumberUtil.doubleToBigDecimal(totalAmount));
            }
        });

    }

    @Override
    public void cancelQuote(QuoteCancelDTO request) {

        QuoteHeader entity = new QuoteHeader();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(QuoteStatusEnum.CANCEL.getKey());
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rolllbackQuote(QuoteRollbackDTO request) {

        QuoteHeader quoteHeader = getById(request.getQuoteId());
        Header header = iHeaderService.getById(quoteHeader.getInquiryId());
        if (header.getQuoteCnt().doubleValue() > 0) {
            header.setQuoteCnt(header.getQuoteCnt().subtract(new BigDecimal("1")));
        }
        QuoteHeader entity = new QuoteHeader();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(QuoteStatusEnum.ROLLBACK.getKey());
        updateById(entity);
        iHeaderService.updateById(header);
    }


}
