package com.midea.cloud.srm.perf.scoring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsProjectStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.perf.scoring.PerfIndicatorDimScore;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDimWeightDTO;
import com.midea.cloud.srm.model.perf.template.entity.*;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.perf.common.IndicatorsConst;
import com.midea.cloud.srm.perf.common.PerfLevelConst;
import com.midea.cloud.srm.perf.common.ScoreItemsConst;
import com.midea.cloud.srm.perf.common.ScoreManScoringConst;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsManService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsSupService;
import com.midea.cloud.srm.perf.scoring.mapper.PerfScoreManScoringMapper;
import com.midea.cloud.srm.perf.scoring.service.IPerfScoreManScoringService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateCategoryService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateDimWeightService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateHeaderService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  评分人绩效评分表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 14:29:16
 *  修改内容:
 * </pre>
 */
@Service
public class PerfScoreManScoringServiceImpl extends ServiceImpl<PerfScoreManScoringMapper, PerfScoreManScoring>
        implements IPerfScoreManScoringService {

    /**
     * 绩效评分项目Service
     */
    @Resource
    private IPerfScoreItemsService iPerfScoreItemsService;

    /**
     * 绩效模板Service
     */
    @Resource
    private IPerfTemplateHeaderService iPerfTemplateHeaderService;

    /**
     * 绩效评分项目供应商
     */
    @Resource
    private IPerfScoreItemsSupService iPerfScoreItemsSupService;

    /**
     * 绩效评分项目评分人Service
     */
    @Resource
    private IPerfScoreItemsManService iPerfScoreItemsManService;

    /**
     * 绩效模型绩指标表ervice
     */
    @Resource
    private IPerfTemplateLineService iPerfTemplateLineService;

    @Autowired
    private IPerfTemplateCategoryService iPerfTemplateCategoryService;

    @Autowired
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;

    @Autowired
    private IPerfTemplateDimWeightService iPerfTemplateDimWeightService;

    @Autowired
    private FileCenterClient fileCenterClient;


    /**
     * 把数据插入评分人绩效评分表（每一项任务可能会分配到多个评分人）
     *
     * @param scoreItemsId
     * @return
     * @throws BaseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveScoreManScoringByScoreItemsId(Long scoreItemsId) throws BaseException {
        String result = ResultCode.OPERATION_FAILED.getMessage();
        // 根据Id获取绩效评分项目信息
        PerfScoreItems scoreItems = iPerfScoreItemsService.getById(scoreItemsId);
        Assert.notNull(scoreItems, ScoreItemsConst.PER_SCORE_ITEMS_NOT_NULL);

        // 根据绩效评分项目ID获取绩效评分供应商集合
        List<PerfScoreItemsSup> scoreItemsSupList = iPerfScoreItemsSupService.list(Wrappers.lambdaQuery(PerfScoreItemsSup.class)
                .eq(PerfScoreItemsSup::getScoreItemsId, scoreItemsId));
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsSupList), ScoreItemsConst.PER_SCORE_ITEMS_SUP_NOT_NULL);

        // 要保存的绩效评分数据集合
        List<PerfScoreManScoring> scoreManScoringList = new ArrayList<>();
        // 绩效模型头ID
        Long templateHeadId = scoreItems.getTemplateHeadId();
        // 绩效模型名称
        String templateName = scoreItems.getTemplateName();
        String projectName = scoreItems.getProjectName();
        String evaluationPeriod = scoreItems.getEvaluationPeriod();
        LocalDate perStartMonth = scoreItems.getPerStartMonth();
        LocalDate perEndMonth = scoreItems.getPerEndMonth();
        Long organizationId = scoreItems.getOrganizationId();
        String organizationName = scoreItems.getOrganizationName();

        // 根据绩效模型头ID获取详细信息
        PerfTemplateDTO templateDTO = iPerfTemplateHeaderService.findPerTemplateByTemplateHeadId(templateHeadId);
        Assert.isTrue(Objects.nonNull(templateDTO), ScoreItemsConst.PER_TEMPLATE_NOT_NULL);
        PerfTemplateCategory category = new PerfTemplateCategory();
        if (CollectionUtils.isNotEmpty(scoreItemsSupList)) {
            // 绩效模型-采购分类集合
            List<PerfTemplateCategory> categoryList = templateDTO.getPerfTemplateCategoryList();
            Assert.isTrue(CollectionUtils.isNotEmpty(categoryList), ScoreItemsConst.PER_TEMPLATE_CATEGORY_NOT_NULL);
            category = categoryList.get(0);
            // 绩效模型指标维度集合
            List<PerfTemplateDimWeightDTO> dimWeightDTOList = templateDTO.getPerfTemplateDimWeightList();
            Assert.isTrue(CollectionUtils.isNotEmpty(dimWeightDTOList), ScoreItemsConst.PER_TEMPLATE_DIM_WEIGHT_NOT_NULL);
        }
        Long categoryId = category.getCategoryId();
        String categoryCode = category.getCategoryCode();
        String categoryName = category.getCategoryName();
        String categoryFullName = category.getCategoryFullName();

        // 评分人及评分人对应分配的任务
        List<PerfScoreItemManSupInd> perfScoreItemManSupInds = iPerfScoreItemManSupIndService.list(Wrappers.lambdaQuery(PerfScoreItemManSupInd.class)
                .eq(PerfScoreItemManSupInd::getScoreItemsId, scoreItemsId));

        if (CollectionUtils.isNotEmpty(perfScoreItemManSupInds)) {
            for (PerfScoreItemManSupInd manSupInd : perfScoreItemManSupInds) {
                PerfScoreManScoring manScoring = new PerfScoreManScoring();
                manScoring.setScoreItemsId(scoreItemsId)
                        .setStatus(ScoreItemsProjectStatusEnum.SCORE_DRAFT.getValue())
                        .setTemplateHeadId(templateHeadId)
                        .setTemplateName(templateName)
                        .setProjectName(projectName)
                        .setEvaluationPeriod(evaluationPeriod)
                        .setPerStartMonth(perStartMonth)
                        .setPerEndMonth(perEndMonth)
                        .setOrganizationId(organizationId)
                        .setOrganizationName(organizationName)
                        .setCategoryId(categoryId)
                        .setCategoryCode(categoryCode)
                        .setCategoryName(categoryName)
                        .setCategoryFullName(categoryFullName);
                Long scoreManScoringId = IdGenrator.generate();
                manScoring.setScoreManScoringId(scoreManScoringId);
                manScoring.setCompanyId(manSupInd.getCompanyId())
                        .setCompanyCode(manSupInd.getCompanyCode())
                        .setCompanyName(manSupInd.getCompanyName());
                // 获取指标维度行
                Long templateDimWeightId = manSupInd.getTemplateDimWeightId();
                PerfTemplateDimWeight perfTemplateDimWeight = iPerfTemplateDimWeightService.getById(templateDimWeightId);
                manScoring.setDimWeightId(manSupInd.getTemplateDimWeightId())
                        .setIndicatorType(perfTemplateDimWeight.getIndicatorType())
                        .setIndicatorDimensionType(perfTemplateDimWeight.getIndicatorDimensionType())
                        .setIndicatorDimensionWeight(perfTemplateDimWeight.getIndicatorDimensionWeight());
                // 获取指标行
                Long templateLineId = manSupInd.getTemplateLineId();
                PerfTemplateLine perfTemplateLine = iPerfTemplateLineService.getById(templateLineId);
                manScoring.setEvaluation(perfTemplateLine.getEvaluation())
                        .setTemplateLineId(templateLineId)
                        .setIndicatorName(perfTemplateLine.getIndicatorName())
                        .setIndicatorLineType(perfTemplateLine.getIndicatorLineType())
                        .setQuoteMode(perfTemplateLine.getQuoteMode())
                        .setDimensionWeight(new BigDecimal(perfTemplateLine.getDimensionWeight()))
                        .setIfScored(YesOrNo.NO.getValue());
                Long scoreItemsManId = manSupInd.getScoreItemsManId();
                PerfScoreItemsMan scoreItemsMan = iPerfScoreItemsManService.getById(scoreItemsManId);
                Assert.notNull(scoreItemsMan, "评分人不存在。");
                manScoring.setScoreUserName(scoreItemsMan.getScoreUserName())
                        .setScoreNickName(scoreItemsMan.getScoreNickName());
                // 检查评分人绩效评分表保存字段不为空
                this.checkEditScoreManScoring(manScoring);
                scoreManScoringList.add(manScoring);
            }
        }

        if (CollectionUtils.isNotEmpty(scoreManScoringList)) {
            try {
                boolean isSave = super.saveBatch(scoreManScoringList);
                if (isSave) {
                    result = ResultCode.SUCCESS.getMessage();
                }
            } catch (Exception e) {
                log.error("保存多条评分人评分表时报错：", e);
                throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
            }
        }

        PerfScoreItemsMan queryScoreItemsMan = new PerfScoreItemsMan();
        queryScoreItemsMan.setScoreItemsId(scoreItemsId);
        List<PerfScoreItemsMan> scoreItemsManList = iPerfScoreItemsManService.list(new QueryWrapper<>(queryScoreItemsMan));
        int scorePeopleCount = scoreItemsManList.size(); //初始化绩效评分人总数
        if (0 < scorePeopleCount) {
            try {
                PerfScoreItems updateScoreItems = new PerfScoreItems();
                updateScoreItems.setScoreItemsId(scoreItemsId);
                updateScoreItems.setScorePeopleCount(Long.parseLong(String.valueOf(scorePeopleCount)));
                iPerfScoreItemsService.updateById(updateScoreItems);
            } catch (Exception e) {
                log.error("获取指标维度绩效得分保存操作-修改绩效评分项目评分人总数时报错：", e);
                throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<PerfScoreManScoring> listScoreManScoringPage(PerfScoreManScoring scoreManScoring) {
        //只有供应商类型的用户才能评分
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        String userName = "";
        if (null != user) {
            Assert.isTrue("BUYER".equals(user.getUserType()), ScoreManScoringConst.IS_BUYER_SCORE);
            userName = user.getUsername();
        }
        Assert.notNull(userName, ScoreManScoringConst.USER_NAME_NOT_NULL);

        List<PerfScoreManScoring> scoreManScoringList = new ArrayList<>();
        try {
            QueryWrapper<PerfScoreManScoring> wrapper = new QueryWrapper<PerfScoreManScoring>();
            if (null != scoreManScoring) {
                String projectName = scoreManScoring.getProjectName();
                String companyName = scoreManScoring.getCompanyName();
                if (StringUtils.isNotEmpty(projectName)) {
                    wrapper.like("PROJECT_NAME", projectName);
                }
                if (StringUtils.isNotEmpty(companyName)) {
                    wrapper.like("COMPANY_NAME", companyName);
                }
                if (StringUtils.isNotEmpty(scoreManScoring.getIndicatorName())) {
                    wrapper.like("INDICATOR_NAME", scoreManScoring.getIndicatorName());
                }
                Long organizationId = scoreManScoring.getOrganizationId();
                String organizationName = scoreManScoring.getOrganizationName();
                String evaluationPeriod = scoreManScoring.getEvaluationPeriod();
                String indicatorDimensionType = scoreManScoring.getIndicatorDimensionType();
                Long categoryId = scoreManScoring.getCategoryId();
                String categoryName = scoreManScoring.getCategoryName();
                if (null != organizationId) {
                    wrapper.eq("ORGANIZATION_ID", organizationId);
                }
                if (StringUtils.isNotEmpty(organizationName)) {
                    wrapper.eq("ORGANIZATION_NAME", organizationName);
                }
                if (StringUtils.isNotEmpty(evaluationPeriod)) {
                    wrapper.eq("EVALUATION_PERIOD", evaluationPeriod);
                }
                if (StringUtils.isNotEmpty(indicatorDimensionType)) {
                    wrapper.eq("INDICATOR_DIMENSION_TYPE", indicatorDimensionType);
                }
                if (null != categoryId) {
                    wrapper.eq("CATEGORY_ID", categoryId);
                }
                if (StringUtils.isNotEmpty(categoryName)) {
                    wrapper.eq("CATEGORY_NAME", categoryName);
                }
            }

            /**控制评分人查看权限(当前用户只能查询到自己要评分的信息)*/
            wrapper.eq("SCORE_USER_NAME", userName);
            wrapper.orderByDesc("CREATION_DATE");
            scoreManScoringList = getBaseMapper().selectList(wrapper);

            /**根据指标维度表ID获取对应的绩效模型指标行相关评分信息*/
            List<PerfTemplateLine> templateLineList = iPerfTemplateLineService.findTempateLineByParam(new PerfTemplateLine());

            if (CollectionUtils.isNotEmpty(scoreManScoringList) && CollectionUtils.isNotEmpty(templateLineList)) {
                for (PerfScoreManScoring manScoring : scoreManScoringList) {
                    if (null != manScoring && null != manScoring.getTemplateLineId()) {
                        Long temlateLineId = manScoring.getTemplateLineId();
                        for (PerfTemplateLine templateLine : templateLineList) {
                            if (null != templateLine && (temlateLineId.longValue() == templateLine.getTemplateLineId().longValue())) { //判断指标ID相等
                                List<PerfTemplateIndsLine> templateIndsLineList = (List<PerfTemplateIndsLine>) ObjectUtil.deepCopy(templateLine.getPerfTemplateIndsLineList());
                                manScoring.setTemplateIndsLineList(templateIndsLineList);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("分页查询评分人评分信息时报错：", e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return scoreManScoringList;
    }

    @Override
    public BigDecimal getScoreByTemplateLineId(Long templateLineId) throws BaseException {
        Assert.notNull(templateLineId, "");
        return null;
    }

    /**
     * 保存评分人绩效评分集合
     *
     * @param scoreManScoringsList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveScoreManScoring(List<PerfScoreManScoring> scoreManScoringsList) {
        String result = ResultCode.OPERATION_FAILED.getMessage();
        if (CollectionUtils.isNotEmpty(scoreManScoringsList)) {
            scoreManScoringsList.forEach(scoreManScoring -> {
                scoreManScoring.setIfScored(YesOrNo.YES.getValue());
            });
            try {
                boolean isUpdate = super.updateBatchById(scoreManScoringsList);
                if (isUpdate) {
                    result = ResultCode.SUCCESS.getMessage();
                }
            } catch (Exception e) {
                log.error("保存评分人评分集合信息时报错：", e);
                throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
            }
        }
        return result;
    }

    /**
     * 获取指标维度绩效得分集合信息
     *
     * @param scoreManScoring
     * @return
     */
    @Override
    public List<PerfIndicatorDimScore> getSaveIndicatorDimScoreList(PerfScoreManScoring scoreManScoring) {
        return getBaseMapper().getSaveIndicatorDimScoreList(scoreManScoring);
    }

    @Override
    public List<PerfOverallScore> getSaveOverallScoreList(PerfScoreManScoring scoreManScoring) {
        return getBaseMapper().getSaveOverallScoreList(scoreManScoring);
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public String updateScoreManScoringStatus(Long scoreItemsId, String status) throws BaseException {
        Assert.notNull(scoreItemsId, "id不能为空");
        Assert.notNull(status, ScoreManScoringConst.STAUS_NOT_NULL);
        String result = ResultCode.OPERATION_FAILED.getMessage();
        PerfScoreManScoring scoreManScoring = new PerfScoreManScoring();
        scoreManScoring.setScoreItemsId(scoreItemsId);
        List<PerfScoreManScoring> scoreManScoringList = getBaseMapper().selectList(new QueryWrapper<>(scoreManScoring));
        List<PerfScoreManScoring> updateManScoringList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(scoreManScoringList)) {
            for (PerfScoreManScoring manScoring : scoreManScoringList) {
                if (null != manScoring) {
                    PerfScoreManScoring updateManScoring = new PerfScoreManScoring();
                    updateManScoring.setScoreManScoringId(manScoring.getScoreManScoringId());
                    updateManScoring.setStatus(status);
                    updateManScoringList.add(updateManScoring);
                }
            }
            if (CollectionUtils.isNotEmpty(updateManScoringList)) {
                try {
                    boolean isUpdate = super.saveOrUpdateBatch(updateManScoringList);
                    if (isUpdate) {
                        result = ResultCode.SUCCESS.getMessage();
                    }
                } catch (Exception e) {
                    log.error("根据绩效评分项目ID修改评分人绩效评分的状态时报错：", e);
                    throw new BaseException(ScoreManScoringConst.UPDATE_STATUS_ERROR);
                }
            }
        }

        return result;
    }

    /**
     * 绩效评分项目 任务分配
     *
     * @param perfScoreManScorings
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allocate(List<PerfScoreManScoring> perfScoreManScorings, PerfScoreItemsMan perfScoreItemsMan, Long scoreItemsId, Long templateHeadId) {
        // 首先删除单据关联的数据
        QueryWrapper<PerfScoreManScoring> removeWrapper = new QueryWrapper<>();
        removeWrapper.eq("SCORE_ITEMS_ID", scoreItemsId);
        if (this.count(removeWrapper) > 0) {
            this.remove(removeWrapper);
        }
        // 查询绩效数据
        PerfTemplateHeader templateHeader = iPerfTemplateHeaderService.getById(templateHeadId);
        Assert.notNull(templateHeader, "找不到对应的绩效模型。");

        // 根据绩效模板头获取品类行（controller已做判空处理）
        List<PerfTemplateCategory> perfTemplateCategories = iPerfTemplateCategoryService.list(Wrappers.lambdaQuery(PerfTemplateCategory.class)
                .eq(PerfTemplateCategory::getTemplateHeadId, templateHeadId));

        // 查询绩效项目数据
        PerfScoreItems perfScoreItems = iPerfScoreItemsService.getById(scoreItemsId);

        perfScoreManScorings.forEach(scoreManScoring -> {
            if (CollectionUtils.isNotEmpty(perfTemplateCategories)) {
                perfTemplateCategories.forEach(perfTemplateCategory -> {
                    Long id = IdGenrator.generate();
                    scoreManScoring.setScoreManScoringId(id)
                            .setCategoryId(perfTemplateCategory.getCategoryId())
                            .setCategoryCode(perfTemplateCategory.getCategoryCode())
                            .setCategoryName(perfTemplateCategory.getCategoryName())
                            .setCategoryFullName(perfTemplateCategory.getCategoryFullName())
                            .setTemplateHeadId(templateHeadId)
                            .setTemplateName(templateHeader.getTemplateName())
                            .setProjectName(perfScoreItems.getProjectName())
                            .setOrganizationId(perfScoreItems.getOrganizationId())
                            .setOrganizationName(perfScoreItems.getOrganizationName())
                            .setFullPathId(perfScoreItems.getFullPathId())
                            .setEvaluationPeriod(perfScoreItems.getEvaluationPeriod())
                            .setPerStartMonth(perfScoreItems.getPerStartMonth())
                            .setPerEndMonth(perfScoreItems.getPerEndMonth())
                            .setScoreUserName(perfScoreItemsMan.getScoreUserName())
                            .setScoreNickName(perfScoreItemsMan.getScoreNickName());
                });
            }

        });

        if (CollectionUtils.isNotEmpty(perfScoreManScorings)) {
            this.saveBatch(perfScoreManScorings);
        }
    }

    /**
     * 附件上传
     *
     * @param perfScoreManScoring
     */
    @Override
    public void uploadFile(PerfScoreManScoring perfScoreManScoring) {
        Assert.notNull(perfScoreManScoring.getScoreManScoringFileId(), "上传的附件不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(perfScoreManScoring.getScoreManScoringFileName()), "上传的附件名称不能为空。");
        PerfScoreManScoring byId = this.getById(perfScoreManScoring.getScoreManScoringId());
        byId.setScoreManScoringFileId(perfScoreManScoring.getScoreManScoringFileId())
                .setScoreManScoringFileName(perfScoreManScoring.getScoreManScoringFileName());
        this.updateById(byId);
    }

    /**
     * 附件删除
     *
     * @param perfScoreManScoring
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(PerfScoreManScoring perfScoreManScoring) {
        PerfScoreManScoring byId = this.getById(perfScoreManScoring.getScoreManScoringId());
        byId.setScoreManScoringFileId(null)
                .setScoreManScoringFileName(null);
        this.updateById(byId);
        fileCenterClient.delete(perfScoreManScoring.getScoreManScoringFileId());
    }

    /**
     * Description 检查评分人绩效评分表保存字段不为空
     *
     * @return
     * @throws Assert
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.11
     **/
    public void checkEditScoreManScoring(PerfScoreManScoring scoreManScoring) {
        Assert.notNull(scoreManScoring, ScoreManScoringConst.SCORE_MAN_SCORING_NOT_NULL);
        Assert.notNull(scoreManScoring.getScoreManScoringId(), "评分人评分主键id不能为空");
        Assert.notNull(scoreManScoring.getStatus(), ScoreManScoringConst.STAUS_NOT_NULL);
        Assert.notNull(scoreManScoring.getScoreItemsId(), ScoreManScoringConst.SCORE_ITEMS_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getTemplateHeadId(), ScoreManScoringConst.TEMPLATE_HEAD_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getTemplateName(), ScoreManScoringConst.TEMPLATE_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getProjectName(), ScoreManScoringConst.PROJECT_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getEvaluationPeriod(), ScoreManScoringConst.EVALUATION_PERIOD_NOT_NULL);
        Assert.notNull(scoreManScoring.getPerStartMonth(), ScoreManScoringConst.PER_START_MONTH_NOT_NULL);
        Assert.notNull(scoreManScoring.getPerEndMonth(), ScoreManScoringConst.PER_END_MONTH_NOT_NULL);
        Assert.notNull(scoreManScoring.getOrganizationId(), PerfLevelConst.ORGANIZATION_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getOrganizationName(), PerfLevelConst.ORGANIZATION_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getCategoryId(), ScoreManScoringConst.CATEGORY_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getCategoryName(), ScoreManScoringConst.CATEGORY_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getCompanyId(), ScoreManScoringConst.COMPANY_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getCompanyCode(), ScoreManScoringConst.COMPANY_CODE_NOT_NULL);
        Assert.notNull(scoreManScoring.getCompanyName(), ScoreManScoringConst.COMPANY_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getDimWeightId(), ScoreManScoringConst.DIM_WEIGHT_ID_NOT_NULL);
        Assert.notNull(scoreManScoring.getIndicatorType(), IndicatorsConst.INDICATOR_TYPE_NOT_NULL);
        Assert.notNull(scoreManScoring.getIndicatorDimensionType(), IndicatorsConst.INDICATOR_DIMENSION_NOT_NULL);
        Assert.notNull(scoreManScoring.getIndicatorDimensionWeight(), ScoreManScoringConst.INDICATOR_DIMENSION_WEIGHT_NOT_NULL);
        Assert.notNull(scoreManScoring.getIndicatorName(), IndicatorsConst.INDICATOR_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getIndicatorLineType(), ScoreManScoringConst.INDICATOR_LINE_TYPE_NOT_NULL);
        Assert.notNull(scoreManScoring.getQuoteMode(), IndicatorsConst.QUOTE_MODE_NOT_NULL);
        Assert.notNull(scoreManScoring.getDimensionWeight(), ScoreManScoringConst.DIMENSION_WEIGHT_NOT_NULL);
        Assert.notNull(scoreManScoring.getScoreUserName(), ScoreManScoringConst.SCORE_USER_NAME_NOT_NULL);
        Assert.notNull(scoreManScoring.getScoreNickName(), ScoreManScoringConst.SCORE_NICK_NAME_NOT_NULL);
    }
}
