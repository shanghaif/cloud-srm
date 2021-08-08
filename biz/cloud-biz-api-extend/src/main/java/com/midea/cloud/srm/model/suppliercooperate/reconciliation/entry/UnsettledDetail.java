package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  未结算数量账单表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_unsettled_detail")
public class UnsettledDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("UNSETTLED_DETAIL_ID")
    private Long unsettledDetailId;

    /**
     * 未结算数量账单ID
     */
    @TableField("UNSETTLED_ORDER_ID")
    private Long unsettledOrderId;

    /**
     * 单据类型
     */
    @TableField("BILL_TYPE")
    private String billType;

    /**
     * 单据ID
     */
    @TableField("BILL_ID")
    private Long billId;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 单据编码
     */
    @TableField("BILL_NUMBER")
    private String billNumber;

    /**
     * 业务日期
     */
    @TableField("BUSINESS_DATE")
    private Date businessDate;

    /**
     * 订单编号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

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
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单价（未税）
     */
    @TableField("UNIT_PRICE_EXCLUDING_TAX")
    private BigDecimal unitPriceExcludingTax;

    /**
     * 未结算数量
     */
    @TableField("UNSETTLED_NUM")
    private BigDecimal unsettledNum;

    /**
     * 金额
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 未对账:UNRECONCILED(Y:是 默认,N:否)
     */
    @TableField("UNRECONCILED")
    private String unreconciled;

    /**
     * 未开票NOT_INVOICED(Y:是 默认,N:否)
     */
    @TableField("NOT_INVOICED")
    private String notInvoiced;

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
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}