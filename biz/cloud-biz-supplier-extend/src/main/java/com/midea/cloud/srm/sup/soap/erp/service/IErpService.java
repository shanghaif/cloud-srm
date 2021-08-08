package com.midea.cloud.srm.sup.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.rbac.po.entity.PoAgent;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorEntity;

import java.util.List;

/**
 * <pre>
 *  供应商模块Erp总线Service
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/27 18:15
 *  修改内容:
 * </pre>
 */
public interface IErpService {

    /**
     * Description
     * @modifiedBy xiexh12@meicloud.com
     * @Date 2020.09.27
     **/
    SoapResponse dealVendorSites(List<VendorEntity> vendorEntityList, String instId, String requestTime);
}
