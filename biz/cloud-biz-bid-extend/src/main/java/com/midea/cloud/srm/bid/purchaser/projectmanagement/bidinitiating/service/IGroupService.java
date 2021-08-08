package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Group;

import java.util.List;
public interface IGroupService extends IService<Group> {

    void saveBatchGroup(List<Group> groupList, Long bidingId);

    void updateBatchGroup(List<Group> groupList, Biding biding);
}