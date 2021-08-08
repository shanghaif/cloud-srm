package com.midea.cloud.srm.model.supplier.info.entity;

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
 *  管理体系信息认证附件表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:49:58
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_management_attach")
public class ManagementAttach extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,管理体系信息附件ID
     */
    @TableId("MANAGEMENT_ATTACH_ID")
    private Long managementAttachId;

    /**
     * 管理体系信息ID
     */
    @TableField("MANAGEMENT_INFO_ID")
    private Long managementInfoId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 公司编码
     */
    @TableField(value = "COMPANY_CODE", exist = false)
    private String companyCode;
    /**
     * 公司名称
     */
    @TableField(value = "COMPANY_NAME", exist = false)
    private String companyName;


    /**
     * 文件上传ID
     */
    @TableField("FILEUPLOAD_ID")
    private Long fileuploadId;

    /**
     * 认证类型
     */
    @TableField("AUTH_TYPE")
    private String authType;

    /**
     * 认证描述
     */
    @TableField("AUTH_DESCRIPTION")
    private String authDescription;

    /**
     * 认证编号
     */
    @TableField("AUTH_NUM")
    private String authNum;

    /**
     * 认证时间
     */
    @TableField("AUTH_DATE")
    private LocalDate authDate;

    /**
     * 认证机构
     */
    @TableField("AUTH_ORG")
    private String authOrg;

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
     * 是否启用提醒
     */
    @TableField("IS_USE_REMINDER")
    private String isUseReminder;

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
     * 来源单据
     */
    @TableField(exist = false)
    private String dataSources;

    /**
     * file_record_id
     */
    @TableField(exist = false)
    private Long fileRecordId;

    /**
     * form_id
     */
    @TableField(exist = false)
    private Long formId;
    /**
     * form type
     */
    @TableField(exist = false)
    private String formType;
    /**
     * file_id
     */
    @TableField(exist = false)
    private Long fileId;

}
