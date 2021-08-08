package com.midea.cloud.srm.bid.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ProcessNodeName;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidFileType;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.logistics.*;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyProcessStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.mapper.LgtBidingMapper;
import com.midea.cloud.srm.bid.service.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateHeadService;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlTopInfoVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.BidingFileTypeEnum;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.vo.StartOrExtendBidingVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import com.midea.cloud.srm.model.logistics.bid.vo.*;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsPurchaseRequirementDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementFile;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementLine;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateHead;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderHeadService;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementHeadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流寻源基础信息表 服务实现类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:54:37
 *  修改内容:
 * </pre>
*/
@Slf4j
@Service
public class LgtBidingServiceImpl extends ServiceImpl<LgtBidingMapper, LgtBiding> implements ILgtBidingService {
    @Resource
    private ILgtFileService iLgtFileService;
    @Resource
    private ILgtBidRequirementLineService iLgtBidRequirementLineService;
    @Resource
    private ILgtBidResultSumService iLgtBidResultSumService;
    @Resource
    private ILgtBidShipPeriodService iLgtBidShipPeriodService;
    @Resource
    private ILgtBidTemplateService iLgtBidTemplateService;
    @Resource
    private ILgtFileConfigService iLgtFileConfigService;
    @Resource
    private ILgtGroupService iLgtGroupService;
    @Resource
    private ILgtPayPlanService iLgtPayPlanService;
    @Resource
    private ILgtQuoteAuthorizeService iLgtQuoteAuthorizeService;
    @Resource
    private ILgtVendorFileService iLgtVendorFileService;
    @Resource
    private ILgtProcessNodeService iLgtProcessNodeService;
    @Resource
    private BidClient bidClient;
    @Resource
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;
    @Resource
    private ILogisticsTemplateHeadService iLogisticsTemplateHeadService;
    @Resource
    private BaseClient baseClient;
    @Resource
    private SupplierClient supplierClient;
    @Resource
    private ILgtVendorService iLgtVendorService;
    @Resource
    private ILgtVendorQuotedHeadService iLgtVendorQuotedHeadService;
    @Resource
    private ILgtVendorQuotedLineService iLgtVendorQuotedLineService;
    @Resource
    private ILgtRoundService iLgtRoundService;
    @Resource
    private ILgtVendorQuotedSumService iLgtVendorQuotedSumService;
    @Resource
    private IRequirementHeadService requirementHeadService;
    @Resource
    private ILgtBidingVendorService iLgtBidingVendorService;
    @Resource
    private IOrderHeadService iOrderHeadService;
    @Resource
    private PmClient pmClient;
    @Resource
    private IRequirementHeadService iRequirementHeadService;
    @Resource
    private RbacClient rbacClient;
    public static Set<String> canSeeResultSet=new HashSet<>();
    static {
        canSeeResultSet.add(BiddingProjectStatus.BUSINESS_EVALUATION.getValue());
        canSeeResultSet.add(BiddingProjectStatus.PUBLICITY_OF_RESULT.getValue());
        canSeeResultSet.add(BiddingProjectStatus.PRICED.getValue());
        canSeeResultSet.add(BiddingProjectStatus.PROJECT_END.getValue());
    }

    @Override
    @Transactional
    public void selectionQuotedLineSaveNew(LgtVendorQuotedLineDto lgtVendorQuotedLines) {
        Assert.notNull(lgtVendorQuotedLines,"参数不能为空!");
        List<LgtVendorQuotedLine> vendorQuotedLines = lgtVendorQuotedLines.getLgtVendorQuotedLines();
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorQuotedLines),"投标明细不能为空!");
        // 贸易术语组合名称
        String combinationName = lgtVendorQuotedLines.getCombinationName();
        LgtBiding lgtBiding = this.getById(vendorQuotedLines.get(0).getBidingId());
        // 获取当前轮次信息
        LgtRound lgtRound = iLgtRoundService.getOne(new QueryWrapper<>(new LgtRound().setBidingId(lgtBiding.getBidingId()).setRound(lgtBiding.getCurrentRound())));
        if(!ObjectUtils.isEmpty(combinationName)){
            // 筛选贸易术语为空的数据
            List<LgtVendorQuotedLine> quotedLines1 = vendorQuotedLines.stream().filter(lgtVendorQuotedLine -> ObjectUtils.isEmpty(lgtVendorQuotedLine.getTradeTerm())).collect(Collectors.toList());
            quotedLines1.forEach(lgtVendorQuotedLine -> lgtVendorQuotedLine.setTradeTerm(""));
            iLgtVendorQuotedLineService.updateBatchById(quotedLines1);

            // 筛选贸易术语不为空的数据
            List<LgtVendorQuotedLine> quotedLines = vendorQuotedLines.stream().filter(lgtVendorQuotedLine -> !ObjectUtils.isEmpty(lgtVendorQuotedLine.getTradeTerm())).collect(Collectors.toList());
            // 保存评选行
            selectionQuotedLineSave(quotedLines);
            // 更新轮次表
            iLgtRoundService.updateById(lgtRound.setCombinationName(combinationName));
        }else {
            // 保存评选行
            selectionQuotedLineSave(vendorQuotedLines);
            // 更新轮次表
            iLgtRoundService.updateById(lgtRound.setCombinationName(""));
        }
    }

    @Override
    public void techOpenBiding(Long bidingId) {
        LgtBiding byId = getById(bidingId);
        if(Objects.isNull(byId)){
            throw new BaseException("找不到招标单信息");
        }
        String bidingStatus = byId.getBidingStatus();
        //如果是投标截止状态，则更新，其他不更新
        if(Objects.equals(bidingStatus,BiddingProjectStatus.TENDER_ENDING.getValue())){
            updateById(new LgtBiding().setBidingId(bidingId)
            .setBidingStatus(BiddingProjectStatus.TECHNICAL_EVALUATION.getValue())
            );
        }
    }

    public void techOpenBusiness(Long bidingId){
        LgtBiding lgtBiding = getById(bidingId);
        // 首次保存则更新状态
        if(BiddingProjectStatus.TENDER_ENDING.getValue().equals(lgtBiding.getBidingStatus())
        ||BiddingProjectStatus.TECHNICAL_EVALUATION.getValue().equals(lgtBiding.getBidingStatus())
        ){
            // 报名截止->商务开标 更新状态
            updateBusinessEvaluationStatus(lgtBiding);
        }
    }

    @Override
    public List<LgtBidTemplate> getLgtBidTemplate(Long bidingId) {
        return iLgtBidTemplateService.list(new QueryWrapper<>(new LgtBidTemplate().setBidingId(bidingId)));
    }

    /**
     * 批量需求池转订单
     * todo 可优化：批量增加招标单
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> requirementToBidings(List<Long> ids) {
        List<Long> result = new LinkedList<>();
        for(Long id : ids){
            result.add(requirementToBiding(id));
        }
        return result;
    }

    /**
     *
     * 获取【立项审批状态为已审批】的招标单的邀请供应商信息
     * @param bidingId
     * @return
     */
    @Override
    public List<LgtBidVendor> getLgtBidVendorByBidingIdAndStatus(Long bidingId) {
        if(Objects.isNull(bidingId)){
            return Collections.EMPTY_LIST;
        }
        LgtBiding biding = this.getById(bidingId);
        if(Objects.isNull(biding)){
            return Collections.EMPTY_LIST;
        }
        if(!BiddingApprovalStatus.APPROVED.getValue().equals(biding.getAuditStatus())){
            return Collections.EMPTY_LIST;
        }
        //获取供应商信息
        List<LgtBidVendor> bidVendors = iLgtVendorService.list(Wrappers.lambdaQuery(LgtBidVendor.class).eq(LgtBidVendor::getBidingId, bidingId));
        return bidVendors;
    }

    @Override
    public void quotedSumExport(Long bidingId, Integer round, Long vendorId, HttpServletResponse response) throws IOException {
        /**
         * 区分国内业务和非国内业务进行分类导出
         */
        LgtBiding lgtBiding = this.getById(bidingId);
        // 获取要导出的数据
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                eq(LgtVendorQuotedLine::getBidingId, bidingId).
                eq(LgtVendorQuotedLine::getRound, round).
                eq(!ObjectUtils.isEmpty(vendorId), LgtVendorQuotedLine::getVendorId, vendorId));
        if (CollectionUtils.isNotEmpty(lgtVendorQuotedLines)) {
            /**
             * 字段转换:
             * 是否往返: Y/N
             * 币制:
             * 费项: CHARGE_NAME
             * 计费方式: CHARGE_LEVEL
             * 计费单位: SUB_LEVEL
             * 贸易术语: TRADE_TERM
             * 整柜/拼柜: FCL/LCL
             * LEG: LEG
             * 进出口方式: EXP/IMP
             */
            // 费项字典
            Map<String, String> chargeNameMap = EasyExcelUtil.getDicCodeName("CHARGE_NAME", baseClient);
            // 计费方式
            Map<String, String> chargeLevelMap = EasyExcelUtil.getDicCodeName("CHARGE_LEVEL", baseClient);
            // 计费单位
            Map<String, String> subLevelMap = EasyExcelUtil.getDicCodeName("SUB_LEVEL", baseClient);
            // 贸易术语
            Map<String, String> tradeTermMap = EasyExcelUtil.getDicCodeName("TRADE_TERM", baseClient);
            // 整柜/拼柜
            Map<String, String> fclLclMap = EasyExcelUtil.getDicCodeName("FCL /LCL", baseClient);
            // LEG
            Map<String, String> legMap = EasyExcelUtil.getDicCodeName("LEG", baseClient);
            // 币种
            Map<String, String> currencyCodeName = EasyExcelUtil.getCurrencyCodeName(baseClient);
            AtomicInteger no = new AtomicInteger(1);
            List<LgtInsideQuotedSumExport> lgtInsideQuotedSumExports = new ArrayList<>();
            List<LgtNoInsideQuotedSumExport> lgtNoInsideQuotedSumExports = new ArrayList<>();
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                Optional.ofNullable(lgtVendorQuotedLine.getExpenseItem()).ifPresent(expenseItem -> lgtVendorQuotedLine.setExpenseItem(chargeNameMap.get(expenseItem)));
                Optional.ofNullable(lgtVendorQuotedLine.getChargeMethod()).ifPresent(chargeMethod -> lgtVendorQuotedLine.setChargeMethod(chargeLevelMap.get(chargeMethod)));
                Optional.ofNullable(lgtVendorQuotedLine.getChargeUnit()).ifPresent(chargeUnit -> lgtVendorQuotedLine.setChargeUnit(subLevelMap.get(chargeUnit)));
                Optional.ofNullable(lgtVendorQuotedLine.getTradeTerm()).ifPresent(tradeTerm -> lgtVendorQuotedLine.setTradeTerm(tradeTermMap.get(tradeTerm)));
                Optional.ofNullable(lgtVendorQuotedLine.getWholeArk()).ifPresent(wholeArk -> lgtVendorQuotedLine.setWholeArk(fclLclMap.get(wholeArk)));
                Optional.ofNullable(lgtVendorQuotedLine.getLeg()).ifPresent(leg -> lgtVendorQuotedLine.setLeg(legMap.get(leg)));
                Optional.ofNullable(lgtVendorQuotedLine.getCurrency()).ifPresent(currency -> lgtVendorQuotedLine.setCurrency(currencyCodeName.get(currency)));
                Optional.ofNullable(lgtVendorQuotedLine.getIfBack()).ifPresent(ifBack -> {
                    if(YesOrNo.YES.getValue().equals(ifBack)){
                        lgtVendorQuotedLine.setIfBack(YesOrNo.YES.getName());
                    }else {
                        lgtVendorQuotedLine.setIfBack(YesOrNo.NO.getName());
                    }
                });


                if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                    LgtInsideQuotedSumExport lgtInsideQuotedSumExport = new LgtInsideQuotedSumExport();
                    BeanCopyUtil.copyProperties(lgtInsideQuotedSumExport,lgtVendorQuotedLine);
                    lgtInsideQuotedSumExport.setNo(no.getAndAdd(1));
                    lgtInsideQuotedSumExports.add(lgtInsideQuotedSumExport);
                }else {
                    LgtNoInsideQuotedSumExport lgtInsideQuotedSumExport = new LgtNoInsideQuotedSumExport();
                    BeanCopyUtil.copyProperties(lgtInsideQuotedSumExport,lgtVendorQuotedLine);
                    lgtInsideQuotedSumExport.setNo(no.getAndAdd(1));
                    lgtNoInsideQuotedSumExports.add(lgtInsideQuotedSumExport);
                }
            });
            OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "报价明细");
            if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                EasyExcelUtil.writeExcelWithModel(outputStream,lgtInsideQuotedSumExports,LgtInsideQuotedSumExport.class);
            }else {
                EasyExcelUtil.writeExcelWithModel(outputStream,lgtNoInsideQuotedSumExports,LgtNoInsideQuotedSumExport.class);
            }
        }
    }

    @Override
    public void saveTechnoSelection(Long bidingId, String technoSelection) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        Assert.notNull(technoSelection,"技术评选评论不能为空!");
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到招标头信息!");
        List<LgtGroup> lgtGroups = iLgtGroupService.list(new QueryWrapper<>(new LgtGroup().setBidingId(bidingId)));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtGroups),"非当前单据技术评委,不能保存!");
        List<String> userNames = lgtGroups.stream().map(LgtGroup::getUserName).collect(Collectors.toList());
        Assert.isTrue(userNames.contains(loginAppUser.getUsername()),"非当前单据技术评委,不能保存!");
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, bidingId).
                eq(LgtRound::getRound, lgtBiding.getCurrentRound()).last(" LIMIT 1"));
        Assert.notNull(lgtRound,"当前轮次信息不存在!");
        lgtRound.setTechnoSelection(technoSelection);
        iLgtRoundService.updateById(lgtRound);
    }

    @Override
    public LgtApproveInfoDto queryResultApproveInfo(Long bidingId,Long round) {
        LgtApproveInfoDto lgtApproveInfoDto = new LgtApproveInfoDto();
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到招标头信息!");
        round = null == round ? lgtBiding.getCurrentRound() : round;
        lgtApproveInfoDto.setLgtBiding(lgtBiding);
        // 结项审批不为拟定时,查找本轮中标的供应商
        if(!BiddingApprovalStatus.DRAFT.getValue().equals(lgtBiding.getEndAuditStatus())){
            List<LgtVendorQuotedSum> lgtVendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                    eq(LgtVendorQuotedSum::getBidingId, bidingId).
                    eq(LgtVendorQuotedSum::getRound, round));
            lgtApproveInfoDto.setLgtVendorQuotedSums(lgtVendorQuotedSums);

            List<LgtVendorQuotedHead> vendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                    eq(LgtVendorQuotedHead::getBidingId, bidingId).
                    eq(LgtVendorQuotedHead::getRound,round).
                    eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()));

            if (CollectionUtils.isNotEmpty(vendorQuotedHeads)) {
                List<Long> quotedHeadIds = vendorQuotedHeads.stream().map(LgtVendorQuotedHead::getQuotedHeadId).collect(Collectors.toList());

                // 查询报价明细
                List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                        in(LgtVendorQuotedLine::getQuotedHeadId, quotedHeadIds));
                lgtApproveInfoDto.setLgtVendorQuotedLines(lgtVendorQuotedLines);
                // 查询船期
                List<LgtBidShipPeriod> lgtBidShipPeriods = iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                        in(LgtBidShipPeriod::getQuotedHeadId, quotedHeadIds));
                lgtApproveInfoDto.setLgtBidShipPeriods(lgtBidShipPeriods);
            }
        }
        return lgtApproveInfoDto;
    }

    @Override
    @Transactional
    public void generateResultApprove(Long bidingId,String summaryDescription) {
        Assert.notNull(summaryDescription,"总结说明不能为空!");
        /**
         * 1. 商务开标
         * 2. 检查有没中标的供应商
         */
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到招标头信息!");
        Assert.isTrue(!checkUserIfTechnology(bidingId),"技术评委不能操作!!!");
        Assert.isTrue(!YesOrNo.YES.getValue().equals(lgtBiding.getGeneratePurchaseApproval()),"请勿重复生成结果审批单!");
        Assert.isTrue(BiddingProjectStatus.PUBLICITY_OF_RESULT.getValue().equals(lgtBiding.getBidingStatus()),"结果未公示,不能生成结果审批单!");
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId, bidingId).
                eq(LgtVendorQuotedSum::getRound, lgtBiding.getCurrentRound()).
                and(wrapper -> wrapper.
                        eq(LgtVendorQuotedSum::getBidResult, SelectionStatusEnum.FIRST_WIN).or().
                        eq(LgtVendorQuotedSum::getBidResult, SelectionStatusEnum.SECOND_WIN).or().
                        eq(LgtVendorQuotedSum::getBidResult, SelectionStatusEnum.WIN)));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtVendorQuotedSums),"当前轮次没有中标的供应商,不能生成结果审批单!");
        // 结项审核状态改为已提交
        lgtBiding.setEndAuditStatus(BiddingApprovalStatus.SUBMITTED.getValue());
        lgtBiding.setSummaryDescription(summaryDescription);

        // 转订单
        try {
            // 更新节点
            updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.projectApproval.getValue())
                    .setBidingId(lgtBiding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));

            List<OrderHead> orderHeads = iOrderHeadService.bidingToOrders(bidingId);
            // 回写数据更新状态
            lgtBiding.setEndAuditStatus(BiddingApprovalStatus.APPROVED.getValue());
            lgtBiding.setBidingStatus(BiddingProjectStatus.PROJECT_END.getValue());
            lgtBiding.setGeneratePurchaseApproval(YesOrNo.YES.getValue());
            if(CollectionUtils.isNotEmpty(orderHeads)){
                StringBuffer afterOrderId = new StringBuffer(); // 后续单据ID
                StringBuffer afterOrderNo = new StringBuffer(); // 后续单据编号
                orderHeads.forEach(orderHead -> {
                    afterOrderId.append(orderHead.getOrderHeadId()).append("-");
                    afterOrderNo.append(orderHead.getOrderHeadNum()).append("-");
                });

                lgtBiding.setAfterOrderId(StringUtils.left(afterOrderId.toString(), afterOrderId.length() - 1));
                lgtBiding.setAfterOrderNo(StringUtils.left(afterOrderNo.toString(), afterOrderNo.length() - 1));
                lgtBiding.setGeneratePurchaseApproval(YesOrNo.YES.getValue());
            }
            this.updateById(lgtBiding);
        } catch (Exception e) {
            log.error("物流招标转订单报错:"+e.getMessage());
            log.error("物流招标转订单报错:"+e);
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            ConcurrentHashMap<String, String> errorMsg = new ConcurrentHashMap<>();
            errorMsg.put("message", e.getClass().getName() + ": " + message);
            errorMsg.put("stackTrace", stackTrace);
            throw new BaseException("物流招标转订单报错:"+JSON.toJSONString(errorMsg));
        }

    }

    @Override
    public List<LgtFileConfig> queryLgtFileConfig(Long bidingId,Long quotedHeadId) {
        LgtBiding lgtBiding = getById(bidingId);
        if(!canSeeResultSet.contains(lgtBiding.getBidingStatus())){
            throw new BaseException("需商务评标后才能看到附件信息");
        }
        List<LgtFileConfig> lgtFileConfigs = iLgtFileConfigService.list(Wrappers.lambdaQuery(LgtFileConfig.class).
                eq(LgtFileConfig::getBidingId, bidingId).
                eq(LgtFileConfig::getReferenceFileType, BidingFileTypeEnum.COMMERCIA_BID.getCode()));
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            List<Long> requireIds = lgtFileConfigs.stream().map(LgtFileConfig::getRequireId).collect(Collectors.toList());
            List<LgtVendorFile> lgtVendorFiles = iLgtVendorFileService.list(Wrappers.lambdaQuery(LgtVendorFile.class).eq(LgtVendorFile::getQuotedHeadId, quotedHeadId).in(LgtVendorFile::getRequireId, requireIds));
            if(CollectionUtils.isNotEmpty(lgtVendorFiles)){
                Map<Long, LgtVendorFile> vendorFileMap = lgtVendorFiles.stream().collect(Collectors.toMap(LgtVendorFile::getRequireId, Function.identity()));
                lgtFileConfigs.forEach(lgtFileConfig -> {
                    LgtVendorFile lgtVendorFile = vendorFileMap.get(lgtFileConfig.getRequireId());
                    if(null != lgtVendorFile){
                        lgtFileConfig.setVendorDocId(lgtVendorFile.getDocId());
                        lgtFileConfig.setVendorFileName(lgtVendorFile.getFileName());
                    }
                });
            }
        }
        return lgtFileConfigs;
    }

    @Override
    public void invalidBid(Long quotedHeadId,String invalidReason) {
        LgtVendorQuotedHead lgtVendorQuotedHead = iLgtVendorQuotedHeadService.getById(quotedHeadId);
        Assert.notNull(lgtVendorQuotedHead,"找不到供应商报价头信息");
        Long bidingId = lgtVendorQuotedHead.getBidingId();
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"没有找到招标头信息!");
        Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"已超过投标截止时间,不能作废!");
        Assert.isTrue(lgtBiding.getCurrentRound().equals(lgtVendorQuotedHead.getRound()),"该报价不是当前轮次,不能作废!");
        Assert.isTrue(!BiddingProjectStatus.PUBLICITY_OF_RESULT.getValue().equals(lgtBiding.getBidingStatus()),"结果已公示,不能作废!");
        lgtVendorQuotedHead.setStatus(BiddingOrderStates.INVALID.getValue());
        lgtVendorQuotedHead.setInvalidReason(invalidReason);
        iLgtVendorQuotedHeadService.updateById(lgtVendorQuotedHead);
    }

    @Override
    public LgtVendorQuotedHeadDto getQuotedInfoByQuotedHeadId(Long quotedHeadId) {
        return iLgtBidingVendorService.getLgtVendorQuotedHeadByQuotedHeadId(quotedHeadId);
    }

    @Override
    @Transactional
    public void submitQuotedPrice(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        iLgtBidingVendorService.checkLgtVendorQuotedLine(lgtVendorQuotedHeadDto);
        // 检查参数
        checkSubmitQuotedPriceParam(lgtVendorQuotedHeadDto);
        // 保存基础信息
        quotedPriceSave(lgtVendorQuotedHeadDto);
        // 更新报价头表状态
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LgtVendorQuotedHead lgtVendorQuotedHead = lgtVendorQuotedHeadDto.getLgtVendorQuotedHead();
        lgtVendorQuotedHead.setStatus(BiddingOrderStates.SUBMISSION.getValue())
                .setIfProxy(YesOrNo.YES.getValue())
                .setSubmitDate(new Date())
                .setSubmitUserId(loginAppUser.getUserId())
                .setSubmitUsername(loginAppUser.getUsername())
                .setSubmitNikeName(loginAppUser.getNickname());
        iLgtVendorQuotedHeadService.updateById(lgtVendorQuotedHead);
        // 更新头表
        Long bidingId = lgtVendorQuotedHeadDto.getLgtBiding().getBidingId();
        LgtBiding lgtBiding = this.getById(bidingId);
        String biddingSuppliers = lgtBiding.getBiddingSuppliers();
        String[] split = biddingSuppliers.split("/");
        biddingSuppliers = (Integer.parseInt(split[0]) + 1) + "/" +split[1];
        lgtBiding.setBiddingSuppliers(biddingSuppliers);
        this.updateById(lgtBiding);
    }

    public void checkSubmitQuotedPriceParam(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        List<LgtFileConfig> lgtFileConfigs = lgtVendorQuotedHeadDto.getLgtFileConfigs();
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            lgtFileConfigs.forEach(lgtFileConfig -> {
                Assert.isTrue(!ObjectUtils.isEmpty(lgtFileConfig.getVendorDocId()),"附件信息不能为空!");
            });
        }
        Assert.notNull(lgtVendorQuotedHeadDto.getLgtBiding(),"参数缺少招标头信息!");

        if(YesOrNo.YES.getValue().equals(lgtVendorQuotedHeadDto.getLgtBiding().getIfVendorSubmitShipDate())){
            Assert.isTrue(CollectionUtils.isNotEmpty(lgtVendorQuotedHeadDto.getLgtBidShipPeriods()),"船期明细不能为空!");
        }

        // 检查报价行
        checkLgtVendorQuotedLines(lgtVendorQuotedHeadDto);

    }

    public void checkLgtVendorQuotedLines(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        LgtBiding lgtBiding = lgtVendorQuotedHeadDto.getLgtBiding();
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = lgtVendorQuotedHeadDto.getLgtVendorQuotedLines();
        Assert.notNull(lgtVendorQuotedLines,"投标信息不能为空!");
        Set<String> hashSet = new HashSet<>();
        lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
            LgtBidingServiceImpl.setStartEndAddress(lgtVendorQuotedLine);
            // 校验唯一 LEG +费项+计费方式+计费单位
            StringBuffer onlyKey = new StringBuffer();
            onlyKey.append(lgtVendorQuotedLine.getRowNum()).
                    append(lgtVendorQuotedLine.getLeg()).
                    append(lgtVendorQuotedLine.getExpenseItem()).
                    append(lgtVendorQuotedLine.getChargeMethod()).
                    append(lgtVendorQuotedLine.getChargeUnit());
            if(!hashSet.add(onlyKey.toString())){
                throw new BaseException(String.format("投标信息行:[%s+LEG +费项+计费方式+计费单位]存在重复!",lgtVendorQuotedLine.getStartAddress()+lgtVendorQuotedLine.getEndAddress()));
            }
        });
    }

    @Transactional
    public void quotedPriceSave(LgtVendorQuotedHeadDto lgtVendorQuotedHead) {
        // 判断状态,投标状态为“未投标”，项目状态为“接受报价中”的行，可进行该操作；
        Long bidingId = lgtVendorQuotedHead.getLgtBiding().getBidingId();
        Assert.notNull(bidingId,"缺少参数: bidingId");
        LgtBiding lgtBiding = this.getById(bidingId);
        LgtVendorQuotedHead vendorQuotedHead = lgtVendorQuotedHead.getLgtVendorQuotedHead();
        Assert.notNull(vendorQuotedHead,"报价头信息不能为空!");
        Assert.isTrue(BiddingProjectStatus.ACCEPT_BID.getValue().equals(lgtBiding.getBidingStatus()) &&
                        (BiddingOrderStates.DRAFT.getValue().equals(vendorQuotedHead.getStatus()) ||
                                BiddingOrderStates.WITHDRAW.getValue().equals(vendorQuotedHead.getStatus())),
                "只有项目状态为\"接受报价中\",投标状态为\"未投标\"的单据才能报价");

        // 保存报价行表
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = lgtVendorQuotedHead.getLgtVendorQuotedLines();
        iLgtVendorQuotedLineService.remove(new QueryWrapper<>(new LgtVendorQuotedLine().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                LgtBidingServiceImpl.setStartEndAddress(lgtVendorQuotedLine);
                lgtVendorQuotedLine.setQuotedLineId(IdGenrator.generate()).
                        setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                        .setBidingId(bidingId)
                        .setIfProxy(YesOrNo.YES.getValue())
                        .setRound(lgtBiding.getCurrentRound());
            });
            iLgtVendorQuotedLineService.saveBatch(lgtVendorQuotedLines);
            // 计算报价行信息
            calculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines);
        }

        // 保存船期
        List<LgtBidShipPeriod> lgtBidShipPeriods = lgtVendorQuotedHead.getLgtBidShipPeriods();
        iLgtBidShipPeriodService.remove(new QueryWrapper<>(new LgtBidShipPeriod().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriods)){
            List<LgtBidRequirementLine> bidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).eq(LgtBidRequirementLine::getBidingId, lgtBiding.getBidingId()));
            Map<String, LgtBidRequirementLine> requirementLineMap = bidRequirementLines.stream().collect(Collectors.toMap(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress(), Function.identity(),(k1, k2)->k1));
            HashSet<String> hashSet = new HashSet<>();
            lgtBidShipPeriods.forEach(lgtBidShipPeriod -> {
                // 校验船期路线是否与需求行匹配
                LgtBidingServiceImpl.setStartEndAddress(lgtBidShipPeriod);
                // String key = String.valueOf(lgtBidShipPeriod.getStartAddress()) + lgtBidShipPeriod.getEndAddress();
                // Assert.isTrue(hashSet.add(key),String.format("船期路线: [%s],存在重复!",key));
                // LgtBidRequirementLine requirementLine = requirementLineMap.get(key);
                // Assert.notNull(requirementLine,String.format("船期路线: [%s],在需求明细不存在!",key));

                lgtBidShipPeriod.setShipPeriodId(IdGenrator.generate())
                        .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                        .setBidingId(bidingId)
                        .setVendorId(vendorQuotedHead.getVendorId())
                        .setVendorCode(vendorQuotedHead.getVendorCode())
                        .setVendorName(vendorQuotedHead.getVendorName())
                        .setRound(lgtBiding.getCurrentRound());
            });
            iLgtBidShipPeriodService.saveBatch(lgtBidShipPeriods);
        }

        // 保存附件
        List<LgtFileConfig> lgtFileConfigs = lgtVendorQuotedHead.getLgtFileConfigs();
        iLgtVendorFileService.remove(new QueryWrapper<>(new LgtVendorFile().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            List<LgtVendorFile> lgtVendorFiles = new ArrayList<>();
            lgtFileConfigs.forEach(lgtFileConfig -> {
                if(!ObjectUtils.isEmpty(lgtFileConfig.getVendorDocId())){
                    LgtVendorFile lgtVendorFile = new LgtVendorFile()
                            .setVendorFileId(IdGenrator.generate())
                            .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                            .setVendorId(vendorQuotedHead.getVendorId())
                            .setVendorCode(vendorQuotedHead.getVendorCode())
                            .setVendorName(vendorQuotedHead.getVendorName())
                            .setRound(lgtBiding.getCurrentRound())
                            .setRequireId(lgtFileConfig.getRequireId())
                            .setDocId(lgtFileConfig.getVendorDocId())
                            .setFileName(lgtFileConfig.getVendorFileName())
                            .setFileType(lgtFileConfig.getReferenceFileType())
                            .setBidingId(bidingId);
                    lgtVendorFiles.add(lgtVendorFile);
                }
            });
            iLgtVendorFileService.saveBatch(lgtVendorFiles);
        }
    }

    public void calculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines) {
        // 获取人民币最新汇率信息  (源币种编码-汇率)
        Map<String, LatestGidailyRate> latestGidailyRateMap = getLatestGidailyRateMap(lgtBiding);

        if (CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
            // 国内业务 计算供应商报价行数据,并更新
            insideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
        }else {
            // 非国内业务 计算供应商报价行数据,并更新
            noInsideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
        }
    }

    @Override
    public LgtVendorQuotedHeadDto getQuotedInfo(Long bidingId, Long vendorId) {
        return iLgtBidingVendorService.getLgtVendorQuotedHead(bidingId, vendorId);
    }

    @Override
    public List<LgtVendorQuotedHead> agencyQuotationQueryVendor(Long bidingId) {
        /**
         * 查找没有投标的供应商
         */
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到招标头信息!");
        Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"已超过投标截止时间,不能代理报价!");
        Assert.isTrue(!BiddingProjectStatus.PUBLICITY_OF_RESULT.getValue().equals(lgtBiding.getBidingStatus()),"结果已公示,不能代理报价!");

        List<LgtVendorQuotedHead> quotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                and(wrapper->wrapper.eq(LgtVendorQuotedHead::getStatus,BiddingOrderStates.DRAFT.getValue()).or().
                        eq(LgtVendorQuotedHead::getStatus,BiddingOrderStates.WITHDRAW.getValue())));
        return quotedHeads;
    }

    @Override
    public void publicResult(Long bidingId, Integer round) {
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.isTrue(lgtBiding.getCurrentRound() == round,"数据不是当前轮次不能进行操作!");
        Assert.isTrue(!checkUserIfTechnology(bidingId),"技术评委不能操作!!!");
        /**
         * 检查是否有评选结果
         * 1. 至少有一条是入围或中标
         */
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId, bidingId).
                eq(LgtVendorQuotedSum::getRound, round));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtVendorQuotedSums),"请选择[入围]或[中标]再公示结果");
        long count = lgtVendorQuotedSums.stream().filter(lgtVendorQuotedSum ->
                YesOrNo.YES.getValue().equals(lgtVendorQuotedSum.getShortlisted()) || SelectionStatusEnum.WIN.getValue().equals(lgtVendorQuotedSum.getBidResult())).count();
        Assert.isTrue(count > 0,"请选择[入围]或[中标]再公示结果");

        // 更新轮次表
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, bidingId).
                eq(LgtRound::getRound, round));
        lgtRound.setPublicResult(YesOrNo.YES.getValue());
        lgtRound.setPublicResultTime(new Date());
        iLgtRoundService.updateById(lgtRound);

        // 更新招标头表
        lgtBiding.setBidingStatus(BiddingProjectStatus.PUBLICITY_OF_RESULT.getValue());
        this.updateById(lgtBiding);

        // 更新节点
        updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.bidEvaluation.getValue())
                .setBidingId(lgtBiding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));

    }

    @Override
    @Transactional
    public void shortlistedOrEliminated(LgtVendorQuotedSumResult lgtVendorQuotedSumResult) {
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = lgtVendorQuotedSumResult.getLgtVendorQuotedSums();
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedSums)){
            // 判断轮次是否本轮
            LgtVendorQuotedSum lgtVendorQuotedSum1 = lgtVendorQuotedSums.get(0);
            Integer round = lgtVendorQuotedSum1.getRound();
            Long bidingId = lgtVendorQuotedSum1.getBidingId();
            Assert.isTrue(!checkUserIfTechnology(bidingId),"技术评委不能操作!!!");
            LgtBiding lgtBiding = this.getById(bidingId);
            Assert.isTrue(BiddingProjectStatus.BUSINESS_EVALUATION.getValue().equals(lgtBiding.getBidingStatus()),"项目状态不是[商务开标],不能操作!");
            Assert.isTrue(lgtBiding.getCurrentRound() == round,"数据不是当前轮次不能进行操作!");
            String bidResult = lgtVendorQuotedSumResult.getBidResult();
            String shortlisted = lgtVendorQuotedSumResult.getShortlisted();
            lgtVendorQuotedSums.forEach(lgtVendorQuotedSum -> {
                Optional.ofNullable(bidResult).ifPresent(s -> lgtVendorQuotedSum.setBidResult(s));
                Optional.ofNullable(shortlisted).ifPresent(s -> lgtVendorQuotedSum.setShortlisted(s));
            });
            iLgtVendorQuotedSumService.updateBatchById(lgtVendorQuotedSums);

            // 清除决标数据
            List<LgtVendorQuotedSum> quotedSumList = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                    eq(LgtVendorQuotedSum::getBidingId, bidingId).
                    eq(LgtVendorQuotedSum::getRound, lgtBiding.getCurrentRound()));
            if(CollectionUtils.isNotEmpty(quotedSumList)){
                quotedSumList.forEach(lgtVendorQuotedSum -> {
                    if(YesOrNo.YES.getValue().equals(lgtVendorQuotedSumResult.getOperateType())){
                        lgtVendorQuotedSum.setBidResult(null);
                    }else {
                        lgtVendorQuotedSum.setShortlisted(null);
                    }
                });
                iLgtVendorQuotedSumService.updateBatchById(quotedSumList);
            }
        }
    }

    /**
     * 获取最新的本位币种汇率
     */
    public Map<String, LatestGidailyRate> getLatestGidailyRateMap(LgtBiding lgtBiding){
        Map<String, LatestGidailyRate> latestGidailyRateMap = new HashMap<>();
        // 本位币制
        String standardCurrency = lgtBiding.getStandardCurrency();
        // 汇率类型
        String exchangeRateType = lgtBiding.getExchangeRateType();
        latestGidailyRateMap = baseClient.getLatestGidailyRate(standardCurrency, exchangeRateType);
        return latestGidailyRateMap;
    }

    /**
     * 检查用户是否技术人员
     */
    public boolean checkUserIfTechnology(Long bidingId){
        boolean flag = false;
        String userName = AppUserUtil.getUserName();
        List<LgtGroup> lgtGroups = iLgtGroupService.list(new QueryWrapper<>(new LgtGroup().setBidingId(bidingId)));
        if(CollectionUtils.isNotEmpty(lgtGroups)) {
            long count = lgtGroups.stream().filter(lgtGroup -> YesOrNo.YES.getValue().equals(lgtGroup.getJudgeFlag()) && userName.equals(lgtGroup.getUserName())).count();
            if (count > 0) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    @Transactional
    public void selectionQuotedLineSave(List<LgtVendorQuotedLine> lgtVendorQuotedLines) {
        /**
         * 首次保存,会生成报价汇总信息,更新基础表和轮次表状态,并且自动分配中标
         * 非首次保存, 更新报价汇总信息, 通过供应商+始发起+目的地
         * 币种信息转换,最终全部转成人民币
         */
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            Long bidingId = lgtVendorQuotedLines.get(0).getBidingId();
//            Assert.isTrue(checkUserIfTechnology(bidingId),"非技术评委不能操作!");
            Assert.notNull(bidingId,"报价明细信息缺少参数: bidingId");
            LgtBiding lgtBiding = this.getById(bidingId);
            Assert.notNull(lgtBiding,"找不到招标头信息!");
            // 获取人民币最新汇率信息  (源币种编码-汇率)
            Map<String, LatestGidailyRate> latestGidailyRateMap = getLatestGidailyRateMap(lgtBiding);

            if (CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                // 国内业务 计算供应商报价行数据,并更新
                insideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
            }else {
                // 非国内业务 计算供应商报价行数据,并更新
                noInsideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
            }

            // 更新或保存报价汇总信息
            saveUpdateQuotedSum(lgtVendorQuotedLines, lgtBiding);
        }
    }

    /**
     * 报名截止->商务开标 更新状态
     * @param lgtBiding
     */
    @Transactional
    public void updateBusinessEvaluationStatus(LgtBiding lgtBiding) {
        lgtBiding.setBidingStatus(BiddingProjectStatus.BUSINESS_EVALUATION.getValue());
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, lgtBiding.getBidingId()).
                eq(LgtRound::getRound, lgtBiding.getCurrentRound()).last(" LIMIT 1"));
        lgtRound.setBusinessOpenBid(YesOrNo.YES.getValue());
        lgtRound.setBusinessOpenBidTime(new Date());

        // 更新招标头表项目状态
        this.updateById(lgtBiding);
        // 更新 轮次表状态
        iLgtRoundService.updateById(lgtRound);
    }


    @Transactional
    public void saveUpdateQuotedSum(List<LgtVendorQuotedLine> lgtVendorQuotedLines,LgtBiding lgtBiding) {
        // 初始化地点信息
        lgtVendorQuotedLines.forEach(LgtBidingServiceImpl::setStartEndAddress);
        /**
         * 1. 保存时进行总计的自动计算，并将供应商+起始地+目的地的金额按所有的总计金额进行汇总
         * 2. 用绿色在总计上标示第一中标供应商（最少累计总计金额的供方）
         * 3. 用黄色在总计上标示第二中标供应商（第二少的总计金额的供方）
         */
        // 报价汇总信息声明
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = new ArrayList<>();
        Map<String, List<LgtVendorQuotedLine>> listMap = null;
        if (BidingAwardWayEnum.COMBINED_DECISION.name().equals(lgtBiding.getBidingAwardWay())) {
            // 按供应商分组
            listMap = lgtVendorQuotedLines.stream().collect(Collectors.groupingBy(
                    lgtVendorQuotedLine -> String.valueOf(lgtVendorQuotedLine.getVendorId())));
        }else {
            // 根据供应商+始发起+目的地分组
            listMap = lgtVendorQuotedLines.stream().collect(Collectors.groupingBy(
                    lgtVendorQuotedLine -> lgtVendorQuotedLine.getVendorId() + lgtVendorQuotedLine.getStartAddress() + lgtVendorQuotedLine.getEndAddress()));
        }

        listMap.forEach((key, lgtVendorQuotedLineList) -> {
            // 获取供应商报价行信息
            LgtVendorQuotedLine lgtVendorQuotedLine = lgtVendorQuotedLineList.get(0);
            // 供应商单路线总价
            BigDecimal sumPrice = lgtVendorQuotedLineList.stream().filter(lgtVendorQuotedLine1 -> !ObjectUtils.isEmpty(lgtVendorQuotedLine1.getTotalAmount())).
                    map(LgtVendorQuotedLine::getTotalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

            // 构建报价明细汇总
            LgtVendorQuotedSum lgtVendorQuotedSum = new LgtVendorQuotedSum()
                    .setQuotedSumId(IdGenrator.generate())
                    .setBidingId(lgtBiding.getBidingId())
                    .setQuotedHeadId(lgtVendorQuotedLine.getQuotedHeadId())
                    .setVendorId(lgtVendorQuotedLine.getVendorId())
                    .setVendorCode(lgtVendorQuotedLine.getVendorCode())
                    .setVendorName(lgtVendorQuotedLine.getVendorName())
                    .setStartAddress(lgtVendorQuotedLine.getStartAddress())
                    .setEndAddress(lgtVendorQuotedLine.getEndAddress())
                    .setSumPrice(sumPrice)
                    .setIfProxy(lgtVendorQuotedLine.getIfProxy())
                    .setRowNum(lgtVendorQuotedLine.getRowNum())
                    .setBidRequirementLineId(lgtVendorQuotedLine.getBidRequirementLineId())
                    .setRound(lgtVendorQuotedLine.getRound());
            lgtVendorQuotedSums.add(lgtVendorQuotedSum);
        });

        // 保存报价汇总
//        saveLgtQuoteSum(lgtBiding, lgtVendorQuotedSums);
        saveLgtQuoteSumNew(lgtBiding, lgtVendorQuotedSums);
    }

    @Transactional
    public void saveLgtQuoteSumNew(LgtBiding lgtBiding, List<LgtVendorQuotedSum> lgtVendorQuotedSums){
        // 把原来的删除再新增
        iLgtVendorQuotedSumService.remove(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedSum::getRound, lgtBiding.getCurrentRound()));
        // 新增
        iLgtVendorQuotedSumService.saveBatch(lgtVendorQuotedSums);
    }

    @Transactional
    public void saveLgtQuoteSum(LgtBiding lgtBiding, List<LgtVendorQuotedSum> lgtVendorQuotedSums) {
        List<LgtVendorQuotedSum> vendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedSum::getRound, lgtBiding.getCurrentRound()));
        if(CollectionUtils.isNotEmpty(vendorQuotedSums)){
            // 更新总计
            Map<String, LgtVendorQuotedSum> quotedSumMap = null;
            if (BidingAwardWayEnum.COMBINED_DECISION.name().equals(lgtBiding.getBidingAwardWay())) {
                // 组合决标
                quotedSumMap = lgtVendorQuotedSums.stream().collect(Collectors.toMap(lgtVendorQuotedSum -> String.valueOf(lgtVendorQuotedSum.getVendorId()), Function.identity()));
            }else {
                quotedSumMap = lgtVendorQuotedSums.stream().collect(Collectors.toMap(lgtVendorQuotedSum -> lgtVendorQuotedSum.getVendorId() +lgtVendorQuotedSum.getStartAddress()+lgtVendorQuotedSum.getEndAddress(), Function.identity()));
            }
            Map<String, LgtVendorQuotedSum> finalQuotedSumMap = quotedSumMap;
            vendorQuotedSums.forEach(lgtVendorQuotedSum -> {
                LgtVendorQuotedSum lgtVendorQuotedSum1 = null;
                if (BidingAwardWayEnum.COMBINED_DECISION.name().equals(lgtBiding.getBidingAwardWay())) {
                    // 组合决标
                    lgtVendorQuotedSum1 = finalQuotedSumMap.get(String.valueOf(lgtVendorQuotedSum.getVendorId()));
                }else {
                    lgtVendorQuotedSum1 = finalQuotedSumMap.get(lgtVendorQuotedSum.getVendorId() +lgtVendorQuotedSum.getStartAddress()+lgtVendorQuotedSum.getEndAddress());
                }
                if(null != lgtVendorQuotedSum1){
                    lgtVendorQuotedSum.setSumPrice(lgtVendorQuotedSum1.getSumPrice());
                }
            });
            iLgtVendorQuotedSumService.updateBatchById(vendorQuotedSums);
        }else {
            iLgtVendorQuotedSumService.saveBatch(lgtVendorQuotedSums);
        }
    }

    public Map<Long, LgtVendorQuotedHead> getQuotedHeadMap(LgtBiding lgtBiding) {
        List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()));
        return lgtVendorQuotedHeads.stream().collect(Collectors.toMap(LgtVendorQuotedHead::getQuotedHeadId, Function.identity()));
    }

    @Override
    @Transactional
    public LgtVendorQuotedSumDto queryLgtVendorQuotedSumDto(Long bidingId,Integer round,Long vendorId,Integer rank,String bidResult) {
        LgtVendorQuotedSumDto lgtVendorQuotedSumDto = new LgtVendorQuotedSumDto();

        LgtBiding lgtBiding = this.getById(bidingId);

        Assert.notNull(lgtBiding,"找不到物流招标头信息!");
        //评选界面界面的内容在商务开标之后相关的状态方可查看，其他之前的状态不允许查看
        if(!canSeeResultSet.contains(lgtBiding.getBidingStatus())){
            return null;
        }
        lgtVendorQuotedSumDto.setTechnoSelection(lgtBiding.getSummaryDescription());

        // 获取本位币最新汇率信息  (源币种编码-汇率)
        Map<String, LatestGidailyRate> latestGidailyRateMap = getLatestGidailyRateMap(lgtBiding);

        // 判断截止时间是否到期
        if(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) < 0){
            if(BiddingProjectStatus.TENDER_ENDING.getValue().equals(lgtBiding.getBidingStatus())){
                // 初始化评选报价行
                initSelectionQuotedLine(lgtBiding, latestGidailyRateMap);
            }

            // 查找已投标的供应商
            List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                    eq(LgtVendorQuotedHead::getBidingId, bidingId).
                    eq(LgtVendorQuotedHead::getRound, round).
                    eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()).
                    eq(!ObjectUtils.isEmpty(vendorId),LgtVendorQuotedHead::getVendorId, vendorId));

            if(CollectionUtils.isNotEmpty(lgtVendorQuotedHeads)) {
                List<Long> quotedHeadIds = lgtVendorQuotedHeads.stream().map(LgtVendorQuotedHead::getQuotedHeadId).collect(Collectors.toList());
                // 查找已投标的供应商报价明细
                List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                        eq(LgtVendorQuotedLine::getBidingId, bidingId).
                        eq(LgtVendorQuotedLine::getRound, round).
                        in(LgtVendorQuotedLine::getQuotedHeadId, quotedHeadIds));
                // 设置始-终
                lgtVendorQuotedLines.forEach(LgtBidingServiceImpl::setStartEndAddress);
                /**
                 * 陆运增加按需求明细的单拖成本进行排序
                 */
                if(TransportModeEnum.LAND_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
                    List<LgtVendorQuotedLine> quotedLines = new ArrayList<>();
                    // 单拖成本为空
                    List<LgtVendorQuotedLine> singleDragCostNulls = lgtVendorQuotedLines.stream().filter(lgtVendorQuotedLine -> ObjectUtils.isEmpty(lgtVendorQuotedLine.getSingleDragCost())).collect(Collectors.toList());
                    // 单拖成本不为空
                    List<LgtVendorQuotedLine> singleDragCostNoNulls = lgtVendorQuotedLines.stream().filter(lgtVendorQuotedLine -> !ObjectUtils.isEmpty(lgtVendorQuotedLine.getSingleDragCost())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(singleDragCostNoNulls)) {
                        // 分组排序
                        singleDragCostNoNulls.sort(Comparator.comparing(LgtVendorQuotedLine::getSingleDragCost));
                        quotedLines.addAll(singleDragCostNoNulls);
                    }
                    if(CollectionUtils.isNotEmpty(singleDragCostNulls)) quotedLines.addAll(singleDragCostNulls);
                    lgtVendorQuotedSumDto.setLgtVendorQuotedLines(quotedLines);
                }else {
                    lgtVendorQuotedSumDto.setLgtVendorQuotedLines(lgtVendorQuotedLines);
                }

                // 供应商报价汇总表
                List<LgtVendorQuotedSum> lgtVendorQuotedSumsResult = new ArrayList<>();
                // 获取评选汇总信息
                List<LgtVendorQuotedSum> lgtVendorQuotedSums = getLgtVendorQuotedSum(bidingId, round, vendorId, lgtBiding, lgtVendorQuotedLines);
                // 按路线分组排序
                if(CollectionUtils.isNotEmpty(lgtVendorQuotedSums)){
                    if(BidingAwardWayEnum.COMBINED_DECISION.name().equals(lgtBiding.getBidingAwardWay())){
                        // 组合
                        AtomicInteger num = new AtomicInteger(1);
                        lgtVendorQuotedSums.sort(Comparator.comparing(LgtVendorQuotedSum::getSumPrice));
                        lgtVendorQuotedSums.forEach(lgtVendorQuotedSum -> lgtVendorQuotedSum.setRank(num.getAndAdd(1)));
                        lgtVendorQuotedSumsResult.addAll(lgtVendorQuotedSums);
                    }else {
                        List<BigDecimal> sumPriceList = lgtVendorQuotedSums.stream().map(LgtVendorQuotedSum::getSumPrice).distinct().collect(Collectors.toList());
                        // 价格排序
                        sumPriceList.sort(BigDecimal::compareTo);
                        Map<String, List<LgtVendorQuotedSum>> listMap = lgtVendorQuotedSums.stream().collect(Collectors.groupingBy(lgtVendorQuotedSum -> lgtVendorQuotedSum.getStartAddress() + lgtVendorQuotedSum.getEndAddress()));
                        listMap.forEach((s, lgtVendorQuotedSums1) -> {
                            AtomicInteger integer = new AtomicInteger(1);
                            /** 价格分组 */
                            Map<BigDecimal, List<LgtVendorQuotedSum>> map = lgtVendorQuotedSums1.stream().collect(Collectors.groupingBy(LgtVendorQuotedSum::getSumPrice));
                            sumPriceList.forEach(price->{
                                List<LgtVendorQuotedSum> vendorQuotedSums = map.get(price);
                                if(CollectionUtils.isNotEmpty(vendorQuotedSums)){
                                    int num = integer.getAndAdd(1);
                                    vendorQuotedSums.forEach(lgtVendorQuotedSum -> {
                                        lgtVendorQuotedSum.setRank(num);
                                    });
                                    lgtVendorQuotedSumsResult.addAll(vendorQuotedSums);
                                }
                            });
                        });
                    }
                }
                lgtVendorQuotedSumDto.setLgtVendorQuotedSums(lgtVendorQuotedSumsResult);
            }
        }

        /**
         * 根据排名和中标结果过来汇总信息
         */
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = lgtVendorQuotedSumDto.getLgtVendorQuotedSums();
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedSums)){
            if(null != rank){
                lgtVendorQuotedSums = lgtVendorQuotedSums.stream().filter(lgtVendorQuotedSum -> rank == lgtVendorQuotedSum.getRank()).collect(Collectors.toList());
            }
            if(null != bidResult){
                lgtVendorQuotedSums = lgtVendorQuotedSums.stream().filter(lgtVendorQuotedSum -> bidResult.equals(lgtVendorQuotedSum.getBidResult())).collect(Collectors.toList());
            }
            lgtVendorQuotedSumDto.setLgtVendorQuotedSums(lgtVendorQuotedSums);
        }
        return lgtVendorQuotedSumDto;
    }

    public List<LgtVendorQuotedSum> getLgtVendorQuotedSum(Long bidingId, Integer round, Long vendorId, LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines) {
        List<LgtVendorQuotedSum> lgtVendorQuotedSums;
        lgtVendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId, bidingId).
                eq(LgtVendorQuotedSum::getRound, round).
                eq(!ObjectUtils.isEmpty(vendorId), LgtVendorQuotedSum::getVendorId, vendorId));
        if (CollectionUtils.isEmpty(lgtVendorQuotedSums)) {
            lgtVendorQuotedSums = new ArrayList<>();
            // 初始化供应商报价汇总
            List<LgtVendorQuotedSum> lgtVendorQuotedSumList = new ArrayList<>();
            Map<String, List<LgtVendorQuotedLine>> listMap;
            /**
             * 根据决标方式分类汇总
             * 单项决标: 按: 供应商+始发起+目的地  分组
             * 组合决标: 按: 供应商  分组
             */
            if(BidingAwardWayEnum.COMBINED_DECISION.name().equals(lgtBiding.getBidingAwardWay())){
                // 组合
                 listMap = lgtVendorQuotedLines.stream().collect(Collectors.groupingBy(
                        lgtVendorQuotedLine -> String.valueOf(lgtVendorQuotedLine.getVendorId())));
            }else {
                // 根据供应商+始发起+目的地分组
                listMap = lgtVendorQuotedLines.stream().collect(Collectors.groupingBy(
                        lgtVendorQuotedLine -> lgtVendorQuotedLine.getVendorId() + lgtVendorQuotedLine.getStartAddress()+lgtVendorQuotedLine.getEndAddress()));
            }

            listMap.forEach((key, lgtVendorQuotedLineList) -> {
                // 获取供应商报价行信息
                LgtVendorQuotedLine lgtVendorQuotedLine = lgtVendorQuotedLineList.get(0);
                // 供应商单路线总价
                BigDecimal sumPrice = lgtVendorQuotedLineList.stream().filter(lgtVendorQuotedLine1 -> !ObjectUtils.isEmpty(lgtVendorQuotedLine1.getTotalAmount())).
                        map(LgtVendorQuotedLine::getTotalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

                // 构建报价明细汇总
                LgtVendorQuotedSum lgtVendorQuotedSum = new LgtVendorQuotedSum()
                        .setQuotedSumId(IdGenrator.generate())
                        .setBidingId(lgtBiding.getBidingId())
                        .setQuotedHeadId(lgtVendorQuotedLine.getQuotedHeadId())
                        .setVendorId(lgtVendorQuotedLine.getVendorId())
                        .setVendorCode(lgtVendorQuotedLine.getVendorCode())
                        .setVendorName(lgtVendorQuotedLine.getVendorName())
                        .setStartAddress(lgtVendorQuotedLine.getStartAddress())
                        .setEndAddress(lgtVendorQuotedLine.getEndAddress())
                        .setSumPrice(sumPrice)
                        .setIfProxy(lgtVendorQuotedLine.getIfProxy())
                        .setRowNum(lgtVendorQuotedLine.getRowNum())
                        .setBidRequirementLineId(lgtVendorQuotedLine.getBidRequirementLineId())
                        .setRound(lgtVendorQuotedLine.getRound());
                lgtVendorQuotedSumList.add(lgtVendorQuotedSum);
            });
            lgtVendorQuotedSums.addAll(lgtVendorQuotedSumList);
            iLgtVendorQuotedSumService.saveBatch(lgtVendorQuotedSums);
        }
        return lgtVendorQuotedSums;
    }

    @Transactional
    public void initSelectionQuotedLine(LgtBiding lgtBiding, Map<String, LatestGidailyRate> latestGidailyRateMap) {
        // 获取当前轮次有投标的供应商报价头
        List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()));
        if (CollectionUtils.isNotEmpty(lgtVendorQuotedHeads)) {
            List<Long> quotedHeadIds = lgtVendorQuotedHeads.stream().map(LgtVendorQuotedHead::getQuotedHeadId).collect(Collectors.toList());
            // 获取当前轮次有投标的供应商报价行
            List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                    eq(LgtVendorQuotedLine::getBidingId, lgtBiding.getBidingId()).
                    eq(LgtVendorQuotedLine::getRound, lgtBiding.getCurrentRound()).
                    in(LgtVendorQuotedLine::getQuotedHeadId, quotedHeadIds));
            if (CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                // 国内业务 计算供应商报价行数据,并更新
                insideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
            }else {
                // 非国内业务 计算供应商报价行数据,并更新
                noInsideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
            }
        }
    }

    /**
     * 计算供应商报价行数据,并更新
     * @param lgtBiding
     * @param lgtVendorQuotedLines
     */
    @Transactional
    public void noInsideCalculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines,Map<String, LatestGidailyRate> latestGidailyRateMap) {
        // 价格精度
        Integer pricePrecision = lgtBiding.getPricePrecision();
        /**
         * 初始化
         * 1. 调整数量默认供方填写数量，采购员可以再次修改数量，总计（人民币）=数量*（费率*汇率）
         * 2. 存在最小费用时，则数量*费率<最小费用，取最小费用
         * 3. 存在最大费用时，则数量*费用>最大费用，取最大费用
         * 4. 保存时进行总计的自动计算，并将供应商+起始地+目的地的金额按所有的总计金额进行汇总
         * BigDecimal.ROUND_HALF_UP
         */
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                // 检查汇率是否存在
                BigDecimal conversionRate = checkCurrencyIfExist(latestGidailyRateMap, lgtVendorQuotedLine,lgtBiding);
                // 检查费率是否非空
                BigDecimal expense = lgtVendorQuotedLine.getExpense();
                if(!ObjectUtils.isEmpty(expense)){
                    // 数量默认1
                    lgtVendorQuotedLine.setNumber(null != lgtVendorQuotedLine.getNumber()?lgtVendorQuotedLine.getNumber():BigDecimal.valueOf(1));
                    // 总计（人民币）=数量*（费率*汇率）
                    lgtVendorQuotedLine.setTotalAmount(getTotalAmount(pricePrecision, lgtVendorQuotedLine, conversionRate, expense));
                }
            });
            // 更新报价行数据
            iLgtVendorQuotedLineService.updateBatchById(lgtVendorQuotedLines);
        }
    }

    /**
     * 计算供应商报价行数据,并更新
     * @param lgtBiding
     * @param lgtVendorQuotedLines
     */
    @Transactional
    public void insideCalculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines,Map<String, LatestGidailyRate> latestGidailyRateMap) {
        // 价格精度
        Integer pricePrecision = lgtBiding.getPricePrecision();
        /**
         * 初始化字段
         * 1. 运输距离调整默认供方运输距离
         * 2. 单公里成本=费率/运输距离调整
         * 3. 单拖成本=费率/装载量
         * 4. 数量默认1
         * 5. 总计（人民币）=数量*（费率*汇率）
         * 6. 存在最小费用时，则数量*费率<最小费用，取最小费用
         * 7. 存在最大费用时，则数量*费用>最大费用，取最大费用
         * 8. 保存时进行总计的自动计算，并将供应商+起始地+目的地的金额按所有的总计金额进行汇总
         * 9. 用绿色在总计上标示第一中标供应商（最少累计总计金额的供方）
         * 10. 用黄色在总计上标示第二中标供应商（第二少的总计金额的供方）
         * BigDecimal.ROUND_HALF_UP
         */
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                // 检查汇率是否存在
                BigDecimal conversionRate = checkCurrencyIfExist(latestGidailyRateMap, lgtVendorQuotedLine,lgtBiding);
                // 检查费率是否非空
                BigDecimal expense = lgtVendorQuotedLine.getExpense();
                if(!ObjectUtils.isEmpty(expense)){
                    // 运输距离调整 = 供方运输距离
                    if (ObjectUtils.isEmpty(lgtVendorQuotedLine.getTransportDistanceRevision())) {
                        if (BiddingProjectStatus.ACCEPT_BID.getValue().equals(lgtBiding.getBidingStatus()) ||
                                BiddingProjectStatus.TENDER_ENDING.getValue().equals(lgtBiding.getBidingStatus())) {
                            lgtVendorQuotedLine.setTransportDistanceRevision(lgtVendorQuotedLine.getTransportDistance());
                        }
                    }
                    // 单公里成本= 费率/ 运输距离调整
                    lgtVendorQuotedLine.setSingleKmCost(null != lgtVendorQuotedLine.getTransportDistance() && !ObjectUtils.isEmpty(lgtVendorQuotedLine.getTransportDistanceRevision())?expense.divide(new BigDecimal(lgtVendorQuotedLine.getTransportDistanceRevision()),pricePrecision,BigDecimal.ROUND_HALF_UP):null);
                    // 单拖成本=费率/装载量
                    lgtVendorQuotedLine.setSingleDragCost(null != lgtVendorQuotedLine.getLoadNumber()?expense.divide(lgtVendorQuotedLine.getLoadNumber(),pricePrecision,BigDecimal.ROUND_HALF_UP):null);
                    // 数量默认1
                    lgtVendorQuotedLine.setNumber(null != lgtVendorQuotedLine.getNumber()?lgtVendorQuotedLine.getNumber():BigDecimal.valueOf(1));
                    // 总计（人民币）=数量*（费率*汇率）
                    lgtVendorQuotedLine.setTotalAmount(getTotalAmount(pricePrecision, lgtVendorQuotedLine, conversionRate, expense));
                }
            });
            // 更新报价行数据
            iLgtVendorQuotedLineService.updateBatchById(lgtVendorQuotedLines);
        }
    }

    /**
     * 计算总计
     * @param pricePrecision
     * @param lgtVendorQuotedLine
     * @param conversionRate
     * @param expense
     */
    public BigDecimal getTotalAmount(Integer pricePrecision, LgtVendorQuotedLine lgtVendorQuotedLine, BigDecimal conversionRate, BigDecimal expense) {
        BigDecimal totalAmountTemp = lgtVendorQuotedLine.getNumber().multiply(expense);
        /**
         * 存在最小费用时，则数量*费率<最小费用，取最小费用
         * 存在最大费用时，则数量*费用>最大费用，取最大费用
         */
        String minCost = lgtVendorQuotedLine.getMinCost();
        if(!ObjectUtils.isEmpty(minCost)){
            if(totalAmountTemp.compareTo(new BigDecimal(minCost)) < 0){
                totalAmountTemp = new BigDecimal(minCost);
            }
        }
        String maxCost = lgtVendorQuotedLine.getMaxCost();
        if(!ObjectUtils.isEmpty(maxCost)){
            if(totalAmountTemp.compareTo(new BigDecimal(maxCost)) > 0){
                totalAmountTemp = new BigDecimal(maxCost);
            }
        }
        return totalAmountTemp.multiply(conversionRate).setScale(pricePrecision,BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal checkCurrencyIfExist(Map<String, LatestGidailyRate> latestGidailyRateMap, LgtVendorQuotedLine lgtVendorQuotedLine,LgtBiding lgtBiding) {
        BigDecimal conversionRate = BigDecimal.valueOf(1);
        if(!ObjectUtils.isEmpty(lgtVendorQuotedLine.getCurrency()) && !lgtBiding.getStandardCurrency().equals(lgtVendorQuotedLine.getCurrency())){
            LatestGidailyRate latestGidailyRate = latestGidailyRateMap.get(lgtVendorQuotedLine.getCurrency());
            Assert.notNull(latestGidailyRate,lgtVendorQuotedLine.getVendorName()+"的币制:"+lgtVendorQuotedLine.getCurrency()+",不存在本位币:"+lgtBiding.getStandardCurrency()+"的汇率!");
            conversionRate = latestGidailyRate.getConversionRate();
        }
        return conversionRate;
    }

    @Override
    @Transactional
    public void bidOpeningCalculationResult(LgtBiding lgtBiding) {
        /**
         * 1. 检查项目状态是否接受投标中
         * 2. 检查投标截止时间是否结束
         */
        if(BiddingProjectStatus.TENDER_ENDING.getValue().equals(lgtBiding.getBidingStatus())
        && lgtBiding.getEnrollEndDatetime().compareTo(new Date()) < 0){
            // 供应报价汇总
            List<LgtVendorQuotedSum> lgtVendorQuotedSums = getLgtVendorQuotedSums(lgtBiding);
            iLgtVendorQuotedSumService.saveBatch(lgtVendorQuotedSums);
        }
    }

    @Override
    public LgtVendorQuotedHeadVo getTopInfo(Long bidingId) {
        LgtVendorQuotedHeadVo lgtVendorQuotedHeadVo = new LgtVendorQuotedHeadVo();
        BidControlTopInfoVO bidControlTopInfoVO = new BidControlTopInfoVO();
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到物流招标头信息!");
        // 本轮投标截止时间
        bidControlTopInfoVO.setEndTime(lgtBiding.getEnrollEndDatetime());
        List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()));
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedHeads)){
            /**
             * private Integer currentRoundSupplierCount;//本轮需投标的供应商数量
             *     private Integer submitSupplierCount;//已提交投标供应商数量
             *     private Date endTime;//本轮投标截止时间
             */
            // 本轮需投标的供应商数量
            bidControlTopInfoVO.setCurrentRoundSupplierCount(lgtVendorQuotedHeads.size());
            // 已投标供应商数量
            long count = lgtVendorQuotedHeads.stream().filter(lgtVendorQuotedHead -> BiddingOrderStates.SUBMISSION.getValue().equals(lgtVendorQuotedHead.getStatus())).count();
            bidControlTopInfoVO.setSubmitSupplierCount((int)count);
        }else {
            bidControlTopInfoVO.setCurrentRoundSupplierCount(0);
            bidControlTopInfoVO.setSubmitSupplierCount(0);
        }
        // 获取延长截止时间原因
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, bidingId).
                eq(LgtRound::getRound, lgtBiding.getCurrentRound()).last("LIMIT 1"));
        bidControlTopInfoVO.setExtendReason(lgtRound.getExtendReason());

        lgtVendorQuotedHeadVo.setBidControlTopInfoVO(bidControlTopInfoVO);
        lgtVendorQuotedHeadVo.setLgtVendorQuotedHeads(lgtVendorQuotedHeads);
        return lgtVendorQuotedHeadVo;
    }

    public List<LgtVendorQuotedSum> getLgtVendorQuotedSums(LgtBiding lgtBiding) {
        List<LgtVendorQuotedSum> lgtVendorQuotedSums = new ArrayList<>();
        /**
         * 获取币种汇率
         */
        // 本位币制
        String standardCurrency = lgtBiding.getStandardCurrency();
        // 保留小数位
        Integer pricePrecision = lgtBiding.getPricePrecision();
        // 汇率转换日期
        LocalDate currencyChangeDate = lgtBiding.getCurrencyChangeDate();
        // 汇率类型
        String exchangeRateType = lgtBiding.getExchangeRateType();
        Map<String, LatestGidailyRate> latestGidailyRate = baseClient.getLatestGidailyRate(standardCurrency, exchangeRateType);

        /**
         * 区分国内的和海外的进行计算
         */
        // 查询已投标的供应商
        List<LgtVendorQuotedHead> vendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()));
        if (CollectionUtils.isNotEmpty(vendorQuotedHeads)) {
            if(!CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                List<Long> ids = vendorQuotedHeads.stream().map(LgtVendorQuotedHead::getQuotedHeadId).collect(Collectors.toList());
                Map<Long, LgtVendorQuotedHead> vendorQuotedHeadMap = vendorQuotedHeads.stream().collect(Collectors.toMap(LgtVendorQuotedHead::getQuotedHeadId, Function.identity()));
                /**
                 * 根据供应商,始发地,目的地进行分组汇总
                 */
                List<LgtVendorQuotedLine> vendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                        in(LgtVendorQuotedLine::getQuotedHeadId, ids));
                if (CollectionUtils.isNotEmpty(vendorQuotedLines)) {
                    Map<String, List<LgtVendorQuotedLine>> listMap = vendorQuotedLines.stream().collect(Collectors.groupingBy(
                            lgtVendorQuotedLine -> lgtVendorQuotedLine.getQuotedHeadId() + lgtVendorQuotedLine.getStartAddress() + lgtVendorQuotedLine.getEndAddress()));
                    List<LgtVendorQuotedSum> finalLgtVendorQuotedSums = new ArrayList<>();
                    listMap.forEach((s, lgtVendorQuotedLines) -> {
                        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
                            LgtVendorQuotedLine quotedLine = lgtVendorQuotedLines.get(0);
                            LgtVendorQuotedHead vendorQuotedHead = vendorQuotedHeadMap.get(quotedLine.getQuotedHeadId());
                            LgtVendorQuotedSum lgtVendorQuotedSum = new LgtVendorQuotedSum();
                            AtomicReference<BigDecimal> sumPrice = new AtomicReference<>(BigDecimal.ZERO);
                            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                                // 总金额
                                BigDecimal totalAmount = lgtVendorQuotedLine.getTotalAmount();
                                // 币制
                                String currency = lgtVendorQuotedLine.getCurrency();
                                if(!ObjectUtils.isEmpty(currency)){
                                    /**
                                     * 检查汇率是否存在
                                     */
                                    LatestGidailyRate gidailyRate = latestGidailyRate.get(currency);
                                    if(null == gidailyRate){
                                        throw new BaseException(vendorQuotedHead.getVendorName()+"的报价币种:\""+currency+"\"与本位币种不存在汇率信息");
                                    }
                                    // 汇率转换保留精度小数
                                    sumPrice.set(sumPrice.get().add(totalAmount.multiply(gidailyRate.getConversionRate()).setScale(pricePrecision,BigDecimal.ROUND_HALF_UP)));
                                }else {
                                    sumPrice.set(sumPrice.get().add(totalAmount.setScale(pricePrecision,BigDecimal.ROUND_HALF_UP)));
                                }
                            });
                            lgtVendorQuotedSum.setQuotedSumId(IdGenrator.generate())
                                    .setBidingId(lgtBiding.getBidingId())
                                    .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                                    .setVendorId(vendorQuotedHead.getVendorId())
                                    .setVendorCode(vendorQuotedHead.getVendorCode())
                                    .setVendorName(vendorQuotedHead.getVendorName())
                                    .setStartAddress(quotedLine.getStartAddress())
                                    .setEndAddress(quotedLine.getEndAddress())
                                    .setSumPrice(sumPrice.get())
                                    .setIfProxy(vendorQuotedHead.getIfProxy())
                                    .setRowNum(quotedLine.getRowNum())
                                    .setRound(vendorQuotedHead.getRound());
                            finalLgtVendorQuotedSums.add(lgtVendorQuotedSum);
                        }
                    });
                    lgtVendorQuotedSums = finalLgtVendorQuotedSums;
                }
            }
            // 报价汇总排名
            lgtVendorQuotedSums = quotedSumsRank(lgtVendorQuotedSums,lgtBiding);
        }
        return lgtVendorQuotedSums;
    }

    // 排名
    public List<LgtVendorQuotedSum> quotedSumsRank(List<LgtVendorQuotedSum> lgtVendorQuotedSums,LgtBiding lgtBiding) {
//        // 如果不是投标截止状态就不更新排名了
//        if(!BiddingProjectStatus.TENDER_ENDING.getValue().equals(lgtBiding.getBidingStatus())){
//            return lgtVendorQuotedSums;
//        }
//
//        List<LgtVendorQuotedSum> quotedSumArrayList = new ArrayList<>();
//        /**
//         * 用绿色在总计上标示第一中标供应商（最少累计总计金额的供方）
//         * 用黄色在总计上标示第二中标供应商（第二少的总计金额的供方）
//         */
//        if(CollectionUtils.isNotEmpty(lgtVendorQuotedSums)){
//            if (lgtVendorQuotedSums.size() == 1) {
//                quotedSumArrayList.add(lgtVendorQuotedSums.get(0).setBidResult(SelectionStatusEnum.FIRST_WIN.getValue()));
//            }else {
//                // 根据汇总价分组
//                Map<BigDecimal, List<LgtVendorQuotedSum>> listMap = lgtVendorQuotedSums.stream().collect(Collectors.groupingBy(LgtVendorQuotedSum::getSumPrice));
//                List<BigDecimal> sumPrices = lgtVendorQuotedSums.stream().map(LgtVendorQuotedSum::getSumPrice).distinct().collect(Collectors.toList());
//                // 排序
//                sumPrices.sort(BigDecimal::compareTo);
//                AtomicInteger integer = new AtomicInteger(0);
//                for (int i = 0;i< sumPrices.size();i++){
//                    integer.set(i);
//                    List<LgtVendorQuotedSum> quotedSums = listMap.get(sumPrices.get(i));
//                    if(CollectionUtils.isNotEmpty(quotedSums)){
//                        quotedSums.forEach(lgtVendorQuotedSum -> {
//                            if (0 == integer.get()) {
//                                lgtVendorQuotedSum.setBidResult(SelectionStatusEnum.FIRST_WIN.getValue());
//                            }else if(1 == integer.get()){
//                                lgtVendorQuotedSum.setBidResult(SelectionStatusEnum.SECOND_WIN.getValue());
//                            }
//                            quotedSumArrayList.add(lgtVendorQuotedSum);
//                        });
//                    }
//                }
//            }
//        }
//        return quotedSumArrayList;
        return lgtVendorQuotedSums;
    }

    @Override
    public void dueImmediately(Long bidingId) {
        Assert.notNull(bidingId,"缺少参数: bidingId");
        LgtBiding biding = this.getById(bidingId);
        int currentRound = biding.getCurrentRound();
        // 更新轮次表
        Date endDate = new Date();
        LgtRound lgtRound = iLgtRoundService.getOne(new QueryWrapper<>(new LgtRound().
                setBidingId(bidingId).setRound(currentRound)));
        lgtRound.setEndTime(endDate);
        iLgtRoundService.updateById(lgtRound);
        // 更新头表
        biding.setEnrollEndDatetime(endDate);
        biding.setBidingStatus(BiddingProjectStatus.TENDER_ENDING.getValue());
        this.updateById(biding);

        /**
         * 更新流程节点
         */
        updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.technicalManagement.getValue())
                .setBidingId(biding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));
        updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.commercialManagement.getValue())
                .setBidingId(biding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));
    }

    /**
     * 供应商查看招标详情
     * @param bidingId
     * @return
     */
    @Override
    public LgtBidInfoVO supplierDetails(Long bidingId) {
        //校验参数
        if(Objects.isNull(bidingId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("招标id不可为空"));
        }

        //校验是否供应商登录
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            throw new BaseException(LocaleHandler.getLocaleMsg("请使用供应商账号登录"));
        }
        //获取供应商id
        Long companyId = loginAppUser.getCompanyId();
        if(Objects.isNull(companyId)){
            throw new BaseException(LocaleHandler.getLocaleMsg(String.format("当前登录人的companyId为空,userName=[%s]",loginAppUser.getUsername())));
        }

        //招标项目基础信息
        LgtBiding lgtBiding = this.getById(bidingId);

        //招标项目附件信息
        List<LgtBidFile> bidFileList = iLgtFileService.list(
                new QueryWrapper<LgtBidFile>(new LgtBidFile().setBidingId(bidingId)
        ));

        //需求明细表
        List<LgtBidRequirementLine> bidRequirementLineList = iLgtBidRequirementLineService.list(
                new QueryWrapper<LgtBidRequirementLine>(new LgtBidRequirementLine().setBidingId(bidingId))
        );

        //投标结果(获取指定供应商，轮次为最后一轮的供应商报价结果)
        List<LgtVendorQuotedSum> vendorQuotedSumList = iLgtVendorQuotedSumService.listCurrency(new LgtVendorQuotedSum().setBidingId(bidingId).setVendorId(companyId));

        //供应商报价信息
        LgtVendorQuotedInfoVO vendorQuotedInfoVO = new LgtVendorQuotedInfoVO();

        //供应商报价头表
        List<LgtVendorQuotedHead> vendorQuotedHeadList = iLgtVendorQuotedHeadService.listCurrency(new LgtVendorQuotedHead().setBidingId(bidingId).setVendorId(companyId));
        LgtVendorQuotedHead vendorQuotedHead = null;
        if(CollectionUtils.isNotEmpty(vendorQuotedHeadList)){
            vendorQuotedHead = vendorQuotedHeadList.get(0);
        }

        //船期信息
        List<LgtBidShipPeriod> bidShipPeriodList = new LinkedList<>();
        if(Objects.nonNull(vendorQuotedHead) && Objects.nonNull(vendorQuotedHead.getQuotedHeadId())){
            bidShipPeriodList = iLgtBidShipPeriodService.list(new QueryWrapper<LgtBidShipPeriod>(new LgtBidShipPeriod().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        }

        //附件信息
        List<LgtVendorFile> vendorFileList = new LinkedList<>();
        if(Objects.nonNull(vendorQuotedHead) && Objects.nonNull(vendorQuotedHead.getQuotedHeadId())){
            vendorFileList = iLgtVendorFileService.list(new QueryWrapper<LgtVendorFile>(new LgtVendorFile().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        }

        //供应商报价行表
        List<LgtVendorQuotedLine> vendorQuotedLineList = new LinkedList<>();
        if(Objects.nonNull(vendorQuotedHead) && Objects.nonNull(vendorQuotedHead.getQuotedHeadId())){
            vendorQuotedLineList = iLgtVendorQuotedLineService.list(new QueryWrapper<LgtVendorQuotedLine>(new LgtVendorQuotedLine().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        }

        vendorQuotedInfoVO.setVendorQuotedHead(vendorQuotedHead)
                .setVendorFileList(vendorFileList)
                .setBidShipPeriodList(bidShipPeriodList)
                .setVendorQuotedLineList(vendorQuotedLineList);

        return new LgtBidInfoVO().setBiding(lgtBiding)
                .setBidfileList(bidFileList)
                .setBidRequirementLineList(bidRequirementLineList)
                .setVendorQuotedInfoList(new LinkedList<LgtVendorQuotedInfoVO>(){{
                    add(vendorQuotedInfoVO);
                }})
                .setVendorQuotedSumList(vendorQuotedSumList);
    }

    /**
     * 技术标管理详情查询
     * @param bidingId
     * @return
     */
    @Override
    public TechBidVO detailTechBiding(Long bidingId) {
        //校验必传参数
        if(Objects.isNull(bidingId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传参数[biddingId]"));
        }
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到招标头信息!");
        //为开技术标前，不能查看信息
        if(!Objects.equals(lgtBiding.getBidingStatus(),BiddingProjectStatus.TECHNICAL_EVALUATION.getValue())
                && !canSeeResultSet.contains(lgtBiding.getBidingStatus()))
        {
            throw new BaseException("技术开标前，不能查看详情信息");
        }
        //获取当前轮次的供应商船期明细表
        List<LgtBidShipPeriod> bidShipPeriodList = iLgtBidShipPeriodService.list(new QueryWrapper<>(new LgtBidShipPeriod().setBidingId(bidingId).setRound(lgtBiding.getCurrentRound())));
        //获取供方技术标附件
        List<LgtVendorFile> lgtVendorFiles = iLgtVendorFileService.list(new QueryWrapper<>(new LgtVendorFile().
                setBidingId(bidingId).
                setRound(lgtBiding.getCurrentRound()).
                setFileType(BidingFileTypeEnum.TECHNICAL_BID.getCode())));
        // 获取技术评选结论
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).eq(LgtRound::getBidingId, bidingId).eq(LgtRound::getRound, lgtBiding.getCurrentRound()));
        return new TechBidVO().setLgtVendorFiles(lgtVendorFiles)
                .setBidShipPeriodList(bidShipPeriodList).
                        setTechnoSelection(null != lgtRound?lgtRound.getTechnoSelection():null);
    }

    /**
     * 商务标管理详情查询
     * @param bidingId
     * @return
     */
    @Override
    public CommercialBidVO detailCommercialBiding(Long bidingId) {
        //校验必传参数
        if(Objects.isNull(bidingId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传参数[biddingId]"));
        }
        List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                and(wrapper -> wrapper.eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue()).or().
                        eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.INVALID.getValue())));
        return new CommercialBidVO().setVendorQuotedHeadList(lgtVendorQuotedHeads);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long requirementToBiding(Long id) {
        //校验参数
        if(Objects.isNull(id)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递参数[requirementHeadId]"));
        }
        LogisticsRequirementHead logisticsRequirementHead = requirementHeadService.getById(id);
        // 检查是否已创建招标单
        Assert.isTrue(ObjectUtils.isEmpty(Optional.ofNullable(logisticsRequirementHead).orElseThrow(()->new BaseException("无法找到物流申请")).getBidingId()),String.format("[%s]已创建招标单,请勿重复创建!",logisticsRequirementHead.getRequirementHeadNum()));

        //查询物流采购申请信息
        LogisticsPurchaseRequirementDTO requirementDTO = requirementHeadService.getByHeadId(id);
        //物流采购申请 -> 物流招标单
        LgtBidDto lgtBidDto = buildBiding(requirementDTO);
        //添加招标单
        Long bidingId = add(lgtBidDto);
        LgtBiding lgtBiding = this.getOne(Wrappers.lambdaQuery(LgtBiding.class).eq(LgtBiding::getRequirementHeadId, id).last("LIMIT 1"));
        // 回写参数
        if(null != lgtBiding){
            logisticsRequirementHead
                    .setBidingId(lgtBiding.getBidingId())
                    .setBidingName(lgtBiding.getBidingName())
                    .setBidingCode(lgtBiding.getBidingNum())
                    .setApplyProcessStatus(LogisticsApplyProcessStatus.CONVERT_BID.getValue());
            requirementHeadService.updateById(logisticsRequirementHead);
        }
        return bidingId;
    }

    /**
     * 物流申请转招标-添加招标单
     * @param lgtBidDto
     * @return
     */
    private Long requirementToBidingAdd(LgtBidDto lgtBidDto, Long requirementHeadId){
        //检查参数
        checkParam(lgtBidDto);
        //添加招标单
        LgtBiding biding = lgtBidDto.getBiding(); // 招标基础信息
        List<LgtFileConfig> bidFileConfigList = lgtBidDto.getBidFileConfigList(); // 供方必须上传附件
        List<LgtBidFile> fileList = lgtBidDto.getFileList(); // 招标文件
        List<LgtGroup> groupList = lgtBidDto.getGroupList(); // 工作小组
        List<LgtBidRequirementLine> requirementLineList = lgtBidDto.getRequirementLineList(); //物流招标需求行
        // 招标ID
        Long bidingId = IdGenrator.generate();
        biding.setBidingId(bidingId);
        // 生成项目编号
        log.info("物流招标新建生成单据编号参数:"+ JSON.toJSONString(biding));
        String requirementNum = getRequirementNum(biding);
        log.info("物流招标新建生成单据编号:"+requirementNum);
        biding.setBidingNum(requirementNum);
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        // 初始化信息
        biding.setAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue()).
                setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                setReleaseFlag(YesOrNo.NO.getValue()).
                setCreatedByName(nickname).
                setCurrentRound(1);
        // 保存模板文件
        if(ObjectUtils.isEmpty(biding.getTemplateFileId())){
            LogisticsTemplateHead templateHead = iLogisticsTemplateHeadService.getById(biding.getTemplateHeadId());
            biding.setTemplateFileId(templateHead.getTemplateFileId());
            biding.setTemplateFileName(templateHead.getTemplateFileName());
        }
        this.save(biding);
        // 保存招标文件
        sevaLgtBidFiles(biding, fileList, bidingId);
        // 保存供方必须上传附件
        saveLgtFileConfigs(biding, bidFileConfigList, bidingId);
        // 保存工作小组
        saveLgtGroups(biding, groupList, bidingId);
        // 保存招标流程节点
        Long processConfigId = biding.getProcessConfigId();
        saveNodes(bidingId,processConfigId);
        // 保存需求明细模板表
        saveLgtBidTemplates(biding, bidingId);
        // 保存需求明细表
        saveLgtBidRequirementLine(biding, requirementLineList);

        //更新采购申请【需求池分配状态】
        LogisticsRequirementHead requirementHead = requirementHeadService.getById(requirementHeadId);
        requirementHeadService.updateById(requirementHead.setApplyProcessStatus(LogisticsApplyProcessStatus.CONVERT_BID.getValue()));
        return bidingId;
    }

    /**
     * 保存需求明细表
     * @param biding
     * @param requirementLineList
     */
    private void saveLgtBidRequirementLine(LgtBiding biding, List<LgtBidRequirementLine> requirementLineList){
        Long bidingId = biding.getBidingId();
        iLgtBidRequirementLineService.remove(new QueryWrapper<LgtBidRequirementLine>(new LgtBidRequirementLine().setBidingId(bidingId)));
        if(CollectionUtils.isNotEmpty(requirementLineList)){
            requirementLineList.forEach(item -> {
                item.setBidingId(bidingId);
                item.setBidRequirementLineId(IdGenrator.generate());
            });
            iLgtBidRequirementLineService.saveBatch(requirementLineList);
        }
    }


    @Override
    public LgtBidInfoVO detail(Long id) {
        //校验参数
        if(Objects.isNull(id)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递参数[bidingId]"));
        }

        //招标项目基础信息
        LgtBiding biding = this.getById(id);
        //招标项目附件信息
        List<LgtBidFile> bidFileList = iLgtFileService.list(new QueryWrapper<LgtBidFile>(new LgtBidFile().setBidingId(id)));
        //供方必须上传附件信息
        List<LgtFileConfig> bidFileConfigList = iLgtFileConfigService.list(new QueryWrapper<LgtFileConfig>(new LgtFileConfig().setBidingId(id)));
        //需求明细表
        List<LgtBidRequirementLine> bidRequirementLineList = iLgtBidRequirementLineService.list(new QueryWrapper<LgtBidRequirementLine>(new LgtBidRequirementLine().setBidingId(id)));
        //投标结果
        List<LgtVendorQuotedSum> vendorQuotedSumList = iLgtVendorQuotedSumService.list(new QueryWrapper<LgtVendorQuotedSum>(new LgtVendorQuotedSum().setBidingId(id).setRound(biding.getCurrentRound())));
        //供应商报价信息
        List<LgtVendorQuotedInfoVO> vendorQuotedInfoList = getVendorQuotedInfoList(id);
        //邀请供应商的信息
        List<LgtVendorVO> vendorList = getVendorList(id);

        return new LgtBidInfoVO().setBiding(biding)
                .setBidfileList(bidFileList)
                .setBidFileConfigList(bidFileConfigList)
                .setBidRequirementLineList(bidRequirementLineList)
                .setVendorQuotedInfoList(vendorQuotedInfoList)
                .setVendorQuotedSumList(vendorQuotedSumList)
                .setVendorList(vendorList);
    }

    /**
     * 获取供应商报价信息
     * @param id
     * @return
     */
    private List<LgtVendorQuotedInfoVO> getVendorQuotedInfoList(Long id){
        List<LgtVendorQuotedInfoVO> result = new LinkedList<LgtVendorQuotedInfoVO>();
        LgtBiding lgtBiding = this.getById(id);
        List<LgtVendorQuotedHead> vendorQuotedHeadList = iLgtVendorQuotedHeadService.list(new QueryWrapper<LgtVendorQuotedHead>(new LgtVendorQuotedHead().setBidingId(id).setRound(lgtBiding.getCurrentRound())));
        if(CollectionUtils.isNotEmpty(vendorQuotedHeadList)){
            for(LgtVendorQuotedHead vendorQuotedHead : vendorQuotedHeadList){
                List<LgtVendorFile>  vendorFileList = iLgtVendorFileService.list(new QueryWrapper<LgtVendorFile>(new LgtVendorFile().setBidingId(id).setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
                List<LgtBidShipPeriod> bidShipPeriodList = iLgtBidShipPeriodService.list(new QueryWrapper<LgtBidShipPeriod>(new LgtBidShipPeriod().setBidingId(id).setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
                List<LgtVendorQuotedLine> vendorQuotedLineList = iLgtVendorQuotedLineService.list(new QueryWrapper<LgtVendorQuotedLine>(new LgtVendorQuotedLine().setBidingId(id).setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
                result.add(new LgtVendorQuotedInfoVO()
                        .setVendorQuotedHead(vendorQuotedHead)
                        .setVendorFileList(vendorFileList)
                        .setBidShipPeriodList(bidShipPeriodList)
                        .setVendorQuotedLineList(vendorQuotedLineList)
                );
            }
        }
        return result;
    }

    /**
     * 获取邀请的供应商新
     * @param id
     * @return
     */
    private List<LgtVendorVO> getVendorList(Long id){
        List<LgtVendorVO> result = new LinkedList<LgtVendorVO>();
        List<LgtBidVendor> bidVendorList = iLgtVendorService.list(new QueryWrapper<LgtBidVendor>(new LgtBidVendor().setBidingId(id)));
        if(CollectionUtils.isNotEmpty(bidVendorList)){
            for(LgtBidVendor bidVendor : bidVendorList){
                List<LgtQuoteAuthorize> quoteAuthorizeList = iLgtQuoteAuthorizeService.list(new QueryWrapper<LgtQuoteAuthorize>(new LgtQuoteAuthorize().setBidVendorId(bidVendor.getBidVendorId())));
                List<LgtPayPlan> payPlanList = iLgtPayPlanService.list(new QueryWrapper<LgtPayPlan>(new LgtPayPlan().setBidVendorId(bidVendor.getBidVendorId())));
                result.add(new LgtVendorVO()
                        .setBidVendor(bidVendor)
                        .setQuoteAuthorizeList(quoteAuthorizeList)
                        .setPayPlanList(payPlanList)
                );
            }
        }
        return result;
    }

    private LgtBidDto buildBiding(LogisticsPurchaseRequirementDTO requirementDTO){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        //获取物流招标流程配置信息
        List<BidProcessConfig> bidProcessConfigs = bidClient.listBidProcessConfig(new BidProcessConfig().setBidingType(BidType.LOGISTICS.getValue()));
        Long processConfigId = null;
        if(CollectionUtils.isNotEmpty(bidProcessConfigs)){
            processConfigId = bidProcessConfigs.get(0).getProcessConfigId();
        }

        //采购需求头
        LogisticsRequirementHead requirementHead = requirementDTO.getRequirementHead();
        //采购需求模板头
        LogisticsTemplateHead templateHead = requirementDTO.getLogisticsTemplateHead();

        //物流招标单头信息
        LgtBiding biding = new LgtBiding()
                .setBidingNum(requirementHead.getRequirementHeadNum())
                .setProcessConfigId(processConfigId)       //招标流程配置id
                .setTemplateHeadId(requirementHead.getTemplateHeadId())        //物流采购申请模板头ID
                .setTemplateCode(requirementHead.getTemplateCode())          //模板编码
                .setTemplateName(requirementHead.getTemplateName())          //模板名称
                .setBusinessModeCode(requirementHead.getBusinessModeCode())      //业务模式
                .setTransportModeCode(requirementHead.getTransportModeCode())     //运输方式
                .setBusinessType(requirementHead.getBusinessType())          //业务类型
                .setServiceProjectCode(requirementHead.getServiceProjectCode())    //服务项目编码
                .setServiceProjectName(requirementHead.getServiceProjectName())    //服务项目名称
//                .setUnitId()                //单位ID
                .setUnitCode(requirementHead.getUnit())              //单位编码
//                .setUnitName()              //单位名称
                .setProjectTotal(requirementHead.getProjectTotal())          //项目总量
                .setBidingName(requirementHead.getRequirementTitle())            //项目名称
                .setDemandDate(requirementHead.getDemandDate())            //需求日期
                .setBudgetAmount(requirementHead.getBudgetAmount())          //预算金额
                .setCurrencyId(requirementHead.getCurrencyId())            //币种ID
                .setCurrencyCode(requirementHead.getCurrencyCode())          //币种编码
                .setCurrencyName(requirementHead.getCurrencyName())          //币种名称
                .setAllowedVehicle(requirementHead.getAllowedVehicle())        //项目地可进最大车型
                .setPriceTimeStart(requirementHead.getPriceStartDate())        //价格有效开始时间
                .setPriceTimeEnd(requirementHead.getPriceEndDate())          //价格有效开始时间
                .setLoadNumber(String.valueOf(requirementHead.getLoadNumber()))            //装载量
//                .setEnrollEndDatetime()     //投标截止时间
//                .setTaxId()                 //税率ID
//                .setTaxCode()               //税率值
//                .setTaxKey()                //税率编码
                .setIfVendorSubmitShipDate(requirementHead.getIfVendorSubmitShipDate())//是否提交船期(N/Y)
                .setContractType(requirementHead.getContractType())          //合同类型
                .setCompanyId(requirementHead.getVendorId())             //指定供应商ID
                .setCompanyCode(requirementHead.getVendorCode())           //指定供应商CODE
                .setCompanyName(requirementHead.getVendorName())           //指定供应商名称
                .setSpecifySupReason(requirementHead.getVendorReason())      //指定供应商原因
                .setComments(requirementHead.getComments())              //备注
                .setRequirementHeadId(requirementHead.getRequirementHeadId())  // 申请ID
                .setRequirementHeadNum(requirementHead.getRequirementHeadNum())    //申请单号
                .setApplyId(requirementHead.getApplyId())               //申请人ID
                .setApplyCode(requirementHead.getApplyCode())             //申请人账号
                .setApplyBy(requirementHead.getApplyBy())               //申请人名称
                .setApplyDepartmentId(requirementHead.getApplyDepartmentId())     //申请部门ID
                .setApplyDepartmentCode(requirementHead.getApplyDepartmentCode())   //申请部门编码
                .setApplyDepartmentName(requirementHead.getApplyDepartmentName())   //申请部门名称
                .setPhone(requirementHead.getPhone())                 //联系电话
//                .setCreatedByName()         //创建人名字
//                .setInternalDesc()          //内部说明
//                .setSupplierDesc()          //供方说明
                .setStandardCurrency(requirementHead.getCurrencyCode())      //本位币
//                .setPricePrecision()        //价格精度
//                .setExchangeRateType()      //汇率类型
//                .setCurrencyChangeDate()    //币种转换日期
                .setBidUserName(requirementHead.getCeeaApplyUserNickname())           //联系人姓名
//                .setBidUserPhone()          //手机
//                .setBidUserEmail()          //邮箱
                .setWithdrawBiding(YesOrNo.YES.getValue())        //是否报价截止前允许供应商撤回报价
//                .setPublicTotalRank()       //是否向供应商公开上轮排名结果
//                .setPublicLowestPrice()     //是否向供应商公开上轮最低价
//                .setVisibleRankResult()     //允许供应商查看最终排名结果
//                .setVisibleFinalPrice()     //允许供应商查看中标价
//                .setVisibleWinVendor()      //允许供应商查看中标供应商
//                .setRevokeReason()          //撤回原因
//                .setReleaseDatetime()       //发布时间
//                .setBiddingSuppliers()      //已投标供应商
                .setIfNeedVendorComfirm(templateHead.getVendorIfSubmitShip())    //是否需要供方确认
                .setTemplateFileId(templateHead.getTemplateFileId())
                .setTemplateFileName(templateHead.getTemplateFileName())
                .setGeneratePurchaseApproval(YesOrNo.NO.getValue())  //生成采购订单(Y/N)
                .setBidUserPhone(loginAppUser.getPhone())
                .setBidUserEmail(loginAppUser.getEmail())
                .setBidUserName(loginAppUser.getNickname())
                //来源单据类型
                .setSourceFrom(SourceFrom.PURCHASE_REQUEST.getItemValue())
                //本位币信息
                .setStandardCurrency("CNY")
                .setExchangeRateType("COMPANY")
                .setPricePrecision(2)
                .setCurrencyChangeDate(LocalDate.now());


        //招标需求明细
        List<LgtBidRequirementLine> requirementLineList = new LinkedList<>();
        List<LogisticsRequirementLine> logisticsRequirementLineList = requirementDTO.getRequirementLineList();
        for(LogisticsRequirementLine item : logisticsRequirementLineList){
            LgtBidRequirementLine requirementLine = new LgtBidRequirementLine();
            BeanUtils.copyProperties(item, requirementLine);
            requirementLine.setRequirementHeadId(requirementHead.getRequirementHeadId());
            requirementLine.setRequirementLineId(item.getRequirementLineId());
            requirementLine.setRequirementLineNum((long)item.getRowNum());
            requirementLineList.add(requirementLine);
        }

        //11954 招标项目信息逻辑优化调整
        //添加内部附件可见
        List<LogisticsRequirementFile> requirementAttaches = requirementDTO.getRequirementAttaches();
        List<LgtBidFile> lgtBidFiles=new ArrayList<>(requirementAttaches.size());
        if(!CollectionUtils.isEmpty(requirementAttaches)){
            for (LogisticsRequirementFile requirementAttach : requirementAttaches) {
                LgtBidFile lgtBidFile=new LgtBidFile()
                        .setFileType(BidFileType.Enterprise.getValue())
                        .setFileName(requirementAttach.getFileName())
                        .setDocId(requirementAttach.getFileRelationId()+"")
                        .setComments(requirementAttach.getRemark());
                lgtBidFiles.add(lgtBidFile);
            }
        }
        User user = rbacClient.getUserByIdAnon(requirementHead.getCreatedId());
        User user1=rbacClient.getUserByIdAnon(loginAppUser.getUserId());
        //添加需求人为工作小组
        List<LgtGroup> defaultGroup = Arrays.asList(new LgtGroup().setConfirmeDatetime(new Date())
                .setConfirmedFlag("Y")
                .setUserName(user.getUsername())
                .setUserId(user.getUserId())
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setPosition(user.getCeeaJobcodeDescr())
                .setJudgeFlag("Y")
                .setFullName(user.getNickname())
                .setMaxEvaluateScore(100L),
        new LgtGroup().setConfirmeDatetime(new Date())
                .setConfirmedFlag("Y")
                .setUserName(user1.getUsername())
                .setUserId(user1.getUserId())
                .setPhone(user1.getPhone())
                .setEmail(user1.getEmail())
                .setPosition(user1.getCeeaJobcodeDescr())
                .setFullName(user1.getNickname())
                .setMaxEvaluateScore(100L)
        );
        return new LgtBidDto()
                .setBiding(biding)
                .setRequirementLineList(requirementLineList)
                .setBidFileConfigList(Collections.EMPTY_LIST)
                .setFileList(lgtBidFiles)
                .setGroupList(defaultGroup);

    }

    @Override
    @Transactional
    public void extendBiding(StartOrExtendBidingVO startOrExtendBidingVO) {
        Long bidingId = startOrExtendBidingVO.getBidingId();
        Assert.notNull(bidingId,"缺少参数: bidingId");
        Assert.notNull(startOrExtendBidingVO.getExtendReason(),"延长原因不能为空!");
        Date endTime = startOrExtendBidingVO.getEndTime();
        Assert.notNull(endTime,"调整时间不能为空");
        Assert.isTrue(endTime.compareTo(new Date()) > 0 ,"调整时间不能小于当前时间");
        LgtBiding biding = this.getById(bidingId);
        Assert.isTrue(BiddingProjectStatus.ACCEPT_BID.getValue().equals(biding.getBidingStatus()) ||
                        BiddingProjectStatus.TENDER_ENDING.getValue().equals(biding.getBidingStatus()) ||
                        BiddingProjectStatus.BUSINESS_EVALUATION.getValue().equals(biding.getBidingStatus()),
                "项目状态不是\"接受投标中\"或\"商务评标\",不能调整投标截止时间!");
        /**
         * 判断项目状态
         */
        int currentRound = biding.getCurrentRound();
        // 更新轮次表
        LgtRound lgtRound = iLgtRoundService.getOne(new QueryWrapper<>(new LgtRound().
                setBidingId(bidingId).setRound(currentRound)));
        lgtRound.setEndTime(endTime);
        lgtRound.setExtendReason(startOrExtendBidingVO.getExtendReason());
        iLgtRoundService.updateById(lgtRound);
        // 更新头表
        biding.setEnrollEndDatetime(endTime);
        biding.setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue());
        this.updateById(biding);
        // 清楚开标数据
        iLgtVendorQuotedSumService.remove(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                eq(LgtVendorQuotedSum::getBidingId,bidingId).
                eq(LgtVendorQuotedSum::getRound,currentRound));
    }

    @Override
    @Transactional
    public void startBiding(StartOrExtendBidingVO startOrExtendBidingVO) {
        /**
         * 1. 如果不是第一轮，判断当前轮是否结束（根据是否公示字段判断）
         * 2. 发起投标的结束时间不能早于当前时间
         * 3. 当前轮有入围的供应商才能发起下一轮投标
         */
        Long bidingId = startOrExtendBidingVO.getBidingId();
        Date endTime = startOrExtendBidingVO.getEndTime();
        Assert.notNull(bidingId, "招标id不能为空");
        Assert.notNull(endTime, "投标结束时间不能为空");
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到物流招标头信息");
        Assert.isTrue(endTime.compareTo(new Date()) > 0,"投标截止时间不能小于当前时间!");
        Assert.isTrue(!BiddingApprovalStatus.APPROVED.getValue().equals(lgtBiding.getEndAuditStatus()),"该单据已结项审批,不能发起新一轮投标!");
        // 检查本轮是否结束 公示
        LgtRound lgtRound = iLgtRoundService.getOne(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, bidingId).
                eq(LgtRound::getRound, lgtBiding.getCurrentRound()));
        Assert.notNull(lgtRound,"没找到当前轮次信息");
        Assert.isTrue(YesOrNo.YES.getValue().equals(lgtRound.getPublicResult()),"投标结果未公示,不能发起新一轮投标!");
        // 检查是否有入围下一轮的供应商
        List<LgtVendorQuotedSum> vendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                select(LgtVendorQuotedSum::getVendorId).
                eq(LgtVendorQuotedSum::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedSum::getBidingId, bidingId).
                eq(LgtVendorQuotedSum::getShortlisted, YesOrNo.YES.getValue()));
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorQuotedSums),"当前轮没有入围下一轮的供应商,不能发起新一轮投标!");

        int round = lgtBiding.getCurrentRound() + 1;
        // 生成当前轮次信息
        saveLgtRoundNew(bidingId, endTime, round);
        // 复制入围供应商报价信息生成新一轮
        saveLgtVendorQuotedNew(bidingId, lgtBiding, vendorQuotedSums, round);
        // 更新招标头表状态
        updateLgtBidingNew(endTime, lgtBiding, vendorQuotedSums, round);
    }

    public void updateLgtBidingNew(Date endTime, LgtBiding lgtBiding, List<LgtVendorQuotedSum> vendorQuotedSums, int round) {
        int size = vendorQuotedSums.size();
        String biddingSuppliers = "0/"+size;
        lgtBiding.setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue())
                .setEnrollEndDatetime(endTime)
                .setCurrentRound(round)
                .setBiddingSuppliers(biddingSuppliers);
        this.updateById(lgtBiding);
    }

    private void saveLgtRoundNew(Long bidingId, Date endTime, int round) {
        iLgtRoundService.remove(Wrappers.lambdaQuery(LgtRound.class).eq(LgtRound::getBidingId,bidingId).eq(LgtRound::getRound,round));
        LgtRound lgtRound = LgtRound.builder()
                .roundId(IdGenrator.generate())
                .bidingId(bidingId)
                .round(round)
                .startTime(new Date())
                .endTime(endTime)
                .build();
        iLgtRoundService.save(lgtRound);
    }

    private void saveLgtVendorQuotedNew(Long bidingId, LgtBiding lgtBiding, List<LgtVendorQuotedSum> vendorQuotedSums, int round) {
        List<LgtVendorFile> lgtVendorFileSave = new ArrayList<>();
        List<LgtBidShipPeriod> lgtBidShipPeriodSave = new ArrayList<>();
        List<LgtVendorQuotedLine> lgtVendorQuotedLineSave = new ArrayList<>();
        List<Long> vendorIds = vendorQuotedSums.stream().map(LgtVendorQuotedSum::getVendorId).collect(Collectors.toList());
        // 获取供应商报价头
        List<LgtVendorQuotedHead> vendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                in(LgtVendorQuotedHead::getVendorId, vendorIds));
        vendorQuotedHeads.forEach(lgtVendorQuotedHead -> {
            Long oldQuotedHeadId = lgtVendorQuotedHead.getQuotedHeadId();
            Long quotedHeadId = IdGenrator.generate();
            lgtVendorQuotedHead.setQuotedHeadId(quotedHeadId)
                    .setStatus(BiddingOrderStates.DRAFT.getValue())
                    .setQuotedHeadCode(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_BIDING_CODE))
                    .setRound(round);
            // 供应商报名附件
            List<LgtVendorFile> lgtVendorFiles = iLgtVendorFileService.list(Wrappers.lambdaQuery(LgtVendorFile.class).
                    eq(LgtVendorFile::getQuotedHeadId, oldQuotedHeadId));
            if (CollectionUtils.isNotEmpty(lgtVendorFiles)) {
                lgtVendorFiles.forEach(lgtVendorFile -> {
                    lgtVendorFile.setQuotedHeadId(quotedHeadId);
                    lgtVendorFile.setRound(round);
                    lgtVendorFile.setVendorFileId(IdGenrator.generate());
                });
                lgtVendorFileSave.addAll(lgtVendorFiles);
            }
            // 供应商船期明细
            List<LgtBidShipPeriod> lgtBidShipPeriods = iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                    eq(LgtBidShipPeriod::getQuotedHeadId, oldQuotedHeadId));
            if (CollectionUtils.isNotEmpty(lgtBidShipPeriods)) {
                lgtBidShipPeriods.forEach(lgtBidShipPeriod -> {
                    lgtBidShipPeriod.setQuotedHeadId(quotedHeadId);
                    lgtBidShipPeriod.setRound(round);
                    lgtBidShipPeriod.setShipPeriodId(IdGenrator.generate());
                });
                lgtBidShipPeriodSave.addAll(lgtBidShipPeriods);
            }
            // 供应商报价明细
            List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                    eq(LgtVendorQuotedLine::getQuotedHeadId, oldQuotedHeadId));
            if (CollectionUtils.isNotEmpty(lgtVendorQuotedLines)) {
                lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                    lgtVendorQuotedLine.setQuotedLineId(IdGenrator.generate())
                            .setRound(round)
                            .setQuotedHeadId(quotedHeadId);
                });
                lgtVendorQuotedLineSave.addAll(lgtVendorQuotedLines);
            }

        });
        iLgtVendorQuotedHeadService.saveBatch(vendorQuotedHeads);
        iLgtVendorFileService.saveBatch(lgtVendorFileSave);
        iLgtBidShipPeriodService.saveBatch(lgtBidShipPeriodSave);
        iLgtVendorQuotedLineService.saveBatch(lgtVendorQuotedLineSave);
    }

    @Override
    @Transactional
    public void delete(List<Long> bidingIds) {
        if(CollectionUtils.isNotEmpty(bidingIds)){
            List<LgtBiding> lgtBidings = this.listByIds(bidingIds);
            if(CollectionUtils.isNotEmpty(lgtBidings)){
                List<String> stringList = lgtBidings.stream().map(LgtBiding::getRequirementHeadNum).collect(Collectors.toList());
                iRequirementHeadService.release(stringList);
            }
            this.removeByIds(bidingIds);
        }
    }

    @Override
    public void withdraw(Long bidingId,String reason) {
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"未找到物流招标基础信息");
        //  撤回原因/ 原因-时间
        String revokeReason = lgtBiding.getRevokeReason();
        if(ObjectUtils.isEmpty(revokeReason)){
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("reason",reason);
            hashMap.put("time", DateUtil.format(new Date()));
            list.add(hashMap);
            revokeReason = JSON.toJSONString(list);
        }else {
            List list = JSON.parseObject(revokeReason, List.class);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("reason",reason);
            hashMap.put("time",DateUtil.format(new Date()));
            list.add(hashMap);
            revokeReason = JSON.toJSONString(list);
        }
        lgtBiding.setRevokeReason(revokeReason);
//        lgtBiding.setRevokeReason(reason);
        // 状态/标识

        lgtBiding.setAuditStatus(BiddingApprovalStatus.WITHDRAW.getValue()).
                setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue()).
                setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                setReleaseDatetime(null).
                setReleaseFlag(YesOrNo.NO.getValue());
        this.updateById(lgtBiding);
    }

    @Override
    @Transactional
    public void release(Long bidingId) {
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"未找到物流招标基础信息");
        Assert.isTrue(
                !BiddingApprovalStatus.SUBMITTED.getValue().equals(lgtBiding.getAuditStatus()) &&
                        !BiddingApprovalStatus.APPROVED.getValue().equals(lgtBiding.getAuditStatus())
                ,"请勿重复发布!");
        // 检查参数
        releaseCheckParam(bidingId);
        Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"招标截止时间小于当前时间, 不能发布!");
        // 更新单据状态
        releaseUpdateStatus(lgtBiding);
        // 生成供应商报价头
        createLgtVendorQuotedHead(bidingId, lgtBiding);
        // 生成轮次表
        saveLgtRound(bidingId, lgtBiding);
        // 更新流程节点状态
        updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.bidingControl.getValue())
                .setBidingId(bidingId).setDataFlag(YesOrNo.YES.getValue()));
    }

    private void saveLgtRound(Long bidingId, LgtBiding lgtBiding) {
        iLgtRoundService.remove(Wrappers.lambdaQuery(LgtRound.class).eq(LgtRound::getBidingId,bidingId).eq(LgtRound::getRound,1));
        LgtRound lgtRound = LgtRound.builder()
                .roundId(IdGenrator.generate())
                .bidingId(bidingId)
                .round(1)
                .startTime(new Date())
                .endTime(lgtBiding.getEnrollEndDatetime())
                .build();
        iLgtRoundService.save(lgtRound);
    }

    private void releaseCheckParam(Long bidingId) {
        int bidRequirementLineCount = iLgtBidRequirementLineService.count(new QueryWrapper<>(new LgtBidRequirementLine().setBidingId(bidingId)));
        Assert.isTrue(bidRequirementLineCount > 0,"需求明细不能为空!!!");
        List<LgtBidVendor> lgtBidVendors = iLgtVendorService.list(new QueryWrapper<>(new LgtBidVendor().setBidingId(bidingId)));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtBidVendors),"邀请供应商不能为空!!!");
        lgtBidVendors.forEach(lgtBidVendor -> {
            Assert.isTrue(!ObjectUtils.isEmpty(lgtBidVendor.getEmail()) &&
                    !ObjectUtils.isEmpty(lgtBidVendor.getPhone()) &&
                    !ObjectUtils.isEmpty(lgtBidVendor.getLinkManName()),"请补全邀请供应商联系人信息!!!");
        });
    }

    private void releaseUpdateStatus(LgtBiding lgtBiding) {
        lgtBiding.setAuditStatus(BiddingApprovalStatus.APPROVED.getValue()).
                setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue()).
                setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                setReleaseDatetime(new Date()).
                setReleaseFlag(YesOrNo.YES.getValue());
        this.updateById(lgtBiding);
    }

    private void createLgtVendorQuotedHead(Long bidingId, LgtBiding lgtBiding) {
        List<LgtBidVendor> lgtBidVendors = iLgtVendorService.list(Wrappers.lambdaQuery(LgtBidVendor.class).
                eq(LgtBidVendor::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(lgtBidVendors)){
            iLgtVendorQuotedHeadService.remove(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).eq(LgtVendorQuotedHead::getBidingId,bidingId));
            List<LgtVendorQuotedHead> lgtVendorQuotedHeads = new ArrayList<>();
            lgtBidVendors.forEach(lgtBidVendor -> {
                LgtVendorQuotedHead quotedHead = LgtVendorQuotedHead.builder()
                        .quotedHeadId(IdGenrator.generate())
                        .bidingId(bidingId)
                        .round(lgtBiding.getCurrentRound())
                        .quotedHeadCode(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_BIDING_CODE))
                        .vendorId(lgtBidVendor.getVendorId())
                        .vendorCode(lgtBidVendor.getVendorCode())
                        .vendorName(lgtBidVendor.getVendorName())
                        .bidingNum(lgtBiding.getBidingNum())
                        .linkManName(lgtBidVendor.getLinkManName())
                        .phone(lgtBidVendor.getPhone())
                        .email(lgtBidVendor.getEmail())
                        .status(BiddingOrderStates.DRAFT.getValue())
                        .ifProxy(YesOrNo.NO.getValue()).build();
                lgtVendorQuotedHeads.add(quotedHead);
            });
            iLgtVendorQuotedHeadService.saveBatch(lgtVendorQuotedHeads);
        }
    }

    @Override
    public List<LgtVendorQuotedDto> previewVendor(Long bidingId) {
        LgtBiding lgtBiding = this.getById(bidingId);
        Assert.notNull(lgtBiding,"找不到物流招标头信息");
        /**
         * 判断招标单是否发布,未发布则做临时数据
         */
        List<LgtVendorQuotedDto> lgtVendorQuotedDtos = new ArrayList<>();
        if (!Objects.equals(lgtBiding.getAuditStatus(), BiddingApprovalStatus.DRAFT.getValue())) {
            /**
             * 招标发布过了, 读取供应商报价头表
             */
            List<LgtVendorQuotedHead> lgtVendorQuotedHeads = iLgtVendorQuotedHeadService.list(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                    eq(LgtVendorQuotedHead::getBidingId, bidingId).eq(LgtVendorQuotedHead::getRound,lgtBiding.getCurrentRound()));
            if(CollectionUtils.isNotEmpty(lgtVendorQuotedHeads)){
                lgtVendorQuotedHeads.forEach(lgtBidVendor -> {
                    LgtVendorQuotedDto lgtVendorQuotedDto = new LgtVendorQuotedDto();
                    BeanCopyUtil.copyProperties(lgtVendorQuotedDto,lgtBidVendor);
                    lgtVendorQuotedDtos.add(lgtVendorQuotedDto);
                });
            }
        }
//        else {
//            /**
//             * 招标还没发布, 找临时数据
//             */
//            List<LgtBidVendor> lgtBidVendors = iLgtVendorService.list(Wrappers.lambdaQuery(LgtBidVendor.class).eq(LgtBidVendor::getBidingId, bidingId));
//            if(CollectionUtils.isNotEmpty(lgtBidVendors)){
//                lgtBidVendors.forEach(lgtBidVendor -> {
//                    LgtVendorQuotedDto lgtVendorQuotedDto = new LgtVendorQuotedDto();
//                    BeanCopyUtil.copyProperties(lgtVendorQuotedDto,lgtBidVendor);
//                    lgtVendorQuotedDto.setStatus(BiddingOrderStates.DRAFT.getValue());
//                    lgtVendorQuotedDtos.add(lgtVendorQuotedDto);
//                });
//            }
//        }
        return lgtVendorQuotedDtos;
    }

    @Override
    @Transactional
    public void saveLgtQuoteAuthorize(List<LgtQuoteAuthorize> lgtQuoteAuthorizes) {
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtQuoteAuthorizes),"报价权限不能为空");
        Long bidVendorId = lgtQuoteAuthorizes.get(0).getBidVendorId();
        Assert.notNull(bidVendorId,"报价权限缺少参数: bidVendorId");
        LgtBidVendor lgtBidVendor = iLgtVendorService.getById(bidVendorId);
        Assert.notNull(lgtBidVendor,"找不到对应邀请供应商");
        LgtBiding lgtBiding = this.getById(lgtBidVendor.getBidingId());
        if(!Objects.equals(lgtBiding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            iLgtQuoteAuthorizeService.updateBatchById(lgtQuoteAuthorizes);
        }
    }

    @Override
    public List<LgtProcessNode> getNodeStatus(Long bidingId) {
        List<LgtProcessNode> lgtProcessNodes = iLgtProcessNodeService.list(Wrappers.lambdaQuery(LgtProcessNode.class).
                eq(LgtProcessNode::getBidingId, bidingId));
        return lgtProcessNodes;
    }

    @Override
    @Transactional
    public void updateNodeStatus(LgtProcessNode processNode) {
        // ProcessNodeName
        LgtProcessNode processNodeEntity = iLgtProcessNodeService.getOne(new QueryWrapper<>(new LgtProcessNode().
                setBidingId(processNode.getBidingId()).
                setNodeCode(processNode.getNodeCode())));
        if (null != processNodeEntity) {
            processNodeEntity.setDataFlag(processNode.getDataFlag());
            iLgtProcessNodeService.updateById(processNodeEntity);
        }
    }

    @Override
    @Transactional
    public List<LgtQuoteAuthorize> getLgtQuoteAuthorizeByBidVendorId(Long bidingId,Long bidVendorId) {
        List<LgtQuoteAuthorize> quoteAuthorizes = new ArrayList<>();
        LgtBiding biding = this.getById(bidingId);
        LgtBidVendor lgtBidVendor = iLgtVendorService.getById(bidVendorId);
        List<LgtBidRequirementLine> requirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                eq(LgtBidRequirementLine::getBidingId, bidingId));
        List<LgtQuoteAuthorize> quoteAuthorizesTemp = iLgtQuoteAuthorizeService.list(Wrappers.lambdaUpdate(LgtQuoteAuthorize.class).
                eq(LgtQuoteAuthorize::getBidVendorId, bidVendorId));
        /**
         * 情况一,报价权限没有初始化
         */
        if(CollectionUtils.isEmpty(quoteAuthorizesTemp)){
            if(CollectionUtils.isNotEmpty(requirementLines)){
                List<LgtQuoteAuthorize> finalQuoteAuthorizes = getLgtQuoteAuthorizes(bidVendorId, biding, lgtBidVendor, requirementLines);
                if (CollectionUtils.isNotEmpty(finalQuoteAuthorizes)) {
                    quoteAuthorizes.addAll(finalQuoteAuthorizes);
                }
                iLgtQuoteAuthorizeService.saveBatch(quoteAuthorizes);
            }
        }else {
            /**
             * 情况二,项目明细变动,导致报价权限与项目需求不一致
             */
            if(CollectionUtils.isNotEmpty(requirementLines)){
                Map<Long, String> map = quoteAuthorizesTemp.stream().collect(Collectors.toMap(LgtQuoteAuthorize::getBidRequirementLineId, LgtQuoteAuthorize::getIfProhibit));
                List<LgtQuoteAuthorize> finalQuoteAuthorizes = getLgtQuoteAuthorizes(bidVendorId, biding, lgtBidVendor, requirementLines);
                if(CollectionUtils.isNotEmpty(finalQuoteAuthorizes)){
                    finalQuoteAuthorizes.forEach(lgtQuoteAuthorize -> {
                        Long bidRequirementLineId = lgtQuoteAuthorize.getBidRequirementLineId();
                        lgtQuoteAuthorize.setIfProhibit(null != map.get(bidRequirementLineId)?map.get(bidRequirementLineId):YesOrNo.NO.getValue());
                    });
                    quoteAuthorizes.addAll(finalQuoteAuthorizes);
                }
                iLgtQuoteAuthorizeService.remove(Wrappers.lambdaQuery(LgtQuoteAuthorize.class).eq(LgtQuoteAuthorize::getBidVendorId,bidVendorId));
                iLgtQuoteAuthorizeService.saveBatch(quoteAuthorizes);
            }
        }
        return quoteAuthorizes;
    }

    private List<LgtQuoteAuthorize> getLgtQuoteAuthorizes(Long bidVendorId, LgtBiding biding, LgtBidVendor lgtBidVendor, List<LgtBidRequirementLine> requirementLines) {
        List<LgtQuoteAuthorize> finalQuoteAuthorizes = new ArrayList<>();
        requirementLines.forEach(lgtBidRequirementLine -> {
            LgtQuoteAuthorize lgtQuoteAuthorize = LgtQuoteAuthorize.builder()
                    .quoteAuthorizeId(IdGenrator.generate())
                    .bidVendorId(bidVendorId)
                    .bidRequirementLineId(lgtBidRequirementLine.getRequirementLineId())
                    .rowNum(lgtBidRequirementLine.getRowNum())
                    .vendorId(lgtBidVendor.getVendorId())
                    .vendorCode(lgtBidVendor.getVendorCode())
                    .vendorName(lgtBidVendor.getVendorName())
                    .bidingName(biding.getBidingName())
                    .ifProhibit(YesOrNo.NO.getValue())
                    .fromPlace(lgtBidRequirementLine.getFromPlace())
                    .toPlace(lgtBidRequirementLine.getToPlace())
                    .build();
            finalQuoteAuthorizes.add(lgtQuoteAuthorize);
        });
        return finalQuoteAuthorizes;
    }

    @Override
    @Transactional
    public void saveLgtBidVendor(List<LgtBidVendor> lgtBidVendors) {
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtBidVendors),"邀请供应商不能为空");
        Set<String> vendorNameHash = new HashSet<>();
        lgtBidVendors.forEach(lgtBidVendor -> {
            Assert.isTrue(vendorNameHash.add(lgtBidVendor.getVendorCode()),"邀请供应商存在重复!");
        });
        Long bidingId = lgtBidVendors.get(0).getBidingId();
        Assert.notNull(bidingId,"邀请供应商缺失参数: bidingId");
        LgtBiding biding = this.getById(bidingId);
        Assert.notNull(biding,"找不到物流招标头信息");
        if(!Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            // 筛选出新增和更新的数据
            ArrayList<LgtBidVendor> lgtBidVendorsUpdate = new ArrayList<>();
            ArrayList<LgtBidVendor> lgtBidVendorsAdd = new ArrayList<>();
            lgtBidVendors.forEach(lgtBidVendor -> {
                if(ObjectUtils.isEmpty(lgtBidVendor.getBidVendorId())){
                    lgtBidVendorsAdd.add(lgtBidVendor);
                }else {
                    lgtBidVendorsUpdate.add(lgtBidVendor);
                }
            });
            if (CollectionUtils.isNotEmpty(lgtBidVendorsUpdate)) {
                List<LgtBidVendor> bidingIds = iLgtVendorService.list(Wrappers.lambdaQuery(LgtBidVendor.class).select(LgtBidVendor::getBidingId).eq(LgtBidVendor::getBidingId, bidingId));
                if (CollectionUtils.isNotEmpty(bidingIds)) {
                    List<Long> ids = lgtBidVendorsUpdate.stream().map(LgtBidVendor::getBidVendorId).collect(Collectors.toList());
                    List<Long> longs = new ArrayList<>();
                    bidingIds.forEach(lgtBidVendor -> {
                        if(!ids.contains(lgtBidVendor.getBidVendorId())){
                            longs.add(lgtBidVendor.getBidVendorId());
                        }
                    });
                    if(CollectionUtils.isNotEmpty(longs)){
                        iLgtVendorService.remove(Wrappers.lambdaQuery(LgtBidVendor.class).in(LgtBidVendor::getBidVendorId,longs));
                    }
                }

            }else {
                iLgtVendorService.remove(Wrappers.lambdaQuery(LgtBidVendor.class).eq(LgtBidVendor::getBidingId,bidingId));
            }


            List<LgtPayPlan> lgtPayPlansUpdate = new ArrayList<>();
            List<LgtPayPlan> lgtPayPlansAdd = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(lgtBidVendorsUpdate)) {
                // 更新数据
                lgtBidVendorsUpdate.forEach(lgtBidVendor -> {
                    // 付款条件
                    List<LgtPayPlan> lgtPayPlans = lgtBidVendor.getLgtPayPlans();
                    if(CollectionUtils.isNotEmpty(lgtPayPlans)){
                        lgtPayPlans.forEach(lgtPayPlan -> {
                            if(ObjectUtils.isEmpty(lgtPayPlan.getPayPlanId())){
                                lgtPayPlan.setBidVendorId(lgtBidVendor.getBidVendorId());
                                lgtPayPlan.setPayPlanId(IdGenrator.generate());
                                lgtPayPlansAdd.add(lgtPayPlan);
                            }else {
                                lgtPayPlansUpdate.add(lgtPayPlan);
                            }
                        });
                    }
                    // 报价权限
                    List<LgtQuoteAuthorize> lgtQuoteAuthorizes = lgtBidVendor.getLgtQuoteAuthorizes();
                    if (CollectionUtils.isNotEmpty(lgtQuoteAuthorizes)) {
                        iLgtQuoteAuthorizeService.updateBatchById(lgtQuoteAuthorizes);
                    }
                });
                iLgtVendorService.updateBatchById(lgtBidVendorsUpdate);
            }

            // 新增数据
            if (CollectionUtils.isNotEmpty(lgtBidVendorsAdd)) {
                lgtBidVendorsAdd.forEach(lgtBidVendor -> {
                    lgtBidVendor.setBidingId(bidingId);
                    lgtBidVendor.setBidVendorId(IdGenrator.generate());
                    lgtBidVendor.setJoinFlag(YesOrNo.NO.getValue());
                    List<LgtPayPlan> lgtPayPlans = lgtBidVendor.getLgtPayPlans();
                    if(CollectionUtils.isNotEmpty(lgtPayPlans)){
                        lgtPayPlans.forEach(lgtPayPlan -> {
                            lgtPayPlan.setBidVendorId(lgtBidVendor.getBidVendorId());
                            lgtPayPlan.setPayPlanId(IdGenrator.generate());
                            lgtPayPlansAdd.add(lgtPayPlan);
                        });
                    }
                    // 报价权限
                    List<LgtQuoteAuthorize> lgtQuoteAuthorizes = lgtBidVendor.getLgtQuoteAuthorizes();
                    if (CollectionUtils.isNotEmpty(lgtQuoteAuthorizes)) {
                        iLgtQuoteAuthorizeService.updateBatchById(lgtQuoteAuthorizes);
                    }
                });
                iLgtVendorService.saveBatch(lgtBidVendorsAdd);
            }

            if(CollectionUtils.isNotEmpty(lgtPayPlansUpdate)){
                iLgtPayPlanService.updateBatchById(lgtPayPlansUpdate);
            }

            if(CollectionUtils.isNotEmpty(lgtPayPlansAdd)){
                iLgtPayPlanService.saveBatch(lgtPayPlansAdd);
            }
            // 更新流程节点状态
            updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.inviteSupplier.getValue())
                    .setBidingId(bidingId).setDataFlag(YesOrNo.YES.getValue()));

            // 更新已投标供应商字段
            int size = lgtBidVendors.size();
            String biddingSuppliers = "0/" + size;
            biding.setBiddingSuppliers(biddingSuppliers);
            this.updateById(biding);
        }
    }

    @Override
    public List<LgtBidVendor> getLgtBidVendorByBidingId(Long bidingId) {
        List<LgtBidVendor> bidVendors = iLgtVendorService.list(Wrappers.lambdaQuery(LgtBidVendor.class).eq(LgtBidVendor::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(bidVendors)){
            bidVendors.forEach(lgtBidVendor -> {
                // 付款条件
                Long bidVendorId = lgtBidVendor.getBidVendorId();
                List<LgtPayPlan> lgtPayPlans = iLgtPayPlanService.list(Wrappers.lambdaQuery(LgtPayPlan.class).
                        eq(LgtPayPlan::getBidVendorId, bidVendorId));
                lgtBidVendor.setLgtPayPlans(lgtPayPlans);
            });
        }
        return bidVendors;
    }

    @Override
    public List<VendorDto> intelligentFindVendor(Long bidingId) {
        List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                eq(LgtBidRequirementLine::getBidingId, bidingId));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtBidRequirementLines),"项目需求不能为空");
        lgtBidRequirementLines.forEach(lgtBidRequirementLine -> Assert.notNull(lgtBidRequirementLine.getLogisticsCategoryId(),
                "项目需求存物料品类为空"));
        List<Long> categoryIds = lgtBidRequirementLines.stream().map(LgtBidRequirementLine::getLogisticsCategoryId).collect(Collectors.toList());
        List<VendorDto> vendorDtos = supplierClient.queryCompanyByBusinessModeCode(categoryIds);
        return vendorDtos;
    }

    @Override
    @Transactional
    public List<LgtBidRequirementLine> updateLgtBidRequirementLine(List<LgtBidRequirementLine> lgtBidRequirementLines) {
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtBidRequirementLines),"需求明细不能为空!");
//        lgtBidRequirementLines.forEach(lgtBidRequirementLine -> Assert.notNull(lgtBidRequirementLine.getLogisticsCategoryId(),"需求明细的物流品类不能为空!"));
        Long bidingId = lgtBidRequirementLines.get(0).getBidingId();
        Assert.notNull(bidingId,"需求明细缺失参数:bidingId");
        LgtBiding lgtBiding = this.getById(bidingId);
        checkPurchaseRequirementLine(lgtBidRequirementLines,bidingId);
        String auditStatus = this.getById(bidingId).getAuditStatus();
        //审批后不操作
        if (Objects.equals(auditStatus, BiddingApprovalStatus.APPROVED.getValue())) {
            return lgtBidRequirementLines;
        }
        AtomicInteger num = new AtomicInteger(1);
        Set<String> hashSet = new HashSet<>();
        lgtBidRequirementLines.forEach(lgtBidRequirementLine -> {
            lgtBidRequirementLine.setBidRequirementLineId(IdGenrator.generate());
            lgtBidRequirementLine.setBidingId(bidingId);
            lgtBidRequirementLine.setRowNum(num.getAndAdd(1));
            setStartEndAddress(lgtBidRequirementLine);
            if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                // 项目需求唯一: 始+终+计费方式+计费单位+LEG+费项
                String onlyKey = new StringBuffer().
                        append(lgtBidRequirementLine.getStartAddress()).
                        append(lgtBidRequirementLine.getEndAddress()).
                        append(lgtBidRequirementLine.getChargeMethod()).
                        append(lgtBidRequirementLine.getChargeUnit()).
                        append(lgtBidRequirementLine.getLeg()).
                        append(lgtBidRequirementLine.getExpenseItem()).
                        toString();
                Assert.isTrue(hashSet.add(onlyKey),String.format("国内业务:存在重复路线,起:[%s],终:[%s]+计费方式:[%s]+计费单位[%s]+LEG[%s]+费项[%s]",lgtBidRequirementLine.getStartAddress(),lgtBidRequirementLine.getEndAddress(),lgtBidRequirementLine.getChargeMethod(),lgtBidRequirementLine.getChargeUnit(),lgtBidRequirementLine.getLeg(),lgtBidRequirementLine.getExpenseItem()));
            }else {
                // 项目需求唯一: 始+终
                String onlyKey = new StringBuffer().append(lgtBidRequirementLine.getStartAddress()).
                        append(lgtBidRequirementLine.getEndAddress()).
                        toString();
                Assert.isTrue(hashSet.add(onlyKey),String.format("非国内业务:存在重复路线,起:[%s],终:[%s]",lgtBidRequirementLine.getStartAddress(),lgtBidRequirementLine.getEndAddress()));
            }
        });

        iLgtBidRequirementLineService.remove(Wrappers.lambdaQuery(LgtBidRequirementLine.class).eq(LgtBidRequirementLine::getBidingId,bidingId));
        AtomicInteger rowNum = new AtomicInteger(1);
        // 填入序号
        lgtBidRequirementLines.forEach(lgtBidRequirementLine -> lgtBidRequirementLine.setRowNum(rowNum.getAndAdd(1)));
        iLgtBidRequirementLineService.saveBatch(lgtBidRequirementLines);
        // 更新流程节点状态
        updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.projectRequirement.getValue())
                .setBidingId(bidingId).setDataFlag(YesOrNo.YES.getValue()));
        return lgtBidRequirementLines;
    }

    /**
     * 校验采购商必填
     * @param lgtBidRequirementLines
     */
    public void checkPurchaseRequirementLine(List<LgtBidRequirementLine> lgtBidRequirementLines,Long bidingId){
        String sourceFrom = lgtBidRequirementLines.get(0).getSourceFrom();
        if (!SourceFrom.PURCHASE_REQUEST.name().equals(sourceFrom) && CollectionUtils.isNotEmpty(lgtBidRequirementLines)) {
            List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                    eq(LgtBidTemplate::getBidingId, bidingId).
                    eq(LgtBidTemplate::getPurchaseNotEmptyFlag, YesOrNo.YES.getValue()));
            if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
                Class<LgtBidRequirementLine> aClass = LgtBidRequirementLine.class;
                Map<String, String> fieldMap = lgtBidTemplates.stream().collect(Collectors.toMap(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode()), LgtBidTemplate::getFieldName, (k1, k2) -> k1));
                lgtBidRequirementLines.forEach(lgtBidRequirementLine -> {
                    fieldMap.keySet().forEach(field -> {
                        Object o = null;
                        boolean flag = true;
                        try {
                            Field declaredField = aClass.getDeclaredField(field);
                            declaredField.setAccessible(true);
                            o = declaredField.get(lgtBidRequirementLine);
                        } catch (Exception e) {
                            flag = false;
                        }
                        if (flag) {
                            Assert.isTrue( !ObjectUtils.isEmpty(o),String.format("项目需求:[%s]不能为空",fieldMap.get(field)));
                        }
                    });
                });
            }
        }
    }

    /**
     * 自动拼接 起运地/最终地
     * @param obj
     */
    public static <T> void setStartEndAddress(T obj){
        /**
         * 最终地 - toCountry-toProvince-toCity-toCounty-toPlace-toPort
         * 起运地 - fromCountry-fromProvince-fromCity-fromCounty-fromPlace-fromPort
         */
        List<String> startList = Arrays.asList("fromCountry", "fromProvince", "fromCity", "fromCounty", "fromPlace","fromPort");
        List<String> endList = Arrays.asList("toCountry", "toProvince", "toCity", "toCounty", "toPlace","toPort");
        StringBuffer startAddress = new StringBuffer();
        StringBuffer endAddress = new StringBuffer();
        endList.forEach(key->{
            getField(obj, endAddress,key);
        });
        startList.forEach(key->{
            getField(obj, startAddress,key);
        });
        Class aClass = obj.getClass();
        try {
            Field startAddress1 = aClass.getDeclaredField("startAddress");
            startAddress1.setAccessible(true);
            Field endAddress1 = aClass.getDeclaredField("endAddress");
            endAddress1.setAccessible(true);
            startAddress1.set(obj,startAddress.toString());
            endAddress1.set(obj,endAddress.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static <T> void getField(T obj, StringBuffer endAddress,String key) {
        try {
            Field field = obj.getClass().getDeclaredField(key);
            if (null != field) {
                field.setAccessible(true);
                Object o = field.get(obj);
                if (!ObjectUtils.isEmpty(o)) {
                    endAddress.append(o);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<LgtBidRequirementLine> getLgtBidRequirementLineByBidingId(Long bidingId) {
        List<LgtBidRequirementLine> bidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                eq(LgtBidRequirementLine::getBidingId, bidingId));
        return bidRequirementLines;
    }

    @Override
    @Transactional
    public LgtBidInfoDto getLgtBidInfoVo(Long bidingId) {
        LgtBiding biding = this.getById(bidingId);
        updateEnrollEndDatetime(biding);
        List<LgtBidFile> lgtBidFiles = iLgtFileService.list(Wrappers.lambdaQuery(LgtBidFile.class).eq(LgtBidFile::getBidingId, biding.getBidingId()));
        List<LgtFileConfig> lgtFileConfigs = iLgtFileConfigService.list(Wrappers.lambdaQuery(LgtFileConfig.class).eq(LgtFileConfig::getBidingId, bidingId));
        List<LgtGroup> lgtGroups = iLgtGroupService.list(Wrappers.lambdaQuery(LgtGroup.class).eq(LgtGroup::getBidingId, bidingId));
        List<LgtProcessNode> lgtProcessNodes = iLgtProcessNodeService.list(Wrappers.lambdaQuery(LgtProcessNode.class).eq(LgtProcessNode::getBidingId, bidingId));
        List<LgtBidTemplate> bidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).eq(LgtBidTemplate::getBidingId, biding));
        LgtBidInfoDto lgtBidInfoDto = LgtBidInfoDto.builder().
                biding(biding).fileList(lgtBidFiles).bidFileConfigList(lgtFileConfigs).groupList(lgtGroups).
                lgtBidTemplates(bidTemplates).lgtProcessNodes(lgtProcessNodes).build();
        return lgtBidInfoDto;
    }

    @Override
    @Transactional
    public PageInfo<LgtBiding> listPage(LgtBiding lgtBiding) {
        PageUtil.startPage(lgtBiding.getPageNum(), lgtBiding.getPageSize());
        /**
         * 	根据用户的品类分工权限显示需求明细上物流品类权限的记录
         * 	可以查看到自己创建的招投标记录
         */
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<Long> categoryIds = pmClient.queryCategoryIdByUserId(loginAppUser.getUserId());
        lgtBiding.setCategoryIds(categoryIds);
        lgtBiding.setCurrentUserName(loginAppUser.getUsername());
        lgtBiding.setCurrentUserId(loginAppUser.getUserId());
        List<LgtBiding> lgtBidings = this.baseMapper.listPageByBuyers(lgtBiding);
        if(CollectionUtils.isNotEmpty(lgtBidings)){
            lgtBidings.forEach(this::updateEnrollEndDatetime);
        }
        return new PageInfo<>(lgtBidings);
    }

    /**
     * 根据头表截止时间更新状态
     * @param lgtBiding
     */
    public void updateEnrollEndDatetime(LgtBiding lgtBiding){
        if (BiddingProjectStatus.ACCEPT_BID.getValue().equals(lgtBiding.getBidingStatus())) {
            Date enrollEndDatetime = lgtBiding.getEnrollEndDatetime();
            if(!ObjectUtils.isEmpty(enrollEndDatetime) && enrollEndDatetime.compareTo(new Date()) < 0){
                /**
                 * 更新项目状态为投标已截止
                 */
                lgtBiding.setBidingStatus(BiddingProjectStatus.TENDER_ENDING.getValue());
                this.updateById(lgtBiding);

                /**
                 * 更新流程节点
                 */
                updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.technicalManagement.getValue())
                        .setBidingId(lgtBiding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));
                updateNodeStatus(new LgtProcessNode().setNodeCode(ProcessNodeName.commercialManagement.getValue())
                        .setBidingId(lgtBiding.getBidingId()).setDataFlag(YesOrNo.YES.getValue()));
            }
        }
    }

    @Override
    @Transactional
    public Long updateOrAdd(LgtBidDto lgtBidDto) {
        LgtBiding biding = lgtBidDto.getBiding(); // 招标基础信息
        List<LgtFileConfig> bidFileConfigList = lgtBidDto.getBidFileConfigList(); // 供方必须上传附件
        List<LgtBidFile> fileList = lgtBidDto.getFileList(); // 招标文件
        List<LgtGroup> groupList = lgtBidDto.getGroupList(); // 工作小组
        List<LgtBidRequirementLine> requirementLineList = lgtBidDto.getRequirementLineList(); //物流招标需求行
        // 招标ID
        Long bidingId = biding.getBidingId();
        if(ObjectUtils.isEmpty(bidingId)){
            bidingId = IdGenrator.generate();
            biding.setBidingId(bidingId);
            if(ObjectUtils.isEmpty(biding.getBidingNum())){
                // 生成项目编号
                log.info("物流招标新建生成单据编号参数:"+ JSON.toJSONString(biding));
                String requirementNum = getRequirementNum(biding);
                log.info("物流招标新建生成单据编号:"+requirementNum);
                biding.setBidingNum(requirementNum);
            }
            String nickname = AppUserUtil.getLoginAppUser().getNickname();
            // 初始化信息
            biding.setAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                    setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue()).
                    setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                    setReleaseFlag(YesOrNo.NO.getValue()).
                    setCreatedByName(nickname).
                    setCurrentRound(1);
            //设置来源单据类型
            if(StringUtils.isBlank(biding.getSourceFrom())){
                biding.setSourceFrom(SourceFrom.MANUAL.getItemValue());
            }
            // 保存模板文件
            if(ObjectUtils.isEmpty(biding.getTemplateFileId())){
                LogisticsTemplateHead templateHead = iLogisticsTemplateHeadService.getById(biding.getTemplateHeadId());
                biding.setTemplateFileId(templateHead.getTemplateFileId());
                biding.setTemplateFileName(templateHead.getTemplateFileName());
            }
            this.save(biding);
        }else {
            this.updateById(biding);
        }
        // 保存招标文件
        sevaLgtBidFiles(biding, fileList, bidingId);
        // 保存供方必须上传附件
        saveLgtFileConfigs(biding, bidFileConfigList, bidingId);
        // 保存工作小组
        saveLgtGroups(biding, groupList, bidingId);
        // 保存招标流程节点
        Long processConfigId = biding.getProcessConfigId();
        saveNodes(bidingId,processConfigId);
        // 保存需求明细模板表
        saveLgtBidTemplates(biding, bidingId);
        // 保存需求行
        if (CollectionUtils.isNotEmpty(requirementLineList)) {
            Long finalBidingId = bidingId;
            requirementLineList.forEach(lgtBidRequirementLine -> {
                lgtBidRequirementLine.setBidingId(finalBidingId);
                lgtBidRequirementLine.setSourceFrom(SourceFrom.PURCHASE_REQUEST.name());
            });
            updateLgtBidRequirementLine(requirementLineList);
        }
        return bidingId;
    }

    @Override
    @Transactional
    public Long add(LgtBidDto lgtBidDto) {
        // 检查参数
        checkParam(lgtBidDto);
        return updateOrAdd(lgtBidDto);
    }

    @Override
    @Transactional
    public Long modify(LgtBidDto lgtBidDto) {
        // 检查参数
        checkParam(lgtBidDto);
        //审批后不操作
        if (Objects.equals(lgtBidDto.getBiding().getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())) {
            return lgtBidDto.getBiding().getBidingId();
        }
        return updateOrAdd(lgtBidDto);
    }

    /**
     * <pre>
     *  //业务模式为国内时，字母为D，业务模式为国际时，字母为F，业务模式为海外时，字母为S；
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     * <p>
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-11-27
     *  修改内容:
     * </pre>
     */
    public String getRequirementNum(LgtBiding biding) {
        String code = biding.getBusinessModeCode();
        Assert.isTrue(!ObjectUtils.isEmpty(code),"业务模式不能为空!");
        AtomicReference<String> reequirementNum = new AtomicReference<>();
        if (BusinessMode.INSIDE.getValue().equals(code)) {
            reequirementNum.set(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_D));
        } else if (BusinessMode.OUTSIDE.getValue().equals(code)) {
            reequirementNum.set(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_F));
        } else {
            reequirementNum.set(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_S));
        }
        return reequirementNum.get();
    }

    private void checkParam(LgtBidDto lgtBidDto) {
        Assert.notNull(lgtBidDto,"参数不能为空!");
        LgtBiding biding = lgtBidDto.getBiding();
        Assert.notNull(biding.getTemplateHeadId(),"缺少参数:物流采购申请模板头ID(templateHeadId)!");
        Assert.notNull(biding.getProcessConfigId(),"缺少参数:招标流程配置ID(processConfigId)!");
        //当来源单据类型为采购申请时,不用做投标截止时间非空校验
        if(!SourceFrom.PURCHASE_REQUEST.getItemValue().equals(biding.getSourceFrom())){
            Assert.notNull(biding.getEnrollEndDatetime(),"投标截止时间不能为空!");
            Assert.notNull(biding.getStandardCurrency(),"本位币种不能为空!");
        }
        List<LgtFileConfig> bidFileConfigList = lgtBidDto.getBidFileConfigList();
        if(CollectionUtils.isNotEmpty(bidFileConfigList)){
            bidFileConfigList.forEach(lgtFileConfig -> {
                Assert.isTrue(StringUtils.isNotBlank(lgtFileConfig.getReferenceFileType()),"供方必须上传附件-附件类型:不能为空!");
            });
        }
        List<LgtGroup> groupList = lgtBidDto.getGroupList();
        boolean hasTechnology=false;
        boolean hasBusiness=false;
        for (LgtGroup group : groupList) {
            String judgeFlag = group.getJudgeFlag();
            if(Objects.equals(judgeFlag,"Y")&&!hasTechnology){
                hasTechnology=true;
            }
            if(!Objects.equals(judgeFlag,"Y")&&!hasBusiness){
                hasBusiness=true;
            }
            if(hasBusiness&&hasTechnology){
                break;
            }
        }
        if(!(hasBusiness&&hasTechnology)){
            throw new BaseException("招标项目必须有一位技术人员和一位商务人员");
        }
    }

    public void saveLgtBidTemplates(LgtBiding biding, Long bidingId) {
        Long templateHeadId = biding.getTemplateHeadId();
        List<LgtBidTemplate> bidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).eq(LgtBidTemplate::getBidingId, biding.getBidingId()));
        if (CollectionUtils.isEmpty(bidTemplates)) {
            List<LogisticsTemplateLine> templateLines = iLogisticsTemplateLineService.list(Wrappers.lambdaQuery(LogisticsTemplateLine.class).eq(LogisticsTemplateLine::getHeadId, templateHeadId));
            if(CollectionUtils.isNotEmpty(templateLines)){
                List<LgtBidTemplate> lgtBidTemplates = new ArrayList<>();
                templateLines.forEach(logisticsTemplateLine -> {
                    LgtBidTemplate lgtBidTemplate = new LgtBidTemplate();
                    BeanCopyUtil.copyProperties(lgtBidTemplate,logisticsTemplateLine);
                    lgtBidTemplate.setBidingId(bidingId);
                    lgtBidTemplate.setBidTemplateId(IdGenrator.generate());
                    lgtBidTemplates.add(lgtBidTemplate);
                });
                iLgtBidTemplateService.saveBatch(lgtBidTemplates);
            }
        }
    }

    public void saveNodes(Long bidingId, Long processConfigId) {
        if (!ObjectUtils.isEmpty(processConfigId)) {
            List<LgtProcessNode> lgtProcessNodes = iLgtProcessNodeService.list(Wrappers.lambdaQuery(LgtProcessNode.class).eq(LgtProcessNode::getBidingId, bidingId));
            if(CollectionUtils.isEmpty(lgtProcessNodes)){
                // 获取招标流程节点信息
                BidProcessConfig bidProcessConfig = bidClient.getBidProcessConfigById(processConfigId);
                Assert.notNull(bidProcessConfig,"未找到招标流程配置信息");
                List<LgtProcessNode> processNodes = new ArrayList<>();

                /** 项目信息 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectInformation())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.projectInformation.getValue());
                    processNode.setDataFlag(YesOrNo.YES.getValue());
                    processNodes.add(processNode);
                }
                /** 技术交流 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnologyExchange())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.technologyExchange.getValue());
                    processNodes.add(processNode);
                }
                /** 项目需求 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectRequirement())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.projectRequirement.getValue());
                    processNodes.add(processNode);
                }
                /** 邀请供应商 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getInviteSupplier())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.inviteSupplier.getValue());
                    processNodes.add(processNode);
                }
                /** 评分规则 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getScoringRule())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.scoringRule.getValue());
                    processNodes.add(processNode);
                }
                /** 流程审批 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProcessApproval())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.processApproval.getValue());
                    processNodes.add(processNode);
                }
                /** 供应商绩效 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getSupplierPerformance())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.supplierPerformance.getValue());
                    processNodes.add(processNode);
                }
                /** 拦标价 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTargetPrice())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.targetPrice.getValue());
                    processNodes.add(processNode);
                }
                /** 项目发布 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectPublish())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.projectPublish.getValue());
                    processNodes.add(processNode);
                }
                /** 报名管理 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getEntryManagement())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.entryManagement.getValue());
                    processNodes.add(processNode);
                }
                /** 质疑澄清 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getQuestionClarification())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.questionClarification.getValue());
                    processNodes.add(processNode);
                }
                /** 投标控制 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidingControl())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.bidingControl.getValue());
                    processNodes.add(processNode);
                }
                /** 技术评分 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnicalScore())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.technicalScore.getValue());
                    processNodes.add(processNode);
                }
                /** 技术标管理 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnicalManagement())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.technicalManagement.getValue());
                    processNodes.add(processNode);
                }
                /** 商务标管理 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getCommercialManagement())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.commercialManagement.getValue());
                    processNodes.add(processNode);
                }
                /** 评选 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidEvaluation())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.bidEvaluation.getValue());
                    processNodes.add(processNode);
                }
                /** 招标结果 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidingResult())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.bidingResult.getValue());
                    processNodes.add(processNode);
                }
                /** 结项报告 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectReport())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.projectReport.getValue());
                    processNodes.add(processNode);
                }
                /** 结项审批 */
                if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectApproval())){
                    LgtProcessNode processNode = new LgtProcessNode();
                    processNode.setNodeCode(ProcessNodeName.projectApproval.getValue());
                    processNodes.add(processNode);
                }
                processNodes.forEach(node->{
                    Long id = IdGenrator.generate();
                    node.setNodeId(id);
                    node.setBidingId(bidingId);
                    node.setProcessConfigId(processConfigId);
                });
                iLgtProcessNodeService.saveBatch(processNodes);
            }
        }
    }

    public void saveLgtGroups(LgtBiding biding, List<LgtGroup> groupList, Long bidingId) {
        iLgtGroupService.remove(Wrappers.lambdaQuery(LgtGroup.class).eq(LgtGroup::getBidingId,bidingId));
        if(CollectionUtils.isNotEmpty(groupList)){
            groupList.forEach(lgtGroup -> {
                lgtGroup.setBidingId(biding.getBidingId());
                lgtGroup.setGroupId(IdGenrator.generate());
            });
            iLgtGroupService.saveBatch(groupList);
        }
    }

    public void saveLgtFileConfigs(LgtBiding biding, List<LgtFileConfig> bidFileConfigList, Long bidingId) {
        iLgtFileConfigService.remove(Wrappers.lambdaQuery(LgtFileConfig.class).eq(LgtFileConfig::getBidingId,bidingId));
        if(CollectionUtils.isNotEmpty(bidFileConfigList)){
            bidFileConfigList.forEach(lgtFileConfig -> {
                lgtFileConfig.setBidingId(biding.getBidingId());
                lgtFileConfig.setRequireId(IdGenrator.generate());
            });
            iLgtFileConfigService.saveBatch(bidFileConfigList);
        }
    }

    public void sevaLgtBidFiles(LgtBiding biding, List<LgtBidFile> fileList, Long bidingId) {
        iLgtFileService.remove(Wrappers.lambdaQuery(LgtBidFile.class).eq(LgtBidFile::getBidingId,bidingId));
        if(CollectionUtils.isNotEmpty(fileList)){
            fileList.forEach(lgtBidFile -> {
                lgtBidFile.setBidingId(biding.getBidingId());
                lgtBidFile.setFileId(IdGenrator.generate());
            });
            iLgtFileService.saveBatch(fileList);
        }
    }
}
