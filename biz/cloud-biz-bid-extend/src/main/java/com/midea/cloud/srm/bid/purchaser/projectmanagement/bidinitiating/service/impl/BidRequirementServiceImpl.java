package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.enums.QuotaDistributeType;

/**
 * <pre>
 *  招标需求表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:  tanjl11@meicloud.com
 *  修改日期: 2020-09-03 17:04:28
 *  修改内容:
 * </pre>
 */
@Service
public class BidRequirementServiceImpl extends ServiceImpl<BidRequirementMapper, BidRequirement> implements IBidRequirementService {

    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;
    @Autowired
    private IBidingService bidingService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private InqClient inqClient;
    @Autowired
    private BaseClient baseClient;

    private static final String REQUIREMENT_LOCK = "bid-requirement-lock";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBidRequirement(BidRequirement bidRequirement, List<BidRequirementLine> bidRequirementLineList) {
        Long bidingId = bidRequirement.getBidingId();
        Assert.notNull(bidingId, "招标id不能为空");
        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId, Biding::getQuotaDistributeType)
                .eq(Biding::getBidingId, bidingId)
        );
        if (null != biding) { //kuangzm 增减判断招标单存在的情况 才做验证
	        //审批后不操作
	        if (Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())) {
	            return;
	        }
	        String quotaDistributeType = biding.getQuotaDistributeType();
	        checkQuota(bidRequirementLineList, quotaDistributeType);
        }
        String key = REQUIREMENT_LOCK + bidingId;
        Boolean isLock = redisUtil.tryLock(key, 30, TimeUnit.SECONDS);
        if (isLock) {
            //查看是否已经有了需求头了,有就赋值并跳过
            try {
                BidRequirement one = getOne(Wrappers.lambdaQuery(BidRequirement.class).eq(BidRequirement::getBidingId, bidingId));
                if (Objects.nonNull(one)) {
                    BeanUtils.copyProperties(one, bidRequirement);
                } else {
                    Long id = IdGenrator.generate();
                    bidRequirement.setRequirementId(id);
                    this.save(bidRequirement);
                }
                //先删除
                iBidRequirementLineService.saveBidRequirementLineList(bidRequirementLineList, bidRequirement);
            } catch (Exception e) {
                throw new BaseException("需求头添加失败" + e.getMessage());
            } finally {
                redisUtil.unLock(key);
            }
        } else {
            throw new BaseException("此单被人占用，请稍后再试");
        }
        

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBidRequirement(BidRequirement bidRequirement, List<BidRequirementLine> bidRequirementLineList) {
        Assert.notNull(bidRequirement.getRequirementId(), "项目需求主键id不能为空");
        Assert.notNull(bidRequirement.getBidingId(), "招标id不能为空");
        /**
         * 校验中标行生效失效时间必填
         */
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(bidRequirementLineList)){
            bidRequirementLineList.forEach(bidRequirementLine -> {
                Date priceStartTime = bidRequirementLine.getPriceStartTime();
                Date priceEndTime = bidRequirementLine.getPriceEndTime();
                Assert.isTrue(null != priceStartTime && null != priceEndTime,"需求明细有效日期不能为空");
                Assert.isTrue(priceStartTime.before(priceEndTime),"需求明细生效日期不能大于失效日期");
            });
        }

        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId, Biding::getQuotaDistributeType)
                .eq(Biding::getBidingId, bidRequirement.getBidingId())
        );
        //审批后不操作
        if (Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())) {
            return;
        }

        String quotaDistributeType = biding.getQuotaDistributeType();
        checkQuota(bidRequirementLineList, quotaDistributeType);
        String key = REQUIREMENT_LOCK + bidRequirement.getBidingId();
        Boolean isLock = redisUtil.tryLock(key, 10, TimeUnit.MINUTES);
        if (isLock) {
            try {
                this.updateById(bidRequirement);
                iBidRequirementLineService.updateBatch(bidRequirementLineList, bidRequirement);
            } finally {
                redisUtil.unLock(key);
            }
        } else {
            throw new BaseException("此单被人占用，请稍后再试");
        }


    }

    @Override
    public BidRequirement getBidRequirement(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");
        return this.getOne(new QueryWrapper<BidRequirement>(new BidRequirement().setBidingId(bidingId)));
    }

    private void checkQuota(List<BidRequirementLine> bidRequirementLineList, String quotaDistributeType) {
        if (!Objects.equals(quotaDistributeType, QuotaDistributeType.NULL_ALLOCATION.getCode())) {
            Set<String> orgCodeSet = new HashSet<>();
            Set<Long> ouGroupIds = new HashSet<>();
            Set<Long> orgIds = new HashSet<>();
            for (BidRequirementLine bidRequirementLine : bidRequirementLineList) {
                Long orgId = bidRequirementLine.getOrgId();
                Long ouId = bidRequirementLine.getOuId();
                if (Objects.nonNull(orgId)) {
                    orgIds.add(orgId);
                }
                if (Objects.nonNull(ouId)) {
                    ouGroupIds.add(ouId);
                }
            }
            //判断所选事业部的配额是不是一致的
            if (!CollectionUtils.isEmpty(ouGroupIds)) {
                List<String> buCodeByOuGroupId = baseClient.getBuCodeByOuGroupId(ouGroupIds);
                if (!CollectionUtils.isEmpty(buCodeByOuGroupId)) {
                    buCodeByOuGroupId.stream().filter(e->Objects.nonNull(e)).forEach(e-> orgCodeSet.add(e));
                }
            }
            if (!CollectionUtils.isEmpty(orgIds)) {
                List<String> buCodeByOrgIds = baseClient.getBuCodeByOrgIds(orgIds);
                if (!CollectionUtils.isEmpty(buCodeByOrgIds)) {
                    buCodeByOrgIds.stream().filter(e->Objects.nonNull(e)).forEach(e->orgCodeSet.add(e));
                }
            }
            if (!CollectionUtils.isEmpty(orgCodeSet)) {
                Boolean sameQuotaInBuIds = inqClient.isSameQuotaInBuIds(orgCodeSet);
                if (!sameQuotaInBuIds) {
                    throw new BaseException("配额计算要求事业部对应配额配置一致！请检查配额配置或者把该单的配额计算方法置为空");
                }
            }
        }
    }
}
