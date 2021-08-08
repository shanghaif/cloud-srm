package com.midea.cloud.srm.logistics.soap.tms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsRegionService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  tms行政区域表(tms系统的数据, 接口表) 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-30 11:05:37
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/tms-region")
public class TmsRegionController extends BaseController {

    @Autowired
    private ITmsRegionService iTmsRegionService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TmsRegion get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTmsRegionService.getById(id);
    }

    /**
    * 新增
    * @param tmsRegion
    */
    @PostMapping("/add")
    public void add(@RequestBody TmsRegion tmsRegion) {
        Long id = IdGenrator.generate();
        tmsRegion.setRegionId(id);
        iTmsRegionService.save(tmsRegion);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTmsRegionService.removeById(id);
    }

    /**
    * 修改
    * @param tmsRegion
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TmsRegion tmsRegion) {
        iTmsRegionService.updateById(tmsRegion);
    }

    /**
    * 分页查询
    * @param tmsRegion
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TmsRegion> listPage(@RequestBody TmsRegion tmsRegion) {
        PageUtil.startPage(tmsRegion.getPageNum(), tmsRegion.getPageSize());
        QueryWrapper<TmsRegion> wrapper = new QueryWrapper<TmsRegion>(tmsRegion);
        return new PageInfo<TmsRegion>(iTmsRegionService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<TmsRegion> listAll() {
        return iTmsRegionService.list();
    }

}
