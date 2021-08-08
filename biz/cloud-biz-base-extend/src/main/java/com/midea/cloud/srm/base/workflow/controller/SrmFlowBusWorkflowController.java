/**
 *
 */
package com.midea.cloud.srm.base.workflow.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.bpm.service.impl.BpmWsServiceImpl;
import com.midea.cloud.srm.base.workflow.service.ISrmFlowBusWorkflowService;
import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年8月13日 上午10:25:43
 *  修改内容:
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/workflow")
public class SrmFlowBusWorkflowController {

    @Autowired
    private ISrmFlowBusWorkflowService workflowService;
    @Autowired
    private BpmWsServiceImpl bpmWsService;


    @GetMapping(value = "/list")
    public List<SrmFlowBusWorkflow> list() {
        return workflowService.list();
    }

    /**
     * 查询所有推送数据
     * @param srmFlowBusWorkflow
     * @return
     */
    @PostMapping(value = "/workflowPageList")
    public PageInfo<SrmFlowBusWorkflow> workflowPageList(@RequestBody SrmFlowBusWorkflow srmFlowBusWorkflow) {
        PageUtil.startPage(srmFlowBusWorkflow.getPageNum(), srmFlowBusWorkflow.getPageSize());
        QueryWrapper<SrmFlowBusWorkflow> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(srmFlowBusWorkflow.getSrmOrderStatus())) {
            wrapper.isNotNull("SRM_ORDER_STATUS");
            wrapper.ne("SRM_ORDER_STATUS", "");
        } else if ("Y".equals(srmFlowBusWorkflow.getSrmOrderStatus())) {

        } else {
            wrapper.eq("SRM_ORDER_STATUS", srmFlowBusWorkflow.getSrmOrderStatus());
        }
        //单据模板id
        wrapper.eq(StringUtils.isNotEmpty(srmFlowBusWorkflow.getFormInstanceId()), "FORM_INSTANCE_ID", srmFlowBusWorkflow.getFormInstanceId());
        //业务单据id
        wrapper.like(StringUtils.isNotEmpty(srmFlowBusWorkflow.getFormTemplateId()), "FORM_TEMPLATE_ID", srmFlowBusWorkflow.getFormTemplateId());
        //备注
        wrapper.like(StringUtils.isNotEmpty(srmFlowBusWorkflow.getFormRemark()),"FORM_REMARK",srmFlowBusWorkflow.getFormRemark());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<SrmFlowBusWorkflow>(workflowService.list(wrapper));
    }

    /**
     * 重推审批通过方法
     * @param ids
     */
    @PostMapping("/getHeavyPush")
    public void getHeavyPush(@RequestBody List<Long> ids) {
        Assert.isTrue(CollectionUtils.isNotEmpty(ids), "请勾选需要审批的单子");
        QueryWrapper<SrmFlowBusWorkflow> wrapper = new QueryWrapper<>();
        wrapper.in(CollectionUtils.isNotEmpty(ids), "ID", ids);
        wrapper.isNotNull("SRM_ORDER_STATUS");
        wrapper.ne("SRM_ORDER_STATUS", "");
        List<SrmFlowBusWorkflow> list = workflowService.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SrmFlowBusWorkflow srmFlowBusWorkflow : list) {
                Header header = new Header();
                //模板id
                header.setFormId(srmFlowBusWorkflow.getFormTemplateId());
                //单据id
                header.setFormInstanceId(srmFlowBusWorkflow.getFormInstanceId());
                //单据状态
                header.setStatus(srmFlowBusWorkflow.getSrmOrderStatus());
                this.getExecute(header, srmFlowBusWorkflow);
            }
        }
    }

    public void getExecute(Header header, SrmFlowBusWorkflow one) {
        String formId = header.getFormId();
        String valueByCode = TempIdToModuleEnum.getValueByCode(formId);
        try {
            if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REQUIREMENT.getValue())) {
                bpmWsService.doRequirement(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ORDER.getValue())) {
                bpmWsService.doOrder(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CONTRACT.getValue())) {
                bpmWsService.doContract(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REVIEW.getValue())) {
                bpmWsService.doReview(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.SUPPLIERAUTH.getValue())) {
                bpmWsService.doSupplierauth(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CHANGE.getValue())) {
                bpmWsService.doChange(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.IMPORT.getValue())) {
                bpmWsService.doImport(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ACCEPT.getValue())) {
                bpmWsService.doAccept(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.COMPANYINFO.getValue())) {
                bpmWsService.doCompanyinfo(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.VENDORASSES.getValue())) {
                bpmWsService.docVendorasses(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.FORCOMPARISON.getValue())) {
                bpmWsService.doBiding(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.BIDING.getValue())) {
                bpmWsService.docForComparison(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.APPROVAL.getValue())) {
                bpmWsService.doApproval(header, one);
            } else {
                Assert.isTrue(false, "ES_RETURN_ID参数模板ID：" + formId + "找不到");
            }
            //状态入库
            one.setSrmOrderStatus(header.getStatus());
            workflowService.updateById(one);
        } catch (Exception e) {
            log.info(e.toString());
            Assert.isTrue(false, "重推失败，原因：" + e.getMessage());
        }

    }


}
