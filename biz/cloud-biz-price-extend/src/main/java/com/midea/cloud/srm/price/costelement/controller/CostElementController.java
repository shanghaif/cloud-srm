package com.midea.cloud.srm.price.costelement.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.costelement.entity.CostElement;
import com.midea.cloud.srm.price.costelement.service.ICostElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  成本要素表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:19
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cost-element")
public class CostElementController extends BaseController {

    @Autowired
    private ICostElementService iCostElementService;

    /**
     * 分页查询
     * @param costElement
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<CostElement> listPage(@RequestBody CostElement costElement) {
        return iCostElementService.listPage(costElement);
    }

    /**
    * 获取
    * @param costElementId
    */
    @GetMapping("/get")
    public CostElement get(Long costElementId) {
        Assert.notNull(costElementId, "id不能为空");
        return iCostElementService.get(costElementId);
    }

    /**
    * 新增
    * @param costElement
    */
    @PostMapping("/add")
    public Long add(@RequestBody CostElement costElement) {
        return iCostElementService.updateOrSave(costElement);
    }

    /**
     * 修改
     * @param costElement
     */
    @PostMapping("/modify")
    public Long modify(@RequestBody CostElement costElement) {
        return iCostElementService.updateOrSave(costElement);
    }

    /**
    * 删除
    * @param costElementId
    */
    @GetMapping("/delete")
    public void delete(Long costElementId) {
        Assert.notNull(costElementId, "costElementId不能为空");
        iCostElementService.removeById(costElementId);
    }

    /**
     * 生效
     * @param costElementId
     */
    @GetMapping("/takeEffect")
    public void takeEffect(Long costElementId){
        iCostElementService.takeEffect(costElementId);
    }

    /**
     * 失效
     * @param costElementId
     */
    @GetMapping("/failure")
    public void failure(Long costElementId){
        iCostElementService.failure(costElementId);
    }

    /**
     * 创建新版本
     * @param costElement
     */
    @PostMapping("/createNewVersion")
    public Long createNewVersion(@RequestBody CostElement costElement){
        return iCostElementService.createNewVersion(costElement);
    }
 
}
