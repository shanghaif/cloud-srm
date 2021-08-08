package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <pre>
 * 绩效信息
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
public class PerfOverallScoreDto extends BaseDTO {

    /**
     * 主键ID
     */
    private Long overallScoreId;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类code
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类全称
     */
    private String categoryFullName;

    /**
     * 绩效开始月份
     */
    private LocalDate perStartMonth;

    /**
     * 绩效结束月份
     */
    private LocalDate perEndMonth;

    /**
     * 综合得分
     */
    private BigDecimal score;

    /**
     * 品质得分
     */
    private BigDecimal scoreAttribute1;

    /**
     * 成本得分
     */
    private BigDecimal scoreAttribute2;

    /**
     * 交付得分
     */
    private BigDecimal scoreAttribute3;

    /**
     * 服务得分
     */
    private BigDecimal scoreAttribute4;

    /**
     * 技术得分
     */
    private BigDecimal scoreAttribute5;

    /**
     * 绩效排名总数
     */
    private Integer indicatorCount;

    /**
     * 绩效等级
     */
    private String levelName;

}
