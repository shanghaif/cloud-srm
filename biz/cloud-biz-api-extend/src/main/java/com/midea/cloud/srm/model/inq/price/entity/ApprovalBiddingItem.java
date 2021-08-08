package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  价格审批单-中标行信息 模型
 * </pre>
 *
 * @author chenwj92@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 19:45:53
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_price_approval_bidding_item")
public class ApprovalBiddingItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 中标行id
     */
    @TableId("APPROVAL_BIDDING_ITEM_ID")
    private Long approvalBiddingItemId;

    /**
     * 价格审批单头部id
     */
    @TableField("APPROVAL_HEADER_ID")
    private Long approvalHeaderId;

    /**
     * 价格类型
     */
    @TableField("PRICE_TYPE")
    private String priceType;

    /**
     * OU组主键ID
     */
    @TableField("OU_ID")
    private Long ouId;

    /**
     * OU组编码
     */
    @TableField("OU_NUMBER")
    private String ouNumber;

    /**
     * OU组名称
     */
    @TableField("OU_NAME")
    private String ouName;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 到货地点
     */
    @TableField("ARRIVAL_PLACE")
    private String arrivalPlace;

    /**
     * 供应商ID
     */
    @NotNull(message = "供应商id不能为空")
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @NotEmpty(message = "供应商编码不能为空")
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @NotEmpty(message = "供应商名称不能为空")
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 物料ID
     */
    @NotNull(message = "物料id不能为空")
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 物料编码
     */
    @NotEmpty(message = "物料编码不能为空")
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料名称
     */
    @NotEmpty(message = "物料名称不能为空")
    @TableField("ITEM_NAME")
    private String itemName;

    /**
     * 物料描述
     */
    @TableField("ITEM_DESCRIPTION")
    private String itemDescription;

    /**
     * 物料小类ID
     */
    @NotNull(message = "物料小类id不能为空")
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @NotEmpty(message = "物料名称不能为空")
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @NotEmpty(message = "物料小类编码不能为空")
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 需求数量
     */
    @NotNull(message = "需求数量不能为空")
    @TableField("NEED_NUM")
    private BigDecimal needNum;

    /**
     * 单位
     */
    @TableField("UNIT")
    @NotEmpty(message = "物料单位不能为空")
    private String unit;

    /**
     * 单价（含税）
     */
    @TableField("TAX_PRICE")
    @NotNull(message = "含税单价不能为空")
    private BigDecimal taxPrice;

    /**
     * 币种(招标单的币种：本位币)
     */
    @TableField("STANDARD_CURRENCY")
    private String standardCurrency;

    /**
     * 币种id
     */
    @TableField("CURRENCY_ID")
    @NotNull(message = "币种id不能为空")
    private Long currencyId;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    @NotEmpty(message = "币种不能为空")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    @NotEmpty(message = "币种名称不能为空")
    private String currencyName;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    @NotEmpty(message = "税率编码不能为空")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    @NotNull(message = "税率不能为空")
    private BigDecimal taxRate;

    /**
     * 单价（未税）
     */
    @TableField("NOTAX_PRICE")
    private BigDecimal notaxPrice;

    /**
     * 价格阶梯类型
     */
    @TableField("LADDER_PRICE_TYPE")
    private String ladderPriceType;

    /**
     * 付款条款
     */
    @TableField("PAYMENT_PROVISION")
    private String paymentProvision;

    /**
     * 配额分配类型
     */
    @TableField("QUOTA_DISTRIBUTION_TYPE")
    private String quotaDistributionType;

    /**
     * 配额比例
     */
    @TableField("QUOTA_PROPORTION")
    private BigDecimal quotaProportion;

    /**
     * L/T
     */
    @TableField("L_AND_T")
    private String lAndT;

    /**
     * 价格开始生效日期
     */
    @TableField("START_TIME")
    @NotNull(message = "价格生效日期不能为空")
    private LocalDate startTime;

    /**
     * 价格失效日期
     */
    @TableField("END_TIME")
    @NotNull(message = "价格失效日期不能为空")
    private LocalDate endTime;

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
     * 创建人ip
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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 价格审批单号
     */
    @TableField("APPROVAL_NO")
    private String approvalNo;

    /**
     * 寻源单号
     */
    @TableField(exist = false)
    private String ceeaSourceNo;

    /**
     * 寻源类型
     */
    @TableField(exist = false)
    private String sourceType;

    /**
     * 采购申请号
     */
    @TableField("CEEA_PURCHASE_REQUEST_NUM")
    private String purchaseRequestNum;

    /**
     * 采购申请行号
     */
    @TableField("CEEA_PURCHASE_REQUEST_ROW_NUM")
    private String purchaseRequestRowNum;


    @TableField("CEEA_FROM_CONTRACT_ID")
    private Long fromContractId;    // 来源合同ID

    @TableField("CEEA_FROM_CONTRACT_CODE")
    private String fromContractCode;  // 来源合同编码

    @TableField("CEEA_FROM_CONTRACT_LINE_ID")
    private Long fromContractLineId; // 来源合同行ID

    /**
     * 最小起订量
     */
    @TableField("MIN_ORDER_QUANTITY")
    private BigDecimal minOrderQuantity;
    /**
     * 中标数量
     */
    @TableField("CEEA_QUOTA_QUANTITY")
    private BigDecimal quotaQuantity;

    @TableField("CEEA_OU_GROUP_JSON")
    private String ouGroupJson;
    /**
     * 是否海鲜价公式
     */
    @TableField(value = "IS_SEA_FOOD_FORMULA")
    private String isSeaFoodFormula;
    /**
     * 基价列表
     */
    @TableField(value = "PRICE_JSON")
    private String priceJson;
    /**
     * 公式值
     */
    @TableField("CEEA_FORMULA_VALUE")
    private String formulaValue;
    /**
     * 公式id
     */
    @TableField("CEEA_FORMULA_ID")
    private Long formulaId;
    /**
     * 供应商要素报价明细
     */
    @TableField("ESSENTIAL_FACTOR_VALUES")
    private String essentialFactorValues;

    @TableField("CEEA_FORMULA_RESULT")
    private String formulaResult;

    @TableField("comments")
    private String comments;

    @TableField("CEEA_TRADE_TERM")
    private String tradeTerm;

    /**
     * 质保期
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    private Integer warrantyPeriod;

    @TableField("CEEA_IS_PROXY_BIDDING")
    private String isProxyBidding;
}
