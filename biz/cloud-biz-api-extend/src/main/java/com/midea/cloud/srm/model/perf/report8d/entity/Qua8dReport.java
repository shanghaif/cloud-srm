package com.midea.cloud.srm.model.perf.report8d.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
/**
* <pre>
 *  8D报告 模型
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:47:57 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_qua_8d_report")
public class Qua8dReport extends BaseEntity {
private static final long serialVersionUID = 565405L;
 /**
 * 报告编号
 */
 @TableId("REPORT_ID")
 private Long reportId;
 /**
 * 报告状态
 */
 @TableField("REPORT_STATUS")
 private String reportStatus;
 /**
 * 打印选项
 */
 @TableField("PRINT_OPTIONS")
 private String printOptions;
 /**
 * 来源单据类型
 */
 @TableField("DOCUMENT_TYPE")
 private String documentType;
 /**
 * 来源单号
 */
 @TableField("SOURCE_ORDER_ID")
 private Long sourceOrderId;
 /**
 * 异常单负责人
 */
 @TableField("EXCEPTION_ORDER_AGENT")
 private String exceptionOrderAgent;
 /**
 * 发出日期
 */
 @TableField("SEND_DATE")
 private Date sendDate;
 /**
 * 回复日期
 */
 @TableField("RESPONSE_DATE")
 private Date responseDate;
 /**
 * 发出单位
 */
 @TableField("SEND_DEPARTMENT")
 private String sendDepartment;
 /**
 * 接收单位
 */
 @TableField("RECEIVE_DEPARTMENT")
 private String receiveDepartment;
 /**
 * 品质审核人
 */
 @TableField("QUALITY_CHECKER")
 private String qualityChecker;
 /**
 * 物料编码
 */
 @TableField("MATERIAL_CODE")
 private Long materialCode;
 /**
 * 检验标准
 */
 @TableField("CHECK_STANDARD")
 private String checkStandard;
 /**
 * 物料名称
 */
 @TableField("MATERIAL_NAME")
 private String materialName;
 /**
 * 物料状态
 */
 @TableField("MATERIAL_STATUS")
 private String materialStatus;
 /**
 * 3个月内发生次数
 */
 @TableField("THREE_MONTHS_HAPPENS")
 private Long threeMonthsHappens;
 /**
 * 供应商
 */
 @TableField("VENDOR_NAME")
 private String vendorName;
 /**
 * DATE CODE
 */
 @TableField("DATE_CODE")
 private Long dateCode;
 /**
 * LOT NO
 */
 @TableField("LOT_NO")
 private Long lotNo;
 /**
 * 不良描述
 */
 @TableField("NG_DESCRIBE")
 private String ngDescribe;
 /**
 * 组长(部门/职位/姓名)
 */
 @TableField("GROUP_LEADER")
 private String groupLeader;
 /**
 * 成员(部门/职位/姓名)
 */
 @TableField("GROUP_MEMBER")
 private String groupMember;
 /**
 * 根本原因追查why1
 */
 @TableField("WHY_1")
 private String why1;
 /**
 * why2
 */
 @TableField("WHY_2")
 private String why2;
 /**
 * why3
 */
 @TableField("WHY_3")
 private String why3;
 /**
 * why4
 */
 @TableField("WHY_4")
 private String why4;
 /**
 * why5
 */
 @TableField("WHY_5")
 private String why5;
 /**
 * 制程流出原因
 */
 @TableField("PROCESS_OUTFLOW_REASON")
 private String processOutflowReason;
 /**
 * 品质流出原因
 */
 @TableField("QUALITY_OUTFLOW_REASON")
 private String qualityOutflowReason;
 /**
 * 客户端确认数量
 */
 @TableField("CLIENT_CONFIRMATION_TOTAL")
 private Long clientConfirmationTotal;
 /**
 * 在途确认数量
 */
 @TableField("TRANSIT_CONFIRMATION_TOTAL")
 private Long transitConfirmationTotal;
 /**
 * 仓库库存确认数量
 */
 @TableField("ORGANIZATION_CONFIRMATION_TOTAL")
 private Long organizationConfirmationTotal;
 /**
 * 客户端确认处理方式
 */
 @TableField("CLIENT_HANDLE_WAY")
 private String clientHandleWay;
 /**
 * 在途确认处理方式
 */
 @TableField("TRANSIT_HANDLE_WAY")
 private String transitHandleWay;
 /**
 * 仓库库存处理方式
 */
 @TableField("ORGANIZATION_HANDLE_WAY")
 private String organizationHandleWay;
 /**
 * 责任人
 */
 @TableField("INTERIM_MEASURES_AGENT")
 private String interimMeasuresAgent;
 /**
 * 临时措施处理日期
 */
 @TableField("INTERIM_MEASURES_DATE")
 private Date interimMeasuresDate;
 /**
 * 临时措施完成日期
 */
 @TableField("INTERIM_MEASURES_FINISH_DATE")
 private Date interimMeasuresFinishDate;
 /**
 * 对策者
 */
 @TableField("COUNTERMEASURE_PERSON")
 private String countermeasurePerson;
 /**
 * 执行部门
 */
 @TableField("EXCUTE_DEPARTMENT")
 private String excuteDepartment;
 /**
 * 永久对策完成日期
 */
 @TableField("COUNTERMEASURE_FINISH_DATE")
 private Date countermeasureFinishDate;
 /**
 * 执行者
 */
 @TableField("EXCUTE_PERSON")
 private String excutePerson;
 /**
 * 根本原因对策
 */
 @TableField("COUNTERMEASURE_ROOT_REASON")
 private String countermeasureRootReason;
 /**
 * 流出原因对策
 */
 @TableField("COUNTERMEASURE_OUTFLOW_REASON")
 private String countermeasureOutflowReason;
 /**
 * 来料检验报告单号1
 */
 @TableField("ITEM_EXCEPTION_ID_1")
 private String itemExceptionId1;
 /**
 * 检验结果1
 */
 @TableField("CHECK_RESULT_1")
 private String checkResult1;
 /**
 * 来料检验报告单号2
 */
 @TableField("ITEM_EXCEPTION_ID_2")
 private String itemExceptionId2;
 /**
 * 检验结果2
 */
 @TableField("CHECK_RESULT_2")
 private String checkResult2;
 /**
 * 来料检验报告单号3
 */
 @TableField("ITEM_EXCEPTION_ID_3")
 private String itemExceptionId3;
 /**
 * 检验结果3
 */
 @TableField("CHECK_RESULT_3")
 private String checkResult3;
 /**
 * 改善前
 */
 @TableField("BEFORE_IMPROVEMENT")
 private String beforeImprovement;
 /**
 * 改善后
 */
 @TableField("AFTER_IMPROVEMENT")
 private String afterImprovement;
 /**
 * 旧流程废除日期
 */
 @TableField("OLD_PROCESS_ABOLISH_DATE")
 private Date oldProcessAbolishDate;
 /**
 * 新流程执行日期
 */
 @TableField("NEW_PROCESS_ABOLISH_DATE")
 private Date newProcessAbolishDate;
 /**
 * 平行展开
 */
 @TableField("PARALLEL_EXPANSION")
 private String parallelExpansion;
 /**
 * 制定人
 */
 @TableField("MADE_BY")
 private String madeBy;
 /**
 * 小组总结审核人
 */
 @TableField("GROUP_SUMMARY_CHECKER")
 private String groupSummaryChecker;
 /**
 * 批准人
 */
 @TableField("APPROVED_BY")
 private String approvedBy;
 /**
 * 发出单位审核结论是否合格
 */
 @TableField("IS_QUALIFIED")
 private String isQualified;
 /**
 * 关闭原因
 */
 @TableField("CLOSE_REASON")
 private String closeReason;
 /**
 * 审核结论
 */
 @TableField("CHECK_CONCLUSION")
 private String checkConclusion;
 /**
 * 审核时间
 */
 @TableField("CHECK_DATE")
 private Date checkDate;
 /**
 * 审核人
 */
 @TableField("REPORT_8D_CHECKER")
 private String report8DChecker;
 /**
 * 提交审核人
 */
 @TableField("SUBMIT_CHECKER")
 private String submitChecker;
 /**
 * 审核备注
 */
 @TableField("CHECK_COMMENT")
 private String checkComment;
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
 @TableField(value = "LAST_UPDATED_ID",fill = FieldFill.UPDATE)
 private Long lastUpdatedId;
 /**
 * 最后更新人
 */
 @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @TableField(value = "LAST_UPDATED_DATE", fill = FieldFill.UPDATE)
 private Date lastUpdatedDate;
 /**
 * 最后更新人IP
 */
 @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @TableField("TENANT_ID")
 private String tenantId;
 /**
 * 版本号
 */
 @TableField("VERSION")
 private Long version;
/**
* 明细行
*/
@TableField(exist = false)
private List<Qua8dProblem> qua8DProblemList;
 /**
  * 文件上传明细行
  */
 @TableField(exist = false)
 private List<Fileupload> fileUploads;

}