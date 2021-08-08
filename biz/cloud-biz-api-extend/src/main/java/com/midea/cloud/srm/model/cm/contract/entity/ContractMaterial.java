package com.midea.cloud.srm.model.cm.contract.entity;

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
 *  合同物料 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:16:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_material")
public class ContractMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,合同物料ID
     */
    @TableId("CONTRACT_MATERIAL_ID")
    private Long contractMaterialId;

    /**
     * 合同头信息ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 订单数量
     */
    @TableField("ORDER_QUANTITY")
    private BigDecimal orderQuantity;

    /**
     * 项次
     */
    @TableField("LINE_NUMBER")
    private Long lineNumber;

    /**
     * 订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 订单行号
     */
    @TableField("ORDER_LINE_NUMBER")
    private Long orderLineNumber;

    /**
     * 来源单号
     */
    @TableField("SOURCE_NUMBER")
    private String sourceNumber;

    /**
     * 来源单行号
     */
    @TableField("SOURCE_LINE_NUMBER")
    private Long sourceLineNumber;

    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

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
     * 规格类型
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 采购分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 合同数量
     */
    @TableField("CONTRACT_QUANTITY")
    private BigDecimal contractQuantity;

//    /**
//     * 订单数量
//     */
//    @TableField("ORDER_QUANTITY")
//    private BigDecimal orderQuantity;

    /**
     * 未税单价
     */
    @TableField("UNTAXED_PRICE")
    private BigDecimal untaxedPrice;

    /**
     * 含税单价
     */
    @TableField("TAXED_PRICE")
    private BigDecimal taxedPrice;

    /**
     * 价格单位
     */
    @TableField("PRICE_UNIT")
    private String priceUnit;

    /**
     * 人工单价
     */
    @TableField("PEOPLE_PRICE")
    private BigDecimal peoplePrice;

    /**
     * 材料单价
     */
    @TableField("MATERIAL_PRICE")
    private BigDecimal materialPrice;

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
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 单位ID
     */
    @TableField("UNIT_ID")
    private Long unitId;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 交货日期
     */
    @TableField("DELIVERY_DATE")
    private LocalDate deliveryDate;

    /**
     * 是否已验收
     */
    @TableField("IS_ACCEPTANCE")
    private String isAcceptance;

//    /**
//     * 验收数量
//     */
//    @TableField("ACCEPTANCE_QUANTITY")
//    private Long acceptanceQuantity;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 币种
     */
    @TableField(exist = false)
    private String currency;

    /**
     * 采购组织id
     */
    @TableField(exist = false)
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField(exist = false)
    private String fullPathId;

    /**
     * 采购组织名称
     */
    @TableField(exist = false)
    private String organizationName;

    /**
     * 供应商id
     */
    @TableField(exist = false)
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField(exist = false)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String vendorName;

    /**
     * 原合同合同物料ID
     */
    @TableField("SOURCE_ID")
    private Long sourceId;

    /**
     * 变更字段
     */
    @TableField("CHANGE_FIELD")
    private String changeField;

    /**
     * 业务实体id
     */
    @TableField("BU_ID")
    private Long buId;

    /**
     * 业务实体编码
     */
    @TableField("BU_CODE")
    private String buCode;

    /**
     * 业务实体名称
     */
    @TableField("BU_NAME")
    private String buName;

    /**
     * 业务实体全路径id
     */
    @TableField("BU_FULL_PATH_ID")
    private String buFullPathId;

    /**
     * 库存组织id
     */
    @TableField("INV_ID")
    private Long invId;

    /**
     * 库存组织编码
     */
    @TableField("INV_CODE")
    private String invCode;

    /**
     * 库存组织名称
     */
    @TableField("INV_NAME")
    private String invName;

    /**
     * 库存组织全路径id
     */
    @TableField("INV_FULL_PATH_ID")
    private String invFullPathId;

    /**
     * 交货地点
     */
    @TableField("TRADING_LOCATIONS")
    private String tradingLocations;

    /**
     * 未税金额
     */
    @TableField("UN_AMOUNT")
    private BigDecimal unAmount;

    /**
     * 制造商
     */
    @TableField("MANUFACTURER")
    private String manufacturer;

    /**
     * 原产地
     */
    @TableField("PLACE_OF_ORIGIN")
    private String placeOfOrigin;

    /**
     * 是否含安装调试业务(N-否,Y-是)
     */
    @TableField("IS_INSTALL_DEBUG")
    private String isInstallDebug;

    //---------------------

    /**
     * 保质期(月)
     */
    @TableField("SHELF_LIFE")
    private BigDecimal shelfLife;

    /**
     * 行备注
     */
    @TableField("LINE_REMARK")
    private String lineRemark;

    /**
     * 项目编号
     */
    @TableField("ITEM_NUMBER")
    private String itemNumber;

    /**
     * 项目名称
     */
    @TableField("ITEM_NAME")
    private String itemName;

    /**
     * 任务编号
     */
    @TableField("TASK_NUMBER")
    private String taskNumber;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 发运地
     */
    @TableField("SHIP_FROM")
    private String shipFrom;

    /**
     * 目的地
     */
    @TableField("DESTINATION")
    private String destination;

    /**
     * ou组id
     */
    @TableField("CEEA_OU_ID")
    private Long ceeaOuId;

    /**
     * ou组名称
     */
    @TableField("CEEA_OU_NAME")
    private String ceeaOuName;

    /**
     * ou组编码
     */
    @TableField("CEEA_OU_NUMBER")
    private String ceeaOuNumber;

    /**
     * 寻源方式
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;

    /**
     * 后续寻源单据号
     */
    @TableField("CEEA_FOLLOW_SOURCE_NUM")
    private String CeeaFollowSourceNum;

    /**
     * 来源单行ID
     */
    @TableField("CEEA_SOURCE_LINE_ID")
    private Long CeeaSourceLineId;

    /**
     * 中标行id
     */
    @TableId("APPROVAL_BIDDING_ITEM_ID")
    private Long approvalBiddingItemId;

    /**
     * 分类结构,该值以父分类节点ID+下划线+分类节点ID值组合而成(比如该分类ID:2,其父分类节点ID为1,则该字段值为1_2)
     */
    @TableId("STRUCT")
    private String struct;

    /**
     * 是否危险化学品
     */
    @TableId("IS_DANGER_CHEMISTRY")
    private String isDangerChemistry;

    /**
     * 税额
     */
    @TableId("TAX_QUOTA")
    private BigDecimal taxQuota;
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

    /**
     * 初始化金额
     */
    @TableField("CEEA_INIT_AMOUNT")
    private BigDecimal ceeaInitAmount;

    /**
     * 初始化数量
     */
    @TableField("CEEA_INIT_NUMBER")
    private BigDecimal ceeaInitNumber;

    /**
     * 已用金额
     */
    @TableField("CEEA_USED_AMOUNT")
    private BigDecimal ceeaUsedAmount;

    /**
     * 已用数量
     */
    @TableField("CEEA_USED_NUMBER")
    private BigDecimal ceeaUsedNumber;

    /**
     * 贸易条款
     */
    @TableField("CEEA_TRADE_TERM")
    private String tradeTerm;

    @TableField("CEEA_APPROVAL_NO")
    private String approvalNo;
}
