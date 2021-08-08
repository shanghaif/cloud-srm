package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidProcessMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidProcessService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidProcess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *  招标流程表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 18:58:55
 *  修改内容:
 * </pre>
 */
@Service
public class BidProcessServiceImpl extends ServiceImpl<BidProcessMapper, BidProcess> implements IBidProcessService {

    @Override
    @Transactional
    public void updateByBidingId(BidProcess bidProcess) {
        this.update(bidProcess, new UpdateWrapper<BidProcess>().setEntity(
                new BidProcess().setBidingId(bidProcess.getBidingId())));
    }
}
