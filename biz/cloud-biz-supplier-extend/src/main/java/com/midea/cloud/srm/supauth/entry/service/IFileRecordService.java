package com.midea.cloud.srm.supauth.entry.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;


/**
* <pre>
 *  准入附件记录 服务类
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
public interface IFileRecordService extends IService<FileRecord>{
    /*
     批量增加
     */
    void batchAdd(List<FileRecord> fileRecordList) throws IOException;

    /*
     批量更新
     */
    void batchUpdate(List<FileRecord> fileRecordList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;

   /*
   分页查询
    */
    PageInfo<FileRecord> listPage(FileRecord fileRecord);

    /**
     * 批量更新
     * @param fileRecordList
     * @param formId
     * @param formType
     */
    public void batchSaveOrUpdate(List<FileRecord> fileRecordList,Long formId,String formType);

    /**
     * 是否启用提醒
     */
    void blockUpOrStartUpReminder(Long id, String isUseReminder);

    /**
     * 更新附件
     */
    void updateUpReminder(ManagementAttach managementAttach);
    /**
     * 根据表单ID和类型获取数据
     * @param formId
     * @param formType
     * @return
     */
    public List<FileRecord> getFileRecord(Long formId,String formType);

}
