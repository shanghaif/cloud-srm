package com.midea.cloud.srm.model.pm.ps.advance.entity;

import java.math.BigDecimal;
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
 *  预付款申请行表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 13:41:48
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_advance_apply_line")
public class AdvanceApplyLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,预付款申请行ID
     */
    @TableId("ADVANCE_APPLY_LINE_ID")
    private Long advanceApplyLineId;

    /**
     * 预付款申请头ID
     */
    @TableId("ADVANCE_APPLY_HEAD_ID")
    private Long advanceApplyHeadId;

    /**
     * 行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 联行号
     */
    @TableField("UNION_CODE")
    private String unionCode;

    /**
     * 银行名称
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 账户名称
     */
    @TableField("BANK_ACCOUNT_NAME")
    private String bankAccountName;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 开户行(分行名称)
     */
    @TableField("OPENING_BANK")
    private String openingBank;

    /**
     * 付款方式
     */
    @TableField("PAY_METHOD")
    private String payMethod;

    /**
     * 申请付款金额
     */
    @TableField("APPLY_PAY_AMOUNT")
    private BigDecimal applyPayAmount;

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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
