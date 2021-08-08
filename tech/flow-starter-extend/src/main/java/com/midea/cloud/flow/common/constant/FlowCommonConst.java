package com.midea.cloud.flow.common.constant;

/**
 * <pre>
 *  flowCenter模块公共用的常数类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 15:38
 *  修改内容:
 * </pre>
 */
public class FlowCommonConst {

    /**成功调用接口ResultCode*/
    public static final String SUCCESS_CODE_010 = "010";
    public static final String ID_IS_NOT_NULL = "id不能为空";
    /**是否删除-0不删除*/
    public static final Integer DELETE_FLAG_NO = 0;
    /**是否删除-1不删除*/
    public static final Integer DELETE_FLAG_YES = 1;
    /**版本号-0*/
    public static final Long VERSION_0 = 0L;
    /**WorkFlowFeignClient类对应的url最后名称*/
    public static final String FUNCTION_HANDLER = "cbpmBaseFeignHandler";
    /**资质审查流程标题头**/
    public static final String NEW_REVIEW_FROM_PROCESS_TITLE = "资质审查";
    /**下划线**/
    public static final String BAR_UNDERLINE = "_";
    /**获取流程表格和审批人列表-用户类型*/
    public static final String  USER_TYPE = "USER";
    /**获取流程表格和审批人列表-部门类型*/
    public static final String DEPARTMETN_TYPE = "DEPARTMETN ";
    /**供应商类型的用户类型*/
    public static final String USER_TYPE_BUYER = "BUYER";
    /**Cbpm body参数不能为空*/
    public static final String CBPM_BODY_NOT_NULL = "Cbpm body参数不能为空";
    /**Cbpm formParam参数不能为空*/
    public static final String CBPM_FORM_PARAM_NOT_NULL = "Cbpm form Param参数不能为空";
    /**appId不能为空*/
    public static final String APPID_NOT_NULL = "appId不能为空";
    /**token不能为空*/
    public static final String TOKEN_NOT_NULL = "token不能为空";
    /**事件回调-成功标志**/
    public static final String SUCCESS = "success";
    /**流程事件回调时*/
    public static final String EVENT_TYPE_CALLBACK= "EventType_Callback";
    /**流程事件待定时*/
    public static final String EVENT_STATUS_PENDDING = "EventStatus_Pendding";
    /**流程事件错误时*/
    public static final String EVENT_STATUS_ERROR = "EventStatus_Error";
    /**CBPM流程引擎标记*/
    public static final String CBPM = "CBPM";
    /**N1流程节点标记*/
    public static final String NODE_ID_N1 = "N1";
    /**当前页*/
    public static final Integer PAGE = 1;
    /**每页显示数*/
    public static final Integer PAGE_SIZE = 10;
    /**租户不能为空*/
    public static final String TENANTI_ID_NOT_NULL = "租户不能为空";
    /**盐值不能为空*/
    public static final String SALT_NOT_NULL = "盐值不能为空";
    /**appId不相等*/
    public static final String APPID_NOT_EQUALS = "appId不相等";
}
