package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;

import java.util.List;

/**
*  <pre>
 *  价格审批单附件 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-23 17:40:03
 *  修改内容:
 * </pre>
*/
public interface IApprovalFileService extends IService<ApprovalFile> {

    /**
     * 根据审批投获取审批附件
     */
    List<ApprovalFile> getByApprovalHeadId(Long approvalHeaderId);
}
