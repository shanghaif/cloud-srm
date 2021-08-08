package com.midea.cloud.srm.model.pm.pr.division.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  品类分工规则表 模型
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:41:41
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_pr_division_category")
public class DivisionCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,品类分工规则ID
     */
    @TableId("DIVISION_CATEGORY_ID")
    private Long divisionCategoryId;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类编码(物料小类编码)
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类名称(物料小类名称)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 供应商管理采购员ID(废弃)
     */
    @TableField("SUP_USER_ID")
    private Long supUserId;

    /**
     * 供应商管理采购员名称(废弃)
     */
    @TableField("SUP_USER_NICKNAME")
    private String supUserNickname;

    /**
     * 供应商管理采购员名称(废弃)
     */
    @TableField("SUP_USER_NAME")
    private String supUserName;

    /**
     * 策略负责采购员ID(废弃)
     */
    @TableField("STRATEGY_USER_ID")
    private Long strategyUserId;

    /**
     * 策略负责采购员名称(废弃)
     */
    @TableField("STRATEGY_USER_NICKNAME")
    private String strategyUserNickname;

    /**
     * 策略负责采购员名称(废弃)
     */
    @TableField("STRATEGY_USER_NAME")
    private String strategyUserName;

    /**
     * 采购履行采购员ID(废弃)
     */
    @TableField("PERFORM_USER_ID")
    private Long performUserId;

    /**
     * 采购履行采购员名称(废弃)
     */
    @TableField("PERFORM_USER_NICKNAME")
    private String performUserNickname;

    /**
     * 采购履行采购员账号(废弃)
     */
    @TableField("PERFORM_USER_NAME")
    private String performUserName;

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

    /**
     * 职责
     */
    @TableField("DUTY")
    private String duty;

    /**
     * 负责人名称
     */
    @TableField("PERSON_IN_CHARGE_NICKNAME")
    private String personInChargeNickname;

    /**
     * 负责人用户名
     */
    @TableField("PERSON_IN_CHARGE_USERNAME")
    private String personInChargeUsername;

    /**
     * 负责人用户ID
     */
    @TableField("PERSON_IN_CHARGE_USER_ID")
    private Long personInChargeUserId;

    /**
     * 是否主负责人
     */
    @TableField("IF_MAIN_PERSON")
    private String ifMainPerson;

    @TableField(exist = false)
    private String erpNum;

    @TableField(exist = false)
    private List<Long> orgIds;
    @TableField(exist = false)
    private List<Long> organizationIds;
}
