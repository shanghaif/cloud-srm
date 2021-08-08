package com.midea.cloud.srm.model.perf.scoreproject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  <pre>
 *  绩效评分项目评分人-供应商指标表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-02 15:18:22
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_score_item_man_supind")
public class PerfScoreItemManSupInd extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("SCORE_ITEM_MAN_SUP_IND_ID")
    private Long scoreItemManSupIndId;

    /**
     * 关联绩效评分项目评分人表ID
     */
    @TableField("SCORE_ITEMS_MAN_ID")
    private Long scoreItemsManId;

    /**
     * 关联绩效评分项目表ID
     */
    @TableField("SCORE_ITEMS_ID")
    private Long scoreItemsId;

    /**
     * 关联绩效评分项目供应商表ID
     */
    @TableField("SCORE_ITEMS_SUP_ID")
    private Long scoreItemsSupId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 供应商编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 供应商名称(英文)
     */
    @TableField("COMPANY_EN_NAME")
    private String companyEnName;

    /**
     * 关联绩效模型指标维度表ID
     */
    @TableField("TEMPLATE_DIM_WEIGHT_ID")
    private Long templateDimWeightId;

    /**
     * 指标维度(绩效模型指标维度表(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术))
     */
    @TableField("INDICATOR_DIMENSION")
    private String indicatorDimension;

    /**
     * 绩效模型表的指标行id
     */
    @TableField("TEMPLATE_LINE_ID")
    private Long templateLineId;

    /**
     * 绩效模型指标表的指标名称
     */
    @TableField("INDICATOR_NAME")
    private String indicatorName;

    /**
     * 允许评分(Y-允许评分/N禁止评分)
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
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
