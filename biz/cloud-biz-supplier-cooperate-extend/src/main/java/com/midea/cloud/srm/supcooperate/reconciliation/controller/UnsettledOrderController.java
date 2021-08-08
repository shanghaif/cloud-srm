package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.UnsettledOrderStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledOrder;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  未结算数量账单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:08
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/reconciliation/unsettledOrder")
public class UnsettledOrderController extends BaseController {

    @Autowired
    private IUnsettledOrderService iUnsettledOrderService;

    /**
     * 分页查询
     *
     * @param unsettledOrder 账单对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<UnsettledOrder> listPage(@RequestBody UnsettledOrder unsettledOrder) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            unsettledOrder.setVendorId(loginAppUser.getCompanyId());
        }
        Date startDate = unsettledOrder.getStartDate();
        Date endDate = unsettledOrder.getEndDate();
        String unsettledOrderNumber = unsettledOrder.getUnsettledOrderNumber();
        unsettledOrder.setStartDate(null);
        unsettledOrder.setEndDate(null);
        unsettledOrder.setUnsettledOrderNumber(null);

        QueryWrapper<UnsettledOrder> wrapper = new QueryWrapper<>(unsettledOrder);

        wrapper.ge(startDate!=null,"START_DATE",startDate);
        wrapper.le(endDate!=null,"END_DATE",endDate);
        wrapper.like(StringUtils.isNotBlank(unsettledOrderNumber),"UNSETTLED_ORDER_NUMBER",unsettledOrderNumber);


        PageUtil.startPage(unsettledOrder.getPageNum(), unsettledOrder.getPageSize());

        if (StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            wrapper.ne("STATUS", UnsettledOrderStatus.CREATE.name());
        }
        wrapper.orderByDesc("CREATION_DATE");
        return new PageInfo(iUnsettledOrderService.list(wrapper));
    }

    /**
     * 供应商新增未结算数量账单
     *
     * @param param
     */
    @PostMapping("/save")
    public void save(@RequestBody UnsettledOrderSaveDTO param) {
        UnsettledOrder unsettledOrder = param.getUnsettledOrder();
        List<UnsettledDetail> unsettledDetails = param.getUnsettledDetails();
        List<UnsettledPenalty> unsettledPenaltys = param.getUnsettledPenaltys();

        Assert.notNull(unsettledOrder, "未结算数量账单不能为空");
        Assert.notNull(unsettledDetails, "未结算数量账单明细不能为空");
        Assert.notNull(unsettledPenaltys, "未结算数量账单罚扣款明细不能为空");

        Assert.notNull(unsettledOrder.getStartDate(), "开始日期不能为空");
        Assert.notNull(unsettledOrder.getEndDate(), "截止日期不能为空");
        Assert.notNull(unsettledOrder.getOrganizationCode(), "采购组织编码不能为空");
        Assert.notNull(unsettledOrder.getOrganizationId(), "采购组织ID不能为空");
        Assert.notNull(unsettledOrder.getOrganizationName(), "采购组织名称不能为空");
        Assert.notNull(unsettledOrder.getRfqSettlementCurrency(), "币种不能为空");
        for (UnsettledDetail item : unsettledDetails) {
            Assert.notNull(item.getUnsettledDetailId(), "明细ID不能为空");
        }
        for (UnsettledPenalty item : unsettledPenaltys) {
            Assert.notNull(item.getPenaltyId(), "罚扣款ID不能为空");
        }

        unsettledOrder.setUnsettledOrderId(null);
        unsettledOrder.setStatus(UnsettledOrderStatus.CREATE.name());

        iUnsettledOrderService.saveOrUpdate(unsettledOrder, unsettledDetails, unsettledPenaltys);
    }

    /**
     * 采购商修改未结算数量账单
     *
     * @param param
     */
    @PostMapping("/update")
    public void update(@RequestBody UnsettledOrderSaveDTO param) {
        UnsettledOrder unsettledOrder = param.getUnsettledOrder();
        List<UnsettledDetail> unsettledDetails = param.getUnsettledDetails();
        List<UnsettledPenalty> unsettledPenaltys = param.getUnsettledPenaltys();

        Assert.notNull(unsettledOrder, "未结算数量账单不能为空");
        Assert.notNull(unsettledDetails, "未结算数量账单明细不能为空");
        Assert.notNull(unsettledPenaltys, "未结算数量账单罚扣款明细不能为空");

        Assert.notNull(unsettledOrder.getUnsettledOrderId(), "ID不能为空");
        Assert.notNull(unsettledOrder.getEndDate(), "截止日期不能为空");
        Assert.notNull(unsettledOrder.getOrganizationCode(), "采购组织编码不能为空");
        Assert.notNull(unsettledOrder.getOrganizationId(), "采购组织ID不能为空");
        Assert.notNull(unsettledOrder.getOrganizationName(), "采购组织名称不能为空");
        Assert.notNull(unsettledOrder.getRfqSettlementCurrency(), "币种不能为空");
        Assert.notNull(unsettledOrder.getStartDate(), "开始日期不能为空");
        for (UnsettledDetail item : unsettledDetails) {
            Assert.notNull(item.getUnsettledDetailId(), "明细ID不能为空");
        }
        for (UnsettledPenalty item : unsettledPenaltys) {
            Assert.notNull(item.getPenaltyId(), "罚扣款ID不能为空");
        }

        unsettledOrder.setStatus(UnsettledOrderStatus.CREATE.name());
        iUnsettledOrderService.saveOrUpdate(unsettledOrder, unsettledDetails, unsettledPenaltys);
    }

    /**
     * 采购商批量作废
     *
     * @param ids ids
     */
    @PostMapping("/cancalBatch")
    public void cancalBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.cancalBatch(ids);
    }

    /**
     * 供应商批量提交
     *
     * @param ids ids
     */
    @PostMapping("/submitBatch")
    public void submitBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.submitBatch(ids);
    }

    /**
     * 供应商批量撤回已提交账单
     *
     * @param ids ids
     */
    @PostMapping("/unSubmitBatch")
    public void unSubmitBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.unSubmitBatch(ids);
    }

    /**
     * 供应商批量审核通过已提交账单
     *
     * @param ids ids
     */
    @PostMapping("/comfirmBatch")
    public void comfirmBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.comfirmBatch(ids);
    }

    /**
     * 采购商批量驳回已提交账单
     *
     * @param orders
     */
    @PostMapping("/refuseBatch")
    public void refuseBatch(@RequestBody List<UnsettledOrder> orders) {
        Assert.notNull(orders,"请选择数据");
        orders.forEach(item->{
            Assert.notNull(item.getUnsettledOrderId(),"ID不能为空");
            Assert.notNull(item.getRefuseReason(),"驳回原因不能为空");
        });
        iUnsettledOrderService.refuseBatch(orders);
    }

    /**
     * 供应商上传确认对账单
     *
     * @param order
     */
    @PostMapping("/sure")
    public void sure(@RequestBody UnsettledOrder order) {
        Assert.notNull(order,"账单不能为空");
        Assert.notNull(order.getUnsettledOrderId(),"ID不能为空");
        Assert.notNull(order.getFileRelationId(),"附件ID不能为空");
        Assert.notNull(order.getFileName(),"附件名称不能为空");
        iUnsettledOrderService.sure(order);
    }

    /**
     * 供应商批量退回已确认账单
     *
     * @param ids ids
     */
    @PostMapping("/rollBackBatch")
    public void rollBackBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.rollBackBatch(ids);
    }

    /**
     * 采购商批量完成账单
     *
     * @param ids ids
     */
    @PostMapping("/finishBatch")
    public void finishBatch(@RequestBody List<Long> ids) {
        iUnsettledOrderService.finishBatch(ids);
    }
}
