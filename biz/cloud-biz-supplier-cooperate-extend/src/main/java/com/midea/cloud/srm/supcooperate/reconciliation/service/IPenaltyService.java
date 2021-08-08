package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.Penalty;

import java.util.List;

/**
 * <pre>
 *  罚扣款单表 服务类
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
public interface IPenaltyService extends IService<Penalty> {

    void saveBatchByExcel(List<Penalty> list);
}
