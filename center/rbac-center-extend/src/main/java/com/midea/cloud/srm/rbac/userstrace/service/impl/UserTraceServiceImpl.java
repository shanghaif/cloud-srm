package com.midea.cloud.srm.rbac.userstrace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.log.trace.entity.UserTrace;
import com.midea.cloud.srm.rbac.userstrace.mapper.UserTraceMapper;
import com.midea.cloud.srm.rbac.userstrace.service.IUserTraceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 用户使用痕迹明细表
 * </pre>
 *
 * @author wangpr
 * @version 2.00.00
 * 创建时间:2020-6-8 13:54:18
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Service
@Slf4j
public class UserTraceServiceImpl extends ServiceImpl<UserTraceMapper, UserTrace> implements IUserTraceService {

    @Override
    public PageInfo<UserTrace> queryUserTracesDto(UserTrace userTrace) {
        PageUtil.startPage(userTrace.getPageNum(), userTrace.getPageSize());
        QueryWrapper<UserTrace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtil.notEmpty(userTrace.getNowDate()),"NOW_DATE",userTrace.getNowDate());
        queryWrapper.like(StringUtil.notEmpty(userTrace.getUsername()),"USERNAME",userTrace.getUsername());
        queryWrapper.like(StringUtil.notEmpty(userTrace.getUserType()),"USER_TYPE",userTrace.getUserType());
        queryWrapper.orderByDesc("TRACE_ID");
        return new PageInfo<>(this.list(queryWrapper));
    }

}
