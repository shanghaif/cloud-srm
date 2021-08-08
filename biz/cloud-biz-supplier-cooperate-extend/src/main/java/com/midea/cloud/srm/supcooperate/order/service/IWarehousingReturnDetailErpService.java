package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;

/**
*  <pre>
 *  入库退货明细erp表 服务类
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:30:44
 *  修改内容:
 * </pre>
*/
public interface IWarehousingReturnDetailErpService extends IService<WarehousingReturnDetailErp> {

    BaseResult transferErpToSrm();
}
