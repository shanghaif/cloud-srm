package com.midea.cloud.srm.inq.quote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceSaveDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceUpdateDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItemPerformance;

import java.util.List;

/**
*  <pre>
 *  报价-供应商物料绩效评分表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 10:09:27
 *  修改内容:
 * </pre>
*/
public interface IQuoteItemPerformanceService extends IService<QuoteItemPerformance> {

    /**
     * 根据物料id，供应商id和组织id获取绩效分数
     */
    QuoteItemPerformance getDimensionScore(Long inquiryId, Long organizationId, Long vendorId, Long itemId);

    /**
     * 查询绩效评分
     */
    List<QuoteItemPerformanceResponseDTO> getQuoteItemPerformance(Long inquiryId);

    /**
     * 保存物料绩效评分
     */
    void saveQuoteItemPerformance(QuoteItemPerformanceSaveDTO request);

    /**
     * 更新物料绩效评分
     */
    void updateSocre(QuoteItemPerformanceUpdateDTO request);

    /**
     * 根据询价单号查询绩效
     */
    List<QuoteItemPerformance> getDimensionByInquiryNo(String inquiryNo);
}
