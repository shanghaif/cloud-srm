package com.midea.cloud.srm.model.base.bda.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  业务状态控制 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 10:17:03
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_bda_state_control")
public class BdaStateControl extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("STATE_CONTROL_ID")
    private Long stateControlId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 业务类型,参考BUSINESS_TYPE字典
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 业务类型名称
     */
    @TableField("BUSINESS_TYPE_NAME")
    private String businessTypeName;

    /**
     * 可供货品类状态，控制供应商在哪个组织下可供货品类，APPLICATION申请中、QUALIFIED合格、SUSPEND暂停、TERMINATION终止,参考字典ORG_CATEOGRY_STATUS_TYPE
     */
    @TableField("CATEGORY_STATUS")
    private String categoryStatus;

    /**
     * 可供货品类状态名称
     */
    @TableField("CATEGORY_STATUS_NAME")
    private String categoryStatusName;

    /**
     * 可供组织状态，控制供应商在哪个组织下可供货,INTRODUCTION引入中、EFFECTIVE有效、FROZEN冻结、INVALID失效,参考字典码ORGANIZATION_STATUS_TYPE
     */
    @TableField("ORG_STATUS")
    private String orgStatus;

    /**
     * 可供组织状态名称
     */
    @TableField("ORG_STATUS_NAME")
    private String orgStatusName;

    /**
     * 是否(Y是 N否),参考字典码IS_ALLOW
     */
    @TableField("IS_ALLOW")
    private String isAllow;

    /**
     * 是否(Y是 N否),参考字典码IS_ALLOW
     */
    @TableField("IS_ALLOW_NAME")
    private String isAllowName;

    /**
     * 生效日期
     */
    @TableField("ACTIVE_DATE")
    private LocalDate activeDate;

    /**
     * 失效日期
     */
    @TableField("INACTIVE_DATE")
    private LocalDate inactiveDate;

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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
