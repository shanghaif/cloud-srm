package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;

import java.util.Collection;
import java.util.List;

/**
*  <pre>
 *  价格库付款条款 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-24 22:14:25
 *  修改内容:
 * </pre>
*/
public interface IPriceLibraryPaymentTermService extends IService<PriceLibraryPaymentTerm> {

    List<PriceLibraryPaymentTerm> listByPriceLibraryIdCollection(Collection<Long> priceLibraryIdCollection);

}
