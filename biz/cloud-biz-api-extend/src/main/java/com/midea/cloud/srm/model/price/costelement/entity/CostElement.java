package com.midea.cloud.srm.model.price.costelement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.price.baseprice.entity.BasePrice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  成本要素表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:19
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_cost_element")
public class CostElement extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 成本要素类型(MATERIAL-材质,CRAFT-工艺,FEE-费用; 字典编码-COST_ELEMENT_TYPE )
     */
    @TableField("ELEMENT_TYPE")
    private String elementType;

    /**
     * 计算方式(DIRECT_CALCULATION-直接计算,CALCULATED_BY_RATE-按费率计算; 字典编码-COST_ELEMENT_CALCULATION )
     */
    @TableField("CALCULATION")
    private String calculation;

    /**
     * 组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织名字
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 状态(DRAFT-拟定,VALID-生效,INVALID-失效; 字典值: COST_ELEMENT_STATUS)
     */
    @TableField("STATUS")
    private String status;

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
     * 单位
     */
    @TableField("UNIT")
    private String unit;

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

    /**
     * 是否显示最新版本
     */
    @TableField(exist = false)
    private String isNew;

    /**
     * 要素属性
     */
    @TableField(exist = false)
    private List<FeatureAttribute> featureAttributeList;

    /**
     * 用量公式
     */
    @TableField(exist = false)
    private List<FeatureFormula> dosageFormulasList;

    /**
     * 价格公式
     */
    @TableField(exist = false)
    private List<FeatureFormula> priceFormulasList;

    /**
     * 基价
     */
    @TableField(exist = false)
    private List<BasePrice> basePricesList;

    /**
     * 费率计算公式
     */
    @TableField(exist = false)
    private RateCalculation rateCalculation;

}
