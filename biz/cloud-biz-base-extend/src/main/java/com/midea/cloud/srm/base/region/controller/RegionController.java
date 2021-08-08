package com.midea.cloud.srm.base.region.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.region.service.IRegionService;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaPramDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.base.region.entity.Region;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  国家地区表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 10:15:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/region")
public class RegionController extends BaseController {

    @Autowired
    private IRegionService iRegionService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Region get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRegionService.getById(id);
    }

    /**
    * 新增
    * @param region
    */
    @PostMapping("/add")
    public void add(Region region) {
        Long id = IdGenrator.generate();
        region.setRegionId(id);
        iRegionService.save(region);
    }

    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRegionService.removeById(id);
    }

    /**
    * 修改
    * @param region
    */
    @PostMapping("/modify")
    public void modify(Region region) {
        iRegionService.updateById(region);
    }

    /**
    * 分页查询
    * @param region
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Region> listPage(Region region) {
        PageUtil.startPage(region.getPageNum(), region.getPageSize());
        QueryWrapper<Region> wrapper = new QueryWrapper<Region>(region);
        return new PageInfo<Region>(iRegionService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Region> listAll() {
        return iRegionService.list();
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/queryRegion")
    public List<AreaDTO> listAll(@RequestParam("queryType") String queryType, @RequestParam(name = "parentId",required = false) Long parentId) {
        return iRegionService.getRegionsByType(queryType,parentId);
    }

    /**
     * 查询所有（新增，个性化定制）
     * @return
     */
    @PostMapping("/queryRegionByParam")
    public List<AreaDTO> listAllByParam(String queryType, Long parentId) {
        return iRegionService.getAllRegionsByType(queryType,parentId);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping ("/queryRegionById")
    public List<AreaDTO> queryRegionById(@RequestBody AreaPramDTO areaPramDTO) {
        return iRegionService.getRegionsByType(areaPramDTO.getQueryType(),areaPramDTO.getParentId());
    }

    /**
     * 检查城市
     * @return
     */
    @PostMapping ("/checkCity")
    public List<AreaDTO> checkCity(@RequestBody CityParamDto cityParamDto) {
        return iRegionService.checkCity(cityParamDto);
    }

    /**
     * 获取省数据
     * @param provinceNameList
     * @return
     */
    @PostMapping("/listProvinceByNameBatch")
    public List<Region> listProvinceByNameBatch(@RequestBody List<String> provinceNameList){
        return iRegionService.listProvinceByNameBatch(provinceNameList);
    }


    /**
     * 获取市数据
     * @param cityNameList
     * @return
     */
    @PostMapping("/listCityByNameBatch")
    public List<Region> listCityByNameBatch(@RequestBody List<String> cityNameList){
        return iRegionService.listCityByNameBatch(cityNameList);
    }

    /**
     * 获取县数据
     * @param countyNameList
     * @return
     */
    @PostMapping("/listCountyByNameBatch")
    public List<Region> listCountyByNameBatch(@RequestBody List<String> countyNameList){
        return iRegionService.listCountyByNameBatch(countyNameList);
    }

    /**
     * 根据名字查找地名信息
     * @return areaName+parentId  : Region
     */
    @PostMapping("/queryRegionByName")
    public Map<String,Region> queryRegionByName(@RequestBody List<String> areaNames) {
        return iRegionService.queryRegionByName(areaNames);
    }


}
