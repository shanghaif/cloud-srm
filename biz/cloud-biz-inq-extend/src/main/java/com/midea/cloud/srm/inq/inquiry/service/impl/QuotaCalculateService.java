package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.inq.inquiry.mapper.*;
import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import com.midea.cloud.srm.model.inq.quota.vo.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.midea.cloud.common.enums.inq.ReasonForLimit.*;

/**
 * @author tanjl11
 * @date 2020/10/21 13:09
 */
@Service
public class QuotaCalculateService {
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private QuotaRestrictionsMapper restrictionsMapper;
    @Autowired
    private QuotaBuMapper quotaBuMapper;
    @Autowired
    private AgreementRatioMapper agreementRatioMapper;
    @Autowired
    private QuotaRebateMapper quotaRebateMapper;
    @Autowired
    private QuotaPreinstallMapper quotaPreinstallMapper;
    @Autowired
    private PriceStandardMapper priceStandardMapper;

    private final ThreadPoolExecutor ioThreadPool;
    private final ForkJoinPool calculateThreadPool;

    public QuotaCalculateService() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ioThreadPool = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("notInRuntimeThreadPool", true), new ThreadPoolExecutor.CallerRunsPolicy());
        calculateThreadPool = new ForkJoinPool(cpuCount + 1);
    }

    @SneakyThrows
    public List<QuotaCalculateParam> calculate(List<QuotaCalculateParam> parameterList) {
        if (CollectionUtils.isEmpty(parameterList)) {
            return Collections.emptyList();
        }
        //校验&批量获取数据库|外围系统数据
        int size = parameterList.stream().map(QuotaCalculateParam::getCalculateType).collect(Collectors.toSet()).size();
        if (size > 1) {
            throw new BaseException("单次配额计算只允许同一类型的计算");
        }

        //根据供应商配额下限分配配额
        Set<String> buCodes = new HashSet<>();
        Set<Long> companyIds = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();
        for (QuotaCalculateParam quotaCalculateParam : parameterList) {
            quotaCalculateParam.getWinVendorInfoDtoList().forEach(e -> companyIds.add(e.getCompanyId()));
            buCodes.add(quotaCalculateParam.getBuCode());
            categoryIds.add(quotaCalculateParam.getCategoryId());
        }

        //来源1 获取为战略供应商的配额配置
        Map<String, Long> buCodeAndQuotaId = new HashMap<>();
        //获取事业部-配额关系
        if (CollectionUtils.isNotEmpty(parameterList) && CollectionUtils.isNotEmpty(buCodes)) {
            buCodeAndQuotaId = quotaBuMapper.selectList(Wrappers.lambdaQuery(QuotaBu.class)
                    .select(QuotaBu::getBuCode, QuotaBu::getQuotaId)
                    .in(QuotaBu::getBuCode, buCodes)).stream().collect(Collectors.toMap(QuotaBu::getBuCode, QuotaBu::getQuotaId));
        }
        StringBuilder errMsg = new StringBuilder();
        for (String buCode : buCodes) {
            if (!buCodeAndQuotaId.containsKey(buCode)) {
                errMsg.append("事业部编码:")
                        .append(buCode).append("没有配额配置,请到配额管理->配额配置配置相关单据;");
            }
        }
        if (errMsg.length() > 0) {
            throw new BaseException(errMsg.toString());
        }
        //配额id
        Collection<Long> quotaIds = buCodeAndQuotaId.values();
        final CountDownLatch countDownLatch = new CountDownLatch(6);
        //======异步构造基础数据开始========
        Map<Long, CompanyInfo> companyLevel = CompletableFuture.supplyAsync(() -> supplierClient.getVendorClassificationByCompanyIdsForAnon(companyIds), ioThreadPool)
                .thenApplyAsync(e -> {
                    Map<Long, CompanyInfo> result = e.stream().collect(Collectors.toMap(CompanyInfo::getCompanyId, Function.identity()));
                    countDownLatch.countDown();
                    return result;
                }, calculateThreadPool).get();
        //1.获取供应商品类关系
        Map<Long, Map<Long, List<OrgCategory>>> companyAndOrgCategoryMap = CompletableFuture.supplyAsync(() -> {
            Map<String, Collection<Long>> map = new HashMap<>();
            map.put("companyIds", companyIds);
            map.put("categoryIds", categoryIds);
            List<OrgCategory> orgRelationWithCompanyIds = supplierClient.getOrgRelationByCompanyIdsForAnon(map);
            return orgRelationWithCompanyIds;
        }, ioThreadPool).thenApplyAsync(e -> {
            Map<Long, Map<Long, List<OrgCategory>>> result = e.stream().collect(Collectors.groupingBy(OrgCategory::getCompanyId
                    , Collectors.groupingBy(OrgCategory::getCategoryId)));
            countDownLatch.countDown();
            return result;
        }, calculateThreadPool).get();
        //2.获取配额id和战略/黄牌供应商/新供应商的配额关系
        Map<Long, Map<String, Integer>> quotaIdAndSYNProPortionValue = new HashMap<>();
        if (CollectionUtils.isNotEmpty(quotaIds)) {
            CompletableFuture.supplyAsync(() -> restrictionsMapper.selectList(Wrappers.lambdaQuery(QuotaRestrictions.class)
                    .select(QuotaRestrictions::getQuotaId, QuotaRestrictions::getProportion, QuotaRestrictions::getRestrictionsType)
                    .in(QuotaRestrictions::getQuotaId, quotaIds)
                    .in(QuotaRestrictions::getRestrictionsType, QUALITY_WARNING.code,
                            STRATEGIC_SUPPLIER.code, YELLOW.code)
            ), ioThreadPool).thenAcceptAsync(e -> {
                for (QuotaRestrictions quotaRestriction : e) {
                    Long quotaId = quotaRestriction.getQuotaId();
                    Map<String, Integer> syProprtion = quotaIdAndSYNProPortionValue.get(quotaId);
                    if (Objects.isNull(syProprtion)) {
                        syProprtion = new HashMap<>();
                    }
                    syProprtion.put(quotaRestriction.getRestrictionsType(), quotaRestriction.getProportion());
                    quotaIdAndSYNProPortionValue.put(quotaId, syProprtion);
                }
                countDownLatch.countDown();
            }, calculateThreadPool);
        } else {
            countDownLatch.countDown();
        }
        //可以根据前面2个map获取 事业部-战略供应商的配额关系

        //3. 事业部和小类和供应商agreement_ratio,该分组为配额Id+小类Id
        Map<Long, Map<Long, List<AgreementRatio>>> quotaAndCategoryGroupMap =
                CompletableFuture.supplyAsync(() -> agreementRatioMapper.selectList(Wrappers.lambdaQuery(AgreementRatio.class)
                        .select(AgreementRatio::getAgreementRatioId, AgreementRatio::getCategoryId
                                , AgreementRatio::getVendorId, AgreementRatio::getQuotaId)
                        .in(AgreementRatio::getCategoryId, categoryIds)
                        .in(AgreementRatio::getQuotaId, quotaIds)), ioThreadPool).thenApplyAsync(rations -> {
                    Map<Long, Map<Long, List<AgreementRatio>>> result = rations.stream()
                            .collect(Collectors.groupingBy(AgreementRatio::getQuotaId
                                    , Collectors.groupingBy(AgreementRatio::getCategoryId)));
                    countDownLatch.countDown();
                    return result;
                }, calculateThreadPool).get();

        //4.获取预设比例
        Map<Long, List<QuotaPreinstall>> quotaPreinstalls = new HashMap<>();
        if (CollectionUtils.isNotEmpty(quotaIds)) {
            quotaPreinstalls = CompletableFuture.supplyAsync(() ->
                            quotaPreinstallMapper.selectList(Wrappers.lambdaQuery(QuotaPreinstall.class)
                                    .in(QuotaPreinstall::getQuotaId, quotaIds)
                            )
                    , ioThreadPool).thenApplyAsync(preinstalls -> {
                Map<Long, List<QuotaPreinstall>> result = preinstalls.stream().collect(Collectors.groupingBy(QuotaPreinstall::getQuotaId));
                countDownLatch.countDown();
                return result;
            }, calculateThreadPool).get();
        } else {
            countDownLatch.countDown();
        }
        //5.获取预估返利
        Map<Long, List<QuotaRebate>> quotaRebateMap = CompletableFuture.supplyAsync(() ->
                        quotaRebateMapper.selectList(Wrappers.lambdaQuery(QuotaRebate.class)
                                .select(QuotaRebate::getCategoryId, QuotaRebate::getQuotaId, QuotaRebate::getVendorId, QuotaRebate::getQuotaRebateId)
                                .in(QuotaRebate::getQuotaId, quotaIds))
                , ioThreadPool).thenApplyAsync(rebates -> {
            Map<Long, List<QuotaRebate>> result = rebates.stream().collect(Collectors.groupingBy(QuotaRebate::getQuotaId));
            countDownLatch.countDown();
            return result;
        }, calculateThreadPool).get();
        //6.获取价差,根据配额和品类的价差计算
        Map<Long, Map<Long, List<PriceStandard>>> quotaAndCategoryPriceStandard = CompletableFuture.supplyAsync(
                () -> priceStandardMapper.selectList(Wrappers.lambdaQuery(PriceStandard.class)
                        .in(PriceStandard::getCategoryId, categoryIds)
                        .in(PriceStandard::getQuotaId, quotaIds)
                ), ioThreadPool).thenApplyAsync(priceStandards -> {
            Map<Long, Map<Long, List<PriceStandard>>> result = priceStandards.stream().collect(Collectors.groupingBy(PriceStandard::getQuotaId, Collectors.groupingBy(PriceStandard::getCategoryId)));
            countDownLatch.countDown();
            return result;
        }, calculateThreadPool).get();
        //等待异步过程
        CountDownLatch parallelCountDownLatch = new CountDownLatch(parameterList.size());
        countDownLatch.await();
        final Map<String, Long> buCodeAndQuotaIdTemp = buCodeAndQuotaId;
        final Map<Long, List<QuotaPreinstall>> quotaPreinstallsTemp = quotaPreinstalls;
        //7.并行计算
        calculateThreadPool.submit(() -> parameterList.parallelStream().forEach(e -> {
            //step1，初始化下限
            initMinQuota(e, buCodeAndQuotaIdTemp, quotaIdAndSYNProPortionValue, quotaAndCategoryGroupMap, companyLevel);
            //step2 对剩余数量按价格和预设比例进行再分配
            setQuotaStep2(e, buCodeAndQuotaIdTemp, quotaPreinstallsTemp, quotaRebateMap);
            //step3 设置配额上限
            setQuotaStep3(quotaIdAndSYNProPortionValue, companyLevel, e, buCodeAndQuotaIdTemp, companyAndOrgCategoryMap, quotaAndCategoryPriceStandard);
            //step4 对剩余数量按价格和预设比例进行再分配
            setQuotaStep4(e, buCodeAndQuotaIdTemp, quotaPreinstallsTemp);
            //step5 取5整数
            setQuotaStep5(e);
            //减一
            parallelCountDownLatch.countDown();
        }));
        parallelCountDownLatch.await();
        return parameterList;
    }


    /**
     * 配置初始配额
     * 1)	对于该寻源行的中标供应商，
     * a)	如果是战略供应商，则根据事业部从配额上下限配置表中获取限额原因为“战略供应商”的比例，即下限，在供应商分级字段体现是否为战略供应商
     * b)	根据事业部和供应商和小类从协议比例表中获取配额下限 quota_
     * 2)	如果一个供应商获取到了多个下限，则取最大值
     * 3)	供应商的配额=获取到的供应商配额下限值
     */
    protected void initMinQuota(QuotaCalculateParam quotaCalculateParam, Map<String, Long> buCodeAndQuotaId
            , Map<Long, Map<String, Integer>> quotaIdAndSYNProPortionValue,
                                Map<Long, Map<Long, List<AgreementRatio>>> quotaAndCategoryGroupMap,
                                Map<Long, CompanyInfo> companyLevel
    ) {
        for (WinVendorInfoDto winVendorInfoDto : quotaCalculateParam.getWinVendorInfoDtoList()) {
            Long quotaId = buCodeAndQuotaId.get(quotaCalculateParam.getBuCode());
            Map<Long, List<AgreementRatio>> categoryMap = quotaAndCategoryGroupMap.get(quotaId);
            List<AgreementRatio> agreementRatios = new LinkedList<>();
            if (Objects.nonNull(categoryMap)) {
                agreementRatios = categoryMap.get(quotaCalculateParam.getCategoryId());
            }
            Long companyId = winVendorInfoDto.getCompanyId();
            Integer from1 = agreementRatios.stream().filter(e -> Objects.equals(e.getVendorId(), companyId)).map(AgreementRatio::getProportion).findAny().orElse(0);
            BigDecimal from1Value = BigDecimal.valueOf(from1);
            winVendorInfoDto.setMinQuota(from1Value);
            String level = companyLevel.get(companyId).getVendorClassification();
            //如果是战略供应商，从取值来源1、2取最大
            if (Objects.equals(STRATEGIC_SUPPLIER.code, level)) {
                //来源2，战略供应商
                BigDecimal from2Value = BigDecimal.valueOf(quotaIdAndSYNProPortionValue.get(quotaId).get(STRATEGIC_SUPPLIER.code));
                winVendorInfoDto.setMinQuota(from2Value.max(from1Value));
            }
            winVendorInfoDto.setQuota(winVendorInfoDto.getMinQuota());
        }
    }

    /**
     * 该寻源行的剩余比例=100%-当前各个供应商配额之和
     * 该品类的剩余比例=100%-当前各个供应商配额之和
     * ----
     * 根据该寻源行的中标供应商数量和事业部从预设比例表中获取预设比例
     * 根据该品类的中标供应商数量和事业部从预设比例表中获取预设比例
     * ----
     * 根据供应商和寻源行的品类和事业部获取预估返利
     * ----
     * 根据供应商对该行的折息价格*（1-预估返利）进行升序排列，按排名分配预设比例
     * 同一品类的中标行的折息价格*（1-预估返利）*需求数量之和，进行升序排列，按排名分配预设比例
     * ----
     * 供应商的配额=供应商当前配额+剩余比例*该供应商的预设比例
     */
    protected void setQuotaStep2(QuotaCalculateParam quotaCalculateParam
            , Map<String, Long> buCodeAndQuotaId
            , Map<Long, List<QuotaPreinstall>> quotaPreinstalls
            , Map<Long, List<QuotaRebate>> quotaRebateMap) {

        //初始化剩余配额
        List<WinVendorInfoDto> winVendorInfoDtoList = quotaCalculateParam.getWinVendorInfoDtoList();
        BigDecimal reduce = winVendorInfoDtoList
                .stream().map(WinVendorInfoDto::getMinQuota).reduce(BigDecimal.valueOf(100), (x, y) -> x.subtract(y));
        //根据该寻源行的中标供应商数量和事业部从预设比例表中获取预设比例
        Long quotaId = buCodeAndQuotaId.get(quotaCalculateParam.getBuCode());
        List<QuotaPreinstall> temp = quotaPreinstalls.get(quotaId);
        //获取当前配额分配
        QuotaPreinstall currentPreInstall = temp.stream().filter(e -> Objects.equals(e.getSupplierNumber(), winVendorInfoDtoList.size()))
                .findAny().orElseThrow(() -> new BaseException(String.format("当前中标供应商数量为%s，该配额配置下无对应的供应商数量分配", winVendorInfoDtoList.size())));
        //获取预估返利并赋值
        List<QuotaRebate> quotaRebates = quotaRebateMap.get(quotaId);
        for (WinVendorInfoDto winVendorInfoDto : winVendorInfoDtoList) {
            boolean findProportion = false;
            for (QuotaRebate quotaRebate : quotaRebates) {
                //如果品类和compangId相等，则找到对应的预估比例
                if (Objects.equals(quotaRebate.getCategoryId(), quotaCalculateParam.getCategoryId())
                        && Objects.equals(quotaRebate.getVendorId(), winVendorInfoDto.getCompanyId())
                ) {
                    winVendorInfoDto.setProportion(quotaRebate.getProportion());
                    findProportion = true;
                    break;
                }
            }
            if (!findProportion) {
                winVendorInfoDto.setProportion(0);
            }
            //赋值临时总价,这里招标/询价那边已经根据类型算好需求量*折息价或折息价
            BigDecimal tempPrice = BigDecimal.valueOf(100 - winVendorInfoDto.getProportion())
                    .multiply(BigDecimal.valueOf(0.01)).multiply(winVendorInfoDto.getDiscountPrice());
            winVendorInfoDto.setTempPrice(tempPrice);
        }
        /**
         * 折息价格(maybe*需求数量)（1-预估返利）之和
         */
        //排序
        Collections.sort(winVendorInfoDtoList, (o1, o2) -> o2.getTempPrice().compareTo(o1.getTempPrice()));
        //供应商当前配额+剩余比例*该供应商的预设比例
        String[] split = currentPreInstall.getQuotaPreinstallNumber().split(",");
        BigDecimal tempReduce = new BigDecimal(reduce.toString());
        for (int i = 0; i < winVendorInfoDtoList.size(); i++) {
            String presetRatio = split[i];
            WinVendorInfoDto currentVendorInfo = winVendorInfoDtoList.get(i);
            BigDecimal currentQuota = currentVendorInfo.getQuota();
            BigDecimal subCount = tempReduce.multiply(new BigDecimal(presetRatio)).multiply(BigDecimal.valueOf(0.01));
            reduce = reduce.subtract(subCount);
            currentVendorInfo.setQuota(currentQuota.add(subCount));
        }
        //当前的剩余配额
        quotaCalculateParam.setLeftQuota(reduce);
    }

    /**
     * 1)	上限取值来源1：如果是黄牌供应商，则根据事业部从配额上下限配置表中获取限额原因为“黄牌”的比例，即上限。黄牌是供应商的品类状态，根据业务实体+品类+供应商去供应商品类关系表中获取
     * 2)	上限取值来源2：如果是新供应商，则根据事业部从配额上下限配置表中获取限额原因为“新供应商”的比例，即上限。新供应商判断逻辑：在系统中不存在有效的采购订单即为新供方
     * 3)	上限取值来源3：获取该寻源行所有中标供应商的折息价格*（1-预付返利），计算该供应商和最低价供应商的“折息价格*（1-预付返利）”价差，根据品类和事业部去价差标准表获取配置的价差值，如果价差>配置的价差值，则获取相应的配额上限
     * 4)	如果供应商存在多个配额上限，则以最小值为准
     * 5)	如果供应商配额上限值不为空，则供应商的配额=该供应商的配额上限
     */
    protected void setQuotaStep3(Map<Long, Map<String, Integer>> quotaIdAndSYNProPortionValue
            , Map<Long, CompanyInfo> companyInfoMap
            , QuotaCalculateParam quotaCalculateParam
            , Map<String, Long> buCodeAndQuotaId
            , Map<Long, Map<Long, List<OrgCategory>>> companyAndOrgCategoryMap
            , Map<Long, Map<Long, List<PriceStandard>>> quotaAndCategoryPriceStandard
    ) {

        BigDecimal leftCount = BigDecimal.valueOf(100);
        WinVendorInfoDto min = quotaCalculateParam.getMinDiscountVendorInfo();
        BigDecimal tempPrice = quotaCalculateParam.getWinVendorInfoDtoList().stream().filter(e -> Objects.equals(e.getCompanyId(), min.getCompanyId())).findAny().get().getTempPrice();
        min.setTempPrice(tempPrice);
        Long categoryId = quotaCalculateParam.getCategoryId();
        for (WinVendorInfoDto winVendorInfoDto : quotaCalculateParam.getWinVendorInfoDtoList()) {
            Long quotaId = buCodeAndQuotaId.get(quotaCalculateParam.getBuCode());
            //是否新供应商
            Long companyId = winVendorInfoDto.getCompanyId();
            CompanyInfo companyInfo = companyInfoMap.get(companyId);
            Integer fromNew = Integer.MAX_VALUE;
            boolean isNew = Objects.equals(companyInfo.getIfNewCompany(), YesOrNo.YES.getValue());
            Integer newTemp = quotaIdAndSYNProPortionValue.get(quotaId).get(NEW_SUPPLIER.code);
            if (isNew && Objects.nonNull(newTemp)) {
                fromNew = newTemp;
            }
            //是否黄牌
            Integer fromYellow = Integer.MAX_VALUE;
            boolean isYellow = false;
            Map<Long, List<OrgCategory>> orgCategoryMap = companyAndOrgCategoryMap.get(companyId);
            if (Objects.nonNull(orgCategoryMap)) {
                List<OrgCategory> orgCategories = orgCategoryMap.get(categoryId);
                if (CollectionUtils.isNotEmpty(orgCategories)) {
                    isYellow = orgCategories.stream().filter(e -> Objects.equals(e.getServiceStatus(), CategoryStatus.YELLOW.name())).findAny().isPresent();
                }
            }
            Integer yellowTemp = quotaIdAndSYNProPortionValue.get(quotaId).get(YELLOW.code);
            if (isYellow && Objects.nonNull(yellowTemp)) {
                fromYellow = yellowTemp;
            }
            //来源3
            Integer from3 = Integer.MAX_VALUE;
            Map<Long, List<PriceStandard>> priceStandMap = quotaAndCategoryPriceStandard.get(quotaId);
            BigDecimal subtract = winVendorInfoDto.getTempPrice().subtract(min.getTempPrice()).divide(min.getTempPrice(), 2, RoundingMode.HALF_DOWN);
            if (Objects.nonNull(priceStandMap)) {
                List<PriceStandard> priceStandards = priceStandMap.get(categoryId);
                for (PriceStandard priceStandard : priceStandards) {
                    if (subtract.compareTo(BigDecimal.valueOf(priceStandard.getSpread())) > 0) {
                        from3 = Math.min(priceStandard.getProportion(), from3);
                    }
                }
            }
            //取上限
            boolean notFindMaxQuota = Objects.equals(from3, Integer.MAX_VALUE)
                    && Objects.equals(fromNew, Integer.MAX_VALUE)
                    && Objects.equals(fromYellow, Integer.MAX_VALUE);
            if (!notFindMaxQuota) {
                //设置上限
                int maxQuota = Math.min(Math.min(from3, fromNew), fromYellow);
                BigDecimal max = BigDecimal.valueOf(maxQuota);
                winVendorInfoDto.setMaxQuota(max);
                winVendorInfoDto.setQuota(max);
            }
            leftCount = leftCount.subtract(winVendorInfoDto.getQuota());
        }
        //设置品类剩余比例
        quotaCalculateParam.setLeftQuota(leftCount);
    }

    /**
     * 2)	计算再分配配额的供应商数量：减去有配额上限的供应商
     * 3)	根据该品类的再分配配额的供应商数量和事业部从预设比例表中获取预设比例
     * 4)	根据供应商和品类和事业部获取预估返利
     * 5)	根据供应商对该品类的总金额进行升序排列，按排名分配预设比例
     * 供应商的配额=供应商当前配额+剩余比例*该供应商的预设比例
     *
     * @param quotaCalculateParam
     * @param quotaPreinstalls
     */
    protected void setQuotaStep4(QuotaCalculateParam quotaCalculateParam
            , Map<String, Long> buCodeAndQuotaId
            , Map<Long, List<QuotaPreinstall>> quotaPreinstalls
    ) {

        List<WinVendorInfoDto> leftVendorInfo = quotaCalculateParam.getWinVendorInfoDtoList().stream().filter(e -> Objects.isNull(e.getMaxQuota()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(leftVendorInfo)) {
            return;
        }
        BigDecimal leftCount = quotaCalculateParam.getLeftQuota();
        String buCode = quotaCalculateParam.getBuCode();
        Long quotaId = buCodeAndQuotaId.get(buCode);
        List<QuotaPreinstall> temp = quotaPreinstalls.get(quotaId);
        QuotaPreinstall currentPreInstall = temp.stream().filter(e -> Objects.equals(e.getSupplierNumber(), leftVendorInfo.size()))
                .findAny().orElseThrow(() -> new BaseException(String.format("当前剩余分配供应商数量为%s，该配额配置下无对应的供应商数量分配", leftVendorInfo.size())));
        Collections.sort(leftVendorInfo, (o1, o2) -> o2.getTempPrice().compareTo(o1.getTempPrice()));
        //供应商当前配额+剩余比例*该供应商的预设比例
        String[] split = currentPreInstall.getQuotaPreinstallNumber().split(",");
        BigDecimal tempReduce = new BigDecimal(leftCount.toString());
        for (int i = 0; i < leftVendorInfo.size(); i++) {
            String presetRatio = split[i];
            WinVendorInfoDto currentVendorInfo = leftVendorInfo.get(i);
            BigDecimal currentQuota = currentVendorInfo.getQuota();
            BigDecimal subCount = tempReduce.multiply(new BigDecimal(presetRatio)).multiply(BigDecimal.valueOf(0.01));
            leftCount = leftCount.subtract(subCount);
            currentVendorInfo.setQuota(currentQuota.add(subCount));
        }
        quotaCalculateParam.setLeftQuota(leftCount);
    }

    //取整数
    protected void setQuotaStep5(QuotaCalculateParam quotaCalculateParam) {
        List<WinVendorInfoDto> winVendorInfoDtoList = quotaCalculateParam.getWinVendorInfoDtoList();
        BigDecimal total = BigDecimal.valueOf(100);
        for (WinVendorInfoDto winVendorInfoDto : winVendorInfoDtoList) {
            BigDecimal five = BigDecimal.valueOf(5);
            BigDecimal divide = winVendorInfoDto.getQuota().divide(five, 0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sub = divide.multiply(five);
            if (sub.compareTo(BigDecimal.ZERO) <= 0) {
                sub = sub.add(five);
            }
            winVendorInfoDto.setQuota(sub);
            total = total.subtract(sub);
        }
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            WinVendorInfoDto winVendorInfoDto = winVendorInfoDtoList.get(0);
            winVendorInfoDto.setQuota(winVendorInfoDto.getQuota().add(total));
        } else if (total.compareTo(BigDecimal.ZERO) < 0) {
            WinVendorInfoDto winVendorInfoDto = winVendorInfoDtoList.get(winVendorInfoDtoList.size() - 1);
            winVendorInfoDto.setQuota(winVendorInfoDto.getQuota().add(total));
        }
    }


}
