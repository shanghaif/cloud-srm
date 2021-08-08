package com.midea.cloud.srm.cm.element.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.cm.element.service.IElemMaintainsService;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.TypeRange;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合同要素维护表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-12 16:29:02
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/elem-maintain")
public class ElemMaintainController extends BaseController {

    @Autowired
    private IElemMaintainsService iElemMaintainsService;

    /**
     * 新增
     * @param elemMaintain
     */
    @PostMapping("/add")
    public Long add(@RequestBody ElemMaintain elemMaintain) {
        return iElemMaintainsService.add(elemMaintain);
    }

    /**
     * 修改
     * @param elemMaintain
     */
    @PostMapping("/modify")
    public Long modify(@RequestBody ElemMaintain elemMaintain) {
        return iElemMaintainsService.modify(elemMaintain);
    }

    /**
    * 获取
    * @param elemMaintainId
    */
    @GetMapping("/get")
    public ElemMaintain get(Long elemMaintainId) {
        return iElemMaintainsService.get(elemMaintainId);
    }
    
    /**
    * 删除
    * @param elemMaintainId
    */
    @GetMapping("/delete")
    public void delete(Long elemMaintainId) {
        Assert.notNull(elemMaintainId, "elemMaintainId不能为空");
        iElemMaintainsService.removeById(elemMaintainId);
    }

    /**
    * 分页查询
    * @param elemMaintain
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ElemMaintain> listPage(@RequestBody ElemMaintain elemMaintain) {
        return iElemMaintainsService.listPage(elemMaintain);
    }

    /**
    * 查询所有有效的
    * @return
    */
    @GetMapping("/listAll")
    public List<ElemMaintain> listAll() { 
        return iElemMaintainsService.listAll();
    }
    
    /**
     *批量修改
     * @param typeRange
     */
     @PostMapping("/batchSaveOrUpdate")
     public void batchSaveOrUpdate(@RequestBody List<ElemMaintain> elemMaintains) {
         Assert.notNull(elemMaintains, "保存数据不能为空");
         if (null != elemMaintains && elemMaintains.size() > 0) {
        	 for (ElemMaintain elemMaintain : elemMaintains) {
        		 if (null == elemMaintain.getElemMaintainId()) {
        			 iElemMaintainsService.add(elemMaintain);
        		 } else {
        			 iElemMaintainsService.updateById(elemMaintain);
        		 }
        	 }
         }
     }
 
}
