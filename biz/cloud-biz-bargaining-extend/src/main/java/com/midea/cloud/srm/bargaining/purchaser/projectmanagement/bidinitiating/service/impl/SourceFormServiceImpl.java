package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.bidprocessconfig.service.IProcessNodeService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.*;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.ISourceFormService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bargaining.purchaser.bidprocessconfig.entity.ProcessNode;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implement of {@link ISourceFormService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class SourceFormServiceImpl implements ISourceFormService {

    private final EntityManager<Biding>             biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidRequirement>     demandHeaderDao
            = EntityManager.use(BidRequirementMapper.class);
    private final EntityManager<BidRequirementLine> demandLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<BidVendor>          bidVendorDao
            = EntityManager.use(BidVendorMapper.class);

    @Resource
    private IGroupService groupService;
    @Resource
    private BaseClient      baseClient;
    @Resource
    private IProcessNodeService processNodeService;
    @Resource
    private BidFileConfigMapper bidFileConfigMapper;
    @Resource
    private BidFileMapper bidFileMapper;
    @Resource
    private BidBidingCurrencyMapper currencyMapper;


    @Transactional
    @Override
    public GenerateSourceFormResult generateForm(GenerateSourceFormParameter generateParameter) {
        SourceForm sourceForm = Optional.ofNullable(generateParameter.getSourceForm())
                .orElseThrow(() -> new BaseException("[sourceForm] could not be null."));

        // 获取 当前登录人
        LoginAppUser currentUser = Optional.ofNullable(AppUserUtil.getLoginAppUser())
                .orElseThrow(() -> new BaseException("获取当前登录账户信息失败。"));

        // 获取 寻源单
        Biding bidding = Optional.ofNullable(sourceForm.getBidding())
                .orElseThrow(() -> new BaseException("获取寻源单信息失败。"))
                .setBidingId(null);


        // 获取 寻源需求[头]
        BidRequirement demandHeader = Optional.ofNullable(sourceForm.getDemandHeader())
                .orElseThrow(() -> new BaseException("获取寻源需求[头]信息失败。"))
                .setRequirementId(null);

        // 获取 寻源需求[行]
        List<BidRequirementLine> demandLines = Optional.ofNullable(sourceForm.getDemandLines())
                .orElse(Collections.emptyList())
                .stream()
                .peek(demandLine -> demandLine.setRequirementLineId(null))
                .collect(Collectors.toList());

        // 获取 寻源供应商
        List<BidVendor> bidVendors = Optional.ofNullable(sourceForm.getBidVendors())
                .orElse(Collections.emptyList())
                .stream()
                .peek(bidVendor -> bidVendor.setBidVendorId(null))
                .collect(Collectors.toList());


        // 存储 寻源单
        biddingDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setBidingId(IdGenrator.generate())
                        .setBidingNum(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_PROJECT_CODE))
                        .setAuditStatus(BiddingApprovalStatus.DRAFT.getValue())
                        .setBidingStatus(BiddingProjectStatus.DRAW_UP.getValue())
                        .setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue())
                        .setBidContactName(currentUser.getNickname())
                        .setBidMobilePhone(currentUser.getPhone())
                        .setBidEmail(currentUser.getEmail())
                )
                .save(bidding);
        //生成节点
        if(Objects.nonNull(bidding.getProcessConfigId())){
            Long processConfigId = bidding.getProcessConfigId();
            processNodeService.remove(Wrappers.lambdaQuery(ProcessNode.class)
                    .eq(ProcessNode::getBidingId,bidding.getBidingId())
            );
            processNodeService.saveNodes(bidding.getBidingId(),processConfigId);
        }
        Collection<Group> bidGroups = sourceForm.getBidGroups();
        if(CollectionUtils.isNotEmpty(bidGroups)){
            List<Group> list = bidGroups.stream().map(e -> e.setGroupId(null)).collect(Collectors.toList());
            groupService.saveBatchGroup(list,bidding.getBidingId());
        }
        if(CollectionUtils.isNotEmpty(sourceForm.getBidFileConfigs())){
            sourceForm.getBidFileConfigs().stream().forEach(e -> {
                e.setBidingId(bidding.getBidingId());
                e.setRequireId(null);
                bidFileConfigMapper.insert(e);
            });
        }
        if(CollectionUtils.isNotEmpty(sourceForm.getBidFiles())){
            sourceForm.getBidFiles().stream().forEach(e->{
                e.setBidingId(bidding.getBidingId());
                e.setFileId(null);
                bidFileMapper.insert(e);
            });
        }

        // 存储 寻源需求[头]
        demandHeaderDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setRequirementId(IdGenrator.generate())
                        .setBidingId(bidding.getBidingId())
                )
                .save(demandHeader);

        // 存储 寻源需求[行]
        demandLineDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setRequirementLineId(IdGenrator.generate())
                        .setRequirementId(demandHeader.getRequirementId())
                        .setBidingId(bidding.getBidingId())
                )
                .save(demandLines);

        // 存储 寻源供应商
        bidVendorDao.useInterceptor()
                .beforeCreate(parameter -> parameter.getPrepareCreateEntity()
                        .setBidVendorId(IdGenrator.generate())
                        .setBidingId(bidding.getBidingId())
                        .setBidingScope(bidding.getBidingScope())
                )
                .save(bidVendors);
        //生成报价币种
        List<BidBidingCurrency> currencies = bidding.getCurrencies();
        if(CollectionUtils.isNotEmpty(currencies)){
            for (BidBidingCurrency currency : currencies) {
                currency.setCurrencyId(IdGenrator.generate());
                currency.setBidingId(bidding.getBidingId());
                currencyMapper.insert(currency);
            }
        }

        // 存储结果返回
        return GenerateSourceFormResult.builder()
                .sourceForm(SourceForm.builder()
                        .bidding(bidding)
                        .demandHeader(demandHeader)
                        .demandLines(demandLines)
                        .bidVendors(bidVendors)
                        .build()
                )
                .build();
    }
}
