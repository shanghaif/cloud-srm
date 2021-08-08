package com.midea.cloud.srm.sup.riskraider.log.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.riskraider.log.entity.RiskInfoLog;
import com.midea.cloud.srm.sup.riskraider.log.service.IRiskInfoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  风险雷达日志表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-14 14:35:38
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cloud-biz-supplier/risk-info-log")
public class RiskInfoLogController extends BaseController {

    @Autowired
    private IRiskInfoLogService iRiskInfoLogService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public RiskInfoLog get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRiskInfoLogService.getById(id);
    }

    /**
    * 新增
    * @param riskInfoLog
    */
    @PostMapping("/add")
    public void add(@RequestBody RiskInfoLog riskInfoLog) {
        Long id = IdGenrator.generate();
        riskInfoLog.setLogId(id);
        iRiskInfoLogService.save(riskInfoLog);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRiskInfoLogService.removeById(id);
    }

    /**
    * 修改
    * @param riskInfoLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody RiskInfoLog riskInfoLog) {
        iRiskInfoLogService.updateById(riskInfoLog);
    }

    /**
    * 分页查询
    * @param riskInfoLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<RiskInfoLog> listPage(@RequestBody RiskInfoLog riskInfoLog) {
        PageUtil.startPage(riskInfoLog.getPageNum(), riskInfoLog.getPageSize());
        QueryWrapper<RiskInfoLog> wrapper = new QueryWrapper<RiskInfoLog>(riskInfoLog);
        return new PageInfo<RiskInfoLog>(iRiskInfoLogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<RiskInfoLog> listAll() { 
        return iRiskInfoLogService.list();
    }
 
}
