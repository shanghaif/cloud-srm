package com.midea.cloud.srm.model.supplierauth.review.entity;

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
 *  品类日志表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-08 11:11:26
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_auth_cate_journal")
public class CateJournal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID,品类日志ID
     */
    @TableId("CATEGORY_JOURNAL_ID")
    private Long categoryJournalId;

    /**
     * 品类日志单据类型
     */
    @TableField("FORM_TYPE")
    private String formType;

    /**
     * 品类日志单据ID
     */
    @TableField("FORM_ID")
    private Long formId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类服务状态
     */
    @TableField("CATEGORY_SERVICE_STATUS")
    private String categoryServiceStatus;

    /**
     * 品类全路径ID
     */
    @TableField("CATEGORY_FULL_ID")
    private String categoryFullId;

    /**
     * 品类全路径名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 品类本年度采购金额
     */
    @TableField("THIS_YEAR_AMOUNT")
    private BigDecimal thisYearAmount;

    /**
     * 生效时间
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效时间
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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
