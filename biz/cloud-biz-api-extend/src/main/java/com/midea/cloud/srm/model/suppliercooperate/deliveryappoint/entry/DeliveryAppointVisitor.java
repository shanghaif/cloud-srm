package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry;

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
 *  预约送货来访人员表 模型
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
@TableName("scc_sc_delivery_appoint_visitor")
public class DeliveryAppointVisitor extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId("DELIVERY_APPOINT_VISITOR_ID")
    private Long deliveryAppointVisitorId;

    /**
     * 主键ID
     */
    @TableId("DELIVERY_APPOINT_ID")
    private Long deliveryAppointId;

    /**
     * 来访人
     */
    @TableField("VISITOR_NAME")
    private String visitorName;

    /**
     * 证件类型
     */
    @TableField("ID_TYPE")
    private String idType;

    /**
     * 证件号码
     */
    @TableField("ID_NO")
    private String idNo;

    /**
     * 联系电话
     */
    @TableField("LINK_PHONE")
    private String linkPhone;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;
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