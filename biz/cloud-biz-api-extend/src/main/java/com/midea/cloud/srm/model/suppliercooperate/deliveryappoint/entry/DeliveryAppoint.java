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
 *  送货预约表 模型
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
@TableName("scc_sc_delivery_appoint")
public class DeliveryAppoint extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId("DELIVERY_APPOINT_ID")
    private Long deliveryAppointId;

    /**
     * 预约单号
     */
    @TableField("DELIVERY_APPOINT_NUMBER")
    private String deliveryAppointNumber;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
        private String vendorName;

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
     * 组织编号
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 车辆主键ID
     */
    @TableField("CAR_INFO_ID")
    private Long carInfoId;

    /**
     * 车牌号码
     */
    @TableField("LICENSE_PLATE")
    private String licensePlate;

    /**
     * 车辆类型
     */
    @TableField("CAR_TYPE")
    private String carType;

    /**
     * 送货日期
     */
    @TableField("ENTRY_TIME")
    private Date entryTime;

    /**
     * 库存地点
     */
    @TableField("ENTRY_PLACE")
    private String entryPlace;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 受访人员
     */
    @TableField("RESPONDENTS")
    private String respondents;

    /**
     * 受访人编号
     */
    @TableField("RESPONDENTS_NO")
    private String respondentsNo;

    /**
     * 受访人部门
     */
    @TableField("RESPONDENTS_GOUND")
    private String respondentsGound;

    /**
     * 受访人部门编号
     */
    @TableField("RESPONDENTS_GOUND_NUMBER")
    private String respondentsGoundNumber;

    /**
     * 送货预约单状态
     * {{@link com.midea.cloud.common.enums.neworder.DeliveryNoticeStatus}}
     * 字典编码：DELIVERY_APPOINT_STATUS
     */
    @TableField("DELIVERY_APPOINT_STATUS")
    private String deliveryAppointStatus;

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
     * 预约单确认人ID
     */
    @TableField("COMFIRM_ID")
    private Long comfirmId;

    /**
     * 预约确认人
     */
    @TableField("COMFIRM_BY")
    private String comfirmBy;

    /**
     * 预约确认时间
     */
    @TableField("COMFIRM_TIME")
    private Date comfirmTime;

    /**
     * 预约拒绝人ID
     */
    @TableField("REFUSE_ID")
    private Long refuseId;

    /**
     * 预约拒绝人
     */
    @TableField("REFUSE_BY")
    private String refuseBy;

    /**
     * 拒绝时间
     */
    @TableField("REFUSE_TIME")
    private Date refuseTime;


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
     * 收货工厂
     */
    @TableField("RECEIVED_FACTORY")
    private String receivedFactory;

    /**
     * 业务实体id
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 送货地址
     */
    @TableField("RECEIVE_ADDRESS")
    private String receiveAddress;

    /**
     * 收单地址
     */
    @TableField("RECEIVE_ORDER_ADDRESS")
    private String receiveOrderAddress;

    /**
     * 受访人电话
     */
    @TableField("RESPONDENTS_PHONE")
    private String respondentsPhone;

    /**
     * 送货地点
     */
    @TableField("DELIVERY_LOCATION")
    private String deliveryLocation;

    /**
     * 拒绝原因
     */
    @TableField("REFUSED_REASON")
    private String refusedReason;

}
