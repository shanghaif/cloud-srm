package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

/**
 * <pre>
 *  订单付款条款
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/13 11:50
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_order_payment_provision")
public class OrderPaymentProvision extends BaseErrorCell {
    private static final long serialVersionUID = 4931212722953560788L;
    /**
     * 订单付款条款id
     */
    @TableId("ORDER_PAYMENT_PROVISION_ID")
    private Long orderPaymentProvisionId;

    /**
     * 订单id
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 付款期数
     */
    @TableField("PAYMENT_PERIODS_NUMBER")
    private Integer paymentPeriodsNumber;

    /**
     * 付款条件
     */
    @TableField("PAYMENT_TERM")
    private String paymentTerm;

    /**
     * 付款账期
     */
    @TableField("PAYMENT_PERIOD")
    private String paymentPeriod;

    /**
     * 付款比例
     */
    @TableField("PAYMENT_RADIO")
    private BigDecimal paymentRadio;

    /**
     * 付款方式
     */
    @TableField("PAYMENT_WAY")
    private String paymentWay;

    /**
     * 付款阶段
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;

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

    @TableField("TENANT_ID")
    private Long tenantId;

    @TableField("VERSION")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPaymentProvision that = (OrderPaymentProvision) o;
        return Objects.equals(paymentTerm, that.paymentTerm) &&
                Objects.equals(paymentPeriod, that.paymentPeriod) &&
                Objects.equals(paymentWay, that.paymentWay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paymentTerm, paymentPeriod, paymentWay);
    }
}
