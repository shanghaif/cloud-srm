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
 *  修改日期: 2020-4-7 9:08
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemPerformanceSaveDTO {

    /**
     * 询价单号
     */
    private Long inquiryId;

    /**
     * 物料评分
     */
    private List<QuoteItemPerformanceDTO> quoteItemPerformances;
}