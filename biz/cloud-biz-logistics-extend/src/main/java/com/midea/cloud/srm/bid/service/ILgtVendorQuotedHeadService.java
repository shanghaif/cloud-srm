package com.midea.cloud.srm.bid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedHead;

import java.util.List;

/**
*  <pre>
 *  供应商报价头表 服务类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
public interface ILgtVendorQuotedHeadService extends IService<LgtVendorQuotedHead> {

    List<LgtVendorQuotedHead> listCurrency(LgtVendorQuotedHead vendorQuotedHead);
}
