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
import com.midea.cloud.api.interfaceconfig.service.IInterfaceParamService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceParam;
import com.midea.cloud.srm.model.common.BaseController;

/**
*  <pre>
 *  接口参数 前端控制器
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
@RequestMapping("/interfaceparam")
public class InterfaceParamController extends BaseController {

    @Autowired
    private IInterfaceParamService iInterfaceParamService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public InterfaceParam get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iInterfaceParamService.getById(id);
    }

    /**
    * 新增
    * @param interfaceParam
    */
    @PostMapping("/add")
    public void add(@RequestBody InterfaceParam interfaceParam) {
        Long id = IdGenrator.generate();
        interfaceParam.setParamId(id);
        iInterfaceParamService.save(interfaceParam);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iInterfaceParamService.removeById(id);
    }

    /**
    * 修改
    * @param interfaceParam
    */
    @PostMapping("/modify")
    public void modify(@RequestBody InterfaceParam interfaceParam) {
        iInterfaceParamService.updateById(interfaceParam);
    }

    /**
    * 分页查询
    * @param interfaceParam
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<InterfaceParam> listPage(@RequestBody InterfaceParam interfaceParam) {
        PageUtil.startPage(interfaceParam.getPageNum(), interfaceParam.getPageSize());
        QueryWrapper<InterfaceParam> wrapper = new QueryWrapper<InterfaceParam>(interfaceParam);
        return new PageInfo<InterfaceParam>(iInterfaceParamService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<InterfaceParam> listAll() { 
        return iInterfaceParamService.list();
    }
 
}
