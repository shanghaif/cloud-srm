package com.midea.cloud.srm.sup.demotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.sup.DemotionType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionDTO;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.sup.demotion.mapper.CompanyDemotionMapper;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionCategoryService;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionOrgService;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionService;
import com.midea.cloud.srm.sup.demotion.workflow.DemotionFlow;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  供应商升降级表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:49:32
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class CompanyDemotionServiceImpl extends ServiceImpl<CompanyDemotionMapper, CompanyDemotion> implements ICompanyDemotionService {

    @Autowired
    private ICompanyDemotionCategoryService iCompanyDemotionCategoryService;

    @Autowired
    private ICompanyDemotionOrgService iCompanyDemotionOrgService;

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private DemotionFlow demotionFlow;

//    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    /**
     * 分页条件查询
     * @param queryDTO
     * @return
     */
    @Override
    public PageInfo<CompanyDemotion> listPageByParam(CompanyDemotionQueryDTO queryDTO) {
        PageUtil.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<CompanyDemotion> companyDemotionList = this.getBaseMapper().listPageByParam(queryDTO);
        return new PageInfo<>(companyDemotionList);
    }

    /**
     * 条件查询
     * @param queryDTO
     * @return
     */
    @Override
    public List<CompanyDemotion> listByParam(CompanyDemotionQueryDTO queryDTO) {
        // 升降级头表查询
        QueryWrapper<CompanyDemotion> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryDTO.getDemotionName())) {
            queryWrapper.like("DEMOTION_NAME", queryDTO.getDemotionName());
        }
        if (StringUtils.isNotEmpty(queryDTO.getDemotionNumber())) {
            queryWrapper.eq("DEMOTION_NUMBER", queryDTO.getDemotionNumber());
        }
        if (Objects.nonNull(queryDTO.getStatus())) {
            queryWrapper.eq("STATUS", queryDTO.getStatus());
        }
        if (Objects.nonNull(queryDTO.getCompanyId())) {
            queryWrapper.eq("COMPANY_ID", queryDTO.getCompanyId());
        }
        if (StringUtils.isNotEmpty(queryDTO.getDemotionType())) {
            queryWrapper.eq("demotionType", queryDTO.getDemotionType());
        }

        // 升降级品类绩效行表查询
        boolean queryLine = Objects.nonNull(queryDTO.getCategoryId()) || StringUtils.isNotEmpty(queryDTO.getPerformanceNumber());
        List<Long> headIds = new ArrayList<>();

        QueryWrapper<CompanyDemotionCategory> categoryQueryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(queryDTO.getCategoryId())) {
            categoryQueryWrapper.eq("CATEGORY_ID", queryDTO.getCategoryId());
        }
        if (StringUtils.isNotEmpty(queryDTO.getPerformanceNumber())) {
            categoryQueryWrapper.eq("PERFORMANCE_NUMBER", queryDTO.getPerformanceNumber());
        }

        List<CompanyDemotionCategory> companyDemotionCategoryList = iCompanyDemotionCategoryService.list(categoryQueryWrapper);
        // 转换成头ids并去重
        headIds = companyDemotionCategoryList.stream().map(CompanyDemotionCategory::getCompanyDemotionId).collect(Collectors.toList())
                .stream().distinct().collect(Collectors.toList());

        if (queryLine && CollectionUtils.isNotEmpty(headIds)) {
            queryWrapper.in("COMPANY_DEMOTION_ID", headIds);
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(queryWrapper);
    }

    /**
     * 查询单据详情
     * @param companyDemotionId
     * @return
     */
    @Override
    public CompanyDemotionDTO getDemotionById(Long companyDemotionId) {
        CompanyDemotionDTO demotionDTO = new CompanyDemotionDTO();
        CompanyDemotion companyDemotion = this.getById(companyDemotionId);
        String reviewUserIds = companyDemotion.getReviewUserIds();
        if (StringUtils.isNotEmpty(reviewUserIds)) {
            List<String> collect = Arrays.stream(reviewUserIds.split(";")).collect(Collectors.toList());
            List<Long> userIdList = new ArrayList<>();
            collect.forEach(stringId -> {
                userIdList.add(Long.valueOf(stringId));
            });
            companyDemotion.setReviewUserIdList(userIdList);
        }
        List<CompanyDemotionCategory> companyDemotionCategories = iCompanyDemotionCategoryService.list(Wrappers.lambdaQuery(CompanyDemotionCategory.class)
                .eq(CompanyDemotionCategory::getCompanyDemotionId, companyDemotionId)
        );
        List<CompanyDemotionOrg> companyDemotionOrgs = iCompanyDemotionOrgService.list(Wrappers.lambdaQuery(CompanyDemotionOrg.class)
                .eq(CompanyDemotionOrg::getCompanyDemotionId, companyDemotionId)
        );
        List<Fileupload> fileuploads = fileCenterClient.listPage(new Fileupload().setBusinessId(companyDemotionId), YesOrNo.YES.getValue()).getList();

        demotionDTO.setCompanyDemotion(companyDemotion);
        demotionDTO.setCompanyDemotionCategories(companyDemotionCategories);
        demotionDTO.setCompanyDemotionOrgs(companyDemotionOrgs);
        demotionDTO.setFileUploads(fileuploads);

        return demotionDTO;
    }

    /**
     * 升降级单据暂存
     * @param demotionDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveTemporary(CompanyDemotionDTO demotionDTO) {
        CompanyDemotion companyDemotion = demotionDTO.getCompanyDemotion();
        Long headId;
        // 设置单据状态为DRAFT
        companyDemotion.setStatus(ApproveStatusType.DRAFT.getValue())
                .setIfDemotioned(YesOrNo.NO.getValue());
        // 设置单据id
        // 1.单据头id为空，首次暂存
        if (Objects.isNull(companyDemotion.getCompanyDemotionId())) {
            headId = IdGenrator.generate();
            companyDemotion.setCompanyDemotionId(headId);
            // 设置评审人相关字段
            companyDemotion = this.setReviewUserFields(companyDemotion);
            // 首次暂存，必然需要生成单据头单号
            companyDemotion.setDemotionNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_COMPANY_DEMOTION_NO));
            this.save(companyDemotion);

            // 1.1 品类行
            generateDemotionCategories(demotionDTO.getCompanyDemotionCategories(), headId);
            // 1.2 OU行
            generateDemotionOrgs(demotionDTO.getCompanyDemotionOrgs(), headId);
            // 1.3 附件行绑定
            generateDemotionFiles(demotionDTO.getFileUploads(), headId);
        }
        // 2.单据头id不为空，单据头 更新操作
        else {
            headId = companyDemotion.getCompanyDemotionId();
            companyDemotion.setIfDemotioned(YesOrNo.NO.getValue());
            // 设置评审人相关字段
            companyDemotion = this.setReviewUserFields(companyDemotion);
            this.updateById(companyDemotion);
            // 2.1 品类行
            generateDemotionCategories(demotionDTO.getCompanyDemotionCategories(), headId);
            // 2.2 OU行
            generateDemotionOrgs(demotionDTO.getCompanyDemotionOrgs(), headId);
            // 2.3 附件行绑定
            generateDemotionFiles(demotionDTO.getFileUploads(), headId);
        }
        return headId;
    }

    /**
     * 升降级单据提交
     * @param demotionDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(CompanyDemotionDTO demotionDTO) {
        CompanyDemotion companyDemotion = demotionDTO.getCompanyDemotion();
        // 设置单据状态为SUBMITTED
        companyDemotion.setStatus(ApproveStatusType.SUBMITTED.getValue())
                .setIfDemotioned(YesOrNo.NO.getValue());
        // 设置单据id
        // 1.单据头id为空，未经暂存，直接提交
        if (Objects.isNull(companyDemotion.getCompanyDemotionId())) {
            long headId = IdGenrator.generate();
            companyDemotion.setCompanyDemotionId(headId);
            // 设置评审人相关字段
            companyDemotion = this.setReviewUserFields(companyDemotion);
            // 首次暂存，必然需要生成单据头单号
            companyDemotion.setDemotionNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_COMPANY_DEMOTION_NO));
            this.save(companyDemotion);
            // 1.1 品类行
            generateDemotionCategories(demotionDTO.getCompanyDemotionCategories(), headId);
            // 1.2 OU行
            generateDemotionOrgs(demotionDTO.getCompanyDemotionOrgs(), headId);
            // 1.3 附件行绑定
            generateDemotionFiles(demotionDTO.getFileUploads(), headId);
        }
        // 2.单据头id不为空，单据头 更新操作
        else {
            Long headId = companyDemotion.getCompanyDemotionId();
            companyDemotion.setIfDemotioned(YesOrNo.NO.getValue());
            // 设置评审人相关字段
            companyDemotion = this.setReviewUserFields(companyDemotion);
            this.updateById(companyDemotion);
            // 2.1 品类行
            generateDemotionCategories(demotionDTO.getCompanyDemotionCategories(), headId);
            // 2.2 OU行
            generateDemotionOrgs(demotionDTO.getCompanyDemotionOrgs(), headId);
            // 2.3 附件行绑定
            generateDemotionFiles(demotionDTO.getFileUploads(), headId);
        }

//        String formId = null;
//        try {
//            formId = demotionFlow.submitDemotionDTOConfFlow(demotionDTO);
//        } catch (Exception e) {
//            throw new BaseException(e.getMessage());
//        }
//        if (StringUtils.isEmpty(formId)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
//        }
        /**
         * 清除审批流，直接调回调接口
         */
        // 审批前校验
        CompanyDemotion demotion = this.getById(companyDemotion.getCompanyDemotionId());
        Assert.isTrue(Objects.equals(ApproveStatusType.SUBMITTED.getValue(), demotion.getStatus()), "只有已提交单据才能审批");
        this.approve(demotion);

    }

    /**
     * 生成品类行数据
     * @param companyDemotionCategories
     * @param headId
     */
    public void generateDemotionCategories(List<CompanyDemotionCategory> companyDemotionCategories, Long headId) {
        if (CollectionUtils.isNotEmpty(companyDemotionCategories)) {
            companyDemotionCategories.forEach(companyDemotionCategory -> {
                // 品类行设置头id
                if (Objects.isNull(companyDemotionCategory.getCompanyDemotionId())) {
                    companyDemotionCategory.setCompanyDemotionId(headId);
                }
            });
            // 批量保存或更新品类行
            iCompanyDemotionCategoryService.saveOrUpdateDemotionCategorys(companyDemotionCategories);
        }
        // 可能是删除了行数据后又暂存
        else {
            QueryWrapper<CompanyDemotionCategory> removeWrapper = new QueryWrapper<>(new CompanyDemotionCategory().setCompanyDemotionId(headId));
            if (iCompanyDemotionCategoryService.count(removeWrapper) > 0) {
                iCompanyDemotionCategoryService.remove(removeWrapper);
            }
        }
    }

    /**
     * 生成OU行数据
     * @param companyDemotionOrgs
     * @param headId
     */
    public void generateDemotionOrgs(List<CompanyDemotionOrg> companyDemotionOrgs, Long headId) {
        if (CollectionUtils.isNotEmpty(companyDemotionOrgs)) {
            companyDemotionOrgs.forEach(companyDemotionOrg -> {
                // OU行设置头id
                if (Objects.isNull(companyDemotionOrg.getCompanyDemotionId())) {
                    companyDemotionOrg.setCompanyDemotionId(headId);
                }
            });
            // 批量保存或更新品类行
            iCompanyDemotionOrgService.saveOrUpdateDemotionOrgs(companyDemotionOrgs);
        }
        // 可能是删除了行数据后又暂存
        else {
            QueryWrapper<CompanyDemotionOrg> removeWrapper = new QueryWrapper<>(new CompanyDemotionOrg().setCompanyDemotionId(headId));
            if (iCompanyDemotionOrgService.count(removeWrapper) > 0) {
                iCompanyDemotionOrgService.remove(removeWrapper);
            }
        }
    }

    /**
     * 绑定附件行
     * @param demotionFiles
     * @param headId
     */
    public void generateDemotionFiles(List<Fileupload> demotionFiles, Long headId) {
        if (CollectionUtils.isNotEmpty(demotionFiles)) {
            // 附件行绑定业务单据id
            fileCenterClient.bindingFileupload(demotionFiles, headId);
        }
    }

    /**
     * 暂存、提交前校验
     * @param demotionDTO
     * @param status
     */
    @Override
    public void check(CompanyDemotionDTO demotionDTO, String status) {
        Map<String, String> connotSaveTemporaryMap = new HashMap<>();
        connotSaveTemporaryMap.put(ApproveStatusType.SUBMITTED.getValue(), ApproveStatusType.SUBMITTED.getName());
        connotSaveTemporaryMap.put(ApproveStatusType.APPROVED.getValue(), ApproveStatusType.APPROVED.getName());
        connotSaveTemporaryMap.put(ApproveStatusType.ABANDONED.getValue(), ApproveStatusType.ABANDONED.getName());

        // 1.暂存前校验
        if (Objects.equals(status, ApproveStatusType.DRAFT.getValue())) {
            // 1.1 暂存前单据头校验
            // a.供应商必填，供应商id不能为空
            // b.升降级类型必填
            CompanyDemotion companyDemotion = demotionDTO.getCompanyDemotion();
            if (Objects.isNull(companyDemotion.getCompanyId())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("进行升降级的供应商不能为空，请选择后重试。"));
            }
            if (StringUtils.isEmpty(companyDemotion.getDemotionType())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("升降级类型不能为空，请选择后重试。"));
            }
            // 1.2 暂存前单据状态校验
            // a.已提交、已审批、已废弃状态 不能暂存
            // b.如果是降级至红牌或黑牌，降级时间必填
            if (connotSaveTemporaryMap.containsKey(companyDemotion.getStatus())) {
                String resultMsg = LocaleHandler.getLocaleMsg("供应商升降级[x]状态的单据不能暂存，请检查后重试。", connotSaveTemporaryMap.get(companyDemotion.getStatus()));
                throw new BaseException(resultMsg);
            }

            String demotionType = companyDemotion.getDemotionType();
            Map<String, String> demotionToRedAndBlackMap = new HashMap<>();
            demotionToRedAndBlackMap.put(DemotionType.DEMOTION_TO_RED.getValue(), DemotionType.DEMOTION_TO_RED.getName());
            demotionToRedAndBlackMap.put(DemotionType.DEMOTION_TO_BLACK.getValue(), DemotionType.DEMOTION_TO_BLACK.getName());
            if (demotionToRedAndBlackMap.containsKey(demotionType) && Objects.isNull(companyDemotion.getDemotionDate())) {
                String resultMsg = LocaleHandler.getLocaleMsg("升降级类型为[x]时，升降级时间不能为空，请检查后重试。", demotionToRedAndBlackMap.get(demotionType));
                throw new BaseException(resultMsg);
            }

        }

        // 2.提交前校验
        if (Objects.equals(status, ApproveStatusType.SUBMITTED.getValue())) {
            // 2.1 提交前单据头校验
            // a.供应商不能为空
            // b.升降级类型不能为空
            CompanyDemotion companyDemotion = demotionDTO.getCompanyDemotion();
            if (Objects.isNull(companyDemotion.getCompanyId())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("进行升降级的供应商不能为空，请选择后重试。"));
            }
            if (StringUtils.isEmpty(companyDemotion.getDemotionType())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("升降级类型不能为空，请选择后重试。"));
            }
            // 2.2 升降级品类行校验 升降级品类不能为空
            List<CompanyDemotionCategory> companyDemotionCategories = demotionDTO.getCompanyDemotionCategories();
            if (CollectionUtils.isEmpty(companyDemotionCategories)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("升降级品类不能为空，请选择后重试。"));
            }
            // 2.3 升降级OU行校验 升降级业务实体不能为空
            List<CompanyDemotionOrg> companyDemotionOrgs = demotionDTO.getCompanyDemotionOrgs();
            if (CollectionUtils.isEmpty(companyDemotionOrgs)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("升降级业务实体不能为空，请选择后重试。"));
            }

            // 2.4 提交前单据状态校验
            // a.已提交、已审批、已废弃状态 不能提交
            // b.如果是降级至红牌或黑牌，降级时间必填
            if (connotSaveTemporaryMap.containsKey(companyDemotion.getStatus())) {
                String resultMsg = LocaleHandler.getLocaleMsg("供应商升降级[x]状态的单据不能提交，请检查后重试。", connotSaveTemporaryMap.get(companyDemotion.getStatus()));
                throw new BaseException(resultMsg);
            }

            String demotionType = companyDemotion.getDemotionType();
            Map<String, String> demotionToRedAndBlackMap = new HashMap<>();
            demotionToRedAndBlackMap.put(DemotionType.DEMOTION_TO_RED.getValue(), DemotionType.DEMOTION_TO_RED.getName());
            demotionToRedAndBlackMap.put(DemotionType.DEMOTION_TO_BLACK.getValue(), DemotionType.DEMOTION_TO_BLACK.getName());
            if (demotionToRedAndBlackMap.containsKey(demotionType)) {
                if (Objects.isNull(companyDemotion.getDemotionDate())) {
                    String resultMsg = LocaleHandler.getLocaleMsg("升降级类型为[x]时，升降级时间不能为空，请检查后重试。", demotionToRedAndBlackMap.get(demotionType));
                    throw new BaseException(resultMsg);
                } else {
                    LocalDate demotionDate = DateUtil.dateToLocalDate(companyDemotion.getDemotionDate());
                    LocalDate currentDate = DateUtil.dateToLocalDate(new Date());
                    if (demotionDate.compareTo(currentDate) < 0) {
                        String resultMsg = LocaleHandler.getLocaleMsg("升降级生效时间[x]不能早于今日，请选择后重试。", String.valueOf(demotionDate));
                        throw new BaseException(resultMsg);
                    }
                }
            }

        }

    }

    /**
     * 升降级单据删除
     * @param companyDemotionId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long companyDemotionId) {
        // 先删除行，再删除头
        // 1.删除OU行
        iCompanyDemotionOrgService.remove(new QueryWrapper<CompanyDemotionOrg>(
                new CompanyDemotionOrg().setCompanyDemotionId(companyDemotionId)

        ));
        // 2.删除品类行
        iCompanyDemotionCategoryService.remove(new QueryWrapper<>(
                new CompanyDemotionCategory().setCompanyDemotionId(companyDemotionId)
        ));
        // 3.删除单据头
        this.removeById(companyDemotionId);
    }

    /**
     * 升降级单据审批
     * @param companyDemotion
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(CompanyDemotion companyDemotion) {
        // 1.根据升降级类型不同，对组织品类关系表的操作不同
        String demotionType = companyDemotion.getDemotionType();

        List<CompanyDemotionOrg> companyDemotionCategoryList = iCompanyDemotionOrgService.list(Wrappers.lambdaQuery(CompanyDemotionOrg.class)
                .eq(CompanyDemotionOrg::getCompanyDemotionId, companyDemotion.getCompanyDemotionId()));

        Long companyId = companyDemotion.getCompanyId();
        // 1.1获取需要更新的数据
        List<OrgCategory> updateList = getOrgCategoryByCompanyAndDemotionOrgs(companyId, companyDemotionCategoryList);

        // a.降级至注册
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_REGISTERED.getValue())) {
            updateList.forEach(orgCategory -> {
                orgCategory.setServiceStatus(CategoryStatus.REGISTERED.name());
            });
            iOrgCategoryService.updateBatchById(updateList);
        }
        // b.降级至黄牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_YELLOW.getValue())) {
            updateList.forEach(orgCategory -> {
                orgCategory.setServiceStatus(CategoryStatus.YELLOW.name());
            });
            iOrgCategoryService.updateBatchById(updateList);
        }
        // 获取当前时间
        Date currentDate = new Date();
        Date demotionDate = companyDemotion.getDemotionDate();
        if (Objects.nonNull(demotionDate)){
            // 审批时间小于升降级生效时间，直接生效
            if (demotionDate.compareTo(currentDate) <= 0) {
                // c.降级至红牌
                if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_RED.getValue())) {
                    updateList.forEach(orgCategory -> {
                        orgCategory.setServiceStatus(CategoryStatus.RED.name());
                        companyDemotion.setIfDemotioned(YesOrNo.YES.getValue());
                        this.updateById(companyDemotion);
                    });
                    iOrgCategoryService.updateBatchById(updateList);
                }
                // d.降级至黑牌，同时供应商变为黑名单
                if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_BLACK.getValue())) {
                    updateList.forEach(orgCategory -> {
                        orgCategory.setServiceStatus(CategoryStatus.BLACK.name());
                    });
                    iOrgCategoryService.updateBatchById(updateList);
                    CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
                    companyDemotion.setIfDemotioned(YesOrNo.YES.getValue());
                    this.updateById(companyDemotion);
                    companyInfo.setIsBacklist(YesOrNo.YES.getValue());
                    iCompanyInfoService.updateById(companyInfo);
                }
            }
        }

        // e.黄牌供应商改善升级
        if (Objects.equals(demotionType, DemotionType.YELLOW_IMPROVE.getValue())) {
            updateList.forEach(orgCategory -> {
                orgCategory.setServiceStatus(CategoryStatus.GREEN.name());
            });
            iOrgCategoryService.updateBatchById(updateList);
        }

        // 2.更新单据状态（放在最后一步更新）
        companyDemotion.setStatus(ApproveStatusType.APPROVED.getValue());
        this.updateById(companyDemotion);

    }

    /**
     * 单据驳回
     * @param companyDemotion
     */
    @Override
    public void reject(CompanyDemotion companyDemotion) {
        companyDemotion.setStatus(ApproveStatusType.REJECTED.getValue());
        this.updateById(companyDemotion);
    }

    /**
     * 单据撤回
     * @param companyDemotion
     */
    @Override
    public void withDraw(CompanyDemotion companyDemotion) {
        companyDemotion.setStatus(ApproveStatusType.WITHDRAW.getValue());
        this.updateById(companyDemotion);
    }

    /**
     * 根据供应商id和升降级OU行查询orgCategory
     * @param companyId
     * @param companyDemotionOrgList
     */
    @Override
    public List<OrgCategory> getOrgCategoryByCompanyAndDemotionOrgs(Long companyId, List<CompanyDemotionOrg> companyDemotionOrgList) {
        // 根据供应商id过滤一部分数据出来
        List<OrgCategory> orgCategoriesByCompanyId = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
        );
        Map<Long, Map<Long, List<OrgCategory>>> map = orgCategoriesByCompanyId.stream().collect(Collectors.groupingBy(OrgCategory::getOrgId, Collectors.groupingBy(OrgCategory::getCategoryId)));

        List<OrgCategory> updateList = new ArrayList<>();

        List<CompanyDemotionOrg> filterdList = companyDemotionOrgList.stream().filter(x -> Objects.equals(YesOrNo.YES.getValue(), x.getEnableFlag())).collect(Collectors.toList());

        for (CompanyDemotionOrg companyDemotionOrg : filterdList) {
            Long orgId = companyDemotionOrg.getOrgId();
            Long categoryId = companyDemotionOrg.getCategoryId();
            if (map.containsKey(orgId) && Objects.nonNull(map.get(orgId))) {
                Map<Long, List<OrgCategory>> subMap = map.get(orgId);
                if (subMap.containsKey(categoryId) && CollectionUtils.isNotEmpty(subMap.get(categoryId))) {
                    List<OrgCategory> orgCategories = subMap.get(categoryId);
                    updateList.add(orgCategories.get(0));
                }
            }
        }
        log.info("修改的数目："+updateList.size());
        return updateList;
    }

    /**
     * 设置升降级单据的评审人字段
     * 包括评审人Ids，评审人工号，评审人nickNames
     * @param companyDemotion
     * @return
     */
    public CompanyDemotion setReviewUserFields(CompanyDemotion companyDemotion) {
        List<Long> reviewUserIdList = companyDemotion.getReviewUserIdList();
        if (CollectionUtils.isEmpty(reviewUserIdList)) {
            companyDemotion.setReviewUserIdList(Collections.EMPTY_LIST)
                    .setReviewUserIds(Strings.EMPTY)
                    .setReviewUserEmpNos(Strings.EMPTY)
                    .setReviewUserNicknames(Strings.EMPTY);
            return companyDemotion;
        }
        reviewUserIdList = reviewUserIdList.stream().distinct().collect(Collectors.toList());
        String userIds = StringUtils.join(reviewUserIdList, ";");
        companyDemotion.setReviewUserIds(userIds);

        List<User> users = rbacClient.getByUserIds(reviewUserIdList);
        List<String> ceeaEmpNoList = users.stream().map(User::getCeeaEmpNo).collect(Collectors.toList());
        String ceeaEmpNos = StringUtils.join(ceeaEmpNoList, ";");
        companyDemotion.setReviewUserEmpNos(ceeaEmpNos);
        List<String> nickNameList = users.stream().map(User::getNickname).distinct().collect(Collectors.toList());
        String nickNames = StringUtils.join(nickNameList, ";");
        companyDemotion.setReviewUserNicknames(nickNames);

        return companyDemotion;
    }

}
