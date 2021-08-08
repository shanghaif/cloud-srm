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
 *  经营信息质量控制表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:52:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_operation_equipment")
public class OperationEquipment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,经营信息设备ID
     */
    @TableId("OP_EQUIPMENT_ID")
    private Long opEquipmentId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 经营信息
     */
    @TableField("OP_INFO_ID")
    private Long opInfoId;

    /**
     * 设备类型
     */
    @TableField("EQUIPMENT_TYPE")
    private String equipmentType;

    /**
     * 设备名称
     */
    @TableField("EQUIPMENT_NAME")
    private String equipmentName;

    /**
     * 规格型号
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private String quantity;

    /**
     * 单位设备产能
     */
    @TableField("EQUIPMENT_CAPACITY")
    private String equipmentCapacity;

    /**
     * 生成厂家
     */
    @TableField("MANUFACTURER")
    private String manufacturer;

    /**
     * 已服役年限
     */
    @TableField("SERVICE_YEAR")
    private String serviceYear;

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


}
