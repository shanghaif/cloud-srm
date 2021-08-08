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
 *  修改日期: 2020-4-7 10:28
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemPerformanceScoreDTO {

    /**
     * 评分行id
     */
    private Long itemPerformanceId;

    /**
     * 评分
     */
    private BigDecimal score;

}