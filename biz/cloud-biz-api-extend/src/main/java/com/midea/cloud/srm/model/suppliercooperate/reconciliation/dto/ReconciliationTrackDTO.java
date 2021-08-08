package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationInvoice;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationPayment;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/3 15:44
 *  修改内容:
 * </pre>
 */
@Data
public class ReconciliationTrackDTO extends ReconciliationTrack {

    /**
     * 对账单跟踪开票列表
     */
    private List<ReconciliationInvoice> reconciliationInvoices;

    /**
     * 对账单跟踪付款表列表
     */
    private List<ReconciliationPayment> reconciliationPayments;
}
