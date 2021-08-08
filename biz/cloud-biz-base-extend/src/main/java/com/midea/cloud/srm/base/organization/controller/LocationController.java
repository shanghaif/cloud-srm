package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.ILocationService;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  地址接口表 前端控制器
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:44:45
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/location")
public class LocationController extends BaseController {

    @Autowired
    private ILocationService iLocationService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/getLocation")
    public Location get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLocationService.getById(id);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/deleteLocation")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLocationService.removeById(id);
    }

    /**
    * 修改
    * @param location
    */
    @PostMapping("/updateLocation")
    public void modify(@RequestBody Location location) {
        iLocationService.updateById(location);
    }

    /**
    * 分页查询
    * @param location
    * @return
    */
    @PostMapping("/listLocationPage")
    public PageInfo<Location> listPage(@RequestBody Location location) {
        PageUtil.startPage(location.getPageNum(), location.getPageSize());
        QueryWrapper<Location> wrapper = new QueryWrapper<Location>(location);
        return new PageInfo<Location>(iLocationService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listLocationAll")
    public List<Location> listAll() {
        return iLocationService.list();
    }

    /**
     * 查询库存组织或业务实体下的地点
     * @return Location
     */
    @PostMapping("/getLocationsByOrganization")
    public List<Location> getLocationsByOrganization(@RequestBody Organization organization){
        Assert.notNull(organization, "所选组织为空！");
        return iLocationService.getLocationsByOrganization(organization);
    }

}
