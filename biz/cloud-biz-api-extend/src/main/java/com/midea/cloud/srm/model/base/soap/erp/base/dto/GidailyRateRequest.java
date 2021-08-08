package com.midea.cloud.srm.model.base.soap.erp.base.dto;


import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  汇率接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 11:12
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "GidailyRateRequest")
public class GidailyRateRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected GidailyRateRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "gidailyRates"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected GidailyRateRequest.RequestInfo.GidailyRates gidailyRates;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "gidailyRate"
        })
        public static class GidailyRates {
            @XmlElement(required = true)
            protected List<GidailyRateEntity> gidailyRate;

            public List<GidailyRateEntity> getGidailyRate() {
                return gidailyRate;
            }
            public void setGidailyRate(List<GidailyRateEntity> gidailyRate) {
                this.gidailyRate = gidailyRate;
            }
        }

        public GidailyRateRequest.RequestInfo.GidailyRates getGidailyRates() {
            return gidailyRates;
        }

        public void setGidailyRates(GidailyRateRequest.RequestInfo.GidailyRates gidailyRates) {
            this.gidailyRates = gidailyRates;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public GidailyRateRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(GidailyRateRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
