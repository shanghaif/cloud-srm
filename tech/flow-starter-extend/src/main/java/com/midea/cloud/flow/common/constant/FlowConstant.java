package com.midea.cloud.flow.common.constant;

/**
 * 工作流常量
 * @author lizl7
 *
 */
public class FlowConstant {

	/**
	 * 产品工作流集成模式
	 */
	public static String IntegrationMode_Product = "Produc";
	/**
	 * iframe嵌入页面模式
	 */
	public static String IntegrationMode_Iframe = "Iframe";
	/**
	 * 自带页面模式
	 */
	public static String IntegrationMode_Self = "Self";
	/**
	 * 无页面推送模式
	 */
	public static String IntegrationMode_Noui = "Push";
	/**
	 * 直接审批模式
	 */
	public static String IntegrationMode_None = "None";
	
	
	/**
	 * 提交审批 FlowHandle_COMMIT
	 */
	public static String Flow_HandleValue_SUBMIT = "FlowHandle_SUBMIT";//提交审批
	/**
	 * 通过 FlowHandle_PASS
	 */
	public static String Flow_HandleValue_PASS = "FlowHandle_PASS";//通过
	/**
	 * 驳回
	 */
	public static String Flow_HandleValue_REJECT = "FlowHandle_REJECT";//驳回
	/**
	 * 结束
	 */
	public static String Flow_HandleValue_ENDED = "FlowHandle_ENDED";//结束
	/**
	 * 催办
	 */
	public static String Flow_HandleValue_URGE = "FlowHandle_URGE";//催办
	/**
	 * 撤回
	 */
	public static String Flow_HandleValue_WITHDRAW = "FlowHandle_WITHDRAW";//撤回
	/**
	 * 废弃
	 */
	public static String Flow_HandleValue_DESTORY = "FlowHandle_DESTORY";//废弃
	/**
	 * 转办
	 */
	public static String Flow_HandleValue_TRANSFER = "FlowHandle_TRANSFER";//转办
	/**
	 * 沟通
	 */
	public static String Flow_HandleValue_CONSULT = "FlowHandle_CONSULT";//沟通
	/**
	 * 取消沟通
	 */
	public static String Flow_HandleValue_CLCONSULT = "FlowHandle_CLCONSULT";//取消沟通
	/**
	 * 回复
	 */
	public static String Flow_HandleValue_REPLY = "FlowHandle_REPLY";//回复
	
	
	
	/**
	 * 发起流程
	 */
	public static String Flow_Status_START = "FlowStatus_START";//发起流程
	/**
	 * 审批中,FlowStatus_PENDING
	 */
	public static String Flow_Status_PENDING = "FlowStatus_PENDING";//审批中
	/**
	 * 通过,FlowStatus_APPROVED
	 */
	public static String Flow_Status_APPROVED = "FlowStatus_APPROVED";//通过
	/**
	 * 驳回,FlowStatus_REJECTED
	 */
	public static String Flow_Status_REJECTED = "FlowStatus_REJECTED";//驳回
	/**
	 * 撤回,FlowStatus_WITHDRAW
	 */
	public static String Flow_Status_WITHDRAW = "FlowStatus_WITHDRAW";//撤回
	/**
	 * 废弃,FlowStatus_DESTORY
	 */
	public static String Flow_Status_DESTORY = "FlowStatus_DESTORY";//废弃

}
