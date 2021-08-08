package com.midea.cloud.signature.electronicsignature.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

public class SrmBidAuctionSignUpDto {
    private Long signUpId; //ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bidAuctionVendorId; //邀请供应商ID
    private String isJoin; //是否参与(Y/N)
    private Long bidAuctionId; //招标单ID
    private Long bidInvitationFileId; //投标邀请书回执附件ID
    private String bidInvitationFileName; //投标邀请书回执附件名称
    private Long bidBondFileId; //投标保证金冻结书附件ID
    private String bidBondFileName; //投标保证金冻结书附件名
    private String status; //报名状态
    private String rejectReason; //驳回原因
    private String notParticipatingReason; //不参与原因
    private String commitBy; //提交人
    private String commitName; //提交人名称
    private Date commitDate; //提交时间
    private String auditBy; //审核人
    private String auditName; //审核人名称
    private Date auditTime; //审核时间
    private Integer deleteFlag;
    private String createdBy; //创建人账号
    private Date creationDate; //创建时间
    private String lastUpdatedBy; //最后更新时间
    private Date lastUpdateDate; //最后更新时间
    private String createdFullName; //创建人姓名
    private String lastUpdatedFullName; //最后更新人姓名
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String platFormPdfFileId; //签约后的平台ID
    private String platFormPdfFileName; //签约后的平台文件名字
    private Integer platPdfFileSize; //平台文件大小
    private String documentPdfFileId; //签约后的标书id
    private String documentPdfFileName; //签约后的标书名字
    private String documentPdfFileSize; //签约后的文件大小

    public Long getSignUpId() {
        return signUpId;
    }

    public void setSignUpId(Long signUpId) {
        this.signUpId = signUpId;
    }

    public Long getBidAuctionVendorId() {
        return bidAuctionVendorId;
    }

    public void setBidAuctionVendorId(Long bidAuctionVendorId) {
        this.bidAuctionVendorId = bidAuctionVendorId;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public Long getBidAuctionId() {
        return bidAuctionId;
    }

    public void setBidAuctionId(Long bidAuctionId) {
        this.bidAuctionId = bidAuctionId;
    }

    public Long getBidInvitationFileId() {
        return bidInvitationFileId;
    }

    public void setBidInvitationFileId(Long bidInvitationFileId) {
        this.bidInvitationFileId = bidInvitationFileId;
    }

    public String getBidInvitationFileName() {
        return bidInvitationFileName;
    }

    public void setBidInvitationFileName(String bidInvitationFileName) {
        this.bidInvitationFileName = bidInvitationFileName;
    }

    public Long getBidBondFileId() {
        return bidBondFileId;
    }

    public void setBidBondFileId(Long bidBondFileId) {
        this.bidBondFileId = bidBondFileId;
    }

    public String getBidBondFileName() {
        return bidBondFileName;
    }

    public void setBidBondFileName(String bidBondFileName) {
        this.bidBondFileName = bidBondFileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getNotParticipatingReason() {
        return notParticipatingReason;
    }

    public void setNotParticipatingReason(String notParticipatingReason) {
        this.notParticipatingReason = notParticipatingReason;
    }

    public String getCommitBy() {
        return commitBy;
    }

    public void setCommitBy(String commitBy) {
        this.commitBy = commitBy;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    public String getAttribute11() {
        return attribute11;
    }

    public void setAttribute11(String attribute11) {
        this.attribute11 = attribute11;
    }

    public String getAttribute12() {
        return attribute12;
    }

    public void setAttribute12(String attribute12) {
        this.attribute12 = attribute12;
    }

    public String getAttribute13() {
        return attribute13;
    }

    public void setAttribute13(String attribute13) {
        this.attribute13 = attribute13;
    }

    public String getAttribute14() {
        return attribute14;
    }

    public void setAttribute14(String attribute14) {
        this.attribute14 = attribute14;
    }

    public String getAttribute15() {
        return attribute15;
    }

    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public String getPlatFormPdfFileId() {
        return platFormPdfFileId;
    }

    public void setPlatFormPdfFileId(String platFormPdfFileId) {
        this.platFormPdfFileId = platFormPdfFileId;
    }

    public String getPlatFormPdfFileName() {
        return platFormPdfFileName;
    }

    public void setPlatFormPdfFileName(String platFormPdfFileName) {
        this.platFormPdfFileName = platFormPdfFileName;
    }

    public Integer getPlatPdfFileSize() {
        return platPdfFileSize;
    }

    public void setPlatPdfFileSize(Integer platPdfFileSize) {
        this.platPdfFileSize = platPdfFileSize;
    }

    public String getDocumentPdfFileId() {
        return documentPdfFileId;
    }

    public void setDocumentPdfFileId(String documentPdfFileId) {
        this.documentPdfFileId = documentPdfFileId;
    }

    public String getDocumentPdfFileName() {
        return documentPdfFileName;
    }

    public void setDocumentPdfFileName(String documentPdfFileName) {
        this.documentPdfFileName = documentPdfFileName;
    }

    public String getDocumentPdfFileSize() {
        return documentPdfFileSize;
    }

    public void setDocumentPdfFileSize(String documentPdfFileSize) {
        this.documentPdfFileSize = documentPdfFileSize;
    }
}
