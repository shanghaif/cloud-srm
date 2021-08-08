package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  供应商接收请求实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/09/27 16:26
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "VendorRequest")
public class VendorRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected VendorRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "vendors"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected VendorRequest.RequestInfo.Vendors vendors;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "vendor"
        })
        public static class Vendors {
            @XmlElement(required = true)
            protected List<VendorEntity> vendor;

            public List<VendorEntity> getVendor() {
                return vendor;
            }
            public void setVendor(List<VendorEntity> vendor) {
                this.vendor = vendor;
            }
        }

        public VendorRequest.RequestInfo.Vendors getVendors() {
            return vendors;
        }

        public void setVendors(VendorRequest.RequestInfo.Vendors vendors) {
            this.vendors = vendors;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public VendorRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(VendorRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}