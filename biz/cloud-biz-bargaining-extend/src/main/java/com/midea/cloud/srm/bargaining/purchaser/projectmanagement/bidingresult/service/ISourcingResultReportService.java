package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service;

import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;

/**
 * 寻源结果报表服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ISourcingResultReportService {

    /**
     * 生成 寻源结果报表
     *
     * @param biddingNum 寻源单号
     * @return 寻源结果报表
     */
    SourcingResultReport generate(String biddingNum, Long inquiryId);
}
