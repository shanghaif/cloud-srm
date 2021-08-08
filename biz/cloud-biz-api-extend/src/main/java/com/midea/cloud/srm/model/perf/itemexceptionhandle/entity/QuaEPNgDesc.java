package com.midea.cloud.srm.model.perf.itemexceptionhandle.entity;

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
 *  来料异常处理单 模型
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 27, 2021 7:40:23 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_qua_ep_ngdesc")
public class QuaEPNgDesc extends BaseEntity {
private static final long serialVersionUID = 377269L;
 /**
 * 环保不合格描述行表ID
 */
 @TableId("EP_NG_DESC_LINE_ID")
 private Long epNgDescLineId;
 /**
  * 头行表异常单号ID
  */
 @TableField("ITEM_EXCEPTION_HEAD_ID")
 private Long itemExceptionHeadId;
 /**
 * 检规项
 */
 @TableField("CHECK_LIST")
 private String checkList;
 /**
 * 测试项目
 */
 @TableField("TEST_PROJECT_NAME")
 private String testProjectName;
 /**
 * 含量
 */
 @TableField("EP_NG_ASSAY")
 private Long epNgAssay;
 /**
 * 固定值
 */
 @TableField("FIXED_NUM")
 private Long fixedNum;
 /**
 * 实际值
 */
 @TableField("ACTUAL_NUM")
 private Long actualNum;
 /**
 * 检验工具
 */
 @TableField("CHECK_TOOL")
 private String checkTool;
 /**
 * 测试记录
 */
 @TableField("CHECK_RECORD")
 private String checkRecord;
 /**
 * 检验结果
 */
 @TableField("CHECK_RESULT")
 private String checkResult;
 /**
 * 不良描述
 */
 @TableField("NG_DESCRIBE")
 private String ngDescribe;
 /**
 * 创建人ID
 */
 @TableField(value = "CREATED_ID",fill = FieldFill.INSERT)
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
 @TableField(value ="CREATED_BY_IP", fill = FieldFill.INSERT)
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