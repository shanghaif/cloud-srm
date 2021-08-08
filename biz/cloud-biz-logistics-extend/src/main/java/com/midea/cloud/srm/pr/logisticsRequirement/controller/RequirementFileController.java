package com.midea.cloud.srm.pr.logisticsRequirement.controller;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementFile;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementFileService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物流采购申请附件 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
@RestController(value = "LogisticsRequirementFileController")
@RequestMapping("/pr/requirement-file")
public class RequirementFileController extends BaseController {

    @Autowired
    private IRequirementFileService iRequirementFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsRequirementFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequirementFileService.getById(id);
    }

    /**
    * 新增
    * @param requirementFile
    */
    @PostMapping("/add")
    public void add(@RequestBody LogisticsRequirementFile requirementFile) {
        Long id = IdGenrator.generate();
        requirementFile.setRequirementFileId(id);
        iRequirementFileService.save(requirementFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequirementFileService.removeById(id);
    }

    /**
    * 修改
    * @param requirementFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LogisticsRequirementFile requirementFile) {
        iRequirementFileService.updateById(requirementFile);
    }

    /**
    * 分页查询
    * @param requirementFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LogisticsRequirementFile> listPage(@RequestBody LogisticsRequirementFile requirementFile) {
        PageUtil.startPage(requirementFile.getPageNum(), requirementFile.getPageSize());
        QueryWrapper<LogisticsRequirementFile> wrapper = new QueryWrapper<LogisticsRequirementFile>(requirementFile);
        return new PageInfo<LogisticsRequirementFile>(iRequirementFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsRequirementFile> listAll() {
        return iRequirementFileService.list();
    }
 
}
