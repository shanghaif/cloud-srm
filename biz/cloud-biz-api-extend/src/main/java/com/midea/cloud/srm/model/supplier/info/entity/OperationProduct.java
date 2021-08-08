package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  经营信息产品能力信息表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:53:37
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_operation_product")
public class OperationProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,经营信息产品能力ID
     */
    @TableId("OP_PRODUCT_ID")
    private Long opProductId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 经营信息
     */
    @TableField("OP_INFO_ID")
    private Long opInfoId;

    /**
     * 生产基地
     */
    @TableField("PRO_BASE")
    private String proBase;

    /**
     * 产品名称
     */
    @TableField("PRO_NAME")
    private String proName;

    /**
     * 产品品牌
     */
    @TableField("PRO_BRAND")
    private String proBrand;

    /**
     * 主要工艺
     */
    @TableField("MAIN_TECHNICS")
    private String mainTechnics;

    /**
     * 年产量
     */
    @TableField("YEAR_OUTPUT")
    private String yearOutput;

    /**
     * 可提供给隆基的供应产能比例
     */
    @TableField("SUPPLY_CAPACITY_RATE")
    private String supplyCapacityRate;

    /**
     * 产品合格率
     */
    @TableField("PRO_QUALIFIED_RATE")
    private String proQualifiedRate;

    /**
     * 年营业额
     */
    @TableField("YEAR_TURNOVER")
    private String yearTurnover;

    /**
     * 备注
     */
    @TableField("COMMENT")
    private String comment;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
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
