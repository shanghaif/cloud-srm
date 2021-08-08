package com.midea.cloud.srm.supauth.workflow.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.km.review.webservice.AttachmentForm;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.supauth.review.service.ICateJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <pre>
 *  供应商资质审查单
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
@RequestMapping("/reviewFlow")
public class ReviewFlow {
    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.reviewTempId}")
    private String reviewTempId;

    @Value("${template.reviewSrmLink}")
    private String reviewSrmLink;



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
    @PostMapping(value = "/submitReviewFlow")
    public String submitReviewConfFlow(@RequestBody ReviewFormDTO reviewFormDTO) throws Exception {
        ReviewForm reviewForm = reviewFormDTO.getReviewForm();
        List<Fileupload> fileUploads = reviewFormDTO.getReviewForm().getFileUploads();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String reviewFormNumber = reviewForm.getReviewFormNumber();
        String urlFormId = String.valueOf(reviewForm.getReviewFormId());

        String formId = "";
        String docSubject = "供应商资质审查单";
        String fdTemplateId	=reviewTempId;
        String docContent = "ceshi";

        //是否主材主设备
//        String sfzczsb = "N";
        List<CateJournal> cateJournals = reviewFormDTO.getCateJournals();
//        for (CateJournal ca:cateJournals){
//            PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(ca.getCategoryId()));
//            String mainMaterial = purchaseCategory.getMainMaterial();
//            if("Y".equals(mainMaterial)){
//                sfzczsb = "Y";
//                break;
//            }
//        }
//        boolean sfzczsb = cateJournals.stream().anyMatch(x -> "Y".equals(baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(x.getCategoryId())).getMainMaterial()));

        boolean logistics = cateJournals.stream().anyMatch(x -> BigCategoryTypeEnum.LOGISTICS.getCode().equals(baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(x.getCategoryId())).getCategoryCode()));



        boolean sfzczsb = cateJournals.stream().anyMatch(
                x -> {
                    String mainMaterial = Optional.ofNullable(
                            baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(x.getCategoryId())))
                            .orElse(new PurchaseCategory()).getMainMaterial();
                    return "MAIN_MATERIAL".equals(mainMaterial) || "MAIN_DEVICE".equals(mainMaterial) || "大宗主辅材".equals(mainMaterial) || "主设备".equals(mainMaterial);
                });



        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_sfzczsb",sfzczsb?"Y":"N");
        formValues.put("fd_is_logtic",logistics?"Y":"N");
        formValues.put("fd_document_num",reviewForm.getReviewFormNumber());
//        formValues.put("fd_zzscdh",reviewForm.getReviewFormNumber());
        formValues.put("fd_srm_link",reviewSrmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38e445b27b1286","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_mobile_link",reviewSrmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"供应商\", \"资质审查\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(reviewForm.getReviewFormId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);


        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if (CollectionUtils.isNotEmpty(fileUploads)){
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
        if ("N".equals(reviewFormDTO.getProcessType())){
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
