package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  部门接口表最外层实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/15 16:54
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "deptRequest")
public class DeptRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected DeptRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "depts"
    })
    public static class RequestInfo {
//        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected DeptRequest.RequestInfo.Depts depts;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "dept"
        })
        public static class Depts {
            @XmlElement(required = true)
            protected List<DeptEntity> dept;

            public List<DeptEntity> getDept() {
                return dept;
            }
            public void setDept(List<DeptEntity> dept) {
                this.dept = dept;
            }
        }

        public DeptRequest.RequestInfo.Depts getDepts() {
            return depts;
        }

        public void setDepts(DeptRequest.RequestInfo.Depts depts) {
            this.depts = depts;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public DeptRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(DeptRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
