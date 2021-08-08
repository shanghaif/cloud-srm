package com.midea.cloud.srm.model.supplierauth.review.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
*  <pre>
 *  银行信息日志表 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:37
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_bank_journal")
public class BankJournal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("BANK_JOURNAL_ID")
    private Long bankJournalId;

    /**
     * 单据ID
     */
    @TableField("FORM_ID")
    private Long formId;

    /**
     * 单据类型
     */
    @TableField("FORM_TYPE")
    private String formType;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 银行名称
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 银行代码
     */
    @NotEmpty(message = "银行代码不能为空")
    @TableField("BANK_CODE")
    private String bankCode;

    /**
     * 开户行
     */
    @TableField("OPENING_BANK")
    private String openingBank;

    /**
     * 联行编码
     */
    @TableField("UNION_CODE")
    private String unionCode;

    /**
     * SWIFT CODE
     */
    @TableField(value = "SWIFT_CODE")
    private String swiftCode;

    /**
     * 银行账号
     */
    @NotEmpty(message = "银行账号不能为空")
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 银行账户名
     */
    @NotEmpty(message = "银行账户名称不能为空")
    @TableField("BANK_ACCOUNT_NAME")
    private String bankAccountName;

    /**
     * 币种
     */
    @NotEmpty(message = "银行信息的币种不能为空")
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 主账号
     */
    @TableField("CEEA_MAIN_ACCOUNT")
    private String ceeaMainAccount;

    /**
     * 启用
     */
    @TableField("CEEA_ENABLED")
    private String ceeaEnabled;

    /**
     * 账户类型
     */
    @TableField("ACCOUNT_TYPE")
    private String accountType;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private Date endDate;

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

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 银行信息Id
     */
    @TableField("CEEA_BANK_INFO_ID")
    private Long ceeaBankInfoId;

}
