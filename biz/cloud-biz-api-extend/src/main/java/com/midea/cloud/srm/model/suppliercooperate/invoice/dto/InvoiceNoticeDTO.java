package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *   InvoiceNoticeDTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/31 19:05
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class InvoiceNoticeDTO extends BaseDTO{

    /**
     * 开票通知ID
     */
    private Long invoiceNoticeId;

    /**
     * 组织ID(业务实体ID)
     */
    private Long orgId;

    /**
     * 组织编码(业务实体编码)
     */
    private String orgCode;

    /**
     * 组织名称(业务实体名称)
     */
    private String orgName;

    /**
     * 开票通知单号
     */
    private String invoiceNoticeNumber;

    /**
     * 开票通知单状态
     */
    private String invoiceNoticeStatus;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 成本类型
     */
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    private String ceeaCostTypeCode;

    /**
     * 开票日期
     */
    private LocalDate ceeaInvoiceDate;

    /**
     * 接收开始日期
     */
    private LocalDate ceeaReceiveStartDate;

    /**
     * 接收结束日期
     */
    private LocalDate ceeaReceiveEndDate;

    /**
     * 净价总金额
     */
    private BigDecimal ceeaNoTaxTotalAmount;

    /**
     * 含税总金额
     */
    private BigDecimal ceeaTaxTotalAmount;

    /**
     * 总税额
     */
    private BigDecimal ceeaTotalTax;

    /**
     * 汇率
     */
    private BigDecimal ceeaExchangeRate;

    /**
     * 是否需要供应商确认
     */
    private String ceeaIfSupplierConfirm;

    /**
     * 对账单总额(未税)
     */
    private BigDecimal statementTotalAmount;

    /**
     * 税控机发票总额(未税)
     */
    private BigDecimal invoiceTotalAmount;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 币种id
     */
    private Long currencyId;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 来源单号(可多个来源单号  用,分隔)
     */
    private String sourceNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 开票通知明细ID
     */
    private Long invoiceDetailId;

    /**
     * 事务处理类型
     */
    private String type;

    /**
     * 接收单号
     */
    private String receiveOrderNo;

    /**
     * 接收行号
     */
    private Integer receiveOrderLineNo;

    /**
     * 事务处理日期
     */
    private LocalDate dealDate;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织编码
     */
    private String organizationCode;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 组织地点(交货地点)
     */
    private String organizationSite;

    /**
     * 组织地点ID(交货地点ID)
     */
    private Long ceeaOrganizationSiteId;

    /**
     * 物料小类ID
     */
    private Long categoryId;

    /**
     * 物料小类名称
     */
    private String categoryName;

    /**
     * 物料小类编码
     */
    private String categoryCode;

    /**
     * 物料ID
     */
    private Long itemId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单位
     */
    private String unitCode;

    /**
     * 单价（含税）
     */
    private BigDecimal unitPriceContainingTax;

    /**
     * 单价（不含税）
     */
    private BigDecimal unitPriceExcludingTax;

    /**
     * 订单数量
     */
    private BigDecimal orderNum;

    /**
     * 接收数量
     */
    private BigDecimal receiveNum;

    /**
     * 接收日期
     */
    private Date receiveDate;

    /**
     * 采购订单号
     */
    private String orderNumber;

    /**
     * 订单行号
     */
    private Integer lineNum;

    /**
     * 合同序号
     */
    private String contractNo;

    /**
     * 净价金额
     */
    private BigDecimal noTaxAmount;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 含税金额
     */
    private BigDecimal taxAmount;

    /**
     * 项目编号
     */
    private String projectNum;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 任务编号
     */
    private String taskNum;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 未开票数量
     */
    private BigDecimal notInvoiceQuantity;

    /**
     * 本次开票数量
     */
    private BigDecimal invoiceQuantity;

    /**
     * 采购申请编号(longi)
     */
    private String ceeaRequirementHeadNum;

    /**
     * 业务小类
     */
    private String ceeaBusinessSmall;

    /**
     * 业务小类编码
     */
    private String ceeaBusinessSmallCode;

    /**
     * 订单明细ID
     */
    private Long orderDetailId;

    /**
     * 采购发运行号
     */
    private Long shipLineNum;

    /**
     * 事务处理类型id
     */
    private Long txnId;

    /**
     * 事务处理类型id(父事务处理类型id)
     */
    private Long parentTxnId;

    /**
     * 合同头ID
     */
    private Long contractHeadId;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 订单行状态
     */
    private String orderDetailStatus;
}
