package com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.mapper.BidProcessConfigMapper;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IBidProcessConfigService;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.BidProcessConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <pre>
 *  招标流程配置表 服务实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * <p>
 * 修改人:
 * 修改日期: 2020-03-16 15:01:13
 * 修改内容:
 * <pre>
 *  修改记录
 *  修改后版本:
 * </pre>
 */
@Service
public class BidProcessConfigServiceImpl extends ServiceImpl<BidProcessConfigMapper, BidProcessConfig> implements IBidProcessConfigService {

    @Override
    @Transactional
    public void saveOrUpdateConfig(BidProcessConfig bidProcessConfig) {
        checkSaveOrUpdateParam(bidProcessConfig);
        if (bidProcessConfig.getProcessConfigId() == null) {
            Long id = IdGenrator.generate();
            bidProcessConfig.setProcessConfigId(id);
        }
        this.saveOrUpdate(bidProcessConfig);

    }

    private void checkSaveOrUpdateParam(BidProcessConfig bidProcessConfig) {
        Assert.hasText(bidProcessConfig.getProcessConfigName(), "模板名称不能为空");
        Assert.hasText(bidProcessConfig.getComments(), "模板简述不能为空");
        //模板名称唯一校验
        QueryWrapper<BidProcessConfig> queryWrapper = new QueryWrapper<>();
        if (bidProcessConfig.getProcessConfigId() != null) {
            queryWrapper.ne("PROCESS_CONFIG_ID", bidProcessConfig.getProcessConfigId());
        }
        queryWrapper.eq("PROCESS_CONFIG_NAME", bidProcessConfig.getProcessConfigName());
        List<BidProcessConfig> infoList = this.list(queryWrapper);
        Assert.isTrue(CollectionUtils.isEmpty(infoList), "已存在相同的模板名称");
    }
}
