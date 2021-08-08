package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.controller;

import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.ISourcingResultReportService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Http接口 - 寻源结果报表
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/bidingResult/sourcingResultReport")
public class SourcingResultReportController extends BaseController {

    @Resource
    private ISourcingResultReportService    sourcingResultReportService;


    @GetMapping("/generate")
    public SourcingResultReport generate(String biddingNum) {
        return sourcingResultReportService.generate(biddingNum,null);
    }
}
