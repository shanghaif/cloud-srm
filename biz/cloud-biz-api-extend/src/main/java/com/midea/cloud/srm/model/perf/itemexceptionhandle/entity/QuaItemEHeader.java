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
import java.util.List;

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
 *  修改日期: Jan 27, 2021 7:33:19 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_qua_item_eheader")
public class QuaItemEHeader extends BaseEntity {

 private static final long serialVersionUID = 958240L;
 /**
 * 头行表异常单号ID
 */
 @TableId("ITEM_EXCEPTION_HEAD_ID")
 private Long itemExceptionHeadId;
 /**
 * 业务实体ID
 */
 @TableField("ORG_ID")
 private Long orgId;
 /**
 * 业务实体
 */
 @TableField("ORG_NAME")
 private String orgName;
 /**
 * 单据日期
 */
 @TableField("ORDER_DATE")
 private Date orderDate;
 /**
 * 物料ID
 */
 @TableField("MATERIAL_ID")
 private Long materialId;
 /**
 * 物料编码
 */
 @TableField("MATERIAL_CODE")
 private String materialCode;
 /**
 * 物料名称
 */
 @TableField("MATERIAL_NAME")
 private String materialName;
 /**
 * 进料总数
 */
 @TableField("ITEM_TOTAL")
 private Long itemTotal;
 /**
 * 检验单编号
 */
 @TableField("CHECK_LIST_ID")
 private Long checkListId;
 /**
 * 检验单类型
 */
 @TableField("CHECK_LIST_TYPE")
 private String checkListType;
 /**
 * 检验标准
 */
 @TableField("CHECK_STANDARD")
 private String checkStandard;
 /**
 * 工厂
 */
 @TableField("FACTORY_NAME")
 private String factoryName;
 /**
 * 供应商ID
 */
 @TableField("VENDOR_ID")
 private Long vendorId;
 /**
 * 供应商
 */
 @TableField("VENDOR_NAME")
 private String vendorName;
 /**
 * 采购负责人
 */
 @TableField("PURCHASE_AGENT")
 private String purchaseAgent;
 /**
 * LOT
 */
 @TableField("LOT_ID")
 private Long lotId;
 /**
 * 材料责任人
 */
 @TableField("ITEM_AGENT")
 private String itemAgent;
 /**
 * 环保责任人
 */
 @TableField("EP_AGENT")
 private String epAgent;
 /**
 * D/C
 */
 @TableField("DATE_CODE")
 private Long dateCode;
 /**
 * 保税物料
 */
 @TableField("FREE_TAX_ITEM")
 private String freeTaxItem;
 /**
 * 材料问题状态
 */
 @TableField("ITEM_STATUS")
 private String itemStatus;
 /**
 * 材料异常等级判定
 */
 @TableField("ITEM_EXCEPTION_LEVEL")
 private String itemExceptionLevel;
 /**
 * 材料异常责任备注
 */
 @TableField("ITEM_EXCEPTION_COMMENT")
 private String itemExceptionComment;
 /**
 * 材料异常责任方
 */
 @TableField("ITEM_EXCEPTION_NAME")
 private String itemExceptionName;
 /**
 * 是否需要回复8D报告
 */
 @TableField("REPORT_8D")
 private String report8D;
 /**
 * 环保问题状态
 */
 @TableField("EP_STATUS")
 private String epStatus;
 /**
 * 返工数量
 */
 @TableField("REWORK_TOTAL")
 private Long reworkTotal;
 /**
 * 合格数量
 */
 @TableField("QUALIFIED_TOTAL")
 private Long qualifiedTotal;
 /**
 * 不合格数量
 */
 @TableField("UNQUALIFIED_TOTAL")
 private Long unqualifiedTotal;
 /**
 * 返工方案
 */
 @TableField("REWORK_PLAN")
 private String reworkPlan;
 /**
  * 返工结论
  */
 @TableField("REWORK_CONCLUSION")
 private String reworkConclusion;
 /**
  * 异常工时
  */
 @TableField("EXCEPTION_WORK_HOUR")
 private Long exceptionWorkHour;
 /**
 * 不合格处理结果
 */
 @TableField("NG_HANDLE_RESULT")
 private String ngHandleResult;
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
 @TableField(value ="CREATED_BY_IP" , fill = FieldFill.INSERT)
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
  * 材料不合格描述行
  */
 @TableField(exist = false)
 private List<QuaItemNgDesc> quaItemNgDescList;
 /**
  * 环保不合格行
  */
 @TableField(exist = false)
 private List<QuaEPNgDesc> quaEPNgDescList;
 /**
  * 问题备注行
  */
 @TableField(exist = false)
 private List<QuaProblemComments> quaProblemCommentsList;
}