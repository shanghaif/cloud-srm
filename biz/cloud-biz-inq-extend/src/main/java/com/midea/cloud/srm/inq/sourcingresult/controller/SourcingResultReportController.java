package com.midea.cloud.srm.inq.sourcingresult.controller;

import com.midea.cloud.srm.inq.sourcingresult.service.ISourcingResultReportService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.GenerateSourcingResultReportParameter;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Http接口 - 寻源结果报表
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/price/sourcingResultReport")
public class SourcingResultReportController extends BaseController {

    @Resource
    private ISourcingResultReportService    sourcingResultReportService;


    @PostMapping("/generate")
    public SourcingResultReport generate(@RequestBody GenerateSourcingResultReportParameter parameter) {
        return sourcingResultReportService.generate(parameter);
    }
}
