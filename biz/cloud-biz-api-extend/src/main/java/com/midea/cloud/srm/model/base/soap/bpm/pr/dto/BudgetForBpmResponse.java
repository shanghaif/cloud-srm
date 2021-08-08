package com.midea.cloud.srm.model.base.soap.bpm.pr.dto;


import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "response"
})
@XmlRootElement(name = "BudgetForBpmResponse")
@Data
public class BudgetForBpmResponse {
    /**
     * 第一层
     */
    @XmlElement(name = "RESPONSE", required = true)
    protected RESPONSE response;

    /**
     * 第二层：ESBINFO
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "esbInfo",
            "resultInfo"
    })
    @Data
    public static class RESPONSE {
        /**
         * 第一层
         */
        @XmlElement(name = "ESBINFO", required = true)
        protected ESBINFO esbInfo;
        @XmlElement(name = "RESULTINFO", required = true)
        protected RESULTINFO resultInfo;
    }

    /**
     * 第三层：ESBINFO
     */
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
    @Data
    public static class ESBINFO {
        private String instId;
        private String returnStatus;
        private String returnCode;
        private String returnMsg;
        private String requestTime;
        private String responseTime;
        private String attr1;
        private String attr2;
        private String attr3;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "responseStatus",
            "responseMessage"
    })
    @Data
    public static class RESULTINFO {
        @XmlElement(name = "RESPONSE_STATUS")
        private String responseStatus;
        @XmlElement(name = "RESPONSE_MESSAGE")
        private String responseMessage;
    }

}
