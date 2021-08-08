package com.midea.cloud.srm.perf.vendorasses.workflow;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.enums.perf.indicators.IndicatorsDimensionEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  审批
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
@RequestMapping("/vendorAssesFlow")
public class VendorAssesFlow {
    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.vendorAssesTempId}")
    private String vendorAssesTempId;

    @Value("${template.vendorAssesSrmLink}")
    private String vendorAssesSrmLink;

//    private String vendorAssesTempId = "175447b2cbb4d69983dda524ded83e61";
//
//    private String vendorAssesSrmLink = "http://10.0.10.48/#/formDataPreview?funName=vendorVendorAsses&formId=$formId$";



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
    @PostMapping(value = "/submitVendorAssesFlow")
    public String submitVendorAssesConfFlow(@RequestBody VendorAssesForm vendorAssesForm) throws Exception {

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String assessmentNo = vendorAssesForm.getAssessmentNo();
        String urlFormId = String.valueOf(vendorAssesForm.getVendorAssesId());

        String formId = "";
        String docSubject = "供应商考核审批_"+assessmentNo;
        String fdTemplateId	=vendorAssesTempId;
        String docContent = "ceshi";



        //4.是否物流类供应商：绿色通道供应商的所有小类中，如果任一小类的大类代码为60，则是否物流类供应商为Y，否则为N
        boolean logistics = BigCategoryTypeEnum.LOGISTICS.getCode().equals(baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(vendorAssesForm.getCategoryId())).getCategoryCode());





        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_document_num",assessmentNo);
//        formValues.put("fd_is_logtic",fd_logistics);
        formValues.put("fd_is_quality",StringUtils.equals(IndicatorsDimensionEnum.QUALITY.getValue(),vendorAssesForm.getIndicatorDimension())?"Y":"N");
        formValues.put("fd_is_logtic",logistics?"Y":"N");
//        formValues.put("fd_logapprover","");
        formValues.put("fd_srm_link",vendorAssesSrmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38f4c77f9a8298","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_mobile_link",vendorAssesSrmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"供应商\", \"考核\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(vendorAssesForm.getVendorAssesId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);


        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if(!StringUtils.isEmpty(vendorAssesForm.getFileUploadId())){
            AttachFileDto attachFileDto = new AttachFileDto();
            attachFileDto.setFdKey("fd_att");
            attachFileDto.setFileId(Long.parseLong(vendorAssesForm.getFileUploadId()));
            fileDtos.add(attachFileDto);
            workflowForm.setFileDtos(fileDtos);
        }

        //起草人意见
        // 流程参数
        if(!org.springframework.util.StringUtils.isEmpty(vendorAssesForm.getCeeaDrafterOpinion())){
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote",vendorAssesForm.getCeeaDrafterOpinion());
            workflowForm.setFlowParam(flowParam);
        }

        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(vendorAssesForm.getProcessType())){
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
