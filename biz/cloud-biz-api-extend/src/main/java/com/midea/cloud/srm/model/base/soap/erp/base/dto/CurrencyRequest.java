package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  币种接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 14:37
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "CurrencyRequest")
public class CurrencyRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected CurrencyRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "currencys"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected CurrencyRequest.RequestInfo.Currencys currencys;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "currency"
        })
        public static class Currencys {
            @XmlElement(required = true)
            protected List<CurrencyEntity> currency;

            public List<CurrencyEntity> getCurrency() {
                return currency;
            }
            public void setCurrency(List<CurrencyEntity> currency) {
                this.currency = currency;
            }
        }

        public CurrencyRequest.RequestInfo.Currencys getCurrencys() {
            return currencys;
        }

        public void setCurrencys(CurrencyRequest.RequestInfo.Currencys currencys) {
            this.currencys = currencys;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public CurrencyRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(CurrencyRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
