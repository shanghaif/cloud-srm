package com.midea.cloud.srm.model.base.work.entry;

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
@TableName("scc_base_work")
public class Work extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("WORK_ID")
    private Long workId;

    /**
     * 业务ID
     */
    @TableField("FROM_ID")
    private Long fromId;

    /**
     * 处理人ID
     */
    @TableField("HANDLE_ID")
    private Long handleId;

    /**
     * 处理人账号
     */
    @TableField("HANDLE_BY")
    private String handleBy;

    /**
     * 处理人姓名
     */
    @TableField("HANDLE_NICKNAME")
    private String handleNickname;

    /**
     * 处理状态
     */
    @TableField("WORK_STATUS")
    private String workStatus;

    /**
     * 发送人ID
     */
    @TableField("SENDER_ID")
    private Long senderId;

    /**
     * 发送人姓名
     */
    @TableField("SEND_BY")
    private String sendBy;

    /**
     * 发送时间
     */
    @TableField("SEND_TIME")
    private Date sendTime;

    /**
     * 单据
     */
    @TableField("BILL")
    private String bill;

    /**
     * 主题
     */
    @TableField("TOPIC")
    private String topic;

    /**
     * 链接
     */
    @TableField("LINK_URL")
    private String linkUrl;

    /**
     * 处理节点
     */
    @TableField("NODE")
    private String node;

    /**
     * 来自供应商ID
     */
    @TableField("FROM_VENDOR_ID")
    private Long fromVendorId;

    /**
     * 来自供应商编号
     */
    @TableField("FROM_VENDOR_CODE")
    private String fromVendorCode;

    /**
     * 来自供应商名称
     */
    @TableField("FROM_VENDOR_NAME")
    private String fromVendorName;

    /**
     * 接收供应商ID
     */
    @TableField("TO_VENDOR_ID")
    private Long toVendorId;

    /**
     * 接收供应商编号
     */
    @TableField("TO_VENDOR_CODE")
    private String toVendorCode;

    /**
     * 接收供应商名称
     */
    @TableField("TO_VENDOR_NAME")
    private String toVendorName;

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