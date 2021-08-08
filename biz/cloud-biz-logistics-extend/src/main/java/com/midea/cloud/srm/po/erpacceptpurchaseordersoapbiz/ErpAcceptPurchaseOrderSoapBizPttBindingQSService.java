
package com.midea.cloud.srm.po.erpacceptpurchaseordersoapbiz;

import com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz.PurchaseInputParameters;
import com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz.PurchaseObjectFactory;
import com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz.PurchaseOutputParameters;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "ErpAcceptPurchaseOrderSoapBiz_ptt", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/SRMSB/ErpAcceptPurchaseOrderSoapBiz")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        PurchaseObjectFactory.class
})
public interface ErpAcceptPurchaseOrderSoapBizPttBindingQSService {

    @WebMethod(operationName = "ErpAcceptPurchaseOrderSoapBiz", action = "ErpAcceptPurchaseOrderSoapBiz")
    @WebResult(name = "OutputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", partName = "OutputParameters")
    public PurchaseOutputParameters erpAcceptPurchaseOrderSoapBiz(
            @WebParam(name = "InputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", partName = "InputParameters")
                    PurchaseInputParameters inputParameters);

}
