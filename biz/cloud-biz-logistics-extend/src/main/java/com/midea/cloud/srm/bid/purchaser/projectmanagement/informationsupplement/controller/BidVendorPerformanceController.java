package com.midea.cloud.srm.bid.purchaser.projectmanagement.informationsupplement.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.informationsupplement.service.IBidVendorPerformanceService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.VendorPerformance;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商绩效 前端控制器
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 20:55:27
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidInitiating/bidVendorPerformance")
public class BidVendorPerformanceController extends BaseController {

    @Autowired
    private IBidVendorPerformanceService iVendorPerformanceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public VendorPerformance get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iVendorPerformanceService.getById(id);
    }

    /**
    * 新增
    * @param VendorPerformance
    */
    @PostMapping("/add")
    public void add(@RequestBody VendorPerformance VendorPerformance) {
        Long id = IdGenrator.generate();
        VendorPerformance.setPerformanceId(id);
        iVendorPerformanceService.save(VendorPerformance);
    }

    /**
     * 批量新增
     * @param VendorPerformanceList
     */
    @PostMapping("/addBatch")
    public void addBatch(@RequestBody List<VendorPerformance> VendorPerformanceList) {
        iVendorPerformanceService.addBatch(VendorPerformanceList);
    }

    /**
     * 批量更新
     * @param VendorPerformanceList
     */
    @PostMapping("/updateBatch")
    public void updateBatch(@RequestBody List<VendorPerformance> VendorPerformanceList) {
        iVendorPerformanceService.updateBatch(VendorPerformanceList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iVendorPerformanceService.removeById(id);
    }

    /**
    * 修改
    * @param VendorPerformance
    */
    @PostMapping("/modify")
    public void modify(@RequestBody VendorPerformance VendorPerformance) {
        iVendorPerformanceService.updateById(VendorPerformance);
    }

    /**
    * 分页查询
    * @param VendorPerformance
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<VendorPerformance> listPage(@RequestBody VendorPerformance VendorPerformance) {
        return iVendorPerformanceService.listPage(VendorPerformance);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<VendorPerformance> listAll() { 
        return iVendorPerformanceService.list();
    }
 
}
