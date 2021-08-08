package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.AttachFileChange;

import java.util.List;

/**
*  <pre>
 *  企业注册附件变更表 服务类
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
public interface IAttachFileChangeService extends IService<AttachFileChange> {
    void saveOrUpdateAttachs(List<AttachFileChange> attachFiles, Long companyId,Long changeId);

    void binding(List<Long> fileuploadIds, Long orderId);

    void deleteByChangeId(Long changeId);

    List<AttachFileChange> getByChangeId(Long changeId);
}
