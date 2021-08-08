package com.midea.cloud.srm.pr.workflow;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import com.midea.cloud.srm.po.order.enums.CategoryTypeEnum;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  采购申请审批流
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
@RequestMapping("/requireFLow")
public class PurchaseRequirementFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.purchaseRequirementTempId}")
    private String tempId;

    @Value("${template.purchaseRequirementSrmLink}")
    private String srmLink;

    @Autowired
    private IRequirementHeadService iRequirementHeadService;


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
    @PostMapping(value = "/submitRequireFlow")
    public String submitPurchaseRequirementConfFlow(@RequestBody PurchaseRequirementDTO purchaseRequirementDTO) throws Exception {
        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String ceeaBusinessSmallCode = requirementHead.getCeeaBusinessSmallCode();
        String urlFormId = String.valueOf(requirementHead.getRequirementHeadId());
        //是服务类，才需要对业务小类进行校验
        if (CategoryTypeEnum.SERVICE.getCode().equals(requirementHead.getCategoryCode())) {
            Assert.notNull(ceeaBusinessSmallCode, "业务小类编码不能为空");
        }

        String formId = "";
        String docSubject = "物料采购申请单"; //文档标题
        String fdTemplateId = tempId;  //文档模板id
        String docContent = "";  //文档的富文本内容 http://10.0.10.48/#/vendorManagement/vendorProfile
//        String formValues = "{\"fd_unit_code\" : \"100021\", \"fd_es_business_unit_code\": \"10_OU_西安隆基\",\"fd_sp_user_name\" : \"100159\",\"fd_srm_link\" : \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_mobile_link\": \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_document_num\":\"HT0001\"}";  //字符串（Json）	流程表单数据


        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name", loginAppUser.getCeeaEmpNo());
        //a.	用户在N-SRM创建并维护采购申请，若为服务类，且维护对应的业务小类为强管控费用类型（如市场费用类、咨询顾问类、保险类等），则提交即审批通过；
        //b.	若采购申请对应的业务小类不为强管控费用类型，则需提交至申请人部门经理进行审批；
        //c.	若采购申请对应的业务小类属于物流类，则在申请人部门经理审批通过后即可完成审批；反之则继续提交至基地负责人，或职能部门总监进行审批；
        //d.	是否强控费用、是否物流类，均以采购申请头表维护的业务小类进行判断；

        //现在判断业务小类，是否服务类，是否物流类,
        String fd_logistics = "N";
        PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(requirementHead.getCategoryId()));
//        List<DictItemDTO> saleLabelDictList = baseClient.listAllByDictCode("PR_IS_LOGISTICS");
//        List<DictItemDTO> collect = saleLabelDictList.stream().filter(x -> ceeaBusinessSmallCode.equals(x.getDictItemCode())).collect(Collectors.toList());
        if (BigCategoryTypeEnum.LOGISTICS.getCode().equals(purchaseCategory.getCategoryCode())) {
            fd_logistics = "Y";
        }

        //楚辉说，招待用品只有一个,判断采购申请小类里面，有没有这个招待用品，就可以了
        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
        List<RequirementLine> collect = requirementLineList.stream().filter(x -> x.getCategoryId().equals(7983382897033216L) || x.getCategoryName().equals("招待用品")).collect(Collectors.toList());
        BigDecimal reduce = collect.stream().map(RequirementLine::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);


        formValues.put("fd_logistics", fd_logistics);
        formValues.put("fd_document_num", requirementHead.getRequirementHeadNum());
        formValues.put("fd_srm_link", srmLink.replace("$formId$", urlFormId));
        formValues.put("fd_is_zhaodai", collect.size() > 0 ? "Y" : "N");//是否招待用品
        formValues.put("fd_amount", reduce);//招待金额
        formValues.put("fd_38e061445a469a", requirementHead.getDemandDepartmentId());
        formValues.put("fd_38e061455fb0b4", requirementHead.getDemandUserName());
        formValues.put("fd_38e06165ad1a8c", "");
        formValues.put("fd_mobile_link", srmLink.replace("$formId$", urlFormId));

        String docStatus = "20";  //文档状态，可以为草稿（"10"）或者待审（"20"）两种状态，默认为待审
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo", loginAppUser.getCeeaEmpNo());
        String fdKeyword = "[\"物料\", \"采购\"]";        //字符串（Json）	文档关键字，格式为["关键字1", "关键字2"...]
        String docProperty = "";    //字符串（Json）	辅类别，格式为["辅类别1的ID", "辅类别2的ID"...]
        workflowForm.setFormInstanceId(String.valueOf(requirementHead.getRequirementHeadId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId); //采购申请审批模板ID
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);


        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        requirementAttaches.stream().forEach(
                x -> {
                    AttachFileDto attachFileDto = new AttachFileDto();
                    attachFileDto.setFdKey("fd_att");
                    attachFileDto.setFileId(x.getFileuploadId());
                    fileDtos.add(attachFileDto);
                });
        workflowForm.setFileDtos(fileDtos);


        WorkflowCreateResponseDto workflowCreateResponse;

        //默认是提交，N为废弃
        //起草人意见
        // 流程参数
        if (!StringUtils.isEmpty(requirementHead.getCeeaDrafterOpinion())) {
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", requirementHead.getCeeaDrafterOpinion());
            workflowForm.setFlowParam(flowParam);
        }

        if ("N".equals(purchaseRequirementDTO.getProcessType())) {
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


    /**
     * <pre>
     *  废弃OA
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
    @PostMapping(value = "/deleteRequireFlow")
    public String deletePurchaseRequirementConfFlow(PurchaseRequirementDTO purchaseRequirementDTO) throws Exception {
        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");

        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(requirementHead.getRequirementHeadId());
        if (srmworkflowForm != null) {
            WorkflowFormDto workflowForm = new WorkflowFormDto();
            String ceeaBusinessSmallCode = requirementHead.getCeeaBusinessSmallCode();
            String urlFormId = String.valueOf(requirementHead.getRequirementHeadId());
            //是服务类，才需要对业务小类进行校验
            if (CategoryTypeEnum.SERVICE.getCode().equals(requirementHead.getCategoryCode())) {
                Assert.notNull(ceeaBusinessSmallCode, "业务小类编码不能为空");
            }

            String formId = "";
            String docSubject = "物料采购申请单"; //文档标题
            String fdTemplateId = tempId;  //文档模板id
            String docContent = "";  //文档的富文本内容 http://10.0.10.48/#/vendorManagement/vendorProfile
//        String formValues = "{\"fd_unit_code\" : \"100021\", \"fd_es_business_unit_code\": \"10_OU_西安隆基\",\"fd_sp_user_name\" : \"100159\",\"fd_srm_link\" : \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_mobile_link\": \"http://10.0.10.48/#/vendorManagement/vendorProfile\",\"fd_document_num\":\"HT0001\"}";  //字符串（Json）	流程表单数据


            JSONObject formValues = new JSONObject();
            formValues.put("fd_user_name", loginAppUser.getCeeaEmpNo());
            //a.	用户在N-SRM创建并维护采购申请，若为服务类，且维护对应的业务小类为强管控费用类型（如市场费用类、咨询顾问类、保险类等），则提交即审批通过；
            //b.	若采购申请对应的业务小类不为强管控费用类型，则需提交至申请人部门经理进行审批；
            //c.	若采购申请对应的业务小类属于物流类，则在申请人部门经理审批通过后即可完成审批；反之则继续提交至基地负责人，或职能部门总监进行审批；
            //d.	是否强控费用、是否物流类，均以采购申请头表维护的业务小类进行判断；

            //现在判断业务小类，是否服务类，是否物流类,
            String fd_logistics = "N";
            PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(requirementHead.getCategoryId()));
//        List<DictItemDTO> saleLabelDictList = baseClient.listAllByDictCode("PR_IS_LOGISTICS");
//        List<DictItemDTO> collect = saleLabelDictList.stream().filter(x -> ceeaBusinessSmallCode.equals(x.getDictItemCode())).collect(Collectors.toList());
            if (BigCategoryTypeEnum.LOGISTICS.getCode().equals(purchaseCategory.getCategoryCode())) {
                fd_logistics = "Y";
            }

            //楚辉说，招待用品只有一个,判断采购申请小类里面，有没有这个招待用品，就可以了
            List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
            List<RequirementLine> collect = requirementLineList.stream().filter(x -> "701101".equals(x.getCategoryCode())).collect(Collectors.toList());
            BigDecimal reduce = collect.stream().map(RequirementLine::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);


            formValues.put("fd_logistics", fd_logistics);
            formValues.put("fd_document_num", requirementHead.getRequirementHeadNum());
            formValues.put("fd_srm_link", srmLink.replace("$formId$", urlFormId));
            formValues.put("fd_is_zhaodai", collect.size() > 0 ? "Y" : "N");//是否招待用品
            formValues.put("fd_amount", reduce);//招待金额
            formValues.put("fd_38e061445a469a", "");
            formValues.put("fd_38e061455fb0b4", "");
            formValues.put("fd_38e06165ad1a8c", "");
            formValues.put("fd_mobile_link", srmLink.replace("$formId$", urlFormId));

            String docStatus = "20";  //文档状态，可以为草稿（"10"）或者待审（"20"）两种状态，默认为待审
            JSONObject docCreator = new JSONObject();
            docCreator.put("PersonNo", loginAppUser.getCeeaEmpNo());
            String fdKeyword = "[\"物料\", \"采购\"]";        //字符串（Json）	文档关键字，格式为["关键字1", "关键字2"...]
            String docProperty = "";    //字符串（Json）	辅类别，格式为["辅类别1的ID", "辅类别2的ID"...]
            workflowForm.setFormInstanceId(String.valueOf(requirementHead.getRequirementHeadId()));
            workflowForm.setDocSubject(docSubject);
            workflowForm.setFdTemplateId(fdTemplateId); //采购申请审批模板ID
            workflowForm.setDocContent(docContent);
            workflowForm.setDocCreator(docCreator);
            workflowForm.setFormValues(formValues);
            workflowForm.setDocStatus(docStatus);


            //附件
            ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
            requirementAttaches.stream().forEach(
                    x -> {
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
            workflowForm.setFileDtos(fileDtos);

            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);

            WorkflowCreateResponseDto workflowCreateResponse = workflowClient.updateFlow(workflowForm);
            formId = workflowCreateResponse.getProcessId();

            return formId;
        }
        return null;
    }
}
