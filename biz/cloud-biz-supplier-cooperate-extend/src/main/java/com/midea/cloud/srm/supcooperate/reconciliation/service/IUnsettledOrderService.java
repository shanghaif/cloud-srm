package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledOrder;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:20
 *  修改内容:
 * </pre>
 */
public interface IUnsettledOrderService extends IService<UnsettledOrder> {

    void saveOrUpdate(UnsettledOrder unsettledOrder, List<UnsettledDetail> unsettledDetails, List<UnsettledPenalty> unsettledPenaltys);

    void cancalBatch(List<Long> ids);

    void submitBatch(List<Long> ids);

    void unSubmitBatch(List<Long> ids);

    void comfirmBatch(List<Long> ids);

    void refuseBatch(List<UnsettledOrder> orders);

    void sure(UnsettledOrder order);

    void rollBackBatch(List<Long> ids);

    void finishBatch(List<Long> ids);
}
