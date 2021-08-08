package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.FileuploadChange;

import java.util.List;

/**
*  <pre>
 *  文件详情记录变更 服务类
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
public interface IFileuploadChangeService extends IService<FileuploadChange> {
    void saveOrUpdateAttachs(List<FileuploadChange> attachFiles, Long companyId, Long changeId);


    void deleteByChangeId(Long changeId);

    List<FileuploadChange> getByChangeId(Long changeId);

}
