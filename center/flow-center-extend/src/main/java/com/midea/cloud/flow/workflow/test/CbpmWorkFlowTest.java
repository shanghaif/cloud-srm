package com.midea.cloud.flow.workflow.test;

import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.flow.workflow.service.ICbpmWorkFlowService;
import com.midea.cloud.srm.model.flow.process.dto.ChangeProcessSubjectDTO;
import com.midea.cloud.srm.model.flow.process.dto.ChangeTemplateEditorDTO;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * cbpm接口集成测试类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/12 10:51
 *  修改内容:
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CbpmWorkFlowTest {
    @Autowired
    private ICbpmWorkFlowService iCbpmWorkFlowService;

    /**
     * Description 删除流程(硬删除,慎用)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void deleteProcess() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="zz";
        String fdId= "1275912445794214064";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.deleteProcess(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.DELETE_PROCESS);
    }

    /**
     * Description 根据模板ID获取模板详情
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void getFlowTemplate() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        String fdId= "1237909316820815872";
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getFlowTemplate(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.GET_FLOW_TEMPLATE);
    }

    /**
     * Description 是否存在流程
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void isExistProcess() throws Exception {
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName = "admin";
        flowProcessQuery.setLoginName(loginName);
        String fdId = "1287408088970805493";
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setTemplateId("1237909316820815872");
/*        String fdModuleId = "";
        flowProcessQuery.setFdModuleId(fdModuleId);
        String formInstanceId = "";
        flowProcessQuery.setFormInstanceId(formInstanceId);
        String formTemplateId = "";
        flowProcessQuery.setFormTemplateId(formTemplateId);*/
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.isExistProcess(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.IS_EXIST_PROCESS);
    }

    /**
     * Description 初始化流程
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void initProcess() throws Exception {
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName = "admin";
        flowProcessQuery.setLoginName(loginName);
        String fdTemplateId = "1242837812143800320";
//        String fdTemplateId = "1237909316820815872";
        flowProcessQuery.setFdTemplateId(fdTemplateId);
/*         String fdTemplateCode = "";
        flowProcessQuery.setFdTemplateCode(fdTemplateCode);*/
        String processNodesInfoResult = iCbpmWorkFlowService.initProcess(flowProcessQuery);
        System.out.println(WorkFlowConst.INIT_PROCESS+": "+processNodesInfoResult);
    }



    /**
     * Description 根据模板ID获取模板详情
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void getTemplateInfo() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String fdTemplateId= "1237909316820815872";
        flowProcessQuery.setFdTemplateId(fdTemplateId);
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getTemplateInfo(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.GET_TEMPLATE_INFO);
    }

    /**
     * Description 获取所有流程节点信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    @Test
    public void getProcessNodesInfo() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdTemplateId= "1237909316820815872";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdTemplateId(fdTemplateId);
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getProcessNodesInfo(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.GET_PROCESS_NODES_INFO);
    }

    /**
     * Description 获取流程待审人信息
     * @Param processIdList 多条流程ID,最大200条,超过200报错,不传入值 则返回null
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    @Test
    public void getProcessApproveInfo() throws Exception{
        List<String> processIdList = new ArrayList<>();
        processIdList.add("1276682997286879257");
//        processIdList.add("1276283067112129377");
        List resultList = iCbpmWorkFlowService.getProcessApproveInfo(processIdList);
        printCbpmList(resultList, WorkFlowConst.GET_PROCESS_APPROVE_INFO);
    }

    /**
     * Description 获取用户处理相关的节点信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws
     **/
    @Test
    public void listFlowNodeInfo() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdId= "1276682997286879257";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List resultMap = iCbpmWorkFlowService.listFlowNodeInfo(flowProcessQuery);
        printCbpmList(resultMap, WorkFlowConst.LIST_FLOW_NODE_INFO);
    }

    /**
     * Description 获取流程状态
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    @Test
    public void getProcessStatus() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="1";
        String fdId= "1287408088970806611";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> result = iCbpmWorkFlowService.getProcessStatus(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_PROCESS_STATUS);
    }

    /**
     * Description 获取所有流程列表
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    @Test
    public void getProcessList() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName = "admin";
        String fdId = "";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> processMap = iCbpmWorkFlowService.getProcessList(flowProcessQuery);
        printCbpmMap(processMap, WorkFlowConst.GET_PROCESS_LIST);
    }

    /**
     * Description 获取所有流程总数
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    @Test
    public void getProcessCount() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
/*        String loginName ="ex_guochun1";
        String fdTemplateId= "1276682997286879257";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdTemplateId(fdTemplateId);*/
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getProcessCount(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.GET_PROCESS_COUNT);
    }

    /**
     * Description 获取流程信息
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getProcessInfo() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="1";
        flowProcessQuery.setLoginName(loginName);
        String fdId= "1287408088970805629";
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getProcessInfo(flowProcessQuery);
        printCbpmMap(processNodesInfoMap, WorkFlowConst.GET_PROCESS_INFO);
    }

    /**
     * Description 获取审批流程意见
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getAuditeNote() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdId= "1276682997286879257";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.getAuditeNote(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_AUDITE_NOTE);
    }

    /**
     * Description 获取审批流程意见
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getFlowLog() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="admin";
        String fdId= "1287408088970805778";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.getFlowLog(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_FLOW_LOG);
    }

    /**
     * Description 获取用户流程相关数量
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void selectUserProcessCount() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="aa";
        flowProcessQuery.setLoginName(loginName);
//        String fdId= "1276682997286879257";
//        flowProcessQuery.setFdId(fdId);
        Map<String, Object> result = iCbpmWorkFlowService.selectUserProcessCount(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.SELECT_USER_PROCESS_COUNT);
    }

    /**
     * Description 获取用户已审批/处理的流程列表
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getMyWorkedProcess() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        Map<String, Object> result = iCbpmWorkFlowService.getMyWorkedProcess(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_MY_WORKED_PROCESS);
    }

    /**
     * Description 获取可驳回流程节点
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getProcessRefuseNode() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdId= "1276682997286879302";
        String nodeInstanceId = "1276682997286879319";  //nodeInstanceId 可以先根据getProcessInfo接口查询到 fdId：1276682997286879302
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setNodeInstanceId(nodeInstanceId);
        List resultList = iCbpmWorkFlowService.getProcessRefuseNode(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_PROCESS_REFUSE_NODE);
    }

    /**
     * Description 获取可驳回流程节点
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getAuditeNoteInAllNodes() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdId= "1276682997286879302";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.getAuditeNoteInAllNodes(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_AUDITE_NOTE_IN_ALL_NODES);
    }

    /**
     * Description 获取上次审批人
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getPrevProcessApprovers() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        String fdTemplateId = "1237909316820815872";  //1237909316820815872 这个值还没查到list数据
        flowProcessQuery.setFdTemplateId(fdTemplateId);
        Map<String, Object> result = iCbpmWorkFlowService.getPrevProcessApprovers(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_PREV_PROCESS_APPROVERS);
    }

    /**
     * Description 获取模板节点事件列表
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void listTemplateNodeEnvents() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String templateId= "1276682997286879302";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setTemplateId(templateId);
        List resultList = iCbpmWorkFlowService.listTemplateNodeEnvents(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.LIST_TEMPLATE_NODE_EVENTS);
    }

    /**
     * Description 获取我启动的流程
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getMyStartProcess() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        Map<String, Object> result = iCbpmWorkFlowService.getMyStartProcess(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_MY_START_PROCESS);
    }

    /**
     * Description 获取抄送给我的流程
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getSendNodesToMe() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        Map<String, Object> result = iCbpmWorkFlowService.getSendNodesToMe(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_SEND_NODES_TO_ME);
    }

    /**
     * Description 获取流程传阅信息
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void listCirculateNote() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        String fdId= "1276682997286879302";
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.listCirculateNote(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.LIST_CIRCULATE_NOTE);
    }

    /**
     * Description 获取传阅给我的流程
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getCircularizeNodesToMe() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        Map<String, Object> result = iCbpmWorkFlowService.getCircularizeNodesToMe(flowProcessQuery);
        printCbpmMap(result, WorkFlowConst.GET_CIRCULARIZE_NODES_TO_ME);
    }

    /**
     * Description 获取流程的某些信息
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getProcessInfoById() throws Exception{
        List<String> idList = new ArrayList<>();
        idList.add("1276682997286879257");
        idList.add("1276283067112129377");
        List resultList = iCbpmWorkFlowService.getProcessInfoById(idList);
        printCbpmList(resultList, WorkFlowConst.GET_PROCESS_INFO_BY_ID);
    }

    /**
     * Description 获取流程待审人信息(批量)
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getProcessApproveInfoList() throws Exception{
        List<String> idList = new ArrayList<>();
        idList.add("1276682997286879257");
        idList.add("1276283067112129377");
        List resultList = iCbpmWorkFlowService.getProcessApproveInfoList(idList);
        printCbpmList(resultList, WorkFlowConst.GET_PROCESS_APPROVE_INFO_LIST);
    }

    /**
     * Description 获取根据角色分组后的流程可用操作
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @Test
    public void getOperationList() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="1";
        flowProcessQuery.setLoginName(loginName);
        String fdId= "1287408088970805629";
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.getOperationList(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_OPERATION_LIST);
    }

    /**
     * Description 更新模板权限（全量更新）
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void updateTemplateAuth() throws Exception{
        ChangeTemplateEditorDTO chanceTemplateEditor = new ChangeTemplateEditorDTO();
        String loginName ="ex_guochun1";
        chanceTemplateEditor.setLoginName(loginName);
        String fdTemplateIds = "1237909316820815872"; //1237909316820815872 为我自己创建的模板ID
        chanceTemplateEditor.setFdTemplateIds(fdTemplateIds);
        String departIds = "u_wooTest";
        chanceTemplateEditor.setFdTemplateReaderLoginNames(departIds);
        List resultList = iCbpmWorkFlowService.updateTemplateAuth(chanceTemplateEditor);
        printCbpmList(resultList, WorkFlowConst.UPDATE_TEMPLATE_AUTH);
    }

    /**
     * Description 获取超级驳回的可驳回节点
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void getProcessSuperRefuseNode() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        String fdId= "1276682997286879302";
        String nodeInstanceId = "1276682997286879319";  //nodeInstanceId 可以先根据getProcessInfo接口查询到 fdId：1276682997286879302
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setNodeInstanceId(nodeInstanceId);
        List resultList = iCbpmWorkFlowService.getProcessSuperRefuseNode(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.GET_PROCESS_SUPER_REFUSE_NODE);
    }

    /**
     * Description 获取用户常用回复
     * @Param
     * @return voif
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void selectCommonReply() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        String fdId= "1276682997286879302";
        flowProcessQuery.setFdId(fdId);
        List resultList = iCbpmWorkFlowService.selectCommonReply(flowProcessQuery);
        printCbpmList(resultList, WorkFlowConst.SELECT_COMMON_REPLY);
    }

    /**
     * Description 获取流程表格与人工决策选择项列表
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void getProcessTableInfoAndManualNodes() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="admin";
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId("1287408088970805629");
        Map<String, Object> resultMap = iCbpmWorkFlowService.getProcessTableInfoAndManualNodes(flowProcessQuery);
        printCbpmMap(resultMap, WorkFlowConst.GET_PROCESS_TABLE_INFO_AND_MANUAL_NODES);
    }

    /**
     * Description 更新流程主题
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void updateProcessSubject() throws Exception{
        ChangeProcessSubjectDTO chanceProcessSubjectDTO = new ChangeProcessSubjectDTO();
        String loginName ="aa";   //开始节点创建人?
        chanceProcessSubjectDTO.setLoginName(loginName);
        String fdId = "1276682997286879326";
        chanceProcessSubjectDTO.setFdId(fdId);
        String subject = "wooTest主题";
        chanceProcessSubjectDTO.setSubject(subject);
        List result = iCbpmWorkFlowService.updateProcessSubject(chanceProcessSubjectDTO);
        printCbpmList(result, WorkFlowConst.UPDATE_PROCESS_SUBJECT);
    }

    /**
     * Description 替换流程实例处理人
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    @Test
    public void replaceProcessHandler() throws Exception{
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        String loginName ="ex_guochun1";
        flowProcessQuery.setLoginName(loginName);
        Map<String, Object> resultMap = iCbpmWorkFlowService.replaceProcessHandler(flowProcessQuery);
        printCbpmMap(resultMap, WorkFlowConst.REPLACE_PROCESS_HANDLER);
    }






    /**打印接口Map结果集*/
    private static void printCbpmMap(Map<String, Object> map, String apiName){
        System.out.println();
        System.out.println(apiName+"：");
        System.out.println(JsonUtil.entityToJsonStr(map));
    }

    /**打印接口List结果集*/
    private static void printCbpmList(List list, String apiName){
        System.out.println();
        System.out.println(apiName+"：");
        System.out.println(JsonUtil.arrayToJsonStr(list));
    }

    public static FlowProcessQueryDTO getFLowProcessQuery(){
        String loginName ="ex_guochun1";
        String fdTemplateId= "1237909316820815872";
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName("loginName");
        return flowProcessQuery;
    }

    public static void main(String[] args) {
        Object str = null;
        System.out.println("str:"+String.valueOf(str)+"----");
    }

}
