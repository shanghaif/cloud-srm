package com.midea.cloud.srm.base.serviceconfig.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.model.base.serviceconfig.entity.ServiceConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;
import com.midea.cloud.srm.base.serviceconfig.service.IBaseServiceConfigService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  产品与服务以及供方管理配置 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-21 19:02:27
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/serviceConfig/base-service-config")
public class BaseServiceConfigController extends BaseController {

    @Autowired
    private IBaseServiceConfigService iBaseServiceConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ServiceConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBaseServiceConfigService.getById(id);
    }

    /**
    * 新增
    * @param serviceConfig
    */
    @PostMapping("/add")
    public void add(@RequestBody ServiceConfig serviceConfig) {
        Long id = IdGenrator.generate();
        serviceConfig.setServiceLevelId(id);
        serviceConfig.setLastUpdateDate(new Date());
        iBaseServiceConfigService.save(serviceConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBaseServiceConfigService.removeById(id);
    }

    /**
    * 修改
    * @param serviceConfig
    */
    @PostMapping("/modify")
    public void modify(ServiceConfig serviceConfig) {
        iBaseServiceConfigService.updateById(serviceConfig);
    }

    /**
    * 分页查询
    * @param serviceConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ServiceConfig> listPage(ServiceConfig serviceConfig) {
        PageUtil.startPage(serviceConfig.getPageNum(), serviceConfig.getPageSize());
        QueryWrapper<ServiceConfig> wrapper = new QueryWrapper<ServiceConfig>(serviceConfig);
        return new PageInfo<ServiceConfig>(iBaseServiceConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @GetMapping("/listAll")
    public List<ServiceConfig> listAll() {
        return iBaseServiceConfigService.list();
    }

    /**
     * 保存或更新
     * @param serviceConfig
     */
    @PostMapping("/saveForOne")
    public void saveForOne(@RequestBody ServiceConfig serviceConfig) {
        iBaseServiceConfigService.saveForOne(serviceConfig);
    }
 
}
