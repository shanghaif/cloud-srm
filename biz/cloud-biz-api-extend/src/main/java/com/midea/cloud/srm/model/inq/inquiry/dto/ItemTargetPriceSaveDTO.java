package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

import java.util.List;

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
 *  修改日期: 2020-4-7 17:45
 *  修改内容:
 * </pre>
 */
@Data
public class ItemTargetPriceSaveDTO {

    /**
     * 询价单ID
     */
    private Long inquiryId;

    /**
     * 目标价
     */
    List<ItemTargetPriceDTO> itemTargetPrices;

}