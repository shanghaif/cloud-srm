
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import com.midea.cloud.srm.model.logistics.soap.logisticsorder.response.ResponseBody;
import com.midea.cloud.srm.model.logistics.soap.logisticsorder.response.ResultInfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.longi.tmssb.srm.logisticscontractrate.schemas.v1 package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSrmTariffInfoResponse_QNAME = new QName("http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0", "getSrmTariffInfoResponse");
    private final static QName _GetSrmTariffInfo_QNAME = new QName("http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0", "getSrmTariffInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.longi.tmssb.srm.logisticscontractrate.schemas.v1
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSrmTariffInfoResponse }
     *
     */
    public GetSrmTariffInfoResponse createGetSrmTariffInfoResponse() {
        return new GetSrmTariffInfoResponse();
    }

    /**
     * Create an instance of {@link GetSrmTariffInfo }
     *
     */
    public GetSrmTariffInfo createGetSrmTariffInfo() {
        return new GetSrmTariffInfo();
    }

    /**
     * Create an instance of {@link Head }
     *
     */
    public Head createHead() {
        return new Head();
    }

    /**
     * Create an instance of {@link ResponseBody }
     *
     */
    public ResponseBody createResponseBody() {
        return new ResponseBody();
    }

    /**
     * Create an instance of {@link Road }
     *
     */
    public Road createRoad() {
        return new Road();
    }

    /**
     * Create an instance of {@link Rate }
     *
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link EsbInfo }
     *
     */
    public EsbInfo createEsbInfo() {
        return new EsbInfo();
    }

    /**
     * Create an instance of {@link EsbInfoRes }
     *
     */
    public EsbInfoRes createEsbInfoRes() {
        return new EsbInfoRes();
    }

    /**
     * Create an instance of {@link ResultInfo }
     *
     */
    public ResultInfo createResultInfo() {
        return new ResultInfo();
    }

    /**
     * Create an instance of {@link Boat }
     *
     */
    public Boat createBoat() {
        return new Boat();
    }

    /**
     * Create an instance of {@link Customerinfo }
     *
     */
    public Customerinfo createCustomerinfo() {
        return new Customerinfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSrmTariffInfoResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0", name = "getSrmTariffInfoResponse")
    public JAXBElement<GetSrmTariffInfoResponse> createGetSrmTariffInfoResponse(GetSrmTariffInfoResponse value) {
        return new JAXBElement<GetSrmTariffInfoResponse>(_GetSrmTariffInfoResponse_QNAME, GetSrmTariffInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSrmTariffInfo }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0", name = "getSrmTariffInfo")
    public JAXBElement<GetSrmTariffInfo> createGetSrmTariffInfo(GetSrmTariffInfo value) {
        return new JAXBElement<GetSrmTariffInfo>(_GetSrmTariffInfo_QNAME, GetSrmTariffInfo.class, null, value);
    }

    public static QName get_GetSrmTariffInfoResponse_QNAME() {
        return _GetSrmTariffInfoResponse_QNAME;
    }

    public static QName get_GetSrmTariffInfo_QNAME() {
        return _GetSrmTariffInfo_QNAME;
    }
}
