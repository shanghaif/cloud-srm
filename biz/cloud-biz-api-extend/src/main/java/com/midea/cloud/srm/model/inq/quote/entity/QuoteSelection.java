package com.midea.cloud.srm.model.inq.quote.entity;

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
 *  供应商报价评选表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 10:59:35
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_quote_selection")
public class QuoteSelection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评选主键
     */
    @TableId("QUOTE_SELECTION_ID")
    private Long quoteSelectionId;

    /**
     * 询价单id
     */
    @TableField("INQUIRY_ID")
    private Long inquiryId;

    /**
     * 报价明细行id
     */
    @TableField("QUOTE_ITEM_ID")
    private Long quoteItemId;

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
     * 现价
     */
    @TableField("CURRENT_PRICE")
    private BigDecimal currentPrice;

    /**
     * 目标价
     */
    @TableField("NOTAX_TARGRT_PRICE")
    private BigDecimal notaxTargrtPrice;

    /**
     * 报价价差
     */
    @TableField("QUOTE_PRICE_DIFFERENCE")
    private BigDecimal quotePriceDifference;

    /**
     * 现价价差
     */
    @TableField("CURRENT_PRICE_DIFFERENCE")
    private BigDecimal currentPriceDifference;

    /**
     * 综合得分
     */
    @TableField("COMPOSITE_SCORE")
    private BigDecimal compositeScore;

    /**
     * 价格得分
     */
    @TableField("PRICE_SCORE")
    private BigDecimal priceScore;

    /**
     * 品质得分
     */
    @TableField("QUALITY_SCORE")
    private BigDecimal qualityScore;

    /**
     * 评选排名
     */
    @TableField("RANKING")
    private Integer ranking;

    /**
     * 是否含税
     */
    @TableField("HAS_TAX")
    private String hasTax;

    /**
     * 是否选定报价
     */
    @TableField("IS_SELECTED")
    private String isSelected;

    /**
     * 创建人id
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
     * 版本
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}
