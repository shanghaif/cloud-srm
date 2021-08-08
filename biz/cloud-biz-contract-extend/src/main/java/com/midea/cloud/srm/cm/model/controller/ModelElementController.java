package com.midea.cloud.srm.cm.model.controller;

import com.midea.cloud.srm.cm.model.service.IModelElementService;
import com.midea.cloud.srm.cm.model.utils.BeanUtils;
import com.midea.cloud.srm.model.cm.model.entity.ModelElement;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板元素表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 17:30:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/modelElement")
public class ModelElementController extends BaseController {

    @Autowired
    private IModelElementService iModelElementService;

    /**
    * 获取固定变量元素
    * @param modelElement
    */
    @PostMapping("/getElement")
    public List<ModelElement> getElement(@RequestBody ModelElement modelElement) {
        return iModelElementService.getElement(modelElement);
    }

    /**
     * 批量根据元素符号获取变量元素
     * @param variableSigns
     */
    @PostMapping("/getElementList")
    public List<ModelElement> getElementList(@RequestBody List<String> variableSigns) {
        return iModelElementService.getElementList(variableSigns);
    }

    /**
    * 新增
    * @param modelElement
    */
    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody ModelElement modelElement) {
        return iModelElementService.add(modelElement);
    }

    /**
     * 同步至固定元素
     * @param elementId
     */
    @GetMapping("/syncFixed")
    public void syncFixed(Long elementId){
        iModelElementService.syncFixed(elementId);
    }
    
    /**
    * 删除
    * @param elementId
    */
    @GetMapping("/delete")
    public void delete(Long elementId) {
        Assert.notNull(elementId, "elementId不能为空");
        iModelElementService.removeById(elementId);
    }

    /**
    * 修改
    * @param modelElement
    */
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody ModelElement modelElement) {
        Assert.notNull(modelElement.getElementId(),"elementId不能为空");
        iModelElementService.updateById(modelElement);
        return BeanUtils.getStringObjectHashMap(modelElement.getElementId());
    }

}
