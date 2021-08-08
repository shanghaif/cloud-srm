package com.midea.cloud.signature.electronicsignature.dto;

import java.util.Date;
import java.util.List;

public class SrmSysMsignAddContractDto {
    private Long msignAddContractId; //主键ID
    private Long msignAddId; //新增智慧签约合同头表ID
    private String url; //下载地址（可以提供统一的地址或者每次不一样的地址，需要提供）
    private String fileName; //合同文件名称
    private String fileNumber; //合同原件文件编号
    private String attchmentId; //固化后附件ID（可以同合同文件编号）
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

    List<SrmSysMsignAddConInfoDto> contractInfoList;

    public List<SrmSysMsignAddConInfoDto> getContractInfoList() {
        return contractInfoList;
    }

    public void setContractInfoList(List<SrmSysMsignAddConInfoDto> contractInfoList) {
        this.contractInfoList = contractInfoList;
    }

    public Long getMsignAddContractId() {
        return msignAddContractId;
    }

    public void setMsignAddContractId(Long msignAddContractId) {
        this.msignAddContractId = msignAddContractId;
    }

    public Long getMsignAddId() {
        return msignAddId;
    }

    public void setMsignAddId(Long msignAddId) {
        this.msignAddId = msignAddId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getAttchmentId() {
        return attchmentId;
    }

    public void setAttchmentId(String attchmentId) {
        this.attchmentId = attchmentId;
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
