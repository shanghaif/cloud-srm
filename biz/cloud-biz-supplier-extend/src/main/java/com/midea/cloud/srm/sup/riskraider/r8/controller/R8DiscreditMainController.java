package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditMainService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditMain;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体企业失信信息表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/r8-discredit-main")
public class R8DiscreditMainController extends BaseController {

    @Autowired
    private IR8DiscreditMainService iR8DiscreditMainService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditMain get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditMainService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditMain
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditMain r8DiscreditMain) {
        Long id = IdGenrator.generate();
        r8DiscreditMain.setDiscreditMainId(id);
        iR8DiscreditMainService.save(r8DiscreditMain);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditMainService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditMain
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditMain r8DiscreditMain) {
        iR8DiscreditMainService.updateById(r8DiscreditMain);
    }

    /**
    * 分页查询
    * @param r8DiscreditMain
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditMain> listPage(@RequestBody R8DiscreditMain r8DiscreditMain) {
        PageUtil.startPage(r8DiscreditMain.getPageNum(), r8DiscreditMain.getPageSize());
        QueryWrapper<R8DiscreditMain> wrapper = new QueryWrapper<R8DiscreditMain>(r8DiscreditMain);
        return new PageInfo<R8DiscreditMain>(iR8DiscreditMainService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditMain> listAll() { 
        return iR8DiscreditMainService.list();
    }
 
}
