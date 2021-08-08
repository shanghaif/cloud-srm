package com.midea.cloud.srm.inq.inquiry.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaSourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaSourceService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额调整-关联寻源列表 前端控制器
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 16:57:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/quotaSource")
public class QuotaSourceController extends BaseController {

    @Autowired
    private IQuotaSourceService iQuotaSourceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuotaSource get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaSourceService.getById(id);
    }

    /**
    * 新增
    * @param quotaSource
    */
    @PostMapping("/add")
    public void add(@RequestBody QuotaSource quotaSource) {
        Long id = IdGenrator.generate();
        quotaSource.setQuotaSourceId(id);
        iQuotaSourceService.save(quotaSource);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaSourceService.removeById(id);
    }

    /**
    * 修改
    * @param quotaSource
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaSource quotaSource) {
        iQuotaSourceService.updateById(quotaSource);
    }

    /**
    * 分页查询
    * @param quotaSource
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuotaSourceDTO> listPage(@RequestBody QuotaSource quotaSource) {
        PageUtil.startPage(quotaSource.getPageNum(), quotaSource.getPageSize());
        return new PageInfo<QuotaSourceDTO>(iQuotaSourceService.quotaSourceList(quotaSource));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuotaSource> listAll() { 
        return iQuotaSourceService.list();
    }
 
}
