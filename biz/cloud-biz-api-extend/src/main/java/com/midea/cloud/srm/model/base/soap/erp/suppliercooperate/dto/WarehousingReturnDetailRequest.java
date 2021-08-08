package com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author @meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/21 11:50
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "warehousingReturnDetailRequest")
public class WarehousingReturnDetailRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "warehousingReturnDetails"
    })

    public static class RequestInfo {

        @XmlElement(required = true)
        protected WarehousingReturnDetails warehousingReturnDetails;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "warehousingReturnDetail"
        })
        public static class WarehousingReturnDetails {
            @XmlElement(required = true)
            protected List<WarehousingReturnDetailEntity> warehousingReturnDetail;

            public List<WarehousingReturnDetailEntity> getWarehousingReturnDetail() {
                return warehousingReturnDetail;
            }

            public void setWarehousingReturnDetail(List<WarehousingReturnDetailEntity> warehousing) {
                this.warehousingReturnDetail = warehousingReturnDetail;
            }
        }

        public WarehousingReturnDetails getWarehousingReturnDetails() {
            return warehousingReturnDetails;
        }

        public void setWarehousingReturnDetails(WarehousingReturnDetails warehousingReturnDetails) {
            this.warehousingReturnDetails = warehousingReturnDetails;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
