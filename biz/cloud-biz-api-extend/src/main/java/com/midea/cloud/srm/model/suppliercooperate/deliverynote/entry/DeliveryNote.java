package com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry;

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
 *  送货单表 模型
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
@TableName("scc_sc_delivery_note")
public class DeliveryNote extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("DELIVERY_NOTE_ID")
    private Long deliveryNoteId;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

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
     * 送货单号
     */
    @TableField("DELIVERY_NUMBER")
    private String deliveryNumber;

    /**
     * 送货日期
     */
    @TableField("DELIVERY_DATE")
    private Date deliveryDate;

    /**
     * 预计到货日期
     */
    @TableField("EXPECTED_ARRIVAL_DATE")
    private Date expectedArrivalDate;

    /**
     * 旧送货单ID
     */
    @TableField("OLD_DELIVERY_NOTE_ID")
    private Long oldDeliveryNoteId;

    /**
     * 状态（单据状态）
     * {{@link com.midea.cloud.common.enums.order.DeliveryNoteStatus}}
     * 字典：DELIVERY_NOTE_STATUS
     */
    @TableField("DELIVERY_NOTE_STATUS")
    private String deliveryNoteStatus;

    /**
     * 状态（采购商确认状态）
     */
    @TableField("PURCHASE_CONFIRM_STATUS")
    private String purchaseConfirmStatus;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 送货单提交人ID
     */
    @TableField("SUBMITTED_ID")
    private Long submittedId;

    /**
     * 送货单提交人
     */
    @TableField("SUBMITTED_BY")
    private String submittedBy;

    /**
     * 提交时间
     */
    @TableField("SUBMITTED_TIME")
    private Date submittedTime;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

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
     * 送货联系人
     */
    @TableField("CONTACT_PEOPLE")
    private String contactPeople;

    /**
     * 联系方式
     */
    @TableField("CONTACT_NUMBER")
    private String contactNumber;

    /**
     * 收货工厂
     */
    @TableField("RECEIVED_FACTORY")
    private String receivedFactory;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 收货地点（交货地点）
     */
    @TableField("CEEA_DELIVERY_PLACE")
    private String ceeaDeliveryPlace;
    /**
     * 送货方式(PO、DP)默认是DP
     */
    @TableField("CEEA_DELIVERY_SYSTEM")
    private String ceeaDeliverySystem;
    /**
     * 寄售类型(“Y”寄售；“N”非寄售；默认“N”)
     */
    @TableField("CEEA_CONSIGN_TYPE")
    private String ceeaConsignType;

    /**
     * 是否创建送货预约(Y/N)
     */
    @TableField("IF_CREATE_DELIVERY_APPOINTMENT")
    private String ifCreateDeliveryAppointment;

}
