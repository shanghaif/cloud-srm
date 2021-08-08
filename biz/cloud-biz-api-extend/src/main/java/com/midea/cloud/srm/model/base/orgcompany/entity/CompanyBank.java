package com.midea.cloud.srm.model.base.orgcompany.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  组织-公司:账户信息表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-14 14:32:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_org_company_bank")
public class CompanyBank extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公司账户ID
     */
    @TableId("COMPANY_BANK_ID")
    private Long companyBankId;

    /**
     * 组织公司头Id
     */
    @TableField("ORG_COMPANY_HEAD_ID")
    private Long orgCompanyHeadId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private String companyId;

    /**
     * 银行编号
     */
    @TableField("BANK_NUM")
    private String bankNum;

    /**
     * 银行名称
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 开户行名称
     */
    @TableField("BRANCH_BANK_NAME")
    private String branchBankName;

    /**
     * 账户名称
     */
    @TableField("ACCOUNT_NAME")
    private String accountName;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 是否主账号,只能有一个主账号(Y-是,N-否)
     */
    @TableField("IS_MAIN")
    private String isMain;

    /**
     * 是否激活(Y-是,N-否)
     */
    @TableField("IS_ACTIVE")
    private String isActive;

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
