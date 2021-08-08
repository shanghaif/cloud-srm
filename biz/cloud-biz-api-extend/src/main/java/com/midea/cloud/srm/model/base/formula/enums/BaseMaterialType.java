package com.midea.cloud.srm.model.base.formula.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
 *  修改日期: 2020/8/27 11:57
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum BaseMaterialType {
    MATERIAL_QUALITY("MATERIAL_QUALITY", "材艺"), CRAFT("CRAFT", "工艺"), COST("COST", "费用");
    private String code;
    private String type;
}
