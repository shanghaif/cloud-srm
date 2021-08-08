package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  供应商可供货组织品类关系日志 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:01:27
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/orgCateJournal")
public class OrgCateJournalController extends BaseController {

    @Autowired
    private IOrgCateJournalService iOrgCateJournalService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrgCateJournal get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrgCateJournalService.getById(id);
    }

    /**
    * 新增
    * @param orgCateJournal
    */
    @PostMapping("/add")
    public void add(OrgCateJournal orgCateJournal) {
        Long id = IdGenrator.generate();
        orgCateJournal.setOrgCateJournalId(id);
        iOrgCateJournalService.save(orgCateJournal);
    }
    
    /**
    * 删除
    * @param orgCateJournalId
    */
    @PostMapping("/delete")
    public void delete(Long orgCateJournalId) {
        Assert.notNull(orgCateJournalId, "orgCateJournalId不能为空");
        iOrgCateJournalService.removeById(orgCateJournalId);
    }

    /**
    * 修改
    * @param orgCateJournal
    */
    @PostMapping("/modify")
    public void modify(OrgCateJournal orgCateJournal) {
        iOrgCateJournalService.updateById(orgCateJournal);
    }

    /**
    * 分页查询
    * @param orgCateJournal
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrgCateJournal> listPage(OrgCateJournal orgCateJournal) {
        PageUtil.startPage(orgCateJournal.getPageNum(), orgCateJournal.getPageSize());
        QueryWrapper<OrgCateJournal> wrapper = new QueryWrapper<OrgCateJournal>(orgCateJournal);
        return new PageInfo<OrgCateJournal>(iOrgCateJournalService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrgCateJournal> listAll() { 
        return iOrgCateJournalService.list();
    }
 
}
