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

/**
* <pre>
 *  问卷调查 模型
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 17, 2021 5:30:44 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_survey_result")
public class SurveyResult extends BaseEntity {
private static final long serialVersionUID = 231888L;
 /**
 * 问卷结果ID
 */
 @TableId("RESULT_ID")
 private Long resultId;
 /**
 * 选项ID
 */
 @TableField("SELECTION_ID")
 private Long selectionId;
 /**
 * 发布供应商范围ID；SCC_BASE_SURVEY_SCOPE_VENDOR.SCOPE_ID
 */
 @TableField("VENDOR_SCOPE_ID")
 private Long vendorScopeId;
 /**
 * 问题ID；SRM_SYS_SURVEY_QUESTION.QUESTION_ID
 */
 @TableField("QUESTION_ID")
 private Long questionId;
 /**
 * 员工范围ID；SCC_BASE_SURVEY_SCOPE_EMPLOYEE.SCOPE_ID
 */
 @TableField("EMPLOYEE_SCOPE_ID")
 private Long employeeScopeId;
 /**
 * 问题结果内容；如选项A、B、C、D……
 */
 @TableField("RESULT_VALUE")
 private String resultValue;
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
}