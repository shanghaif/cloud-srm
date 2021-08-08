package com.midea.cloud.srm.model.supplier.riskraider.r7.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7FinancialDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

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
@Accessors(chain = true)
public class R7FinancialDto extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 企业经营ID
     */
    private Long financialId;

    /**
     * 经营状况（注：只有监控企业财务接口有此字段）
     */
    private String situation;

    /**
     * 评分（注：只有监控企业财务接口有此字段）
     */
    private String score;

    /**
     * 资产总额评价
     */
    private String totalAssetsAssessment;

    /**
     * 负债总额评价
     */
    private String totalLiabilitiesAssessment;

    /**
     * 营业总收入评价
     */
    private String totalRevenueAssessment;

    /**
     * 净利润评价
     */
    private String netProfitAssessment;

    /**
     * 纳税总额评价
     */
    private String totalAmountOfTaxPaymentAssessment;

    /**
     * 资产负债率评价
     */
    private String assetLiabilityRatioAssessment;

    /**
     * 净利润率评价
     */
    private String netProfitMarginAssessment;

    /**
     * 资产净利润率评价
     */
    private String netProfitRateOfAssetsAssessment;

    /**
     * 总资产周转率评价
     */
    private String turnoverOfTotalAssetsAssessment;

    /**
     * 分析结论
     */
    private String summary;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编号
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    private List<R7FinancialDetail> companyFinanceDetailList;


}
