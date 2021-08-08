package com.midea.cloud.api.interfacelog.controller;

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
import com.midea.cloud.api.interfacelog.service.IInterfaceLogLineService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLogLine;
import com.midea.cloud.srm.model.common.BaseController;

/**
*  <pre>
 *  接口日志行表 前端控制器
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 10:58:43
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/interface-log-line")
public class InterfaceLogLineController extends BaseController {

    @Autowired
    private IInterfaceLogLineService iInterfaceLogLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public InterfaceLogLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iInterfaceLogLineService.getById(id);
    }

    /**
    * 新增
    * @param interfaceLogLine
    */
    @PostMapping("/add")
    public void add(@RequestBody InterfaceLogLine interfaceLogLine) {
        Long id = IdGenrator.generate();
        interfaceLogLine.setLogLineId(id);
        iInterfaceLogLineService.save(interfaceLogLine);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iInterfaceLogLineService.removeById(id);
    }

    /**
    * 修改
    * @param interfaceLogLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody InterfaceLogLine interfaceLogLine) {
        iInterfaceLogLineService.updateById(interfaceLogLine);
    }

    /**
    * 分页查询
    * @param interfaceLogLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<InterfaceLogLine> listPage(@RequestBody InterfaceLogLine interfaceLogLine) {
        PageUtil.startPage(interfaceLogLine.getPageNum(), interfaceLogLine.getPageSize());
        QueryWrapper<InterfaceLogLine> wrapper = new QueryWrapper<InterfaceLogLine>(interfaceLogLine);
        return new PageInfo<InterfaceLogLine>(iInterfaceLogLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<InterfaceLogLine> listAll() { 
        return iInterfaceLogLineService.list();
    }
 
}
