package com.midea.cloud.srm.base.monitor.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.quartz.handler.QuartzManager;
import com.midea.cloud.srm.base.monitor.service.MonitorBizConfigService;
import com.midea.cloud.srm.base.monitor.service.MonitorBizLogService;
import com.midea.cloud.srm.base.quicksearch.mapper.QuicksearchConfigMapper;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizConfig;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizLog;
import com.midea.cloud.srm.model.base.monitor.enums.BizStatus;
import com.midea.cloud.srm.model.base.monitor.enums.MonitorMode;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Job("monitorBizJob")
@Slf4j
public class MonitorBizJob implements ExecuteableJob {
    @Autowired
    private QuartzManager quartzManager;
    @Resource
    private MonitorBizConfigService monitorBizConfigService;

    @Resource
    private MonitorBizLogService iMonitorBizLogService;

    @Resource
    private QuicksearchConfigMapper quicksearchConfigMapper;

    @Autowired
    private BaseClient baseClient;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        String monitorBizConfigId = params.get("id");
        Date executeStartTime = new Date();
        MonitorBizConfig monitorBizConfig = monitorBizConfigService.getById(monitorBizConfigId);

        List<Map<String, Object>> dataList = null;
        try {
            if (YesOrNo.N.equals(monitorBizConfig.getValidStatus())) {
                return BaseResult.buildSuccess("任务:<" + monitorBizConfig.getMonitorBizName() + ">未执行，配置未启用");
            }
            if (MonitorMode.EXCEPTION.equals(monitorBizConfig.getMonitorMode())
                    && BizStatus.EXCEPTION.equals(monitorBizConfig.getLatestBizStatus())) {
                return BaseResult.buildSuccess("任务:<" + monitorBizConfig.getMonitorBizName() + ">未执行，异常还未处理");
            }

            //切换数据源
            CheckModuleHolder.checkout(Module.get(monitorBizConfig.getQueryModule()));
            dataList = quicksearchConfigMapper.getQuickData(monitorBizConfig.getSqlExpression());
            if (CollectionUtils.isNotEmpty(dataList)) {
                MonitorBizLog monitorBizLog = new MonitorBizLog()
                        .setMonitorBizConfigId(monitorBizConfig.getMonitorBizConfigId())
                        .setMonitorBizName(monitorBizConfig.getMonitorBizName())
                        .setReceiveMails(monitorBizConfig.getReceiveMails())
                        .setExecuteStartTime(executeStartTime)
                        .setBizStatus(BizStatus.NORMAL)
                        .setDealStatus(YesOrNo.N);
                if ("SINGLE".equals(monitorBizConfig.getResultDataType())) {
                    Map<String, Object> data = dataList.get(0);
                    Object valueObj = data.get(monitorBizConfig.getColumnName());
                    if (valueObj == null) {
                        monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                        monitorBizLog.setErrorMsg("结果数据为空");
                    }
                    long value = Long.parseLong(valueObj.toString());
                    long dbValue = Long.parseLong(monitorBizConfig.getDataValue());
                    switch (monitorBizConfig.getOperationSymbol()) {
                        case EQUAL_TO:
                            if (value != dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
                        case NOT_EQUAL_TO:
                            if (value == dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
                        case GREATER_THAN:
                            if (value <= dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
                        case GREATER_THAN_OR_EQUAL:
                            if (value < dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
                        case LESS_THAN:
                            if (value >= dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
                        case LESS_THAN_OR_EQUAL:
                            if (value > dbValue) {
                                monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                                monitorBizLog.setErrorMsg("预期值不一致：结果：" + value + "; 预期：" + dbValue);
                            }
                            break;
//                        以下类型待实现
//                        case LIKE:
//                            break;
//                        case NOT_LIKE:
//                            break;
//                        case IN:
//                            break;
//                        case NOT_IN:
//                            break;
//                        case BETWEEN:
//                            break;
//                        case NOT_BETWEEN:
//                            break;
                        default:
                            // 异常
                            monitorBizLog.setBizStatus(BizStatus.EXCEPTION);
                            monitorBizLog.setErrorMsg("比较类型不存在");
                    }
                }
                monitorBizLog.setExecuteEndTime(new Date());
                monitorBizLog.setMonitorBizLogId(IdGenrator.generate());
                iMonitorBizLogService.save(monitorBizLog);

                MonitorBizConfig update = new MonitorBizConfig().setMonitorBizConfigId(monitorBizConfig.getMonitorBizConfigId())
                        .setLatestBizStatus(monitorBizLog.getBizStatus());
                monitorBizConfigService.updateById(update);

                if (BizStatus.EXCEPTION.equals(monitorBizLog.getBizStatus())) {
                    baseClient.sendEmail(monitorBizConfig.getReceiveMails(), monitorBizConfig.getMonitorBizName(), monitorBizLog.getErrorMsg());
                }
            }
        } finally {
            CheckModuleHolder.release();
        }
        return BaseResult.buildSuccess("任务:<" + monitorBizConfig.getMonitorBizName() + ">执行成功");
    }

}
