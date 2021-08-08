package com.midea.cloud.srm.supcooperate.deliver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  到货计划详情表 前端控制器
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/deliver/deliverPlanDetail")
public class DeliverPlanDetailController extends BaseController {

    @Autowired
    private IDeliverPlanDetailService iDeliverPlanDetailService;

    /**
     * 获取指定订单明细
     *
     * @param deliverPlanDetai
     */
    @PostMapping("/getDeliverPlanDetail")
    public DeliverPlanDetail getDeliverPlanDetail(@RequestBody DeliverPlanDetail deliverPlanDetai) {
        Assert.notNull(deliverPlanDetai, "验收单详情不能为空");
        return iDeliverPlanDetailService.getDeliverPlanDetail(deliverPlanDetai);
    }

    /**
     * 新增
     *
     * @param deliverPlanDetail
     */
    @PostMapping("/add")
    public void add(@RequestBody DeliverPlanDetail deliverPlanDetail) {
        Long id = IdGenrator.generate();
        deliverPlanDetail.setDeliverPlanDetailId(id);
        iDeliverPlanDetailService.save(deliverPlanDetail);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDeliverPlanDetailService.removeById(id);
    }

    /**
     * 修改
     *
     * @param deliverPlanDetail
     */
    @PostMapping("/modify")
    public void modify(@RequestBody DeliverPlanDetail deliverPlanDetail) {
        iDeliverPlanDetailService.updateById(deliverPlanDetail);
    }

    /**
     * 分页查询
     *
     * @param deliverPlanDetail
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliverPlanDetail> listPage(@RequestBody DeliverPlanDetail deliverPlanDetail) {
        PageUtil.startPage(deliverPlanDetail.getPageNum(), deliverPlanDetail.getPageSize());
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<DeliverPlanDetail>(deliverPlanDetail);
        return new PageInfo<DeliverPlanDetail>(iDeliverPlanDetailService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<DeliverPlanDetail> listAll() {
        return iDeliverPlanDetailService.list();
    }

    /**
     * 锁定到货详情行
     * @param ids
     */
    @PostMapping("/getDeliverPlanLock")
    public void  getDeliverPlanLock(@RequestBody List<Long> ids){
        Assert.notEmpty(ids, "请勾选需要锁定的到货计划详情");
        iDeliverPlanDetailService.getDeliverPlanLock(ids);
    }

    /**
     * 确认行状态
     * @param ids
     */
    @PostMapping("/getDeliverPlanStatus")
    public void  getDeliverPlanStatus(@RequestBody List<Long> ids){
        Assert.notEmpty(ids, "请勾选需要锁定的到货计划详情");
        iDeliverPlanDetailService.getDeliverPlanStatus(ids);
    }

    /**
     * 订单匹配
     * @param deliverPlanDTO
     */
    @PostMapping("/MatchingOrder")
    public void MatchingOrder(@RequestBody DeliverPlanDTO deliverPlanDTO){
        Assert.notNull(deliverPlanDTO, "id不能为空");
        iDeliverPlanDetailService.MatchingOrder(deliverPlanDTO);
    }

}
