package com.midea.cloud.srm.sup.reminderrecord.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.sup.ExpiredCertificateType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import com.midea.cloud.srm.model.supplier.reminderrecord.entity.ReminderRecord;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.IManagementAttachService;
import com.midea.cloud.srm.sup.info.service.IVendorInformationService;
import com.midea.cloud.srm.sup.reminderrecord.mapper.ReminderRecordMapper;
import com.midea.cloud.srm.sup.reminderrecord.service.IReminderRecordService;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ReminderRecordService extends ServiceImpl<ReminderRecordMapper,ReminderRecord> implements IReminderRecordService {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IFileRecordService iFileRecordService;

    @Autowired
    private IManagementAttachService iManagementAttachService;

    @Override
    public void saveReminderRecord(ManagementAttach managementAttach) {

        ReminderRecord reminderRecord = new ReminderRecord();
        copyAttribute(managementAttach,reminderRecord);
        if(managementAttach.getFormType().equals(ExpiredCertificateType.COMPANY_INFO.getValue())) {
            //id有疑问看xml
            Long id = managementAttach.getManagementAttachId();
            CompanyInfo companyInfo = iCompanyInfoService.getByCompanyId(id);
            Long fileId = Long.parseLong(companyInfo.getBusinessLicenseFileId());
            setOldRecord(reminderRecord, id, fileId, companyInfo.getBusinessLicense(), companyInfo.getBusinessEndDate());

        } else if(managementAttach.getFormType().equals(ExpiredCertificateType.MANAGEMENT_ATTACH.getValue())) {
            Long id = managementAttach.getManagementAttachId();
            ManagementAttach attach = iManagementAttachService.getById(id);
            setOldRecord(reminderRecord, id, attach.getFileuploadId(), attach.getAuthType(), attach.getEndDate());

        } else {
            Long id = managementAttach.getFileRecordId();
            FileRecord fileRecord = iFileRecordService.getById(id);
            LocalDate localDate = fileRecord.getFileValidDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            setOldRecord(reminderRecord, id, fileRecord.getFileId(), fileRecord.getFileName(), localDate);
        }

        this.save(reminderRecord);
    }

    @Override
    public List<ReminderRecord> getRecordsById(ManagementAttach managementAttach) {

        Long id = null;
        if(managementAttach.getFormType().equals(ExpiredCertificateType.COMPANY_INFO.getValue())) {
            id = managementAttach.getManagementAttachId();
        } else if(managementAttach.getFormType().equals(ExpiredCertificateType.MANAGEMENT_ATTACH.getValue())) {
            id = managementAttach.getManagementAttachId();
        } else {
            id = managementAttach.getFileRecordId();
        }
        List<ReminderRecord> reminderRecords = this.list(
                Wrappers.lambdaQuery(ReminderRecord.class)
                        .eq(ReminderRecord::getMixId,id));
        return reminderRecords;
    }

    private void copyAttribute(ManagementAttach src,ReminderRecord tar) {
        tar.setReminderRecordId(IdGenrator.generate());
        tar.setVendorName(src.getCompanyName());
        tar.setVendorCode(src.getCompanyCode());
        tar.setDataSources(src.getDataSources());
        tar.setFormType(src.getFormType());
        tar.setEndDate(src.getEndDate());
        tar.setFileUploadId(src.getFileId());
        tar.setAuthType(src.getAuthType());
    }

    private void setOldRecord(ReminderRecord reminderRecord,Long mixId, Long fileId, String authType, LocalDate endDate) {
        reminderRecord.setFileUploadIdOld(fileId);
        reminderRecord.setAuthTypeOld(authType);
        reminderRecord.setEndDateOld(endDate);
        reminderRecord.setMixId(mixId);
    }
}
