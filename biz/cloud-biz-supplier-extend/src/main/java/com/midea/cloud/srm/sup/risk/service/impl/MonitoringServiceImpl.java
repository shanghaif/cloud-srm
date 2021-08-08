package com.midea.cloud.srm.sup.risk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.sup.risk.RiskMonitoringStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.risk.entity.Monitoring;
import com.midea.cloud.srm.model.supplier.risk.entity.ProcessControl;
import com.midea.cloud.srm.model.supplier.risk.entity.Responses;
import com.midea.cloud.srm.sup.risk.mapper.MonitoringMapper;
import com.midea.cloud.srm.sup.risk.service.IMonitoringService;
import com.midea.cloud.srm.sup.risk.service.IProcessControlService;
import com.midea.cloud.srm.sup.risk.service.IResponsesService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
*  <pre>
 *  供应商风险监控 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 13:46:12
 *  修改内容:
 * </pre>
*/
@Service
public class MonitoringServiceImpl extends ServiceImpl<MonitoringMapper, Monitoring> implements IMonitoringService {
    @Resource
    private IProcessControlService iProcessControlService;
    @Resource
    private IResponsesService iResponsesService;
    @Resource
    private BaseClient baseClient;

    @Override
    public Monitoring get(Long riskMonitoringId) {
        Assert.notNull(riskMonitoringId, "riskMonitoringId不能为空");
        Monitoring monitoring = this.getById(riskMonitoringId);
        if(null != monitoring){
            List<ProcessControl> processControls = iProcessControlService.list(new QueryWrapper<>(new ProcessControl().setRiskMonitoringId(riskMonitoringId)));
            List<Responses> responses = iResponsesService.list(new QueryWrapper<>(new Responses().setRiskMonitoringId(riskMonitoringId)));
            monitoring.setProcessControls(processControls);
            monitoring.setResponses(responses);
        }
        return monitoring;
    }

    @Override
    public Long saveOrUpdateMonitoring(Monitoring monitoring,String status) {
        // 处理头表
        Long riskMonitoringId = monitoring.getRiskMonitoringId();
        if (StringUtil.notEmpty(status)) {
            monitoring.setStatus(status);
        }
        if(StringUtil.isEmpty(riskMonitoringId)){
            riskMonitoringId = IdGenrator.generate();
            monitoring.setRiskMonitoringId(riskMonitoringId);
            monitoring.setRiskCode(baseClient.seqGen(SequenceCodeConstant.SEQ_RISK_CODE));
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            monitoring.setCreatedName(loginAppUser.getNickname());
            // 部门
            if(StringUtil.isEmpty(monitoring.getDepartment())){
                monitoring.setDepartment(loginAppUser.getDepartment());
            }
            this.save(monitoring);
        }else {
            this.updateById(monitoring);
        }

        // 保存风险应对措施
        saveResponses(monitoring, riskMonitoringId);
        // 保存系统业务流程控制
        saveProcessControl(monitoring, riskMonitoringId);

        return riskMonitoringId;
    }

    public void saveProcessControl(Monitoring monitoring, Long riskMonitoringId) {
        List<ProcessControl> processControls = monitoring.getProcessControls();
        iProcessControlService.remove(new QueryWrapper<>(new ProcessControl().setRiskMonitoringId(riskMonitoringId)));
        if(CollectionUtils.isNotEmpty(processControls)){
            processControls.forEach(processControl -> {
                processControl.setRiskMonitoringId(riskMonitoringId);
                processControl.setProcessControlId(IdGenrator.generate());
            });
            iProcessControlService.saveBatch(processControls);
        }
    }

    public void saveResponses(Monitoring monitoring, Long riskMonitoringId) {
        List<Responses> responses = monitoring.getResponses();
        iResponsesService.remove(new QueryWrapper<>(new Responses().setRiskMonitoringId(riskMonitoringId)));
        if(CollectionUtils.isNotEmpty(responses)){
            responses.forEach(responses1 -> {
                responses1.setRiskMonitoringId(riskMonitoringId);
                responses1.setRiskResponsesId(IdGenrator.generate());
            });
            iResponsesService.saveBatch(responses);
        }
    }

    @Override
    public PageInfo<Monitoring> listPage(Monitoring monitoring) {
        // 设置分页
        PageUtil.startPage(monitoring.getPageNum(),monitoring.getPageSize());
        QueryWrapper<Monitoring> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getCategoryId()),"CATEGORY_ID",monitoring.getCategoryId());
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getVendorId()),"VENDOR_ID",monitoring.getVendorId());
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getStatus()),"STATUS",monitoring.getStatus());
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getCreatedName()),"CREATED_NAME",monitoring.getCreatedName());
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getRiskType()),"RISK_TYPE",monitoring.getRiskType());
        queryWrapper.eq(StringUtil.notEmpty(monitoring.getRiskLevel()),"RISK_LEVEL",monitoring.getRiskLevel());
        queryWrapper.ge(StringUtil.notEmpty(monitoring.getStartDate()),"CREATION_DATE",monitoring.getStartDate());
        queryWrapper.le(StringUtil.notEmpty(monitoring.getEndDate()),"CREATION_DATE",monitoring.getEndDate());
        List<Monitoring> monitorings = this.list(queryWrapper);
        return new PageInfo<>(monitorings);
    }

    @Override
    @Transactional
    public Long submit(Monitoring monitoring) {
        // 保存信息
        Long riskMonitoringId = saveOrUpdateMonitoring(monitoring, RiskMonitoringStatus.APPROVAL_ADD.name());
        // TODO 接入审批流

        return riskMonitoringId;
    }

    @Override
    public Long addPass(Long riskMonitoringId) {
        Monitoring monitoring = this.getById(riskMonitoringId);
        if(null != monitoring){
            monitoring.setStatus(RiskMonitoringStatus.MONITORING.name());
            this.updateById(monitoring);
        }
        return riskMonitoringId;
    }

    @Override
    @Transactional
    public Long close(Monitoring monitoring) {
        // 保存信息
        // 关闭时间为当前时间
        monitoring.setCloseDate(LocalDate.now());
        Long riskMonitoringId = saveOrUpdateMonitoring(monitoring, RiskMonitoringStatus.APPROVAL_CLOSE.name());
        // TODO 失效之前的审批流

        // TODO 启动关闭审批流

        return riskMonitoringId;
    }

    @Override
    public Long closePass(Long riskMonitoringId) {
        Monitoring monitoring = this.getById(riskMonitoringId);
        if(null != monitoring){
            monitoring.setStatus(RiskMonitoringStatus.CLOSED.name());
            this.updateById(monitoring);
        }
        return riskMonitoringId;
    }

    @Override
    public List<Responses> queryResponses(Long vendorId, String riskType) {
        Assert.notNull(vendorId,"vendorId不能为空");
        Assert.notNull(riskType,"riskType不能为空");
        Monitoring monitoring = this.baseMapper.queryByVendorIdAndRiskType(vendorId, riskType);
        if(null != monitoring && StringUtil.notEmpty(monitoring.getRiskMonitoringId())){
            Long riskMonitoringId = monitoring.getRiskMonitoringId();
            List<Responses> responses = iResponsesService.list(new QueryWrapper<>(new Responses().setRiskMonitoringId(riskMonitoringId)));
            return responses;
        }
        return null;
    }
}
