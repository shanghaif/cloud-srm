package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

/**
 * <pre>
 *  价格审批单中标行-付款条款
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/24 16:21
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_price_approval_bidding_item_payment_term")
public class ApprovalBiddingItemPaymentTerm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 中标行付款条款id
     */
    @TableId("APPROVAL_BIDDING_ITEM_PAYMENT_TERM_ID")
    private Long approvalBiddingItemPaymentTermId;

    /**
     * 中标行id
     */
    @TableField("APPROVAL_BIDDING_ITEM_ID")
    private Long approvalBiddingItemId;

    /**
     * 付款条件
     */
    @TableField("PAYMENT_TERM")
    @NotEmpty(message = "付款条件不能为空")
    private String paymentTerm;

    /**
     * 付款方式
     */
    @TableField("PAYMENT_WAY")
    @NotEmpty(message = "付款方式不能为空")
    private String paymentWay;

    /**
     * 账期
     */
    @TableField("PAYMENT_DAY")
    @NotNull(message = "账期不能为空")
    private Integer paymentDay;

    /**
     * 账期编码
     */
    @TableField("PAYMENT_DAY_CODE")
    @NotEmpty(message = "账期编码不能为空")
    private String paymentDayCode;
    /**
     * 付款阶段(字典)（付款条件）
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;
    /**
     * 付款比例
     */
    @TableField("PAYMENT_RATIO")
    private BigDecimal paymentRatio;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ApprovalBiddingItemPaymentTerm that = (ApprovalBiddingItemPaymentTerm) o;
        return Objects.equals(paymentTerm, that.paymentTerm) &&
                Objects.equals(paymentWay, that.paymentWay) &&
                Objects.equals(paymentDayCode, that.paymentDayCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), approvalBiddingItemPaymentTermId, approvalBiddingItemId, paymentTerm, paymentWay, paymentDay, paymentDayCode, createdId, createdBy, creationDate, createdByIp);
    }
}
