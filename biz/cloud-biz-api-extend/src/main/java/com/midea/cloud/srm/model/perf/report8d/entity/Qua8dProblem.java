package com.midea.cloud.srm.model.perf.report8d.entity;

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
@TableName("scc_perf_qua_8d_problem")
public class Qua8dProblem extends BaseEntity {
private static final long serialVersionUID = 422710L;
 /**
 * 序号
 */
 @TableId("PROBLEM_ID")
 private Long problemId;
 /**
  * 8D报告编号
  */
 @TableField("REPORT_ID")
 private Long reportId;
 /**
 * 检规项
 */
 @TableField("CHECK_LIST")
 private String checkList;
 /**
 * 检规内容
 */
 @TableField("CHECK_INFOR")
 private String checkInfor;
 /**
 * AQL标准值
 */
 @TableField("AQL_STANDARD_NUM")
 private Long aqlStandardNum;
 /**
 * 固定抽检数量
 */
 @TableField("FIXED_CHECK_TOTAL")
 private Long fixedCheckTotal;
 /**
 * 检验工具
 */
 @TableField("CHECK_TOOL")
 private String checkTool;
 /**
 * 抽样数
 */
 @TableField("SAMPLE_TOTAL")
 private Long sampleTotal;
 /**
 * AC
 */
 @TableField("AC")
 private String ac;
 /**
 * RE
 */
 @TableField("RE")
 private String re;
 /**
 * 不良数
 */
 @TableField("UNQUALIFIED_TOTAL")
 private Long unqualifiedTotal;
 /**
 * 检验记录
 */
 @TableField("CHECK_RECORD")
 private String checkRecord;
 /**
 * 结果判定
 */
 @TableField("RESULT_JUDGE")
 private String resultJudge;
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
}