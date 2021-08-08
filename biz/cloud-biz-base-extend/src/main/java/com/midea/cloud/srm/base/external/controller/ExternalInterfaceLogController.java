package com.midea.cloud.srm.base.external.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.external.service.IExternalInterfaceLogService;
import com.midea.cloud.srm.model.base.external.entity.ExternalInterfaceLog;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  外部接口交互日志表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 16:40:11
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base-anon/external/external-interface-log")
public class ExternalInterfaceLogController extends BaseController {

    @Autowired
    private IExternalInterfaceLogService iExternalInterfaceLogService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ExternalInterfaceLog get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iExternalInterfaceLogService.getById(id);
    }

    /**
    * 新增
    * @param externalInterfaceLog
    */
    @PostMapping("/add")
    public void add(@RequestBody ExternalInterfaceLog externalInterfaceLog) {
        Long id = IdGenrator.generate();
        externalInterfaceLog.setExternalInterfaceLogId(id);
        iExternalInterfaceLogService.save(externalInterfaceLog);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iExternalInterfaceLogService.removeById(id);
    }

    /**
    * 修改
    * @param externalInterfaceLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ExternalInterfaceLog externalInterfaceLog) {
        iExternalInterfaceLogService.updateById(externalInterfaceLog);
    }

    /**
    * 分页查询
    * @param externalInterfaceLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ExternalInterfaceLog> listPage(@RequestBody ExternalInterfaceLog externalInterfaceLog) {
        PageUtil.startPage(externalInterfaceLog.getPageNum(), externalInterfaceLog.getPageSize());
        QueryWrapper<ExternalInterfaceLog> wrapper = new QueryWrapper<ExternalInterfaceLog>(externalInterfaceLog);
        return new PageInfo<ExternalInterfaceLog>(iExternalInterfaceLogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ExternalInterfaceLog> listAll() { 
        return iExternalInterfaceLogService.list();
    }
 
}
