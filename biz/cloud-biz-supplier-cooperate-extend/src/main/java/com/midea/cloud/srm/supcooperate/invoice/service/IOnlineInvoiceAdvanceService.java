package com.midea.cloud.srm.supcooperate.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;

import java.util.List;

/**
*  <pre>
 *  网上开票-预付款 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:51:12
 *  修改内容:
 * </pre>
*/
public interface IOnlineInvoiceAdvanceService extends IService<OnlineInvoiceAdvance> {

    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNo(String fsscNo);

    void updateOnlineInvoiceAdvanceByParam(List<OnlineInvoiceAdvance> onlineInvoiceAdvances);

    List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByOnlineInvoiceId(Long onlineInvoiceId);
}
