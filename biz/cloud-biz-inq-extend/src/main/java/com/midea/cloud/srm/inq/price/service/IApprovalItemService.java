package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalItem;

import java.util.List;

/**
*  <pre>
 *  价格审批单行表 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-08 15:28:50
 *  修改内容:
 * </pre>
*/
public interface IApprovalItemService extends IService<ApprovalItem> {

    /**
     * 根据价格审核头id查询审核行信息
     */
    List<ApprovalItem> queryByApprovalHeaderId(Long approvalHeaderId);
}
