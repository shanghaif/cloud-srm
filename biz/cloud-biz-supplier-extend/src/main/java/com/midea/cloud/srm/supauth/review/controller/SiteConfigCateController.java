package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteConfigCate;
import com.midea.cloud.srm.supauth.review.service.ISiteConfigCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  现场评审周期设置(按品类) 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 16:38:22
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/siteConfigCate")
public class SiteConfigCateController extends BaseController {

    @Autowired
    private ISiteConfigCateService iSiteConfigCateService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SiteConfigCate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSiteConfigCateService.getById(id);
    }

    /**
    * 新增
    * @param siteConfigCate
    */
    @PostMapping("/add")
    public void add(@RequestBody SiteConfigCate siteConfigCate) {
        Long id = IdGenrator.generate();
        siteConfigCate.setConfigCateId(id);
        iSiteConfigCateService.save(siteConfigCate);
    }
    
    /**
    * 删除
    * @param configCateId
    */
    @GetMapping("/delete")
    public void delete(Long configCateId) {
        Assert.notNull(configCateId, "configCateId不能为空");
        iSiteConfigCateService.removeById(configCateId);
    }

    /**
    * 修改
    * @param siteConfigCate
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SiteConfigCate siteConfigCate) {
        iSiteConfigCateService.updateById(siteConfigCate);
    }

    /**
    * 分页查询
    * @param siteConfigCate
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SiteConfigCate> listPage(@RequestBody SiteConfigCate siteConfigCate) {
        PageUtil.startPage(siteConfigCate.getPageNum(), siteConfigCate.getPageSize());
        QueryWrapper<SiteConfigCate> wrapper = new QueryWrapper<SiteConfigCate>(siteConfigCate);
        return new PageInfo<SiteConfigCate>(iSiteConfigCateService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SiteConfigCate> listAll() { 
        return iSiteConfigCateService.list();
    }

    /**
     * 新增或编辑
     * @param siteConfigCate
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdateSiteConfigCate(@RequestBody SiteConfigCate siteConfigCate) {
        iSiteConfigCateService.saveOrUpdateSiteConfigCate(siteConfigCate);
    }

    /**
     * 条件分页查询
     * @param siteConfigCate
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<SiteConfigCate> listPageByParm(@RequestBody SiteConfigCate siteConfigCate) {
        return iSiteConfigCateService.listPageByParm(siteConfigCate);
    }
}
