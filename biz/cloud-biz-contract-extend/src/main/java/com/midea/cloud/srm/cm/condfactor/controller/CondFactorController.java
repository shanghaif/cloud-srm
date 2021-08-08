package com.midea.cloud.srm.cm.condfactor.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.cm.condfactor.service.ICondFactorService;
import com.midea.cloud.srm.model.cm.condfactor.entity.CondFactor;
import com.midea.cloud.srm.model.common.BaseController;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  条件因素表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 11:15:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cond-factor")
public class CondFactorController extends BaseController {

    @Resource
    private ICondFactorService iCondFactorService;

    /**
     * 分页查询
     * @param condFactor
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<CondFactor> listPage(@RequestBody CondFactor condFactor) {
        return iCondFactorService.listPage(condFactor);
    }

    /**
    * 获取
    * @param condFactorId
    */
    @GetMapping("/get")
    public CondFactor get(Long condFactorId) {
        Assert.notNull(condFactorId, "id不能为空");
        return iCondFactorService.getById(condFactorId);
    }

    /**
     * 批量获取
     * @param idList (多个id用英文逗号隔开)
     */
    @GetMapping("/getByIdList")
    public List<CondFactor> getByIdList(String idList) {
        return iCondFactorService.getByIdList(idList);
    }

    /**
    * 新增
    * @param condFactor
    */
    @PostMapping("/add")
    public Long add(@RequestBody CondFactor condFactor) {
        return iCondFactorService.add(condFactor);
    }
    
    /**
    * 删除
    * @param condFactorId
    */
    @GetMapping("/delete")
    public void delete(Long condFactorId) {
        Assert.notNull(condFactorId, "condFactorId不能为空");
        iCondFactorService.removeById(condFactorId);
    }

    /**
    * 修改
    * @param condFactor
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody CondFactor condFactor) {
        Assert.notNull(condFactor.getCondFactorId(), "condFactorId不能为空");
        iCondFactorService.updateById(condFactor);
        return condFactor.getCondFactorId();
    }
    
    /**
     * 修改
     * @param condFactor
     */
    @Transactional
     @PostMapping("/batchSaveOrUpdate")
     public void batchSaveOrUpdate(@RequestBody List<CondFactor> condFactors) {
    	 if (null != condFactors && condFactors.size() >0) {
    		 for (CondFactor condFactor : condFactors) {
    			 if (null != condFactor.getCondFactorId()) {
    				 iCondFactorService.updateById(condFactor);
    			 } else {
    				 iCondFactorService.add(condFactor);
    			 }
    		 }
    	 }
     }

    /**
    * 查询所有
    * @return
    */
    @GetMapping("/listAll")
    public List<CondFactor> listAll() { 
        return iCondFactorService.queryAll();
    }
 
}
