package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  职位接口表最外层实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/17 16:29
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "PositionRequest")
public class PositionRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected PositionRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "positions"
    })
    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected PositionRequest.RequestInfo.Positions positions;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "position"
        })
        public static class Positions {
            @XmlElement(required = true)
            protected List<PositionEntity> position;

            public List<PositionEntity> getPosition() {
                return position;
            }
            public void setPosition(List<PositionEntity> position) {
                this.position = position;
            }
        }

        public PositionRequest.RequestInfo.Positions getPositions() {
            return positions;
        }

        public void setPositions(PositionRequest.RequestInfo.Positions positions) {
            this.positions = positions;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public PositionRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PositionRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
