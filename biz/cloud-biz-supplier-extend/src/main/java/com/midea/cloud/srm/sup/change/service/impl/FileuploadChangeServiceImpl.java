package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.OpConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.change.entity.FileuploadChange;
import com.midea.cloud.srm.sup.change.mapper.FileuploadChangeMapper;
import com.midea.cloud.srm.sup.change.service.IFileuploadChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
*  <pre>
 *  文件详情记录变更 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-20 10:43:38
 *  修改内容:
 * </pre>
*/
@Service
public class FileuploadChangeServiceImpl extends ServiceImpl<FileuploadChangeMapper, FileuploadChange> implements IFileuploadChangeService {
    @Autowired
    private FileCenterClient fileCenterClient;

    @Override
    public void saveOrUpdateAttachs(List<FileuploadChange> attachFiles, Long companyId, Long changeId) {
       if(!CollectionUtils.isEmpty(attachFiles)){
           for(FileuploadChange fileuploadChange:attachFiles){
               fileuploadChange.setChangeId(changeId);
               fileuploadChange.setBusinessId(companyId);
                if(fileuploadChange.getFileuploadChangeId() != null){
                        this.updateById(fileuploadChange);
                }else {
                    Long id = IdGenrator.generate();
                    fileuploadChange.setFileuploadChangeId(id);
                    this.save(fileuploadChange);
                }
           }
       }
    }

    @Override
    public void deleteByChangeId(Long changeId) {
        FileuploadChange attachFileChange = new FileuploadChange();
        attachFileChange.setChangeId(changeId);
        attachFileChange.setOpType(OpConstant.add);
        List<FileuploadChange> addFiles = this.list(new QueryWrapper<>(attachFileChange));
        FileuploadChange deletByChangeId = new FileuploadChange();
        deletByChangeId.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deletByChangeId));
        for(FileuploadChange fileChange:addFiles){
            if(fileChange.getFileuploadId() != null){
                Fileupload fileupload = new Fileupload();
                fileupload.setFileuploadId(fileChange.getFileuploadId()).setBusinessId(fileChange.getBusinessId());
                fileCenterClient.deleteByParam(fileupload);
            }
        }
    }

    @Override
    public List<FileuploadChange> getByChangeId(Long changeId) {
        FileuploadChange attachFileChange = new FileuploadChange();
        attachFileChange.setChangeId(changeId);
        return this.list(new QueryWrapper<>(attachFileChange));
    }
}
