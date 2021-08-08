package com.midea.cloud.srm.sup.riskraider.monitor.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.riskraider.monitor.HaveMonitor;
import com.midea.cloud.srm.sup.riskraider.monitor.service.IHaveMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  风险雷达以监控企业列表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-20 18:39:44
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/have-monitor")
public class HaveMonitorController extends BaseController {

    @Autowired
    private IHaveMonitorService iHaveMonitorService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public HaveMonitor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iHaveMonitorService.getById(id);
    }

    /**
    * 新增
    * @param haveMonitor
    */
    @PostMapping("/add")
    public void add(@RequestBody HaveMonitor haveMonitor) {
        Long id = IdGenrator.generate();
        iHaveMonitorService.save(haveMonitor);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iHaveMonitorService.removeById(id);
    }

    /**
    * 修改
    * @param haveMonitor
    */
    @PostMapping("/modify")
    public void modify(@RequestBody HaveMonitor haveMonitor) {
        iHaveMonitorService.updateById(haveMonitor);
    }

    /**
    * 分页查询
    * @param haveMonitor
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<HaveMonitor> listPage(@RequestBody HaveMonitor haveMonitor) {
        PageUtil.startPage(haveMonitor.getPageNum(), haveMonitor.getPageSize());
        QueryWrapper<HaveMonitor> wrapper = new QueryWrapper<HaveMonitor>(haveMonitor);
        return new PageInfo<HaveMonitor>(iHaveMonitorService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<HaveMonitor> listAll() { 
        return iHaveMonitorService.list();
    }
 
}
