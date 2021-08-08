package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  退货单表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_return_order")
public class ReturnOrder  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("RETURN_ORDER_ID")
    private Long returnOrderId;

    /**
     * 退货单号
     */
    @TableField("RETURN_ORDER_NUMBER")
    private String returnOrderNumber;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 采购组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 退货日期
     */
    @TableField("RETURN_DATE")
    private Date returnDate;

    /**
     * 退货单状态
     * {{@link com.midea.cloud.common.enums.neworder.ReturnOrderStatus}}
     * 字典：RETURN_ORDER_STATUS
     */
    @TableField("RETURN_STATUS")
    private String returnStatus;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 订单提交人ID
     */
    @TableField("SUBMITTED_ID")
    private Long submittedId;

    /**
     * 订单提交人
     */
    @TableField("SUBMITTED_BY")
    private String submittedBy;

    /**
     * 提交时间
     */
    @TableField("SUBMITTED_TIME")
    private Date submittedTime;

    /**
     * 订单确认人ID
     */
    @TableField("CONFIRM_ID")
    private Long confirmId;

    /**
     * 订单确认人
     */
    @TableField("CONFIRM_BY")
    private String confirmBy;

    /**
     * 确认时间
     */
    @TableField("CONFIRM_TIME")
    private Date confirmTime;

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

    /**
     * 退货原因
     */
    @TableField("RETURN_REASON")
    private String returnReason;

    /**
     * 拒绝原因
     */
    @TableField("REJECT_REASON")
    private String rejectReason;

    @TableField(exist = false)
    private List<Long> ids;

}
