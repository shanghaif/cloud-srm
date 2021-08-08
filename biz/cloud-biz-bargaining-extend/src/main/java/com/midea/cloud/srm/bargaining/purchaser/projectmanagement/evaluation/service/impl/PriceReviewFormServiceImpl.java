package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.ISourcingResultReportService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.IEvaluationService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.IPriceReviewFormService;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.mapper.BidVendorFileMapper;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.inq.PriceApprovalClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.QuotaDistributeType;
import com.midea.cloud.srm.model.inq.price.dto.InsertPriceApprovalDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItemPaymentTerm;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.pm.pr.documents.param.RemoveParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Implement of {@link IPriceReviewFormService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class PriceReviewFormServiceImpl implements IPriceReviewFormService {

    private final EntityManager<Biding> biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidRequirement> demandHeaderDao
            = EntityManager.use(BidRequirementMapper.class);
    private final EntityManager<BidVendorFile> bidVendorFileDao
            = EntityManager.use(BidVendorFileMapper.class);

    @Resource
    private IEvaluationService evaluationService;
    @Resource
    private PriceApprovalClient priceApprovalClient;
    @Resource
    private ISourcingResultReportService resultReportService;
    @Resource
    private BaseClient baseClient;
    @Resource
    private IBidRequirementLineService requirementLineService;
    @Resource
    private PmClient pmClient;

    private final ThreadPoolExecutor ioThreadPool;

    public PriceReviewFormServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ioThreadPool = new ThreadPoolExecutor(cpuCount + 1, cpuCount + 1,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024),
                new NamedThreadFactory("notInRuntimeThreadPool", true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void generateForm(Long biddingId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingId)
                .map(biddingDao::findById)
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + biddingId + "]"));

        if ("Y".equals(bidding.getGeneratePriceApproval()))
            throw new BaseException("招标单: [" + bidding.getBidingNum() + "]已生成价格审批单。");

        if (BiddingProjectStatus.PROJECT_END.equals(BiddingProjectStatus.get(bidding.getBidingStatus())))
            throw new BaseException("招标单: [" + bidding.getBidingNum() + "]非已结项状态，不能生成价格审批。");


        // 获取 中标结果
        List<EvaluationResult> winningResults = evaluationService.findEvaluationResults(
                EvaluationQueryParam.builder()
                        .bidingId(bidding.getBidingId())
                        .round(bidding.getCurrentRound())
                        .selectionStatus(SelectionStatusEnum.WIN.getValue())
                        .build()
        );
        // 如果一行都没中标，则提示
        if (winningResults.isEmpty())
            throw new BaseException("没有任何中标结果。 | 招标单: [" + bidding.getBidingNum() + "]");

        // 获取 招标寻求[头]
        BidRequirement demandHeader = Optional.ofNullable(demandHeaderDao
                .findOne(Wrappers.lambdaQuery(BidRequirement.class)
                        .eq(BidRequirement::getBidingId, bidding.getBidingId())
                ))
                .orElseThrow(() -> new BaseException("获取招标寻求头失败。 | biddingId: [" + bidding.getBidingId() + "]"));
        //释放
        List<Long> reqLineIds = winningResults.stream().map(EvaluationResult::getRequirementLineId).distinct().collect(Collectors.toList());
        List<BidRequirementLine> collect = requirementLineService.
                list(Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, biddingId)
                        .notIn(BidRequirementLine::getRequirementLineId, reqLineIds)
                ).stream().filter(e -> StringUtils.isNotBlank(e.getPurchaseRequestNum()) && StringUtils.isNotBlank(e.getPurchaseRequestRowNum())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            Map<String, List<BidRequirementLine>> map = collect.stream().collect(Collectors.groupingBy(BidRequirementLine::getPurchaseRequestNum));
            RemoveParam param = new RemoveParam();
            param.setFormNo(biddingId.toString());
            Map<String, List<String>> m = new HashMap<>();
            map.forEach((k, v) -> {
                List<String> list = v.stream().map(BidRequirementLine::getPurchaseRequestRowNum).collect(Collectors.toList());
                m.put(k, list);
            });
            param.setParams(m);
            pmClient.deleletByRowNumAndFolloFormId(param);
        }
        // 获取 供应商投标附件
        List<BidVendorFile> bidVendorFiles = bidVendorFileDao
                .findAll(Wrappers.lambdaQuery(BidVendorFile.class)
                        .eq(BidVendorFile::getBidingId, bidding.getBidingId())
                );
        //中标总金额
        String quotaDistributeType = bidding.getQuotaDistributeType();
        boolean isNullQuota = Objects.equals(quotaDistributeType, QuotaDistributeType.NULL_ALLOCATION.getCode());
        BigDecimal winAmount = BigDecimal.ZERO;
        /**
         * 1)	如果配额类型不为空：中标总金额=每一中标行的配额/100*需求数量*含税单价
         * 2)	如果配额类型为空
         * a)	中标行的中标数量必填
         * b)	中标总金额=每一中标行的中标数量*含税单价
         */
        for (EvaluationResult winningResult : winningResults) {
            String currencyCode = winningResult.getCurrencyCode();
            BigDecimal currentTaxPrice = winningResult.getPrice();
            //汇率转换
            if (!Objects.equals(currencyCode, bidding.getStandardCurrency())) {
                BigDecimal rate = baseClient.getRateByFromTypeAndToType(currencyCode, bidding.getStandardCurrency());
                currentTaxPrice = currentTaxPrice.multiply(rate);
            }
            if (isNullQuota) {
                BigDecimal quantity = Optional.ofNullable(winningResult.getQuotaQuantity()).orElseThrow(() -> new BaseException("请维护中标行的中标数量"));
                //每一中标行的中标数量*含税单价
                winAmount = winAmount.add(quantity.multiply(currentTaxPrice));
            }
            if (!isNullQuota) {
                //每一中标行的配额/100*需求数量*含税单价
                BigDecimal ratio = Optional.ofNullable(winningResult.getQuotaRatio()).orElseThrow(() -> new BaseException("请点击计算配额"));
                BigDecimal quotaQuantity = ratio.divide(BigDecimal.valueOf(100)).multiply(new BigDecimal(winningResult.getQuantity()));
                BigDecimal money = quotaQuantity.multiply(currentTaxPrice);
                winAmount = winAmount.add(money);
                winningResult.setQuotaQuantity(quotaQuantity);
            }
        }
        // 价格审批单[头]
        ApprovalHeader approvalHeader = ApprovalHeader.builder()
                .sourceType(SourcingType.RFQ.getItemValue())
                .ceeaTitle(bidding.getBidingName())
                .ceeaSourceNo(bidding.getBidingNum())
                .ceeaAwareWay(bidding.getBidingAwardWay())
                .ceeaAllocationType(quotaDistributeType)
                .ceeaIfUpdatePriceLibrary(bidding.getIsSyncToPriceLibrary())
                .ceeaRequirementOverview(demandHeader.getRequireDesc()) // todo
                .ceeaDescription(demandHeader.getRequireDesc())
                .standardCurrency(bidding.getStandardCurrency())
                .bidAmount(winAmount)
                .build();
        // 价格审批单[中标行]
        List<ApprovalBiddingItemVO> approvalItems = winningResults.stream()
                .map(winningResult -> {

                    // 中标行
                    ApprovalBiddingItem item = ApprovalBiddingItem.builder()
                            .fromContractId(winningResult.getFromContractId())
                            .fromContractLineId(winningResult.getFromContractLineId())
                            .fromContractCode(winningResult.getFromContractCode())
                            .purchaseRequestNum(winningResult.getPurchaseRequestNum())
                            .purchaseRequestRowNum(winningResult.getPurchaseRequestRowNum())
                            .priceType(winningResult.getPriceType())
                            .ouId(winningResult.getOuId())
                            .ouNumber(winningResult.getOuNumber())
                            .ouName(winningResult.getOuName())
                            .orgId(winningResult.getOrgId())
                            .comments(winningResult.getComments())
                            .orgCode(winningResult.getOrgCode())
                            .orgName(winningResult.getOrgName())
                            .organizationId(winningResult.getInvId())
                            .organizationCode(winningResult.getInvCode())
                            .organizationName(winningResult.getInvName())
                            .arrivalPlace(winningResult.getDeliveryPlace())
                            .vendorId(winningResult.getVendorId())
                            .vendorCode(winningResult.getVendorCode())
                            .vendorName(winningResult.getVendorName())
                            .itemId(winningResult.getTargetId())
                            .itemCode(winningResult.getTargetNum())
                            .itemName(winningResult.getTargetDesc())
                            .itemDescription(winningResult.getTargetDesc())
                            .categoryId(winningResult.getCategoryId())
                            .categoryCode(winningResult.getCategoryCode())
                            .categoryName(winningResult.getCategoryName())
                            .needNum(Optional.ofNullable(winningResult.getQuantity())
                                    .map(BigDecimal::valueOf)
                                    .orElse(BigDecimal.ZERO)
                            )
                            .unit(winningResult.getUomCode())
                            .taxPrice(winningResult.getPrice())
                            .currencyId(winningResult.getCurrencyId())
                            .currencyCode(winningResult.getCurrencyCode())
                            .currencyName(winningResult.getCurrencyName())
                            .standardCurrency(winningResult.getStandardCurrency())
                            .taxKey(winningResult.getTaxKey())
                            .taxRate(winningResult.getTaxRate())
                            .notaxPrice(winningResult.getNoTaxPrice())
                            .paymentProvision(null) // todo
                            .quotaDistributionType(winningResult.getQuotaDistributeType())
                            .quotaProportion(winningResult.getQuotaRatio())
                            .lAndT(null)            // todo
                            .startTime(Optional.ofNullable(winningResult.getPriceStartTime())
                                    .map(date -> Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                                    .orElse(null)
                            )
                            .endTime(Optional.ofNullable(winningResult.getPriceEndTime())
                                    .map(date -> Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                                    .orElse(null)
                            )
                            .isSeaFoodFormula(winningResult.getIsSeaFoodFormula())
                            .priceJson(winningResult.getPriceJson())
                            .formulaId(winningResult.getFormulaId())
                            .formulaValue(winningResult.getFormulaValue())
                            .essentialFactorValues(winningResult.getEssentialFactorValues())
                            .quotaQuantity(winningResult.getQuotaQuantity())
                            .formulaResult(winningResult.getFormulaResult())
                            .warrantyPeriod(winningResult.getWarrantyPeriod())
                            .tradeTerm(winningResult.getTradeTerm())
                            .build();
                    // 付款条款
                    List<ApprovalBiddingItemPaymentTerm> paymentTerms = winningResult.getPaymentTerms().stream()
                            .map(paymentTerm -> ApprovalBiddingItemPaymentTerm.builder()
                                    .paymentTerm(paymentTerm.getPaymentTerm())
                                    .paymentWay(paymentTerm.getPaymentWay())
                                    .paymentDay(paymentTerm.getPaymentDay())
                                    .paymentRatio(paymentTerm.getPaymentRatio())
                                    .paymentStage(paymentTerm.getPaymentStage())
                                    .paymentDayCode(paymentTerm.getPaymentDayCode())
                                    .build())
                            .collect(Collectors.toList());

                    // 创建VO
                    return ApprovalBiddingItemVO.create(item, paymentTerms);
                })
                .collect(Collectors.toList());

        // 价格审批单[附件]
        List<ApprovalFile> approvalFiles = bidVendorFiles.stream()
                .map(bidVendorFile -> ApprovalFile.builder()
                        .fileRelationId(Long.parseLong(bidVendorFile.getDocId()))
                        .fileName(bidVendorFile.getFileName())
                        .build())
                .collect(Collectors.toList());


        // 创建 记录
        bidding.setGeneratePriceApproval("Y");
        biddingDao.save(bidding);

        // 推送中标结果至价格模块，进行价格审批单创建
        Long inquiryId = priceApprovalClient.savePriceApproval(InsertPriceApprovalDTO.builder()
                .approvalHeader(approvalHeader)
                .approvalBiddingItemList(approvalItems)
                .approvalFiles(approvalFiles)
                .build());
//        resultReportService.generate(bidding.getBidingNum(), inquiryId);
        ioThreadPool.submit(() -> {
            resultReportService.generate(bidding.getBidingNum(), inquiryId);
        });
    }
}
