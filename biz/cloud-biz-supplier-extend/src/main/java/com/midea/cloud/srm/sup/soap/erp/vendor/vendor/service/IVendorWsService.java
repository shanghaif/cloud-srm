package com.midea.cloud.srm.sup.soap.erp.vendor.vendor.service;


import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorRequest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iVendorWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        VendorRequest.class
})
public interface IVendorWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "vendorResponse_part")
    SoapResponse execute(@WebParam(name = "vendorRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "vendorResponse_part")
                                 VendorRequest vendorRequest);
}
