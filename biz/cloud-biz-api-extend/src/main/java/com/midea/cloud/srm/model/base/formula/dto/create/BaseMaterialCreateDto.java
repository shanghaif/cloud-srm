package com.midea.cloud.srm.model.base.formula.dto.create;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

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
 *  修改日期: 2020/8/27 0:24
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialCreateDto {

    /**
     * 基材名称
     */
    @NotEmpty(message = "基材名称不能为空")
    private String baseMaterialName;

    /**
     * 基材类型
     */
    private String baseMaterialType;
    /**
     * 是否海鲜价
     */
    @NotEmpty(message = "是否海鲜价不能为空")
    private String seaFoodPrice;



    /**
     * 计算方式
     */
    private String baseMaterialCalculateType;

    /**
     * 基材单位
     */
    @NotEmpty(message = "基材单位不能为空")
    private String baseMaterialUnit;
}
