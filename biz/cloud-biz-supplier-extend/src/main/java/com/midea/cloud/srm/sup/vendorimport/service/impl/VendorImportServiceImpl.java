package com.midea.cloud.srm.sup.vendorimport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.sup.VendorImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportCategoryDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.OrgDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImportDetail;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import com.midea.cloud.srm.sup.vendorimport.mapper.VendorImportMapper;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportDetailService;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportService;
import com.midea.cloud.srm.sup.vendorimport.workflow.VendorImportFlow;
import io.swagger.models.auth.In;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <pre>
 *  供应商引入表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-13 18:08:07
 *  修改内容:
 * </pre>
 */
@Service
public class VendorImportServiceImpl extends ServiceImpl<VendorImportMapper, VendorImport> implements IVendorImportService {

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private IVendorImportDetailService iVendorImportDetailService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private FileCenterClient fileCenterClient;
    @Autowired
    private VendorImportFlow vendorImportFlow;

    @Override
    @AuthData(module = MenuEnum.CROSS_ORG_IMPORT)
    public PageInfo<VendorImport> listPageByParam(VendorImport vendorImport) {
        PageUtil.startPage(vendorImport.getPageNum(), vendorImport.getPageSize());
        QueryWrapper<VendorImport> queryWrapper = new QueryWrapper<>();
        if (vendorImport.getImportNum() != null) {
            queryWrapper.like("IMPORT_NUM", vendorImport.getImportNum());
        }
        if (vendorImport.getVendorName() != null) {
            queryWrapper.like("VENDOR_NAME", vendorImport.getVendorName());
        }
        if (vendorImport.getImportStatus() != null) {
            queryWrapper.eq("IMPORT_STATUS", vendorImport.getImportStatus());
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(queryWrapper));
    }

    @Override
    public VendorImportSaveDTO getVendorImportDetail(Long importId) {
        VendorImportSaveDTO dto = new VendorImportSaveDTO();
        if (importId != null) {
            /** 查询头表信息 开始**/
            VendorImport vendorImport = this.getById(importId);
            /** 查询头表信息 结束**/

            Set<String> orgCodeSet = new HashSet<>();
            /** 查询详情业务实体品类信息列表 开始**/
            QueryWrapper<VendorImportDetail> importDetailWrapper = new QueryWrapper<>();
            importDetailWrapper.eq("IMPORT_ID", importId);
            List<VendorImportDetail> vendorImportDetails = iVendorImportDetailService.list(importDetailWrapper)
                    .stream().filter(r -> !orgCodeSet.contains(r.getOrgCode()) && orgCodeSet.add(r.getOrgCode())).collect(Collectors.toList());
            /** 查询详情业务实体品类信息列表 结束**/

            /** 查询附件列表开始**/
            PageInfo<Fileupload> listPage = fileCenterClient.listPage(new Fileupload().setBusinessId(importId), "");
            List<Fileupload> fileuploads = listPage.getList();
            /** 查询附件列表结束**/

            dto.setVendorImport(vendorImport);
            dto.setVendorImportDetails(vendorImportDetails);
            dto.setFileuploads(fileuploads);
        }
        return dto;
    }

    @Override
    public List<OrgCategory> listOrgCategoryByParam(OrgCategoryQueryDTO orgCategoryQueryDTO) {
        List<String> categoryStatusList = new ArrayList<>();
        categoryStatusList.add(CategoryStatus.REGISTERED.name());
        categoryStatusList.add(CategoryStatus.GREEN.name());
        categoryStatusList.add(CategoryStatus.YELLOW.name());
        QueryWrapper<OrgCategory> orgCategoryQueryWrapper = new QueryWrapper<>(new OrgCategory().
                setOrgId(orgCategoryQueryDTO.getOrgId()).
                setCompanyId(orgCategoryQueryDTO.getCompanyId()));
        orgCategoryQueryWrapper.in("SERVICE_STATUS", categoryStatusList);
        List<OrgCategory> orgCategories = iOrgCategoryService.list(orgCategoryQueryWrapper);
        return orgCategories;
    }

    @Override
    @Transactional
    public Long saveTemporary(VendorImportSaveDTO vendorImportSaveDTO, String vendorImportStatus) {
        VendorImport vendorImport = vendorImportSaveDTO.getVendorImport();
        List<VendorImportDetail> vendorImportDetails = vendorImportSaveDTO.getVendorImportDetails();
        List<Fileupload> fileuploads = vendorImportSaveDTO.getFileuploads();
        Assert.notNull(vendorImport, LocaleHandler.getLocaleMsg("供应商扩展信息vendorImport不能为空"));
        Assert.notEmpty(vendorImportDetails, LocaleHandler.getLocaleMsg("引入实体信息vendorImportDetails不能为空"));

        Long vendorImportId = saveOrUpdateVendorImport(vendorImportStatus, vendorImport);
        saveOrUpdateVendorImportDetail(vendorImportDetails, vendorImportId);
        if (CollectionUtils.isNotEmpty(fileuploads)) {
            fileCenterClient.bindingFileupload(fileuploads, vendorImportId);
        }
        return vendorImportId;
    }

    /**
     * 提交（可能在新增的时候直接提交）
     *
     * @param vendorImportSaveDTO
     * @param vendorImportStatus
     * @return
     */
    @Override
    public Long submit(VendorImportSaveDTO vendorImportSaveDTO, String vendorImportStatus) {
        VendorImport vendorImport = vendorImportSaveDTO.getVendorImport();
        List<VendorImportDetail> vendorImportDetails = vendorImportSaveDTO.getVendorImportDetails();
        List<Fileupload> fileuploads = vendorImportSaveDTO.getFileuploads();

        Assert.notNull(vendorImport, LocaleHandler.getLocaleMsg("vendorImport不能为空"));
        Assert.notEmpty(vendorImportDetails, LocaleHandler.getLocaleMsg("vendorImportDetails不能为空"));

        Long vendorImportId = vendorImport.getImportId();
        vendorImport.setImportStatus(vendorImportStatus);
        //如果vendorImportId为空，说明是首次新增进来就直接提交了
        if (vendorImportId == null) {
            vendorImportId = saveOrUpdateVendorImport(vendorImportStatus, vendorImport);
            saveOrUpdateVendorImportDetail(vendorImportDetails, vendorImportId);
            if (CollectionUtils.isNotEmpty(fileuploads)) {
                fileCenterClient.bindingFileupload(fileuploads, vendorImportId);
            }
        }
        //否则，说明是之前暂存（保存）之后进行了提交（但是可能删除了某些行之后又添加了某些行）
        else {
            this.updateById(vendorImport);
            saveOrUpdateVendorImportDetail(vendorImportDetails, vendorImport.getImportId());
            if (CollectionUtils.isNotEmpty(fileuploads)) {
                fileCenterClient.bindingFileupload(fileuploads, vendorImportId);
            }
        }

        /* Begin by chenwt24@meicloud.com   2020-10-16 */

        String formId = null;
        try {
            formId = vendorImportFlow.submitVendorImportDTOFlow(vendorImportSaveDTO);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        if (StringUtils.isEmpty(formId)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));

        }

        /* End by chenwt24@meicloud.com     2020-10-16 */

        return vendorImportId;
    }

    @Override
    @Transactional
    public void approve(Long importId) {
        VendorImport vendorImport = this.getById(importId);
        String importStatus = vendorImport.getImportStatus();
        Assert.isTrue(importStatus.equals(VendorImportStatus.SUBMITTED.getValue()), "只有已提交状态才能审批！");
        vendorImport.setImportStatus(VendorImportStatus.APPROVED.getValue());

        /** 将组织品类关系维护到组织与品类表org_category 开始 **/

        //得到供应商Id
        Long vendorId = vendorImport.getVendorId();
        //得到头表上的旧的业务实体
        Long oldOrgId = vendorImport.getOldOrgId();
        //得到在新业务实体上引入的品类列表
        QueryWrapper<VendorImportDetail> detailQueryWrapper = new QueryWrapper<>();
        detailQueryWrapper.eq("IMPORT_ID", importId);
        List<VendorImportDetail> detailList = iVendorImportDetailService.list(detailQueryWrapper);

        if (!detailList.isEmpty()) {
            for (VendorImportDetail detail : detailList) {

                Long categoryId = detail.getCategoryId();
                String categoryCode = detail.getCategoryCode();
                String categoryName = detail.getCategoryName();
                String categoryFullId = detail.getCategoryFullId();
                String categoryFullName = detail.getCategoryFullName();

                QueryWrapper<OrgCategory> orgCategoryQueryWrapper = new QueryWrapper<>();
                orgCategoryQueryWrapper.eq("COMPANY_ID", vendorId)
                        .eq("ORG_ID", oldOrgId)
                        .eq("CATEGORY_ID", categoryId);
                List<OrgCategory> orgCategoryList = iOrgCategoryService.list(orgCategoryQueryWrapper);
                //得到这个供应商在元业务实体这个品类上的服务状态
                String serviceStatus = "";
                if (!orgCategoryList.isEmpty() && orgCategoryList.get(0) != null) {
                    serviceStatus = orgCategoryList.get(0).getServiceStatus();
                }

                Long orgId = detail.getOrgId();
                String orgCode = detail.getOrgCode();
                String orgName = detail.getOrgName();

                OrgCategory saveOrUpdateOrgCategory = new OrgCategory();
                saveOrUpdateOrgCategory.setCompanyId(vendorId);
                saveOrUpdateOrgCategory.setOrgId(orgId);
                saveOrUpdateOrgCategory.setCategoryId(categoryId);
                //查询这一条新引入的品类
                QueryWrapper<OrgCategory> saveOrUpdateWrapper = new QueryWrapper<>(saveOrUpdateOrgCategory);
                List<OrgCategory> repeatList = iOrgCategoryService.list(saveOrUpdateWrapper);
                if (repeatList.isEmpty()) {
                    Long orgCategoryId = IdGenrator.generate();
                    saveOrUpdateOrgCategory.setOrgCategoryId(orgCategoryId)
                            .setOrgCode(orgCode)
                            .setOrgName(orgName)
                            .setCategoryCode(categoryCode)
                            .setCategoryName(categoryName)
                            .setCategoryFullId(categoryFullId)
                            .setServiceStatus(serviceStatus)
                            .setCategoryFullName(categoryFullName);
                    iOrgCategoryService.save(saveOrUpdateOrgCategory);
                } else {
                    Long orgCategoryId = repeatList.get(0).getOrgCategoryId();
                    saveOrUpdateOrgCategory.setOrgCategoryId(orgCategoryId)
                            .setOrgCode(orgCode)
                            .setOrgName(orgName)
                            .setCategoryCode(categoryCode)
                            .setCategoryName(categoryName)
                            .setCategoryFullId(categoryFullId)
                            .setServiceStatus(serviceStatus)
                            .setCategoryFullName(categoryFullName);
                    iOrgCategoryService.updateById(saveOrUpdateOrgCategory);
                }
                //这一条新引入的品类 使用这个供应商在原来业务实体上这个品类的服务状态
            }
        }

        /** 将组织品类关系维护到组织与品类表org_category 结束 **/

        //更新这条头表引入行的状态为APPROVED 回写到头表
        this.updateById(vendorImport);
    }

    @Override
    public void reject(Long importId, String opinion) {
        this.updateById(this.getById(importId).setImportStatus(VendorImportStatus.REJECTED.getValue()));
    }

    @Override
    public void withdraw(Long importId, String opinion) {
        this.updateById(this.getById(importId).setImportStatus(VendorImportStatus.WITHDRAW.getValue()));
    }

    @Override
    public void delete(Long importId) {
        QueryWrapper<VendorImportDetail> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("IMPORT_ID", importId);
        iVendorImportDetailService.remove(deleteWrapper);
        this.removeById(importId);
    }


    /**
     * 废弃订单
     *
     * @param importId
     */
    @Transactional
    @Override
    public void abandon(Long importId) {
        VendorImportSaveDTO vendorImportSaveDTO = this.getVendorImportDetail(importId);
        VendorImport vendorImport = vendorImportSaveDTO.getVendorImport();
        Assert.notNull(ObjectUtils.isEmpty(vendorImport), "找不到废弃的订单");
        String importStatus = vendorImport.getImportStatus();
        Assert.isTrue(VendorImportStatus.WITHDRAW.toString().equals(importStatus) || VendorImportStatus.REJECTED.toString().equals(importStatus), "只有已驳回，已撤回的订单才可以撤回。");
        vendorImport.setImportStatus(VendorImportStatus.ABANDONED.toString());
        this.updateById(vendorImport);
        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(importId);
        if (srmworkflowForm != null) {
            try {
                vendorImportSaveDTO.setProcessType("N");
                vendorImportFlow.submitVendorImportDTOFlow(vendorImportSaveDTO);
            } catch (Exception e) {
                Assert.isTrue(false, "废弃同步审批流失败。");
            }
        }
    }

    /**
     * 根据vendorId去OrgCategory表里查询合作的Org（业务实体）
     *
     * @param vendorId
     * @return
     */
    @Override
    public List<OrgDTO> getOrgByVendorId(Long vendorId) {
        List<OrgDTO> orgList = this.getBaseMapper().getOrgListByVendorId(vendorId);
        return orgList;
    }

    @Override
    public List<VendorImportCategoryDTO> getCategoryListByParams(Long vendorId, Long orgId) {
        List<VendorImportCategoryDTO> categoryList = this.getBaseMapper().getCategoryListByParams(vendorId, orgId);
        return categoryList;
    }

    private void saveOrUpdateVendorImportDetail(List<VendorImportDetail> vendorImportDetails, Long vendorImportId) {
        //1.校验
        for (VendorImportDetail detail : vendorImportDetails) {
            if (detail.getOrgId() == null || detail.getOrgCode() == null || detail.getOrgName() == null) {
                throw new BaseException("业务实体不能为空，请选择业务实体！");
            }
//            if (detail.getCategoryId() == null || detail.getCategoryCode() == null || detail.getCategoryCode() == null){
//                throw new BaseException("引入品类不能为空，请选择引入品类！");
//            }
        }
        //结束 保存（暂存）前校验 OU和品类必填

        //开始 保存跨组织引入行
        //2.获取业务实体下的品类信息
        VendorImport vendorImport = this.getById(vendorImportId);
        OrgCategoryQueryDTO orgCategoryQuery = new OrgCategoryQueryDTO();
        orgCategoryQuery.setOrgId(vendorImport.getOldOrgId()).setCompanyId(vendorImport.getVendorId());
        List<OrgCategory> orgCategoryList = this.listOrgCategoryByParam(orgCategoryQuery);

        //3.获取供应商引入明细表 记录
        List<VendorImportDetail> dbDetailRecord = iVendorImportDetailService.list(
                new QueryWrapper<>(new VendorImportDetail().setImportId(vendorImportId))
        );

        Set<String> dbOrgCodeSet = dbDetailRecord.stream().map(
                r -> r.getOrgCode()
        ).collect(Collectors.toSet());

        Map<String, VendorImportDetail> orgCategoryMap = dbDetailRecord.stream()
                .collect(Collectors.toMap(
                        r -> new StringBuffer()
                                .append(r.getOrgCode())
                                .append(r.getCategoryId())
                                .toString(),
                        r -> r,
                        (o, o2) -> o)
                );
        //4.记录中已存在 业务实体品类记录 计数
        Map<String, Integer> orgCategoryCount = Optional.ofNullable(dbDetailRecord).orElseGet(() -> {
            return new ArrayList<>();
        }).stream().collect(Collectors.toMap(r -> r.getOrgCode(), r -> 0, (o, o2) -> o));

        dbDetailRecord.forEach(r -> {
            Integer count = orgCategoryCount.get(r.getOrgCode());
            orgCategoryCount.put(r.getOrgCode(), count + 1);
        });
        //5. 保存
        List<VendorImportDetail> addVendorImportDetails = new ArrayList<>();
        vendorImportDetails.forEach(vendorImportDetail -> {
            //不存在任何品类记录-都存
            boolean notExistAnyFlag = Objects.nonNull(vendorImportDetail) && !dbOrgCodeSet.contains(vendorImportDetail.getOrgCode());
            //存在部分品类关系-存没有记录的
            boolean hasSomeFlag = Objects.nonNull(vendorImportDetail) &&
                    dbOrgCodeSet.contains(vendorImportDetail.getOrgCode()) &&
                    orgCategoryCount.get(vendorImportDetail.getOrgCode()).compareTo(orgCategoryList.size()) == -1;
            orgCategoryList.forEach(orgCategory -> {

                VendorImportDetail importDetail = new VendorImportDetail();
                BeanUtils.copyProperties(vendorImportDetail, importDetail);
                importDetail.setImportDetailId(IdGenrator.generate())
                        .setImportId(vendorImportId)
                        .setCategoryId(orgCategory.getCategoryId())
                        .setCategoryName(orgCategory.getCategoryName())
                        .setDivision(vendorImportDetail.getDivision())
                        .setCategoryCode(orgCategory.getCategoryCode());
                //当前品类关系是否存在数据库中
                boolean itOrgCategoryHasFlag = orgCategoryMap.containsKey(new StringBuffer()
                        .append(orgCategory.getOrgCode()).append(orgCategory.getCategoryId()).toString());
                if (notExistAnyFlag) {
                    addVendorImportDetails.add(importDetail);

                } else if (hasSomeFlag && !itOrgCategoryHasFlag) {
                    addVendorImportDetails.add(importDetail);
                }
            });
        });
        if (!addVendorImportDetails.isEmpty()) {
            iVendorImportDetailService.saveBatch(addVendorImportDetails);
        }
        //结束 保存跨组织引入行
    }

    private Long saveOrUpdateVendorImport(String vendorImportStatus, VendorImport vendorImport) {
        long vendorImportId = IdGenrator.generate();
        if (vendorImport.getImportId() == null) {
            vendorImport.setImportId(vendorImportId)
                    .setImportNum(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_VENDOR_IMPORT_CODE))
                    .setImportStatus(vendorImportStatus);
            this.save(vendorImport);
        } else {
            vendorImportId = vendorImport.getImportId();
            vendorImport.setImportStatus(vendorImportStatus);
            //更换了【原业务实体】，清空之前保存的记录
            if (!Objects.equals(this.getById(vendorImportId).getOldOrgCode(), vendorImport.getOldOrgCode())) {
                iVendorImportDetailService.remove(new QueryWrapper<>(new VendorImportDetail().setImportId(vendorImportId)));
            }
            this.updateById(vendorImport);
        }
        return vendorImportId;
    }

    /**
     * 刷新组织品类关系数据(批量)
     */
    @Override
    public void refresh() {
        List<VendorImport> vendorImportList = this.list(Wrappers.lambdaQuery(VendorImport.class)
                .lt(VendorImport::getLastUpdateDate, "2020-11-17 00:00:00"));
        vendorImportList.forEach(vendorImport -> {
            refreshData(vendorImport);
        });
    }

    public void refreshData(VendorImport vendorImport) {
        Long companyId = vendorImport.getVendorId();

        Long importId = vendorImport.getImportId();

        Long oldOrgId = vendorImport.getOldOrgId();

        // 获取行表数据集
        List<VendorImportDetail> vendorImportDetailList = iVendorImportDetailService.list(Wrappers.lambdaQuery(VendorImportDetail.class)
                .eq(VendorImportDetail::getImportId, importId));

        vendorImportDetailList.forEach(vendorImportDetail -> {
            Long categoryId = vendorImportDetail.getCategoryId();
            PurchaseCategory category = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(categoryId));

            // 获取引入至ou
            Long newOrgId = vendorImportDetail.getOrgId();
            Organization organization = baseClient.getOrganization(new Organization().setOrganizationId(newOrgId));

            // 获取这个供应商旧的ou在这个品类上的状态
            List<OrgCategory> oldOrgCategoryList = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .eq(OrgCategory::getOrgId, oldOrgId)
                    .eq(OrgCategory::getCategoryId, categoryId));
            String status = "";
            if (CollectionUtils.isNotEmpty(oldOrgCategoryList) && Objects.nonNull(oldOrgCategoryList.get(0))) {
                status = oldOrgCategoryList.get(0).getServiceStatus();
                List<OrgCategory> newOrgCategoryList = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                        .eq(OrgCategory::getCompanyId, companyId)
                        .eq(OrgCategory::getOrgId, newOrgId)
                        .eq(OrgCategory::getCategoryId, categoryId));
                if (CollectionUtils.isNotEmpty(newOrgCategoryList) && Objects.nonNull(newOrgCategoryList.get(0))){
                    OrgCategory updateOrgCategory = newOrgCategoryList.get(0);
                    updateOrgCategory.setServiceStatus(status);
                    iOrgCategoryService.updateById(updateOrgCategory);
                } else {
                    OrgCategory saveOrgCategory = new OrgCategory();
                    saveOrgCategory.setOrgCategoryId(IdGenrator.generate())
                            .setCompanyId(companyId)
                            .setOrgId(newOrgId).setOrgCode(organization.getOrganizationCode()).setOrgName(organization.getOrganizationName())
                            .setCategoryId(categoryId).setCategoryCode(category.getCategoryCode()).setCategoryName(category.getCategoryName())
                            .setCategoryFullId(category.getStruct()).setCategoryFullName(category.getCategoryFullName()).setServiceStatus(status);
                    iOrgCategoryService.save(saveOrgCategory);
                }
            }

        });
    }

    /**
     * 刷新组织品类关系数据
     * @param vendorImportList
     */
    @Override
    public void refreshByImportNum(List<VendorImport> vendorImportList) {
        if (CollectionUtils.isNotEmpty(vendorImportList)){
            vendorImportList.forEach(vendorImport -> {
                refreshData(vendorImport);
            });
        }
    }

}
