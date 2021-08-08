package com.midea.cloud.srm.model.cm.accept.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.vispractice.f10.soap package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class AssetsReimbursement {

    private final static QName _BusinessFormResponse_QNAME = new QName("http://soap.f10.vispractice.com", "businessFormResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vispractice.f10.soap
     */
    public AssetsReimbursement() {
    }

    /**
     * Create an instance of {@link BusinessFormRequest }
     */
    public BusinessFormRequest createBusinessFormRequest() {
        return new BusinessFormRequest();
    }

    /**
     * Create an instance of {@link BusinessFormRequest.BUSINESSFORMS }
     */
    public BusinessFormRequest.BUSINESSFORMS createBusinessFormRequestBUSINESSFORMS() {
        return new BusinessFormRequest.BUSINESSFORMS();
    }

    /**
     * Create an instance of {@link ErpBudgetResponse }
     */
    public ErpBudgetResponse createErpBudgetResponse() {
        return new ErpBudgetResponse();
    }

    /**
     * Create an instance of {@link ResponseBody }
     */
    public ResponseBody createResponseBody() {
        return new ResponseBody();
    }

    /**
     * Create an instance of {@link BusinessFormResponse }
     */
    public BusinessFormResponse createBusinessFormResponse() {
        return new BusinessFormResponse();
    }

    /**
     * Create an instance of {@link BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM }
     */
    public BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM createBusinessFormRequestBUSINESSFORMSBUSINESSFORM() {
        return new BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessFormResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://soap.f10.vispractice.com", name = "businessFormResponse")
    public JAXBElement<BusinessFormResponse> createBusinessFormResponse(BusinessFormResponse value) {
        return new JAXBElement<BusinessFormResponse>(_BusinessFormResponse_QNAME, BusinessFormResponse.class, null, value);
    }

}