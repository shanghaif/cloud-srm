package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  （隆基）银行分行接口表请求类（Esb总线）
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/14 12:39
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "BranchBankRequest")
public class BranchBankRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected BranchBankRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "branchBanks"
    })

    public static class RequestInfo {
        @XmlElement(required = true)
        protected BranchBankRequest.RequestInfo.BranchBanks branchBanks;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "branchBank"
        })
        public static class BranchBanks {
            @XmlElement(required = true)
            protected List<BranchBankEntity> branchBank;

            public List<BranchBankEntity> getBranchBank() {
                return branchBank;
            }
            public void setBranchBank(List<BranchBankEntity> branchBank) {
                this.branchBank = branchBank;
            }
        }

        public BranchBankRequest.RequestInfo.BranchBanks getBranchBanks() {
            return branchBanks;
        }

        public void setBranchBanks(BranchBankRequest.RequestInfo.BranchBanks branchBanks) {
            this.branchBanks = branchBanks;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public BranchBankRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(BranchBankRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
