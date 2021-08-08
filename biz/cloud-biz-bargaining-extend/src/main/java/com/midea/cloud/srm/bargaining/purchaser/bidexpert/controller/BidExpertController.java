package com.midea.cloud.srm.bargaining.purchaser.bidexpert.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.bargaining.purchaser.bidexpert.service.IBidExpertService;
import com.midea.cloud.srm.model.bargaining.purchaser.bidexpert.entity.BidExpert;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  专家库表 前端控制器
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-19 20:42:21
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidExpert")
public class BidExpertController extends BaseController {

    @Autowired
    private IBidExpertService iBidExpertService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidExpert get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidExpertService.getById(id);
    }

    /**
    * 保存
    * @param bidExpertList
    */
    @PostMapping("/saveOrUpdateBidExpertBatch")
    public void saveOrUpdateBidExpertBatch(@RequestBody List<BidExpert> bidExpertList) {
        iBidExpertService.saveOrUpdateBidExpertBatch(bidExpertList);
    }
    
    /**
    * 批量删除
    * @param idList
    */
    @PostMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> idList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(idList), "删除内容不能为空");
        iBidExpertService.removeByIds(idList);
    }

    /**
    * 修改
    * @param bidExpert
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidExpert bidExpert) {
        iBidExpertService.updateById(bidExpert);
    }

    /**
     * 失效
     * @param idList
     */
    @PostMapping("/invalid")
    public void invalid(@RequestBody List<Long> idList) {
        iBidExpertService.invalid(idList);
    }

    /**
    * 分页查询
    * @param bidExpert
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidExpert> listPage(@RequestBody BidExpert bidExpert) {
       return iBidExpertService.listPage(bidExpert);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidExpert> listAll() { 
        return iBidExpertService.list();
    }
 
}
