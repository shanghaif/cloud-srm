package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.annonations.JudgeEquals;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.common.assist.handler.BigDecimalFormatAESEncryptHandler;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  招标需求明细表 模型
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 17:04:28
 *  修改内容:
 *          </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "scc_lgt_requirement_line", autoResultMap = true)
public class BidRequirementLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 拦标价 * 预计采购量
     *
     * @return
     */
    public Double getTargetAmount() {
        return getTargetPrice() == null || getQuantity() == null ? null : getTargetPrice().multiply(new BigDecimal(getQuantity())).doubleValue();
    }

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
     * 标的ID
     */
    @TableField("TARGET_ID")
    @JudgeEquals
    private Long targetId;

    /**
     * 标的编号
     */
    @TableField("TARGET_NUM")
    private String targetNum;

    /**
     * 标的描述
     */
    @TableField("TARGET_DESC")
    @JudgeEquals
    private String targetDesc;

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
     * 含税现价
     */
    @TableField("TAX_CURRENT_PRICE")
    private BigDecimal taxCurrentPrice;

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
     * 物料组(组合)
     */
    @TableField("ITEM_GROUP")
    private String itemGroup;

    /**
     * 采购数量
     */
    @TableField("QUANTITY")
    @JudgeEquals
    private Double quantity;

    /**
     * 预计采购金额(万元)
     */
    @TableField("AMOUNT")
    @JudgeEquals
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
     * 拦标价
     */
    @TableField(value = "TARGET_PRICE", typeHandler = BigDecimalFormatAESEncryptHandler.class)
    private BigDecimal targetPrice;

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
    @JudgeEquals
    private Date priceStartTime;

    /**
     * 定价结束时间
     */
    @TableField("PRICE_END_TIME")
    @JudgeEquals
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
     * 拥有者/租户/所属组等
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**
     * ou组id
     */
    @TableField("CEEA_OU_ID")
    private Long ouId;
    /**
     * ou组编号
     */
    @TableField("CEEA_OU_NUMBER")
    @JudgeEquals
    private String ouNumber;
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
     * 基准ou名
     */
    /**
     * 配比
     */
    @TableField("CEEA_MATERIAL_MATCHING")
    private String materialMatching;
    /**
     * 交货地点
     */
    @TableField("CEEA_DELIVERY_PLACE")
    @JudgeEquals
    private String deliveryPlace;
    /**
     * 价格类型
     */
    @TableField("CEEA_PRICE_TYPE")
    @JudgeEquals
    private String priceType;
    /**
     * 是否公式控制
     */
    @TableField("CEEA_IS_FORMULA_CONTROL")
    private String formulaControl;
    /**
     * 公式id
     */
    @TableField("CEEA_FORMULA_ID")
    private Long formulaId;
    /**
     * 公式值
     */
    @TableField("CEEA_FORMULA_VALUE")
    private String formulaValue;
    /**
     * 采购类型
     */
    @TableField("CEEA_PURCHASE_TYPE")
    @JudgeEquals
    private String purchaseType;
    /**
     * 贸易条款
     */
    @TableField("CEEA_TRADE_TERM")
    @JudgeEquals
    private String tradeTerm;
    /**
     * 运输方式
     */
    @TableField("CEEA_TRANSPORT_TYPE")
    @JudgeEquals
    private String transportType;
    /**
     * 是否显示需求量
     */
    @TableField("CEEA_IS_SHOW_REQUIRE_NUM")
    @JudgeEquals
    private String showRequireNum;
    /**
     * 保修期(月)
     */
    @TableField("CEEA_WARRANTY_PERIOD")
    @JudgeEquals
    private Integer warrantyPeriod;

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

    /**
     * 指定供应商id
     */
    @TableField("CEEA_AWARDED_SUPPLIER_ID")
    private Long awardedSupplierId;
    /**
     * 指定供应商名
     */
    @TableField("CEEA_AWARDED_SUPPLIER_NAME")
    private String awardedSupplierName;
    /**
     * 报价状态
     */
    @TableField("CEEA_QUOTE_STATUS")
    private String quoteStatus;


    @TableField("CEEA_INV_ID")
    private Long invId;          // 库存组织ID

    @JudgeEquals
    @TableField("CEEA_INV_CODE")
    private String invCode;        // 库存组织CODE

    @TableField("CEEA_INV_NAME")
    private String invName;        // 库存组织名称

    @TableField("CEEA_CATEGORY_CODE")
    @JudgeEquals
    private String categoryCode;    // 采购分类编码

    @TableField("CEEA_ORG_CODE")
    private String orgCode;         // 采购组织编码

    @TableField("CEEA_FROM_CONTRACT_ID")
    private Long fromContractId;    // 来源合同ID

    @TableField("CEEA_FROM_CONTRACT_CODE")
    private String fromContractCode;  // 来源合同编码

    @TableField("CEEA_FROM_CONTRACT_LINE_ID")
    private Long fromContractLineId; // 来源合同行ID

    @TableField("CEEA_DEMAND_DATE")
    private LocalDate ceeaDemandDate; // 需求日期

    @TableField(value = "PRICE_JSON")
    private String priceJson;

    /**
     * 基价列表
     */
    @TableField(exist = false)
    private List<BaseMaterialPriceDTO> materialPrices;

    /**
     * 是否海鲜价公式
     */
    @TableField(value = "IS_SEA_FOOD_FORMULA")
    private String isSeaFoodFormula;
}
