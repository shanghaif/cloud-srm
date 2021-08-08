package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.midea.cloud.srm.model.logistics.soap.tms.request.RequestEsbInfo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  <pre>
 *  erp 单位 webService 请求类
 * </pre>
 *
 * @author xiexh12@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-22 15:30:43
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "unitOfMeasuresRequest")
@XmlType(name="", propOrder = {
        "esbInfo","requestInfo"
})
public class UnitOfMeasureRequest {

    @XmlElement(required = true)
    protected RequestEsbInfo esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "unitOfMeasures"
    })

    public static class RequestInfo{

        @XmlElement
        private UnitOfMeasures unitOfMeasures;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"unitOfMeasure"})
        public static class UnitOfMeasures{
            @XmlElement
            private List<UnitOfMeasureEntity> unitOfMeasure;

            public List<UnitOfMeasureEntity> getUnitOfMeasure() {
                return unitOfMeasure;
            }
            public void setUnitOfMeasure(List<UnitOfMeasureEntity> unitOfMeasure) {
                this.unitOfMeasure = unitOfMeasure;
            }
        }

        public UnitOfMeasures getUnitOfMeasures() {
            return unitOfMeasures;
        }
        public void setUnitOfMeasures(UnitOfMeasures unitOfMeasures) {
            this.unitOfMeasures = unitOfMeasures;
        }
    }

    public RequestEsbInfo getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(RequestEsbInfo esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
