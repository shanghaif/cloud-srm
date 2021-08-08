package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationInvoice;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationPayment;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  对账单跟踪excel导入模板接收对象 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/1 20:14
 *  修改内容:
 * </pre>
 */
@Data
public class ExcelReconciliationTrackReqDTO {
    private Date date;

    private ReconciliationTrack reconciliationTrack;

    private List<ReconciliationInvoice> reconciliationInvoices;

    private List<ReconciliationPayment> reconciliationPayments;
}
