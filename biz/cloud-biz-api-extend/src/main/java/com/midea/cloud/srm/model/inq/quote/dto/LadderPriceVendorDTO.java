package com.midea.cloud.srm.model.inq.quote.dto;

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
 *  修改日期: 2020-4-1 15:26
 *  修改内容:
 * </pre>
 */
@Data
public class LadderPriceVendorDTO {

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;
}