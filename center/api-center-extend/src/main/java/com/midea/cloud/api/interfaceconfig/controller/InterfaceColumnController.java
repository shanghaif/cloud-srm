package com.midea.cloud.api.interfaceconfig.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.common.BaseController;

/**
*  <pre>
 *  接口字段 前端控制器
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/interfacecolumn")
public class InterfaceColumnController extends BaseController {

    @Autowired
    private IInterfaceColumnService iInterfaceColumnService;
    
    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public InterfaceColumn get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iInterfaceColumnService.getById(id);
    }

    /**
    * 新增
    * @param interfaceColumn
    */
    @PostMapping("/add")
    public void add(@RequestBody InterfaceColumn interfaceColumn) {
        Long id = IdGenrator.generate();
        interfaceColumn.setColumnId(id);
        iInterfaceColumnService.save(interfaceColumn);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iInterfaceColumnService.removeById(id);
    }

    /**
    * 修改
    * @param interfaceColumn
    */
    @PostMapping("/modify")
    public void modify(@RequestBody InterfaceColumn interfaceColumn) {
        iInterfaceColumnService.updateById(interfaceColumn);
    }

    /**
    * 分页查询
    * @param interfaceColumn
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<InterfaceColumn> listPage(@RequestBody InterfaceColumn interfaceColumn) {
        PageUtil.startPage(interfaceColumn.getPageNum(), interfaceColumn.getPageSize());
        QueryWrapper<InterfaceColumn> wrapper = new QueryWrapper<InterfaceColumn>(interfaceColumn);
        return new PageInfo<InterfaceColumn>(iInterfaceColumnService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<InterfaceColumn> listAll() { 
        return iInterfaceColumnService.list();
    }
 
}
