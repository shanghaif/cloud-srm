package com.midea.cloud.flow.common.constant;

/**
 * <pre>
 * 流程相关参数公共类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/12 20:45
 *  修改内容:
 * </pre>
 */
public class WorkFlowConst {
    /**删除流程(硬删除,慎用)*/
    public static final String DELETE_PROCESS = "deleteProcess";
    /**获取流程模板*/
    public static final String GET_FLOW_TEMPLATE = "getFlowTemplate";
    /**是否存在流程 */
    public static final String IS_EXIST_PROCESS = "isExistProcess";
    /**初始化流程*/
    public static final String INIT_PROCESS = "initProcess";
    /**获取所有流程节点信息 */
    public static final String GET_PROCESS_NODES_INFO = "getProcessNodesInfo";
    /**根据模板ID获取模板详情 */
    public static final String GET_TEMPLATE_INFO = "getTemplateInfo";
    /**获取用户待处理的流程列表 */
    public static final String GET_MY_RUNNING_PROCESS = "getMyRunningProcess";
    /**获取流程待审人信息 */
    public static final String GET_PROCESS_APPROVE_INFO = "getProcessApproveInfo";
    /**获取用户处理相关的节点信息 */
    public static final String LIST_FLOW_NODE_INFO = "listFlowNodeInfo";
    /**获取流程状态 */
    public static final String GET_PROCESS_STATUS = "getProcessStatus";
    /**获取所有流程列表 */
    public static final String GET_PROCESS_LIST ="getProcessList";
    /**获取所有流程总记录数 */
    public static final String GET_PROCESS_COUNT ="getProcessCount";
    /**获取流程详情 */
    public static final String GET_PROCESS_INFO = "getProcessInfo";
    /**获取审批意见 */
    public static final String GET_AUDITE_NOTE = "getAuditeNote";
    /**获取流程审批记录 */
    public static final String GET_FLOW_LOG = "getFlowLog";
    /**获取流程用户相关数量 */
    public static final String SELECT_USER_PROCESS_COUNT = "selectUserProcessCount";
    /**获取用户已审批/处理的流程列表 */
    public static final String GET_MY_WORKED_PROCESS ="getMyWorkedProcess";
    /**获取可驳回的节点 */
    public static final String GET_PROCESS_REFUSE_NODE= "getProcessRefuseNode";
    /**获取历史审批意见和未来审批节点接口 */
    public static final String GET_AUDITE_NOTE_IN_ALL_NODES= "getAuditeNoteInAllNodes";
    /**获取上次审批人 */
    public static final String GET_PREV_PROCESS_APPROVERS = "getPrevProcessApprovers";
    /**获取模板节点事件列表 */
    public static final String LIST_TEMPLATE_NODE_EVENTS="listTemplateNodeEnvents";
    /**获取我启动的流程 */
    public static final String GET_MY_START_PROCESS ="getMyStartProcess";
    /**获取抄送给我的流程 */
    public static final String GET_SEND_NODES_TO_ME="getSendNodesToMe";
    /**获取流程传阅信息 */
    public static final String LIST_CIRCULATE_NOTE ="listCirculateNote";
    /**获取传阅给我的流程 */
    public static final String GET_CIRCULARIZE_NODES_TO_ME = "getCircularizeNodesToMe";
    /**获取流程的某些信息 */
    public static final String GET_PROCESS_INFO_BY_ID = "getProcessInfoById";
    /**获取流程待审人信息(批量) */
    public static final String GET_PROCESS_APPROVE_INFO_LIST = "getProcessApproveInfoList";
    /**获取根据角色分组后的流程可用操作 */
    public static final String GET_OPERATION_LIST = "getOperationList";
    /**更新模板权限（全量更新） */
    public static final String UPDATE_TEMPLATE_AUTH = "updateTemplateAuth";
    /**获取超级驳回的可驳回节点 */
    public static final String  GET_PROCESS_SUPER_REFUSE_NODE = "getProcessSuperRefuseNode";
    /**获取用户常用回复 */
    public static final String SELECT_COMMON_REPLY = "selectCommomReply";
    /**获取流程表格与人工决策选择项列表 */
    public static final String GET_PROCESS_TABLE_INFO_AND_MANUAL_NODES = "getProcessTableInfoAndManualNodes";
    /**更新流程主题 */
    public static final String UPDATE_PROCESS_SUBJECT = "updateProcessSubject";
    /**替换流程实例处理人 */
    public static final String REPLACE_PROCESS_HANDLER = "replaceProcessHandler";
    /**审批流程接口（包括：通过、驳回、沟通、转办、回复沟通、取消沟通、起草人提交、保存草稿、起草人废弃、起草人撤回、审批人废弃、传阅、催办） */
    public static final String APPROVE_PROCESS ="approveProcess";

    public static final String BUSINESS_ID_NOT_NULL = "业务单据ID不能为空";
    public static final String  BUDINESS_KEY_NOT_NULL = "模板ID不能为空";
    public static final String  PROCESS_PARAM_NOT_NULL = "流程参数不能为空";
    public static final String  OPERATION_TYPE_NOT_NULL = "审批人流程处理方式不能为空";
    public static final String  TEMPLATE_INSTANCE_NOT_NULL = "流程实例不能为空";
    /**功能菜单ID不能为空*/
    public static final String  FUNCTION_ID__NOT_NULL = "功能菜单ID不能为空";
    public static final String  TASK_ID__NOT_NULL = "任务ID不能为空";
    public static final String  ACTIVITY_TYPE_NOT_NULL = "节点任务类型不能为空";
    public static final String  TO_OTHER_PERSONS_NOT_NULL = "转办，传阅、沟通用户登录名不能为空";
    public static final String  REFUSE_PASSED_TO_THIS_NODE_NOT_NULL = "驳回后是否直接返回本节点不能为空";
    public static final String  FORM_PARAM_NOT_NULL = "表单不能为空";
    public static final String  SUBJECT_NOT_NULL = "流程主题不能为空";
    /**用户名不能为空*/
    public static final String  LOGIN_NAME_NOT_NULL = "用户名不能为空";
    /**用户ID不能为空*/
    public static final String  USER_ID_NOT_NULL = "用户ID不能为空";
    /**流程已存在*/
    public static final String PROCESS_IS_XISITS="流程已存在";
    /**流程名称不能为空*/
    public static final String SUBUJECT_NOT_NULL ="流程名称不能为空";
    /**业务系统模板ID不能为空*/
    public static final String FORM_TEMPLATE_ID = "业务系统模板ID不能为空";
    /**操作类型不能为空*/
    public static final String OPTION_TYPE_NOT_NULL = "操作类型不能为空";
    /**获取当前流程信息时报错*/
    public static final String GET_CURRENT_PROCESS_INFO = "获取当前流程信息时报错";
    /**菜单ID不能为空*/
    public static final String MENU_ID_NOT_NULL = "菜单ID不能为空";
    /**流程模板Code不能为空*/
    public static final String TEMPLATE_CODE_NOT_NULL = "流程模板Code不能为空";
}
