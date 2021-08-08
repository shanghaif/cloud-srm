package com.midea.cloud.srm.model.logistics.baseprice.entity;

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
 *  物流招标基础价格 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 4:32:59 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_base_price")
public class BasePrice extends BaseEntity {
private static final long serialVersionUID = 715211L;
 /**
 * 基础价格ID
 */
 @TableId("BASE_PRICE_ID")
 private Long basePriceId;
 /**
 * 业务模式
 */
 @TableField("BUSINESS_MODE_CODE")
 private String businessModeCode;
 /**
 * 运输方式
 */
 @TableField("TRANSPORT_MODE_CODE")
 private String transportModeCode;
 /**
 * 行政区域编码
 */
 @TableField("REGION_CODE")
 private String regionCode;
 /**
 * 行政区域名称
 */
 @TableField("REGION_NAME")
 private String regionName;
 /**
 * 区域层级编码
 */
 @TableField("REGION_LEVEL_CODE")
 private String regionLevelCode;
 /**
 * 港口代码
 */
 @TableField("PORT_CODE")
 private String portCode;
 /**
 * 港口中文名称
 */
 @TableField("PORT_NAME_ZHS")
 private String portNameZhs;
 /**
 * 港口英文名称
 */
 @TableField("PORT_NAME_EN")
 private String portNameEn;
 /**
 * LEG 字典:LEG
 */
 @TableField("LEG")
 private String leg;
 /**
 * 费项 字典:CHARGE_NAME
 */
 @TableField("EXPENSE_ITEM")
 private String expenseItem;
 /**
 * 计费单位 字典:SUB_LEVEL
 */
 @TableField("CHARGE_UNIT")
 private String chargeUnit;
 /**
 * 计费方式 字典:CHARGE_LEVEL
 */
 @TableField("CHARGE_METHOD")
 private String chargeMethod;
 /**
 * 价格
 */
 @TableField("PRICE")
 private Long price;
 /**
 * 状态 字典:LOGISTICS_STATUS
 */
 @TableField("STATUS")
 private String status;
 /**
 * 创建人名字
 */
 @TableField("CREATED_BY_NAME")
 private String createdByName;
 /**
 * 更新人名字
 */
 @TableField("LAST_UPDATED_BY_NAME")
 private String lastUpdatedByName;

 /**
 * 创建人ID
 */
 @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
 private Long createdId;
 /**
 * 创建人
 */
 @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
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
}