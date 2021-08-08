package com.midea.cloud.srm.po.workflow;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.km.review.webservice.AttachmentForm;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderAttach;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import com.midea.cloud.srm.pr.workflow.PurchaseRequirementFlow;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author vent
 * @Description
 **/
@RequestMapping("/orderFlow")
@RestController
public class OrderFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Value("${template.orderTempId}")
    private String tempId;

    @Value("${template.orderSrmLink}")
    private String srmLink;

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
    @PostMapping(value = "/submitOrderFlow")
    public String submitOrderConfFlow(@RequestBody OrderSaveRequestDTO orderSaveRequestDTO) throws Exception {
        Order order = orderSaveRequestDTO.getOrder();
        List<OrderAttach> orderAttachList = orderSaveRequestDTO.getAttachList();
        String urlFormId = String.valueOf(order.getOrderId());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");

        //2.是否生产材料
        //以采购订单来源判断是否标准订单，若订单来源为手工创建，则是否标准订单标识为N；若订单来源为需求转订单，则是否标准订单标识为Y；
        //订单来源列表（对应字典编码ORDER_SOURCE）
        String standardFlag = "N";
        String sourceSystem = order.getSourceSystem();
        if("MANUAL".equals(sourceSystem)){
            standardFlag = "N";
        }else if("DEMAND".equals(sourceSystem)){
            standardFlag = "Y";
        }

        //3.是否生产材料
        //以采购订单明细行物料小类所对应的大类判断是否生产材料，若物料大类为生产材料或备品备件，则是否生产材料标识为Y；反之标识为N；
        //注：相同采购订单，允许同时存在生产材料和备品备件的物料明细，存在生产材料或备品备件的采购订单，不允许存在其他物料大类的物料明细；
        String productionFlag = "N";
        List<OrderDetail> detailList = orderSaveRequestDTO.getDetailList();
        Assert.notNull(detailList,"订单明细不能为空");
        for(OrderDetail od:detailList){
            // 获取大类
            PurchaseCategory lc = new PurchaseCategory().setCategoryId(od.getCategoryId());
            PurchaseCategory largeCategory = baseClient.queryMaxLevelCategory(lc);
            if("30".equals(largeCategory.getCategoryCode()) || "10".equals(largeCategory.getCategoryCode())){
                productionFlag = "Y";
                break;
            }
        }

        //4.订单总金额
        //以采购订单头表记录的含税金额合（CEEA_TOTAL_AMOUNT_INCLUDING_TAX）传递至BPM进行有关金额分段的控制
        BigDecimal ceeaTotalAmountIncludingTax = order.getCeeaTaxAmount();

        /**
         * 1.如果为标准订单 且小于100W 且为生产材料，则不走审批流
         * 2.如果为标准订单 且 除生产材料以外的所有物料 ,则不走审批流
         */
        String productionMaterialFlag = "N";
        for(OrderDetail od:detailList){
            PurchaseCategory lc = new PurchaseCategory().setCategoryId(od.getCategoryId());
            PurchaseCategory largeCategory = baseClient.queryMaxLevelCategory(lc);
            if("10".equals(largeCategory.getCategoryCode())){
                productionMaterialFlag = "Y";
                break;
            }
        }
        if("Y".equals(standardFlag) &&
                "Y".equals(productionMaterialFlag) &&
                order.getCeeaTaxAmount().compareTo(new BigDecimal(1000000)) <= 0
        ){
            return "NOT_TO_BPM";
        }
        if("Y".equals(standardFlag) &&
            "N".equals(productionMaterialFlag)
        ){
            return "NOT_TO_BPM";
        }


        String formId = "";
        String docSubject = "采购订单";
        String fdTemplateId	=tempId;
        String docContent = "ceshi";

        JSONObject formValues = new JSONObject();
        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_order",standardFlag);
        formValues.put("fd_document_num",order.getOrderNumber());
        formValues.put("fd_amout",ceeaTotalAmountIncludingTax);
        formValues.put("fd_srm_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_mobile_link",srmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"物料\", \"采购\"]";
        String docProperty ="";

        WorkflowFormDto workflowForm = new WorkflowFormDto();
        workflowForm.setFormInstanceId(String.valueOf(order.getOrderId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId); //采购申请审批模板ID
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);

        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if (CollectionUtils.isNotEmpty(orderAttachList)){
            orderAttachList.stream().forEach(
                    x->{
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getFileuploadId());
                        fileDtos.add(attachFileDto);
                    });
        }

        workflowForm.setFileDtos(fileDtos);

        WorkflowCreateResponseDto workflowCreateResponse;
        //起草人意见
        // 流程参数

        if ("N".equals(orderSaveRequestDTO.getProcessType())){
            JSONObject flowParam = new JSONObject();
            //起草人意见
            // 流程参数
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        }else {
            JSONObject flowParam = new JSONObject();
            String ceeaOpinion =StringUtils.isEmpty(order.getCeeaOpinion())?"": order.getCeeaOpinion();
            flowParam.put("auditNote",ceeaOpinion);
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }
        formId = workflowCreateResponse.getProcessId();
        return formId;
    }


}
