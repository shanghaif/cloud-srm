/**
 * 
 */
package com.midea.cloud.srm.model.workflow.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.km.review.webservice.AttachmentForm;
import lombok.Data;

/**
 * <pre>
 *  启动、审批、更新流程表单工作流实体类
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 上午10:44:24
  *  修改内容:
 * </pre>
 */
@Data
public class WorkflowFormDto implements Serializable {

	private static final long serialVersionUID = -7208417720240497530L;


	/**流程实例ID*/
	private String flowInstanceId;

	/**
	 * 标题
	 */
	private String docSubject;

	/**
	 * 文档模板ID
	 */
	private String fdTemplateId;

	/**文档的富文本内容**/
	private String docContent;

	/**
	 * 流程表表单值
	 */
	private JSONObject formValues;

	/**文档状态，可以为草稿（"10"）或者待审（"20"）两种状态，默认为待审*/
	private String docStatus;

	/**
	 * 业务ID
	 */
	private String formInstanceId;
	
	/**
	 * 流程发起人，为单值，格式详见人员组织架构的定义说明
	 */
	private JSONObject docCreator;

	/**辅类别，格式为["辅类别1的ID", "辅类别2的ID"...]*/
	private JSONObject docProperty;

	/**
	 * 附件
	 */
	private List<AttachmentForm> attachmentForms;
	private List<AttachFileDto>fileDtos;

	protected String attachmentValues;
	/**流程ID*/
	private String fdId;
	/**文档关键字，格式为["关键字1", "关键字2"...]*/
	private JSONObject fdKeyword;
	private String fdSource;
	/**流程参数*/
	private JSONObject flowParam;

}
