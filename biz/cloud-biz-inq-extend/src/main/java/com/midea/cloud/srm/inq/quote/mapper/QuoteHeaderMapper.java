package com.midea.cloud.srm.inq.quote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.quote.domain.QuoteTrackingQueryResult;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteHeader;

import java.util.List;

/**
 * <p>
 * 报价-报价信息头表 Mapper 接口
 * </p>
 *
 * @author linxc6@meiCloud.com
 * @since 2020-03-23
 */
public interface QuoteHeaderMapper extends BaseMapper<QuoteHeader> {

    List<QuoteTrackingQueryResult> queryByInquiryId(Long inquiryId);
}
