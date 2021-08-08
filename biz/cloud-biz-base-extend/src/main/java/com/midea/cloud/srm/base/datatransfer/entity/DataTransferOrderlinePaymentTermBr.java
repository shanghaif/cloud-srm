package com.midea.cloud.srm.base.datatransfer.entity;

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
*  <pre>
 *  供应商可选付款条款 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-01 19:52:59
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_orderline_payment_term_br")
public class DataTransferOrderlinePaymentTermBr extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款条款id
     */
    @TableId("PAYMENT_TERM_ID")
    private Long paymentTermId;

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
     * 更新人
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

    /**
     * 报价行Id
     */
    @TableField("ORDERLINE_ID")
    private Long orderlineId;

    /**
     * 账期编码
     */
    @TableField("PAYMENT_DAY_CODE")
    private String paymentDayCode;

    /**
     * 付款阶段
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;

    /**
     * 付款比例
     */
    @TableField("PAYMENT_RATIO")
    private Long paymentRatio;

    /**
     * 付款条款描述
     */
    @TableField("PAYMENT_COMMENTS")
    private String paymentComments;

    @TableField("CREATED_BY_CODE")
    private String createdByCode;


}
