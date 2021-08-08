package com.midea.cloud.signature.electronicsignature.dto;

import java.util.Date;

public class SrmSysMsignAddConInfoDto {
    private Long msignAddConInfoId; //主键ID
    private Long msignAddId; //新增智慧签约合同头表ID
    private Long msignAddContractId; //新增智慧签约合同-签署合同详细信息ID
    private String signatoryInfoId; //签署方企业证件号（或个人）
    private String signatoryInfoName; //签署方企业名称（或个人）
    private String signType; //签章类型(SINGLE单页签章、MULTI多页签章、EDGES骑缝章、KEY关键字签章、AUTO最后一页自动盖章）
    private String publicOrPrivate;//对公或对私
    private String hText; //印章名称，对公可以指定
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

    private SrmSysMsignAddSignPosDto signPos;

    public String getPublicOrPrivate() {
        return publicOrPrivate;
    }

    public void setPublicOrPrivate(String publicOrPrivate) {
        this.publicOrPrivate = publicOrPrivate;
    }

    public Long getMsignAddConInfoId() {
        return msignAddConInfoId;
    }

    public void setMsignAddConInfoId(Long msignAddConInfoId) {
        this.msignAddConInfoId = msignAddConInfoId;
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

    public String getSignatoryInfoId() {
        return signatoryInfoId;
    }

    public void setSignatoryInfoId(String signatoryInfoId) {
        this.signatoryInfoId = signatoryInfoId;
    }

    public String getSignatoryInfoName() {
        return signatoryInfoName;
    }

    public void setSignatoryInfoName(String signatoryInfoName) {
        this.signatoryInfoName = signatoryInfoName;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public SrmSysMsignAddSignPosDto getSignPos() {
        return signPos;
    }

    public void setSignPos(SrmSysMsignAddSignPosDto signPos) {
        this.signPos = signPos;
    }

    public String gethText() {
        return hText;
    }

    public void sethText(String hText) {
        this.hText = hText;
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
