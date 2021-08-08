package com.midea.cloud.srm.supauth.soap.erp.webservice.service;


import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorBankResponse;
import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorContactResponse;
import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.VendorResquest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iVendorBankWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        VendorResquest.class
})
public interface IVendorBankWsService {

    @WebMethod(action = "execute")
    @WebResult(name = "vendorBankResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "vendorBankResponse_part")
    VendorBankResponse execute(@WebParam(name = "vendorRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "vendorRequest_part")
                                          VendorResquest request);
}
