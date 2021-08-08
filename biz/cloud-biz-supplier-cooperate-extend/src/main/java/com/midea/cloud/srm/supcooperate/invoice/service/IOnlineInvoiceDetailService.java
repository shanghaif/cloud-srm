package com.midea.cloud.srm.supcooperate.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;

import java.util.List;

/**
*  <pre>
 *  网上开票-发票明细表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:48:49
 *  修改内容:
 * </pre>
*/
public interface IOnlineInvoiceDetailService extends IService<OnlineInvoiceDetail> {

    void updateChangeFlag(List<Long> collect);

    void updateUnitPrice(List<Long> collect);

    void updateAmount(List<Long> collect);

    void updateTax(List<Long> collect);

    void updateCompareResult(List<Long> collect);
}
