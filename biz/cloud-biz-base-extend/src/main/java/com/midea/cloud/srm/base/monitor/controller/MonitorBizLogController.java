package com.midea.cloud.srm.base.monitor.controller;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.monitor.service.MonitorBizLogService;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizLog;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.IOException;


/**
* <pre>
 *  业务监控日志 前端控制器
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
@RestController
@RequestMapping("/base/monitor_biz_log")
public class MonitorBizLogController extends BaseController {

    @Autowired
    private MonitorBizLogService monitorBizLogService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public MonitorBizLog get(Long id) {
        Assert.notNull(id, "id不能为空");
        return monitorBizLogService.getById(id);
    }

    @PostMapping("/deal")
    public void deal(@RequestBody MonitorBizLog monitorBizLog) throws IOException {
        monitorBizLogService.deal(monitorBizLog.getMonitorBizLogId());
    }

    /**
    * 新增
    * @param monitorBizLog
    */
    @PostMapping("/add")
    public void add(@RequestBody MonitorBizLog monitorBizLog) {
        Long id = IdGenrator.generate();
        monitorBizLog.setMonitorBizLogId(id);
        monitorBizLog.setMonitorBizConfigId(id);
        monitorBizLogService.save(monitorBizLog);
    }

    /**
     * 批量新增或者修改
     * @param monitorBizLogList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<MonitorBizLog> monitorBizLogList) throws IOException{
         monitorBizLogService.batchSaveOrUpdate(monitorBizLogList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        monitorBizLogService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        monitorBizLogService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param monitorBizLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody MonitorBizLog monitorBizLog) {
        monitorBizLogService.updateById(monitorBizLog);
    }

    /**
    * 分页查询
    * @param monitorBizLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<MonitorBizLog> listPage(@RequestBody MonitorBizLog monitorBizLog) {
        return monitorBizLogService.listPage(monitorBizLog);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MonitorBizLog> listAll() {
        return monitorBizLogService.list();
    }
}
