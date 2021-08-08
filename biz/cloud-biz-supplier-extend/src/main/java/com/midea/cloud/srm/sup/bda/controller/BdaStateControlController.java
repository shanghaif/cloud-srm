package com.midea.cloud.srm.sup.bda.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.bda.entity.BdaStateControl;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.sup.bda.service.IBdaStateControlService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  业务状态控制 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 10:17:03
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bda/bdaState")
public class BdaStateControlController extends BaseController {

    @Autowired
    private IBdaStateControlService iBdaStateControlService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BdaStateControl get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBdaStateControlService.getById(id);
    }

    /**
    * 新增
    * @param bdaStateControl
    */
    @PostMapping("/add")
    public void add(BdaStateControl bdaStateControl) {
        Long id = IdGenrator.generate();
        bdaStateControl.setStateControlId(id);
        iBdaStateControlService.save(bdaStateControl);
    }
    
    /**
    * 删除
    * @param stateControlId
    */
    @GetMapping("/delete")
    public void delete(Long stateControlId) {
        Assert.notNull(stateControlId, "id不能为空");
        iBdaStateControlService.removeById(stateControlId);
    }

    /**
    * 修改
    * @param bdaStateControl
    */
    @PostMapping("/modify")
    public void modify(BdaStateControl bdaStateControl) {
        iBdaStateControlService.updateById(bdaStateControl);
    }

    /**
    * 分页查询
    * @param bdaStateControl
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BdaStateControl> listPage(BdaStateControl bdaStateControl) {
        PageUtil.startPage(bdaStateControl.getPageNum(), bdaStateControl.getPageSize());
        QueryWrapper<BdaStateControl> wrapper = new QueryWrapper<BdaStateControl>(bdaStateControl);
        return new PageInfo<BdaStateControl>(iBdaStateControlService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BdaStateControl> listAll() { 
        return iBdaStateControlService.list();
    }


    /**
     * 分页查询
     * @param bdaStateControl
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<BdaStateControl> listPageByParam(@RequestBody BdaStateControl bdaStateControl) {
        return  iBdaStateControlService.listPageByParam(bdaStateControl);
    }

    /**
     * 保存或更新
     * @param bdaStateControl
     */
    @PostMapping("/saveOrUpdateBda")
    public void saveOrUpdateBda(@RequestBody BdaStateControl bdaStateControl) {
          iBdaStateControlService.saveOrUpdateBda(bdaStateControl);
    }
 
}
