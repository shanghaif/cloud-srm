package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.BidConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.techscore.ScoreStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service.IBusinessProposalService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service.IRoundService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.IEvaluationService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.OrderHeadFileMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service.ITechScoreHeadService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.BusinessItemVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.CancelBidParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.OrderDetailVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.BidingFileTypeEnum;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidOrderHeadVO;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidOrderLineVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 商务开标
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 上午11:01:06
 *  修改内容:
 *          </pre>
 */
@Service
public class BusinessProposalServiceImpl implements IBusinessProposalService {

    @Autowired
    private IOrderHeadService iOrderHeadService;

    @Autowired
    private IOrderLineService iOrderLineService;

    @Autowired
    private IBidingService iBidingService;

    @Autowired
    private IRoundService iRoundService;

    @Autowired
    private IBidVendorService iBidVendorService;

    @Autowired
    private ITechScoreHeadService iTechScoreHeadService;

    @Autowired
    private IGroupService iGroupService;

    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;
    @Autowired
    private OrderHeadFileMapper orderHeadFileMapper;

    @Resource
    private IEvaluationService evaluationService;

    private final EntityManager<OrderLine> orderLineDao = EntityManager.use(OrderLineMapper.class);

    @Override
    public String openBidCheck(Long bidingId) {
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Integer currentRound = biding.getCurrentRound();
        Assert.notNull(currentRound, "未发起投标，不能进行商务开标");
        Date currentDate = new Date();
        ///todo 先去掉校验，后期加上
        /*boolean after = biding.getEnrollEndDatetime().after(currentDate);
        if(after){
            throw new BaseException("未到报价截止时间，无法商务开标");
        }*/
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(currentRound)));
        Assert.notNull(round, "招标轮次不存在");
//		Assert.isTrue(!"Y".equals(round.getBusinessOpenBid()), "本轮已商务开标，无需重复开标");
        // 提前结束时，校验所有有权限投标的供应商是否都已经投标
        if (round.getEndTime().after(currentDate)) {
            int notSubmitPriceCount = 0;// 未提交报价的供应商数
            List<Long> hadJoinVendorId = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(bidingId).setJoinFlag("Y")).select("VENDOR_ID")).stream().map(BidVendor::getVendorId).collect(Collectors.toList());
            if (currentRound == BidConstant.FIRST_ROUND) {
                List<OrderHead> orderHeadList = iOrderHeadService.list(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(bidingId).setRound(currentRound).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())).select("BID_VENDOR_ID", "ORDER_HEAD_ID"));
                notSubmitPriceCount = hadJoinVendorId.size() - orderHeadList.size();
            } else {
                Function<OrderHead, Optional<Long>> getVendorIdByOrderHeader = e -> Optional.of(iBidVendorService.getOne(Wrappers.lambdaQuery(BidVendor.class)
                        .select(BidVendor::getVendorId)
                        .eq(BidVendor::getBidVendorId, e.getBidVendorId())).getVendorId());
                //把上轮投标的供应商查出来,并执行报价操作
                List<OrderHead> orderLastHeadList = iOrderHeadService.list(Wrappers.lambdaQuery(OrderHead.class)
                        .select(OrderHead::getBidVendorId, OrderHead::getOrderHeadId, OrderHead::getPricingType)
                        .eq(OrderHead::getBidingId, bidingId)
                        .eq(OrderHead::getRound, currentRound - 1)
                        .eq(OrderHead::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue()));
                Map<Long, Optional<Long>> currentBidVendorAndVendorIdMap = iOrderHeadService.list(Wrappers.lambdaQuery(OrderHead.class)
                        .select(OrderHead::getBidVendorId, OrderHead::getOrderHeadId)
                        .eq(OrderHead::getBidingId, bidingId)
                        .eq(OrderHead::getRound, currentRound)
                        .eq(OrderHead::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue()))
                        .stream().collect(Collectors.toMap(OrderHead::getBidVendorId,
                                getVendorIdByOrderHeader,
                                (v1, v2) -> v1.isPresent() ? v1 : v2));
                //上轮报价头对应bidvendor表查出对应的供应商id
                for (Long joinVendorId : hadJoinVendorId) {
                    boolean isSubmit = false;
                    for (Map.Entry<Long, Optional<Long>> bidVendorIdAndVendorId : currentBidVendorAndVendorIdMap.entrySet()) {
                        Optional<Long> value = bidVendorIdAndVendorId.getValue();
                        if (value.isPresent() && joinVendorId.equals(value.get())) {
                            isSubmit = true;
                            break;
                        }
                    }
                    //该供应商没有提交，则根据他找到他对应bidVendorId
                    if (!isSubmit) {
                        BidVendor bidVendor = iBidVendorService.getOne(Wrappers.lambdaQuery(BidVendor.class)
                                .select(BidVendor::getBidVendorId)
                                .eq(BidVendor::getBidingId, bidingId)
                                .eq(BidVendor::getVendorId, joinVendorId));
                        if (Objects.nonNull(bidVendor)) {
                            for (OrderHead lastHead : orderLastHeadList) {
                                if (lastHead.getBidVendorId().equals(bidVendor.getBidVendorId())&&!Objects.equals(lastHead.getOrderStatus(),OrderStatusEnum.INVALID.getValue())) {
                                    iOrderHeadService.submitOrderForSupplier(joinVendorId, lastHead, bidingId, round);
                                }
                            }
                        }
                    }
                }


            }
            if (notSubmitPriceCount > 0) {
                return LocaleHandler.getLocaleMsg("首轮有供应商未完成投标，是否继续开标？");
            }
        }
        return null;
    }

    @Override
    public void openBid(Long bidingId) {
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Assert.notNull(biding.getCurrentRound(), "未发起投标，不能进行商务开标");
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(biding.getCurrentRound())));
        Assert.notNull(round, "招标轮次不存在");
        Assert.isTrue(!"Y".equals(round.getBusinessOpenBid()), "本轮已商务开标，无需重复开标");
        if (BidType.get(biding.getBidingType()) == BidType.TECHNOLOGY_BUSINESS) {
            Assert.isTrue("Y".equals(biding.getTechOpenBid()), "技术标开标未完成，请前往技术开标");
            Integer notReviewCount = 0;
            List<TechScoreHead> techScoreHeadList = iTechScoreHeadService.list(new QueryWrapper<TechScoreHead>(new TechScoreHead().setBidingId(bidingId).setScoreStatus(ScoreStatusEnum.FINISHED.getValue())));
            Map<Long, List<TechScoreHead>> techScoreHeadMap = techScoreHeadList.stream().collect(Collectors.groupingBy(TechScoreHead::getBidVendorId));
            List<Group> groupList = iGroupService.list(new QueryWrapper<Group>(new Group().setBidingId(bidingId).setJudgeFlag("Y")));
            Set<Long> groupUserIdSet = groupList.stream().map(Group::getUserId).collect(Collectors.toSet());
            for (Long bidVendorId : techScoreHeadMap.keySet()) {
                Set<Long> techScoreHeadIdSet = techScoreHeadMap.get(bidVendorId).stream().map(TechScoreHead::getCreatedId).collect(Collectors.toSet());
                if (groupUserIdSet.size() != techScoreHeadIdSet.size() || !groupUserIdSet.containsAll(techScoreHeadIdSet)) {
                    notReviewCount++;
                }
            }
            Assert.isTrue(notReviewCount == 0, "技术评分尚未完成，无法商务开标");
        }
        biding.setBidingStatus(BiddingProjectStatus.BUSINESS_EVALUATION.getValue());
        round.setBusinessOpenBid("Y");
        round.setBusinessOpenBidTime(new Date());
        iRoundService.updateById(round);
        iBidingService.updateById(biding);

        // 默认设置所有报价行都允许进入下一轮
        this.defaultSetOrderLinesNextRound(biding);
    }

    protected void defaultSetOrderLinesNextRound(Biding bidding) {
        // 存储.
        iOrderLineService.update(Wrappers.lambdaUpdate(OrderLine.class)
                .set(OrderLine::getWin, "Y")
                .eq(OrderLine::getBidingId, bidding.getBidingId())
                .eq(OrderLine::getRound, bidding.getCurrentRound())
                .ne(OrderLine::getWin, "Q")
                .ne(OrderLine::getWin,"Y")
        );
    }

    @Override
    public List<BusinessItemVO> queryBusinessItemList(Long bidingId) {
        List<BusinessItemVO> result = new ArrayList<BusinessItemVO>();
        List<OrderHead> orderHeadList = iOrderHeadService.list(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(bidingId)).in("ORDER_STATUS", Arrays.asList(OrderStatusEnum.SUBMISSION.getValue(), OrderStatusEnum.INVALID.getValue())).orderByDesc("ROUND").orderByDesc("SUBMIT_TIME"));
        if (orderHeadList.size() == 0) {
            return result;
        }
        List<Long> bidVendorIdList = orderHeadList.stream().map(OrderHead::getBidVendorId).collect(Collectors.toList());
        if (bidVendorIdList.size() == 0) {
            return result;
        }
        List<BidVendor> bidVendorList = iBidVendorService.listByIds(bidVendorIdList);
        Map<Long, BidVendor> vendorMap = bidVendorList.stream().collect(Collectors.toMap(BidVendor::getBidVendorId, Function.identity()));
        for (OrderHead orderHead : orderHeadList) {
            BidVendor vendor = vendorMap.get(orderHead.getBidVendorId());
            if (vendor == null) {
                continue;
            }
            BusinessItemVO vo = new BusinessItemVO();
            vo.setBidDetail(orderHead.getBidOrderNum());
            vo.setOrderHeadId(orderHead.getOrderHeadId());
            vo.setOrderStatus(orderHead.getOrderStatus());
            vo.setRejectReason(orderHead.getRejectReason());
            vo.setRound(orderHead.getRound());
            vo.setSubmitTime(orderHead.getSubmitTime());
            vo.setVendorCode(vendor.getVendorCode());
            vo.setVendorName(vendor.getVendorName());
            vo.setBidVendorId(vendor.getBidVendorId());
            result.add(vo);
        }
        return result;
    }

    @Override
    public void cancelBid(CancelBidParam cancelBidParam) {
        Assert.notNull(cancelBidParam.getOrderHeadId(), "投标单ID不能为空");
        OrderHead orderHead = iOrderHeadService.getById(cancelBidParam.getOrderHeadId());
        Assert.notNull(orderHead, "投标单ID不存在");
        if (OrderStatusEnum.get(orderHead.getOrderStatus()) == OrderStatusEnum.INVALID) {
            return;
        }
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setRoundId(orderHead.getBidRoundId())));
        Assert.notNull(round, "投标轮次不存在");
        Assert.isTrue(!"Y".equals(round.getBusinessOpenBid()), "已商务开标，不能作废");
        orderHead.setOrderStatus(OrderStatusEnum.INVALID.getValue());
        orderHead.setRejectReason(cancelBidParam.getRejectReason());
        orderHead.setRejectTime(new Date());
        iOrderHeadService.updateById(orderHead);
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setOrderHeadId(cancelBidParam.getOrderHeadId())));
        orderLineList.forEach(l -> l.setOrderStatus(OrderStatusEnum.INVALID.getValue()));
        iOrderLineService.updateBatchById(orderLineList);
    }

    @Override
    public List<OrderDetailVO> queryOrderDetailList(Long orderHeadId) {
        List<OrderDetailVO> result = new ArrayList<OrderDetailVO>();
        OrderHead orderHead = iOrderHeadService.getById(orderHeadId);
        Assert.notNull(orderHead, "投标头表单据ID不存在");
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setOrderHeadId(orderHeadId)));
        if (orderLineList.size() == 0) {
            return result;
        }
        Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(orderHead.getBidingId()).setRound(orderHead.getRound())));
        List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(orderHead.getBidingId())));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        Map<Long, OrderLine> lastRoundInfoMap = new HashMap<Long, OrderLine>();
        if (orderHead.getRound() > BidConstant.FIRST_ROUND) {
            // 上轮入围记录
            List<OrderLine> lastRoundOrderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(orderHead.getBidingId()).setBidVendorId(orderHead.getBidVendorId()).setRound(orderHead.getRound() - 1).setWin("Y").setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
            lastRoundInfoMap = lastRoundOrderLineList.stream().collect(Collectors.toMap(OrderLine::getRequirementLineId, Function.identity()));
        }
        for (OrderLine orderLine : orderLineList) {
            BidRequirementLine requirementLine = requirementLineMap.get(orderLine.getRequirementLineId());
            if (requirementLine != null) {
                OrderDetailVO vo = new OrderDetailVO().setOrgName(requirementLine.getOrgName()).setItemGroup(requirementLine.getItemGroup()).setTargetNum(requirementLine.getTargetNum()).setTargetDesc(requirementLine.getTargetDesc());
                if ("Y".equals(round.getBusinessOpenBid())) {
                    vo.setPrice(orderLine.getPrice());// 未商务开标，不能查看报价
                }
                vo.setPurchaseCategory(requirementLine.getCategoryName()).setQuantity(requirementLine.getQuantity()).setAmount(requirementLine.getAmount()).setComments(requirementLine.getComments()).setTargetPrice(requirementLine.getTargetPrice());
                OrderLine lastRoundOrderLine = lastRoundInfoMap.get(orderLine.getRequirementLineId());
                if (lastRoundOrderLine != null) {
                    vo.setLastRoundPrice(lastRoundOrderLine.getPrice());
                    vo.setLastRoundMinPrice(lastRoundOrderLine.getCurrentRoundMinPrice());
                    vo.setLastRoundRank(lastRoundOrderLine.getRank());
                }
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public BidOrderHeadVO queryNewOrderDetailList(Long orderHeadId) {
        OrderHead byId = iOrderHeadService.getById(orderHeadId);

        List<BidOrderLineVO> bidOrderLineVOs = iOrderHeadService.getOrderLineVOS(byId.getBidingId(), orderHeadId, byId.getBidVendorId());
       /* for (BidOrderLineVO vo : bidOrderLineVOs) {
            OrderLine line = iOrderLineService.getOne(Wrappers.lambdaQuery(OrderLine.class)
                    .select(OrderLine::getPrice)
                    .eq(OrderLine::getOrderLineId, vo.getOrderLineId())
                    .eq(Objects.nonNull(vo.getRound()) && vo.getRound() > 0, OrderLine::getRound, vo.getRound()));
            if (Objects.nonNull(line)) {
                vo.setLastPrice(line.getPrice());
            }
            BigDecimal currentPrice = vo.getPrice();
            if (Objects.nonNull(currentPrice) && Objects.nonNull(vo.getLastPrice())) {
                if(!Objects.equals(vo.getLastPrice(),BigDecimal.ZERO)){
                    vo.setCompareRate(currentPrice.divide(vo.getLastPrice(), 8, RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100)));
                }
            }
        }*/
        BidOrderHeadVO vo = BeanCopyUtil.copyProperties(byId, BidOrderHeadVO::new);
        vo.setOrderLines(bidOrderLineVOs);
        List<OrderHeadFile> files = orderHeadFileMapper.selectList(Wrappers.lambdaQuery(OrderHeadFile.class)
                .eq(OrderHeadFile::getOrderHeadId, orderHeadId));
        vo.setOrderLines(bidOrderLineVOs);
        vo.setOrderHeadFiles(files);
        return vo;
    }

    @Override
    public List<OrderHeadFile> getOrderHeadFileByOrderHeadId(Long orderHeadId) {
        List<OrderHeadFile> files = orderHeadFileMapper.selectList(Wrappers.lambdaQuery(OrderHeadFile.class)
                .eq(OrderHeadFile::getOrderHeadId, orderHeadId));
        if (CollectionUtils.isEmpty(files)) {
            return files;
        }
        return files.stream().filter(e -> e.getVendorReferenceFileType().equals(BidingFileTypeEnum.COMMERCIA_BID.getCode())).collect(Collectors.toList());
    }

    @Override
    public List<OrderHeadFile> getOrderHeadFileByVendorIdAndBidingId(Long biddingId, Long vendorId) {
        Biding biding = iBidingService.getById(biddingId);
        Integer currentRound = biding.getCurrentRound();
        Long bidVendorId = iBidVendorService.getOne(Wrappers.lambdaQuery(BidVendor.class)
                .select(BidVendor::getBidVendorId)
                .eq(BidVendor::getBidingId, biddingId).eq(BidVendor::getVendorId, vendorId)).getBidVendorId();
        OrderHead head = iOrderHeadService.getOne(Wrappers.lambdaQuery(OrderHead.class)
                .eq(OrderHead::getBidingId, biddingId)
                .eq(OrderHead::getRound, currentRound)
                .eq(OrderHead::getBidVendorId, bidVendorId)
        );
        if (Objects.isNull(head)) {
            return Collections.emptyList();
        }
        List<OrderHeadFile> files = orderHeadFileMapper.selectList(Wrappers.lambdaQuery(OrderHeadFile.class)
                .eq(OrderHeadFile::getOrderHeadId, head.getOrderHeadId()));
        if (CollectionUtils.isEmpty(files)) {
            return files;
        }
        return files.stream().filter(e -> e.getVendorReferenceFileType().equals(BidingFileTypeEnum.TECHNICAL_BID.getCode())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> generateBidReport(Long bidingId) throws Exception {
        Assert.notNull(bidingId, "投标单ID不能为空");
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "招投标ID不存在");
        Assert.notNull(biding.getCurrentRound(), "未发起投标");
        List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(bidingId)));
        Assert.notEmpty(requirementLineList, "招标需求为空");
        List<OrderLine> orderLineList = iOrderLineService.list(new QueryWrapper<OrderLine>(new OrderLine().setBidingId(bidingId).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
        Assert.notEmpty(orderLineList, "报价行为空");
        List<BidVendor> vendorList = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(bidingId)));
        Assert.notEmpty(vendorList, "供应商为空");
        Map<Long, BidVendor> vendorMap = vendorList.stream().collect(Collectors.toMap(BidVendor::getBidVendorId, Function.identity()));
        String fileName = UUID.randomUUID().toString() + ".xls";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0, LocaleHandler.getLocaleMsg("价格得分及波动情况")).build();
        sheet1.setHead(head1(orderLineList, vendorMap));
        excelWriter.write(data1(biding, requirementLineList, orderLineList, vendorMap), sheet1);
        WriteSheet sheet2 = EasyExcel.writerSheet(1, LocaleHandler.getLocaleMsg("排名情况")).build();
        sheet2.setHead(head2(orderLineList, vendorMap));
        excelWriter.write(data2(biding, requirementLineList, orderLineList, vendorMap), sheet2);
        excelWriter.finish();
        File file = new File(fileName);
        byte[] buffer = FileUtils.readFileToByteArray(file);
        file.delete();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buffer", buffer);
        result.put("fileName", biding.getBidingNum() + ".xls");
        return result;
    }


    private List<List<String>> head1(List<OrderLine> orderLineList, Map<Long, BidVendor> vendorMap) {
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("序号")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("采购组织")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("标的编码")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("标的名称")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("采购量")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("采购金额(含税，万元)")));
        // 默认排名升序，供应商ID升序
        Map<Integer, Map<Long, OrderLine>> orderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound, Collectors.toMap(OrderLine::getBidVendorId, Function.identity(), (k1, k2) -> k1)));
        for (Integer round : orderLineMap.keySet()) {
            String msg = LocaleHandler.getLocaleMsg("第N轮报价", round.toString());
            for (Long bidVendorId : orderLineMap.get(round).keySet()) {
                if (vendorMap.containsKey(bidVendorId)) {
                    head.add(Arrays.asList(msg, vendorMap.get(bidVendorId).getVendorName()));
                }
            }
            head.add(Arrays.asList(msg, LocaleHandler.getLocaleMsg("第N轮最低价", round.toString())));
//			head.add(Arrays.asList(msg, LocaleHandler.getLocaleMsg("拦标价")));
        }
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("含税中标价格")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("中标供应商")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("备注")));
        for (Integer i = 1; i <= 10; i++) {
            head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("跟标", i.toString())));
        }
        return head;
    }

    private List<List<String>> head2(List<OrderLine> orderLineList, Map<Long, BidVendor> vendorMap) {
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("序号")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("采购组织")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("标的编码")));
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("标的名称")));
        // 默认排名升序，供应商ID升序
        Map<Integer, Map<Long, OrderLine>> orderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound, Collectors.toMap(OrderLine::getBidVendorId, Function.identity(), (k1, k2) -> k1)));
        for (Integer round : orderLineMap.keySet()) {
            String msg = LocaleHandler.getLocaleMsg("第N轮排名", round.toString());
            for (Long bidVendorId : orderLineMap.get(round).keySet()) {
                if (vendorMap.containsKey(bidVendorId)) {
                    head.add(Arrays.asList(msg, vendorMap.get(bidVendorId).getVendorName()));
                }
            }
            head.add(Arrays.asList(msg, LocaleHandler.getLocaleMsg("第N轮最低价", round.toString())));
//			head.add(Arrays.asList(msg, LocaleHandler.getLocaleMsg("拦标价")));
        }
        head.add(Arrays.asList("", LocaleHandler.getLocaleMsg("含税中标价格")));
        return head;
    }

    private List<List<String>> data1(Biding biding, List<BidRequirementLine> requirementLineList, List<OrderLine> orderLineList, Map<Long, BidVendor> vendorMap) {
        List<List<String>> data = new ArrayList<List<String>>();
        Map<Integer, List<OrderLine>> roundOrderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        Map<Integer, Map<Long, List<OrderLine>>> orderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound, Collectors.groupingBy(OrderLine::getRequirementLineId)));
        Map<Long, List<OrderLine>> winBidMap = orderLineList.stream().filter(l -> SelectionStatusEnum.get(l.getSelectionStatus()) == SelectionStatusEnum.WIN).collect(Collectors.groupingBy(OrderLine::getRequirementLineId));
        Map<Long, List<OrderLine>> firstRoundOrderLineMap = orderLineMap.get(BidConstant.FIRST_ROUND);
        Assert.notNull(firstRoundOrderLineMap, "第一轮投标行数据不存在");
        int index = 1;
        // 用第一轮的报价数据的招标需求作为行数据
        for (Long requirementLineId : firstRoundOrderLineMap.keySet()) {
            BidRequirementLine line = requirementLineMap.get(requirementLineId);
            if (line == null) {
                continue;
            }
            List<String> dataItem = new ArrayList<String>();
            dataItem.add(String.valueOf(index++));
            dataItem.add(line.getOrgName());
            dataItem.add(line.getTargetNum());
            dataItem.add(line.getTargetDesc());
            dataItem.add(line.getQuantity() + "");
            dataItem.add(line.getAmount().setScale(2, RoundingMode.HALF_DOWN).toPlainString());
            // 所有轮次
            for (int round = BidConstant.FIRST_ROUND; round <= biding.getCurrentRound(); round++) {
                if (!orderLineMap.containsKey(round)) {
                    continue;
                }
                List<OrderLine> list = orderLineMap.get(round).get(requirementLineId);
                if (list == null || list.size() == 0) {
                    continue;
                }
                // 按供应商ID升序
                Set<Long> bidVendorIdSet = roundOrderLineMap.get(round).stream().map(OrderLine::getBidVendorId).collect(Collectors.toSet());
                Map<Long, OrderLine> priceMap = list.stream().collect(Collectors.toMap(OrderLine::getBidVendorId, Function.identity(), (k1, k2) -> k1));
                for (Long bidVendorId : bidVendorIdSet) {
                    dataItem.add(priceMap.get(bidVendorId) == null ? null : priceMap.get(bidVendorId).getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                }
                dataItem.add(list.get(0).getCurrentRoundMinPrice() == null ? null : list.get(0).getCurrentRoundMinPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                dataItem.add(line.getTargetPrice() == null ? null : line.getTargetPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
            }
            String winBidPrice = "";
            String winBidVendorName = "";
            List<OrderLine> winBidOrderLineList = winBidMap.get(requirementLineId);
            if (winBidOrderLineList != null) {
                for (OrderLine o : winBidOrderLineList) {
                    if (vendorMap.get(o.getBidVendorId()) == null) {
                        continue;
                    }
                    winBidPrice = winBidPrice + o.getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString() + ",";
                    winBidVendorName = winBidVendorName + vendorMap.get(o.getBidVendorId()).getVendorName() + ",";
                }
            }
            dataItem.add(StringUtils.isNotBlank(winBidPrice) ? winBidPrice.substring(0, winBidPrice.length() - 1) : "");
            dataItem.add(StringUtils.isNotBlank(winBidVendorName) ? winBidVendorName.substring(0, winBidVendorName.length() - 1) : "");
            dataItem.add(line.getComments());
            Map<Long, List<OrderLine>> lastRoundMap = orderLineMap.get(biding.getCurrentRound());
            if (lastRoundMap != null && lastRoundMap.get(requirementLineId) != null) {
                for (OrderLine orderLine : lastRoundMap.get(requirementLineId).stream().sorted((r1, r2) -> r1.getBidVendorId().compareTo(r2.getBidVendorId())).collect(Collectors.toList())) {
                    if (SelectionStatusEnum.get(orderLine.getSelectionStatus()) == SelectionStatusEnum.FOLLOW) {
                        dataItem.add(vendorMap.get(orderLine.getBidVendorId()).getVendorName());
                    }
                }
            }
            data.add(dataItem);
        }
        return data;
    }

    private List<List<String>> data2(Biding biding, List<BidRequirementLine> requirementLineList, List<OrderLine> orderLineList, Map<Long, BidVendor> vendorMap) {
        List<List<String>> data = new ArrayList<List<String>>();
        Map<Integer, List<OrderLine>> roundOrderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound));
        Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
        Map<Integer, Map<Long, List<OrderLine>>> orderLineMap = orderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRound, Collectors.groupingBy(OrderLine::getRequirementLineId)));
        Map<Long, List<OrderLine>> winBidMap = orderLineList.stream().filter(l1 -> SelectionStatusEnum.get(l1.getSelectionStatus()) == SelectionStatusEnum.WIN).collect(Collectors.groupingBy(OrderLine::getRequirementLineId));
        Map<Long, List<OrderLine>> firstRoundOrderLineMap = orderLineMap.get(BidConstant.FIRST_ROUND);
        Assert.notNull(firstRoundOrderLineMap, "第一轮投标行数据不存在");
        int index = 1;
        // 用第一轮的报价数据的招标需求作为行数据
        for (Long requirementLineId : firstRoundOrderLineMap.keySet()) {
            BidRequirementLine line = requirementLineMap.get(requirementLineId);
            if (line == null) {
                continue;
            }
            List<String> dataItem = new ArrayList<String>();
            dataItem.add(String.valueOf(index++));
            dataItem.add(line.getOrgName());
            dataItem.add(line.getTargetNum());
            dataItem.add(line.getTargetDesc());
            for (Integer round = BidConstant.FIRST_ROUND; round <= biding.getCurrentRound(); round++) {
                if (!orderLineMap.containsKey(round)) {
                    continue;
                }
                List<OrderLine> list = orderLineMap.get(round).get(requirementLineId);
                if (list == null || list.size() == 0) {
                    continue;
                }
                // 按供应商ID升序
                Set<Long> bidVendorSet = roundOrderLineMap.get(round).stream().map(OrderLine::getBidVendorId).collect(Collectors.toSet());
                Map<Long, OrderLine> priceMap = list.stream().collect(Collectors.toMap(OrderLine::getBidVendorId, Function.identity(), (k1, k2) -> k1));
                for (Long bidVendorId : bidVendorSet) {
                    dataItem.add(priceMap.get(bidVendorId) == null ? null : priceMap.get(bidVendorId).getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                }
                dataItem.add(list.get(0).getCurrentRoundMinPrice() == null ? null : list.get(0).getCurrentRoundMinPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
                dataItem.add(line.getTargetPrice() == null ? null : line.getTargetPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString());
            }
            String winBidPrice = "";
            List<OrderLine> winBidOrderLineList = winBidMap.get(requirementLineId);
            if (winBidOrderLineList != null) {
                for (OrderLine o : winBidOrderLineList) {
                    if (vendorMap.get(o.getBidVendorId()) == null) {
                        continue;
                    }
                    winBidPrice = winBidPrice + o.getPrice().setScale(biding.getDecimalAccuracy(), RoundingMode.HALF_DOWN).toPlainString() + ",";
                }
            }
            dataItem.add(StringUtils.isNotBlank(winBidPrice) ? winBidPrice.substring(0, winBidPrice.length() - 1) : "");
            data.add(dataItem);
        }
        return data;
    }

}
