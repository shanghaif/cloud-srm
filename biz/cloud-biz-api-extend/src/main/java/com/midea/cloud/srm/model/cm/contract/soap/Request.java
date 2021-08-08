
package com.midea.cloud.srm.model.cm.contract.soap;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "esbInfo",
        "requestInfo"
})
@XmlRootElement(name = "request")
public class Request {

    @XmlElement(required = true)
    protected Request.EsbInfo esbInfo;
    @XmlElement(required = true)
    protected Request.RequestInfo requestInfo;

    /**
     * 获取esbInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link Request.EsbInfo }
     *
     */
    public Request.EsbInfo getEsbInfo() {
        return esbInfo;
    }

    /**
     * 设置esbInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Request.EsbInfo }
     *
     */
    public void setEsbInfo(Request.EsbInfo value) {
        this.esbInfo = value;
    }

    /**
     * 获取requestInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link Request.RequestInfo }
     *
     */
    public Request.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * 设置requestInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Request.RequestInfo }
     *
     */
    public void setRequestInfo(Request.RequestInfo value) {
        this.requestInfo = value;
    }



    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "instId",
            "requestTime",
            "attr1",
            "attr2",
            "attr3",
            "sysCode"
    })
    public static class EsbInfo {

        @XmlElement(required = true)
        protected String instId;
        @XmlElement(required = true)
        protected String requestTime;
        @XmlElement(required = true)
        protected String attr1;
        @XmlElement(required = true)
        protected String attr2;
        @XmlElement(required = true)
        protected String attr3;
        @XmlElement(name = "SysCode", required = true)
        protected String sysCode;

        /**
         * 获取instId属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getInstId() {
            return instId;
        }

        /**
         * 设置instId属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setInstId(String value) {
            this.instId = value;
        }

        /**
         * 获取requestTime属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestTime() {
            return requestTime;
        }

        /**
         * 设置requestTime属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestTime(String value) {
            this.requestTime = value;
        }

        /**
         * 获取attr1属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr1() {
            return attr1;
        }

        /**
         * 设置attr1属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr1(String value) {
            this.attr1 = value;
        }

        /**
         * 获取attr2属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr2() {
            return attr2;
        }

        /**
         * 设置attr2属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr2(String value) {
            this.attr2 = value;
        }

        /**
         * 获取attr3属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr3() {
            return attr3;
        }

        /**
         * 设置attr3属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr3(String value) {
            this.attr3 = value;
        }

        /**
         * 获取sysCode属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSysCode() {
            return sysCode;
        }

        /**
         * 设置sysCode属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSysCode(String value) {
            this.sysCode = value;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "cmsSiCtHeaders"
    })
    public static class RequestInfo {

        @XmlElement(required = true)
        protected Request.RequestInfo.CmsSiCtHeaders cmsSiCtHeaders;

        /**
         * 获取cmsSiCtHeaders属性的值。
         *
         * @return
         *     possible object is
         *     {@link Request.RequestInfo.CmsSiCtHeaders }
         *
         */
        public Request.RequestInfo.CmsSiCtHeaders getCmsSiCtHeaders() {
            return cmsSiCtHeaders;
        }

        /**
         * 设置cmsSiCtHeaders属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link Request.RequestInfo.CmsSiCtHeaders }
         *
         */
        public void setCmsSiCtHeaders(Request.RequestInfo.CmsSiCtHeaders value) {
            this.cmsSiCtHeaders = value;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "items",
                "cmsSiSubjectMatters",
                "cmsSiCtItems",
                "cmsSiCtMergeCts",
                "cmsPeRtnpayItems",
                "cmsSiProjectTasks"
        })
        public static class CmsSiCtHeaders {

            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.Items items;
            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters cmsSiSubjectMatters;
            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.CmsSiCtItems cmsSiCtItems;
            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.CmsSiCtMergeCts cmsSiCtMergeCts;
            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems cmsPeRtnpayItems;
            @XmlElement(required = true)
            protected Request.RequestInfo.CmsSiCtHeaders.CmsSiProjectTasks cmsSiProjectTasks;

            /**
             * 获取items属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.Items }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.Items getItems() {
                return items;
            }

            /**
             * 设置items属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.Items }
             *
             */
            public void setItems(Request.RequestInfo.CmsSiCtHeaders.Items value) {
                this.items = value;
            }

            /**
             * 获取cmsSiSubjectMatters属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters getCmsSiSubjectMatters() {
                return cmsSiSubjectMatters;
            }

            /**
             * 设置cmsSiSubjectMatters属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters }
             *
             */
            public void setCmsSiSubjectMatters(Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters value) {
                this.cmsSiSubjectMatters = value;
            }

            /**
             * 获取cmsSiCtItems属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiCtItems }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.CmsSiCtItems getCmsSiCtItems() {
                return cmsSiCtItems;
            }

            /**
             * 设置cmsSiCtItems属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiCtItems }
             *
             */
            public void setCmsSiCtItems(Request.RequestInfo.CmsSiCtHeaders.CmsSiCtItems value) {
                this.cmsSiCtItems = value;
            }

            /**
             * 获取cmsSiCtMergeCts属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiCtMergeCts }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.CmsSiCtMergeCts getCmsSiCtMergeCts() {
                return cmsSiCtMergeCts;
            }

            /**
             * 设置cmsSiCtMergeCts属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiCtMergeCts }
             *
             */
            public void setCmsSiCtMergeCts(Request.RequestInfo.CmsSiCtHeaders.CmsSiCtMergeCts value) {
                this.cmsSiCtMergeCts = value;
            }

            /**
             * 获取cmsPeRtnpayItems属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems getCmsPeRtnpayItems() {
                return cmsPeRtnpayItems;
            }

            /**
             * 设置cmsPeRtnpayItems属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems }
             *
             */
            public void setCmsPeRtnpayItems(Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems value) {
                this.cmsPeRtnpayItems = value;
            }

            /**
             * 获取cmsSiProjectTasks属性的值。
             *
             * @return
             *     possible object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiProjectTasks }
             *
             */
            public Request.RequestInfo.CmsSiCtHeaders.CmsSiProjectTasks getCmsSiProjectTasks() {
                return cmsSiProjectTasks;
            }

            /**
             * 设置cmsSiProjectTasks属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiProjectTasks }
             *
             */
            public void setCmsSiProjectTasks(Request.RequestInfo.CmsSiCtHeaders.CmsSiProjectTasks value) {
                this.cmsSiProjectTasks = value;
            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "cmsPeRtnpayItem"
            })
            public static class CmsPeRtnpayItems {

                protected List<CmsPeRtnpayItem> cmsPeRtnpayItem;

                public List<CmsPeRtnpayItem> getCmsPeRtnpayItem() {
                    if (cmsPeRtnpayItem == null) {
                        cmsPeRtnpayItem = new ArrayList<CmsPeRtnpayItem>();
                    }
                    return this.cmsPeRtnpayItem;
                }

                public void setCmsPeRtnpayItem(List<CmsPeRtnpayItem> cmsPeRtnpayItem){
                    this.cmsPeRtnpayItem = cmsPeRtnpayItem;
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "originalcurrencytotalamount",
                        "rtnpayitemid",
                        "rtnpayphasecode",
                        "rtnpayphasename",
                        "planrtnpayrate",
                        "planrtnpaydate",
                        "planrtnpayamount",
                        "orderno",
                        "memo"
                })
                public static class CmsPeRtnpayItem {

                    @XmlElement(name = "ORIGINALCURRENCYTOTALAMOUNT")
                    protected BigDecimal originalcurrencytotalamount;
                    @XmlElement(name = "RTNPAYITEMID")
                    protected String rtnpayitemid;
                    @XmlElement(name = "RTNPAYPHASECODE")
                    protected String rtnpayphasecode;
                    @XmlElement(name = "RTNPAYPHASENAME")
                    protected String rtnpayphasename;
                    @XmlElement(name = "PLANRTNPAYRATE")
                    protected BigDecimal planrtnpayrate;
                    @XmlElement(name = "PLANRTNPAYDATE")
                    protected String planrtnpaydate;
                    @XmlElement(name = "PLANRTNPAYAMOUNT")
                    protected String planrtnpayamount;
                    @XmlElement(name = "ORDERNO")
                    protected String orderno;
                    @XmlElement(name = "MEMO")
                    protected String memo;

                    /**
                     * 获取originalcurrencytotalamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getORIGINALCURRENCYTOTALAMOUNT() {
                        return originalcurrencytotalamount;
                    }

                    /**
                     * 设置originalcurrencytotalamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setORIGINALCURRENCYTOTALAMOUNT(BigDecimal value) {
                        this.originalcurrencytotalamount = value;
                    }

                    /**
                     * 获取rtnpayitemid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRTNPAYITEMID() {
                        return rtnpayitemid;
                    }

                    /**
                     * 设置rtnpayitemid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRTNPAYITEMID(String value) {
                        this.rtnpayitemid = value;
                    }

                    /**
                     * 获取rtnpayphasecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRTNPAYPHASECODE() {
                        return rtnpayphasecode;
                    }

                    /**
                     * 设置rtnpayphasecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRTNPAYPHASECODE(String value) {
                        this.rtnpayphasecode = value;
                    }

                    /**
                     * 获取rtnpayphasename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRTNPAYPHASENAME() {
                        return rtnpayphasename;
                    }

                    /**
                     * 设置rtnpayphasename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRTNPAYPHASENAME(String value) {
                        this.rtnpayphasename = value;
                    }

                    /**
                     * 获取planrtnpayrate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getPLANRTNPAYRATE() {
                        return planrtnpayrate;
                    }

                    /**
                     * 设置planrtnpayrate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setPLANRTNPAYRATE(BigDecimal value) {
                        this.planrtnpayrate = value;
                    }

                    /**
                     * 获取planrtnpaydate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPLANRTNPAYDATE() {
                        return planrtnpaydate;
                    }

                    /**
                     * 设置planrtnpaydate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPLANRTNPAYDATE(String value) {
                        this.planrtnpaydate = value;
                    }

                    /**
                     * 获取planrtnpayamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPLANRTNPAYAMOUNT() {
                        return planrtnpayamount;
                    }

                    /**
                     * 设置planrtnpayamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPLANRTNPAYAMOUNT(String value) {
                        this.planrtnpayamount = value;
                    }

                    /**
                     * 获取orderno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORDERNO() {
                        return orderno;
                    }

                    /**
                     * 设置orderno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORDERNO(String value) {
                        this.orderno = value;
                    }

                    /**
                     * 获取memo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getMEMO() {
                        return memo;
                    }

                    /**
                     * 设置memo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setMEMO(String value) {
                        this.memo = value;
                    }

                }

            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "cmsSiCtItem"
            })
            public static class CmsSiCtItems {

                protected List<CmsSiCtItem> cmsSiCtItem;

                public List<CmsSiCtItem> getCmsSiCtItem() {
                    if (cmsSiCtItem == null) {
                        cmsSiCtItem = new ArrayList<CmsSiCtItem>();
                    }
                    return this.cmsSiCtItem;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "ctitemid",
                        "ctitemtype",
                        "ctitemcode",
                        "ctitemname",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5"
                })
                public static class CmsSiCtItem {

                    @XmlElement(name = "CTITEMID")
                    protected String ctitemid;
                    @XmlElement(name = "CTITEMTYPE")
                    protected String ctitemtype;
                    @XmlElement(name = "CTITEMCODE")
                    protected String ctitemcode;
                    @XmlElement(name = "CTITEMNAME")
                    protected String ctitemname;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;

                    /**
                     * 获取ctitemid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTITEMID() {
                        return ctitemid;
                    }

                    /**
                     * 设置ctitemid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTITEMID(String value) {
                        this.ctitemid = value;
                    }

                    /**
                     * 获取ctitemtype属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTITEMTYPE() {
                        return ctitemtype;
                    }

                    /**
                     * 设置ctitemtype属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTITEMTYPE(String value) {
                        this.ctitemtype = value;
                    }

                    /**
                     * 获取ctitemcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTITEMCODE() {
                        return ctitemcode;
                    }

                    /**
                     * 设置ctitemcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTITEMCODE(String value) {
                        this.ctitemcode = value;
                    }

                    /**
                     * 获取ctitemname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTITEMNAME() {
                        return ctitemname;
                    }

                    /**
                     * 设置ctitemname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTITEMNAME(String value) {
                        this.ctitemname = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "cmsSiCtMergeCt"
            })
            public static class CmsSiCtMergeCts {

                protected List<CmsSiCtMergeCt> cmsSiCtMergeCt;

                public List<CmsSiCtMergeCt> getCmsSiCtMergeCt() {
                    if (cmsSiCtMergeCt == null) {
                        cmsSiCtMergeCt = new ArrayList<CmsSiCtMergeCt>();
                    }
                    return this.cmsSiCtMergeCt;
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "ctcode",
                        "ctname",
                        "ctversionno",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5"
                })
                public static class CmsSiCtMergeCt {

                    @XmlElement(name = "CTCODE")
                    protected String ctcode;
                    @XmlElement(name = "CTNAME")
                    protected String ctname;
                    @XmlElement(name = "CTVERSIONNO")
                    protected String ctversionno;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;

                    /**
                     * 获取ctcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTCODE() {
                        return ctcode;
                    }

                    /**
                     * 设置ctcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTCODE(String value) {
                        this.ctcode = value;
                    }

                    /**
                     * 获取ctname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTNAME() {
                        return ctname;
                    }

                    /**
                     * 设置ctname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTNAME(String value) {
                        this.ctname = value;
                    }

                    /**
                     * 获取ctversionno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTVERSIONNO() {
                        return ctversionno;
                    }

                    /**
                     * 设置ctversionno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTVERSIONNO(String value) {
                        this.ctversionno = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                }

            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "cmsSiProjectTask"
            })
            public static class CmsSiProjectTasks {

                protected List<CmsSiProjectTask> cmsSiProjectTask;

                public List<CmsSiProjectTask> getCmsSiProjectTask() {
                    if (cmsSiProjectTask == null) {
                        cmsSiProjectTask = new ArrayList<CmsSiProjectTask>();
                    }
                    return this.cmsSiProjectTask;
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "taskid",
                        "tasknumber",
                        "taskname",
                        "taskamount",
                        "taskrate",
                        "tasktaxamount",
                        "tasknotaxamount",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5",
                        "buffer6",
                        "buffer7",
                        "buffer8"
                })
                public static class CmsSiProjectTask {

                    @XmlElement(name = "TASKID")
                    protected BigDecimal taskid;
                    @XmlElement(name = "TASKNUMBER")
                    protected String tasknumber;
                    @XmlElement(name = "TASKNAME")
                    protected String taskname;
                    @XmlElement(name = "TASKAMOUNT")
                    protected BigDecimal taskamount;
                    @XmlElement(name = "TASKRATE")
                    protected BigDecimal taskrate;
                    @XmlElement(name = "TASKTAXAMOUNT")
                    protected BigDecimal tasktaxamount;
                    @XmlElement(name = "TASKNOTAXAMOUNT")
                    protected BigDecimal tasknotaxamount;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;
                    @XmlElement(name = "BUFFER6")
                    protected String buffer6;
                    @XmlElement(name = "BUFFER7")
                    protected String buffer7;
                    @XmlElement(name = "BUFFER8")
                    protected String buffer8;

                    /**
                     * 获取taskid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTASKID() {
                        return taskid;
                    }

                    /**
                     * 设置taskid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTASKID(BigDecimal value) {
                        this.taskid = value;
                    }

                    /**
                     * 获取tasknumber属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getTASKNUMBER() {
                        return tasknumber;
                    }

                    /**
                     * 设置tasknumber属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setTASKNUMBER(String value) {
                        this.tasknumber = value;
                    }

                    /**
                     * 获取taskname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getTASKNAME() {
                        return taskname;
                    }

                    /**
                     * 设置taskname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setTASKNAME(String value) {
                        this.taskname = value;
                    }

                    /**
                     * 获取taskamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTASKAMOUNT() {
                        return taskamount;
                    }

                    /**
                     * 设置taskamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTASKAMOUNT(BigDecimal value) {
                        this.taskamount = value;
                    }

                    /**
                     * 获取taskrate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTASKRATE() {
                        return taskrate;
                    }

                    /**
                     * 设置taskrate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTASKRATE(BigDecimal value) {
                        this.taskrate = value;
                    }

                    /**
                     * 获取tasktaxamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTASKTAXAMOUNT() {
                        return tasktaxamount;
                    }

                    /**
                     * 设置tasktaxamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTASKTAXAMOUNT(BigDecimal value) {
                        this.tasktaxamount = value;
                    }

                    /**
                     * 获取tasknotaxamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTASKNOTAXAMOUNT() {
                        return tasknotaxamount;
                    }

                    /**
                     * 设置tasknotaxamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTASKNOTAXAMOUNT(BigDecimal value) {
                        this.tasknotaxamount = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                    /**
                     * 获取buffer6属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER6() {
                        return buffer6;
                    }

                    /**
                     * 设置buffer6属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER6(String value) {
                        this.buffer6 = value;
                    }

                    /**
                     * 获取buffer7属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER7() {
                        return buffer7;
                    }

                    /**
                     * 设置buffer7属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER7(String value) {
                        this.buffer7 = value;
                    }

                    /**
                     * 获取buffer8属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER8() {
                        return buffer8;
                    }

                    /**
                     * 设置buffer8属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER8(String value) {
                        this.buffer8 = value;
                    }

                }

            }


            /**
             * <p>anonymous complex type的 Java 类。
             *
             * <p>以下模式片段指定包含在此类中的预期内容。
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="cmsSiSubjectMatter" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="SUBJECTMATTERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SUBJECTMATTERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SUBJECTMATTERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SPECIFICATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="TAXUNITPRICE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="QTY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="TAXRATE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="TAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="NOTAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="MEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ITEMIDENTIFIER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LINENUM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ITEMPERPW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="ORDPRODTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="GIFTVOLUMEWA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="PROJECTCONTEXTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SALETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PRODUCTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="FREEW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="PROJECTCONTEXTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SALETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PRODUCTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="UNITNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OEDPRODTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PCW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ACCEPTINVENTORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="DEGRADEDPRODUCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="QUALITYSTANDARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORDERQUALIFICATIONRATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LINEID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "cmsSiSubjectMatter"
            })
            public static class CmsSiSubjectMatters {

                protected List<CmsSiSubjectMatter> cmsSiSubjectMatter;

                /**
                 * Gets the value of the cmsSiSubjectMatter property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the cmsSiSubjectMatter property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCmsSiSubjectMatter().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters.CmsSiSubjectMatter }
                 *
                 *
                 */
                public List<CmsSiSubjectMatter> getCmsSiSubjectMatter() {
                    if (cmsSiSubjectMatter == null) {
                        cmsSiSubjectMatter = new ArrayList<CmsSiSubjectMatter>();
                    }
                    return this.cmsSiSubjectMatter;
                }

                public void setCmsSiSubjectMatter(List<CmsSiSubjectMatter> cmsSiSubjectMatter){
                    this.cmsSiSubjectMatter = cmsSiSubjectMatter;
                }

                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="SUBJECTMATTERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SUBJECTMATTERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SUBJECTMATTERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SPECIFICATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="UNIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="TAXUNITPRICE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="QTY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="TAXRATE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="TAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="NOTAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="MEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ITEMIDENTIFIER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LINENUM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ITEMPERPW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="ORDPRODTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="GIFTVOLUMEWA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="PROJECTCONTEXTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SALETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PRODUCTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="FREEW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="PROJECTCONTEXTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SALETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PRODUCTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="UNITNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OEDPRODTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PCW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ACCEPTINVENTORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="DEGRADEDPRODUCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="QUALITYSTANDARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORDERQUALIFICATIONRATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LINEID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "subjectmatterid",
                        "subjectmattercode",
                        "subjectmattername",
                        "specification",
                        "unit",
                        "taxunitprice",
                        "qty",
                        "taxrate",
                        "taxamount",
                        "notaxamount",
                        "memo",
                        "itemidentifier",
                        "linenum",
                        "itemperpw",
                        "ordprodtype",
                        "giftvolumewa",
                        "projectcontextcode",
                        "saletypecode",
                        "producttypecode",
                        "freew",
                        "projectcontextname",
                        "saletypename",
                        "producttypename",
                        "unitname",
                        "oedprodtype",
                        "pcw",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5",
                        "acceptinventory",
                        "degradedproduct",
                        "qualitystandard",
                        "orderqualificationrate",
                        "lineid",
                        "buffer6",
                        "buffer7",
                        "buffer8",
                        "buffer9",
                        "buffer10",
                        "buffer11",
                        "buffer12",
                        "buffer13",
                        "buffer14",
                        "buffer15",
                        "buffer16",
                        "buffer17",
                        "buffer18",
                        "buffer19",
                        "buffer20"
                })
                public static class CmsSiSubjectMatter {

                    @XmlElement(name = "SUBJECTMATTERID")
                    protected String subjectmatterid;
                    @XmlElement(name = "SUBJECTMATTERCODE")
                    protected String subjectmattercode;
                    @XmlElement(name = "SUBJECTMATTERNAME")
                    protected String subjectmattername;
                    @XmlElement(name = "SPECIFICATION")
                    protected String specification;
                    @XmlElement(name = "UNIT")
                    protected String unit;
                    @XmlElement(name = "TAXUNITPRICE")
                    protected BigDecimal taxunitprice;
                    @XmlElement(name = "QTY")
                    protected BigDecimal qty;
                    @XmlElement(name = "TAXRATE")
                    protected BigDecimal taxrate;
                    @XmlElement(name = "TAXAMOUNT")
                    protected BigDecimal taxamount;
                    @XmlElement(name = "NOTAXAMOUNT")
                    protected BigDecimal notaxamount;
                    @XmlElement(name = "MEMO")
                    protected String memo;
                    @XmlElement(name = "ITEMIDENTIFIER")
                    protected String itemidentifier;
                    @XmlElement(name = "LINENUM")
                    protected String linenum;
                    @XmlElement(name = "ITEMPERPW")
                    protected BigDecimal itemperpw;
                    @XmlElement(name = "ORDPRODTYPE")
                    protected String ordprodtype;
                    @XmlElement(name = "GIFTVOLUMEWA")
                    protected BigDecimal giftvolumewa;
                    @XmlElement(name = "PROJECTCONTEXTCODE")
                    protected String projectcontextcode;
                    @XmlElement(name = "SALETYPECODE")
                    protected String saletypecode;
                    @XmlElement(name = "PRODUCTTYPECODE")
                    protected String producttypecode;
                    @XmlElement(name = "FREEW")
                    protected BigDecimal freew;
                    @XmlElement(name = "PROJECTCONTEXTNAME")
                    protected String projectcontextname;
                    @XmlElement(name = "SALETYPENAME")
                    protected String saletypename;
                    @XmlElement(name = "PRODUCTTYPENAME")
                    protected String producttypename;
                    @XmlElement(name = "UNITNAME")
                    protected String unitname;
                    @XmlElement(name = "OEDPRODTYPE")
                    protected String oedprodtype;
                    @XmlElement(name = "PCW")
                    protected BigDecimal pcw;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;
                    @XmlElement(name = "ACCEPTINVENTORY")
                    protected String acceptinventory;
                    @XmlElement(name = "DEGRADEDPRODUCT")
                    protected String degradedproduct;
                    @XmlElement(name = "QUALITYSTANDARD")
                    protected String qualitystandard;
                    @XmlElement(name = "ORDERQUALIFICATIONRATE")
                    protected String orderqualificationrate;
                    @XmlElement(name = "LINEID")
                    protected String lineid;
                    @XmlElement(name = "BUFFER6")
                    protected String buffer6;
                    @XmlElement(name = "BUFFER7")
                    protected String buffer7;
                    @XmlElement(name = "BUFFER8")
                    protected String buffer8;
                    @XmlElement(name = "BUFFER9")
                    protected String buffer9;
                    @XmlElement(name = "BUFFER10")
                    protected String buffer10;
                    @XmlElement(name = "BUFFER11")
                    protected String buffer11;
                    @XmlElement(name = "BUFFER12")
                    protected String buffer12;
                    @XmlElement(name = "BUFFER13")
                    protected String buffer13;
                    @XmlElement(name = "BUFFER14")
                    protected String buffer14;
                    @XmlElement(name = "BUFFER15")
                    protected String buffer15;
                    @XmlElement(name = "BUFFER16")
                    protected String buffer16;
                    @XmlElement(name = "BUFFER17")
                    protected String buffer17;
                    @XmlElement(name = "BUFFER18")
                    protected String buffer18;
                    @XmlElement(name = "BUFFER19")
                    protected String buffer19;
                    @XmlElement(name = "BUFFER20")
                    protected String buffer20;

                    /**
                     * 获取subjectmatterid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBJECTMATTERID() {
                        return subjectmatterid;
                    }

                    /**
                     * 设置subjectmatterid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBJECTMATTERID(String value) {
                        this.subjectmatterid = value;
                    }

                    /**
                     * 获取subjectmattercode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBJECTMATTERCODE() {
                        return subjectmattercode;
                    }

                    /**
                     * 设置subjectmattercode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBJECTMATTERCODE(String value) {
                        this.subjectmattercode = value;
                    }

                    /**
                     * 获取subjectmattername属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBJECTMATTERNAME() {
                        return subjectmattername;
                    }

                    /**
                     * 设置subjectmattername属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBJECTMATTERNAME(String value) {
                        this.subjectmattername = value;
                    }

                    /**
                     * 获取specification属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSPECIFICATION() {
                        return specification;
                    }

                    /**
                     * 设置specification属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSPECIFICATION(String value) {
                        this.specification = value;
                    }

                    /**
                     * 获取unit属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getUNIT() {
                        return unit;
                    }

                    /**
                     * 设置unit属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setUNIT(String value) {
                        this.unit = value;
                    }

                    /**
                     * 获取taxunitprice属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTAXUNITPRICE() {
                        return taxunitprice;
                    }

                    /**
                     * 设置taxunitprice属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTAXUNITPRICE(BigDecimal value) {
                        this.taxunitprice = value;
                    }

                    /**
                     * 获取qty属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getQTY() {
                        return qty;
                    }

                    /**
                     * 设置qty属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setQTY(BigDecimal value) {
                        this.qty = value;
                    }

                    /**
                     * 获取taxrate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTAXRATE() {
                        return taxrate;
                    }

                    /**
                     * 设置taxrate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTAXRATE(BigDecimal value) {
                        this.taxrate = value;
                    }

                    /**
                     * 获取taxamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getTAXAMOUNT() {
                        return taxamount;
                    }

                    /**
                     * 设置taxamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setTAXAMOUNT(BigDecimal value) {
                        this.taxamount = value;
                    }

                    /**
                     * 获取notaxamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getNOTAXAMOUNT() {
                        return notaxamount;
                    }

                    /**
                     * 设置notaxamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setNOTAXAMOUNT(BigDecimal value) {
                        this.notaxamount = value;
                    }

                    /**
                     * 获取memo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getMEMO() {
                        return memo;
                    }

                    /**
                     * 设置memo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setMEMO(String value) {
                        this.memo = value;
                    }

                    /**
                     * 获取itemidentifier属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getITEMIDENTIFIER() {
                        return itemidentifier;
                    }

                    /**
                     * 设置itemidentifier属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setITEMIDENTIFIER(String value) {
                        this.itemidentifier = value;
                    }

                    /**
                     * 获取linenum属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getLINENUM() {
                        return linenum;
                    }

                    /**
                     * 设置linenum属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setLINENUM(String value) {
                        this.linenum = value;
                    }

                    /**
                     * 获取itemperpw属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getITEMPERPW() {
                        return itemperpw;
                    }

                    /**
                     * 设置itemperpw属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setITEMPERPW(BigDecimal value) {
                        this.itemperpw = value;
                    }

                    /**
                     * 获取ordprodtype属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORDPRODTYPE() {
                        return ordprodtype;
                    }

                    /**
                     * 设置ordprodtype属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORDPRODTYPE(String value) {
                        this.ordprodtype = value;
                    }

                    /**
                     * 获取giftvolumewa属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getGIFTVOLUMEWA() {
                        return giftvolumewa;
                    }

                    /**
                     * 设置giftvolumewa属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setGIFTVOLUMEWA(BigDecimal value) {
                        this.giftvolumewa = value;
                    }

                    /**
                     * 获取projectcontextcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPROJECTCONTEXTCODE() {
                        return projectcontextcode;
                    }

                    /**
                     * 设置projectcontextcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPROJECTCONTEXTCODE(String value) {
                        this.projectcontextcode = value;
                    }

                    /**
                     * 获取saletypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSALETYPECODE() {
                        return saletypecode;
                    }

                    /**
                     * 设置saletypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSALETYPECODE(String value) {
                        this.saletypecode = value;
                    }

                    /**
                     * 获取producttypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPRODUCTTYPECODE() {
                        return producttypecode;
                    }

                    /**
                     * 设置producttypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPRODUCTTYPECODE(String value) {
                        this.producttypecode = value;
                    }

                    /**
                     * 获取freew属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getFREEW() {
                        return freew;
                    }

                    /**
                     * 设置freew属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setFREEW(BigDecimal value) {
                        this.freew = value;
                    }

                    /**
                     * 获取projectcontextname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPROJECTCONTEXTNAME() {
                        return projectcontextname;
                    }

                    /**
                     * 设置projectcontextname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPROJECTCONTEXTNAME(String value) {
                        this.projectcontextname = value;
                    }

                    /**
                     * 获取saletypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSALETYPENAME() {
                        return saletypename;
                    }

                    /**
                     * 设置saletypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSALETYPENAME(String value) {
                        this.saletypename = value;
                    }

                    /**
                     * 获取producttypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getPRODUCTTYPENAME() {
                        return producttypename;
                    }

                    /**
                     * 设置producttypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setPRODUCTTYPENAME(String value) {
                        this.producttypename = value;
                    }

                    /**
                     * 获取unitname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getUNITNAME() {
                        return unitname;
                    }

                    /**
                     * 设置unitname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setUNITNAME(String value) {
                        this.unitname = value;
                    }

                    /**
                     * 获取oedprodtype属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getOEDPRODTYPE() {
                        return oedprodtype;
                    }

                    /**
                     * 设置oedprodtype属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setOEDPRODTYPE(String value) {
                        this.oedprodtype = value;
                    }

                    /**
                     * 获取pcw属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getPCW() {
                        return pcw;
                    }

                    /**
                     * 设置pcw属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setPCW(BigDecimal value) {
                        this.pcw = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                    /**
                     * 获取acceptinventory属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getACCEPTINVENTORY() {
                        return acceptinventory;
                    }

                    /**
                     * 设置acceptinventory属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setACCEPTINVENTORY(String value) {
                        this.acceptinventory = value;
                    }

                    /**
                     * 获取degradedproduct属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getDEGRADEDPRODUCT() {
                        return degradedproduct;
                    }

                    /**
                     * 设置degradedproduct属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setDEGRADEDPRODUCT(String value) {
                        this.degradedproduct = value;
                    }

                    /**
                     * 获取qualitystandard属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getQUALITYSTANDARD() {
                        return qualitystandard;
                    }

                    /**
                     * 设置qualitystandard属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setQUALITYSTANDARD(String value) {
                        this.qualitystandard = value;
                    }

                    /**
                     * 获取orderqualificationrate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORDERQUALIFICATIONRATE() {
                        return orderqualificationrate;
                    }

                    /**
                     * 设置orderqualificationrate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORDERQUALIFICATIONRATE(String value) {
                        this.orderqualificationrate = value;
                    }

                    /**
                     * 获取lineid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getLINEID() {
                        return lineid;
                    }

                    /**
                     * 设置lineid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setLINEID(String value) {
                        this.lineid = value;
                    }

                    /**
                     * 获取buffer6属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER6() {
                        return buffer6;
                    }

                    /**
                     * 设置buffer6属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER6(String value) {
                        this.buffer6 = value;
                    }

                    /**
                     * 获取buffer7属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER7() {
                        return buffer7;
                    }

                    /**
                     * 设置buffer7属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER7(String value) {
                        this.buffer7 = value;
                    }

                    /**
                     * 获取buffer8属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER8() {
                        return buffer8;
                    }

                    /**
                     * 设置buffer8属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER8(String value) {
                        this.buffer8 = value;
                    }

                    /**
                     * 获取buffer9属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER9() {
                        return buffer9;
                    }

                    /**
                     * 设置buffer9属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER9(String value) {
                        this.buffer9 = value;
                    }

                    /**
                     * 获取buffer10属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER10() {
                        return buffer10;
                    }

                    /**
                     * 设置buffer10属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER10(String value) {
                        this.buffer10 = value;
                    }

                    /**
                     * 获取buffer11属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER11() {
                        return buffer11;
                    }

                    /**
                     * 设置buffer11属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER11(String value) {
                        this.buffer11 = value;
                    }

                    /**
                     * 获取buffer12属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER12() {
                        return buffer12;
                    }

                    /**
                     * 设置buffer12属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER12(String value) {
                        this.buffer12 = value;
                    }

                    /**
                     * 获取buffer13属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER13() {
                        return buffer13;
                    }

                    /**
                     * 设置buffer13属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER13(String value) {
                        this.buffer13 = value;
                    }

                    /**
                     * 获取buffer14属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER14() {
                        return buffer14;
                    }

                    /**
                     * 设置buffer14属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER14(String value) {
                        this.buffer14 = value;
                    }

                    /**
                     * 获取buffer15属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER15() {
                        return buffer15;
                    }

                    /**
                     * 设置buffer15属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER15(String value) {
                        this.buffer15 = value;
                    }

                    /**
                     * 获取buffer16属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER16() {
                        return buffer16;
                    }

                    /**
                     * 设置buffer16属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER16(String value) {
                        this.buffer16 = value;
                    }

                    /**
                     * 获取buffer17属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER17() {
                        return buffer17;
                    }

                    /**
                     * 设置buffer17属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER17(String value) {
                        this.buffer17 = value;
                    }

                    /**
                     * 获取buffer18属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER18() {
                        return buffer18;
                    }

                    /**
                     * 设置buffer18属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER18(String value) {
                        this.buffer18 = value;
                    }

                    /**
                     * 获取buffer19属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER19() {
                        return buffer19;
                    }

                    /**
                     * 设置buffer19属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER19(String value) {
                        this.buffer19 = value;
                    }

                    /**
                     * 获取buffer20属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER20() {
                        return buffer20;
                    }

                    /**
                     * 设置buffer20属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER20(String value) {
                        this.buffer20 = value;
                    }

                }

            }


            /**
             * <p>anonymous complex type的 Java 类。
             *
             * <p>以下模式片段指定包含在此类中的预期内容。
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="item00" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CTHEADERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTVERSIONNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTGROUPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="UPDATEBY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="item04" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CTEXCLUSIVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="item05" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CTTPL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="item01" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="BELONGORGCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BELONGORGNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SHORTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SHORTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LARGETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LARGETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CONTRACTTPLCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CONTRACTTPLNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ISSTANDARDCONTRACT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="item02" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="SUBMITUSERNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SUBMITUSERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SUBMITUSERTEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SUBMITDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="MERGESUBMITCODE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="BELONGDEPTCODE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="BELONGDEPTNAME" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="BUSINESSNEGOTIATORNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUSINESSNEGOTIATORNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="EMERGENCYDEGREECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="EMERGENCYDEGREENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="item03" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CONTRACTURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTGRADECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTGRADENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNPLACE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNCOMPANYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNCOMPANYNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNOTHERTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNOTHERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SIGNOTHERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="INCOMEEXPENDTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="INCOMEEXPENDTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ISFRAMEWORKAGREEMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="RELFRAMEWORKAGREEMENTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ISRELATIONDEAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUSINESSAREACODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUSINESSAREANAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ACCOUNTPERIOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="STARTDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ENDDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SEALMODECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SEALMODENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ADVANCERECEIVEPAYMENTAMOUT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="RECEIVEPAYMENTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="RECEIVEPAYMENTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OTHERSIGNINGMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORIGINALCURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORIGINALCURRENCYTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORIGINALCURRENCYTOTALUAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="TAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="NOTAXTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="STANDARDCURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="EXCHANGERATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="STANDARDCURRENCYTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OTHERSIDESEALDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="APPROVEDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OURSIDESEALDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="EFFECTDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CUSTOMERCTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CLOSETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CLOSETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CLOSEDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CLOSEMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGEOLDCTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGEOLDCTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGEOLDCTVERSIONNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CHANGEMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="MEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUFFER9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CTSEALSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "item00",
                    "item04",
                    "item05",
                    "item01",
                    "item02",
                    "item03"
            })
            @Data
            public static class Items {

                protected List<Item00> item00;
                protected List<Item04> item04;
                protected List<Item05> item05;
                protected List<Item01> item01;
                protected List<Item02> item02;
                protected List<Item03> item03;

                /**
                 * Gets the value of the item00 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item00 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem00().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item00 }
                 *
                 *
                 */
                public List<Item00> getItem00() {
                    if (item00 == null) {
                        item00 = new ArrayList<Item00>();
                    }
                    return this.item00;
                }

                /**
                 * Gets the value of the item04 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item04 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem04().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item04 }
                 *
                 *
                 */
                public List<Item04> getItem04() {
                    if (item04 == null) {
                        item04 = new ArrayList<Item04>();
                    }
                    return this.item04;
                }

                /**
                 * Gets the value of the item05 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item05 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem05().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item05 }
                 *
                 *
                 */
                public List<Item05> getItem05() {
                    if (item05 == null) {
                        item05 = new ArrayList<Item05>();
                    }
                    return this.item05;
                }

                /**
                 * Gets the value of the item01 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item01 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem01().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item01 }
                 *
                 *
                 */
                public List<Item01> getItem01() {
                    if (item01 == null) {
                        item01 = new ArrayList<Item01>();
                    }
                    return this.item01;
                }

                /**
                 * Gets the value of the item02 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item02 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem02().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item02 }
                 *
                 *
                 */
                public List<Item02> getItem02() {
                    if (item02 == null) {
                        item02 = new ArrayList<Item02>();
                    }
                    return this.item02;
                }

                /**
                 * Gets the value of the item03 property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item03 property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem03().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Request.RequestInfo.CmsSiCtHeaders.Items.Item03 }
                 *
                 *
                 */
                public List<Item03> getItem03() {
                    if (item03 == null) {
                        item03 = new ArrayList<Item03>();
                    }
                    return this.item03;
                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CTHEADERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTVERSIONNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTGROUPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="UPDATEBY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "ctheaderid",
                        "ctcode",
                        "ctname",
                        "ctversionno",
                        "ctstatus",
                        "ctgroupid",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5",
                        "buffer6",
                        "updateby"
                })
                public static class Item00 {

                    @XmlElement(name = "CTHEADERID")
                    protected String ctheaderid;
                    @XmlElement(name = "CTCODE")
                    protected String ctcode;
                    @XmlElement(name = "CTNAME")
                    protected String ctname;
                    @XmlElement(name = "CTVERSIONNO")
                    protected String ctversionno;
                    @XmlElement(name = "CTSTATUS")
                    protected String ctstatus;
                    @XmlElement(name = "CTGROUPID")
                    protected String ctgroupid;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;
                    @XmlElement(name = "BUFFER6")
                    protected String buffer6;
                    @XmlElement(name = "UPDATEBY")
                    protected String updateby;

                    /**
                     * 获取ctheaderid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTHEADERID() {
                        return ctheaderid;
                    }

                    /**
                     * 设置ctheaderid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTHEADERID(String value) {
                        this.ctheaderid = value;
                    }

                    /**
                     * 获取ctcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTCODE() {
                        return ctcode;
                    }

                    /**
                     * 设置ctcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTCODE(String value) {
                        this.ctcode = value;
                    }

                    /**
                     * 获取ctname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTNAME() {
                        return ctname;
                    }

                    /**
                     * 设置ctname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTNAME(String value) {
                        this.ctname = value;
                    }

                    /**
                     * 获取ctversionno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTVERSIONNO() {
                        return ctversionno;
                    }

                    /**
                     * 设置ctversionno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTVERSIONNO(String value) {
                        this.ctversionno = value;
                    }

                    /**
                     * 获取ctstatus属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTSTATUS() {
                        return ctstatus;
                    }

                    /**
                     * 设置ctstatus属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTSTATUS(String value) {
                        this.ctstatus = value;
                    }

                    /**
                     * 获取ctgroupid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTGROUPID() {
                        return ctgroupid;
                    }

                    /**
                     * 设置ctgroupid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTGROUPID(String value) {
                        this.ctgroupid = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                    /**
                     * 获取buffer6属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER6() {
                        return buffer6;
                    }

                    /**
                     * 设置buffer6属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER6(String value) {
                        this.buffer6 = value;
                    }

                    /**
                     * 获取updateby属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getUPDATEBY() {
                        return updateby;
                    }

                    /**
                     * 设置updateby属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setUPDATEBY(String value) {
                        this.updateby = value;
                    }

                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="BELONGORGCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BELONGORGNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SHORTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SHORTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LARGETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LARGETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CONTRACTTPLCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CONTRACTTPLNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ISSTANDARDCONTRACT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "belongorgcode",
                        "belongorgname",
                        "shorttypecode",
                        "shorttypename",
                        "largetypecode",
                        "largetypename",
                        "contracttplcode",
                        "contracttplname",
                        "isstandardcontract",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5"
                })
                public static class Item01 {

                    @XmlElement(name = "BELONGORGCODE")
                    protected String belongorgcode;
                    @XmlElement(name = "BELONGORGNAME")
                    protected String belongorgname;
                    @XmlElement(name = "SHORTTYPECODE")
                    protected String shorttypecode;
                    @XmlElement(name = "SHORTTYPENAME")
                    protected String shorttypename;
                    @XmlElement(name = "LARGETYPECODE")
                    protected String largetypecode;
                    @XmlElement(name = "LARGETYPENAME")
                    protected String largetypename;
                    @XmlElement(name = "CONTRACTTPLCODE")
                    protected String contracttplcode;
                    @XmlElement(name = "CONTRACTTPLNAME")
                    protected String contracttplname;
                    @XmlElement(name = "ISSTANDARDCONTRACT")
                    protected String isstandardcontract;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;

                    /**
                     * 获取belongorgcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBELONGORGCODE() {
                        return belongorgcode;
                    }

                    /**
                     * 设置belongorgcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBELONGORGCODE(String value) {
                        this.belongorgcode = value;
                    }

                    /**
                     * 获取belongorgname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBELONGORGNAME() {
                        return belongorgname;
                    }

                    /**
                     * 设置belongorgname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBELONGORGNAME(String value) {
                        this.belongorgname = value;
                    }

                    /**
                     * 获取shorttypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSHORTTYPECODE() {
                        return shorttypecode;
                    }

                    /**
                     * 设置shorttypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSHORTTYPECODE(String value) {
                        this.shorttypecode = value;
                    }

                    /**
                     * 获取shorttypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSHORTTYPENAME() {
                        return shorttypename;
                    }

                    /**
                     * 设置shorttypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSHORTTYPENAME(String value) {
                        this.shorttypename = value;
                    }

                    /**
                     * 获取largetypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getLARGETYPECODE() {
                        return largetypecode;
                    }

                    /**
                     * 设置largetypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setLARGETYPECODE(String value) {
                        this.largetypecode = value;
                    }

                    /**
                     * 获取largetypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getLARGETYPENAME() {
                        return largetypename;
                    }

                    /**
                     * 设置largetypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setLARGETYPENAME(String value) {
                        this.largetypename = value;
                    }

                    /**
                     * 获取contracttplcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCONTRACTTPLCODE() {
                        return contracttplcode;
                    }

                    /**
                     * 设置contracttplcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCONTRACTTPLCODE(String value) {
                        this.contracttplcode = value;
                    }

                    /**
                     * 获取contracttplname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCONTRACTTPLNAME() {
                        return contracttplname;
                    }

                    /**
                     * 设置contracttplname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCONTRACTTPLNAME(String value) {
                        this.contracttplname = value;
                    }

                    /**
                     * 获取isstandardcontract属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getISSTANDARDCONTRACT() {
                        return isstandardcontract;
                    }

                    /**
                     * 设置isstandardcontract属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setISSTANDARDCONTRACT(String value) {
                        this.isstandardcontract = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="SUBMITUSERNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SUBMITUSERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SUBMITUSERTEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SUBMITDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="MERGESUBMITCODE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="BELONGDEPTCODE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="BELONGDEPTNAME" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="BUSINESSNEGOTIATORNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUSINESSNEGOTIATORNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="EMERGENCYDEGREECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="EMERGENCYDEGREENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "submituserno",
                        "submitusername",
                        "submitusertel",
                        "submitdate",
                        "mergesubmitcode",
                        "belongdeptcode",
                        "belongdeptname",
                        "businessnegotiatorno",
                        "businessnegotiatorname",
                        "emergencydegreecode",
                        "emergencydegreename",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5"
                })
                public static class Item02 {

                    @XmlElement(name = "SUBMITUSERNO")
                    protected String submituserno;
                    @XmlElement(name = "SUBMITUSERNAME")
                    protected String submitusername;
                    @XmlElement(name = "SUBMITUSERTEL")
                    protected String submitusertel;
                    @XmlElement(name = "SUBMITDATE")
                    protected String submitdate;
                    @XmlElement(name = "MERGESUBMITCODE")
                    protected BigDecimal mergesubmitcode;
                    @XmlElement(name = "BELONGDEPTCODE")
                    protected BigDecimal belongdeptcode;
                    @XmlElement(name = "BELONGDEPTNAME")
                    protected BigDecimal belongdeptname;
                    @XmlElement(name = "BUSINESSNEGOTIATORNO")
                    protected String businessnegotiatorno;
                    @XmlElement(name = "BUSINESSNEGOTIATORNAME")
                    protected String businessnegotiatorname;
                    @XmlElement(name = "EMERGENCYDEGREECODE")
                    protected String emergencydegreecode;
                    @XmlElement(name = "EMERGENCYDEGREENAME")
                    protected String emergencydegreename;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;

                    /**
                     * 获取submituserno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBMITUSERNO() {
                        return submituserno;
                    }

                    /**
                     * 设置submituserno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBMITUSERNO(String value) {
                        this.submituserno = value;
                    }

                    /**
                     * 获取submitusername属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBMITUSERNAME() {
                        return submitusername;
                    }

                    /**
                     * 设置submitusername属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBMITUSERNAME(String value) {
                        this.submitusername = value;
                    }

                    /**
                     * 获取submitusertel属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBMITUSERTEL() {
                        return submitusertel;
                    }

                    /**
                     * 设置submitusertel属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBMITUSERTEL(String value) {
                        this.submitusertel = value;
                    }

                    /**
                     * 获取submitdate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSUBMITDATE() {
                        return submitdate;
                    }

                    /**
                     * 设置submitdate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSUBMITDATE(String value) {
                        this.submitdate = value;
                    }

                    /**
                     * 获取mergesubmitcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getMERGESUBMITCODE() {
                        return mergesubmitcode;
                    }

                    /**
                     * 设置mergesubmitcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setMERGESUBMITCODE(BigDecimal value) {
                        this.mergesubmitcode = value;
                    }

                    /**
                     * 获取belongdeptcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getBELONGDEPTCODE() {
                        return belongdeptcode;
                    }

                    /**
                     * 设置belongdeptcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setBELONGDEPTCODE(BigDecimal value) {
                        this.belongdeptcode = value;
                    }

                    /**
                     * 获取belongdeptname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *
                     */
                    public BigDecimal getBELONGDEPTNAME() {
                        return belongdeptname;
                    }

                    /**
                     * 设置belongdeptname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *
                     */
                    public void setBELONGDEPTNAME(BigDecimal value) {
                        this.belongdeptname = value;
                    }

                    /**
                     * 获取businessnegotiatorno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUSINESSNEGOTIATORNO() {
                        return businessnegotiatorno;
                    }

                    /**
                     * 设置businessnegotiatorno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUSINESSNEGOTIATORNO(String value) {
                        this.businessnegotiatorno = value;
                    }

                    /**
                     * 获取businessnegotiatorname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUSINESSNEGOTIATORNAME() {
                        return businessnegotiatorname;
                    }

                    /**
                     * 设置businessnegotiatorname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUSINESSNEGOTIATORNAME(String value) {
                        this.businessnegotiatorname = value;
                    }

                    /**
                     * 获取emergencydegreecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getEMERGENCYDEGREECODE() {
                        return emergencydegreecode;
                    }

                    /**
                     * 设置emergencydegreecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setEMERGENCYDEGREECODE(String value) {
                        this.emergencydegreecode = value;
                    }

                    /**
                     * 获取emergencydegreename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getEMERGENCYDEGREENAME() {
                        return emergencydegreename;
                    }

                    /**
                     * 设置emergencydegreename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setEMERGENCYDEGREENAME(String value) {
                        this.emergencydegreename = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CONTRACTURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTGRADECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTGRADENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNPLACE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNCOMPANYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNCOMPANYNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNOTHERTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNOTHERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SIGNOTHERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="INCOMEEXPENDTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="INCOMEEXPENDTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ISFRAMEWORKAGREEMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="RELFRAMEWORKAGREEMENTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ISRELATIONDEAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUSINESSAREACODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUSINESSAREANAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ACCOUNTPERIOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="STARTDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ENDDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SEALMODECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SEALMODENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ADVANCERECEIVEPAYMENTAMOUT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="RECEIVEPAYMENTTYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="RECEIVEPAYMENTTYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OTHERSIGNINGMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORIGINALCURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORIGINALCURRENCYTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORIGINALCURRENCYTOTALUAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="TAXAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="NOTAXTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="STANDARDCURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="EXCHANGERATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="STANDARDCURRENCYTOTALAMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OTHERSIDESEALDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="APPROVEDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OURSIDESEALDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="EFFECTDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CUSTOMERCTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CLOSETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CLOSETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CLOSEDATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CLOSEMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGEOLDCTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGEOLDCTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGEOLDCTVERSIONNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGETYPECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGETYPENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CHANGEMEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="MEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUFFER9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CTSEALSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "contracturl",
                        "ctgradecode",
                        "ctgradename",
                        "signplace",
                        "signcompanycode",
                        "signcompanyname",
                        "signothertype",
                        "signothercode",
                        "signothername",
                        "incomeexpendtypecode",
                        "incomeexpendtypename",
                        "isframeworkagreement",
                        "relframeworkagreementcode",
                        "isrelationdeal",
                        "businessareacode",
                        "businessareaname",
                        "accountperiod",
                        "startdate",
                        "enddate",
                        "sealmodecode",
                        "sealmodename",
                        "advancereceivepaymentamout",
                        "receivepaymenttypecode",
                        "receivepaymenttypename",
                        "othersigningmemo",
                        "originalcurrency",
                        "originalcurrencytotalamount",
                        "originalcurrencytotaluamount",
                        "taxamount",
                        "notaxtotalamount",
                        "standardcurrency",
                        "exchangerate",
                        "standardcurrencytotalamount",
                        "othersidesealdate",
                        "approvedate",
                        "oursidesealdate",
                        "effectdate",
                        "customerctcode",
                        "closetypecode",
                        "closetypename",
                        "closedate",
                        "closememo",
                        "changeoldctcode",
                        "changeoldctname",
                        "changeoldctversionno",
                        "changetypecode",
                        "changetypename",
                        "changememo",
                        "memo",
                        "buffer1",
                        "buffer2",
                        "buffer3",
                        "buffer4",
                        "buffer5",
                        "buffer7",
                        "buffer8",
                        "buffer9",
                        "ouid",
                        "ctsealstatus"
                })
                public static class Item03 {

                    @XmlElement(name = "CONTRACTURL")
                    protected String contracturl;
                    @XmlElement(name = "CTGRADECODE")
                    protected String ctgradecode;
                    @XmlElement(name = "CTGRADENAME")
                    protected String ctgradename;
                    @XmlElement(name = "SIGNPLACE")
                    protected String signplace;
                    @XmlElement(name = "SIGNCOMPANYCODE")
                    protected String signcompanycode;
                    @XmlElement(name = "SIGNCOMPANYNAME")
                    protected String signcompanyname;
                    @XmlElement(name = "SIGNOTHERTYPE")
                    protected String signothertype;
                    @XmlElement(name = "SIGNOTHERCODE")
                    protected String signothercode;
                    @XmlElement(name = "SIGNOTHERNAME")
                    protected String signothername;
                    @XmlElement(name = "INCOMEEXPENDTYPECODE")
                    protected String incomeexpendtypecode;
                    @XmlElement(name = "INCOMEEXPENDTYPENAME")
                    protected String incomeexpendtypename;
                    @XmlElement(name = "ISFRAMEWORKAGREEMENT")
                    protected String isframeworkagreement;
                    @XmlElement(name = "RELFRAMEWORKAGREEMENTCODE")
                    protected String relframeworkagreementcode;
                    @XmlElement(name = "ISRELATIONDEAL")
                    protected String isrelationdeal;
                    @XmlElement(name = "BUSINESSAREACODE")
                    protected String businessareacode;
                    @XmlElement(name = "BUSINESSAREANAME")
                    protected String businessareaname;
                    @XmlElement(name = "ACCOUNTPERIOD")
                    protected String accountperiod;
                    @XmlElement(name = "STARTDATE")
                    protected String startdate;
                    @XmlElement(name = "ENDDATE")
                    protected String enddate;
                    @XmlElement(name = "SEALMODECODE")
                    protected String sealmodecode;
                    @XmlElement(name = "SEALMODENAME")
                    protected String sealmodename;
                    @XmlElement(name = "ADVANCERECEIVEPAYMENTAMOUT")
                    protected String advancereceivepaymentamout;
                    @XmlElement(name = "RECEIVEPAYMENTTYPECODE")
                    protected String receivepaymenttypecode;
                    @XmlElement(name = "RECEIVEPAYMENTTYPENAME")
                    protected String receivepaymenttypename;
                    @XmlElement(name = "OTHERSIGNINGMEMO")
                    protected String othersigningmemo;
                    @XmlElement(name = "ORIGINALCURRENCY")
                    protected String originalcurrency;
                    @XmlElement(name = "ORIGINALCURRENCYTOTALAMOUNT")
                    protected String originalcurrencytotalamount;
                    @XmlElement(name = "ORIGINALCURRENCYTOTALUAMOUNT")
                    protected String originalcurrencytotaluamount;
                    @XmlElement(name = "TAXAMOUNT")
                    protected String taxamount;
                    @XmlElement(name = "NOTAXTOTALAMOUNT")
                    protected String notaxtotalamount;
                    @XmlElement(name = "STANDARDCURRENCY")
                    protected String standardcurrency;
                    @XmlElement(name = "EXCHANGERATE")
                    protected String exchangerate;
                    @XmlElement(name = "STANDARDCURRENCYTOTALAMOUNT")
                    protected String standardcurrencytotalamount;
                    @XmlElement(name = "OTHERSIDESEALDATE")
                    protected String othersidesealdate;
                    @XmlElement(name = "APPROVEDATE")
                    protected String approvedate;
                    @XmlElement(name = "OURSIDESEALDATE")
                    protected String oursidesealdate;
                    @XmlElement(name = "EFFECTDATE")
                    protected String effectdate;
                    @XmlElement(name = "CUSTOMERCTCODE")
                    protected String customerctcode;
                    @XmlElement(name = "CLOSETYPECODE")
                    protected String closetypecode;
                    @XmlElement(name = "CLOSETYPENAME")
                    protected String closetypename;
                    @XmlElement(name = "CLOSEDATE")
                    protected String closedate;
                    @XmlElement(name = "CLOSEMEMO")
                    protected String closememo;
                    @XmlElement(name = "CHANGEOLDCTCODE")
                    protected String changeoldctcode;
                    @XmlElement(name = "CHANGEOLDCTNAME")
                    protected String changeoldctname;
                    @XmlElement(name = "CHANGEOLDCTVERSIONNO")
                    protected String changeoldctversionno;
                    @XmlElement(name = "CHANGETYPECODE")
                    protected String changetypecode;
                    @XmlElement(name = "CHANGETYPENAME")
                    protected String changetypename;
                    @XmlElement(name = "CHANGEMEMO")
                    protected String changememo;
                    @XmlElement(name = "MEMO")
                    protected String memo;
                    @XmlElement(name = "BUFFER1")
                    protected String buffer1;
                    @XmlElement(name = "BUFFER2")
                    protected String buffer2;
                    @XmlElement(name = "BUFFER3")
                    protected String buffer3;
                    @XmlElement(name = "BUFFER4")
                    protected String buffer4;
                    @XmlElement(name = "BUFFER5")
                    protected String buffer5;
                    @XmlElement(name = "BUFFER7")
                    protected String buffer7;
                    @XmlElement(name = "BUFFER8")
                    protected String buffer8;
                    @XmlElement(name = "BUFFER9")
                    protected String buffer9;
                    @XmlElement(name = "OUID")
                    protected String ouid;
                    @XmlElement(name = "CTSEALSTATUS")
                    protected String ctsealstatus;

                    /**
                     * 获取contracturl属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCONTRACTURL() {
                        return contracturl;
                    }

                    /**
                     * 设置contracturl属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCONTRACTURL(String value) {
                        this.contracturl = value;
                    }

                    /**
                     * 获取ctgradecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTGRADECODE() {
                        return ctgradecode;
                    }

                    /**
                     * 设置ctgradecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTGRADECODE(String value) {
                        this.ctgradecode = value;
                    }

                    /**
                     * 获取ctgradename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTGRADENAME() {
                        return ctgradename;
                    }

                    /**
                     * 设置ctgradename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTGRADENAME(String value) {
                        this.ctgradename = value;
                    }

                    /**
                     * 获取signplace属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNPLACE() {
                        return signplace;
                    }

                    /**
                     * 设置signplace属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNPLACE(String value) {
                        this.signplace = value;
                    }

                    /**
                     * 获取signcompanycode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNCOMPANYCODE() {
                        return signcompanycode;
                    }

                    /**
                     * 设置signcompanycode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNCOMPANYCODE(String value) {
                        this.signcompanycode = value;
                    }

                    /**
                     * 获取signcompanyname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNCOMPANYNAME() {
                        return signcompanyname;
                    }

                    /**
                     * 设置signcompanyname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNCOMPANYNAME(String value) {
                        this.signcompanyname = value;
                    }

                    /**
                     * 获取signothertype属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNOTHERTYPE() {
                        return signothertype;
                    }

                    /**
                     * 设置signothertype属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNOTHERTYPE(String value) {
                        this.signothertype = value;
                    }

                    /**
                     * 获取signothercode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNOTHERCODE() {
                        return signothercode;
                    }

                    /**
                     * 设置signothercode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNOTHERCODE(String value) {
                        this.signothercode = value;
                    }

                    /**
                     * 获取signothername属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSIGNOTHERNAME() {
                        return signothername;
                    }

                    /**
                     * 设置signothername属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSIGNOTHERNAME(String value) {
                        this.signothername = value;
                    }

                    /**
                     * 获取incomeexpendtypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getINCOMEEXPENDTYPECODE() {
                        return incomeexpendtypecode;
                    }

                    /**
                     * 设置incomeexpendtypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setINCOMEEXPENDTYPECODE(String value) {
                        this.incomeexpendtypecode = value;
                    }

                    /**
                     * 获取incomeexpendtypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getINCOMEEXPENDTYPENAME() {
                        return incomeexpendtypename;
                    }

                    /**
                     * 设置incomeexpendtypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setINCOMEEXPENDTYPENAME(String value) {
                        this.incomeexpendtypename = value;
                    }

                    /**
                     * 获取isframeworkagreement属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getISFRAMEWORKAGREEMENT() {
                        return isframeworkagreement;
                    }

                    /**
                     * 设置isframeworkagreement属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setISFRAMEWORKAGREEMENT(String value) {
                        this.isframeworkagreement = value;
                    }

                    /**
                     * 获取relframeworkagreementcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRELFRAMEWORKAGREEMENTCODE() {
                        return relframeworkagreementcode;
                    }

                    /**
                     * 设置relframeworkagreementcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRELFRAMEWORKAGREEMENTCODE(String value) {
                        this.relframeworkagreementcode = value;
                    }

                    /**
                     * 获取isrelationdeal属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getISRELATIONDEAL() {
                        return isrelationdeal;
                    }

                    /**
                     * 设置isrelationdeal属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setISRELATIONDEAL(String value) {
                        this.isrelationdeal = value;
                    }

                    /**
                     * 获取businessareacode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUSINESSAREACODE() {
                        return businessareacode;
                    }

                    /**
                     * 设置businessareacode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUSINESSAREACODE(String value) {
                        this.businessareacode = value;
                    }

                    /**
                     * 获取businessareaname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUSINESSAREANAME() {
                        return businessareaname;
                    }

                    /**
                     * 设置businessareaname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUSINESSAREANAME(String value) {
                        this.businessareaname = value;
                    }

                    /**
                     * 获取accountperiod属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getACCOUNTPERIOD() {
                        return accountperiod;
                    }

                    /**
                     * 设置accountperiod属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setACCOUNTPERIOD(String value) {
                        this.accountperiod = value;
                    }

                    /**
                     * 获取startdate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSTARTDATE() {
                        return startdate;
                    }

                    /**
                     * 设置startdate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSTARTDATE(String value) {
                        this.startdate = value;
                    }

                    /**
                     * 获取enddate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getENDDATE() {
                        return enddate;
                    }

                    /**
                     * 设置enddate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setENDDATE(String value) {
                        this.enddate = value;
                    }

                    /**
                     * 获取sealmodecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSEALMODECODE() {
                        return sealmodecode;
                    }

                    /**
                     * 设置sealmodecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSEALMODECODE(String value) {
                        this.sealmodecode = value;
                    }

                    /**
                     * 获取sealmodename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSEALMODENAME() {
                        return sealmodename;
                    }

                    /**
                     * 设置sealmodename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSEALMODENAME(String value) {
                        this.sealmodename = value;
                    }

                    /**
                     * 获取advancereceivepaymentamout属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getADVANCERECEIVEPAYMENTAMOUT() {
                        return advancereceivepaymentamout;
                    }

                    /**
                     * 设置advancereceivepaymentamout属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setADVANCERECEIVEPAYMENTAMOUT(String value) {
                        this.advancereceivepaymentamout = value;
                    }

                    /**
                     * 获取receivepaymenttypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRECEIVEPAYMENTTYPECODE() {
                        return receivepaymenttypecode;
                    }

                    /**
                     * 设置receivepaymenttypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRECEIVEPAYMENTTYPECODE(String value) {
                        this.receivepaymenttypecode = value;
                    }

                    /**
                     * 获取receivepaymenttypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getRECEIVEPAYMENTTYPENAME() {
                        return receivepaymenttypename;
                    }

                    /**
                     * 设置receivepaymenttypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setRECEIVEPAYMENTTYPENAME(String value) {
                        this.receivepaymenttypename = value;
                    }

                    /**
                     * 获取othersigningmemo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getOTHERSIGNINGMEMO() {
                        return othersigningmemo;
                    }

                    /**
                     * 设置othersigningmemo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setOTHERSIGNINGMEMO(String value) {
                        this.othersigningmemo = value;
                    }

                    /**
                     * 获取originalcurrency属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORIGINALCURRENCY() {
                        return originalcurrency;
                    }

                    /**
                     * 设置originalcurrency属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORIGINALCURRENCY(String value) {
                        this.originalcurrency = value;
                    }

                    /**
                     * 获取originalcurrencytotalamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORIGINALCURRENCYTOTALAMOUNT() {
                        return originalcurrencytotalamount;
                    }

                    /**
                     * 设置originalcurrencytotalamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORIGINALCURRENCYTOTALAMOUNT(String value) {
                        this.originalcurrencytotalamount = value;
                    }

                    /**
                     * 获取originalcurrencytotaluamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getORIGINALCURRENCYTOTALUAMOUNT() {
                        return originalcurrencytotaluamount;
                    }

                    /**
                     * 设置originalcurrencytotaluamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setORIGINALCURRENCYTOTALUAMOUNT(String value) {
                        this.originalcurrencytotaluamount = value;
                    }

                    /**
                     * 获取taxamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getTAXAMOUNT() {
                        return taxamount;
                    }

                    /**
                     * 设置taxamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setTAXAMOUNT(String value) {
                        this.taxamount = value;
                    }

                    /**
                     * 获取notaxtotalamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getNOTAXTOTALAMOUNT() {
                        return notaxtotalamount;
                    }

                    /**
                     * 设置notaxtotalamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setNOTAXTOTALAMOUNT(String value) {
                        this.notaxtotalamount = value;
                    }

                    /**
                     * 获取standardcurrency属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSTANDARDCURRENCY() {
                        return standardcurrency;
                    }

                    /**
                     * 设置standardcurrency属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSTANDARDCURRENCY(String value) {
                        this.standardcurrency = value;
                    }

                    /**
                     * 获取exchangerate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getEXCHANGERATE() {
                        return exchangerate;
                    }

                    /**
                     * 设置exchangerate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setEXCHANGERATE(String value) {
                        this.exchangerate = value;
                    }

                    /**
                     * 获取standardcurrencytotalamount属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getSTANDARDCURRENCYTOTALAMOUNT() {
                        return standardcurrencytotalamount;
                    }

                    /**
                     * 设置standardcurrencytotalamount属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setSTANDARDCURRENCYTOTALAMOUNT(String value) {
                        this.standardcurrencytotalamount = value;
                    }

                    /**
                     * 获取othersidesealdate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getOTHERSIDESEALDATE() {
                        return othersidesealdate;
                    }

                    /**
                     * 设置othersidesealdate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setOTHERSIDESEALDATE(String value) {
                        this.othersidesealdate = value;
                    }

                    /**
                     * 获取approvedate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getAPPROVEDATE() {
                        return approvedate;
                    }

                    /**
                     * 设置approvedate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setAPPROVEDATE(String value) {
                        this.approvedate = value;
                    }

                    /**
                     * 获取oursidesealdate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getOURSIDESEALDATE() {
                        return oursidesealdate;
                    }

                    /**
                     * 设置oursidesealdate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setOURSIDESEALDATE(String value) {
                        this.oursidesealdate = value;
                    }

                    /**
                     * 获取effectdate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getEFFECTDATE() {
                        return effectdate;
                    }

                    /**
                     * 设置effectdate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setEFFECTDATE(String value) {
                        this.effectdate = value;
                    }

                    /**
                     * 获取customerctcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCUSTOMERCTCODE() {
                        return customerctcode;
                    }

                    /**
                     * 设置customerctcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCUSTOMERCTCODE(String value) {
                        this.customerctcode = value;
                    }

                    /**
                     * 获取closetypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCLOSETYPECODE() {
                        return closetypecode;
                    }

                    /**
                     * 设置closetypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCLOSETYPECODE(String value) {
                        this.closetypecode = value;
                    }

                    /**
                     * 获取closetypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCLOSETYPENAME() {
                        return closetypename;
                    }

                    /**
                     * 设置closetypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCLOSETYPENAME(String value) {
                        this.closetypename = value;
                    }

                    /**
                     * 获取closedate属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCLOSEDATE() {
                        return closedate;
                    }

                    /**
                     * 设置closedate属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCLOSEDATE(String value) {
                        this.closedate = value;
                    }

                    /**
                     * 获取closememo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCLOSEMEMO() {
                        return closememo;
                    }

                    /**
                     * 设置closememo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCLOSEMEMO(String value) {
                        this.closememo = value;
                    }

                    /**
                     * 获取changeoldctcode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGEOLDCTCODE() {
                        return changeoldctcode;
                    }

                    /**
                     * 设置changeoldctcode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGEOLDCTCODE(String value) {
                        this.changeoldctcode = value;
                    }

                    /**
                     * 获取changeoldctname属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGEOLDCTNAME() {
                        return changeoldctname;
                    }

                    /**
                     * 设置changeoldctname属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGEOLDCTNAME(String value) {
                        this.changeoldctname = value;
                    }

                    /**
                     * 获取changeoldctversionno属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGEOLDCTVERSIONNO() {
                        return changeoldctversionno;
                    }

                    /**
                     * 设置changeoldctversionno属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGEOLDCTVERSIONNO(String value) {
                        this.changeoldctversionno = value;
                    }

                    /**
                     * 获取changetypecode属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGETYPECODE() {
                        return changetypecode;
                    }

                    /**
                     * 设置changetypecode属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGETYPECODE(String value) {
                        this.changetypecode = value;
                    }

                    /**
                     * 获取changetypename属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGETYPENAME() {
                        return changetypename;
                    }

                    /**
                     * 设置changetypename属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGETYPENAME(String value) {
                        this.changetypename = value;
                    }

                    /**
                     * 获取changememo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCHANGEMEMO() {
                        return changememo;
                    }

                    /**
                     * 设置changememo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCHANGEMEMO(String value) {
                        this.changememo = value;
                    }

                    /**
                     * 获取memo属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getMEMO() {
                        return memo;
                    }

                    /**
                     * 设置memo属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setMEMO(String value) {
                        this.memo = value;
                    }

                    /**
                     * 获取buffer1属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER1() {
                        return buffer1;
                    }

                    /**
                     * 设置buffer1属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER1(String value) {
                        this.buffer1 = value;
                    }

                    /**
                     * 获取buffer2属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER2() {
                        return buffer2;
                    }

                    /**
                     * 设置buffer2属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER2(String value) {
                        this.buffer2 = value;
                    }

                    /**
                     * 获取buffer3属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER3() {
                        return buffer3;
                    }

                    /**
                     * 设置buffer3属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER3(String value) {
                        this.buffer3 = value;
                    }

                    /**
                     * 获取buffer4属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER4() {
                        return buffer4;
                    }

                    /**
                     * 设置buffer4属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER4(String value) {
                        this.buffer4 = value;
                    }

                    /**
                     * 获取buffer5属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER5() {
                        return buffer5;
                    }

                    /**
                     * 设置buffer5属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER5(String value) {
                        this.buffer5 = value;
                    }

                    /**
                     * 获取buffer7属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER7() {
                        return buffer7;
                    }

                    /**
                     * 设置buffer7属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER7(String value) {
                        this.buffer7 = value;
                    }

                    /**
                     * 获取buffer8属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER8() {
                        return buffer8;
                    }

                    /**
                     * 设置buffer8属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER8(String value) {
                        this.buffer8 = value;
                    }

                    /**
                     * 获取buffer9属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getBUFFER9() {
                        return buffer9;
                    }

                    /**
                     * 设置buffer9属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setBUFFER9(String value) {
                        this.buffer9 = value;
                    }

                    /**
                     * 获取ouid属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getOUID() {
                        return ouid;
                    }

                    /**
                     * 设置ouid属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setOUID(String value) {
                        this.ouid = value;
                    }

                    /**
                     * 获取ctsealstatus属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTSEALSTATUS() {
                        return ctsealstatus;
                    }

                    /**
                     * 设置ctsealstatus属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTSEALSTATUS(String value) {
                        this.ctsealstatus = value;
                    }

                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CTEXCLUSIVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "ctexclusive"
                })
                public static class Item04 {

                    @XmlElement(name = "CTEXCLUSIVE")
                    protected String ctexclusive;

                    /**
                     * 获取ctexclusive属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTEXCLUSIVE() {
                        return ctexclusive;
                    }

                    /**
                     * 设置ctexclusive属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTEXCLUSIVE(String value) {
                        this.ctexclusive = value;
                    }

                }


                /**
                 * <p>anonymous complex type的 Java 类。
                 *
                 * <p>以下模式片段指定包含在此类中的预期内容。
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CTTPL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "cttpl"
                })
                public static class Item05 {

                    @XmlElement(name = "CTTPL")
                    protected String cttpl;

                    /**
                     * 获取cttpl属性的值。
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCTTPL() {
                        return cttpl;
                    }

                    /**
                     * 设置cttpl属性的值。
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCTTPL(String value) {
                        this.cttpl = value;
                    }

                }

            }

        }

    }

}

