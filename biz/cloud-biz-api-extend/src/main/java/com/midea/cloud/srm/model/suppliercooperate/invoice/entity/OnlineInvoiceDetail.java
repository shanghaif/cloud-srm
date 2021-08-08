package com.midea.cloud.srm.model.suppliercooperate.invoice.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  网上开票-发票明细表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:48:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_online_invoice_detail")
public class OnlineInvoiceDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,网上开票明细ID
     */
    @TableId("ONLINE_INVOICE_DETAIL_ID")
    private Long onlineInvoiceDetailId;

    /**
     * 网上开票ID
     */
    @TableField("ONLINE_INVOICE_ID")
    private Long onlineInvoiceId;

    /**
     * 开票通知明细ID
     */
    @TableField("INVOICE_DETAIL_ID")
    private Long invoiceDetailId;

    /**
     * 开票通知ID
     */
    @TableField("INVOICE_NOTICE_ID")
    private Long invoiceNoticeId;

    /**
     * 开票通知单号
     */
    @TableField("INVOICE_NOTICE_NUMBER")
    private String invoiceNoticeNumber;

    /**
     * 发票行号
     */
    @TableField("INVOICE_ROW")
    private Integer invoiceRow;

    /**
     * 对比结果
     */
    @TableField("COMPARE_RESULT")
    private String compareResult;

    /**
     * 事务处理类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 接收单号
     */
    @TableField("RECEIVE_ORDER_NO")
    private String receiveOrderNo;

    /**
     * 接收行号
     */
    @TableField("RECEIVE_ORDER_LINE_NO")
    private Integer receiveOrderLineNo;

    /**
     * 接收日期
     */
    @TableField("RECEIVE_DATE")
    private Date receiveDate;

    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 物料ID
     */
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料名称
     */
    @TableField("ITEM_NAME")
    private String itemName;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 接收数量
     */
    @TableField("RECEIVE_NUM")
    private BigDecimal receiveNum;

    /**
     * 本次开票数量
     */
    @TableField("INVOICE_QUANTITY")
    private BigDecimal invoiceQuantity;

    /**
     * 未开票数量
     */
    @TableField("NOT_INVOICE_QUANTITY")
    private BigDecimal notInvoiceQuantity;

    /**
     * 单价（含税)
     */
    @TableField("UNIT_PRICE_CONTAINING_TAX")
    private BigDecimal unitPriceContainingTax;

    /**
     * 单价（不含税）(本次开票净价)
     */
    @TableField("UNIT_PRICE_EXCLUDING_TAX")
    private BigDecimal unitPriceExcludingTax;

    /**
     * 订单单价(不含税)
     */
    @TableField("ORDER_UNIT_PRICE_TAX_N")
    private BigDecimal orderUnitPriceTaxN;

    /**
     * 净价金额
     */
    @TableField("NO_TAX_AMOUNT")
    private BigDecimal noTaxAmount;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率计算值
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 税额
     */
    @TableField("TAX")
    private BigDecimal tax;

    /**
     * 含税金额
     */
    @TableField("TAX_AMOUNT")
    private BigDecimal taxAmount;

    /**
     * 合同序号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 合同头信息ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 项目编号
     */
    @TableField("PROJECT_NUM")
    private String projectNum;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 任务编号
     */
    @TableField("TASK_NUM")
    private String taskNum;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 明细备注
     */
    @TableField("COMMENT")
    private String comment;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
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
     * 行金额调整标记 Y:调整 N:非调整
     */
    @TableField("PO_UNIT_PRICE_CHANGE_FLAG")
    private String poUnitPriceChangeFlag;

    @TableField(exist = false)
    private Long orderDetailId;//订单明细ID

    /**
     * 采购发运行号
     */
    @TableField("SHIP_LINE_NUM")
    private Long shipLineNum;

    /**
     * 事务处理类型id
     */
    @TableField("TXN_ID")
    private Long txnId;

    /**
     * 事务处理类型id(父事务处理类型id)
     */
    @TableField("PARENT_TXN_ID")
    private Long parentTxnId;

}
