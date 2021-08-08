package com.midea.cloud.srm.model.base.soap.bpm.pr.dto;

import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.BudgetHeader;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/10 15:49
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo", "requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "BudgetForBpmRequest")
@Data
public class BudgetForBpmRequest {

    @XmlElement(name = "ebsInfo", required = true)
    protected EsbInfo esbInfo;
    @XmlElement(name = "requestInfo", required = true)
    protected RequestInfo requestInfo;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "instId",
            "requestTime"
    })
    @Data
    public static class EsbInfo {

        @XmlElement(name = "INST_ID", required = true)
        protected String instId;
        @XmlElement(name = "REQUEST_TIME", required = true)
        protected String requestTime;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "header",
            "context"
    })
    @Data
    public static class RequestInfo {

        @XmlElement(name = "HEADER", required = true)
        protected HEADER header;
        @XmlElement(name = "CONTEXT", required = true)
        protected CONTEXT context;


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "approve"
        })
        @Data
        public static class CONTEXT {
            @XmlElement(name = "APPROVE", required = true)
            protected APPROVE approve;


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "budgetHeader"
            })
            @Data
            public static class APPROVE {

                @XmlElement(name = "RECORD", required = true)
                protected BudgetHeader budgetHeader;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "businessgroup",
                "systemcode",
                "requestid",
                "ifcatecode",
                "ifcode",
                "username",
                "password",
                "batchnum",
                "totalsegcount",
                "segnum"
        })
        @Data
        public static class HEADER {
            @XmlElement(name = "BUSINESS_GROUP", required = true)
            protected String businessgroup;
            @XmlElement(name = "SYSTEM_CODE", required = true)
            protected String systemcode;
            @XmlElement(name = "REQUEST_ID", required = true)
            protected String requestid;
            @XmlElement(name = "IF_CATE_CODE", required = true)
            protected String ifcatecode;
            @XmlElement(name = "IF_CODE", required = true)
            protected String ifcode;
            @XmlElement(name = "USER_NAME", required = true)
            protected String username;
            @XmlElement(name = "PASSWORD", required = true)
            protected String password;
            @XmlElement(name = "BATCH_NUM", required = true)
            protected String batchnum;
            @XmlElement(name = "TOTAL_SEG_COUNT", required = true)
            protected String totalsegcount;
            @XmlElement(name = "SEG_NUM", required = true)
            protected String segnum;

        }
    }
}
