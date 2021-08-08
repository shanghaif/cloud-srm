package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.midea.cloud.common.constants.BidConstant;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.BidingAwardWayEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.exception.BidException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidBidingCurrencyMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.mapper.RoundMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.IRoundService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.ICalculateScoreService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.IEvaluationService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.IPriceReviewFormService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.*;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.model.base.customtable.vo.CustomTableVO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.enums.PaymentProvisionType;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.enums.QuotaDistributeType;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.enums.RequirementPricingType;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.param.CreateFollowTenderParam;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo.EvaluationResultDto;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo.EvaluationResultImportDto;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplateReportLineVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplateTempReportVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.TemplateVendorPriceVO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParam;
import com.midea.cloud.srm.model.inq.quota.vo.WinVendorInfoDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 评选
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月26日 下午7:29:54
 *  修改内容:
 *          </pre>
 */
@Service
@Transactional
@Slf4j
public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    private IBidingService iBidingService;

    @Autowired
    private IOrderLineService iOrderLineService;

    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;

    @Autowired
    private IRoundService iRoundService;

    @Autowired
    private IBidVendorService iBidVendorService;

    @Autowired
    private InqClient inqClient;
    @Autowired
    private BaseClient baseClient;
    @Resource
    private OrderLineMapper orderLineMapper;
    @Resource
    private BidOrderLineTemplatePriceDetailMapper templateHeaderMapper;
    @Resource
    private BidOrderLineTemplatePriceDetailLineMapper templateLineMapper;
    @Resource
    private BidOrderLineFormulaPriceDetailMapper formulaPriceDetailMapper;


    private final EntityManager<Biding> biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<Round> biddingRoundDao
            = EntityManager.use(RoundMapper.class);
    private final EntityManager<BidRequirementLine> demandLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<BidVendor> bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<OrderHead> orderHeaderDao
            = EntityManager.use(OrderHeadMapper.class);
    private final EntityManager<OrderLine> orderLineDao
            = EntityManager.use(OrderLineMapper.class);
    private final EntityManager<BidBidingCurrency> biddingCurrencyDao
            = EntityManager.use(BidBidingCurrencyMapper.class);
    private final EntityManager<OrderlinePaymentTerm> orderLinePaymentTermDao
            = EntityManager.use(OrderlinePaymentTermMapper.class);

    @Resource
    private ICalculateScoreService calculateScoreService;
    @Resource
    private IPriceReviewFormService priceReviewFormService;
    @Resource
    private IOrderLineService orderLineService;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<EvaluationResultImportDto> evaluationResultDtos = readData(file);
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        AtomicBoolean flag = new AtomicBoolean(false);
        // 校验数据
        checkData(flag, evaluationResultDtos, orderLines);
        if (flag.get()) {
            // 有报错
            fileupload.setFileSourceName("评选结果导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    evaluationResultDtos, EvaluationResultImportDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(), fileupload1.getSceneFileSourceName());
        } else {
            if (CollectionUtils.isNotEmpty(orderLines)) {
                List<OrderLine> collect = orderLines.stream().map(e -> new OrderLine().setOrderLineId(e.getOrderLineId())
                        .setQuotaQuantity(e.getQuotaQuantity())
                        .setQuotaRatio(e.getQuotaRatio())
                        .setSelectionStatus(e.getSelectionStatus())
                ).collect(Collectors.toList());
               /* orderLines.forEach(orderLine -> {
                    UpdateWrapper<OrderLine> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.set("SELECTION_STATUS", orderLine.getSelectionStatus());
                    updateWrapper.set("CEEA_QUOTA_QUANTITY", orderLine.getQuotaQuantity());
                    updateWrapper.eq("ORDER_LINE_ID", orderLine.getOrderLineId());
                    orderLineService.update(updateWrapper);
                });*/
                orderLineService.updateBatchById(collect);
            }
            return ImportStatus.importSuccess();
        }
    }

    private void checkData(AtomicBoolean flag, List<EvaluationResultImportDto> evaluationResultDtos, ArrayList<OrderLine> orderLines) {
        if (CollectionUtils.isNotEmpty(evaluationResultDtos)) {
            Map<String, String> dicMap = setMapValueKey("BIDDING_SELECT_STATES");
            Set<String> ids = evaluationResultDtos.stream().map(EvaluationResultImportDto::getOrderLineId).collect(Collectors.toSet());
            Map<Long, OrderLine> orderLineMap = orderLineService.list(Wrappers.lambdaQuery(OrderLine.class)
                    .select(OrderLine::getOrderLineId, OrderLine::getRequirementLineId)
                    .in(OrderLine::getOrderLineId, ids)
            ).stream().collect(Collectors.toMap(OrderLine::getOrderLineId, Function.identity()));

            Set<Long> requirementIds = orderLineMap.values().stream().map(OrderLine::getRequirementLineId).collect(Collectors.toSet());
            Map<Long, Double> requirementMap = iBidRequirementLineService.list(Wrappers.lambdaQuery(BidRequirementLine.class)
                    .in(BidRequirementLine::getRequirementLineId, requirementIds)
            ).stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, BidRequirementLine::getQuantity));
            evaluationResultDtos.forEach(evaluationResultImportDto -> {
                StringBuffer errorMsg = new StringBuffer();
                OrderLine orderLine = new OrderLine();
                // 校验唯一行标识
                String orderLineId = evaluationResultImportDto.getOrderLineId();
                if (StringUtil.notEmpty(orderLineId)) {
                    orderLineId = orderLineId.trim();
                    long id = Long.parseLong(orderLineId);
                    OrderLine orderLine1 = orderLineMap.get(id);
                    if (Objects.nonNull(orderLine1)) {
                        orderLine.setOrderLineId(id);
                    } else {
                        flag.set(true);
                        errorMsg.append("数据库找不着行内容, 请勿修改行唯一标识; ");
                    }
                } else {
                    flag.set(true);
                    errorMsg.append("缺少行唯一标识; ");
                }
                // 中标数量
                String quotaQuantity = evaluationResultImportDto.getQuotaQuantity();
                if (StringUtil.notEmpty(quotaQuantity)) {
                    quotaQuantity = quotaQuantity.trim();
                    if (StringUtil.isDigit(quotaQuantity)) {
                        orderLine.setQuotaQuantity(new BigDecimal(quotaQuantity));
                    } else {
                        flag.set(true);
                        errorMsg.append("中标数量格式非法; ");
                    }
                }

                // 配额
                String quotaRatio = evaluationResultImportDto.getQuotaRatio();
                if (StringUtil.notEmpty(quotaRatio)) {
                    quotaRatio = quotaRatio.trim();
                    if (StringUtil.isDigit(quotaRatio)) {
                        orderLine.setQuotaRatio(new BigDecimal(quotaRatio));
                        if (StringUtils.isBlank(evaluationResultImportDto.getQuotaQuantity())) {
                            OrderLine orderLine1 = orderLineMap.get(orderLine.getOrderLineId());
                            if (Objects.nonNull(orderLine1)) {
                                Long requirementLineId = orderLine1.getRequirementLineId();
                                Double needCount = requirementMap.get(requirementLineId);
                                if (Objects.nonNull(needCount)) {
                                    orderLine.setQuotaQuantity(orderLine.getQuotaRatio().multiply(BigDecimal.valueOf(0.01))
                                            .multiply(BigDecimal.valueOf(needCount)));
                                }
                            }
                        }
                    } else {
                        flag.set(true);
                        errorMsg.append("配额格式非法; ");
                    }
                }


                // 检查评选结果状态
                String selectionStatus = evaluationResultImportDto.getSelectionStatus();
                if (StringUtil.notEmpty(evaluationResultImportDto.getSelectionStatus())) {
                    selectionStatus = selectionStatus.trim();
                    if (StringUtil.notEmpty(dicMap.get(selectionStatus))) {
                        orderLine.setSelectionStatus(dicMap.get(selectionStatus));
                    } else {
                        flag.set(true);
                        errorMsg.append("审核状态字典值不存在; ");
                    }
                }

                orderLines.add(orderLine);

                if (errorMsg.length() > 0) {
                    evaluationResultImportDto.setErrorMsg(errorMsg.toString());
                }
            });
        }
    }

    private List<EvaluationResultImportDto> readData(MultipartFile file) {
        List<EvaluationResultImportDto> evaluationResultDtos;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<EvaluationResultImportDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(EvaluationResultImportDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            evaluationResultDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return evaluationResultDtos;
    }

    @Override
    public void importModelDownload(EvaluationQueryParam queryParam, HttpServletResponse response) throws Exception {
        Assert.notNull(queryParam.getBidingId(), "缺少必传参数:bidingId");
        // 触发智能评选
        try {
            intelligentEvaluation(queryParam.getBidingId());
        } catch (Exception e) {
            log.error("导出触发智能评选报错:" + e);
        }
        // 触发计算配额
//        try {
//            calculateQuotaResult(queryParam.getBidingId());
//        } catch (Exception e) {
//            log.error("导出触发计算配额报错:"+e);
//        }
        // --------------------------------------导出数据----------------------------------------
        List<EvaluationResult> evaluationResultPageInfo = findEvaluationResults(queryParam);
        Map<String, String> dicMap = getImportDic(); // 字典集合
        Map<String, Map<String, String>> dicMap1 = getDicMap();
        ArrayList<EvaluationResultDto> evaluationResultDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(evaluationResultPageInfo)) {
            evaluationResultPageInfo.forEach(evaluationResult -> {
                EvaluationResultDto evaluationResultDto = new EvaluationResultDto();
                BeanCopyUtil.copyProperties(evaluationResultDto, evaluationResult);
                // id
                evaluationResultDto.setOrderLineId(String.valueOf(evaluationResult.getOrderLineId()));
//                // 拼接税率
//                if(StringUtil.notEmpty(evaluationResult.getTaxRate()) && StringUtil.notEmpty(dicMap.get(evaluationResult.getTaxRate().toString()))){
//                    evaluationResultDto.setTaxKey(dicMap.get(evaluationResult.getTaxRate().toString()) + "/" + evaluationResult.getTaxRate().doubleValue());
//                }
                if (YesOrNo.YES.getValue().equals(evaluationResult.getWin())) {
                    evaluationResultDto.setWin("入围");
                } else {
                    evaluationResultDto.setWin(null);
                }
                // 字典值转换
                if (StringUtil.notEmpty(evaluationResult.getPriceType()) && StringUtil.notEmpty(dicMap1.get("PRICE_TYPE").get(evaluationResult.getPriceType()))) {
                    evaluationResultDto.setPriceType(dicMap1.get("PRICE_TYPE").get(evaluationResult.getPriceType()));
                }
                // 配额分类类型
                if (StringUtil.notEmpty(evaluationResult.getQuotaDistributeType()) && StringUtil.notEmpty(dicMap1.get("QUOTA_DISTRIBUTE_TYPE").get(evaluationResult.getQuotaDistributeType()))) {
                    evaluationResultDto.setQuotaDistributeType(dicMap1.get("QUOTA_DISTRIBUTE_TYPE").get(evaluationResult.getQuotaDistributeType()));
                }
                // 贸易条款 tradeTerm
                if (StringUtil.notEmpty(evaluationResult.getTradeTerm()) && StringUtil.notEmpty(dicMap1.get("trade_clause").get(evaluationResult.getTradeTerm()))) {
                    evaluationResultDto.setTradeTerm(dicMap1.get("trade_clause").get(evaluationResult.getTradeTerm()));
                }
                // 是否代理报价
                if (YesOrNo.YES.getValue().equals(evaluationResult.getIsProxyBidding())) {
                    evaluationResultDto.setIsProxyBidding(YesOrNo.YES.getName());
                } else {
                    evaluationResultDto.setIsProxyBidding(YesOrNo.NO.getName());
                }
                // 本币种
                if (StringUtil.notEmpty(evaluationResult.getStandardCurrency()) && StringUtil.notEmpty(dicMap.get(evaluationResult.getStandardCurrency()))) {
                    evaluationResultDto.setStandardCurrency(dicMap.get(evaluationResult.getStandardCurrency()));
                }
                // 评选结果
                if (StringUtil.notEmpty(evaluationResult.getSelectionStatus()) && StringUtil.notEmpty(dicMap1.get("BIDDING_SELECT_STATES").get(evaluationResult.getSelectionStatus()))) {
                    evaluationResultDto.setSelectionStatus(dicMap1.get("BIDDING_SELECT_STATES").get(evaluationResult.getSelectionStatus()));
                }
                evaluationResultDtos.add(evaluationResultDto);
            });
            Map<String, Integer> hashMap = new HashMap<>();
            AtomicInteger index = new AtomicInteger(1);
            if (CollectionUtils.isNotEmpty(evaluationResultDtos)) {
                evaluationResultDtos.forEach(evaluationResultDto -> {
                    Optional.ofNullable(evaluationResultDto.getTargetDesc()).ifPresent(itemCode -> {
                        Integer no = hashMap.get(itemCode);
                        if (Objects.nonNull(no)) {
                            evaluationResultDto.setNo(no);
                        } else {
                            evaluationResultDto.setNo(index.get());
                            hashMap.put(itemCode, index.get());
                            index.addAndGet(1);
                        }
                    });
                });
            }

            /**
             * 对导出结果分组排序处理, 跟项目需求的排序一样
             * OU组编号OR库存组织+需求数量+物料描述作为唯一标识；
             * 如果不唯一，不报错，随意排序
             */
            // 获取项目需求
            BidRequirementLine bidRequirementLine = new BidRequirementLine();
            bidRequirementLine.setBidingId(queryParam.getBidingId());
            bidRequirementLine.setPageNum(1);
            bidRequirementLine.setPageSize(9999);
            List<BidRequirementLine> bidRequirementLines = iBidRequirementLineService.listPage(bidRequirementLine).getList();

            List<EvaluationResultDto> evaluationResultDtoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(evaluationResultDtos) && CollectionUtils.isNotEmpty(bidRequirementLines)) {
                List<String> orderKeys = bidRequirementLines.stream().map(bidRequirement -> {
                    StringBuffer key = new StringBuffer();
                    key.append(bidRequirement.getOuNumber()).
                            append(bidRequirement.getInvName()).
                            append(bidRequirement.getQuantity()).
                            append(bidRequirement.getTargetDesc());
                    return key.toString();
                }).distinct().collect(Collectors.toList());

                Map<String, List<EvaluationResultDto>> evaluationResultDtoMap = evaluationResultDtos.stream().collect(Collectors.groupingBy(
                        evaluationResultDto -> {
                            StringBuffer key = new StringBuffer();
                            key.append(evaluationResultDto.getOuNumber()).
                                    append(evaluationResultDto.getInvName()).
                                    append(evaluationResultDto.getQuantity()).
                                    append(evaluationResultDto.getTargetDesc());
                            return key.toString();
                        }
                ));
                AtomicInteger num = new AtomicInteger(1);
                orderKeys.forEach(key -> {
                    List<EvaluationResultDto> evaluationResults = evaluationResultDtoMap.get(key);
                    if (CollectionUtils.isNotEmpty(evaluationResults)) {
                        evaluationResults.forEach(evaluationResultDto -> evaluationResultDto.setNo(num.get()));
                        evaluationResultDtoList.addAll(evaluationResults);
                        num.addAndGet(1);
                    }
                });
            }

            // EvaluationResultDto
            String fileName = "评选结果";
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
            EasyExcelUtil.writeExcelWithModel(outputStream, fileName, evaluationResultDtoList, EvaluationResultDto.class);
        }
    }

    /**
     * 获取供应商导入所需字典
     *
     * @return
     */
    public Map<String, String> getImportDic() {
        HashMap<String, String> dicMap = new HashMap<>();
        // 币种
        List<PurchaseCurrency> purchaseCurrencies = baseClient.listCurrencyAll();
        if (CollectionUtils.isNotEmpty(purchaseCurrencies)) {
            purchaseCurrencies.forEach(purchaseCurrency -> {
                dicMap.put(purchaseCurrency.getCurrencyCode(), purchaseCurrency.getCurrencyName());
            });
        }

        // 税率
        List<PurchaseTax> purchaseTaxes = baseClient.listTaxAll();
        if (CollectionUtils.isNotEmpty(purchaseTaxes)) {
            purchaseTaxes.forEach(purchaseTax -> {
                if (StringUtil.notEmpty(purchaseTax.getTaxCode())) {
                    dicMap.put(String.valueOf(purchaseTax.getTaxCode()), purchaseTax.getTaxKey());
                }
            });
        }
        return dicMap;
    }

    private Map<String, Map<String, String>> getDicMap() {
        HashMap<String, Map<String, String>> hashMap = new HashMap<>();

        // 查询字典
        hashMap.put("PRICE_TYPE", setMapKeyValue("PRICE_TYPE"));
        hashMap.put("QUOTA_DISTRIBUTE_TYPE", setMapKeyValue("QUOTA_DISTRIBUTE_TYPE"));
        hashMap.put("trade_clause", setMapKeyValue("trade_clause"));
        hashMap.put("BIDDING_SELECT_STATES", setMapKeyValue("BIDDING_SELECT_STATES"));

        return hashMap;
    }

    /**
     * 查询字典集合
     *
     * @param dicCode
     */
    public Map<String, String> setMapKeyValue(String dicCode) {
        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode(dicCode);
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemCode(), dictItemDTO.getDictItemName());
            });
        }
        return map;
    }

    /**
     * 查询字典集合
     *
     * @param dicCode
     */
    public Map<String, String> setMapValueKey(String dicCode) {
        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode(dicCode);
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemName().trim(), dictItemDTO.getDictItemCode().trim());
            });
        }
        return map;
    }

    @Override
    public List<EvaluationResult> findEvaluationResults(EvaluationQueryParam queryParam) {
        // 空字符清空
        StringUtil.trim(queryParam);


        // 获取 招标单
        Biding bidding = Optional.ofNullable(queryParam.getBidingId())
                .map(biddingDao::findById)
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + queryParam.getBidingId() + "]"));

        // 获取 招标轮次
        Round round = Optional.ofNullable(queryParam.getRound())
                .map(roundCount -> biddingRoundDao.findOne(
                        Wrappers.lambdaQuery(Round.class)
                                .eq(Round::getBidingId, bidding.getBidingId())
                                .eq(Round::getRound, roundCount)
                ))
                .orElseThrow(() -> new BaseException("获取招标轮次失败。 | biddingId: [" + bidding.getBidingId() + "], round: [" + queryParam.getRound() + "]"));

        // 未开标，不能查看报价
        if (!"Y".equals(round.getBusinessOpenBid()))
            return Collections.emptyList();


        // 获取 指定轮次投标行
        List<OrderLine> orderLines = orderLineDao.findAll(
                Wrappers.lambdaQuery(OrderLine.class)
                        .eq(OrderLine::getBidingId, bidding.getBidingId())
                        .eq(OrderLine::getRound, round.getRound())
                        .eq(OrderLine::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue())
        ).stream().filter(e -> {
            if (queryParam.isFilterQuit()) {
                return !Objects.equals(e.getWin(), "Q");
            }
            return true;
        }).collect(Collectors.toList());
        if (orderLines.isEmpty())
            return Collections.emptyList();

        Set<Long> orderLineIds = orderLines.stream().map(OrderLine::getOrderLineId).collect(Collectors.toSet());


        // 获取 投标行关联的报价头
        Map<Long, OrderHead> orderHeaders = orderHeaderDao
                .findAll(Wrappers.lambdaQuery(OrderHead.class)
                        .in(OrderHead::getOrderHeadId, orderLines.stream().map(OrderLine::getOrderHeadId).collect(Collectors.toSet()))
                )
                .stream()
                .collect(Collectors.toMap(OrderHead::getOrderHeadId, x -> x));

        // 获取 投标行关联的寻源需求行
        List<BidRequirementLine> all = demandLineDao
                .findAll(Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, bidding.getBidingId())
                );
        if (all.isEmpty())
            return Collections.emptyList();
        Map<Long, BidRequirementLine> demandLines = all
                .stream()
                .collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, x -> x));
        Map<Long, String> factorValuesT = null;
        boolean seaFoodPrice = Objects.equals(all.get(0).getIsSeaFoodFormula(), YesOrNo.YES.getValue());
        if (seaFoodPrice) {
            factorValuesT = formulaPriceDetailMapper.selectList(Wrappers.lambdaQuery(BidOrderLineFormulaPriceDetail.class)
                    .in(BidOrderLineFormulaPriceDetail::getLineId, orderLineIds)
            ).stream().collect(Collectors.toMap(BidOrderLineFormulaPriceDetail::getLineId, BidOrderLineFormulaPriceDetail::getEssentialFactorValues));
        }
        Map<Long, String> factorValues = factorValuesT;
        // 获取 投标行关联的币种信息
        Map<String, BidBidingCurrency> currencies = biddingCurrencyDao
                .findAll(Wrappers.lambdaQuery(BidBidingCurrency.class)
                        .eq(BidBidingCurrency::getBidingId, bidding.getBidingId()))
                .stream()
                .collect(Collectors.toMap(BidBidingCurrency::getCurrencyCode, x -> x));

        // 获取 投标行关联的付款条件 - 按[投标行]分组
        Map<Long, List<OrderlinePaymentTerm>> paymentTermsGroups = orderLinePaymentTermDao
                .findAll(Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                        .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)
                )
                .stream()
                .collect(Collectors.groupingBy(OrderlinePaymentTerm::getOrderLineId));


        // 获取 关联投标供应商
        Map<Long, BidVendor> bidVendors = bidVendorDao
                .findAll(Wrappers.lambdaQuery(BidVendor.class)
                        .eq(BidVendor::getBidingId, bidding.getBidingId())
                )
                .stream()
                .collect(Collectors.toMap(BidVendor::getBidVendorId, x -> x));
        if (bidVendors.isEmpty())
            return Collections.emptyList();


        // 创建 评选对象
        return orderLines.stream()
                .filter(orderLine -> {
                    boolean isFilter = true;
                    if (StringUtils.isNotBlank(queryParam.getSelectionStatus()))
                        isFilter = orderLine.getSelectionStatus().equals(queryParam.getSelectionStatus());

                    if (StringUtils.isNotBlank(queryParam.getIsAllowNextRound()))
                        isFilter = isFilter && orderLine.getWin().equals(queryParam.getIsAllowNextRound());

                    if (queryParam.getRequirementLineId() != null)
                        isFilter = isFilter && orderLine.getRequirementLineId().equals(queryParam.getRequirementLineId());


                    // 获取 寻源需求行
                    BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(orderLine.getRequirementLineId()))
                            .orElseThrow(() -> new BaseException("获取寻源需求行失败。 | demandLineId: [" + orderLine.getRequirementLineId() + "]"));

                    if (StringUtils.isNotBlank(queryParam.getTargetNum()))
                        isFilter = isFilter && demandLine.getTargetNum() != null
                                && demandLine.getTargetNum().contains(queryParam.getTargetNum());

                    if (StringUtils.isNotBlank(queryParam.getTargetDesc()))
                        isFilter = isFilter && demandLine.getTargetDesc() != null
                                && demandLine.getTargetDesc().contains(queryParam.getTargetDesc());

                    if (StringUtils.isNotBlank(queryParam.getOrgName()))
                        isFilter = isFilter && demandLine.getOrgName() != null
                                && demandLine.getOrgName().contains(queryParam.getOrgName());

                    if (StringUtils.isNotBlank(queryParam.getItemGroup()))
                        isFilter = isFilter && demandLine.getItemGroup() != null
                                && demandLine.getItemGroup().contains(queryParam.getItemGroup());


                    // 获取 供应商
                    BidVendor bidVendor = Optional.ofNullable(bidVendors.get(orderLine.getBidVendorId()))
                            .orElseThrow(() -> new BaseException("获取供应商失败。 | bidVendorId: [" + orderLine.getBidVendorId() + "]"));

                    if (StringUtils.isNotBlank(queryParam.getVendorName()))
                        isFilter = isFilter && bidVendor.getVendorName() != null
                                && bidVendor.getVendorName().contains(queryParam.getVendorName());


                    return isFilter;
                })
                .map(orderLine -> {
                    if (seaFoodPrice) {
                        orderLine.setEssentialFactorValues(factorValues.get(orderLine.getOrderLineId()));
                    }
                    // 获取 供应商报价头
                    OrderHead orderHeader = Optional.ofNullable(orderHeaders.get(orderLine.getOrderHeadId()))
                            .orElseThrow(() -> new BaseException("获取供应商投标单失败。 | orderHeaderId: [" + orderLine.getOrderHeadId() + "]"));

                    // 获取 供应商
                    BidVendor bidVendor = Optional.ofNullable(bidVendors.get(orderHeader.getBidVendorId()))
                            .orElseThrow(() -> new BaseException("获取供应商失败。 | bidVendorId: [" + orderHeader.getBidVendorId() + "]"));

                    // 获取 寻源需求行
                    BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(orderLine.getRequirementLineId()))
                            .orElseThrow(() -> new BaseException("获取寻源需求行失败。 | demandLineId: [" + orderLine.getRequirementLineId() + "]"));

                    // 获取 币种信息
                    BidBidingCurrency biddingCurrency = Optional.ofNullable(currencies.get(orderLine.getCurrencyType()))
                            .orElseThrow(() -> new BaseException("获取币种信息失败。 | currency: [" + orderLine.getCurrencyType() + "]"));

                    // 获取 付款条件
                    List<OrderlinePaymentTerm> paymentTerms = paymentTermsGroups.getOrDefault(orderLine.getOrderLineId(), Collections.emptyList());


                    // 创建 评选对象
                    return EvaluationResult.create(
                            bidVendor, orderHeader, orderLine,
                            bidding, demandLine,
                            biddingCurrency, paymentTerms
                    );

                })
                .sorted((x, y) -> {
                    int compared = 0;
                    if (compared == 0) {
                        compared = Long.compare(Optional.ofNullable(x.getOrgId()).orElse(0L) + Optional.ofNullable(x.getOuId()).orElse(0L), Optional.ofNullable(y.getOrgId()).orElse(0L) + Optional.ofNullable(y.getOuId()).orElse(0L));
                    }
                    if (compared == 0 && x.getTargetNum() != null && y.getTargetNum() != null) {
                        compared = x.getTargetNum().compareTo(y.getTargetNum());
                    }
                    if (compared == 0 && x.getItemGroup() != null && y.getItemGroup() != null) {
                        compared = x.getItemGroup().compareTo(y.getItemGroup());
                    }
                    if (compared == 0 && x.getWin() != null && y.getWin() != null) {
                        compared = Integer.compare("DYNQ".indexOf(x.getWin()), "DYNQ".indexOf(y.getWin()));  // 待确定记录排前
                    }
                    if (compared == 0) {
                        int xl = 0;
                        int yl = 0;
                        if (!org.springframework.util.StringUtils.isEmpty(x.getSelectionStatus())) {
                            xl = x.getSelectionStatus().length();
                        }
                        if (!org.springframework.util.StringUtils.isEmpty(y.getSelectionStatus())) {
                            yl = y.getSelectionStatus().length();
                        }
                        compared = Integer.compare(xl, yl);
                    }
                    if (compared == 0 && x.getRank() != null && y.getRank() != null) {
                        compared = Integer.compare(x.getRank(),y.getRank());
                    }
                    if (compared == 0) {
                        compared = Long.compare(x.getOrderLineId(),y.getOrderLineId());
                    }
                    return compared;
                })
                //添加排名过滤
                .filter(e -> Objects.isNull(queryParam.getRank()) || Objects.isNull(e.getRank()) || Objects.equals(e.getRank(), queryParam.getRank()))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<EvaluationResult> queryEvaluationPage(EvaluationQueryParam queryParam) {
        return Optional.of(this.findEvaluationResults(queryParam))
                .map(results -> {
                    Integer pageNo = Optional.ofNullable(queryParam.getPageNum())
                            .orElse(1);
                    Integer pageSize = Optional.ofNullable(queryParam.getPageSize())
                            .orElse(10);
                    return PageUtil.pagingByFullData(pageNo, pageSize, results);
                })
                .get();
    }

    @Override
    public void intelligentEvaluation(Long biddingId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingDao.findById(biddingId))
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + biddingId + "]"));

//        if ("Y".equals(bidding.getFinalRound()))
//            throw new BaseException("招标单: [" + bidding.getBidingNum() + "] 已计算中标结果。");

        if (!BiddingProjectStatus.BUSINESS_EVALUATION.equals(BiddingProjectStatus.get(bidding.getBidingStatus())))
            throw new BaseException("招标单: [" + bidding.getBidingNum() + "] 非商务评选中。");


        // 获取 当前轮次信息
        Round round = Optional.ofNullable(biddingRoundDao.findOne(
                Wrappers.lambdaQuery(Round.class)
                        .eq(Round::getBidingId, bidding.getBidingId())
                        .eq(Round::getRound, bidding.getCurrentRound())
        )).orElseThrow(() -> new BaseException("获取当前轮次信息失败。 | biddingId: [" + bidding.getBidingId() + "], round: [" + bidding.getCurrentRound() + "]"));

        if ("Y".equals(round.getPublicResult()))
            throw new BaseException("招标单: [" + bidding.getBidingNum() + "] 已公式本轮结果。");


        // 获取 关联需求行
        Map<Long, BidRequirementLine> demandLines = demandLineDao
                .findAll(Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, bidding.getBidingId())
                )
                .stream()
                .collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, x -> x));

        // 获取 当前轮次的所有报价行
        List<OrderLine> orderLines = orderLineDao
                .findAll(
                        Wrappers.lambdaQuery(OrderLine.class)
                                .eq(OrderLine::getBidingId, bidding.getBidingId())
                                .eq(OrderLine::getRound, bidding.getCurrentRound())
                                .eq(OrderLine::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue())
                )
                .stream()
                .filter(e -> !Objects.equals(e.getWin(), "Q"))
                .peek(orderLine -> {

                    // 获取 需求行信息
                    BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(orderLine.getRequirementLineId()))
                            .orElseThrow(() -> new BaseException("获取需求行信息失败。 | demandLineId: [" + orderLine.getRequirementLineId() + "]"));

                    // 若是组合报价，设置组合、配比信息
                    if (StringUtils.isNotBlank(demandLine.getItemGroup())) {
                        orderLine.setItemGroup(demandLine.getItemGroup());
                        orderLine.setQuantity(Double.parseDouble(demandLine.getMaterialMatching()));
                    }

                })
                .collect(Collectors.toList());


        // 计算 各报价行得分
        calculateScoreService.calculateAndSet(bidding, orderLines);
        // 计算 得分排名
        calculateScoreService.rankAndSet(orderLines);
        // 更新报价行数据
        iOrderLineService.updateBatchById(orderLines);
    }

    @Override
    public void generatePriceApproval(Long biddingId) {
        priceReviewFormService.generateForm(biddingId);
    }


    /**
     * 计算本轮最低价，本轮最高价
     *
     * @param currentRoundOrderLineList
     */
    private void calculateMinAndMaxPrice(List<OrderLine> currentRoundOrderLineList) {
        Map<Long, List<OrderLine>> orderLineMap = currentRoundOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRequirementLineId));
        for (Long requirementLineId : orderLineMap.keySet()) {
            List<OrderLine> orderLineList = orderLineMap.get(requirementLineId);
            BigDecimal max = BigDecimal.valueOf(Integer.MIN_VALUE);
            BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);
            for (OrderLine orderLine : orderLineList) {
                BigDecimal currentPrice = orderLine.getPrice();
                max = max.max(currentPrice);
                min = min.min(currentPrice);
            }
            for (OrderLine orderLine : orderLineList) {
                orderLine.setCurrentRoundMinPrice(min);
                orderLine.setCurrentRoundMaxPrice(max);
            }
        }
    }

    /**
     * 淘汰供应商
     *
     * @param biding
     * @param submissionOrderLineList
     */
    private void eliminateVendor(Biding biding, List<OrderLine> submissionOrderLineList) {
        Map<Long, List<OrderLine>> orderLineMap = submissionOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRequirementLineId));
        List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(biding.getBidingId())));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        for (Long requirementLineId : orderLineMap.keySet()) {
            List<OrderLine> orderLineList = orderLineMap.get(requirementLineId);
            // 排名升序
            Collections.sort(orderLineList, new Comparator<OrderLine>() {
                @Override
                public int compare(OrderLine o1, OrderLine o2) {
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
            Map<Integer, List<OrderLine>> rankMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRank));
           /* // 商务第N轮保留供应商数量
            Integer limitRoundCount = 0;
            if (biding.getCurrentRound() >= 1 && biding.getCurrentRound() <= 5) {
                limitRoundCount = (Integer) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(BidRequirementLine.class, "getLimitRound" + biding.getCurrentRound()), requirementLineMap.get(requirementLineId));
            }*/
            List<Integer> rankList = new ArrayList<Integer>(rankMap.keySet());
            Collections.sort(rankList);
            for (Integer rank : rankList) {
                List<OrderLine> tmpOrderLineList = rankMap.get(rank);
                tmpOrderLineList.forEach(l -> l.setWin("D"));// 临界点，待确定
               /* if (limitRoundCount >= tmpOrderLineList.size()) {
                    limitRoundCount = limitRoundCount - tmpOrderLineList.size();
                    tmpOrderLineList.forEach(l -> l.setWin("Y"));// 入围
                } else if (limitRoundCount <= 0) {
                    tmpOrderLineList.forEach(l -> l.setWin("N"));// 淘汰
                } else if (limitRoundCount < tmpOrderLineList.size()) {
                    limitRoundCount = limitRoundCount - tmpOrderLineList.size();

                }*/
            }
        }
    }

    /**
     * 入围/淘汰校验
     *
     * @param orderLineList
     */
    private List<OrderLine> enterNextRoundAndEliminateCheck(List<OrderLine> orderLineList) {
        if (orderLineList.size() == 0) {
            return orderLineList;
        }
        Biding biding = iBidingService.getById(orderLineList.get(0).getBidingId());
        Assert.notNull(biding, "招投标ID不存在");
        Assert.isTrue(BiddingProjectStatus.BUSINESS_EVALUATION == BiddingProjectStatus.get(biding.getBidingStatus()), "非商务评选中");
        for (OrderLine orderLine : orderLineList) {
            Assert.isTrue(orderLine.getRound().compareTo(biding.getCurrentRound()) == 0, "只能淘汰/入围最新一轮的投标数据");
        }
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(biding.getBidingId()).setRound(biding.getCurrentRound())));
        Assert.isTrue(!"Y".equals(round.getPublicResult()), "本轮结果已公示");
        // 组合决标，自动按组合入围/淘汰
//        if (BidingAwardWayEnum.get(biding.getBidingAwardWay()) == BidingAwardWayEnum.COMBINED_DECISION) {
//            List<OrderLine> result = new ArrayList<OrderLine>();
//            List<OrderLine> currentRoundOrderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(biding.getBidingId()).setRound(biding.getCurrentRound())));
//            List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(biding.getBidingId())));
//            Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
//            Set<String> containSet = new HashSet<String>();
//            orderLineList.forEach(l -> containSet.add(l.getOrderHeadId() + "-" + requirementLineMap.get(l.getRequirementLineId()).getItemGroup()));
//            for (OrderLine orderLine : currentRoundOrderLineList) {
//                if (containSet.contains(orderLine.getOrderHeadId() + "-" + requirementLineMap.get(orderLine.getRequirementLineId()).getItemGroup())) {
//                    result.add(orderLine);
//                }
//            }
//            return result;
//        }
        return orderLineList;
    }

    @Override
    public void enterNextRound(List<Long> orderLineIdList) {
        List<OrderLine> orderLineList = iOrderLineService.listByIds(orderLineIdList);
        List<OrderLine> newOrderLineList = enterNextRoundAndEliminateCheck(orderLineList);
        newOrderLineList.forEach(l -> l.setWin("Y"));
        iOrderLineService.updateBatchById(orderLineList);
    }

    @Override
    public void eliminate(List<Long> orderLineIdList) {
        List<OrderLine> orderLineList = iOrderLineService.listByIds(orderLineIdList);
        List<OrderLine> newOrderLineList = enterNextRoundAndEliminateCheck(orderLineList);
        newOrderLineList.forEach(l -> l.setWin("N"));
        iOrderLineService.updateBatchById(newOrderLineList);
    }

    @Override
    public void publicityOfResult(Long bidingId) {
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Assert.isTrue(BiddingProjectStatus.BUSINESS_EVALUATION == BiddingProjectStatus.get(biding.getBidingStatus()), "非商务评选中");
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(bidingId).setRound(biding.getCurrentRound()).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
        for (OrderLine orderLine : orderLineList) {
            Assert.isTrue(("Y".equals(orderLine.getWin()) || "N".equals(orderLine.getWin()) || "Q".equals(orderLine.getWin())), "存在待确定是否入围记录");
        }
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(biding.getCurrentRound())));
        Assert.isTrue(!"Y".equals(round.getPublicResult()), "本轮结果已公示，无需重复执行");
        round.setPublicResult("Y");
        round.setPublicResultTime(new Date());
        iRoundService.updateById(round);
    }

    @Override
    public void calculateWinBidResult(Long bidingId) {
        /*Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Round currentRound = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(biding.getCurrentRound())));
        Assert.isTrue("Y".equals(currentRound.getBusinessOpenBid()), "本轮商务未开标，不能计算中标结果");
        Assert.isTrue(!"Y".equals(biding.getFinalRound()), "已计算中标结果，无需重复执行");
        int rankCount = iOrderLineService.count(
                Wrappers.lambdaQuery(OrderLine.class)
                        .eq(OrderLine::getBidingId, biding)
                        .eq(OrderLine::getRound, biding.getCurrentRound())
                        .isNotNull(OrderLine::getRank));
        Assert.isTrue(rankCount > 0, "请先进行智能评选");
         ///
        *//*  Round followRound = iRoundService.getOne(
                Wrappers.lambdaQuery(Round.class)
                        .eq(Round::getBidingId,biding)
                        .eq(Round::getRound,BidConstant.FOLLOW_ROUND));
        Assert.isNull(followRound, "已发起跟标确认，不能计算中标结果");*//*
        biding.setFinalRound("Y");
        iBidingService.updateById(biding);
        List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(
                Wrappers.lambdaQuery(BidRequirementLine.class).eq(BidRequirementLine::getBidingId,biding));
         /// 暂时不需要拦标价
        *//* for (BidRequirementLine line : requirementLineList) {
            Assert.notNull(line.getTargetPrice(), "存在招标需求没有维护拦标价");
        }*//*
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        // 当前轮（最后一轮）供应商投标记录
        List<OrderLine> orderLineList = iOrderLineService.list(Wrappers.lambdaQuery(OrderLine.class)
                .eq(OrderLine::getBidingId, biding)
                .eq(OrderLine::getRound, biding.getCurrentRound()));
        //收集已投标的行
        List<OrderLine> submissionOrderLines = new ArrayList<>(orderLineList.size());
        for (OrderLine orderLine : orderLineList) {
            String orderStatus = orderLine.getOrderStatus();
            // 将投标状态为作废的记录，评选状态设置成作废
            if (orderStatus.equals(OrderStatusEnum.INVALID.getValue())) {
                orderLine.setSelectionStatus(SelectionStatusEnum.INVALID.getValue());
            }
            if (orderStatus.equals(OrderStatusEnum.SUBMISSION.getValue())) {
                submissionOrderLines.add(orderLine);
            }
        }
        EvaluateMethodEnum evaluateMethodEnum = EvaluateMethodEnum.get(biding.getEvaluateMethod());
        if (BidingAwardWayEnum.get(biding.getBidingAwardWay()) == BidingAwardWayEnum.INDIVIDUAL_DECISION) {
            // 单项决标
            Map<Long, List<OrderLine>> orderLineListMap = submissionOrderLines.stream().collect(Collectors.groupingBy(OrderLine::getRequirementLineId));// 将供应商报价，按招标需求行ID分组
            for (Long requirementLineId : orderLineListMap.keySet()) {
                List<OrderLine> firstRankOrderLineList = orderLineListMap.get(requirementLineId).stream().filter(l -> l.getRank() == 1).collect(Collectors.toList());// 排名第一的记录
                // 第一名，并且按情况符合拦标价约束的，则中标
                List<Long> winBidVendorIdList = new ArrayList<Long>();
                for (OrderLine orderLine : firstRankOrderLineList) {
//                    BigDecimal targetPrice = requirementLineMap.get(requirementLineId).getTargetPrice();
                    if (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE *//*&& orderLine.getPrice().compareTo(targetPrice) >= 0*//*) {
                        // 合理高价法，高于或等于拦标价
                        winBidVendorIdList.add(orderLine.getBidVendorId());
                    } else if ((evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE || evaluateMethodEnum == EvaluateMethodEnum.COMPOSITE_SCORE) *//*&& orderLine.getPrice().compareTo(targetPrice) <= 0*//*) {
                        // 合理低价法，排名第一，低于或等于拦标价 OR 综合评标法，低于或等于拦标价
                        winBidVendorIdList.add(orderLine.getBidVendorId());
                    }
                }
                if (winBidVendorIdList.size() == 0) {
                    orderLineListMap.get(requirementLineId).forEach(l -> l.setSelectionStatus(SelectionStatusEnum.ABORT.getValue()));
                } else {
                    orderLineListMap.get(requirementLineId).forEach(l -> l.setSelectionStatus(winBidVendorIdList.contains(l.getBidVendorId()) ? SelectionStatusEnum.WIN.getValue() : SelectionStatusEnum.WAIT.getValue()));
                }
            }
        } else if (BidingAwardWayEnum.get(biding.getBidingAwardWay()) == BidingAwardWayEnum.COMBINED_DECISION) {
            // 组合决标
            Map<String, DoubleSummaryStatistics> targetAmountSummary = requirementLineList.stream().collect(Collectors.groupingBy(BidRequirementLine::getItemGroup, Collectors.summarizingDouble(BidRequirementLine::getTargetAmount)));// 招标需求按物料组分组,将预计采购量*拦标价做聚合
            submissionOrderLineList.forEach(l -> l.setItemGroup(requirementLineMap.get(l.getRequirementLineId()).getItemGroup()));
            Map<String, List<OrderLine>> orderLineListMap = submissionOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getItemGroup));// 将供应商报价，按物料组分组
            for (String itemGroup : orderLineListMap.keySet()) {
                Map<Long, List<OrderLine>> vendorOrderLineListMap = orderLineListMap.get(itemGroup).stream().filter(l -> l.getRank() == 1).collect(Collectors.groupingBy(OrderLine::getBidVendorId));// 排名第一的记录,按供应商分组
                List<Long> winBidVendorIdList = new ArrayList<Long>();
                for (Long bidVendorId : vendorOrderLineListMap.keySet()) {
                    BigDecimal targetAmount = new BigDecimal(targetAmountSummary.get(itemGroup).getSum());
                    BigDecimal totalAmount = vendorOrderLineListMap.get(bidVendorId).get(0).getTotalAmount();
                    if (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE *//*&& totalAmount.compareTo(targetAmount) >= 0*//*) {
                        // 合理高价法，高于或等于拦标价总金额
                        winBidVendorIdList.add(bidVendorId);
                    } else if ((evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE || evaluateMethodEnum == EvaluateMethodEnum.COMPOSITE_SCORE) *//*&& totalAmount.compareTo(targetAmount) <= 0*//*) {
                        // 合理低价法，排名第一，低于或等于拦标价总金额 OR 综合评标法，低于或等于拦标价总金额
                        winBidVendorIdList.add(bidVendorId);
                    }
                }
                if (winBidVendorIdList.size() == 0) {
                    orderLineListMap.get(itemGroup).forEach(l -> l.setSelectionStatus(SelectionStatusEnum.ABORT.getValue()));
                } else {
                    orderLineListMap.get(itemGroup).forEach(l -> l.setSelectionStatus(winBidVendorIdList.contains(l.getBidVendorId()) ? SelectionStatusEnum.WIN.getValue() : SelectionStatusEnum.WAIT.getValue()));
                }
            }
        }
        iOrderLineService.updateBatchById(orderLineList);*/
    }

    @Override
    public void createFollowTender(CreateFollowTenderParam param) {
        Biding biding = iBidingService.getById(param.getBidingId());
        Assert.notNull(biding, "招投标ID不存在");
        Assert.isTrue(!"Y".equals(biding.getEndEvaluation()), "已结束评选，不能发起跟标");
        Assert.notNull(param.getBidingId(), "招投标ID不能为空");
        Assert.notNull(param.getStartTime(), "开始时间不能为空");
        Assert.notNull(param.getEndTime(), "结束时间不能为空");
        Assert.isTrue(param.getStartTime().before(param.getEndTime()), "开始时间不能晚于结束时间");
        Assert.isTrue(param.getStartTime().after(new Date()), "开始时间不能晚于当前时间");
        Round followRound = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(param.getBidingId()).setRound(BidConstant.FOLLOW_ROUND)));
        Assert.isNull(followRound, "已发起过跟标确认，不能重复发起");
        // 将所有评选结果为：待跟标 ，标记为拥有跟标权限
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(param.getBidingId()).setSelectionStatus(SelectionStatusEnum.WAIT.getValue())));
        orderLineList.forEach(l -> l.setWithStanardPermission("Y"));
        iOrderLineService.updateBatchById(orderLineList);
        iRoundService.save(new Round().setRoundId(IdGenrator.generate()).setStartTime(param.getStartTime()).setEndTime(param.getEndTime()).setRound(BidConstant.FOLLOW_ROUND).setBidingId(param.getBidingId()));
    }

    @Override
    public void endFollowBid(Long bidingId) {
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Round followRound = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(BidConstant.FOLLOW_ROUND)));
        if (followRound != null) {
            List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(bidingId).setRound(biding.getCurrentRound()).setWithStanardPermission("Y")));
            // 确认跟标，将评选结果改为跟标，反之改为落标
            orderLineList.forEach(orderLine -> orderLine.setSelectionStatus("Y".equals(orderLine.getWithStandard()) ? SelectionStatusEnum.FOLLOW.getValue() : SelectionStatusEnum.FAIL.getValue()));
            iOrderLineService.updateBatchById(orderLineList);
        }
    }

    @Override
    public void endEvaluation(Long bidingId) {

        // 更新 招标单结束标识
        iBidingService.update(Wrappers.lambdaUpdate(Biding.class)
                .set(Biding::getEndEvaluation, YesOrNo.YES.getValue())
                .eq(Biding::getBidingId, bidingId));

        // 生成价格审批单
        this.generatePriceApproval(bidingId);

       /* iBidingService.saveOrUpdate(biding);
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(bidingId).setRound(biding.getCurrentRound()).setSelectionStatus(SelectionStatusEnum.WAIT.getValue())));
        orderLineList.forEach(orderLine -> orderLine.setSelectionStatus(SelectionStatusEnum.FAIL.getValue()));
        iOrderLineService.updateBatchById(orderLineList);*/
    }

    @Override
    public String getRoundEndTime(Long bidingId, Integer round) {
        Round roundDTO = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(round)));
        return roundDTO == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(roundDTO.getEndTime());
    }

    /**
     * 打印PDF数据头
     *
     * @author Fanzz
     */
    @Data
    private class PrintPDFLineDto {
        private String no;// 序号
        private String targetNum;// 物料编码
        private String targetDesc;// 物料名称
        private String uomDesc;// 单位
        private String amount;// 预计采购金额（万元）
        private List<String> priceList;// 轮次报价数据
        private String winBidPrice;// 含税中标价
        private String withStandard;// 是否跟标
    }

    /**
     * 打印PDF数据行表
     *
     * @author Fanzz
     */
    @Data
    private class PrintPDFHeaderDto {
        private String vendorCode;// 供应商编码
        private String vendorName;// 供应商名称
        private String pageInfo;// 页码
        private List<PrintPDFLineDto> lineList = new ArrayList<PrintPDFLineDto>();
    }

    private List<PrintPDFHeaderDto> buildPrintPDFHeaderList(Biding biding) {
        List<PrintPDFHeaderDto> result = new ArrayList<PrintPDFHeaderDto>();
        List<BidVendor> vendorList = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(biding.getBidingId())));
        if (vendorList.size() == 0) {
            return result;
        }
        Map<Long, BidVendor> vendorMap = vendorList.stream().collect(Collectors.toMap(BidVendor::getBidVendorId, Function.identity()));
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(biding.getBidingId()).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
        if (orderLineList.size() == 0) {
            return result;
        }
        List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(biding.getBidingId())));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        Map<Long, Map<Long, Map<Integer, OrderLine>>> orderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getBidVendorId, Collectors.groupingBy(OrderLine::getRequirementLineId, Collectors.toMap(OrderLine::getRound, Function.identity(), (k1, k2) -> k1))));
        // 过滤中标记录，按价格升序，再按招标需求ID分组，取最先匹配到的数据
        Map<Long, OrderLine> winBidMap = orderLineList.stream().filter(l -> SelectionStatusEnum.get(l.getSelectionStatus()) == SelectionStatusEnum.WIN).sorted((l1, l2) -> l1.getPrice().compareTo(l2.getPrice())).collect(Collectors.toMap(OrderLine::getRequirementLineId, Function.identity(), (k1, k2) -> k1));
        for (Long bidVendorId : orderLineMap.keySet()) {
            BidVendor vendor = vendorMap.get(bidVendorId);
            Assert.notNull(vendor, "投标单上的供应商ID不存在");
            // 构造打印PDF对象
            PrintPDFHeaderDto headerDto = new PrintPDFHeaderDto();
            headerDto.setVendorCode(vendor.getVendorCode());
            headerDto.setVendorName(vendor.getVendorName());
            for (Long requirementLineId : orderLineMap.get(bidVendorId).keySet()) {
                List<String> priceList = new ArrayList<String>();
                for (int round = BidConstant.FIRST_ROUND; round <= biding.getCurrentRound(); round++) {
                    OrderLine orderLine = orderLineMap.get(bidVendorId).get(requirementLineId).get(round);
//                    priceList.add(orderLine == null ? "-" : orderLine.getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                }
                OrderLine lastRoundOrderLine = orderLineMap.get(bidVendorId).get(requirementLineId).get(biding.getCurrentRound());
                String withStandard = "-";
                if (lastRoundOrderLine != null) {
                    if (SelectionStatusEnum.get(lastRoundOrderLine.getSelectionStatus()) == SelectionStatusEnum.WIN) {
                        withStandard = "中标";
                    } else if (SelectionStatusEnum.get(lastRoundOrderLine.getSelectionStatus()) == SelectionStatusEnum.FOLLOW) {
                        withStandard = "跟标";
                    }
                }
                BidRequirementLine line = requirementLineMap.get(requirementLineId);
                Assert.notNull(line, "投标单上的招标需求行ID不存在");
                PrintPDFLineDto lineDto = new PrintPDFLineDto();
                lineDto.setTargetNum(line.getTargetNum());
                lineDto.setTargetDesc(line.getTargetDesc());
                lineDto.setUomDesc(line.getUomDesc());
                lineDto.setAmount(line.getAmount() != null ? line.getAmount().setScale(2, RoundingMode.HALF_DOWN).toPlainString() : "");
                OrderLine winBidOrderLine = winBidMap.get(requirementLineId);
//                lineDto.setWinBidPrice(winBidOrderLine == null ? "-" : winBidOrderLine.getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                lineDto.setWithStandard(withStandard);
                lineDto.setPriceList(priceList);
                headerDto.getLineList().add(lineDto);
            }
            if (headerDto.getLineList().size() > 0) {
                result.add(headerDto);
            }
        }
        return result;
    }

    private List<PrintPDFHeaderDto> pagingPrintPDFHeader(List<PrintPDFHeaderDto> list) {
        int pageSize = 15;
        List<PrintPDFHeaderDto> printHeaderPageList = new ArrayList<PrintPDFHeaderDto>();
        for (PrintPDFHeaderDto dto : list) {
            // 总页数
            int totalPage = (dto.getLineList().size() / pageSize) + 1;
            for (int i = 1; i <= totalPage; i++) {
                PrintPDFHeaderDto pageDto = new PrintPDFHeaderDto();
                pageDto.setVendorCode(dto.getVendorCode());
                pageDto.setVendorName(dto.getVendorName());
                pageDto.setPageInfo(i + "/" + totalPage);
                for (int j = ((i - 1) * pageSize); j < i * pageSize; j++) {
                    if (j < dto.getLineList().size()) {
                        pageDto.getLineList().add(dto.getLineList().get(j));
                    }
                }
                printHeaderPageList.add(pageDto);
            }
        }
        for (PrintPDFHeaderDto dto : printHeaderPageList) {
            int no = 1;
            for (PrintPDFLineDto line : dto.getLineList()) {
                line.setNo(String.valueOf(no++));
            }
        }
        return printHeaderPageList;
    }

    @Override
    public Map<String, Object> printPDF(Long bidingId) throws Exception {
        Assert.notNull(bidingId, "招投标ID不能为空");
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Assert.notNull(biding.getCurrentRound(), "未发起投标");
        // 构造打印数据
        List<PrintPDFHeaderDto> printHeaderList = buildPrintPDFHeaderList(biding);
        // 每15条数据拆分
        List<PrintPDFHeaderDto> printHeaderPageList = pagingPrintPDFHeader(printHeaderList);
        File file = new File(UUID.randomUUID().toString() + ".pdf");
        Rectangle r = new Rectangle(297 * 3, 210 * 3);
        Document document = new Document(r);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // 字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 9, Font.NORMAL);
        Font nameFontChinese = new Font(bfChinese, 8, Font.NORMAL);
        document.open();
        if (biding.getCurrentRound() > 0) {
            int numColumns = 7 + biding.getCurrentRound(); // 固定8列+动态报价轮次列数
            String[] numChinese = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
            for (PrintPDFHeaderDto dto : printHeaderPageList) {
                Paragraph pageInfoPara = new Paragraph(dto.pageInfo, fontChinese);
                pageInfoPara.setAlignment(2);
                document.add(pageInfoPara);
                document.add(new Paragraph("供应商：      " + dto.vendorCode + "," + dto.vendorName, fontChinese));
                document.add(new Paragraph("   ", fontChinese));
                PdfPTable table = new PdfPTable(numColumns);
                table.setWidthPercentage(100f);
                float[] widths = new float[numColumns];
                widths[0] = 12f;
                widths[1] = 40f;
                widths[2] = 180f;
                widths[3] = 20f;
                widths[4] = 30f;
                for (int i = 5; i < 5 + biding.getCurrentRound(); i++) {
                    widths[i] = 25f;
                }
                widths[numColumns - 2] = 26f;
                widths[numColumns - 1] = 26f;
                table.setWidths(widths);
                PdfPCell[] headerCells = new PdfPCell[numColumns];
                headerCells[0] = new PdfPCell(new Paragraph("序号", fontChinese));
                headerCells[1] = new PdfPCell(new Paragraph("物料编码", fontChinese));
                headerCells[2] = new PdfPCell(new Paragraph("物料名称", fontChinese));
                headerCells[3] = new PdfPCell(new Paragraph("单位", fontChinese));
                headerCells[4] = new PdfPCell(new Paragraph("预计采购金额（万元）", fontChinese));
                for (int i = 5; i < 5 + biding.getCurrentRound(); i++) {
                    headerCells[i] = new PdfPCell(new Paragraph("第" + numChinese[i - 4] + "轮            含税报价", fontChinese));
                }
                headerCells[numColumns - 2] = new PdfPCell(new Paragraph("含税中标价", fontChinese));
                headerCells[numColumns - 1] = new PdfPCell(new Paragraph("是否跟标", fontChinese));
                List<PdfPRow> listRow = table.getRows();
                PdfPRow headerRow = new PdfPRow(headerCells);
                listRow.add(headerRow);
                for (PrintPDFLineDto lineDto : dto.getLineList()) {
                    PdfPCell[] dataCells = new PdfPCell[numColumns];
                    dataCells[0] = new PdfPCell(new Paragraph(lineDto.getNo(), fontChinese));
                    dataCells[1] = new PdfPCell(new Paragraph(lineDto.getTargetNum(), fontChinese));
                    dataCells[2] = new PdfPCell(new Paragraph(lineDto.getTargetDesc(), nameFontChinese));
                    dataCells[3] = new PdfPCell(new Paragraph(lineDto.getUomDesc(), fontChinese));
                    dataCells[4] = new PdfPCell(new Paragraph(lineDto.getAmount(), fontChinese));
                    for (int i = 5; i < 5 + biding.getCurrentRound(); i++) {
                        dataCells[i] = new PdfPCell(new Paragraph(lineDto.getPriceList().get(i - 5), fontChinese));
                    }
                    dataCells[numColumns - 2] = new PdfPCell(new Paragraph(lineDto.getWinBidPrice(), fontChinese));
                    dataCells[numColumns - 1] = new PdfPCell(new Paragraph(lineDto.getWithStandard(), fontChinese));
                    PdfPRow dataRow = new PdfPRow(dataCells);
                    listRow.add(dataRow);
                }
                Paragraph sign1 = new Paragraph("参标代表签字：                                                                                          ", fontChinese);
                sign1.setAlignment(2);
                Paragraph sign2 = new Paragraph("公章：                                                                                            ", fontChinese);
                sign2.setAlignment(2);
                document.add(table);
                document.add(new Paragraph("                                                                                         "));
                document.add(sign1);
                document.add(sign2);
                document.newPage();
            }
        }
        if (printHeaderPageList.size() == 0) {
            document.add(new Paragraph("没有数据！", fontChinese));
        }
        document.close();
        writer.close();
        byte[] buffer = FileUtils.readFileToByteArray(file);
        file.delete();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buffer", buffer);
        result.put("fileName", biding.getBidingNum() + ".pdf");
        return result;
    }

    @Override
    public Map<String, Object> exportExcel(EvaluationQueryParam queryParam) throws Exception {
        List<EvaluationResult> dataList = this.findEvaluationResults(queryParam);
        Biding biding = iBidingService.getById(queryParam.getBidingId());
        List<CustomTableVO> customTableList = baseClient.queryCustomTableList(queryParam.getBidingId(), "EVALUATION_TABLE");
        String fileName = UUID.randomUUID().toString() + ".xls";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0, "评选列表").build();
        sheet1.setHead(head(customTableList));
        excelWriter.write(data(customTableList, dataList), sheet1);
        excelWriter.finish();
        File file = new File(fileName);
        byte[] buffer = FileUtils.readFileToByteArray(file);
        file.delete();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buffer", buffer);
        result.put("fileName", biding.getBidingNum() + ".xls");
        return result;
    }

    /**
     * 返回折息价格
     *
     * @param taxPrice        含税价
     * @param type            币种类型
     * @param isHonour        是否承兑
     * @param currentDay      供应商报价账期
     * @param monthlyInterest 月利率
     * @param taxRate         税率
     * @return
     */
    @Override
    public BigDecimal calculateDiscountPrice(BigDecimal taxPrice, String type, Boolean isHonour, BigDecimal currentDay, BigDecimal monthlyInterest, BigDecimal taxRate, BigDecimal rate) {
        //汇率转换
        taxPrice = taxPrice.multiply(rate);
        //转换成未税价,未含税*（1+税率）=含税
        BigDecimal noTaxPrice = taxPrice.divide(BigDecimal.ONE.add(taxRate), 8, BigDecimal.ROUND_HALF_DOWN);
        //没有账期，无需折息，返回汇率转换完剔除税额
        if (Objects.isNull(currentDay) || currentDay.equals(BigDecimal.ZERO)) {
            //返回不含税
            return noTaxPrice.multiply(BigDecimal.ONE.subtract(taxRate));
        } else {
            //返回含税
            BigDecimal month = currentDay.divide(BigDecimal.valueOf(30), 0, BigDecimal.ROUND_HALF_UP);
            BigDecimal N = isHonour ? month.add(BigDecimal.valueOf(6)) : month;
            return noTaxPrice.divide(BigDecimal.ONE.add(N.multiply(monthlyInterest)), 8, BigDecimal.ROUND_HALF_UP);
        }

    }

    @Override
    public BigDecimal calculateDiscountPrice(OrderLine orderLine, BigDecimal monthlyInterest, boolean high, List<OrderlinePaymentTerm> terms) {
        Boolean isHonour = orderLine.getPaymentWay().equals(PaymentProvisionType.LONGI_BILL.getCode()) ||
                orderLine.getPaymentWay().equals(PaymentProvisionType.LONGI_BILL01.getCode());
        Long bidingId = orderLine.getBidingId();
        if (Objects.isNull(bidingId)) {
            throw new BidException("需求行的bidingId不能为空");
        }
        String currencyType = orderLine.getCurrencyType();
        BidBidingCurrency currency = biddingCurrencyDao.findOne(Wrappers.lambdaQuery(BidBidingCurrency.class)
                .select(BidBidingCurrency::getPriceTax)
                .eq(BidBidingCurrency::getCurrencyCode, currencyType)
                .eq(BidBidingCurrency::getBidingId, bidingId).last("limit 1")
        );
        Integer day = null;
        if (terms != null && terms.size() > 0) {
            day = terms.get(0).getPaymentDay();
            if (terms.size() > 1) {
                for (int i = 1; i < terms.size(); i++) {
                    if (!terms.get(i).getPaymentDay().equals(day)) {
                        day = null;
                        break;
                    }
                }
            }
        }
        if (high) {
            day = null;
        }
        return calculateDiscountPrice(
                orderLine.getPrice()
                , currencyType
                , isHonour
                , Objects.isNull(day) ? null : BigDecimal.valueOf(day)
                , monthlyInterest
                , orderLine.getTaxRate().multiply(BigDecimal.valueOf(0.01))
                , currency.getPriceTax());
    }

    @Override
    public void changeOrderLineStatus(List<Long> orderLineIds, Boolean fail) {
        iOrderLineService.update(Wrappers.lambdaUpdate(OrderLine.class)
                .set(OrderLine::getSelectionStatus,
                        fail ? SelectionStatusEnum.FAIL.getValue() : SelectionStatusEnum.WIN.getValue())
                .in(OrderLine::getOrderLineId, orderLineIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<EvaluationResult> calculateQuotaResult(Long bidingId) {

        Biding bidding = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getBidingId, Biding::getCurrentRound
//                        , Biding::getQuotaDistributeType
                )
                .eq(Biding::getBidingId, bidingId));
//        String quotaDistributeType = bidding.getQuotaDistributeType();
//        if (StringUtil.isEmpty(quotaDistributeType)) {
//            throw new BaseException("配额类型不存在，请确认项目信息是否维护");
//        }
//        if (Objects.equals(QuotaDistributeType.NULL_ALLOCATION.getCode(), quotaDistributeType)) {
//            throw new BaseException("配额类型为空，无法计算配额");
//        }
        //计算配额
        intelligentEvaluation(bidingId);
        List<EvaluationResult> winningResults = findEvaluationResults(EvaluationQueryParam.builder()
                .bidingId(bidding.getBidingId())
                .round(bidding.getCurrentRound())
                .build());
        boolean hasTriggerEvaluation = winningResults.stream().anyMatch(e -> Objects.isNull(e.getDiscountPrice()));
        if (!hasTriggerEvaluation) {
            //触发评选
            intelligentEvaluation(bidingId);
            winningResults = findEvaluationResults(EvaluationQueryParam.builder()
                    .bidingId(bidding.getBidingId())
                    .round(bidding.getCurrentRound())
                    .build());
        }
        List<Long> collect = winningResults.stream().map(EvaluationResult::getOrderLineId).collect(Collectors.toList());
        orderLineMapper.update(null, Wrappers.lambdaUpdate(OrderLine.class)
                .set(OrderLine::getQuotaQuantity, 0)
                .set(OrderLine::getQuotaRatio, 0)
                .in(OrderLine::getOrderLineId, collect));
        for (int i = winningResults.size() - 1; i >= 0; i--) {
            EvaluationResult evaluationResult = winningResults.get(i);
            if (!Objects.equals(evaluationResult.getSelectionStatus(), "WIN")) {
                winningResults.remove(i);
            }
        }
        if (CollectionUtils.isEmpty(winningResults)) {
            return null;
        }
        String buCode = getBuCode(winningResults.get(0));
//        if (Objects.equals(QuotaDistributeType.NUMBER_ALLOCATION.getCode(), quotaDistributeType)) {
//            //按需求行分组
//            Map<Long, List<EvaluationResult>> map = winningResults.stream().collect(Collectors.groupingBy(EvaluationResult::getRequirementLineId));
//            //对分组的行进行操作
//            List<QuotaCalculateParam> quotaCalculateParams = new LinkedList<>();
//            for (Map.Entry<Long, List<EvaluationResult>> requireMap : map.entrySet()) {
//                List<EvaluationResult> value = requireMap.getValue();
//                if (CollectionUtils.isNotEmpty(value)) {
//                    Long categoryId = null;
//                    BigDecimal minPrice = BigDecimal.valueOf(Integer.MAX_VALUE);
//                    for (EvaluationResult evaluationResult : value) {
//                        minPrice = minPrice.min(evaluationResult.getDiscountPrice());
//                    }
//                    WinVendorInfoDto minInfo = null;
//                    Long requirementId = requireMap.getKey();
//                    String categoryName = null;
//                    List<WinVendorInfoDto> info = new LinkedList<>();
//                    for (EvaluationResult e : value) {
//                        WinVendorInfoDto winVendorInfoDto = WinVendorInfoDto.builder()
//                                .companyId(e.getVendorId())
//                                .companyName(e.getVendorName())
//                                .orderLineId(e.getOrderLineId())
//                                .needCount(e.getQuantity())
//                                .discountPrice(e.getDiscountPrice()).build();
//                        if (categoryId == null && e.getCategoryId() != null) {
//                            categoryId = e.getCategoryId();
//                        }
//                        if (Objects.isNull(categoryName) && Objects.nonNull(e.getCategoryName())) {
//                            categoryName = e.getCategoryName();
//                        }
//                        if (Objects.equals(e.getDiscountPrice(), minPrice) && Objects.isNull(minInfo)) {
//                            minInfo = WinVendorInfoDto.builder()
//                                    .companyId(e.getVendorId())
//                                    .companyName(e.getVendorName())
//                                    .discountPrice(e.getDiscountPrice()).build();
//                        }
//                        info.add(winVendorInfoDto);
//                    }
//                    QuotaCalculateParam dto = QuotaCalculateParam.builder()
//                            .winVendorInfoDtoList(info)
//                            .minDiscountVendorInfo(minInfo)
//                            .categoryId(categoryId)
//                            .requirementId(requirementId)
//                            .calculateType(QuotaDistributeType.NUMBER_ALLOCATION.getCode())
//                            .buCode(buCode)
//                            .categoryName(categoryName)
//                            .build();
//                    quotaCalculateParams.add(dto);
//                }
//            }
//            if (CollectionUtils.isNotEmpty(quotaCalculateParams)) {
//                List<QuotaCalculateParam> calculate = inqClient.calculate(quotaCalculateParams);
//                //结果
//                List<OrderLine> orderLines = new LinkedList<>();
//                for (QuotaCalculateParam quotaCalculateParam : calculate) {
//                    for (WinVendorInfoDto winVendorInfoDto : quotaCalculateParam.getWinVendorInfoDtoList()) {
//                        BigDecimal quotaRatio = winVendorInfoDto.getQuota();
//                        BigDecimal num = BigDecimal.valueOf(winVendorInfoDto.getNeedCount()).multiply(quotaRatio.multiply(BigDecimal.valueOf(0.01)));
//                        //更新需求行的配额
//                        OrderLine orderLine = new OrderLine();
//                        orderLine.setOrderLineId(winVendorInfoDto.getOrderLineId())
//                                .setQuotaRatio(quotaRatio)
//                                .setQuotaQuantity(num);
//                        orderLines.add(orderLine);
//                    }
//                }
//                orderLineService.updateBatchById(orderLines);
//            }
//
//        }
//        if (Objects.equals(quotaDistributeType, QuotaDistributeType.CATEGORY_ALLOCATION.getCode())) {
//            Map<Long, List<EvaluationResult>> categoryAndVendorInfo = winningResults.stream()
//                    .collect(Collectors.groupingBy(EvaluationResult::getCategoryId));
//            List<QuotaCalculateParam> quotaCalculateParams = new LinkedList<>();
//            for (Map.Entry<Long, List<EvaluationResult>> cInfo : categoryAndVendorInfo.entrySet()) {
//                Long categoryId = cInfo.getKey();
//                String categoryName = cInfo.getValue().get(0).getCategoryName();
//                //按供应商分组，算每个品类的最低折息价
//                Map<Long, List<EvaluationResult>> veInfos = cInfo.getValue().stream().collect(Collectors.groupingBy(EvaluationResult::getVendorId));
//                BigDecimal minPrice = BigDecimal.valueOf(Integer.MAX_VALUE);
//                WinVendorInfoDto minInfo = null;
//                List<WinVendorInfoDto> infoDtos = new LinkedList<>();
//                for (Map.Entry<Long, List<EvaluationResult>> veInfo : veInfos.entrySet()) {
//                    List<EvaluationResult> value = veInfo.getValue();
//                    EvaluationResult tempResult = value.get(0);
//                    BigDecimal tempMin = BigDecimal.ZERO;
//                    Map<Long, Double> lineCountMap = new HashMap<>(value.size());
//                    for (EvaluationResult evaluationResult : value) {
//                        BigDecimal multiply = evaluationResult.getDiscountPrice().multiply(BigDecimal.valueOf(evaluationResult.getQuantity()));
//                        tempMin = tempMin.add(multiply);
//                        lineCountMap.put(evaluationResult.getOrderLineId(), evaluationResult.getQuantity());
//                    }
//                    WinVendorInfoDto winVendorInfoDto = WinVendorInfoDto.builder()
//                            .companyId(tempResult.getVendorId())
//                            .companyName(tempResult.getVendorName())
//                            .discountPrice(tempMin)
//                            .lineCountMap(lineCountMap)
//                            .build();
//                    if (minPrice.compareTo(tempMin) > 0) {
//                        minPrice = tempMin;
//                        minInfo = winVendorInfoDto;
//                    }
//                    infoDtos.add(winVendorInfoDto);
//                }
//                minInfo = WinVendorInfoDto.builder()
//                        .companyId(minInfo.getCompanyId())
//                        .companyName(minInfo.getCompanyName())
//                        .discountPrice(minInfo.getDiscountPrice())
//                        .build();
//                QuotaCalculateParam param = QuotaCalculateParam.builder()
//                        .buCode(buCode)
//                        .winVendorInfoDtoList(infoDtos)
//                        .categoryId(categoryId)
//                        .categoryName(categoryName)
//                        .calculateType(quotaDistributeType)
//                        .minDiscountVendorInfo(minInfo).build();
//                quotaCalculateParams.add(param);
//            }
//            if (CollectionUtils.isNotEmpty(quotaCalculateParams)) {
//                List<OrderLine> orderLines = new LinkedList<>();
//                List<QuotaCalculateParam> calculate = inqClient.calculate(quotaCalculateParams);
//                for (QuotaCalculateParam param : calculate) {
//                    for (WinVendorInfoDto winVendorInfoDto : param.getWinVendorInfoDtoList()) {
//                        BigDecimal quotaRatio = winVendorInfoDto.getQuota();
//                        //更新需求行的配额
//                        winVendorInfoDto.getLineCountMap().forEach((k, v) -> {
//                            BigDecimal num = BigDecimal.valueOf(v).multiply(quotaRatio.multiply(BigDecimal.valueOf(0.01)));
//
//                            OrderLine orderLine = new OrderLine();
//                            orderLine.setOrderLineId(k).setQuotaQuantity(num).setQuotaRatio(quotaRatio);
//                            orderLines.add(orderLine);
//                        });
//                    }
//                }
//                orderLineService.updateBatchById(orderLines);
//            }
//
//        }
        return null;
    }

    //用于按品类金额分配类型的校验
    public String getBuCode(List<EvaluationResult> results) {
        Set<String> buIds = new HashSet<>();
        for (EvaluationResult result : results) {
            if (result.getOuId() == null) {
                buIds.add(baseClient.getOrgParentCodeByOrgId(result.getOrgId()));
            } else {
                BaseOuGroupDetailVO baseOuGroupDetailVO = baseClient.queryOuDetailById(result.getOuId());
                if (Objects.isNull(baseOuGroupDetailVO)) {
                    throw new BaseException("无法找到ou组信息");
                }
                if (Objects.nonNull(baseOuGroupDetailVO) && !CollectionUtils.isEmpty(baseOuGroupDetailVO.getDetails())) {
                    Set<String> buSets = baseOuGroupDetailVO.getDetails().stream().map(BaseOuDetailVO::getBuId).collect(Collectors.toSet());
                    buIds.addAll(buSets);
                }
            }
        }
        Boolean sameQuotaInBuIds = inqClient.isSameQuotaInBuIds(buIds);
        if (!sameQuotaInBuIds) {
            throw new BaseException("无法计算配额，按品类金额分配的所对应需求行的事业部的所属配额不一致");
        }
        return buIds.stream().findFirst().orElse("");
    }

    public String getBuCode(EvaluationResult result) {
        if (result.getOuId() == null) {
            return baseClient.getOrgParentCodeByOrgId(result.getOrgId());
        } else {
            BaseOuGroupDetailVO baseOuGroupDetailVO = baseClient.queryOuDetailById(result.getOuId());
            if (Objects.isNull(baseOuGroupDetailVO)) {
                throw new BaseException("无法找到ou组信息");
            }
            if (Objects.nonNull(baseOuGroupDetailVO) && !CollectionUtils.isEmpty(baseOuGroupDetailVO.getDetails())) {
                List<String> buIds = baseOuGroupDetailVO.getDetails().stream().map(BaseOuDetailVO::getBuId).collect(Collectors.toList());
                Boolean sameQuotaInBuIds = inqClient.isSameQuotaInBuIds(buIds);
                if (!sameQuotaInBuIds) {
                    throw new BaseException("无法计算配额，需求行对应的ou组所属配额不一致");
                }
                return buIds.get(0);
            }
        }
        return "";
    }

    /**
     * 根据用户当前的需求行去获取对应的报价行，根据报价行去获取
     *
     * @param currentResult
     * @return
     */
    @Override
    public List<BidOrderLineTemplateReportLineVO> generateTemplatePriceReport(EvaluationResult currentResult) {
        OrderHead one = orderHeaderDao.findOne(Wrappers.lambdaQuery(OrderHead.class)
                .eq(OrderHead::getBidingId, currentResult.getBidingId()).last("limit 1"));
        if (!RequirementPricingType.MODEL_PURCHASER.getCode().equals(one.getPricingType())) {
            return Collections.emptyList();
        }
        EvaluationQueryParam queryParam = EvaluationQueryParam.builder()
                .bidingId(currentResult.getBidingId())
                .requirementLineId(currentResult.getRequirementLineId())
                .round(currentResult.getRound())
                .build();
        //找出同一个需求行的评选行
        List<EvaluationResult> evaluationResults = findEvaluationResults(queryParam);
        //获取模板
        List<BidOrderLineTemplateTempReportVO> list = new LinkedList<>();
        for (EvaluationResult evaluationResult : evaluationResults) {
            Long orderLineId = evaluationResult.getOrderLineId();
            String vendorName = evaluationResult.getVendorName();
            List<BidOrderLineTemplatePriceDetail> headers = templateHeaderMapper.selectList(Wrappers.lambdaQuery(BidOrderLineTemplatePriceDetail.class)
                    .eq(BidOrderLineTemplatePriceDetail::getLineId, orderLineId));
            for (BidOrderLineTemplatePriceDetail h : headers) {
                //获取到一个头模板的明细项列表
                List<BidOrderLineTemplateTempReportVO> collect = templateLineMapper
                        .selectList(Wrappers.lambdaQuery(BidOrderLineTemplatePriceDetailLine.class)
                                .eq(BidOrderLineTemplatePriceDetailLine::getHeaderId, h.getId()))
                        .stream().map(e -> {
                            BidOrderLineTemplateTempReportVO detail = new BidOrderLineTemplateTempReportVO();
                            detail.setHeaderName(h.getDescription());
                            detail.setLineName(e.getCostDescription());
                            detail.setVendorName(vendorName);
                            detail.setTaxPrice(e.getTaxTotalPrice());
                            return detail;
                        }).collect(Collectors.toList());
                list.addAll(collect);
            }
        }
        //按大类、明细分组
        Map<String, Map<String, List<BidOrderLineTemplateTempReportVO>>> groupMap = list.stream()
                .collect(Collectors.groupingBy(BidOrderLineTemplateTempReportVO::getHeaderName
                        , Collectors.groupingBy(BidOrderLineTemplateTempReportVO::getLineName)));
        return groupMap.keySet().stream().flatMap(headerName -> {
            //获取同一个大类的所有明细,明细按行分组
            Map<String, List<BidOrderLineTemplateTempReportVO>> lineMap = groupMap.get(headerName);
            return lineMap.keySet().stream().map(lineName -> {
                //按照上述规则分组的明细
                List<BidOrderLineTemplateTempReportVO> bidOrderLineTemplateTempReportVOS = lineMap.get(lineName);
                BidOrderLineTemplateReportLineVO vo = new BidOrderLineTemplateReportLineVO();
                vo.setHeaderName(headerName);
                vo.setLineName(lineName);
                List<TemplateVendorPriceVO> priceVOS = bidOrderLineTemplateTempReportVOS.stream().map(e -> {
                    TemplateVendorPriceVO priceVO = new TemplateVendorPriceVO();
                    priceVO.setTaxPrice(e.getTaxPrice());
                    priceVO.setVendorName(e.getVendorName());
                    return priceVO;
                }).collect(Collectors.toList());
                vo.setPrice(priceVOS);
                return vo;
            });
        }).collect(Collectors.toList());
    }

    private List<List<String>> head(List<CustomTableVO> customTableList) {
        List<List<String>> head = new ArrayList<List<String>>();
        for (CustomTableVO customTableVO : customTableList) {
            head.add(Arrays.asList(customTableVO.getColumnName()));
        }
        return head;
    }

    private List<List<String>> data(List<CustomTableVO> customTableList, List<EvaluationResult> list) {
        List<List<String>> data = new ArrayList<List<String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (EvaluationResult vo : list) {
            List<String> dataItem = new ArrayList<String>();
            for (CustomTableVO table : customTableList) {
                String methodName = "get" + String.valueOf(table.getColumnCode().charAt(0)).toUpperCase() + table.getColumnCode().substring(1);
                Object obj = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(EvaluationResult.class, methodName), vo);
                if (("getPriceStartTime".equals(methodName) || "getPriceEndTime".equals(methodName)) && obj != null) {
                    obj = sdf.format(obj);
                }
                dataItem.add(obj == null ? "" : obj.toString());
            }
            data.add(dataItem);
        }
        return data;
    }


}
