package com.midea.cloud.srm.supauth.review.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.BankJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteJournal;
import com.midea.cloud.srm.supauth.review.mapper.SiteJournalMapper;
import com.midea.cloud.srm.supauth.review.service.ISiteJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  地点信息日志表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-23 16:58:56
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/site-journal")
public class SiteJournalController extends BaseController {

    @Autowired
    private ISiteJournalService iSiteJournalService;
    @Autowired
    private SiteJournalMapper journalMapper;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SiteJournal get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSiteJournalService.getById(id);
    }

    /**
    * 新增
    * @param siteJournal
    */
    @PostMapping("/add")
    public void add(@RequestBody SiteJournal siteJournal) {
        Long id = IdGenrator.generate();
        siteJournal.setSiteJournalId(id);
        iSiteJournalService.save(siteJournal);
    }
    
    /**
    * 删除
    * @param siteJournalId
    */
    @GetMapping("/deleteSiteJournal")
    public void delete(Long siteJournalId) {
        Assert.notNull(siteJournalId, "id不能为空");
        iSiteJournalService.removeById(siteJournalId);
    }

    /**
    * 修改
    * @param siteJournal
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SiteJournal siteJournal) {
        iSiteJournalService.updateById(siteJournal);
    }

    /**
    * 分页查询
    * @param siteJournal
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SiteJournal> listPage(@RequestBody SiteJournal siteJournal) {
        PageUtil.startPage(siteJournal.getPageNum(), siteJournal.getPageSize());
        QueryWrapper<SiteJournal> wrapper = new QueryWrapper<SiteJournal>(siteJournal);
        return new PageInfo<SiteJournal>(iSiteJournalService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SiteJournal> listAll() { 
        return iSiteJournalService.list();
    }

    /**
     * 根据reviewFormId, vendorId获取地点信息（从地点档案表查询）
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    @GetMapping("/listSiteJournal")
    public List<SiteJournal> listSiteJournal(Long reviewFormId, Long vendorId) {
        return iSiteJournalService.listSiteJournal(reviewFormId, vendorId);
    }

    @PostMapping("/deleteDuplicateSiteInfo")
    public void deleteDuplicateSiteInfo(){
        List<Map<String, Object>> duplicateSiteInfo = journalMapper.getDuplicateSiteInfo();
        List<Long> shoudlDeleteIds=new LinkedList<>();
        for (Map<String, Object> map : duplicateSiteInfo) {
            String[] ids = map.get("ids").toString().split(",");
            for (int i = 1; i < ids.length; i++) {
                shoudlDeleteIds.add(Long.valueOf(ids[i]));
            }
        }

    }

}
