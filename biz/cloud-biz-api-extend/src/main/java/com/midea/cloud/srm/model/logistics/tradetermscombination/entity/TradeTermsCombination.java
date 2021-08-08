package com.midea.cloud.srm.model.logistics.tradetermscombination.entity;

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
 *  贸易术语组合 模型
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Mar 3, 2021 11:52:27 AM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_trade_terms_combination")
public class TradeTermsCombination extends BaseEntity {
private static final long serialVersionUID = 360826L;
 /**
 * 主键ID
 */
 @TableId("TRADE_TERMS_COMBINATION_ID")
 private Long tradeTermsCombinationId;
 /**
 * 组合名称
 */
 @TableField("COMBINATION_NAME")
 private String combinationName;
 /**
 * 贸易术语名称
 */
 @TableField("TRADE_TERMS_NAME")
 private String tradeTermsName;
 /**
 * 贸易术语编码
 */
 @TableField("TRADE_TERMS_CODE")
 private String tradeTermsCode;
 /**
 * 进出口名称
 */
 @TableField("IMPORT_EXPORT_NAME")
 private String importExportName;
 /**
 * 进出口编码
 */
 @TableField("IMPORT_EXPORT_CODE")
 private String importExportCode;
 /**
 * 状态
  * @see com.midea.cloud.common.enums.logistics.LogisticsStatus
 */
 @TableField("LOGISTICS_STATUS")
 private String logisticsStatus;
 /**
 * LEG编码
 */
 @TableField("LEG_CODE")
 private String legCode;
 /**
 * 费用编码
 */
 @TableField("FEE_CODE")
 private String feeCode;
 /**
 * 费用项
 */
 @TableField("FEE_NAME")
 private String feeName;
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
 private String tenantId;
 /**
 * 版本号
 */
 @TableField("VERSION")
 private Long version;
 /**
 * 创建人中文名
 */
 @TableField("CREATED_BY_NAME")
 private String createdByName;
}