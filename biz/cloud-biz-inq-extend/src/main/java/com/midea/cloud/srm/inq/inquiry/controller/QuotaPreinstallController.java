package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaPreinstallService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaPreinstall;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额-预设比例表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/quotaPreinstall")
public class QuotaPreinstallController extends BaseController {

    @Autowired
    private IQuotaPreinstallService iQuotaPreinstallService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuotaPreinstall get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaPreinstallService.getById(id);
    }

    /**
    * 新增或保存
    * @param quotaPreinstall
    */
    @PostMapping("/quotaPreinstallAdd")
    public void quotaPreinstallAdd(@RequestBody List<QuotaPreinstall> quotaPreinstall) {
        Assert.isTrue(CollectionUtils.isNotEmpty(quotaPreinstall),"保存或修改的对象不能为空。");
        iQuotaPreinstallService.quotaPreinstallAdd(quotaPreinstall);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaPreinstallService.removeById(id);
    }

    /**
    * 修改
    * @param quotaPreinstall
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaPreinstall quotaPreinstall) {
        iQuotaPreinstallService.updateById(quotaPreinstall);
    }

    /**
    * 分页查询
    * @param quotaPreinstall
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuotaPreinstall> listPage(@RequestBody QuotaPreinstall quotaPreinstall) {
        PageUtil.startPage(quotaPreinstall.getPageNum(), quotaPreinstall.getPageSize());
        QueryWrapper<QuotaPreinstall> wrapper = new QueryWrapper<QuotaPreinstall>(quotaPreinstall);
        return new PageInfo<QuotaPreinstall>(iQuotaPreinstallService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuotaPreinstall> listAll() { 
        return iQuotaPreinstallService.list();
    }
 
}
