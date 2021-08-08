package com.midea.cloud.srm.perf.template.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsApproveStatusEnum;
import com.midea.cloud.common.enums.perf.scoreproject.ScoreItemsProjectStatusEnum;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.perf.common.ScoreItemsConst;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.perf.template.PerfTemplateStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.perf.template.dto.*;
import com.midea.cloud.srm.model.perf.template.entity.*;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.perf.template.mapper.PerfTemplateHeaderMapper;
import com.midea.cloud.srm.perf.template.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  <pre>
 *  绩效模型头表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
@Service
public class PerfTemplateHeaderServiceImpl extends ServiceImpl<PerfTemplateHeaderMapper, PerfTemplateHeader>
        implements IPerfTemplateHeaderService {

    /**绩效模型分类Service接口*/
    @Autowired
    private IPerfTemplateCategoryService iPerfTemplateCategoryService;

    /**绩效模型指标维度表Service*/
    @Autowired
    private IPerfTemplateDimWeightService iPerfTemplateDimWeightService;

    /**绩效模型指标信息 Service*/
    @Autowired
    private IPerfTemplateLineService iPerfTemplateLineService;

    /**绩效模型指标行信息Service*/
    @Autowired
    private IPerfTemplateIndsLineService iPerfTemplateIndsLineService;

    /**绩效评分项目Service*/
    @Resource
    private IPerfScoreItemsService iPerfScoreItemsService;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private BaseClient baseClient;

    @Override
    public List<PerfTemplateHeaderDTO> findPerTemplateHeadList(PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException {
        List<PerfTemplateHeaderDTO> pefTemplateList = new ArrayList<>();
        try{
            pefTemplateHeader.setDeleteFlag(Enable.N.toString());
            pefTemplateList = getBaseMapper().findPerTemplateHeadList(pefTemplateHeader);
        }catch (Exception e){
            log.error("根据条件(绩效模型头和采购分类表)获取绩效模型头时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return pefTemplateList;
    }

    @Override
    public PerfTemplateDTO findPerTemplateByTemplateHeadId(Long perfTemplateHeaderId) throws BaseException {
        Assert.notNull(perfTemplateHeaderId, "id不能为空");
        PerfTemplateDTO perfTemplateDTO = new PerfTemplateDTO();

        /**根据绩效模型ID获取绩效模型头和采购分类表*/
        try{
            PerfTemplateHeader perfTemplateHeader = getBaseMapper().selectById(perfTemplateHeaderId);
            perfTemplateDTO.setPerfTemplateHeader(perfTemplateHeader);
            PerfTemplateCategory perfTemplateCategory = new PerfTemplateCategory();
            perfTemplateCategory.setTemplateHeadId(perfTemplateHeaderId);
            List<PerfTemplateCategory> perfTemplateLineList = iPerfTemplateCategoryService.list(new QueryWrapper<>(perfTemplateCategory));
            perfTemplateDTO.setPerfTemplateCategoryList(perfTemplateLineList);
        }catch (Exception e){
            log.error("根据ID获取绩效模型头和采购分类表时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }

        /**根据绩效模型ID获取绩效指标维护和绩效指标表信息*/
        PerfTemplateDimWeight perfTemplateDimWeight = new PerfTemplateDimWeight();
        perfTemplateDimWeight.setTemplateHeadId(perfTemplateHeaderId);
        perfTemplateDimWeight.setDeleteFlag(Enable.N.toString());
        List<PerfTemplateDimWeightDTO> perfTemplateDimWeightList = iPerfTemplateDimWeightService.findPerTemplateDimWeightAndLine(perfTemplateDimWeight);
        perfTemplateDTO.setPerfTemplateDimWeightList(perfTemplateDimWeightList);
        return perfTemplateDTO;
    }

    @Override
    public List<PerfTemplateDTO> findPerTemplateHeadAndOrgCateGory(PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException {
        List<PerfTemplateDTO> pefTemplateList = new ArrayList<>();
        try{
            pefTemplateHeader.setDeleteFlag(Enable.N.toString());
//            pefTemplateList = getBaseMapper().findPerTemplateHeadAndOrgCateGory(pefTemplateHeader);
        }catch (Exception e){
            log.error("根据条件获取绩效模型头和采购分类表时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return pefTemplateList;
    }

    @Override
    public String enablePefTemplateHeader(Long pefTempateHeadId, String templateStatus) throws BaseException {
        Assert.notNull(pefTempateHeadId, "id不能为空");
        Assert.notNull(templateStatus, "启动/禁用不能为空");
        PerfTemplateHeader pefTemplateHeader = getBaseMapper().selectById(pefTempateHeadId);
        if(null != pefTemplateHeader){
            pefTemplateHeader.setTemplateStatus(templateStatus);
            try {
                getBaseMapper().updateById(pefTemplateHeader);
            }catch (Exception e){
                log.error("绩效模型启动/禁用时报错：",e);
                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
            }
        }
        return ResultCode.SUCCESS.getMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delPefTemplateHeader(Long pefTemplateHeadId) throws BaseException {
        Assert.notNull(pefTemplateHeadId, "id不能为空");
        PerfTemplateHeader pefTemplateHeader = this.getById(pefTemplateHeadId);
        Optional.ofNullable(pefTemplateHeader).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("找不到对应的模型数据。")));
        // 删除之前检查是否被绩效评分项目引用(若被引用，则不能删除)
        checkIsDelete(pefTemplateHeadId);
        List<PerfTemplateDimWeight> perfTemplateDimWeights = iPerfTemplateDimWeightService.list(Wrappers.lambdaQuery(PerfTemplateDimWeight.class)
                .eq(PerfTemplateDimWeight::getTemplateHeadId, pefTemplateHeadId));
        if (CollectionUtils.isNotEmpty(perfTemplateDimWeights)) {
            // 查询维度对应的指标数据
            perfTemplateDimWeights.forEach(perfTemplateDimWeight -> {
                List<PerfTemplateLine> perfTemplateLines = iPerfTemplateLineService.list(Wrappers.lambdaQuery(PerfTemplateLine.class)
                        .eq(PerfTemplateLine::getTemplateDimWeightId, perfTemplateDimWeight.getDimWeightId()));
                if (CollectionUtils.isNotEmpty(perfTemplateLines)) {
                    List<Long> templateLineIds = perfTemplateLines.stream().map(PerfTemplateLine::getTemplateLineId).distinct().collect(Collectors.toList());
                    iPerfTemplateLineService.removeByIds(templateLineIds);
                }
                // 删除维度数据
                iPerfTemplateDimWeightService.removeById(perfTemplateDimWeight.getDimWeightId());
            });
        }
        // 最后删除模型头数据
        this.removeById(pefTemplateHeadId);
    }

    /**
     * Description 删除之前检查是否被绩效评分项目引用(若被引用，则不能删除)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.08
     * @throws
     **/
    private boolean checkIsDelete(Long templateHeadId){
        boolean isDetele = false;
        Assert.notNull(templateHeadId, "id不能为空");
        PerfScoreItems queryScoreItems = new PerfScoreItems();
        queryScoreItems.setTemplateHeadId(templateHeadId);
        List<PerfScoreItems> scoreItemsList = iPerfScoreItemsService.list(new QueryWrapper<>(queryScoreItems));
        if(CollectionUtils.isNotEmpty(scoreItemsList) && null != scoreItemsList.get(0)){
            Assert.isTrue(false, ScoreItemsConst.IS_NOT_DELETE_TEMPALTE_HEADER);
        }
        isDetele = true;
        return isDetele;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdatePerfTemplate(PerfTemplateDTO perfTemplateDTO) throws BaseException{
        Assert.notNull(perfTemplateDTO, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Long templeateHeadId = null;
        PerfTemplateHeader perfTemplateHeader = perfTemplateDTO.getPerfTemplateHeader();
        if(null != perfTemplateHeader){
            //获取版本号(格式yyyyMM0001)
            Long versionByLong = null;
            //获取当前用户姓名
            String nickName = "";
            LoginAppUser user = AppUserUtil.getLoginAppUser();
            if(null != user){
                nickName = user.getNickname();
            }

            if(null == perfTemplateHeader.getTemplateHeadId()){ //新增
                String version = baseClient.seqGen(SequenceCodeConstant.SEQ_PER_TEMPLATE_VERSION);
                Assert.notNull(version, "版本号不能为空");
                versionByLong = Long.parseLong(version);
                perfTemplateHeader.setDeleteFlag(Enable.N.toString());
                perfTemplateHeader.setVersion(versionByLong);
                //新增时默认为拟定状态
                perfTemplateHeader.setTemplateStatus(PerfTemplateStatusEnum.DRAFT.getValue());
                perfTemplateHeader.setCreatedFullName(nickName);
                perfTemplateHeader.setLastUpdatedFullName(nickName);
            }else {
                versionByLong = perfTemplateHeader.getVersion();
                perfTemplateHeader.setLastUpdatedFullName(nickName);
            }
            /**保存绩效模型头表信息*/
            templeateHeadId = (null != perfTemplateHeader.getTemplateHeadId() ? perfTemplateHeader.getTemplateHeadId() : IdGenrator.generate());
            boolean updateHeaderCount = false ; //初始化保存/修改指标模型头记录为false
            try {
                perfTemplateHeader.setTemplateHeadId(templeateHeadId);
                updateHeaderCount = super.saveOrUpdate(perfTemplateHeader);
            }catch (Exception e){
                log.error("保存绩效模型头表信息时报错: "+e);
                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
            }

            if(updateHeaderCount){
                /**保存/修改绩效模型采购分类表信息*/
                try {
                    List<PerfTemplateCategory> TemplateCategoryList = perfTemplateDTO.getPerfTemplateCategoryList();
                    if (CollectionUtils.isNotEmpty(TemplateCategoryList)) {
                        for (PerfTemplateCategory perfTemplateCategory : TemplateCategoryList) {
                            if (null != perfTemplateCategory) {
                                if(null == perfTemplateCategory.getTemplateCategoryId()){ //新增操作
                                    perfTemplateCategory.setDeleteFlag(Enable.N.toString());
                                }
                                perfTemplateCategory.setVersion(versionByLong);
                                Long TemplateCategoryId = (null != perfTemplateCategory.getTemplateCategoryId() ?
                                        perfTemplateCategory.getTemplateCategoryId() : IdGenrator.generate());
                                perfTemplateCategory.setTemplateCategoryId(TemplateCategoryId);
                                perfTemplateCategory.setTemplateHeadId(templeateHeadId);
                            }
                        }
                        iPerfTemplateCategoryService.saveOrUpdateBatch(TemplateCategoryList);
                    }
                }catch (Exception e){
                    log.error("保存/修改绩效模型采购分类表信息时报错: "+e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }

                /**保存/修改绩效指标维度、绩效指标和绩效指标行信息*/
                List<PerfTemplateDimWeightDTO> perfTemplateDimWeightDTOList = perfTemplateDTO.getPerfTemplateDimWeightList();
                if(CollectionUtils.isNotEmpty(perfTemplateDimWeightDTOList)){
                    for(PerfTemplateDimWeightDTO dimWeightDTO : perfTemplateDimWeightDTOList){
                        if(null != dimWeightDTO){

                            /**保存/修改绩效指标维度信息*/
                            PerfTemplateDimWeight perfTemplateDimWeight = dimWeightDTO.getPerfTemplateDimWeight();
                            if(null == perfTemplateDimWeight.getDimWeightId()) { //新增
                                perfTemplateDimWeight.setDeleteFlag(Enable.N.toString());
                            }
                            perfTemplateDimWeight.setVersion(versionByLong);
                            boolean isUpdateDimWeight = (null != perfTemplateDimWeight.getDimWeightId() ? true : false); //是否修改绩效指标维度，是为true
                            Long dimWeightId = (null != perfTemplateDimWeight.getDimWeightId() ? perfTemplateDimWeight.getDimWeightId()
                                    : IdGenrator.generate());
                            perfTemplateDimWeight.setDimWeightId(dimWeightId);
                            perfTemplateDimWeight.setTemplateHeadId(templeateHeadId);
                            boolean isDimWeight = false;

                            try {
                                isDimWeight = iPerfTemplateDimWeightService.saveOrUpdate(perfTemplateDimWeight);
                            }catch (Exception e){
                                log.error("保存/修改绩效模型指标维度时报错: "+e);
                                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                            }

                            if(isDimWeight){
                                /**保存/修改绩效指标和绩效指标行信息（修改指标时要先删除指标和绩效行信息）*/
                                if(isUpdateDimWeight){
                                    PerfTemplateLine queryPerfTemplateLine = new PerfTemplateLine();
                                    queryPerfTemplateLine.setTemplateDimWeightId(dimWeightId);
                                    List<PerfTemplateLine> delPerfTemplateLineList = iPerfTemplateLineService.list(new QueryWrapper<>(queryPerfTemplateLine));
                                    List<Long> delPerfTemplateLineIds = new ArrayList<>();      //需要删除的绩效指标信息Id集合
                                    List<Long> delPerfTemplateIndsLineIds = new ArrayList<>();  //需要删除的绩效指标行信息Id集合
                                    if(CollectionUtils.isNotEmpty(delPerfTemplateLineList)){
                                        for(PerfTemplateLine perfTemplateLine : delPerfTemplateLineList){
                                            if(null != perfTemplateLine && null != perfTemplateLine.getTemplateLineId()){
                                                delPerfTemplateLineIds.add(perfTemplateLine.getTemplateLineId());
                                                PerfTemplateIndsLine queryPerfTemplateIndsLine = new PerfTemplateIndsLine();
                                                queryPerfTemplateIndsLine.setTemplateLineId(perfTemplateLine.getTemplateLineId());
                                                List<PerfTemplateIndsLine> perfTemplateIndsLineList =
                                                        iPerfTemplateIndsLineService.list(new QueryWrapper<>(queryPerfTemplateIndsLine));
                                                for(PerfTemplateIndsLine perfTemplateIndsLine : perfTemplateIndsLineList){
                                                    if(null != perfTemplateIndsLine && null != perfTemplateIndsLine.getTemplateIndsLineId()){
                                                        delPerfTemplateIndsLineIds.add(perfTemplateIndsLine.getTemplateIndsLineId());
                                                    }
                                                }

                                            }
                                        }
                                        try {
                                            if(CollectionUtils.isNotEmpty(delPerfTemplateLineIds)) {
                                                iPerfTemplateLineService.removeByIds(delPerfTemplateLineIds);
                                            }
                                        }catch (Exception e){
                                            log.error("修改绩效模型指标-修改绩效指标,删除绩效指标时报错: "+e);
                                            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                        }
                                        try {
                                            if(CollectionUtils.isNotEmpty(delPerfTemplateIndsLineIds)) {
                                                iPerfTemplateIndsLineService.removeByIds(delPerfTemplateIndsLineIds);
                                            }
                                        }catch (Exception e){
                                            log.error("修改绩效模型指标-修改绩效指标,删除绩效指标行时报错: "+e);
                                            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                        }
                                    }
                                }

                                List<PerfTemplateLineDTO> perfTemplateLinesList = dimWeightDTO.getPerfTemplateLineList();
                                if(CollectionUtils.isNotEmpty(perfTemplateLinesList)){
                                    for(PerfTemplateLineDTO perfTemplateLineDTO : perfTemplateLinesList){
                                        if(null != perfTemplateLineDTO){
                                            PerfTemplateLine perfTemplateLine = perfTemplateLineDTO.getPerfTemplateLine();
                                            if(null != perfTemplateLine){
                                                Long templateLineId = (null != perfTemplateLine.getTemplateLineId()
                                                        ? perfTemplateLine.getTemplateLineId() : IdGenrator.generate());
                                                perfTemplateLine.setTemplateLineId(templateLineId);
                                                perfTemplateLine.setTemplateDimWeightId(dimWeightId);
                                                perfTemplateLine.setDeleteFlag(Enable.N.toString());
                                                perfTemplateLine.setVersion(versionByLong);
                                                boolean updateLineCount = false;
                                                try{
                                                    updateLineCount = iPerfTemplateLineService.saveOrUpdate(perfTemplateLine);
                                                }catch (Exception e){
                                                    log.error("保存/修改绩效模型指标时报错: "+e);
                                                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                                }

                                                try {
                                                    if (updateLineCount && CollectionUtils.isNotEmpty(perfTemplateLineDTO.getPerfTemplateIndsLineList())) {
                                                        List<PerfTemplateIndsLine> perfTemplateIndsLineList = perfTemplateLineDTO.getPerfTemplateIndsLineList();
                                                        for (PerfTemplateIndsLine perfTemplateIndsLine : perfTemplateIndsLineList) {
                                                            if (null != perfTemplateIndsLine) {
                                                                Long teplateIndsLineId = (null != perfTemplateIndsLine.getTemplateIndsLineId() ?
                                                                        perfTemplateIndsLine.getTemplateIndsLineId() : IdGenrator.generate());
                                                                perfTemplateIndsLine.setTemplateIndsLineId(teplateIndsLineId);
                                                                perfTemplateIndsLine.setTemplateLineId(templateLineId);
                                                                perfTemplateIndsLine.setDeleteFlag(Enable.N.toString());
                                                                perfTemplateIndsLine.setVersion(versionByLong);
                                                            }
                                                        }
                                                        iPerfTemplateIndsLineService.saveOrUpdateBatch(perfTemplateIndsLineList);
                                                    }
                                                }catch (Exception e){
                                                    log.error("保存/修改绩效模型指标行时报错: "+e);
                                                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return String.valueOf(templeateHeadId);
    }

    /**
     * 根据绩效模型查询供应商
     * 根据绩效模型行上的品类去组织品类表查询供应商
     * 文宣说的 1.如果有绩效模型行上有多个品类，取交集，即所查的供应商必须同时有这些品类的绿牌服务状态
     * @param perfTemplateHeader
     * @return
     */
    @Override
    public List<CompanyInfo> listCompanysByPerfTemplateHeader(PerfTemplateHeader perfTemplateHeader) {
        PerfTemplateHeader head = this.getById(perfTemplateHeader.getTemplateHeadId());
        List<PerfTemplateCategory> templateCategories = iPerfTemplateCategoryService.list(Wrappers.lambdaQuery(PerfTemplateCategory.class)
                .eq(PerfTemplateCategory::getTemplateHeadId, head.getTemplateHeadId())
        );
        List<CompanyInfo> companyInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(templateCategories)) {
            List<Long> categoryIds = templateCategories.stream().filter(x -> null != x.getCategoryId()).map(PerfTemplateCategory::getCategoryId).collect(Collectors.toList());
            companyInfos = supplierClient.listCompanyInfosByCategoryIds(categoryIds);
        }
        return companyInfos;
    }

    /**
     * 根据模板头id查询维度和指标信息
     * @param templateHeaderId
     * @return
     */
    @Override
    public List<PerfTemplateLine> listTemplateLinesByTemplateHeaderId(Long templateHeaderId) {
        // 根据模板头id查询对应的指标维度
        List<PerfTemplateDimWeight> templateDimWeights = iPerfTemplateDimWeightService.list(Wrappers.lambdaQuery(PerfTemplateDimWeight.class)
                .eq(PerfTemplateDimWeight::getTemplateHeadId, templateHeaderId));
        if (CollectionUtils.isNotEmpty(templateDimWeights)) {
            List<Long> templateDimWeightIds = templateDimWeights.stream().map(PerfTemplateDimWeight::getDimWeightId).distinct().collect(Collectors.toList());
            return CollectionUtils.isEmpty(templateDimWeightIds) ? Collections.emptyList() :
                    iPerfTemplateLineService.list(Wrappers.lambdaQuery(PerfTemplateLine.class).in(PerfTemplateLine::getTemplateDimWeightId, templateDimWeightIds));
        }
        return Collections.emptyList();
    }

    /**
     * 列表条件查询
     * @param queryDTO
     * @return
     */
    @Override
    public List<PerfTemplateHeader> listPefTemplateHeaderPage(PerfTemplateHeaderQueryDTO queryDTO) {
        // 根据头筛选条件查询
        QueryWrapper<PerfTemplateHeader> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(queryDTO.getTemplateName()), "TEMPLATE_NAME", queryDTO.getTemplateName());
        wrapper.eq(StringUtils.isNotEmpty(queryDTO.getTemplateStatus()), "TEMPLATE_STATUS", queryDTO.getTemplateStatus());
        wrapper.eq(Objects.nonNull(queryDTO.getOrganizationId()), "ORGANIZATION_ID", queryDTO.getOrganizationId());
        wrapper.eq(Objects.nonNull(queryDTO.getVersion()), "VERSION", queryDTO.getVersion());
        // 根据行筛选条件查询
        QueryWrapper<PerfTemplateCategory> lineWrapper = new QueryWrapper<>();
        if (Objects.nonNull(queryDTO.getCategoryId())) {
            lineWrapper.eq("CATEGORY_ID", queryDTO.getCategoryId());
        }
        List<PerfTemplateCategory> perfTemplateCategories = iPerfTemplateCategoryService.list(Wrappers.lambdaQuery(PerfTemplateCategory.class)
                .eq(PerfTemplateCategory::getCategoryId, queryDTO.getCategoryId()));
        if (CollectionUtils.isNotEmpty(perfTemplateCategories)) {
            List<Long> headIds = perfTemplateCategories.stream().map(PerfTemplateCategory::getTemplateHeadId).distinct().collect(Collectors.toList());
            wrapper.in("TEMPLATE_HEAD_ID", headIds);
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

    /**
     * 绩效模板复制
     * @param perfTemplateHeaderId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyPerfTemplateHeader(Long perfTemplateHeaderId) {
        PerfTemplateHeader perfTemplateHeader = this.getById(perfTemplateHeaderId);
        // 新的m模板头id
        Long newHeaderId = IdGenrator.generate();
        //获取版本号(格式yyyyMM0001)
        Long versionByLong = null;
        String version = baseClient.seqGen(SequenceCodeConstant.SEQ_PER_TEMPLATE_VERSION);
        Assert.notNull(version, "版本号不能为空");
        versionByLong = Long.parseLong(version);
        perfTemplateHeader.setTemplateHeadId(newHeaderId)
                .setTemplateStatus(PerfTemplateStatusEnum.DRAFT.getValue())
                .setVersion(versionByLong);
        this.save(perfTemplateHeader);

        // 复制模板品类行
        List<PerfTemplateCategory> perfTemplateCategories = iPerfTemplateCategoryService.list(Wrappers.lambdaQuery(PerfTemplateCategory.class)
                .eq(PerfTemplateCategory::getTemplateHeadId, perfTemplateHeaderId));
        if (CollectionUtils.isNotEmpty(perfTemplateCategories)) {
            perfTemplateCategories.forEach(perfTemplateCategory -> {
                perfTemplateCategory.setTemplateCategoryId(IdGenrator.generate())
                        .setTemplateHeadId(newHeaderId);
            });
            // 批量新增数据
            iPerfTemplateCategoryService.saveBatch(perfTemplateCategories);
        }

        // 复制模型指标维度
        List<PerfTemplateDimWeight> oldPerfTemplateDimWeights = iPerfTemplateDimWeightService.list(Wrappers.lambdaQuery(PerfTemplateDimWeight.class)
                .eq(PerfTemplateDimWeight::getTemplateHeadId, perfTemplateHeaderId));
        if (CollectionUtils.isNotEmpty(oldPerfTemplateDimWeights)) {
            List<PerfTemplateDimWeight> newPerfTemplateDimWeights = new ArrayList<>();
            oldPerfTemplateDimWeights.forEach(oldPerfTemplateDimWeight -> {
                PerfTemplateDimWeight newPerfTemplateDimWeight = new PerfTemplateDimWeight();
                BeanUtils.copyProperties(oldPerfTemplateDimWeight, newPerfTemplateDimWeight);
                long newDimWeightId = IdGenrator.generate();
                newPerfTemplateDimWeight.setDimWeightId(newDimWeightId)
                        .setTemplateHeadId(newHeaderId);

                // 开始 复制模板指标行
                List<PerfTemplateLine> oldPerfTemplateLines = iPerfTemplateLineService.list(Wrappers.lambdaQuery(PerfTemplateLine.class)
                        .eq(PerfTemplateLine::getTemplateDimWeightId, oldPerfTemplateDimWeight.getDimWeightId()));
                if (CollectionUtils.isNotEmpty(oldPerfTemplateLines)) {
                    List<PerfTemplateLine> newPerfTemplateLines = new ArrayList<>();
                    oldPerfTemplateLines.forEach(oldPerfTemplateLine -> {
                        PerfTemplateLine newPerfTemplateLine = new PerfTemplateLine();
                        BeanUtils.copyProperties(oldPerfTemplateLine, newPerfTemplateLine);
                        newPerfTemplateLine.setTemplateLineId(IdGenrator.generate())
                                .setTemplateDimWeightId(newDimWeightId);
                        newPerfTemplateLines.add(newPerfTemplateLine);
                    });
                    iPerfTemplateLineService.saveBatch(newPerfTemplateLines);
                }
                // 结束 复制模板指标行

                iPerfTemplateDimWeightService.save(newPerfTemplateDimWeight);
            });
        }
    }

    /**
     * 校验模型头，模型名称不能重复
     * @param perfTemplateHeader
     */
    @Override
    public void checkTemplateHeader(PerfTemplateHeader perfTemplateHeader) {
        // 模型头id不为空，即已经暂存过了
        if (Objects.nonNull(perfTemplateHeader.getTemplateHeadId())) {
            List<PerfTemplateHeader> perfTemplateHeaders = this.list(Wrappers.lambdaQuery(PerfTemplateHeader.class)
                    .ne(PerfTemplateHeader::getTemplateHeadId, perfTemplateHeader.getTemplateHeadId())
                    .eq(PerfTemplateHeader::getTemplateName, perfTemplateHeader.getTemplateName())
            );
            if (CollectionUtils.isNotEmpty(perfTemplateHeaders)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("模板名称重复，请检查后重试。"));
            }
        }
        // 模型头id为空
        else {
            List<PerfTemplateHeader> templateHeaders = this.list(Wrappers.lambdaQuery(PerfTemplateHeader.class)
                    .eq(PerfTemplateHeader::getTemplateName, perfTemplateHeader.getTemplateName())
            );
            if (CollectionUtils.isNotEmpty(templateHeaders)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("模板名称重复，请检查后重试。"));
            }
        }
    }

}
