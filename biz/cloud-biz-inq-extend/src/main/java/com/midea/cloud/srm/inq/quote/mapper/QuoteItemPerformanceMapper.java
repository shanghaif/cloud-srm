package com.midea.cloud.srm.inq.quote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteItemPerformanceQueryResult;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItemPerformance;

import java.util.List;

/**
 * <p>
 * 报价-供应商物料绩效评分表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-03-27
 */
public interface QuoteItemPerformanceMapper extends BaseMapper<QuoteItemPerformance> {

    /**
     * 查询供应商物料绩效评分
     */
    List<QuoteItemPerformanceQueryResult> getQuoteItemPerformances(Long inquiryId);
}
