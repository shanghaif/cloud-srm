package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;

/**
*  <pre>
 *  管理体系信息认证附件表 服务类
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
public interface IManagementAttachService extends IService<ManagementAttach> {

    void startUpOrBlockUpReminder(Long id, String isReminder);

    void updateUpReminder(ManagementAttach managementAttach);
}
