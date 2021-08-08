package com.midea.cloud.srm.inq.quote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.inq.quote.domain.EffectiveQuoteItemsResult;
import com.midea.cloud.srm.inq.quote.mapper.QuoteItemMapper;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemService;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.inq.quote.service.IQuoteSelectionService;
import com.midea.cloud.srm.inq.quote.service.impl.convertor.QuoteTrackingDetailConvertor;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteTrackingItemsResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  报价-报价信息行表 服务实现类
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
@Service
public class QuoteItemServiceImpl extends ServiceImpl<QuoteItemMapper, QuoteItem> implements IQuoteItemService {

    @Autowired
    private IQuoteLadderPriceService iQuoteLadderPriceService;
    @Autowired
    private IQuoteSelectionService iQuoteSelectionService;

    @Override
    public PageInfo<QuoteTrackingItemsResponseDTO> trackingDetail(Long quoteId, Integer pageNum, Integer pageSize) {

        QueryWrapper<QuoteItem> wrapper = new QueryWrapper<>();
        wrapper.eq("QUOTE_ID", quoteId);

        /*查询报价物料*/
        PageUtil.startPage(pageNum, pageSize);
        List<QuoteItem> items = list(wrapper);

        List<QuoteLadderPrice> quoteLadderPrices = null;
        List<QuoteSelection> quoteItemSelections = null;
        if (CollectionUtils.isNotEmpty(items)) {
            //阶梯价报价行
            ArrayList<Long> quoteItemIds = new ArrayList<>();
            //所有的报价行
            ArrayList<Long> allquoteItemIds = new ArrayList<>();
            items.forEach(quoteItem -> {
                /*是否为阶梯价*/
                if (YesOrNo.YES.getValue().equalsIgnoreCase(quoteItem.getIsLadder())) {
                    quoteItemIds.add(quoteItem.getQuoteItemId());
                }
                allquoteItemIds.add(quoteItem.getQuoteItemId());
            });
            /*查询阶梯价*/
            if (CollectionUtils.isNotEmpty(quoteItemIds)) {
                quoteLadderPrices = iQuoteLadderPriceService.getLadderPrice(quoteItemIds);
            }
            /*查询报价选定信息*/
            quoteItemSelections = iQuoteSelectionService.getQuoteSelectionByQuoteItemId(allquoteItemIds);

        }

        return QuoteTrackingDetailConvertor.convert(items, quoteLadderPrices, quoteItemSelections);
    }

    @Override
    public List<EffectiveQuoteItemsResult> getEffectiveQuoteItems(Long inquiryId) {
        return this.baseMapper.effrcticeQuoteItems(inquiryId);
    }

    @Override
    public List<QuoteItem> getQuoteItemByQuteIds(List<Long> quoteIds) {

        QueryWrapper<QuoteItem> wrapper = new QueryWrapper<>();
        wrapper.in("QUOTE_ID", quoteIds);
        return list(wrapper);
    }

    @Override
    public List<QuoteItem> getQuoteItemByQuteItemIds(List<Long> quoteItemIds) {
        QueryWrapper<QuoteItem> wrapper = new QueryWrapper<>();
        wrapper.in("QUOTE_ITEM_ID", quoteItemIds);
        return list(wrapper);
    }
}
