package com.midea.cloud.srm.model.base.formula.dto.update;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
 *  修改日期: 2020/8/31 8:45
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialUpdateDto {
    @NotNull(message = "基材id不能为空")
    private Long baseMaterialId;

    private String baseMaterialName;
    /**
     * 基材类型
     */
    private String baseMaterialType;

    /**
     * 基材状态
     */
    private String baseMaterialStatus;
    /**
     * 是否海鲜价
     */
    private String seaFoodPrice;
    /**
     * 基材单位
     */
    private String baseMaterialUnit;
    /**
     * 计算方式
     */
    private String baseMaterialCalculateType;
}
