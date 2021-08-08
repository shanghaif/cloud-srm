package com.midea.cloud.srm.model.base.clarify.entity;

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
 * <pre>
 *  寻源模块澄清公告表 模型
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:47:41
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sourcing_clarify_reply")
public class SourcingClarifyReply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 澄清公告回复主键
     */
    @TableId("CLARIFY_REPLY_ID")
    private Long clarifyReplyId;

    /**
     * 澄清公告主键
     */
    @TableField("CLARIFY_ID")
    private Long clarifyId;

    /**
     * 澄清公告编号
     */
    @TableField("CLARIFY_NUMBER")
    private String clarifyNumber;

    /**
     * 澄清公告标题
     */
    @TableField("CLARIFY_TITLE")
    private String clarifyTitle;

    /**
     * 截止时间
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 寻源类型，招标、询比价、竞价
     */
    @TableField("SOURCING_TYPE")
    private String sourcingType;

    /**
     * 寻源项目id
     */
    @TableField("SOURCING_ID")
    private Long sourcingId;

    /**
     * 寻源项目编号
     */
    @TableField("SOURCING_NUMBER")
    private String sourcingNumber;

    /**
     * 寻源项目名称
     */
    @TableField("SOURCING_PROJECT_NAME")
    private String sourcingProjectName;

    /**
     * 供应商id
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 回复内容
     */
    @TableField("REPLY_CONTENT")
    private String replyContent;
    /**
     * 公司id
     */
    @TableField("COMPANY_ID")
    private Long companyId;
    /**
     * 公司名
     */
    @TableField("COMPANY_NAME")
    private String companyName;
    /**
     * 公司编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;
    /**
     * 回复状态
     */
    @TableField("REPLY_STATUS")
    private String replyStatus;
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
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
