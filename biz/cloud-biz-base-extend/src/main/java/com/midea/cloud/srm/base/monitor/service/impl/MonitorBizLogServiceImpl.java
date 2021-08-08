package com.midea.cloud.srm.base.monitor.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.monitor.mapper.MonitorBizLogMapper;
import com.midea.cloud.srm.base.monitor.service.MonitorBizConfigService;
import com.midea.cloud.srm.base.monitor.service.MonitorBizLogService;

import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizConfig;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizLog;

import com.midea.cloud.srm.model.base.monitor.enums.BizStatus;
import com.midea.cloud.srm.model.base.monitor.enums.MonitorMode;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
/**
* <pre>
 *  业务监控日志 服务实现类
 * </pre>
*
* @author huanglj50@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-5-5 10:01:51
 *  修改内容:
 * </pre>
*/
@Service
public class MonitorBizLogServiceImpl extends ServiceImpl<MonitorBizLogMapper, MonitorBizLog> implements MonitorBizLogService {

    @Resource
    private MonitorBizConfigService monitorBizConfigService;

    @Transactional
    public void batchUpdate(List<MonitorBizLog> monitorBizLogList) {
        this.saveOrUpdateBatch(monitorBizLogList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<MonitorBizLog> monitorBizLogList) throws IOException {
        for(MonitorBizLog monitorBizLog : monitorBizLogList){
            if(monitorBizLog.getMonitorBizConfigId() == null){
                Long id = IdGenrator.generate();
                monitorBizLog.setMonitorBizConfigId(id);
            }
        }
        if(!CollectionUtils.isEmpty(monitorBizLogList)) {
            batchUpdate(monitorBizLogList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<MonitorBizLog> listPage(MonitorBizLog monitorBizLog) {
        PageUtil.startPage(monitorBizLog.getPageNum(), monitorBizLog.getPageSize());
        List<MonitorBizLog> monitorBizLogs = getMonitorBizLogs(monitorBizLog);
        return new PageInfo<>(monitorBizLogs);
    }

    @Override
    public void deal(Long id) throws IOException {
        MonitorBizLog monitorBizLog = getById(id);

        MonitorBizConfig monitorBizConfig = monitorBizConfigService.getById(monitorBizLog.getMonitorBizConfigId());
        if (MonitorMode.EXCEPTION.equals(monitorBizConfig.getMonitorMode())) { // 同时更新配置状态为已处理
            MonitorBizConfig updateConfig = new MonitorBizConfig().setMonitorBizConfigId(monitorBizConfig.getMonitorBizConfigId())
                    .setLatestBizStatus(BizStatus.NORMAL);
            monitorBizConfigService.updateById(updateConfig);
        }

        MonitorBizLog updateLog = new MonitorBizLog().setMonitorBizLogId(id).setDealStatus(YesOrNo.Y);
        updateById(updateLog);
    }

    public List<MonitorBizLog> getMonitorBizLogs(MonitorBizLog monitorBizLog) {
        QueryWrapper<MonitorBizLog> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",monitorBizLog.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",monitorBizLog.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",monitorBizLog.getStartDate()).
//                        le("CREATION_DATE",monitorBizLog.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
