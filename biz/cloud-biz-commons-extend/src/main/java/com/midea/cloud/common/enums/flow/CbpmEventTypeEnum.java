package com.midea.cloud.common.enums.flow;

/**
 * <pre>
 *  cbpm流程事件说明(注意:添加一条是记得要在flow-center模块国际化，有用于前端提示信息)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20
 *  修改内容:
 * </pre>
 */
public enum CbpmEventTypeEnum {
    PROCESS_END_EVENT("processEndEvent","流程结束"),
    PROCESS_START_EVENT("processStartEvent","流程开始"),
    PROCESS_ABANDON_EVENT("processAbandonEvent", "流程废弃"),
    ACTIVITY_START_ENVEN("activityStartEvent","节点进入"),
    ACTIVITY_EMD_ENVEN("activityEndEvent","节点结束"),
    ACTIVITY_ABANDON_ENVEN("activityAbandonEvent","节点废弃"),
    TASK_START_EVENT("taskStartEvent","工作项开始"),
    TASK_END_EVENT("taskEndEvent","工作项结束"),
    TASK_ABANDON_EVENT("taskAbandonEvent","工作项废弃"),
    DRAFT_ABANDON_EVENT("draftAbandonEvent","起草人废弃"),
    DRAFT_RETURN_EVENT("draftReturnEvent","起草人撤回"),
    DRAFT_SUBMIT_EVENT("draftSubmitEvent","起草人提交"),
    HANDLE_ABANDON_EVENT("handleAbandonEvent","处理人废弃"),
    HANDLE_COMMISSION_EVENT("handleCommissionEvent","处理人转办"),
    HANDLE_COMMUNICATE_EVENT("handleCommunicateEvent","处理人沟通"),
    HANDLE_PASS_EVENT("handlePassEvent","处理人通过"),
    HANDLE_CANCEL_COMMUNICATE_EVENT("HandlerCancelCommunicateEvent","处理人取消沟通"),
    HANDLE_REFUSE_EVENT("handleRefuseEvent","处理人驳回"),
    HANDLE_RETURN_COMMUNICATE_EVENT("handlerReturnCommunicateEvent","处理人回复沟通");
    private String key;
    private String value;
    CbpmEventTypeEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
