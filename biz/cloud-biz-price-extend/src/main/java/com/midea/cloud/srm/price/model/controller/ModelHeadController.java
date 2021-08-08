package com.midea.cloud.srm.price.model.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.model.entity.ModelHead;
import com.midea.cloud.srm.price.model.service.IModelHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  价格模型头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/model-head")
public class ModelHeadController extends BaseController {

    @Autowired
    private IModelHeadService iModelHeadService;

    /**
     * 分页查询
     * @param modelHead/cost-element/listPage
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ModelHead> listPage(@RequestBody ModelHead modelHead) {
        return iModelHeadService.listPage(modelHead);
    }

    /**
    * 获取
    * @param priceModelHeadId
    */
    @GetMapping("/get")
    public ModelHead get(Long priceModelHeadId) {
        return iModelHeadService.get(priceModelHeadId);
    }

    /**
    * 新增
    * @param modelHead
    */
    @PostMapping("/add")
    public Long add(@RequestBody ModelHead modelHead) {
        return iModelHeadService.add(modelHead);
    }

    /**
     * 修改
     * @param modelHead
     */
    @PostMapping("/modify")
    public void modify(@RequestBody ModelHead modelHead) {
        iModelHeadService.modify(modelHead);
    }
    
    /**
    * 删除
    * @param priceModelHeadId
    */
    @GetMapping("/delete")
    public void delete(Long priceModelHeadId) {
        iModelHeadService.delete(priceModelHeadId);
    }

    /**
     * 生效
     * @param priceModelHeadId
     */
    @GetMapping("/takeEffect")
    public void takeEffect(Long priceModelHeadId){
        iModelHeadService.takeEffect(priceModelHeadId);
    }

    /**
     * 失效
     * @param priceModelHeadId
     */
    @GetMapping("/failure")
    public void failure(Long priceModelHeadId){
        iModelHeadService.failure(priceModelHeadId);
    }
 
}
