package com.midea.cloud.srm.model.supplier.riskradar.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplier.riskradar.entity.*;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditDto;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * 风险雷达信息
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/5
 *  修改内容:
 * </pre>
 */
@Data
public class RiskRadarDto extends BaseDTO {
    /**
     * 风险评级
     */
    private RiskRating riskRating;

    /**
     * 财务状况(近两年数据)
     */
    private List<FinancialStatus> financialStatuses;

    /**
     * 财务状况评价
     */
    private FinancialAssess financialAssess;

    /**
     * 财务评级(近两年数据)
     */
    private List<FinancialRating> financialRating;

    /**
     * 财务评级评论
     */
    private FinancialRatRev financialRatRev;

    /**
     * 失信信息
     */
    private R8DiscreditDto r8DiscreditDto;
}
