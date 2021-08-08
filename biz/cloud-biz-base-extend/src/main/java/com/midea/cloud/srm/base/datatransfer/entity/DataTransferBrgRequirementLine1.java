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
 *  招标需求明细表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-31 14:46:01
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_requirement_line1")
public class DataTransferBrgRequirementLine1 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("REQUIREMENT_LINE_ID")
    private Long requirementLineId;

    /**
     * 招标需求ID
     */
    @TableField("REQUIREMENT_ID")
    private Long requirementId;

    /**
     * 招标ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 标的/物料id
     */
    @TableField("TARGET_ID")
    private Long targetId;

    /**
     * 标的编号(物料编码)
     */
    @TableField("TARGET_NUM")
    private String targetNum;

    /**
     * 标的描述
     */
    @TableField("TARGET_DESC")
    private String targetDesc;

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
     * 采购组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 含税现价
     */
    @TableField("TAX_CURRENT_PRICE")
    private BigDecimal taxCurrentPrice;

    /**
     * 预计采购金额(万元)
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 商务第一轮保留供应商数量
     */
    @TableField("LIMIT_ROUND1")
    private Integer limitRound1;

    /**
     * 商务第二轮保留供应商数量
     */
    @TableField("LIMIT_ROUND2")
    private Integer limitRound2;

    /**
     * 商务第三轮保留供应商数量
     */
    @TableField("LIMIT_ROUND3")
    private Integer limitRound3;

    /**
     * 商务第四轮保留供应商数量
     */
    @TableField("LIMIT_ROUND4")
    private Integer limitRound4;

    /**
     * 商务第五轮保留供应商数量
     */
    @TableField("LIMIT_ROUND5")
    private Integer limitRound5;

    /**
     * 物料组(组合)
     */
    @TableField("ITEM_GROUP")
    private String itemGroup;

    /**
     * 采购数量
     */
    @TableField("QUANTITY")
    private Double quantity;

    /**
     * 拦标价
     */
    @TableField("TARGET_PRICE")
    private String targetPrice;

    /**
     * 单位编码
     */
    @TableField("UOM_CODE")
    private String uomCode;

    /**
     * 单位描述
     */
    @TableField("UOM_DESC")
    private String uomDesc;

    /**
     * 定价开始时间
     */
    @TableField("PRICE_START_TIME")
    private Date priceStartTime;

    /**
     * 定价结束时间
     */
    @TableField("PRICE_END_TIME")
    private Date priceEndTime;

    /**
     * 行类型
     */
    @TableField("ROW_TYPE")
    private String rowType;

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
     * 关联ou组编码
     */
    @TableField("CEEA_OU_NUMBER")
    private String ceeaOuNumber;

    /**
     * 关联ou组名称
     */
    @TableField("CEEA_OU_NAME")
    private String ceeaOuName;

    /**
     * 是否为OU组
     */
    @TableField("CEEA_IS_BASE_OU")
    private String ceeaIsBaseOu;

    /**
     * 配比
     */
    @TableField("CEEA_MATERIAL_MATCHING")
    private String ceeaMaterialMatching;

    /**
     * 交货地点
     */
    @TableField("CEEA_DELIVERY_PLACE")
    private String ceeaDeliveryPlace;

    /**
     * 价格类型
     */
    @TableField("CEEA_PRICE_TYPE")
    private String ceeaPriceType;

    /**
     * 是否公式控制
     */
    @TableField("CEEA_IS_FORMULA_CONTROL")
    private String ceeaIsFormulaControl;

    /**
     * 公式id
     */
    @TableField("CEEA_FORMULA_ID")
    private Long ceeaFormulaId;

    /**
     * 公式内容
     */
    @TableField("CEEA_FORMULA_VALUE")
    private String ceeaFormulaValue;

    /**
     * 采购类型
     */
    @TableField("CEEA_PURCHASE_TYPE")
    private String ceeaPurchaseType;

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
     * 显示需求量
     */
    @TableField("CEEA_IS_SHOW_REQUIRE_NUM")
    private String ceeaIsShowRequireNum;

    /**
     * 保修期(月)
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    private Integer ceeaWarrantyPeriod;

    /**
     * 采购申请号
     */
    @TableField("CEEA_PURCHASE_REQUEST_NUM")
    private String ceeaPurchaseRequestNum;

    /**
     * 指定供应商id
     */
    @TableField("CEEA_AWARDED_SUPPLIER_ID")
    private Long ceeaAwardedSupplierId;

    /**
     * 指定供应商名
     */
    @TableField("CEEA_AWARDED_SUPPLIER_NAME")
    private String ceeaAwardedSupplierName;

    /**
     * 报价状态
     */
    @TableField("CEEA_QUOTE_STATUS")
    private String ceeaQuoteStatus;

    /**
     * 库存组织ID
     */
    @TableField("CEEA_INV_ID")
    private Long ceeaInvId;

    /**
     * 库存组织CODE
     */
    @TableField("CEEA_INV_CODE")
    private String ceeaInvCode;

    /**
     * 库存组织名
     */
    @TableField("CEEA_INV_NAME")
    private String ceeaInvName;

    /**
     * 采购分类编码
     */
    @TableField("CEEA_CATEGORY_CODE")
    private String ceeaCategoryCode;

    /**
     * 采购组织编码
     */
    @TableField("CEEA_ORG_CODE")
    private String ceeaOrgCode;

    /**
     * 采购申请行号
     */
    @TableField("CEEA_PURCHASE_REQUEST_ROW_NUM")
    private String ceeaPurchaseRequestRowNum;

    /**
     * 来源合同ID
     */
    @TableField("CEEA_FROM_CONTRACT_ID")
    private Long ceeaFromContractId;

    /**
     * 来源合同编码
     */
    @TableField("CEEA_FROM_CONTRACT_CODE")
    private String ceeaFromContractCode;

    /**
     * 来源合同行ID
     */
    @TableField("CEEA_FROM_CONTRACT_LINE_ID")
    private Long ceeaFromContractLineId;

    /**
     * 采购申请头ID
     */
    @TableField("REQUEST_HEADER_ID")
    private Long requestHeaderId;

    /**
     * 采购申请行id
     */
    @TableField("REQUEST_LINE_ID")
    private Long requestLineId;

    /**
     * 采购分类
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 需求日期
     */
    @TableField("NEED_BY_DATE")
    private LocalDate needByDate;

    @TableField("CREATED_BY_CODE")
    private String createdByCode;

    /**
     * RFx单据类型
     */
    @TableField("RFX_TYPE")
    private String rfxType;

    /**
     * 议标类型
     */
    @TableField("INSTRUMENT_TYPE")
    private String instrumentType;

    /**
     * 单据类别
     */
    @TableField("RFX_CATEGORY")
    private String rfxCategory;

    /**
     * 竞价方向
     */
    @TableField("RFX_AUCTION_DIRECTION")
    private String rfxAuctionDirection;

    /**
     * RFx方式
     */
    @TableField("RFX_METHOD")
    private String rfxMethod;

    /**
     * 议标等级
     */
    @TableField("NEGOTIATION_LEVEL")
    private String negotiationLevel;

    /**
     * 行号
     */
    @TableField("LINE_NUM")
    private Long lineNum;

    /**
     * 竞价规则
     */
    @TableField("AUCTION_RULE")
    private Long auctionRule;

    /**
     * 含税标识
     */
    @TableField("TAX_INCLUDED_FLAG")
    private String taxIncludedFlag;

    /**
     * 币种
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 轮次
     */
    @TableField("ROUND")
    private Long round;

    /**
     * 寻源单号
     */
    @TableField("REQUIREMENT_NUMBER")
    private String requirementNumber;

    /**
     * 标题
     */
    @TableField("REQUIREMENT_TITLE")
    private String requirementTitle;

    /**
     * 核价审批完成日期
     */
    @TableField("REQUIREMENT_APPOVEDD_DATE")
    private LocalDate requirementAppoveddDate;


}
