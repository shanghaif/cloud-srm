package com.midea.cloud.srm.feign.inq;

import com.midea.cloud.srm.model.inq.sourcingresult.vo.GenerateSourcingResultReportParameter;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * FeignClient - 寻源结果报表API
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(value = "cloud-biz-inquiry", contextId = "cloud-biz-inquiry-sourcingResultReport")
public interface SourcingResultReportClient {

    @PostMapping("/price/sourcingResultReport/generate")
    SourcingResultReport generate(@RequestBody GenerateSourcingResultReportParameter parameter);
}
