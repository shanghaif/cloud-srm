
package com.midea.cloud.srm.cm.soap;

import com.midea.cloud.srm.cm.CmSaopUrl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "execute_bindQSService", targetNamespace = "http://www.longi.com/CMSSB/Esb/OeBlanket/WSDLs/EsbPublishOeBlanketSoapProxy/v1.0"
//        , wsdlLocation = "http://soatest.longi.com:8011/CMSSB/Esb/OeBlanket/ProxyServices/CmsSendOeBlanketSoapProxy?wsdl"
)
public class ContractExecuteBindQSService
    extends Service
{

    private final static URL EXECUTEBINDQSSERVICE_WSDL_LOCATION;
    private final static WebServiceException EXECUTEBINDQSSERVICE_EXCEPTION;
    private final static QName EXECUTEBINDQSSERVICE_QNAME = new QName("http://www.longi.com/CMSSB/Esb/OeBlanket/WSDLs/EsbPublishOeBlanketSoapProxy/v1.0", "execute_bindQSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(CmSaopUrl.contractUrl);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EXECUTEBINDQSSERVICE_WSDL_LOCATION = url;
        EXECUTEBINDQSSERVICE_EXCEPTION = e;
    }

    public ContractExecuteBindQSService() {
        super(__getWsdlLocation(), EXECUTEBINDQSSERVICE_QNAME);
    }

    public ContractExecuteBindQSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), EXECUTEBINDQSSERVICE_QNAME, features);
    }

    public ContractExecuteBindQSService(URL wsdlLocation) {
        super(wsdlLocation, EXECUTEBINDQSSERVICE_QNAME);
    }

    public ContractExecuteBindQSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EXECUTEBINDQSSERVICE_QNAME, features);
    }

    public ContractExecuteBindQSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ContractExecuteBindQSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ExecutePtt
     */
    @WebEndpoint(name = "execute_bindQSPort")
    public ContractExecutePtt getExecuteBindQSPort() {
        return super.getPort(new QName("http://www.longi.com/CMSSB/Esb/OeBlanket/WSDLs/EsbPublishOeBlanketSoapProxy/v1.0", "execute_bindQSPort"), ContractExecutePtt.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ExecutePtt
     */
    @WebEndpoint(name = "execute_bindQSPort")
    public ContractExecutePtt getExecuteBindQSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.longi.com/CMSSB/Esb/OeBlanket/WSDLs/EsbPublishOeBlanketSoapProxy/v1.0", "execute_bindQSPort"), ContractExecutePtt.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EXECUTEBINDQSSERVICE_EXCEPTION!= null) {
            throw EXECUTEBINDQSSERVICE_EXCEPTION;
        }
        return EXECUTEBINDQSSERVICE_WSDL_LOCATION;
    }

}
