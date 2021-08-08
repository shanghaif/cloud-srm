package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.AccountsPayableRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.AccountsPayable;

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
 *  修改日期: 2020/4/3 8:49
 *  修改内容:
 * </pre>
 */
public interface IAccountsPayableService extends IService<AccountsPayable> {

    void saveBatchByExcel(List<AccountsPayable> list);

    AccountsPayable getSum(AccountsPayableRequestDTO requestDTO);

    List listPage(AccountsPayableRequestDTO requestDTO);
}
