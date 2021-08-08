package com.midea.cloud.srm.inq.price.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.price.service.IApprovalBiddingItemPaymentTermService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItemPaymentTerm;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  中标行-付款条款 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-24 16:58:32
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inq/approval-bidding-item-payment-term")
public class ApprovalBiddingItemPaymentTermController extends BaseController {

    @Autowired
    private IApprovalBiddingItemPaymentTermService iApprovalBiddingItemPaymentTermService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ApprovalBiddingItemPaymentTerm get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iApprovalBiddingItemPaymentTermService.getById(id);
    }

    /**
    * 新增
    * @param approvalBiddingItemPaymentTerm
    */
    @PostMapping("/add")
    public void add(@RequestBody ApprovalBiddingItemPaymentTerm approvalBiddingItemPaymentTerm) {
        Long id = IdGenrator.generate();
        iApprovalBiddingItemPaymentTermService.save(approvalBiddingItemPaymentTerm);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iApprovalBiddingItemPaymentTermService.removeById(id);
    }

    /**
    * 修改
    * @param approvalBiddingItemPaymentTerm
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ApprovalBiddingItemPaymentTerm approvalBiddingItemPaymentTerm) {
        iApprovalBiddingItemPaymentTermService.updateById(approvalBiddingItemPaymentTerm);
    }

    /**
    * 分页查询
    * @param approvalBiddingItemPaymentTerm
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ApprovalBiddingItemPaymentTerm> listPage(@RequestBody ApprovalBiddingItemPaymentTerm approvalBiddingItemPaymentTerm) {
        PageUtil.startPage(approvalBiddingItemPaymentTerm.getPageNum(), approvalBiddingItemPaymentTerm.getPageSize());
        QueryWrapper<ApprovalBiddingItemPaymentTerm> wrapper = new QueryWrapper<ApprovalBiddingItemPaymentTerm>(approvalBiddingItemPaymentTerm);
        return new PageInfo<ApprovalBiddingItemPaymentTerm>(iApprovalBiddingItemPaymentTermService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ApprovalBiddingItemPaymentTerm> listAll() {
        return iApprovalBiddingItemPaymentTermService.list();
    }

}
