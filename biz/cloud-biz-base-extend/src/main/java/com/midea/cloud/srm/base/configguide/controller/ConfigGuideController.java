package com.midea.cloud.srm.base.configguide.controller;

import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.configguide.service.IConfigGuideService;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配置导引表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-17 10:14:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/configGuide")
public class ConfigGuideController extends BaseController {

    @Autowired
    private IConfigGuideService iConfigGuideService;

    /**
    * 获取
    * @param configGuideId
    */
    @GetMapping("/get")
    public ConfigGuide get(Long configGuideId) {
        Assert.notNull(configGuideId, "id不能为空");
        return iConfigGuideService.getById(configGuideId);
    }

    /**
    * 新增
    * @param configGuide
    */
    @PostMapping("/add")
    public void add(@RequestBody ConfigGuide configGuide) {
        iConfigGuideService.save(configGuide);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iConfigGuideService.removeById(id);
    }

    /**
    * 修改
    * @param configGuide
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ConfigGuide configGuide) {
        iConfigGuideService.updateById(configGuide);
    }

    /**
     * 保存或更新
     * @param configGuide
     */
    @PostMapping("/saveOrUpdateConfigGuide")
    public void saveOrUpdateConfigGuide(@RequestBody ConfigGuide configGuide) {
        iConfigGuideService.saveOrUpdateConfigGuide(configGuide);
    }

    /**
    * 分页查询
    * @param configGuide
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ConfigGuide> listPage(@RequestBody ConfigGuide configGuide) {
        PageUtil.startPage(configGuide.getPageNum(), configGuide.getPageSize());
        QueryWrapper<ConfigGuide> wrapper = new QueryWrapper<ConfigGuide>(configGuide);
        return new PageInfo<ConfigGuide>(iConfigGuideService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ConfigGuide> listAll() { 
        return iConfigGuideService.list();
    }


    /**
     * 通过后台获取当前配置是否处理的信息
     */
    @GetMapping("/getInfoByUser")
    public ConfigGuide getInfoByUser() {
        return iConfigGuideService.getInfoByUser();
    }
 
}
