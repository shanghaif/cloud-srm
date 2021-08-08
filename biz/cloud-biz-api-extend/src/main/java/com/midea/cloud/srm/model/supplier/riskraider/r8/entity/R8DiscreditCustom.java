package com.midea.cloud.srm.model.supplier.riskraider.r8.entity;

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
 *  关联企业失信信息表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_r8_discredit_custom")
public class R8DiscreditCustom extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关联企业失信ID
     */
    @TableId("DISCREDIT_CUSTOM_ID")
    private Long discreditCustomId;

    /**
     * 企业失信ID
     */
    @TableField("DISCREDIT_ID")
    private Long discreditId;

    /**
     * Saas 账号名
     */
    @TableField("ACCOUNT_NAME")
    private String accountName;

    /**
     * 企业名称
     */
    @TableField("ENTERPRISE_NAME")
    private String enterpriseName;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 标的金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 类型（1：企业近期经营不善或违规被处罚，2：企业上下游客户存赊款逾期或货物拖欠，3：企业法人、股东、高管，近期存有涉诉或从事不法活动，4：其他）
     */
    @TableField("TYPE")
    private String type;

    /**
     * 状态（0：未履约，1：已履约）
     */
    @TableField("STATE")
    private String state;

    /**
     * 发生日期
     */
    @TableField("HAPPEN_DATE")
    private String happenDate;

    /**
     * 内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDate createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    private LocalDate modifyTime;

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
