package com.midea.cloud.srm.model.inq.inquiry.entity;

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
*  <pre>
 *  询价-询价信息行表 模型
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-16 14:55:25
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_inquiry_item")
public class Item extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 询价行id
     */
    @TableId("INQUIRY_ITEM_ID")
    private Long inquiryItemId;

    /**
     * 询价单头id
     */
    @TableField("INQUIRY_ID")
    private Long inquiryId;

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
     * 物料名称
     */
    @TableField("ITEM_DESC")
    private String itemDesc;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 需求数量
     */
    @TableField("DEMAND_QUANTITY")
    private BigDecimal demandQuantity;

    /**
     * 规格
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

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
     * 行类型
     */
    @TableField("ITEM_TYPE")
    private String itemType;

    /**
     * 行类型
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 需求计划日期
     */
    @TableField("PLAN_DAY")
    private BigDecimal planDay;

    /**
     * 订单数量
     */
    @TableField("ORDER_QUANTITY")
    private BigDecimal orderQuantity;

    /**
     * 未税目标价
     */
    @TableField("NOTAX_TARGRT_PRICE")
    private String notaxTargrtPrice;

    /**
     * 未税单价
     */
    @TableField("NOTAX_PRICE")
    private BigDecimal notaxPrice;

    /**
     * 定价开始日期
     */
    @TableField("FIXED_PRICE_BEGIN")
    private Date fixedPriceBegin;

    /**
     * 定价结束日期
     */
    @TableField("FIXED_PRICE_END")
    private Date fixedPriceEnd;

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
     * 说明
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 附件id
     */
    @TableField("FILE_RELATION_ID")
    private Long fileRelationId;

    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 品类是否可编辑
     */
    @TableField("CATEGORY_CAN_EDIT")
    private String categoryCanEdit;

    /**
     * 删除标识
     */
    @TableField("DEL_FLAG")
    private String delFlag;

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

}
