package com.midea.cloud.srm.inq.quote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.RfqResult;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.NumberUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.common.enums.inq.InquiryPublishStatusEnum;
import com.midea.cloud.common.enums.inq.ScoreDimension;
import com.midea.cloud.common.enums.inq.ScoreRuleEnum;
import com.midea.cloud.common.enums.inq.SelectionTypeEnum;
import com.midea.cloud.srm.inq.inquiry.service.*;
import com.midea.cloud.srm.inq.price.service.IPriceLadderPriceService;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryService;
import com.midea.cloud.srm.model.inq.quote.domain.EffectiveQuoteItemsResult;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteItemCalculation;
import com.midea.cloud.srm.inq.quote.mapper.QuoteSelectionMapper;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemPerformanceService;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemService;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.inq.quote.service.IQuoteSelectionService;
import com.midea.cloud.srm.inq.quote.service.impl.convertor.QuoteSelectionConvertor;
import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import com.midea.cloud.srm.model.inq.price.entity.PriceLadderPrice;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.quote.dto.*;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItemPerformance;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

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
 *  修改日期: 2020-3-24 16:32
 *  修改内容:
 * </pre>
 */
@Service
public class QuoteSelectionServiceImpl extends ServiceImpl<QuoteSelectionMapper, QuoteSelection> implements IQuoteSelectionService {

    @Autowired
    private IQuoteItemService iQuoteItemService;
    @Autowired
    private IQuoteLadderPriceService iQuoteLadderPriceService;
    @Autowired
    private IPriceLibraryService iPriceLibraryService;
    @Autowired
    private IPriceLadderPriceService iPriceLadderPriceService;
    @Autowired
    private IHeaderService iHeaderService;
    @Autowired
    private IScoreRuleService iScoreRuleService;
    @Autowired
    private IScoreRuleItemService iScoreRuleItemService;
    @Autowired
    private IQuoteItemPerformanceService iQuoteItemPerformanceService;
//    @Autowired
//    private VendorNService vendorNService;
    @Autowired
    private IItemService iItemService;
    @Autowired
    private IVendorService iVendorService;
    @Autowired
    private ILadderPriceService iLadderPriceService;
    @Autowired
    private IQuoteAuthService iQuoteAuthService;
    @Autowired
    private BaseClient baseClient;

    @Override
    public List<QuoteSelection> getQuoteSelectionByInquiryId(Long inquiryId) {

        QueryWrapper<QuoteSelection> wrapper = new QueryWrapper<>();
        wrapper.eq("INQUIRY_ID", inquiryId);
        return list(wrapper);
    }

    @Override
    public Map<Long, List<QuoteSelectionQueryResponseDTO>> quoteSelect(Long inquiryId) {

        Header header = iHeaderService.getById(inquiryId);
        checkBeforeSelect(header);

        /*查询有效报价行*/
        List<EffectiveQuoteItemsResult> effectiveQuoteItems = iQuoteItemService.getEffectiveQuoteItems(inquiryId);
        if (CollectionUtils.isEmpty(effectiveQuoteItems)) {
            throw new BaseException("没有有效报价行");
        }

        /*查询是否已经评分*/
        boolean hasScore = false;
        List<QuoteSelection> selectionList = getQuoteSelectionByInquiryId(inquiryId);
        if (CollectionUtils.isNotEmpty(selectionList)) {
            hasScore = true;
        }
        //价格计算数据
        List<QuoteItemCalculation> calculations = new ArrayList<>();
        //阶梯价数据
        List<QuoteLadderPrice> quoteLadderPrices = null;
        //是阶梯价的物料id集合
        ArrayList<Long> quoteItemIds = new ArrayList<>();
        for (EffectiveQuoteItemsResult itemsResult : effectiveQuoteItems) {
            QuoteItemCalculation calculation = new QuoteItemCalculation();
            calculation.setQuoteItemId(itemsResult.getQuoteItemId());
            /*是否为阶梯价*/
            if (YesOrNo.YES.getValue().equalsIgnoreCase(itemsResult.getIsLadder())) {
                quoteItemIds.add(itemsResult.getQuoteItemId());
            }
            /*没有评分才进行计算*/
            if (!hasScore) {
                /*计算现价*/
                BigDecimal currentPrice = getCurrentPrice(itemsResult);
                calculation.setCurrentPrice(currentPrice);
                /*计算报价差价*/
                calculateQuotePriceDifference(effectiveQuoteItems, itemsResult.getItemCode(), calculation);
                /*计算现价差价*/
                calculation.setCurrentPriceDifference(calculateCurrentPriceDifference(currentPrice, itemsResult.getNotaxPrice()));
                calculations.add(calculation);
            }
        }
        /*查询报价阶梯价*/
        if (CollectionUtils.isNotEmpty(quoteItemIds)) {
            quoteLadderPrices = iQuoteLadderPriceService.getLadderPrice(quoteItemIds);
        }

        /*把查询结果进行组合*/
        List<QuoteSelectionQueryResponseDTO> responseList =
                QuoteSelectionConvertor.convert(effectiveQuoteItems, calculations, quoteLadderPrices, selectionList);

        if (InquiryPublishStatusEnum.CLOSE_QUOTATION.getKey().equals(header.getStatus())) {
            /*询价单状态变成【评选中】*/
            header.setStatus(InquiryPublishStatusEnum.BEING_SELECTED.getKey());
            iHeaderService.updateById(header);
        }

        /*分组并排序输出*/
        return groupByAndRank(inquiryId, responseList, hasScore);
    }

    /**
     * （该行的未税价-现价）/现价*100%
     */
    public BigDecimal calculateCurrentPriceDifference(BigDecimal currentPrice, BigDecimal notaxPrice) {

        if (currentPrice != null && currentPrice.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal multiply = notaxPrice.subtract(currentPrice).
                    divide(currentPrice, 5, RoundingMode.HALF_UP).multiply(NumberUtil.doubleToBigDecimal(100d));
            return multiply.setScale(2, RoundingMode.UP);
        }else {
            return BigDecimal.ZERO;
        }
    }

    private void checkBeforeSelect(Header header) {

        Date now = new Date();
        if (now.before(header.getDeadline())) {
            throw new BaseException("报价未截止");
        }

        if (StringUtils.isBlank(header.getIsTargetPriceEncry())) {
            throw new BaseException("未设定目标价");
        }else if (YesOrNo.YES.getValue().equals(header.getIsTargetPriceEncry())) {
            throw new BaseException("目标价未解密");
        }

        if (ScoreRuleEnum.COMPREHENSIVE_SCORING_METHOD.toString().equals(header.getInquiryRule())) {
            List<QuoteItemPerformance> performances =
                    iQuoteItemPerformanceService.getDimensionByInquiryNo(header.getInquiryNo());
            if (CollectionUtils.isEmpty(performances) || performances.get(0).getScore() == null) {
                throw new BaseException("绩效评分未维护");
            }
        }

//        List<VendorN> vendorNS = vendorNService.queryByInquiryId(header.getInquiryId());
//        if(CollectionUtils.isEmpty(vendorNS)) {
//            throw new BaseException("供应商N值未维护");
//        }
    }

    private Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByAndRank(
            Long inquiryId, List<QuoteSelectionQueryResponseDTO> responseList, boolean hasScore) {
        /*查询询价单头获取报价规则、评分方式*/
        Header inquiryHeader = iHeaderService.getById(inquiryId);

        if (hasScore) {
            /*已经评选了*/
            if (SelectionTypeEnum.QUOTE_BY_LINE.toString().equals(inquiryHeader.getQuoteRule())) {
                /*按物料分组*/
                Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByItem =
                        responseList.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getItemId));
                groupByItem.forEach((key, value) -> {
                    /*对每个分组按综合得分进行排序*/
                    value.sort(Comparator.comparing(QuoteSelectionQueryResponseDTO::getCompositeScore).reversed());
                });
                return groupByItem;

            }else if (SelectionTypeEnum.QUOTE_BY_SUPPLIER.toString().equals(inquiryHeader.getQuoteRule())) {
                /*按供应商分组*/
                responseList.sort(Comparator.comparing(QuoteSelectionQueryResponseDTO::getCompositeScore).reversed()
                        .thenComparing(QuoteSelectionQueryResponseDTO::getVendorId));
                /*按供应商进行分组返回*/
                return responseList.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getVendorId));
            }
        }else {
            /*没有评选*/
            if (SelectionTypeEnum.QUOTE_BY_LINE.toString().equals(inquiryHeader.getQuoteRule())) {
                /*按物料进行分组*/
                return responseList.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getItemId));

            }else if (SelectionTypeEnum.QUOTE_BY_SUPPLIER.toString().equals(inquiryHeader.getQuoteRule())) {
                /*按供应商进行分组*/
                return responseList.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getItemId));

            }
        }
        return null;
    }

    @Override
    public Map<Long, List<QuoteSelectionQueryResponseDTO>> calculateScore(
            Long inquiryId, List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {

        List<QuoteSelection> selections = getQuoteSelectionByInquiryId(inquiryId);
        if (CollectionUtils.isNotEmpty(selections)) {
            throw new BaseException("该询价单已评选");
        }
        /*查询询价单头获取报价规则、评分方式*/
        Header inquiryHeader = iHeaderService.getById(inquiryId);

        if (ScoreRuleEnum.MININUM_PRICE_METHOD.toString().equals(inquiryHeader.getInquiryRule()) &&
                SelectionTypeEnum.QUOTE_BY_LINE.toString().equals(inquiryHeader.getQuoteRule())) {
            /*合理低价评分法 + 单项评选*/
            return miniNumPriceMethodAndLine(inquiryId, effectiveQuoteItems);

        }else if (ScoreRuleEnum.MININUM_PRICE_METHOD.toString().equals(inquiryHeader.getInquiryRule()) &&
                SelectionTypeEnum.QUOTE_BY_SUPPLIER.toString().equals(inquiryHeader.getQuoteRule())) {
            /*合理低价评分法 + 组合评选*/
            return miniNumPriceMethodAndSupplier(inquiryId, effectiveQuoteItems);

        }else if (ScoreRuleEnum.COMPREHENSIVE_SCORING_METHOD.toString().equals(inquiryHeader.getInquiryRule()) &&
                SelectionTypeEnum.QUOTE_BY_LINE.toString().equals(inquiryHeader.getQuoteRule())) {
            /*综合评分法 + 单项评选*/
            return comprehensiveAndLine(inquiryId, effectiveQuoteItems);

        }else if (ScoreRuleEnum.COMPREHENSIVE_SCORING_METHOD.toString().equals(inquiryHeader.getInquiryRule()) &&
                SelectionTypeEnum.QUOTE_BY_SUPPLIER.toString().equals(inquiryHeader.getQuoteRule())) {
            /*综合评分法 + 组合评选*/
            return comprehensiveAndSupplier(inquiryId, effectiveQuoteItems);
        }

        return null;
    }

    @Override
    public void saveQuoteSelection(Long inquiryId) {

        Header header = iHeaderService.getById(inquiryId);

        List<QuoteSelection> selectionList = getQuoteSelectionByInquiryId(inquiryId);
        if (CollectionUtils.isEmpty(selectionList)) {
            throw new BaseException("未评选，不能保存");
        }
        /*修改询价单状态为已定价*/
        header.setStatus(InquiryPublishStatusEnum.FIXED_PRICE.getKey());
        iHeaderService.updateById(header);
    }

    @Override
    public List<QuoteSelection> getQuoteSelectionByQuoteItemId(List<Long> quoteItemIds) {
        List<QuoteSelection> quoteSelections = null;
        if (CollectionUtils.isNotEmpty(quoteItemIds)) {
            QueryWrapper<QuoteSelection> wrapper = new QueryWrapper<>();
            wrapper.in("QUOTE_ITEM_ID", quoteItemIds);
            quoteSelections = this.list(wrapper);
        }
        return quoteSelections;
    }

    @Override
    public QuoteBargainingQueryResponseDTO getQuoteBargaining(QuoteBargainingQueryRequestDTO request,
                                                              List<QuoteSelectionQueryResponseDTO> stroeInRedis) {
        //物料去重
        HashSet<Long> itemSet = new HashSet<>();
        //邀请供应商去重
        HashSet<Long> vendorSet = new HashSet<>();

        request.getQuoteItemIds().forEach(quoteItem -> {

            QuoteSelectionQueryResponseDTO quoteSelection =
                    stroeInRedis.stream().filter(f -> f.getQuoteItemId().equals(quoteItem)).findFirst().orElse(null);
            if (quoteSelection != null) {
                itemSet.add(quoteSelection.getQuoteItemId());
                vendorSet.add(quoteSelection.getInquiryVendorId());
            }
        });

        List<Long> inquiryIds = new ArrayList<>();
        /*查询询价物料信息*/
        List<Item> items = queryInquiryItems(itemSet);
        if (CollectionUtils.isNotEmpty(items)) {
            items.stream().filter(f -> YesOrNo.YES.getValue().equals(f.getIsLadder()))
                            .forEach(f -> inquiryIds.add(f.getInquiryItemId()));
        }
        List<LadderPrice> ladderPrice = null;
        if (CollectionUtils.isNotEmpty(inquiryIds)) {
            ladderPrice = iLadderPriceService.getLadderPrice(inquiryIds);
        }
        List<Vendor> vendorIds = iVendorService.getByInquiryVendorIds(new ArrayList<>(vendorSet));
        List<QuoteAuth> quoteAuth = iQuoteAuthService.getByHeadId(request.getInquiryId());
        return QuoteSelectionConvertor.convertBargaining(items, ladderPrice, vendorIds, quoteAuth);
    }

    /**
     * 根据报价行查询询价物料信息
     */
    private List<Item> queryInquiryItems(HashSet<Long> itemSet) {
        List<Long> inquiryItemIds = new ArrayList<>();
        List<QuoteItem> quoteItems = iQuoteItemService.getQuoteItemByQuteItemIds(new ArrayList<>(itemSet));
        quoteItems.forEach(quoteItem -> inquiryItemIds.add(quoteItem.getInquiryItemId()));
        return iItemService.getItemsByIds(inquiryItemIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBargaining(QuoteBargainingSaveRequestDTO request) {

        //查询主询价单信息
        Header header = iHeaderService.getById(request.getInquiryId());
        if (!InquiryPublishStatusEnum.FIXED_PRICE.getKey().equals(header.getStatus())) {
            throw new BaseException("询价单未定价，不能继续议价");
        }
        //创建新询价单信息
        Header newHeader = buildInquiryHeader(header);
        header.setChildInquiryId(newHeader.getInquiryId());
        List<Header> headers = Lists.newArrayList(header, newHeader);

        /*构建新询价单物料行和供应商信息*/
        List<Item> items = new ArrayList<>();
        List<LadderPrice> ladderPrices = new ArrayList<>();
        buildItemAndLadderPrice(items, ladderPrices, request, newHeader.getInquiryId());
        List<QuoteAuth> quoteAuths = new ArrayList<>();
        List<Vendor> vendors = builtVendors(request.getVendors(), newHeader.getInquiryId(), quoteAuths);

        /*查询评选信息*/
        QueryWrapper<QuoteSelection> wrapper = new QueryWrapper<>();
        wrapper.in("QUOTE_ITEM_ID", request.getQuoteItemIds());
        List<QuoteSelection> selections = list(wrapper);
        selections.forEach(selection -> {
            //修改选中报价行是否选定为继续议价
            selection.setIsSelected(RfqResult.CONTINUE_NEGOTIATE.getKey());
        });

        /*评分规则*/
        ScoreRule scoreRule = iScoreRuleService.getByHeadId(request.getInquiryId());
        List<ScoreRuleItem> ruleItems = iScoreRuleItemService.getByRuleId(scoreRule.getScoreRuleId());
        scoreRule.setScoreRuleId(IdGenrator.generate());
        scoreRule.setInquiryId(newHeader.getInquiryId());
        ruleItems.forEach(scoreRuleItem -> {
            scoreRuleItem.setScoreRuleItemId(IdGenrator.generate());
            scoreRuleItem.setScoreRuleId(scoreRule.getScoreRuleId());
        });

        iHeaderService.saveOrUpdateBatch(headers);
        iItemService.saveBatch(items);
        iLadderPriceService.saveBatch(ladderPrices);
        iVendorService.saveBatch(vendors);
        iQuoteAuthService.saveBatch(quoteAuths);
        iScoreRuleService.save(scoreRule);
        iScoreRuleItemService.saveBatch(ruleItems);
        updateBatchById(selections);
    }

    /**
     * 构建询价头
     */
    private Header buildInquiryHeader(Header header) {
        Header newHeader = new Header();
        BeanUtils.copyProperties(header, newHeader);
        newHeader.setInquiryId(IdGenrator.generate());
        newHeader.setParentInquiryId(header.getInquiryId());
        newHeader.setInquiryNo(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_INQUIRYNO_CODE));
        newHeader.setStatus(InquiryPublishStatusEnum.DRAFT.getKey());
        newHeader.setAuditStatus(ApproveStatusType.DRAFT.getValue());
        newHeader.setQuoteCnt(new BigDecimal("0"));
        return newHeader;
    }

    private List<Vendor> builtVendors(List<InquiryVendorDTO> vendorDTOS, Long inquiryId, List<QuoteAuth> quoteAuths) {
        List<Vendor> vendors = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(vendorDTOS)) {
            vendorDTOS.forEach(inquiryVendorDTO -> {
                Long vendorId = inquiryVendorDTO.getVendorId();
                Vendor vendor = new Vendor();
                BeanUtils.copyProperties(inquiryVendorDTO, vendor);
                vendor.setInquiryVendorId(IdGenrator.generate());
                vendor.setInquiryId(inquiryId);

                inquiryVendorDTO.getQuoteAuths().forEach(quoteAuthDTO -> {
                    QuoteAuth quoteAuth = new QuoteAuth();
                    BeanUtils.copyProperties(quoteAuthDTO, quoteAuth);
                    quoteAuth.setInquiryId(inquiryId);
                    quoteAuth.setInquiryQuoteAuthId(IdGenrator.generate());
                    quoteAuth.setVendorId(vendorId);
                    quoteAuths.add(quoteAuth);
                });
                vendors.add(vendor);
            });
        }
        return vendors;
    }

    /**
     * 构建询价物料和阶梯价信息
     */
    private void buildItemAndLadderPrice(List<Item> items, List<LadderPrice> ladderPrices,
                                         QuoteBargainingSaveRequestDTO request, Long inquiryId) {
        request.getItems().forEach(inquiryItemDTO -> {
            Item item = new Item();
            BeanUtils.copyProperties(inquiryItemDTO, item);
            item.setInquiryItemId(IdGenrator.generate());
            item.setInquiryId(inquiryId);
            items.add(item);
            if (CollectionUtils.isNotEmpty(inquiryItemDTO.getLadderPrices())) {
                inquiryItemDTO.getLadderPrices().forEach(inquiryLadderPriceDTO -> {
                    LadderPrice ladderPrice = new LadderPrice();
                    BeanUtils.copyProperties(inquiryLadderPriceDTO, ladderPrice);
                    ladderPrice.setInquiryLadderPriceId(IdGenrator.generate());
                    ladderPrice.setInquiryId(inquiryId);
                    ladderPrice.setInquiryItemId(item.getInquiryItemId());
                    ladderPrices.add(ladderPrice);
                });
            }

        });
    }

    /**
     * 综合评分法 + 组合评选
     */
    private Map<Long, List<QuoteSelectionQueryResponseDTO>> comprehensiveAndSupplier(
            Long inquiryId, List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {

        List<QuoteSelection> entities = new ArrayList<>(effectiveQuoteItems.size());
        //价格权重
        double priceWeight = 0;
        //绩效权重
        double performanceWeight = 0;
        /*获取价格和绩效权重*/
        ScoreRule scoreHead = iScoreRuleService.getByHeadId(inquiryId);
        List<ScoreRuleItem> byRuleId = iScoreRuleItemService.getByRuleId(scoreHead.getScoreRuleId());
        for(ScoreRuleItem rule : byRuleId) {
            if (ScoreDimension.PRICE_DIME.toString().equals(rule.getDimension())) {
                priceWeight = NumberUtil.div(rule.getScoreWeight().doubleValue(), 100);
            }else if (ScoreDimension.PERFORMANCE_DIME.toString().equals(rule.getDimension())) {
                performanceWeight = NumberUtil.div(rule.getScoreWeight().doubleValue(), 100);
            }
        }
        //构建供应商的总金额MAP
        HashMap<Long, Double> theSameVendorsMap = buildThesameVendorsMap(effectiveQuoteItems);
        //所有供应商最低总金额
        double pMin = theSameVendorsMap.entrySet().stream().mapToDouble(Map.Entry::getValue).min().getAsDouble();

        for (QuoteSelectionQueryResponseDTO item : effectiveQuoteItems) {

            //该供应商的总金额
            Double totalAmount = theSameVendorsMap.get(item.getVendorId());
            /*价格得分=100-[(总金额-Pmin)/Pmin]×100，四舍五入保留两位小数，如果价格得分<0，则默认为0*/
            double priceScore = NumberUtil.formatDoubleByScale(
                    NumberUtil.sub(100, NumberUtil.mul(NumberUtil.div(NumberUtil.sub(totalAmount, pMin), pMin), 100)),2);
            if (priceScore < 0) {
                priceScore = 0;
            }
            /*查询绩效得分*/
            double dimensionScore = 0;
            QuoteItemPerformance dimension =
                    iQuoteItemPerformanceService.getDimensionScore(inquiryId, item.getOrganizationId(), item.getVendorId(), item.getItemId());
            if (dimension != null) {
                dimensionScore = dimension.getScore().doubleValue();
            }
            /*综合得分=价格得分*价格权重+绩效得分*绩效权重，四舍五入保留两位小数*/
            double compositeScore = NumberUtil.formatDoubleByScale(
                    NumberUtil.add(NumberUtil.mul(priceScore, priceWeight), NumberUtil.mul(dimensionScore, performanceWeight)), 2);

            QuoteSelection entity = buildDefaultQuoteSelection(inquiryId, item);
            entity.setPriceScore(NumberUtil.doubleToBigDecimal(priceScore));
            entity.setQualityScore(NumberUtil.doubleToBigDecimal(dimensionScore));
            entity.setCompositeScore(NumberUtil.doubleToBigDecimal(compositeScore));
            entities.add(entity);

            item.setPriceScore(entity.getPriceScore());
            item.setQualityScore(entity.getQualityScore());
            item.setCompositeScore(entity.getCompositeScore());
        }
        /*根据供应商进行分组排名*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByVendor = rankingGroupByVendor(effectiveQuoteItems, entities);
        /*保存到数据库中*/
        saveBatch(entities);

        return groupByVendor;
    }

    /*
    * 综合评分法 + 组合评选
    */
    private Map<Long, List<QuoteSelectionQueryResponseDTO>> comprehensiveAndLine(
            Long inquiryId, List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {
        List<QuoteSelection> entities = new ArrayList<>(effectiveQuoteItems.size());
        //价格权重
        double priceWeight = 0;
        //绩效权重
        double performanceWeight = 0;
        /*获取价格和绩效权重*/
        ScoreRule scoreHead = iScoreRuleService.getByHeadId(inquiryId);
        List<ScoreRuleItem> byRuleId = iScoreRuleItemService.getByRuleId(scoreHead.getScoreRuleId());
        for(ScoreRuleItem rule : byRuleId) {
            if (ScoreDimension.PRICE_DIME.toString().equals(rule.getDimension())) {
                priceWeight = NumberUtil.div(rule.getScoreWeight().doubleValue(), 100);
            }else if (ScoreDimension.PERFORMANCE_DIME.toString().equals(rule.getDimension())) {
                performanceWeight = NumberUtil.div(rule.getScoreWeight().doubleValue(), 100);
            }
        }
        for (QuoteSelectionQueryResponseDTO item : effectiveQuoteItems) {

            //当前行未税单价
            double p = item.getNotaxPrice().doubleValue();
            /*根据该物料行过滤出相同物料的信息*/
            List<QuoteSelectionQueryResponseDTO> theSameItems =
                    effectiveQuoteItems.stream().filter(value -> value.getItemId().equals(item.getItemId())).collect(Collectors.toList());
            //过滤出的供应商的最低未税单价
            double pMin = theSameItems.stream().mapToDouble(value -> value.getNotaxPrice().doubleValue()).min().getAsDouble();
            /*价格得分=100-[(P-Pmin)/Pmin]×100，四舍五入保留两位小数，如果价格得分<0，则默认为0*/
            double priceScore = NumberUtil.formatDoubleByScale(
                    NumberUtil.sub(100, NumberUtil.mul(NumberUtil.div(NumberUtil.sub(p,pMin), pMin), 100)),2);
            if (priceScore < 0) {
                priceScore = 0;
            }
            /*查询绩效得分*/
            double dimensionScore = 0;
            QuoteItemPerformance dimension =
                    iQuoteItemPerformanceService.getDimensionScore(inquiryId, item.getOrganizationId(), item.getVendorId(), item.getItemId());
            if (dimension != null) {
                dimensionScore = dimension.getScore().doubleValue();
            }
            /*综合得分=价格得分*价格权重+绩效得分*绩效权重，四舍五入保留两位小数*/
            double compositeScore = NumberUtil.formatDoubleByScale(
                    NumberUtil.add(NumberUtil.mul(priceScore, priceWeight), NumberUtil.mul(dimensionScore, performanceWeight)), 2);

            QuoteSelection entity = buildDefaultQuoteSelection(inquiryId, item);
            entity.setPriceScore(NumberUtil.doubleToBigDecimal(priceScore));
            entity.setQualityScore(NumberUtil.doubleToBigDecimal(dimensionScore));
            entity.setCompositeScore(NumberUtil.doubleToBigDecimal(compositeScore));
            entities.add(entity);

            item.setPriceScore(entity.getPriceScore());
            item.setQualityScore(entity.getQualityScore());
            item.setCompositeScore(entity.getCompositeScore());
        }

        /*对物料进行分组然后排序*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByItem = rankingGroupByItem(effectiveQuoteItems, entities);
        /*保存到数据库中*/
        saveBatch(entities);

        return groupByItem;
    }

    /**
     * 合理低价评分法 + 组合评选
     */
    private Map<Long, List<QuoteSelectionQueryResponseDTO>> miniNumPriceMethodAndSupplier(
            Long inquiryId, List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {

        List<QuoteSelection> entities = new ArrayList<>(effectiveQuoteItems.size());
        //获取分数权重
        double scoreWeight =  getScoreWeight(inquiryId);
        //构建供应商的总金额MAP
        HashMap<Long, Double> theSameVendorsMap = buildThesameVendorsMap(effectiveQuoteItems);
        //所有供应商最低总金额
        double pMin = theSameVendorsMap.entrySet().stream().mapToDouble(Map.Entry::getValue).min().getAsDouble();

        effectiveQuoteItems.forEach(item -> {

            //该供应商的总金额
            Double totalAmount = theSameVendorsMap.get(item.getVendorId());
            /*价格得分=100-[(总金额-Pmin)/Pmin]×100，四舍五入保留两位小数，如果价格得分<0，则默认为0*/
            double priceScore = NumberUtil.formatDoubleByScale(
                    NumberUtil.sub(100, NumberUtil.mul(NumberUtil.div(NumberUtil.sub(totalAmount, pMin), pMin), 100)),2);
            if (priceScore < 0) {
                priceScore = 0;
            }
            /*综合得分=价格得分*价格权重*/
            double compositeScore = NumberUtil.mul(priceScore, scoreWeight);

            QuoteSelection entity = buildDefaultQuoteSelection(inquiryId, item);
            entity.setPriceScore(NumberUtil.doubleToBigDecimal(priceScore));
            entity.setCompositeScore(NumberUtil.doubleToBigDecimal(compositeScore));
            entities.add(entity);

            item.setPriceScore(entity.getPriceScore());
            item.setCompositeScore(entity.getCompositeScore());
        });
        /*根据供应商进行分组排名*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByVendor = rankingGroupByVendor(effectiveQuoteItems, entities);
        /*保存到数据库中*/
        saveBatch(entities);

        return groupByVendor;
    }

    private Map<Long, List<QuoteSelectionQueryResponseDTO>> rankingGroupByVendor(
            List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems, List<QuoteSelection> entities) {

        /*先按价格进行降序排序，然后再进行供应商的降序*/
        effectiveQuoteItems.sort(Comparator.comparing(QuoteSelectionQueryResponseDTO::getCompositeScore).reversed()
                .thenComparing(QuoteSelectionQueryResponseDTO::getVendorId));
        /*按供应商进行排名*/
        rankByVendor(effectiveQuoteItems, entities);
        /*按供应商进行分组返回*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByVendor =
                effectiveQuoteItems.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getVendorId));
        return groupByVendor;
    }

    /**
     * 按供应商进行排名
     */
    private void rankByVendor(List<QuoteSelectionQueryResponseDTO> dtos, List<QuoteSelection> entities) {
        //设置排名从1开始
        int rank = 1;
        //并列排名
        int theSameRank = 0;
        QuoteSelectionQueryResponseDTO before = null;
        for (QuoteSelectionQueryResponseDTO dto : dtos) {

            if (before != null && before.getVendorId().equals(dto.getVendorId())) {
                /*相同供应商排名一样*/
                dto.setRanking(before.getRanking());
            }else if (before != null && !before.getVendorId().equals(dto.getVendorId())
                    && before.getCompositeScore().equals(dto.getCompositeScore())) {
                /*不同供应商但分数一样排名则一样*/
                dto.setRanking(rank);
                theSameRank++;
            }else {
                /*不同供应商分数不同则排名+并列排名*/
                rank += theSameRank;
                dto.setRanking(rank++);
                theSameRank = 0;
            }

            if (dto.getRanking() == 1) {
                dto.setIsSelected(RfqResult.SELECTED.getKey());
            }
            /*当前数据保存起来供下一个数据进行判断*/
            before = dto;

            /*把查询排名放进数据库*/
            for (QuoteSelection entity : entities) {
                if (entity.getQuoteItemId().equals(dto.getQuoteItemId())) {
                    entity.setRanking(dto.getRanking());
                    entity.setIsSelected(dto.getIsSelected());
                    break;
                }
            }
        }
    }

    /**
     * 构建供应商的总金额MAP
     */
    private HashMap<Long, Double> buildThesameVendorsMap(List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {
        HashMap<Long, Double> theSameVendorsMap = new HashMap<>();
        /*按供应商计算出对应的总金额*/
        effectiveQuoteItems.forEach(item -> {
            /*根据该行过滤出相同供应商*/
            if (!theSameVendorsMap.containsKey(item.getVendorId())) {
                List<QuoteSelectionQueryResponseDTO> theSameVendors =
                        effectiveQuoteItems.stream().filter(value -> value.getVendorId().equals(item.getVendorId())).collect(Collectors.toList());
                //该供应商的总金额
                double total = 0;
                for (QuoteSelectionQueryResponseDTO vendor : theSameVendors) {
                    /*按供应商汇总各行金额得到总金额*/
                    total = NumberUtil.add(total, NumberUtil.mul(vendor.getNotaxPrice().doubleValue(), vendor.getDemandQuantity().doubleValue()));
                }
                theSameVendorsMap.put(item.getVendorId(), total);
            }
        });
        return theSameVendorsMap;
    }

    /**
     * 合理低价评分法 + 单项评选
     */
    private Map<Long, List<QuoteSelectionQueryResponseDTO>> miniNumPriceMethodAndLine(Long inquiryId,
                                                                                      List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems) {

        List<QuoteSelection> entities = new ArrayList<>(effectiveQuoteItems.size());
        //获取分数权重
        double scoreWeight = getScoreWeight(inquiryId);

        effectiveQuoteItems.forEach(item -> {

            //当前行未税单价
            double p = item.getNotaxPrice().doubleValue();
            /*根据该物料行过滤出相同物料的信息*/
            List<QuoteSelectionQueryResponseDTO> theSameItems =
                    effectiveQuoteItems.stream().filter(value -> value.getItemId().equals(item.getItemId())).collect(Collectors.toList());
            //过滤出的供应商的最低未税单价
            double pMin = theSameItems.stream().mapToDouble(value -> value.getNotaxPrice().doubleValue()).min().getAsDouble();
            /*价格得分=100-[(P-Pmin)/Pmin]×100，四舍五入保留两位小数，如果价格得分<0，则默认为0*/
            double score = NumberUtil.formatDoubleByScale(
                    NumberUtil.sub(100, NumberUtil.mul(NumberUtil.div(NumberUtil.sub(p,pMin), pMin), 100)),2);
            if (score < 0) {
                score = 0;
            }

            QuoteSelection entity = buildDefaultQuoteSelection(inquiryId, item);
            entity.setPriceScore(NumberUtil.doubleToBigDecimal(score));
            entity.setCompositeScore(NumberUtil.doubleToBigDecimal(NumberUtil.mul(score, scoreWeight)));
            entities.add(entity);

            item.setPriceScore(entity.getPriceScore());
            item.setCompositeScore(entity.getCompositeScore());
        });
        /*对物料进行分组然后排序*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByItem = rankingGroupByItem(effectiveQuoteItems, entities);
        /*保存到数据库中*/
        saveBatch(entities);

        return groupByItem;
    }

    /**
     * 构建新的评分信息
     */
    private QuoteSelection buildDefaultQuoteSelection(Long inquiryId, QuoteSelectionQueryResponseDTO item) {

        QuoteSelection entity = new QuoteSelection();
        BeanUtils.copyProperties(item, entity);
        entity.setQuoteSelectionId(IdGenrator.generate());
        entity.setInquiryId(inquiryId);
        return entity;
    }

    /**
     * 对物料进行分组然后排序
     */
    private Map<Long, List<QuoteSelectionQueryResponseDTO>> rankingGroupByItem(
            List<QuoteSelectionQueryResponseDTO> effectiveQuoteItems, List<QuoteSelection> entities) {
        /*对物料进行分组*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> groupByItem =
                effectiveQuoteItems.stream().collect(Collectors.groupingBy(QuoteSelectionQueryResponseDTO::getItemId));

        groupByItem.forEach((key, value) -> {
            /*对每个分组按综合得分进行排序*/
            value.sort(Comparator.comparing(QuoteSelectionQueryResponseDTO::getCompositeScore).reversed());
            /*排序完成后设置排名*/
            rankByLine(value, entities);
        });
        return groupByItem;
    }

    /**
     * 按行进行排名
     */
    private void rankByLine(List<QuoteSelectionQueryResponseDTO> dtos, List<QuoteSelection> entities) {
        //设置排名从1开始
        int rank = 1;
        //并列排名
        int theSameRank = 0;
        QuoteSelectionQueryResponseDTO before = null;
        for (QuoteSelectionQueryResponseDTO dto : dtos) {

            if (before != null && before.getCompositeScore().equals(dto.getCompositeScore())) {
                /*如果分数相同则直接设置相同的排名，并列排名+1*/
                dto.setRanking(rank);
                theSameRank++;
            }else {
                /*如果分数不同则设置排名+并列排名, 重置并列排名*/
                rank += theSameRank;
                dto.setRanking(rank++);
                theSameRank = 0;
            }

            if (dto.getRanking() == 1) {
                dto.setIsSelected(RfqResult.SELECTED.getKey());
            }
            /*当前数据保存起来供下一个数据进行判断*/
            before = dto;

            /*把查询排名放进数据库*/
            for (QuoteSelection entity : entities) {
                if (entity.getQuoteItemId().equals(dto.getQuoteItemId())) {
                    entity.setRanking(dto.getRanking());
                    entity.setIsSelected(dto.getIsSelected());
                    break;
                }
            }
        }

    }

    /**
     * 获取分数权重
     */
    private double getScoreWeight(Long inquiryId) {
        ScoreRule scoreHead = iScoreRuleService.getByHeadId(inquiryId);
        List<ScoreRuleItem> byRuleId = iScoreRuleItemService.getByRuleId(scoreHead.getScoreRuleId());
        if(CollectionUtils.isNotEmpty(byRuleId)) {
            //价格权重,由于不是存的小数，所以先除以100
            return NumberUtil.div(byRuleId.get(0).getScoreWeight().doubleValue(), 100);
        }
        return 0.0;
    }

    /**
     * 计算现价
     */
    private BigDecimal getCurrentPrice(EffectiveQuoteItemsResult itemsResult) {
        /*查询价格目录表*/
        List<PriceLibrary> priceLibraries =
                iPriceLibraryService.getQuotePrice(itemsResult.getOrganizationId(), itemsResult.getVendorId(), itemsResult.getItemId());
        if (CollectionUtils.isEmpty(priceLibraries)) {
            return null;
        }

        PriceLibrary priceLibrary = priceLibraries.get(0);
        if (YesOrNo.YES.getValue().equalsIgnoreCase(priceLibrary.getIsLadder())) {
            /*如果价格目录是阶梯价，则根据数量找到对应的阶梯价格*/
            List<PriceLadderPrice> ladderPrice = iPriceLadderPriceService.getLadderPrice(priceLibrary.getPriceLibraryId());
            for (PriceLadderPrice price : ladderPrice) {
                if (itemsResult.getDemandQuantity().doubleValue() >= price.getBeginQuantity().doubleValue() &&
                        itemsResult.getDemandQuantity().doubleValue() <= price.getEndQuantity().doubleValue()) {
                    /*数量在区间内*/
                    return price.getPrice();
                }
            }
        }else {
            return priceLibrary.getNotaxPrice();
        }

        return null;
    }

    /**
     * 计算报价差价
     */
    private void calculateQuotePriceDifference(List<EffectiveQuoteItemsResult> effectiveQuoteItems, String itemCode,
                                               QuoteItemCalculation calculation) {

        /*过滤出相同的物料*/
        List<EffectiveQuoteItemsResult> thesameItems = effectiveQuoteItems.stream().filter(s ->
                s.getItemCode().equals(itemCode)).collect(Collectors.toList());
        /*该物料最高价*/
        double itemMaxPrice = thesameItems.stream().mapToDouble(value -> value.getNotaxPrice().doubleValue()).max().getAsDouble();
        /*该物料最低价*/
        double itemMinPrice = thesameItems.stream().mapToDouble(value -> value.getNotaxPrice().doubleValue()).min().getAsDouble();
        calculation.setQuotePriceDifference(calculateQuotePriceDifference(itemMaxPrice, itemMinPrice));
    }

    /**
     * （该物料的最高报价-最低报价）/最低报价*100%
     */
    public BigDecimal calculateQuotePriceDifference(Double maxPrice, Double minPrice) {
        Double result = NumberUtil.mul(NumberUtil.div(NumberUtil.sub(maxPrice, minPrice), minPrice), 100);
        return NumberUtil.doubleToBigDecimal(result);
    }
}