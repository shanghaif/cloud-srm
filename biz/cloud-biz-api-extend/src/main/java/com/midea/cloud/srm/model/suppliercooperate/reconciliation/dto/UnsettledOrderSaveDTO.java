package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledOrder;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledPenalty;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/27 21:22
 *  修改内容:
 * </pre>
 */
@Data
public class UnsettledOrderSaveDTO {
    /**
     * 未结算数量账单
     */
    private UnsettledOrder unsettledOrder;

    /**
     * 未结算数量账单明细
     */
    private List<UnsettledDetail> unsettledDetails;

    /**
     * 未结算数量账单罚扣款明细
     */
    private List<UnsettledPenalty> unsettledPenaltys;
}
