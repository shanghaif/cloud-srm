package com.midea.cloud.srm.supcooperate.reconciliation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ShouldPayOpenBill;

import java.math.BigDecimal;

/**
 * <pre>
 *  应付未开票表  Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 21:09
 *  修改内容:
 * </pre>
 */
public interface ShouldPayOpenBillMapper extends BaseMapper<ShouldPayOpenBill> {

    ShouldPayOpenBill sum(ShouldPayOpenBill shouldPayOpenBill);

    BigDecimal getLastEndingBalance(ShouldPayOpenBill shouldPayOpenBill);
}