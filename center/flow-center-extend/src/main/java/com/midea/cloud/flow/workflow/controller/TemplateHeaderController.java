package com.midea.cloud.flow.workflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  工作流模板头表 前端控制器
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
@RequestMapping("/flow/TemplateHeader")
public class TemplateHeaderController extends BaseController {

    @Autowired
    private ITemplateHeaderService iTemplateHeaderService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TemplateHeader get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTemplateHeaderService.getById(id);
    }

    /**
    * 新增
    * @param templateHeader
    */
    @PostMapping("/add")
    public void add(@RequestBody TemplateHeader templateHeader) {
        Long id = IdGenrator.generate();
        templateHeader.setTemplateHeadId(id);
        iTemplateHeaderService.save(templateHeader);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTemplateHeaderService.removeById(id);
    }

    /**
    * 修改
    * @param templateHeader
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TemplateHeader templateHeader) {
        iTemplateHeaderService.updateById(templateHeader);
    }

    /**
    * 分页查询
    * @param templateHeader
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TemplateHeader> listPage(@RequestBody TemplateHeader templateHeader) {
        PageUtil.startPage(templateHeader.getPageNum(), templateHeader.getPageSize());
        QueryWrapper<TemplateHeader> wrapper = new QueryWrapper<TemplateHeader>(templateHeader);
        return new PageInfo<TemplateHeader>(iTemplateHeaderService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<TemplateHeader> listAll() {
        return iTemplateHeaderService.list();
    }
 
}
