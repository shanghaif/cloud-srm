package com.midea.cloud.srm.bid.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.mapper.LgtRoundMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.service.ILgtRoundService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtRound;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * <pre>
 * 招标轮次
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 下午5:22:45
 *  修改内容:
 *          </pre>
 */
@Service
public class LgtRoundServiceImpl extends ServiceImpl<LgtRoundMapper, LgtRound> implements ILgtRoundService {

    @Autowired
    private IBidingService bidingService;

    @Override
    public void publicResult(Long bidingId) {
        Assert.notNull(bidingId, "招标单id不能为空");
        Biding biding = bidingService.getById(bidingId);
        //不是最后一轮或未结束评选则退出
        if (
//                !"Y".equals(biding.getFinalRound()) ||
                        !"Y".equals(biding.getEndEvaluation())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("不是最后一轮或未结束评选,不能公示招标结果"));
        }
        this.update(new LgtRound().setPublicResult("Y").setPublicResultTime(new Date()),
                new UpdateWrapper<>(new LgtRound().setBidingId(bidingId).setRound(biding.getCurrentRound())));
    }
}

