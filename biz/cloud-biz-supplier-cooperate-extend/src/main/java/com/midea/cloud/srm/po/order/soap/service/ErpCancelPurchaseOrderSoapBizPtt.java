
package com.midea.cloud.srm.po.order.soap.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.midea.cloud.srm.model.pm.soap.order.OrderChangeInputParameters;
import com.midea.cloud.srm.model.pm.soap.order.OrderChangeObjectFactory;
import com.midea.cloud.srm.model.pm.soap.order.OrderChangeOutputParameters;


/**
 * Description erp订单变更Service接口
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.09.29
 * @throws
 **/
@WebService(name = "ErpCancelPurchaseOrderSoapBiz_ptt", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/ServiceBusApplication/NSrm/ErpCancelPurchaseOrderSoapBiz")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    OrderChangeObjectFactory.class
})
public interface ErpCancelPurchaseOrderSoapBizPtt {


    /**
     * 
     * @param inputParameters
     * @return
     *     returns com.oracle.xmlns.pcbpel.adapter.db.sp.erpcancelpurchaseordersoapbiz.OutputParameters
     */
    @WebMethod(operationName = "ErpCancelPurchaseOrderSoapBiz", action = "ErpCancelPurchaseOrderSoapBiz")
    @WebResult(name = "OutputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz", partName = "OutputParameters")
    public OrderChangeOutputParameters erpCancelPurchaseOrderSoapBiz(
            @WebParam(name = "InputParameters", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz", partName = "InputParameters")
                    OrderChangeInputParameters inputParameters);

}
