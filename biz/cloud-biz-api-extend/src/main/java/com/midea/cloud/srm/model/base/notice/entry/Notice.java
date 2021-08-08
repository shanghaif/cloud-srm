package com.midea.cloud.srm.model.base.notice.entry;

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
 *  公告表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/27 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_notice")
public class Notice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("NOTICE_ID")
    private Long noticeId;

    /**
     * 是否通知所有
     */
    @TableField("NOTICE_ALL")
    private String noticeAll;

    /**
     * 公告分类
     */
    @TableField("NOTICE_TYPE")
    private String noticeType;

    /**
     * 状态
     */
    @TableField("NOTICE_STATUS")
    private String noticeStatus;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 正文
     */
    @TableField("DETAIL")
    private String detail;

    /**
     * 附件ID
     */
    @TableField("FILE_RELATION_ID")
    private Long fileRelationId;

    /**
     * 附件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 置顶公告,Y：是,N：否
     */
    @TableField("IS_TOP")
    private String isTop;

    /**
     * 发布人ID
     */
    @TableField("PUBLISHER_ID")
    private Long publisherId;

    /**
     * 发布人
     */
    @TableField("PUBLISH_BY")
    private String publishBy;

    /**
     * 发布时间
     */
    @TableField("PUBLISH_TIME")
    private Date publishTime;


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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}