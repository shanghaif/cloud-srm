package com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  送货单WMS清单 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:11:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_delivery_note_wms")
public class DeliveryNoteWms extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "DELIVERY_NOTE_WMS_ID")
    private Long deliveryNoteWmsId;

    /**
     * 送货单ID
     */
    @TableField("DELIVERY_NOTE_ID")
    private Long deliveryNoteId;

    /**
     * 订单明细ID
     */
    @TableField("ORDER_DETAIL_ID")
    private Long orderDetailId;

    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

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
     * 批次号
     */
    @TableField("BATCH_NUM")
    private String batchNum;
    /**
     * 浆料型号
     */
    @TableField("PASTE_TYPE")
    private String pasteType;
    /**
     * 制造商
     */
    @TableField("MANUFACTURER")
    private String manufacturer;

    /**
     * 托盘号
     */
    @TableField("CONSIGNMENT_NUM")
    private String consignmentNum;
    /**
     * 大托号
     */
    @TableField("BIG_CONSIGNMENT_NUM")
    private String bigConsignmentNum;
    /**
     * 箱号
     */
    @TableField("BOX_NUM")
    private String boxNum;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 本次送货数量
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 生产日期
     */
    @TableField("PRODUCE_DATE")
    private Date produceDate;

    /**
     * 有效日期
     */
    @TableField("EFFECTIVE_DATE")
    private Date effectiveDate;

    /**
     * 承诺到货日期
     */
    @TableField("PLAN_RECEIVE_DATE")
    private Date planReceiveDate;

    /**
     * 明细备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 到货计划详情ID
     */
    @TableField("CEEA_ARRIVAL_DETAIL_ID")
    private Long ceeaArrivalDetailId;

    /**
     * 到货计划号
     */
    @TableField("CEEA_ARRIVAL_NUMBER")
    private String ceeaArrivalNumber;

    /**
     * 订单数量
     */
    @TableField("ORDER_NUM")
    private BigDecimal orderNum;

    /**
     * 订单累计收货量
     */
    @TableField("RECEIVE_SUM")
    private BigDecimal receiveSum;

    /**
     * 租户ID
     */
    @TableField(value = "TENANT_ID",fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 版本号
     */
    @TableField(value = "VERSION",fill = FieldFill.INSERT_UPDATE)
    private Long version;

    /**
     * 送货单明细ID
     */
    @TableField("DELIVERY_NOTE_DETAIL_ID")
    private Long deliveryNoteDetailId;


}
