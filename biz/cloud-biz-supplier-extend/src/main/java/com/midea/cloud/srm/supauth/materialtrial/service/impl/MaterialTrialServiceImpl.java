package com.midea.cloud.srm.supauth.materialtrial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.CatalogStatusType;
import com.midea.cloud.common.enums.OrgCateBillType;
import com.midea.cloud.common.enums.SampleStatusType;
import com.midea.cloud.common.enums.SampleTestResultType;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.MaterialTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.RequestTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalog;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.midea.cloud.srm.supauth.materialtrial.mapper.MaterialTrialMapper;
import com.midea.cloud.srm.supauth.materialtrial.service.IMaterialTrialService;
import com.midea.cloud.srm.supauth.purchasecatalog.service.IPurchaseCatalogService;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;

import feign.FeignException;

/**
 *  <pre>
 *  物料试用表 服务实现类
 * </pre>
 *
 * @author zhuwl7@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 10:59:47
 *  修改内容:
 * </pre>
 */
@Service
public class MaterialTrialServiceImpl extends ServiceImpl<MaterialTrialMapper, MaterialTrial> implements IMaterialTrialService {
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IPurchaseCatalogService iPurchaseCatalogService;

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private WorkFlowFeign workFlowFeign;
    
    @Autowired
    private IOrgCateJournalService iOrgCateJournalService;
    
    @Autowired
    private IFileRecordService iFileRecordService;
    
    @Autowired
    private IQuaSampleService iQuaSampleService;

    @Override
    public PageInfo<MaterialTrial> listPageByParam(RequestTrialDTO requestTrialDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            if(loginAppUser.getCompanyId() != null) {
                requestTrialDTO.setVendorId(loginAppUser.getCompanyId());
            }else{
                requestTrialDTO.setVendorId(-1L);
            }
        }
        QueryWrapper<MaterialTrial> wrapper = new QueryWrapper<MaterialTrial>();
        if(requestTrialDTO.getPurchaseOrgId() != null){
            wrapper.eq("PURCHASE_ORG_ID",requestTrialDTO.getPurchaseOrgId());
        }
        if(StringUtils.isNoneBlank(requestTrialDTO.getPurchaseOrgCode())){
            wrapper.eq("PURCHASE_ORG_CODE",requestTrialDTO.getPurchaseOrgCode());
        }
        if(StringUtils.isNoneBlank(requestTrialDTO.getTrialNumber())){
            wrapper.like("TRIAL_NUMBER",requestTrialDTO.getTrialNumber());
        }
        if(requestTrialDTO.getVendorId() != null){
            wrapper.eq("VENDOR_ID",requestTrialDTO.getVendorId());
        }
        if(StringUtils.isNotBlank(requestTrialDTO.getVendorName())){
            wrapper.like("VENDOR_NAME",requestTrialDTO.getVendorName());
        }
        if(StringUtils.isNotBlank(requestTrialDTO.getVendorCode())){
            wrapper.like("VENDOR_CODE",requestTrialDTO.getVendorCode());
        }
        if(StringUtils.isNoneBlank(requestTrialDTO.getApproveStatus())){
            wrapper.eq("APPROVE_STATUS",requestTrialDTO.getApproveStatus());
        }
        if(requestTrialDTO.getCategoryId() != null){
            wrapper.eq("CATEGORY_ID",requestTrialDTO.getCategoryId());
        }
        if(StringUtils.isNoneBlank(requestTrialDTO.getCategoryCode())){
            wrapper.eq("CATEGORY_CODE",requestTrialDTO.getCategoryId());
        }
        if(requestTrialDTO.getMaterialId() != null){
            wrapper.eq("MATERIAL_ID",requestTrialDTO.getMaterialId());
        }
        if(StringUtils.isNoneBlank(requestTrialDTO.getMaterialCode())){
            wrapper.eq("MATERIAL_CODE",requestTrialDTO.getMaterialCode());
        }
        if(requestTrialDTO.getStartDate() != null){
            wrapper.ge("TRIAL_START_DATE",requestTrialDTO.getStartDate());
        }
        if(requestTrialDTO.getEndDate() != null){
            wrapper.ge("TRIAL_START_DATE",requestTrialDTO.getEndDate());
        }
        if(StringUtils.isNotBlank(requestTrialDTO.getTrialResult())){
            wrapper.eq("TRIAL_RESULT",requestTrialDTO.getTrialResult());
        }
        if(requestTrialDTO.getSampleId() != null){
            wrapper.eq("SAMPLE_ID",requestTrialDTO.getSampleId());
        }
        wrapper.like(StringUtils.isNotBlank(requestTrialDTO.getCategoryName()),
                "CATEGORY_NAME",requestTrialDTO.getCategoryName());
        wrapper.like(StringUtils.isNotBlank(requestTrialDTO.getSampleNumber()),
                "SAMPLE_NUMBER",requestTrialDTO.getSampleNumber());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<MaterialTrial>(this.list(wrapper));
    }

    @Override
    public Long addMaterialTrial(MaterialTrial materialTrial) {
        Long id = IdGenrator.generate();
        materialTrial.setMaterialTrialId(id);
        materialTrial.setCreationDate(new Date());
        materialTrial.setTrialNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_AUTH_MATERIAL_TRIAL_NUM));
        this.save(materialTrial);
        if(!CollectionUtils.isEmpty(materialTrial.getFileUploads())){
            fileCenterClient.bindingFileupload(materialTrial.getFileUploads(),materialTrial.getMaterialTrialId());
        }
        return materialTrial.getMaterialTrialId();
    }

    @Override
    public Long modifyMaterial(MaterialTrial materialTrial) {
        if (materialTrial.getMaterialTrialId() !=null) {
            materialTrial.setLastUpdateDate(new Date());
            this.updateById(materialTrial);
            if(!CollectionUtils.isEmpty(materialTrial.getFileUploads())){
                fileCenterClient.bindingFileupload(materialTrial.getFileUploads(),materialTrial.getMaterialTrialId());
            }
            return  materialTrial.getMaterialTrialId();
        }
        return null;
    }

    @Override
    @Transactional
    public void bathDeleteByList(List<Long> materialTrialIds) {
        for(Long materialTrialId:materialTrialIds){
            MaterialTrial existMaterialTrial = this.getById(materialTrialId);
            if(existMaterialTrial.getApproveStatus().equals(SampleStatusType.DRAFT.getValue())){
                this.removeById(materialTrialId);
                Fileupload fileupload = new Fileupload();
                fileupload.setBusinessId(materialTrialId);
                fileCenterClient.deleteByParam(fileupload);
            }else{
                throw new BaseException(LocaleHandler.getLocaleMsg("当前单据不可删除"));
            }
        }
    }

    @Override
    public void commonCheck(MaterialTrial materialTrial, String orderStatus) {
        MaterialTrial existsMaterialTriall;
        Assert.notNull(materialTrial,LocaleHandler.getLocaleMsg("不能传空值"));
        if(materialTrial.getMaterialTrialId() != null){
            existsMaterialTriall = this.getById(materialTrial.getMaterialTrialId());
            Assert.notNull(existsMaterialTriall,LocaleHandler.getLocaleMsg("当前单据不存在"));
            if(SampleStatusType.APPROVED.getValue().equals(existsMaterialTriall.getApproveStatus())){
                throw  new BaseException(LocaleHandler.getLocaleMsg("已审批单据，不允许修改"));
            }
            switch (orderStatus){
                case "DRAFT":
                    checkBeforesaveTemporary(existsMaterialTriall);
                    break;
                case "PUBLISHED":
                    checkBeforePublished(existsMaterialTriall,materialTrial);
                    break;
                case "CONFIRMED":
                    checkBeforeConfirmed(existsMaterialTriall,materialTrial);
                    break;
                case "REFUSED":
                    if(!SampleStatusType.PUBLISHED.getValue().equals(existsMaterialTriall.getApproveStatus())){
                        throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
                    }
                    break;
                case "SUBMITTED":
                    checkBeforeSubmitted(existsMaterialTriall,materialTrial);
                    break;
                case "REJECTED":
                    if(!SampleStatusType.CONFIRMED.getValue().equals(existsMaterialTriall.getApproveStatus())){
                        throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
                    }
                    break;
                case "APPROVED":
                    checkBeforeApprove(existsMaterialTriall);
                    break;
                default:
                    break;
            }
        }

    }

    private void checkBeforePublished(MaterialTrial materialTrial, MaterialTrial dataMaterialTrial) {
        String approveStatus = StringUtils.isNotBlank(materialTrial.getApproveStatus())?materialTrial.getApproveStatus():"DRAFT";
        if(!SampleStatusType.DRAFT.getValue().equals(approveStatus) &&
                !SampleStatusType.REJECTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
        Assert.notNull(dataMaterialTrial.getVendorId(),LocaleHandler.getLocaleMsg("供应商不能为空"));
        Assert.notNull(dataMaterialTrial.getTrialRequireTime(),LocaleHandler.getLocaleMsg("物料需要时间不能为空"));
        Assert.notNull(dataMaterialTrial.getTrialStartDate(),LocaleHandler.getLocaleMsg("试用开始时间不能为空"));
        Assert.notNull(dataMaterialTrial.getTrialEndDate(),LocaleHandler.getLocaleMsg("试用结束时间不能为空"));
//        Assert.notNull(dataMaterialTrial.getCategoryId(),LocaleHandler.getLocaleMsg("品类不能为空"));
//        Assert.notNull(dataMaterialTrial.getPurchaseOrgId(),LocaleHandler.getLocaleMsg("采购组织不能为空"));
//        Assert.notNull(dataMaterialTrial.getMaterialId(),LocaleHandler.getLocaleMsg("物料不能为空"));
    }

    private void checkBeforesaveTemporary(MaterialTrial materialTrial){
        String approveStatus = StringUtils.isNotBlank(materialTrial.getApproveStatus())?materialTrial.getApproveStatus():"DRAFT";
        if(!SampleStatusType.DRAFT.getValue().equals(approveStatus) &&
                !SampleStatusType.REJECTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }




    private void checkBeforeConfirmed(MaterialTrial materialTrial,MaterialTrial dataMaterialTrial){
        String approveStatus = materialTrial.getApproveStatus();
        if(!SampleStatusType.PUBLISHED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
        Assert.hasLength(dataMaterialTrial.getExpressType(),LocaleHandler.getLocaleMsg("送样方式不能空"));
        Assert.notNull(dataMaterialTrial.getEstimatedDeliveryTime(),LocaleHandler.getLocaleMsg("预计送达时间不能为空"));
    }

    private void checkBeforeSubmitted(MaterialTrial materialTrial,MaterialTrial dataMaterialTrial){
        String approveStatus = materialTrial.getApproveStatus();
        if(!SampleStatusType.CONFIRMED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
//        Assert.hasLength(dataMaterialTrial.getTrialResult(),LocaleHandler.getLocaleMsg("试用结果不能为空"));
    }

    private void checkBeforeApprove(MaterialTrial materialTrial){
        String approveStatus = materialTrial.getApproveStatus();
        if(!SampleStatusType.SUBMITTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }

    @Override
    @Transactional
    public FormResultDTO updateMaterial(MaterialTrial materialTrial, String orderStatus) {
        FormResultDTO formResultDTO = new FormResultDTO();
        formResultDTO.setFormId(materialTrial.getMaterialTrialId());
        MaterialTrial existsMaterial = this.getById(materialTrial.getMaterialTrialId());
        materialTrial.setApproveStatus(orderStatus);
        materialTrial.setCreationDate(existsMaterial.getCreationDate());
        materialTrial.setCreatedBy(existsMaterial.getCreatedBy());
        materialTrial.setCreatedByIp(existsMaterial.getCreatedByIp());
        materialTrial.setCreatedId(existsMaterial.getCreatedId());
        this.modifyMaterial(materialTrial);
        //待审时检验是否要启用流程并且确认无误后开始
        if(SampleStatusType.SUBMITTED.getValue().equals(orderStatus) && materialTrial.getMenuId() != null){
            Long menuId = materialTrial.getMenuId();
            Permission menu = rbacClient.getMenu(menuId);
            if (menu != null && YesOrNo.YES.getValue().equals(menu.getEnableWorkFlow())) {
                //1.启用:进入工作流,
                formResultDTO.setEnableWorkFlow(menu.getEnableWorkFlow());
                formResultDTO.setFunctionId(menu.getFunctionId());
            }
        }

        if(!YesOrNo.YES.getValue().equals(formResultDTO.getEnableWorkFlow())
                && SampleStatusType.SUBMITTED.getValue().equals(orderStatus)){
            orderStatus=ApproveStatusType.APPROVED.getValue();
            materialTrial.setApproveStatus(orderStatus);
            this.modifyMaterial(materialTrial);
        }

        if(SampleStatusType.APPROVED.getValue().equals(orderStatus)){
//            this.updateCataLog(materialTrial);
        	this.updateMainVendor(materialTrial);
        }
        return formResultDTO;
    }
    
    /**
     * 供应商主数据品类状态更新
     * @param quaSample
     */
    private void updateMainVendor(MaterialTrial materialTrial) {
    	if (null != materialTrial && null != materialTrial.getSampleId()) {
    		QuaSample quaSample = iQuaSampleService.getById(materialTrial.getSampleId());
    		if (null != quaSample && null != quaSample.getReviewFormId()) {
    			List<OrgCateJournal> orgCateJournals = iOrgCateJournalService.getOrgCateList(materialTrial.getMaterialTrialId(), OrgCateBillType.MATERIAL_FORM.getValue());
    			List<OrgCateJournal> list = new ArrayList<OrgCateJournal>();
    			if (null != orgCateJournals) {
    	    		for (OrgCateJournal orgCate: orgCateJournals ) {
    	    			if ("QUALIFICATION".equals(orgCate.getResult())) {
    	    				list.add(orgCate);
    	    			}
    	    		}
    	    	}
    	    	iOrgCateJournalService.generateOrgCategorys(list, quaSample.getReviewFormId(), materialTrial.getMaterialTrialId(), OrgCateBillType.MATERIAL_FORM.getValue());
    		}
    	}
    }

    private void updateCataLog(MaterialTrial materialTrial){
        PurchaseCatalog purchaseCatalog = new PurchaseCatalog();
        purchaseCatalog.setVendorId(materialTrial.getVendorId());
        purchaseCatalog.setVendorCode(materialTrial.getVendorCode());
        purchaseCatalog.setVendorName(materialTrial.getVendorName());
        purchaseCatalog.setPurchaseOrgId(materialTrial.getPurchaseOrgId());
        purchaseCatalog.setPurchaseOrgCode(materialTrial.getPurchaseOrgCode());
        purchaseCatalog.setPurchaseOrgName(materialTrial.getPurchaseOrgName());
        purchaseCatalog.setParentOrgId(materialTrial.getParentOrgId());
        purchaseCatalog.setParentOrgCode(materialTrial.getParentOrgCode());
        purchaseCatalog.setPurchaseOrgName(materialTrial.getParentOrgName());
        purchaseCatalog.setCategoryId(materialTrial.getCategoryId());
        purchaseCatalog.setCategoryCode(materialTrial.getCategoryCode());
        purchaseCatalog.setCategoryName(materialTrial.getCategoryName());
        purchaseCatalog.setCategoryFullName(materialTrial.getCategoryFullName());
        purchaseCatalog.setMaterialId(materialTrial.getMaterialId());
        purchaseCatalog.setMaterialCode(materialTrial.getMaterialCode());
        purchaseCatalog.setMaterialName(materialTrial.getMaterialName());
        if(SampleTestResultType.QUALIFICATION.getValue().equals(materialTrial.getTrialResult())){
            purchaseCatalog.setCatalogStatus(CatalogStatusType.VALID.getValue());
            purchaseCatalog.setUpdatedReason("物料试用合格");
            if(purchaseCatalog.getVendorId() == null
                    || purchaseCatalog.getPurchaseOrgId() == null
                    || purchaseCatalog.getMaterialId() == null
            ){
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商、采购组织、物料不能为空"));
            }
            iPurchaseCatalogService.updateByMaterialTrial(purchaseCatalog);
        }

    }

    @Override
    @Transactional
    public Long saveOrUpdateMaterial(MaterialTrialDTO materialTrialDTO, String orderStatus) {
    	//物料试用头信息
        Long materialTrialId;
        MaterialTrial materialTrial = materialTrialDTO.getMaterialTrial();
        materialTrial.setApproveStatus(orderStatus);
        if(materialTrial.getMaterialTrialId() != null){
            materialTrialId=   this.modifyMaterial(materialTrial);
        }else{
            materialTrialId=  this.addMaterialTrial(materialTrial);
        }
        
        //批量保存组织品类记录信息
        iOrgCateJournalService.batchSaveOrUpdate(materialTrialDTO.getOrgCateJournals(), materialTrial, OrgCateBillType.MATERIAL_FORM.getValue());
        
        //批量保存附件信息
        iFileRecordService.batchSaveOrUpdate(materialTrialDTO.getFileRecords(), materialTrialId, OrgCateBillType.MATERIAL_FORM.getValue());
        
        return materialTrialId;
    }

    @Override
    @Transactional
    public void submittedSave(MaterialTrialDTO materialTrialDTO) {
        MaterialTrial materialTrial = materialTrialDTO.getMaterialTrial();
      //批量保存组织品类记录信息
       iOrgCateJournalService.batchSaveOrUpdate(materialTrialDTO.getOrgCateJournals(), materialTrial, OrgCateBillType.MATERIAL_FORM.getValue());
    }

    @Override
    public WorkCount countConfirmed() {
        WorkCount workCount = new WorkCount();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        MaterialTrial materialTrial = new MaterialTrial();
        if(org.apache.commons.lang.StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            materialTrial.setVendorId(loginAppUser.getCompanyId());
        }
        materialTrial.setApproveStatus(SampleStatusType.PUBLISHED.getValue());
        int count = this.count(new QueryWrapper<MaterialTrial>(materialTrial));
        workCount.setCount(count);
        workCount.setTitle("待处理物料试用单");
        workCount.setUrl("/vendorManagement/materialTrial?from=workCount&approveStatus="+materialTrial.getApproveStatus());
        return workCount;
    }

    @Override
    public Map<String, Object> updateMaterialWithFlow(MaterialTrialDTO materialTrialDTO) {
    	MaterialTrial materialTrial = materialTrialDTO.getMaterialTrial();
        //需要为确认状态才可以提交
        commonCheck(materialTrial,ApproveStatusType.SUBMITTED.getValue());
        Boolean flowEnable ;
        Long menuId = materialTrialDTO.getMenuId();
        flowEnable = enableWorkFlow(menuId);
        MaterialTrial source = this.getById(materialTrial.getMaterialTrialId());
        Map<String, Object> map = new HashMap();
        
        //批量保存组织品类记录信息
        iOrgCateJournalService.batchSaveOrUpdate(materialTrialDTO.getOrgCateJournals(), materialTrial, OrgCateBillType.MATERIAL_FORM.getValue());
        
        if (flowEnable) {
            map.put("businessId", materialTrial.getMaterialTrialId());
            map.put("fdId", source.getCbpmInstaceId());
            map.put("subject", materialTrial.getVendorName());
            if(materialTrial.getCbpmInstaceId()==null){
                CbpmRquestParamDTO cbpmRquestParamDTO = buildCbpmRquest(materialTrial);
                map = workFlowFeign.initProcess(cbpmRquestParamDTO);
            }else{
                map.put("fdId",source.getCbpmInstaceId());
            }
            source.setApproveStatus(SampleStatusType.SUBMITTED.getValue());
            this.updateById(source);
        }else {
            source.setApproveStatus(SampleStatusType.APPROVED.getValue());
            this.updateMaterial(materialTrial, SampleStatusType.APPROVED.getValue());
        }
        return map;
    }

    private CbpmRquestParamDTO buildCbpmRquest(MaterialTrial materialTrial) {
        CbpmRquestParamDTO cbpmRquestParamDTO = new CbpmRquestParamDTO();
        cbpmRquestParamDTO.setBusinessId(String.valueOf(materialTrial.getMaterialTrialId()));
        cbpmRquestParamDTO.setTemplateCode(CbpmFormTemplateIdEnum.QUA_OF_MATERIAL_TRIAL.getKey());
        cbpmRquestParamDTO.setSubject(materialTrial.getVendorName());
        cbpmRquestParamDTO.setFdId(materialTrial.getCbpmInstaceId());
        return cbpmRquestParamDTO;
    }

    private Boolean enableWorkFlow(Long menuId) {
        Boolean flowEnable;
        Permission menu = rbacClient.getMenu(menuId);
        try {
            flowEnable = workFlowFeign.getFlowEnable(menuId, menu.getFunctionId(), CbpmFormTemplateIdEnum.QUA_OF_MATERIAL_TRIAL.getKey());
        } catch (FeignException e) {
            log.error("提交样品确认时报错,判断工作流是否启动时,参数 menuId：" + menuId + ",functionId" + menu.getFunctionId()
                    + ",templateCode" + CbpmFormTemplateIdEnum.QUA_OF_MATERIAL_TRIAL.getKey() + "报错：", e);
            throw new BaseException("提交物料试用时判断工作流是否启动时报错");
        }
        return flowEnable;
    }

    @Override
    public void updateCataLogAfterFlow(MaterialTrial materialTrial){
        PurchaseCatalog purchaseCatalog = new PurchaseCatalog();
        BeanUtils.copyProperties(materialTrial,purchaseCatalog);
        if(SampleTestResultType.QUALIFICATION.getValue().equals(materialTrial.getTrialResult())){
            purchaseCatalog.setCatalogStatus(CatalogStatusType.VALID.getValue());
            purchaseCatalog.setUpdatedReason("物料试用合格");
            if(purchaseCatalog.getVendorId() == null
                    || purchaseCatalog.getPurchaseOrgId() == null
                    || purchaseCatalog.getMaterialId() == null
            ){
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商、采购组织、物料不能为空"));
            }
            iPurchaseCatalogService.updateByMaterialTrial(purchaseCatalog);
        }

    }
}
