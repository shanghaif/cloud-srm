package com.midea.cloud.srm.model.inq.price.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  价格目录阶梯价添加参数
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-15 11:14
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryLadderPriceAddParam {

    /**
     * 阶梯价id
     */
    private Long priceLadderPriceId;

    /**
     * 报价起始数量
     */
    private BigDecimal beginQuantity;

    /**
     * 报价结束数量
     */
    private BigDecimal endQuantity;

    /**
     * 单价
     */
    private BigDecimal price;
}