package com.midea.cloud.srm.model.price.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  价格模型头表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:17
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_model_head")
public class ModelHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 价格模型头ID
     */
    @TableId("PRICE_MODEL_HEAD_ID")
    private Long priceModelHeadId;

    /**
     * 核价模型编号
     */
    @TableField("PRICE_MODEL_CODE")
    private String priceModelCode;

    /**
     * 核价模型名称
     */
    @TableField("PRICE_MODEL_NAME")
    private String priceModelName;

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
     * 采购分类名字
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 模型状态(DRAFT-拟定,VALID-生效,INVALID-失效; 字典值: PRICE_MODEL_STATUS)
     */
    @TableField("STATUS")
    private String status;

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
     * 采购分类
     */
    @TableField(exist = false)
    private List<ModelCategory> modelCategoryList;

    /**
     * 成本要素
     */
    @TableField(exist = false)
    private List<ModelElement> modelElementList;

    /**
     * 采购分类ID
     */
    @TableField(exist = false)
    private Long categoryId;

}
