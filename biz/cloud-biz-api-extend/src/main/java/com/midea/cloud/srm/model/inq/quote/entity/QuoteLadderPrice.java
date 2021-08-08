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
 *  报价--物料阶梯价表 模型
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 15:22:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_quote_ladder_price")
public class QuoteLadderPrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 阶梯价id
     */
    @TableId("QUOTE_LADDER_PRICE_ID")
    private Long quoteLadderPriceId;

    /**
     * 报价单id
     */
    @TableField("QUOTE_ID")
    private Long quoteId;

    /**
     * 报价单行id
     */
    @TableField("QUOTE_ITEM_ID")
    private Long quoteItemId;

    @TableField("ITEM_ID")
    private Long itemId;

    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 报价起始数量
     */
    @TableField("BEGIN_QUANTITY")
    private BigDecimal beginQuantity;

    /**
     * 报价结束数量
     */
    @TableField("END_QUANTITY")
    private BigDecimal endQuantity;

    /**
     * 单价
     */
    @TableField("PRICE")
    private BigDecimal price;

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
