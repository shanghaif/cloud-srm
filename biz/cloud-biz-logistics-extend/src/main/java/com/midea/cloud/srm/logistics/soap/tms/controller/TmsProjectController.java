package com.midea.cloud.srm.logistics.soap.tms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsProjectService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  tms项目表(tms系统的数据) 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 14:31:06
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/tms-project")
public class TmsProjectController extends BaseController {

    @Autowired
    private ITmsProjectService iTmsProjectService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TmsProject get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTmsProjectService.getById(id);
    }

    /**
    * 新增
    * @param tmsProject
    */
    @PostMapping("/add")
    public void add(@RequestBody TmsProject tmsProject) {
        Long id = IdGenrator.generate();
        tmsProject.setProjectId(id);
        iTmsProjectService.save(tmsProject);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTmsProjectService.removeById(id);
    }

    /**
    * 修改
    * @param tmsProject
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TmsProject tmsProject) {
        iTmsProjectService.updateById(tmsProject);
    }

    /**
    * 分页查询
    * @param tmsProject
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TmsProject> listPage(@RequestBody TmsProject tmsProject) {
        PageUtil.startPage(tmsProject.getPageNum(), tmsProject.getPageSize());
        QueryWrapper<TmsProject> wrapper = new QueryWrapper<TmsProject>(tmsProject);
        return new PageInfo<TmsProject>(iTmsProjectService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<TmsProject> listAll() {
        return iTmsProjectService.list();
    }

}
