package com.midea.cloud.srm.bid.suppliercooperate.projectlist.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderLineVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商投标行表 前端控制器
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-03 09:54:39
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/supplierCooperate/bidOrderLine")
public class OrderLineController extends BaseController {

    @Autowired
    private IOrderLineService iOrderLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderLineService.getById(id);
    }

    /**
    * 新增
    * @param bidOrderLine
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderLine bidOrderLine) {
        Long id = IdGenrator.generate();
        bidOrderLine.setOrderLineId(id);
        iOrderLineService.save(bidOrderLine);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderLineService.removeById(id);
    }

    /**
    * 修改
    * @param bidOrderLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderLine bidOrderLine) {
        iOrderLineService.updateById(bidOrderLine);
    }

    /**
    * 分页查询
    * @param bidOrderLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderLine> listPage(@RequestBody OrderLine bidOrderLine) {
        PageUtil.startPage(bidOrderLine.getPageNum(), bidOrderLine.getPageSize());
        QueryWrapper<OrderLine> wrapper = new QueryWrapper<OrderLine>(bidOrderLine);
        return new PageInfo<OrderLine>(iOrderLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderLine> listAll() {
        return iOrderLineService.list();
    }

    @GetMapping("/getOrderLineByBidingId")
    public List<BidOrderLineVO> getOrderLineByBidingId(@RequestParam Long bidingId,@RequestParam Long bidVendorId){
        return iOrderLineService.getRequirementLineByBidingIdAndBidVendorId(bidingId,bidVendorId);
    }

    @GetMapping("/getAllOrderLine")
    public List<BidOrderLineVO> getOrderLineByBidingIdAndVendorId(@RequestParam Long bidingId,@RequestParam Long vendorId){
        return iOrderLineService.getOrderLineByBidingIdAndVendorId(bidingId,vendorId);
    }

    @GetMapping("/getOrderLineByOrderHeadId")
    public List<BidOrderLineVO> getOrderLineByOrderHeadId(@RequestParam Long orderHeadId ){
        return iOrderLineService.getOrderLineByOrderHeadId(orderHeadId);
    }
}
