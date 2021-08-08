package com.midea.cloud.srm.sup.change.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.change.entity.SiteInfoChange;
import com.midea.cloud.srm.sup.change.service.ISiteInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  地点信息变更 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-25 10:44:41
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/site-info-change")
public class SiteInfoChangeController extends BaseController {

    @Autowired
    private ISiteInfoChangeService iSiteInfoChangeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SiteInfoChange get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSiteInfoChangeService.getById(id);
    }

    /**
    * 新增
    * @param siteInfoChange
    */
    @PostMapping("/add")
    public void add(@RequestBody SiteInfoChange siteInfoChange) {
        Long id = IdGenrator.generate();
        siteInfoChange.setSiteChangeId(id);
        iSiteInfoChangeService.save(siteInfoChange);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iSiteInfoChangeService.removeById(id);
    }

    /**
    * 修改
    * @param siteInfoChange
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SiteInfoChange siteInfoChange) {
        iSiteInfoChangeService.updateById(siteInfoChange);
    }

    /**
    * 分页查询
    * @param siteInfoChange
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SiteInfoChange> listPage(@RequestBody SiteInfoChange siteInfoChange) {
        PageUtil.startPage(siteInfoChange.getPageNum(), siteInfoChange.getPageSize());
        QueryWrapper<SiteInfoChange> wrapper = new QueryWrapper<SiteInfoChange>(siteInfoChange);
        return new PageInfo<SiteInfoChange>(iSiteInfoChangeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SiteInfoChange> listAll() { 
        return iSiteInfoChangeService.list();
    }
 
}
