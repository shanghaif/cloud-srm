package com.midea.cloud.srm.inq.quote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteCancelDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteRollbackDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteTrackingQueryResponseDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteHeader;

/**
*  <pre>
 *  报价-报价信息头表 服务类
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
public interface IQuoteHeaderService extends IService<QuoteHeader> {

    /**
     * 保存报价信息
     */
    Long saveQuote(QuoteHeaderDto dto);

    /**
     * 根据询价单查询报价单信息
     */
    QuoteTrackingQueryResponseDTO tracking(Long inquiryId);

    /**
     * 作废报价
     */
    void cancelQuote(QuoteCancelDTO request);
    /**
     * 撤回报价
     * @param request
     */
    void rolllbackQuote(QuoteRollbackDTO request);
}
