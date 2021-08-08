package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaRebateService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaRebate;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额-预估返利 前端控制器
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
@RequestMapping("/inquiry/quotaRebate")
public class QuotaRebateController extends BaseController {

    @Autowired
    private IQuotaRebateService iQuotaRebateService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuotaRebate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaRebateService.getById(id);
    }

    /**
    * 新增或保存
    * @param quotaRebate
    */
    @PostMapping("/quotaRebateAdd")
    public void quotaRebateAdd(@RequestBody List<QuotaRebate> quotaRebate) {
        Assert.isTrue(CollectionUtils.isNotEmpty(quotaRebate),"保存或修改的对象不能为空。");
        iQuotaRebateService.quotaRebateAdd(quotaRebate);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaRebateService.removeById(id);
    }

    /**
    * 修改
    * @param quotaRebate
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaRebate quotaRebate) {
        iQuotaRebateService.updateById(quotaRebate);
    }

    /**
    * 分页查询
    * @param quotaRebate
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuotaRebate> listPage(@RequestBody QuotaRebate quotaRebate) {
        PageUtil.startPage(quotaRebate.getPageNum(), quotaRebate.getPageSize());
        QueryWrapper<QuotaRebate> wrapper = new QueryWrapper<QuotaRebate>(quotaRebate);
        return new PageInfo<QuotaRebate>(iQuotaRebateService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuotaRebate> listAll() { 
        return iQuotaRebateService.list();
    }
 
}
