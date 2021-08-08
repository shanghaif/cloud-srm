package com.midea.cloud.srm.model.price.baseprice.entity;

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
 *  基价表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:16:13
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_base_price")
public class BasePrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 基价ID
     */
    @TableId("BASE_PRICE_ID")
    private Long basePriceId;

    /**
     * 组合编码
     */
    @TableField("COMBINATION_CODE")
    private String combinationCode;

    /**
     * 关键属性组合
     */
    @TableField("KEY_ATTRIBUTE_COMBINATION")
    private String keyAttributeCombination;

    /**
     * 属性值组合
     */
    @TableField("ATTRIBUTE_VALUE_COMBINATION")
    private String attributeValueCombination;

    /**
     * 成本要素类型(MATERIAL-材质,CRAFT-工艺,FEE-费用; 字典编码-COST_ELEMENT_TYPE )
     */
    @TableField("ELEMENT_TYPE")
    private String elementType;

    /**
     * 成本要素编码
     */
    @TableField("ELEMENT_CODE")
    private String elementCode;

    /**
     * 成本要素名称
     */
    @TableField("ELEMENT_NAME")
    private String elementName;

    /**
     * 基价
     */
    @TableField("BASE_PRICE")
    private BigDecimal basePrice;

    /**
     * 基价状态(DRAFT-拟定,VALID-生效,INVALID-失效; 字典:BASE_PRICE_STATUS )
     */
    @TableField("STATUS")
    private String status;

    /**
     * 币种,字典: BID_TENDER_CURRENCY
     */
    @TableField("CLEAR_CURRENCY")
    private String clearCurrency;

    /**
     * 有效期从
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 有效期至
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 显示类别(0-分表显示,1-总表显示,2-总分表显示)
     */
    @TableField("DISPLAY_CATEGORY")
    private String displayCategory;

    /**
     * 基价版本
     */
    @TableField("PRICE_VERSION")
    private String priceVersion;

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
