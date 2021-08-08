package com.midea.cloud.srm.model.inq.quote.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  报价计算对象
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-25 9:11
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemCalculation {

    /**
     * 报价行id
     */
    private Long quoteItemId;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 报价差价
     */
    private BigDecimal quotePriceDifference;

    /**
     * 现价差价
     */
    private BigDecimal currentPriceDifference;

}