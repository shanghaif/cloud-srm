package com.midea.cloud.srm.supauth.entry.service.impl;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.inq.inquiry.entity.File;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.supauth.entry.mapper.FileRecordMapper;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;

/**
* <pre>
 *  准入附件记录 服务实现类
 * </pre>
*
* @author kuangzm
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 25, 2021 11:15:34 PM
 *  修改内容:
 * </pre>
*/
@Service
public class FileRecordServiceImpl extends ServiceImpl<FileRecordMapper, FileRecord> implements IFileRecordService {

    @Override
    @Transactional
    public void batchAdd(List<FileRecord> fileRecordList) {
        //设置主键id
        for(FileRecord fileRecord :fileRecordList){
            Long id = IdGenrator.generate();
            fileRecord.setFileRecordId(id);
        }
        this.saveBatch(fileRecordList);
    }

    @Override
    @Transactional
    public void batchUpdate(List<FileRecord> fileRecordList) {
        this.saveOrUpdateBatch(fileRecordList);
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public PageInfo<FileRecord> listPage(FileRecord fileRecord) {
        PageUtil.startPage(fileRecord.getPageNum(), fileRecord.getPageSize());
        List<FileRecord> fileRecords = getFileRecords(fileRecord);
        return new PageInfo<>(fileRecords);
    }

    public List<FileRecord> getFileRecords(FileRecord fileRecord) {
        QueryWrapper<FileRecord> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",fileRecord.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",fileRecord.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",fileRecord.getStartDate()).
//                        le("CREATION_DATE",fileRecord.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
    
    
    @Override
    public void batchSaveOrUpdate(List<FileRecord> fileRecordList,Long formId,String formType) {
    	QueryWrapper<FileRecord> wrapper = new QueryWrapper<>();
    	wrapper.eq("FORM_ID", formId);
    	wrapper.eq("FORM_TYPE", formType);
    	this.remove(wrapper);
    	
        //设置主键id
        for(FileRecord fileRecord :fileRecordList){
            Long id = IdGenrator.generate();
            fileRecord.setFormId(formId);
            fileRecord.setFormType(formType);
            fileRecord.setFileRecordId(id);
        }
        this.saveBatch(fileRecordList);
    }

    @Override
    public void blockUpOrStartUpReminder(Long id, String isUseReminder) {
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileRecordId(id);
        fileRecord.setIsUseReminder(isUseReminder);
        this.updateById(fileRecord);
    }

    @Override
    public void updateUpReminder(ManagementAttach managementAttach) {
        FileRecord fileRecord =new FileRecord();
        fileRecord.setFileRecordId(managementAttach.getFileRecordId());
        fileRecord.setFileName(managementAttach.getAuthType());
        fileRecord.setFileId(managementAttach.getFileId());
        Date endDate = Date.from(managementAttach.getEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        fileRecord.setFileValidDate(endDate);
        this.updateById(fileRecord);
    }

    /**
     * 根据表单ID和类型获取数据
     * @param formId
     * @param formType
     * @return
     */
    public List<FileRecord> getFileRecord(Long formId,String formType) {
    	QueryWrapper<FileRecord> wrapper = new QueryWrapper<>();
    	wrapper.eq("FORM_ID", formId);
    	wrapper.eq("FORM_TYPE", formType);
    	return this.list(wrapper);
    }
}
