package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商投标行[模型报价]明细行表
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ceea_brg_ord_line_templprice_l")
public class BidOrderLineTemplatePriceDetailLine extends BaseEntity {

    @TableId("ID")
    private Long        id;                 // 明细行表ID

    @TableField("HEADER_ID")
    private Long        headerId;           // 供应商投标行[模型报价]明细头ID

    @TableField("COST_TYPE")
    private String      costType;           // 费用类型

    @TableField("COST_DESCRIPTION")
    private String      costDescription;    // 费用描述

    @TableField("QUANTITY")
    private BigDecimal quantity;            // 数量

    @TableField("UNIT")
    private String      unit;               // 单位

    @TableField("TAX_UNIT_PRICE")
    private BigDecimal  taxUnitPrice;       // 含税单价

    @TableField("TAX_RATE")
    private BigDecimal  taxRate;            // 税率
    @TableField("TAX_KEY")
    private String  taxKey;             //税率编码

    @TableField("TAX_TOTAL_PRICE")
    private BigDecimal  taxTotalPrice;      // 含税总价

    @TableField("REMARK")
    private String      remark;             // 备注


    /* ============================ 默认字段 ============================ */

    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long    createdId;              // 创建人ID

    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String  createdBy;              // 创建人

    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date    creationDate;           // 创建时间

    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String  createdByIp;            // 创建人IP

    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long    lastUpdatedId;          // 最后更新人ID

    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String  lastUpdatedBy;          // 更新人

    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date    lastUpdateDate;         // 最后更新时间

    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String  lastUpdatedByIp;        // 最后更新人IP

    @TableField("TENANT_ID")
    private String  tenantId;               // 租户ID

    @TableField("VERSION")
    private Long    version;                // 版本号
}
