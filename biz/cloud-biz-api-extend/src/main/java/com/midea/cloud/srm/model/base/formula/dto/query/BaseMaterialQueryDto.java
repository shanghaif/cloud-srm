package com.midea.cloud.srm.model.base.formula.dto.query;

import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/31 8:48
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialQueryDto {
    /**
     * 基材名称
     */
    private String baseMaterialName;

    /**
     * 基材类型
     */
    private String baseMaterialType;
    /**
     * 是否海鲜价
     */
    private String seaFoodPrice;

    /**
     * 基材状态
     */
    private String baseMaterialStatus;

    /**
     * 计算方式
     */
    private String baseMaterialCalculateType;

    /**
     * 基材单位
     */
    private String baseMaterialUnit;

    private Integer pageNum;

    private Integer pageSize;
}
