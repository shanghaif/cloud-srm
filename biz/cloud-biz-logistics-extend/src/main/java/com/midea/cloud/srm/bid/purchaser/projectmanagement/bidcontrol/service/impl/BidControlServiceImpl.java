package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidcontrol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.BidConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidingScope;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.projectlist.service.IBidSignUpService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidcontrol.mapper.BidControlMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidcontrol.service.IBidControlService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidcontrol.service.IRoundChangeService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.IRoundService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.vo.BidControlListVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.vo.BidControlTopInfoVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
 *  修改人:
 *  修改日期: 2020年4月9日 上午16:44:37
 *  修改内容:
 *          </pre>
 */
@Service
public class BidControlServiceImpl implements IBidControlService {

    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private IBidSignUpService iBidSignUpService;
    @Autowired
    private IRoundService iRoundService;
    @Autowired
    private IOrderHeadService iOrderHeadService;
    @Autowired
    private IRoundChangeService iRoundChangeService;
    @Autowired
    private BidControlMapper bidControlMapper;
    @Autowired
    private BidRequirementLineMapper requirementLineMapper;
    @Autowired
    private OrderLineMapper orderLineMapper;


    @Override
    public PageInfo<BidControlListVO> listPageBidControl(Long biddingId, Integer pageNum, Integer pageSize) {
        return new PageInfo<>(this.listPageBidControls(biddingId, pageNum, pageSize));
    }

    protected List<BidControlListVO> listPageBidControls(Long biddingId, Integer pageNum, Integer pageSize) {

        // 设置 分页查询
        PageUtil.startPage(pageNum, pageSize);

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingId)
                .map(iBidingService::getById)
                .orElseThrow(() -> new BaseException("获取招标项目失败。 | biddingId: [" + biddingId + "]"));

        // 未开始首轮投标，返回空
        if (bidding.getCurrentRound() == null)
            return Collections.emptyList();

        // 获取 当前轮次的供应商报价信息
        Map<Long, OrderHead> currentRoundOrderHeaders = iOrderHeadService
                .list(Wrappers.lambdaQuery(OrderHead.class)
                        .eq(OrderHead::getBidingId, biddingId)
                        .eq(OrderHead::getRound, bidding.getCurrentRound())
                        .and(wrapper -> wrapper.eq(OrderHead::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue()).
                                or().eq(OrderHead::getOrderStatus, OrderStatusEnum.WITHDRAW.getValue()))
                )
                .stream()
                .collect(Collectors.toMap(OrderHead::getBidVendorId, x -> x, (x, y) -> x));


//        // 若是邀请招标方式，需将本轮需投标的供应商都显示在投标表中
//        if (BidingScope.INVITE_TENDER.getValue().equals(bidding.getBidingScope())) {
//
//            // 首轮查询已报名的供应商
//            if (bidding.getCurrentRound() == 1) {
//                return bidControlMapper.listBidControlFirstRound(biddingId, SignUpStatus.SIGNUPED.getValue());
//            }
//            // 若不是首轮,则查询上一轮入围的供应商 & 设置当前轮次信息
//            else {
//                return bidControlMapper.listBidControl(biddingId, bidding.getCurrentRound() - 1)
//                        .stream()
//                        .peek(vo -> {
//                            // 获取当前供应商当前轮次的投标信息
//                            OrderHead currentRoundOrderHeader = currentRoundOrderHeaders.get(vo.getBidVendorId());
//                            if (currentRoundOrderHeader == null) {
//                                vo.setRound(bidding.getCurrentRound());
//                                vo.setOrderStatus(null);
//                                vo.setIsProxyBidding(null);
//                            }
//                            else {
//                                vo.setRound(bidding.getCurrentRound());
//                                vo.setOrderStatus(currentRoundOrderHeader.getOrderStatus());
//                                vo.setIsProxyBidding(currentRoundOrderHeader.getIsProxyBidding());
//                            }
//                        })
//                        .collect(Collectors.toList());
//            }
//
//        }
//        // 公开招标方式，只需查询已起草/提交投标单的
//        else {
//
//            // 获取 投标供应商[集]
//            Map<Long, BidVendor> bidVendors = iBidVendorService
//                    .list(Wrappers.lambdaQuery(BidVendor.class)
//                            .eq(BidVendor::getBidingId, bidding.getBidingId())
//                    )
//                    .stream()
//                    .collect(Collectors.toMap(BidVendor::getBidVendorId, x -> x));
//
//            return currentRoundOrderHeaders.values().stream()
//                    .map(currentRoundOrderHeader -> {
//
//                        // 获取 投标供应商
//                        BidVendor bidVendor = Optional.ofNullable(bidVendors.get(currentRoundOrderHeader.getBidVendorId()))
//                                .orElseThrow(() -> new BaseException("获取投标供应商失败。 | bidVendorId: [" + currentRoundOrderHeader.getBidVendorId() + "]"));
//
//                        // 对象转换
//                        return BidControlListVO.builder()
//                                .bidVendorId(bidVendor.getBidVendorId())
//                                .vendorCode(bidVendor.getVendorCode())
//                                .vendorName(bidVendor.getVendorName())
//                                .linkManName(bidVendor.getLinkManName())
//                                .phone(bidVendor.getPhone())
//                                .email(bidVendor.getEmail())
//                                .round(currentRoundOrderHeader.getRound())
//                                .orderStatus(currentRoundOrderHeader.getOrderStatus())
//                                .isProxyBidding(currentRoundOrderHeader.getIsProxyBidding())
//                                .createdBy(currentRoundOrderHeader.getCreatedBy())
//                                .lastUpdatedBy(currentRoundOrderHeader.getLastUpdatedBy())
//                                .lastUpdateDate(currentRoundOrderHeader.getLastUpdateDate())
//                                .build();
//                    })
//                    .collect(Collectors.toList());
//        }
        return null;

    }

    @Override
    public BidControlTopInfoVO getTopInfo(Long bidingId) {
        BidControlTopInfoVO bidControlTopInfoVO = new BidControlTopInfoVO();
        Biding biding = iBidingService.getById(bidingId);
        int round = biding.getCurrentRound() == null ? 0 : biding.getCurrentRound();
        int currentRoundSupplierCount = 0;

        //首轮则统计供应商报名总数
        if (round <= 1) {
            currentRoundSupplierCount = iBidSignUpService.count(new QueryWrapper<>(new SignUp().
                    setBidingId(bidingId).setSignUpStatus(SignUpStatus.SIGNUPED.getValue())));
        } else {
            //非首轮则统计上一轮入围的供应商(只要有一种物料入围则算作该供应商入围)
            currentRoundSupplierCount = bidControlMapper.countVendor(bidingId);
        }

        int submitSupplierCount = iOrderHeadService.count(new QueryWrapper<>(new OrderHead().
                setBidingId(bidingId).setRound(biding.getCurrentRound()).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));

        Round currentRound = iRoundService.getOne(new QueryWrapper<>(new Round().
                setBidingId(bidingId).setRound(biding.getCurrentRound())));

        if (currentRound != null) {
            bidControlTopInfoVO.setEndTime(currentRound.getEndTime());
        }

        bidControlTopInfoVO.setCurrentRoundSupplierCount(currentRoundSupplierCount);
        bidControlTopInfoVO.setSubmitSupplierCount(submitSupplierCount);

        return bidControlTopInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startBiding(Long bidingId, Date endTime) {
        //如果是首轮的情况（轮次表为空）,判断是否有已报名的供应商
        List<Round> roundList = iRoundService.list(new QueryWrapper<>(new Round().setBidingId(bidingId)));
        Biding biding = iBidingService.getById(bidingId);
        Integer currentRoundCount = biding.getCurrentRound();
        int currentRound = (currentRoundCount == null ? 0 : currentRoundCount);
       /* if (currentRound == 0) {
            int joinCount = iBidVendorService.count(new QueryWrapper<>(new BidVendor().setBidingId(bidingId).setJoinFlag("Y")));
            if (joinCount <= 0) {
                throw new BaseException(LocaleHandler.getLocaleMsg("首轮无供应商报名,不能发起投标"));
            }
        }*/
        //如果第一轮没有需求行报名,该需求行报价状态改改为N
        if (currentRound == 1) {
            handleNoSupplierQuote(bidingId);
        }
        //检查要发起的轮次在轮次表是否已存在
        Round checkRound = iRoundService.getOne(new QueryWrapper<>(new Round().setBidingId(bidingId).setRound(currentRound + 1)));
        if (checkRound != null) {
            throw new BaseException(LocaleHandler.getLocaleMsg("已发起下一轮次投标，请勿重复操作"));
        }

        if (CollectionUtils.isNotEmpty(roundList)) {
            //当前轮次为跟标轮不能再发起投标
            Round followRound = iRoundService.getOne(new QueryWrapper<>(new Round().setBidingId(bidingId).setRound(BidConstant.FOLLOW_ROUND)));
            if (followRound != null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前轮次为跟标轮,不能再发起投标"));
            }

            //如果不是第一轮，判断当前轮是否结束（根据是否公示字段判断）
            Round currentRoundObj = iRoundService.getOne(new QueryWrapper<>(new Round().
                    setBidingId(bidingId).setRound(currentRound)));
            if (!("Y".equals(currentRoundObj.getPublicResult()))) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前轮投标评标结果未完成或未公示，请先前往评标处处理"));
            }

            //发起投标的结束时间不能早于当前时间
            if (endTime.compareTo(new Date()) <= 0) {
                throw new BaseException(LocaleHandler.getLocaleMsg("发起投标的结束时间不能早于当前时间"));
            }

            //当前轮有入围的供应商才能发起下一轮投标
            QueryWrapper<OrderHead> orderHeadQueryWrapper = new QueryWrapper<>();
            orderHeadQueryWrapper.eq("BIDING_ID", bidingId);
            orderHeadQueryWrapper.eq("ROUND", currentRound);
            orderHeadQueryWrapper.eq("ORDER_STATUS", OrderStatusEnum.SUBMISSION.getValue());
            int submitSupplierCount = iOrderHeadService.count(orderHeadQueryWrapper);
            if (submitSupplierCount <= 0) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前轮无入围供应商，不能发起投标"));
            }
        }

        //供应商投标轮次表新增一条记录
        int nextRound = currentRound + 1;
        Round round = new Round();
        Long id = IdGenrator.generate();
        Round nextRoundObj = round.setRoundId(id).setRound(nextRound).
                setStartTime(new Date()).setEndTime(endTime).
                setBidingId(bidingId);
        iRoundService.save(nextRoundObj);

        //更新招标基础信息表-当前轮和招标项目状态
        iBidingService.update(new Biding().setCurrentRound(nextRound).setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue()),
                new UpdateWrapper<>(new Biding().setBidingId(bidingId)));


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void extendBiding(Long bidingId, Date endTime, String extendReason) {
        Biding biding = iBidingService.getById(bidingId);
        int currentRound = biding.getCurrentRound();
        Round currentRoundObj = iRoundService.getOne(new QueryWrapper<>(new Round().
                setBidingId(bidingId).setRound(currentRound)));

    /*    //endTime时间不得早于当前时间或修改前时间
        if (endTime.compareTo(new Date()) <= 0 || endTime.compareTo(currentRoundObj.getEndTime()) <= 0) {
            throw new BaseException(LocaleHandler.getLocaleMsg("时间设置错误"));
        }*/

        iRoundService.update(new Round().setEndTime(endTime),
                new UpdateWrapper<>(new Round().setRoundId(currentRoundObj.getRoundId())));

        /*//增加变更记录:供应商投标轮次变更表
        RoundChange roundChange = new RoundChange();
        Long id = IdGenrator.generate();
        roundChange.setChangeBidingId(bidingId).setChangeEndTime(endTime).
                setReason(extendReason).setChangeId(id).
                setChangeBidingId(bidingId).setChangeRoundId(currentRoundObj.getRoundId()).
                setChangeRound(currentRoundObj.getRound()).setChangeStartTime(currentRoundObj.getStartTime()).
                setChangeBusinessOpenBid(currentRoundObj.getBusinessOpenBid()).setChangeBusinessOpenBidTime(currentRoundObj.getBusinessOpenBidTime()).
                setChangePublicResult(currentRoundObj.getPublicResult()).setChangePublicResultTime(currentRoundObj.getPublicResultTime());
        iRoundChangeService.save(roundChange);*/
    }

    /**
     * 第一轮以后，没有供应商报价的需求行改个状态
     * 然后在获取报价行那里加个查询条件
     */
    private void handleNoSupplierQuote(Long bidId) {
        //根据bidId找出lineId
        List<Long> requireIds = requirementLineMapper.selectList(Wrappers.lambdaQuery(BidRequirementLine.class)
                .select(BidRequirementLine::getRequirementLineId)
                .eq(BidRequirementLine::getBidingId, bidId)).stream().map(BidRequirementLine::getRequirementLineId).collect(Collectors.toList());
        //然后根据去orderLine那里找没有出现的requireLineId
        List<Long> existsIds = orderLineMapper.selectList(Wrappers.lambdaQuery(OrderLine.class).in(OrderLine::getRequirementLineId, requireIds)).stream().map(OrderLine::getRequirementLineId).collect(Collectors.toList());
        List<Long> shouldChange = new ArrayList<>();
        for (Long existsId : existsIds) {
            if (!requireIds.contains(existsId)) {
                shouldChange.add(existsId);
            }
        }
        if(CollectionUtils.isNotEmpty(shouldChange)){
            requirementLineMapper.update(null, Wrappers.lambdaUpdate(BidRequirementLine.class)
                    .in(BidRequirementLine::getRequirementLineId, shouldChange)
                    .set(BidRequirementLine::getQuoteStatus, YesOrNo.NO.getValue()));
        }
    }
}
