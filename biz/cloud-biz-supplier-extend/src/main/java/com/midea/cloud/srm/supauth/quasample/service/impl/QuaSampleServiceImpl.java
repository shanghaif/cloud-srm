package com.midea.cloud.srm.supauth.quasample.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.*;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
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
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalog;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.QuaSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.RequestSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.midea.cloud.srm.supauth.purchasecatalog.service.IPurchaseCatalogService;
import com.midea.cloud.srm.supauth.quasample.mapper.QuaSampleMapper;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;

import feign.FeignException;

/**
 *  <pre>
 *  样品确认表 服务实现类
 * </pre>
 *
 * @author zhuwl7
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 19:04:43
 *  修改内容:
 * </pre>
 */
@Service
public class QuaSampleServiceImpl extends ServiceImpl<QuaSampleMapper, QuaSample> implements IQuaSampleService, IFlowBusinessCallbackService {
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

    @Override
    public PageInfo<QuaSample> listPageByParam(RequestSampleDTO requestSampleDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            if(loginAppUser.getCompanyId() != null) {
                requestSampleDTO.setVendorId(loginAppUser.getCompanyId());
            }else{
                requestSampleDTO.setVendorId(-1L);
            }
        }
        QueryWrapper<QuaSample> wrapper = new QueryWrapper<>();
        if(requestSampleDTO.getPurchaseOrgId() != null){
            wrapper.eq(requestSampleDTO.getPurchaseOrgId()!=null,"PURCHASE_ORG_ID",requestSampleDTO.getPurchaseOrgId());
        }
        if(requestSampleDTO.getCategoryId() != null){
            wrapper.eq("CATEGORY_ID",requestSampleDTO.getCategoryId());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getCategoryCode())){
            wrapper.eq("CATEGORY_CODE",requestSampleDTO.getCategoryId());
        }
        if(requestSampleDTO.getMaterialId() != null){
            wrapper.eq("MATERIAL_ID",requestSampleDTO.getMaterialId());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getMaterialCode())){
            wrapper.eq("MATERIAL_CODE",requestSampleDTO.getMaterialCode());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getSampleNumber())){
            wrapper.like("SAMPLE_NUMBER",requestSampleDTO.getSampleNumber());
        }
        if(requestSampleDTO.getVendorId() != null){
            wrapper.eq("VENDOR_ID",requestSampleDTO.getVendorId());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getVendorName())){
            wrapper.like("VENDOR_NAME",requestSampleDTO.getVendorName());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getVendorCode())){
            wrapper.like("VENDOR_CODE",requestSampleDTO.getVendorCode());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getTestResult())){
            wrapper.eq("TEST_RESULT",requestSampleDTO.getTestResult());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getIsMaterialTrial())){
            wrapper.eq("IS_MATERIAL_TRIAL",requestSampleDTO.getIsMaterialTrial());
        }
        if(requestSampleDTO.getReviewFormId() != null){
            wrapper.eq("REVIEW_FORM_ID",requestSampleDTO.getReviewFormId());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getApproveStatus())){
            wrapper.eq("APPROVE_STATUS",requestSampleDTO.getApproveStatus());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getReceiver())){
            wrapper.like("RECEIVER",requestSampleDTO.getReceiver());
        }
        if(StringUtils.isNoneBlank(requestSampleDTO.getReceiverPhone())){
            wrapper.like("RECEIVER_PHONE",requestSampleDTO.getReceiverPhone());
        }
        if(requestSampleDTO.getRequireStarTime() != null){
            wrapper.ge("REQUIRE_SEND_TIME",requestSampleDTO.getRequireStarTime());
        }
        if(requestSampleDTO.getRequireEndTime() != null){
            wrapper.le("REQUIRE_SEND_TIME",requestSampleDTO.getRequireEndTime());
        }
        wrapper.like(StringUtils.isNotBlank(requestSampleDTO.getCategoryName()),
                "CATEGORY_NAME",requestSampleDTO.getCategoryName());
        wrapper.like(StringUtils.isNotBlank(requestSampleDTO.getReviewFormNumber()),
                "REVIEW_FORM_NUMBER",requestSampleDTO.getReviewFormNumber());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<QuaSample>(this.list(wrapper));
    }

    @Override
    public Long addQuaSample(QuaSample quaSample) {
        Long id = IdGenrator.generate();
        quaSample.setSampleId(id);
        quaSample.setCreationDate(new Date());
        quaSample.setSampleNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_AUTH_SAMPLE_NUM));
        this.save(quaSample);
        if(!CollectionUtils.isEmpty(quaSample.getFileUploads())){
            fileCenterClient.bindingFileupload(quaSample.getFileUploads(),id);
        }

        return quaSample.getSampleId();
    }

    @Override
    public Long modifyQuaSample(QuaSample quaSample) {
        if(quaSample.getSampleId() != null){
            quaSample.setLastUpdateDate(new Date());
            this.updateById(quaSample);
            if(!CollectionUtils.isEmpty(quaSample.getFileUploads())){
                fileCenterClient.bindingFileupload(quaSample.getFileUploads(),quaSample.getSampleId());
            }
            return quaSample.getSampleId();
        }
        return null;
    }


    private void checkBeforesaveTemporary(QuaSample quaSample){
        String approveStatus = StringUtils.isNotBlank(quaSample.getApproveStatus())?quaSample.getApproveStatus():"DRAFT";
        if(!SampleStatusType.DRAFT.getValue().equals(approveStatus) &&
                !SampleStatusType.REJECTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }




    private void checkBeforeConfirmed(QuaSample quaSample,QuaSample dataSample){
        String approveStatus = quaSample.getApproveStatus();
        if(!SampleStatusType.PUBLISHED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
        Assert.hasLength(dataSample.getExpressType(),LocaleHandler.getLocaleMsg("送样方式不能空"));
        Assert.notNull(dataSample.getEstimatedDeliveryTime(),LocaleHandler.getLocaleMsg("预计送达时间不能为空"));
    }

    private void checkBeforeSubmitted(QuaSample quaSample,QuaSample dataQuaSample){
        String approveStatus = quaSample.getApproveStatus();
        if(!SampleStatusType.CONFIRMED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
//        Assert.hasLength(dataQuaSample.getTestResult(),LocaleHandler.getLocaleMsg("测试结果不能为空"));
    }

    private void checkBeforeApprove(QuaSample quaSample){
        String approveStatus = quaSample.getApproveStatus();
        if(!SampleStatusType.SUBMITTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }

    @Override
    public void commonCheck(QuaSample quaSample,String orderStatus) {
        QuaSample existQuaSample = null;
        Assert.notNull(quaSample,LocaleHandler.getLocaleMsg("样品确认信息不能为空"));
        if(quaSample.getSampleId() != null){
            existQuaSample= this.getById(quaSample.getSampleId());
            Assert.notNull(existQuaSample,LocaleHandler.getLocaleMsg("当前单据不存在"));
            if(SampleStatusType.APPROVED.getValue().equals(existQuaSample.getApproveStatus())){
                throw  new BaseException(LocaleHandler.getLocaleMsg("已审批单据，不允许修改"));
            }
            switch (orderStatus){
                case "DRAFT":
                    checkBeforesaveTemporary(existQuaSample);
                    break;
                case "PUBLISHED":
                    checkBeforePublish(existQuaSample,quaSample);
                    break;
                case "CONFIRMED":
                    checkBeforeConfirmed(existQuaSample,quaSample);
                    break;
                case "REFUSED":
                    if(!SampleStatusType.PUBLISHED.getValue().equals(existQuaSample.getApproveStatus())){
                        throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
                    }
                    break;
                case "SUBMITTED":
                    checkBeforeSubmitted(existQuaSample,quaSample);
                    break;
                case "REJECTED":
                    checkBeforeApprove(existQuaSample);
                    break;
                case "APPROVED":
                    checkBeforeApprove(existQuaSample);
                    break;
                default:
                    break;
            }
        }
    }

    private void checkBeforePublish(QuaSample existQuaSample,QuaSample quaSample) {
        String approveStatus = StringUtils.isNotBlank(existQuaSample.getApproveStatus())?existQuaSample.getApproveStatus():"DRAFT";
        if(!SampleStatusType.DRAFT.getValue().equals(approveStatus) &&
                !SampleStatusType.REJECTED.getValue().equals(approveStatus)){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }

        Assert.notNull(quaSample.getVendorId(),LocaleHandler.getLocaleMsg("供应商不能为空"));
        Assert.hasLength(quaSample.getReceiver(),LocaleHandler.getLocaleMsg("样品接收人不能为空"));
        Assert.hasLength(quaSample.getReceiverPhone(),LocaleHandler.getLocaleMsg("接收人电话不能为空"));
        Assert.notNull(quaSample.getRequireSendTime(),LocaleHandler.getLocaleMsg("要求送样时间不能为空"));
//        Assert.notNull(quaSample.getCategoryId(),LocaleHandler.getLocaleMsg("品类不能为空"));
//        Assert.notNull(quaSample.getPurchaseOrgId(),LocaleHandler.getLocaleMsg("采购组织不能为空"));
//        Assert.notNull(quaSample.getMaterialId(),LocaleHandler.getLocaleMsg("物料不能为空"));
    }

    @Override
    @Transactional
    public Long saveOrUpdateSample(QuaSampleDTO quaSampleDTO,String orderStatus) {
    	//保存样品确认头表信息
        Long sampleId;
        QuaSample quaSample = quaSampleDTO.getQuaSample();
        quaSample.setApproveStatus(orderStatus);
        if(quaSample.getSampleId() != null){
            sampleId=   this.modifyQuaSample(quaSample);
        }else{
            sampleId=  this.addQuaSample(quaSample);
        }
        quaSample.setSampleId(sampleId);

        //批量保存组织品类记录信息
        iOrgCateJournalService.batchSaveOrUpdate(quaSampleDTO.getOrgCateJournals(), quaSample, OrgCateBillType.SAMPLE_FORM.getValue());

        //批量保存附件信息
        iFileRecordService.batchSaveOrUpdate(quaSampleDTO.getFileRecords(), sampleId, OrgCateBillType.SAMPLE_FORM.getValue());

        return sampleId;
    }

    @Override
    @Transactional
    public void updateQuaSample(QuaSample quaSample,String orderStatus) {
        QuaSample sample = this.getById(quaSample.getSampleId());
        quaSample.setApproveStatus(orderStatus);
        quaSample.setCreationDate(sample.getCreationDate());
        quaSample.setCreatedBy(sample.getCreatedBy());
        quaSample.setCreatedByIp(sample.getCreatedByIp());
        quaSample.setCreatedId(sample.getCreatedId());
        this.modifyQuaSample(quaSample);
        if(SampleStatusType.SUBMITTED.getValue().equals(orderStatus)) {
            orderStatus=SampleStatusType.APPROVED.getValue();
            quaSample.setApproveStatus(orderStatus);
            this.modifyQuaSample(quaSample);
//            this.saveOrUpdateCataLog(quaSample);
            this.updateMainVendor(quaSample);
        }else if (SampleStatusType.APPROVED.getValue().equals(orderStatus)) {
//            this.saveOrUpdateCataLog(quaSample);
        	 this.updateMainVendor(quaSample);
        }
    }
    /**
     * 供应商主数据品类状态更新
     * @param quaSample
     */
    private void updateMainVendor(QuaSample quaSample) {
    	List<OrgCateJournal> orgCateJournals = iOrgCateJournalService.getOrgCateList(quaSample.getSampleId(), OrgCateBillType.SAMPLE_FORM.getValue());
    	List<OrgCateJournal> list = new ArrayList<OrgCateJournal>();
    	if (null != orgCateJournals) {
    		for (OrgCateJournal orgCate: orgCateJournals ) {
    			if ("QUALIFICATION".equals(orgCate.getResult())) {
    				list.add(orgCate);
    			}
    		}
    	}
    	iOrgCateJournalService.generateOrgCategorys(list, quaSample.getReviewFormId(), quaSample.getSampleId(), OrgCateBillType.SAMPLE_FORM.getValue());
    }

    private void saveOrUpdateCataLog(QuaSample quaSample){
        PurchaseCatalog purchaseCatalog = new PurchaseCatalog();
        purchaseCatalog.setVendorId(quaSample.getVendorId());
        purchaseCatalog.setVendorCode(quaSample.getVendorCode());
        purchaseCatalog.setVendorName(quaSample.getVendorName());
        purchaseCatalog.setPurchaseOrgId(quaSample.getPurchaseOrgId());
        purchaseCatalog.setPurchaseOrgCode(quaSample.getPurchaseOrgCode());
        purchaseCatalog.setPurchaseOrgName(quaSample.getPurchaseOrgName());
        purchaseCatalog.setParentOrgId(quaSample.getParentOrgId());
        purchaseCatalog.setParentOrgCode(quaSample.getParentOrgCode());
        purchaseCatalog.setParentOrgName(quaSample.getParentOrgName());
        purchaseCatalog.setCategoryId(quaSample.getCategoryId());
        purchaseCatalog.setCategoryCode(quaSample.getCategoryCode());
        purchaseCatalog.setCategoryName(quaSample.getCategoryName());
        purchaseCatalog.setCategoryFullName(quaSample.getCategoryFullName());
        purchaseCatalog.setMaterialId(quaSample.getMaterialId());
        purchaseCatalog.setMaterialCode(quaSample.getMaterialCode());
        purchaseCatalog.setMaterialName(quaSample.getMaterialName());
        if(SampleTestResultType.QUALIFICATION.getValue().equals(quaSample.getTestResult())){
            if(YesOrNo.YES.getValue().equals(quaSample.getIsMaterialTrial())){
                purchaseCatalog.setCatalogStatus(CatalogStatusType.INVALID.getValue());
                purchaseCatalog.setUpdatedReason("样品测试结果合格需试用");
            }else{
                purchaseCatalog.setCatalogStatus(CatalogStatusType.VALID.getValue());
                purchaseCatalog.setUpdatedReason("样品测试结果合格无需试用");
            }
            if(purchaseCatalog.getVendorId() == null
                    || purchaseCatalog.getPurchaseOrgId() == null
                    || purchaseCatalog.getMaterialId() == null
            ){
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商、采购组织、物料不能为空"));
            }
            iPurchaseCatalogService.saveOrUpdateByQuaSample(purchaseCatalog);
        }
    }

    @Override
    @Transactional
    public void bathDeleteByList(List<Long> sampleIds) {
        for(Long sampleId:sampleIds){
            QuaSample quaSample = this.getById(sampleId);
            if(quaSample.getApproveStatus().equals(SampleStatusType.DRAFT.getValue())
                    ||quaSample.getApproveStatus().equals(SampleStatusType.REJECTED.getValue())){
                this.removeById(sampleId);
                Fileupload fileupload = new Fileupload();
                fileupload.setBusinessId(sampleId);
                fileCenterClient.deleteByParam(fileupload);
            }else{
                throw new BaseException(LocaleHandler.getLocaleMsg("当前单据不可删除"));
            }
        }
    }

    @Override
    public void submittedSave(QuaSampleDTO quaSampleDTO) {
        QuaSample quaSample = this.getById(quaSampleDTO.getQuaSample().getSampleId());
        if(quaSample == null){
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据不存在"));
        }
    	if (null != quaSampleDTO.getOrgCateJournals()) {
        	for (OrgCateJournal orgCate : quaSampleDTO.getOrgCateJournals()) {
        		if (null == orgCate.getResult() || orgCate.getResult().isEmpty()) {
        			 throw new BaseException("请填写结果");
        		}
        	}
        }
        iOrgCateJournalService.batchSaveOrUpdate(quaSampleDTO.getOrgCateJournals(), quaSample, OrgCateBillType.SAMPLE_FORM.getValue());
    }

    @Override
    public WorkCount countConfirmed() {
        WorkCount workCount = new WorkCount();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        QuaSample quaSample = new QuaSample();
        if(org.apache.commons.lang.StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            quaSample.setVendorId(loginAppUser.getCompanyId());
        }
        quaSample.setApproveStatus(SampleStatusType.PUBLISHED.getValue());
        int count = this.count(new QueryWrapper<QuaSample>(quaSample));
        workCount.setCount(count);
        workCount.setTitle("待处理样品确认单");
        workCount.setUrl("/vendorManagement/sampleConfirmed?from=workCount&approveStatus="+quaSample.getApproveStatus());
        return workCount;
    }

    @Override
    public Map<String, Object> updateQuaSampleWithFlow(QuaSampleDTO quaSampleDTO, String orderStatus) {
    	QuaSample quaSample = quaSampleDTO.getQuaSample();
        //判断是否处于确认状态（样品确认已评价）
        if (!SampleStatusType.CONFIRMED.getValue().equals(quaSample.getApproveStatus())) {
            throw new BaseException("样品确认单状态需要为已确认才可以进行后续操作");
        }
        if (null != quaSampleDTO.getOrgCateJournals()) {
        	for (OrgCateJournal orgCate : quaSampleDTO.getOrgCateJournals()) {
        		if (null == orgCate.getResult() || orgCate.getResult().isEmpty()) {
        			 throw new BaseException("请填写结果");
        		}
        	}
        }
        iOrgCateJournalService.batchSaveOrUpdate(quaSampleDTO.getOrgCateJournals(), quaSample, OrgCateBillType.SAMPLE_FORM.getValue());
        quaSample.setMenuId(quaSampleDTO.getMenuId());
        Boolean flowEnable = enableFlowWork(quaSample);
        QuaSample source = this.getById(quaSample.getSampleId());
        Map<String, Object> map = new HashMap();
        //启动了工作流
        if (flowEnable) {
            map.put("businessId", source.getSampleId());
            map.put("fdId", quaSample.getCbpmInstaceId());
            map.put("subject", source.getVendorName());
            if (source.getCbpmInstaceId() == null) {
                //初始化工作流
                CbpmRquestParamDTO cbpmRquestParamDTO = buildCbpmRquest(source);
                map = workFlowFeign.initProcess(cbpmRquestParamDTO);
            } else {
                map.put("fdId", source.getCbpmInstaceId());
            }
            source.setApproveStatus(SampleStatusType.SUBMITTED.getValue());
            updateById(source);
        } else {
            this.updateQuaSample(quaSample, SampleStatusType.APPROVED.getValue());
        }
        return map;
    }

    private Boolean enableFlowWork(QuaSample quaSample) {
        Long menuId = quaSample.getMenuId();
        Permission menu = rbacClient.getMenu(menuId);
        Boolean flowEnable;
        try {
            flowEnable = workFlowFeign.getFlowEnable(menuId, menu.getFunctionId(), CbpmFormTemplateIdEnum.QUA_OF_SAMPLE_FLOW_CHECK.getKey());
        } catch (FeignException e) {
            log.error("提交样品确认时报错,判断工作流是否启动时,参数 menuId：" + menuId + ",functionId" + menu.getFunctionId()
                    + ",templateCode" + CbpmFormTemplateIdEnum.QUA_OF_SAMPLE_FLOW_CHECK.getKey() + "报错：", e);
            throw new BaseException("提交样品确认时判断工作流是否启动时报错");
        }
        return flowEnable;
    }

    private CbpmRquestParamDTO buildCbpmRquest(QuaSample source) {
        CbpmRquestParamDTO cbpmRquestParamDTO = new CbpmRquestParamDTO();
        cbpmRquestParamDTO.setBusinessId(String.valueOf(source.getSampleId()));
        cbpmRquestParamDTO.setTemplateCode(CbpmFormTemplateIdEnum.QUA_OF_SAMPLE_FLOW_CHECK.getKey());
        cbpmRquestParamDTO.setSubject(source.getVendorName());
        cbpmRquestParamDTO.setFdId(source.getCbpmInstaceId());
        return cbpmRquestParamDTO;
    }

    @Override

    public void saveOrUpdateCataLogAfterFlow(QuaSample quaSample){
        PurchaseCatalog purchaseCatalog = new PurchaseCatalog();
        BeanUtils.copyProperties(quaSample,purchaseCatalog);
        if(SampleTestResultType.QUALIFICATION.getValue().equals(quaSample.getTestResult())){
            if(YesOrNo.YES.getValue().equals(quaSample.getIsMaterialTrial())){
                purchaseCatalog.setCatalogStatus(CatalogStatusType.INVALID.getValue());
                purchaseCatalog.setUpdatedReason("样品测试结果合格需试用");
            }else{
                purchaseCatalog.setCatalogStatus(CatalogStatusType.VALID.getValue());
                purchaseCatalog.setUpdatedReason("样品测试结果合格无需试用");
            }
            if(purchaseCatalog.getVendorId() == null
                    || purchaseCatalog.getPurchaseOrgId() == null
                    || purchaseCatalog.getMaterialId() == null
            ){
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商、采购组织、物料不能为空"));
            }
            iPurchaseCatalogService.saveOrUpdateByQuaSample(purchaseCatalog);
        }
    }

    @Override
    public QuaSampleDTO getQualifiedSample(Long sampleId) {
        Assert.notNull(sampleId, "sampleId不能为空");
        QuaSampleDTO quaSampleDTO = new QuaSampleDTO();
        QuaSample quaSample = this.getById(sampleId);
        Assert.notNull(quaSample, "样品确认单据不存在");
        quaSampleDTO.setQuaSample(quaSample);
        //sampleId, OrgCateBillType.SAMPLE_FORM.getValue())
        quaSampleDTO.setOrgCateJournals(iOrgCateJournalService.list(
                Wrappers.lambdaQuery(OrgCateJournal.class)
                        .eq(OrgCateJournal::getOrgCateBillId, sampleId)
                        .eq(OrgCateJournal::getOrgCateBillType, OrgCateBillType.SAMPLE_FORM.getValue())
                        .ne(OrgCateJournal::getResult, SampleTestResultType.DISQUALIFICATION.getValue())));
        quaSampleDTO.setFileRecords(iFileRecordService.getFileRecord(sampleId,OrgCateBillType.SAMPLE_FORM.getValue()));
        return quaSampleDTO;
    }
    @Transactional
    public void pass(QuaSample quaSample) {
        QuaSample byId = this.getById(quaSample.getSampleId());
        if (byId != null) {
            byId.setApproveStatus(ApproveStatusType.APPROVED.getValue());
            this.updateById(new QuaSample().setSampleId(quaSample.getSampleId()).setApproveStatus(ApproveStatusType.APPROVED.getValue()));
        }
    }

    @Override
    public void submitFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =new QuaSample();
        quaSample.setSampleId(businessId);
        this.updateById(quaSample.setApproveStatus(SampleStatusType.PUBLISHED.getValue()));
    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =new QuaSample();
        quaSample.setSampleId(businessId);
        this.pass(quaSample);
    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =new QuaSample();
        quaSample.setSampleId(businessId);
        this.updateById(quaSample.setApproveStatus(ApproveStatusType.REJECTED.getValue()));
    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =new QuaSample();
        quaSample.setSampleId(businessId);
        this.updateById(quaSample.setApproveStatus(ApproveStatusType.WITHDRAW.getValue()));
    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =new QuaSample();
        quaSample.setSampleId(businessId);
        //用撤回代替
        this.updateById(quaSample.setApproveStatus(ApproveStatusType.WITHDRAW.getValue()));
    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        QuaSample quaSample =this.getById(businessId);
        return JsonUtil.entityToJsonStr(quaSample);
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
          QuaSample quaSample  =this.getById(businessId);
        return JsonUtil.entityToJsonStr(quaSample);
    }
}
