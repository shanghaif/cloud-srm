package com.midea.cloud.srm.supcooperate.reconciliation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.AccountsPayable;

import java.math.BigDecimal;

/**
 * <pre>
 *  应付款明细表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface AccountsPayableMapper extends BaseMapper<AccountsPayable> {

    AccountsPayable sum(AccountsPayable accountsPayable);

    BigDecimal getLastEndingBalance(AccountsPayable accountsPayable);
}