package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.workflow.BrgInitProjectFlow;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.EvaluateMethodEnum;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.check.PreCheck;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.bidprocessconfig.service.IBidProcessConfigService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidcontrol.service.IBidControlService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.*;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.workflow.BrgInitProjectFlow;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service.ISignUpService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.informationsupplement.service.IBidVendorPerformanceService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.bargaining.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.*;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.ProjectInfoVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.VendorPerformance;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.quoteauthorize.entity.QuoteAuthorize;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.midea.cloud.common.enums.bargaining.projectmanagement.bidinitiating.BidingScope.INVITE_TENDER;

/**
 * <pre>
 *  招标基础信息表 服务实现类
 * </pre>
 *
 * @author fengdc3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-18 10:10:11
 *  修改内容:
 * </pre>
 */
@Service
public class BidingServiceImpl extends ServiceImpl<BidingMapper, Biding> implements IBidingService {
    @Resource
    private BrgInitProjectFlow brgInitProjectFlow;

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private PmClient pmClient;
    @Autowired
    private IGroupService iGroupService;
    @Autowired
    private IBidFileService iBidFileService;
    @Autowired
    private IBidFileConfigService iBidFileConfigService;
    @Autowired
    private IBidProcessConfigService iBidProcessConfigService;
    @Autowired
    private IBidRequirementService iBidRequirementService;
    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;
    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IBidScoreRuleService iBidScoreRuleService;
    @Autowired
    private IBidScoreRuleLineService iBidScoreRuleLineService;
    @Autowired
    private IBidProcessService iBidProcessService;
    @Autowired
    private IBidVendorPerformanceService iBidVendorPerformanceService;
    @Autowired
    private WorkFlowFeign workFlowFeign;
    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private IQuoteAuthorizeService iQuoteAuthorizeService;
    @Autowired
    private IBidControlService iBidControlService;
    @Autowired
    private ISignUpService signUpService;
    @Autowired
    private RedisUtil redisUtil;

    private final EntityManager<BidRequirementLine> demandLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<ScoreRule> scoreRuleDao
            = EntityManager.use(BidScoreRuleMapper.class);
    private final EntityManager<ScoreRuleLine> scoreRuleLineDao
            = EntityManager.use(BidScoreRuleLineMapper.class);


    /**
     * 询比价查询列表
     *
     * @param biding
     * @return
     */
    @Override
    @AuthData(module = MenuEnum.BIDDING_PROJECT_NEW)
    public PageInfo<Biding> listPage(Biding biding) {
        PageUtil.startPage(biding.getPageNum(), biding.getPageSize());
        QueryWrapper<Biding> wrapper = new QueryWrapper<Biding>();
        wrapper.like(StringUtils.isNoneBlank(biding.getBidingName()),
                "BIDING_NAME", biding.getBidingName());
        wrapper.like(StringUtils.isNoneBlank(biding.getBidingNum()),
                "BIDING_NUM", biding.getBidingNum());
        wrapper.eq(StringUtils.isNoneBlank(biding.getBidingStatus()),
                "BIDING_STATUS", biding.getBidingStatus());
        wrapper.eq(StringUtils.isNoneBlank(biding.getAuditStatus()),
                "AUDIT_STATUS", biding.getAuditStatus());
        wrapper.like(StringUtils.isNoneBlank(biding.getCreatedBy()),
                "CREATED_BY", biding.getCreatedBy());
        wrapper.eq(StringUtils.isNoneBlank(biding.getEvaluateMethod()),
                "EVALUATE_METHOD", biding.getEvaluateMethod());
        wrapper.orderByDesc("CREATION_DATE");
        return new PageInfo<Biding>(this.list(wrapper));
    }

    @Override
    @Transactional
    @PreCheck(checkMethod = "preCheckOpProjectInfo")
    public Long saveProjectInfo(ProjectInfoVO projectInfoVO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (redisUtil.tryLock("brgSaveProjectInfo" + loginAppUser.getUserId(), 2, TimeUnit.MINUTES)) {
            Long bidingId = IdGenrator.generate();
            try {
                Biding biding = projectInfoVO.getBiding();
                List<BidFile> fileList = projectInfoVO.getFileList();
                List<BidFileConfig> bidFileConfigList = projectInfoVO.getBidFileConfigList();
                List<Group> groupList = projectInfoVO.getGroupList();

                this.saveBiding(biding, bidingId);
                iBidFileService.saveBatchBidFile(fileList, bidingId);
                iBidFileConfigService.saveBatchBidFileConfig(bidFileConfigList, bidingId);
                iGroupService.saveBatchGroup(groupList, bidingId);
            }finally {
                redisUtil.unLock("brgSaveProjectInfo" + loginAppUser.getUserId());
            }
            return bidingId;
        }
        throw new BaseException(String.format("亲爱的%s,您好！询价单正在保存ing，请稍后再编辑或刷新重试^_^", loginAppUser.getNickname()));
    }

    private boolean isLimit(BidProcessConfig processConfig) {
        return "Y".equals(processConfig.getTechnologyExchange()) ||
                "Y".equals(processConfig.getTechnicalScore()) ||
                "Y".equals(processConfig.getTechnicalManagement());
    }

    private void preCheckOpProjectInfo(ProjectInfoVO projectInfoVO) {
        Biding biding = projectInfoVO.getBiding();
        BidProcessConfig processConfig = iBidProcessConfigService.getById(biding.getProcessConfigId());
        //若流程模板中选择了"技术交流""技术评分""技术标管理"其中之一，则将"招标类型"限制为"技术招标+商务招标"
        if (isLimit(processConfig) && !(BidType.TECHNOLOGY_BUSINESS.getValue()).equals(biding.getBidingType())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("该流程模板存在限制,招标类型应为'技术招标+商务招标'"));
        }
        //如果是综合评分，至少有一个评委
        if (Objects.equals(processConfig.getEvaluateMethod(), EvaluateMethodEnum.COMPOSITE_SCORE.getValue())) {
            projectInfoVO.getGroupList().stream().filter(e -> Objects.equals(e.getJudgeFlag(), "Y")).findAny().orElseThrow(() -> new BaseException("综合评分至少需要一名技术评委"));
        }
        List<Group> groupList = projectInfoVO.getGroupList();
        checkBidingInfo(projectInfoVO.getBiding());
//        Assert.isTrue(CollectionUtils.isNotEmpty(projectInfoVO.getBidFileConfigList()), "供方必须上传附件配置不能为空");
//        Assert.isTrue(CollectionUtils.isNotEmpty(groupList), "工作小组不能为空");
        if (CollectionUtils.isNotEmpty(groupList)) {
            groupList.forEach(e -> {
                Assert.notNull(e.getUserName(), "成员账号不能为空");
                Assert.notNull(e.getFullName(), "成员姓名不能为空");
                Assert.notNull(e.getUserName(), "电话不能为空");
                Assert.notNull(e.getUserName(), "邮箱不能为空");
                Assert.notNull(e.getUserName(), "岗位不能为空");
            });
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreCheck(checkMethod = "preCheckOpProjectInfo")
    public void updateProjectInfo(ProjectInfoVO projectInfoVO) {
        Biding biding = projectInfoVO.getBiding();
        //让他不进行修改
        biding.setBidingNum(null);

        List<BidFile> fileList = projectInfoVO.getFileList();
        List<BidFileConfig> bidFileConfigList = projectInfoVO.getBidFileConfigList();
        List<Group> groupList = projectInfoVO.getGroupList();

        this.updateById(biding);
        // 如果 项目信息的[默认价格有效期]不为空，则给价格有效期为空的需求行上补充
        if (biding.getDefaultPriceValidFrom() != null && biding.getDefaultPriceValidTo() != null) {
            List<BidRequirementLine> demandLines = demandLineDao
                    .findAll(Wrappers.lambdaQuery(BidRequirementLine.class)
                            .eq(BidRequirementLine::getBidingId, biding.getBidingId())
                            .isNull(BidRequirementLine::getPriceStartTime)
                            .isNull(BidRequirementLine::getPriceEndTime)
                    );
            demandLines.forEach(demandLine -> {
                demandLine.setPriceStartTime(biding.getDefaultPriceValidFrom());
                demandLine.setPriceEndTime(biding.getDefaultPriceValidTo());
            });
            demandLineDao.save(demandLines);
        }

        iBidFileService.updateBatchBidFile(fileList, biding);
        iBidFileConfigService.updateBatchBidFileConfig(bidFileConfigList, biding);
        iGroupService.updateBatchGroup(groupList, biding);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByBidingId(Long id) {
        Assert.notNull(id, "id不能为空");
        this.removeById(id);
        iGroupService.remove(new QueryWrapper<>(new Group().setBidingId(id)));
        iBidFileService.remove(new QueryWrapper<>(new BidFile().setBidingId(id)));
        iBidFileConfigService.remove(new QueryWrapper<>(new BidFileConfig().setBidingId(id)));
        iBidRequirementService.remove(new QueryWrapper<>(new BidRequirement().setBidingId(id)));
        iBidRequirementLineService.remove(new QueryWrapper<>(new BidRequirementLine().setBidingId(id)));
        iBidVendorService.remove(new QueryWrapper<>(new BidVendor().setBidingId(id)));
        iBidScoreRuleService.remove(new QueryWrapper<>(new ScoreRule().setBidingId(id)));
        iBidScoreRuleLineService.remove(new QueryWrapper<>(new ScoreRuleLine().setBidingId(id)));
        iBidProcessService.remove(new QueryWrapper<>(new BidProcess().setBidingId(id)));
        iBidVendorPerformanceService.remove(new QueryWrapper<>(new VendorPerformance().setBidingId(id)));
        iQuoteAuthorizeService.remove(new QueryWrapper<QuoteAuthorize>(new QuoteAuthorize().setBidingId(id)));
        //删除需求池的后续回写的招标id
        pmClient.deleteByRequirementLineId(id);
    }

    /**
     * 废弃订单
     *
     * @param bidingId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abandon(Long bidingId) {
        Biding biding = this.getById(bidingId);
        Assert.notNull(biding, "找不到需要废弃的询价单。");
        String auditStatus = biding.getAuditStatus();
        Assert.isTrue(BiddingApprovalStatus.APPROVED.getValue().equals(auditStatus)
                ||BiddingApprovalStatus.REJECTED.getValue().equals(auditStatus)
                ||BiddingApprovalStatus.WITHDRAW.getValue().equals(auditStatus)
                , "已审批、已撤回、已驳回的询价单才可以废弃。");
        biding.setAuditStatus(BiddingApprovalStatus.ABANDONED.getValue());
        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(bidingId);
        if (srmworkflowForm != null) {
            try {
                if(!BiddingApprovalStatus.APPROVED.getValue().equals(auditStatus)){
                    biding.setProcessType("N");
                    brgInitProjectFlow.submitBrgInitProjectConfFlow(biding);
                }
            } catch (Exception e) {
                Assert.isTrue(false, "驳回同步审批流失败。");
            }
        }
        this.updateById(biding);
        pmClient.deleteByRequirementLineId(bidingId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");
        Biding biding = this.getById(bidingId);
        //审批状态必须为:申请已审批
        if (!(BiddingApprovalStatus.APPROVED.getValue()).equals(biding.getAuditStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("项目非已审批状态,不能发布项目"));
        }
        //校验项目状态必须为:未发布
        if (!(BiddingProjectStatus.UNPUBLISHED.getValue()).equals(biding.getBidingStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("项目状态非未发布状态,不能发布项目"));
        }
        Biding updateBiding = new Biding().setBidingStatus(BiddingProjectStatus.ACCEPT_SIGNUP.getValue()).
                setReleaseDatetime(new Date()).setReleaseFlag("Y");
        //发布后可以让供应商投标
        Integer currentRoundCount = biding.getCurrentRound();
        if (Objects.isNull(currentRoundCount) || currentRoundCount.equals(0)) {
            //若为邀请招标且保证金为0，则把默认把供应商都报名,并且把当前状态改为接受投标中
            int i = biding.getBondAmount().subtract(BigDecimal.ZERO).compareTo(BigDecimal.ZERO);
            if (Objects.equals(biding.getBidingScope(), INVITE_TENDER.getValue()) && i <= 0) {
                updateBiding.setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue());
                List<SignUp> list = signUpService.list(Wrappers.lambdaQuery(SignUp.class).eq(SignUp::getBidingId, biding));
                if (CollectionUtils.isEmpty(list)) {
                    signUpForSupplier(bidingId);
                }
                iBidControlService.startBiding(bidingId, biding.getEnrollEndDatetime());
            }
        }
        this.update(updateBiding, new QueryWrapper<>(new Biding().setBidingId(bidingId)));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //发布时校验招标单是否来自采购需求,如果是的话就往采购需求行回写后续单据名称
                pmClient.updateIfExistRequirementLine(FollowNameParam.builder()
                        .sourceForm(FollowNameParam.SourceForm.builder()
                                .formNum(biding.getBidingNum())
                                .formTitle(biding.getBidingName())
                                .build())
                        .build()
                );
            }
        });


    }

    @Override
    public void updatePermission(Biding biding) {
        Assert.notNull(biding.getBidingId(), "招标id不能为空");
        this.updateById(biding);
    }

    @Transactional
    @Override
    public void initProjectApproval(Long bidingId) {

        // 若招标类型是[商务]，且没有评分规则，则需要自动填充默认评分规则
        if (this.isNeedAutoCreateDefaultBusinessEvaluateRule(bidingId))
            this.autoCreateDefaultBusinessEvaluateRule(bidingId);

        Biding biding = new Biding();
        biding.setBidingId(bidingId);
        biding.setAuditStatus(BiddingApprovalStatus.APPROVED.getValue());
        biding.setBidingStatus(BiddingProjectStatus.UNPUBLISHED.getValue());
        this.updateById(biding);
    }

    @Override
    public void endProjectApproval(Long bidingId) {
        Biding biding = new Biding();
        biding.setBidingId(bidingId);
        biding.setBidingStatus(BiddingProjectStatus.PROJECT_END.getValue());
        this.updateById(biding);
    }

    @Override
    @Transactional
    public String requirementGenBiding(List<RequirementLine> requirementLineList) {
        String code = null;
        if (CollectionUtils.isNotEmpty(requirementLineList)) {
            Biding biding = new Biding();
            Long bidingId = IdGenrator.generate();
            code = baseClient.seqGen(SequenceCodeConstant.SEQ_BID_PROJECT_CODE);
            biding.setBidingId(bidingId).setBidingNum(code).
                    setAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue()).
                    setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
            this.save(biding);

            //保存需求
            Long requirementId = IdGenrator.generate();
            BidRequirement bidRequirement = new BidRequirement();
            bidRequirement.setBidingId(bidingId);
            bidRequirement.setOrgId(requirementLineList.get(0).getOrgId());
            bidRequirement.setRequirementId(requirementId);
            iBidRequirementService.save(bidRequirement);

            // 保存行
            requirementLineList.forEach(requirementLine -> {
                List<PurchaseUnit> purchaseUnitList = baseClient.listPurchaseUnitByParam(new PurchaseUnit().setUnitName(requirementLine.getUnit()));
                BidRequirementLine bidLine = new BidRequirementLine();
                //此处注意BigDecimal转double可能会丢失精度
                bidLine.setRequirementLineId(IdGenrator.generate()).setRequirementId(requirementId).
                        setBidingId(bidingId).setUomDesc(requirementLine.getUnit()).
                        setTargetId(requirementLine.getMaterialId()).setTargetNum(requirementLine.getMaterialName()).
                        setTargetDesc(requirementLine.getMaterialCode()).setQuantity(requirementLine.getRequirementQuantity().doubleValue()).
                        setTaxRate(requirementLine.getTaxRate()).setTaxKey(requirementLine.getTaxKey()).setOrgId(requirementLine.getOrgId()).
                        setOrgName(requirementLine.getPurchaseOrganization()).setCategoryId(requirementLine.getCategoryId()).
                        setCategoryName(requirementLine.getCategoryName());

                //当价格目录的未税单价有记录时才插入对应的数据
                if (requirementLine.getNotaxPrice() != null) {
                    if (CollectionUtils.isEmpty(purchaseUnitList)) {
                        throw new BaseException(LocaleHandler.getLocaleMsg("根据单位名称查询单位信息为空"));
                    }
                    bidLine.setUomCode(purchaseUnitList.get(0).getUnitCode());
                }

                iBidRequirementLineService.save(bidLine);
            });
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callBackForWorkFlow(Biding biding) {
        if (Objects.isNull(biding.getBidingId()) || Objects.isNull(biding.getAuditStatus())) {
            throw new BaseException("招标单id或审批状态不能为空");
        }
        BiddingApprovalStatus biddingApprovalStatus = BiddingApprovalStatus.valueOf(biding.getAuditStatus());
        if (Objects.isNull(biddingApprovalStatus)) {
            throw new BaseException("找不到对应的审批状态");
        }
        LambdaUpdateWrapper<Biding> wrapper = Wrappers.lambdaUpdate(Biding.class)
                .set(Biding::getAuditStatus, biddingApprovalStatus.getValue())
                .set(Biding::getCeeaDrafterOpinion, biding.getCeeaDrafterOpinion())
                .eq(Biding::getBidingId, biding.getBidingId());
        if (biddingApprovalStatus.equals(BiddingApprovalStatus.APPROVED)) {
            initProjectApproval(biding.getBidingId());
            publish(biding.getBidingId());
        }
        update(wrapper);
    }


    private void saveBiding(Biding biding, Long bidingId) {
        biding.setBidingId(bidingId).setBidingNum(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_PROJECT_CODE)).
                setAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue()).
                setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue()).
                setSourceFrom(SourceFrom.MANUAL.getItemValue());
        this.save(biding);
    }

    private void checkBidingInfo(Biding biding) {
        Assert.notNull(biding, "招标基础信息不能为空");
        boolean isZero = BigDecimal.ZERO.equals(biding.getBondAmount());
        if (Objects.isNull(biding.getBondEndDatetime()) && !isZero) {
            throw new BaseException(LocaleHandler.getLocaleMsg("保证金提交截止时间不能为空"));
        }
        if (biding.getEnrollEndDatetime() == null || biding.getEnrollEndDatetime().toString().length() == 0) {
            throw new BaseException("询价截止时间不能为空");
        }
        if (biding.getEnrollEndDatetime().before(new Date())) {
            throw new BaseException("询价截止时间必须大于当前时间");
        }
        if (!isZero && biding.getBondEndDatetime().compareTo(biding.getEnrollEndDatetime()) > 0) {
            throw new BaseException(LocaleHandler.getLocaleMsg("保证金提交截止时间不能晚于报名截止时间"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> initProjectWorkFlow(Long bidingId, Long menuId, String templateCode, String title) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        boolean enable = this.checkParam(bidingId, menuId, templateCode);
        if (enable) {
            /*try {
                stringObjectMap = workFlowFeign.initProcess(buildCbpmRquestParamDTO(templateCode, bidingId, title));
            } catch (Exception e) {
                log.error("操作失败", e);
                throw new BaseException(LocaleHandler.getLocaleMsg("远程调用工作流接口失败"));
            }*/
            String key = "bar-submit-flow" + bidingId;
            if (!redisUtil.tryLock(key, 2, TimeUnit.MINUTES)) {
                Map<String,Object> map=new HashMap<>();
                map.put("submit","Y");
                return map;
            }
            try {
                Biding byId = this.getById(bidingId);
                if (BiddingApprovalStatus.DRAFT.getValue().equals(byId.getAuditStatus()) ||
                        BiddingApprovalStatus.REJECTED.getValue().equals(byId.getAuditStatus()) ||
                        BiddingApprovalStatus.WITHDRAW.getValue().equals(byId.getAuditStatus())
                ) {
                    byId.setAuditStatus(BiddingApprovalStatus.SUBMITTED.getValue());
                    this.updateById(byId);
                    brgInitProjectFlow.submitBrgInitProjectConfFlow(byId);
                }
                Map<String,Object> map=new HashMap<>();
                map.put("submit","Y");
                return map;
            } catch (Exception e) {
                Assert.isTrue(false, "审批流提交失败，错误信息：" + e.getMessage());
            } finally {
                redisUtil.unLock(key);
            }

        } else {
            if (CbpmFormTemplateIdEnum.END_PROJECT_APPROVAL.getKey().equals(templateCode)) {
                endProjectApproval(bidingId);
            } else {
                Integer count = count(Wrappers.lambdaQuery(Biding.class).eq(Biding::getBidingId, bidingId)
                        .eq(Biding::getAuditStatus, BiddingApprovalStatus.DRAFT.getValue()));
                //同意后就发布
                if (count != 0) {
                    initProjectApproval(bidingId);
                    publish(bidingId);
                }
            }
        }
        return stringObjectMap;

    }

    private Boolean checkParam(Long bidingId, Long menuId, String cbpmTemplateCode) {
        Assert.notNull(bidingId, "招标id不能为空");
        Assert.notNull(menuId, "菜单id不能为空");
        Biding biding = this.getById(bidingId);
        Assert.notNull(biding, "查询招标信息为空");

        //当结项审批时未结束评选则退出
        if (CbpmFormTemplateIdEnum.END_PROJECT_APPROVAL.getKey().equals(cbpmTemplateCode)
                && !"Y".equals(biding.getEndEvaluation())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("未结束评选,不能提交结项审批"));
        }


        //判断是否启用工作流
        Permission menu = rbacClient.getMenu(menuId);

        if (menu != null) {
            return menu.getEnableWorkFlow().equals("Y");
        }
        return false;
    }

    /**
     * 构建初始化流程参数
     */
    private CbpmRquestParamDTO buildCbpmRquestParamDTO(String templateCode, Long bidingId, String title) {
        Biding biding = this.getById(bidingId);
        CbpmRquestParamDTO cbpmRquestParam = new CbpmRquestParamDTO();
        cbpmRquestParam.setTemplateCode(templateCode);
        cbpmRquestParam.setBusinessId(String.valueOf(bidingId));
        cbpmRquestParam.setSubject(title + biding.getBidingName());
        return cbpmRquestParam;
    }

    private void signUpForSupplier(Long bidingId) {
        Date now = new Date();
        List<BidVendor> bidVendors = iBidVendorService.list(Wrappers.lambdaQuery(BidVendor.class)
                .eq(BidVendor::getBidingId, bidingId)).stream()
                .peek(e -> e.setJoinFlag(YesOrNo.YES.getValue()))
                .collect(Collectors.toList());

        List<Long> supplierIds = bidVendors.stream().map(BidVendor::getVendorId).collect(Collectors.toList());

        List<SignUp> signUps = supplierIds.stream().map(e -> {
            SignUp up = new SignUp();
            up.setBidingId(bidingId);
            up.setVendorId(e);
            up.setSignUpStatus(SignUpStatus.SIGNUPED.getValue());
            up.setReplyDatetime(now);
            return up;
        }).collect(Collectors.toList());
        signUpService.saveBatch(signUps);
        iBidVendorService.updateBatch(bidVendors);
    }

    /**
     * 自动创建 默认[商务]评分规则
     *
     * @param biddingId 招标单ID
     */
    protected void autoCreateDefaultBusinessEvaluateRule(Long biddingId) {

        // 创建 默认[商务]评分规则
        ScoreRule scoreRule = new ScoreRule()
                .setRuleConfigName("自动补充评分规则")
                .setTotalScore(BigDecimal.valueOf(100))
                .setEnableFlag("Y");
        ScoreRuleLine scoreRuleLine = new ScoreRuleLine()
                .setScoreDimension(ScoreDimensionEnum.PRICE.getValue())
                .setScoreItem("价格")
                .setScoreStandard("价格")
                .setScoreSource("SYS_VALUE")
                .setScoreWeight(100)
                .setFullScore(BigDecimal.valueOf(100));


        // 存储 评分规则[头]
        scoreRuleDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setRuleId(IdGenrator.generate())
                        .setBidingId(biddingId)
                )
                .save(scoreRule);
        // 存储 评分规则[行]
        scoreRuleLineDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setRuleLineId(IdGenrator.generate())
                        .setRuleId(scoreRule.getRuleId())
                        .setBidingId(biddingId)
                )
                .save(scoreRuleLine);
    }

    /**
     * 是否需要自动创建 默认[商务]评分规则
     *
     * @param biddingId 招标单ID
     * @return 判断结果
     */
    protected boolean isNeedAutoCreateDefaultBusinessEvaluateRule(Long biddingId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingId)
                .map(this::getById)
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + biddingId + "]"));

        // 已存在评分规则，无需自动创建
        if (scoreRuleDao.count(Wrappers.lambdaQuery(ScoreRule.class).eq(ScoreRule::getBidingId, biddingId)) > 0)
            return false;

        // 招标类型非[商务]类型的，无需自动创建
        if (!BidType.BUSINESS.getValue().equals(bidding.getBidingType()))
            return false;

        return true;
    }
}
