package com.midea.cloud.srm.sup.riskradar.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.model.supplier.riskradar.dto.RiskRadarDto;
import com.midea.cloud.srm.model.supplier.riskradar.entity.*;
import com.midea.cloud.srm.model.supplier.riskraider.r2.dto.R2RiskInfoDto;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskLabel;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditDto;
import com.midea.cloud.srm.sup.riskradar.mapper.RiskRatingMapper;
import com.midea.cloud.srm.sup.riskradar.service.*;
import com.midea.cloud.srm.sup.riskraider.r2.service.impl.R2RiskInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r7.service.impl.R7FinancialServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r8.service.impl.R8DiscreditServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  风险评级表 服务实现类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 14:14:17
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class RiskRatingServiceImpl extends ServiceImpl<RiskRatingMapper, RiskRating> implements IRiskRatingService {
    @Resource
    private IFinancialAssessService financialAssessService;
    @Resource
    private IFinancialRatingService financialRatingService;
    @Resource
    private IFinancialRatRevService financialRatRevService;
    @Resource
    private IFinancialStatusService iFinancialStatusService;

    @Autowired
    private R2RiskInfoServiceImpl r2RiskInfoService;
    @Autowired
    private R7FinancialServiceImpl financialService;
    @Autowired
    private R8DiscreditServiceImpl r8DiscreditService;

    private final ThreadPoolExecutor executorService;


    public RiskRatingServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        executorService = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("风险雷达-send-log", true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public RiskRadarDto getRiskRadarDto(Long vendorId) {
        Assert.notNull(vendorId, "vendorId不能为空");
        RiskRadarDto riskRadarDto = new RiskRadarDto();

        // 获取风险评级信息
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
        HashMap<String, Long> map = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        CompletableFuture.runAsync(() -> {
            long start = System.currentTimeMillis();
            try {
                RiskRating riskRating = buildRiskRating(vendorId);
                riskRadarDto.setRiskRating(riskRating);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            } finally {
                countDownLatch.countDown();
                map.put("r1:", System.currentTimeMillis() - start);
            }

        }, executorService);

        //获取财务状况信息
        CompletableFuture.runAsync(() -> {
            long start = System.currentTimeMillis();
            R7FinancialDto financialDto = financialService.getR7FinancialDtoByCompanyId(vendorId);
            try {
                if (Objects.nonNull(financialDto)) {
                    // 获取财务状况数据
                    List<FinancialStatus> financialStatuses = buildFinancialStatusList(financialDto);
                    riskRadarDto.setFinancialStatuses(financialStatuses);
                    // 获取财务状况评价
                    riskRadarDto.setFinancialAssess(buildFinancialAssess(financialDto));
                    // 获取财务评级数据
                    List<FinancialRating> financialRatings = buildFinancialRatingList(financialDto);
                    riskRadarDto.setFinancialRating(financialRatings);
                    // 获取财务评级评论表
                    riskRadarDto.setFinancialRatRev(buildFinancialRatRev(financialDto));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            } finally {
                map.put("r2:", System.currentTimeMillis() - start);
                countDownLatch.countDown();
            }


        }, executorService);

        CompletableFuture.runAsync(() -> {
            long start = System.currentTimeMillis();
            try {
                R8DiscreditDto discreditDto = r8DiscreditService.getR8DiscreditDtoByCompanyId(vendorId);
                riskRadarDto.setR8DiscreditDto(discreditDto);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            } finally {
                countDownLatch.countDown();
                map.put("r3:", System.currentTimeMillis() - start);
            }


        }, executorService);

        try {
            countDownLatch.await(120, TimeUnit.SECONDS);
            map.forEach((k, v) -> log.info(k + "--" + v));
        } catch (InterruptedException e) {
            return null;
        }

        return riskRadarDto;
    }

    public RiskRating buildRiskRating(Long companyId) {
        R2RiskInfoDto riskInfoDto = r2RiskInfoService.getRiskInfoDtoByCompanyId(companyId);
        if (Objects.isNull(riskInfoDto)) {
            return null;

        }
        R2RiskLabel riskLabel = Optional.ofNullable(riskInfoDto.getRiskLabel()).orElse(new R2RiskLabel());
        return new RiskRating()
                .setVendorId(riskInfoDto.getVendorId())
                .setVendorName(riskInfoDto.getVendorName())
                .setVendorCode(riskInfoDto.getVendorCode())
                .setBusiness(riskLabel.getBusinessRiskAnalysis())
                .setOpinion(riskLabel.getNewsRiskAnalysis())
                .setOperating(riskLabel.getFinanceRiskAnalysis())
                .setInvolved(riskLabel.getJudicialRiskAnalysis())
                .setDishSysLog(riskLabel.getBlacklistRiskAnalysis())
                .setLabel(riskInfoDto.getLabels())
                .setSituation(riskInfoDto.getRiskSituation());
    }

    public List<FinancialRating> buildFinancialRatingList(R7FinancialDto financialDto) {
        return financialDto.getCompanyFinanceDetailList().stream().map(x -> {
            return new FinancialRating()
                    .setVendorId(financialDto.getVendorId())
                    .setVendorCode(financialDto.getVendorCode())
                    .setVendorName(financialDto.getVendorName())
                    .setYear(Integer.valueOf(x.getYear()))
                    .setAssetLoadRatio(transferString(x.getAssetLiabilityRatio()))
                    .setNetProfitRate(transferString(x.getNetProfitMargin()))
                    .setNetAssetProfitMargin(transferString(x.getNetProfitRateOfAssets()))
                    .setTotalAssetTurnover(transferString(x.getTurnoverOfTotalAssets()));
        }).collect(Collectors.toList());
    }

    public BigDecimal transferString(String str) {
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }

    public FinancialAssess buildFinancialAssess(R7FinancialDto financialDto) {
        return new FinancialAssess()
                .setVendorId(financialDto.getVendorId())
                .setVendorCode(financialDto.getVendorCode())
                .setVendorName(financialDto.getVendorName())
                .setTitle(financialDto.getSummary())
                .setAsset(financialDto.getTotalAssetsAssessment())
                .setLiabilities(financialDto.getTotalLiabilitiesAssessment())
                .setBusiness(financialDto.getTotalRevenueAssessment())
                .setNetProfit(financialDto.getNetProfitAssessment())
                .setPayTaxes(financialDto.getTotalAmountOfTaxPaymentAssessment());

    }

    public List<FinancialStatus> buildFinancialStatusList(R7FinancialDto financialDto) {
        return financialDto.getCompanyFinanceDetailList().stream().map(x -> {
            return new FinancialStatus()
                    .setVendorId(financialDto.getVendorId())
                    .setVendorCode(financialDto.getVendorCode())
                    .setVendorName(financialDto.getVendorName())
                    .setYear(Integer.valueOf(x.getYear()))
                    .setAsset(new BigDecimal(x.getTotalAssets()))
                    .setLiabilities(new BigDecimal(x.getTotalLiabilities()))
                    .setBusiness(new BigDecimal(x.getTotalRevenue()))
                    .setNetProfit(new BigDecimal(x.getNetProfit()))
                    .setPayTaxes(new BigDecimal(x.getTotalAmountOfTaxPayment()));
        }).collect(Collectors.toList());
    }

    public FinancialRatRev buildFinancialRatRev(R7FinancialDto financialDto) {
        return new FinancialRatRev()
                .setVendorId(financialDto.getVendorId())
                .setVendorCode(financialDto.getVendorCode())
                .setVendorName(financialDto.getVendorName())
                .setTitle(financialDto.getSummary())
                .setAssetLoadRatio(financialDto.getAssetLiabilityRatioAssessment())
                .setNetProfitRate(financialDto.getNetProfitMarginAssessment())
                .setNetAssetProfitMargin(financialDto.getNetProfitRateOfAssetsAssessment())
                .setTotalAssetTurnover(financialDto.getTurnoverOfTotalAssetsAssessment());

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Long startTime = System.currentTimeMillis();
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("1号完成");
            return "A";
        }, executorService);
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("2号完成");
            return "B";
        }, executorService);
        CompletableFuture<String> stringCompletableFuture3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("3号完成");
            return "C";
        }, executorService).thenApplyAsync(e -> {
            System.out.println(e);
            return "CC";
        });

        countDownLatch.await(10, TimeUnit.SECONDS);
        String s1 = stringCompletableFuture1.get();
        String s2 = stringCompletableFuture2.get();
        String s3 = stringCompletableFuture3.get();
        System.out.println(s1 + s2 + s3);
        System.out.println("总共耗时：" + (System.currentTimeMillis() - startTime) / 1000);
        executorService.shutdown();
    }
}
