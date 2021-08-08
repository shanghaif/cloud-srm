package com.midea.cloud.srm.model.cm.template.entity;

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
 *  合同模板头表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-19 08:58:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_templ_head")
public class TemplHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("TEMPL_HEAD_ID")
    private Long templHeadId;

    /**
     * 模板类型
     */
    @TableField("TEMPL_TYPE")
    private String templType;

    /**
     * 模板名称
     */
    @TableField("TEMPL_NAME")
    private String templName;

    /**
     * 模板描述
     */
    @TableField("TEMPL_DESCRIPTION")
    private String templDescription;

    /**
     * 模板状态
     */
    @TableField("TEMPL_STATUS")
    private String templStatus;

    /**
     * 甲方
     */
    @TableField("OWNER")
    private String owner;

    /**
     * 传真
     */
    @TableField("FAX")
    private String fax;

    /**
     * 电话
     */
    @TableField("PHONE")
        private String phone;

    /**
     * 签约地点
     */
    @TableField("SIGNING_SITE")
    private String signingSite;

    /**
     * 邮编
     */
    @TableField("POSTCODE")
    private String postcode;

    /**
     * 开户行
     */
    @TableField("OPENING_BANK")
    private String openingBank;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 法定代表人
     */
    @TableField("LEGAL_PERSON")
    private String legalPerson;

    /**
     * 委托代理人
     */
    @TableField("ENTRUSTED_AGENT")
    private String entrustedAgent;

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
     * 失效原因
     */
    @TableField("INVALID_REASON")
    private String invalidReason;

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


}
