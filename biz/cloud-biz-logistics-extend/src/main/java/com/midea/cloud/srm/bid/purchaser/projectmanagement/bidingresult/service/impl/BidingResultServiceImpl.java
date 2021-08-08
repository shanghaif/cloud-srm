package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.mapper.BidResultMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.IBidingResultService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.entity.BidResult;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.AbandonmentBidSupVO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.midea.cloud.common.utils.BeanCopyUtil.getNullPropertyNames;

/**
 * <pre>
 * 投标控制 服务实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020年4月9日 上午16:44:37
 *  修改内容:
 *          </pre>
 */
@Service
public class BidingResultServiceImpl extends ServiceImpl<BidResultMapper, BidResult> implements IBidingResultService {

    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;
    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IBidingResultService iBidingResultService;
    @Autowired
    private OrderLineMapper orderLineMapper;

    /**
     * 返回中标结果
     *
     * @param bidingId
     * @return
     */
    @Override
    @Transactional
    public PageInfo<BidResult> listPageBidingResult(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");

        Biding biding = iBidingService.getById(bidingId);

        //招标结果表已有记录则直接返回
        List<BidResult> list = iBidingResultService.list(new QueryWrapper<>(new BidResult().setBidingId(bidingId)));
        if (CollectionUtils.isNotEmpty(list)) {
            return new PageInfo<BidResult>(list);
        }

        //不是最后一轮或未结束评选则返回空
        if (
                // 没有规定最后一轮
//                !"Y".equals(biding.getFinalRound()) ||
                        !"Y".equals(biding.getEndEvaluation())) {
            return new PageInfo<BidResult>(null);
        }

        List<OrderLine> orderLineList = orderLineMapper.getBidingResultList(bidingId);
        List<BidResult> bidResultList = new ArrayList<>();
        for (OrderLine oldLine : orderLineList) {
            BidResult bidResult = new BidResult();
            BeanUtils.copyProperties(oldLine, bidResult, "comments");
            BidRequirementLine bidRequirementLine = iBidRequirementLineService.getOne(new QueryWrapper<>(
                    new BidRequirementLine().setRequirementLineId(oldLine.getRequirementLineId())));
            BeanUtils.copyProperties(bidRequirementLine, bidResult, "comments");
            //采购组织信息
            try {
                Organization organization = baseClient.getOrganizationByParam(new Organization().
                        setOrganizationId(bidRequirementLine.getOrgId()));
                if (organization != null) {
                    bidResult.setOrgCode(organization.getOrganizationCode());
                }
                //采购分类信息
                PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(bidRequirementLine.getCategoryId()));
                if (purchaseCategory != null) {
                    bidResult.setCategoryCode(purchaseCategory.getCategoryCode());
                }
            } catch (Exception e) {
                log.error("远程调用失败:" + e.getMessage());
                throw new BaseException("远程调用失败");
            }
            bidResult.setPurchaseCategory(bidRequirementLine.getCategoryName());
            //供应商信息
            BidVendor bidVendor = iBidVendorService.getById(oldLine.getBidVendorId());
            CompanyInfo companyInfo = supplierClient.getCompanyInfo(bidVendor.getVendorId());
            bidResult.setVendorId(bidVendor.getVendorId());
            bidResult.setVendorName(companyInfo.getCompanyName());
            bidResult.setVendorCode(companyInfo.getCompanyCode());
            bidResult.setTaxKey(bidRequirementLine.getTaxKey());
            bidResult.setTaxRate(bidRequirementLine.getTaxRate());
            //含税中标价格
            bidResult.setBidPriceIncludingTax(oldLine.getPrice());
            //不含税中标价格
            bidResult.setBidPriceExcludingTax(oldLine.getPrice().divide(BigDecimal.ONE.add(
                    oldLine.getTaxRate().divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_UP)),
                    4, BigDecimal.ROUND_HALF_UP));

            Long id = IdGenrator.generate();
            bidResult.setBidResultId(id).setBidingId(bidingId);
            bidResultList.add(bidResult);
        }
        this.saveBatch(bidResultList);
        return new PageInfo<>(bidResultList);
    }


    @Override
    public void updateBidResultBatchById(List<BidResult> bidResultList) {
        List<BidResult> newBidResultList = new ArrayList<>();
        for (BidResult oldBidResult : bidResultList) {
            BidResult newBidResult = new BidResult();
            newBidResult.setBidResultId(oldBidResult.getBidResultId()).setComments(oldBidResult.getComments()).
                    setBidingProportionQuota(oldBidResult.getBidingProportionQuota());
            newBidResultList.add(newBidResult);
        }
        this.updateBatchById(newBidResultList);
    }


    @Override
    public List<BidResult> getResultByBidingId(Long bidingId) {

        Biding biding = iBidingService.getById(bidingId);
        //公示结果后供应商可看见中标供应商名称 VISIBLE_WIN_VENDOR
        if (YesOrNo.NO.getValue().equals(biding.getVisibleWinVendor())) {
            return Collections.EMPTY_LIST;
        }
        List<BidResult> results = this.list(new QueryWrapper<>(new BidResult().setBidingId(bidingId)));
        for (int i = results.size() - 1; i >= 0; i++) {
            BidResult result = results.get(i);
            //允许供应商查看最终排名结果 VISIBLE_TOTAL_RANKING
            if (YesOrNo.NO.getValue().equals(biding.getVisibleRankResult())) {
                result.setRank(null);
            }
            //允许供应商查看中标价 VISIBLE_FINAL_PRICE
            if (YesOrNo.NO.getValue().equals(biding.getVisibleFinalPrice())) {
                //含税中标价
                result.setBidPriceIncludingTax(null);
            }

        }


        return results;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfo<EvaluationResult> getNewResultByBidingId(Map<String, Object> paramMap) {
        Long bidingId = (Long) paramMap.get("bidingId");
        Integer pageSize = (Integer) paramMap.get("pageSize");
        Integer pageNum = (Integer) paramMap.get("pageNum");
        pageSize = 9999;
        Biding biding = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class).select(
                Biding::getCurrentRound,
                Biding::getVisibleWinVendor,
                Biding::getVisibleRankResult,
                Biding::getVisibleFinalPrice
        ).eq(Biding::getBidingId, bidingId));
        if (Objects.isNull(biding)) {
            throw new BaseException("该招标单不存在");
        }

        //公示结果后供应商可看见中标供应商名称 VISIBLE_WIN_VENDOR
        if (YesOrNo.NO.getValue().equals(biding.getVisibleWinVendor())) {
            return new PageInfo<>(Collections.EMPTY_LIST);
        }
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        Boolean isVendor = Objects.equals(user.getUserType(), UserType.VENDOR.name());
        BidVendor one = iBidVendorService.getOne(Wrappers.lambdaQuery(BidVendor.class)
                .select(BidVendor::getBidVendorId, BidVendor::getVendorId)
                .eq(BidVendor::getBidingId, bidingId)
                .eq(BidVendor::getVendorId, user.getCompanyId())
        );
        if (Objects.isNull(one)) {
            return new PageInfo<>(Collections.EMPTY_LIST);
        }
        Long bidVendorId = isVendor ? one.getBidVendorId() : null;
        PageUtil.startPage(pageNum, pageSize);
        List<OrderLine> orderLines = orderLineMapper.selectList(Wrappers.lambdaQuery(OrderLine.class)
                .eq(OrderLine::getBidingId, bidingId)
                .eq(OrderLine::getRound, biding.getCurrentRound())
                .nested(e -> e.eq(OrderLine::getSelectionStatus, SelectionStatusEnum.WIN.getValue())
                        .or()
                        .nested(q -> q.eq(OrderLine::getWin, "Y").eq(isVendor, OrderLine::getBidVendorId, bidVendorId))
                )
        );
        if(CollectionUtils.isEmpty(orderLines)){
            return new PageInfo<>(Collections.EMPTY_LIST);
        }
        Set<Long> requirementId = orderLines.stream().map(OrderLine::getRequirementLineId).collect(Collectors.toSet());

        List<BidRequirementLine> bidRequirementLines = iBidRequirementLineService.listByIds(requirementId);
        List<EvaluationResult> results = new LinkedList<>();
        for (OrderLine orderLine : orderLines) {
            for (BidRequirementLine demandLine : bidRequirementLines) {
                if (Objects.equals(demandLine.getRequirementLineId(), orderLine.getRequirementLineId())) {
                    EvaluationResult vo = new EvaluationResult();
                    BeanUtils.copyProperties(demandLine, vo, getNullPropertyNames(demandLine));
                    BeanUtils.copyProperties(orderLine, vo, getNullPropertyNames(orderLine));
                    //允许供应商查看最终排名结果 VISIBLE_TOTAL_RANKING
                    if (YesOrNo.NO.getValue().equals(biding.getVisibleRankResult())) {
                        vo.setRank(null);
                    }
                    //允许供应商查看中标价 VISIBLE_FINAL_PRICE
                    if (YesOrNo.NO.getValue().equals(biding.getVisibleFinalPrice())) {
                        //含税中标价
                        vo.setPrice(null);
                    }
                    results.add(vo);
                    break;
                }
            }
        }
        return new PageInfo<>(results);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AbandonmentBidSupVO> getAbandonBidSup(Long bidingId) {
        List<AbandonmentBidSupVO> result = new LinkedList<>();
        /*弃标供应商，先根据biding去找对应的报名标bidvendor*/
        Map<Long, BidVendor> signUpVendorInfo = iBidVendorService.list(Wrappers
                .lambdaQuery(BidVendor.class)
                .select(BidVendor::getVendorName, BidVendor::getBidVendorId)
                .eq(BidVendor::getBidingId, bidingId))
                .stream().collect(Collectors.toMap(BidVendor::getBidVendorId, Function.identity()));
        /*获取报价行中*/
        Map<Long, Set<Long>> requirementLineIdsAndBidVendorIdInOrderLines = orderLineMapper
                .getDistinctColumnByCondition(
                        Wrappers.lambdaQuery(OrderLine.class)
                                .select(OrderLine::getRequirementLineId, OrderLine::getBidVendorId)
                                .eq(OrderLine::getBidingId, bidingId))
                .stream().collect(Collectors.groupingBy(OrderLine::getRequirementLineId
                        , Collectors.mapping(OrderLine::getBidVendorId, Collectors.toSet())));
        //目前的需求行
        List<BidRequirementLine> requirementLines = iBidRequirementLineService.list(Wrappers
                .lambdaQuery(BidRequirementLine.class)
                .select(BidRequirementLine::getItemGroup, BidRequirementLine::getTargetDesc,
                        BidRequirementLine::getRequirementLineId,
                        BidRequirementLine::getOrgName,
                        BidRequirementLine::getTargetNum)
                .eq(BidRequirementLine::getBidingId, bidingId));
        Set<Long> signUpBidVendorIds = signUpVendorInfo.keySet();
        for (BidRequirementLine requirementLine : requirementLines) {
            Set<Long> currentRequirementLineBidVendorIds = requirementLineIdsAndBidVendorIdInOrderLines.get(requirementLine.getRequirementLineId());
            signUpBidVendorIds
                    .stream()
                    .filter(e -> !currentRequirementLineBidVendorIds.contains(e))
                    .forEach(e -> {
                        AbandonmentBidSupVO vo = BeanCopyUtil.copyProperties(requirementLine, AbandonmentBidSupVO::new);
                        vo.setSupplierName(signUpVendorInfo.get(e).getVendorName());
                        result.add(vo);
                    });
        }
        return result;
    }

}
