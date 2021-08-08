package com.midea.cloud.srm.model.base.questionairesurvey.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
* <pre>
 *  问卷调查 模型
 * </pre>
*
* @author yancj@1.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 14, 2021 5:07:37 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_survey_question")
public class SurveyQuestion extends BaseEntity {
private static final long serialVersionUID = 112587L;
 /**
 * 问题ID
 */
 @TableId("QUESTION_ID")
 private Long questionId;
 /**
 * 问卷ID；SCC_BASE_SURVEY_HEADER.SURVEY_ID
 */
 @TableField("SURVEY_ID")
 private Long surveyId;
 /**
 * 问题序号
 */
 @TableField("ORDER_NUM")
 private Long orderNum;
 /**
 * 问题名称
 */
 @TableField("QUESTION_NAME")
 private String questionName;
 /**
 * 问题类型，S：单选题；M：多选题；Q：问答题
 */
 @TableField("QUESTION_TYPE")
 private String questionType;
 /**
 * 最多可选项
 */
 @TableField("MAX_SELECTION")
 private Long maxSelection;
 /**
 * 是否员工调查；Y：是；N：否
 */
 @TableField("EMPLOYEE_FLAG")
 private String employeeFlag;
 /**
 * 选项数量
 */
 @TableField("SELECTION_COUNT")
 private Long selectionCount;
  /**
  * 创建人ID
  */
  @TableField(value ="CREATED_ID",fill = FieldFill.INSERT)
  private Long createdId;
 /**
 * 创建人
 */
 @TableField(value = "CREATED_BY",fill = FieldFill.INSERT)
 private String createdBy;
 /**
 * 创建时间
 */
 @TableField(value = "CREATION_DATE",fill = FieldFill.INSERT)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @TableField(value ="CREATED_BY_IP" ,fill = FieldFill.INSERT)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @TableField(value = "LAST_UPDATED_ID",fill = FieldFill.INSERT_UPDATE)
 private Long lastUpdatedId;
 /**
 * 最后更新人
 */
 @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
 private Date lastUpdateDate;
 /**
 * 最后更新人IP
 */
 @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @TableField("TENANT_ID")
 private Long tenantId;
 /**
 * 版本号
 */
 @TableField("VERSION")
 private Long version;
 /**
  * 改问题答题总数量
  */
 @TableField(exist = false)
 private int totalCount;

 /**
  * 角色
  */
 @TableField(exist = false)
 private List<String> jobList;
}