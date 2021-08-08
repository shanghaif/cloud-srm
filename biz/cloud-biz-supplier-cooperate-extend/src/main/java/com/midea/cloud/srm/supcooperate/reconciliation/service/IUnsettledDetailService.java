package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;

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
public interface IUnsettledDetailService extends IService<UnsettledDetail> {

    List findList(UnsettledOrderRequestDTO requestDTO);

    List<UnsettledDetail> materialCountList(UnsettledOrderRequestDTO requestDTO);
}
