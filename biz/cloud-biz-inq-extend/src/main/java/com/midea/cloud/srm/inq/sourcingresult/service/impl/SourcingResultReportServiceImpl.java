package com.midea.cloud.srm.inq.sourcingresult.service.impl;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryMapper;
import com.midea.cloud.srm.inq.sourcingresult.service.ISourcingResultReportService;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.quota.vo.VendorInfo;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.midea.cloud.common.utils.Functions.distinctByKey;

/**
 * Implement of {@link ISourcingResultReportService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class SourcingResultReportServiceImpl implements ISourcingResultReportService {

    @Resource
    private PriceLibraryMapper priceLibraryMapper;
    @Resource
    private BaseClient baseClient;


    @Override
    public SourcingResultReport generate(GenerateSourcingResultReportParameter parameter) {

        // 若没有任何投标行，则返回空报表
        if (CollectionUtils.isEmpty(parameter.getDemandLines())
                || CollectionUtils.isEmpty(parameter.getLastRoundVendorBiddingLines()))
            return SourcingResultReport.EMPTY_REPORT;


        // 预查询 OU组所对应的组织
        List<Long> ouIds = parameter.getDemandLines().stream()
                .filter(demandLine -> demandLine.getOuGroupId() != null)
                .map(SourcingDemandLine::getOuGroupId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, List<BaseOuDetailVO>> ouGroups = ouIds.isEmpty()
                ? Collections.emptyMap()
                : baseClient.queryOuInfoDetailByIds(ouIds).stream()
                .collect(Collectors.toMap(BaseOuGroupDetailVO::getOuGroupId, BaseOuGroupDetailVO::getDetails));

        // 获取 所有中标的投标行 & 按[需求行]分组
        Map<Long, List<VendorBiddingLine>> winningBiddingLines = parameter.getLastRoundVendorBiddingLines()
                .stream()
//                .filter(VendorBiddingLine::isWinning)
                .collect(Collectors.groupingBy(VendorBiddingLine::getDemandLineId));

        // 获取 所有中标的需求行 & 按[需求行ID]获取
        Map<Long, SourcingDemandLine> winningDemandLines = parameter.getDemandLines()
                .stream()
                .filter(demandLine -> winningBiddingLines.values().stream().flatMap(Collection::stream)
                        .anyMatch(biddingLine -> biddingLine.getDemandLineId().equals(demandLine.getId())))
                .collect(Collectors.toMap(SourcingDemandLine::getId, x -> x, (x, y) -> x));


        // generate.
        return this.generate(GenerateParameter.builder()
                .winningDemandLines(winningDemandLines)
                .winningBiddingLines(winningBiddingLines)
                .lastRoundVendorBiddingLines(parameter.getLastRoundVendorBiddingLines())
                .relatedOuGroups(ouGroups)
                .build());
    }

    /**
     * 生成 寻源结果报表
     *
     * @param parameter 寻源报表生成参数
     * @return 寻源结果报表
     */
    protected SourcingResultReport generate(GenerateParameter parameter) {

        List<DictItem> priceUpdateCategary = baseClient.listDictItemByDictCode("PRICE_UPDATE_CATEGARY");
        Set<String> codeSet = priceUpdateCategary.stream().map(DictItem::getDictItemCode).collect(Collectors.toSet());
        // 创建 [寻源结果需求行报表]集
        List<SourcingResultDemandLineReport> demandLineReports = parameter.getWinningDemandLines().values().stream()
                .filter(e -> {
                    List<VendorBiddingLine> lines = parameter.getWinningBiddingLines().get(e.getId());
                    //忽视所有数量为0或数量为空的值
                    return !lines.stream().allMatch(l -> Objects.isNull(l.getQuotaQuantity()) || l.getQuotaQuantity().compareTo(BigDecimal.ZERO) <= 0);
                })
                .map(demandLine -> {

                    // 获取 指定需求行的所有供应商中标投标行
                    List<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports = parameter.getWinningBiddingLines()
                            .getOrDefault(demandLine.getId(), Collections.emptyList())
                            .stream()
                            .map(biddingLine -> {

                                // 创建 [寻源结果供应商投标行报表]
                                return SourcingResultVendorBiddingLineReport.create(biddingLine, demandLine);

                            })
                            .collect(Collectors.toList());

                    // 创建 需求行价格趋势
                    List<SourcingDemandLinePriceTrend> sourcingDemandLinePriceTrends = this.createDemandLinePriceTrends(demandLine, parameter, codeSet);

                    // 创建 [寻源结果需求行报表]
                    return SourcingResultDemandLineReport.create(demandLine, vendorBiddingLineReports, sourcingDemandLinePriceTrends);

                })
                .collect(Collectors.toList());


        // 获取 最后一轮的供应商报价行集 & 按[投标供应商ID]分组
        Map<Long, List<VendorBiddingLine>> vendorBiddingLinesGroups = parameter.getLastRoundVendorBiddingLines().stream()
                .collect(Collectors.groupingBy(VendorBiddingLine::getBidVendorId));
        Map<Long, SourcingDemandLine> winningDemandLines = parameter.getWinningDemandLines();
        // 创建 [寻源结果供应商投标总结报表]集
        List<SourcingResultVendorBiddingSummaryReport> vendorBiddingSummaryReports = vendorBiddingLinesGroups.keySet().stream()
                .map(bidVendorId -> {

                    // 获取 指定供应商最后一轮的报价行
                    List<SourcingResultVendorBiddingLineReport> vendorBiddingLineReports = vendorBiddingLinesGroups.get(bidVendorId).stream()
                            .filter(e -> Objects.nonNull(parameter.getWinningDemandLines().get(e.getDemandLineId())))
                            .map(biddingLine -> SourcingResultVendorBiddingLineReport.create(biddingLine,
                                    parameter.getWinningDemandLines().get(biddingLine.getDemandLineId())))
                            .collect(Collectors.toList());

                    // 创建 [寻源结果供应商投标总结报表]
                    return SourcingResultVendorBiddingSummaryReport.create(vendorBiddingLineReports, winningDemandLines);

                })
                .collect(Collectors.toList());

        Map<Long, SourcingResultVendorBiddingSummaryReport> collect = vendorBiddingSummaryReports.stream().collect(Collectors.toMap(SourcingResultVendorBiddingSummaryReport::getBidVendorId, Function.identity()));
        for (SourcingResultDemandLineReport demandLineReport : demandLineReports) {
            Map<Long, SourcingResultVendorBiddingLineReport> map = demandLineReport.getWinningVendorBiddingLineReports().stream().collect(Collectors.toMap(SourcingResultVendorBiddingLineReport::getBidVendorId, Function.identity()));
            collect.forEach((k, v) -> {
                if (!map.containsKey(k)) {
                    SourcingResultVendorBiddingLineReport next = v.getVendorBiddingLineReports().iterator().next();
                    SourcingResultVendorBiddingLineReport report = BeanCopyUtil.copyProperties(next, SourcingResultVendorBiddingLineReport::new);
                    report.setDiscountPrice(null);
                    report.setQuotePrice(null);
                    SourcingResultVendorBiddingLineReport report1 = BeanCopyUtil.copyProperties(next, SourcingResultVendorBiddingLineReport::new);
                    report1.setDiscountPrice(null);
                    report1.setQuotePrice(null);
                    demandLineReport.getWinningVendorBiddingLineReports().add(report);
                    demandLineReport.getVendorBiddingLineReports().add(report1);
                }
            });
        }
        //去除一行都没有的供应商
        Map<Long, Boolean> shouldRemove = vendorBiddingSummaryReports.stream().collect(Collectors.toMap(SourcingResultVendorBiddingSummaryReport::getVendorId, sourcingResultVendorBiddingSummaryReport -> true));
        for (SourcingResultDemandLineReport demandLineReport : demandLineReports) {
            List<SourcingResultVendorBiddingLineReport> winReport = demandLineReport.getWinningVendorBiddingLineReports();
            for (SourcingResultVendorBiddingLineReport innerReport : winReport) {
                shouldRemove.put(innerReport.getVendorId(), shouldRemove.get(innerReport.getVendorId()) && !innerReport.isWinning());
            }
        }
        shouldRemove.forEach((k, v) -> {
            if (v) {
                for (SourcingResultDemandLineReport demandLineReport : demandLineReports) {
                    List<SourcingResultVendorBiddingLineReport> reports = demandLineReport.getWinningVendorBiddingLineReports();
                    for (int i = reports.size() - 1; i >= 0; i--) {
                        if (reports.get(i).getVendorId().equals(k)) {
                            reports.remove(i);
                        }
                    }
                }
            }
        });
        //去除后，一个个往前推

        // 创建 [寻源结果报表]
        return SourcingResultReport.builder()
                .demandLineReports(demandLineReports)
                .vendorBiddingSummaryReports(vendorBiddingSummaryReports)
                .build();
    }

    /**
     * 创建 需求行价格趋势
     *
     * @param demandLine 需求行
     * @param parameter  报表生成参数
     * @return 需求行价格趋势
     */
    protected List<SourcingDemandLinePriceTrend> createDemandLinePriceTrends(SourcingDemandLine demandLine,
                                                                             GenerateParameter parameter, Set<String> codeSet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 获取 价格数据
        Map<String, List<PriceLibrary>> priceTrendsGroups = new ArrayList<>(Optional.ofNullable(demandLine.getOuGroupId())
                .map(ouGroupId -> parameter.getRelatedOuGroups()
                        .getOrDefault(ouGroupId, Collections.emptyList())
                        .stream()
                        .map(BaseOuDetailVO::getOuId)
                        .distinct()
                        .collect(Collectors.toList())
                )
                .orElseGet(() -> Collections.singletonList(demandLine.getOrgId())))
                .stream()
                .flatMap(orgId -> {
                            List<PriceLibrary> priceTrends = priceLibraryMapper
                                    .findPriceTrends(FindPriceTrendParameter.builder()
                                            .orgItemCompose(new FindPriceTrendParameter.OrgItemCompose(orgId, demandLine.getItemId()))
                                            .itemDesc(codeSet.contains(demandLine.getCategoryCode()) ? demandLine.getItemName() : null)
                                            .limit(5L)
                                            .build()
                                    );
                            List<PriceLibrary> old = priceLibraryMapper.listPriceLibraryOld(demandLine.getOrgCode(), demandLine.getItemCode(), codeSet.contains(demandLine.getCategoryCode()) ? demandLine.getItemName() : null)
                                    .stream().map(e -> new PriceLibrary().setCreationDate(e.getCreationDate())
                                            .setItemCode(e.getTargetNum())
                                            .setItemDesc(e.getTargetDesc())
                                            .setCeeaOrgCode(e.getOrgCode())
                                            .setCeeaOrgName(e.getOrgCode())
                                            .setVendorCode(e.getVendorCode())
                                            .setVendorName(e.getVendorName())
                                            .setTaxPrice(e.getPrice())
                                            .setCategoryCode(e.getCategoryCode())
                                    ).collect(Collectors.toList());
                            priceTrends
                                    .addAll(old);
                            return priceTrends.stream();
                        }
                )
                .collect(Collectors.groupingBy(x -> dateFormat.format(x.getCreationDate())));


        // 创建 需求行价格趋势
        return priceTrendsGroups.keySet().stream()
                .map(dateStr -> {
                    try {
                        List<PriceLibrary> priceTrendsGroup = priceTrendsGroups.get(dateStr);

                        // 获取 日期
                        Date date = dateFormat.parse(dateStr);

                        // 计算 最低价
                        BigDecimal minTaxPrice = priceTrendsGroup.stream()
                                .map(PriceLibrary::getTaxPrice)
                                .min(BigDecimal::compareTo)
                                .orElseThrow(() -> new BaseException("获取最低价失败。"));

                        // 获取 最低价的价格数据
                        List<PriceLibrary> minPriceTrendsGroup = priceTrendsGroup.stream()
                                .filter(priceTrend -> minTaxPrice.compareTo(priceTrend.getTaxPrice()) == 0)
                                .collect(Collectors.toList());

                        // create.
                        return SourcingDemandLinePriceTrend.builder()
                                .itemId(demandLine.getItemId())
                                .itemCode(demandLine.getItemCode())
                                .itemName(demandLine.getItemName())
                                .taxPrice(minTaxPrice)
                                .creationDate(date)
                                .vendorInfos(minPriceTrendsGroup.stream()
                                        .map(priceTrend -> VendorInfo.builder()
                                                .vendorId(priceTrend.getVendorId())
                                                .vendorCode(priceTrend.getVendorCode())
                                                .vendorName(priceTrend.getVendorName())
                                                .build())
                                        .filter(distinctByKey(VendorInfo::getVendorName))
                                        .collect(Collectors.toList())
                                )
                                .build();

                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .sorted(Comparator.comparing(SourcingDemandLinePriceTrend::getCreationDate))
                .collect(Collectors.toList());
    }


    @Builder
    @Data
    static class GenerateParameter implements Serializable {
        private Map<Long, SourcingDemandLine> winningDemandLines;
        private Map<Long, List<VendorBiddingLine>> winningBiddingLines;
        private Collection<VendorBiddingLine> lastRoundVendorBiddingLines;
        private Map<Long, List<BaseOuDetailVO>> relatedOuGroups;
    }
}
