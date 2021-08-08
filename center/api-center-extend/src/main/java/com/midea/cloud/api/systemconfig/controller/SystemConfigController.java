package com.midea.cloud.api.systemconfig.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;
import com.midea.cloud.srm.model.common.BaseController;

/**
*  <pre>
 *  接口系统配置 前端控制器
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/systemconfig")
public class SystemConfigController extends BaseController {

    @Autowired
    private ISystemConfigService iSystemConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SystemConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSystemConfigService.getById(id);
    }

    /**
    * 新增
    * @param systemConfig
    */
    @PostMapping("/add")
    public void add(@RequestBody SystemConfig systemConfig) {
        Long id = IdGenrator.generate();
        systemConfig.setSystemId(id);
        iSystemConfigService.save(systemConfig);
    }
    
    /**
     * 新增
     * @param systemConfig
     */
     @PostMapping("/saveOrUpdate")
     public void saveOrUpdate(@RequestBody List<SystemConfig> systemConfigs) {
    	 if (null != systemConfigs && systemConfigs.size() > 0) {
    		 for (SystemConfig systemConfig : systemConfigs) {
    			 if(null != systemConfig && null != systemConfig.getSystemId()) {
    	    		 iSystemConfigService.updateById(systemConfig);
    	    	 } else {
    	    		 Long id = IdGenrator.generate();
    	             systemConfig.setSystemId(id);
    	             iSystemConfigService.save(systemConfig);
    	    	 }
    		 }
    	 }
     }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long systemId) {
        Assert.notNull(systemId, "systemId不能为空");
        iSystemConfigService.removeById(systemId);
    }

    /**
    * 修改
    * @param systemConfig
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SystemConfig systemConfig) {
        iSystemConfigService.updateById(systemConfig);
    }

    /**
    * 分页查询
    * @param systemConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SystemConfig> listPage(@RequestBody SystemConfig systemConfig) {
        PageUtil.startPage(systemConfig.getPageNum(), systemConfig.getPageSize());
        String systemName = systemConfig.getSystemName();
        systemConfig.setSystemName(null);
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<SystemConfig>(systemConfig);
        if (StringUtil.isNotEmpty(systemName)) {
        	wrapper.like("SYSTEM_NAME", systemName);
        }
        return new PageInfo<SystemConfig>(iSystemConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SystemConfig> listAll() { 
        return iSystemConfigService.list();
    }
}
