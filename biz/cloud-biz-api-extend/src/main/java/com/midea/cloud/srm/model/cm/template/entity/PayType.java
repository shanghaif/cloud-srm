package com.midea.cloud.srm.model.cm.template.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  合同付款类型 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-13 16:49:04
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_templ_pay_type")
public class PayType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("PAY_TYPE_ID")
    private Long payTypeId;

    /**
     * 付款类型
     */
    @TableField("PAY_TYPE")
    private String payType;

    /**
     * 付款类型编码
     */
    @TableField("PAY_TYPE_CODE")
    private String payTypeCode;

    /**
     * 付款说明
     */
    @TableField("PAY_EXPLAIN")
    private String payExplain;

    /**
     * 付款天数
     */
    @TableField("PAY_DELAYTIME")
    private Integer payDelaytime;

    /**
     * 支付日期是否必填
     */
    @TableField("PAY_DATE_REQUIRED")
    private String payDateRequired;

    /**
     * 是否预付款
     */
    @TableField("IS_ADVANCE")
    private String isAdvance;

    /**
     * 是否验收后触发
     */
    @TableField("IS_AFTER_CHECK")
    private String isAfterCheck;

    /**
     * 是否开票后触发
     */
    @TableField("IS_AFTER_INVOICING")
    private String isAfterInvoicing;

    /**
     * 逻辑说明
     */
    @TableField("LOGICAL_EXPLAIN")
    private String logicalExplain;

    /**
     * 状态,生效:EFFECTIVE,失效:INVALID
     */
    @TableField("PAY_TYPE_STATUS")
    private String payTypeStatus;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 条件因素ID数组,多个用英文逗号隔开
     */
    @TableField("COND_FACTOR_ID")
    private String condFactorId;

    /**
     * 条件因素数组,多个用英文逗号隔开
     */
    @TableField("COND_FACTOR")
    private String condFactor;

    /**
     * 取值区间
     */
    @TableField("VALUE_RANGE")
    private String valueRange;
}
