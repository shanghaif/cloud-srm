package com.midea.cloud.srm.logistics.expense.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
*  <pre>
 *  行政区域维护表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 12:03:51
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/region")
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
    public void add(@RequestBody Region region) {
        Long id = IdGenrator.generate();
        region.setRegionId(id);
        iRegionService.save(region);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRegionService.removeById(id);
    }

    /**
    * 修改
    * @param region
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Region region) {
        iRegionService.updateById(region);
    }

    /**
    * 分页条件查询
    * @param region
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<Region> listPageByParam(@RequestBody Region region) {
        PageUtil.startPage(region.getPageNum(), region.getPageSize());
        return new PageInfo<Region>(iRegionService.listPageByParam(region));
    }

    /**
     * 保存行政区域数据(单条)
     */
    @PostMapping("/saveRegion")
    public void saveRegion(@RequestBody Region region) {
        Assert.isTrue(Objects.nonNull(region), "要保存的行政区域数据为空.");
        iRegionService.checkNotEmptyBeforeSave(region);
        iRegionService.saveRegion(region);
    }

    /**
     * 通过id删除行政区域(单条删除)
     * @param regionId
     */
    @GetMapping("/deleteById")
    public void deleteById(@RequestParam("regionId") Long regionId) {
        checkRegionId(regionId);
        Assert.isTrue(Objects.equals(LogisticsStatus.DRAFT.getValue(), iRegionService.getById(regionId).getStatus()), "只有拟定状态才可以删除.");
        iRegionService.removeById(regionId);
    }

    /**
     * 行政区域生效(单条)
     * @param regionId
     */
    @GetMapping("/effectiveRegion")
    public void effectiveRegion(@RequestParam("regionId") Long regionId) {
        checkRegionId(regionId);
        iRegionService.updateRegionStatus(regionId, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 行政区域失效(单条)
     * @param regionId
     */
    @GetMapping("/inEffectiveRegion")
    public void inEffectiveRegion(@RequestParam("regionId") Long regionId) {
        checkRegionId(regionId);
        iRegionService.updateRegionStatus(regionId, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 行政区域id判空及数据校验
     * @param regionId
     */
    public void checkRegionId(@RequestParam("regionId") Long regionId) {
        Assert.notNull(regionId, "行政区域id为空.");
        Assert.isTrue(Objects.nonNull(iRegionService.getById(regionId)), "无效的行政区域id: "+regionId+", 找不到相应的行政区域.");
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
     * 根据父编码和类型查找行政区域
     * @return
     */
    @GetMapping("/queryRegionBy")
    public List<Region> queryRegionBy(String regionLevelCode,String parentRegionCode){
        return iRegionService.queryRegionBy(regionLevelCode,parentRegionCode);
    }
}
