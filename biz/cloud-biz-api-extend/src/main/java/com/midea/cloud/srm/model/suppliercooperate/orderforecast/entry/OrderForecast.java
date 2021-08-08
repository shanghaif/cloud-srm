package com.midea.cloud.srm.model.suppliercooperate.orderforecast.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.BaseErrorCell;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  三月滚动预测 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/14 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_order_forecast")
public class OrderForecast extends BaseErrorCell {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_FORECAST_ID")
    private Long orderForecastId;

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
     * 计划月
     */
    @TableField("PLAN_MONTH")
    private String planMonth;

    /**
     * 版本号
     */
    @TableField("PLAN_VERSION")
    private String planVersion;

    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;


    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 计划月后第一个月预测量
     */
    @TableField("FIRST_MONTH")
    private BigDecimal firstMonth;

    /**
     * 计划月后第二个月预测量
     */
    @TableField("SECOND_MONTH")
    private BigDecimal secondMonth;

    /**
     * 计划月后第三个月预测量
     */
    @TableField("THREE_MONTH")
    private BigDecimal threeMonth;


    /**
     * 确认人ID
     */
    @TableField("COMFIRM_ID")
    private Long comfirmId;

    /**
     * 确认人
     */
    @TableField("COMFIRM_BY")
    private String comfirmBy;

    /**
     * 确认时间
     */
    @TableField("COMFIRM_TIME")
    private Date comfirmTime;

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
     * 版本号PAYMENT_AMOUNT
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}