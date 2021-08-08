package com.midea.cloud.srm.sup.vendorimport.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportService;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author vent
 * @Description
 **/
@RequestMapping("/vendorImportFlow")
@RestController
public class VendorImportFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Autowired
    private IVendorImportService iVendorImportService;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.vendorImportTempId}")
    private String tempId;

    @Value("${template.vendorImportSrmLink}")
    private String srmLink;

//    private final String tempId = "17511d4219977716e9fb15a415183103";
//    private final String srmLink = "http://10.0.10.48/#/formDataPreview?funName=crossOrgImport&formId=7904305291329536";

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
    @PostMapping(value = "/submitVendorImportDTOFlow")
    public String submitVendorImportDTOFlow(@RequestBody VendorImportSaveDTO vendorImportSaveDTO) throws Exception {
        VendorImport vendorImport = vendorImportSaveDTO.getVendorImport();
        List<Fileupload> fileuploads = vendorImportSaveDTO.getFileuploads();
        String urlFormId = String.valueOf(vendorImport.getImportId());


        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");


        //获取所有物料小类信息
        OrgCategoryQueryDTO orgCategoryQueryDTO = new OrgCategoryQueryDTO();
        orgCategoryQueryDTO.setCompanyId(vendorImport.getVendorId()).setOrgId(vendorImport.getOldOrgId());
        List<OrgCategory> orgCategoryList = iVendorImportService.listOrgCategoryByParam(orgCategoryQueryDTO);


        boolean logistics = false;
        if (CollectionUtils.isNotEmpty(orgCategoryList)) {
            //获取物流类的大类的小类
            List<PurchaseCategory> purchaseCategorieList = baseClient.listByCategoryCode(BigCategoryTypeEnum.LOGISTICS.getCode());
            Map<String, PurchaseCategory> purchaseCategorieMap = purchaseCategorieList.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryCode, Function.identity()));
            //是否为物流类
            logistics = orgCategoryList.stream().anyMatch(x -> purchaseCategorieMap.get(x.getCategoryCode()) != null);
        }


        String formId = "";
        String docSubject = "供应商跨OU引入审批";
        String fdTemplateId = tempId;
        String docContent = "ceshi";

        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name", loginAppUser.getCeeaEmpNo());
        formValues.put("fd_document_num", vendorImport.getImportNum());
        formValues.put("fd_is_logtic", logistics ? "Y" : "N");

        formValues.put("fd_srm_link", srmLink.replace("$formId$", urlFormId));
        formValues.put("fd_mobile_link", srmLink.replace("$formId$", urlFormId));
//        formValues.put("fd_38e445b27b1286","");
//        formValues.put("fd_38e061455fb0b4","");
//        formValues.put("fd_38e06165ad1a8c","");


        String docStatus = "20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo", loginAppUser.getCeeaEmpNo());
        String fdKeyword = "[\"供应商\", \"引入\"]";
        String docProperty = "";

        WorkflowFormDto workflowForm = new WorkflowFormDto();
        workflowForm.setFormInstanceId(String.valueOf(vendorImport.getImportId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if (!ObjectUtils.isEmpty(fileuploads)) {
            fileuploads.stream().forEach(
                    x -> {
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
        }
        workflowForm.setFileDtos(fileDtos);

        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(vendorImportSaveDTO.getProcessType())) {
            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        } else {
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }

        formId = workflowCreateResponse.getProcessId();
        return formId;
    }


}
