package com.midea.cloud.srm.sup.change.workflow;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.entity.FileuploadChange;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.SiteInfoChange;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author vent
 * @Description
 **/
@RequestMapping("/vendorInfoChangeFlow")
@RestController
public class VendorInfoChangeFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Autowired
    private RbacClient RbacClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.vendorInfoChangeTempId}")
    private String tempId;

    @Value("${template.vendorInfoChangeSrmLink}")
    private String srmLink;

//    private final String tempId = "1751a78eab12cbfd18fc0f842369425";
//    private final String srmLink = "http://10.0.10.48/#/formDataPreview?funName=vendorInfoChange&formId=7976864017022976";

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
     *  修改日期: 2020-10-15
     *  修改内容:
     * </pre>
     */
    @PostMapping(value = "/submitVendorInfoChangeDTOFlow")
    public String submitVendorInfoChangeDTOFlow(@RequestBody ChangeInfoDTO changeInfoDTO) throws Exception {
        InfoChange infoChange = changeInfoDTO.getInfoChange();
        List<FileuploadChange> fileuploadChanges = changeInfoDTO.getFileuploadChanges();
        List<SiteInfoChange> siteInfoChanges = changeInfoDTO.getSiteInfoChanges();
        String urlFormId = String.valueOf(infoChange.getChangeId());
        //OU编码
        String unitCode = null;
        if(!ObjectUtils.isEmpty(siteInfoChanges)){
            unitCode = siteInfoChanges.get(0).getOrgCode();
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.isTrue(StringUtils.isNotEmpty(loginAppUser.getCeeaEmpNo()), "申请人工号不能为空");


        //提交人身份标识   0为厂商，1为采购人员
        String userType = loginAppUser.getUserType();
        String ceeaEmpNo=null;
        if("VENDOR".equals(userType)){
            Assert.notNull(infoChange.getNoticeById(),"供应商负责人信息不能为空，请维护供应商负责人信息。");
            List<Long> userList = new ArrayList<>();
            userList.add(infoChange.getNoticeById());
            List<User> byUserIds = RbacClient.getByUserIds(userList);
            Assert.isTrue(CollectionUtils.isNotEmpty(byUserIds),"供应商负责人信息找不到，请维护供应商负责人信息。");
            Assert.notNull(byUserIds.get(0).getCeeaEmpNo(),"供应商负责人工号不能为空，请维护供应商负责人信息。");
            ceeaEmpNo = byUserIds.get(0).getCeeaEmpNo();
        }else if("BUYER".equals(userType)){
            ceeaEmpNo = loginAppUser.getCeeaEmpNo();
        }

        String formId = "";
        String docSubject = "供应商信息变更审批";
        String fdTemplateId	=tempId;
        String docContent = "ceshi";

        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",ceeaEmpNo);
        formValues.put("fd_4M",infoChange.getEnable4MChange());
        formValues.put("fd_document_num",infoChange.getChangeApplyNo());
//        formValues.put("fd_sumbit_flag",sumbitFlag);
        formValues.put("fd_es_business_unit_code",unitCode);
        formValues.put("fd_srm_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_mobile_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38ee5e0a7202b2","");
        formValues.put("fd_38ee5e0b22b348","");
        formValues.put("fd_38ee5e0b8fcd58","");


        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"供应商\", \"信息变更\"]";
        String docProperty ="";

        WorkflowFormDto workflowForm = new WorkflowFormDto();
        workflowForm.setFormInstanceId(String.valueOf(infoChange.getChangeId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if(!ObjectUtils.isEmpty(fileuploadChanges)){
            fileuploadChanges.stream().forEach(
                    x->{
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
        }
        workflowForm.setFileDtos(fileDtos);


        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(changeInfoDTO.getProcessType())){
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
