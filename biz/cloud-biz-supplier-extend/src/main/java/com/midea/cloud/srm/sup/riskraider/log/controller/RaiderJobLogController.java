package com.midea.cloud.srm.sup.riskraider.log.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.riskraider.log.entity.RaiderJobLogEntity;
import com.midea.cloud.srm.model.supplier.riskraider.monitor.HaveMonitor;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.info.service.impl.OrgCategoryServiceImpl;
import com.midea.cloud.srm.sup.riskraider.log.service.IRaiderJobLogService;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  风险雷达调度记录表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-02-03 14:43:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/raider-job-log")
public class RaiderJobLogController extends BaseController {

    @Autowired
    private IRaiderJobLogService iRaiderJobLogService;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;
    @Autowired
    private OrgCategoryServiceImpl orgCategoryService;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public RaiderJobLogEntity get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRaiderJobLogService.getById(id);
    }

    /**
    * 新增
    * @param raiderJobLog
    */
    @PostMapping("/add")
    public void add(@RequestBody RaiderJobLogEntity raiderJobLog) {
        Long id = IdGenrator.generate();
        raiderJobLog.setJobLogId(id);
        iRaiderJobLogService.save(raiderJobLog);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRaiderJobLogService.removeById(id);
    }

    /**
    * 修改
    * @param raiderJobLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody RaiderJobLogEntity raiderJobLog) {
        iRaiderJobLogService.updateById(raiderJobLog);
    }

    /**
    * 分页查询
    * @param raiderJobLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<RaiderJobLogEntity> listPage(@RequestBody RaiderJobLogEntity raiderJobLog) {
        PageUtil.startPage(raiderJobLog.getPageNum(), raiderJobLog.getPageSize());
        QueryWrapper<RaiderJobLogEntity> wrapper = new QueryWrapper<RaiderJobLogEntity>(raiderJobLog);
        return new PageInfo<RaiderJobLogEntity>(iRaiderJobLogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<RaiderJobLogEntity> listAll() { 
        return iRaiderJobLogService.list();
    }

    @PostMapping("/doAddOnMonitorSup")
    public void doAddOnMonitorSup(){
        //1、获取所有的监控供应商
        List<HaveMonitor> list = haveMonitorService.list().stream().filter(x-> Objects.nonNull(x.getCompanyId())).collect(Collectors.toList());
        Set<Long> companyIdSet = list.stream().map(HaveMonitor::getCompanyId).collect(Collectors.toSet());

        Map<Long, OrgCategory> collectMap = orgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .select(OrgCategory::getCompanyId)
                .and(e->e.eq(OrgCategory::getServiceStatus,"GREEN").or().eq(OrgCategory::getServiceStatus,"VERIFY"))
        ).stream().filter(x->!companyIdSet.contains(x.getCompanyId())).collect(Collectors.toMap(x -> x.getCompanyId(), Function.identity(), (o1, o2) -> o1));

        List<CompanyInfo> companyInfos = companyInfoService.listByIds(collectMap.keySet());
        companyInfos.stream().forEach(x->{
            OrgCategory orgCategory = collectMap.get(x.getCompanyId());
            orgCategory.setCompanyCode(x.getCompanyCode()).setCompanyName(x.getCompanyName());
            collectMap.put(x.getCompanyId(),orgCategory);
        });

        ArrayList<RaiderJobLogEntity> raiderJobLogEntities = new ArrayList<>();
        for (Map.Entry<Long, OrgCategory> entry : collectMap.entrySet()) {
            RaiderJobLogEntity raiderJobLogEntity = new RaiderJobLogEntity()
                    .setJobLogId(IdGenrator.generate())
                    .setBatchNum(1)
                    .setInterfaceCode("RN")
                    .setVendorId(entry.getValue().getCompanyId())
                    .setVendorCode(entry.getValue().getCompanyCode())
                    .setVendorName(entry.getValue().getCompanyName());
            raiderJobLogEntities.add(raiderJobLogEntity);
        }
        iRaiderJobLogService.saveBatch(raiderJobLogEntities);

    }
 
}
