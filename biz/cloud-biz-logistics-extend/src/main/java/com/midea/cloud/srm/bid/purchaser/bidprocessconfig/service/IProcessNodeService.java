package com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.bidprocessconfig.entity.ProcessNode;

import java.util.List;

/**
*  <pre>
 *  招标流程节点标记表 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-27 09:03:45
 *  修改内容:
 * </pre>
*/
public interface IProcessNodeService extends IService<ProcessNode> {
    boolean saveBatchNode(List<ProcessNode> nodes);

    List<ProcessNode> saveNodes(Long bidingId, Long processConfigId);

    List<ProcessNode> updateNodeStatus(ProcessNode processNode);

}
