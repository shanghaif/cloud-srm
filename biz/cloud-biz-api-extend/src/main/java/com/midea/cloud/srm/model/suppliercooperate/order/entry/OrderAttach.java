package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.BaseErrorCell;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 *  订单附件
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/4 8:42
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_order_attach")
public class OrderAttach extends BaseErrorCell {
    private static final long serialVersionUID = 8037565442431868098L;
    /**
     * 附件id
     */
    @TableId("ATTACH_ID")
    private Long attachId;

    /**
     * 订单id
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 文件上传id
     */
    @TableField("FILEUPLOAD_ID")
    private Long fileuploadId;

    /**
     * 行号
     */
    @TableField("ROW_NUM")
    private Long rowNum;

    /**
     * 附件名
     */
    @TableField("ATTACH_NAME")
    private String attachName;

    /**
     * 开始日期
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 结束日期
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 附件类型
     */
    @TableField("ORDER_ATTACH_TYPE")
    private String orderAttachType;

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
     * 租户id
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

}
