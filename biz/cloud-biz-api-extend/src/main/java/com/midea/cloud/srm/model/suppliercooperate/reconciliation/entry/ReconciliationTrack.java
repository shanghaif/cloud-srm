package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.BaseErrorCell;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  对账单跟踪表 模型
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
@TableName("scc_sc_reconciliation_track")
public class ReconciliationTrack extends BaseErrorCell {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("RECONCILIATION_TRACK_ID")
    private Long reconciliationTrackId;

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
     * 采购组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

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
     * 单据类型
     */
    @TableField("BILL_TYPE")
    private String billType;

    /**
     * 单据编码
     */
    @TableField("BILL_CODE")
    private String billCode;

    /**
     * 业务日期
     */
    @TableField("BUSINESS_DATE")
    private Date businessDate;

    /**
     * 币别
     */
    @TableField("RFQ_SETTLEMENT_CURRENCY")
    private String rfqSettlementCurrency;

    /**
     * 价税合计
     */
    @TableField("PRICE_TAX_SUM")
    private BigDecimal priceTaxSum;

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 订单编号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 入库单ID
     */
    @TableField("WAREHOUSE_RECEIPT_ID")
    private Long warehouseReceiptId;

    /**
     * 入库单编号
     */
    @TableField("WAREHOUSE_RECEIPT_NUMBER")
    private String warehouseReceiptNumber;

    /**
     * 退货单ID
     */
    @TableField("RETURN_ORDER_ID")
    private Long returnOrderId;

    /**
     * 退货单编号
     */
    @TableField("RETURN_ORDER_NUMBER")
    private String returnOrderNumber;

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