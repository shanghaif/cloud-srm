package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.IBidingResultService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service.IRoundService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.IEvaluationService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.entity.BidResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.param.BidResultParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.AbandonmentBidSupVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplateReportLineVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * 招标结果页 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-09 9:50:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidingResult")
public class BidingResultController extends BaseController {

    @Autowired
    private IBidingResultService iBidingResultService;
    @Autowired
    private IEvaluationService iEvaluationService;
    @Autowired
    private IRoundService iRoundService;
    @Autowired
    private IEvaluationService evaluationService;
    @Autowired
    private BaseClient baseClient;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/listPageBidingResult")
    public PageInfo<BidResult> listPageBidingResult(@RequestBody BidResultParam bidResultParam) {
        return iBidingResultService.listPageBidingResult(bidResultParam.getBidingId());
    }

    /**
     * 投标结果
     *
     * @return
     */
    @GetMapping("/getResultByBidingId")
    public List<BidResult> getResultByBidingId(@RequestParam Long bidingId) {
        return iBidingResultService.getResultByBidingId(bidingId);
    }

    /**
     * 新的投标结果接口
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/getNewResultByBidingId")
    public PageInfo<EvaluationResult> getNewResultByBidingId(@RequestBody Map<String,Object> paramMap) {
        return iBidingResultService.getNewResultByBidingId(paramMap);
    }


    /**
     * 修改
     *
     * @param bidResultList
     */
    @PostMapping("/modify")
    public void modify(@RequestBody List<BidResult> bidResultList) {
        iBidingResultService.updateBidResultBatchById(bidResultList);
    }

    /**
     * 生成价格审批单
     * @return
     */
    @GetMapping("/generatePriceApproval")
    public void generatePriceApproval(Long bidingId) {
        iEvaluationService.generatePriceApproval(bidingId);
    }

    /**
     * 公示招标结果
     *
     * @param bidingId
     */
    @GetMapping("/publicResult")
    public void publicResult(Long bidingId) {
        iRoundService.publicResult(bidingId);
    }

    /**
     * 获取报名但没有投标的供应商
     */
    @GetMapping("/getAbandonBidSup")
    public List<AbandonmentBidSupVO> getAbandonBidSup(@RequestParam Long bidingId) {
        return iBidingResultService.getAbandonBidSup(bidingId);
    }

    /**
     * 获取根据模型报价的报表信息
     */
    @PostMapping("/generateTemplatePriceReport")
    public List<Map<String, Object>> getTemplateReport(@RequestBody EvaluationResult result) {
        List<BidOrderLineTemplateReportLineVO> bidOrderLineTemplateReportLineVOS = evaluationService.generateTemplatePriceReport(result);
        List<Map<String, Object>> results = null;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        //报价行币
        String currencyCode = result.getCurrencyCode();
        //本位币
        String standardCurrency = result.getStandardCurrency();
        BigDecimal rate = BigDecimal.ONE;
        if (!Objects.equals(currencyCode, standardCurrency)) {
            rate = baseClient.getRateByFromTypeAndToType(currencyCode, standardCurrency);
        }
        if (CollectionUtils.isEmpty(bidOrderLineTemplateReportLineVOS)) {
            Map<Long, List<EvaluationResult>> collect = evaluationService.findEvaluationResults(EvaluationQueryParam.builder()
                    .bidingId(result.getBidingId())
                    .requirementLineId(result.getRequirementLineId())
                    .round(result.getRound())
                    .build()).stream().collect(Collectors.groupingBy(EvaluationResult::getRequirementLineId));
            results = new LinkedList<>();
            for (Map.Entry<Long, List<EvaluationResult>> map : collect.entrySet()) {
                Map<String, Object> temp = new HashMap<>(map.getValue().size());
                for (EvaluationResult evaluationResult : map.getValue()) {
                    BigDecimal price = evaluationResult.getPrice().multiply(rate);
                    temp.put(evaluationResult.getVendorName(),
                            String.format("币种编号:[%s]，含税单价：[%s]，中标总金额：[%s]",standardCurrency,df.format(price),df.format(Optional.ofNullable(evaluationResult.getQuotaQuantity()).orElse(BigDecimal.ZERO).multiply(price))));
                }
                results.add(temp);
            }
            return results;
        }

        Map<String, Object> totalMap = new LinkedHashMap<>(bidOrderLineTemplateReportLineVOS.size() + 2);
        totalMap.put("templateHeaderDesc", "");
        totalMap.put("templateLineDesc", "");
        results = bidOrderLineTemplateReportLineVOS.stream().map(e -> {
            Map<String, Object> row = new LinkedHashMap<>(e.getPrice().size() + 2);
            row.put("templateHeaderDesc", e.getHeaderName());
            row.put("templateLineDesc", e.getLineName());
            e.getPrice().forEach(q -> {
                Object o = totalMap.get(q.getVendorName());
                if (Objects.isNull(o)) {
                    totalMap.put(q.getVendorName(), q.getTaxPrice());
                } else {
                    BigDecimal temp = (BigDecimal) o;
                    temp = temp.add(q.getTaxPrice());
                    totalMap.put(q.getVendorName(), temp);
                }
                row.put(q.getVendorName(), df.format(q.getTaxPrice()));
            });
            return row;
        }).collect(Collectors.toList());
        int count = 0;
        for (Map.Entry<String, Object> entry : totalMap.entrySet()) {
            if (++count > 2) {
                BigDecimal value = (BigDecimal) entry.getValue();
                entry.setValue(df.format(value.multiply(rate)));
            }
        }
        results.add(totalMap);
        return results;
    }

    @GetMapping("/calculateQuota")
    public List<EvaluationResult> calculateQuota(@RequestParam Long biddingId) {
        return iEvaluationService.calculateQuotaResult(biddingId);
    }
}
