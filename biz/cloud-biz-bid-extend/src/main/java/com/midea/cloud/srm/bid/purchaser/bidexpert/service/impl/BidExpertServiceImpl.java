package com.midea.cloud.srm.bid.purchaser.bidexpert.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.bidexpert.mapper.BidExpertMapper;
import com.midea.cloud.srm.bid.purchaser.bidexpert.service.IBidExpertService;
import com.midea.cloud.srm.model.bid.purchaser.bidexpert.entity.BidExpert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  专家库表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-19 20:42:21
 *  修改内容:
 * </pre>
 */
@Service
public class BidExpertServiceImpl extends ServiceImpl<BidExpertMapper, BidExpert> implements IBidExpertService {

    @Override
    public PageInfo<BidExpert> listPage(BidExpert bidExpert) {
        PageUtil.startPage(bidExpert.getPageNum(), bidExpert.getPageSize());
        QueryWrapper<BidExpert> wrapper = new QueryWrapper<BidExpert>();
        if (StringUtils.isNoneBlank(bidExpert.getExpertName())) {
            wrapper.like("EXPERT_NAME", bidExpert.getExpertName());
        }
        if (StringUtils.isNoneBlank(bidExpert.getStatus())) {
            wrapper.like("STATUS", bidExpert.getStatus());
        }
        return new PageInfo<BidExpert>(this.list(wrapper));
    }

    @Override
    public void saveOrUpdateBidExpertBatch(List<BidExpert> bidExpertList) {
        for (BidExpert bidExpert : bidExpertList) {
            checkParam(bidExpert);
            if (bidExpert.getExpertId() != null) {
                //更新
                this.updateById(bidExpert);
            } else {
                //新增
                Long id = IdGenrator.generate();
                bidExpert.setExpertId(id).setStartDate(LocalDate.now());
                this.save(bidExpert);
            }
        }
    }

    private void checkParam(BidExpert bidExpert) {
        Assert.notNull(bidExpert.getExpertName(), "专家姓名不能为空");
        Assert.notNull(bidExpert.getExpertType(), "专家类型不能为空");
        Assert.notNull(bidExpert.getPhone(), "电话不能为空");
        Assert.notNull(bidExpert.getEmail(), "邮箱不能为空");
    }

    @Override
    public void invalid(List<Long> idList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(idList), "失效内容不能为空");
        for (Long id : idList) {
            Assert.notNull(id, "未保存的专家信息不能失效,请检查");
            this.update(new BidExpert().setStatus("N").setEndDate(LocalDate.now()),
                    new UpdateWrapper<>(new BidExpert().setExpertId(id)));
        }
    }

}
