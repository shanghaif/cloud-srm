package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ExcelReconciliationTrackReqDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationInvoice;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationPayment;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;

import java.util.List;

/**
 * <pre>
 *  对账单跟踪付款表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/1 20:21
 *  修改内容:
 * </pre>
 */
public interface IReconciliationTrackService extends IService<ReconciliationTrack> {

    void saveOrUpdate(ReconciliationTrack reconciliationTrack, List<ReconciliationInvoice> reconciliationInvoices, List<ReconciliationPayment> reconciliationPayments);

    void saveBatchByExcel(List<ExcelReconciliationTrackReqDTO> list);

    List listPage(ReconciliationTrackRequestDTO requestDTO);
}
