package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  报价-价格目录表 模型
 * </pre>
 *
 * @author linxc6@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 11:41:48
 *  修改内容:
 *          </pre>
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_library")
public class PriceLibrary extends BaseEntity {

    private static final long serialVersionUID = 5881201967912119785L;

    /**
     * 价格id
     */
    @TableId("PRICE_LIBRARY_ID")
    private Long priceLibraryId;

    /**
     * 物料id
     */
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料描述
     */
    @TableField("ITEM_DESC")
    private String itemDesc;

    /**
     * 品类id(物料小类)
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类名称(物料小类)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品类编码(物料小类)
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;


    /**
     * 供应商id
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
     * 审批单号
     */
    @TableField("APPROVAL_NO")
    private String approvalNo;

    /**
     * 价格类型
     */
    @TableField("PRICE_TYPE")
    private String priceType;

    /**
     * 单价（未税）
     */
    @TableField("NOTAX_PRICE")
    private BigDecimal notaxPrice;

    /**
     * 单价（含税）
     */
    @TableField("TAX_PRICE")
    private BigDecimal taxPrice;

    /**
     * 税率id
     */
    @TableField("TAX_ID")
    private Long taxId;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率值（产品的坑，应该是BigDecimal，因多处引用无法修改）
     */
    @TableField("TAX_RATE")
    private String taxRate;

    /**
     * 币种id
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 有效日期
     */
    @TableField("EFFECTIVE_DATE")
    private Date effectiveDate;

    /**
     * 失效日期
     */
    @TableField("EXPIRATION_DATE")
    private Date expirationDate;

    /**
     * 单位名称
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 是否阶梯价
     */
    @TableField("IS_LADDER")
    private String isLadder;

    /**
     * 阶梯价类型
     */
    @TableField("LADDER_TYPE")
    private String ladderType;

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


    /*新增的字段*/
    /**
     * 价格库编号
     */
    @TableField("CEEA_PRICE_LIBRARY_NO")
    private String ceeaPriceLibraryNo;

    /**
     * 业务实体ID
     */
    @TableField("CEEA_ORG_ID")
    private Long ceeaOrgId;

    /**
     * 业务实体编码
     */
    @TableField("CEEA_ORG_CODE")
    private String ceeaOrgCode;

    /**
     * 业务实体名称
     */
    @TableField("CEEA_ORG_NAME")
    private String ceeaOrgName;

    /**
     * 库存组织ID
     */
    @TableField("CEEA_ORGANIZATION_ID")
    private Long ceeaOrganizationId;

    /**
     * 库存组织编码
     */
    @TableField("CEEA_ORGANIZATION_CODE")
    private String ceeaOrganizationCode;

    /**
     * 库存组织名称
     */
    @TableField("CEEA_ORGANIZATION_NAME")
    private String ceeaOrganizationName;

    /**
     * 到货地点
     */
    @TableField("CEEA_ARRIVAL_PLACE")
    private String ceeaArrivalPlace;

    /**
     * 配额分配类型
     */
    @TableField("CEEA_ALLOCATION_TYPE")
    private String ceeaAllocationType;

    /**
     * 配额比例
     */
    @TableField("CEEA_QUOTA_PROPORTION")
    private BigDecimal ceeaQuotaProportion;

    /**
     * L/T
     */
    @TableField("CEEA_LT")
    private String ceeaLt;

    /**
     * 是否已上架
     */
    @TableField("CEEA_IF_USE")
    private String ceeaIfUse;

    /**
     * 寻源单号
     */
    @TableField("SOURCE_NO")
    private String sourceNo;

    /**
     * 最小订单数量(最小起订量)
     */
    @TableField("MIN_ORDER_QUANTITY")
    private BigDecimal minOrderQuantity;

    /**
     * 框架协议编号(合同编号)
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;
    /**
     * 框架协议名称(合同名称)
     */
    @TableField("CONTRACT_NAME")
    private String contractName;

    /**
     * 报价行id
     */
    @TableField("QUOTATION_LINE_ID")
    private String quotationLineId;


    /*不使用的字段*/
    /**
     * 需求日期(不使用)
     */
    @TableField(exist = false)
    private Date requirementDate;

    /**
     * 价格编码(不使用)
     */
    @TableField("PRICE_NUMBER")
    private String priceNumber;

    /**
     * 采购组织id(不使用)
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID(不使用)
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织名称(不使用)
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 寻源方式(不使用)
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;

    /**
     * 物料组编码(不使用)
     */
    @TableField("ITEM_GROUP_CODE")
    private String itemGroupCode;

    /**
     * 规格型号(不使用)
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 订单数量(不使用)
     */
    @TableField("ORDER_QUANTITY")
    private BigDecimal orderQuantity;


    /**
     * 合同号(不使用)
     */
    @TableField("CONTRACT_NO")
    private String contractNo;


    /**
     * 最小包装数量(不使用)
     */
    @TableField("MIN_PACK_QUANTITY")
    private BigDecimal minPackQuantity;

    /**
     * 业务类型(不使用)
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 备注(不使用)
     */
    @TableField("REMARKS")
    private String remarks;

    /**
     * 同步状态(不使用)
     */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /**
     * 删除标识(不使用)
     */
    @TableField("DEL_FLAG")
    private String delFlag;

    /**
     * 价格单位(不使用)
     */
    @TableField("PRICE_UNIT")
    private String priceUnit;

    /**
     * 币种(不使用)
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 查询条件:业务实体ids
     */
    @TableField(exist = false)
    List<Long> ceeaOrgIds;

    /**
     * 查询条件：库存组织ids
     */
    @TableField(exist = false)
    List<Long> ceeaOrganizationIds;

    /**
     * 查询条件：所有物料id
     */
    @TableField(exist = false)
    List<Long> itemIds;

    @TableField(exist = false)
    private List<PriceLibraryPaymentTerm> paymentTerms;

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
    @TableField(value = "CEEA_FORMULA_VALUE")
    private String formulaValue;
    /**
     * 公式id
     */
    @TableField(value = "CEEA_FORMULA_ID")
    private Long formulaId;
    /**
     * 供应商要素报价明细
     */
    @TableField(value = "ESSENTIAL_FACTOR_VALUES")
    private String essentialFactorValues;
    @TableField("CEEA_FORMULA_RESULT")
    private String formulaResult;
    /**
     * 贸易条款
     */
    @TableField("CEEA_TRADE_TERM")
    private String tradeTerm;
    /**
     * 质保期
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    private Integer warrantyPeriod;
}
