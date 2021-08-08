package com.midea.cloud.srm.cm.model.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.model.service.IModelHeadService;
import com.midea.cloud.srm.cm.model.utils.BeanUtils;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/modelHead")
public class ModelHeadController extends BaseController {

    @Autowired
    private IModelHeadService iModelHeadService;

    /**
    * 根据id获取
    * @param modelHeadId
    */
    @GetMapping("/getById")
    public ModelHead getById(Long modelHeadId) {
        Assert.notNull(modelHeadId, "modelHeadId不能为空");
        return iModelHeadService.getById(modelHeadId);
    }

    /**
    * 新增
    * @param modelHead
    */
    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody ModelHead modelHead) {
        return iModelHeadService.add(modelHead);
    }
    
    /**
    * 删除
    * @param modelHeadId
    */
    @GetMapping("/delete")
    public void delete(Long modelHeadId) {
        Assert.notNull(modelHeadId, "id不能为空");
        iModelHeadService.removeById(modelHeadId);
    }

    /**
    * 修改
    * @param modelHead
    */
    @PostMapping("/modify")
    public Map<String,Object> modify(@RequestBody ModelHead modelHead) {
        return iModelHeadService.update(modelHead);
    }

    /**
     * 修改
     * @param modelHead
     */
    @PostMapping("/modifyAll")
    public Map<String,Object> modifyAll(@RequestBody ModelHead modelHead) {
        Assert.notNull(modelHead.getModelHeadId(),"modelHeadId不能为空");
        iModelHeadService.updateById(modelHead);
        if(StringUtil.isEmpty(modelHead.getEndDate())){
            UpdateWrapper<ModelHead> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("END_DATE",null);
            updateWrapper.eq("MODEL_HEAD_ID",modelHead.getModelHeadId());
            iModelHeadService.update(updateWrapper);
        }
        return BeanUtils.getStringObjectHashMap(modelHead.getModelHeadId());
    }

    /**
    * 分页查询
    * @param modelHead
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ModelHead> listPage(@RequestBody ModelHead modelHead) {
        return iModelHeadService.listPage(modelHead);
    }

    /**
     * 获取模板选择列表
     * @return
     */
    @GetMapping("/modelList")
    public List<ModelHead> modelList(){
        return iModelHeadService.modelList();
    }

    /**
     * 根据模板类型获取模板选择列表
     * @return
     */
    @GetMapping("/modelListByType")
    public List<ModelHead> modelListByType(String modelType){
        return iModelHeadService.modelListByType(modelType);
    }


    /**
     * 生效
     * @param modelHeadId
     */
    @GetMapping("/takeEffect")
    public Long takeEffect(Long modelHeadId){
        iModelHeadService.takeEffect(modelHeadId);
        return modelHeadId;
    }

    /**
     * 失效
     * @param modelHeadId
     */
    @GetMapping("/failure")
    public Long failure(Long modelHeadId){
        iModelHeadService.failure(modelHeadId);
        return modelHeadId;
    }

    /**
     * 冻结
     * @param modelHeadId
     */
    @GetMapping("/freeze")
    public Long freeze(Long modelHeadId){
        iModelHeadService.freeze(modelHeadId);
        return modelHeadId;
    }

}
