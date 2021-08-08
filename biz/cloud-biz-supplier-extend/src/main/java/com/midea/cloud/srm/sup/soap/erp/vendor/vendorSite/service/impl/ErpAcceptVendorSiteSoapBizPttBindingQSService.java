
package com.midea.cloud.srm.sup.soap.erp.vendor.vendorSite.service.impl;

import com.midea.cloud.srm.sup.SupplierSoapUrl;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendorSite.service.ErpAcceptVendorSiteSoapBizPtt;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSService", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/MdmSB/ErpAcceptVendorSiteSoapBiz"
//        , wsdlLocation = "http://soatest.longi.com:8011/MdmSB/Erp/VendorSite/ProxyServices/ErpAcceptVendorSiteSoapProxy?wsdl"
)
public class ErpAcceptVendorSiteSoapBizPttBindingQSService
    extends Service
{

    private final static URL ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_WSDL_LOCATION;
    private final static WebServiceException ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_EXCEPTION;
    private final static QName ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/MdmSB/ErpAcceptVendorSiteSoapBiz", "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(SupplierSoapUrl.sendVendorSiteUrl);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_WSDL_LOCATION = url;
        ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_EXCEPTION = e;
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService() {
        super(__getWsdlLocation(), ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_QNAME);
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_QNAME, features);
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService(URL wsdlLocation) {
        super(wsdlLocation, ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_QNAME);
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_QNAME, features);
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ErpAcceptVendorSiteSoapBizPttBindingQSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ErpAcceptVendorSiteSoapBizPtt
     */
    @WebEndpoint(name = "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSPort")
    public ErpAcceptVendorSiteSoapBizPtt getErpAcceptVendorSiteSoapBizPttBindingQSPort() {
        return super.getPort(new QName("http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/MdmSB/ErpAcceptVendorSiteSoapBiz", "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSPort"), ErpAcceptVendorSiteSoapBizPtt.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ErpAcceptVendorSiteSoapBizPtt
     */
    @WebEndpoint(name = "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSPort")
    public ErpAcceptVendorSiteSoapBizPtt getErpAcceptVendorSiteSoapBizPttBindingQSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://xmlns.oracle.com/pcbpel/adapter/db/LongiServiceBusApp/MdmSB/ErpAcceptVendorSiteSoapBiz", "ErpAcceptVendorSiteSoapBiz_ptt-bindingQSPort"), ErpAcceptVendorSiteSoapBizPtt.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_EXCEPTION!= null) {
            throw ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_EXCEPTION;
        }
        return ERPACCEPTVENDORSITESOAPBIZPTTBINDINGQSSERVICE_WSDL_LOCATION;
    }

}
