package com.midea.cloud.srm.model.base.organization.entity;

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
 *  银行分行信息（隆基银行分行数据同步） 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-14 12:29:12
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_erp_branch_bank")
public class ErpBranchBank extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 银行分行主键ID
     */
    @TableId("BRANCH_BANK_ID")
    private Long branchBankId;

    /**
     * 国家编码（隆基erp字段）
     */
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /**
     * 银行编号（隆基erp字段）
     */
    @TableField("BANK_NUM")
    private String bankNum;

    /**
     * 银行名称（隆基erp字段）
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 分行编号（隆基erp字段）
     */
    @TableField("BRANCH_BANK_NUM")
    private String branchBankNum;

    /**
     * 分行名称（隆基erp字段）
     */
    @TableField("BRANCH_BANK_NAME")
    private String branchBankName;

    /**
     * 银行分行名称（隆基erp字段）
     */
    @TableField("ALT_BRANCH_BANK_NAME")
    private String altBranchBankName;

    /**
     * 分行类型（隆基erp字段）
     */
    @TableField("BRANCH_BANK_TYPE")
    private String branchBankType;

    /**
     * EDI地点（隆基erp字段）
     */
    @TableField("EDI_SITE")
    private String ediSite;

    /**
     * EFT编号（隆基erp字段）
     */
    @TableField("EFT_NUMBER")
    private String eftNumber;

    /**
     * 说明（隆基erp字段）
     */
    @TableField("DESCR")
    private String descr;

    /**
     * 分行详细地址（隆基erp字段）
     */
    @TableField("ADDR_DETAIL")
    private String addrDetail;

    /**
     * 无效日期（隆基erp字段）
     */
    @TableField("DISABLE_DATE")
    private String disableDate;

    /**
     * 联系人姓名（隆基erp字段）
     */
    @TableField("CONTRACTOR_NAME")
    private String contractorName;

    /**
     * 联系电话（隆基erp字段）
     */
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 电子邮箱（隆基erp字段）
     */
    @TableField("EMAIL_ADDR")
    private String emailAddr;

    /**
     * 接口状态（NEW：新增，UPDATE：更新）
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 数据来源系统（ERP：ERP系统，HR：人力资源系统，FINANCAIL_SHAREING）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

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
