package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.SourcingTemplateMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidIntelligentRecommendVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.ISourceFormService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.ISourcingTemplateService;
import com.midea.cloud.srm.feign.bargaining.BargainClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.*;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.SourcingTemplate;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.enums.SourcingTemplateStatus;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.SourcingTemplateQueryParameter;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.VendorPricingPermission;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.midea.cloud.common.constants.SequenceCodeConstant.SEQ_SOURCING_TEMPLATE;


/**
 * Implement of {@link ISourcingTemplateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class SourcingTemplateServiceImpl implements ISourcingTemplateService {

    private final EntityManager<SourcingTemplate> sourcingTemplateDao
            = EntityManager.use(SourcingTemplateMapper.class);

    @Resource
    private BaseClient baseClient;
    @Resource
    private IBidIntelligentRecommendVendorService bidIntelligentRecommendVendorService;
    @Resource
    private BargainClient bargainClient;
    @Resource
    private ISourceFormService sourceFormService;


    @Override
    public PageInfo<SourcingTemplate> findSourcingTemplatesWithoutTemplateData(SourcingTemplateQueryParameter queryParameter,
                                                                               int pageNo, int pageSize) {
        return sourcingTemplateDao.findAll(
                Wrappers.lambdaQuery(SourcingTemplate.class)
                        .select(info -> !info.getColumn().equals("TEMPLATE_DATA"))    // 不查询出模板内具有的[模板数据]字段
                        .like(StringUtils.hasText(queryParameter.getSn()), SourcingTemplate::getSn, queryParameter.getSn())
                        .like(StringUtils.hasText(queryParameter.getName()), SourcingTemplate::getName, queryParameter.getName())
                        .eq(StringUtils.hasText(queryParameter.getStatus()), SourcingTemplate::getStatus, queryParameter.getStatus())
                        .eq(StringUtils.hasText(queryParameter.getSourcingType()), SourcingTemplate::getSourcingType, queryParameter.getSourcingType()),
                pageNo, pageSize
        );
    }

    @Override
    public SourcingTemplate findSourcingTemplate(Long id) {
        return Optional.ofNullable(sourcingTemplateDao.findById(id))
                .orElseThrow(() -> new BaseException("获取寻源模板失败。 id: [" + id + "]"));
    }

    @Override
    public SourceForm findSourcingTemplateData(Long id) {
        return Optional.ofNullable(this.findSourcingTemplate(id).getTemplateData())
                .map(templateData -> JSON.parseObject(templateData, new TypeReference<SourceForm>(){}))
                .orElseGet(SourceForm::new);
    }

    @Override
    public SourcingTemplate draftSourcingTemplate(SourcingTemplate sourcingTemplate) {
        sourcingTemplate.setStatus(SourcingTemplateStatus.VALID.getItemValue());
        SourceForm sourceForm = JSON.parseObject(sourcingTemplate.getTemplateData(), new TypeReference<SourceForm>(){});
        for (BidRequirementLine demandLine : sourceForm.getDemandLines()) {
            if (Objects.isNull(demandLine.getRequirementLineId())) {
                demandLine.setRequirementLineId(IdGenrator.generate());
            }
        }
        if (Objects.isNull(sourceForm.getBidding().getBidingId())) {
            sourceForm.getBidding().setBidingId(IdGenrator.generate());
        }
        sourcingTemplate.setTemplateData(JSON.toJSONString(sourceForm));
        sourcingTemplateDao.useInterceptor()
                .beforeCreate(parameter -> {
                    SourcingTemplate prepareCreateEntity = parameter.getPrepareCreateEntity();
                    prepareCreateEntity.setId(IdGenrator.generate());
                    prepareCreateEntity.setSn(baseClient.seqGen(SEQ_SOURCING_TEMPLATE));
                })
                .save(sourcingTemplate);
        return sourcingTemplate;
    }

    @Override
    public void validSourcingTemplates(Long[] ids) {
        this.changeSourcingTemplatesStatus(ids, SourcingTemplateStatus.VALID);
    }

    @Override
    public void inValidSourcingTemplates(Long[] ids) {
        this.changeSourcingTemplatesStatus(ids, SourcingTemplateStatus.INVALID);
    }

    @Override
    public void deleteSourcingTemplates(Long[] ids) {
        sourcingTemplateDao.delete(ids);
    }

    @Override
    public Object generateSourceForm(Long id) {

        // 获取 寻源模板
        SourcingTemplate sourcingTemplate = this.findSourcingTemplate(id);

        if (StringUtils.isEmpty(sourcingTemplate.getTemplateData()))
            throw new BaseException("寻源模板数据为空。 | id: [" + id + "]");

        // 调用生成接口
        switch (SourcingType.get(sourcingTemplate.getSourcingType())) {
            case TENDER:
                return sourceFormService
                        .generateForm(com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter.builder()
                                .sourceForm(JSON.parseObject(
                                        sourcingTemplate.getTemplateData(),
                                        new TypeReference<SourceForm>(){}
                                ))
                                .build());
            case RFQ:
                return SpringContextHolder.getBean(com.midea.cloud.srm.feign.bargaining.SourceFormClient.class)
                        .generateForm(com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter.builder()
                                .sourceForm(JSON.parseObject(
                                        sourcingTemplate.getTemplateData(),
                                        new TypeReference<com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm>(){}
                                ))
                                .build());
            default:
                throw new BaseException("生成寻源单失败。 id: [" + id + "], sourcingType: [" + sourcingTemplate.getSourcingType() + "]");
        }
    }

    /**
     * 更改 寻源模板集状态
     *
     * @param ids    寻源模板ID集
     * @param status 状态
     */
    protected void changeSourcingTemplatesStatus(Long[] ids, SourcingTemplateStatus status) {
        if (ids == null || ids.length == 0)
            return;

        // 获取 寻源模板
        List<SourcingTemplate> sourcingTemplates = sourcingTemplateDao.findAll(
                Wrappers.lambdaQuery(SourcingTemplate.class)
                        .in(SourcingTemplate::getId, Arrays.stream(ids).toArray())
        );

        // 设置状态
        sourcingTemplates.forEach(sourcingTemplate -> sourcingTemplate.setStatus(status.getItemValue()));

        // 存储
        sourcingTemplateDao.save(sourcingTemplates);
    }

    /**
     * 根据templateId获取需求行，再获取供应商集合
     *
     * @param templateId
     * @return
     */
    @Override
    public List<CompanyInfo> findVendors(Long templateId) {
        SourcingTemplate sourcingTemplate = this.findSourcingTemplate(templateId);
        SourceForm sourceForm = Optional.ofNullable(sourcingTemplate.getTemplateData())
                .map(templateData -> JSON.parseObject(templateData, new TypeReference<SourceForm>(){}))
                .orElseGet(SourceForm::new);
        switch (SourcingType.get(sourcingTemplate.getSourcingType())) {
            case TENDER:
                return bidIntelligentRecommendVendorService.findVendors(sourceForm);
            case RFQ:
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm
                        temp = transfer(sourceForm);
                return bargainClient.findVendorsByTemplate(temp);
        }
        return Collections.emptyList();
    }

    @Override
    public List findQuoteAuthorizes(Long vendorId, Long templateId) {
        SourcingTemplate sourcingTemplate = findSourcingTemplate(templateId);
        SourceForm sourceForm = Optional.ofNullable(sourcingTemplate.getTemplateData())
                .map(templateData -> JSON.parseObject(templateData, new TypeReference<SourceForm>(){}))
                .orElseGet(SourceForm::new);
        switch (SourcingType.get(sourcingTemplate.getSourcingType())) {
            //招标
            case TENDER:
                List<VendorPricingPermission> vendorPricingPermissions = bidIntelligentRecommendVendorService.getVendorPricingPermissions(sourceForm.getDemandLines(), new Long[]{vendorId}, sourceForm.getBidding().getBidingAwardWay());
                List<BidRequirementLine> collect = vendorPricingPermissions.stream().flatMap(e -> e.getRequirementLines().stream()).collect(Collectors.toList());
                return collect;
            //询价
            case RFQ:
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm transfer = transfer(sourceForm);
                Map<String,Object> paramMap=new HashMap<>();
                paramMap.put("sourceForm",transfer);
                paramMap.put("vendorId",vendorId);
                return bargainClient.getVendorPricingPermissionsByTemplateId(paramMap).stream().flatMap(e -> e.getRequirementLines().stream()).collect(Collectors.toList());
        }
        return null;
    }

    private com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm transfer(SourceForm sourceForm) {
        com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm form = new com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm();
        form.setBidding(BeanCopyUtil.copyProperties(sourceForm.getBidding(), Biding::new));
        List<com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine> demandLines = sourceForm.getDemandLines().stream()
                .map(e -> BeanCopyUtil.copyProperties(e, com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine::new))
                .collect(Collectors.toList());
        form.setDemandLines(demandLines);
        form.setDemandHeader(BeanCopyUtil.copyProperties(sourceForm.getDemandHeader(), BidRequirement::new));
        List<BidFileConfig> fileConfigs = sourceForm.getBidFileConfigs().stream().map(e -> BeanCopyUtil.copyProperties(e, BidFileConfig::new)).collect(Collectors.toList());
        form.setBidFileConfigs(fileConfigs);
        List<Group> groups = sourceForm.getBidGroups().stream().map(e -> BeanCopyUtil.copyProperties(e, Group::new)).collect(Collectors.toList());
        form.setBidGroups(groups);
        form.setScoreRule(BeanCopyUtil.copyProperties(sourceForm.getScoreRule(), ScoreRule::new));
        List<ScoreRuleLine> scoreRuleLines = sourceForm.getScoreRuleLines().stream().map(e -> BeanCopyUtil.copyProperties(e, ScoreRuleLine::new)).collect(Collectors.toList());
        form.setScoreRuleLines(scoreRuleLines);
        List<BidVendor> bidVendors = sourceForm.getBidVendors().stream().map(e -> BeanCopyUtil.copyProperties(e, BidVendor::new)).collect(Collectors.toList());
        form.setBidVendors(bidVendors);
        form.setBidFiles(sourceForm.getBidFiles().stream().map(e -> BeanCopyUtil.copyProperties(e, BidFile::new)).collect(Collectors.toList()));
        return form;
    }

}
