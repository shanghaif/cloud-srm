package com.midea.cloud.srm.inq.price.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.price.service.IApprovalBiddingItemService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
*  <pre>
 *  价格审批单-中标行信息 前端控制器
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 11:10:33
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/price/approval-bidding-item")
public class ApprovalBiddingItemController extends BaseController {

    @Autowired
    private IApprovalBiddingItemService iApprovalBiddingItemService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ApprovalBiddingItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iApprovalBiddingItemService.getById(id);
    }

    /**
    * 新增
    * @param approvalBiddingItem
    */
    @PostMapping("/add")
    public void add(@RequestBody ApprovalBiddingItem approvalBiddingItem) {
        Long id = IdGenrator.generate();
        iApprovalBiddingItemService.save(approvalBiddingItem);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iApprovalBiddingItemService.removeById(id);
    }

    /**
    * 修改
    * @param approvalBiddingItem
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ApprovalBiddingItem approvalBiddingItem) {
        iApprovalBiddingItemService.updateById(approvalBiddingItem);
    }

    /**
    * 分页查询
    * @param approvalBiddingItem
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ApprovalBiddingItem> listPage(@RequestBody ApprovalBiddingItem approvalBiddingItem) {
        PageUtil.startPage(approvalBiddingItem.getPageNum(), approvalBiddingItem.getPageSize());
        QueryWrapper<ApprovalBiddingItem> wrapper = new QueryWrapper<ApprovalBiddingItem>(approvalBiddingItem);
        return new PageInfo<ApprovalBiddingItem>(iApprovalBiddingItemService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ApprovalBiddingItem> listAll() {
        return iApprovalBiddingItemService.list();
    }

    /**
     * 根据价格审批单id获取中标行(采购申请报表使用)
     * @param approvalHeadIds
     * @return
     */
    @PostMapping("/listApprovalBiddingItemByApprovalHeadIds")
    public List<ApprovalBiddingItem> listApprovalBiddingItemByApprovalHeadIds(@RequestBody List<Long> approvalHeadIds){
        if(CollectionUtils.isEmpty(approvalHeadIds)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<ApprovalBiddingItem> wrapper = new QueryWrapper<>();
        wrapper.in("APPROVAL_HEADER_ID",approvalHeadIds);
        return iApprovalBiddingItemService.list(wrapper);
    }



}
