package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidFileMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidFileService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class BidFileServiceImpl extends ServiceImpl<BidFileMapper, BidFile> implements IBidFileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchBidFile(List<BidFile> fileList, Long bidingId) {
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (BidFile file : fileList) {
                Long id = IdGenrator.generate();
                file.setBidingId(bidingId).setFileId(id);
            }
            this.saveBatch(fileList);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchBidFile(List<BidFile> fileList, Biding biding) {
        //先根据招标id删除
        Long bidingId = biding.getBidingId();
//        Assert.isTrue(CollectionUtils.isNotEmpty(fileList), "附件不能为空");
        Assert.notNull(bidingId, "更新附件-招标id不能为空");
        BidFile bidFile = new BidFile().setBidingId(bidingId);
        QueryWrapper<BidFile> wrapper = new QueryWrapper<>(bidFile);
        this.remove(wrapper);

        //批量新增
        this.saveBatchBidFile(fileList, bidingId);
    }
}
