package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  物料接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 15:30
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "MaterialItemRequest")
public class MaterialItemRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected MaterialItemRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "materialItems"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected MaterialItemRequest.RequestInfo.MaterialItems materialItems;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "materialItem"
        })
        public static class MaterialItems {
            @XmlElement(required = true)
            protected List<MaterialItemEntity> materialItem;

            public List<MaterialItemEntity> getMaterialItem() {
                return materialItem;
            }
            public void setMaterialItem(List<MaterialItemEntity> materialItem) {
                this.materialItem = materialItem;
            }
        }

        public MaterialItemRequest.RequestInfo.MaterialItems getMaterialItems() {
            return materialItems;
        }

        public void setMaterialItems(MaterialItemRequest.RequestInfo.MaterialItems materialItems) {
            this.materialItems = materialItems;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public MaterialItemRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(MaterialItemRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}