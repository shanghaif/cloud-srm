package com.midea.cloud.flow.workflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.flow.workflow.service.ITemplateInstanceService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  流程实例表 前端控制器
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 14:05:14
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/flow/TemplateInstance")
public class TemplateInstanceController extends BaseController {

    @Autowired
    private ITemplateInstanceService iTemplateInstanceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TemplateInstance get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTemplateInstanceService.getById(id);
    }

    /**
    * 新增
    * @param templateInstance
    */
    @PostMapping("/add")
    public void add(@RequestBody TemplateInstance templateInstance) {
        Long id = IdGenrator.generate();
        templateInstance.setInstanceId(id);
        iTemplateInstanceService.save(templateInstance);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTemplateInstanceService.removeById(id);
    }

    /**
    * 修改
    * @param templateInstance
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TemplateInstance templateInstance) {
        iTemplateInstanceService.updateById(templateInstance);
    }

    /**
    * 分页查询
    * @param templateInstance
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TemplateInstance> listPage(@RequestBody TemplateInstance templateInstance) {
        PageUtil.startPage(templateInstance.getPageNum(), templateInstance.getPageSize());
        QueryWrapper<TemplateInstance> wrapper = new QueryWrapper<TemplateInstance>(templateInstance);
        return new PageInfo<TemplateInstance>(iTemplateInstanceService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<TemplateInstance> listAll() {
        return iTemplateInstanceService.list();
    }
 
}
