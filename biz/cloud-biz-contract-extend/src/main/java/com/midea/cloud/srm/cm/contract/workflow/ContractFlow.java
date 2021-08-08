package com.midea.cloud.srm.cm.contract.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.contract.ContractLevelForBpmEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
@RequestMapping("/contractFlow")
@RestController
public class ContractFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.contractTempId}")
    private String tempId;

    @Value("${template.contractSrmLink}")
    private String srmLink;

//    private final String tempId = "1751ac32ff05629370a37e44d9b88ba9";
//    private final String srmLink = "http://10.0.10.48/#/formDataPreview?funName=contractManager&formId=7998064471769088";

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
    @PostMapping(value = "/submitContractDTOFlow")
    public String submitContractDTOConfFlow(@RequestBody ContractDTO contractDTO) throws Exception {
        ContractHead contractHead = contractDTO.getContractHead();
        List<Fileupload> fileuploads = contractDTO.getFileuploads();
        String urlFormId = String.valueOf(contractHead.getContractHeadId());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");

        Organization organization = baseClient.get(contractHead.getBuId());

        //是否标准合同，1是标准合同，0是非标合同
        Integer standardFlag = "Y".equals(contractHead.getEnable())?1:0;

        //String contractLevelForBpm = ContractLevelForBpmEnum.getValueByCode(contractHead.getContractLevel());

        String formId = "";
        String docSubject = "合同审批";
        String fdTemplateId	=tempId;
        String docContent = "ceshi";

        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_contract_number",contractHead.getContractCode());
        formValues.put("fd_title",contractHead.getContractName());
        formValues.put("fd_contract_level",contractHead.getContractLevel());
        formValues.put("fd_standard_flag",standardFlag);
        formValues.put("fd_business_unit_code",ObjectUtils.isEmpty(organization)?"":organization.getOrganizationName());
        formValues.put("fd_touzi","N");//是否投资类合同，默认否，楚辉说我们没有投资类合同
        formValues.put("fd_document_num",contractHead.getContractNo());
        formValues.put("fd_srm_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_mobile_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38ee6803f03394","");
        formValues.put("fd_38ee6805022772","");
        formValues.put("fd_38ee68065757d0","");
        formValues.put("fd_headquarters_flag",StringUtils.isEmpty(contractHead.getIsHeadquarters())?"N":contractHead.getIsHeadquarters());
        formValues.put("fd_is_simplecontract",StringUtils.isEmpty(contractHead.getCeeaIsPortableContract())?"N":contractHead.getCeeaIsPortableContract());



        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"物料\", \"采购\"]";
        String docProperty ="";

        WorkflowFormDto workflowForm = new WorkflowFormDto();
        workflowForm.setFormInstanceId(String.valueOf(contractHead.getContractHeadId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if(CollectionUtils.isNotEmpty(fileuploads)){
            fileuploads.stream().forEach(
                    x->{
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
        }
        workflowForm.setFileDtos(fileDtos);

        WorkflowCreateResponseDto workflowCreateResponse;

        if ("N".equals(contractDTO.getProcessType())){
            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        }else {
            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote",StringUtils.isEmpty(contractHead.getDrafterOpinion())?"起草人节点意见":contractHead.getDrafterOpinion());
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }

        formId = workflowCreateResponse.getProcessId();
        return formId;
    }


}
