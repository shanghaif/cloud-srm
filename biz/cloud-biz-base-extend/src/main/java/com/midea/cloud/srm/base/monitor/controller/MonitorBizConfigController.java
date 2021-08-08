package com.midea.cloud.srm.base.monitor.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.monitor.service.MonitorBizConfigService;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizConfig;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
* <pre>
 *  业务监控配置 前端控制器
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
@RestController
@RequestMapping("/base/monitor_biz_config")
public class MonitorBizConfigController extends BaseController {

    @Autowired
    private MonitorBizConfigService monitorBizConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public MonitorBizConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return monitorBizConfigService.getById(id);
    }

    /**
    * 新增
    * @param monitorBizConfig
    */
    @PostMapping("/addOrUpdate")
    public void addOrUpdate(@RequestBody MonitorBizConfig monitorBizConfig) {
        monitorBizConfigService.addOrUpdate(monitorBizConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) throws IOException {
        Assert.notNull(id, "id不能为空");
        monitorBizConfigService.deleteById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        monitorBizConfigService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param monitorBizConfig
    */
    @PostMapping("/modify")
    public void modify(@RequestBody MonitorBizConfig monitorBizConfig) {
        monitorBizConfigService.updateById(monitorBizConfig);
    }

    /**
    * 分页查询
    * @param monitorBizConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<MonitorBizConfig> listPage(@RequestBody MonitorBizConfig monitorBizConfig) {
        return monitorBizConfigService.listPage(monitorBizConfig);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MonitorBizConfig> listAll() {
        return monitorBizConfigService.list();
    }

    /**
     * 开始任务
     * @throws IOException
     */
    @PostMapping("/startTask")
    public void startTask(@RequestBody MonitorBizConfig monitorBizConfig) throws IOException {
        List<Long> ids = new ArrayList<>();
        ids.add(monitorBizConfig.getMonitorBizConfigId());
        monitorBizConfigService.batchDealTask(ids, YesOrNo.Y);
    }

    /**
     * 停止任务
     * @throws IOException
     */
    @PostMapping("/stopTask")
    public void stopTask(@RequestBody MonitorBizConfig monitorBizConfig) throws IOException {
        List<Long> ids = new ArrayList<>();
        ids.add(monitorBizConfig.getMonitorBizConfigId());
        monitorBizConfigService.batchDealTask(ids, YesOrNo.N);
    }

}
