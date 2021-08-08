package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

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
 *  修改日期: 2020-4-13 11:20
 *  修改内容:
 * </pre>
 */
@Data
public class ItemTargetPriceDTO {

    /**
     * 寻价行id
     */
    private Long inquiryItemId;

    /**
     * 未税目标价
     */
    private String notaxTargrtPrice;
}