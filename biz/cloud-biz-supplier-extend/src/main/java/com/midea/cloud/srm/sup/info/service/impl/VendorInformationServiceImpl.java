package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.SupplierDataSourceType;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.review.FormType;
import com.midea.cloud.common.enums.sup.DueDate;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.*;
import com.midea.cloud.srm.model.supplier.statuslog.entity.CompanyStatusLog;
import com.midea.cloud.srm.sup.info.mapper.VendorInformationMapper;
import com.midea.cloud.srm.sup.info.service.*;
import com.midea.cloud.srm.sup.quest.mapper.QuestSupplierMapper;
import com.midea.cloud.srm.sup.statuslog.service.ICompanyStatusLogService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  供应商档案ServiceImpl
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/9 20:24
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class VendorInformationServiceImpl extends ServiceImpl<VendorInformationMapper, VendorInformation> implements IVendorInformationService {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private ICompanyInfoDetailService iCompanyInfoDetailService;

    @Autowired
    private IManagementInfoService iManagementInfoService;

    @Autowired
    private IManagementAttachService iManagementAttachService;

    @Autowired
    private IBankInfoService iBankInfoService;

    @Autowired
    private IContactInfoService iContactInfoService;

    @Autowired
    private IFinanceInfoService iFinanceInfoService;

    @Autowired
    private IOperationInfoService iOperationInfoService;

    @Autowired
    private IOperationQualityService iOperationQualityService;

    @Autowired
    private IOperationProductService iOperationProductService;

    @Autowired
    private IOperationEquipmentService iOperationEquipmentService;

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private IOrgInfoService iOrgInfoService;

    @Autowired
    private IBusinessInfoService iBusinessInfoService;

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private ICompanyStatusLogService iCompanyStatusLogService;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private QuestSupplierMapper questSupplierMapper;

    @Override
    public PageInfo<CompanyInfo> listByDTO(CompanyRequestDTO companyRequestDTO) {
        List<CompanyInfo> companyInfoList = iCompanyInfoService.listByDTO(companyRequestDTO);
        if (CollectionUtils.isNotEmpty(companyInfoList)) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            List<Long> companyIdList = companyInfoList.stream().map(CompanyInfo::getCompanyId).collect(Collectors.toList());
            List<String> approvalStatusList = companyRequestDTO.getApprovalStatusList();
            if(CollectionUtils.isEmpty(approvalStatusList)) {
                approvalStatusList= Arrays.asList("APPROVED");
            }
            List<OrganizationUser> organizationUsers = loginAppUser.getOrganizationUsers();
            List<Long> orgIdList = null;
            if (CollectionUtils.isNotEmpty(organizationUsers)) {
                orgIdList = organizationUsers.stream().map(OrganizationUser::getOrganizationId).collect(Collectors.toList());
            } else {
                orgIdList = new ArrayList<>();
            }
            List<Long> companyIdTempList = questSupplierMapper.countQuestByCompanyId(approvalStatusList, companyIdList, orgIdList);
            for (CompanyInfo companyInfo : companyInfoList) {
                if (companyIdTempList.contains(companyInfo.getCompanyId())) {
                    companyInfo.setShowQuestSupplier(true);
                }
            }
        }
        return new PageInfo<CompanyInfo>(companyInfoList);
    }

    @Override
    public List<CategoryDTO> getCategoryListByCompanyId(Long companyId, Long categoryId) {
        Assert.notNull(companyId, "供应商编码为空！");
        List<CategoryDTO> categoryDTOList = this.baseMapper.getCategoryListByCompanyId(companyId, categoryId);
        return categoryDTOList;
    }

    /**
     * 编辑（修改）供应商档案时
     * 只允许修改供应商信息里的供应商分级和组织品类里的是否单一供方
     * @param infoDTO
     */
    @Override
    public void saveOrUpdateInformation(InfoDTO infoDTO) {
        CompanyInfo companyInfo = infoDTO.getCompanyInfo();
        iCompanyInfoService.updateById(companyInfo);
        List<OrgCategory> orgCategoryList = infoDTO.getOrgCategorys();
        if (!orgCategoryList.isEmpty()){
            iOrgCategoryService.updateBatchById(orgCategoryList);
        }
    }

    @Override
    @Transactional
    public void deleteVendorInformation(Long companyId) {
        /** 删除基本信息从表 companyInfoDetail 开始 **/
        QueryWrapper<CompanyInfoDetail> detailWrapper = new QueryWrapper<>();
        detailWrapper.eq("COMPANY_ID", companyId);
        iCompanyInfoDetailService.remove(detailWrapper);
        /** 删除基本信息从表 companyInfoDetail 结束 **/

        /** 删除管理体系信息 managementInfo 开始 **/
        QueryWrapper<ManagementInfo> managementInfoWrapper = new QueryWrapper<>();
        managementInfoWrapper.eq("COMPANY_ID", companyId);
        iManagementInfoService.remove(managementInfoWrapper);
        /** 删除管理体系信息 managementInfo 结束 **/

        /** 删除管理体系信息认证附件 managementAttaches List 开始 **/
        QueryWrapper<ManagementAttach> managementAttachWrapper = new QueryWrapper<>();
        managementAttachWrapper.eq("COMPANY_ID", companyId);
        iManagementAttachService.remove(managementAttachWrapper);
        /** 删除管理体系信息认证附件 managementAttaches List 结束 **/

        /** 删除银行信息的相关信息行 bankInfos List 开始 **/
        QueryWrapper<BankInfo> bankInfoWrapper = new QueryWrapper<>();
        bankInfoWrapper.eq("COMPANY_ID", companyId);
        iBankInfoService.remove(bankInfoWrapper);
        /** 删除银行信息的相关信息行 bankInfos List 结束 **/

        /** 删除联系人信息 contactInfos List 开始 **/
        QueryWrapper<ContactInfo> contactInfoWrapper = new QueryWrapper<>();
        contactInfoWrapper.eq("COMPANY_ID", companyId);
        iContactInfoService.remove(contactInfoWrapper);
        /** 删除联系人信息 contactInfos List 结束 **/

        /** 删除财务信息 financeInfos List 开始 **/
        QueryWrapper<FinanceInfo> financeInfoWrapper = new QueryWrapper<>();
        financeInfoWrapper.eq("COMPANY_ID", companyId);
        iFinanceInfoService.remove(financeInfoWrapper);
        /** 删除财务信息 financeInfos List 结束 **/

        /** 删除经营信息 operationInfo 开始 **/
        QueryWrapper<OperationInfo> operationInfoWrapper = new QueryWrapper<>();
        operationInfoWrapper.eq("COMPANY_ID", companyId);
        iOperationInfoService.remove(operationInfoWrapper);
        /** 删除经营信息 operationInfo 结束 **/

        /** 删除经营信息质量控制信息 operationQualities List 开始 **/
        QueryWrapper<OperationQuality> operationQualityWrapper = new QueryWrapper<>();
        operationQualityWrapper.eq("COMPANY_ID", companyId);
        iOperationQualityService.remove(operationQualityWrapper);
        /** 删除经营信息质量控制信息 operationQualities List 结束 **/

        /** 删除经营信息产品信息 operationProducts List 开始 **/
        QueryWrapper<OperationProduct> operationProductWrapper = new QueryWrapper<>();
        operationProductWrapper.eq("COMPANY_ID", companyId);
        iOperationProductService.remove(operationProductWrapper);
        /** 删除经营信息产品信息 operationProducts List 结束 **/

        /** 删除经营信息设备信息 operationEquipments List 开始 **/
        QueryWrapper<OperationEquipment> OperationEquipmentWrapper = new QueryWrapper<>();
        OperationEquipmentWrapper.eq("COMPANY_ID", companyId);
        iOperationEquipmentService.remove(OperationEquipmentWrapper);
        /** 删除经营信息设备信息 operationEquipments List 结束 **/

        /** 删除组织与品类信息 orgCategorys List 开始 **/
        QueryWrapper<OrgCategory> orgCategoryWrapper = new QueryWrapper<>();
        orgCategoryWrapper.eq("COMPANY_ID", companyId);
        iOrgCategoryService.remove(orgCategoryWrapper);
        /** 删除组织与品类信息 orgCategorys List 结束 **/

        /** 删除合作组织信息 orgInfos List 开始 **/
        QueryWrapper<OrgInfo> orgInfoWrapper = new QueryWrapper<>();
        orgInfoWrapper.eq("COMPANY_ID", companyId);
        iOrgInfoService.remove(orgInfoWrapper);
        /** 删除合作组织信息 orgInfos List 结束 **/

        /** 删除业务信息 businessInfos List 开始 **/
        QueryWrapper<BusinessInfo> businessInfoWrapper = new QueryWrapper<>();
        businessInfoWrapper.eq("COMPANY_ID", companyId);
        iBusinessInfoService.remove(businessInfoWrapper);
        /** 删除业务信息 orgInfos List 结束 **/

        /** 删除用户信息 userInfo 开始 **/
        rbacClient.deleteByCompanyId(companyId);
        /** 删除用户信息 userInfo 结束 **/

        /** 删除文件上传信息 fileUploads List 开始 **/
        Fileupload fileupload = new Fileupload();
        fileupload.setBusinessId(companyId);
        fileCenterClient.deleteByParam(fileupload);
        /** 删除文件上传信息 fileUploads List 结束 **/

        /** 删除操作历史信息 companyStatusLogs List 开始 **/
        QueryWrapper<CompanyStatusLog> companyStatusLogWrapper = new QueryWrapper<>();
        companyStatusLogWrapper.eq("COMPANY_ID", companyId);
        iCompanyStatusLogService.remove(companyStatusLogWrapper);
        /** 删除操作历史信息 companyStatusLogs List 结束 **/

        /** 最后删除公司信息主表 companyInfo 开始 **/
        iCompanyInfoService.removeById(companyId);
        /** 最后删除公司信息主表 companyInfo 开始 **/
    }

    @Override
    public void vendorInformationApprove(Long companyId) {
        CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
        String oldStatus = companyInfo.getStatus();
        Assert.isTrue(oldStatus.equals(ApproveStatusType.SUBMITTED.getValue()), "只有已提交状态才能进行审批！");
        companyInfo.setStatus(ApproveStatusType.APPROVED.getValue());
        companyInfo.setStatusName(ApproveStatusType.APPROVED.getName());
        // 审批通过时 生成供应商编码
        if (StringUtils.isEmpty(companyInfo.getCompanyCode())) {
            companyInfo.setCompanyCode(baseClient.seqGenForAnon(SequenceCodeConstant.SEQ_SUP_COMPANY_CODE));
        }
        iCompanyInfoService.updateById(companyInfo);

        //添加组织品类关系
        List<OrgCategory> orgCategories = iOrgCategoryService.list(new QueryWrapper<>(new OrgCategory().setCompanyId(companyId)));
        String orgCategoryStatus = "";
        String formType = "";
        if(SupplierDataSourceType.MANUALLY_CREATE.name().equals(companyInfo.getDataSources()) && ApproveStatusType.APPROVED.getValue().equals(companyInfo.getStatus())) {
            orgCategoryStatus = CategoryStatus.GREEN.name();
            formType = FormType.GREEN_CHANNEL.name();
        }
        if (SupplierDataSourceType.ONESELF_REGISTER.name().equals(companyInfo.getDataSources())) {
            orgCategoryStatus = CategoryStatus.REGISTERED.name();
            formType = FormType.SUPPLIER_REGISTRATION.name();
        }
        if (CollectionUtils.isNotEmpty(orgCategories)) {
            for (OrgCategory orgCategory : orgCategories) {
                if (orgCategory == null) continue;
                OrgCategorySaveDTO orgCategorySaveDTO = new OrgCategorySaveDTO();
                orgCategorySaveDTO.setOrgCategory(new OrgCategory()
                        .setCompanyId(companyInfo.getCompanyId())
                        .setOrgId(orgCategory.getOrgId())
                        .setOrgName(orgCategory.getOrgName())
                        .setOrgCode(orgCategory.getOrgCode())
                        .setCategoryId(orgCategory.getCategoryId())
                        .setCategoryCode(orgCategory.getCategoryCode())
                        .setCategoryName(orgCategory.getCategoryName())
                        .setCategoryFullName(orgCategory.getCategoryFullName())
                        .setCategoryFullId(orgCategory.getCategoryFullId())
                        .setServiceStatus(orgCategoryStatus));//ToDo
                List<OrgCategoryChange> orgCategoryChanges = new ArrayList<>();
                orgCategoryChanges.add(new OrgCategoryChange().setFormType(formType).setFormNum(companyInfo.getCompanyCode()));
                orgCategorySaveDTO.setOrgCategoryChanges(orgCategoryChanges);
                iOrgCategoryService.collectOrgCategory(orgCategorySaveDTO);
            }
        }
    }

    @Override
    public PageInfo<ManagementAttach> listManagementAttachPageByDTO(ManagementAttachRequestDTO managementAttachRequestDTO) {
        PageUtil.startPage(managementAttachRequestDTO.getPageNum(), managementAttachRequestDTO.getPageSize());
//        String dueDate = managementAttachRequestDTO.getDueDate();
//        Date nowdate = new Date();
//        Calendar futureCalendar = Calendar.getInstance();
//        Calendar pastCalendar = Calendar.getInstance();
//        futureCalendar.setTime(nowdate);
//        pastCalendar.setTime(nowdate);
//
//        Date newFutureDueDate = nowdate;
//        Date newPastDueDate = nowdate;
//        if (dueDate != null){
//            switch (dueDate){
//                case "ONE_MONTH" : {
//                    futureCalendar.add(Calendar.MONTH, 1);
//                    pastCalendar.add(Calendar.MONTH, -1);
//                    newFutureDueDate = futureCalendar.getTime();
//                    newPastDueDate = pastCalendar.getTime();
//                    break;
//                }
//                case "THREE_MONTHS": {
//                    futureCalendar.add(Calendar.MONTH, 3);
//                    pastCalendar.add(Calendar.MONTH, -3);
//                    newFutureDueDate = futureCalendar.getTime();
//                    newPastDueDate = pastCalendar.getTime();
//                    break;
//                }
//                case "SIX_MONTHS": {
//                    futureCalendar.add(Calendar.MONTH, 6);
//                    pastCalendar.add(Calendar.MONTH, -6);
//                    newFutureDueDate = futureCalendar.getTime();
//                    newPastDueDate = pastCalendar.getTime();
//                    break;
//                }
//            }
//        }
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dueDateFutureString = simpleDateFormat.format(newFutureDueDate);
//        String dueDatePastString = simpleDateFormat.format(newPastDueDate);
//        managementAttachRequestDTO.setFutureDate(dueDateFutureString);
//        managementAttachRequestDTO.setPastDate(dueDatePastString);
//        List<ManagementAttach> managementAttachList = this.baseMapper.listAttachByDTO(managementAttachRequestDTO);
        List<ManagementAttach> managementAttachList = this.baseMapper.listAttachMix(managementAttachRequestDTO);
        return new PageInfo<ManagementAttach>(managementAttachList);
    }

    @Override
    public List<ManagementAttach> listAllManagementAttachByDTO(ManagementAttach managementAttach) {
        Map<String,Object> map =new HashMap<>();
        map.put("COMPANY_ID",managementAttach.getCompanyId());
        return iManagementAttachService.listByMap(map);
    }

    /**
     * 供应商清单驳回 从已提交SUBMITTED驳回为DRAFT
     * @param companyId
     */
    @Override
    public void rejectVendorInformation(Long companyId) {
        CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
        String oldStatus = companyInfo.getStatus();
        Assert.isTrue(oldStatus.equals(ApproveStatusType.SUBMITTED.getValue()), "只有待审核状态才能进行驳回！");

        // 删除供应商组织品类关系数据
        iOrgCategoryService.remove(Wrappers.lambdaQuery(OrgCategory.class).eq(OrgCategory::getCompanyId, companyId));

        // 将供应商状态设置为DRAFT
        companyInfo.setStatus(ApproveStatusType.DRAFT.getValue());
        companyInfo.setStatusName(ApproveStatusType.DRAFT.getName());
        iCompanyInfoService.updateById(companyInfo);
    }
}
