package com.midea.cloud.srm.sup.info.service.impl;

import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.sup.info.service.IAttachFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
*  <pre>
 *  企业注册附件表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-09 15:44:17
 *  修改内容:
 * </pre>
*/
@Service
public class AttachFileServiceImpl implements IAttachFileService {
    @Autowired
    private FileCenterClient fileCenterClient;

    @Override
    public void saveOrUpdateAttachs(List<Fileupload> attachFiles, Long companyId) {
        if(!CollectionUtils.isEmpty(attachFiles)) {
            fileCenterClient.bindingFileupload(attachFiles, companyId);
        }
    }

    @Override
    public void removeByOrderId(Long orderId) {
        Fileupload fileupload = new Fileupload();
        fileupload.setBusinessId(orderId);
        if(fileupload != null
                && fileupload.getBusinessId() != null) {
            fileCenterClient.deleteByParam(fileupload);
        }
    }

    @Override
    public void bindingFileupload(List<Fileupload> fileuploads, Long orderId) {
        fileCenterClient.bindingFileupload(fileuploads, orderId);
    }

    @Override
    public void removeByFileId(Long fileId) {
        Assert.notNull(fileId,"id不能为空");
        fileCenterClient.delete(fileId);
    }
}
