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
 *  扣罚&派利明细 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 17:39:56
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_invoice_punish")
public class InvoicePunish extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,扣罚返利明细ID
     */
    @TableId("INVOICE_PUNISH_ID")
    private Long invoicePunishId;

    /**
     * 开票通知ID
     */
    @TableField("INVOICE_NOTICE_ID")
    private Long invoiceNoticeId;

    /**
     * 供应商考核单ID
     */
    @TableField("VENDOR_ASSES_ID")
    private Long vendorAssesId;

    /**
     * 考核单号
     */
    @TableField("ASSESSMENT_NO")
    private String assessmentNo;

    /**
     * 考核时间
     */
    @TableField("ASSESSMENT_DATE")
    private LocalDate assessmentDate;

    /**
     * 考核类型
     */
    @TableField("ASSESSMENT_TYPE")
    private String assessmentType;

    /**
     * 实际考核金额(含税)
     */
    @TableField("ACTUAL_ASSESSMENT_AMOUNT_Y")
    private BigDecimal actualAssessmentAmountY;

    /**
     * 实际考核金额(不含税)
     */
    @TableField("ACTUAL_ASSESSMENT_AMOUNT_N")
    private BigDecimal actualAssessmentAmountN;

    /**
     * 税额
     */
    @TableField("TAX")
    private BigDecimal tax;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 采购分类id(物料分类id)
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码(物料分类编码)
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类名称(物料分类名称)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料名称
     */
    @TableField("ITEM_NAME")
    private String itemName;

    /**
     * 备注
     */
    @TableField("COMMENT")
    private String comment;

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
     * 是否引用(是:Y,否:N)
     */
    @TableField("IF_QUOTE")
    private String ifQuote;

}
