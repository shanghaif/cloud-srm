package com.midea.cloud.srm.base.modulename.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.modulename.mapper.DemoMapper;
import com.midea.cloud.srm.base.modulename.service.IDemoService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.modulename.entity.Demo;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *  演示 前端控制器
 * </pre>
 *
 * @author huanghb14@example.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-12 22:53:25
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/moduleName/demo")
public class DemoController extends BaseController {

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private IDemoService iDemoService;

    @Autowired
    private DemoMapper demoMapper;

    @GetMapping("/countRecord")
    public int countRecord() {
        System.out.println("rbacClient:" + rbacClient.findByUsername("admin"));
        return iDemoService.countRecord();
    }

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public Demo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDemoService.getById(id);
    }

    /**
     * 新增
     *
     * @param demo
     */
    @PostMapping("/add")
    public void add(Demo demo) {
        Long id = IdGenrator.generate();
        iDemoService.save(demo);
    }

    /**
     * 新增2
     *
     * @param demo
     */
    @PostMapping("/add2")
    public void add2(Demo demo) {
        // 通过自定义mapper插入demo
        demoMapper.insertByDemo(new Demo().setAge(11L).setName("me").setSecret("秘密"));
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDemoService.removeById(id);
    }

    /**
     * 修改
     *
     * @param demo
     */
    @PostMapping("/modify")
    public void modify(Demo demo) {
        iDemoService.update(demo, new UpdateWrapper<Demo>().setEntity(new Demo().setAge(18L)));
    }

    /**
     * 分页查询
     *
     * @param demo
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Demo> listPage(Demo demo) {
        PageUtil.startPage(demo.getPageNum(), demo.getPageSize());
        QueryWrapper<Demo> wrapper = new QueryWrapper<Demo>(demo);
        return new PageInfo<Demo>(iDemoService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<Demo> listAll() {
        return demoMapper.selectAll();
//        return iDemoService.list();
    }

}
