package com.midea.cloud.srm.inq.quote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.inq.quote.domain.EffectiveQuoteItemsResult;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteTrackingItemsResponseDTO;

import java.util.List;

/**
*  <pre>
 *  报价-报价信息行表 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 14:51:59
 *  修改内容:
 * </pre>
*/
public interface IQuoteItemService extends IService<QuoteItem> {

    /**
     * 查看报价分页查询
     */
    PageInfo<QuoteTrackingItemsResponseDTO> trackingDetail(Long quoteId, Integer pageNum, Integer pageSize);

    /**
     * 获取有效报价行
     */
    List<EffectiveQuoteItemsResult> getEffectiveQuoteItems(Long inquiryId);

    /**
     * 批量根据报价头获取报价行
     */
    List<QuoteItem> getQuoteItemByQuteIds(List<Long> quoteIds);

    /**
     * 批量获取报价行
     */
    List<QuoteItem> getQuoteItemByQuteItemIds(List<Long> quoteItemIds);
}
