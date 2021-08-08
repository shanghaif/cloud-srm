package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-10 8:52
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalLadderPriceDTO {

    /**
     * 报价起始数量
     */
    private BigDecimal beginQuantity;

    /**
     * 报价结束数量
     */
    private BigDecimal endQuantity;

    /**
     * 未税单价
     */
    private BigDecimal price;
}