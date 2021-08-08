package com.midea.cloud.srm.model.base.formula.dto.create;

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
 *  修改日期: 2020/9/2 11:37
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialFormulaRelateCreateDto {
    @NotNull(message = "物料id不能为空")
    private Long materialId;
    @NotNull(message = "公式id不能为空")
    private Long formulaId;
    /**
     * 物料code
     */
    @NotEmpty(message = "物料编码不能为空")
    private String materialCode;
    /**
     * 物料名称
     */
    @NotEmpty(message = "物料名称不能为空")
    private String materialName;
    /**
     * 规格型号
     */
        private String specification;
    /**
     * 品类id
     */
    @NotNull(message = "品类id不能为空")
    private Long categoryId;
    /**
     * 品类名
     */
    @NotEmpty(message = "品类名不能为空")
    private String categoryName;
    /**
     * 单位
     */
    @NotEmpty(message = "单位不能为空")
    private String unit;
    /**
     * 单位名
     */
    @NotEmpty(message = "单位名不能为空")
    private String unitName;
}
