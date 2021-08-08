package com.midea.cloud.srm.model.supplierauth.soap.erp.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:35
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "response"
})
@XmlRootElement(name = "vendorBankResponse")
public class VendorBankResponse {

    @XmlElement(name = "RESPONSE", required = true)
    protected VendorBankResponse.RESPONSE response;
    @XmlAttribute(name = "success")
    protected String success;

    /**
     * 获取response属性的值。
     */
    public VendorBankResponse.RESPONSE getResponse() {
        return response;
    }

    /**
     * 设置response属性的值。
     */
    public void setResponse(VendorBankResponse.RESPONSE value) {
        this.response = value;
    }

    /**
     * 获取success属性的值。
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置success属性的值。
     */
    public void setSuccess(String value) {
        this.success = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "esbInfo", "responseInfo"
    })
    public static class RESPONSE {

        @XmlElement(required = true)
        protected VendorBankResponse.RESPONSE.EsbInfo esbInfo;
        @XmlElement(required = true)
        protected VendorBankResponse.RESPONSE.ResponseInfo responseInfo;

        /**
         * 获取esbInfo属性的值。
         */
        public VendorBankResponse.RESPONSE.EsbInfo getEsbInfo() {
            return esbInfo;
        }

        /**
         * 设置esbInfo属性的值。
         */
        public void setEsbInfo(VendorBankResponse.RESPONSE.EsbInfo value) {
            this.esbInfo = value;
        }

        /**
         * 获取resultInfo属性的值。
         */
        public VendorBankResponse.RESPONSE.ResponseInfo getResponseInfo() {
            return responseInfo;
        }

        /**
         * 设置resultInfo属性的值。
         */
        public void setResponseInfo(VendorBankResponse.RESPONSE.ResponseInfo value) {
            this.responseInfo = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "instId",
                "returnStatus",
                "returnCode",
                "returnMsg",
                "requestTime",
                "responseTime",
                "attr1",
                "attr2",
                "attr3"
        })
        public static class EsbInfo {

            @XmlElement(required = true)
            protected String instId;
            @XmlElement(required = true)
            protected String returnStatus;
            @XmlElement(required = true)
            protected String returnCode;
            @XmlElement(required = true)
            protected String returnMsg;
            @XmlElement(required = true)
            protected String requestTime;
            @XmlElement(required = true)
            protected String responseTime;
            @XmlElement(required = true)
            protected String attr1;
            @XmlElement(required = true)
            protected String attr2;
            @XmlElement(required = true)
            protected String attr3;

            /**
             * 获取instId属性的值。
             */
            public String getInstId() {
                return instId;
            }

            /**
             * 设置instId属性的值。
             */
            public void setInstId(String value) {
                this.instId = value;
            }

            /**
             * 获取returnStatus属性的值。
             */
            public String getReturnStatus() {
                return returnStatus;
            }

            /**
             * 设置returnStatus属性的值。
             */
            public void setReturnStatus(String value) {
                this.returnStatus = value;
            }

            /**
             * 获取returnCode属性的值。
             */
            public String getReturnCode() {
                return returnCode;
            }

            /**
             * 设置returnCode属性的值。
             */
            public void setReturnCode(String value) {
                this.returnCode = value;
            }

            /**
             * 获取returnMsg属性的值。
             */
            public String getReturnMsg() {
                return returnMsg;
            }

            /**
             * 设置returnMsg属性的值。
             */
            public void setReturnMsg(String value) {
                this.returnMsg = value;
            }

            /**
             * 获取requestTime属性的值。
             */
            public String getRequestTime() {
                return requestTime;
            }

            /**
             * 设置requestTime属性的值。
             */
            public void setRequestTime(String value) {
                this.requestTime = value;
            }

            /**
             * 获取responseTime属性的值。
             */
            public String getResponseTime() {
                return responseTime;
            }

            /**
             * 设置responseTime属性的值。
             */
            public void setResponseTime(String value) {
                this.responseTime = value;
            }

            /**
             * 获取attr1属性的值。
             */
            public String getAttr1() {
                return attr1;
            }

            /**
             * 设置attr1属性的值。
             */
            public void setAttr1(String value) {
                this.attr1 = value;
            }

            /**
             * 获取attr2属性的值。
             */
            public String getAttr2() {
                return attr2;
            }

            /**
             * 设置attr2属性的值。
             */
            public void setAttr2(String value) {
                this.attr2 = value;
            }

            /**
             * 获取attr3属性的值。
             */
            public String getAttr3() {
                return attr3;
            }

            /**
             * 设置attr3属性的值。
             */
            public void setAttr3(String value) {
                this.attr3 = value;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {
                "vendorBanks"
        })
        public static class ResponseInfo{

            @XmlElement
            private VendorBankResponse.RESPONSE.ResponseInfo.VendorBanks vendorBanks;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(propOrder = {"vendorBank"})
            public static class VendorBanks{
                @XmlElement
                private List<VendorBankEntity> vendorBank;

                public List<VendorBankEntity> getVendorBank() {
                    return vendorBank;
                }

                public void setVendorBank(List<VendorBankEntity> vendorBank) {
                    this.vendorBank = vendorBank;
                }
            }

            public VendorBankResponse.RESPONSE.ResponseInfo.VendorBanks getVendorBanks() {
                return vendorBanks;
            }
            public void setVendorBanks(VendorBankResponse.RESPONSE.ResponseInfo.VendorBanks vendorBanks) {
                this.vendorBanks = vendorBanks;
            }

        }

    }
}
