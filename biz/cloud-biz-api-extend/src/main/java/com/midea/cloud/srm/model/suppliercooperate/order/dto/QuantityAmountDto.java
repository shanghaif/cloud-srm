package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20
 *  修改内容:
 * </pre>
 */
@Data
public class QuantityAmountDto implements Serializable {
    /**
     * 订单数量
     */
    private BigDecimal orderNum;

    /**
     * 单价（含税）
     */
    private BigDecimal unitPriceContainingTax;
}
