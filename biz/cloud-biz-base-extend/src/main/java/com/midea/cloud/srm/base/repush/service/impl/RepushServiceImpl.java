package com.midea.cloud.srm.base.repush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.repush.service.RepushHandlerService;
import com.midea.cloud.srm.base.repush.mapper.RepushMapper;
import com.midea.cloud.srm.base.repush.service.RepushService;
import com.midea.cloud.srm.model.base.repush.entity.Repush;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *  接口重推实现
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 16:37
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class RepushServiceImpl extends ServiceImpl<RepushMapper, Repush> implements RepushService {

    @Autowired
    private RepushMapper repushMapper;

    @Resource
    private RepushHandlerService repushHandlerService;

    @Autowired
    private RibbonTemplateWrapper ribbonTemplateWrapper;


    @Override
    public List<Repush> listEffective() {
        return repushMapper.listEffective();
    }

    /**
     * 分页条件查询
     * @param repush
     * @return
     */
    @Override
    public List<Repush> listPageByParam(Repush repush) {
        QueryWrapper<Repush> queryWrapper = new QueryWrapper<Repush>();
        queryWrapper.like(StringUtils.isNotEmpty(repush.getTitle()), "TITLE", repush.getTitle());
        queryWrapper.eq(Objects.nonNull(repush.getPushStatus()), "PUSH_STATUS", repush.getPushStatus());
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(queryWrapper);
    }

    /**
     * 进行重推动作
     * @param repush
     * @return
     */
    @Override
    @Async
    public void doRePush(Repush repush){
        try {
            ribbonTemplateWrapper.postForObject("http://" + repush.getModule() + "/job-anon/internal/repushHandler/push", repush, String.class);
            if (log.isDebugEnabled()) {
                log.debug("重推接口成功: {}.{}" + repush.getCallServiceClassName(), repush.getCallServiceMethod());
            }
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("重推接口失败: {}.{}" + repush.getCallServiceClassName(), repush.getCallServiceMethod());
            }
            throw new BaseException("重推接口失败: " + repush.getTitle());
        }
    }

}
