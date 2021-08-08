
package com.midea.cloud.srm.sup.soap.erp.vendor.vendorSite.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteInputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteObjectFactory;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteOutputParameters;


@WebService(name = "ErpAcceptVendorSiteSoapBiz_ptt", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/MdmSB/ErpAcceptVendorSiteSoapBiz")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    VendorSiteObjectFactory.class
})
public interface ErpAcceptVendorSiteSoapBizPtt {


    @WebMethod(operationName = "ErpAcceptVendorSiteSoapBiz", action = "ErpAcceptVendorSiteSoapBiz")
    @WebResult(name = "OutputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", partName = "OutputParameters")
    public VendorSiteOutputParameters erpAcceptVendorSiteSoapBiz(
            @WebParam(name = "InputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", partName = "InputParameters")
                    VendorSiteInputParameters inputParameters);

}
