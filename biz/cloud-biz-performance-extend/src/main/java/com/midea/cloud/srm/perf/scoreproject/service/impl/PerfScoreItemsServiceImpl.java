package com.midea.cloud.srm.perf.scoreproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsApproveStatusEnum;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsProjectStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemSupIndDTO;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemsDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.perf.common.ScoreItemsConst;
import com.midea.cloud.srm.perf.scoreproject.mapper.PerfScoreItemsMapper;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsManService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsSupService;
import com.midea.cloud.srm.perf.scoring.service.IPerfOverallScoreService;
import com.midea.cloud.srm.perf.scoring.service.IPerfScoreManScoringService;
import com.midea.cloud.srm.perf.scoring.service.IScoreManScoringV1Service;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateDimWeightService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateHeaderService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateIndsLineService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import feign.FeignException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <pre>
 *  绩效评分项目主信息表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-05 17:33:40
 *  修改内容:
 * </pre>
 */
@Service
public class PerfScoreItemsServiceImpl extends ServiceImpl<PerfScoreItemsMapper, PerfScoreItems> implements IPerfScoreItemsService {

    /**
     * 绩效模板维度Service
     */
    @Resource
    private IPerfTemplateDimWeightService iPerfTemplateDimWeightService;

    /**
     * 模板头Service
     */
    @Autowired
    private IPerfTemplateHeaderService iPerfTemplateHeaderService;

    /**
     * 绩效模板指标Service
     */
    @Resource
    private IPerfTemplateLineService iPerfTemplateLineService;

    /**
     * 绩效评分项目供应商Service
     */
    @Resource
    private IPerfScoreItemsSupService iPerfScoreItemsSupService;

    /**
     * 绩效评分项目评分人Service
     */
    @Resource
    private IPerfScoreItemsManService iPerfScoreItemsManService;

    /**绩效评分项目评分人-供应商Service*/
//    @Resource
//    private IPerfScoreItemsManSupService iPerfScoreItemsManSupService;
    /**绩效评分项目评分人-绩效指标Service*/
//    @Resource
//    private IPerfScoreItemsManIndicatorService iPerfScoreItemsManIndicatorService;
    /**
     * 绩效评分项目评分人-供应商指标Service
     */
    @Resource
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;
    /**
     * 评分人绩效评分Service
     */
    @Resource
    private IPerfScoreManScoringService iPerfScoreManScoringService;

    @Autowired
    private IScoreManScoringV1Service iScoreManScoringV1Service;

    /**
     * 指标维度绩效得分Service
     */
    @Resource
    private IPerfOverallScoreService iPerfOverallScoreService;

    /**
     * 绩效模板指标行Service
     */
    @Resource
    private IPerfTemplateIndsLineService iPerfTemplateIndsLineService;

    @Resource
    private WorkFlowFeign workFlowFeign;

    @Resource
    private RbacClient rbacClient;


    public static final Map<String, String> INDICATOR_DIMENSION_MAP;

    static  {
        INDICATOR_DIMENSION_MAP = new HashMap<>();
        INDICATOR_DIMENSION_MAP.put("QUALITY", "品质");
        INDICATOR_DIMENSION_MAP.put("SERVICE", "服务");
        INDICATOR_DIMENSION_MAP.put("DELIVER", "交付");
        INDICATOR_DIMENSION_MAP.put("TECHNOLOGY", "技术");
    }



    @Override
    public List<PerfTemplateLine> getPerfTemplateLineByHeaderId(Long templateHeadId) throws BaseException {
        Assert.notNull(templateHeadId, "id不能为空");
        List<PerfTemplateLine> resultPerfTemplateLineList = new ArrayList<>();
        PerfTemplateDimWeight templateDimWeight = new PerfTemplateDimWeight();
        templateDimWeight.setTemplateHeadId(templateHeadId);
        templateDimWeight.setDeleteFlag(Enable.N.toString());
        List<PerfTemplateDimWeight> templateHeaderList = null;
        try {
            templateHeaderList = iPerfTemplateDimWeightService.list(new QueryWrapper<>(templateDimWeight));
        } catch (Exception e) {
            log.error("绩效评分项目-根据ID获取绩效模型维度信息时报错：", e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }

        try {
            if (CollectionUtils.isNotEmpty(templateHeaderList)) {
                for (PerfTemplateDimWeight dimWeight : templateHeaderList) {
                    if (null != dimWeight && null != dimWeight.getDimWeightId()) {
                        Long dimWeightId = dimWeight.getDimWeightId();
                        PerfTemplateLine queryTemplateLine = new PerfTemplateLine();
                        queryTemplateLine.setDeleteFlag(Enable.N.toString());
                        queryTemplateLine.setTemplateDimWeightId(dimWeightId);
                        List<PerfTemplateLine> perfTemplateLineList = iPerfTemplateLineService.list(new QueryWrapper<>(queryTemplateLine));
                        if (CollectionUtils.isNotEmpty(perfTemplateLineList)) {
                            for (PerfTemplateLine line : perfTemplateLineList) {
                                if (null != line) {
                                    Long templateLineId = line.getTemplateLineId();
                                    PerfTemplateIndsLine templateIndsLine = new PerfTemplateIndsLine();
                                    templateIndsLine.setTemplateLineId(templateLineId);
                                    List<PerfTemplateIndsLine> templateIndsLineList = iPerfTemplateIndsLineService.list(new QueryWrapper<>(templateIndsLine));
                                    if (CollectionUtils.isNotEmpty(templateIndsLineList)) {
                                        line.setPerfTemplateIndsLineList(templateIndsLineList);
                                    }
                                    resultPerfTemplateLineList.add(line);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("绩效评分项目-根据维度ID获取绩效模型指标信息时报错：", e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return resultPerfTemplateLineList;
    }

    @Override
    public PerfScoreItemsDTO findScoreItemsAndSonList(Long scoreItemsId) {
        PerfScoreItemsDTO scoreItemsDTO = new PerfScoreItemsDTO();

        // 查询头表数据
        PerfScoreItems scoreItems = this.getById(scoreItemsId);
        BeanUtils.copyProperties(scoreItems, scoreItemsDTO);

        // 查询评分供应商数据
        List<PerfScoreItemsSup> scoreItemsSupList = iPerfScoreItemsSupService.list(Wrappers.lambdaQuery(PerfScoreItemsSup.class)
                .eq(PerfScoreItemsSup::getScoreItemsId, scoreItemsId)
        );
        scoreItemsDTO.setPerfScoreItemsSupList(scoreItemsSupList);

        // 查询评分人数据
        List<PerfScoreItemsMan> scoreItemsManList = iPerfScoreItemsManService.list(Wrappers.lambdaQuery(PerfScoreItemsMan.class)
                .eq(PerfScoreItemsMan::getScoreItemsId, scoreItemsId)
        );
        // 查询评分人分配的任务
        if (CollectionUtils.isNotEmpty(scoreItemsManList)) {
            scoreItemsManList.forEach(scoreItemsMan -> {
                List<PerfScoreItemManSupInd> scoreItemManSupIndList = iPerfScoreItemManSupIndService.list(Wrappers.lambdaQuery(PerfScoreItemManSupInd.class)
                        .eq(PerfScoreItemManSupInd::getScoreItemsManId, scoreItemsMan.getScoreItemsManId())
                );
                scoreItemsMan.setPerfScoreItemManSupIndList(scoreItemManSupIndList);
            });
        }
        scoreItemsDTO.setPerfScoreItemsManList(scoreItemsManList);

        return scoreItemsDTO;
    }

    /**
     * 绩效项目数据保存
     *
     * @param scoreItemsDTO
     * @return
     * @throws BaseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateScoreItemsAndSon(PerfScoreItemsDTO scoreItemsDTO) throws BaseException {
        Assert.notNull(scoreItemsDTO, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Long scoreItemsId = scoreItemsDTO.getScoreItemsId();
        PerfScoreItems perfScoreItems = new PerfScoreItems();
        BeanUtils.copyProperties(scoreItemsDTO, perfScoreItems);
        perfScoreItems.setScorePeopleCount(Long.valueOf(scoreItemsDTO.getPerfScoreItemsManList().size()));
        if (null == scoreItemsId) {   //新增操作
            scoreItemsId = IdGenrator.generate();
            perfScoreItems.setApproveStatus(ScoreItemsApproveStatusEnum.DRAFT.getValue());

        }

        //获取用户信息
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        String nickName = "";   //用户名称
        if (null != user) {
            nickName = user.getNickname();
        }
        /** 保存绩效评分项目主表信息**/
        boolean isUpdateScoreItems = false;
        // 校验校验区间月份是否重叠
        this.checkIfMonthOverLap(scoreItemsDTO);
        // 判断能否保存/修改绩效评分项目,能返回空，反之Assert提示错误信息
        this.checkIsSaveOrUpdateScoreItems(null, scoreItemsDTO);
        try {
            perfScoreItems.setScoreItemsId(scoreItemsId);
            perfScoreItems.setCreatedFullName(nickName);
            isUpdateScoreItems = super.saveOrUpdate(perfScoreItems);
        } catch (Exception e) {
            log.error("新增/保存绩效评分项目主表时报错：", e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }

        if (isUpdateScoreItems) {
            /**获取供绩效评分项目评分人和子表信息*/
            List<PerfScoreItemsMan> scoreItemsManList = scoreItemsDTO.getPerfScoreItemsManList();

            /**新增/修改供绩效评分项目供应商信息*/
            List<PerfScoreItemsSup> scoreItemsSupList = scoreItemsDTO.getPerfScoreItemsSupList();
            if (CollectionUtils.isNotEmpty(scoreItemsSupList)) {
                for (PerfScoreItemsSup scoreItemsSup : scoreItemsSupList) {
                    if (null != scoreItemsSup) {
                        Long scoreItemsSupId = scoreItemsSup.getScoreItemsSupId();
                        if (null == scoreItemsSupId) {
                            scoreItemsSupId = IdGenrator.generate();
                        }
                        scoreItemsSup.setScoreItemsSupId(scoreItemsSupId);
                        scoreItemsSup.setScoreItemsId(scoreItemsId);

                        //如果供应商ID和供应商Code相同，则把绩效评分供应商ID设置到绩效评分项目评分人-供应商指标表ID
                        Long companyId = scoreItemsSup.getCompanyId();
                        String companyCode = scoreItemsSup.getCompanyCode();
                        if (null != companyId && StringUtil.isNotEmpty(companyCode) && CollectionUtils.isNotEmpty(scoreItemsManList)) {
                            for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {
                                if (null != scoreItemsMan) {
                                    /**获取绩效评分项目评分人-供应商指标表**/
                                    List<PerfScoreItemManSupInd> scoreItemsManSupList = scoreItemsMan.getPerfScoreItemManSupIndList();
                                    if (CollectionUtils.isNotEmpty(scoreItemsManSupList)) {
                                        for (PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList) {
                                            if (null != scoreItemsManSup && null != scoreItemsManSup.getCompanyId() && companyId.longValue() ==
                                                    scoreItemsManSup.getCompanyId().longValue() && companyCode.equals(scoreItemsManSup.getCompanyCode())) {
                                                scoreItemsManSup.setScoreItemsSupId(scoreItemsSupId);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                try {
                    iPerfScoreItemsSupService.saveOrUpdateBatch(scoreItemsSupList);
                } catch (Exception e) {
                    log.error("新增/保存绩效评分项目供应商表时报错：", e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }

            /**新增/修改供绩效评分项目评分人信息*/
            if (CollectionUtils.isNotEmpty(scoreItemsManList)) {
                for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {
                    boolean isUpdateItemsMan = false;   //初始化是否修改评分人绩效评分项目为false
                    if (null != scoreItemsMan) {
                        Long scoreItemsManId = scoreItemsMan.getScoreItemsManId();
                        if (null == scoreItemsManId) {
                            scoreItemsManId = IdGenrator.generate();
                        } else {
                            isUpdateItemsMan = true;
                        }
                        scoreItemsMan.setScoreItemsManId(scoreItemsManId);
                        scoreItemsMan.setScoreItemsId(scoreItemsId);
                        boolean isUpdateScoreItemsMan = false;
                        try {
                            isUpdateScoreItemsMan = iPerfScoreItemsManService.saveOrUpdate(scoreItemsMan);
                        } catch (Exception e) {
                            log.error("新增/保存绩效评分项目评分人表时报错：", e);
                            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                        }
                        if (isUpdateScoreItemsMan) {
                            /**新增/删除绩效评分项目评分人-供应商指标表**/
                            //如果是修绩效评分项目评分人，则先删除之前所有的记录，再保存新记录
                            if (null != scoreItemsManId) {
                                PerfScoreItemManSupInd queryScoreItemsManSupInd = new PerfScoreItemManSupInd();
                                queryScoreItemsManSupInd.setScoreItemsManId(scoreItemsManId);
                                List<PerfScoreItemManSupInd> delScoreItemManSupIndList = iPerfScoreItemManSupIndService.list(new QueryWrapper<>(queryScoreItemsManSupInd));
                                List<Long> scoreItemsManSupIndIds = new ArrayList<>();    //需要删除的评分人绩效评分项目-供应商指标ID集合
                                if (CollectionUtils.isNotEmpty(delScoreItemManSupIndList)) {
                                    for (PerfScoreItemManSupInd manSupInd : delScoreItemManSupIndList) {
                                        if (null != manSupInd && null != manSupInd.getScoreItemManSupIndId()) {
                                            scoreItemsManSupIndIds.add(manSupInd.getScoreItemManSupIndId());
                                        }
                                    }
                                }
                                if (CollectionUtils.isNotEmpty(scoreItemsManSupIndIds)) {
                                    try {
                                        iPerfScoreItemManSupIndService.removeByIds(scoreItemsManSupIndIds);
                                    } catch (Exception e) {
                                        log.error("修改绩效评分项目评分人,删除绩效评分人评分项目-供应商指标表时报错：", e);
                                        throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                    }
                                }

                            }
                            List<PerfScoreItemManSupInd> scoreItemsManSupList = scoreItemsMan.getPerfScoreItemManSupIndList();
                            if (CollectionUtils.isNotEmpty(scoreItemsManSupList)) {
                                for (PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList) {
                                    if (null != scoreItemsManSup) {
                                        Long scoreItemManSupIndId = scoreItemsManSup.getScoreItemManSupIndId();
                                        Long scoreItemsSupId = scoreItemsManSup.getScoreItemsSupId();
                                        if (null == scoreItemManSupIndId) {
                                            scoreItemManSupIndId = IdGenrator.generate();
                                            //如果新增时启用状态为空，则设置为不启用
                                            if (StringUtils.isBlank(scoreItemsManSup.getEnableFlag())) {
                                                scoreItemsManSup.setEnableFlag(Enable.N.toString());
                                            }
                                        }

                                        if (null == scoreItemsSupId) {
                                            scoreItemsSupId = IdGenrator.generate();
                                        }
                                        scoreItemsManSup.setCreatedFullName(nickName);
                                        scoreItemsManSup.setScoreItemManSupIndId(scoreItemManSupIndId);
                                        scoreItemsManSup.setScoreItemsManId(scoreItemsManId);
                                        scoreItemsManSup.setScoreItemsId(scoreItemsId);
                                        scoreItemsManSup.setScoreItemsSupId(scoreItemsSupId);
                                    }
                                }

                                try {
                                    iPerfScoreItemManSupIndService.saveOrUpdateBatch(scoreItemsManSupList);
                                } catch (Exception e) {
                                    log.error("新增/修改绩效评分项目评分人-供应商表时报错：", e);
                                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                }
                            }

                            /**新增/保存绩效评分项目评分人-绩效指标表信息*/
/*                            List<PerfScoreItemsManIndicator> itemsManIndicatorList = scoreItemsMan.getPerfScoreItemsManIndicatorList();
                            if(CollectionUtils.isNotEmpty(itemsManIndicatorList)){
                                for(PerfScoreItemsManIndicator itemsManIndicator : itemsManIndicatorList){
                                    if(null != itemsManIndicator){
                                        Long itemsManIndicatorId = itemsManIndicator.getScoreItemsManIndicatorId();
                                        if(null == itemsManIndicatorId){
                                            itemsManIndicatorId = IdGenrator.generate();
                                            //如果新增时启用状态为空，则设置为不启用
                                            if(StringUtils.isBlank(itemsManIndicator.getEnableFlag())){
                                                itemsManIndicator.setEnableFlag(Enable.N.toString());
                                            }
                                        }
                                        itemsManIndicator.setCreatedFullName(nickName);
                                        itemsManIndicator.setScoreItemsManIndicatorId(itemsManIndicatorId);
                                        itemsManIndicator.setScoreItemsManId(scoreItemsManId);
                                        itemsManIndicator.setScoreItemsId(scoreItemsId);
                                    }
                                }

                                try{
                                    iPerfScoreItemsManIndicatorService.saveOrUpdateBatch(itemsManIndicatorList);
                                }catch (Exception e){
                                    log.error("新增/保存绩效评分项目评分人-绩效指标时报错：",e);
                                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                }
                            }*/
                        }
                    }
                }
            }
        }
        return String.valueOf(scoreItemsId);
    }

    /**
     * 新增或更新绩效评分项目和子表信息
     *
     * @param scoreItemsDTO
     * @throws BaseException
     * @author xiexh12@meicloud.com
     */
    @Override
    public Long saveOrUpdatePerfScoreItems(PerfScoreItemsDTO scoreItemsDTO) throws BaseException {
        Assert.notNull(scoreItemsDTO, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        // 校验校验区间月份是否重叠
        this.checkIfMonthOverLap(scoreItemsDTO);
        // 校验项目名称不能重复
        this.checkIfProjectNameDuplicate(scoreItemsDTO);
        // 校验评分供应商不能重复
        this.checkScoreItemsSup(scoreItemsDTO.getPerfScoreItemsSupList());
        // 校验评分人不能重复
        this.checkScoreItemsMan(scoreItemsDTO.getPerfScoreItemsManList());

        Long scoreItemsId = scoreItemsDTO.getScoreItemsId();
        PerfScoreItems perfScoreItems = new PerfScoreItems();
        BeanUtils.copyProperties(scoreItemsDTO, perfScoreItems);
        perfScoreItems.setScorePeopleCount(Long.valueOf(scoreItemsDTO.getPerfScoreItemsManList().size()));
        // 新增操作
        if (null == scoreItemsId) {
            scoreItemsId = IdGenrator.generate();
            perfScoreItems.setApproveStatus(ScoreItemsApproveStatusEnum.DRAFT.getValue());
        }

        //获取用户信息
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        String nickName = "";
        // 用户名称
        if (Objects.nonNull(user)) {
            nickName = user.getNickname();
        }
        /** 保存绩效评分项目主表信息**/
        boolean isUpdateScoreItems = false;
        try {
            perfScoreItems.setScoreItemsId(scoreItemsId);
            perfScoreItems.setCreatedFullName(nickName);
            isUpdateScoreItems = super.saveOrUpdate(perfScoreItems);
        } catch (Exception e) {
            log.error("新增/保存绩效评分项目主表时报错：", e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }

        if (isUpdateScoreItems) {
            /**获取供绩效评分项目评分人和子表信息*/
            List<PerfScoreItemsMan> scoreItemsManList = scoreItemsDTO.getPerfScoreItemsManList();

            /**新增/修改供绩效评分项目供应商信息*/
            List<PerfScoreItemsSup> scoreItemsSupList = scoreItemsDTO.getPerfScoreItemsSupList();
            if (CollectionUtils.isNotEmpty(scoreItemsSupList)) {
                for (PerfScoreItemsSup scoreItemsSup : scoreItemsSupList) {
                    if (null != scoreItemsSup) {
                        Long scoreItemsSupId = scoreItemsSup.getScoreItemsSupId();
                        if (null == scoreItemsSupId) {
                            scoreItemsSupId = IdGenrator.generate();
                        }
                        scoreItemsSup.setScoreItemsSupId(scoreItemsSupId);
                        scoreItemsSup.setScoreItemsId(scoreItemsId);

                        // 如果供应商ID和供应商Code相同，则把绩效评分供应商ID设置到绩效评分项目评分人-供应商指标表ID
                        Long companyId = scoreItemsSup.getCompanyId();
                        String companyCode = scoreItemsSup.getCompanyCode();
                        if (null != companyId && StringUtil.isNotEmpty(companyCode) && CollectionUtils.isNotEmpty(scoreItemsManList)) {
                            for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {
                                if (null != scoreItemsMan) {
                                    /**获取绩效评分项目评分人-供应商指标表**/
                                    List<PerfScoreItemManSupInd> scoreItemsManSupList = scoreItemsMan.getPerfScoreItemManSupIndList();
                                    if (CollectionUtils.isNotEmpty(scoreItemsManSupList)) {
                                        for (PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList) {
                                            if (null != scoreItemsManSup && null != scoreItemsManSup.getCompanyId() && companyId.longValue() ==
                                                    scoreItemsManSup.getCompanyId().longValue() && companyCode.equals(scoreItemsManSup.getCompanyCode())) {
                                                scoreItemsManSup.setScoreItemsSupId(scoreItemsSupId);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                try {
                    iPerfScoreItemsSupService.saveOrUpdateBatch(scoreItemsSupList);
                } catch (Exception e) {
                    log.error("新增/保存绩效评分项目供应商表时报错：", e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }

            /**新增/修改供绩效评分项目评分人信息*/
            if (CollectionUtils.isNotEmpty(scoreItemsManList)) {
                for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {
                    // 初始化是否修改评分人绩效评分项目为false
                    boolean isUpdateItemsMan = false;
                    if (null != scoreItemsMan) {
                        Long scoreItemsManId = scoreItemsMan.getScoreItemsManId();
                        if (Objects.isNull(scoreItemsManId)) {
                            scoreItemsManId = IdGenrator.generate();
                        } else {
                            isUpdateItemsMan = true;
                        }
                        scoreItemsMan.setScoreItemsManId(scoreItemsManId);
                        scoreItemsMan.setScoreItemsId(scoreItemsId);
                        boolean isUpdateScoreItemsMan = false;
                        try {
                            isUpdateScoreItemsMan = iPerfScoreItemsManService.saveOrUpdate(scoreItemsMan);
                        } catch (Exception e) {
                            log.error("新增/保存绩效评分项目评分人表时报错：", e);
                            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                        }
                        if (isUpdateScoreItemsMan) {
                            /**新增/删除绩效评分项目评分人-供应商指标表**/
                            //如果是修绩效评分项目评分人，则先删除之前所有的记录，再保存新记录
                            if (Objects.nonNull(scoreItemsManId)) {
                                PerfScoreItemManSupInd queryScoreItemsManSupInd = new PerfScoreItemManSupInd();
                                queryScoreItemsManSupInd.setScoreItemsManId(scoreItemsManId);
                                List<PerfScoreItemManSupInd> delScoreItemManSupIndList = iPerfScoreItemManSupIndService.list(new QueryWrapper<>(queryScoreItemsManSupInd));
                                // 需要删除的评分人绩效评分项目-供应商指标ID集合
                                List<Long> scoreItemsManSupIndIds = new ArrayList<>();
                                if (CollectionUtils.isNotEmpty(delScoreItemManSupIndList)) {
                                    for (PerfScoreItemManSupInd manSupInd : delScoreItemManSupIndList) {
                                        if (null != manSupInd && null != manSupInd.getScoreItemManSupIndId()) {
                                            scoreItemsManSupIndIds.add(manSupInd.getScoreItemManSupIndId());
                                        }
                                    }
                                }
                                if (CollectionUtils.isNotEmpty(scoreItemsManSupIndIds)) {
                                    try {
                                        iPerfScoreItemManSupIndService.removeByIds(scoreItemsManSupIndIds);
                                    } catch (Exception e) {
                                        log.error("修改绩效评分项目评分人,删除绩效评分人评分项目-供应商指标表时报错：", e);
                                        throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                    }
                                }

                            }
                            List<PerfScoreItemManSupInd> scoreItemsManSupList = scoreItemsMan.getPerfScoreItemManSupIndList();
                            if (CollectionUtils.isNotEmpty(scoreItemsManSupList)) {
                                for (PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList) {
                                    if (null != scoreItemsManSup) {
                                        Long scoreItemManSupIndId = scoreItemsManSup.getScoreItemManSupIndId();
                                        Long scoreItemsSupId = scoreItemsManSup.getScoreItemsSupId();
                                        if (null == scoreItemManSupIndId) {
                                            scoreItemManSupIndId = IdGenrator.generate();
                                            // 如果新增时启用状态为空，则设置为不启用
                                            if (StringUtils.isBlank(scoreItemsManSup.getEnableFlag())) {
                                                scoreItemsManSup.setEnableFlag(Enable.N.toString());
                                            }
                                        }

                                        if (null == scoreItemsSupId) {
                                            scoreItemsSupId = IdGenrator.generate();
                                        }
                                        scoreItemsManSup.setCreatedFullName(nickName);
                                        scoreItemsManSup.setScoreItemManSupIndId(scoreItemManSupIndId);
                                        scoreItemsManSup.setScoreItemsManId(scoreItemsManId);
                                        scoreItemsManSup.setScoreItemsId(scoreItemsId);
                                        scoreItemsManSup.setScoreItemsSupId(scoreItemsSupId);
                                    }
                                }

                                try {
                                    iPerfScoreItemManSupIndService.saveOrUpdateBatch(scoreItemsManSupList);
                                } catch (Exception e) {
                                    log.error("新增/修改绩效评分项目评分人-供应商表时报错：", e);
                                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
        return scoreItemsId;
    }

    /**
     * 通知评分人
     *
     * @param scoreItems
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notifyScorers(PerfScoreItems scoreItems) {
        Assert.notNull(scoreItems, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        String result = ResultCode.OPERATION_FAILED.getMessage();
        // 获取项目头id
        Long scoreItemsId = scoreItems.getScoreItemsId();
        Assert.notNull(scoreItemsId, "绩效项目头id不能为空。");
        String projectStatus = scoreItems.getProjectStatus();
        PerfScoreItems oldScoreItems = this.getById(scoreItemsId);
        if (Objects.nonNull(oldScoreItems)) {
            String oldProjectStatus = oldScoreItems.getProjectStatus();
            // 通知评分人操作
            if (ScoreItemsProjectStatusEnum.SCORE_NOTIFIED.getValue().equals(projectStatus)) {
                if (!ScoreItemsProjectStatusEnum.SCORE_DRAFT.getValue().equals(oldProjectStatus)) {
                    throw new BaseException(LocaleHandler.getLocaleMsg(ScoreItemsConst.SCORE_ITEMS_NOTIFY_SCORERS));
                }
            }
            int isUpdateCount = 0;
            // 更新绩效评分项目数据
            try {
                UpdateWrapper<PerfScoreItems> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SCORE_ITEMS_ID", scoreItems.getScoreItemsId())
                        .set("PROJECT_STATUS",scoreItems.getProjectStatus());
                this.update(updateWrapper);
            } catch (Exception e) {
                log.error("绩效评分项目-通知评分人操作时报错: ", e);
                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
            }
            // 判断能否保存/修改绩效评分项目，能返回空，反之Assert提示错误信息
            //this.checkIsSaveOrUpdateScoreItems(scoreItemsId, null);
            // 通知评分人前校验 所有指标必须分配到评分人（一项任务可以分给多个评分人）
            this.checkIfAllIndicatorsAllocateNotifiers(scoreItemsId);
            // 把数据插入评分人评分表
            result = iScoreManScoringV1Service.saveScoreManScoringByScoreItemsId(scoreItemsId);
        }
        return result;
    }

    /**
     * 通知评分人前校验 所有指标必须分配到评分人
     *
     * @param scoreItemsId
     */
    public void checkIfAllIndicatorsAllocateNotifiers(Long scoreItemsId) {

        PerfScoreItems scoreItems = this.getById(scoreItemsId);
        Long templateHeadId = scoreItems.getTemplateHeadId();

        // 获取评分供应商集合
        List<PerfScoreItemsSup> scoreItemsSupList = iPerfScoreItemsSupService.list(Wrappers.lambdaQuery(PerfScoreItemsSup.class)
                .eq(PerfScoreItemsSup::getScoreItemsId, scoreItemsId));
        // 校验评分供应商不能为空
        if (CollectionUtils.isEmpty(scoreItemsSupList)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("评分供应商不能为空，请选择后重试。"));
        }

        // 获取评分人集合
        List<PerfScoreItemsMan> scoreItemsManList = iPerfScoreItemsManService.list(Wrappers.lambdaQuery(PerfScoreItemsMan.class)
                .eq(PerfScoreItemsMan::getScoreItemsId, scoreItemsId));
        // 校验评分人不能为空
        if (CollectionUtils.isEmpty(scoreItemsManList)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("评分人不能为空，请选择后重试。"));
        }

        // 获取评分人分配的供应商指标集合
        List<PerfScoreItemManSupInd> scoreItemsSupIndicatorList = iPerfScoreItemManSupIndService.list(Wrappers.lambdaQuery(PerfScoreItemManSupInd.class)
                .eq(PerfScoreItemManSupInd::getScoreItemsId, scoreItemsId));
        if (CollectionUtils.isEmpty(scoreItemsSupIndicatorList)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("评分人未分配任务，请分配任务后重试。"));
        }

        List<PerfTemplateLine> perfTemplateLines = iPerfTemplateHeaderService.listTemplateLinesByTemplateHeaderId(templateHeadId);

        // 根据指标名称（即模板行），查询是否都进行了任务分配
        Map<String, String> indicatorDimensionMap = new HashMap<>();
        indicatorDimensionMap.put("QUALITY", "品质");
        indicatorDimensionMap.put("SERVICE", "服务");
        indicatorDimensionMap.put("DELIVER", "交付");
        indicatorDimensionMap.put("TECHNOLOGY", "技术");

        perfTemplateLines.forEach(x -> {
            String indicatorDimension = x.getIndicatorDimension();
            String indicatorName = x.getIndicatorName();
            QueryWrapper<PerfScoreItemManSupInd> countWrapper = new QueryWrapper<>();
            countWrapper.eq("SCORE_ITEMS_ID", scoreItemsId);
            countWrapper.eq("INDICATOR_DIMENSION", indicatorDimension);
            countWrapper.eq("INDICATOR_NAME", indicatorName);
            if (iPerfScoreItemManSupIndService.count(countWrapper) == 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("当前项目存在未分配的任务，指标维度：[").append(indicatorDimensionMap.get(indicatorDimension))
                        .append("]，指标名称：[").append(indicatorName).append("]未分配，请分配任务后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        });
    }

    /**
     * 绩效项目计算评分
     *
     * @param scoreItems
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateScoreItems(PerfScoreItems scoreItems) {
        // 计算评分前数据检验
        Assert.notNull(scoreItems, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        String result = ResultCode.OPERATION_FAILED.getMessage();
        Long scoreItemsId = scoreItems.getScoreItemsId();
        Assert.notNull(scoreItemsId, "绩效项目id不能为空");

        // 废弃、计算、流程提交、发布操作，不能进行计算操作
        Map<String, String> connotCalculateStatusMap = new HashMap<>();
        connotCalculateStatusMap.put(ScoreItemsProjectStatusEnum.OBSOLETE.getValue(), ScoreItemsProjectStatusEnum.OBSOLETE.getName());
        connotCalculateStatusMap.put(ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getValue(), ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getName());
        connotCalculateStatusMap.put(ScoreItemsProjectStatusEnum.RESULT_NO_PUBLISHED.getValue(), ScoreItemsProjectStatusEnum.RESULT_NO_PUBLISHED.getName());
        connotCalculateStatusMap.put(ScoreItemsProjectStatusEnum.RESULT_PUBLISHED.getValue(), ScoreItemsProjectStatusEnum.RESULT_PUBLISHED.getName());

        String projectStatus = scoreItems.getProjectStatus();
        if (!connotCalculateStatusMap.containsKey(projectStatus)) {
            throw new BaseException(ScoreItemsConst.SCORE_ITEMS_CAN_NOT_OPERATION);
        }

        LocalDate nowLocalDate = null;
        try {
            //获取当前时间：格式yyyy-MM-dd
            Date nowDate = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_10);
            nowLocalDate = DateUtil.dateToLocalDate(nowDate);
        } catch (Exception e) {
            log.error("修改绩效评分项目主表信息-获取当前时间时报错: ", e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }

        PerfScoreItems oldScoreItems = new PerfScoreItems();
        // 根据ID获取绩效评分项目信息
        oldScoreItems = this.getById(scoreItemsId);

        if (Objects.nonNull(oldScoreItems)) {
            String oldProjectStatus = oldScoreItems.getProjectStatus();
            LocalDate scoreStartTime = oldScoreItems.getScoreStartTime();
//            LocalDate scoreEndTime = oldScoreItems.getScoreEndTime();

            if (ScoreItemsProjectStatusEnum.OBSOLETE.getValue().equals(projectStatus) && ScoreItemsProjectStatusEnum.OBSOLETE.getValue().equals(oldProjectStatus)) {
                // 废弃操作(项目状态已是已废弃，不需要重新操作)
                Assert.isTrue(false, ScoreItemsConst.SCORE_ITEMS_ABANDON);
                Assert.isTrue(ScoreItemsProjectStatusEnum.SCORE_DRAFT.getValue().equals(projectStatus), ScoreItemsConst.IS_NOT_OBSOLETE);
            } else if (ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getValue().equals(projectStatus)) {
                // 计算操作(项目状态只能为通知评分人才能进行计算)
                if (!ScoreItemsProjectStatusEnum.SCORE_NOTIFIED.getValue().equals(oldProjectStatus)) {
                    Assert.isTrue(false, "项目状态只能为通知评分人才能进行计算。");
                } else if (nowLocalDate.isBefore(scoreStartTime)) {
                    Assert.isTrue(false, ScoreItemsConst.SCORE_ITEMS_CALCULATE_START_TIME);
                }/*else if(nowLocalDate.isAfter(scoreEndTime)){     //评分结束时间不用判断
                    Assert.isTrue(false, ScoreItemsConst.SCORE_ITEMS_CALCULATE_END_TIME);
                }*/

                // 将多人评分的数据转换成另一张表的数据
                convertMutiScoringToSingleScoringData(scoreItemsId);

                // 评分结果计算
                boolean isCalculated = iPerfOverallScoreService.generateScoreInfo(scoreItemsId);
                // 修改指标维度综合得分主表状态为已发布 并赋值绩效排名总数
                iPerfOverallScoreService.publishScoreItemsUpdateStatus(scoreItemsId);
                if (!isCalculated) {
                    throw new BaseException("评分结果计算失败。");
                }

                // 根据绩效评分项目ID修改评分人绩效评分的状态为已计算评分
                iPerfScoreManScoringService.updateScoreManScoringStatus(scoreItemsId, ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getValue());
                // 锁定绩效项目评分页面的 是否已截止评分标志位
                iScoreManScoringV1Service.lockIfEndScored(scoreItemsId);
            } else if (ScoreItemsProjectStatusEnum.RESULT_NO_PUBLISHED.getValue().equals(projectStatus)) {
                //流程提交操作(项目状态只能为已计算得分)
                if (!ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getValue().equals(oldProjectStatus)) {
                    Assert.isTrue(false, ScoreItemsConst.SCORE_ITEMS_SUBMIT_PROCESS);
                }
                //修改绩效项目审批状态改为“已批准”
                scoreItems.setApproveStatus(ScoreItemsApproveStatusEnum.APPROVED.getValue());
            } else if (ScoreItemsProjectStatusEnum.RESULT_PUBLISHED.getValue().equals(projectStatus)) {    //发布操作
                if (!ScoreItemsProjectStatusEnum.RESULT_NO_PUBLISHED.getValue().equals(oldProjectStatus)) {
                    Assert.isTrue(false, ScoreItemsConst.SCORE_ITEMS_PUBLISH);
                }

                // 修改指标维度综合得分主表状态为已发布
                iPerfOverallScoreService.publishScoreItemsUpdateStatus(scoreItemsId);
            }
        }
        try {
            int isUpdateCount = getBaseMapper().updateById(scoreItems);
            if (0 < isUpdateCount) {
                result = ResultCode.SUCCESS.getMessage();
            }
        } catch (Exception e) {
            log.error("修改绩效评分项目主表信息时报错: ", e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    @Override
    public List<PerfScoreItems> findCalculatedScoreItemsList() throws BaseException {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            List<String> projectStatusList = new ArrayList<>();
            projectStatusList.add(ScoreItemsProjectStatusEnum.SCORE_CALCULATED.getValue());
            projectStatusList.add(ScoreItemsProjectStatusEnum.RESULT_NO_PUBLISHED.getValue());
            projectStatusList.add(ScoreItemsProjectStatusEnum.RESULT_PUBLISHED.getValue());
            queryWrapper.in("PROJECT_STATUS", projectStatusList);
            return super.list(queryWrapper);
        } catch (Exception e) {
            log.error("获取已经计算的之后的评分绩效项目集合");
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delScoreItemsAndSon(Long scoreItemsId) throws BaseException {
        Assert.notNull(scoreItemsId, "id不能为空");
        String result = ResultCode.OPERATION_FAILED.getMessage();

        /**根据绩效模型主表ID获取子表相关记录*/
        PerfScoreItems scoreItems = getBaseMapper().selectById(scoreItemsId);
        if (null != scoreItems) {

            /**根据绩效项目主表ID获取绩效项目供应商表ID集合*/
            List<Long> delScoreItemsSupIds = new ArrayList<>(); //需要删除的绩效项目供应商ID集合
            PerfScoreItemsSup queryScoreItemsSup = new PerfScoreItemsSup();
            queryScoreItemsSup.setScoreItemsId(scoreItemsId);
            List<PerfScoreItemsSup> scoreItemsSupList = iPerfScoreItemsSupService.list(new QueryWrapper<>(queryScoreItemsSup));
            if (CollectionUtils.isNotEmpty(scoreItemsSupList)) {
                for (PerfScoreItemsSup scoreItemsSup : scoreItemsSupList) {
                    if (null != scoreItemsSup && null != scoreItemsSup.getScoreItemsSupId()) {
                        delScoreItemsSupIds.add(scoreItemsSup.getScoreItemsSupId());
                    }
                }
            }

            /**根据绩效项目主表ID获取绩效项目评分人表ID集合*/
            List<Long> delScoreItemsManIds = new ArrayList<>(); //需要删除的绩效项目评分人ID集合
            List<Long> delScoreItemsManSupIds = new ArrayList<>(); //需要删除的绩效项目评分人-供应商ID集合
            List<Long> delScoreItemsManIndicatorIds = new ArrayList<>(); //需要删除的绩效项目评分人-评分人ID集合
            PerfScoreItemsMan queryScoreItemsMan = new PerfScoreItemsMan();
            queryScoreItemsMan.setScoreItemsId(scoreItemsId);
            List<PerfScoreItemsMan> scoreItemsManList = iPerfScoreItemsManService.list(new QueryWrapper<>(queryScoreItemsMan));
            if (CollectionUtils.isNotEmpty(scoreItemsManList)) {
                for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {
                    if (null != scoreItemsMan && null != scoreItemsMan.getScoreItemsManId()) {
                        Long scoreItemsManId = scoreItemsMan.getScoreItemsManId();
                        delScoreItemsManIds.add(scoreItemsManId);

                        PerfScoreItemManSupInd queryScoreItemsManSup = new PerfScoreItemManSupInd();
                        queryScoreItemsManSup.setScoreItemsManId(scoreItemsManId);
                        List<PerfScoreItemManSupInd> scoreItemsManSupList = iPerfScoreItemManSupIndService.list(new QueryWrapper<>(queryScoreItemsManSup));
                        if (CollectionUtils.isNotEmpty(scoreItemsManSupList)) {
                            for (PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList) {
                                if (null != scoreItemsManSup && null != scoreItemsManSup.getScoreItemManSupIndId()) {
                                    delScoreItemsManSupIds.add(scoreItemsManSup.getScoreItemManSupIndId());
                                }
                            }
                        }

/*                        PerfScoreItemsManIndicator queryScoreItemsManIndicator = new PerfScoreItemsManIndicator();
                        queryScoreItemsManIndicator.setScoreItemsManId(scoreItemsManId);
                        List<PerfScoreItemsManIndicator> itemsManIndicatorList = iPerfScoreItemsManIndicatorService.list(new QueryWrapper<>(queryScoreItemsManIndicator));
                        if(CollectionUtils.isNotEmpty(itemsManIndicatorList)){
                            for(PerfScoreItemsManIndicator itemsManIndicator : itemsManIndicatorList){
                                if(null != itemsManIndicator && null != itemsManIndicator.getScoreItemsManIndicatorId()){
                                    delScoreItemsManIndicatorIds.add(itemsManIndicator.getScoreItemsManIndicatorId());
                                }
                            }
                        }*/

                    }
                }
            }

/*            if(CollectionUtils.isNotEmpty(delScoreItemsManIndicatorIds)){
                try {
                    iPerfScoreItemsManIndicatorService.removeByIds(delScoreItemsManIndicatorIds);
                }catch (Exception e){
                    log.error(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_INDICATOR_ERROR);
                    throw new BaseException(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_INDICATOR_ERROR);
                }
            }*/
            if (CollectionUtils.isNotEmpty(delScoreItemsManSupIds)) {
                try {
                    iPerfScoreItemManSupIndService.removeByIds(delScoreItemsManSupIds);
                } catch (Exception e) {
                    log.error(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_SUP_ERROR);
                    throw new BaseException(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_SUP_ERROR);
                }
            }
            if (CollectionUtils.isNotEmpty(delScoreItemsManIds)) {
                try {
                    iPerfScoreItemsManService.removeByIds(delScoreItemsManIds);
                } catch (Exception e) {
                    log.error(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_ERROR);
                    throw new BaseException(ScoreItemsConst.DELETE_SCORE_ITEMS_MAN_ERROR);
                }
            }
            if (CollectionUtils.isNotEmpty(delScoreItemsSupIds)) {
                try {
                    iPerfScoreItemsSupService.removeByIds(delScoreItemsSupIds);
                } catch (Exception e) {
                    log.error(ScoreItemsConst.DELETE_SCORE_ITEMS_SUP_ERROR);
                    throw new BaseException(ScoreItemsConst.DELETE_SCORE_ITEMS_SUP_ERROR);
                }
            }
            if (null != scoreItemsId) {
                try {
                    getBaseMapper().deleteById(scoreItemsId);
                } catch (Exception e) {
                    log.error(ScoreItemsConst.DELETE_SCORE_ITEMS_ERROR);
                    throw new BaseException(ScoreItemsConst.DELETE_SCORE_ITEMS_ERROR);
                }
            }
        }
        result = ResultCode.SUCCESS.getMessage();
        return result;
    }

    /**
     * 将多人评分的数据转换到另一张表的数据
     *
     * @param scoreItemsId
     */
    public void convertMutiScoringToSingleScoringData(Long scoreItemsId) {
        // 获取多人评分的数据集合
        List<ScoreManScoringV1> scoreManScoringV1List = iScoreManScoringV1Service.list(Wrappers.lambdaQuery(ScoreManScoringV1.class)
                .eq(ScoreManScoringV1::getScoreItemsId, scoreItemsId));
        Map<String, List<ScoreManScoringV1>> scoreManScoringMap = scoreManScoringV1List.stream().collect(Collectors.groupingBy(ScoreManScoringV1::getUniqueKey));
        List<PerfScoreManScoring> saveOrUpdateList = new ArrayList<>();
        scoreManScoringMap.forEach((k, v) -> {
            Assert.isTrue(CollectionUtils.isNotEmpty(v), "当前数据无评分人数据。");
            PerfScoreManScoring scoreManScoring = new PerfScoreManScoring();
            ScoreManScoringV1 v1 = v.get(0);
            // 复制属性
            BeanUtils.copyProperties(v1, scoreManScoring);
            // 生成主键id
            Long id = IdGenrator.generate();
            scoreManScoring.setScoreManScoringId(id);

            // 多人评分，计算平均值
            // 先过滤掉pefScore为null的数据，只计算已经评了分的数据的平均值
            List<ScoreManScoringV1> filterdV1 = v.stream().filter(x -> Objects.nonNull(x.getPefScore())).collect(Collectors.toList());

            // 如果所有评分人（可能多个）都未评分，即pefScore为null，评分设置为0
            if (v.stream().allMatch(e -> Objects.isNull(e.getPefScore()))) {
                scoreManScoring.setPefScore(new BigDecimal(0));
                scoreManScoring.setScore(new BigDecimal(0));
            } else if (filterdV1.size() > 1) {
                int n = filterdV1.size();
                BigDecimal totalScore = filterdV1.stream().map(ScoreManScoringV1::getPefScore).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal averageScore = totalScore.divide(BigDecimal.valueOf(n), 1, BigDecimal.ROUND_HALF_UP);
                scoreManScoring.setPefScore(averageScore);
                scoreManScoring.setScore(averageScore);
            } else if (filterdV1.size() == 1) {
                ScoreManScoringV1 v2 = filterdV1.get(0);
                BeanUtils.copyProperties(v2, scoreManScoring);
                // 生成主键id
                scoreManScoring.setScoreManScoringId(IdGenrator.generate());
            }

            saveOrUpdateList.add(scoreManScoring);
        });
        if (CollectionUtils.isNotEmpty(saveOrUpdateList)) {
            iPerfScoreManScoringService.saveBatch(saveOrUpdateList);
        }
    }

    /**
     * 评分人打分之后，更新绩效项目信息
     *
     * @param scoreManScoringsList
     * @return
     * @throws BaseException
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public String updateScoreItemsScorePeople(List<PerfScoreManScoring> scoreManScoringsList) throws BaseException {
        String result = ResultCode.OPERATION_FAILED.getMessage();
        // 如果项目 某人评分完毕，则修改已评分人数+1
        List<Long> scoreItemsIds = scoreManScoringsList.stream().map(PerfScoreManScoring::getScoreItemsId).filter(x -> Objects.nonNull(x)).collect(Collectors.toList());

        Map<Long, List<PerfScoreManScoring>> perfScoreManScoringMap = CollectionUtils.isEmpty(scoreItemsIds) ? Collections.emptyMap() :
                iPerfScoreManScoringService.list(Wrappers.lambdaQuery(PerfScoreManScoring.class).in(PerfScoreManScoring::getScoreItemsId, scoreItemsIds))
                        .stream().collect(Collectors.groupingBy(PerfScoreManScoring::getScoreItemsId));

        if (MapUtils.isNotEmpty(perfScoreManScoringMap)) {
            perfScoreManScoringMap.forEach((scoreItemsId, scoreManScoringList) -> {

                PerfScoreItems updateScoreItems = this.getById(scoreItemsId);
                // 根据评分人分组
                Map<String, List<PerfScoreManScoring>> scoreManScoringByUserMap = scoreManScoringList.stream()
                        .collect(Collectors.groupingBy(PerfScoreManScoring::getScoreUserName));
                // 已评分
                AtomicInteger scoredCount = new AtomicInteger(0);
                if (MapUtils.isNotEmpty(scoreManScoringByUserMap)) {
                    // 对于某一个项目，当前评分人对于所有指标，品类都已经评分，则计入评分回应
                    scoreManScoringByUserMap.forEach((userName, scoringList) -> {
                        if (scoringList.stream().allMatch(e -> Objects.equals(YesOrNo.YES.getValue(), e.getIfScored()))) {
                            scoredCount.addAndGet(1);
                        }
                    });
                }

                // 获取当前评分项目的评分人集合
                List<PerfScoreItemsMan> scoreItemsManList = CollectionUtils.isEmpty(scoreItemsIds) ? Collections.emptyList() :
                        iPerfScoreItemsManService.list(Wrappers.lambdaQuery(PerfScoreItemsMan.class).eq(PerfScoreItemsMan::getScoreItemsId, scoreItemsId));
                // 已评分人数不能大于总评分人数
                int count = scoredCount.get();
                if (count <= scoreItemsManList.size()) {
                    updateScoreItems.setScorePeople(Long.valueOf(count));
                    this.updateById(updateScoreItems);
                }

            });
        }

        result = ResultCode.SUCCESS.getMessage();
        return result;
    }

    /**
     * 评分人打分之后，更新绩效项目信息
     *
     * @param scoreManScoringV1List
     * @return
     * @throws BaseException
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public String updateScoreItemsAndScorePeople(List<ScoreManScoringV1> scoreManScoringV1List) throws BaseException {
        String result = ResultCode.OPERATION_FAILED.getMessage();
        // 如果项目 某人评分完毕，则修改已评分人数+1
        List<Long> scoreItemsIds = scoreManScoringV1List.stream().map(ScoreManScoringV1::getScoreItemsId).filter(x -> Objects.nonNull(x)).collect(Collectors.toList());

        Map<Long, List<ScoreManScoringV1>> perfScoreManScoringMap = CollectionUtils.isEmpty(scoreItemsIds) ? Collections.emptyMap() :
                iScoreManScoringV1Service.list(Wrappers.lambdaQuery(ScoreManScoringV1.class).in(ScoreManScoringV1::getScoreItemsId, scoreItemsIds))
                        .stream().collect(Collectors.groupingBy(ScoreManScoringV1::getScoreItemsId));

        if (MapUtils.isNotEmpty(perfScoreManScoringMap)) {
            perfScoreManScoringMap.forEach((scoreItemsId, scoreManScoringList) -> {

                PerfScoreItems updateScoreItems = this.getById(scoreItemsId);
                // 根据评分人分组
                Map<String, List<ScoreManScoringV1>> scoreManScoringByUserMap = scoreManScoringList.stream()
                        .collect(Collectors.groupingBy(ScoreManScoringV1::getScoreUserName));
                // 已评分
                AtomicInteger scoredCount = new AtomicInteger(0);
                if (MapUtils.isNotEmpty(scoreManScoringByUserMap)) {
                    // 对于某一个项目，当前评分人对于所有指标，品类都已经评分，则计入评分回应
                    scoreManScoringByUserMap.forEach((userName, scoringList) -> {
                        if (scoringList.stream().allMatch(e -> Objects.equals(YesOrNo.YES.getValue(), e.getIfScored()))) {
                            scoredCount.addAndGet(1);
                        }
                    });
                }

                // 获取当前评分项目的评分人集合
                List<PerfScoreItemsMan> scoreItemsManList = CollectionUtils.isEmpty(scoreItemsIds) ? Collections.emptyList() :
                        iPerfScoreItemsManService.list(Wrappers.lambdaQuery(PerfScoreItemsMan.class).eq(PerfScoreItemsMan::getScoreItemsId, scoreItemsId));
                // 已评分人数不能大于总评分人数
                int count = scoredCount.get();
                if (count <= scoreItemsManList.size()) {
                    updateScoreItems.setScorePeople(Long.valueOf(count));
                    this.updateById(updateScoreItems);
                }

            });
        }

        result = ResultCode.SUCCESS.getMessage();
        return result;
    }

    @Override
    public List<PerfScoreItemSupIndDTO> getScoreItemsSupIndicatorList(List<PerfScoreItemsSup> scoreItemsSupList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsSupList) && null != scoreItemsSupList.get(0),
                ScoreItemsConst.PER_SCORE_ITEMS_SUP_NOT_NULL);
        Long templateHeadId = scoreItemsSupList.get(0).getTemplateHeadId();
        List<PerfTemplateLine> templateLineList = this.getPerfTemplateLineByHeaderId(templateHeadId);   //根据绩效模型头ID获取绩效指标信息集合

        List<PerfScoreItemSupIndDTO> scoreItemsSupIndicatorList = new ArrayList<>();
        for (PerfScoreItemsSup scoreItemsSup : scoreItemsSupList) {
            if (null != scoreItemsSup) {
                if (CollectionUtils.isNotEmpty(templateLineList)) {
                    for (PerfTemplateLine templateLine : templateLineList) {
                        if (null != templateLine) {
                            PerfScoreItemSupIndDTO scoreItemsSupIndicatorDTO = new PerfScoreItemSupIndDTO();
                            BeanUtils.copyProperties(scoreItemsSup, scoreItemsSupIndicatorDTO);
                            scoreItemsSupIndicatorDTO.setTemplateLineId(templateLine.getTemplateLineId());
                            scoreItemsSupIndicatorDTO.setDimensionWeight(templateLine.getDimensionWeight());
                            scoreItemsSupIndicatorDTO.setEvaluation(templateLine.getEvaluation());
                            scoreItemsSupIndicatorDTO.setIndicatorDimension(templateLine.getIndicatorDimension());
                            scoreItemsSupIndicatorDTO.setIndicatorLineType(templateLine.getIndicatorLineType());
                            scoreItemsSupIndicatorDTO.setIndicatorLogic(templateLine.getIndicatorLogic());
                            scoreItemsSupIndicatorDTO.setIndicatorName(templateLine.getIndicatorName());
                            scoreItemsSupIndicatorDTO.setIndicatorType(templateLine.getIndicatorType());
                            scoreItemsSupIndicatorDTO.setMarkLimit(templateLine.getMarkLimit());
                            scoreItemsSupIndicatorDTO.setQuoteMode(templateLine.getQuoteMode());
                            scoreItemsSupIndicatorDTO.setTemplateDimWeightId(templateLine.getTemplateDimWeightId());
                            scoreItemsSupIndicatorList.add(scoreItemsSupIndicatorDTO);
                        }
                    }
                }
            }
        }
        return scoreItemsSupIndicatorList;
    }

    /**
     * 校验区间月份是否重叠
     *
     * @param scoreItemsDTO
     */
    public void checkIfMonthOverLap(PerfScoreItemsDTO scoreItemsDTO) {
        // 获取输入月份的开始和结束月份
        String startMonth = scoreItemsDTO.getPerStartMonth().toString();
        String endMonth = scoreItemsDTO.getPerEndMonth().toString();
        if (startMonth.compareTo(endMonth) > 0) {
            throw new BaseException(LocaleHandler.getLocaleMsg("绩效开始月份不能大于结束月份，请调整后重试。"));
        }
        Long scoreItemsId = scoreItemsDTO.getScoreItemsId();
        // 获取模板id
        Assert.notNull(scoreItemsDTO.getTemplateHeadId(), "绩效模型不能为空，请选择后重试。");
        Long templateHeadId = scoreItemsDTO.getTemplateHeadId();
        List<PerfScoreItems> perfScoreItemsList = this.list(Wrappers.lambdaQuery(PerfScoreItems.class)
                .eq(PerfScoreItems::getTemplateHeadId, templateHeadId));
        if (Objects.nonNull(scoreItemsId)) {
            perfScoreItemsList = perfScoreItemsList.stream().filter(x -> !Objects.equals(x.getScoreItemsId(), scoreItemsId)).collect(Collectors.toList());
        }
        for (PerfScoreItems perfScoreItems : perfScoreItemsList) {
            LocalDate perStartMonth = perfScoreItems.getPerStartMonth();
            LocalDate perEndMonth = perfScoreItems.getPerEndMonth();
            if (Objects.isNull(perStartMonth) || Objects.isNull(perEndMonth)) {
                continue;
            }
            String dbPerStartMonthString = perStartMonth.toString();
            String dbPerEndMonthString = perEndMonth.toString();
            // 左边开始节点在右边区间
            boolean case1 = (startMonth.compareTo(dbPerStartMonthString) > 0) && (startMonth.compareTo(dbPerEndMonthString) < 0);
            // 右边开始节点在左边区间
            boolean case2 = (dbPerStartMonthString.compareTo(startMonth) > 0) && (dbPerStartMonthString.compareTo(endMonth) < 0);
            // 开始节点相等
            boolean case3 = (startMonth.compareTo(dbPerStartMonthString) == 0);
            // 结束节点相等
            boolean case4 = (endMonth.compareTo(dbPerEndMonthString) == 0);
            if (case1 || case2 || case3 || case4) {
                int dbYear1 = perStartMonth.getYear();
                int dbMonth1 = perStartMonth.getMonthValue();
                String dbStartMonthString = dbYear1 + "-" + dbMonth1;
                int dbYear2 = perEndMonth.getYear();
                int dbMonth2 = perEndMonth.getMonthValue();
                String dbEndMonthString = dbYear2 + "-" + dbMonth2;
                StringBuffer sb = new StringBuffer();
                sb.append("绩效月份重叠，重叠项目：[").append(perfScoreItems.getProjectName()).append("]，已有月份区间：[")
                        .append(dbStartMonthString).append("至").append(dbEndMonthString).append("]，请调整后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 校验项目名称不能重复
     *
     * @param scoreItemsDTO
     */
    public void checkIfProjectNameDuplicate(PerfScoreItemsDTO scoreItemsDTO) {
        Long scoreItemsId = scoreItemsDTO.getScoreItemsId();
        String projectName = scoreItemsDTO.getProjectName();
        List<PerfScoreItems> perfScoreItemsList = new ArrayList<>();
        // 还未经暂存，没有生成头表主键id
        if (Objects.isNull(scoreItemsId)) {
            perfScoreItemsList = this.list(Wrappers.lambdaQuery(PerfScoreItems.class)
                    .eq(PerfScoreItems::getProjectName, projectName));
            if (perfScoreItemsList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                String existProjectName = perfScoreItemsList.get(0).getProjectName();
                sb.append("绩效项目名称重复，已存在项目：[").append(existProjectName).append("]，请修改项目名称后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
        // 经过了暂存，或者是经过项目复制产生的项目
        else {
            perfScoreItemsList = this.list(Wrappers.lambdaQuery(PerfScoreItems.class)
                    .ne(PerfScoreItems::getScoreItemsId, scoreItemsId)
                    .eq(PerfScoreItems::getProjectName, projectName));
            if (perfScoreItemsList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                String existProjectName = perfScoreItemsList.get(0).getProjectName();
                sb.append("绩效项目名称重复，已存在项目：[").append(existProjectName).append("]，请修改项目名称后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 校验评分供应商不能重复
     *
     * @param scoreItemsSupList
     */
    public void checkScoreItemsSup(List<PerfScoreItemsSup> scoreItemsSupList) {
        if (CollectionUtils.isEmpty(scoreItemsSupList)) {
            return;
        }
        Map<Long, List<PerfScoreItemsSup>> scoreItemsSupMap = scoreItemsSupList.stream().collect(Collectors.groupingBy(PerfScoreItemsSup::getCompanyId));
        scoreItemsSupMap.forEach((k, v) -> {
            if (v.size() > 1) {
                StringBuffer sb = new StringBuffer();
                sb.append("评分供应商不能重复，供应商名称：[").append(v.get(0).getCompanyName()).append("]，请检查后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        });
    }

    /**
     * 校验评分人不能重复
     *
     * @param perfScoreItemsManList
     */
    public void checkScoreItemsMan(List<PerfScoreItemsMan> perfScoreItemsManList) {
        if (CollectionUtils.isEmpty(perfScoreItemsManList)) {
            return;
        }
        Map<String, List<PerfScoreItemsMan>> scoreItemsManMap = perfScoreItemsManList.stream().collect(Collectors.groupingBy(PerfScoreItemsMan::getScoreUserName));
        scoreItemsManMap.forEach((k, v) -> {
            if (v.size() > 1) {
                StringBuffer sb = new StringBuffer();
                sb.append("评分人不能重复，评分人账号：[").append(v.get(0).getScoreUserName()).append("]，请检查后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        });
    }

    @Override
    public String checkIsSaveOrUpdateScoreItems(Long scoreItemsId, PerfScoreItemsDTO scoreItemsDTO) {
        String result = "";
//        if(null == scoreItemsDTO && null != scoreItemsId) {
//            PerfScoreItems queryScoreItems = new PerfScoreItems();
//            queryScoreItems.setScoreItemsId(scoreItemsId);
//            scoreItemsDTO = this.findScoreItemsAndSonList(queryScoreItems);
//        }
//        Assert.isTrue(null != scoreItemsDTO && CollectionUtils.isNotEmpty(scoreItemsDTO.getPerfScoreItemsManList()),
//                ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN);
//
//        boolean isRepeat = false;   //是否重复指标权限
//        if(null != scoreItemsDTO && CollectionUtils.isNotEmpty(scoreItemsDTO.getPerfScoreItemsManList())) {
//            List<PerfScoreItemsMan> scoreItemsManList = scoreItemsDTO.getPerfScoreItemsManList();
//            Assert.isTrue(null != scoreItemsManList.get(0), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN);
//
//            /**1个评分人的供应商权限和指标权限不为空和重复的判断*/
//            if (1 == scoreItemsManList.size() && null != scoreItemsManList.get(0)) {  //绩效评分人只有一个
//                PerfScoreItemsMan scoreItemsManOne = scoreItemsManList.get(0);
//                if (null != scoreItemsManOne) {
//                    List<PerfScoreItemsManSup> scoreItemsManSupList = scoreItemsManOne.getPerfScoreItemsManSupList();
//                    Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsManSupList) || null != scoreItemsManSupList.get(0), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN);
//                    List<PerfScoreItemsManIndicator> scoreItemsManIndicatorList = scoreItemsManOne.getPerfScoreItemsManIndicatorList();
//                    Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsManIndicatorList) || null != scoreItemsManIndicatorList.get(0), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                    for (PerfScoreItemsManSup itemsManSup : scoreItemsManSupList) {
//                        if (Enable.N.toString().equals(itemsManSup.getEnableFlag()) || null == itemsManSup.getCompanyId()) {
//                            log.error("判断能否保存/修改绩效评分项目时,供应商权限ID: "+String.valueOf(itemsManSup.getScoreItemsManSupId())
//                                    +"供应商名称:"+itemsManSup.getCompanyName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                            Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                        }
//                    }
//                    for (PerfScoreItemsManIndicator itemsManIndicator : scoreItemsManIndicatorList) {
//                        if (Enable.N.toString().equals(itemsManIndicator.getEnableFlag()) || null == itemsManIndicator.getTemplateLineId()) {
//                            log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(itemsManIndicator.getScoreItemsManIndicatorId())
//                                    +"供应商名称:"+itemsManIndicator.getIndicatorName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                            Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                        }
//                    }
//                }
//            } else {
//                /**多个评分人的供应商权限和指标权限不为空和重复的判断*/
//                for (PerfScoreItemsMan scoreItemsMan : scoreItemsManList) {   //绩效评分人
//                    Assert.isTrue(null != scoreItemsMan, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN);
//                    if (null != scoreItemsMan) {
//                        String scoreUserName = scoreItemsMan.getScoreUserName();
//                        Assert.isTrue(null != scoreUserName, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN);
//                        List<PerfScoreItemsManSup> scoreItemsManSupList = scoreItemsMan.getPerfScoreItemsManSupList();
//                        Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsManSupList), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                        List<PerfScoreItemsManIndicator> scoreItemsManIndicatorList = scoreItemsMan.getPerfScoreItemsManIndicatorList();
//                        Assert.isTrue(CollectionUtils.isNotEmpty(scoreItemsManIndicatorList), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                        /**一个供应商权限*/
//                        if (1 == scoreItemsManSupList.size()) {
//                            PerfScoreItemsManSup itemsManSup = scoreItemsManSupList.get(0);
//                            if (Enable.N.toString().equals(itemsManSup.getEnableFlag()) || null == itemsManSup.getCompanyId()) {
//                                log.error("判断能否保存/修改绩效评分项目时,供应商权限ID: "+String.valueOf(itemsManSup.getScoreItemsManSupId())
//                                        +"供应商名称:"+itemsManSup.getCompanyName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                                Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                            }
//                        }else{  //多个供应商
//
//                            /**多个供应商，同一个供应商只要都没有选中，则提示错误信息*/
//                            for(PerfScoreItemsManSup manSup : scoreItemsManSupList){
//                                Assert.isTrue(null == manSup, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                                boolean isManSup = Enable.Y.toString().equals(manSup.getEnableFlag()) ? true : false;   //初始化是否选中供应商
//                                if(!isManSup){
//                                    Long companyId = manSup.getCompanyId();
//                                    for(PerfScoreItemsManSup itemsManSup : scoreItemsManSupList){
//                                        if(null != companyId && companyId.compareTo(itemsManSup.getCompanyId()) == 0
//                                                && Enable.Y.toString().equals(itemsManSup.getEnableFlag()) ){
//                                            isManSup = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                                Assert.isTrue(isManSup, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//
//                                //一个指标权限,判断指标权限不为空
//                                if(1 == scoreItemsManIndicatorList.size()){
//                                    PerfScoreItemsManIndicator itemsManIndicator = scoreItemsManIndicatorList.get(0);
//                                    if (Enable.N.toString().equals(itemsManIndicator.getEnableFlag()) || null == itemsManIndicator.getDimWeightId()) {
//                                        log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(itemsManIndicator.getScoreItemsManIndicatorId())
//                                                +"指标名称:"+itemsManIndicator.getIndicatorName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                        Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                    }else{  //多个指标权限
//                                        for(PerfScoreItemsManIndicator scoreItemsManIndicator : scoreItemsManIndicatorList){
//                                            Assert.isTrue(null != scoreItemsManIndicator.getDimWeightId(), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                            boolean isIndicator = false;    //初始化已经配置的指标权限为false
//                                           /* if (null != scoreItemsManIndicator) {
//                                                Long templateLineId = scoreItemsManIndicator.getTemplateLineId();
//                                                String indicatorEnableFlag = scoreItemsManIndicator.getEnableFlag();
//                                                isIndicator = Enable.Y.toString().equals(indicatorEnableFlag) ? true : false;
//                                                for (PerfScoreItemsManIndicator itemsManIndicator : itemsManIndicatorList) {
//                                                    if (null != itemsManIndicator && isIndicator
//                                                            && templateLineId.compareTo(itemsManIndicator.getTemplateLineId()) == 0
//                                                            && Enable.Y.toString().equals(itemsManIndicator.getEnableFlag())) {
//                                                        log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(scoreItemsManIndicator.getScoreItemsManIndicatorId())
//                                                                +"指标名称:"+scoreItemsManIndicator.getIndicatorName()+","+ScoreItemsConst.REPEAT_SCORE_ITEMS_MAN_INDICATORS);
//                                                        Assert.isTrue(false, ScoreItemsConst.REPEAT_SCORE_ITEMS_MAN_INDICATORS);
//                                                    }else if (!isIndicator && null != itemsManIndicator && templateLineId.compareTo(itemsManIndicator.getTemplateLineId()) == 0
//                                                            && Enable.Y.toString().equals(itemsManIndicator.getEnableFlag())) {
//                                                        isIndicator = true;
//                                                        break;
//                                                    }
//                                                }
//                                                if(!isIndicator){
//                                                    log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(scoreItemsManIndicator.getScoreItemsManIndicatorId())
//                                                            +"指标名称:"+scoreItemsManIndicator.getIndicatorName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                    Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                }
//                                            }*/
//                                        }
//                                    }
//                                }
//                            }
//
//
//                            for (PerfScoreItemsMan itemsMan : scoreItemsManList) {   //绩效评分人
//                                if(!scoreUserName.equals(itemsMan.getScoreUserName())){
//                                    List<PerfScoreItemsManSup> itemsManSupList = itemsMan.getPerfScoreItemsManSupList();
//                                    Assert.isTrue(CollectionUtils.isNotEmpty(itemsManSupList), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                                    List<PerfScoreItemsManIndicator> itemsManIndicatorList = itemsMan.getPerfScoreItemsManIndicatorList();
//                                    for (PerfScoreItemsManSup scoreItemsManSup : scoreItemsManSupList) {
//                                        Long companyId = scoreItemsManSup.getCompanyId();
//                                        String supEnableFlag = scoreItemsManSup.getEnableFlag();
//                                        for (PerfScoreItemsManSup itemsManSup : itemsManSupList) {
//                                            if (null != companyId && companyId.compareTo(itemsManSup.getCompanyId()) == 1
//                                                    && Enable.N.toString().equals(supEnableFlag)
//                                                    && Enable.N.toString().equals(itemsManSup.getEnableFlag())) {  //判断供应商权限不为空
//                                                log.error("判断能否保存/修改绩效评分项目时,供应商权限ID: "+String.valueOf(itemsManSup.getScoreItemsManSupId())
//                                                        +"供应商名称:"+itemsManSup.getCompanyName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                                                Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_SUP);
//                                            }
//                                        }
//
//                                        //一个指标权限,判断指标权限不为空
//                                        if(1 == scoreItemsManIndicatorList.size()){
//                                            PerfScoreItemsManIndicator itemsManIndicator = scoreItemsManIndicatorList.get(0);
//                                            if (Enable.N.toString().equals(itemsManIndicator.getEnableFlag()) || null == itemsManIndicator.getDimWeightId()) {
//                                                log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(itemsManIndicator.getScoreItemsManIndicatorId())
//                                                        +"指标名称:"+itemsManIndicator.getIndicatorName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                            }
//                                        }else{  //多个指标权限
//                                            for(PerfScoreItemsManIndicator scoreItemsManIndicator : scoreItemsManIndicatorList){
//                                                Assert.isTrue(null != scoreItemsManIndicator.getDimWeightId(), ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                boolean isIndicator = false;    //初始化已经配置的指标权限为false
//                                                if (null != scoreItemsManIndicator) {
//                                                    Long templateLineId = scoreItemsManIndicator.getTemplateLineId();
//                                                    String indicatorEnableFlag = scoreItemsManIndicator.getEnableFlag();
//                                                    isIndicator = Enable.Y.toString().equals(indicatorEnableFlag) ? true : false;
//                                                    for (PerfScoreItemsManIndicator itemsManIndicator : itemsManIndicatorList) {
//                                                        if (null != itemsManIndicator && isIndicator
//                                                                && templateLineId.compareTo(itemsManIndicator.getTemplateLineId()) == 0
//                                                                && Enable.Y.toString().equals(itemsManIndicator.getEnableFlag())) {
//                                                            log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(scoreItemsManIndicator.getScoreItemsManIndicatorId())
//                                                                    +"指标名称:"+scoreItemsManIndicator.getIndicatorName()+","+ScoreItemsConst.REPEAT_SCORE_ITEMS_MAN_INDICATORS);
//                                                            Assert.isTrue(false, ScoreItemsConst.REPEAT_SCORE_ITEMS_MAN_INDICATORS);
//                                                        }else if (!isIndicator && null != itemsManIndicator && templateLineId.compareTo(itemsManIndicator.getTemplateLineId()) == 0
//                                                                && Enable.Y.toString().equals(itemsManIndicator.getEnableFlag())) {
//                                                            isIndicator = true;
//                                                            break;
//                                                        }
//                                                    }
//                                                    if(!isIndicator){
//                                                        log.error("判断能否保存/修改绩效评分项目时,指标权限ID: "+String.valueOf(scoreItemsManIndicator.getScoreItemsManIndicatorId())
//                                                                +"指标名称:"+scoreItemsManIndicator.getIndicatorName()+","+ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                        Assert.isTrue(false, ScoreItemsConst.NOT_SET_SCORE_ITEMS_MAN_INDICATORS);
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return result;
    }

    @Override
    public Map<String, Object> updateScoreItemsWithFlow(PerfScoreItems scoreItems) {
        Map<String, Object> map = new HashMap();
        PerfScoreItems source = this.getById(scoreItems.getScoreItemsId());
        if (enableFlowWork(scoreItems)) {
            map.put("businessId", source.getScoreItemsId());
            map.put("fdId", source.getCbpmInstaceId());
            map.put("subject", source.getOrganizationName());
            if (StringUtil.isEmpty(source.getCbpmInstaceId())) {
                CbpmRquestParamDTO request = buildCbpmRquest(source);
                map = workFlowFeign.initProcess(request);
            }

        }
        return map;
    }

    /**
     * 绩效项目复制
     *
     * @param scoreItemsId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyScoreItems(Long scoreItemsId) {
        PerfScoreItems scoreItems = this.getById(scoreItemsId);
        // 新的项目id
        Long newScoreItemsId = IdGenrator.generate();
        scoreItems.setScoreItemsId(newScoreItemsId)
                .setProjectStatus(ScoreItemsProjectStatusEnum.SCORE_DRAFT.getValue())
                .setApproveStatus(ScoreItemsApproveStatusEnum.DRAFT.getValue())
                .setPerStartMonth(null)
                .setPerEndMonth(null)
                .setScoreStartTime(null)
                .setScoreEndTime(null)
                .setScorePeople(Long.valueOf(0));
        this.save(scoreItems);

        // 复制评分供应商
        List<PerfScoreItemsSup> scoreItemsSups = iPerfScoreItemsSupService.list(Wrappers.lambdaQuery(PerfScoreItemsSup.class)
                .eq(PerfScoreItemsSup::getScoreItemsId, scoreItemsId));
        if (CollectionUtils.isNotEmpty(scoreItemsSups)) {
            scoreItemsSups.forEach(scoreItemsSup -> {
                scoreItemsSup.setScoreItemsSupId(IdGenrator.generate());
                scoreItemsSup.setScoreItemsId(newScoreItemsId);
            });
            // 批量新增数据
            iPerfScoreItemsSupService.saveBatch(scoreItemsSups);
        }

        // 复制评分人
        List<PerfScoreItemsMan> scoreItemsMans = iPerfScoreItemsManService.list(Wrappers.lambdaQuery(PerfScoreItemsMan.class)
                .eq(PerfScoreItemsMan::getScoreItemsId, scoreItemsId));
        if (CollectionUtils.isNotEmpty(scoreItemsMans)) {
            scoreItemsMans.forEach(scoreItemsMan -> {
                scoreItemsMan.setScoreItemsManId(IdGenrator.generate());
                scoreItemsMan.setScoreItemsId(newScoreItemsId);
            });
            // 批量新增数据
            iPerfScoreItemsManService.saveBatch(scoreItemsMans);
        }
    }

    /**
     * 项目计算评分前查询哪些评分人没有评分
     * @param scoreItems
     * @return
     */
    @Override
    public BaseResult<String> confirmBeforeCalculate(PerfScoreItems scoreItems) {
        List<ScoreManScoringV1> scoreManScoringV1s = iScoreManScoringV1Service.list(Wrappers.lambdaQuery(ScoreManScoringV1.class)
                .eq(ScoreManScoringV1::getScoreItemsId, scoreItems.getScoreItemsId()));
        // 获取总的数据条数
        List<ScoreManScoringV1> isNullScoreManScoringV1s = scoreManScoringV1s.stream().filter(x -> Objects.isNull(x.getScore())).collect(Collectors.toList());
        BaseResult baseResult = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        int isNullSize = isNullScoreManScoringV1s.size();
        if (isNullSize == 0) {
            baseResult.setData(Collections.EMPTY_LIST);
            return baseResult;
        }
        List<String> resultMsg = new ArrayList<>();
        isNullScoreManScoringV1s.forEach(scoreManScoringV1 -> {
            StringBuffer sb = new StringBuffer();
            // 供应商
            String companyName = scoreManScoringV1.getCompanyName();
            // 指标维度
            String indicatorDimensionType = scoreManScoringV1.getIndicatorDimensionType();
            String indicatorDimension = INDICATOR_DIMENSION_MAP.get(indicatorDimensionType);
            // 指标名称
            String indicatorName = scoreManScoringV1.getIndicatorName();
            // 评分人
            String scoreNickName = scoreManScoringV1.getScoreNickName();
            sb.append("供应商：[").append(companyName).append("]，指标名称：[").append(indicatorName).append("]，评分人：[").append(scoreNickName).append("]，未评分");
            resultMsg.add(sb.toString());
        });
        baseResult.setData(resultMsg);
        return baseResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abandonAlready(Long scoreItemId) {
        Assert.notNull(scoreItemId,"ID不能为空");
        PerfScoreItems perfScoreItems = this.getById(scoreItemId);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!Objects.equals(loginAppUser.getUserId(),perfScoreItems.getCreatedId())) {
            throw new BaseException("只有项目创建人可以废弃项目");
        }
        //废弃并删除明细
        this.updateById(new PerfScoreItems()
                .setApproveStatus(ScoreItemsApproveStatusEnum.DRAFT.getValue())
                .setProjectStatus("OBSOLETE").setScoreItemsId(scoreItemId));
        iScoreManScoringV1Service.remove(Wrappers.lambdaQuery(ScoreManScoringV1.class)
                .eq(ScoreManScoringV1::getScoreItemsId,scoreItemId));
    }

    private Boolean enableFlowWork(PerfScoreItems source) {
        Long menuId = source.getMenuId();
        Permission menu = rbacClient.getMenu(menuId);
        Boolean flowEnable;
        try {
            flowEnable = workFlowFeign.getFlowEnable(menuId, menu.getFunctionId(), CbpmFormTemplateIdEnum.PERF_SCORE_ITEMS.getKey());
        } catch (FeignException e) {
            log.error("提交绩效评分项目时报错,判断工作流是否启动时,参数 menuId：" + menuId + ",functionId" + menu.getFunctionId()
                    + ",templateCode" + CbpmFormTemplateIdEnum.PERF_SCORE_ITEMS.getKey() + "报错：", e);
            throw new BaseException("提交绩效评分项目时判断工作流是否启动时报错");
        }
        return flowEnable;
    }

    private CbpmRquestParamDTO buildCbpmRquest(PerfScoreItems source) {
        CbpmRquestParamDTO cbpmRquestParamDTO = new CbpmRquestParamDTO();
        cbpmRquestParamDTO.setBusinessId(String.valueOf(source.getScoreItemsId()));
        cbpmRquestParamDTO.setTemplateCode(CbpmFormTemplateIdEnum.PERF_SCORE_ITEMS.getKey());
        cbpmRquestParamDTO.setSubject(source.getProjectName());
        cbpmRquestParamDTO.setFdId(source.getCbpmInstaceId());
        return cbpmRquestParamDTO;
    }
}

