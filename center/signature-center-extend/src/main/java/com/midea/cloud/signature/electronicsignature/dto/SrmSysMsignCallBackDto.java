package com.midea.cloud.signature.electronicsignature.dto;

import java.util.Date;
import java.util.List;

public class SrmSysMsignCallBackDto {
    private Long msignCallBackId; //主键ID
    private String receiptId; //外部单据ID
    private String flowId; //外部流程ID（可与外部单据相同）
    private String contractNumber; //合同编号
    private String serialNum; //回调序列号
    private String status; //回调处理状态
    private String createdBy; //创建人账号
    private Date creationDate; //创建时间
    private String lastUpdatedBy; //最后更新人账号
    private Date lastUpdateDate; //最后更新时间
    private String createdFullName; //创建人姓名
    private String lastUpdatedFullName; //最后更新人姓名
    private Long deleteFlag; //是否删除 0不删除 1删除
    private Long version; //版本号
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<SrmSysMsignCallBackConDto> contractList;

    public List<SrmSysMsignCallBackConDto> getContractList() {
        return contractList;
    }

    public void setContractList(List<SrmSysMsignCallBackConDto> contractList) {
        this.contractList = contractList;
    }

    public Long getMsignCallBackId() {
        return msignCallBackId;
    }

    public void setMsignCallBackId(Long msignCallBackId) {
        this.msignCallBackId = msignCallBackId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreatedFullName() {
        return createdFullName;
    }

    public void setCreatedFullName(String createdFullName) {
        this.createdFullName = createdFullName;
    }

    public String getLastUpdatedFullName() {
        return lastUpdatedFullName;
    }

    public void setLastUpdatedFullName(String lastUpdatedFullName) {
        this.lastUpdatedFullName = lastUpdatedFullName;
    }

    public Long getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Long deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAttributeCategory() {
        return attributeCategory;
    }

    public void setAttributeCategory(String attributeCategory) {
        this.attributeCategory = attributeCategory;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }
}
