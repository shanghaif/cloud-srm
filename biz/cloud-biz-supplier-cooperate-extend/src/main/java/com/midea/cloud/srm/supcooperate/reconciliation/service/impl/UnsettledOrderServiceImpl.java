package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.order.ReturnStatus;
import com.midea.cloud.common.enums.order.UnsettledOrderStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledOrder;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.UnsettledOrderMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledOrderService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledPenaltyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  账单表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/27 19:33
 *  修改内容:
 * </pre>
 */
@Service
public class UnsettledOrderServiceImpl extends ServiceImpl<UnsettledOrderMapper, UnsettledOrder> implements IUnsettledOrderService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private FileCenterClient fileCenterClient;
    @Autowired
    private IUnsettledDetailService iUnsettledDetailService;
    @Autowired
    private IUnsettledPenaltyService iUnsettledPenaltyService;

    @Override
    @Transactional
    public void saveOrUpdate(UnsettledOrder unsettledOrder, List<UnsettledDetail> unsettledDetails, List<UnsettledPenalty> unsettledPenaltys) {
        if (unsettledOrder.getUnsettledOrderId() == null) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

            unsettledOrder.setUnsettledOrderId(IdGenrator.generate());
            unsettledOrder.setUnsettledOrderNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_UNSETTLED_ORDER_NUM));
            unsettledOrder.setVendorId(loginAppUser.getCompanyId());
            unsettledOrder.setVendorCode(loginAppUser.getCompanyCode());
            unsettledOrder.setVendorName(loginAppUser.getCompanyName());
        } else {
            UnsettledOrder checkUnsettledOrder = this.getBaseMapper().selectById(unsettledOrder.getUnsettledOrderId());
            Assert.notNull(checkUnsettledOrder, "账单不存在");

            if (!StringUtils.equals(UnsettledOrderStatus.CREATE.name(), checkUnsettledOrder.getStatus())
                    &&!StringUtils.equals(UnsettledOrderStatus.REFUSE.name(), checkUnsettledOrder.getStatus())) {
                Assert.isTrue(false, "不是拟态或已驳回不能修改");
            }
            QueryWrapper<UnsettledDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("UNSETTLED_ORDER_ID", unsettledOrder.getUnsettledOrderId());
            iUnsettledDetailService.remove(wrapper);

            QueryWrapper<UnsettledPenalty> wrapper2 = new QueryWrapper<>();
            wrapper.eq("UNSETTLED_ORDER_ID", unsettledOrder.getUnsettledOrderId());
            iUnsettledPenaltyService.remove(wrapper2);
        }
        unsettledOrder.setStatus(ReturnStatus.EDITING.name());
        this.saveOrUpdate(unsettledOrder);

        if (unsettledDetails != null && unsettledDetails.size() > 0) {
            unsettledDetails.forEach(item -> {
                QueryWrapper<UnsettledDetail> wrapper = new QueryWrapper<>(item);
                UnsettledDetail unsettledDetail = iUnsettledDetailService.getOne(wrapper);
                if (unsettledDetail != null && unsettledDetail.getUnsettledOrderId() != null) {
                    Assert.isTrue(false, "存在已关联对账的对账单明细，请重新加载界面");
                }
                item.setUnsettledOrderId(unsettledOrder.getUnsettledOrderId());
            });

            iUnsettledDetailService.updateBatchById(unsettledDetails);
        }

        if (unsettledPenaltys != null && unsettledPenaltys.size() > 0) {
            unsettledPenaltys.forEach(item -> {
                QueryWrapper<UnsettledPenalty> wrapper = new QueryWrapper<>(item);
                UnsettledPenalty unsettledPenalty = iUnsettledPenaltyService.getOne(wrapper);
                if (unsettledPenalty != null) {
                    Assert.isTrue(false, "存在已关联对账的罚扣款账单，请重新加载界面");
                }

                item.setUnsettledOrderId(unsettledOrder.getUnsettledOrderId());
                item.setUnsettledPenaltyId(IdGenrator.generate());
            });

            iUnsettledPenaltyService.saveBatch(unsettledPenaltys);
        }
    }

    @Override
    @Transactional
    public void cancalBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.CREATE.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "只能作废拟态账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.CANCAL.name());

            list.add(order);


            //取消明细关联
            QueryWrapper<UnsettledDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("UNSETTLED_ORDER_ID", item);
            iUnsettledDetailService.remove(wrapper);

            QueryWrapper<UnsettledPenalty> wrapper2 = new QueryWrapper<>();
            wrapper.eq("UNSETTLED_ORDER_ID", item);
            iUnsettledPenaltyService.remove(wrapper2);
        });
        this.updateBatchById(list);
    }

    @Override
    @Transactional
    public void submitBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.CREATE.name(), checkOrder.getStatus())
                    &&!StringUtils.equals(UnsettledOrderStatus.REFUSE.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请提交拟态或已驳回账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.SUBMIT.name());

            list.add(order);
        });
        this.updateBatchById(list);
    }

    @Override
    public void unSubmitBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.SUBMIT.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请撤回已提交账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.CREATE.name());

            list.add(order);
        });
        this.updateBatchById(list);
    }

    @Override
    public void comfirmBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.SUBMIT.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请审核已提交账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.COMFIRM.name());

            list.add(order);
        });
        this.updateBatchById(list);
    }

    @Override
    public void refuseBatch(List<UnsettledOrder> orders) {
        orders.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item.getUnsettledOrderId());
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.SUBMIT.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请驳回已提交账单");
            }

            item.setStatus(UnsettledOrderStatus.REFUSE.name());
        });
        this.updateBatchById(orders);
    }

    @Override
    public void sure(UnsettledOrder order) {
        UnsettledOrder checkOrder = this.getById(order.getUnsettledOrderId());
        Assert.notNull(checkOrder, "找不到账单");
        if (!StringUtils.equals(UnsettledOrderStatus.COMFIRM.name(), checkOrder.getStatus())
            &&!StringUtils.equals(UnsettledOrderStatus.SURE.name(), checkOrder.getStatus())) {
            Assert.isTrue(false, "请操作已审核或已确认账单");
        }
        if (checkOrder.getFileRelationId() != null) {
            try {
                fileCenterClient.delete(checkOrder.getFileRelationId());
            } catch (Exception e) {
                Assert.isTrue(false, "旧附件删除错误");
            }
        }

        order.setStatus(UnsettledOrderStatus.SURE.name());
        this.updateById(order);
    }

    @Override
    public void rollBackBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.SURE.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请退回供应商已确认账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.COMFIRM.name());

            list.add(order);
        });
        this.updateBatchById(list);
    }

    @Override
    public void finishBatch(List<Long> ids) {
        List list = new ArrayList();

        ids.forEach(item -> {
            UnsettledOrder checkOrder = this.getById(item);
            Assert.notNull(checkOrder, "找不到账单");
            if (!StringUtils.equals(UnsettledOrderStatus.SURE.name(), checkOrder.getStatus())) {
                Assert.isTrue(false, "请完成供应商已确认账单");
            }

            UnsettledOrder order = new UnsettledOrder();
            order.setUnsettledOrderId(item);
            order.setStatus(UnsettledOrderStatus.FINISH.name());

            list.add(order);
        });
        this.updateBatchById(list);
    }
}
