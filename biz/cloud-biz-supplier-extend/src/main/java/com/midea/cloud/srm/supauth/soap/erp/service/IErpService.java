package com.midea.cloud.srm.supauth.soap.erp.service;

import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorBankResponse;
import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorContactResponse;
import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorResponse;

public interface IErpService {

    /**
     * 获取供应商基础信息
     * @param instId
     * @param requestTime
     * @return
     */
    VendorResponse getVendors(String instId, String requestTime);

    /**
     * 获取供应商联系人信息
     * @param instId
     * @param requestTime
     * @return
     */
    VendorContactResponse getVendorContacts(String instId, String requestTime);

    /**
     * 获取供应商银行信息
     * @param instId
     * @param requestTime
     * @return
     */
    VendorBankResponse getVendorBanks(String instId, String requestTime);
}
