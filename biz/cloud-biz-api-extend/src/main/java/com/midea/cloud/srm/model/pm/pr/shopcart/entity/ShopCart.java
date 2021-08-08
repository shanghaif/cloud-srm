package com.midea.cloud.srm.model.pm.pr.shopcart.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  购物车 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-12 16:11:56
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_pr_shop_cart")
public class ShopCart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    @TableId("SHOP_CART_ID")
    private Long shopCartId;

    @TableField(exist = false)
    private List<Long> shopCartds;
    /**
     * 业务实体id
     */
    @TableField("ORG_ID")
    private Long orgId;

    @TableField(exist = false)
    private List<Long> orgIds;

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
     * 库存组织id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    @TableField(exist = false)
    private List<Long> organizationIds;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 物料小类id
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
     * 物料id
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
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 退回原因
     */
    @TableField("RETURN_REASON")
    private String returnReason;

    /**
     * 汇总人id
     */
    @TableField("SUMMARY_USER_ID")
    private Long summaryUserId;

    @TableField(exist = false)
    private List<Long> summaryUserIds;

    /**
     * 汇总人工号
     */
    @TableField("SUMMARY_EMP_NO")
    private String summaryEmpNo;

    /**
     * 汇总人昵称
     */
    @TableField("SUMMARY_NICKNAME")
    private String summaryNickname;

    /**
     * 通知人id
     */
    @TableField("NOTICE_USER_ID")
    private Long noticeUserId;

    @TableField(exist = false)
    private List<Long> noticeUserIds;

    /**
     * 通知人工号
     */
    @TableField("NOTICE_EMP_NO")
    private String noticeEmpNo;

    /**
     * 通知人昵称
     */
    @TableField("NOTICE_NICKNAME")
    private String noticeNickname;

    /**
     * 采购类型
     */
    @TableField("PURCHASE_TYPE")
    private String purchaseType;

    /**
     * 需求时间
     */
    @TableField("REQUIREMENT_DATE")
    private LocalDate requirementDate;

    /**
     * 数量
     */
    @TableField("REQUIREMENT_NUM")
    private BigDecimal requirementNum;

    /**
     * 是否目录化
     */
    @TableField("IF_CATALOG")
    private String ifCatalog;

    /**
     * 规格型号
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 单位编码
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 预算单价
     */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

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
     * 供应商id
     */
    @TableField("SUPPLIER_ID")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @TableField("SUPPLIER_NAME")
    private String supplierName;

    /**
     * 供应商编码
     */
    @TableField("SUPPLIER_CODE")
    private String supplierCode;

    /**
     * 加入购物车用户id
     */
    @TableField("ADD_TO_USER_ID")
    private Long addToUserId;

    /**
     * 加入购物车用户账号
     */
    @TableField("ADD_TO_EMP_NO")
    private String addToEmpNo;

    /**
     * 加入购物车用户昵称
     */
    @TableField("ADD_TO_NICKNAME")
    private String addToNickname;

    /**
     * 创建人id
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    @TableField(exist = false)
    private List<Long> createdIds;

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
     * 更新人id
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户id
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 大类名称
     */
    @TableField("large_category_name")
    private String largeCategoryName;

    @TableField(exist = false)
	private List<Long> ids;

    /**
     * 采购需求编号(申请编号)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 物料大类
     */
    @TableField(exist = false)
    private PurchaseCategory bigPurchaseCategory;

    /**
     * 是否为招待用品
     */
    @TableField(exist = false)
    private String ifEntertainment;
}
