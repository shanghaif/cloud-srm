package com.midea.cloud.srm.model.perf.processexceptionhandle.entity;

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
 *  制程异常处理单 模型
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:45:00 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_qua_process_exception")
public class QuaProcessException extends BaseEntity {
private static final long serialVersionUID = 506203L;
 /**
 * 单据编码
 */
 @TableId("BILL_CODE")
 private Long billCode;
 /**
 * 问题状态
 */
 @TableField("PROBLEM_STATUS")
 private String problemStatus;
 /**
 * 处理结果
 */
 @TableField("HANDLE_RESULT")
 private String handleResult;
 /**
 * 业务实体ID
 */
 @TableField("ORG_ID")
 private Long orgId;
 /**
 * 业务实体名字
 */
 @TableField("ORG_NAME")
 private String orgName;
 /**
 * 分属事业部
 */
 @TableField("BU_NAME")
 private String buName;
 /**
 * 负责人
 */
 @TableField("PROCESS_AGENT")
 private String processAgent;
 /**
 * 批号
 */
 @TableField("BATCH_CODE")
 private Long batchCode;
 /**
 * 订单数量
 */
 @TableField("ORDER_TOTAL")
 private Long orderTotal;
 /**
 * 工单
 */
 @TableField("ORDER_CODE")
 private Long orderCode;
 /**
 * 产品代码
 */
 @TableField("PRODUCTION_CODE")
 private Long productionCode;
 /**
 * 产品描述
 */
 @TableField("PRODUCTION_DESC")
 private String productionDesc;
 /**
 * 供应商已回复
 */
 @TableField("SUPPLIER_RESPONSED")
 private String supplierResponsed;
 /**
 * 投入数量
 */
 @TableField("INVESTMENT_TOTAL")
 private Long investmentTotal;
 /**
 * 不良数量
 */
 @TableField("UNQUALIFIED_TOTAL")
 private Long unqualifiedTotal;
 /**
 * 不良率%
 */
 @TableField("UNQUALIFIED_RATE")
 private Long unqualifiedRate;
 /**
 * 外协工厂
 */
 @TableField("OUTSOURCING_FACTORY")
 private String outsourcingFactory;
 /**
 * 工站
 */
 @TableField("WORK_STATION")
 private String workStation;
 /**
 * 已包装数量
 */
 @TableField("PACKED_TOTAL")
 private Long packedTotal;
 /**
 * 供应商ID
 */
 @TableField("VENDOR_ID")
 private Long vendorId;
 /**
 * 供应商编码
 */
 @TableField("VENDOR_CODE")
 private String vendorCode;
 /**
 * 供应商名称
 */
 @TableField("VENDOR_NAME")
 private String vendorName;
 /**
 * 线体
 */
 @TableField("LINEAR_BODY")
 private String linearBody;
 /**
 * 位号
 */
 @TableField("TAG_CODE")
 private Long tagCode;
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
 * 3个月内发生次数
 */
 @TableField("THREE_MONTHS_HAPPENS")
 private Long threeMonthsHappens;
 /**
 * 物料编码
 */
 @TableField("MATERIAL_CODE")
 private Long materialCode;
 /**
 * 物料状态
 */
 @TableField("MATERIAL_STATUS")
 private String materialStatus;
 /**
 * 物料名称
 */
 @TableField("MATERIAL_NAME")
 private String materialName;
 /**
 * 备注
 */
 @TableField("PROCESS_COMMENTS")
 private String processComments;
 /**
 * 工厂临时措施
 */
 @TableField("FACTORY_TEMPORARY_MEASURES")
 private String factoryTemporaryMeasures;
 /**
 * 材料异常等级判定
 */
 @TableField("ITEM_EXCEPTION_LEVEL")
 private String itemExceptionLevel;
 /**
 * 是否需要回复8D报告
 */
 @TableField("REPORT_8D")
 private String report8D;
 /**
 * 材料异常责任方
 */
 @TableField("ITEM_EXCEPTION_NAME")
 private String itemExceptionName;
 /**
 * 材料异常责任备注
 */
 @TableField("ITEM_EXCEPTION_COMMENT")
 private String itemExceptionComment;
 /**
 * 不良描述
 */
 @TableField("UNQUALIFIED_DESC")
 private String unqualifiedDesc;
 /**
 * 不良分类
 */
 @TableField("UNQUALIFIED_TYPE")
 private String unqualifiedType;
 /**
 * 复现方法/不良现象复现率
 */
 @TableField("PROCESS_REPRODUCTION")
 private String processReproduction;
 /**
 * 功能缺陷
 */
 @TableField("FUCTION_DEFECT")
 private String fuctionDefect;
 /**
 * 工艺缺陷
 */
 @TableField("TECHNOLOGY_DEFECT")
 private String technologyDefect;
 /**
 * 工厂自身问题
 */
 @TableField("FACTORY_PROBLEM")
 private String factoryProblem;
 /**
 * 外观缺陷
 */
 @TableField("APPEARANCE_DEFECT")
 private String appearanceDefect;
 /**
 * 设计缺陷
 */
 @TableField("DESIGN_DEFECT")
 private String designDefect;
 /**
 * 其他缺陷
 */
 @TableField("OTHERS_DEFECT")
 private String othersDefect;
 /**
 * 退货处理
 */
 @TableField("RETURN_HANDLE")
 private String returnHandle;
 /**
 * 停线、待处理
 */
 @TableField("PROCES_PENDING")
 private String procesPending;
 /**
 * 换料生产
 */
 @TableField("REPLACE_ITEM")
 private String replaceItem;
 /**
 * 特采使用
 */
 @TableField("SPECIAL_PURCHASE_USE")
 private String specialPurchaseUse;
 /**
 * 未生产品数量及处理
 */
 @TableField("UNPRODUCTIVE_ITEM_HANDLE")
 private Long unproductiveItemHandle;
 /**
 * 涉及半成品数量及处理
 */
 @TableField("SEMI_ITEM_HANDLE")
 private String semiItemHandle;
 /**
 * 库存
 */
 @TableField("ORGANIZATION_TOTAL")
 private Long organizationTotal;
 /**
 * 涉及成品处理
 */
 @TableField("FINISHED_PRODUCT_HANDLE")
 private String finishedProductHandle;
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
 private Date lastUpdatedDated;
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