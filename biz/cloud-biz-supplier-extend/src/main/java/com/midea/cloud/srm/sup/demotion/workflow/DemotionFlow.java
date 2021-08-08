package com.midea.cloud.srm.sup.demotion.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.sup.DemotionType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  供应商升降级工作流
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/1/25 11:09
 *  修改内容:
 * </pre>
 */
@RequestMapping("/demotionFlow")
@RestController
public class DemotionFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SupcooperateClient supcooperateClient;

    @Autowired
    private RbacClient rbacClient;

    @Value("${template.demotionTempId:1}")
    private String tempId;

    @Value("${template.demotionSrmLink:1}")
    private String srmLink;

    @PostMapping(value = "/submitDemotionDTOFlow")
    public String submitDemotionDTOConfFlow(@RequestBody CompanyDemotionDTO demotionDTO) throws Exception {
        CompanyDemotion demotion = demotionDTO.getCompanyDemotion();

        // 升降级附件
        List<Fileupload> fileUploads = demotionDTO.getFileUploads();
        String urlFormId = String.valueOf(demotion.getCompanyDemotionId());
        // 1.申请人工号
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空。");

        // 2.是否主材红黑牌降级（降级类型）
        String demotionType = ifRedBlackDemotion(demotionDTO) ? "Y" : "N";

        //String contractLevelForBpm = ContractLevelForBpmEnum.getValueByCode(contractHead.getContractLevel());

        String formId = "";
        String docSubject = "供应商升降级审批";
        String fdTemplateId	= tempId;
        String docContent = "ceshi";

        JSONObject formValues = new JSONObject();
        // 申请人工号
        formValues.put("fd_user_name", loginAppUser.getCeeaEmpNo());
        // srm申请单编号
        formValues.put("fd_document_num", demotion.getDemotionNumber());
        // 降级类型，是否红黑牌降级且品类包含大宗生产材料或主设备
        formValues.put("fd_is_demotion", demotionType);
        // OU列表（20节点抄送人员）
        formValues.put("fd_business_unit_code", CollectionUtils.isNotEmpty(demotionDTO.getCompanyDemotionOrgs()) ? getDemotionOUs(demotionDTO.getCompanyDemotionOrgs()) : "1");
        // 相关品类主负责人工号列表（20节点抄送人员）
        formValues.put("fd_20node", CollectionUtils.isNotEmpty(demotionDTO.getCompanyDemotionCategories()) ? getCategoryResponsibilities(demotionDTO.getCompanyDemotionOrgs()) : "1");
        // 评审人工号列表
        formValues.put("fd_31node", CollectionUtils.isNotEmpty(demotion.getReviewUserIdList()) ? getReviewUserEmpNos(demotion.getReviewUserIdList()) : "1");
        // SRM链接地址
        formValues.put("fd_srm_link", srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_mobile_link", srmLink.replace("$formId$",urlFormId));
        // 预留字段1 2 3
        formValues.put("fd_38e445b27b1286", "");
        formValues.put("fd_38e061455fb0b4", "");
        formValues.put("fd_38e06165ad1a8c", "");

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo", loginAppUser.getCeeaEmpNo());

        WorkflowFormDto workflowForm = new WorkflowFormDto();
        workflowForm.setFormInstanceId(String.valueOf(demotion.getCompanyDemotionId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        // 附件
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

        if ("N".equals(demotionDTO.getProcessType())){
            // 起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        }else {
            // 起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", StringUtils.isEmpty(demotion.getDrafterOpinion()) ? "起草人节点意见":demotion.getDrafterOpinion());
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }

        formId = workflowCreateResponse.getProcessId();
        return formId;
    }

    /**
     * 判断降级类型是否是红、黑牌，且包含大宗生产材料和主设备
     * @param demotionDTO
     * @return
     */
    public boolean ifRedBlackDemotion(CompanyDemotionDTO demotionDTO) {
        CompanyDemotion companyDemotion = demotionDTO.getCompanyDemotion();
        // 获取升降级类型
        String demotionType = companyDemotion.getDemotionType();
        List<String> redBlackList = Arrays.asList(DemotionType.DEMOTION_TO_RED.getValue(), DemotionType.DEMOTION_TO_BLACK.getValue());
        // 获取升降级品类，用于判断品类是否包含大宗生产材料和主设备
        List<CompanyDemotionCategory> demotionCategories = demotionDTO.getCompanyDemotionCategories();
        boolean ifContainsMainEquipment = false;
        // 升降级品类为空，则认为不包含大宗生产材料或主设备
        if (CollectionUtils.isEmpty(demotionCategories)) {
            return false;
        }
        List<Long> categoryIds = demotionCategories.stream().map(CompanyDemotionCategory::getCategoryId).distinct().collect(Collectors.toList());
        List<PurchaseCategory> categories = baseClient.listCategoryByIds(categoryIds);
        List<String> mainMaterialList = Arrays.asList("大宗主辅材", "主设备");
        boolean mainMaterialFlag = categories.stream().anyMatch(e -> mainMaterialList.contains(e.getMainMaterial()));

        if (redBlackList.contains(demotionType) && mainMaterialFlag) {
            return true;
        }
        return false;
    }

    /**
     * 获取升降级品类OU列表
     * @param companyDemotionOrgs
     * @return
     */
    public String getDemotionOUs(List<CompanyDemotionOrg> companyDemotionOrgs) {
        List<String> collect = companyDemotionOrgs.stream().map(CompanyDemotionOrg::getOrgName).distinct().collect(Collectors.toList());
        return org.apache.commons.lang3.StringUtils.join(collect, ";");
    }

    /**
     * 获取升降级品类品类主负责人工号
     * @param companyDemotionCategories
     * @return
     */
    public String getCategoryResponsibilities(List<CompanyDemotionOrg> companyDemotionCategories) {
        List<DivisionCategory> divisionCategories = supcooperateClient.listDivisionPurchaseStrategyMainPerson(companyDemotionCategories);
        List<Long> userIds = divisionCategories.stream().map(DivisionCategory::getPersonInChargeUserId).distinct().collect(Collectors.toList());
        List<User> users = rbacClient.getByUserIds(userIds);
        List<String> ceeaEmpNos = users.stream().map(User::getCeeaEmpNo).collect(Collectors.toList());
        return org.apache.commons.lang3.StringUtils.join(ceeaEmpNos, ";");
    }

    /**
     * 根据userIds获取评审人工号
     * @param userIds
     * @return
     */
    public String getReviewUserEmpNos(List<Long> userIds) {
        List<User> users = rbacClient.getByUserIds(userIds);
        List<String> ceeaEmpNos = users.stream().map(User::getCeeaEmpNo).distinct().collect(Collectors.toList());
        return org.apache.commons.lang3.StringUtils.join(ceeaEmpNos, ";");
    }

}
