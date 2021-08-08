package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.IEvaluationService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param.CreateFollowTenderParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <pre>
 * 评选
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: tanjl11@meicloud.com
 *  修改日期: 2020年9月9日 上午11:30:59
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private IEvaluationService iEvaluationService;
    @Autowired
    private IOrderLineService orderLineService;


    /**
     * 评选分页列表
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/queryEvaluationPage")
    public PageInfo<EvaluationResult> queryEvaluationPage(EvaluationQueryParam queryParam) {
        queryParam.setPageSize(99999);
        return iEvaluationService.queryEvaluationPage(queryParam);
    }

    /**
     * 评选结果导入模板下载
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/importModelDownload")
    public void importModelDownload(EvaluationQueryParam queryParam, HttpServletResponse response) throws Exception {
        iEvaluationService.importModelDownload(queryParam, response);
    }

    /**
     * 导入评选结果
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iEvaluationService.importExcel(file, fileupload);
    }

    /**
     * 智能评选
     *
     * @param bidingId
     * @return
     */
    @GetMapping("/intelligentEvaluation")
    public String intelligentEvaluation(@RequestParam(name = "bidingId", required = true) Long bidingId) {
        iEvaluationService.intelligentEvaluation(bidingId);
        return null;
    }

    /**
     * 入围
     *
     * @param orderLineIdList
     */
    @PostMapping("/enterNextRound")
    public void enterNextRound(@RequestBody List<Long> orderLineIdList) {
        iEvaluationService.enterNextRound(orderLineIdList);
    }

    /**
     * 淘汰
     *
     * @param orderLineIdList
     */
    @PostMapping("/eliminate")
    public void eliminate(@RequestBody List<Long> orderLineIdList) {
        iEvaluationService.eliminate(orderLineIdList);
    }

    /**
     * 公示结果
     *
     * @param bidingId
     */
    @GetMapping("/publicityOfResult")
    public void publicityOfResult(@RequestParam(name = "bidingId", required = true) Long bidingId) {
        iEvaluationService.publicityOfResult(bidingId);
    }

    /**
     * 计算中标结果
     * 这里不需要
     *
     * @param bidingId
     * @return
     */
    @Deprecated
    @GetMapping("/calculateWinBidResult")
    public void calculateWinBidResult(@RequestParam(name = "bidingId", required = true) Long bidingId) {
        iEvaluationService.calculateWinBidResult(bidingId);
    }

    /**
     * 发起跟标确认
     *
     * @param createFollowTenderParam
     */
    @PostMapping("/createFollowTender")
    public void createFollowTender(@RequestBody CreateFollowTenderParam createFollowTenderParam) {
        iEvaluationService.createFollowTender(createFollowTenderParam);
    }

    /**
     * 结束跟标
     * 不需要
     *
     * @param bidingId
     * @return
     */
    @Deprecated
    @GetMapping("/endFollowBid")
    public void endFollowBid(@RequestParam(name = "bidingId", required = true) Long bidingId) {
        iEvaluationService.endFollowBid(bidingId);
    }

    /**
     * 结束评选这里主要把待评选的改为落标
     * 不需要
     *
     * @param bidingId
     * @return
     */

    @GetMapping("/endEvaluation")
    public void endEvaluation(@RequestParam(name = "bidingId", required = true) Long bidingId) {
        iEvaluationService.endEvaluation(bidingId);
    }

    /**
     * 获取投标截止时间
     *
     * @param bidingId
     * @return
     */
    @GetMapping("/getRoundEndTime")
    public String getRoundEndTime(@RequestParam(name = "bidingId", required = true) Long bidingId, @RequestParam(name = "round", required = true) Integer round) {
        return iEvaluationService.getRoundEndTime(bidingId, round);
    }

    @PostMapping("/changeWinStatus")
    public void changeStatus(@RequestBody List<Long> ids) {
        iEvaluationService.changeOrderLineStatus(ids, false);
    }

    @PostMapping("/changeFailStatus")
    public void changeFailStatus(@RequestBody List<Long> ids) {
        iEvaluationService.changeOrderLineStatus(ids, true);
    }

    @PostMapping("/changeQuantity")
    public void changeQuantity(@RequestBody List<Map<String, Object>> maps) {
        List<OrderLine> orderLines = new LinkedList<>();
        List<Long> setNullList = new LinkedList<>();
        for (Map<String, Object> map : maps) {
            Long orderLineId = Optional.ofNullable(map.get("orderLineId"))
                    .map(Object::toString)
                    .map(Long::valueOf).orElseThrow(() -> new BaseException("报价行id不能为空"));
            BigDecimal quantity = Optional.ofNullable(map.get("quotaQuantity"))
                    .map(Object::toString)
                    .map(BigDecimal::new).orElse(BigDecimal.ZERO);
            if (quantity.compareTo(BigDecimal.ZERO) > 0) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderLineId(orderLineId).setQuotaQuantity(quantity);
                orderLines.add(orderLine);
            } else {
                setNullList.add(orderLineId);
            }

        }
        if (CollectionUtils.isNotEmpty(orderLines)) {
            orderLineService.updateBatchById(orderLines);
        }
        if (CollectionUtils.isNotEmpty(setNullList)) {
            orderLineService.update(Wrappers.lambdaUpdate(OrderLine.class)
                    .set(OrderLine::getQuotaQuantity, null)
                    .in(OrderLine::getOrderLineId, setNullList)
            );
        }
    }

    /**
     * 打印预览
     *
     * @param bidingId
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/printPDF")
    public void printPDF(Long bidingId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = iEvaluationService.printPDF(bidingId);
        String fileName = URLEncoder.encode(result.get("fileName").toString(), "UTF-8");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
        response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write((byte[]) result.get("buffer"));
        response.getOutputStream().close();
    }

    /**
     * 导出
     *
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/exportExcel")
    public void exportExcel(EvaluationQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = iEvaluationService.exportExcel(queryParam);
        String fileName = URLEncoder.encode(result.get("fileName").toString(), "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
        response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write((byte[]) result.get("buffer"));
        response.getOutputStream().close();
    }
}
