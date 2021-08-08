package com.midea.cloud.flow.workflow.test;

import com.midea.cloud.common.enums.flow.CbpmEventTypeEnum;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.flow.workflow.service.ITemplateLinesService;
import com.midea.cloud.srm.model.flow.process.dto.ProcessTemplateDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * <pre>
 *  流程模板单元测试
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/22 10:16
 *  修改内容:
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateProcessTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateProcessTest.class);
    @Autowired
    private ITemplateHeaderService iTemplateHeaderService; //流程表头Service接口

    @Autowired
    private ITemplateLinesService iTemplateLinesService; //流程行Service接口

    /**模板ID*/
    private static final String MODLE_ID ="1237909316820815872";

    @Test
    public void newTemplateProcess(){
        ProcessTemplateDTO processTemplate = new ProcessTemplateDTO();
        List<TemplateLines> templateLinesList = new ArrayList<>();

        TemplateHeader templateHeader = new TemplateHeader();
        templateHeader.setLanguage("zh_CN");
        templateHeader.setTemplateCode("TakeLeaveTemp");
        //region Description
        templateHeader.setTemplateId(6845200260595712L);
        //endregion
        templateHeader.setModelId(MODLE_ID);
        templateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
        templateHeader.setPendingApproveUrl("/daiban");
        templateHeader.setBussinessClass("com.midea.cloud.flow.process.controller.ProcessTemplateController");
        templateHeader.setFeignClient("base");
        processTemplate.setTemplateHeader(templateHeader);


        for(int i= 0; i < 2; i++){
            TemplateLines templateLines = new TemplateLines();
            templateLines.setTemplateLinesId(IdGenrator.generate());
            templateLines.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
            templateLines.setBussinessType(CbpmEventTypeEnum.ACTIVITY_START_ENVEN.getKey());
            templateLines.setBussinessFunction(CbpmEventTypeEnum.ACTIVITY_START_ENVEN.getKey());
            templateLines.setDescription(CbpmEventTypeEnum.ACTIVITY_EMD_ENVEN.getValue());
            templateLinesList.add(templateLines);
        }
        processTemplate.setTemplateLinesList(templateLinesList);
        System.out.println("参数: "+ JsonUtil.entityToJsonStr(processTemplate));
        BaseResult<String> result = iTemplateHeaderService.saveProcessTemplate(processTemplate);
        System.out.println("结果： "+result);
    }

    @Test
    public void updateProcessTemplate(){
        ProcessTemplateDTO processTemplate = new ProcessTemplateDTO();
        List<TemplateLines> templateLinesList = new ArrayList<>();

        TemplateHeader templateHeader = new TemplateHeader();
        templateHeader.setTemplateHeadId(6866004496678912L);
        templateHeader.setLanguage("en_US");
        templateHeader.setTemplateCode("TakeLeaveTemp");
        templateHeader.setTemplateId(6845200260595712L);
        templateHeader.setModelId(MODLE_ID);
        templateHeader.setPendingApproveUrl("/daiban/test");
        templateHeader.setBussinessClass("com.midea.cloud.flow.process.controller.ProcessTemplateController");
        templateHeader.setFeignClient("supplier");
        processTemplate.setTemplateHeader(templateHeader);


        for(int i= 0; i < 1; i++){
            TemplateLines templateLines = new TemplateLines();
            templateLines.setBussinessType(CbpmEventTypeEnum.DRAFT_RETURN_EVENT.getKey());
            templateLines.setBussinessFunction(CbpmEventTypeEnum.DRAFT_RETURN_EVENT.getKey());
            templateLines.setDescription(CbpmEventTypeEnum.DRAFT_RETURN_EVENT.getValue());
            templateLinesList.add(templateLines);
        }
        TemplateLines templateLines = new TemplateLines();
        templateLines.setTemplateLinesId(6861321040429056L);
        templateLines.setBussinessType(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getKey());
        templateLines.setBussinessFunction(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getKey());
        templateLines.setDescription(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getValue());
        templateLinesList.add(templateLines);

        processTemplate.setTemplateLinesList(templateLinesList);
        System.out.println("参数: "+ JsonUtil.entityToJsonStr(processTemplate));
        BaseResult<String> result = iTemplateHeaderService.updateProcessTemplate(processTemplate);
        System.out.println("结果： "+result);
    }

    /**
     * Description 修改流程行测试
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.22
     * @throws
     **/
    @Test
    public void updateTemplateLines(){
        String loginName = "admin";
        String nickName = "系统管理员";
        TemplateLines templateLines = new TemplateLines();
        templateLines.setTemplateLinesId(6861321040429056L);
        templateLines.setBussinessType(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getKey());
        templateLines.setBussinessFunction(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getKey());
        templateLines.setAttributeCategory(CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getValue());
        templateLines.setLastUpdateDate(new Date());
        templateLines.setLastUpdatedBy(loginName);
        templateLines.setLastUpdatedFullName(nickName);
        boolean isUpdate = iTemplateLinesService.updateById(templateLines);
        System.out.println("修改流程行结果："+isUpdate);
    }


}
