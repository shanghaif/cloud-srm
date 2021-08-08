package com.midea.cloud.srm.model.supplier.invite.entity;

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
 * <pre>
 *  邀请供应商 模型
 * </pre>
 *
 * @author dengbin1@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 30, 2021 9:52:55 AM
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_invite_vendor")
public class InviteVendor extends BaseEntity {
    private static final long serialVersionUID = 940824L;
    /**
     * invite_vendor_id
     */
    @TableId("INVITE_VENDOR_ID")
    private Long inviteVendorId;
    /**
     * 单据编码
     */
    @TableField("INVITE_VENDOR_NO")
    private String inviteVendorNo;
    /**
     * 状态
     */
    @TableField("INVITE_STATUS")
    private String inviteStatus;
    /**
     * 邀请原因，字典值
     */
    @TableField("INVITE_REASON")
    private String inviteReason;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 联系人
     */
    @TableField("CONTACT_PERSON")
    private String contactPerson;
    /**
     * 联系邮箱
     */
    @TableField("CONTACT_EMAIL")
    private String contactEmail;
    /**
     * 手机号码
     */
    @TableField("PHONE_NUMBER")
    private String phoneNumber;
    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;
    /**
     * 统一社会信用代码
     */
    @TableField("SOCIAL_CREDIT_CODE")
    private String socialCreditCode;
    /**
     * 发布（邀请）时间
     */
    @TableField("PUBLISH_DATE")
    private Date publishDate;
    /**
     * 注册后的供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;
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

    /***
     * 查询邀请时间的区间
     */
    @TableField(exist = false)
    private Date publishStartDate;
    @TableField(exist = false)
    private Date publishEndDate;
}