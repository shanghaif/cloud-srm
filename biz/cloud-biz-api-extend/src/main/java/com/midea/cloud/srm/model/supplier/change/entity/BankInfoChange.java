package com.midea.cloud.srm.model.supplier.change.entity;

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
import java.util.Map;

/**
*  <pre>
 *  银行信息变更 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:37
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_bank_info_change")
public class BankInfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 银行变更ID
     */
    @TableId("BANK_CHANGE_ID")
    private Long bankChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("BANK_INFO_ID")
    private Long bankInfoId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**ERP供应商Id（隆基新增） */
    @TableField("ERP_VENDOR_ID")
    private Long erpVendorId;

    /**
     * 银行国家（地区）
     */
    @TableField("BANK_COUNTRY")
    private String bankCountry;

    /**
     * 银行名称
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 银行代码
     */
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
    @TableField("SWIFT_CODE")
    private String swiftCode;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 银行账户名
     */
    @TableField("BANK_ACCOUNT_NAME")
    private String bankAccountName;

    /**
     * 币种
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**主账号 */
    @TableField("CEEA_MAIN_ACCOUNT")
    private String ceeaMainAccount;
    /**启用 */
    @TableField("CEEA_ENABLED")
    private String ceeaEnabled;
    /**
     * 账户类型
     */
    @TableField("ACCOUNT_TYPE")
    private String accountType;

    /**
     * 开户证明文件ID
     */
    @TableField("PROOF_FILE_ID")
    private String proofFileId;

    /**
     * 上传开户证明
     */
    @TableField("PROOF")
    private String proof;

    /**
     * 上传委托收款证明文件ID
     */
    @TableField("UPLOAD_CO_FILE_ID")
    private String uploadCoFileId;

    /**
     * 上传委托收款证明
     */
    @TableField("UPLOAD_COLLECTIONS")
    private String uploadCollections;

    /**
     * 生效时间
     */
    @TableField("ACTIVE_DATE")
    private LocalDate activeDate;

    /**
     * 失效时间
     */
    @TableField("INACTIVE_DATE")
    private LocalDate inactiveDate;

    /**
     * 合作组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 合作组织
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 合作组织code
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 合作组织ID
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

    /**
     * 父组织名称
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 父组织code
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;

    @TableField("OP_TYPE")
    private String opType;


}
