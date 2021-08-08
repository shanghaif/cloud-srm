package com.midea.cloud.srm.supauth.workflow.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import  com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.review.dto.SiteFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;

/**
 * <pre>
 *  供应商认证审查单
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-29
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/supplierAuthFlow")
public class SupplierAuthFlow {

    @Resource
    private RbacClient rbacClient;
    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.supplierAuthTempId}")
    private String supplierAuthTempId;

    @Value("${template.supplierAuthSrmLink}")
    private String supplierAuthSrmLink;



    /**
     * <pre>
     *
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-10-05
     *  修改内容:
     * </pre>
     */
    @PostMapping(value = "/submitSupplierAuthFlow")
    public String submitSupplierAuthFlow(@RequestBody SiteFormDTO siteFormDTO) throws Exception {
        SiteForm siteForm = siteFormDTO.getSiteForm();
        List<Fileupload> fileUploads = siteFormDTO.getSiteForm().getFileUploads();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String siteFormNumber = siteForm.getSiteFormNumber();
        String urlFormId = String.valueOf(siteForm.getSiteFormId());

        List<FileRecord> siteFormRecords = siteFormDTO.getFileRecords();
        List<String> assessors=null;
        try {
            assessors= siteFormRecords.stream().map(x -> rbacClient.findByUsername(x.getReviewPeople()).getCeeaEmpNo()).collect(Collectors.toList());
        }catch (Exception e){
            Assert.isTrue(false,"请选择工号不为空的评选人员");
        }

        //审批人，用；隔开
        String join = org.apache.commons.lang3.StringUtils.join(assessors, ";");


        String formId = "";
        String docSubject = "供应商认证审批";
        String fdTemplateId	=supplierAuthTempId;
        String docContent = "ceshi";

        //是否主材主设备
//        String sfzczsb = "N";
        List<CateJournal> cateJournals = siteFormDTO.getCateJournals();
//        for (CateJournal ca:cateJournals){
//            PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(ca.getCategoryId()));
////            String mainMaterial = purchaseCategory.getMainMaterial();
//            if(!ObjectUtils.isEmpty(purchaseCategory) && !StringUtil.isEmpty(purchaseCategory.getMainMaterial())){
//                sfzczsb = "Y";
//                break;
//            }
//        }

        boolean sfzczsb = cateJournals.stream().anyMatch(
                x -> {
                    String mainMaterial = Optional.ofNullable(
                            baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(x.getCategoryId())))
                            .orElse(new PurchaseCategory()).getMainMaterial();
                    return "MAIN_MATERIAL".equals(mainMaterial) || "MAIN_DEVICE".equals(mainMaterial) || "大宗主辅材".equals(mainMaterial) || "主设备".equals(mainMaterial);
                });



        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_sfzczsb",sfzczsb ? "Y":"N");
//        formValues.put("fd_gysrzbh",siteFormNumber);
        formValues.put("fd_document_num",siteFormNumber);
        formValues.put("fd_srm_link",supplierAuthSrmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38e445b27b1286","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_20approver",join);
        formValues.put("fd_mobile_link",supplierAuthSrmLink.replace("$formId$",urlFormId));


        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"供应商\", \"认证\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(siteForm.getSiteFormId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);


        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if(CollectionUtils.isNotEmpty(fileUploads)){
            fileUploads.stream().forEach(
                    x->{
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
            workflowForm.setFileDtos(fileDtos);
        }


        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(siteFormDTO.getProcessType())){
            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        }else {
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }
        formId = workflowCreateResponse.getProcessId();
        return formId;
    }
}
