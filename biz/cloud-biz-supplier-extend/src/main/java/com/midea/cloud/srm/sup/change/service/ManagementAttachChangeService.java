package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.change.entity.FileuploadChange;
import com.midea.cloud.srm.model.supplier.change.entity.ManagementAttachChange;

import java.util.List;
import java.io.IOException;

/**
* <pre>
 *  后端开发 服务类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 12, 2021 5:16:45 PM
 *  修改内容:
 * </pre>
*/
public interface ManagementAttachChangeService extends IService<ManagementAttachChange>{
    /*
    批量更新或者批量新增
    */
     void saveOrUpdateAttachs(List<ManagementAttachChange> attachFiles, Long companyId, Long changeId);

    /**
     *  获取变更记录
     */
    List<ManagementAttachChange> getByChangeId(Long changeId);

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    void deleteByChangeId(Long changeId);
    /*
   分页查询
    */
    PageInfo<ManagementAttachChange> listPage(ManagementAttachChange managementAttachChange);


}
