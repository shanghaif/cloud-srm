package com.midea.cloud.srm.model.suppliercooperate.invoice.entity;

import java.math.BigDecimal;
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
 *  税控发票表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-27 11:57:07
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_invoice_tax_control")
public class InvoiceTaxControl extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,税控发票ID
     */
    @TableId("TAX_CONTROL_ID")
    private Long taxControlId;

    /**
     * 开票通知ID
     */
    @TableField("INVOICE_NOTICE_ID")
    private Long invoiceNoticeId;

    /**
     * 税控机发票号
     */
    @TableField("INVOICE_NUMBER")
    private String invoiceNumber;

    /**
     * 开票日期
     */
    @TableField("INVOICE_DATE")
    private LocalDate invoiceDate;

    /**
     * 不含税金额
     */
    @TableField("AMOUNT_NO_TAX")
    private BigDecimal amountNoTax;

    /**
     * 购方名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /**
     * 销方名称
     */
    @TableField("SELLER_NAME")
    private String sellerName;

    /**
     * 附件上传ID
     */
    @TableField("FILE_UPLOAD_ID")
    private Long fileUploadId;

    /**
     * 附件上传名称
     */
    @TableField("FILE_UPLOAD_NAME")
    private String fileUploadName;

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
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
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
