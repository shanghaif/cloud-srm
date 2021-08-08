package com.midea.cloud.srm.model.supplier.info.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 * 异常跟踪
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
@Data
public class AssesFormDto extends BaseDTO {

    /**
     * 供应商考核单ID
     */
    private Long vendorAssesId;
    /**
     * 业务实体ID
     */
    private Long organizationId;

    /**
     * 业务实体编码
     */
    private String organizationCode;

    /**
     * 业务实体名称
     */
    private String organizationName;

    /**
     * 采购分类id
     */
    private Long categoryId;

    /**
     * 采购分类编码
     */
    private String categoryCode;

    /**
     * 采购分类名称
     */
    private String categoryName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 指标维度(1-品质，2-服务，3-交付，4-成本，5-技术)
     */
    private String indicatorDimension;

    /**
     * 指标
     */
    private String indicatorName;

    /**
     * 说明
     */
    private String indicatorLineDes;

    /**
     * 实际考核金额(含税)
     */
    private BigDecimal actualAssessmentAmountY;

    /**
     * 实际考核金额(不含税)
     */
    private BigDecimal actualAssessmentAmountN;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 考核单状态(DRAFT-拟定,IN_FEEDBACK-反馈中,WITHDRAWN-已撤回,OBSOLETE-已废弃,ASSESSED-已考核)
     */
    private String status;
}
