package com.midea.cloud.srm.price.estimate.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateHead;
import com.midea.cloud.srm.price.estimate.service.IEstimateHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  价格估算头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/estimate-head")
public class EstimateHeadController extends BaseController {

    @Autowired
    private IEstimateHeadService iEstimateHeadService;

    /**
     * 分页查询
     * @param estimateHead
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<EstimateHead> listPage(@RequestBody EstimateHead estimateHead) {
        return iEstimateHeadService.listPage(estimateHead);
    }

    /**
    * 获取
    * @param estimateHeadId
    */
    @GetMapping("/get")
    public EstimateHead get(Long estimateHeadId) {
        return iEstimateHeadService.get(estimateHeadId);
    }

    /**
    * 新增
    * @param estimateHead
    */
    @PostMapping("/add")
    public Long add(@RequestBody EstimateHead estimateHead) {
        return iEstimateHeadService.add(estimateHead);
    }
    
    /**
    * 删除
    * @param estimateHeadId
    */
    @GetMapping("/delete")
    public void delete(Long estimateHeadId) {
        Assert.notNull(estimateHeadId, "estimateHeadId不能为空");
        iEstimateHeadService.removeById(estimateHeadId);
    }

    /**
    * 修改
    * @param estimateHead
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody EstimateHead estimateHead) {
        iEstimateHeadService.modify(estimateHead);
        return estimateHead.getEstimateHeadId();
    }

}
