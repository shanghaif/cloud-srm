package com.midea.cloud.srm.model.pm.pr.requirement.entity;

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
 *  采购需求行表 模型
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-27 15:58:01
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_pr_requirement_line")
public class RequirementLineNonAuto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("REQUIREMENT_LINE_ID")
    private Long requirementLineId;

    /**
     * 采购需求头表ID
     */
    @TableField("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;

    /**
     * 采购需求编号(申请编号)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 申请行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 业务主体ID(longi)
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务主体编码(longi)
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务主体名称(longi)
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID(longi)
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码(longi)
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称(longi)
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织全路径ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 需求部门
     */
    @TableField("REQUIREMENT_DEPARTMENT")
    private String requirementDepartment;

    /**
     * 物料小类ID(longi)
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称(longi)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码(longi)
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 预算（隆基不使用）
     */
    @TableField("BUDGET")
    private BigDecimal budget;

    /**
     * 是否目录化(longi)
     */
    @TableField("CEEA_IF_DIRECTORY")
    private String ceeaIfDirectory;

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
     * 采购组织
     */
    @TableField("PURCHASE_ORGANIZATION")
    private String purchaseOrganization;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 单位描述
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 未税单价(预算单价)
     */
    @TableField("NOTAX_PRICE")
    private BigDecimal notaxPrice;

    /**
     * 价格单位（隆基不使用）
     */
    @TableField("PRICE_UNIT")
    private String priceUnit;

    /**
     * 税率(不使用)
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 税率编码(不使用)
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 币种
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 币种ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 当前库存量
     */
    @TableField("CURRENT_INVENTORY")
    private BigDecimal currentInventory;

    /**
     * 需求数量
     */
    @TableField("REQUIREMENT_QUANTITY")
    private BigDecimal requirementQuantity;

    /**
     * 剩余可下单数量
     */
    @TableField("ORDER_QUANTITY")
    private BigDecimal orderQuantity;

    /**
     * 货源供应商 Y:有,N:无
     */
    @TableField("HAVE_SUPPLIER")
    private String haveSupplier;

    /**
     * 有效价格 Y:有,N:无
     */
    @TableField("HAVE_EFFECTIVE_PRICE")
    private String haveEffectivePrice;

    /**
     * 需求来源
     */
    @TableField("REQUIREMENT_SOURCE")
    private String requirementSource;

    /**
     * 收货工厂
     */
    @TableField("RECEIVED_FACTORY")
    private String receivedFactory;

    /**
     * 外部申请编号
     */
    @TableField("EXTERNAL_APPLY_CODE")
    private String externalApplyCode;

    /**
     * 外部申请行号
     */
    @TableField("EXTERNAL_APPLY_ROW_NUM")
    private Integer externalApplyRowNum;

    /**
     * 后续单据编号(废弃)
     */
    @TableField("FOLLOW_FORM_CODE")
    private String followFormCode;

    /**
     * 后续单据名称(废弃)
     */
    @TableField("FOLLOW_FORM_NAME")
    private String followFormName;

    /**
     * 总金额(预算金额)
     */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /**
     * 需求日期
     */
    @TableField("REQUIREMENT_DATE")
    private LocalDate requirementDate;

    /*指定供应商ID*/
    @TableField("VENDOR_ID")
    private Long vendorId;

    /*指定供应商名称*/
    @TableField("VENDOR_NAME")
    private String vendorName;

    /*指定供应商编码*/
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 申请原因
     */
    @TableField("APPLY_REASON")
    private String applyReason;

    /**
     * 供应商管理采购员ID
     */
    @TableField("CEEA_SUP_USER_ID")
    private Long ceeaSupUserId;

    /**
     * 供应商管理采购员名称
     */
    @TableField("CEEA_SUP_USER_NICKNAME")
    private String ceeaSupUserNickname;

    /**
     * 供应商管理采购员账号
     */
    @TableField("CEEA_SUP_USER_NAME")
    private String ceeaSupUserName;

    /**
     * 策略负责采购员ID
     */
    @TableField("CEEA_STRATEGY_USER_ID")
    private Long ceeaStrategyUserId;

    /**
     * 策略负责采购员名称
     */
    @TableField("CEEA_STRATEGY_USER_NICKNAME")
    private String ceeaStrategyUserNickname;

    /**
     * 策略负责采购员账号
     */
    @TableField("CEEA_STRATEGY_USER_NAME")
    private String ceeaStrategyUserName;

    /**
     * 采购履行采购员ID
     */
    @TableField("CEEA_PERFORM_USER_ID")
    private Long ceeaPerformUserId;

    /**
     * 采购履行采购员名称
     */
    @TableField("CEEA_PERFORM_USER_NICKNAME")
    private String ceeaPerformUserNickname;

    /**
     * 采购履行采购员账号
     */
    @TableField("CEEA_PERFORM_USER_NAME")
    private String ceeaPerformUserName;

    /**
     * 已执行数量
     */
    @TableField("CEEA_EXECUTED_QUANTITY")
    private BigDecimal ceeaExecutedQuantity;

    /**
     * 交货地点
     */
    @TableField("CEEA_DELIVERY_PLACE")
    private String ceeaDeliveryPlace;

    /**
     * 申请状态(数据字典APPLICATION_STATUS)
     */
    @TableField("APPLY_STATUS")
    private String applyStatus;

    /**
     * 驳回原因
     */
    @TableField("REJECT_REASON")
    private String rejectReason;

    /**
     * 库存地点
     */
    @TableField("INVENTORY_PLACE")
    private String inventoryPlace;

    /**
     * 成本类型
     */
    @TableField("COST_TYPE")
    private String costType;

    /**
     * 成本编号
     */
    @TableField("COST_NUM")
    private String costNum;

    /**
     * 指定品牌
     */
    @TableField("BRAND")
    private String brand;

    /**
     * 采购员ID
     */
    @TableField("BUYER_ID")
    private Long buyerId;

    /**
     * 采购员账号
     */
    @TableField("BUYER")
    private String buyer;

    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /**
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME")
    private String createdFullName;

    /**
     * 内部备注
     */
    @TableField("INTERNAL_COMMENTS")
    private String internalComments;

    /**
     * 外部备注
     */
    @TableField("EXTERNAL_COMMENTS")
    private String externalComments;

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
     * 来源系统（如：erp系统）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

    /**
     * 创建人ID
     */
    @TableField("CREATED_ID")
    private Long createdId;

    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE")
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP")
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**是否取消(取消分配的时候为true)*/
    @TableField(exist = false)
    private boolean enableUnAssigned;

    /**
     * 业务小类
     */
    @TableField("CEEA_BUSINESS_SMALL")
    private String ceeaBusinessSmall;

    /**
     * 业务小类编码
     */
    @TableField("CEEA_BUSINESS_SMALL_CODE")
    private String ceeaBusinessSmallCode;

    /**
     * 购物车外键ID
     */
    @TableField("SHOP_CART_ID")
    private Long shopCartId;

    /**
     * 第一次需求变更数量
     */
    @TableField("CEEA_FIRST_QUANTITY")
    private BigDecimal ceeaFirstQuantity;

    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 需求人列
     */
    @TableField("DMAND_LINE_REQUEST")
    private String dmandLineRequest;

    /**
     * 是否有创建后续单据
     */
    @TableField("IF_CREATE_FOLLOW_FORM")
    private String ifCreateFollowForm;
    /**
     * 项目id、code、name
     */
    @TableField("PROJECT_ID")
    private Long ProjectId;
    @TableField("PROJECT_NUMBER")
    private String ProjectNumber;
    @TableField("PROJECT_NAME")
    private String ProjectName;
    /**
     * 任务id、code、name
     */
    @TableField("TASK_ID")
    private Long TaskId;
    @TableField("TASK_NUMBER")
    private String TaskNumber;
    @TableField("TASK_NAME")
    private String TaskName;
    @TableField("CEEA_DELIVERY_PLACE_SITE")
    private String ceeaDeliveryPlaceSite;

}
