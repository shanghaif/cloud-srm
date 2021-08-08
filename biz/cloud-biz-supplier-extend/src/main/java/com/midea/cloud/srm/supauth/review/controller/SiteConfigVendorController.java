package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteConfigVendor;
import com.midea.cloud.srm.supauth.review.service.ISiteConfigVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  现场评审周期设置(按供应商) 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 16:40:00
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/siteConfigVendor")
public class SiteConfigVendorController extends BaseController {

    @Autowired
    private ISiteConfigVendorService iSiteConfigVendorService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SiteConfigVendor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSiteConfigVendorService.getById(id);
    }

    /**
    * 新增
    * @param siteConfigVendor
    */
    @PostMapping("/add")
    public void add(@RequestBody SiteConfigVendor siteConfigVendor) {
        Long id = IdGenrator.generate();
        siteConfigVendor.setConfigVendorId(id);
        iSiteConfigVendorService.save(siteConfigVendor);
    }
    
    /**
    * 删除
    * @param configVendorId
    */
    @GetMapping("/delete")
    public void delete(Long configVendorId) {
        Assert.notNull(configVendorId, "configVendorId不能为空");
        iSiteConfigVendorService.removeById(configVendorId);
    }

    /**
    * 修改
    * @param siteConfigVendor
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SiteConfigVendor siteConfigVendor) {
        iSiteConfigVendorService.updateById(siteConfigVendor);
    }

    /**
    * 分页查询
    * @param siteConfigVendor
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SiteConfigVendor> listPage(@RequestBody SiteConfigVendor siteConfigVendor) {
        PageUtil.startPage(siteConfigVendor.getPageNum(), siteConfigVendor.getPageSize());
        QueryWrapper<SiteConfigVendor> wrapper = new QueryWrapper<SiteConfigVendor>(siteConfigVendor);
        return new PageInfo<SiteConfigVendor>(iSiteConfigVendorService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SiteConfigVendor> listAll() { 
        return iSiteConfigVendorService.list();
    }

    /**
     * 新增或编辑
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdateSiteConfigVendor(@RequestBody SiteConfigVendor siteConfigVendor) {
        iSiteConfigVendorService.saveOrUpdateSiteConfigVendor(siteConfigVendor);
    }

    /**
     *
     * @param siteConfigVendor
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<SiteConfigVendor> listPageByParm(@RequestBody SiteConfigVendor siteConfigVendor) {
        return iSiteConfigVendorService.listPageByParm(siteConfigVendor);
    }
}
