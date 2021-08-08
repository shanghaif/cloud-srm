package com.midea.cloud.srm.model.base.questionairesurvey.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
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
@TableName("scc_base_survey_header")
public class SurveyHeader extends BaseEntity {
private static final long serialVersionUID = 256962L;
 /**
 * 问卷ID
 */
 @TableId("SURVEY_ID")
 private Long surveyId;
 /**
 * 问卷版本号
 */
 @TableField("SURVEY_NUM")
 private String surveyNum;
 /**
 * 事业部编码
 */
 @TableField("BU_CODE")
 private String buCode;
 /**
 * 事业部ID
 */
 @TableField("BU_ID")
 private Long buId;
 /**
 * 问卷标题
 */
 @TableField("SURVEY_TITLE")
 private String surveyTitle;
 /**
 * 问卷说明
 */
 @TableField("SURVEY_DESC")
 private String surveyDesc;
 /**
 * 问卷状态，DRAFT：拟定；PUBLISHING：发布中；PUBLISHED：已发布；COMPLETED：已完成
 */
 @TableField("STATUS_CODE")
 private String statusCode;
 /**
 * 供应商发布范围，ALL：全集团范围；PART：事业部范围
 */
 @TableField("VENDOR_SCOPE_FLAG")
 private String vendorScopeFlag;
 /**
 * 反馈范围，ALL：所有人；CURRENT：当前
 */
 @TableField("FEEDBACK_FLAG")
 private String feedbackFlag;
 /**
 * 附件ID
 */
 @TableField("FILE_RELATION_ID")
 private String fileRelationId;
 /**
 * 附件名称
 */
 @TableField("FILE_NAME")
 private String fileName;
 /**
 * 发布时间
 */
 @TableField("PUBLISH_DATE")
 private Date publishDate;
 /**
  * 是否附件必填
  */
 @TableField("FILE_FLAG")
 private String fileFlag;
 /**
 * 反馈截止时间
 */
 @TableField("END_DATE")
 private Date endDate;
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
* 明细行
*/
@TableField(exist = false)
private List<SurveyQuestionDTO> surveyQuestionDTOList;

/**
 * 供应商已反馈数
 */
@TableField(exist = false)
 private Long backCount;

 /**
  * 供应商总数
  */
 @TableField(exist = false)
 private Long totalCount;
}