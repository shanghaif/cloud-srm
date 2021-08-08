package com.midea.cloud.srm.inq.sourcingresult.service;

import com.midea.cloud.srm.model.inq.sourcingresult.vo.GenerateSourcingResultReportParameter;
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
     * @param parameter 寻源报表生成参数
     * @return 寻源结果报表
     */
    SourcingResultReport generate(GenerateSourcingResultReportParameter parameter);
}
