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
 *  价格估算头表 模型
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
@TableName("scc_price_estimate_head")
public class EstimateHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 价格估算头ID
     */
    @TableId("ESTIMATE_HEAD_ID")
    private Long estimateHeadId;

    /**
     * 单据编号
     */
    @TableField("ESTIMATE_CODE")
    private String estimateCode;

    /**
     * 单据名称
     */
    @TableField("ESTIMATE_NAME")
    private String estimateName;

    /**
     * 核价人员ID
     */
    @TableField("NUCLEAR_USER_ID")
    private Long nuclearUserId;

    /**
     * 核价人员账号
     */
    @TableField("NUCLEAR_USER_NAME")
    private String nuclearUserName;

    /**
     * 核价人员名字
     */
    @TableField("NUCLEAR_FULL_NAME")
    private String nuclearFullName;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类名字
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名字
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 币种,字典: BID_TENDER_CURRENCY
     */
    @TableField("CLEAR_CURRENCY")
    private String clearCurrency;

    /**
     * 价格模型头ID
     */
    @TableField("PRICE_MODEL_HEAD_ID")
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
     * 预估总价
     */
    @TableField("ESTIMATED_TOTAL_PRICE")
    private BigDecimal estimatedTotalPrice;

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
    private List<EstimateFile> estimateFileList;

    @TableField(exist = false)
    private List<EstimateAttrHead> estimateAttrHeadList;

}
