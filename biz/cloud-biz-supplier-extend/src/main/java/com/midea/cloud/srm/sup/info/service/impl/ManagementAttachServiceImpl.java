package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import com.midea.cloud.srm.sup.info.mapper.ManagementAttachMapper;
import com.midea.cloud.srm.sup.info.service.IManagementAttachService;
import org.springframework.stereotype.Service;

/**
*  <pre>
 *  管理体系信息认证附件表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:49:58
 *  修改内容:
 * </pre>
*/
@Service
public class ManagementAttachServiceImpl extends ServiceImpl<ManagementAttachMapper, ManagementAttach> implements IManagementAttachService {

    @Override
    public void startUpOrBlockUpReminder(Long id, String isReminder) {
        ManagementAttach managementAttach =new ManagementAttach();
        managementAttach.setManagementAttachId(id);
        managementAttach.setIsUseReminder(isReminder);
        this.updateById(managementAttach);
    }

    @Override
    public void updateUpReminder(ManagementAttach managementAttach) {
        ManagementAttach attach =new ManagementAttach();
        attach.setManagementAttachId(managementAttach.getManagementAttachId());
        attach.setAuthType(managementAttach.getAuthType());
        attach.setFileuploadId(managementAttach.getFileId());
        attach.setEndDate(managementAttach.getEndDate());
        this.updateById(attach);
    }
}
