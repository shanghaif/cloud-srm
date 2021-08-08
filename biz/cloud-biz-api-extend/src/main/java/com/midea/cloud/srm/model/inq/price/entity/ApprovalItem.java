package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  价格审批单行表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-08 15:28:50
 *  修改内容:
 * </pre>
*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_approval_item")
public class ApprovalItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 价格审批单行主键
     */
    @TableId("APPROVAL_ITEM_ID")
    private Long approvalItemId;

    /**
     * 组织id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 价格审批单头主键
     */
    @TableField("APPROVAL_HEADER_ID")
    private Long approvalHeaderId;

    /**
     * 价格审批单号
     */
    @TableField("APPROVAL_NO")
    private String approvalNo;

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
     * 品类id
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

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
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 未税现价
     */
    @TableField("NOTAX_CURRENT_PRICE")
    private BigDecimal notaxCurrentPrice;

    /**
     * 未税选定单价
     */
    @TableField("NOTAX_SELECTED_PRICE")
    private BigDecimal notaxSelectedPrice;

    /**
     * 含税单价
     */
    @TableField("TAX_PRICE")
    private BigDecimal taxPrice;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private String taxRate;

    /**
     * 币种
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 定价开始时间
     */
    @TableField("FIXED_PRICE_BEGIN")
    private Date fixedPriceBegin;

    /**
     * 定价结束时间
     */
    @TableField("FIXED_PRICE_END")
    private Date fixedPriceEnd;

    /**
     * 行类型（价格类型）
     */
    @TableField("ITEM_TYPE")
    private String itemType;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
