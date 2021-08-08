package com.midea.cloud.srm.model.supplier.riskraider.r7.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  企业财务信息表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_r7_financial")
public class R7Financial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 企业经营ID
     */
    @TableId("FINANCIAL_ID")
    private Long financialId;

    /**
     * 经营状况（注：只有监控企业财务接口有此字段）
     */
    @TableField("SITUATION")
    private String situation;

    /**
     * 评分（注：只有监控企业财务接口有此字段）
     */
    @TableField("SCORE")
    private String score;

    /**
     * 资产总额评价
     */
    @TableField("TOTAL_ASSETS_ASSESSMENT")
    private String totalAssetsAssessment;

    /**
     * 负债总额评价
     */
    @TableField("TOTAL_LIABILITIES_ASSESSMENT")
    private String totalLiabilitiesAssessment;

    /**
     * 营业总收入评价
     */
    @TableField("TOTAL_REVENUE_ASSESSMENT")
    private String totalRevenueAssessment;

    /**
     * 净利润评价
     */
    @TableField("NET_PROFIT_ASSESSMENT")
    private String netProfitAssessment;

    /**
     * 纳税总额评价
     */
    @TableField("TOTAL_AMOUNT_OF_TAX_PAYMENT_ASSESSMENT")
    private String totalAmountOfTaxPaymentAssessment;

    /**
     * 资产负债率评价
     */
    @TableField("ASSET_LIABILITY_RATIO_ASSESSMENT")
    private String assetLiabilityRatioAssessment;

    /**
     * 净利润率评价
     */
    @TableField("NET_PROFIT_MARGIN_ASSESSMENT")
    private String netProfitMarginAssessment;

    /**
     * 资产净利润率评价
     */
    @TableField("NET_PROFIT_RATE_OF_ASSETS_ASSESSMENT")
    private String netProfitRateOfAssetsAssessment;

    /**
     * 总资产周转率评价
     */
    @TableField("TURNOVER_OF_TOTAL_ASSETS_ASSESSMENT")
    private String turnoverOfTotalAssetsAssessment;

    /**
     * 分析结论
     */
    @TableField("summary")
    private String summary;

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


}
