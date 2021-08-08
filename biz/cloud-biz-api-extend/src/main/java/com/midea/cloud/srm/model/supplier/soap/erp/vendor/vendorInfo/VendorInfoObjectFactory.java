
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class VendorInfoObjectFactory {

    private final static QName _APPSCUXPOVENDORSYX1137205X1X1INSTID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "INSTID");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X1REQUESTTIME_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "REQUESTTIME");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X1ATTR3_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "ATTR3");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X1ATTR2_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "ATTR2");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X1ATTR1_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "ATTR1");
    private final static QName _InputParametersPVENDORTBL_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "P_VENDOR_TBL");
    private final static QName _InputParametersPESBINFOREC_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "P_ESB_INFO_REC");
    private final static QName _OutputParametersXESBRESULTINFOREC_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "X_ESB_RESULT_INFO_REC");
    private final static QName _APPSCUXPOVENDORSX1137205X1X18RETURNSTATUS_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "RETURNSTATUS");
    private final static QName _APPSCUXPOVENDORSX1137205X1X18RETURNMSG_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "RETURNMSG");
    private final static QName _APPSCUXPOVENDORSX1137205X1X18RESPONSETIME_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "RESPONSETIME");
    private final static QName _APPSCUXPOVENDORSX1137205X1X18RETURNCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "RETURNCODE");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8VENDORNUMBER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "VENDORNUMBER");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8SOURCESYSCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "SOURCESYSCODE");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8ATTR5_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "ATTR5");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8ATTR4_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "ATTR4");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8SOURCELINEID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "SOURCELINEID");
    private final static QName _APPSCUXPOVENDORSYX1137205X1X8VENDORNAME_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", "VENDORNAME");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vendor
     * 
     */
    public VendorInfoObjectFactory() {
    }

    /**
     * Create an instance of {@link VendorInfoOutputParameters }
     *
     */
    public VendorInfoOutputParameters createOutputParameters() {
        return new VendorInfoOutputParameters();
    }

    /**
     * Create an instance of {@link APPSCUXPOVENDORSX1137205X1X18 }
     *
     */
    public APPSCUXPOVENDORSX1137205X1X18 createAPPSCUXPOVENDORSX1137205X1X18() {
        return new APPSCUXPOVENDORSX1137205X1X18();
    }

    /**
     * Create an instance of {@link VendorInfoInputParameters }
     *
     */
    public VendorInfoInputParameters createInputParameters() {
        return new VendorInfoInputParameters();
    }

    /**
     * Create an instance of {@link APPSCUXPOVENDORSYX1137205X1X1 }
     *
     */
    public APPSCUXPOVENDORSYX1137205X1X1 createAPPSCUXPOVENDORSYX1137205X1X1() {
        return new APPSCUXPOVENDORSYX1137205X1X1();
    }

    /**
     * Create an instance of {@link APPSCUXPOVENDORSYX1137205X1X7 }
     *
     */
    public APPSCUXPOVENDORSYX1137205X1X7 createAPPSCUXPOVENDORSYX1137205X1X7() {
        return new APPSCUXPOVENDORSYX1137205X1X7();
    }

    /**
     * Create an instance of {@link APPSCUXPOVENDORSYX1137205X1X8 }
     *
     */
    public APPSCUXPOVENDORSYX1137205X1X8 createAPPSCUXPOVENDORSYX1137205X1X8() {
        return new APPSCUXPOVENDORSYX1137205X1X8();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "INSTID", scope = APPSCUXPOVENDORSYX1137205X1X1.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X1INSTID(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1INSTID_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X1.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "REQUESTTIME", scope = APPSCUXPOVENDORSYX1137205X1X1.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X1REQUESTTIME(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1REQUESTTIME_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X1.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR3", scope = APPSCUXPOVENDORSYX1137205X1X1.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X1ATTR3(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR3_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X1.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR2", scope = APPSCUXPOVENDORSYX1137205X1X1.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X1ATTR2(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR2_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X1.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR1", scope = APPSCUXPOVENDORSYX1137205X1X1.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X1ATTR1(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR1_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X1.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link APPSCUXPOVENDORSYX1137205X1X7 }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "P_VENDOR_TBL", scope = VendorInfoInputParameters.class)
    public JAXBElement<APPSCUXPOVENDORSYX1137205X1X7> createInputParametersPVENDORTBL(APPSCUXPOVENDORSYX1137205X1X7 value) {
        return new JAXBElement<APPSCUXPOVENDORSYX1137205X1X7>(_InputParametersPVENDORTBL_QNAME, APPSCUXPOVENDORSYX1137205X1X7.class, VendorInfoInputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link APPSCUXPOVENDORSYX1137205X1X1 }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "P_ESB_INFO_REC", scope = VendorInfoInputParameters.class)
    public JAXBElement<APPSCUXPOVENDORSYX1137205X1X1> createInputParametersPESBINFOREC(APPSCUXPOVENDORSYX1137205X1X1 value) {
        return new JAXBElement<APPSCUXPOVENDORSYX1137205X1X1>(_InputParametersPESBINFOREC_QNAME, APPSCUXPOVENDORSYX1137205X1X1.class, VendorInfoInputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link APPSCUXPOVENDORSX1137205X1X18 }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "X_ESB_RESULT_INFO_REC", scope = VendorInfoOutputParameters.class)
    public JAXBElement<APPSCUXPOVENDORSX1137205X1X18> createOutputParametersXESBRESULTINFOREC(APPSCUXPOVENDORSX1137205X1X18 value) {
        return new JAXBElement<APPSCUXPOVENDORSX1137205X1X18>(_OutputParametersXESBRESULTINFOREC_QNAME, APPSCUXPOVENDORSX1137205X1X18.class, VendorInfoOutputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "RETURNSTATUS", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18RETURNSTATUS(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSX1137205X1X18RETURNSTATUS_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "INSTID", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18INSTID(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1INSTID_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "REQUESTTIME", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18REQUESTTIME(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1REQUESTTIME_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "RETURNMSG", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18RETURNMSG(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSX1137205X1X18RETURNMSG_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "RESPONSETIME", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18RESPONSETIME(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSX1137205X1X18RESPONSETIME_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "RETURNCODE", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18RETURNCODE(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSX1137205X1X18RETURNCODE_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR3", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18ATTR3(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR3_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR2", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18ATTR2(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR2_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR1", scope = APPSCUXPOVENDORSX1137205X1X18.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSX1137205X1X18ATTR1(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR1_QNAME, String.class, APPSCUXPOVENDORSX1137205X1X18.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "VENDORNUMBER", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8VENDORNUMBER(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8VENDORNUMBER_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "SOURCESYSCODE", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8SOURCESYSCODE(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8SOURCESYSCODE_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR5", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8ATTR5(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8ATTR5_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR4", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8ATTR4(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8ATTR4_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR3", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8ATTR3(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR3_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "SOURCELINEID", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8SOURCELINEID(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8SOURCELINEID_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR2", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8ATTR2(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR2_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "VENDORNAME", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8VENDORNAME(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X8VENDORNAME_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", name = "ATTR1", scope = APPSCUXPOVENDORSYX1137205X1X8.class)
    public JAXBElement<String> createAPPSCUXPOVENDORSYX1137205X1X8ATTR1(String value) {
        return new JAXBElement<String>(_APPSCUXPOVENDORSYX1137205X1X1ATTR1_QNAME, String.class, APPSCUXPOVENDORSYX1137205X1X8.class, value);
    }

}
