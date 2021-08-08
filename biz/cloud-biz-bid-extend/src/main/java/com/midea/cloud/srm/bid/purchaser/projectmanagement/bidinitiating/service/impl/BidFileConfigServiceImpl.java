package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidFileConfigMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidFileConfigService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 *  供方必须上传附件配置表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 20:13:34
 *  修改内容:
 * </pre>
 */
@Service
public class BidFileConfigServiceImpl extends ServiceImpl<BidFileConfigMapper, BidFileConfig> implements IBidFileConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchBidFileConfig(List<BidFileConfig> bidFileConfigList, Long bidingId) {
        if (CollectionUtils.isNotEmpty(bidFileConfigList)) {
            for (BidFileConfig fileConfig : bidFileConfigList) {
                Long id = IdGenrator.generate();
                fileConfig.setBidingId(bidingId).setRequireId(id);
            }
            this.saveBatch(bidFileConfigList);
        }

    }

    @Override
    @Transactional
    public void updateBatchBidFileConfig(List<BidFileConfig> bidFileConfigList, Biding biding) {
        //先根据招标id删除
        Long bidingId = biding.getBidingId();
       /* Assert.isTrue(CollectionUtils.isNotEmpty(bidFileConfigList), "供方必须上传附件配置不能为空");
        Assert.notNull(bidingId, "更新供方必须上传附件配置-招标id不能为空");*/
        BidFileConfig bidFileConfig = new BidFileConfig().setBidingId(bidingId);
        QueryWrapper<BidFileConfig> wrapper = new QueryWrapper<>(bidFileConfig);
        this.remove(wrapper);

        //批量新增
        this.saveBatchBidFileConfig(bidFileConfigList, bidingId);
    }
}
