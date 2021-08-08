package com.midea.cloud.srm.inq.quote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.quote.dto.LadderPriceCompareResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteSelectionQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;

import java.util.List;

/**
*  <pre>
 *  报价--物料阶梯价表 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 15:22:46
 *  修改内容:
 * </pre>
*/
public interface IQuoteLadderPriceService extends IService<QuoteLadderPrice> {

    /**
     * 根据报价物料批量获取阶梯价
     */
    List<QuoteLadderPrice> getLadderPrice(List<Long> quoteItemIds);

    /**
     * 阶梯价比价
     */
    List<LadderPriceCompareResponseDTO> compareLadderPrice(Long quoteItemId, List<QuoteSelectionQueryResponseDTO> selectionQueryResult);
}
