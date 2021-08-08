package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaRestrictionsService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaRestrictions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额-配额上下限表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:41:44
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/quotaRestrictions")
public class QuotaRestrictionsController extends BaseController {

    @Autowired
    private IQuotaRestrictionsService iQuotaRestrictionsService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuotaRestrictions get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaRestrictionsService.getById(id);
    }

    /**
    * 新增或保存
    * @param quotaRestrictions
    */
    @PostMapping("/quotaRestrictionsAdd")
    public void quotaRestrictionsAdd(@RequestBody List<QuotaRestrictions> quotaRestrictions) {
        Assert.isTrue(CollectionUtils.isNotEmpty(quotaRestrictions),"保存或修改的对象不能为空。");
        iQuotaRestrictionsService.quotaRestrictionsAdd(quotaRestrictions);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaRestrictionsService.removeById(id);
    }

    /**
    * 修改
    * @param quotaRestrictions
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaRestrictions quotaRestrictions) {
        iQuotaRestrictionsService.updateById(quotaRestrictions);
    }

    /**
    * 分页查询
    * @param quotaRestrictions
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuotaRestrictions> listPage(@RequestBody QuotaRestrictions quotaRestrictions) {
        PageUtil.startPage(quotaRestrictions.getPageNum(), quotaRestrictions.getPageSize());
        QueryWrapper<QuotaRestrictions> wrapper = new QueryWrapper<QuotaRestrictions>(quotaRestrictions);
        return new PageInfo<QuotaRestrictions>(iQuotaRestrictionsService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuotaRestrictions> listAll() { 
        return iQuotaRestrictionsService.list();
    }
 
}
