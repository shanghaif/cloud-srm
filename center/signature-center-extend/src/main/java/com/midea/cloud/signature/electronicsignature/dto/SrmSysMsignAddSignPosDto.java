package com.midea.cloud.signature.electronicsignature.dto;

import java.util.Date;

public class SrmSysMsignAddSignPosDto {
    private Long msignAddSignPosId; //主键ID
    private Long msignAddId; //新增智慧签约合同头表ID
    private Long msignAddContractId; //新增智慧签约合同-签署合同详细信息ID
    private Long msignAddConInfoId; //新增智慧签约合同-签署方详细信息ID
    private Double posX=0D; //签署位置X坐标，若为关键字定位，相对于关键字的X坐标偏移量，默认0
    private Double width; //印章展现宽度
    private Double posY=0D; //签署位置Y坐标，若为关键字定位，相对于关键字的Y坐标偏移量，默认0
    private String key; //关键字，仅限关键字签章时有效，若为关键字定位时，不可空
    private String posPage; // 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
    private String posType; //定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效
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

    public Long getMsignAddSignPosId() {
        return msignAddSignPosId;
    }

    public void setMsignAddSignPosId(Long msignAddSignPosId) {
        this.msignAddSignPosId = msignAddSignPosId;
    }

    public Long getMsignAddId() {
        return msignAddId;
    }

    public void setMsignAddId(Long msignAddId) {
        this.msignAddId = msignAddId;
    }

    public Long getMsignAddContractId() {
        return msignAddContractId;
    }

    public void setMsignAddContractId(Long msignAddContractId) {
        this.msignAddContractId = msignAddContractId;
    }

    public Long getMsignAddConInfoId() {
        return msignAddConInfoId;
    }

    public void setMsignAddConInfoId(Long msignAddConInfoId) {
        this.msignAddConInfoId = msignAddConInfoId;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPosPage() {
        return posPage;
    }

    public void setPosPage(String posPage) {
        this.posPage = posPage;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
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
