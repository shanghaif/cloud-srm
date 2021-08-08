package com.midea.cloud.srm.inq.quote.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.inq.quote.service.IQuoteHeaderService;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteTrackingQueryResponseDTO;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteTrackingItemsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  报价跟踪
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 09:49:07
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quote/tracking")
public class QuoteTrackingController extends BaseController {

    @Autowired
    private IQuoteHeaderService iQuoteHeaderService;
    @Autowired
    private IQuoteItemService iQuoteItemService;

    /**
     * 报价跟踪列表
     */
    @RequestMapping
    public QuoteTrackingQueryResponseDTO tracking(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单ID不能为空");
        return iQuoteHeaderService.tracking(inquiryId);
    }

    /**
     * 报价跟踪详情
     */
    @RequestMapping("/detail")
    public PageInfo<QuoteTrackingItemsResponseDTO> trackingDetail(Long quoteId, Integer pageNum, Integer pageSize) {
        Assert.notNull(quoteId, "报价单ID不能为空");
        return iQuoteItemService.trackingDetail(quoteId, pageNum, pageSize);
    }

}
