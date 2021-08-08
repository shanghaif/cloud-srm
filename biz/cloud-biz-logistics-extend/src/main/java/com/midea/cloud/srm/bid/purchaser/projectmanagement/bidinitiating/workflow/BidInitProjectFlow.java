package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.workflow;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller.BidFileConfigController;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller.BidFileController;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  招议标审批流
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/26
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidFLow")
public class BidInitProjectFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Resource
    private BidFileController bidFileController;

    @Resource
    private BidFileConfigController bidFileConfigController;

    @Value("${template.bidInitProjectTempId}")
    private String tempId;
    //private String tempId = "17562b39900289ff05112854187a3097";

    @Value("${template.bidInitProjectSrmLink}")
    private String srmLink;

    //private String tempId = "17562b39900289ff05112854187a3097";
    //private String srmLink = "http://10.0.10.48/#/formDataPreview?funName=biddingProject&formId=$formId$";



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
     *  修改日期: 2020-10-26
     *  修改内容:
     * </pre>
     */
    @PostMapping(value = "/submitBidInitProjectFlow")
    public String submitBidInitProjectConfFlow(@RequestBody Biding biding) throws Exception {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String urlFormId = String.valueOf(biding.getBidingId());


        String formId = "";
        String docSubject = "招议标文件审批_"+biding.getBidingNum();
        String fdTemplateId	=tempId;
        String docContent = "";


        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());




        formValues.put("fd_document_num",biding.getBidingNum());
        formValues.put("fd_srm_link",srmLink.replace("$formId$",urlFormId));

        formValues.put("fd_38e445b27b1286","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_mobile_link",srmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"招议标\", \"审批\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(biding.getBidingId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        List<BidFile> bidFiles = bidFileController.listAll(new BidFile().setBidingId(biding.getBidingId()));
        List<BidFileConfig> bidFileConfigs = bidFileConfigController.listAll(new BidFileConfig().setBidingId(biding.getBidingId()));


//        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        bidFiles.stream().forEach(
                x->{
                    if(!StringUtils.isEmpty(x.getDocId())){
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(Long.valueOf(x.getDocId()));
                        fileDtos.add(attachFileDto);
                    }
                });
        bidFileConfigs.stream().forEach(
                x->{
                    if(!StringUtils.isEmpty(x.getReferenceFileId())){
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(Long.valueOf(x.getReferenceFileId()));
                        fileDtos.add(attachFileDto);
                    }
                });
        workflowForm.setFileDtos(fileDtos);

        //起草人意见
        // 流程参数
//        JSONObject flowParam = new JSONObject();
//        flowParam.put("auditNote",requirementHead.getCeeaDrafterOpinion());
////      "{auditNote:\"请审核\", futureNodeId:\"N7\", changeNodeHandlers:[\"N7:1183b0b84ee4f581bba001c47a78b2d9;131d019fbac792eab0f0a684c8a8d0ec\"]}";
//        workflowForm.setFlowParam(flowParam);

        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(biding.getProcessType())){
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
