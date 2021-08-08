package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ShouldPayOpenBillRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ShouldPayOpenBill;

import java.util.List;

/**
 * <pre>
 *  应付款明细表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 21:15
 *  修改内容:
 * </pre>
 */
public interface IShouldPayOpenBillService extends IService<ShouldPayOpenBill> {

    void saveBatchByExcel(List<ShouldPayOpenBill> list);

    ShouldPayOpenBill getSum(ShouldPayOpenBillRequestDTO requestDTO);

    List listPage(ShouldPayOpenBillRequestDTO requestDTO);
}
