package com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.BidingAwardWayEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidIntelligentRecommendVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.workflow.BidInitProjectFlow;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.mapper.QuoteAuthorizeMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.FindVendorPricingPermissionParameter;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.entity.QuoteAuthorize;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay.COMBINED_DECISION;

/**
 * <pre>
 * 报价权限
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月23日 下午7:19:01
 *  修改内容:
 *          </pre>
 */
@Service
public class QuoteAuthorizeServiceImpl extends ServiceImpl<QuoteAuthorizeMapper, QuoteAuthorize> implements IQuoteAuthorizeService {

    private final EntityManager<Biding> biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidVendor> bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<BidRequirementLine> bidRequirementLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<QuoteAuthorize> quoteAuthorizeDao
            = EntityManager.use(QuoteAuthorizeMapper.class);

    @Resource
    private IBidIntelligentRecommendVendorService bidIntelligentRecommendVendorService;


    @Override
    public List<QuoteAuthorizeVO> findQuoteAuthorizes(Long biddingId, Long vendorId) {
        Assert.notNull(biddingId, "投标单ID不允许为空。");
        Assert.notNull(vendorId, "供应商ID不允许为空。");

        // 获取 供应商
        BidVendor bidVendor = bidVendorDao.findOne(
                Wrappers.lambdaQuery(BidVendor.class)
                        .eq(BidVendor::getBidingId, biddingId)
                        .eq(BidVendor::getVendorId, vendorId)
        );

        // 获取 被禁止报价的需求行
        Map<Long, QuoteAuthorize> forbiddenAuths = Optional.ofNullable(bidVendor)
                .map(BidVendor::getBidVendorId)
                .map(bidVendorId -> quoteAuthorizeDao.findAll(
                        Wrappers.lambdaQuery(QuoteAuthorize.class)
                                .eq(QuoteAuthorize::getBidVendorId, bidVendorId))
                )
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(QuoteAuthorize::getRequirementLineId, x -> x, (x, y) -> x));

        // 获取 默认允许报价的需求行
        List<BidRequirementLine> defaultAllowAuths = bidIntelligentRecommendVendorService
                .findInitialVendorPricingPermissions(
                        FindVendorPricingPermissionParameter.builder()
                                .bidding(biddingId)
                                .vendorIds(new Long[]{vendorId})
                                .build())
                .stream()
                .findAny()
                .map(VendorPricingPermission::getRequirementLines)
                .orElse(Collections.emptyList());


        // 填充报价权限
        return defaultAllowAuths.stream()
                .map(auth -> new QuoteAuthorizeVO()
                        .setCategoryName(auth.getCategoryName())
                        .setForbidden(forbiddenAuths.containsKey(auth.getRequirementLineId()) ? "Y" : "N")
                        .setItemGroup(auth.getItemGroup())
                        .setRequirementLineId(auth.getRequirementLineId())
                        .setTargetNum(auth.getTargetNum())
                        .setTargetDesc(auth.getTargetDesc())
                        .setOrgId(auth.getOrgId())
                        .setOrgCode(auth.getOrgCode())
                        .setOrgName(auth.getOrgName())
                        .setInvId(auth.getInvId())
                        .setInvCode(auth.getInvCode())
                        .setInvName(auth.getInvName())
                        .setOuId(auth.getOuId())
                        .setOuCode(auth.getOuNumber())
                        .setOuName(auth.getOuName())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void saveQuoteAuthorize(Long bidVendorId, List<QuoteAuthorizeVO> list) {
        Assert.notEmpty(list, "报价权限列表不能为空");
        Assert.notNull(bidVendorId, "供应商ID不能为空");
        BidVendor vendor = bidVendorDao.findById(bidVendorId);
        Assert.notNull(vendor, "供应商ID不存在");
        List<BidRequirementLine> requirementLineList = bidRequirementLineDao.findAll(new QueryWrapper<>(new BidRequirementLine().setBidingId(vendor.getBidingId())));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        Map<String, Map<Long, BidRequirementLine>> requirementLineItemGroupMap = requirementLineList.stream().collect(Collectors.groupingBy(BidRequirementLine::getItemGroup, Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity())));
        Biding biding = biddingDao.findById(vendor.getBidingId());
        Set<Long> lineIdSet = new HashSet<Long>();
        for (QuoteAuthorizeVO vo : list) {
            if ("Y".equals(vo.getForbidden())) {
                // 组合决标时，根据组合禁止报价权限
                if (BidingAwardWayEnum.get(biding.getBidingAwardWay()) == BidingAwardWayEnum.COMBINED_DECISION) {
                    BidRequirementLine requirementLine = requirementLineMap.get(vo.getRequirementLineId());
                    if (requirementLine != null) {
                        lineIdSet.addAll(requirementLineItemGroupMap.get(requirementLine.getItemGroup()).keySet());
                    }
                } else {
                    lineIdSet.add(vo.getRequirementLineId());
                }
            }
        }
        this.remove(new QueryWrapper<QuoteAuthorize>(new QuoteAuthorize().setBidVendorId(vendor.getBidVendorId())));
        List<QuoteAuthorize> saveList = new ArrayList<QuoteAuthorize>();
        for (Long lineId : lineIdSet) {
            saveList.add(new QuoteAuthorize().setQuoteAuthorizeId(IdGenrator.generate()).setBidingId(vendor.getBidingId()).setBidVendorId(vendor.getBidVendorId()).setRequirementLineId(lineId));
        }
        if (saveList.size() > 0) {
            this.saveBatch(saveList);
        }
    }

    @Override
    public void saveQuoteAuthorize(List<BidVendor> bidVendorList) {
        if (bidVendorList != null && bidVendorList.size() > 0) {
            Long bidingId = bidVendorList.get(0).getBidingId();
            Biding biding = biddingDao.findById(bidingId);
            List<BidRequirementLine> requirementLineList = bidRequirementLineDao.findAll(new QueryWrapper<>(new BidRequirementLine().setBidingId(bidingId)));
            Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
            requirementLineList.forEach(e -> {
                if (StringUtils.isEmpty(e.getItemGroup())) {
                    e.setItemGroup(e.getRequirementLineId().toString());
                }
            });
            Map<String, Map<Long, BidRequirementLine>> requirementLineItemGroupMap = requirementLineList.stream().collect(Collectors.groupingBy(BidRequirementLine::getItemGroup, Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity())));
            Set<Long> removeBidVendorIdSet = new HashSet<Long>();
            Map<Long, Set<Long>> saveMap = new HashMap<Long, Set<Long>>();
            for (BidVendor vendor : bidVendorList) {
                // 报价权限列表为空，不更新
                if (vendor.getQuoteAuthorizeList() == null || vendor.getQuoteAuthorizeList().size() == 0) {
                    continue;
                }
                removeBidVendorIdSet.add(vendor.getBidVendorId());
                Set<Long> lineIdSet = new HashSet<Long>();
                for (QuoteAuthorizeVO vo : vendor.getQuoteAuthorizeList()) {
                    if ("Y".equals(vo.getForbidden())) {
                        // 组合决标时，根据组合禁止报价权限
                        if (BidingAwardWayEnum.get(biding.getBidingAwardWay()) == BidingAwardWayEnum.COMBINED_DECISION) {
                            BidRequirementLine requirementLine = requirementLineMap.get(vo.getRequirementLineId());
                            if (requirementLine != null) {
                                lineIdSet.addAll(requirementLineItemGroupMap.get(requirementLine.getItemGroup()).keySet());
                            }
                        } else {
                            lineIdSet.add(vo.getRequirementLineId());
                        }
                    }
                }
                if (lineIdSet.size() > 0) {
                    saveMap.put(vendor.getBidVendorId(), lineIdSet);
                }
            }
            if (removeBidVendorIdSet.size() > 0) {
                this.remove(new QueryWrapper<QuoteAuthorize>().in("BID_VENDOR_ID", removeBidVendorIdSet));
            }
            List<QuoteAuthorize> saveList = new ArrayList<QuoteAuthorize>();
            for (Long bidVendorId : saveMap.keySet()) {
                for (Long lineId : saveMap.get(bidVendorId)) {
                    saveList.add(new QuoteAuthorize().setQuoteAuthorizeId(IdGenrator.generate()).setBidingId(bidingId).setBidVendorId(bidVendorId).setRequirementLineId(lineId));
                }
            }
            if (saveList.size() > 0) {
                this.saveBatch(saveList);
            }
        }
    }

    @Override
    public void checkQuoteAuthorize(Long biddingId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingDao.findById(biddingId))
                .orElseThrow(() -> new BaseException("招标单获取失败。 | bidding: [" + biddingId + "]"));


        // 非[组合决标]，无需额外校验
        if (!COMBINED_DECISION.equals(BiddingAwardWay.get(bidding.getBidingAwardWay())))
            return;


        // fn - 获取 组合Key
        Function<BidRequirementLine, String> getItemGroupKeyFn = demandLine -> StringUtils.isEmpty(demandLine.getItemGroup())
                ? demandLine.getRequirementLineId().toString()
                : demandLine.getItemGroup();


        // 获取 需求行
        Map<Long, BidRequirementLine> demandLines = bidRequirementLineDao
                .findAll(Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, bidding.getBidingId()))
                .stream()
                .collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, x -> x));
        // 按[组合] 分组
        Map<String, List<BidRequirementLine>> demandLinesGroups = demandLines.values().stream()
                .collect(Collectors.groupingBy(getItemGroupKeyFn));


        // 获取 报价权限
        List<QuoteAuthorize> quoteAuthorizes = quoteAuthorizeDao.findAll(
                Wrappers.lambdaQuery(QuoteAuthorize.class)
                        .eq(QuoteAuthorize::getBidingId, bidding.getBidingId())
        );
        // 设置 报价权限关联的组合
        quoteAuthorizes.forEach(quoteAuthorize -> {

            // 获取报价权限对应的需求行
            BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(quoteAuthorize.getRequirementLineId()))
                    .orElseThrow(() -> new BaseException("需求行获取失败。 | requirementLineId: [" + quoteAuthorize.getRequirementLineId() + "]"));

            // 设置 组合信息
            quoteAuthorize.setItemGroup(getItemGroupKeyFn.apply(demandLine));
        });
        // 按[组合] 分组
        Map<String, List<QuoteAuthorize>> quoteAuthorizesGroups = quoteAuthorizes.stream()
                .collect(Collectors.groupingBy(QuoteAuthorize::getItemGroup));


        // 组合决标时，同一组合的报价权限必须一致
        quoteAuthorizesGroups.forEach((itemGroup, quoteAuthorizesGroup) -> {
            boolean isCorrect = demandLinesGroups.get(itemGroup).stream()
                    .allMatch(demandLine -> quoteAuthorizesGroup.stream()
                            .anyMatch(quoteAuthorize -> demandLine.getRequirementLineId().equals(quoteAuthorize.getRequirementLineId()))
                    );
            if (!isCorrect)
                throw new BaseException("同一组合的报价权限必须一致。 | 组合: [" + itemGroup + "]");
        });

    }

}
