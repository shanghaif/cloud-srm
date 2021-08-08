package com.midea.cloud.srm.model.base.formula.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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
 *  修改日期: 2020/8/26 22:39
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum EssentialFactorFromType {
    MATERIAL_MAIN_DATA("MATERIAL_MAIN_DATA", "物料主数据"),
    BASE_MATERIAL_PRICE("BASE_MATERIAL_PRICE", "基材物料价格"),
    SUPPLIER_QUOTED_PRICE("SUPPLIER_QUOTED_PRICE", "报价");
    private String code;
    private String type;

    public static EssentialFactorFromType getByItemValue(String itemValue) {
        return Arrays.stream(EssentialFactorFromType.values())
                .filter(type -> itemValue .equals(type.getCode()))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find enum with itemValue [" + itemValue +  "]"));
    }
}
