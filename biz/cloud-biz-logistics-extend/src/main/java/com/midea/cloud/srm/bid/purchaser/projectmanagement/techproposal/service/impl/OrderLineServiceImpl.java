package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.BidingAwardWayEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.exception.BidException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderHeadMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderlinePaymentTermMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.RequirementPricingType;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.enums.PaymentDayEnum;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidOrderLineVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * 供应商投标行表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: tanjl11@meicloud.com
 *  修改日期: 2020-09-08
 *  修改内容:
 *          </pre>
 */
@Service
@Slf4j
public class OrderLineServiceImpl extends ServiceImpl<OrderLineMapper, OrderLine> implements IOrderLineService {

    private final EntityManager<Biding> biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidRequirementLine> bidRequirementLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<OrderHead> orderHeaderDao
            = EntityManager.use(OrderHeadMapper.class);
    private final EntityManager<OrderLine> orderLineDao
            = EntityManager.use(OrderLineMapper.class);
    private final EntityManager<BidVendor> bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<OrderlinePaymentTerm> orderLinePaymentTermDao
            = EntityManager.use(OrderlinePaymentTermMapper.class);

    @Resource
    private IQuoteAuthorizeService quoteAuthorizeService;
    @Resource
    private OrderLinePaymentTermService paymentTermMapper;
    @Resource
    private BaseClient baseClient;


    @Override
    public List<BidOrderLineVO> getRequirementLineByBidingIdAndBidVendorId(Long biddingId, Long bidVendorId) {

        // 获取 投标供应商信息
        BidVendor bidVendor = Optional.ofNullable(bidVendorDao.findById(bidVendorId))
                .orElseThrow(() -> new BaseException("获取投标供应商信息失败。 | bidVendorId: [" + bidVendorId + "]"));

        // 获取 拥有报价权限的需求行ID
        Set<Long> authDemandLineIds = quoteAuthorizeService.findQuoteAuthorizes(biddingId, bidVendor.getVendorId()).stream()
                .filter(authorize -> !"Y".equals(authorize.getForbidden()))
                .map(QuoteAuthorizeVO::getRequirementLineId)
                .collect(Collectors.toSet());
        if (authDemandLineIds.isEmpty())
            return Collections.emptyList();

        // 获取 需求行信息
        List<BidRequirementLine> demandLines = bidRequirementLineDao.findAll(
                Wrappers.lambdaQuery(BidRequirementLine.class)
                        .in(BidRequirementLine::getRequirementLineId, authDemandLineIds)
                        .ne(BidRequirementLine::getQuoteStatus, "N")
        );
        return demandLines.stream()
                .map(line -> {
                    BidOrderLineVO vo = new BidOrderLineVO();
                    BeanUtils.copyProperties(line, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BidOrderLineVO> getWinOrderLineByBidingIdAndVendorId(Long biddingId, Long bidVendorId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingId)
                .map(biddingDao::findById)
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + biddingId + "]"));

        // 获取 该供应商[上一轮]的投标单[头]
        OrderHead orderHeader = orderHeaderDao.findOne(
                Wrappers.lambdaQuery(OrderHead.class)
                        .eq(OrderHead::getBidingId, biddingId)
                        .eq(OrderHead::getBidVendorId, bidVendorId)
                        .eq(OrderHead::getOrderStatus, "SUBMISSION")
                        .eq(OrderHead::getRound, bidding.getCurrentRound() - 1)
        );

        if (orderHeader == null)
            return Collections.emptyList();
        List<OrderLine> orderLines = orderLineDao
                .findAll(Wrappers.lambdaQuery(OrderLine.class)
                        .eq(OrderLine::getOrderHeadId, orderHeader.getOrderHeadId())
                        .eq(OrderLine::getWin, "Y")
                );
        List<Long> orderLineIds = orderLines.stream().map(OrderLine::getOrderLineId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(orderLineIds)) {
            return Collections.emptyList();
        }
        Map<Long, List<OrderlinePaymentTerm>> collect = orderLinePaymentTermDao.findAll(
                Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                        .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)
        ).stream().collect(Collectors.groupingBy(OrderlinePaymentTerm::getOrderLineId));

        // 获取 该供应商上一轮的[中标]投标单[行]
        return
                orderLines.stream().map(line -> {

                    // 获取 付款信息

                    // 创建VO对象
                    BidOrderLineVO vo = new BidOrderLineVO();
                    BeanUtils.copyProperties(line, vo);
                    vo.setPaymentTermList(collect.get(vo.getOrderLineId()));

                    return vo;
                })
                        .collect(Collectors.toList());
    }

    private void getLastPrice(BidOrderLineVO vo) {
        //todo 获取上一轮价格,根据性能问题看看需不需要在报价时加列存储信息
        BigDecimal lastPrice = getOne(Wrappers.lambdaQuery(OrderLine.class)
                .select(OrderLine::getPrice)
                .eq(OrderLine::getOrderLineId, vo.getOrderLineId())
                .eq(Objects.nonNull(vo.getRound()) && vo.getRound() > 0, OrderLine::getRound, vo.getRound())).getPrice();
        vo.setLastPrice(lastPrice);
        BigDecimal currentPrice = vo.getPrice();
        if (Objects.isNull(currentPrice) || Objects.isNull(lastPrice)) {
            vo.setCompareRate(currentPrice.divide(lastPrice, 8, RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100)));
        }
    }

    @Override
    public List<BidOrderLineVO> getOrderLineByBidingIdAndVendorId(Long bidingId, Long vendorId) {
        List<BidOrderLineVO> orderLineVOS = this.getBaseMapper().getOrderLineByBidingIdAndVendorId(bidingId, vendorId);
        orderLineVOS = judgeShowTargetPrice(bidingId, orderLineVOS);
        return orderLineVOS;
    }

    @Override
    public List<BidOrderLineVO> getOrderLineByOrderHeadId(Long orderHeadId) {
        List<BidOrderLineVO> orderLineVOS = this.getBaseMapper().getOrderLineByOrderHeadId(orderHeadId);

        boolean isFirst = false;
        if (CollectionUtils.isNotEmpty(orderLineVOS)) {
            BidOrderLineVO bidOrderLineVO = orderLineVOS.get(0);
            Long bidingId = bidOrderLineVO.getBidingId();
            if (StringUtil.notEmpty(bidingId)) {
                isFirst = bidOrderLineVO.getRound().equals(1);
                orderLineVOS = judgeShowTargetPrice(bidingId, orderLineVOS);
            }
        }
       /* if (!isFirst) {
            orderLineVOS.forEach(e -> getLastPrice(e));
        }*/
        return orderLineVOS;
    }

    @Override
    public List<BidOrderLineVO> getWithStandardOrderInfoByOrderHeadId(Long orderHeadId) {
        List<BidOrderLineVO> orderLineVOS = this.getBaseMapper().getWithStandardOrderInfoByOrderHeadId(orderHeadId);
        Long bidingId = orderLineVOS.get(0).getBidingId();
        orderLineVOS = judgeShowTargetPrice(bidingId, orderLineVOS);
        return orderLineVOS;
    }

    /**
     * 判断查询结果是否展示拦标价
     *
     * @param bidingId
     * @param orderLineVOS
     * @return
     */
    private List<BidOrderLineVO> judgeShowTargetPrice(Long bidingId, List<BidOrderLineVO> orderLineVOS) {
        if(org.springframework.util.CollectionUtils.isEmpty(orderLineVOS)){
            return Collections.emptyList();
        }
        Biding biding = biddingDao.findById(bidingId);
        List<Long> orderLineIds = orderLineVOS.stream().map(BidOrderLineVO::getOrderLineId).collect(Collectors.toList());
        Map<Long, List<OrderlinePaymentTerm>> collect =null;
        if(CollectionUtils.isNotEmpty(orderLineIds)){
            collect= paymentTermMapper.list(Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                    .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)).stream().collect(Collectors.groupingBy(OrderlinePaymentTerm::getOrderLineId));
        }
        orderLineIds = null;
        for (BidOrderLineVO orderLineVO : orderLineVOS) {
            //是否向供应商公开上轮总体排名
            if (YesOrNo.NO.getValue().equals(biding.getPublicTotalRank())) {
                orderLineVO.setRank(null);
            }
            // 向供应商公开上轮最低价
            if (YesOrNo.NO.getValue().equals(biding.getPublicLowestPrice())) {
                orderLineVO.setFinalPrice(null);
            }
            if(!org.springframework.util.CollectionUtils.isEmpty(collect)){
                orderLineVO.setPaymentTermList(collect.get(orderLineVO.getOrderLineId()));
            }
        }
        collect = null;
        return orderLineVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchOrderLines(List<BidOrderLineVO> orderLineVOS, boolean isSubmit,Set<Long> materialIds) {
        boolean flag = false;
        List<OrderLine> listAdd = new ArrayList<>();
        List<OrderLine> listUpdate = new ArrayList<>();
        boolean isModel = false;
        boolean isInPrice = false;
        Map<Long, Set<String>> idCodeMap=null;
        if (isSubmit) {
            idCodeMap= baseClient.queryTaxByItemBatch(materialIds);
            Long bidingId = null;
            for (BidOrderLineVO lineVO : orderLineVOS) {
                if (lineVO.getBidingId() != null) {
                    bidingId = lineVO.getBidingId();
                    break;
                }
            }
            if (bidingId == null) {
                throw new BaseException("保存需求行时获取招标单id失败");
            }
            Biding biding = biddingDao.findById(bidingId);
//            //判断报价单是不是组合,如果是组合就就必须每一项都填
//            boolean isItem = BidingAwardWayEnum.COMBINED_DECISION.getValue().equals(biding.getBidingAwardWay());
//            //部分报价校验及报价不为空时税率必填
//            boolean partTime = Objects.equals(biding.getPartPrice(), YesOrNo.YES.getValue());
//            //判断是不是模型报价
//            OrderHead one = orderHeaderDao.findOne(Wrappers.lambdaQuery(OrderHead.class)
//                    .eq(OrderHead::getBidingId, bidingId).last("limit 1"));
//            isModel = Objects.equals(RequirementPricingType.MODEL_PURCHASER.getCode(), one.getPricingType());
//            isInPrice = Objects.equals(biding.getIsSyncToPriceLibrary(), YesOrNo.YES.getValue());
//            if (isItem) {
//                Map<String, List<BidOrderLineVO>> itemGroup = orderLineVOS.stream().collect(Collectors.groupingBy(BidOrderLineVO::getItemGroup));
//                itemGroup.forEach((k, v) -> {
//                    boolean present = v.stream().filter(e -> e.getPrice() == null).findAny().isPresent();
//                    if (present) {
//                        throw new BaseException("组合报价不允许同一个组合下部分报价");
//                    }
//                });
//            }
//            boolean isFirstRound = biding.getCurrentRound() == null || biding.getCurrentRound() == 1;
//            //如果不允许部分报价且不是第一轮
//            if (!partTime && !isFirstRound) {
//                boolean present = orderLineVOS.stream().filter(e -> e.getPrice() == null).findAny().isPresent();
//                if (present) {
//                    throw new BaseException("本次招标的投标控制不允许部分报价");
//                }
//            }
//            //如果允许报价并且是第一轮，置为Q,弃标
//            if (partTime && isFirstRound) {
//                for (int i = orderLineVOS.size() - 1; i >= 0; i--) {
//                    BidOrderLineVO current = orderLineVOS.get(i);
//                    if (current.getPrice() == null) {
//                        current.setWin("Q");
//                    }
//                }
//            }

        }

        List<Long> orderLineIds = new LinkedList<>();
        List<OrderlinePaymentTerm> paymentTerms = new LinkedList<>();
        //新增
        for (BidOrderLineVO orderLineVO : orderLineVOS) {
            Long id = orderLineVO.getOrderLineId();
            boolean isNew = Objects.isNull(id);
            // 提交时，验证字段
            if (isSubmit) {
                this.validateOrderLine(orderLineVO, isModel, isInPrice);
                if (!Objects.equals(orderLineVO.getWin(), "Q")) {
                    Set<String> codes = idCodeMap.get(orderLineVO.getTargetId());
                    if (!codes.contains(orderLineVO.getTaxKey())&&!Objects.equals(orderLineVO.getTaxKey(),"VAT 0")){
                        throw new BaseException(String.format("物料%s的税率不能选择%s",orderLineVO.getTargetDesc(),orderLineVO.getTaxKey()));
                    }
                    orderLineVO.setOrderStatus(OrderStatusEnum.SUBMISSION.getValue());
                }
            }
            if (isNew) {
                log.info("新增 orderLineVO : \n {}", orderLineVO);
                OrderLine orderLine = new OrderLine();
                BeanCopyUtil.copyProperties(orderLine, orderLineVO);
                id = IdGenrator.generate();
                orderLineVO.setOrderLineId(id);
                orderLine.setOrderLineId(id);
                listAdd.add(orderLine);
            } else {
                log.info("更新 orderLineVO : \n {}", orderLineVO);
                OrderLine orderLine = new OrderLine();
                BeanCopyUtil.copyProperties(orderLine, orderLineVO);
                listUpdate.add(orderLine);
                orderLineIds.add(orderLine.getOrderLineId());
            }


            orderLineIds.add(orderLineVO.getOrderLineId());

            //再重新生成
            List<OrderlinePaymentTerm> paymentTermList = orderLineVO.getPaymentTermList();
            if (paymentTermList != null) {
                for (OrderlinePaymentTerm relate : paymentTermList) {
                    if (Objects.isNull(relate.getPaymentDay())) {
                        if (Objects.equals(relate.getPaymentDayCode(),"00")) {
                            relate.setPaymentDay(0);
                        } else {
                            relate.setPaymentDay(PaymentDayEnum.getDayFromCode(relate.getPaymentDayCode()));
                        }
                    }
                    relate.setOrderLineId(id);
                    relate.setPaymentTermId(IdGenrator.generate());
                    paymentTerms.add(relate);
                }
            }
        }
        if (listAdd.size() > 0) {
            this.saveBatch(listAdd);
        }
        if (listUpdate.size() > 0) {
            this.updateBatchById(listUpdate);
        }
        //3 保存投标行的付款条款
        //先删除已有的
        if(CollectionUtils.isNotEmpty(orderLineIds)){
            paymentTermMapper.remove(Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                    .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)
            );
        }
        if(CollectionUtils.isNotEmpty(paymentTerms)){
            //批量保存
            paymentTermMapper.saveBatch(paymentTerms);
        }

        log.info("数据保存{}", flag);

        return flag;
    }

    protected void validateOrderLine(BidOrderLineVO orderLineVO, Boolean isModel, Boolean isInPrice) {
        //如果是弃标,不校验
        if (Objects.equals(orderLineVO.getWin(), "Q")) {
            return;
        }
        if (CollectionUtils.isEmpty(orderLineVO.getPaymentTermList())) {
            throw new BidException(String.format("物料%s的付款条款不能为空", orderLineVO.getTargetDesc()));
        } else {
            List<OrderlinePaymentTerm> paymentTermList = orderLineVO.getPaymentTermList();
            for (OrderlinePaymentTerm relate : paymentTermList) {
                if (isInPrice && Objects.equals(relate.getPaymentDayCode(), "00")) {
                    throw new BaseException("该单的寻源结果会同步到价格库，付款条款中无法选择预付款");
                }
                if (Objects.isNull(relate.getPaymentDayCode())) {
                    throw new BaseException("账期编码不能为空");
                }
            }
        }

        if (Objects.isNull(orderLineVO.getPrice())) {
            throw new BidException(String.format("物料%s的含税报价不能为空", orderLineVO.getTargetDesc()));
        }

        if ((orderLineVO.getTaxRate() == null || StringUtil.isEmpty(orderLineVO.getTaxKey()))) {
            if (!isModel) {
                throw new BidException(String.format("报价不为空时，物料%s的税率编码、税率值必填", orderLineVO.getTargetDesc()));
            } else {
                orderLineVO.setTaxRate(BigDecimal.ZERO);
                orderLineVO.setTaxKey("");
            }
        }
        if (StringUtil.isEmpty(orderLineVO.getCurrencyType())) {
            throw new BidException(String.format("物料%s的币种不能为空", orderLineVO.getTargetDesc()));
        }
        if(StringUtil.isEmpty(orderLineVO.getMQO())){
            throw new BidException(String.format("物料%s的最小起订量不能为空",orderLineVO.getTargetDesc()));
        }
        if(Objects.isNull(orderLineVO.getDeliverDate())){
            throw new BaseException(String.format("物料%s的承诺交货期不能为空",orderLineVO.getTargetDesc()));
        }
        if(Objects.isNull(orderLineVO.getLeadTime())){
            throw new BaseException(String.format("物料%s的供货周期不能为空",orderLineVO.getTargetDesc()));
        }
    }
}
