package com.midea.cloud.srm.model.base.formula.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/12/7 16:15
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class FormulaPriceDTO {
    private String orgName;

    private String materialItemDesc;

    private String content;

    /**
     * 数据类型
     * {@link com.midea.cloud.common.enums.base.FormulaPriceExportTypeEnum }
     */
    private String type;

    /**
     * 基材名称
     */
    private String baseMaterialName;

    /**
     * 基材价格
     */
    private String baseMaterialPrice;
}
