package com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry;

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
 *  送货单明细表 模型
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
@TableName("scc_sc_delivery_note_detail")
public class DeliveryNoteDetail extends BaseErrorCell {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("DELIVERY_NOTE_DETAIL_ID")
    private Long deliveryNoteDetailId;

    /**
     * 行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

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
     * 送货数量(本次送货数量)
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 收货数量
     */
    @TableField("RECEIVED_NUM")
    private BigDecimal receivedNum;

    /**
     * 不良数量
     */
    @TableField("BAD_NUM")
    private BigDecimal badNum;

    /**
     * 差异数量
     */
    @TableField("DIFFERENCE_NUM")
    private BigDecimal differenceNum;

    /**
     * 实退数量(退货数量)
     */
    @TableField("ACTUAL_RETURNED_NUM")
    private BigDecimal actualReturnedNum;

    /**
     * 补货数量
     */
    @TableField("REPLENISH_NUM")
    private BigDecimal replenishNum;

    /**
     * 扣款数量
     */
    @TableField("DEDUCTION_NUM")
    private BigDecimal deductionNum;

    /**
     * 收货时间
     */
    @TableField("RECEIVED_TIME")
    private Date receivedTime;

    /**
     * 不良原因
     */
    @TableField("BAD_REASON")
    private String badReason;

    /**
     * 送货通知单ID
     */
    @TableField("DELIVERY_NOTICE_ID")
    private Long deliveryNoticeId;

    /**
     * 送货通知单号
     */
    @TableField("DELIVERY_NOTICE_NUM")
    private String deliveryNoticeNum;

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
     * 批次号
     */
    @TableField("CEEA_BATCH_NUM")
    private String ceeaBatchNum;

    /**
     * 到货计划明细ID
     */
    @TableField("CEEA_ARRIVAL_DETAIL_ID")
    private Long ceeaArrivalDetailId;

    /**
     * 入库数量
     */
    @TableField("WAREHOUSE_QUANTITY")
    private BigDecimal warehouseQuantity;

    /**
     * 订单来源
     * {{@link com.midea.cloud.common.enums.supcooperate.DeliveryNoteSource}}
     */
    @TableField("orderSource")
    private String orderSource;

}
