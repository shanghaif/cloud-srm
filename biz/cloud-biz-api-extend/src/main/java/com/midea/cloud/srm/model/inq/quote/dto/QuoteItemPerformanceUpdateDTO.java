package com.midea.cloud.srm.model.inq.quote.dto;

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
 *  修改日期: 2020-4-7 10:25
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemPerformanceUpdateDTO {

    /**
     * 询价单号
     */
    private Long inquiryId;

    /**
     * 评分列表
     */
    private List<QuoteItemPerformanceScoreDTO> quoteItemPerformanceScores;

}