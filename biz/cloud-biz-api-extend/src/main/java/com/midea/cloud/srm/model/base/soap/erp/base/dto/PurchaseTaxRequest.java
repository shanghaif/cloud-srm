package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  （隆基）税率接口表请求类（Esb总线）
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/13 17:44
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "PurchaseTaxRequest")
public class PurchaseTaxRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected PurchaseTaxRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "purchaseTaxs"
    })

    public static class RequestInfo {
        @XmlElement(required = true)
        protected PurchaseTaxRequest.RequestInfo.PurchaseTaxs purchaseTaxs;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "purchaseTax"
        })
        public static class PurchaseTaxs {
            @XmlElement(required = true)
            protected List<PurchaseTaxEntity> purchaseTax;

            public List<PurchaseTaxEntity> getPurchaseTax() {
                return purchaseTax;
            }
            public void setPurchaseTax(List<PurchaseTaxEntity> purchaseTax) {
                this.purchaseTax = purchaseTax;
            }
        }

        public PurchaseTaxRequest.RequestInfo.PurchaseTaxs getPurchaseTaxs() {
            return purchaseTaxs;
        }

        public void setPurchaseTaxs(PurchaseTaxRequest.RequestInfo.PurchaseTaxs purchaseTaxs) {
            this.purchaseTaxs = purchaseTaxs;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public PurchaseTaxRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PurchaseTaxRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
