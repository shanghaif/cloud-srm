package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  供应商表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 17:36:33
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inviteVendor/bidVendor")
public class BidVendorController extends BaseController {

    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IBidingService bidingService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidVendor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidVendorService.getById(id);
    }

    /**
     * 新增
     *
     * @param bidVendor
     */
    @PostMapping("/add")
    public void add(@RequestBody BidVendor bidVendor) {
        Long bidingId = bidVendor.getBidingId();
        Assert.notNull(bidingId,"招标单id不能为空");
        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId));
        //审批后不操作
        if(Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            return;
        }
        Long id = IdGenrator.generate();
        bidVendor.setBidVendorId(id);
        iBidVendorService.save(bidVendor);
    }

    /**
     * 批量新增
     * @param bidVendorList
     */
    @PostMapping("/addBatch")
    public void addBatch(@RequestBody List<BidVendor> bidVendorList) {
        Long bidingId = bidVendorList.stream().findAny().orElseThrow(() -> new BaseException("询价id不能为空")).getBidingId();
        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId));
        //审批后不操作
        if(Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            return;
        }
        iBidVendorService.saveBidVendorList(bidVendorList);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidVendorService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidVendorList
     */
    @PostMapping("/modify")
    public void modify(@RequestBody List<BidVendor> bidVendorList) {
        Long bidingId = bidVendorList.stream().findAny().orElseThrow(() -> new BaseException("询价id不能为空")).getBidingId();
        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId));
        //审批后不操作
        if(Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            return;
        }
        iBidVendorService.updateBatch(bidVendorList);
    }

    /**
     * 分页查询
     *
     * @param bidVendor
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidVendor> listPage(@RequestBody BidVendor bidVendor) {
        PageUtil.startPage(bidVendor.getPageNum(), 9999);
        QueryWrapper<BidVendor> wrapper = new QueryWrapper<BidVendor>(bidVendor);
        return new PageInfo<BidVendor>(iBidVendorService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<BidVendor> listAll() {
        return iBidVendorService.list();
    }

//    /**
//     * 智能推荐列表
//     *
//     * @return
//     */
//    @GetMapping("/listIntelligentRecommendInfo")
//    public List<IntelligentRecommendVO> listIntelligentRecommendInfo(Long bidingId) {
//        return iBidVendorService.listIntelligentRecommendInfo(bidingId);
//    }

    /**
     * 根据供应商主表id获取联系方式
     *
     * @param vendorIdList
     */
    @PostMapping("/listVendorContactInfo")
    public List<BidVendor> listVendorContactInfo(@RequestBody List<Long> vendorIdList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorIdList), "供应商id列表不能为空");
        return iBidVendorService.listVendorContactInfo(vendorIdList);
    }

}
