package com.midea.cloud.srm.model.price.estimate.entity;

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
import java.util.List;

/**
*  <pre>
 *  价格估算属性头表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_estimate_attr_head")
public class EstimateAttrHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 价格估算属性头ID
     */
    @TableField("ATTR_HEAD_ID")
    private Long attrHeadId;

    /**
     * 价格估算头ID
     */
    @TableId("ESTIMATE_HEAD_ID")
    private Long estimateHeadId;

    /**
     * 成本要素类型(MATERIAL-材质,CRAFT-工艺,FEE-费用; 字典编码-COST_ELEMENT_TYPE )
     */
    @TableField("ELEMENT_TYPE")
    private String elementType;

    /**
     * 成本要素ID
     */
    @TableId("COST_ELEMENT_ID")
    private Long costElementId;

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
     * 要素版本: V1
     */
    @TableField("ELEMENT_VERSION")
    private String elementVersion;

    /**
     * 关键属性
     */
    @TableField("CRUCIAL_ATTRIBUTES")
    private String crucialAttributes;

    /**
     * 关键属性值
     */
    @TableField("CRUCIAL_ATTRIBUTES_VALUE")
    private String crucialAttributesValue;

    /**
     * 关键属性单位
     */
    @TableField("CRUCIAL_ATTRIBUTES_UNIT")
    private String unit;

    /**
     * 基价
     */
    @TableField("BASE_PRICE")
    private BigDecimal basePrice;

    /**
     * 放大系数
     */
    @TableField("ENLARGE_FACTOR")
    private BigDecimal enlargeFactor;

    /**
     * 单项估价
     */
    @TableField("ESTIMATE")
    private BigDecimal estimate;

    /**
     * 必选标记(N-否, Y-是)
     */
    @TableField("REQUIRED_FLAG")
    private String requiredFlag;

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

    @TableField(exist = false)
    private List<EstimateAttrLine> estimateAttrLines;


}
