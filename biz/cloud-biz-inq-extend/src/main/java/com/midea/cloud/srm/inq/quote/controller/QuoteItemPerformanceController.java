package com.midea.cloud.srm.inq.quote.controller;

import com.midea.cloud.srm.inq.quote.service.IQuoteItemPerformanceService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceSaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  报价-供应商物料绩效评分表 前端控制器
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
@RestController
@RequestMapping("/quote/performance")
public class QuoteItemPerformanceController extends BaseController {

    @Autowired
    private IQuoteItemPerformanceService iQuoteItemPerformanceService;

    /**
     * 获取绩效评分
     */
    @GetMapping
    public List<QuoteItemPerformanceResponseDTO> getQuoteItemPerformance(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单ID不能为空");
        return iQuoteItemPerformanceService.getQuoteItemPerformance(inquiryId);
    }

    /**
     * 保存物料得分
     */
    @PutMapping
    public void saveQuoteItemPerformance(@RequestBody QuoteItemPerformanceSaveDTO request) {
        Assert.notNull(request.getInquiryId(), "询价单ID不能为空");
        Assert.notNull(request.getQuoteItemPerformances(), "保存评分不能为空");
        iQuoteItemPerformanceService.saveQuoteItemPerformance(request);
    }

    /**
     * 更新物料得分

    @PostMapping
    public void updateScore(@RequestBody QuoteItemPerformanceUpdateDTO request) {
        Assert.notNull(request.getInquiryId(), "询价单ID不能为空");
        iQuoteItemPerformanceService.updateSocre(request);
    }*/


}
