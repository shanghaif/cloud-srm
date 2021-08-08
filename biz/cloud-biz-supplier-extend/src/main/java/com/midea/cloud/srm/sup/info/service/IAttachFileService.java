package com.midea.cloud.srm.sup.info.service;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;

import java.util.List;

/**
*  <pre>
 *  企业注册附件表 服务类
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
public interface IAttachFileService {

    void saveOrUpdateAttachs(List<Fileupload> attachFiles, Long companyId);

    void bindingFileupload(List<Fileupload> fileuploads, Long orderId);

    void removeByOrderId(Long orderId);

    void removeByFileId(Long fileId);
}
