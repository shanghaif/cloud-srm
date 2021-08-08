package com.midea.cloud.srm.supauth.workflow.controller;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *  供应商工作流Controller
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/15 23:25
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/supplierFLow")
public class SupplierFlowController {

    /**工作流Client**/
//    @Resource
//    private BaseClient baseClient;
    @Resource
    private WorkflowClient workflowClient;

    /**
     * Description 提交供应商认证计划审
     * @Param
     * @return 返回流程id
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.15
     * @throws
     **/
    @PostMapping("/submitSupplierConfFlow")
    public String submitSupplierContificationFlow(@RequestBody WorkflowFormDto workflowForm){
        String formId = "";
        String docSubject = "供应商认证计划审批2"; //文档标题
        String fdTemplateId	="16ee865db4d553cb288194f48008dd5b";  //文档模板id
        String docContent = "ceshi";  //文档的富文本内容 http://10.0.10.48/#/vendorManagement/vendorProfile
        String formValues = "{\"fd_unit_code\" : \"100021\", \"fd_es_business_unit_code\": \"10_OU_西安隆基\",\"fd_sp_user_name\" : \"100159\",\"fd_srm_link\" : \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_mobile_link\": \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_document_num\":\"HT0001\"}";  //字符串（Json）	流程表单数据
        String docStatus ="20";  //文档状态，可以为草稿（"10"）或者待审（"20"）两种状态，默认为待审
        String docCreator ="{\"PersonNo\": \"117595\"}";  	//字符串（Json）	流程发起人，为单值，格式详见人员组织架构的定义说明
        String fdKeyword ="";  	    //字符串（Json）	文档关键字，格式为["关键字1", "关键字2"...]
        String docProperty ="";  	//字符串（Json）	辅类别，格式为["辅类别1的ID", "辅类别2的ID"...]
        workflowForm.setFormInstanceId("7682462799495298");
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId); //供应商认证计划审批模板ID
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(JSON.parseObject(docCreator));
        workflowForm.setFormValues(JSON.parseObject(formValues));
        workflowForm.setDocStatus(docStatus);
        WorkflowCreateResponseDto workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        formId = workflowCreateResponse.getProcessId();
        return formId;
    }
}
