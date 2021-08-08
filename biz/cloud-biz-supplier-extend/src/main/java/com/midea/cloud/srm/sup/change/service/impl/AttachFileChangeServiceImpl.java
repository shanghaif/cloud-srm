package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.OpConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.change.entity.AttachFileChange;
import com.midea.cloud.srm.model.supplier.info.dto.AttachFileDTO;
import com.midea.cloud.srm.sup.change.mapper.AttachFileChangeMapper;
import com.midea.cloud.srm.sup.change.service.IAttachFileChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
*  <pre>
 *  企业注册附件变更表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 17:24:14
 *  修改内容:
 * </pre>
*/
@Service
public class AttachFileChangeServiceImpl extends ServiceImpl<AttachFileChangeMapper, AttachFileChange> implements IAttachFileChangeService {
    @Autowired
    private FileCenterClient fileCenterClient;

    @Override
    public void saveOrUpdateAttachs(List<AttachFileChange> attachFiles, Long companyId,Long changeId) {

        List<Long> fileIds = new ArrayList<>();
        for(AttachFileChange attachFileChange:attachFiles){
            if(attachFileChange.getFileChangeId() != null){
                this.updateById(attachFileChange);
            }else{
                Long id = IdGenrator.generate();
                attachFileChange.setFileChangeId(id);
                attachFileChange.setChangeId(changeId);
                attachFileChange.setCompanyId(companyId);
            }
//            fileIds.add(attachFileChange.getAttachmentPicFileId());
        }
        this.saveOrUpdateBatch(attachFiles);
//        if(!CollectionUtils.isEmpty(fileIds)) {
//            fileCenterClient.binding(fileIds, changeId);
//        }
    }

    @Override
    public void binding(List<Long> fileuploadIds, Long orderId) {

    }

    @Override
    public void deleteByChangeId(Long changeId) {
        AttachFileChange attachFileChange = new AttachFileChange();
        attachFileChange.setChangeId(changeId);
        attachFileChange.setOpType(OpConstant.add);
        List<AttachFileChange> addFiles = this.list(new QueryWrapper<>(attachFileChange));
        AttachFileChange deletByChangeId = new AttachFileChange();
        deletByChangeId.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deletByChangeId));
        for(AttachFileChange fileChange:addFiles){
            if(fileChange.getAttachmentPicFileId() != null){
                Fileupload fileupload = new Fileupload();
                fileupload.setFileuploadId(fileChange.getAttachmentPicFileId());
                fileCenterClient.deleteByParam(fileupload);
            }
        }
    }

    @Override
    public List<AttachFileChange> getByChangeId(Long changeId) {
        AttachFileChange attachFileChange = new AttachFileChange();
        attachFileChange.setChangeId(changeId);
        return this.list(new QueryWrapper<>(attachFileChange));
    }


}
