package com.midea.cloud.srm.base.datatransfer.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  供应商投标行表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-31 11:12:02
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_order_line1")
public class DataTransferBrgOrderLine1 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_LINE_ID")
    private Long orderLineId;

    /**
     * 招标单ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 供应商投标头表ID
     */
    @TableField("ORDER_HEAD_ID")
    private Long orderHeadId;

    /**
     * 投标状态
     */
    @TableField("ORDER_STATUS")
    private String orderStatus;

    /**
     * 轮次
     */
    @TableField("ROUND")
    private Integer round;

    /**
     * 物料需求ID
     */
    @TableField("REQUIREMENT_LINE_ID")
    private Long requirementLineId;

    /**
     * 供应商ID
     */
    @TableField("BID_VENDOR_ID")
    private Long bidVendorId;

    /**
     * 报价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 组合总金额
     */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /**
     * 排名
     */
    @TableField("RANK")
    private Integer rank;

    /**
     * 跟标权限
     */
    @TableField("WITH_STANDARD_PERMISSION")
    private String withStandardPermission;

    /**
     * 是否跟标Y/N
     */
    @TableField("WITH_STANDARD")
    private String withStandard;

    /**
     * 是否入围Y=入围，N=淘汰，D=待确定
     */
    @TableField("WIN")
    private String win;

    /**
     * 评选结果
     */
    @TableField("SELECTION_STATUS")
    private String selectionStatus;

    /**
     * 本轮最低价
     */
    @TableField("CURRENT_ROUND_MIN_PRICE")
    private BigDecimal currentRoundMinPrice;

    /**
     * 本轮最高价
     */
    @TableField("CURRENT_ROUND_MAX_PRICE")
    private BigDecimal currentRoundMaxPrice;

    /**
     * 价格得分
     */
    @TableField("PRICE_SCORE")
    private BigDecimal priceScore;

    /**
     * 技术得分
     */
    @TableField("TECH_SCORE")
    private BigDecimal techScore;

    /**
     * 绩效得分
     */
    @TableField("PERF_SCORE")
    private BigDecimal perfScore;

    /**
     * 综合得分
     */
    @TableField("COMPOSITE_SCORE")
    private BigDecimal compositeScore;

    /**
     * 修改原因
     */
    @TableField("UPDATE_REASON")
    private String updateReason;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

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
     * 更新人
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
     * 关联ou组ID
     */
    @TableField("CEEA_OU_ID")
    private Long ceeaOuId;

    /**
     * 关联ou组名称
     */
    @TableField("CEEA_OU_NAME")
    private String ceeaOuName;

    /**
     * 是否为基准OU组
     */
    @TableField("CEEA_IS_BASE_OU")
    private String ceeaIsBaseOu;

    /**
     * 交货地点
     */
    @TableField("CEEA_DELIVERY_PLACE")
    private String ceeaDeliveryPlace;

    /**
     * MQO
     */
    @TableField("CEEA_MQO")
    private String ceeaMqo;

    /**
     * 贸易条款
     */
    @TableField("CEEA_TRADE_TERM")
    private String ceeaTradeTerm;

    /**
     * 运输方式
     */
    @TableField("CEEA_TRANSPORT_TYPE")
    private String ceeaTransportType;

    /**
     * 保修期(月)
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    private String ceeaWarrantyPeriod;

    /**
     * 采购类型
     */
    @TableField("CEEA_PURCHASE_TYPE")
    private String ceeaPurchaseType;

    /**
     * 报价账期
     */
    @TableField("CEEA_PAYMENT_DAY")
    private Integer ceeaPaymentDay;

    /**
     * 报价币种
     */
    @TableField("CEEA_CURRENCY_TYPE")
    private String ceeaCurrencyType;

    /**
     * 付款条款
     */
    @TableField("CEEA_PAYMENT_TERM")
    private String ceeaPaymentTerm;

    /**
     * 折息价
     */
    @TableField("CEEA_DISCOUNT_PRICE")
    private BigDecimal ceeaDiscountPrice;

    /**
     * 本轮最低折息价
     */
    @TableField("CEEA_CUR_ROUND_MIN_DISC_PRICE")
    private BigDecimal ceeaCurRoundMinDiscPrice;

    /**
     * 本轮最高折息价
     */
    @TableField("CEEA_CUR_ROUND_MAX_DISC_PRICE")
    private BigDecimal ceeaCurRoundMaxDiscPrice;

    /**
     * 组合折息总金额
     */
    @TableField("CEEA_TOTAL_DISCOUNT_AMOUNT")
    private BigDecimal ceeaTotalDiscountAmount;

    /**
     * 付款方式
     */
    @TableField("CEEA_PAYMENT_WAY")
    private String ceeaPaymentWay;

    /**
     * 付款条款id
     */
    @TableField("CEEA_PAYMENT_TERM_ID")
    private Long ceeaPaymentTermId;

    /**
     * 本轮最高折息总金额
     */
    @TableField("CEEA_CUR_ROUND_MAX_DISC_AMOUNT")
    private BigDecimal ceeaCurRoundMaxDiscAmount;

    /**
     * 本轮最低折息总金额
     */
    @TableField("CEEA_CUR_ROUND_MIN_DISC_AMOUNT")
    private BigDecimal ceeaCurRoundMinDiscAmount;

    /**
     * 供货周期
     */
    @TableField("CEEA_LEAD_TIME")
    private String ceeaLeadTime;

    /**
     * 承诺交货期
     */
    @TableField("CEEA_DELIVER_DATE")
    private Date ceeaDeliverDate;

    /**
     * 配额数量
     */
    @TableField("CEEA_QUOTA_QUANTITY")
    private BigDecimal ceeaQuotaQuantity;

    /**
     * 系统计算的配额比例
     */
    @TableField("CEEA_QUOTA_RATIO")
    private String ceeaQuotaRatio;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 报价有效期从
     */
    @TableField("EXPIRY_DATE_FROM")
    private LocalDate expiryDateFrom;

    /**
     * 报价有效期至
     */
    @TableField("EXPIRY_DATE_TO")
    private LocalDate expiryDateTo;

    /**
     * 中标标识
     */
    @TableField("SUGGESTED_FLAG")
    private String suggestedFlag;

    /**
     * 代理报价
     */
    @TableField("AGENT_FLAG")
    private String agentFlag;

    @TableField("CREATED_BY_CODE")
    private String createdByCode;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 是否创建合同
     */
    @TableField("CREATE_CONTRACT_FLAG")
    private String createContractFlag;


}
