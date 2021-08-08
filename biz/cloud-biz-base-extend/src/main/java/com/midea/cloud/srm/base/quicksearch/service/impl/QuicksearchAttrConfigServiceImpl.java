package com.midea.cloud.srm.base.quicksearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.quicksearch.mapper.QuicksearchAttrConfigMapper;
import com.midea.cloud.srm.base.quicksearch.service.IQuicksearchAttrConfigService;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchAttrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 *  快速查询属性 实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
@Service
public class QuicksearchAttrConfigServiceImpl extends ServiceImpl<QuicksearchAttrConfigMapper, QuicksearchAttrConfig> implements IQuicksearchAttrConfigService {

    @Autowired
    private QuicksearchAttrConfigMapper quicksearchAttrConfigMapper;

    /**
     * 查询属性配置列表
     *
     * @param param
     * @return
     */
    @Override
    public List<QuicksearchAttrConfig> listQuicksearchAttrConfig(QuicksearchAttrConfig param) {
        QuicksearchAttrConfig selectAttrConfig = new QuicksearchAttrConfig();
        selectAttrConfig.setQuicksearchId(param.getQuicksearchId());
        QueryWrapper<QuicksearchAttrConfig> queryWrapper = new QueryWrapper<>(selectAttrConfig);
        return quicksearchAttrConfigMapper.selectList(queryWrapper);
    }


    /**
     * 根据快速查询id删除属性
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeByQuicksearchId(Long id) {
        QuicksearchAttrConfig selectAttrConfig = new QuicksearchAttrConfig();
        selectAttrConfig.setQuicksearchId(id);
        QueryWrapper<QuicksearchAttrConfig> queryWrapper = new QueryWrapper<>(selectAttrConfig);
        this.remove(queryWrapper);
    }
}

