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
 *  报价-供应商物料绩效评分表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 10:09:27
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_quote_item_performance")
public class QuoteItemPerformance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 绩效评分主键
     */
    @TableId("ITEM_PERFORMANCE_ID")
    private Long itemPerformanceId;

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
     * 询价单id
     */
    @TableField("INQUIRY_ID")
    private Long inquiryId;

    /**
     * 询价单号
     */
    @TableField("INQUIRY_NO")
    private String inquiryNo;

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
     * 评分项目编码，关联-评分规则明细表
     */
    @TableField("SCORE_ITEM_CODE")
    private String scoreItemCode;

    /**
     * 评分项目名称，关联-评分规则明细表
     */
    @TableField("SCORE_ITEM_NAME")
    private String scoreItemName;

    /**
     * 评分规则，关联-评分规则明细表
     */
    @TableField("SCORE_RULE")
    private String scoreRule;

    /**
     * 满分值，关联-评分规则明细表
     */
    @TableField("FULL_SCORE")
    private BigDecimal fullScore;

    /**
     * 得分
     */
    @TableField("SCORE")
    private BigDecimal score;

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
