package com.midea.cloud.srm.sup.risk.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.sup.risk.RiskMonitoringStatus;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.risk.entity.Monitoring;
import com.midea.cloud.srm.model.supplier.risk.entity.Responses;
import com.midea.cloud.srm.sup.risk.service.IMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商风险监控 前端控制器
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
@RestController
@RequestMapping("/risk/monitoring")
public class MonitoringController extends BaseController {

    @Autowired
    private IMonitoringService iMonitoringService;

    /**
    * 获取
    * @param riskMonitoringId
    */
    @GetMapping("/get")
    public Monitoring get(Long riskMonitoringId) {
        return iMonitoringService.get(riskMonitoringId);
    }

    /**
    * 新增
    * @param monitoring
    */
    @PostMapping("/add")
    public Long add(@RequestBody Monitoring monitoring) {
        return iMonitoringService.saveOrUpdateMonitoring(monitoring, RiskMonitoringStatus.ADD.name());
    }
    
    /**
    * 删除
    * @param riskMonitoringId
    */
    @GetMapping("/delete")
    public void delete(Long riskMonitoringId) {
        Assert.notNull(riskMonitoringId, "riskMonitoringId不能为空");
        iMonitoringService.removeById(riskMonitoringId);
    }

    /**
    * 修改
    * @param monitoring
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody Monitoring monitoring) {
        return iMonitoringService.saveOrUpdateMonitoring(monitoring,null);
    }

    /**
    * 分页查询
    * @param monitoring
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Monitoring> listPage(@RequestBody Monitoring monitoring) {
        return iMonitoringService.listPage(monitoring);
    }

    /**
     * 提交
     * @param monitoring
     * @return
     */
    @PostMapping("/submit")
    public Long submit(@RequestBody Monitoring monitoring){
        return iMonitoringService.submit(monitoring);
    }

    /**
     * 新建审批通过
     * @param riskMonitoringId
     * @return
     */
    @GetMapping("/addPass")
    public Long addPass(@RequestParam("riskMonitoringId") Long riskMonitoringId){
        return iMonitoringService.addPass(riskMonitoringId);
    }

    /**
     * 关闭审批
     * @param monitoring
     * @return
     */
    @PostMapping("/close")
    public Long close(@RequestBody Monitoring monitoring){
        return iMonitoringService.close(monitoring);
    }

    /**
     * 关闭审批通过
     * @param riskMonitoringId
     * @return
     */
    @GetMapping("/closePass")
    public Long closePass(@RequestParam("riskMonitoringId") Long riskMonitoringId){
        return iMonitoringService.closePass(riskMonitoringId);
    }

    /**
     * 查询供应商上期应对措施
     * @param vendorId
     * @param riskType
     * @return
     */
    @GetMapping("/queryResponses")
    public List<Responses> queryResponses (@RequestParam("vendorId") Long vendorId,@RequestParam("riskType")String riskType){
        return iMonitoringService.queryResponses(vendorId,riskType);
    }
}
