package com.midea.cloud.srm.base.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.quartz.handler.QuartzManager;
import com.midea.cloud.quartz.job.service.IStrategyService;
import com.midea.cloud.quartz.job.service.ITriggerService;
import com.midea.cloud.srm.base.monitor.mapper.MonitorBizConfigMapper;
import com.midea.cloud.srm.base.monitor.service.MonitorBizConfigService;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizConfig;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import com.midea.cloud.srm.model.quartz.scheduler.entity.QuartzStrategy;
import com.midea.cloud.srm.model.quartz.scheduler.entity.QuartzTrigger;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
* <pre>
 *  业务监控配置 服务实现类
 * </pre>
*
* @author huanglj50@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-5-5 9:58:31
 *  修改内容:
 * </pre>
*/
@Service
public class MonitorBizConfigServiceImpl extends ServiceImpl<MonitorBizConfigMapper, MonitorBizConfig> implements MonitorBizConfigService {
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private ITriggerService iTriggerService;
    @Autowired
    private IStrategyService iStrategyService;

    @Transactional
    public void batchUpdate(List<MonitorBizConfig> monitorBizConfigList) {
        this.saveOrUpdateBatch(monitorBizConfigList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<MonitorBizConfig> monitorBizConfigList) throws IOException {
        for(MonitorBizConfig monitorBizConfig : monitorBizConfigList){
            if(monitorBizConfig.getMonitorBizConfigId() == null){
                Long id = IdGenrator.generate();
                monitorBizConfig.setMonitorBizConfigId(id);
            }
        }
        if(!CollectionUtils.isEmpty(monitorBizConfigList)) {
            batchUpdate(monitorBizConfigList);
        }
    }


    @Override
    public Long addOrUpdate(MonitorBizConfig monitorBizConfig) {
        // 校验数据
        Long monitorBizConfigId = monitorBizConfig.getMonitorBizConfigId();
        if(StringUtil.isEmpty(monitorBizConfigId)){
            monitorBizConfig.setValidStatus(YesOrNo.N);
            monitorBizConfig.setMonitorBizConfigId(IdGenrator.generate());
            this.save(monitorBizConfig);
        }else {
            MonitorBizConfig dbConfig = super.getById(monitorBizConfigId);
            if (YesOrNo.Y.equals(dbConfig.getValidStatus())) {
                monitorBizConfig.setTriggerName(null);
                monitorBizConfig.setValidStatus(YesOrNo.Y);
            } else {
                monitorBizConfig.setValidStatus(YesOrNo.N);
            }
            this.updateById(monitorBizConfig);
        }
        return monitorBizConfig.getMonitorBizConfigId();
    }

    @Override
    public void deleteById(Long id) throws IOException {
        MonitorBizConfig dbConfig = super.getById(id);
        if (YesOrNo.Y.equals(dbConfig.getValidStatus())) { // 停止任务
            quartzManager.removeTrigger(dbConfig.getTriggerName());
        }
        if (StringUtils.isNotBlank(dbConfig.getTriggerName())) {
            LambdaQueryWrapper<QuartzStrategy> deleteStrategyWrapper = Wrappers.lambdaQuery();
            deleteStrategyWrapper.eq(QuartzStrategy::getStrategyName, dbConfig.getTriggerName());
            iStrategyService.remove(deleteStrategyWrapper);
        }

        super.removeById(id);
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        List<MonitorBizConfig> dbConfigList = super.listByIds(ids);
        for (MonitorBizConfig dbConfig: dbConfigList) {
            if (YesOrNo.Y.equals(dbConfig.getValidStatus())) { // 停止任务
                quartzManager.removeTrigger(dbConfig.getTriggerName());
            }
        }

        List<String> triggerNameList = dbConfigList.stream().filter(item -> StringUtils.isNotBlank(item.getTriggerName())).map(MonitorBizConfig::getTriggerName).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(triggerNameList)) {
            LambdaQueryWrapper<QuartzStrategy> deleteStrategyWrapper = Wrappers.lambdaQuery();
            deleteStrategyWrapper.in(QuartzStrategy::getStrategyName, triggerNameList);
            iStrategyService.remove(deleteStrategyWrapper);
        }

        this.removeByIds(ids);
    }
    @Override
    public PageInfo<MonitorBizConfig> listPage(MonitorBizConfig monitorBizConfig) {
        PageUtil.startPage(monitorBizConfig.getPageNum(), monitorBizConfig.getPageSize());
        List<MonitorBizConfig> monitorBizConfigs = getMonitorBizConfigs(monitorBizConfig);
        return new PageInfo<>(monitorBizConfigs);
    }

    @Override
    public void batchDealTask(List<Long> ids, YesOrNo yesOrNo) throws IOException {
        List<MonitorBizConfig> monitorBizConfigs = listByIds(ids);
        if (YesOrNo.Y.equals(yesOrNo)) { // 将关闭的全部启动
            List<MonitorBizConfig> waitList = monitorBizConfigs.stream()
                    .filter(item -> YesOrNo.N.equals(item.getValidStatus()))
                    .collect(Collectors.toList());

            for (MonitorBizConfig dbConfig: waitList) {
                // 如果name并不存在，则需要创建并生成
                // name strategy创建
                String strategyUrl = "http://cloud-biz-base/job-anon/internal/job/execute?jobName=monitorBizJob&id="+dbConfig.getMonitorBizConfigId();

                // 策略表添加
                String triggerName = dbConfig.getTriggerName();
                if (StringUtils.isBlank(triggerName)) {
                    // MonitorBizConfig+configId+UUID
                    triggerName = "Monitor_Biz_Config_" + dbConfig.getMonitorBizConfigId() + "_" + UUID.randomUUID().toString();
                    QuartzStrategy quartzStrategy = new QuartzStrategy().setStrategyName(triggerName).setStrategyUrl(strategyUrl);
                    iStrategyService.saveStrategy(quartzStrategy);
                } else {
                    List<QuartzStrategy>  strategyList = iStrategyService.getStrategyByTriggerName(triggerName);
                    if (CollectionUtils.isEmpty(strategyList)) {
                        QuartzStrategy quartzStrategy = new QuartzStrategy().setStrategyName(triggerName).setStrategyUrl(strategyUrl);
                        iStrategyService.saveStrategy(quartzStrategy);
                    }
                }

                dbConfig.setTriggerName(triggerName);

                // 触发器添加
                quartzManager.addCronTrigger(triggerName, dbConfig.getCronExpression());
            }

            List<MonitorBizConfig> updateList = new ArrayList<>();
            for (MonitorBizConfig monitorBizConfig: waitList) {
                updateList.add(new MonitorBizConfig().setMonitorBizConfigId(monitorBizConfig.getMonitorBizConfigId())
                        .setTriggerName(monitorBizConfig.getTriggerName()).setValidStatus(YesOrNo.Y));
            }
            this.updateBatchById(updateList);
        } else {
            List<MonitorBizConfig> waitList = monitorBizConfigs.stream()
                    .filter(item -> YesOrNo.Y.equals(item.getValidStatus()) && StringUtils.isNotBlank(item.getTriggerName()))
                    .collect(Collectors.toList());

            // 停止
            for (MonitorBizConfig monitorBizConfig: monitorBizConfigs) {
                quartzManager.removeTrigger(monitorBizConfig.getTriggerName());
            }

            // 更新当前配置状态
            List<MonitorBizConfig> updateList = new ArrayList<>();
            for (MonitorBizConfig monitorBizConfig: monitorBizConfigs) {
                updateList.add(new MonitorBizConfig().setMonitorBizConfigId(monitorBizConfig.getMonitorBizConfigId()).setValidStatus(YesOrNo.N));
            }
            this.updateBatchById(updateList);
        }
    }

    public List<MonitorBizConfig> getMonitorBizConfigs(MonitorBizConfig monitorBizConfig) {
        QueryWrapper<MonitorBizConfig> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",monitorBizConfig.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",monitorBizConfig.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",monitorBizConfig.getStartDate()).
//                        le("CREATION_DATE",monitorBizConfig.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
