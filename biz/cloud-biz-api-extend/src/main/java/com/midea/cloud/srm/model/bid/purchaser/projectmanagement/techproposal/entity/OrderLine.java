package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity;

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
 * 供应商投标行表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 上午11:13:01
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_bid_order_line")
public class OrderLine extends BaseEntity {

    private static final long serialVersionUID = -7770176322891148178L;
    // -----------------------------------------------非数据库字段-----------------------------------------------
    @TableField(exist = false)
    private Double quantity;// 采购数量

    @TableField(exist = false)
    private String itemGroup;// 组合

    /**
     * 行总金额
     *
     * @return
     */
    public Double calculateLineAmount() {
        return getQuantity() != null && getPrice() != null ? getQuantity().doubleValue() * getPrice().doubleValue() : null;
    }

    /**
     * 总金额
     *
     * @return
     */
    public Double getTotalAmountDoubleValue() {
        return getTotalAmount() != null ? getTotalAmount().doubleValue() : null;
    }

    /**
     * 报价
     *
     * @return
     */
    public Double getPriceDoubleValue() {
        return getPrice() != null ? getPrice().doubleValue() : null;
    }
    // -----------------------------------------------非数据库字段-----------------------------------------------

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
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

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
    private String withStanardPermission;

    /**
     * 是否跟标
     */
    @TableField("WITH_STANDARD")
    private String withStandard;

    /**
     * 当前轮次是否入围
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
     * 创建日期
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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**
     * ou组Id
     */
    @TableField("CEEA_OU_ID")
    private Long ouId;
    /**
     * ou组名
     */
    @TableField("CEEA_OU_NAME")
    private String ouName;
    /**
     * 是否为基准ou
     */
    @TableField("CEEA_IS_BASE_OU")
    private String baseOu;
    /**
     * 交货地点
     */
    @TableField("CEEA_DELIVERY_PLACE")
    private String deliveryPlace;

    @TableField("CEEA_MQO")
    private String MQO;
    /**
     * 贸易条款
     */
    @TableField("CEEA_TRADE_TERM")
    private String tradeTerm;
    /**
     * 运输方式
     */
    @TableField("CEEA_TRANSPORT_TYPE")
    private String transportType;
    /**
     * 保修期
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    private Integer warrantyPeriod;
    /**
     * 采购类型
     */
    @TableField("CEEA_PURCHASE_TYPE")
    private String purchaseType;
    /**
     * 报价币种
     */
    @TableField("CEEA_CURRENCY_TYPE")
    private String currencyType;
    /**
     * 账期
     */
    @TableField("CEEA_PAYMENT_DAY")
    private Integer paymentDay;
    /**
     * 付款条款
     */
    @TableField("CEEA_PAYMENT_TERM")
    private String paymentTerm;
    /**
     * 付款条款id
     */
    @TableField("CEEA_PAYMENT_TERM_ID")
    private Long paymentTermId;
    /**
     * 付款方式，字典值
     */
    @TableField("CEEA_PAYMENT_WAY")
    private String paymentWay;

    /**
     * 折息价
     */
    @TableField("CEEA_DISCOUNT_PRICE")
    private BigDecimal discountPrice;

    /**
     * 本轮最低折息价
     */
    @TableField("CEEA_CUR_ROUND_MIN_DISC_PRICE")
    private BigDecimal currentRoundMinDiscountPrice;

    /**
     * 本轮最高折息价
     */
    @TableField("CEEA_CUR_ROUND_MAX_DISC_PRICE")
    private BigDecimal currentRoundMaxDiscountPrice;

    /**
     * 组合折息总金额
     */
    @TableField("CEEA_TOTAL_DISCOUNT_AMOUNT")
    private BigDecimal totalDiscountAmount;

    /**
     * 本轮最低折息总金额
     */
    @TableField("CEEA_CUR_ROUND_MIN_DISC_AMOUNT")
    private BigDecimal currentRoundMinDiscountAmount;

    /**
     * 本轮最高折息总金额
     */
    @TableField("CEEA_CUR_ROUND_MAX_DISC_AMOUNT")
    private BigDecimal currentRoundMaxDiscountAmount;

    /**
     * 供货周期
     */
    @TableField("CEEA_LEAD_TIME")
    private String leadTime;

    /**
     * 承诺交货期
     */
    @TableField("CEEA_DELIVER_DATE")
    private Date deliverDate;
    /**
     * 配额数量
     */
    @TableField("CEEA_QUOTA_QUANTITY")
    private BigDecimal quotaQuantity;
    /**
     * 配额比例
     */
    @TableField("CEEA_QUOTA_RATIO")
    private BigDecimal quotaRatio;

    @TableField(exist = false)
    private String essentialFactorValues;

    @TableField("CEEA_FORMULA_RESULT")
    private String formulaResult;
}
