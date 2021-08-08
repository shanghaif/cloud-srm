package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidIntelligentRecommendVendorService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.VendorOrgCateRelClient;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.FindVendorPricingPermissionParameter;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.midea.cloud.common.utils.Functions.distinctByKey;

/**
 * Implement of {@link IBidIntelligentRecommendVendorService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IBidIntelligentRecommendVendorServiceImpl implements IBidIntelligentRecommendVendorService {

    private final EntityManager<Biding>             biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidRequirementLine> requirementLineDao
            = EntityManager.use(BidRequirementLineMapper.class);

    @Resource
    private VendorOrgCateRelClient  vendorOrgCateRelClient;
    @Resource
    private BaseClient              baseClient;


    @Override
    public List<CompanyInfo> findVendors(FindVendorPricingPermissionParameter parameter) {
        return findInitialVendorPricingPermissions(parameter).stream()
                .map(VendorPricingPermission::getCompanyInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<VendorPricingPermission> findInitialVendorPricingPermissions(FindVendorPricingPermissionParameter parameter) {
        Assert.notNull(parameter.getBidding(), "招标单ID不能为空。");

        // 获取 寻源单
        Biding bidding = Optional.ofNullable(parameter.getBidding())
                .map(biddingDao::findById)
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + parameter.getBidding() + "]"));

        // 获取 当前寻源单所有需求行
        List<BidRequirementLine> demandLines = requirementLineDao.findAll(
                Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, bidding.getBidingId())
        );
        if (CollectionUtils.isEmpty(demandLines))
            return Collections.emptyList();

        Long[] vendorIds = parameter.getVendorIds();
        String bidingAwardWay = bidding.getBidingAwardWay();
        return getVendorPricingPermissions(demandLines, vendorIds, bidingAwardWay);
    }


    /**
     * 根据模板获取供应商集合
     * @param form
     * @return
     */
    @Override
    public List<CompanyInfo> findVendors(SourceForm form) {
        Collection<BidRequirementLine> demandLines = form.getDemandLines();
        Biding bidding = form.getBidding();
        return getVendorPricingPermissions(demandLines,null,bidding.getBidingAwardWay()).stream()
                .map(VendorPricingPermission::getCompanyInfo)
                .collect(Collectors.toList());
    }

    /**
     * 针对OU组[拆分]寻源需求行
     *
     * @param demandLines 寻源需求行
     * @return [拆分]后的寻源需求行
     */
    protected List<BidRequirementLine> splitDemandLinesWithOuGroup(Collection<BidRequirementLine> demandLines) {

        // 预查询 OU组所对应的组织
        List<Long> ouIds = demandLines.stream()
                .filter(line -> line.getOuId() != null)
                .map(BidRequirementLine::getOuId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, List<BaseOuDetailVO>> ouGroups = ouIds.isEmpty()
                ? Collections.emptyMap()
                : baseClient.queryOuInfoDetailByIds(ouIds).stream()
                .collect(Collectors.toMap(BaseOuGroupDetailVO::getOuGroupId, BaseOuGroupDetailVO::getDetails));


        // 针对OU组拆分寻源需求行
        return demandLines.stream().flatMap(line -> {

            // 非OU组报价，则无需拆分
            if (line.getOuId() == null)
                return Stream.of(line);

            // OU组报价，拆分
            return ouGroups.getOrDefault(line.getOuId(), Collections.emptyList()).stream()
                    .map(org -> {
                        BidRequirementLine clone = new BidRequirementLine();
                        BeanUtils.copyProperties(line, clone);
                        clone.setOrgId(org.getOuId());
                        clone.setOrgName(org.getOuName());
                        return clone;
                    });

        }).collect(Collectors.toList());
    }

    /**
     * 针对OU组[合并]寻源需求行
     *
     * @param splitDemandLines [拆分]后的寻源需求行
     * @return [合并]后的寻源需求行
     */
    protected List<BidRequirementLine> mergeDemandLinesWithOuGroup(List<BidRequirementLine> splitDemandLines) {
        return splitDemandLines.stream()
                .filter(distinctByKey(BidRequirementLine::getRequirementLineId))    // 合并
                .map(line -> {
                    BidRequirementLine clone = new BidRequirementLine();
                    BeanUtils.copyProperties(line, clone);
                    if (clone.getOuId() != null) {
                        clone.setOrgId(null);
                        clone.setOrgName(null);
                    }
                    return clone;
                })
                .collect(Collectors.toList());
    }


    /**
     *
     * @param demandLines 需求行
     * @param vendorIds 待查询的供应商id
     * @param bidingAwardWay 招标单的类型
     * @return
     */
    @Override
    public List<VendorPricingPermission> getVendorPricingPermissions(Collection<BidRequirementLine> demandLines, Long[] vendorIds, String bidingAwardWay) {
        // 针对OU组[拆分]寻源需求行
        List<BidRequirementLine> splitDemandLines = this.splitDemandLinesWithOuGroup(demandLines);


        // 获取 对应组织品类为合格的供应商
        List<VendorOrgCateRelsVO> vendorOrgCateRels = vendorOrgCateRelClient.findValidVendorOrgCateRels(
                FindVendorOrgCateRelParameter.builder()
                        .vendorIds(vendorIds)
                        .orgCateComposes(
                                splitDemandLines.stream()
                                        .map(line -> new FindVendorOrgCateRelParameter.OrgCateCompose(line.getOrgId(), line.getCategoryId()))
                                        .collect(Collectors.toList())
                        ).isBargain(false)
                        .build()
        );


        // 对象转换 & 合并OU组报价的需求行
        return vendorOrgCateRels.stream()
                .map(rel -> {

                    // 获取 [单个]供应商的初始报价权限集
                    List<BidRequirementLine> permissions = this.findPerInitialVendorPricingPermissions(
                            rel,
                            Objects.equals(bidingAwardWay, BiddingAwardWay.COMBINED_DECISION), demandLines,
                            splitDemandLines
                    );

                    // 对象转换
                    return VendorPricingPermission.builder()
                            .companyInfo(rel.getCompanyInfo())
                            .requirementLines(permissions)
                            .build();

                })
                .filter(permission -> !CollectionUtils.isEmpty(permission.getRequirementLines()))   // 过滤掉没有权限的供应商
                .collect(Collectors.toList());
    }

    /**
     * 获取 [单个]供应商的初始报价权限集
     *
     * @param rel               供应商组织品类关系
     * @param isCombinedDecision   是否组合决标
     * @param demandLines       [拆分]前的寻源需求行
     * @param splitDemandLines  [拆分]后的寻源需求行
     * @return [单个]供应商的初始报价权限集
     */
    protected List<BidRequirementLine> findPerInitialVendorPricingPermissions(VendorOrgCateRelsVO rel,
                                                                              Boolean isCombinedDecision, Collection<BidRequirementLine> demandLines,
                                                                              Collection<BidRequirementLine> splitDemandLines) {

        // 按[组合分组] 需求行
        Map<String, List<BidRequirementLine>> itemGroupDemandLines = demandLines.stream()
                .filter(demandLine -> StringUtils.hasText(demandLine.getItemGroup()))
                .collect(Collectors.groupingBy(BidRequirementLine::getItemGroup));


        // 按[组织 + 品类]分组 需求行
        Map<Long, Map<Long, List<BidRequirementLine>>> orgCateSplitDemandLines = splitDemandLines.stream()
                .collect(Collectors.groupingBy(
                        BidRequirementLine::getOrgId,
                        Collectors.groupingBy(BidRequirementLine::getCategoryId)
                ));



        // 获取 满足供应商组织品类关系的寻源需求行
        List<BidRequirementLine> permissions = this.mergeDemandLinesWithOuGroup(
                rel.getOrgCategories().stream()
                        .filter(orgCategory -> orgCateSplitDemandLines.containsKey(orgCategory.getOrgId()))
                        .flatMap(orgCategory -> orgCateSplitDemandLines.get(orgCategory.getOrgId())
                                .getOrDefault(orgCategory.getCategoryId(), Collections.emptyList())
                                .stream()
                        )
                        .collect(Collectors.toList())
        );


        // 非[组合决标]，需判断是否同一组合分组全部有权限
        if (!isCombinedDecision)
            return permissions;


        // 按[组合]分组 满足供应商组织品类关系的寻源需求行
        Map<String, List<BidRequirementLine>> itemGroupPermissions = permissions.stream()
                .filter(demandLine -> StringUtils.hasText(demandLine.getItemGroup()))
                .collect(Collectors.groupingBy(BidRequirementLine::getItemGroup));

        // [组合决标]，需判断是否拥有同一组合分组的所有权限
        return itemGroupPermissions.keySet().stream()
                .filter(itemGroupPermissions::containsKey)
                .filter(itemGroupKey -> {

                    // 获取[当前组合]的满足供应商组织品类关系的寻源需求行[ID]
                    List<Long> itemGroupPermissionIds = itemGroupPermissions.get(itemGroupKey).stream()
                            .map(BidRequirementLine::getRequirementLineId)
                            .collect(Collectors.toList());

                    // 获取[当前组合]的寻源需求行[ID]
                    List<Long> itemGroupDemandLineIds = itemGroupDemandLines.get(itemGroupKey).stream()
                            .map(BidRequirementLine::getRequirementLineId)
                            .collect(Collectors.toList());

                    // 校验需求行是否完全一致
                    return itemGroupPermissionIds.size() == itemGroupDemandLineIds.size()
                            && itemGroupPermissionIds.containsAll(itemGroupDemandLineIds);

                })
                .flatMap(itemGroupKey -> itemGroupPermissions.get(itemGroupKey).stream())
                .collect(Collectors.toList());
    }



}
