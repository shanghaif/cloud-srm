package com.midea.cloud.srm.perf.scoring.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsProjectStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.perf.scoring.dto.ScoreManScoringV1Import;
import com.midea.cloud.srm.model.perf.scoring.dto.ScoreManScoringV1SubmitConfirmDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDimWeightDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleFuncSet;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.perf.common.IndicatorsConst;
import com.midea.cloud.srm.perf.common.PerfLevelConst;
import com.midea.cloud.srm.perf.common.ScoreItemsConst;
import com.midea.cloud.srm.perf.common.ScoreManScoringConst;
import com.midea.cloud.srm.perf.indicators.mapper.IndicatorsLineMapper;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsManService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsSupService;
import com.midea.cloud.srm.perf.scoring.constants.QuoteMode;
import com.midea.cloud.srm.perf.scoring.mapper.ScoreManScoringV1Mapper;
import com.midea.cloud.srm.perf.scoring.service.IScoreManScoringV1Service;
import com.midea.cloud.srm.perf.scoring.utils.ScoreManScoringV1ExportUtils;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateCategoryService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateDimWeightService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateHeaderService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <pre>
 *  评分人绩效评分表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-21 17:26:15
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class ScoreManScoringV1ServiceImpl extends ServiceImpl<ScoreManScoringV1Mapper, ScoreManScoringV1> implements IScoreManScoringV1Service {

    @Autowired
    private IPerfScoreItemsService iPerfScoreItemsService;

    @Autowired
    private IPerfTemplateHeaderService iPerfTemplateHeaderService;

    @Autowired
    private IPerfScoreItemsSupService iPerfScoreItemsSupService;

    @Autowired
    private IPerfScoreItemsManService iPerfScoreItemsManService;

    @Autowired
    private IPerfTemplateLineService iPerfTemplateLineService;

    @Autowired
    private IPerfTemplateCategoryService iPerfTemplateCategoryService;

    @Autowired
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;

    @Autowired
    private IPerfTemplateDimWeightService iPerfTemplateDimWeightService;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SupplierClient supplierClient;

    @Resource
    private RbacClient rbacClient;

    @Resource
    private IndicatorsLineMapper indicatorsLineMapper;


    public static final Map<String, String> INDICATOR_DIMENSION_MAP;

    public static final Map<String, String> INDICATOR_DIMENSION_MAP_1;

    static {
        INDICATOR_DIMENSION_MAP = new HashMap<>();
        INDICATOR_DIMENSION_MAP.put("QUALITY", "品质");
        INDICATOR_DIMENSION_MAP.put("SERVICE", "服务");
        INDICATOR_DIMENSION_MAP.put("DELIVER", "交付");
        INDICATOR_DIMENSION_MAP.put("TECHNOLOGY", "技术");
        INDICATOR_DIMENSION_MAP.put("COST", "成本");
        INDICATOR_DIMENSION_MAP.put("COMPREHENSIVE", "综合");

        INDICATOR_DIMENSION_MAP_1 = new HashMap<>();
        INDICATOR_DIMENSION_MAP_1.put("品质", "QUALITY");
        INDICATOR_DIMENSION_MAP_1.put("服务", "SERVICE");
        INDICATOR_DIMENSION_MAP_1.put("交付", "DELIVER");
        INDICATOR_DIMENSION_MAP_1.put("技术", "TECHNOLOGY");
        INDICATOR_DIMENSION_MAP_1.put("成本", "COST");
        INDICATOR_DIMENSION_MAP_1.put("综合", "COMPREHENSIVE");

    }


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
        List<ScoreManScoringV1> scoreManScoringV1List = new ArrayList<>();
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
        String createdByUsername = scoreItems.getCreatedBy();
        String createdNickName = scoreItems.getCreatedFullName();

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
                ScoreManScoringV1 manScoringV1 = new ScoreManScoringV1();
                manScoringV1.setScoreItemsId(scoreItemsId)
                        .setStatus(ScoreItemsProjectStatusEnum.SCORE_DRAFT.getValue())
                        .setTemplateHeadId(templateHeadId)
                        .setTemplateName(templateName)
                        .setProjectName(projectName)
                        .setEvaluationPeriod(evaluationPeriod)
                        .setPerStartMonth(perStartMonth)
                        .setPerEndMonth(perEndMonth)
                        .setOrganizationId(organizationId)
                        .setOrganizationName(organizationName)
                        .setScoreItemsCreatedUsername(createdByUsername)
                        .setScoreItemsCreatedNickname(createdNickName)
                        .setCategoryId(categoryId)
                        .setCategoryCode(categoryCode)
                        .setCategoryName(categoryName)
                        .setCategoryFullName(categoryFullName);
                Long scoreManScoringId = IdGenrator.generate();
                manScoringV1.setScoreManScoringId(scoreManScoringId);
                manScoringV1.setCompanyId(manSupInd.getCompanyId())
                        .setCompanyCode(manSupInd.getCompanyCode())
                        .setCompanyName(manSupInd.getCompanyName());
                // 获取指标维度行
                Long templateDimWeightId = manSupInd.getTemplateDimWeightId();
                PerfTemplateDimWeight perfTemplateDimWeight = iPerfTemplateDimWeightService.getById(templateDimWeightId);
                manScoringV1.setDimWeightId(manSupInd.getTemplateDimWeightId())
                        .setIndicatorType(perfTemplateDimWeight.getIndicatorType())
                        .setIndicatorDimensionType(perfTemplateDimWeight.getIndicatorDimensionType())
                        .setIndicatorDimensionWeight(perfTemplateDimWeight.getIndicatorDimensionWeight());
                // 获取指标行
                Long templateLineId = manSupInd.getTemplateLineId();
                PerfTemplateLine perfTemplateLine = iPerfTemplateLineService.getById(templateLineId);
                manScoringV1.setEvaluation(perfTemplateLine.getEvaluation())
                        .setTemplateLineId(templateLineId)
                        .setIndicatorName(perfTemplateLine.getIndicatorName())
                        .setIndicatorLineType(perfTemplateLine.getIndicatorLineType())
                        .setQuoteMode(perfTemplateLine.getQuoteMode())
                        .setDimensionWeight(new BigDecimal(perfTemplateLine.getDimensionWeight()))
                        .setIfScored(YesOrNo.NO.getValue());
                Long scoreItemsManId = manSupInd.getScoreItemsManId();
                PerfScoreItemsMan scoreItemsMan = iPerfScoreItemsManService.getById(scoreItemsManId);
                Assert.notNull(scoreItemsMan, "评分人不存在。");
                manScoringV1.setScoreUserName(scoreItemsMan.getScoreUserName())
                        .setScoreNickName(scoreItemsMan.getScoreNickName());
                // 检查评分人绩效评分表保存字段不为空
                this.checkEditScoreManScoring(manScoringV1);
                scoreManScoringV1List.add(manScoringV1);
            }
        }

        if (CollectionUtils.isNotEmpty(scoreManScoringV1List)) {
            try {
                boolean isSave = super.saveBatch(scoreManScoringV1List);
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
                UpdateWrapper<PerfScoreItems> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("SCORE_PEOPLE_COUNT",Long.parseLong(String.valueOf(scorePeopleCount)))
                        .eq("SCORE_ITEMS_ID", scoreItems.getScoreItemsId());
                PerfScoreItems updateScoreItems = new PerfScoreItems();
                updateScoreItems.setScoreItemsId(scoreItemsId);
                updateScoreItems.setScorePeopleCount(Long.parseLong(String.valueOf(scorePeopleCount)));
                iPerfScoreItemsService.update(updateWrapper);
            } catch (Exception e) {
                log.error("获取指标维度绩效得分保存操作-修改绩效评分项目评分人总数时报错：", e);
                throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
            }
        }
        return result;
    }

    /**
     * 评分人绩效评分提交
     *
     * @param scoreManScoringV1List
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveScoreManScoring(List<ScoreManScoringV1> scoreManScoringV1List) {
        String result = ResultCode.OPERATION_FAILED.getMessage();
        scoreManScoringV1List.forEach(scoreManScoringV1 -> {
            // 绩效明细的评分说明，在提交的时候要加必填校验
            if (StringUtils.isEmpty(scoreManScoringV1.getComments())) {
                String projectName = scoreManScoringV1.getProjectName();
                String companyName = scoreManScoringV1.getCompanyName();
                String indicatorDimensionType = scoreManScoringV1.getIndicatorDimensionType();
                String indicatorDimension = INDICATOR_DIMENSION_MAP.get(indicatorDimensionType);
                String indicatorName = scoreManScoringV1.getIndicatorName();
                String resultMsg = LocaleHandler.getLocaleMsg("打分说明不能为空！项目名称[x],供应商名称[x],指标维度[x],指标名称[x]",
                        projectName, companyName, indicatorDimension, indicatorName);
                throw new BaseException(resultMsg);
            }
            scoreManScoringV1.setIfScored(YesOrNo.YES.getValue());
        });
        try {
            boolean isUpdate = super.updateBatchById(scoreManScoringV1List);
            if (isUpdate) {
                result = ResultCode.SUCCESS.getMessage();
            }
        } catch (Exception e) {
            log.error("保存评分人评分集合信息时报错：", e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return result;
    }

    /**
     * 放弃评分
     * @param scoreManScoringV1List
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abstention(List<ScoreManScoringV1> scoreManScoringV1List) {
        // 校验评分是否都为空
        scoreManScoringV1List.forEach(scoreManScoringV1 -> {
            if (Objects.nonNull(scoreManScoringV1.getPefScore())) {
                StringBuffer sb = new StringBuffer();
                String indicatorDimensionTypeName = INDICATOR_DIMENSION_MAP.get(scoreManScoringV1.getIndicatorDimensionType());
                sb.append("绩效项目：[").append(scoreManScoringV1.getProjectName()).append("]，供应商：[")
                        .append(scoreManScoringV1.getCompanyName()).append("]，指标维度：[")
                        .append(indicatorDimensionTypeName).append("]，指标名称：[")
                        .append(scoreManScoringV1.getIndicatorName()).append("]，已存在评分值，请重新选择要弃权评分的数据。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        });
        scoreManScoringV1List.forEach(scoreManScoringV1 -> {
            scoreManScoringV1.setIfScored(YesOrNo.YES.getValue());
        });
        this.updateBatchById(scoreManScoringV1List);
    }

    /**
     * 分页查询
     *
     * @param scoreManScoringV1
     * @return
     */
    @Override
    public List<ScoreManScoringV1> listScoreManScoringPage(ScoreManScoringV1 scoreManScoringV1) {
        // 只有采购商类型的用户才能评分
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        String userName = "";
        if (null != user) {
            // 文宣说,绩效明细页面,供应商也要能看到数据,所以这里注释掉
//            Assert.isTrue("BUYER".equals(user.getUserType()), ScoreManScoringConst.IS_BUYER_SCORE);
            userName = user.getUsername();
        }
        Assert.notNull(userName, ScoreManScoringConst.USER_NAME_NOT_NULL);

        List<ScoreManScoringV1> scoreManScoringList = new ArrayList<>();
        if ("BUYER".equals(user.getUserType())) {
            scoreManScoringList = listScoreManScoringV1ByBuyer(scoreManScoringV1, userName);
        } else if ("VENDOR".equals(user.getUserType())) {
            scoreManScoringV1.setCompanyId(user.getCompanyId());
            scoreManScoringV1.setIfScored(YesOrNo.YES.getValue());
            scoreManScoringList = listScoreManScoringV1ByVendor(scoreManScoringV1);
        }

        // 根据指标维度表ID获取对应的绩效模型指标行相关评分信息
        List<PerfTemplateLine> templateLineList = iPerfTemplateLineService.list();

        if (CollectionUtils.isNotEmpty(scoreManScoringList) && CollectionUtils.isNotEmpty(templateLineList)) {
            for (ScoreManScoringV1 manScoringV1 : scoreManScoringList) {
                if (null != manScoringV1 && null != manScoringV1.getTemplateLineId()) {
                    Long temlateLineId = manScoringV1.getTemplateLineId();
                    for (PerfTemplateLine templateLine : templateLineList) {
                        if (null != templateLine && (temlateLineId.longValue() == templateLine.getTemplateLineId().longValue())) { //判断指标ID相等
                            List<PerfTemplateIndsLine> templateIndsLineList = (List<PerfTemplateIndsLine>) ObjectUtil.deepCopy(templateLine.getPerfTemplateIndsLineList());
                            manScoringV1.setTemplateIndsLineList(templateIndsLineList);
                            break;
                        }
                    }
                    PerfTemplateLine templateLine = iPerfTemplateLineService.getById(temlateLineId);
                    manScoringV1.setIndicatorLogic(templateLine.getIndicatorLogic());
                    // 赋值模板品类属性字段
                    Long templateHeadId = manScoringV1.getTemplateHeadId();
                    List<PerfTemplateCategory> perfTemplateCategoryList = iPerfTemplateCategoryService.list(Wrappers.lambdaQuery(PerfTemplateCategory.class)
                            .eq(PerfTemplateCategory::getTemplateHeadId, templateHeadId));
                    String categoryNames = "";
                    if (CollectionUtils.isNotEmpty(perfTemplateCategoryList)) {
                        List<String> categoryNameList = perfTemplateCategoryList.stream().filter(e -> Objects.nonNull(e.getCategoryId()))
                                .map(PerfTemplateCategory::getCategoryName).collect(Collectors.toList());
                        categoryNames = StringUtils.join(categoryNameList, ";");
                    }
                    manScoringV1.setCategoryNames(categoryNames);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(scoreManScoringList)){
            scoreManScoringList.forEach(scoreManScoringV11 -> {
                List<IndicatorsLine> indicatorsLines = indicatorsLineMapper.queruIndicatorsLine(scoreManScoringV11.getScoreManScoringId());
                scoreManScoringV11.setIndicatorsLines(indicatorsLines);
            });
        }

        return scoreManScoringList;
    }

    /**
     * 采购商查询
     * @param scoreManScoringV1
     * @param userName
     * @return
     */
    public List<ScoreManScoringV1> listScoreManScoringV1ByBuyer(ScoreManScoringV1 scoreManScoringV1, String userName) {
        QueryWrapper<ScoreManScoringV1> wrapper = new QueryWrapper<ScoreManScoringV1>();
        if (null != scoreManScoringV1) {
            if (StringUtils.isNotEmpty(scoreManScoringV1.getProjectName())) {
                wrapper.like("PROJECT_NAME", scoreManScoringV1.getProjectName());
            }
            if (Objects.nonNull(scoreManScoringV1.getCompanyId())) {
                wrapper.eq("COMPANY_ID", scoreManScoringV1.getCompanyId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getCompanyName())) {
                wrapper.like("COMPANY_NAME", scoreManScoringV1.getCompanyName());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIndicatorName())) {
                wrapper.like("INDICATOR_NAME", scoreManScoringV1.getIndicatorName());
            }
            if (null != scoreManScoringV1.getOrganizationId()) {
                wrapper.eq("ORGANIZATION_ID", scoreManScoringV1.getOrganizationId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getOrganizationName())) {
                wrapper.eq("ORGANIZATION_NAME", scoreManScoringV1.getOrganizationName());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIndicatorDimensionType())) {
                wrapper.eq("INDICATOR_DIMENSION_TYPE", scoreManScoringV1.getIndicatorDimensionType());
            }
            if (null != scoreManScoringV1.getCategoryId()) {
                wrapper.eq("CATEGORY_ID", scoreManScoringV1.getCategoryId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getCategoryName())) {
                wrapper.eq("CATEGORY_NAME", scoreManScoringV1.getCategoryName());
            }
            // 是否已计算评分
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIfEndScored())) {
                wrapper.eq("IF_END_SCORED", scoreManScoringV1.getIfEndScored());
            }
            // 是否已评分
            // 否
            if (Objects.equals(YesOrNo.NO.getValue(), scoreManScoringV1.getIfScored())) {
                wrapper.eq("IF_SCORED", YesOrNo.NO.getValue());
            }
            // 是
            if (Objects.equals(YesOrNo.YES.getValue(), scoreManScoringV1.getIfScored())) {
                wrapper.eq("IF_SCORED", YesOrNo.YES.getValue());
            }
            // 是否以发起人身份查询
            // 是 以发起人身份查询
            if (Objects.equals(YesOrNo.YES.getValue(), scoreManScoringV1.getIfQueryByScoreItemsCreatedBy())) {
                wrapper.eq("SCORE_ITEMS_CREATED_USERNAME", userName);
            }
            // 否 不以发起人身份查询 只能查到自己作为评分人的数据
            if (Objects.equals(YesOrNo.NO.getValue(), scoreManScoringV1.getIfQueryByScoreItemsCreatedBy()) || (!isFunSetAll(AppUserUtil.getLoginAppUser(),"graderRating") && StringUtils.isEmpty(scoreManScoringV1.getIfQueryByScoreItemsCreatedBy()))) {
                /**控制评分人查看权限(当前用户只能查询到自己要评分的信息)*/
                wrapper.eq("SCORE_USER_NAME", userName);
            }
            //12449 绩效明细界面的权限。如果不配权限就按原有逻辑控制。如果配置了全部。则当是否按发起人查询条件为空的时候，查出所有的绩效明细数据
        }

        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return getBaseMapper().selectList(wrapper);
    }

    public boolean isFunSetAll(LoginAppUser user, String functionCode) {
        if(Objects.isNull(user) || StringUtils.isEmpty(functionCode)){
           return false;
        }
        List<Role> roleList = user.getRolePermissions();
        if(CollectionUtils.isEmpty(roleList)){
            return false;
        }
        List<String> roleCodes = roleList.stream().map(x->x.getRoleCode()).collect(Collectors.toList());
        //根据菜单Code获取角色-菜单配置集合
        List<RoleFuncSet> roleFuncSetList = rbacClient.findRoleFuncSetMoreRole(functionCode,roleCodes);
        return roleFuncSetList.stream().anyMatch(x -> Objects.equals("ALL", x.getRoleFuncSetType()));

    }

            /**
             * 供应商查询
             * @param scoreManScoringV1
             * @return
             */
    public List<ScoreManScoringV1> listScoreManScoringV1ByVendor(ScoreManScoringV1 scoreManScoringV1) {
        QueryWrapper<ScoreManScoringV1> wrapper = new QueryWrapper<ScoreManScoringV1>();
        if (null != scoreManScoringV1) {
            if (StringUtils.isNotEmpty(scoreManScoringV1.getProjectName())) {
                wrapper.like("PROJECT_NAME", scoreManScoringV1.getProjectName());
            }
            if (Objects.nonNull(scoreManScoringV1.getCompanyId())) {
                wrapper.eq("COMPANY_ID", scoreManScoringV1.getCompanyId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getCompanyName())) {
                wrapper.like("COMPANY_NAME", scoreManScoringV1.getCompanyName());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIndicatorName())) {
                wrapper.like("INDICATOR_NAME", scoreManScoringV1.getIndicatorName());
            }
            if (null != scoreManScoringV1.getOrganizationId()) {
                wrapper.eq("ORGANIZATION_ID", scoreManScoringV1.getOrganizationId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getOrganizationName())) {
                wrapper.eq("ORGANIZATION_NAME", scoreManScoringV1.getOrganizationName());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIndicatorDimensionType())) {
                wrapper.eq("INDICATOR_DIMENSION_TYPE", scoreManScoringV1.getIndicatorDimensionType());
            }
            if (null != scoreManScoringV1.getCategoryId()) {
                wrapper.eq("CATEGORY_ID", scoreManScoringV1.getCategoryId());
            }
            if (StringUtils.isNotEmpty(scoreManScoringV1.getCategoryName())) {
                wrapper.eq("CATEGORY_NAME", scoreManScoringV1.getCategoryName());
            }
            // 是否已计算评分
            if (StringUtils.isNotEmpty(scoreManScoringV1.getIfEndScored())) {
                wrapper.eq("IF_END_SCORED", scoreManScoringV1.getIfEndScored());
            }
            // 是否已评分
            // 否
            if (Objects.equals(YesOrNo.NO.getValue(), scoreManScoringV1.getIfScored())) {
                wrapper.eq("IF_SCORED", YesOrNo.NO.getValue());
            }
            // 是
            if (Objects.equals(YesOrNo.YES.getValue(), scoreManScoringV1.getIfScored())) {
                wrapper.eq("IF_SCORED", YesOrNo.YES.getValue());
            }
        }

        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return getBaseMapper().selectList(wrapper);
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
    public void checkEditScoreManScoring(ScoreManScoringV1 scoreManScoringV1) {
        Assert.notNull(scoreManScoringV1, ScoreManScoringConst.SCORE_MAN_SCORING_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getScoreManScoringId(), "评分人评分主键id不能为空");
        Assert.notNull(scoreManScoringV1.getStatus(), ScoreManScoringConst.STAUS_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getScoreItemsId(), ScoreManScoringConst.SCORE_ITEMS_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getTemplateHeadId(), ScoreManScoringConst.TEMPLATE_HEAD_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getTemplateName(), ScoreManScoringConst.TEMPLATE_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getProjectName(), ScoreManScoringConst.PROJECT_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getEvaluationPeriod(), ScoreManScoringConst.EVALUATION_PERIOD_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getPerStartMonth(), ScoreManScoringConst.PER_START_MONTH_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getPerEndMonth(), ScoreManScoringConst.PER_END_MONTH_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getOrganizationId(), PerfLevelConst.ORGANIZATION_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getOrganizationName(), PerfLevelConst.ORGANIZATION_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getCategoryId(), ScoreManScoringConst.CATEGORY_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getCategoryName(), ScoreManScoringConst.CATEGORY_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getCompanyId(), ScoreManScoringConst.COMPANY_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getCompanyCode(), ScoreManScoringConst.COMPANY_CODE_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getCompanyName(), ScoreManScoringConst.COMPANY_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getDimWeightId(), ScoreManScoringConst.DIM_WEIGHT_ID_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getIndicatorType(), IndicatorsConst.INDICATOR_TYPE_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getIndicatorDimensionType(), IndicatorsConst.INDICATOR_DIMENSION_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getIndicatorDimensionWeight(), ScoreManScoringConst.INDICATOR_DIMENSION_WEIGHT_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getIndicatorName(), IndicatorsConst.INDICATOR_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getIndicatorLineType(), ScoreManScoringConst.INDICATOR_LINE_TYPE_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getQuoteMode(), IndicatorsConst.QUOTE_MODE_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getDimensionWeight(), ScoreManScoringConst.DIMENSION_WEIGHT_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getScoreUserName(), ScoreManScoringConst.SCORE_USER_NAME_NOT_NULL);
        Assert.notNull(scoreManScoringV1.getScoreNickName(), ScoreManScoringConst.SCORE_NICK_NAME_NOT_NULL);
    }

    /**
     * 附件上传
     *
     * @param scoreManScoringV1
     */
    @Override
    public void uploadFile(ScoreManScoringV1 scoreManScoringV1) {
        Assert.notNull(scoreManScoringV1.getScoreManScoringFileId(), "上传的附件不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(scoreManScoringV1.getScoreManScoringFileName()), "上传的附件名称不能为空。");
        ScoreManScoringV1 byId = this.getById(scoreManScoringV1.getScoreManScoringId());
        byId.setScoreManScoringFileId(scoreManScoringV1.getScoreManScoringFileId())
                .setScoreManScoringFileName(scoreManScoringV1.getScoreManScoringFileName());
        this.updateById(byId);
    }

    /**
     * 附件删除
     *
     * @param scoreManScoringV1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(ScoreManScoringV1 scoreManScoringV1) {
        ScoreManScoringV1 byId = this.getById(scoreManScoringV1.getScoreManScoringId());
        byId.setScoreManScoringFileId(null)
                .setScoreManScoringFileName(null);
        this.updateById(byId);
        fileCenterClient.delete(scoreManScoringV1.getScoreManScoringFileId());
    }

    /**
     * 锁定绩效项目评分页面的 是否已截止评分标志位
     *
     * @param scoreItemsId
     */
    @Override
    public void lockIfEndScored(Long scoreItemsId) {
        List<ScoreManScoringV1> scoreManScoringV1List = this.list(Wrappers.lambdaQuery(ScoreManScoringV1.class)
                .eq(ScoreManScoringV1::getScoreItemsId, scoreItemsId));
        if (CollectionUtils.isNotEmpty(scoreManScoringV1List)) {
            scoreManScoringV1List.forEach(scoreManScoringV1 -> {
                scoreManScoringV1.setIfEndScored(YesOrNo.YES.getValue());
            });
            try {
                this.updateBatchById(scoreManScoringV1List);
            } catch (Exception e) {
                StringBuffer sb = new StringBuffer();
                sb.append("锁定评分人绩效项目评分截止评分标志位失败。");
                log.error(sb.toString());
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 评分人绩效评分提交前获取哪些数据没有评分
     *
     * @param scoreManScoringV1List
     * @return
     */
    @Override
    public BaseResult<ScoreManScoringV1SubmitConfirmDTO> confirmBeforeScoreManScoringSubmit
    (List<ScoreManScoringV1> scoreManScoringV1List) {
        List<Long> ids = scoreManScoringV1List.stream()
                .map(ScoreManScoringV1::getScoreManScoringId).collect(Collectors.toList());
        List<ScoreManScoringV1> scoreManScoringV1s = this.listByIds(ids);
        scoreManScoringV1s.forEach(s ->{
            if (StringUtils.equals(s.getIfEndScored(), YesOrNo.YES.getValue())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商[x][x]指标已计算评分,不能修改提交",s.getCompanyName(),s.getIndicatorName()));
            }
        });
        // 提交评分前校验
        ScoreManScoringV1SubmitConfirmDTO submitConfirmDTO = new ScoreManScoringV1SubmitConfirmDTO();
        BaseResult baseResult = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        List<String> notScoredList = new ArrayList<>();
        scoreManScoringV1List.forEach(scoreManScoringV1 -> {
            if (Objects.isNull(scoreManScoringV1.getPefScore())) {
                StringBuffer sb = new StringBuffer();
                sb.append("评分项目：[").append(scoreManScoringV1.getProjectName()).append("]，指标维度：[")
                        .append(INDICATOR_DIMENSION_MAP.get(scoreManScoringV1.getIndicatorDimensionType())).append("]，指标名称：[")
                        .append(scoreManScoringV1.getIndicatorName()).append("]，供应商名称：[").append(scoreManScoringV1.getCompanyName()).append("]，未评分。");
                notScoredList.add(sb.toString());
            }
        });
        submitConfirmDTO.setNotScoredList(notScoredList);
        baseResult.setData(submitConfirmDTO);
        return baseResult;
    }

    /**
     * 评分人绩效评分获取项目列表
     *
     * @param scoreManScoringV1List
     * @return
     */
    @Override
    public List<PerfScoreItems> getScoreItemsAfterSubmit(List<ScoreManScoringV1> scoreManScoringV1List) {
        List<Long> scoreItemsIdList = scoreManScoringV1List.stream().map(ScoreManScoringV1::getScoreItemsId)
                .filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(scoreItemsIdList)) {
            return Collections.EMPTY_LIST;
        }
        return iPerfScoreItemsService.listByIds(scoreItemsIdList);
    }

    /**
     * 评分人绩效评分
     *
     * @param scoreManScoringV1ExportParam
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public void exportScoreManScoringV1Excel
    (ExportExcelParam<ScoreManScoringV1> scoreManScoringV1ExportParam, HttpServletResponse response) throws
            IOException {
        // 获取导出的数据
        List<List<Object>> dataList = this.queryExportData(scoreManScoringV1ExportParam);
        // 标题
        LinkedHashMap<String, String> scoreManScoringV1Titles = ScoreManScoringV1ExportUtils.getScoreManScoringV1Titles();
        scoreManScoringV1Titles.put("scoreManScoringId", "唯一标识");
        List<String> head = scoreManScoringV1ExportParam.getMultilingualHeader(scoreManScoringV1ExportParam, scoreManScoringV1Titles);
        // 文件名
        String fileName = scoreManScoringV1ExportParam.getFileName();
        // 开始导出
        EasyExcelUtilNotAutoSetWidth.exportStart(response, dataList, head, fileName);
    }

    /**
     * 评分人绩效评分
     *
     * @param response
     * @throws Exception
     */
    @Override
    public void importScoreManScoringV1Download(HttpServletResponse response) throws Exception {
        String fileName = "评分人绩效评分导入模版";
        List<ScoreManScoringV1Import> scoreManScoringV1Imports = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        List column = Arrays.asList(IntStream.range(0, 11).toArray());
        HashMap titleConfig = new HashMap();
        titleConfig.put(0, "必填，项目名称必填");
        titleConfig.put(1, "必填，供应商名称必填");
        titleConfig.put(2, "必填，指标维度必填");
        titleConfig.put(3, "必填，指标名称必填");
        titleConfig.put(4, "非必填");
        titleConfig.put(5, "非必填");
        titleConfig.put(6, "非必填");
        titleConfig.put(7, "非必填");
        titleConfig.put(8, "非必填");
        titleConfig.put(9, "非必填");
        titleConfig.put(10, "非必填");
        titleConfig.put(11, "非必填");
        titleConfig.put(12, "非必填");
        titleConfig.put(13, "非必填");
        titleConfig.put(14, "非必填");
        titleConfig.put(15, "必填，唯一标识必填");
        TitleHandler titleHandler = new TitleHandler(column, IndexedColors.RED.index, titleConfig);
        EasyExcelUtil.writeExcelWithModel(outputStream, scoreManScoringV1Imports, ScoreManScoringV1Import.class, fileName, titleHandler);
    }

    /**
     * 评分人绩效评分自定义导入
     *
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> importScoreManScoringV1Excel(MultipartFile file, Fileupload fileupload) throws
            Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ScoreManScoringV1Import> scoreManScoringV1ImportList = readData(file);
        // 是否有报错标识
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 获取数据
        List<ScoreManScoringV1> scoreManScoringV1List = getImportData(scoreManScoringV1ImportList, errorFlag);
        if (errorFlag.get()) {
            //报错
            fileupload.setFileSourceName("评分人绩效评分导入报错");
            Fileupload fileUpload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    scoreManScoringV1ImportList, ScoreManScoringV1Import.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileUpload.getFileuploadId(), fileUpload.getFileSourceName());
        } else {
            // 更新数据库表数据
            updateScoreManScoringV1s(scoreManScoringV1List);
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 查询通过excel导入的数据
     *
     * @return
     */
    @Override
    public List<ScoreManScoringV1> listExcelImportData() {
        return this.list(Wrappers.lambdaQuery(ScoreManScoringV1.class)
                .eq(ScoreManScoringV1::getIfExcelImport, YesOrNo.YES.getValue())
                .eq(ScoreManScoringV1::getIfFreshAfterImport, YesOrNo.NO.getValue())
        );
    }

    /**
     * 刷新通过excel导入的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void freshExcelImportData() {
        List<ScoreManScoringV1> scoreManScoringV1s = this.listExcelImportData();
        scoreManScoringV1s.forEach(scoreManScoringV1 -> {
            scoreManScoringV1.setIfFreshAfterImport(YesOrNo.YES.getValue());
        });
        this.updateBatchById(scoreManScoringV1s);
    }

    /**
     * 读取excel文件的数据
     *
     * @param file
     * @return
     */
    private List<ScoreManScoringV1Import> readData(MultipartFile file) {
        List<ScoreManScoringV1Import> scoreManScoringV1ImportList = null;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<ScoreManScoringV1Import> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(ScoreManScoringV1Import.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            scoreManScoringV1ImportList = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return scoreManScoringV1ImportList;
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     */
    private List<ScoreManScoringV1> getImportData(List<ScoreManScoringV1Import> scoreManScoringV1ImportList, AtomicBoolean errorFlag) throws IOException, ParseException {
        List<ScoreManScoringV1> scoreManScoringV1s = new ArrayList<>();
        BigDecimal zero = new BigDecimal(0);
        BigDecimal hundred = new BigDecimal(100);
        if (CollectionUtils.isNotEmpty(scoreManScoringV1ImportList)) {
            for (ScoreManScoringV1Import scoreManScoringV1Import : scoreManScoringV1ImportList) {
                ScoreManScoringV1 scoreManScoringV1 = new ScoreManScoringV1();

                List<Long> scoreManScoringV1IdList = new ArrayList<>();

                List<String> scoreManScoringV1IdStringList = scoreManScoringV1ImportList.stream().map(ScoreManScoringV1Import::getScoreManScoringId)
                        .filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                scoreManScoringV1IdStringList.forEach(scoreManScoringV1IdString -> {
                    scoreManScoringV1IdList.add(Long.valueOf(scoreManScoringV1IdString));
                });

                List<ScoreManScoringV1> dbScoreManScoringV1List = this.listByIds(scoreManScoringV1IdList);

                Map<Long, ScoreManScoringV1> dbScoreManScoringMap = dbScoreManScoringV1List.stream().collect(Collectors.toMap(k->k.getScoreManScoringId(), Function.identity(), (o1, o2) -> o2));

                StringBuffer errorMsg = new StringBuffer();
                // 获取主键id
                String scoreManScoringIdString = scoreManScoringV1Import.getScoreManScoringId();
                if (StringUtils.isEmpty(scoreManScoringIdString)) {
                    errorFlag.set(true);
                    errorMsg.append("唯一标识不能为空，请先导出需要评分的数据，进行评分，再进行导入。");
                } else {
                    Long scoreManScoringId = Long.valueOf(scoreManScoringIdString);
                    if (!dbScoreManScoringMap.containsKey(scoreManScoringId) && Objects.nonNull(dbScoreManScoringMap.get(scoreManScoringId))) {
                        errorFlag.set(true);
                        errorMsg.append("根据唯一标识：[").append(scoreManScoringId).append("]找不到对应的评分记录，请先导出需要评分的数据，进行评分，再进行导入。");
                    } else {
                        ScoreManScoringV1 manScoringV1 = dbScoreManScoringMap.get(scoreManScoringId);
                        String quoteMode = manScoringV1.getQuoteMode();
                        // 获取评分值
                        String pefScoreString = scoreManScoringV1Import.getPefScore();

                        if (StringUtils.isNotEmpty(pefScoreString)) {
                            // 直接取值 区间折算
                            if (Objects.equals(QuoteMode.DIRECT_QUOTE.getValue(), quoteMode) || Objects.equals(QuoteMode.INTERVAL_CONVERSION.getValue(), quoteMode)) {
                                if (!isNumeric(pefScoreString)) {
                                    errorFlag.set(true);
                                    errorMsg.append("指标评分值：[").append(pefScoreString).append("]不符合格式。");
                                } else {
                                    BigDecimal pefScore = new BigDecimal(pefScoreString);
                                    if (pefScore.compareTo(zero) < 0 || pefScore.compareTo(hundred) > 0) {
                                        errorFlag.set(true);
                                        errorMsg.append("指标评分值：[").append(pefScoreString).append("]不符合百分制打分格式。请输入0-100评分值。");
                                    }
                                    scoreManScoringV1.setPefScore(pefScore);
                                }
                            }
                            // 文本折算
                            if (Objects.equals(QuoteMode.TEXT_CONVERSION.getValue(), quoteMode)) {
                                BigDecimal pefScore = new BigDecimal(pefScoreString);
                                scoreManScoringV1.setPefScore(pefScore);
                            }
                        }
                        scoreManScoringV1.setComments(scoreManScoringV1Import.getComments());
                    }
                }
                Long scoreManScoringId = Long.valueOf(scoreManScoringIdString);
                scoreManScoringV1.setScoreManScoringId(scoreManScoringId);

                if (errorMsg.length() > 0) {
                    scoreManScoringV1Import.setErrorMsg(errorMsg.toString());
                } else {
                    scoreManScoringV1Import.setErrorMsg(null);
                }
                if (Objects.nonNull(scoreManScoringV1.getPefScore())) {
                    scoreManScoringV1s.add(scoreManScoringV1);
                }
            }
        }
        return scoreManScoringV1s;
    }

    /**
     * 更新评分数据
     *
     * @param scoreManScoringV1List
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateScoreManScoringV1s(List<ScoreManScoringV1> scoreManScoringV1List) {
        List<ScoreManScoringV1> updateList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(scoreManScoringV1List)) {
            for (ScoreManScoringV1 scoreManScoringV1 : scoreManScoringV1List) {
                if (Objects.nonNull(scoreManScoringV1)) {
                    ScoreManScoringV1 v1 = this.getById(scoreManScoringV1.getScoreManScoringId());
                    v1.setPefScore(scoreManScoringV1.getPefScore());
                    v1.setIfExcelImport(YesOrNo.YES.getValue())
                            .setIfFreshAfterImport(YesOrNo.NO.getValue())
                            .setComments(scoreManScoringV1.getComments());
                    updateList.add(v1);
                }
            }
            if (CollectionUtils.isNotEmpty(updateList)) {
                log.info("将excel文件评分人绩效评分数据导入到数据库...更新操作");
                this.updateBatchById(updateList);
            }
        }
    }

    /**
     * 查询导出数据并转换excel数据集
     *
     * @param exportParam
     * @return
     * @Auth xiexh12@meicloud.com 2021-01-28 19:50
     */
    private List<List<Object>> queryExportData(ExportExcelParam<ScoreManScoringV1> exportParam) {
        // 查询参数
        ScoreManScoringV1 queryParam = exportParam.getQueryParam();

        /**
         * 先查询要导出的数据有多少条, 大于2万是建议用户填分页参数进行分页导出, 每页不能超过2万
         */
        int count = this.listScoreManScoringPage(queryParam).size();
        if (count > 50000) {
            /**
             * 要导出的数据超过50000, 要求用户选择分页参数
             */
            if (StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum())) {
                Assert.isTrue(queryParam.getPageSize() <= 50000, "分页导出单页上限50000");
            } else {
                throw new BaseException("当前要导出的数据超过50000条, 请选择分页导出, 单页上限50000;");
            }
        }
        boolean flag = null != queryParam.getPageSize() && null != queryParam.getPageNum();
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        List<ScoreManScoringV1> scoreManScoringExportList = this.listScoreManScoringPage(queryParam);

        // 转Map(对象转map，key：属性名，value：属性值)
        List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(scoreManScoringExportList);
        // 导出excel的标题
        List<String> titleList = exportParam.getTitleList();

        // 绩效开始月份，绩效结束月份 列
        List<String> perStartMonthAndEndMonth = Arrays.asList("perStartMonth", "perEndMonth");
        // 绩效评分值，绩效得分 列
        List<String> perScoreAndScore = Arrays.asList("perScore", "score");
        // 是 否
        Map<String, String> yNMap= new HashMap<>();
        yNMap.put("Y", "是");
        yNMap.put("N", "否");

        List<List<Object>> results = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mapList)) {
            mapList.forEach((map) -> {
                List<Object> list = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(titleList)) {
                    titleList.forEach((title) -> {
                        if (Objects.nonNull(map)) {
                            Object value = map.get(title);
                            // 指标维度 字典码转换
                            if ("indicatorDimensionType".equals(title)) {
                                String s = INDICATOR_DIMENSION_MAP.get(String.valueOf(value));
                                list.add(s);
                            }
                            // 绩效开始和结束月份
                            else if (perStartMonthAndEndMonth.contains(title)) {
                                Date date = DateUtil.localDateToDate((LocalDate) value);
                                String s = DateUtil.format(date, "yyyy-MM");
                                list.add(s);
                            } else if (perScoreAndScore.contains(title)) {
                                BigDecimal bigDecimal = (BigDecimal) value;
                                if (null == bigDecimal) {
                                    list.add("");
                                } else if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) {
                                    list.add("0");
                                } else {
                                    list.add(String.valueOf(bigDecimal));
                                }
                            } else if ("scoreNickName".equals(title)) {
                                String scoreNickName = (String) map.get("scoreNickName");
                                String scoreUserName = (String) map.get("scoreUserName");
                                String scorer = scoreNickName + "(" + scoreUserName + ")";
                                list.add(scorer);
                            } else if ("ifEndScored".equals(title)) {
                                String s = yNMap.get(String.valueOf(value));
                                list.add(s);
                            }
                            else {
                                list.add(StringUtil.StringValue(value));
                            }
                        }
                    });
                }
                results.add(list);
            });
        }
        return results;
    }

    /**
     * 根据查询条件查询
     *
     * @param queryDTO
     * @return
     */
    public QueryWrapper<ScoreManScoringV1> queryByParam(ScoreManScoringV1 queryDTO) {
        QueryWrapper<ScoreManScoringV1> queryWrapper = new QueryWrapper<>();
        // 项目名称
        if (StringUtils.isNotEmpty(queryDTO.getProjectName())) {
            queryWrapper.like("PROJECT_NAME", queryDTO.getProjectName());
        }
        // 采购组织
        if (Objects.nonNull(queryDTO.getOrganizationId())) {
            queryWrapper.eq("ORGANIZATION_ID", queryDTO.getOrganizationId());
        }
        // 供应商名称
        if (StringUtils.isNotEmpty(queryDTO.getCompanyName())) {
            queryWrapper.like("COMPANY_NAME", queryDTO.getCompanyName());
        }
        // 指标维度
        if (StringUtils.isNotEmpty(queryDTO.getIndicatorDimensionType())) {
            queryWrapper.eq("INDICATOR_DIMENSION_TYPE", queryDTO.getIndicatorDimensionType());
        }
        // 指标名称
        if (StringUtils.isNotEmpty(queryDTO.getIndicatorName())) {
            queryWrapper.eq("INDICATOR_NAME", queryDTO.getIndicatorName());
        }
        return queryWrapper;
    }

    /**
     * 设置评分人绩效评分的指标逻辑
     *
     * @param scoreManScoringV1List
     */
    public void setIndicatorLogic(List<ScoreManScoringV1> scoreManScoringV1List) {
        if (CollectionUtils.isNotEmpty(scoreManScoringV1List)) {
            scoreManScoringV1List.forEach(scoreManScoringV1 -> {
                PerfTemplateLine templateLine = iPerfTemplateLineService.getById(scoreManScoringV1.getTemplateLineId());
                scoreManScoringV1.setIndicatorLogic(templateLine.getIndicatorLogic());
            });
        }
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(\\-)?[0-9]+(\\.[0-9]*)?$");
        return pattern.matcher(str).matches();
    }


}
