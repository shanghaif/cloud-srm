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
 *  修改日期: 2020/8/26 22:33
 *  修改内容:
 * </pre>
 */
@Data
public class PricingFormulaQueryDto {

    private String pricingFormulaStatus;

    private String pricingFormulaDesc;

    private String pricingFormulaName;

    private int pageSize;

    private int pageNum;

}
