package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  招标工作小组表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 13:59:12
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/group")
public class GroupController extends BaseController {

    @Autowired
    private IGroupService iGroupService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public Group get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iGroupService.getById(id);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iGroupService.removeById(id);
    }

    /**
     * 分页查询
     *
     * @param group
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Group> listPage(@RequestBody Group group) {
        PageUtil.startPage(group.getPageNum(), group.getPageSize());
        QueryWrapper<Group> wrapper = new QueryWrapper<Group>(group);
        return new PageInfo<Group>(iGroupService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<Group> listAll(@RequestBody Group group) {
        QueryWrapper<Group> wrapper = new QueryWrapper<Group>(group);
        return iGroupService.list(wrapper);
    }

}
