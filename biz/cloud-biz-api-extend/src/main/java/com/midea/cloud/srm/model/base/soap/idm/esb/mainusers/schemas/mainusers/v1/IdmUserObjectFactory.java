
package com.midea.cloud.srm.model.base.soap.idm.esb.mainusers.schemas.mainusers.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.longi.idmsb.idm.esb.mainusers.schemas.mainusers.v1 package. 
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
public class IdmUserObjectFactory {

    private final static QName _RequestRequestInfoMainUsersSysCode_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "SysCode");
    private final static QName _RequestRequestInfoMainUsersMainUserUSEREMAIL_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "USER_EMAIL");
    private final static QName _RequestRequestInfoMainUsersMainUserAttr5_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "Attr5");
    private final static QName _RequestRequestInfoMainUsersMainUserAttr2_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "Attr2");
    private final static QName _RequestRequestInfoMainUsersMainUserAttr1_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "Attr1");
    private final static QName _RequestRequestInfoMainUsersMainUserAttr4_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "Attr4");
    private final static QName _RequestRequestInfoMainUsersMainUserAttr3_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "Attr3");
    private final static QName _RequestRequestInfoMainUsersMainUserUSERTYPE_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "USER_TYPE");
    private final static QName _RequestRequestInfoMainUsersMainUserPASSWORD_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "PASSWORD");
    private final static QName _RequestRequestInfoMainUsersMainUserSTATUS_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "STATUS");
    private final static QName _RequestRequestInfoMainUsersMainUserCOMPANYNAME_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "COMPANY_NAME");
    private final static QName _RequestRequestInfoMainUsersMainUserUSERNAMEC_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "USER_NAME_C");
    private final static QName _RequestRequestInfoMainUsersMainUserUSERTEL_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "USER_TEL");
    private final static QName _RequestRequestInfoMainUsersMainUserEDIBPMTIME_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "EDI_BPM_TIME");
    private final static QName _RequestRequestInfoMainUsersMainUserEDIBPMFLAG_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "EDI_BPM_FLAG");
    private final static QName _RequestRequestInfoMainUsersMainUserUSERID_QNAME = new QName("http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", "USERID");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.longi.idmsb.idm.esb.mainusers.schemas.mainusers.v1
     * 
     */
    public IdmUserObjectFactory() {
    }

    /**
     * Create an instance of {@link IdmUserSoapRequest }
     * 
     */
    public IdmUserSoapRequest createRequest() {
        return new IdmUserSoapRequest();
    }

    /**
     * Create an instance of {@link IdmUserResponse }
     * 
     */
    public IdmUserResponse createResponse() {
        return new IdmUserResponse();
    }

    /**
     * Create an instance of {@link IdmUserSoapRequest.RequestInfo }
     * 
     */
    public IdmUserSoapRequest.RequestInfo createRequestRequestInfo() {
        return new IdmUserSoapRequest.RequestInfo();
    }

    /**
     * Create an instance of {@link IdmUserSoapRequest.RequestInfo.MainUsers }
     * 
     */
    public IdmUserSoapRequest.RequestInfo.MainUsers createRequestRequestInfoMainUsers() {
        return new IdmUserSoapRequest.RequestInfo.MainUsers();
    }

    /**
     * Create an instance of {@link IdmUserSoapRequest.EsbInfo }
     * 
     */
    public IdmUserSoapRequest.EsbInfo createRequestEsbInfo() {
        return new IdmUserSoapRequest.EsbInfo();
    }

    /**
     * Create an instance of {@link IdmUserResponse.EsbInfo }
     * 
     */
    public IdmUserResponse.EsbInfo createResponseEsbInfo() {
        return new IdmUserResponse.EsbInfo();
    }

    /**
     * Create an instance of {@link IdmUserSoapRequest.RequestInfo.MainUsers.MainUser }
     * 
     */
    public IdmUserSoapRequest.RequestInfo.MainUsers.MainUser createRequestRequestInfoMainUsersMainUser() {
        return new IdmUserSoapRequest.RequestInfo.MainUsers.MainUser();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "SysCode", scope = IdmUserSoapRequest.RequestInfo.MainUsers.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersSysCode(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersSysCode_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "USER_EMAIL", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserUSEREMAIL(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserUSEREMAIL_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "Attr5", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserAttr5(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserAttr5_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "Attr2", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserAttr2(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserAttr2_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "Attr1", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserAttr1(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserAttr1_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "Attr4", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserAttr4(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserAttr4_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "Attr3", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserAttr3(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserAttr3_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "USER_TYPE", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserUSERTYPE(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserUSERTYPE_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "PASSWORD", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserPASSWORD(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserPASSWORD_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "STATUS", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserSTATUS(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserSTATUS_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "COMPANY_NAME", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserCOMPANYNAME(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserCOMPANYNAME_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "USER_NAME_C", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserUSERNAMEC(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserUSERNAMEC_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "USER_TEL", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserUSERTEL(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserUSERTEL_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "EDI_BPM_TIME", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserEDIBPMTIME(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserEDIBPMTIME_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "EDI_BPM_FLAG", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserEDIBPMFLAG(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserEDIBPMFLAG_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", name = "USERID", scope = IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class)
    public JAXBElement<String> createRequestRequestInfoMainUsersMainUserUSERID(String value) {
        return new JAXBElement<String>(_RequestRequestInfoMainUsersMainUserUSERID_QNAME, String.class, IdmUserSoapRequest.RequestInfo.MainUsers.MainUser.class, value);
    }

}
