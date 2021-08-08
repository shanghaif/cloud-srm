package com.midea.cloud.srm.model.logistics.soap.tms.request;

import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineFee;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineShip;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/5 19:57
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "logisticsOrderRequest")
@XmlType(name="", propOrder = {
        "esbInfo","requestInfo"
})
public class TmsOrderRequest {
    @XmlElement(required = true)
    protected RequestEsbInfo esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "logisticsOrders"
    })
    public static class RequestInfo {
        @XmlElement
        private List<LogisticsOrder> logisticsOrders;


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"orderHead", "orderLine", "orderLineFee", "orderLineShip"})
        public static class LogisticsOrder {
            @XmlElement
            private TmsOrderHeadEntity orderHead;

            @XmlElement
            private List<TmsOrderLineEntity> orderLine;

            @XmlElement
            private List<TmsOrderLineFeeEntity> orderLineFee;

            @XmlElement
            private List<TmsOrderLineShipEntity> orderLineShip;

            public TmsOrderHeadEntity getOrderHead() {
                return orderHead;
            }

            public void setOrderHead(TmsOrderHeadEntity orderHead) {
                this.orderHead = orderHead;
            }

            public List<TmsOrderLineEntity> getOrderLine() {
                return orderLine;
            }

            public void setOrderLine(List<TmsOrderLineEntity> orderLine) {
                this.orderLine = orderLine;
            }

            public List<TmsOrderLineFeeEntity> getOrderLineFee() {
                return orderLineFee;
            }

            public void setOrderLineFee(List<TmsOrderLineFeeEntity> orderLineFee) {
                this.orderLineFee = orderLineFee;
            }

            public List<TmsOrderLineShipEntity> getOrderLineShip() {
                return orderLineShip;
            }

            public void setOrderLineShip(List<TmsOrderLineShipEntity> orderLineShip) {
                this.orderLineShip = orderLineShip;
            }
        }

        public List<LogisticsOrder> getLogisticsOrders() {
            return logisticsOrders;
        }

        public void setLogisticsOrders(List<LogisticsOrder> logisticsOrders) {
            this.logisticsOrders = logisticsOrders;
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
