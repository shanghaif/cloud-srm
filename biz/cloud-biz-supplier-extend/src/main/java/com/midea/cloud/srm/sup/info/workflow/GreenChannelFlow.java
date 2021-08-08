package com.midea.cloud.srm.sup.info.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.enums.pm.ps.CategoryEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
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
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <pre>
 *  供应绿色通道审批
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
@RequestMapping("/greenChannelFlow")
public class GreenChannelFlow {
    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.greenChannelTempId}")
    private String greenChannelTempId;

    @Value("${template.greenChannelSrmLink}")
    private String greenChannelSrmLink;

//    private String greenChannelTempId = "1754422fe66dd865bc59bdd47a5b6650";
//
//    private String greenChannelSrmLink = "http://10.0.10.48/#/formDataPreview?funName=vendorGreenChannel&formId=$formId$";



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
    @PostMapping(value = "/submitGreenChannelFlow")
    public String submitGreenChannelConfFlow(@RequestBody InfoDTO infoDTO) throws Exception {
        CompanyInfo companyInfo = infoDTO.getCompanyInfo();
        List<Fileupload> fileUploads = infoDTO.getFileUploads();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String companyCode = companyInfo.getCompanyCode();
        String urlFormId = String.valueOf(companyInfo.getCompanyId());

        String formId = "";
        String docSubject = "供应商绿色通道审批_"+companyInfo.getCompanyName();
        String fdTemplateId	=greenChannelTempId;
        String docContent = "ceshi";

        List<OrgCategory> orgCategorys = infoDTO.getOrgCategorys();


        //2.是否主材主设备：如果绿色通道供应商中有任一一个品类是大宗主辅材或者主设备，则是否主材主设备为Y，否则为N
//        String sfzczsb = "N";
//        List<OrgCategory> orgCategorys = infoDTO.getOrgCategorys();
//        for (OrgCategory ca:orgCategorys){
//            PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(ca.getCategoryId()));
//            String mainMaterial = purchaseCategory.getMainMaterial();
//            if("Y".equals(mainMaterial)){
//                sfzczsb = "Y";
//                break;
//            }
//        }
        //boolean zczsb = orgCategorys.stream().anyMatch(x -> "Y".equals(baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(x.getCategoryId())).getMainMaterial()));

        boolean sfzczsb = orgCategorys.stream().anyMatch(
                x -> {
                    String mainMaterial = Optional.ofNullable(
                            baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(x.getCategoryId())))
                            .orElse(new PurchaseCategory()).getMainMaterial();
                    return "MAIN_MATERIAL".equals(mainMaterial) || "MAIN_DEVICE".equals(mainMaterial) || "大宗主辅材".equals(mainMaterial) || "主设备".equals(mainMaterial);
                });


        //4.是否物流类供应商：绿色通道供应商的所有小类中，如果任一小类的大类代码为60，则是否物流类供应商为Y，否则为N
//        String fd_logistics = "N";
//        for (OrgCategory ca:orgCategorys){
//            PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(ca.getCategoryId()));
//            if(BigCategoryTypeEnum.LOGISTICS.getCode().equals(purchaseCategory.getCategoryCode())){
//                fd_logistics = "Y";
//                break;
//            }
//        }
        boolean logistics = orgCategorys.stream().anyMatch(x -> BigCategoryTypeEnum.LOGISTICS.getCode().equals(baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(x.getCategoryId())).getCategoryCode()));



        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_sfzczsb",sfzczsb ? "Y":"N");
        formValues.put("fd_document_num",companyInfo.getCompanyName());
        formValues.put("fd_is_logtic",logistics ? "Y":"N");
//        formValues.put("fd_is_logtic",fd_logistics);
//        formValues.put("fd_logapprover","");
        formValues.put("fd_srm_link",greenChannelSrmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38e445b27b1286","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_mobile_link",greenChannelSrmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"供应商\", \"绿色通道\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(companyInfo.getCompanyId()));
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
        }
        workflowForm.setFileDtos(fileDtos);

        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(infoDTO.getProcessType())){
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
