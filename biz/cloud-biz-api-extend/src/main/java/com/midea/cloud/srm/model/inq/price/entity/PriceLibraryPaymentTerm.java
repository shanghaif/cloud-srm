package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/24 20:35
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_price_library_payment_term")
public class PriceLibraryPaymentTerm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 价格库付款条款id
     */
    @TableId("PRICE_LIBRARY_PAYMENT_TERM_ID")
    private Long priceLibraryPaymentTermId;

    /**
     * 价格库id
     */
    @TableField("PRICE_LIBRARY_ID")
    private Long priceLibraryId;

    /**
     * 付款条件
     */
    @TableField("PAYMENT_TERM")
    private String paymentTerm;

    /**
     * 付款方式
     */
    @TableField("PAYMENT_WAY")
    private String paymentWay;

    /**
     * 账期
     */
    @TableField("PAYMENT_DAY")
    private Integer paymentDay;
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

}
