package com.midea.cloud.srm.bargaining.purchaser.techdiscuss.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.techdiscuss.service.ITechDiscussSupplierService;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.entity.TechDiscussSupplier;
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
 *  技术交流项目供应商表 前端控制器
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/techDiscuss/techDiscussSupplier")
public class TechDiscussSupplierController extends BaseController {

    @Autowired
    private ITechDiscussSupplierService iTechDiscussSupplierService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TechDiscussSupplier get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTechDiscussSupplierService.getById(id);
    }

    /**
    * 新增
    * @param techDiscussSupplier
    */
    @PostMapping("/add")
    public void add(@RequestBody TechDiscussSupplier techDiscussSupplier) {
        Long id = IdGenrator.generate();
        techDiscussSupplier.setSupplierId(id);
        iTechDiscussSupplierService.save(techDiscussSupplier);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTechDiscussSupplierService.removeById(id);
    }

    /**
    * 修改
    * @param techDiscussSupplier
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TechDiscussSupplier techDiscussSupplier) {
        iTechDiscussSupplierService.updateById(techDiscussSupplier);
    }

    /**
    * 分页查询
    * @param techDiscussSupplier
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TechDiscussSupplier> listPage(@RequestBody TechDiscussSupplier techDiscussSupplier) {
        PageUtil.startPage(techDiscussSupplier.getPageNum(), techDiscussSupplier.getPageSize());
        QueryWrapper<TechDiscussSupplier> wrapper = new QueryWrapper<TechDiscussSupplier>(techDiscussSupplier);
        return new PageInfo<TechDiscussSupplier>(iTechDiscussSupplierService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<TechDiscussSupplier> listAll() { 
        return iTechDiscussSupplierService.list();
    }

    /**
     * 根据项目id查询邀请供应商列表
     * @return
     */
    @GetMapping("/listByProjId")
    public List<TechDiscussSupplier> listByProjId(Long projId) {
        return iTechDiscussSupplierService.list(new QueryWrapper<>(new TechDiscussSupplier().setProjId(projId)));
    }

}
