package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.RequestSettlementOrder;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.PoHeadSettlementErp;

import java.util.List;

/**
*  <pre>
 *   服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-02 15:32:59
 *  修改内容:
 * </pre>
*/
public interface IPoHeadSettlementErpService extends IService<PoHeadSettlementErp> {

    SoapResponse acceptErpData(List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD> recordList, String instId, String requestTime);
}
