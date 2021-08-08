package com.midea.cloud.srm.model.price.costelement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  要素属性表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:20
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_feature_attribute")
public class FeatureAttribute extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 要素属性ID
     */
    @TableId("FEATURE_ATTRIBUTE_ID")
    private Long featureAttributeId;

    /**
     * 成本要素ID
     */
    @TableField("COST_ELEMENT_ID")
    private Long costElementId;

    /**
     * 属性名字
     */
    @TableField("ATTRIBUTE_NAME")
    private String attributeName;

    /**
     * 属性单位, 字典编码: FEATURE_ATTRIBUTE_UNIT
     */
    @TableField("ATTRIBUTE_UNIT")
    private String attributeUnit;

    /**
     * 属性类型, 字典编码: FEATURE_ATTRIBUTE_TYPE
     */
    @TableField("ATTRIBUTE_TYPE")
    private String attributeType;

    /**
     * 属性值
     */
    @TableField("ATTRIBUTE_VALUE")
    private String attributeValue;

    /**
     * 关键属性(N-否, Y-是)
     */
    @TableField("CRUCIAL_FLAG")
    private String crucialFlag;

    /**
     * 必填标记(N-否, Y-是)
     */
    @TableField("REQUIRED_FLAG")
    private String requiredFlag;

    /**
     * 禁用标记(N-否, Y-是)
     */
    @TableField("DISABLE_FLAG")
    private String disableFlag;

    /**
     * 排序标记(1,2...)
     */
    @TableField("SEQUENCE_FLAG")
    private Integer sequenceFlag;

    /**
     * 是否数值(N-否, Y-是)
     */
    @TableField("NUMBER_FLAG")
    private String numberFlag;

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
