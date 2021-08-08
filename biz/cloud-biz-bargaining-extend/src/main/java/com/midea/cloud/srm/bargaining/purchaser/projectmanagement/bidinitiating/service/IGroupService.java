package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;

import java.util.List;

/**
*  <pre>
 *  招标工作小组表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 13:59:12
 *  修改内容:
 * </pre>
*/
public interface IGroupService extends IService<Group> {

    void saveBatchGroup(List<Group> groupList, Long bidingId);

    void updateBatchGroup(List<Group> groupList, Biding biding);
}
